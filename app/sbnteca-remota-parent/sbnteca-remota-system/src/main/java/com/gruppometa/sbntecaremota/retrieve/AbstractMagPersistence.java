package com.gruppometa.sbntecaremota.retrieve;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.gruppometa.sbntecaremota.restweb.AudioCutterComponent;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsService;
import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.objects.ExternalMagReference;
import com.gruppometa.sbntecaremota.objects.ImportSettings;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;

public abstract class AbstractMagPersistence implements MagPersistence {

	// lista tipi di risorse
	private static final List<String> groupTypes = Arrays.asList("img_group", "audio_group", "video_group");
	
	// lista tipi di risorse
	private static final List<String> resourceTypes = Arrays.asList("img", "audio", "video", "ocr", "doc");
	
	// builder
	protected static DocumentBuilder docBuilder = null;

	public boolean isRelativize() {
		return relativize;
	}

	public void setRelativize(boolean relativize) {
		this.relativize = relativize;
	}

	protected boolean relativize = true;



	static {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(true);
		
		try {
			docBuilder = docFactory.newDocumentBuilder();
			
		} catch (ParserConfigurationException e) {
			docBuilder = null;
		}
	}

	// logger
	private static Logger logger = LoggerFactory.getLogger(AbstractMagPersistence.class);

	private VfsFileSystem vfsFileSystem;
	public VfsFileSystem getVfsFileSystem(){
		return vfsFileSystem;
	}

	public void setVfsFileSystem(VfsFileSystem vfsFileSystem) {
		this.vfsFileSystem = vfsFileSystem;
	}

	@Override
	public Mag getMag(String project, String path, Document document, 
			List<ExternalMagReference> externalReferences,
			ImportSettings settings, int publicFlag, int dressFlag, 
			String documentFormat) throws Exception {
		
		Mag mag = new Mag();
		mag.setDocumentFormat(documentFormat);
		mag.setDraft(false);
		mag.setProject(project);
		mag.setIdJob(settings.getJobID());
		mag.getUsageA().addAll(settings.getUsageA());
		mag.getUsageE().addAll(settings.getUsageE());
		mag.setPath(path);
		
		// recupero le informazioni degli allegati associati al mag
		mag.setMagProject(UtilXML.convertDocumentToString(document));
		mag.setMagOriginal(null);
		mag.setMagInternal(null);
		mag.setMagExternal(null);
		mag.setMagTot(null);
		mag.setPublishFlag(publicFlag);
		readFieldFromMAG(mag, document);	
		updatePathResource(mag, document, externalReferences, dressFlag, path);
		return mag;
	}

	/**
	 * Aggiorna i percorsi delle risorse di un singolo mag, per i parametri da impostare nell'oggetto mag vedere il metodo di sopra
	 * Il metodo prima crea due copie del mag originale, aggiungendo solo i nodi gruppi, ne crea una copia per l'xml solo di importazione
	 * ed un xml per l'esportazione anche, dopo di che elimina tutte le risorse dai due xml, per chiamare infinie il metodo create mag
	 * il quale in base ad i usage delle risorse ed al flag pubblica decide la risorsa su quale xml deve essere scritta 
	 * 
	 * @param currentMag bean Mag
	 * @param doc documento MAG
	 * @param externalDocuments documenti riferiti
	 * @param dress flag di vestizione MAG
	 * @throws Exception 
	 * @throws DOMException 
	 */
	private void updatePathResource(Mag currentMag, Document doc, 
			List<ExternalMagReference> externalReferences, int dress, String pathOfMetsOrMag) throws Exception {

		Document docMag = (Document) doc.cloneNode(true);

		// vestizione mag
		if(dress == 1 && externalReferences != null && !externalReferences.isEmpty())
			dressMag(currentMag.getPath(), docMag, externalReferences);

		this.loadResources(currentMag, currentMag.getPath().substring(0, currentMag.getPath().
				lastIndexOf("/") + 1), currentMag.getUsageA(), currentMag.getUsageE(), docMag);
		
		// creazione versioni mag (interno, esterno, generale)
		UtilXML.createMagFile(docMag, currentMag.getUsageA(), 
				currentMag.getUsageE(), currentMag);
	}

	public void createMetsFile(Mag currentMag, String pathOfMetsOrMag) {
		if (!Mag.METS.equals(currentMag.getDocumentFormat())) {
			logger.info("NO mets file for " + currentMag.getIdMag());
			return;
		}
		try {
			Document docMets = docBuilder.parse(new File(pathOfMetsOrMag));
			currentMag.setMetsOriginal(UtilXML.convertDocumentToString(docMets));
			// modifica file
			NodeList fileOriginalNodeList = docMets.getElementsByTagNameNS(
					"http://www.loc.gov/METS/", "FLocat");

			for (int i = 0; i < fileOriginalNodeList.getLength(); i++) {
				Element fileNode = (Element) fileOriginalNodeList.item(i);
				String[] namespaces = new String[]{"http://www.w3.org/1999/xlink", "http://www.w3.org/TR/xlink"};
				for (String ns : namespaces) {
					Attr href = fileNode.getAttributeNodeNS(ns, "href");
					if (href != null && !href.getValue().isEmpty()) {
						href.setValue(mapToExternal(vfsFileSystem.getVfsService(), currentMag.getPath(), href.getValue()));
						break;
					}
				}
			}

			currentMag.setMetsExternal(UtilXML.convertDocumentToString(docMets));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private static String mapToExternal(VfsService vfsService, String path, String value) {
		logger.info("Check "+ path + " AND "+value);
		VfsFile vfsFile = vfsService.getResourceByVfsPathAndHref(path, value, false);
		if(vfsFile!=null)
			return "digitalObject/"+vfsFile.getId();
		return value;
	}


	/**
	 * Esegue la vestizione dei MAG
	 * 
	 * @param magPath path del mag da "vestire"
	 * @param magDocument documento da "vestire"
	 * @param otherMags oggetti Mag inclusi nel processo di importazione corrente
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public void dressMag(String magPath, Document magDocument,
						 List<ExternalMagReference> externalReferences) throws NumberFormatException, Exception {
		dressMag(magPath, magDocument, externalReferences, null);
	}
	public void dressMag(String magPath, Document magDocument,
			List<ExternalMagReference> externalReferences, Map<String, String> hrefMap) throws NumberFormatException, Exception {
		
		NodeList elementNodeList = magDocument.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "element");
		
		// perogni element della stru
		for(int i = 0; i < elementNodeList.getLength(); i++) {
			Element elementNode = (Element) elementNodeList.item(i);
			
			String resourceType = "img";
			NodeList resourceNode = elementNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "resource");
			
			if(resourceNode.getLength() > 0)
				resourceType = resourceNode.item(0).getTextContent();
			
			NodeList startNode = elementNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "start");
			NamedNodeMap attrList = startNode.item(0).getAttributes();
			String start = "";
			
			for (int j = 0; j < attrList.getLength(); j++) {
				Node node = attrList.item(j);
				
			    if ("sequence_number".equals(node.getLocalName()))
					start = node.getTextContent();
			}
			
			String stop = start;
			NodeList stopNode = elementNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "stop");

			if(stopNode.getLength() > 0) {
				attrList = stopNode.item(0).getAttributes();
				
				for (int j = 0; j < attrList.getLength(); j++) {
					Node node = attrList.item(j);
					
				    if ("sequence_number".equals(node.getLocalName()))
						stop = node.getTextContent();
				}
			}

			// ricerca documento esterno
			ExternalMagReference reference = this.getExternalDocument(magPath, elementNode,
					resourceType, start, stop, externalReferences);
			
			// se c'è
			if(reference != null && reference.getExternalDocument() != null) {
				Map<String, List<String>> groupNames = new HashMap<String, List<String>>();
				Map<String, List<Element>> groups = new HashMap<String, List<Element>>();
				Map<String, List<Element>> resources = new HashMap<String, List<Element>>();
				
				// recupera le risorse e i gruppi già appartenenti al MAG da validare
				this.recoverOwnResources(magDocument, groupNames, groups, resources);
				
				// aggiungi le risorse dall'esterno
				this.addExternalResources(magPath, magDocument, reference, groupNames, 
						groups, resources, resourceType, Integer.parseInt(start), 
						Integer.parseInt(stop), hrefMap);
				
				// aggiorna il documento con risorse aggiuntive dall'esterno
				this.dressUpdateMag(magDocument, groups, resources);
			}
		}
	}
	
	/**
	 * Restituisce il documento esterno
	 * 
	 * @param path path del MAG da vestire
	 * @param elementNode node element
	 * @param resourceType tipo risorsa digitale (img, audio, video, ocr, doc)
	 * @param start sequence_number iniziale intervallo
	 * @param stop sequence_number finale intervallo
	 * @param externalReferences riferimenti esterni
	 * @return Documento esterno
	 */
	private ExternalMagReference getExternalDocument(String path, Element elementNode, 
			String resourceType, String start, String stop, 
			List<ExternalMagReference> externalReferences) {
		
		NodeList fileNode = elementNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
		
		if(fileNode.getLength() > 0) {
			Element file = (Element) fileNode.item(0);
			File externalFile = new File(new File(path).getParent(), file.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
			file.getParentNode().removeChild(file);
			ExternalMagReference reference = new ExternalMagReference();
			reference.setExternalPath(externalFile.getPath());
			reference.setExternalDocument(this.openMag(externalFile.getPath()));
			reference.setResourceType(resourceType);
			reference.setStart(start);
			reference.setStop(stop);
			return reference;
		}
		
		NodeList identifierNode = elementNode.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", "identifier");
		
		if(identifierNode.getLength() == 0)
			return null;
		
		String identifier = identifierNode.item(0).getTextContent();
		
		NodeList issueNode = elementNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "issue");
		String issue = issueNode.getLength() > 0 ? issueNode.item(0).getTextContent() : null;
		
		NodeList yearNode = elementNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "year");
		String year = yearNode.getLength() > 0 ? yearNode.item(0).getTextContent() : null;
		
		for(ExternalMagReference externalRef : externalReferences) {
			if(identifier.equals(externalRef.getIdentifiers().get(0)) && 
					resourceType.equals(externalRef.getResourceType()) && 
					start.equals(externalRef.getStart()) &&
					stop.equals(externalRef.getStop()) &&
					(issue == null || (issue.equals(externalRef.getIssue()) && 
					year.equals(externalRef.getYear())))) {
				
				elementNode.removeChild(identifierNode.item(0));
				
				if(issueNode.getLength() > 0)
					elementNode.removeChild(issueNode.item(0).getParentNode());
				
				return externalRef;
			}
		}
		
		return null;
	}
	
	/**
	 * Recupera le risorse e i gruppi del MAG elaborato
	 * 
	 * @param document documento MAG XML
	 * @param groupNames mappa dei nomi dei gruppi
	 * @param groups mappa dei gruppi
	 * @param resources mappa delle risorse
	 */
	private void recoverOwnResources(Document document, 
			Map<String, List<String>> groupNames, 
			Map<String, List<Element>> groups,
			Map<String, List<Element>> resources) {
		
		for(String groupType : groupTypes) {
			groups.put(groupType, new ArrayList<Element>());
			groupNames.put(groupType, new ArrayList<String>());
			
			NodeList groupNodeList = document.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", groupType);
			
			for(int i = 0; i < groupNodeList.getLength(); i++) {
				Element groupNode = (Element) groupNodeList.item(i);
				String groupID = groupNode.getAttribute("ID");
				
				if(!groupNames.get(groupType).contains(groupID))
					groupNames.get(groupType).add(groupID);
				
				groups.get(groupType).add(groupNode);
				groupNode.getParentNode().removeChild(groupNode);
			}
		}
		
		for(String resourceType : resourceTypes) {
			resources.put(resourceType, new ArrayList<Element>());
			
			NodeList resourceNodeList = document.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
			
			for(int i = 0; i < resourceNodeList.getLength(); i++) {
				Element resourceNode = (Element) resourceNodeList.item(i);
				resources.get(resourceType).add((Element) resourceNodeList.item(i));
				resourceNode.getParentNode().removeChild(resourceNode);
			}
		}
	}
	
	/**
	 * Aggiunge le risorse e gli eventuali gruppi, esterni al MAG in elaborazione
	 * 
	 * @param path path documento MAG da vestire
	 * @param document documento MAG da vestire
	 * @param externalDocument documento MAG esterno
	 * @param groupNames nomi gruppi già elaborati
	 * @param groups gruppi estratti
	 * @param resources risorse estratte
	 * @param resourceType tipo di risorsa da cercare
	 * @param start estremo inferiore intervallo di ricerca sequence number nel documento MAG esterno
	 * @param stop estremo superiore intervallo di ricerca sequence number nel documento MAG esterno
	 * @throws IOException 
	 * @throws DOMException 
	 */
	private void addExternalResources(String path, Document document, 
			ExternalMagReference reference, 
			Map<String, List<String>> groupNames, 
			Map<String, List<Element>> groups,
			Map<String, List<Element>> resources, 
			String resourceType, int start, int stop, Map<String,String> hrefMap) throws Exception {
		
		Document externalDocument = reference.getExternalDocument();
		
		NodeList resourceNodeList = externalDocument.getElementsByTagNameNS(
				"http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
		
		// preleva gruppi da esterno
		for(String groupType : groupTypes) {
			NodeList groupNodeList = externalDocument.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", groupType);
			
			for(int i = 0; i < groupNodeList.getLength(); i++) {
				Element groupNode = (Element) groupNodeList.item(i);
				String groupID = groupNode.getAttribute("ID");
				
				if(!groupNames.get(groupType).contains(groupID)) {
					groupNames.get(groupType).add(groupID);
					
					groups.get(groupType).add((Element) document.
							importNode(groupNode, true));
				}
			}
		}
		
		File parent1 = new File(path).getParentFile();
		Path dirPath = Paths.get(parent1.getCanonicalPath());
		File parent2 = new File(reference.getExternalPath()).getParentFile();
		
		// preleva risorse da esterno
		for(int i = 0; i < resourceNodeList.getLength(); i++) {
			Element resourceNode = (Element) resourceNodeList.item(i);
			
			// lettura sequence number
			int seqNumber = Integer.parseInt(resourceNode.
					getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", 
							"sequence_number").item(0).getTextContent());
			
			if(seqNumber >= start && seqNumber <= stop) {
				if(!parent1.getCanonicalPath().equals(parent2.getCanonicalFile())) {
					NodeList fileNodeList = resourceNode.
							getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
					
					for(int j = 0; j < fileNodeList.getLength(); j++) {
						Element fileNode = (Element) fileNodeList.item(j);
						String href = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href");
						if(relativize) {
							File resourceFile = new File(parent2, fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
							Path resourcePath = Paths.get(resourceFile.getCanonicalPath());
							fileNode.setAttributeNS("http://www.w3.org/TR/xlink",
									document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") + ":href",
									dirPath.relativize(resourcePath).toString());
						}
						else{
							fileNode.setAttributeNS("http://www.w3.org/TR/xlink",
									document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") + ":href",
									makeHref(href, hrefMap));
						}
					}
				}
				
				resources.get(resourceType).add((Element) document.
						importNode(resourceNode, true));
			}
		}
	}

	private String makeHref(String href, Map<String, String> hrefMap) {
		if(hrefMap==null)
			return href;
		if(href!=null && href.startsWith("digitalObject/")) {
			if(!hrefMap.containsKey(href))
				hrefMap.put(href, href+"_"+System.currentTimeMillis());
			return hrefMap.get(href);
		}
		else
			return href;
	}

	/**
	 * Verifica la presenza di gruppi da prelevare dal MAG esterno
	 * 
	 * @param document documento da aggiornare
	 * @param groupNames ID gruppi estratti
	 * @param groups gruppi estratti
	 * @param resourceType tipo di risorsa in elaborazione
	 * @param resourceNode tag risorsa
	 * @param groupNodeList lista gruppi
	 */
	private void verifyExternalGroups(Document document, 
			Map<String, List<String>> groupNames, 
			Map<String, List<Element>> groups,
			String resourceType, Element resourceNode,
			NodeList groupNodeList) {
		
		if(resourceNode.hasAttribute(resourceType + "groupID")) {
			String groupID = resourceNode.getAttribute(resourceType + "groupID");
			
			if(groupNames.get(resourceType + "_group").contains(groupID)) {
				for(int i = 0; i < groupNodeList.getLength(); i++) {
					Element groupNode = (Element) groupNodeList.item(i);
					
					if(groupID.equals(groupNode.getAttribute("ID"))) {
						groups.get(resourceType + "_group").add((Element) document.
								importNode(groupNode, true));
						
						groupNames.get(resourceType + "_group").add(groupID);
					}
				}
			}
		}
	}
	
	/**
	 * Aggiorna il documento per la vestizione
	 * 
	 * @param document documento MAG da vestire
	 * @param groups gruppi da aggiungere
	 * @param resources risorse da aggiungere
	 */
	private void dressUpdateMag(Document document, 
			Map<String, List<Element>> groups,
			Map<String, List<Element>> resources) {
		
		Element genNode = (Element) document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen").item(0);
		
		for(Element imgGroup : groups.get("img_group"))
			genNode.appendChild(imgGroup);
		
		for(Element audioGroup : groups.get("audio_group"))
			genNode.appendChild(audioGroup);
		
		for(Element videoGroup : groups.get("video_group"))
			genNode.appendChild(videoGroup);
		
		genNode.normalize();
		
		
		
		Element metadigitNode = (Element) document.getElementsByTagNameNS(
				"http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
		
		for(Element img : resources.get("img"))
			metadigitNode.appendChild(img);
		
		for(Element audio : resources.get("audio"))
			metadigitNode.appendChild(audio);
		
		for(Element video : resources.get("video"))
			metadigitNode.appendChild(video);
		
		for(Element ocr : resources.get("ocr"))
			metadigitNode.appendChild(ocr);
		
		for(Element doc : resources.get("doc"))
			metadigitNode.appendChild(doc);
		
		metadigitNode.normalize();
	}

	/**
	 * Il metodo dato il mag in ingresso con il campo doc inizializzato con xml originale legge i parametri dal mag memorizzandoli
	 * sull'oggetto mag, il quale successivamente sarà memorizzato su solr
	 * 
	 * @param Mag mag con valorizzato il campo doc con l'xml originale
	 * @param Document doc documento MAG
	 * */
	private void readFieldFromMAG(Mag mag, Document doc) {
		Element genNode = (Element) doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen").item(0);
		
		if(genNode.hasAttribute("creation"))
			mag.setCreation(genNode.getAttribute("creation"));
		
		if(genNode.hasAttribute("last_update"))
			mag.setLastUpdate(genNode.getAttribute("last_update"));
		
		NodeList genChildren = genNode.getChildNodes();
		
		for(int i = 0; i < genChildren.getLength(); i++) {
			Node node = genChildren.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				
				if("collection".equals(elem.getLocalName()))
					mag.setCollection(elem.getTextContent());
				
				else if("agency".equals(elem.getLocalName()))
					mag.setAgency(elem.getTextContent());
				
				else if("stprog".equals(elem.getLocalName()))
					mag.setStprog(elem.getTextContent());
				
				else if("access_rights".equals(elem.getLocalName()))
					mag.setAccessRights(Integer.parseInt(elem.getTextContent()));
				
				else if("completeness".equals(elem.getLocalName()))
					mag.setCompleteness(Integer.parseInt(elem.getTextContent()));
			}
		}
		
		// campo tipo di MAG
		Element bibNode = (Element) doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "bib").item(0);
		mag.setLevel(bibNode.getAttribute("level"));
		NodeList bibChildren = bibNode.getChildNodes();
		
		for(int i = 0; i < bibChildren.getLength(); i++) {
			Node node = bibChildren.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				
				if("identifier".equals(elem.getLocalName()))
					mag.getIdentifiers().add(elem.getTextContent());
				
				else if("title".equals(elem.getLocalName()))
					mag.getTitles().add(elem.getTextContent());
				
				else if("contributor".equals(elem.getLocalName()))
					mag.getContributors().add(elem.getTextContent());
				
				else if("creator".equals(elem.getLocalName()))
					mag.getCreators().add(elem.getTextContent());
				
				else if("publisher".equals(elem.getLocalName()))
					mag.getPublishers().add(elem.getTextContent());
				
				else if("subject".equals(elem.getLocalName()))
					mag.getSubjects().add(elem.getTextContent());
				
				else if("description".equals(elem.getLocalName()))
					mag.getDescriptions().add(elem.getTextContent());
				
				else if("date".equals(elem.getLocalName()))
					mag.getDates().add(elem.getTextContent());
				
				else if("type".equals(elem.getLocalName()))
					mag.getTypes().add(elem.getTextContent());
				
				else if("format".equals(elem.getLocalName()))
					mag.getFormats().add(elem.getTextContent());
				
				else if("source".equals(elem.getLocalName()))
					mag.getSources().add(elem.getTextContent());
				
				else if("language".equals(elem.getLocalName()))
					mag.getLanguages().add(elem.getTextContent());
				
				else if("relation".equals(elem.getLocalName()))
					mag.getRelations().add(elem.getTextContent());
				
				else if("coverage".equals(elem.getLocalName()))
					mag.getCoverages().add(elem.getTextContent());
				
				else if("rights".equals(elem.getLocalName()))
					mag.getRights().add(elem.getTextContent());
				
				else if("local_bib".equals(elem.getLocalName())) {
					NodeList localBibChildren = elem.getChildNodes();
					
					for(int j = 0; j < localBibChildren.getLength(); j++) {
						Node localNode = localBibChildren.item(j);
						
						if(localNode.getNodeType() == Node.ELEMENT_NODE) {
							if("geo_coord".equals(localNode.getLocalName()))
								mag.getGeoCoord().add(localNode.getTextContent());
							
							else if("not_date".equals(localNode.getLocalName()))
								mag.getNotDate().add(localNode.getTextContent());
						}
					}
				}
				
				else if("piece".equals(elem.getLocalName())) {
					NodeList pieceChildren = elem.getChildNodes();
					
					for(int j = 0; j < pieceChildren.getLength(); j++) {
						Node childNode = pieceChildren.item(j);
						
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							if("issue".equals(childNode.getLocalName()))
								mag.setIssue(childNode.getTextContent());
							
							else if("year".equals(childNode.getLocalName()))
								mag.setYear(childNode.getTextContent());
							
							else if("stpiece_per".equals(childNode.getLocalName()))
								mag.setStpiecePer(childNode.getTextContent());
							
							else if("part_number".equals(childNode.getLocalName()))
								mag.setPartNumber(childNode.getTextContent());
							
							else if("part_name".equals(childNode.getLocalName()))
								mag.setPartName(childNode.getTextContent());
							
							else if("stpiece_vol".equals(childNode.getLocalName()))
								mag.setStpieceVol(childNode.getTextContent());
						}
					}
				}

				else if("holdings".equals(elem.getLocalName())) {
					NodeList holdingChildren = elem.getChildNodes();
					
					for(int j = 0; j < holdingChildren.getLength(); j++) {
						Node childNode = holdingChildren.item(j);
						
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							if("library".equals(childNode.getLocalName()))
								mag.getLibraries().add(childNode.getTextContent());
							
							else if("inventory_number".equals(childNode.getLocalName()))
								mag.getInventoryNumbers().add(childNode.getTextContent());
							
							else if("shelfmark".equals(childNode.getLocalName()))
								mag.getShelfmarks().add(childNode.getTextContent());
						}
					}
				}
			}
		}
		
		NodeList nomenclatureNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature");
		
		for(int i = 0; i < nomenclatureNodeList.getLength(); i++) {
			Node nomenclatureNode = nomenclatureNodeList.item(i);
			
			if("stru".equals(nomenclatureNode.getParentNode().getLocalName())) {
				String nomenclature = nomenclatureNode.getTextContent();
				
				if(!mag.getStruNomenclatures().contains(nomenclature))
					mag.getStruNomenclatures().add(nomenclature);
			}
			
			else if("element".equals(nomenclatureNode.getParentNode().getLocalName())) {
				String nomenclature = nomenclatureNode.getTextContent();
				
				if(!mag.getElementNomenclatures().contains(nomenclature))
					mag.getElementNomenclatures().add(nomenclature);
			}
		}
	}
	
	/**
	 * Carica le informazioni sulle risorse
	 * 
	 * @param mag oggetto MAG
	 * @param projectPath path del progetto
	 * @param usageAcq usage acquisizione
	 * @param usageExp usage esportazione
	 * @param document documento MAG
	 * @throws DOMException
	 * @throws Exception
	 */
	private void loadResources(Mag mag, String projectPath, List<String> usageAcq, 
			List<String> usageExp, Document document) throws DOMException, Exception {	
		
		List<DeliveryResource> imgResources = processDocumentResources(mag, "img", 
				projectPath, usageAcq, usageExp, document);
		
		mag.setNumberImg(imgResources.size());
		mag.getDigitalResources().addAll(imgResources);

		/**
		 * TODO preview
		 */
//		List<DeliveryResource> imgResourcesPreview = processDocumentResources(mag, "dis_item",
//				projectPath, usageAcq, usageExp, document);
//		mag.setNumberImg(imgResources.size()+ imgResourcesPreview.size());
//		mag.getDigitalResources().addAll(imgResourcesPreview);


		List<DeliveryResource> audioResources = processDocumentResources(mag, "audio", 
				projectPath, usageAcq, usageExp, document);
		
		mag.setNumberAudio(audioResources.size());
		mag.getDigitalResources().addAll(audioResources);
		
		
		
		List<DeliveryResource> videoResources = processDocumentResources(mag, "video", 
				projectPath, usageAcq, usageExp, document);
		
		mag.setNumberVideo(videoResources.size());
		mag.getDigitalResources().addAll(videoResources);
		
		
		
		List<DeliveryResource> ocrResources = processDocumentResources(mag, "ocr", 
				projectPath, usageAcq, usageExp, document);
		
		mag.setNumberOcr(ocrResources.size());
		mag.getDigitalResources().addAll(ocrResources);
		
		
		
		List<DeliveryResource> docResources = processDocumentResources(mag, "doc", 
				projectPath, usageAcq, usageExp, document);
		
		mag.setNumberDoc(docResources.size());
		mag.getDigitalResources().addAll(docResources);
		
		mag.getDigitalResources().addAll(processDisItems(mag.getPath(), document));
		
		// statistica finale risorse valide e risorse totali
		int totalResources = mag.getNumberAudio() + mag.getNumberDoc() + 
				mag.getNumberImg() + mag.getNumberOcr() + mag.getNumberVideo();
		
		logger.info("Estratte " + totalResources + " risorse digitali");
	}
	
	/**
	 * Elabora le risorse da valide da filtrare in base al tipo di risorsa e 
	 * agli usage di acquisizione
	 * 
	 * @param mag oggetto MAG
	 * @param filetype tipo di risorsa digitlae
	 * @param projectPath path del progetto da importare
	 * @param usageAcq lista usage di acquisizione
	 * @param usageExp lista usage di esportazione
	 * @param doc documento MAG XML
	 * @return List<MagResource> Risorse digitali valide
	 * @throws Exception 
	 * @throws DOMException 
	 */
    private List<DeliveryResource> processDocumentResources(Mag mag, String filetype, String projectPath, 
    		List<String> usageAcq, List<String> usageExp, Document doc) throws Exception {
    	
    	List<DeliveryResource> resources = new ArrayList<DeliveryResource>();
    	String magName = new File(mag.getPath()).getName();
    	int end = magName.indexOf(".");
    	magName = end <= 0 ? magName : magName.substring(0, end);
    	magName = magName.replaceAll("[ \n\r\t]+", "_");
    	
    	// prendi tutti i gruppi se disponibili
    	List<Element> groups = new ArrayList<Element>();
    	
    	if(Arrays.asList("img", "audio", "video").contains(filetype)) {
    		NodeList groupNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", filetype + "_group");
    		
    		for(int i = 0; i < groupNodeList.getLength(); i++)
    			groups.add((Element) groupNodeList.item(i));
    	}
    	
    	// per ogni nodo risorsa
    	NodeList resourceNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", filetype);
    	List<Element> resourceList = new ArrayList<Element>();
    	
    	for(int i = 0; i < resourceNodeList.getLength(); i++) {
    		if("audio".equals(filetype) || "video".equals(filetype)) {
    			NodeList proxiesNodeList = ((Element) resourceNodeList.item(i)).
    					getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
    			
    			for(int j = 0; j < proxiesNodeList.getLength(); j++)
    				resourceList.add((Element) proxiesNodeList.item(j));
    		}
    		
    		else {
    			Element resourceNode = (Element) resourceNodeList.item(i);
    			resourceList.add(resourceNode);
    			
    			if("img".equals(filetype)) {
        			NodeList altimgNodeList = resourceNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
        			
        			for(int j = 0; j < altimgNodeList.getLength(); j++)
        				resourceList.add((Element) altimgNodeList.item(j));
    			}
    		}
    	}
	    
		for (int i = 0; i < resourceList.size(); i++) {
			DeliveryResource res = new DeliveryResource();
			res.setType(filetype);
			res.setProject(mag.getProject());
			// ricerca usage risorsa
			Element resourceNode = resourceList.get(i);
			List<Element> usageNodeList = UtilXML.searchInResource(resourceNode, "http://www.iccu.sbn.it/metaAG1.pdf", "usage");
			
			for(Element usageNode : usageNodeList)
				res.getUsage().add(usageNode.getTextContent().trim());
			
			// assenza usage, default 3
			if(res.getUsage().isEmpty())
				res.getUsage().add("3");
			
			// verifica che la risorsa abbia lo usage
			boolean resHasUsageAcq = false;
			
			for(String usage : res.getUsage()) {
				if(usageAcq.contains(usage))
					resHasUsageAcq = true;
			}
			/**
			 * non c'era prima...
			 */
			if(!resHasUsageAcq)
				continue;
			Element fileNode = UtilXML.searchInResource(resourceNode, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "file").get(0);

			List<Element> nomenclatureNode = UtilXML.searchInResource(resourceNode,
					"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature");
			if(nomenclatureNode.size()>0) {
				res.setLabel(nomenclatureNode.get(0).getTextContent());
			}

			Attr href = fileNode.getAttributeNodeNS("http://www.w3.org/TR/xlink", "href");
			VfsFile originalFile =
					vfsFileSystem.getVfsService().getResourceByVfsPathAndHref(mag.getPath(), href.getValue(), true);
			if(!res.getUsage().contains(originalFile.getUsage())){
				originalFile.setUsage(res.getUsage().get(0));
				vfsFileSystem.getVfsService().save(originalFile);
				logger.debug("Save usage...");
			}
					// new File(projectPath, href.getValue());
			setResourceProperties(mag.getPath(), mag.getMagProject(), projectPath, res, href, originalFile);
			href.setValue(StringUtils.isEmpty(res.getDeliveryID()) ? "" : "digitalObject/" + res.getDeliveryID() + "/original");
			
			if(href.getValue().isEmpty() && resHasUsageAcq)
				throw new Exception("File '" + originalFile.getPath() + "' non presente nel delivery");
			
			if(!res.getDeliveryID().isEmpty()) {
				// se usage presente tra gli usage di acquisizione aggiorna percorso relativo
				for(String usageResource : res.getUsage()) {
					if(usageAcq.contains(usageResource)) {
						this.searchDigitalType(mag, resourceNode, filetype, groups);
						href.setValue("digitalObject/" + res.getDeliveryID());
						resources.add(res);
						ckeckCutAudioInsert(resources, res, usageExp, filetype, originalFile);
						break;
					}
				}
			}
			
			if("ocr".equals(filetype)) {
				Element sourceNode = UtilXML.searchInResource(resourceNode, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "source").get(0);
				
				href = sourceNode.getAttributeNodeNS("http://www.w3.org/TR/xlink", "href");
				originalFile = vfsFileSystem.getVfsService().getResourceByVfsPathAndHref(mag.getPath(), href.getValue(), true);
				String sourceDeliveryID = Utility.getResourceDeliveryID(originalFile.getFile());
				
				if(!sourceDeliveryID.isEmpty())
					href.setValue("digitalObject/" + sourceDeliveryID + "/original");
			}
		}
		
		return resources;
    }

	private void ckeckCutAudioInsert(List<DeliveryResource> resources, DeliveryResource res, List<String> usageExp, String fileType, VfsFile vfsFile){
		if("audio".equals(fileType) && res.getUsage().contains("3") && usageExp.contains("5")){
			logger.info("Add cut resource");
			DeliveryResource deliveryResource = new DeliveryResource();
			VfsFile vfsFileCopy = vfsFileSystem.getVfsService().makeCutAudio(vfsFile);
			setResourceProperties(res, vfsFileCopy, deliveryResource );
			resources.add(deliveryResource);
		}
	}
	private void setResourceProperties(DeliveryResource source, VfsFile vfsFile, DeliveryResource destination) {
		destination.setHref(source.getVfsFilename());
		destination.setVfsContainer(source.getVfsContainer());
		destination.setType(source.getType());
		destination.setVfsDirectory(source.getVfsDirectory());
		destination.setDeliveryID(source.getDeliveryID()+ AudioCutterComponent.SUFFIX);
		destination.setHref(vfsFile.getPath());
		destination.setProject(source.getProject());
	}
	private void setResourceProperties(String magPath, String magProject, String projectPath, DeliveryResource res, Attr href, VfsFile originalFile) {
		res.setVfsFilename(href.getValue());
		if(originalFile.getContainer()!=null && originalFile.getContainer().size()>0) {
			logger.debug("Resource has container: "+originalFile.getContainer().stream().collect(Collectors.joining(",")));
			res.setVfsContainer(originalFile.getContainer().get(originalFile.getContainer().size() - 1));
		}
		else
			res.setVfsContainer(magPath);
		res.setVfsType("object");
		res.setVfsDirectory(magProject);

		/**
		 * i file del VFS sono già con id giusti
		 */
		res.setDeliveryID(
				//originalFile.isTakeFromOriginalPath()?
				//Utility.getResourceDeliveryID(originalFile.getFile()):
				originalFile.getId());
		res.setHref(originalFile.getPath());
		res.setProject(new File(projectPath).getName());
	}

	/**
     * Creazione path per la sezione DIS
     * 
     * @param projectPath root diretory del progetto
     * @param doc documento XML
     * @return List<DeliveryResource> lista dis item per il delivery
     * @throws Exception
     */
    private List<DeliveryResource> processDisItems(String projectPath, Document doc) throws Exception {
    	List<DeliveryResource> disItemResources = new ArrayList<DeliveryResource>();
    	NodeList disItemNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "dis_item");
    	
    	for(int i = 0; i < disItemNodeList.getLength(); i++) {
    		Element disItemNode = (Element) disItemNodeList.item(i);
    		NodeList fileNodeList = disItemNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
    		
    		for(int j = 0; j < fileNodeList.getLength(); j++) {
    			DeliveryResource res = new DeliveryResource();
    			res.setType("dis");
    			Element fileNode = (Element) fileNodeList.item(j);
    			Attr href = fileNode.getAttributeNodeNS("http://www.w3.org/TR/xlink", "href");
				if(!StringUtils.isEmpty(href.getValue())) {
					VfsFile originalFile =
							vfsFileSystem.getVfsService().getResourceByVfsPathAndHref(projectPath, href.getValue(), true);
					if (originalFile == null)
						logger.error("VfsFile not found " + href.getValue());
					//File originalFile = new File(projectPath, href.getValue());
					//res.setDeliveryID(Utility.getResourceDeliveryID(originalFile));
//				res.setDeliveryID(originalFile.getId());
//    			res.setHref(originalFile.getPath());
//    			res.setProject(new File(projectPath).getName());
					setResourceProperties(projectPath, projectPath, projectPath, res, href, originalFile);
					href.setValue(res.getDeliveryID().isEmpty() ? "" : "digitalObject/" + res.getDeliveryID() + originalFile.getPreview());
				}
				else{
					logger.info("Vfs href void ");
				}
    		}
    	}
    	
    	return disItemResources;
    }
	
	/**
	 * Ricerca il formato di appartenenza per la risorsa digitale
	 * 
	 * @param mag oggetto digitale
	 * @param resourceType tipo di risorsa
	 * @param resourceNode nodo risorsa
	 * @param groups lista dei gruppi
	 */
	private void searchDigitalType(Mag mag, Element resourceNode, String resourceType, List<Element> groups) {
		List<Element> formatNodeList = UtilXML.searchInResource(resourceNode, "http://www.iccu.sbn.it/metaAG1.pdf", "format");
		
		// se non c'è cerca all'interno dei gruppi se ci sono
		if(formatNodeList.isEmpty()) {
			if(resourceNode.hasAttribute(resourceType + "groupID")) {
				String groupID = resourceNode.getAttribute(resourceType + "groupID");
				
				for(Element group : groups) {
					if(group.hasAttribute("ID") && groupID.equals(group.getAttribute("ID"))) {
						NodeList formatList = group.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "format");
						
						for(int i = 0; i < formatList.getLength(); i++)
							formatNodeList.add((Element) formatList.item(i));
					}
				}
			}
		}
		
		// lettura dei nodi formato
		for(Element format : formatNodeList) {
			NodeList extNode = format.getElementsByTagNameNS("http://www.niso.org/pdfs/DataDict.pdf", "name");
			
			extNode = extNode.getLength() > 0 ? extNode : 
					format.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "name");
			
			String ext = extNode.getLength() > 0 ? extNode.item(0).getTextContent() : "";
			
			if(!ext.isEmpty() && !mag.getDigitalTypes().contains(ext))
				mag.getDigitalTypes().add(ext);
			
			
			NodeList mimeNode = format.getElementsByTagNameNS("http://www.niso.org/pdfs/DataDict.pdf", "mime");
			
			mimeNode = mimeNode.getLength() > 0 ? mimeNode : 
				format.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "mime");
			
			String mime = mimeNode.getLength() > 0 ? mimeNode.item(0).getTextContent() : "";
			
			if(!mime.isEmpty() && !mag.getMimeTypes().contains(mime))
				mag.getMimeTypes().add(mime);
		}
	}

}
