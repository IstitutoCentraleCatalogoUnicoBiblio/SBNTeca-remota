package com.gruppometa.sbntecaremota.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Oggetto contenente i metadati di generali di un MAG
 * 
 *
 */
public class Mag extends MagReference {
	private static final long serialVersionUID = 3549072868313164903L;
	
	public static final String MAG = "mag";
	public static final String METS = "mets";
	public static final String IIIF = "iiif";
	public static final String MAG02 = "xml-mag02";
	
	private int accessRights;
	private int completeness;
	private int publishFlag = 0;
	
	private String magProject;
	private String magOriginal;
	private String metsOriginal;
	private String metsExternal;
	private String magInternal;
	private String magExternal;
	private String magTot;
	private String collection;
	private String agency;
	private String level;
	private String idMag;
	private String oaiIdentifier;
	private String stpiecePer;
	private String partNumber;
	private String partName;
	private String stpieceVol;
	private String creation;
	private String lastUpdate;
	private String stprog;
	private String idJob;
	private String path;
    private String project;
    private String magPreview;
    
	private List<String> titles = new ArrayList<String>();
	private List<String> libraries = new ArrayList<String>();
	private List<String> inventoryNumbers = new ArrayList<String>();
	private List<String> shelfmarks = new ArrayList<String>();
	private List<String> contributors = new ArrayList<String>();
	private List<String> usageA = new ArrayList<String>();
	private List<String> usageE = new ArrayList<String>();
    private List<String> creators = new ArrayList<String>();
    private List<String> publishers = new ArrayList<String>();
    private List<String> subjects = new ArrayList<String>();
    private List<String> descriptions = new ArrayList<String>();
    private List<String> dates = new ArrayList<String>();
    private List<String> types = new ArrayList<String>();
    private List<String> formats = new ArrayList<String>();
    private List<String> sources = new ArrayList<String>();
    private List<String> languages = new ArrayList<String>();
    private List<String> relations = new ArrayList<String>();
    private List<String> coverages = new ArrayList<String>();
    private List<String> rights = new ArrayList<String>();
    private List<String> geoCoord = new ArrayList<String>();
    private List<String> notDate = new ArrayList<String>();
    private List<String> struNomenclatures = new ArrayList<String>();
    private List<String> elementNomenclatures = new ArrayList<String>();
    private List<String> digitalTypes = new ArrayList<String>();
    private List<String> mimeTypes = new ArrayList<String>();
    
    private boolean deleted = false;
    private boolean draft = false;
    private String documentFormat = MAG;
    private Date timestamp;
    private Date timestampCreated;
    private Date timestampDeleted;
    
    List<DeliveryResource> digitalResources = new ArrayList<DeliveryResource>();
    private int numberImg = 0;
    private int numberVideo = 0;
    private int numberAudio = 0;
    private int numberOcr = 0;
    private int numberDoc = 0;
    
    
    
    /**
     * Restituisce la versione originale del MAG caricata tramite progetto (XML in formato String)
     * 
     * @return String versione originale del MAG caricata tramite progetto (XML in formato String)
     */
	public String getMagProject() {
		return magProject;
	}
	
	/**
	 * Imposta la versione originale del MAG caricata tramite progetto (XML in formato String)
	 * 
	 * @param magOriginal versione originale del MAG caricata tramite progetto (XML in formato String)
	 */
	public void setMagProject(String magProject) {
		this.magProject = magProject;
	}

    /**
     * Restituisce la versione originale del MAG (XML in formato String)
     * 
     * @return String versione originale del MAG (XML in formato String)
     */
	public String getMagOriginal() {
		return magOriginal;
	}
	
	/**
	 * Imposta la versione originale del MAG (XML in formato String)
	 * 
	 * @param magOriginal versione originale del MAG (XML in formato String)
	 */
	public void setMagOriginal(String magOriginal) {
		this.magOriginal = magOriginal;
	}
	
	/**
	 * Restituisce la versione acquisizione del MAG (XML in formato String)
	 * 
	 * @return String versione acquisizione del MAG (XML in formato String)
	 */
	public String getMagInternal() {
		return magInternal;
	}
	
	/**
	 * Imposta la versione acquisizione del MAG (XML in formato String)
	 * 
	 * @param magInternal versione acquisizione del MAG (XML in formato String)
	 */
	public void setMagInternal(String magInternal) {
		this.magInternal = magInternal;
	}
	
	/**
	 * Restituisce la versione esportazione del MAG (XML in formato String)
	 * 
	 * @return String versione esportazione del MAG (XML in formato String)
	 */
	public String getMagExternal() {
		return magExternal;
	}
	
	/**
	 * Imposta la versione esportazione del MAG (XML in formato String)
	 * 
	 * @param magExternal versione esportazione del MAG (XML in formato String)
	 */
	public void setMagExternal(String magExternal) {
		this.magExternal = magExternal;
	}
	
	/**
	 * Restituisce il valore del campo collection del MAG
	 * 
	 * @return String valore del campo collection del MAG
	 */
	public String getCollection() {
		return collection;
	}
	
	/**
	 * Imposta il valore del campo collection del MAG
	 * 
	 * @param collection valore del campo collection del MAG
	 */
	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	/**
	 * Restituisce il valore del campo agency del MAG
	 * 
	 * @return String valore del campo agency del MAG
	 */
	public String getAgency() {
		return agency;
	}
	
	/**
	 * Imposta il valore del campo agency del MAG
	 * 
	 * @param agency valore del campo agency del MAG
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	/**
	 * Restituisce la lista dei campi title del MAG
	 * 
	 * @return List<String> lista dei campi title del MAG
	 */
	public List<String> getTitles() {
		return titles;
	}
	
	/**
	 * Imposta la lista dei campi title del MAG
	 * 
	 * @param titles lista dei campi title del MAG
	 */
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	
	/**
	 * Restituisce il campo library del MAG
	 * 
	 * @return String campo library del MAG
	 */
	public List<String> getLibraries() {
		return libraries;
	}
	
	/**
	 * Imposta il campo library del MAG
	 * 
	 * @param library campo library del MAG
	 */
	public void setLibraries(List<String> libraries) {
		this.libraries = libraries;
	}

	/**
	 * Restituisce il campo inventory_number del MAG
	 * 
	 * @return String campo inventory_number del MAG
	 */
	public List<String> getInventoryNumbers() {
		return inventoryNumbers;
	}
	
	/**
	 * Imposta il campo inventory_number del MAG
	 * 
	 * @param library campo inventory_number del MAG
	 */
	public void setInventoryNumbers(List<String> inventoryNumbers) {
		this.inventoryNumbers = inventoryNumbers;
	}

	/**
	 * Restituisce il campo inventory_number del MAG
	 * 
	 * @return String campo inventory_number del MAG
	 */
	public List<String> getShelfmarks() {
		return shelfmarks;
	}
	
	/**
	 * Imposta il campo inventory_number del MAG
	 * 
	 * @param library campo inventory_number del MAG
	 */
	public void setShelfmarks(List<String> shelfmarks) {
		this.shelfmarks = shelfmarks;
	}
	
	/**
	 * Restituisce la lista dei campi contributor del MAG
	 * 
	 * @return List<String> lista dei campi contributor del MAG
	 */
	public List<String> getContributors() {
		return contributors;
	}
	
	/**
	 * Imposta la lista dei campi contributor del MAG
	 * 
	 * @param contributors lista dei campi contributor del MAG
	 */
	public void setContributors(List<String> contributors) {
		this.contributors = contributors;
	}
	
	/**
	 * Restituisce il campo level del MAG
	 * 
	 * @return String campo level del MAG
	 */
	public String getLevel() {
		return level;
	}
	
	/**
	 * Imposta il campo level del MAG
	 * 
	 * @param type campo level del MAG
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	
	/**
	 * Restituisce il campo creation del MAG
	 * 
	 * @return String campo creation del MAG
	 */
	public String getCreation() {
		return creation;
	}
	
	/**
	 * Imposta il campo creation del MAG
	 * 
	 * @param creation campo creation del MAG
	 */
	public void setCreation(String creation) {
		this.creation = creation;
	}

	/**
	 * Restituisce il campo last_update del MAG
	 * 
	 * @return String campo last_update del MAG
	 */
	public String getLastUpdate() {
		return lastUpdate;
	}
	
	/**
	 * Imposta il campo last_update del MAG
	 * 
	 * @param creation campo last_update del MAG
	 */
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	/**
	 * Restituisce l'ID di indicizzazione del MAG
	 * 
	 * @return String ID di indicizzazione del MAG
	 */
	public String getIdMag() {
		return idMag;
	}
	
	/**
	 * Imposta l'ID di indicizzazione del MAG
	 * 
	 * @param idMag ID di indicizzazione del MAG
	 */
	public void setIdMag(String idMag) {
		this.idMag = idMag;
	}

	/**
	 * Restituisce il campo stpiece_per per MAG di tipo seriali
	 * 
	 * @return String campo stpiece_per per MAG di tipo seriali
	 */
	public String getStpiecePer() {
		return stpiecePer;
	}
	
	/**
	 * Imposta il campo stpiece_per per MAG di tipo seriali
	 * 
	 * @param year campo stpiece_per per MAG di tipo seriali
	 */
	public void setStpiecePer(String stpiecePer) {
		this.stpiecePer = stpiecePer;
	}
	
	/**
	 * Restituisce il campo part_number per MAG di tipo seriali
	 * 
	 * @return String campo part_number per MAG di tipo seriali
	 */
	public String getPartNumber() {
		return partNumber;
	}
	
	/**
	 * Imposta il campo part_number per MAG di tipo seriali
	 * 
	 * @param issue campo part_number per MAG di tipo seriali
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * Restituisce il campo stpiece_vol per MAG di tipo seriali
	 * 
	 * @return String campo stpiece_vol per MAG di tipo seriali
	 */
	public String getStpieceVol() {
		return stpieceVol;
	}
	
	/**
	 * Imposta il campo stpiece_vol per MAG di tipo seriali
	 * 
	 * @param year campo stpiece_vol per MAG di tipo seriali
	 */
	public void setStpieceVol(String stpieceVol) {
		this.stpieceVol = stpieceVol;
	}
	
	/**
	 * Restituisce il campo part_name per MAG di tipo seriali
	 * 
	 * @return String campo part_name per MAG di tipo seriali
	 */
	public String getPartName() {
		return partName;
	}
	
	/**
	 * Imposta il campo part_name per MAG di tipo seriali
	 * 
	 * @param issue campo part_name per MAG di tipo seriali
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	/**
	 * Restituisce gli usage di acquisizione
	 * 
	 * @return String usage di acquisizione
	 */
	public List<String> getUsageA() {
		return usageA;
	}
	
	/**
	 * Imposta gli usage di acquisizione
	 * 
	 * @param usageA usage di acquisizione
	 */
	public void setUsageA(List<String> usageA) {
		this.usageA = usageA;
	}
	
	/**
	 * Restituisce gli usage di esportazione
	 * 
	 * @return String usage di esportazione
	 */
	public List<String> getUsageE() {
		return usageE;
	}
	
	/**
	 * Imposta gli usage di esportazione
	 * 
	 * @param usageE usage di esportazione
	 */
	public void setUsageE(List<String> usageE) {
		this.usageE = usageE;
	}
	
	/**
	 * Restituisce il flag di pubblicazione MAG
	 * 
	 * @return int flag di pubblicazione MAG
	 */
	public int getPublishFlag() {
		return publishFlag;
	}
	
	/**
	 * Imposta il flag di pubblicazione MAG
	 * 
	 * @param publishFlag flag di pubblicazione MAG
	 */
	public void setPublishFlag(int publishFlag) {
		this.publishFlag = publishFlag;
	}
	
	/**
	 * Restituisce il campo stprog del MAG
	 * 
	 * @return String campo stprog del MAG
	 */
	public String getStprog() {
		return stprog;
	}
	
	/**
	 * Imposta il campo stprog del MAG
	 * 
	 * @param stprog campo stprog del MAG
	 */
	public void setStprog(String stprog) {
		this.stprog = stprog;
	}
	
	/**
	 * Restituisce l'ID del job di importazione
	 * 
	 * @return String ID del job di importazione
	 */
	public String getIdJob() {
		return idJob;
	}
	
	/**
	 * Imposta l'ID del job di importazione
	 * 
	 * @param idJob ID del job di importazione
	 */
	public void setIdJob(String idJob) {
		this.idJob = idJob;
	}
	
	/**
	 * Restituisce la versione completa del MAG (XML in formato String)
	 * 
	 * @return String versione completa del MAG (XML in formato String)
	 */
	public String getMagTot() {
		return magTot;
	}
	
	/**
	 * Imposta la versione completa del MAG (XML in formato String)
	 * 
	 * @param magTot versione completa del MAG (XML in formato String)
	 */
	public void setMagTot(String magTot) {
		this.magTot = magTot;
	}

	/**
	 * Restituisce il path originale del MAG
	 * 
	 * @return String path originale del MAG
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Imposta il path originale del MAG
	 * 
	 * @param path path originale del MAG
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Restituisce i diritti di accesso al MAG
	 * 
	 * @return int diritti di accesso
	 */
	public int getAccessRights() {
		return accessRights;
	}
	
	/**
	 * Imposta i dirittti di accesso al MAG
	 * 
	 * @param accessRights diritti di accesso
	 */
	public void setAccessRights(int accessRights) {
		this.accessRights = accessRights;
	}


	/**
	 * Restituisce i diritti di accesso al MAG
	 * 
	 * @return int diritti di accesso
	 */
	public int getCompleteness() {
		return completeness;
	}
	
	/**
	 * Imposta i dirittti di accesso al MAG
	 * 
	 * @param accessRights diritti di accesso
	 */
	public void setCompleteness(int completeness) {
		this.completeness = completeness;
	}
	
	/**
	 * Restituisce le risorse digitali da importare
	 * 
	 * @return List<MagResource> risorse digitali da importare
	 */
	public List<DeliveryResource> getDigitalResources() {
		return digitalResources;
	}
	
	/**
	 * Imposta le informazioni ed i metadati sulle risorse digitali del MAG
	 * 
	 * @param resourceInfo informazioni ed i metadati sulle risorse digitali del MAG
	 */
	public void setDigitalResources(List<DeliveryResource> digitalResources) {
		this.digitalResources = digitalResources;
	}

	/**
	 * Restituisce la lista dei campi creator del MAG
	 * 
	 * @return List<String> lista dei campi creator del MAG
	 */
	public List<String> getCreators() {
		return creators;
	}
	
	/**
	 * Imposta la lista dei campi creator del MAG
	 * 
	 * @param creators lista dei campi creator del MAG
	 */
	public void setCreators(List<String> creators) {
		this.creators = creators;
	}

	/**
	 * Restituisce la lista dei campi publisher del MAG
	 * 
	 * @return List<String> lista dei campi publisher del MAG
	 */
	public List<String> getPublishers() {
		return publishers;
	}
	
	/**
	 * Imposta la lista dei campi publisher del MAG
	 * 
	 * @param publishers lista dei campi publisher del MAG
	 */
	public void setPublishers(List<String> publishers) {
		this.publishers = publishers;
	}

	/**
	 * Restituisce la lista dei campi subject del MAG
	 * 
	 * @return List<String> lista dei campi subject del MAG
	 */
	public List<String> getSubjects() {
		return subjects;
	}
	
	/**
	 * Imposta la lista dei campi subject del MAG
	 * 
	 * @param subjects lista dei campi subject del MAG
	 */
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
	
	/**
	 * Restituisce la lista dei campi description del MAG
	 * 
	 * @return List<String> lista dei campi description del MAG
	 */
	public List<String> getDescriptions() {
		return descriptions;
	}
	
	/**
	 * Imposta la lista dei campi description del MAG
	 * 
	 * @param description lista dei campi description del MAG
	 */
	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}
	
	/**
	 * Restituisce la lista dei campi date del MAG
	 * 
	 * @return List<String> lista dei campi date del MAG
	 */
	public List<String> getDates() {
		return dates;
	}
	
	/**
	 * Imposta la lista dei campi date del MAG
	 * 
	 * @param dates lista dei campi date del MAG
	 */
	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	/**
	 * Restituisce la lista dei campi type del MAG
	 * 
	 * @return List<String> lista dei campi type del MAG
	 */
	public List<String> getTypes() {
		return types;
	}
	
	/**
	 * Imposta la lista dei campi type del MAG
	 * 
	 * @param dcTypes lista dei campi type del MAG
	 */
	public void setTypes(List<String> types) {
		this.types = types;
	}

	/**
	 * Restituisce la lista dei campi format del MAG
	 * 
	 * @return List<String> lista dei campi format del MAG
	 */
	public List<String> getFormats() {
		return formats;
	}
	
	/**
	 * Imposta la lista dei campi format del MAG
	 * 
	 * @param formats lista dei campi format del MAG
	 */
	public void setFormats(List<String> formats) {
		this.formats = formats;
	}
	
	/**
	 * Restituisce la lista dei campi source del MAG
	 * 
	 * @return List<String> lista dei campi source del MAG
	 */
	public List<String> getSources() {
		return sources;
	}
	
	/**
	 * Imposta la lista dei campi source del MAG
	 * 
	 * @param dcSources lista dei campi source del MAG
	 */
	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	/**
	 * Restituisce la lista dei campi language del MAG
	 * 
	 * @return List<String> lista dei campi language del MAG
	 */
	public List<String> getLanguages() {
		return languages;
	}
	
	/**
	 * Imposta la lista dei campi language del MAG
	 * 
	 * @param languages lista dei campi language del MAG
	 */
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	
	/**
	 * Restituisce la lista dei campi relation del MAG
	 * 
	 * @return List<String> lista dei campi relation del MAG
	 */
	public List<String> getRelations() {
		return relations;
	}
	
	/**
	 * Imposta la lista dei campi relation del MAG
	 * 
	 * @param relations lista dei campi relation del MAG
	 */
	public void setRelations(List<String> relations) {
		this.relations = relations;
	}
	
	/**
	 * Restituisce la lista dei campi coverage del MAG
	 * 
	 * @return List<String> lista dei campi coverage del MAG
	 */
	public List<String> getCoverages() {
		return coverages;
	}
	
	/**
	 * Imposta la lista dei campi coverage del MAG
	 * 
	 * @param coverages lista dei campi coverage del MAG
	 */
	public void setCoverages(List<String> coverages) {
		this.coverages = coverages;
	}

	/**
	 * Restituisce la lista dei campi rights del MAG
	 * 
	 * @return List<String> lista dei campi rights del MAG
	 */
	public List<String> getRights() {
		return rights;
	}
	
	/**
	 * Imposta la lista dei campi rights del MAG
	 * 
	 * @param dcRights lista dei campi rights del MAG
	 */
	public void setRights(List<String> rights) {
		this.rights = rights;
	}
	
	/**
	 * Restituisce la lista dei campi geo_coord del MAG
	 * 
	 * @return List<String> lista dei campi geo_coord del MAG
	 */
	public List<String> getGeoCoord() {
		return geoCoord;
	}
	
	/**
	 * Imposta la lista dei campi geo_coord del MAG
	 * 
	 * @param geoCoord lista dei campi geo_coord del MAG
	 */
	public void setGeoCoord(List<String> geoCoord) {
		this.geoCoord = geoCoord;
	}

	/**
	 * Restituisce la lista dei campi not_date del MAG
	 * 
	 * @return List<String> lista dei not_date del MAG
	 */
	public List<String> getNotDate() {
		return notDate;
	}
	
	/**
	 * Imposta la lista dei campi not_date del MAG
	 * 
	 * @param notDate lista dei campi not_date del MAG
	 */
	public void setNotDate(List<String> notDate) {
		this.notDate = notDate;
	}

	/**
	 * Restituisce la lista dei campi nomenclature per la STRU del MAG
	 * 
	 * @return List<String> lista dei nomenclature per la STRU del MAG
	 */
	public List<String> getStruNomenclatures() {
		return struNomenclatures;
	}
	
	/**
	 * Imposta la lista dei campi nomenclature per la STRU del MAG
	 * 
	 * @param geoCoord lista dei campi nomenclature per la STRU del MAG
	 */
	public void setStruNomenclatures(List<String> struNomenclatures) {
		this.struNomenclatures = struNomenclatures;
	}

	/**
	 * Restituisce la lista dei campi nomenclature per la ELEMENT del MAG
	 * 
	 * @return List<String> lista dei nomenclature per la ELEMENT del MAG
	 */
	public List<String> getElementNomenclatures() {
		return elementNomenclatures;
	}
	
	/**
	 * Imposta la lista dei campi nomenclature per la ELEMENT del MAG
	 * 
	 * @param geoCoord lista dei campi nomenclature per la ELEMENT del MAG
	 */
	public void setElementNomenclatures(List<String> elementNomenclatures) {
		this.elementNomenclatures = elementNomenclatures;
	}

	/**
	 * Restituisce il nome del progetto MAG di riferimento
	 * 
	 * @return String nome del progetto MAG
	 */
	public String getProject() {
		return project;
	}
	
	/**
	 * Imposta il nome del progetto MAG di riferimento
	 * 
	 * @param project nome del progetto MAG
	 */
	public void setProject(String project) {
		this.project = project;
	}
	
	/**
	 * Restituisce l'identifier OAI del MAG di riferimento
	 * 
	 * @return String l'identifier OAI del MAG
	 */
	public String getOaiIdentifier() {
		return oaiIdentifier;
	}
	
	/**
	 * Imposta l'identifier OAI del MAG di riferimento
	 * 
	 * @param oaiIdentifier l'identifier OAI del MAG
	 */
	public void setOaiIdentifier(String oaiIdentifier) {
		this.oaiIdentifier = oaiIdentifier;
	}
	
	/**
	 * Restituisce true se il MAG è stato cancellato
	 * 
	 * @return true se il MAG è stato cancellato
	 */
	public boolean isDeleted() {
		return deleted;
	}
	
	/**
	 * Imposta il MAG cancellato o attivo
	 * 
	 * @param deleted cancellato o attivo
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Restituisce true se il MAG è una bozza (editor)
	 * 
	 * @return true se il MAG è una bozza (editor)
	 */
	public boolean isDraft() {
		return draft;
	}
	
	/**
	 * Imposta true se è di tipo bozza
	 * 
	 * @param draft true se è di tipo bozza
	 */
	public void setDraft(boolean draft) {
		this.draft = draft;
	}
	
	/**
	 * Restituisce il timestamp di ultima modifica MAG
	 * 
	 * @return Date timestamp di ultima modifica MAG
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Imposta il timestamp di ultima modifica MAG
	 * 
	 * @param timestamp timestamp di ultima modifica MAG
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Restituisce il timestamp di creazione MAG
	 * 
	 * @return Date timestamp di creazione MAG
	 */
	public Date getTimestampCreated() {
		return timestampCreated;
	}
	
	/**
	 * Imposta il timestamp di creazione MAG
	 * 
	 * @param timestamp timestamp di creazione MAG
	 */
	public void setTimestampCreated(Date timestampCreated) {
		this.timestampCreated = timestampCreated;
	}

	/**
	 * Restituisce il timestamp di cancellazione MAG
	 * 
	 * @return Date timestamp di cancellazione MAG
	 */
	public Date getTimestampDeleted() {
		return timestampDeleted;
	}
	
	/**
	 * Imposta il timestamp di cancellazione MAG
	 * 
	 * @param timestamp timestamp di cancellazione MAG
	 */
	public void setTimestampDeleted(Date timestampDeleted) {
		this.timestampDeleted = timestampDeleted;
	}

	/**
	 * Restituisce la lista dei tipi digitali
	 * 
	 * @return List<String> lista dei tipi digitali
	 */
	public List<String> getDigitalTypes() {
		return digitalTypes;
	}
	
	/**
	 * Imposta i tipi digitali
	 * 
	 * @param digitalTypes tipi digitali
	 */
	public void setDigitalTypes(List<String> digitalTypes) {
		this.digitalTypes = digitalTypes;
	}

	/**
	 * Restituisce la lista dei MIME
	 * 
	 * @return List<String> lista dei MIME
	 */
	public List<String> getMimeTypes() {
		return mimeTypes;
	}
	
	/**
	 * Imposta i MIME
	 * 
	 * @param mimeTypes MIME
	 */
	public void setMimeTypes(List<String> mimeTypes) {
		this.mimeTypes = mimeTypes;
	}

	/**
	 * Restituisce il numero delle risorse di tipo immagine (IMG)
	 * 
	 * @return int numero delle risorse di tipo immagine (IMG)
	 */
	public int getNumberImg() {
		return numberImg;
	}

	/**
	 * Imposta il numero delle risorse di tipo immagine (IMG)
	 * 
	 * @param numberImg numero delle risorse di tipo immagine (IMG)
	 */
	public void setNumberImg(int numberImg) {
		this.numberImg = numberImg;
	}

	/**
	 * Restituisce il numero delle risorse di tipo video (VIDEO)
	 * 
	 * @return int numero delle risorse di tipo video (VIDEO)
	 */
	public int getNumberVideo() {
		return numberVideo;
	}

	/**
	 * Imposta il numero delle risorse di tipo video (VIDEO)
	 * 
	 * @param numberVideo numero delle risorse di tipo video (VIDEO)
	 */
	public void setNumberVideo(int numberVideo) {
		this.numberVideo = numberVideo;
	}

	/**
	 * Restituisce il numero delle risorse di tipo audio (AUDIO)
	 * 
	 * @return int numero delle risorse di tipo audio (AUDIO)
	 */
	public int getNumberAudio() {
		return numberAudio;
	}

	/**
	 * Imposta il numero delle risorse di tipo audio (AUDIO)
	 * 
	 * @param numberAudio numero delle risorse di tipo audio (AUDIO)
	 */
	public void setNumberAudio(int numberAudio) {
		this.numberAudio = numberAudio;
	}

	/**
	 * Restituisce il numero delle risorse di tipo ocr (OCR)
	 * 
	 * @return int numero delle risorse di tipo ocr (OCR)
	 */
	public int getNumberOcr() {
		return numberOcr;
	}

	/**
	 * Imposta il numero delle risorse di tipo ocr (OCR)
	 * 
	 * @param numberOcr numero delle risorse di tipo ocr (OCR)
	 */
	public void setNumberOcr(int numberOcr) {
		this.numberOcr = numberOcr;
	}

	/**
	 * Restituisce il numero delle risorse di tipo documento (DOC)
	 * 
	 * @return int numero delle risorse di tipo documento (DOC)
	 */
	public int getNumberDoc() {
		return numberDoc;
	}

	/**
	 * Imposta il numero delle risorse di tipo documento (DOC)
	 * 
	 * @param numberDoc numero delle risorse di tipo documento (DOC)
	 */
	public void setNumberDoc(int numberDoc) {
		this.numberDoc = numberDoc;
	}

	/**
	 * Restituisce l'URL di preview del MAG
	 * 
	 * @return String URL di preview del MAG
	 */
	public String getMagPreview() {
		return magPreview;
	}
	
	public void setMagPreview(String magPreview) {
		this.magPreview = magPreview;
	}
	
	/**
	 * Restituisce il tipo di formato del documento (MAG, METS)
	 * 
	 * @return String tipo di formato del documento (MAG, METS)
	 */
	public String getDocumentFormat() {
		return documentFormat;
	}
	
	/**
	 * Imposta il tipo di formato del documento (MAG, METS)
	 * 
	 * @param documentFormat tipo di formato del documento (MAG, METS)
	 */
	public void setDocumentFormat(String documentFormat) {
		this.documentFormat = documentFormat;
	}

	public String getMetsOriginal() {
		return metsOriginal;
	}

	public void setMetsOriginal(String metsOriginal) {
		this.metsOriginal = metsOriginal;
	}

	public String getMetsExternal() {
		return metsExternal;
	}

	public void setMetsExternal(String metsExternal) {
		this.metsExternal = metsExternal;
	}
}
