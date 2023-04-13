package com.gruppometa.sbntecaremota.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.ProjectSummary;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceTypes;

import javax.xml.xpath.*;

import org.xml.sax.InputSource;

/**
 * Metodi di utilità generale per lettura, parsing e scrittura file XML
 *
 *
 */
public class UtilXML {
	private static Logger logger = LoggerFactory.getLogger(UtilXML.class);

	public static XPath getXpathForMets() {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		xpath.setNamespaceContext(new NamespaceContext() {

			public String getNamespaceURI(String prefix) {
				if (prefix.equals("mets"))
					return "http://www.loc.gov/METS/";
				if (prefix.equals("rights"))
					return "http://cosimo.stanford.edu/sdr/metsrights/";
				if (prefix.equals("mods"))
					return "http://www.loc.gov/mods/v3";
				else
					return null;
			}

			public String getPrefix(String uri) {
				if (uri.equals("http://www.loc.gov/METS/"))
					return "mets";
				if (uri.equals("http://cosimo.stanford.edu/sdr/metsrights/"))
					return "rights";
				if (uri.equals("http://www.loc.gov/mods/v3"))
					return "mods";
				else
					return null;
			}

			@SuppressWarnings("rawtypes")
			public Iterator getPrefixes(String arg0) {
				return null;
			}
		});
		return xpath;
	}

	/**
	 * Elabora la risorsa di tipo img per la sua definizione (o ridefinizione) nelle versioni
	 * del MAG (acquisizione/esportazione)
	 * 
	 * @param docMag documento originale
	 * @param docInternal documento di acquisizione
	 * @param docExternal documento di esportazione
	 * @param magMetadigitInternal elemento root del MAG di acquisizione
	 * @param magMetadigitExternal elemento root del MAG di esportazione
	 * @param resource nodo risorsa img da elaborare
	 * @param usageA usage di acquisizione
	 * @param usageE usage di esportazione
	 * @param flagPubblica flag di pubblicazione
	 */
	public static void processImg(Document docMag, Document docInternal, Document docExternal,
            Node magMetadigitInternal, Node magMetadigitExternal, Node resource,
            List<String> usageA, List<String> usageE, Map<String, String> firstDigitalObjects) {
		
		// controllo degli usage img per acquisizione
		Node imgInternal = resource.cloneNode(true);
		List<Element> usageAImgNodeList = UtilXML.searchInResource(imgInternal, "http://www.iccu.sbn.it/metaAG1.pdf", "usage");
		boolean imgHasUsageA = hasUsageImg(usageAImgNodeList, usageA);
		List<Element> altimgANodeList = UtilXML.searchInResource(imgInternal, "http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
		processAltimg(docInternal, magMetadigitInternal, usageA, imgInternal, altimgANodeList, imgHasUsageA, firstDigitalObjects);
		
		// controllo degli usage img per esportazione
		Node imgExternal = resource.cloneNode(true);
		List<Element> usageEImgNodeList = UtilXML.searchInResource(imgExternal, "http://www.iccu.sbn.it/metaAG1.pdf", "usage");
		boolean imgHasUsageE = hasUsageImg(usageEImgNodeList, usageE);
		List<Element> altimgENodeList = UtilXML.searchInResource(imgExternal, "http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
		processAltimg(docExternal, magMetadigitExternal, usageE, imgExternal, altimgENodeList, imgHasUsageE, null);
	}
	
	/**
	 * Restituisce true se l'img di base (a risoluzione massima), contiene lo usage 
	 * indicato, altrimenti restituisce false (e si procede con la scelta dell'altimg)
	 * 
	 * @param usageImgNodeList
	 * @param usageVersion
	 * @return
	 */
	private static boolean hasUsageImg(List<Element> usageImgNodeList, List<String> usageVersion) {
		List<String> imgUsages = new ArrayList<String>();
		
		for(Element imgUsageNode : usageImgNodeList)
			imgUsages.add(imgUsageNode.getTextContent().trim());
		
		// assenza usages, default 3
		if(imgUsages.isEmpty())
			imgUsages.add("3");
		
		for(String imgUsage : imgUsages) {
			if(usageVersion.contains(imgUsage))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Elabora gli altimg per la costruzione delle versioni MAG
	 * 
	 * @param docVersion versione del documento MAG da elaborare (acquisizione/esportazione)
	 * @param magMetadigitVersion elemento root XML del documento MAG da elaborare (acquisizione/esportazione)
	 * @param usageA lista usage per la versione del documento (acquisizione/esportazione)
	 * @param imgVersion versione del nodo img della risorsa da elaborare
	 * @param altimgList lista dei nodi altimg
	 * @param export modalità export dele risorse digitali
	 * @param imgHasUsage se l'img originale contiene gli usage della versione (acquisizione/esportazione)
	 * @param firstDigitalObjects mappa oggetti digitali per preview
	 */
	private static void processAltimg(Document docVersion, Node magMetadigitVersion, 
			List<String> usageVersion, Node imgVersion, 
			List<Element> altimgList, boolean imgHasUsage, Map<String, String> firstDigitalObjects) {
		
		List<String> tagOrder = NormalizationUtility.getOrderContainer("img");
		List<String> altimgTags = NormalizationUtility.getOrderContainer("altimg");
		
		// se l'img originale non contiene gli usage seleziona altimg a minimo usage
		// contenuto nello usage della versione indicata
		boolean promotedAltimg = false;
		
		if(!imgHasUsage) {
			Element minAltimg = null;
			int promoteMinUsage = Integer.MAX_VALUE;
			
			// per ogni altimg controlla gli usage e calcola altimg con min usage
			for(Element altimg : altimgList) {
				List<Element> usageNodeList = UtilXML.searchInResource(altimg, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "usage");
				
				List<String> altimgUsages = new ArrayList<String>();
				
				for(Element usageNode : usageNodeList)
					altimgUsages.add(usageNode.getTextContent().trim());
				
				// assenza di usages, default 3
				if(altimgUsages.isEmpty())
					altimgUsages.add("3");
				
				for(String altimgUsage : altimgUsages) {
					int usageInt = Integer.parseInt(altimgUsage);
					
					if(usageVersion.contains(altimgUsage) && usageInt < promoteMinUsage) {
						minAltimg = altimg;
						promoteMinUsage = usageInt;
					}
				}
			}
			
			// se esiste altimg per la sostituzione rimuovi dalla lista altimg
			// e crea nuovo nodo altimg
			if(minAltimg != null) {
				promotedAltimg = true;
				altimgList.remove(minAltimg);
				
				Element imgNew = (Element) imgVersion.cloneNode(true);
				
				if(minAltimg.hasAttribute("imggroupID"))
					imgNew.setAttribute("imggroupID", minAltimg.getAttribute("imggroupID"));
				
				else
					imgNew.removeAttribute("imggroupID");
				
				// cancella tutti i nodi
				while (imgNew.hasChildNodes())
					imgNew.removeChild(imgNew.getFirstChild());
				
				imgNew.normalize();
				
				
				for(String tag : tagOrder) {
					// nodo presente solo in img
					if(!altimgTags.contains(tag)) {
						// se altimg accoda tuttgli altimg
						if("altimg".equals(tag)) {
							for(Element altimg : altimgList)
								imgNew.appendChild(altimg);
						}
						
						else {
							List<Element> tagList = UtilXML.searchInResource(imgVersion, 
									"http://www.iccu.sbn.it/metaAG1.pdf", tag);
							
							for(Element elem : tagList)
								imgNew.appendChild(elem);
						}
					}
					
					else {
						List<Element> tagList = UtilXML.searchInResource(minAltimg, 
								"http://www.iccu.sbn.it/metaAG1.pdf", tag);
						
						for(Element elem : tagList)
							imgNew.appendChild(elem);
					}
				}
				
				// nuova versione img
				imgNew.normalize();
				imgVersion = imgNew;
			}
		}
		
		if(imgHasUsage || promotedAltimg) {
			Node maxUsageNode = imgVersion;
			int usageMax = Integer.MIN_VALUE;
			
			// per ogni altimg controlla gli usage
			for(Element altimg : altimgList) {
				boolean hasUsage = false;
				
				List<Element> usageNodeList = UtilXML.searchInResource(altimg, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "usage");
				
				List<String> altimgUsages = new ArrayList<String>();
				
				for(Element usageNode : usageNodeList)
					altimgUsages.add(usageNode.getTextContent().trim());
				
				// assenza di usages, default 3
				if(altimgUsages.isEmpty())
					altimgUsages.add("3");
				
				for(String altimgUsage : altimgUsages) {
					if(usageVersion.contains(altimgUsage)) {
						hasUsage = true;
						int altimgUsageInt = Integer.parseInt(altimgUsage);
						
						if(altimgUsageInt > usageMax) {
							usageMax = altimgUsageInt;
							maxUsageNode = altimg;
						}
						
						break;
					}
				}
				
				// cancella altimg se non contiene lo usage
				if(!hasUsage)
					imgVersion.removeChild(altimg);
			}
			
			magMetadigitVersion.appendChild(docVersion.importNode(imgVersion, true));
			
			if(firstDigitalObjects != null && !firstDigitalObjects.containsKey("img")) {
				NodeList childrenNodeList = maxUsageNode.getChildNodes();
				
				for(int i = 0; i < childrenNodeList.getLength(); i++) {
					Node childNode = childrenNodeList.item(i);
					
					if(childNode.getNodeType() == Document.ELEMENT_NODE) {
						Element childElem = (Element) childNode;
						
						if("file".equals(childElem.getLocalName())) {
							String deliveryID = childElem.getAttributeNS("http://www.w3.org/TR/xlink", "href").
									replaceAll("digitalObject/", "").replaceAll("/original", "");
							
							firstDigitalObjects.put("img", deliveryID);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Esegue l'elaborazione degli audio e video per la costruzione delle versioni
	 * di acquisizione/esportazione
	 * 
	 * @param docMag documento originale
	 * @param docInternal documento di acquisizione
	 * @param docExternal documento di esportazione
	 * @param magMetadigitInternal elemento root del MAG di acquisizione
	 * @param magMetadigitExternal elemento root del MAG di esportazione
	 * @param resource nodo risorsa img da elaborare
	 * @param usageA usage di acquisizione
	 * @param usageE usage di esportazione
	 * @param firstDigitalObjects mappa dei primi oggetti digitali per ogni tipo di oggetto
	 * @param flagPubblica flag di pubblicazione
	 */
	private static void processAudioVideo(Document docMag, Document docInternal, Document docExternal,
            Node magMetadigitInternal, Node magMetadigitExternal, Node resource,
            List<String> usageA, List<String> usageE, Map<String, String> firstDigitalObjects) {
		
		// controllo degli usage per ogni proxies per acquisizione ed esportazione
		List<Element> proxyNodeList = UtilXML.searchInResource(resource, "http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
		List<Element> proxiesUsageA = new ArrayList<Element>();
		List<Element> proxiesUsageE = new ArrayList<Element>();
		
		for(int i = 0; i < proxyNodeList.size(); i++) {
			Element proxyNode = proxyNodeList.get(i);
			boolean proxyHasUsageA = false;
			boolean proxyHasUsageE = false;
			List<Element> proxyUsageNodeList = UtilXML.searchInResource(proxyNode, "http://www.iccu.sbn.it/metaAG1.pdf", "usage");
			List<String> proxyUsages = new ArrayList<String>();
			
			for(Element usageNode : proxyUsageNodeList)
				proxyUsages.add(usageNode.getTextContent().trim());
			
			// assenza usages, default 3
			if(proxyUsages.isEmpty())
				proxyUsages.add("3");
			
			for(String proxyUsage : proxyUsages) {
				if(usageA.contains(proxyUsage))
					proxyHasUsageA = true;
				
				if(usageE.contains(proxyUsage))
					proxyHasUsageE = true;
				
				if(proxyHasUsageA && proxyHasUsageE)
					break;
			}
			
			if(proxyHasUsageA) {
				proxiesUsageA.add(proxyNode);
				
				if(i == proxyNodeList.size() - 1 && !firstDigitalObjects.containsKey(resource.getLocalName())) {
					NodeList childrenNodeList = proxyNode.getChildNodes();
					
					for(int j = 0; j < childrenNodeList.getLength(); j++) {
						Node childNode = childrenNodeList.item(j);
						
						if(childNode.getNodeType() == Document.ELEMENT_NODE) {
							Element childElem = (Element) childNode;
							
							if("file".equals(childElem.getLocalName())) {
								String deliveryID = childElem.getAttributeNS("http://www.w3.org/TR/xlink", "href").
										replaceAll("digitalObject/", "").replaceAll("/original", "");
								
								firstDigitalObjects.put(resource.getLocalName(), deliveryID);
							}
						}
					}
				}
			}
			
			if(proxyHasUsageE)
				proxiesUsageE.add(proxyNode);
		}
		
		processProxies(resource, docInternal, magMetadigitInternal, proxiesUsageA);
		processProxies(resource, docExternal, magMetadigitExternal, proxiesUsageE);
	}
	
	/**
	 * Elabora i proxies per la risorsa digitale multimediale, filtrando per usage, per 
	 * la versione MAG selezionata
	 * 
	 * @param resource risorsa audio/video
	 * @param docVersion versione documento MAG (acquisizione/esportazione)
	 * @param magMetadigitVersion elemento root della versiona MAG
	 * @param proxiesUsage lista dei proxies accettati per la versione del MAG
	 */
	private static void processProxies(Node resource, Document docVersion, Node magMetadigitVersion, List<Element> proxiesUsage) {
		// se esiste almeno un proxy si aggiunge la risorsa al documento di acquisizione
		if(!proxiesUsage.isEmpty()) {
			// copia risorsa e cancella vecchi proxies
			Node audiovideoVersion = resource.cloneNode(true);
			
			List<Element> oldProxies = UtilXML.searchInResource(audiovideoVersion, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
			
			for(Element oldP : oldProxies)
				oldP.getParentNode().removeChild(oldP);
			
			audiovideoVersion.normalize();
			
			
			// aggiungi i nuovi proxies
			Node nodeAfter = UtilXML.searchInResource(audiovideoVersion, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature").get(0);
			
			boolean lastChild = nodeAfter.getNextSibling() == null;
			
			for(Node proxyNode : proxiesUsage) {
				if(lastChild)
					audiovideoVersion.appendChild(proxyNode);
				
				else {
					audiovideoVersion.insertBefore(proxyNode, nodeAfter.getNextSibling());
					nodeAfter = proxyNode;
				}
			}
			
			audiovideoVersion.normalize();
			
			// aggiunta finale nel documento
			magMetadigitVersion.appendChild(docVersion.importNode(audiovideoVersion, true));
		}
	}
	
	/**
	 * Esegue la scansione della directory di progetto
	 * 
	 * @param file directory di progetto
	 * @return List<String> lista dei path dei documenti MAG
	 */
	public static ProjectSummary scanDir(VfsFile file) {
		return scanDir(file.getFile());
	}

	public static ProjectSummary scanDir(File file) {
		ProjectSummary summary = new ProjectSummary();
		
		try {
			UtilXML.scanDir(file, summary);
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return summary;
	}
	
	/**
	 * Esegue una scansione ricorsiva del directory, controllando la presenza di file XML e verificando
	 * la presenza di MAG
	 * 
	 * @param file file da verificare
	 * @param projSummary sommario dei path di progetto (MAG e oggett digitali)
	 * @param saxParser parser per rilevare i MAG
	 * @param magVerifier handler di lettura e verifica XML
	 * @throws SAXException
	 * @throws IOException
	 */
	private static void scanDir(File file, ProjectSummary projSummary) throws IOException {
		if (file.exists() && file.isDirectory()) {
			for (File child : file.listFiles()) {
				if (child.isDirectory())
					scanDir(child, projSummary);
				
				else {
					if(child.getName().endsWith("xml") && !projSummary.getMags().contains(child.getPath())) {
						if(Utility.isMag(child) || Utility.isMets(child))
							projSummary.getMags().add(child.getPath());
						
						else
							projSummary.getDigitalObjects().add(child.getPath());
					}
					
					else
						projSummary.getDigitalObjects().add(child.getPath());
				}
			}
		}
	}
	
	/**
	 * Il metodo trasforma un Document DOM in testo
	 * 
	 * @parama Document xml di input
	 * @return String xml in formato stringa
	 * */
	public static String convertDocumentToString(Document doc) {
	    try {
	    	// normalizza spazi vuoti
	    	/*
	    	XPath xPath = XPathFactory.newInstance().newXPath();
		    NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']", doc, XPathConstants.NODESET);
		 
		    for (int i = 0; i < nodeList.getLength(); ++i) {
		        Node node = nodeList.item(i);
		        node.getParentNode().removeChild(node);
		    }
		    */
		    // trasforma in stringa
	    	StreamResult result = new StreamResult(new StringWriter());
	    	TransformerFactory factory = TransformerFactory.newInstance();
	    	// factory.setAttribute("indent-number", 4);
	    	
	    	Transformer transformer = factory.newTransformer();
	    	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	    	transformer.transform(new DOMSource(doc), result);
	    	// xmlStringTransformer.transform(new DOMSource(doc), result);
	    	
	    	return result.getWriter().toString().replaceAll("[\n]+", "\n");
	    	
	    } catch(TransformerException e){
	        logger.error(e.getMessage(), e);
	        return "";
	    } /*catch (XPathExpressionException e) {
	    	logger.error(e.getMessage(), e);
		    return "";
		}*/
	}

	/**
	 * Il metodo trasforma un Document DOM in testo
	 * 
	 * @parama Document xml di input
	 * @return byte[] xml in formato stringa
	 * */
	public static byte[] convertDocumentToByteArray(Document doc) {
	    return convertDocumentToString(doc).getBytes();
	}
	
	/**
	 * Aggiunge nodi memorizzati nella lista al documento 
	 * 
	 * @param Document xml a cui aggiungere i nodi
	 * @param ArrayList<Node> lista di nodi da aggiungere
	 * */
	public static void addNodeToFile(Document doc, ArrayList<Node> listNode){
		Node node;
		Node metadigitNode=doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
		
		for (int i=0;i<listNode.size();i++){
			node=listNode.get(i);
			metadigitNode.appendChild(node);
		}
	}
	
	/**
	 * Cancella tutti i tag aventi un certo nome dal mag originale
	 * 
	 * @param Document mag in formato xml
	 * @param String tipo di nodo da cancellare
	 * */
	public static void clearDocument(Document doc, String fileType, String namespace) {
		NodeList nodes = doc.getElementsByTagNameNS(namespace, fileType);
		
		if (nodes != null && nodes.getLength() > 0){
			for (int i = nodes.getLength() - 1; i >= 0; i--) {
			    Element e = (Element) nodes.item(i);
			    Node parent = e.getParentNode();
			    parent.removeChild(e);
			    parent.normalize();
			}
		}
	}
	
	/**
	 * Esegue la cancellazione delle risorse digitali da file system, con eventuale backup
	 * in una cartella apposita
	 * 
	 * @param doc documento MAG
	 * @param idMag ID del MAG di cui cancellare le risorse
	 * @param backup true se eseguire il backup, false per eseguire soltanto la cancellazione
	 */
	public static void deleteFileFromFS(Document doc, String idMag, boolean backup) {	
		// ricerca i tag file
	    NodeList fileNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
	    String sep = System.getProperty("file.separator");
	    String resourceRootDir = ContentStatic.getProperties().getProperty("resourceDIR");
	    String backupRootDir = ContentStatic.getProperties().getProperty("pathMagCancellati");
	    
	    for (int i = 0; i < fileNodeList.getLength(); i++) {
    		// pathRel = Utility.createPathFileOutput(backupRootDir);
    		Element fileNode = (Element) fileNodeList.item(i);
    		String relPath = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href");
    		File resourceFile = new File(resourceRootDir, relPath);
    		
    		// se la risosa digitale esiste
    		if (resourceFile.exists()) {	
    			// in caso di backup esegui copia prima di cancellare
	    		if(backup) {
		    		String backupResourceRelPath = idMag + sep + relPath;
		    		File backupResourceFile = new File(backupRootDir, backupResourceRelPath);
		    		Utility.createDir(backupResourceFile.getParent());
		    		Utility.copyFileFS(resourceFile.getPath(), backupResourceFile.getPath(), true);
	    		}
	    		
	    		// cancella risorse digitali
	    		Utility.deleteFile(resourceFile.getPath());
    		}
    	}
	}
	
	/**
	 * Converte una stringa xml in un oggetto di tipo Document
	 * @param String stringa sotto forma di xml
	 * @return Document oggetto xml
	 * */
	public static Document convertStringToDocumentXML(String xml){
		Document doc =null;
		
		try {
			DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder db=dbFactory.newDocumentBuilder();
			Reader reader = new StringReader(xml);
			InputSource is = new InputSource(reader);
			doc = db.parse(is);
			reader.close();
			
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage(), e);
		}		
		
		return doc;
	}
	
	/**
	 * Cancella un nodo dal mag
	 * 
	 *@param Node nodo da cancellare
	 *@return boolean true se il nodo è stato cancellato, false altrimenti 
	 */
	public static boolean removeNode(Node node){
		if (node!=null ){
			try {
				if (node.getParentNode()==null){
					System.out.println("null");
				}
				
				if (node.getParentNode().removeChild(node)==null){
					System.out.println("null");
				}	
				
				return true;
				
			} catch(DOMException e){				
				logger.error(e.getMessage());
				return false;
				//throw new DOMException(code, message)
			}
		}
		
		return false;
	}
	
	/**
	 * Apertura documento MAG
	 * 
	 * @param String percorso del mag su filesystem
	 * @param String indica il tipo di mag, possibili valori sono file, http, ftp, db
	 * @return Document documento DOM
	 * */
	public static Document openMag(String pathXml, String type) {
		Document doc=null;
		
		if (type.equals(MagPersistenceTypes.FILE))
		   doc=openXMLFS(pathXml);
		
		else if (type.equals(MagPersistenceTypes.HTTP)) {
			doc=openXMLHttp(pathXml);
		} 
		
		else if (type.equals(MagPersistenceTypes.FTP)) {
			doc=openXMLFTP(pathXml);
		} 
		
		else if (type.equals(MagPersistenceTypes.DB)) {
			doc=openXMLDB(pathXml);
		}	
		
		return doc;
	}
	

	/**
	 * Apertura documento MAG
	 * 
	 * @param stream InputStream di lettura
	 * @return Document documento DOM
	 * */
	public static Document openMag(InputStream stream) {
		/* Apro il file xml memorizzandolo nell'oggetto document */
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = null;
		Document doc = null;
		
		try {
			/* Apro il file xml memorizzandolo nell'oggetto document */
			docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(stream);
			stream.close();
		}
		// Alb: manca la gestione delle eccezioni!!! Qui e fuori (interrompere
		// esecuzione?)
		catch (ParserConfigurationException e) {
            logger.error("Errore: " + e.getMessage());
		} catch (IOException e) {
           logger.error("Errore: " + e.getMessage());
		} catch (SAXException e) {
			logger.error("Errore: " + e.getMessage());
		}

		return doc;
	}
	
	/**
	 * Restituisce il contenuto di un nodo, cercato attraverso una query xpath
	 * 
	 * @param Document xml del mag
	 * @String espressione xpath 
	 * @return String contenuto del nodo
	 * */
	public static String getContentNode(Document doc, String expression) {
		String testo = "";
		XPathFactory xPathFactory = XPathFactory.newInstance();
		// Create XPath object from XPathFactory
		XPath xpath = xPathFactory.newXPath();
		
		try {
			javax.xml.xpath.XPathExpression expr = xpath.compile(expression);
			testo = (String) expr.evaluate(doc, XPathConstants.STRING);	
			
		} catch (XPathExpressionException e) {
			logger.error(expression + " " + e.getMessage());
			//mag.getReport().getListErrors().add(expression + " " + e.getMessage());
			return "";
		}
		
		return testo;
	}

	/**
	 * Restituisce l'intero nodo, ottenuto attraverso una query xpath
	 * 
	 * @param Document xml di input
	 * @param String espressione xpath
	 * @return Node nodo recuperato usando xpath
	 * */
	public static Node getContentNodeFullNode(Document doc, String expression) {
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();
		
		try {
			javax.xml.xpath.XPathExpression expr = xpath.compile(expression);
			return (Node) expr.evaluate(doc, XPathConstants.NODE);		
			
		} catch (XPathExpressionException e) {
			logger.error(expression + " " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Restituisce una lista di nodi, risultati di una query xpath
	 * 
	 *@param Document xml di input
	 *@param String espressione xpath
	 *@return NodeList insieme di nodi ritornati 
	 * */
	public static NodeList getContentNodeListResource(Document doc, String expression) {
		//Document doc = UtilXML.openMag(mag.getPath(), mag.getTipo());
		XPathFactory xPathFactory = XPathFactory.newInstance();
		// Create XPath object from XPathFactory
		XPath xpath = xPathFactory.newXPath();
		//ok funziona l'indice indica quale elemento usage prende all'interno di tutto il file xml e non si riferisce al nodo img per poi ricavare gli usage interni		
		NodeList nodes=null;
		//expression="(//*[local-name()='img'][1]/*[local-name()='usage'])";
		
		try {
			javax.xml.xpath.XPathExpression expr = xpath.compile(expression);
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			return nodes;	
			
		} catch (XPathExpressionException e) {
			logger.error(expression + " " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Restituisce la lista dei contenuti dei nodi ottenuti da una query xpath
	 * @param Document xml di input
	 * @param String espressione xpath
	 * @return ArrayList<String> valori ricavati dall'espressione xpath
	 * */
	public static ArrayList<String> getContentNodeList(Document doc, String expression) {
		//Document doc = UtilXML.openMag(mag.getPath(), mag.getTipo());
		ArrayList<String> valuesNodes=new ArrayList<String>();
		XPathFactory xPathFactory = XPathFactory.newInstance();
		// Create XPath object from XPathFactory
		XPath xpath = xPathFactory.newXPath();
		//ok funziona l'indice indica quale elemento usage prende all'interno di tutto il file xml e non si riferisce al nodo img per poi ricavare gli usage interni		
		//expression="(//*[local-name()='img'][1]/*[local-name()='usage'])";
		int i=0;
		
		try {
			javax.xml.xpath.XPathExpression expr = xpath.compile(expression);
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			
			if (nodes!=null && nodes.getLength()>0){
				//logger.info(nodes.getLength());
				if (nodes.getLength()>0){
					valuesNodes=new ArrayList<String>();
					
					for (i=0;i<nodes.getLength();i++){					
	                    valuesNodes.add(nodes.item(i).getTextContent().trim());					
					}
				}
			}
			
			else{
				logger.info("Espressione "+expression+" non ha trovato nessun valore");
				
			}
			
		} catch (XPathExpressionException e) {
			System.out.println(e.getMessage());
			logger.error(expression + " " + e.getMessage());
		} 
		
		return valuesNodes;
	}
	
	/**
	 *  Restituisce il numero di occorrenze di un nodo con un certo nome
	 *  @param Document xml del mag in ingresso
	 *  @param String memorizza il tipo di nodo da elaborare valori possibili sono mag:img, mag:video, mag:audio, mag:doc, mag:ocr
	 *  @return int restituisce il numero di occorrenze trovate 
	 *  */
	public static int getNumberOccuranceNode(Document doc, String strNode, String namespace) {
		NodeList node = null;
		
		try {
		    node = doc.getElementsByTagNameNS(namespace, strNode);
		    
		} catch(NullPointerException e){
			logger.error(e.getMessage());
			return 0;
		}
		
		return node.getLength();
	}

	/**
	 * Apertura di un file da file system
	 * 
	 * @param String percorso del mag da aprire
	 * @return Document mag memorizzato sotto forma xml 
	 * */
	public static Document openXMLFS(String pathFileXML) {
		try {
			/* Apro il file xml memorizzandolo nell'oggetto document */
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Reader reader = new FileReader(pathFileXML);
			return docBuilder.parse(new InputSource(reader));
		}
		// Alb: manca la gestione delle eccezioni!!! Qui e fuori (interrompere
		// esecuzione?)
		catch (ParserConfigurationException e) {
            logger.error("Errore " + pathFileXML + ": " + e.getMessage());
		} catch (IOException e) {
           logger.error("Errore " + pathFileXML + ": " + e.getMessage());
		} catch (SAXException e) {
			logger.error("Errore " + pathFileXML + ": " + e.getMessage());
		}

		return null;
		// Alb: anche questo metodo o fuori di questo dovrebbe scrivere nel
		// report!!
	}
	
	private static Document openXMLFTP(String pathFileXML) {
		Document doc=null;
		return doc;
	}
	
	private static Document openXMLHttp(String pathFileXML) {
		Document doc=null;
		return doc;
	}
	
	private static Document openXMLDB(String pathFileXML) {
		Document doc=null;
		return doc;
	}
	
	/**
	 * Recupera tutte le risorse da un mag
	 * 
	 * @param Document xml in input
	 * @return ArrayList<NodeList> insieme dei nodi risorsse presente nei mag
	 * */
	public static ArrayList<NodeList> getAllResource(Document doc) {
		ArrayList<NodeList> result=new ArrayList<NodeList>();		
		NodeList listNode=UtilXML.getContentNodeListResource(doc,"(//*[local-name()='img'])");
		
		if (listNode != null){
		   result.add(listNode);
		}
		
		listNode=UtilXML.getContentNodeListResource(doc,"(//*[local-name()='audio'])");
		
		if (listNode != null){
		   result.add(listNode);
		}
		
		listNode=UtilXML.getContentNodeListResource(doc,"(//*[local-name()='video'])");
		
		if (listNode != null){
		   result.add(listNode);
		}
		listNode=UtilXML.getContentNodeListResource(doc,"(//*[local-name()='doc'])");
		
		if (listNode != null){
		   result.add(listNode);
		}
		
		listNode=UtilXML.getContentNodeListResource(doc,"(//*[local-name()='ocr'])");
		
		if (listNode != null){
		   result.add(listNode);
		}
		
		return result;
	}

    /**
     * Costruisce le diverse versioni del MAG (acquisizione, esportazione, completa)
     * 
     * @param docMag documento MAG di cui calcolare varie versioni
     * @param usageAcquisizione usage di acquisizione (separati da whitespace ' ')
     * @param usageEsportazione usage di esportazione (separati da whitespace ' ')
     * @return Map<String, Document> mappa contenente le versioni del MAG (acquisizione, esportazione, completa)
     */
	public static void createMagFile(Document docMag, List<String> usageA, List<String> usageE, Mag magObject) {
		// documento originale
		Document docOutputOriginal = (Document) docMag.cloneNode(true);
		
		// modifica file
		NodeList fileOriginalNodeList = docOutputOriginal.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
		
		for(int i = 0; i < fileOriginalNodeList.getLength(); i++) {
			Element fileNode = (Element) fileOriginalNodeList.item(i);
			Attr href = fileNode.getAttributeNodeNS("http://www.w3.org/TR/xlink", "href");
			
			if(href != null && !href.getValue().endsWith("/original") && !href.getValue().isEmpty())
				href.setValue(href.getValue() + "/original");
		}
		
		// modifica source (OCR)
		NodeList sourceOriginalNodeList = docOutputOriginal.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "source");
		
		for(int i = 0; i < sourceOriginalNodeList.getLength(); i++) {
			Element sourceNode = (Element) sourceOriginalNodeList.item(i);
			Attr href = sourceNode.getAttributeNodeNS("http://www.w3.org/TR/xlink", "href");
			
			if(href != null && href.getValue().startsWith("digitalObject/") && !href.getValue().endsWith("/original") && !href.getValue().isEmpty())
				href.setValue(href.getValue() + "/original");
		}
		
		// documento uso interno
		Document docOutputInternal = (Document) docMag.cloneNode(true);
		UtilXML.clearDocument(docOutputInternal, "img", "http://www.iccu.sbn.it/metaAG1.pdf");
		UtilXML.clearDocument(docOutputInternal, "audio", "http://www.iccu.sbn.it/metaAG1.pdf");
		UtilXML.clearDocument(docOutputInternal, "video", "http://www.iccu.sbn.it/metaAG1.pdf");
		UtilXML.clearDocument(docOutputInternal, "ocr", "http://www.iccu.sbn.it/metaAG1.pdf");
		UtilXML.clearDocument(docOutputInternal, "doc", "http://www.iccu.sbn.it/metaAG1.pdf");
		
		// documento uso esterno
		Document docOutputExternal = (Document) docMag.cloneNode(true);
		UtilXML.clearDocument(docOutputExternal, "img", "http://www.iccu.sbn.it/metaAG1.pdf");
		UtilXML.clearDocument(docOutputExternal, "audio", "http://www.iccu.sbn.it/metaAG1.pdf");
		UtilXML.clearDocument(docOutputExternal, "video", "http://www.iccu.sbn.it/metaAG1.pdf");
		UtilXML.clearDocument(docOutputExternal, "ocr", "http://www.iccu.sbn.it/metaAG1.pdf");
		UtilXML.clearDocument(docOutputExternal, "doc", "http://www.iccu.sbn.it/metaAG1.pdf");
		
		Node magmetadigitdocMag = docMag.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
		Node magmetadigitInternal = docOutputInternal.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
		Node magmetadigitExternal = docOutputExternal.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
		NodeList metadigitChildren = magmetadigitdocMag.getChildNodes();
		List<String> resourceTypes = Arrays.asList("img", "audio", "video", "doc", "ocr");
		Map<String, String> firstDigitalObjects = new HashMap<String, String>();
		
		for(int i = 0; i < metadigitChildren.getLength(); i++) {
			Node nodeResource = metadigitChildren.item(i);
			
			if(nodeResource.getNodeType() == Document.ELEMENT_NODE) {
				String typeRes = nodeResource.getLocalName();
				
				if(resourceTypes.contains(typeRes)) {
					if("img".equals(typeRes)) {
						processImg(docMag, docOutputInternal, docOutputExternal, 
								magmetadigitInternal, magmetadigitExternal, 
								nodeResource, usageA, usageE, firstDigitalObjects);		
					}
					
					else if("audio".equals(typeRes) || "video".equals(typeRes)) {
						processAudioVideo(docMag, docOutputInternal, docOutputExternal, 
								magmetadigitInternal, magmetadigitExternal, 
								nodeResource, usageA, usageE, firstDigitalObjects);
					}
					
					else {
						List<String> usages = new ArrayList<String>();
						
						List<Element> usageNodeList = UtilXML.searchInResource(nodeResource, 
								"http://www.iccu.sbn.it/metaAG1.pdf", "usage");
						
						for(Element usageNode : usageNodeList)
							usages.add(usageNode.getTextContent().trim());		
						
						// assenza usages, default 3
						if(usages.isEmpty())
							usages.add("3");
						
						boolean internalOK = false;
						boolean externalOK = false;
						
						for(String usage : usages) {
							if (usageA.contains(usage))
								internalOK = true;
							
							if (usageE.contains(usage))
								externalOK = true;
							
							if(internalOK && externalOK)
								break;
						}
						
						if(internalOK) {
							magmetadigitInternal.appendChild(docOutputInternal.importNode(nodeResource, true));
							
							if(!firstDigitalObjects.containsKey(typeRes)) {
								NodeList childrenNodeList = nodeResource.getChildNodes();
								
								for(int j = 0; j < childrenNodeList.getLength(); j++) {
									Node childNode = childrenNodeList.item(j);
									
									if(childNode.getNodeType() == Document.ELEMENT_NODE) {
										Element childElem = (Element) childNode;
										
										if("file".equals(childElem.getLocalName())) {
											String deliveryID = childElem.getAttributeNS("http://www.w3.org/TR/xlink", "href").
													replaceAll("digitalObject/", "").replaceAll("/original", "");
											
											firstDigitalObjects.put(typeRes, deliveryID);
										}
									}
								}
							}
						}
						
						if(externalOK)
							magmetadigitExternal.appendChild(docOutputExternal.importNode(nodeResource, true));	
					}
				}
			}
		}
		
		List<String> priority = Arrays.asList("img", "video", "audio", "doc", "ocr");
		
		for(String resourceType : priority) {
			if(firstDigitalObjects.containsKey(resourceType)) {
				magObject.setMagPreview(firstDigitalObjects.get(resourceType));
				break;
			}
		}
		
		magObject.setMagOriginal(UtilXML.convertDocumentToString(docOutputOriginal));
		magObject.setMagInternal(UtilXML.convertDocumentToString(docOutputInternal));
		magObject.setMagExternal(UtilXML.convertDocumentToString(docOutputExternal));
		magObject.setMagTot(UtilXML.convertDocumentToString(docMag));
	}
	
	/**
	 * Esegue la normalizzazione rispetto ad un documento MAG, indipendente dalla versione
	 * 
	 * @param document documento MAG XML
	 * @param pathXml percorso XML del tag
	 * @param oldValue valore attuale, da sostituire
	 * @param newValue valore nuovo, da applicare
	 * @return Document documento modificato
	 */
	public static Document normalizeDocument(Document document, XPathExpression xPathExpression, String oldValue, String newValue, String fieldName) {
		if(fieldName.equals("type")){
			if(oldValue.equals("testo"))
				oldValue = "text";
			if(newValue.equals("immagine"))
				newValue = "image";
		}
		if(fieldName.equals("creator")||fieldName.equals("contributor")){
			if(oldValue.contains("["))
				oldValue = oldValue.substring(0, oldValue.indexOf("["));
			if(oldValue.contains("<"))
				oldValue = oldValue.substring(0, oldValue.indexOf("<"));
			if(newValue.contains("["))
				newValue = newValue.substring(0, newValue.indexOf("["));
			if(newValue.contains("<"))
				newValue = newValue.substring(0, newValue.indexOf("<"));
			newValue = newValue.trim();
			oldValue = oldValue.trim();
		}
		boolean changed = false;
		try {
			NodeList nodes = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
			for(int i = 0; i < nodes.getLength(); i++){
				Node node = nodes.item(i);
				if(node instanceof Element){
					if(((Element)node).getTextContent().equals(oldValue)) {
						((Element) node).setTextContent(newValue);
						changed = true;
					}
				}
			}
		}
		catch (Exception e){
			logger.error("", e);
		}
		if(changed)
			return document;
		else
			return null;
	}

	public static Document normalizeDocument(Document document, List<String> pathXml, String oldValue, String newValue) {
		Element parent = document.getDocumentElement();
		String element = pathXml.get(pathXml.size() - 1);
		
		NodeList elemNodes = parent.getElementsByTagNameNS(NormalizationUtility.getNamespaceTag(element), element);

		// assenza = inserimento
		if(elemNodes.getLength() == 0) {
			// ricostruzione percorso
			for(int i = 1; i < pathXml.size(); i++) {
				String tag = pathXml.get(i);
				NodeList tagNodes = parent.getElementsByTagNameNS(NormalizationUtility.getNamespaceTag(tag), tag);
				
				// se l'elemento già esiste vai in profondità
				if(tagNodes.getLength() > 0)
					parent = (Element) tagNodes.item(0);
				
				// altrimenti crea figlio
				else {
					// crea tag
					Element elem = null;
					
					if(document.isDefaultNamespace(NormalizationUtility.getNamespaceTag(tag)))
						elem = document.createElement(tag);
					
					else {
						String prefix = document.lookupPrefix(NormalizationUtility.getNamespaceTag(tag));
						
						elem = prefix == null || prefix.isEmpty() ? 
								document.createElement(tag) : document.createElement(prefix + ":" + tag);
					}
					
					if(i == pathXml.size() - 1)
						elem.setTextContent(newValue);
					
					// controlla ordine tags del parent
					List<String> childrenOrder = NormalizationUtility.getOrderContainer(parent.getLocalName());
					int idx = childrenOrder.indexOf(tag);
					
					// tag non trovato nell'ordine
					if(idx < 0)
						logger.error("Tag '" + tag + "' non registrato per le operazioni di normalizzazione");
					
					// inserisci nel parent nella giusta posizione
					else {
						Node nextElem = null;
						
						// ricerca next sibling
						for(int j = idx + 1; j < childrenOrder.size(); j++) {
							String nextTagName = childrenOrder.get(j);
							
							NodeList nextSiblingNodes = parent.getElementsByTagNameNS(NormalizationUtility.
									getNamespaceTag(nextTagName), nextTagName);
							
							if(nextSiblingNodes.getLength() > 0) {
								nextElem = nextSiblingNodes.item(0);
								break;
							}
						}
						
						// se presente inserisci prima del next sibling
						if(nextElem != null)
							parent.insertBefore(elem, nextElem);
						
						// altrimenti appendi in coda
						else
							parent.appendChild(elem);
						
						parent = elem;
					}
				}
			}
		}
		
		// presenza = aggiornamento
		else {
			List<Node> oldValuesElements = new ArrayList<Node>();
			boolean hasNewValue = false;
			
			// ricerca corrispondenza vecchio valore e nuovo valore
			for(int i = 0; i < elemNodes.getLength(); i++) {
				Node elem = elemNodes.item(i);
				
				if(oldValue.equals(elem.getTextContent()))
					oldValuesElements.add(elem);
				
				else if(newValue.equals(elem.getTextContent()))
					hasNewValue = true;
			}
			
			if(!oldValuesElements.isEmpty()) {
				for(Node oldValueElem : oldValuesElements) {
					if(hasNewValue)
						oldValueElem.getParentNode().removeChild(oldValueElem);
						
					else {
						oldValueElem.setTextContent(newValue);
						hasNewValue = true;
					}
				}
			}
		}
		
		return document;
	}

	/**
	 * Restituisce la lista degli elementi relativi ad un determinato all'interno del nodo della risorsa.
	 * In caso di risorsa di tipo, img, la lista degli elementi va ricercata tra i figli, a causa 
	 * dei possibili altimg presenti, che corrispondono a risorse "separate"
	 * 
	 * @param resource nodo risorsa
	 * @param namespace namespace XML del tag da ricercare
	 * @param tagName nome del Tag
	 * @return List<Element> la lista degli elementi corrispondenti al tag cercato per la risorsa
	 */
	public static List<Element> searchInResource(Node resource, String namespace, String tagName) {
		List<Element> elements = new ArrayList<Element>();
		
		NodeList children = resource.getChildNodes();
			
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE) {
				if(tagName.equals(child.getLocalName()))
					elements.add((Element) child);
			}
		}
		
		return elements;
	}

	/**
	 * Restituisce il nodo risorsa corrispondente a tipo, sequence_number e usage
	 * 
	 * @param document documento MAG
	 * @param resourceType tipo risorsa (img, audio, video, ocr, doc)
	 * @param sequenceNumber sequence_number
	 * @return l'elemento della risorsa (img, audio, video, ocr, doc) o null se 
	 * l'elemento descritto non è stato trovato
	 */
	public static Element getResourceNode(Document document, String resourceType, String sequenceNumber) {
		// ricerca per tipo di nodo
		NodeList resourceTypeNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
		
		for(int i = 0; i < resourceTypeNodeList.getLength(); i++) {
			Element resourceTypeNode = (Element) resourceTypeNodeList.item(i);
			
			NodeList sequenceNumberNodeList = resourceTypeNode.
					getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number");
			
			// confronto sequence number
			if(sequenceNumberNodeList.getLength() > 0) {
				if(sequenceNumber.equals(sequenceNumberNodeList.item(0).getTextContent()))
					return resourceTypeNode;
			}
		}
		
		return null;
	}
	
	/**
	 * Restituisce il nodo risorsa corrispondente a tipo, sequence_number e usage
	 * 
	 * @param document documento MAG
	 * @param resourceType tipo risorsa (img, audio, video, ocr, doc)
	 * @param sequenceNumber sequence_number
	 * @param usage usage
	 * @return l'elemento della risorsa (img, altimg, proxies, ocr, doc) o null se 
	 * l'elemento descritto non è stato trovato
	 */
	public static Element getResourceNode(Document document, String resourceType, 
			String sequenceNumber, String usage) {
		
		// ricerca per tipo di nodo
		NodeList resourceTypeNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
		
		for(int i = 0; i < resourceTypeNodeList.getLength(); i++) {
			Element resourceTypeNode = (Element) resourceTypeNodeList.item(i);
			
			NodeList sequenceNumberNodeList = resourceTypeNode.
					getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number");
			
			// confronto sequence number
			if(sequenceNumberNodeList.getLength() > 0) {
				if(sequenceNumber.equals(sequenceNumberNodeList.item(0).getTextContent())) {
					NodeList usageNodeList = resourceTypeNode.
							getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "usage");
					
					if(usageNodeList.getLength() == 0 && "3".equals(usage))
						return resourceTypeNode;
					 
					// confronto usage
					for(int j = 0; j < usageNodeList.getLength(); j++) {
						Node usageNode = usageNodeList.item(j);
						 
						if(usage.equals(usageNode.getTextContent()))
							return (Element) usageNode.getParentNode();
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Restituisce gli ID delle risorse digitali acquisite
	 * 
	 * @param document documento MAG
	 * @return ID risorse digitali acquisite
	 */
	public static List<String> getResourceIDs(Document document) {
		List<String> resourceIDs = new ArrayList<String>();
		NodeList fileNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
		
		for(int i = 0; i < fileNodeList.getLength(); i++) {
			Element fileNode = (Element) fileNodeList.item(i);
			Attr href = fileNode.getAttributeNodeNS("http://www.w3.org/TR/xlink", "href");
			
			if(!href.getValue().isEmpty())
				resourceIDs.add(href.getValue().replace("digitalObject/", "").replaceAll("/original", ""));
		}
		
		return resourceIDs;
	}

}
