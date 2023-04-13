package com.gruppometa.sbntecaremota.restweb.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.gruppometa.data.mets.Mets;
import com.gruppometa.data.mods.Mods;
import com.gruppometa.data.mods.ModsCollection;
import com.gruppometa.mets2mag.MetsConvertor;
import com.gruppometa.sbntecaremota.mets.MetsCreator;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsService;
import com.mysql.cj.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.SbnConfiguration;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcess;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcessDao;
import com.gruppometa.sbntecaremota.objects.db.DBTecaUser;
import com.gruppometa.sbntecaremota.objects.db.DBTecaUserDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.objects.json.JsonEditorSaveResponse;
import com.gruppometa.sbntecaremota.objects.json.JsonEditorValidateResponse;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorBib;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorContent;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorDis;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorGen;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorResource;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorUsageResource;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorStru;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.EditorUtility;
import com.gruppometa.sbntecaremota.util.NormalizationUtility;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbnmarc.mag.MagTransformer;
import com.gruppometa.sbnmarc.mag.SbnMarcClient;
import com.gruppometa.sbnmarc.mag.UnimarcClient;

public class MagEditorService {
	
	@Autowired
	private MagResourceDelivery delivery;

	@Autowired
	private DBTecaProcessDao tecaProcessDao;

	@Autowired
	private DBTecaUserDao userDao;

	@Autowired
	private VfsFileSystem vfsFileSystem;

	@Autowired
	private MetsCreator metsCreator;

	// builder document
	private static DocumentBuilder docBuilder = null;

	@Autowired
	private SbnConfiguration sbnConfiguration;

	// logger
	private static Logger logger = LoggerFactory.getLogger(MagEditorService.class);

	/**
	 * Supporto versione 2 con file virtuali dei progetti
	 */
	boolean isVirtuale = true;


	static {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(true);
		
		try {
			docBuilder = docFactory.newDocumentBuilder();
			
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage(), e);
			docBuilder = null;
		}
	}

	/**
	 * Ricerca le sezioni attive per il MAG con ID specificato
	 * 
	 * LISTA SEZIONI ATTIVE (EDITOR)
	 * 
	 * @param magID ID del MAG
	 * @return List<String> lista delle sezioni attive
	 * @throws FileNotFoundException
	 */
	public List<String> getActiveSections(String magID) throws FileNotFoundException {
		List<String> sections = new ArrayList<String>();
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			throw new FileNotFoundException("MAG ID '" + magID + "' non trovato");
		
		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		NodeList children = originalDocument.getDocumentElement().getChildNodes();
		
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE && !sections.contains(child.getLocalName()))
				sections.add(child.getLocalName());
		}
		
		logger.info("Sezioni attive per il MAG ID '" + magID + "': " + sections.toString());
		return sections;
	}

	/**
	 * Restituisce i contenuti delle sezioni specificate per il MAG
	 * 
	 * LETTURA METADATI SEZIONI MAG 
	 * 
	 * @param magID ID MAG
	 * @param filterSections sezioni di cui filtrare i contenuti
	 * @return JsonMagEditorContent contenuti
	 * @throws FileNotFoundException
	 */
	public JsonMagEditorContent getActiveSections(String magID, List<String> filterSections) throws FileNotFoundException {
		JsonMagEditorContent content = new JsonMagEditorContent();
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			throw new FileNotFoundException("MAG ID '" + magID + "' non trovato");
		
		content.setProject(mag.getProject());
		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		
		if(filterSections.contains("gen"))
			content.setGen(EditorUtility.createGenJson(originalDocument));
		
		if(filterSections.contains("bib"))
			content.setBib(EditorUtility.createBibJson(originalDocument));
		
		if(filterSections.contains("stru"))
			content.getStru().addAll(EditorUtility.createStruJson(originalDocument));
		
		
		Document projectDocument = UtilXML.convertStringToDocumentXML(mag.getMagProject());
		List<String> types = Arrays.asList("img", "audio", "video", "ocr", "doc");
		
		for(String type : types) {
			if(filterSections.contains(type)) {
				EditorUtility.createDigitalResourcesSection(originalDocument, type, content);
				
				if(!this.hasResourcesProjectFolder(mag.getProject(), type, projectDocument)) {
					content.getWarnings().add("Il MAG contiene riferimenti ad oggetti digitali "
							+ "che non sono stati caricati nel progetto oppure "
							+ "sono stati cancellati dal progetto");
				}
			}
		}
		
		if(filterSections.contains("dis"))
			content.setDis(EditorUtility.createDisJson(originalDocument));
		
		return content;
	}

	/**
	 * Controlla la presenza delle risorse digitali originali nella directory di progetto originale,
	 * restituendo true se tutte le risorse sono ancora presenti
	 * 
	 * @param project
	 * @param type
	 * @param document
	 * @return
	 */
	private boolean hasResourcesProjectFolder(String project, String type, Document document) {
		NodeList resourceNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", type);
		
		for(int i = 0; i < resourceNodeList.getLength(); i++) {
			Element resourceNode = (Element) resourceNodeList.item(i);

			// per ogni file controlla percorso sul file system
			NodeList fileNodeList = resourceNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");

			for(int j = 0; j < fileNodeList.getLength(); j++) {
				Element fileNode = (Element) fileNodeList.item(j);
				String href = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href");
				
				File digitalRes = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), project + "/" + href);
				
				if(!digitalRes.exists())
					return false;
			}
		}
		
		return true;
	}

	/**
	 * Restituisce le versioni di una risorsa digitale
	 * 
	 * LETTURA AMBITI D'USO DISPONIBILI PER OGGETTI DIGITALE (EDITOR) 
	 * 
	 * @param magID ID MAG
	 * @param type tipo di risorsa digitale (img, audio, video, ocr, doc)
	 * @param seqNumber numero di sequenza
	 * @param usageFilter filtro per ottenere la versione raltiva ad un certo usage (opzionale)
	 * @return
	 * @throws IOException 
	 */
	public Map<Integer, JsonMagEditorUsageResource> getResourceVersions(String magID, String type,
			String seqNumber, Integer usageFilter) {
		
		Map<Integer, JsonMagEditorUsageResource> versions = new HashMap<Integer, JsonMagEditorUsageResource>();
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			return versions;
		
		// ricerca nel documento
		Document doc = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		
		Element parentResource = usageFilter == null ? 
				UtilXML.getResourceNode(doc, type, seqNumber) : 
				UtilXML.getResourceNode(doc, type, seqNumber, usageFilter + "");
		
		if(parentResource == null)
			return versions;
		
		// ocr/doc
		if("ocr".equals(parentResource.getLocalName()) || "doc".equals(parentResource.getLocalName()))
			versions.putAll(EditorUtility.createDocJson(parentResource));
		
		// audio/video
		else if("proxies".equals(parentResource.getLocalName()))
			versions.putAll(EditorUtility.createProxiesJson(parentResource));
			
		// audio/video
		else if("audio".equals(parentResource.getLocalName()) || "video".equals(parentResource.getLocalName())) {
			NodeList proxiesNodeList = parentResource.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
			
			for(int i = 0; i < proxiesNodeList.getLength(); i++)
				versions.putAll(EditorUtility.createProxiesJson((Element) proxiesNodeList.item(i)));
		}
		
		// immagini
		else {
			versions.putAll(EditorUtility.createImgJson(parentResource));
			
			if("img".equals(parentResource.getLocalName()) && usageFilter == null) {
				NodeList altimgNodeList = parentResource.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
				
				for(int i = 0; i < altimgNodeList.getLength(); i++)
					versions.putAll(EditorUtility.createImgJson((Element) altimgNodeList.item(i)));
			}
		}
		
		return versions;
	}
	
	/**
	 * Esegue il salvataggio della sezione GEN
	 * 
	 * SALVATAGGIO SEZIONE GEN
	 * 
	 * @param genObject oggetto JSON con metadati della sezione
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveGen(JsonMagEditorGen genObject, String magID, String project, int userID) {
		return saveGen(genObject, magID, project, userID, null);
	}
	public JsonEditorSaveResponse saveGen(JsonMagEditorGen genObject, String magID, String project, int userID, String label) {
		boolean isNew = magID==null;
		Mag mag = this.createMagObject(magID, project);
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}
		VfsFile vfsFile = null;
		if(!isNew){
			vfsFile = VfsService.getContainerByVfsPath(mag.getPath());
		}
		if(project==null)
			project = mag.getProject();
		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		Element genNode = EditorUtility.createGenXml(originalDocument, mag, genObject);
		
		Date now = new Date(System.currentTimeMillis());
		this.updateDocument("gen", Arrays.asList(genNode), originalDocument, now);
		
		Document projectDocument = mag.getMagProject() != null ? 
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
				
		EditorUtility.updateNamespaces(projectDocument);
		this.updateProjectDocument("gen", Arrays.asList(genNode), projectDocument, now, mag.getProject(), null);
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		
		try {
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "gen", now, userID);
			response.setId(mag.getIdMag());
			if(isNew) {
				String basePath = new File(ContentStatic.getProperties().getProperty("resourceDIRO"),
						project).getCanonicalPath();
				File drafttFile = vfsFileSystem.newFile(basePath, label != null ? label : "draft", mag.getIdMag(), project, "mag",
						vfsFile != null ? vfsFile.getVfsPath() : null, VfsFile.TYPE_CONTAINER);
				UtilSolr.setFieldSolr(mag.getIdMag(), "path", drafttFile.getPath(), true);
			}
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}
	
	/**
	 * Esegue il salvataggio della sezione BIB
	 * 
	 * SALVATAGGIO SEZIONE BIB
	 * 
	 * @param bibObject oggetto JSON con metadati della sezione
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveBib(JsonMagEditorBib bibObject, String magID, String project, int userID) {
		Mag mag = this.createMagObject(magID, project);
		
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}
		
		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		Element bibNode = EditorUtility.createBibXml(originalDocument, mag, bibObject);

		Date now = new Date(System.currentTimeMillis());
		this.updateDocument("bib", Arrays.asList(bibNode), originalDocument, now);
		
		Document projectDocument = mag.getMagProject() != null ? 
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
				
		EditorUtility.updateNamespaces(projectDocument);
		this.updateProjectDocument("bib", Arrays.asList(bibNode), projectDocument, now, mag.getProject(), null);
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		
		try {
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "bib", now, userID);
			response.setId(mag.getIdMag());
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}

	/**
	 * Esegue il salvataggio della sezione STRU
	 * 
	 * SALVATAGGIO SEZIONE STRU
	 * 
	 * @param struObjects lista oggetti JSON con metadati della sezione
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveStru(List<JsonMagEditorStru> struObjects, String magID, String project, int userID) {
		Mag mag = this.createMagObject(magID, project);
		
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}
		
		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		List<Element> struElements = EditorUtility.createStruXml(originalDocument, mag, struObjects);
		
		Date now = new Date(System.currentTimeMillis());
		this.updateDocument("stru", struElements, originalDocument, now);
		
		Document projectDocument = mag.getMagProject() != null ? 
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
				
		EditorUtility.updateNamespaces(projectDocument);
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		
		try {
			String oldMagDirectory = mag.getPath() == null || mag.getPath().isEmpty() ? 
					null : new File(mag.getPath()).getParentFile().getCanonicalPath();
					
			this.updateProjectDocument("stru", struElements, projectDocument, now, mag.getProject(), oldMagDirectory);
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "stru", now, userID);
			response.setId(mag.getIdMag());
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}

	/**
	 * Esegue il salvataggio della sezione IMG
	 * 
	 * SALVATAGGIO SEZIONE IMG
	 * 
	 * @param imgObjects lista oggetti JSON con metadati della sezione
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveImg(List<JsonMagEditorResource> imgObjects, String magID, String project, int userID) {
		Mag mag = this.createMagObject(magID, project);
		
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}

		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		// EditorUtility.checkSequenceNumbers(imgObjects);
		List<Integer> deleteElements = new ArrayList<Integer>();
		Map<Integer, Element> addOrUpdateElements = new HashMap<Integer, Element>();
		
		for(JsonMagEditorResource imgObj : imgObjects)
			this.createResourceXml(imgObj, originalDocument, addOrUpdateElements, deleteElements, "img");
		
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		Date now = new Date(System.currentTimeMillis());
		this.updateResourcesDocument("img", addOrUpdateElements, deleteElements, originalDocument, now);
		
		Document projectDocument = mag.getMagProject() != null ? 
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
				
		EditorUtility.updateNamespaces(projectDocument);
				
		try {
			String oldMagDirectory = mag.getPath() == null || mag.getPath().isEmpty() ? 
					null : new File(mag.getPath()).getParentFile().getCanonicalPath();
					
			boolean done = this.updateProjectResourcesDocument("img", addOrUpdateElements, deleteElements, 
					projectDocument, now, mag.getProject(), oldMagDirectory);

			if(!done) {
				response.setId(mag.getIdMag());
				response.setSaved(false);
				response.setError("Risorse digitali non più disponibili nel progetto originale");
				return response;
			}
			
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "img", now, userID);
			response.setId(mag.getIdMag());
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}

	/**
	 * Esegue il salvataggio della sezione AUDIO
	 * 
	 * SALVATAGGIO SEZIONE AUDIO
	 * 
	 * @param audioObjects lista oggetti JSON con metadati della sezione
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveAudio(List<JsonMagEditorResource> audioObjects, String magID, String project, int userID) {
		Mag mag = this.createMagObject(magID, project);
		
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}

		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		// EditorUtility.checkSequenceNumbers(audioObjects);
		List<Integer> deleteElements = new ArrayList<Integer>();
		Map<Integer, Element> addOrUpdateElements = new HashMap<Integer, Element>();
		
		for(JsonMagEditorResource audioObj : audioObjects)
			this.createResourceXml(audioObj, originalDocument, addOrUpdateElements, deleteElements, "audio");
		
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		Date now = new Date(System.currentTimeMillis());
		this.updateResourcesDocument("audio", addOrUpdateElements, deleteElements, originalDocument, now);

		// perchè si dovrebbe prendere il project, originale è stato aggiornato. L'originale potrebbe essere vestito
		boolean takeProjectDocument = false; // non funziona: !mag.isDraft();
		Document projectDocument = (takeProjectDocument && mag.getMagProject() != null) ?
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
		
		EditorUtility.updateNamespaces(projectDocument);		
		
		try {
			String oldMagDirectory = mag.getPath() == null || mag.getPath().isEmpty() ? 
					null : new File(mag.getPath()).getParentFile().getCanonicalPath();
			
			boolean done = this.updateProjectResourcesDocument("audio", addOrUpdateElements, deleteElements, 
					projectDocument, now, mag.getProject(), oldMagDirectory);

			if(!done) {
				response.setId(mag.getIdMag());
				response.setSaved(false);
				response.setError("Risorse digitali non più disponibili nel progetto originale");
				return response;
			}
			
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "audio", now, userID);
			response.setId(mag.getIdMag());
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}

	/**
	 * Esegue il salvataggio della sezione VIDEO
	 * 
	 * SALVATAGGIO SEZIONE VIDEO
	 * 
	 * @param videoObjects lista oggetti JSON con metadati della sezione
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveVideo(List<JsonMagEditorResource> videoObjects, String magID, String project, int userID) {
		Mag mag = this.createMagObject(magID, project);
		
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}

		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		// EditorUtility.checkSequenceNumbers(videoObjects);
		List<Integer> deleteElements = new ArrayList<Integer>();
		Map<Integer, Element> addOrUpdateElements = new HashMap<Integer, Element>();
		
		for(JsonMagEditorResource videoObj : videoObjects)
			this.createResourceXml(videoObj, originalDocument, addOrUpdateElements, deleteElements, "video");
		
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		Date now = new Date(System.currentTimeMillis());
		this.updateResourcesDocument("video", addOrUpdateElements, deleteElements, originalDocument, now);
		
		Document projectDocument = mag.getMagProject() != null ? 
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
		
		EditorUtility.updateNamespaces(projectDocument);
		
		try {
			String oldMagDirectory = mag.getPath() == null || mag.getPath().isEmpty() ? 
					null : new File(mag.getPath()).getParentFile().getCanonicalPath();
					
			boolean done = this.updateProjectResourcesDocument("video", addOrUpdateElements, deleteElements, 
					projectDocument, now, mag.getProject(), oldMagDirectory);

			if(!done) {
				response.setId(mag.getIdMag());
				response.setSaved(false);
				response.setError("Risorse digitali non più disponibili nel progetto originale");
				return response;
			}
			
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "video", now, userID);
			response.setId(mag.getIdMag());
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}

	/**
	 * Esegue il salvataggio della sezione OCR
	 * 
	 * SALVATAGGIO SEZIONE OCR
	 * 
	 * @param ocrObjects lista oggetti JSON con metadati della sezione
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveOcr(List<JsonMagEditorResource> ocrObjects, String magID, String project, int userID) {
		Mag mag = this.createMagObject(magID, project);
		
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}

		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		// EditorUtility.checkSequenceNumbers(ocrObjects);
		List<Integer> deleteElements = new ArrayList<Integer>();
		Map<Integer, Element> addOrUpdateElements = new HashMap<Integer, Element>();
		
		for(JsonMagEditorResource ocrObj : ocrObjects)
			this.createResourceXml(ocrObj, originalDocument, addOrUpdateElements, deleteElements, "ocr");
		
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		Date now = new Date(System.currentTimeMillis());
		this.updateResourcesDocument("ocr", addOrUpdateElements, deleteElements, originalDocument, now);
		
		Document projectDocument = mag.getMagProject() != null ? 
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
				
		EditorUtility.updateNamespaces(projectDocument);
				
		try {
			String oldMagDirectory = mag.getPath() == null || mag.getPath().isEmpty() ? 
					null : new File(mag.getPath()).getParentFile().getCanonicalPath();
			
			boolean done = this.updateProjectResourcesDocument("ocr", addOrUpdateElements, deleteElements, 
					projectDocument, now, mag.getProject(), oldMagDirectory);
			
			if(!done) {
				response.setId(mag.getIdMag());
				response.setSaved(false);
				response.setError("Risorse digitali non più disponibili nel progetto originale");
				return response;
			}
			
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "ocr", now, userID);
			response.setId(mag.getIdMag());
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}

	/**
	 * Esegue il salvataggio della sezione DOC
	 * 
	 * SALVATAGGIO SEZIONE DOC
	 * 
	 * @param docObjects lista oggetti JSON con metadati della sezione
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveDoc(List<JsonMagEditorResource> docObjects, String magID, String project, int userID) {
		Mag mag = this.createMagObject(magID, project);
		
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}

		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		// EditorUtility.checkSequenceNumbers(docObjects);
		List<Integer> deleteElements = new ArrayList<Integer>();
		Map<Integer, Element> addOrUpdateElements = new HashMap<Integer, Element>();
		
		for(JsonMagEditorResource docObj : docObjects)
			this.createResourceXml(docObj, originalDocument, addOrUpdateElements, deleteElements, "doc");
		
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		Date now = new Date(System.currentTimeMillis());
		this.updateResourcesDocument("doc", addOrUpdateElements, deleteElements, originalDocument, now);
		
		Document projectDocument = mag.getMagProject() != null ? 
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
		
		EditorUtility.updateNamespaces(projectDocument);
				
		try {
			String oldMagDirectory = mag.getPath() == null || mag.getPath().isEmpty() ? 
					null : new File(mag.getPath()).getParentFile().getCanonicalPath();
					
			boolean done = this.updateProjectResourcesDocument("doc", addOrUpdateElements, deleteElements, 
					projectDocument, now, mag.getProject(), oldMagDirectory);
			
			
			if(!done) {
				response.setId(mag.getIdMag());
				response.setSaved(false);
				response.setError("Risorse digitali non più disponibili nel progetto originale");
				return response;
			}
			
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "doc", now, userID);
			response.setId(mag.getIdMag());
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}
	
	/**
	 * Esegue il salvataggio della sezione DIS
	 * 
	 * SALVATAGGIO SEZIONE DIS
	 * 
	 * @param disObject oggetto sezione DIS JSON
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param project progetto di riferimento (modalità nuovo)
	 * @param userID ID utente
	 * @return JsonEditorSaveResponse esito finale salvataggio
	 */
	public JsonEditorSaveResponse saveDis(JsonMagEditorDis disObject, String magID, String project, int userID) {
		Mag mag = this.createMagObject(magID, project);
		
		if(mag == null) {
			JsonEditorSaveResponse response = new JsonEditorSaveResponse();
			
			if(magID != null)
				response.setId(magID);
			
			response.setSaved(false);
			response.setError("MAG non " + (magID != null ? "trovato" : "creato"));
			return response;
		}

		Document originalDocument = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
		EditorUtility.updateNamespaces(originalDocument);
		Element disNode = EditorUtility.createDisXml(originalDocument, disObject);
		
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		Date now = new Date(System.currentTimeMillis());
		this.updateDocument("dis", Arrays.asList(disNode), originalDocument, now);
		
		Document projectDocument = mag.getMagProject() != null ? 
				UtilXML.convertStringToDocumentXML(mag.getMagProject()) : 
					(Document) originalDocument.cloneNode(true);
		
		EditorUtility.updateNamespaces(projectDocument);
				
		try {
			String oldMagDirectory = mag.getPath() == null || mag.getPath().isEmpty() ? 
					null : new File(mag.getPath()).getParentFile().getCanonicalPath();
			
			boolean done = this.updateProjectDocument("dis", Arrays.asList(disNode), 
					projectDocument, now, mag.getProject(), oldMagDirectory);
			
			if(!done) {
				response.setId(mag.getIdMag());
				response.setSaved(false);
				response.setError("Risorse digitali non più disponibili nel progetto originale");
				return response;
			}
			
			this.indexEditedMag(mag, originalDocument, projectDocument, magID, "dis", now, userID);
			response.setId(mag.getIdMag());
			response.setSaved(true);
			return response;
			
		} catch (SolrServerException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (IOException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		} catch (DaoException e) {
			if(magID != null)
				response.setId(magID);
			
			logger.error("Errore database: " + e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}
	
	/**
	 * Crea il nodo XML dell'oggetto digitale e lo aggiunge alla lista
	 * 
	 * @param resourceObj oggetto JSON risorsa
	 * @param document documento MAG
	 * @param addOrUpdateElements mappa degli elementi da inserire/aggiornare
	 * @param deleteElements lista degli elementi da cancellare
	 * @param resourceType tipo di risorsa digitale (IMG, AUDIO, VIDEO, OCR, DOC)
	 */
	private void createResourceXml(JsonMagEditorResource resourceObj, Document document, 
			Map<Integer, Element> addOrUpdateElements, List<Integer> deleteElements, 
			String resourceType) {
		
		// inserimento
		if(resourceObj.isAdded()) {
			Element newNode = null;
			
			if("img".equals(resourceType))
				newNode = EditorUtility.createImgXml(document, resourceObj);
			
			else if("audio".equals(resourceType))
				newNode = EditorUtility.createAudioXml(document, resourceObj);
			
			else if("video".equals(resourceType))
				newNode = EditorUtility.createVideoXml(document, resourceObj);
			
			else if("ocr".equals(resourceType))
				newNode = EditorUtility.createOcrXml(document, resourceObj);
			
			else if("doc".equals(resourceType))
				newNode = EditorUtility.createDocXml(document, resourceObj);

			if(newNode != null)
				addOrUpdateElements.put(Integer.parseInt(resourceObj.getSequenceNumber()), newNode);
		}
		
		else {
			// cancellazione
			if(resourceObj.isDeleted())
				deleteElements.add(Integer.parseInt(resourceObj.getId()));
			
			// aggiornamento
			else if(resourceObj.isUpdated()) {
				int oldID = Integer.parseInt(resourceObj.getId());
				
				// risorsa vecchia da cancellare per spostamento
				if(oldID != Integer.parseInt(resourceObj.getSequenceNumber()))
					deleteElements.add(oldID);
				
				// invio oggetto digitale parziale --> merge del vecchio e del nuovo
				if(resourceObj.getVersions().isEmpty()) {
					// prendi il nodo vecchio
					Element oldNode = UtilXML.getResourceNode(document, resourceType, 
							new Integer(resourceObj.getId()).toString());
					
					// aggancia le precedenti risorse digitali
					if(oldNode != null) {
						// ocr/doc
						if("ocr".equals(oldNode.getLocalName()) || "doc".equals(oldNode.getLocalName()))
							resourceObj.getVersions().putAll(EditorUtility.createDocJson(oldNode));

						// audio/video
						else if("audio".equals(oldNode.getLocalName()) || "video".equals(oldNode.getLocalName())) {
							NodeList proxiesNodeList = oldNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
							
							for(int i = 0; i < proxiesNodeList.getLength(); i++)
								resourceObj.getVersions().putAll(EditorUtility.createProxiesJson((Element) proxiesNodeList.item(i)));
						}
						
						// img
						else if("img".equals(oldNode.getLocalName())) {
							resourceObj.getVersions().putAll(EditorUtility.createImgJson(oldNode));
							
							NodeList altimgNodeList = oldNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
							
							for(int i = 0; i < altimgNodeList.getLength(); i++)
								resourceObj.getVersions().putAll(EditorUtility.createImgJson((Element) altimgNodeList.item(i)));
						}
					}
				}
				
				Element newNode = null;
				
				if("img".equals(resourceType))
					newNode = EditorUtility.createImgXml(document, resourceObj);
				
				else if("audio".equals(resourceType))
					newNode = EditorUtility.createAudioXml(document, resourceObj);
				
				else if("video".equals(resourceType))
					newNode = EditorUtility.createVideoXml(document, resourceObj);
				
				else if("ocr".equals(resourceType))
					newNode = EditorUtility.createOcrXml(document, resourceObj);
				
				else if("doc".equals(resourceType))
					newNode = EditorUtility.createDocXml(document, resourceObj);

				if(newNode != null)
					addOrUpdateElements.put(Integer.parseInt(resourceObj.getSequenceNumber()), newNode);
			}
		}
	}
	
	/**
	 * Restituisce l'oggetto MAG indicizzato attraverso il suo ID o ne crea uno nuovo
	 * 
	 * @param magID ID del MAG
	 * @param project progetto
	 * @return Mag oggetto MAG indicizzato
	 */
	private Mag createMagObject(String magID, String project) {
		if(magID == null) {
			if(docBuilder == null)
				return null;
			
			Document document = docBuilder.newDocument();
			Element root = document.createElementNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit");
			root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns", "http://www.iccu.sbn.it/metaAG1.pdf");
			root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:mag", "http://www.iccu.sbn.it/metaAG1.pdf");
			root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:dc", "http://purl.org/dc/elements/1.1/");
			root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:niso", "http://www.niso.org/pdfs/DataDict.pdf");
			root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:oai_dc", "http://www.openarchives.org/OAI/2.0/oai_dc/");
			root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:xlink", "http://www.w3.org/TR/xlink");
			root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			
			root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", 
					"http://www.iccu.sbn.it/metaAG1.pdf metadigit.xsd");
			
			root.setPrefix(root.lookupPrefix("http://www.iccu.sbn.it/metaAG1.pdf"));
			document.appendChild(root);
			
			Mag mag = new Mag();
			mag.setProject(project);
			mag.setDocumentFormat("mag");
			mag.setDraft(true);
			mag.setDeleted(false);
			mag.setPublishFlag(0);
			mag.setMagProject(UtilXML.convertDocumentToString(document));
			mag.setMagOriginal(UtilXML.convertDocumentToString(document));
			mag.setMagInternal(null);
			mag.setMagExternal(null);
			mag.setMagTot(null);
			return mag;
		}
		
		else
			return UtilSolr.selectDocumentById(magID);
	}
	
	/**
	 * Esegue l'indicizzazione della bozza
	 * 
	 * @param magObject oggetto MAG
	 * @param originalDocument documento originale
	 * @param projectDocument documento originale di progetto
	 * @param magID ID del MAG (modalità aggiornamento)
	 * @param resourceType tipo di risorsa digitale (img, audio, video, ocr, doc)
	 * @param timestamp timestamp
	 * @param userID ID utente
	 * @throws SolrServerException
	 * @throws IOException
	 * @throws DaoException 
	 */
	private void indexEditedMag(Mag magObject, Document originalDocument, 
			Document projectDocument, String magID, String resourceType, 
			Date timestamp, int userID) throws SolrServerException, IOException, DaoException {
		
		// creazione oggetto
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		if(magID == null || !magObject.isDraft()) {
			if(!magObject.isDraft()) {
				Document internalDocument = UtilXML.convertStringToDocumentXML(magObject.getMagInternal());
				
				if(internalDocument!=null && !"a".equals(magObject.getLevel())) {
					List<String> deliveryIDs = UtilXML.getResourceIDs(internalDocument);
					//delivery.deleteTecaResourcesNotVirtual(magID, deliveryIDs);
				}
				
				magObject.setMagInternal(null);
				magObject.setMagExternal(null);
			}
			
			magObject.setIdMag("draft_" + formatter.format(timestamp));
			magObject.setDraft(true);
			magObject.setPublishFlag(0);
		}
		
		magObject.setTimestamp(timestamp);
		magObject.setMagProject(UtilXML.convertDocumentToString(projectDocument));
		magObject.setMagOriginal(UtilXML.convertDocumentToString(originalDocument));
		
		
		
		// indicizzazione Solr
		if(magID != null)
			UtilSolr.deleteMag(Arrays.asList(magID), true);
		
		UtilSolr.insertListMag(Arrays.asList(magObject));
		
		
		
		// salvataggio sul database
		DBTecaProcess process = tecaProcessDao.findByID(magObject.getIdMag());
		boolean processExists = process != null;
		
		if(!processExists) {
			process = new DBTecaProcess();
			process.setId(magObject.getIdMag());
			process.setTimestampStart(new BigInteger(timestamp.getTime() + ""));
			process.setStatus(0);
		}
		
		process.setMessage("Bozza " + (magID != null ? "aggiornata" : "creata"));
		process.setTimestampEnd(new BigInteger(timestamp.getTime() + ""));
		
		DBTecaUser userDB = userDao.getUserByID(userID);
		process.setTecaUser(userDB);
		
		if(processExists)
			tecaProcessDao.update(process);
		
		else
			tecaProcessDao.insert(process);
	}
	
	/**
	 * Aggiornamento sezioni generali MAG (GEN, BIB, STRU, DIS)
	 * 
	 * @param section sezione MAG da aggiornare
	 * @param elements elementi aggiornati della sezione
	 * @param document documento MAG
	 * @param timestamp timestamp
	 */
	private void updateDocument(String section, List<Element> elements, Document document, Date timestamp) {
		// aggiorna timestamp GEN
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		// sezione GEN modificata
		if("gen".equals(section)) {
			Element genNode = elements.get(0);
			genNode.setAttribute("last_update", formatter.format(timestamp));
			NodeList oldGenNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen");
			
			if(oldGenNodeList.getLength() > 0) {
				Element oldGenNode = (Element) oldGenNodeList.item(0);
				
				if(oldGenNode.hasAttribute("creation"))
					genNode.setAttribute("creation", oldGenNode.getAttribute("creation"));
				
				else
					genNode.setAttribute("creation", formatter.format(timestamp));
			}
			
			else
				genNode.setAttribute("creation", formatter.format(timestamp));
		}
		
		// altre sezioni modificate
		else {
			NodeList oldGenNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen");
			
			if(oldGenNodeList.getLength() > 0) {
				Element genNode = (Element) oldGenNodeList.item(0);
				genNode.setAttribute("last_update", formatter.format(timestamp));
				
				if(!genNode.hasAttribute("creation"))
					genNode.setAttribute("creation", formatter.format(timestamp));
			}
		}
		
		// modifica sezione
		NodeList sectionNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", section);
		
		// cancella i vecchi nodi
		if(sectionNodeList.getLength() > 0) {
			List<Node> removeElements = new ArrayList<Node>();
			Node metadigitNode = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
			
			for(int i = 0; i < sectionNodeList.getLength(); i++) {
				Node oldSectionNode = sectionNodeList.item(i);
				Node parent = oldSectionNode.getParentNode();
				
				if("metadigit".equals(parent.getLocalName()))
					removeElements.add(oldSectionNode);
			}
			
			for(Node elem : removeElements)
				metadigitNode.removeChild(elem);
			
			if(!removeElements.isEmpty())
				document.normalize();
		}
		
		// ordine di inserimento sezione
		List<String> order = NormalizationUtility.getOrderContainer("metadigit");
		int idx = order.indexOf(section);
		boolean append = true;
		
		for(int i = idx + 1; i < order.size(); i++) {
			NodeList nextSectionNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", order.get(i));
			
			// ordinamento a sinistra rispetto alla sezione successiva
			if(nextSectionNodeList.getLength() > 0) {
				Node metadigitNode = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
				Node nextSibling = nextSectionNodeList.item(0);
				
				for(int j = 0; j < elements.size(); j++)
					metadigitNode.insertBefore(elements.get(j), nextSibling);
				
				append = false;
				break;
			}
		}
		
		// non ci sono sezioni successive, append delle risorse digitali
		if(append) {
			Node metadigitNode = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
			
			for(int i = 0; i < elements.size(); i++)
				metadigitNode.appendChild(elements.get(i));
		}
			
		
		document.normalize();
	}
	
	/**
	 * Aggiornamento sezioni oggetti digitali MAG (IMG, AUDIO, VIDEO, OCR, DOC)
	 * 
	 * @param section sezione oggetti digitali
	 * @param addOrUpdateElements mappa degli elementi da inserire/modificare
	 * @param deleteElements lista degli elementi da cancellare
	 * @param document documento MAG
	 * @param timestamp timestamp
	 */
	private void updateResourcesDocument(String section, Map<Integer, Element> addOrUpdateElements, 
			List<Integer> deleteElements, Document document, Date timestamp) {
		
		// crea lista sequence numbers
		List<Integer> sequenceNumbers = new ArrayList<Integer>(addOrUpdateElements.keySet());
		Collections.sort(sequenceNumbers);
		Collections.sort(deleteElements);
		
		// aggiorna timestamp GEN
		NodeList genNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen");
		
		if(genNodeList.getLength() > 0) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Element genNode = (Element) genNodeList.item(0);
			genNode.setAttribute("last_update", formatter.format(timestamp));
			
			if(!genNode.hasAttribute("creation"))
				genNode.setAttribute("creation", formatter.format(timestamp));
		}

		// aggiorna la sezione
		NodeList sectionNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", section);
		
		// presenza di risorse digitali --> aggiornamento
		if(sectionNodeList.getLength() > 0) {
			if(!deleteElements.isEmpty()) {
				int countDeleted = 0;
				List<Node> nodesToRemove = new ArrayList<Node>();
				
				for(int i = 0; i < sectionNodeList.getLength(); i++) {
					Element sectionNode = (Element) sectionNodeList.item(i);
					
					NodeList sequenceNumberNode = sectionNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", 
							"sequence_number");
					
					if(sequenceNumberNode.getLength() > 0) {
						int seqNumber = Integer.parseInt(sequenceNumberNode.item(0).getTextContent());
						
						if(countDeleted < deleteElements.size() && seqNumber == deleteElements.get(countDeleted)) {
							nodesToRemove.add(sectionNode);
							countDeleted++;
						}
					}
					
					// aggiunti e cancellati tutti
					if(countDeleted == deleteElements.size())
						break;
				}
				
				// cancella i nodi da cancellare
				if(!nodesToRemove.isEmpty()) {
					for(Node removeNode : nodesToRemove)
						removeNode.getParentNode().removeChild(removeNode);
					
					document.normalize();
				}
			}
			
			if(!addOrUpdateElements.isEmpty()) {
				int countAdded = 0;
				
				for(int i = 0; i < sectionNodeList.getLength(); i++) {
					Element sectionNode = (Element) sectionNodeList.item(i);
					
					NodeList sequenceNumberNode = sectionNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", 
							"sequence_number");
					
					if(sequenceNumberNode.getLength() > 0) {
						int seqNumber = Integer.parseInt(sequenceNumberNode.item(0).getTextContent());
						
						if(countAdded < sequenceNumbers.size() && seqNumber == sequenceNumbers.get(countAdded)) {
							sectionNode.getParentNode().replaceChild(addOrUpdateElements.get(seqNumber), sectionNode);
							countAdded++;
						}
						
						else if(countAdded < sequenceNumbers.size() && seqNumber > sequenceNumbers.get(countAdded)) {
							sectionNode.getParentNode().insertBefore(addOrUpdateElements.
									get(sequenceNumbers.get(countAdded)), sectionNode);
							
							countAdded++;
						}
					}
					
					// aggiunti e cancellati tutti
					if(countAdded == sequenceNumbers.size())
						break;
				}
				
				// aggiungi in coda
				if(countAdded < sequenceNumbers.size()) {
					// ordine di inserimento sezione
					List<String> order = NormalizationUtility.getOrderContainer("metadigit");
					int idx = order.indexOf(section);
					boolean append = true;
					
					for(int i = idx + 1; i < order.size(); i++) {
						NodeList nextSectionNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", order.get(i));
						
						// ordinamento a sinistra rispetto alla sezione successiva
						if(nextSectionNodeList.getLength() > 0) {
							Node metadigitNode = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
							Node nextSibling = nextSectionNodeList.item(0);
							
							for(int j = countAdded; j < sequenceNumbers.size(); j++)
								metadigitNode.insertBefore(addOrUpdateElements.get(sequenceNumbers.get(j)), nextSibling);
							
							append = false;
							break;
						}
					}
					
					// non ci sono sezioni successive, append delle risorse digitali
					if(append) {
						Node metadigitNode = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
						
						for(int j = countAdded; j < sequenceNumbers.size(); j++)
							metadigitNode.appendChild(addOrUpdateElements.get(sequenceNumbers.get(j)));
					}
				}
				
				document.normalize();
			}
		}
		
		// solo inserimento
		else {
			// ordine di inserimento sezione
			List<String> order = NormalizationUtility.getOrderContainer("metadigit");
			int idx = order.indexOf(section);
			boolean append = true;
			
			for(int i = idx + 1; i < order.size(); i++) {
				NodeList nextSectionNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", order.get(i));
				
				// ordinamento a sinistra rispetto alla sezione successiva
				if(nextSectionNodeList.getLength() > 0) {
					Node metadigitNode = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
					Node nextSibling = nextSectionNodeList.item(0);
					
					for(int j = 0; j < sequenceNumbers.size(); j++)
						metadigitNode.insertBefore(addOrUpdateElements.get(sequenceNumbers.get(j)), nextSibling);
					
					append = false;
					break;
				}
			}
			
			// non ci sono sezioni successive, append delle risorse digitali
			if(append) {
				Node metadigitNode = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
				
				for(int j = 0; j < sequenceNumbers.size(); j++)
					metadigitNode.appendChild(addOrUpdateElements.get(sequenceNumbers.get(j)));
			}
			
			document.normalize();
		}
	}
	
	/**
	 * Aggiornamento documento MAG originale per il progetto (GEN, BIB, STRU, DIS)
	 * 
	 * @param section sezione MAG da aggiornare
	 * @param elements elementi aggiornati della sezione
	 * @param document documento MAG
	 * @param timestamp timestamp
	 * @param project progetto di riferimento
	 * @param oldMagDirectory directory del MAG se precedentemente importato
	 * @return boolean true se le risorse digitali del progetto esistono nel file system
	 */
	private boolean updateProjectDocument(String section, List<Element> elements, 
			Document document, Date timestamp, String project, String oldMagDirectory) {
		
		List<Element> elementsForProject = new ArrayList<Element>();
		
		for(Element originalElem : elements) {
			Element elemForProject = (Element) document.importNode(originalElem, true);
			NodeList fileNodeList = elemForProject.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
			for(int i = 0; i < fileNodeList.getLength(); i++) {
				if(!makeFileElement(document, fileNodeList, i, project, oldMagDirectory))
					return false;
			}
			elementsForProject.add(elemForProject);
		}
		
		this.updateDocument(section, elementsForProject, document, timestamp);
		return true;
	}

	public boolean makeFileElement(Document document, NodeList fileNodeList, int i, String project, String oldMagDirectory){
		boolean skipThisMapping = true;
		Element fileNode = (Element) fileNodeList.item(i);
		String href = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href");
		String deliveryID = href.replace("digitalObject/", "").replace("/original", "");
		String originalPath = delivery.findPathByID(deliveryID);

		if(originalPath != null && !originalPath.isEmpty() && new File(originalPath).exists()) {
			String relativePath = this.getRelativePath(originalPath, project, oldMagDirectory);

			if(relativePath.startsWith("/"))
				relativePath = relativePath.substring(1);

			if(skipThisMapping && href.startsWith("digitalObject/")) {
				logger.info("Skipping mapping " + href + " to " + relativePath);
				fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href",
						href.replace("/original", ""));
			}
			else
				fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", relativePath);
			return true;
		}
		else
			return false;
	}
	/**
	 * Aggiornamento documento MAG originale per il progetto (IMG, AUDIO, VIDEO, OCR, DOC)
	 * 
	 * @param section sezione MAG da aggiornare
	 * @param addOrUpdateElements elementi aggiornati della sezione
	 * @param document documento MAG
	 * @param timestamp timestamp
	 * @param project progetto di riferimento
	 * @param oldMagDirectory directory del MAG se precedentemente importato
	 * @return boolean true se le risorse digitali del progetto esistono nel file system
	 */
	private boolean updateProjectResourcesDocument(String section, Map<Integer, Element> addOrUpdateElements, 
			List<Integer> deleteElements, Document document, Date timestamp, 
			String project, String oldMagDirectory) {
		
		Map<Integer, Element> addOrUpdateElementsForProject = new HashMap<Integer, Element>();
		
		// aggiorna i riferimenti al file system per il nodo file
		for(Entry<Integer, Element> elementEntry : addOrUpdateElements.entrySet()) {
			Element elemForProject = (Element) document.importNode(elementEntry.getValue(), true);
			NodeList fileNodeList = elemForProject.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
			
			for(int i = 0; i < fileNodeList.getLength(); i++) {
				if(!makeFileElement(document, fileNodeList, i, project, oldMagDirectory))
					return false;
			}

			// aggiorna i riferimenti al file system per il nodo source
			if("ocr".equals(section)) {
				NodeList sourceNodeList = elemForProject.
						getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "source");
				
				for(int i = 0; i < sourceNodeList.getLength(); i++) {
					if(!makeFileElement(document, sourceNodeList, i, project, oldMagDirectory))
						return false;
				}
			}
			
			addOrUpdateElementsForProject.put(elementEntry.getKey(), elemForProject);
		}
		
		this.updateResourcesDocument(section, addOrUpdateElementsForProject, deleteElements, document, timestamp);
		return true;
	}
	
	/**
	 * Costruisce il path relativo (partendo dalla directory root del progetto) per il documento originale per il progetto
	 * 
	 * @param absolutePath file corrente da analizzare
	 * @param project nome progetto
	 * @param oldMagDirectory vecchia directory del MAG (precedentemente importato)
	 * @return String percorso relativo della risorsa digitale partendo dalla directory root del progetto
	 */
	private String getRelativePath(String absolutePath, String project, String oldMagDirectory) {
		// MAG creato ex novo
		if(oldMagDirectory == null || oldMagDirectory.isEmpty()) {
			String projectDir = new File(ContentStatic.getProperties().
					getProperty("resourceDIRO"), project).getPath();
			
			return "." + absolutePath.replaceAll(projectDir, "");
		}
		
		// MAG precedentemente importato
		else {
			Path pathAbsolute = Paths.get(absolutePath);
	        Path pathBase = Paths.get(oldMagDirectory);
	        Path pathRelative = pathBase.relativize(pathAbsolute);
	        return pathRelative.toString();
		}
	}
	
	/**
	 * Effettua la creazione su file system della bozza specificata dal suo ID, importante
	 * per l'importazione del MAG
	 * 
	 * @param magID ID del MAG bozza
	 * @return JsonImportProject oggetto JSON con i dati del MAG da importare
	 * @throws IOException
	 */
	public JsonEditorValidateResponse createEditorMagFS(String magID) {
		JsonEditorValidateResponse response = new JsonEditorValidateResponse();
		
		// query Solr
		Mag magObject = UtilSolr.selectDocumentById(magID);
		
		if(magObject == null) {
			response.setError("Bozza MAG con ID '" + magID + "' non trovata");
			return response;
		}
		
		// crea file MAG su file system
		String fileName = "teca_digitale_editor[" + magID + "].xml";
		String relativePath = "";
		
		try {
			String basePath = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), 
					magObject.getProject()).getCanonicalPath();
			
			// calcola path relativo
			if(magObject.getPath() != null && !magObject.getPath().isEmpty()) {
				String oldBasePath = basePath;
				basePath = new File(magObject.getPath()).getParentFile().getCanonicalPath();
				
				if(!oldBasePath.equals(basePath))
					relativePath = basePath.replaceAll(oldBasePath + "/", "") + "/";
			}
			
			File magFile = vfsFileSystem.newFile(basePath, fileName, magID, magObject.getProject(), "mag",
					magObject.getPath(), VfsFile.TYPE_CONTAINER);
			StringReader reader = new StringReader(magObject.getMagProject());
			if(!magFile.getParentFile().exists())
				magFile.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(magFile, Charset.forName("UTF-8")); // attenzione alla codifica
			IOUtils.copy(reader, writer);
			reader.close();
			writer.close();
			
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
		
		// restituisci oggetto JSON
		response.setProject(magObject.getProject());
		response.setMag(relativePath + fileName);
		response.setIdentifier(magObject.getIdentifiers().isEmpty() ? " - " : magObject.getIdentifiers().get(0));
		
		if(magObject.getYear() != null && !magObject.getYear().isEmpty())
			response.setYearPartNumber(magObject.getYear());
		
		if(magObject.getPartNumber() != null && !magObject.getPartNumber().isEmpty())
			response.setYearPartNumber(magObject.getPartNumber());
		
		if(magObject.getIssue() != null && !magObject.getIssue().isEmpty())
			response.setIssuePartName(magObject.getIssue());
		
		if(magObject.getPartName() != null && !magObject.getPartName().isEmpty())
			response.setIssuePartName(magObject.getPartName());
		return response;
	}
	
	/**
	 * Verifica l'esistenza del progetto originale dato l'ID del MAG (indicizzato o bozza)
	 * 
	 * VERIFICA PRESENZA PROGETTO SU FILE SYSTEM PER MAG IMPORTATI DA MODIFICARE CON EDITOR
	 * 
	 * @param magID ID del MAG
	 * @return Boolean true se il progetto originale c'è, false altrimenti
	 */
	public Boolean checkProjectForEditor(String magID) {
		// query Solr
		Mag magObject = UtilSolr.selectDocumentById(magID);
		if(magObject == null){
			try {
				magObject = UtilSolr.selectDocumentById(
						UtilSolr.getMagIDFromPath(UtilSolr.getPathFromId(magID))
				);
			} catch (SolrServerException e) {
				logger.error("", e);
			}
		}
		if(magObject == null) {
			logger.error("MAG '" + magID + "' non trovato");
			return false;
		}

		/**
		 * TODO: perchè serve il file originale? projectFile.exists(); Perché le immagini vengono importate
		 * nuovamente dal progetto
		 */
		if(isVirtuale){
			return true;
		}
		else {
			File projectFile = new File(ContentStatic.getProperties().getProperty("resourceDIRO"),
					magObject.getProject());
			return vfsFileSystem.exists(projectFile);
		}
	}
	
	/**
	 * Restituisce la sezione BIB del MAG identificato dall'identifier
	 * 
	 * SERVIZIO SBN
	 * 
	 * @param identifier identifier MAG
	 * @return JsonMagEditorBib sezione BIB per l'editor
	 */
	public JsonMagEditorBib getBibBySbnRequest(String identifier) {
		if("OPAC".equalsIgnoreCase(sbnConfiguration.getRequestType())) {
			MagTransformer magTransformer = new MagTransformer();  
			UnimarcClient unimarcClient = new UnimarcClient();                                                                                                                                         
		                                                                                                           
		    try {                                                                                                  
		        String mag = unimarcClient.getOpacSbn2Mag(magTransformer, identifier);
		        if(mag.startsWith("<error>")) {
		        	logger.error("Error for id:'"+identifier+"': "+mag);
					return new JsonMagEditorBib();
				}
		        Document doc = docBuilder.parse(new InputSource(new StringReader(mag)));
				return EditorUtility.createBibJson(doc);
		        
		    } catch (Exception e) {                                                                                
		        logger.error("",e);
		    }                                                                                                      

		}
		
		else if("SBNMARC".equalsIgnoreCase(sbnConfiguration.getRequestType())) {
			MagTransformer magTransformer = new MagTransformer();  
			SbnMarcClient sbnMarcClient = new SbnMarcClient();                                                     
		    sbnMarcClient.setUsername(sbnConfiguration.getRequestUsername());                                                                   
		    sbnMarcClient.setUrl(sbnConfiguration.getRequestUrl());   
		    
		    try {                                                                                                  
		        String mag = sbnMarcClient.getSbnMarc2Mag(magTransformer, identifier);  
		        Document doc = docBuilder.parse(new InputSource(new StringReader(mag)));
				return EditorUtility.createBibJson(doc);
		        
		    } catch (Exception e) {                                                                                
		        logger.error("", e);
		    }                                               
		}
		
		else
			logger.error("Tipo di richiesta sconosciuta");
		
		return new JsonMagEditorBib();
	}

	public Mods getModsBySbnRequest(String identifier) {
		UnimarcClient unimarcClient = new UnimarcClient();
		try {
			String mods = unimarcClient.getOpacSbn2Mods(identifier);
			if(mods.contains("<error>"))
				throw new Exception(mods);
			MetsConvertor metsConvertor = new MetsConvertor();
			ModsCollection modsCollection = metsConvertor.readModsFromString(mods);
			if(modsCollection!=null && modsCollection.getMods().size()>0)
				return modsCollection.getMods().get(0);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}


	public JsonEditorSaveResponse createMetsForUser(String project, Integer userID, String label) {
		JsonEditorSaveResponse response = new JsonEditorSaveResponse();
		String magID = null;
		try {
			/**
			 * creazione container
			 */
			String basePath =  new File(ContentStatic.getProperties().getProperty("resourceDIRO"),
					project).getCanonicalPath();
			File drafttFile = vfsFileSystem.newFile(basePath, label!=null?label:"draft", "bozza", project, "mets",null, VfsFile.TYPE_CONTAINER);
			VfsFile vfsFile = VfsService.getVfsFileByVfsPath(drafttFile.getPath());
			vfsFileSystem.getVfsService().save(vfsFile);
			/**
			 * nuovo mets
			 */
			metsCreator.createMets(vfsFile.getId(), null, true, false);
			/**
			 * risposta
			 */
			response.setId(UtilSolr.getMagIDFromPath(drafttFile.getPath()));
			UtilSolr.setFieldSolr(response.getId(), "draft","1", true);

			response.setSaved(true);
			return response;

		} catch (Exception e) {
			if(magID != null)
				response.setId(magID);

			logger.error(e.getMessage(), e);
			response.setError(e.getMessage());
			return response;
		}
	}

}
