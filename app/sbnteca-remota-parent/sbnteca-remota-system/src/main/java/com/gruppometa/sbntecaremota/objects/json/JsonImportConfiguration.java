package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;


/**
 * JSON di richiesta servizo di importazione/validazione/aggiornamento/campio usage
 * 
 *
 */
public class JsonImportConfiguration {

	//private String inputDirectory;
	@JsonProperty("flag_pubblica")
	private Integer pubblica = null; //indica se le risorse devono essere pubblicate 1 o meno 0
	
	@JsonProperty("vestizione_mag")
	private Integer dressMag = null;

	@JsonProperty("flag_sovrascrittura")
	private int overwrite = 1;
	
	@JsonProperty("flag_update_mag")
	private int importUpdate = 0;
	
	@JsonProperty("sorgente")
	private String source;//indica il tipo di mag i valori possibili sono file, http, ftp, db
	
	@JsonProperty("tipo")
	private String type;//indica il tipo di mag i valori possibili sono file, http, ftp, db
	
	@JsonProperty("usage_acquisizione")
	private List<String> usageA = new ArrayList<String>();//indica i valori degli usage di acquisizione
	
	@JsonProperty("usage_esportazione")
	private List<String> usageE = new ArrayList<String>();//indica i valore degli usage di esportazione
	
	@JsonProperty("progetti")
	private List<JsonImportProject> projects = new ArrayList<JsonImportProject>();
	
	@JsonProperty("utente")
	private int userID;
	
	@JsonProperty("configurazione")
	private Map<String, String> configuration = new HashMap<String, String>();

	
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
	 * Restituisce gli usage di esportazione (separati da whitespace ' ')
	 * 
	 * @return String usage di esportazione (separati da whitespace ' ')
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
	 * Restituisce il flag che indica se si tratta di un aggiornamento progetto
	 * 
	 * @return int flag di aggiornamento progetto
	 */
	public int getImportUpdate() {
		return importUpdate;
	}
	
	/**
	 * Imposta il flag che indica se si tratta di un aggiornamento progetto
	 * 
	 * @param flag di aggiornamento progetto
	 */
	public void setImportUpdate(int importUpdate) {
		this.importUpdate = importUpdate;
	}

	/**
	 * Restituisce la sorgente per il MAG (file system, http, ftp, db)
	 * 
	 * @return String sorgente per il MAG (file system, http, ftp, db)
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * Imposta la sorgente per il MAG (file system, http, ftp, db)
	 * 
	 * @param typeMag sorgente per il MAG (file system, http, ftp, db)
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * Restituisce la lista dei progetti da importare/validare
	 * 
	 * @return lista dei progetti da importare/validare
	 */
	public List<JsonImportProject> getProjects() {
		return projects;
	}
	
	/**
	 * Imposta la lista dei progetti da importare/validare
	 * 
	 * @param projects lista dei progetti da importare/validare
	 */
	public void setProjects(List<JsonImportProject> projects) {
		this.projects = projects;
	}
	
	/**
	 * Restituisce il flag di pubblicazione
	 * 
	 * @return int flag di pubblicazione
	 */
	public Integer getPubblica() {
		return pubblica;
	}
	
	/**
	 * Imposta il flag di pubblicazione
	 * 
	 * @param pubblica flag di pubblicazione
	 */
	public void setPubblica(Integer pubblica) {
		this.pubblica = pubblica;
	}
	
	/**
	 * Restituisce la mappa di configurazione custom per il lancio del processo di importazione/validazione
	 * 
	 * @return Map<String, String> mappa di configurazione custom per il lancio del processo di importazione/validazione
	 */
	public Map<String, String> getConfiguration() {
		return configuration;
	}
	
	/**
	 * Imposta la mappa di configurazione custom per il lancio del processo di importazione/validazione
	 * 
	 * @param configuration mappa di configurazione custom per il lancio del processo di importazione/validazione
	 */
	public void setConfiguration(Map<String, String> configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * Restituisce l'utente
	 * 
	 * @return int utente
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Imposta l'utente
	 * 
	 * @param userID utente
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * Restituisce il flag di vestizione MAG 
	 * 
	 * @return Integer flag di vestizione MAG 
	 */
	public Integer getDressMag() {
		return dressMag;
	}
	
	/**
	 * Imposta il flag di vestizione MAG 
	 * 
	 * @param dressMag flag di vestizione MAG 
	 */
	public void setDressMag(Integer dressMag) {
		this.dressMag = dressMag;
	}

	/**
	 * Restituisce il flag di sovrascrittura MAG (in caso di MAG esistente)
	 * 
	 * @return Integer flag di sovrascrittura MAG 
	 */
	public int getOverwrite() {
		return overwrite;
	}
	
	/**
	 * Imposta il flag di sovrascrittura MAG (in caso di MAG esistente)
	 * 
	 * @param dressMag flag di sovrascrittura MAG 
	 */
	public void setOverwrite(int overwrite) {
		this.overwrite = overwrite;
	}

	public Properties getConfigurationAsProperties() {
		Properties properties = new Properties();
		configuration.keySet().stream().forEach(key-> properties.put(key, configuration.get(key)));
		return properties;
	}
}
