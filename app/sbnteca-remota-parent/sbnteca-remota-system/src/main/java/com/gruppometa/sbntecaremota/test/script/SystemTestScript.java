package com.gruppometa.sbntecaremota.test.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.FileUtils;
import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.io.Files;
import com.gruppometa.sbntecaremota.util.Utility;

public class SystemTestScript {
	
	// configurazione script
	private Properties configuration;
	
	// random
	private Random random;
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(SystemTestScript.class);
	
	// document builder
	private static DocumentBuilder docBuilder = null;
	
	static {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(true);
		
		try {
			docBuilder = docFactory.newDocumentBuilder();
			
		} catch (ParserConfigurationException e) {
			docBuilder = null;
			logger.error(e.getMessage(), e);
		}
	}
	
	// namespace
	private static final String DC_NAMESPACE = "http://purl.org/dc/elements/1.1/";
	private static final String MAG_NAMESPACE = "http://www.iccu.sbn.it/metaAG1.pdf";
	
	
	
	public SystemTestScript(Properties configuration) {
		this.configuration = configuration;
		this.random = new Random(System.currentTimeMillis());
	}
	
	public boolean createRealSystemTest() {
		File directoryInput = new File(configuration.getProperty("systemTest.inputFolder"));
		File directoryOutput = new File(configuration.getProperty("systemTest.outputFolder"));
		Map<String, List<File>> projectMap = new HashMap<String, List<File>>();
		List<String> bigProjects = new ArrayList<String>();
		List<String> smallProjects = new ArrayList<String>();
		
		// copia i progetti originali nella directory di destinazione
		try {
			if(directoryInput.isDirectory()) {
				for(File child : directoryInput.listFiles()) {
					if(Utility.isCompressed(child.getName())) {
						int idx = child.getName().indexOf(".");
						String projectName = child.getName().substring(0, idx);
						String extension = child.getName().substring(idx + 1);
						List<File> mags = new ArrayList<File>();
						boolean existsProject = new File(directoryOutput, projectName).exists();
						int objectSize = this.unzipProject(projectName, extension, child, mags, !existsProject);
						
						if(objectSize >= 1000)
							bigProjects.add(projectName);
						
						else
							smallProjects.add(projectName);
							
						projectMap.put(projectName, mags);
					}
				}
			}

			// duplica i progetti
			this.duplicateProjects(projectMap, smallProjects, bigProjects);
			
			// cancella i progetti originali dalla directory di destinazione
			for(String originalProject : projectMap.keySet())
				FileUtils.deleteDirectory(new File(configuration.getProperty("systemTest.outputFolder"), originalProject));
			
			
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		
		return true;
	}
	
	private int unzipProject(String projectName, String extension, File zippedProject, 
			List<File> mags, boolean overrideIfExists) throws IOException {
		
		ArchiveStreamFactory arcFactory = new ArchiveStreamFactory();
	    CompressorInputStream cis = null;
	    ArchiveInputStream ais = null;
	    int numObjects = 0;
	    
	    

    	// ricerca tipo di archivio (eccezione se archivio non riconosciuto)
    	String archive = null;
    	
    	if(extension.startsWith("zip"))
    		archive = ArchiveStreamFactory.ZIP;
    	
    	else if(extension.startsWith("tar"))
    		archive = ArchiveStreamFactory.TAR;
    	
    	
    	
    	// ricerca tipo compressione (opzionale)
    	CompressorStreamFactory comprFactory = new CompressorStreamFactory();
    	String compression = null;
    	
		if(extension.contains(".bz2"))
			compression = CompressorStreamFactory.BZIP2;
		
		else if(extension.contains(".gz"))
			compression = CompressorStreamFactory.GZIP;
		
		else if(extension.contains(".Z"))
			compression = CompressorStreamFactory.Z;
		
		File directoryOutput = new File(configuration.getProperty("systemTest.outputFolder"), projectName);
		logger.info("Unzipping progetto '" + projectName + "' in corso ...");
		long t0 = System.currentTimeMillis();
		
    	// decompressione archivio
		try {
	    	if(archive != null) {
		    	ais = arcFactory.createArchiveInputStream(archive, compression == null ? new FileInputStream(zippedProject) : 
	    					comprFactory.createCompressorInputStream(compression, new FileInputStream(zippedProject)));
		    	
		    	ArchiveEntry entry = null;
		    	
		    	while((entry = ais.getNextEntry()) != null) {
			    	File destPath = new File(directoryOutput, entry.getName());
			    	File parentDir = destPath.getParentFile();
			    	
			    	// crea directory genitore
			    	if(!parentDir.exists() && overrideIfExists)
			    		parentDir.mkdirs();
			    	
			    	// salvataggio
			    	if(entry.isDirectory() && overrideIfExists) {
			    		if(!destPath.exists())
			    			destPath.mkdir();
			    	}
	
			    	else {
			    		if(overrideIfExists)
			    			Utility.saveFile(ais, destPath.getPath());
			    		
			        	if(entry.getName().endsWith("xml"))
			        		mags.add(destPath);
			        	
			        	else
			        		numObjects++;
			    	}
		    	}
	    	} 
	    	
		} catch (ArchiveException e) {
			throw new IOException(e.getMessage());
		} catch (CompressorException e) {
			throw new IOException(e.getMessage());
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		} finally {
			// chiusura stream
			if(cis != null)
				cis.close();
			
			if(ais != null)
				ais.close();
		}
		
		long t1 = System.currentTimeMillis();
		logger.info("Progetto decompresso in " + (t1 - t0) + " ms");
		return numObjects;
	}
	
	private void duplicateProjects(Map<String, List<File>> projectMap, 
			List<String> smallProjects, List<String> bigProjects) throws IOException {
		
		// parametri di configurazione
		File directoryOutput = new File(configuration.getProperty("systemTest.outputFolder"));
		
		int totalMags = Integer.parseInt(configuration.getProperty("systemTest.totalMags"));
		int smallMags = (totalMags * Integer.parseInt(configuration.getProperty("systemTest.smallMagPercent"))) / 100;
		int bigMags = (totalMags * Integer.parseInt(configuration.getProperty("systemTest.bigMagPercent"))) / 100;
		
		int minMags = Integer.parseInt(configuration.getProperty("systemTest.minMagPerProject"));
		int maxMags = Integer.parseInt(configuration.getProperty("systemTest.maxMagPerProject"));
		
		// algoritmo di creazione automatica
		while(totalMags > 0) {
			String selectedProject = null;
			boolean small = true;
			
			if(1 + random.nextInt(totalMags) <= smallMags)
				selectedProject = smallProjects.get(random.nextInt(smallProjects.size()));
			
			else {
				small = false;
				selectedProject = bigProjects.get(random.nextInt(bigProjects.size()));
			}
			
			// creazione numero di MAG per nuovo progetto
			int numMags = minMags + 1 + random.nextInt(maxMags - minMags);
			logger.info("Duplicazione progetto '" + selectedProject + "' in corso ...");
			long t0 = System.currentTimeMillis();
			
			// crea copia del progetto
			String duplicatedProject = this.addRandomString(selectedProject, " ", 5);
			File duplicatedProjectFile = new File(directoryOutput, duplicatedProject);
			this.addDuplicatedObjects(duplicatedProjectFile, new File(directoryOutput, selectedProject).listFiles(), "");
			Map<String, String> fieldPerProjectMap = new HashMap<String, String>();
			Map<String, String> staticSuffixes = new HashMap<String, String>();
			staticSuffixes.put("creator", this.addRandomString("", " ", 4));
			staticSuffixes.put("publisher", this.addRandomString("", " ", 4));
			staticSuffixes.put("contributor", this.addRandomString("", " ", 4));
			staticSuffixes.put("type", this.addRandomString("", " ", 4));
			staticSuffixes.put("format", this.addRandomString("", " ", 4));
			
			for(int i = 1; i <= numMags; i++) {
				List<File> selectionMags = new ArrayList<File>();
				selectionMags.addAll(projectMap.get(selectedProject));
				File selectedMag = selectionMags.get(random.nextInt(selectionMags.size()));
						
				logger.info("Duplicazione " + i  + "/" + numMags + " in corso...");
				long t2 = System.currentTimeMillis();
					
				if(docBuilder != null) {
					try {
						long t4 = System.currentTimeMillis();
						Document selectedDuplicatedDocument = docBuilder.
								parse(new InputSource(new FileReader(selectedMag)));
						
						long t5 = System.currentTimeMillis();
						logger.info("Lettura documento in " + (t5 - t4) + " ms");
						
						String relativePath = selectedMag.getPath().
								replaceAll(directoryOutput.getPath() + "/" + selectedProject + "/", "");
						
						int idx = relativePath.lastIndexOf("/");
						
						File duplicatedDirectoryMag = new File(duplicatedProjectFile, 
								idx >= 0 ? relativePath.substring(0, idx + 1) : "");
						
						long t6 = System.currentTimeMillis();
						
						File file = this.createMagFile(selectedDuplicatedDocument, 
								duplicatedDirectoryMag, fieldPerProjectMap, staticSuffixes);
						
						long t7 = System.currentTimeMillis();
						logger.info("Aggiornamento dati in " + (t7 - t6) + " ms");
						
						long t8 = System.currentTimeMillis();
						this.saveMag(selectedDuplicatedDocument, file);
						long t9 = System.currentTimeMillis();
						logger.info("Salvataggio file in " + (t9 - t8) + " ms");
						
					} catch (SAXException e) {
						throw new IOException(e.getMessage(), e);
					}
				}
				
				long t3 = System.currentTimeMillis();
				logger.info("MAG duplicato in " + (t3 - t2) + " ms");
			}
			
			// decrementa correttamente
			totalMags -= numMags;
			
			if(small) {
				smallMags -= numMags;
				
				if(smallMags < 0)
					smallMags = 0;
			}
			
			else {
				bigMags -= numMags;
				
				if(bigMags < 0)
					bigMags = 0;
			}
			
			// comprimi progetto
			this.compressDuplicatedProject(duplicatedProject);
			long t1 = System.currentTimeMillis();
			logger.info("Duplicazione progetto completata in " + (t1 - t0) + " ms");
		}
	}
	
	private String addRandomString(String originalString, String separator, int length) {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder randomBuilder = new StringBuilder();
		
		for(int i = 0; i < length; i++)
			randomBuilder.append(alphabet.charAt(random.nextInt(alphabet.length())));
		
		return originalString + separator + randomBuilder.toString();
	}
	
	private void compressDuplicatedProject(String duplicatedProject) throws IOException {
		File directoryOutput = new File(configuration.getProperty("systemTest.outputFolder"));
		File duplicatedProjectFile = new File(directoryOutput, duplicatedProject);
		String extension = configuration.getProperty("systemTest.duplicatedProjectExtension");
		ArchiveOutputStream archiveStream = null;
		
		try {
			archiveStream = Utility.createArchive(Utility.hasCompressor(extension) ? 
					Utility.createCompressor(new FileOutputStream(duplicatedProjectFile + "." + extension), extension) : 
						new FileOutputStream(duplicatedProjectFile + "." + extension), extension);
			
			
			this.addToCompressedFile(duplicatedProjectFile.listFiles(), "", archiveStream, extension);
			
		} catch (ArchiveException e) {
			throw new IOException(e.getMessage(), e);
		} catch (CompressorException e) {
			throw new IOException(e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new IOException(e.getMessage(), e);
		} finally {
			if(archiveStream != null) {
				try {
					archiveStream.close();
					
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		FileUtils.deleteDirectory(duplicatedProjectFile);
	}
	
	private void addToCompressedFile(File[] files, String prefix, 
			ArchiveOutputStream archiveStream, String extension) throws IOException {
		
		for(File file : files) {
			if(file.isDirectory()) {
				this.addToCompressedFile(file.listFiles(), prefix.isEmpty() ? 
						file.getName() : prefix + "/" + file.getName(), archiveStream, extension);
			}
			
			else {
				archiveStream.putArchiveEntry(Utility.createArchiveEntry((prefix.isEmpty() ? 
						"" : prefix + "/") + file.getName(), extension, file.length()));
				
				FileInputStream fileStream = new FileInputStream(file);
				IOUtils.copy(fileStream, archiveStream);
				fileStream.close();
				archiveStream.closeArchiveEntry();
			}
		}
	}
	
	private File createMagFile(Document doc, File createdDirectoryMag, 
			Map<String, String> fieldPerProjectMap, Map<String, String> staticSuffixes) {
		
		int threshold = Integer.parseInt(configuration.getProperty("systemTest.thresholdMagSize"));
		this.changeFieldProject(doc, MAG_NAMESPACE, "stprog", "/", fieldPerProjectMap);
		this.changeFieldProject(doc, MAG_NAMESPACE, "collection", " ", fieldPerProjectMap);
		this.changeFieldProject(doc, MAG_NAMESPACE, "agency", ", ", fieldPerProjectMap);
		this.changeFieldProject(doc, null, "library", " ", fieldPerProjectMap);
		this.changeFieldProject(doc, null, "part_name", " ", 4);
		this.changeFieldProjectStatic(doc, DC_NAMESPACE, "creator", staticSuffixes.get("creator"));
		this.changeFieldProjectStatic(doc, DC_NAMESPACE, "publisher", staticSuffixes.get("publisher"));
		this.changeFieldProjectStatic(doc, DC_NAMESPACE, "contributor", staticSuffixes.get("contributor"));
		this.changeFieldProjectStatic(doc, DC_NAMESPACE, "type", staticSuffixes.get("type"));
		this.changeFieldProjectStatic(doc, DC_NAMESPACE, "format", staticSuffixes.get("format"));
		this.changeFieldProjectStru(doc, MAG_NAMESPACE, "nomenclature", " ", 4);
		
		NodeList imgNodeList = doc.getElementsByTagNameNS(MAG_NAMESPACE, "img");
		
		if(imgNodeList.getLength() > threshold) {
			int numObjects = threshold + 1 + random.nextInt(imgNodeList.getLength() - threshold);
			List<Node> deleteNodeList = new ArrayList<Node>();
			
			for(int i = numObjects; i < imgNodeList.getLength(); i++)
				deleteNodeList.add(imgNodeList.item(i));
			
			for(Node deleteNode : deleteNodeList) {
				Node parent = deleteNode.getParentNode();
				parent.removeChild(deleteNode);
			}
			
			NodeList stopNodeList = doc.getElementsByTagNameNS(MAG_NAMESPACE, "stop");
			
			if(stopNodeList.getLength() > 0) {
				Element stopNode = (Element) stopNodeList.item(0);
				
				for(int j = 0; j < stopNode.getAttributes().getLength(); j++) {
					Attr attribute = (Attr) stopNode.getAttributes().item(j);
					
					if("sequence_number".equals(attribute.getLocalName()))
						attribute.setValue(numObjects + "");
				}
			}
		}
		
		String identifier = "";
		String issue = "";
		NodeList bibNodeList = doc.getElementsByTagNameNS(MAG_NAMESPACE, "bib");
		
		if(bibNodeList.getLength() > 0) {
			Element bibNode = (Element) bibNodeList.item(0);
			String level = bibNode.getAttribute("level");
			
			if(level != null && "s".equals(level)) {
				if(!staticSuffixes.containsKey("identifier"))
					staticSuffixes.put("identifier", this.addRandomString("", "_", 4));
				
				if(!staticSuffixes.containsKey("title"))
					staticSuffixes.put("title", this.addRandomString("", " ", 4));
				
				this.changeFieldProjectStatic(doc, DC_NAMESPACE, "identifier", staticSuffixes.get("identifier"));
				this.changeFieldProjectStatic(doc, DC_NAMESPACE, "title", staticSuffixes.get("title"));
				this.changeFieldProject(doc, null, "issue", " ", 4);
			}
			
			else {
				this.changeFieldProject(doc, DC_NAMESPACE, "identifier", "_", 4);
				this.changeFieldProject(doc, DC_NAMESPACE, "title", " ", 4);
			}
		}
		
		NodeList identifierNodeList = doc.getElementsByTagNameNS(DC_NAMESPACE, "identifier");
		
		if(identifierNodeList.getLength() > 0)
			identifier = identifierNodeList.item(0).getTextContent();
		
		NodeList issueNodeList = doc.getElementsByTagName("issue");
		
		if(issueNodeList.getLength() > 0)
			issue = issueNodeList.item(0).getTextContent();
		
		String fileName = identifier.replaceAll("[\\/\\\\]+", "_") + (issue.isEmpty() ? "" : "_" + issue.replaceAll("[ \t\n\r\f\\/\\\\]+", "_") ) + ".xml";
		return new File(createdDirectoryMag, fileName);
	}
	
	private void saveMag(Document doc, File createdMag) {
		try {
	    	// normalizza spazi vuoti
	    	XPath xPath = XPathFactory.newInstance().newXPath();
		    NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']", doc, XPathConstants.NODESET);
		 
		    for (int i = 0; i < nodeList.getLength(); ++i) {
		        Node node = nodeList.item(i);
		        node.getParentNode().removeChild(node);
		    }
		    
		    // salva nel file
		    Writer writer = new FileWriter(createdMag, Charset.forName("UTF-8"));
	    	StreamResult result = new StreamResult(writer);
	    	TransformerFactory factory = TransformerFactory.newInstance();
	    	factory.setAttribute("indent-number", 4);
	    	
	    	Transformer transformer = factory.newTransformer();
	    	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	    	transformer.transform(new DOMSource(doc), result);
	    	writer.close();
	    	
	    } catch(TransformerException e){
	        logger.error(e.getMessage(), e);
	    } catch (XPathExpressionException e) {
	    	logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	} 
	
	private void changeFieldProject(Document doc, String namespace, String tag, String separator,
			Map<String, String> fieldPerProjectMap) {
		
		NodeList nodeList = namespace != null ? doc.getElementsByTagNameNS(namespace, tag) : doc.getElementsByTagName(tag);
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			if(fieldPerProjectMap.containsKey(tag))
				nodeList.item(i).setTextContent(fieldPerProjectMap.get(tag));
			
			else {
				Node node = nodeList.item(i);
				String duplicateField = this.addRandomString(node.getTextContent(), separator, 4);
				node.setTextContent(duplicateField);
				fieldPerProjectMap.put(tag, duplicateField);
			}
		}
	}

	private void changeFieldProject(Document doc, String namespace, String tag, String separator, int length) {
		NodeList nodeList = namespace != null ? doc.getElementsByTagNameNS(namespace, tag) : doc.getElementsByTagName(tag);
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String duplicateField = this.addRandomString(node.getTextContent(), separator, length);
			node.setTextContent(duplicateField);
		}
	}

	private void changeFieldProjectStatic(Document doc, String namespace, String tag, String staticSuffix) {
		NodeList nodeList = namespace != null ? doc.getElementsByTagNameNS(namespace, tag) : doc.getElementsByTagName(tag);
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String duplicateField = node.getTextContent() + staticSuffix;
			node.setTextContent(duplicateField);
		}
	}
	
	private void changeFieldProjectStru(Document doc, String namespace, String tag, String separator, int length) {
		NodeList struNodeList = doc.getElementsByTagNameNS(MAG_NAMESPACE, "stru");
		
		for(int j = 0; j < struNodeList.getLength(); j++) {
			Element struNode = (Element) struNodeList.item(j);
			NodeList nodeList = namespace != null ? struNode.getElementsByTagNameNS(namespace, tag) : doc.getElementsByTagName(tag);
			
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String duplicateField = this.addRandomString(node.getTextContent(), separator, length);
				node.setTextContent(duplicateField);
			}
		}
	}
	
	private void addDuplicatedObjects(File duplicatedProjectFile, File[] projectChildrenFile, String prefix) throws IOException {
		for(File childFile : projectChildrenFile) {
			if(childFile.isDirectory()) {
				this.addDuplicatedObjects(duplicatedProjectFile, childFile.listFiles(), prefix.isEmpty() ? 
						childFile.getName() : prefix + "/" + childFile.getName());
			}
			
			else {
				if(!(childFile.getName().endsWith("xml") && Utility.isMag(childFile))) {
					File newFile = new File(duplicatedProjectFile, prefix.isEmpty() ? 
							childFile.getName() : prefix + "/" + childFile.getName());
					
					File parent = newFile.getParentFile();
					
					if(!parent.exists())
						parent.mkdirs();
					
					Files.copy(childFile, newFile);
				}
			}
		}
	}

}
