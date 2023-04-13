package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonExportRequest implements Serializable {
	private static final long serialVersionUID = -1524457273088131261L;

	// utente
	@JsonProperty("utente")
	private int user;
	
	// mags
	@JsonProperty("ricerca_avanzata")
	private JsonSearchRequest searchRequest;
	
	// estensione
	@JsonProperty("estensione")
	private String extension;
	
	// estensione
	@JsonProperty("nome_file")
	private String filename;

	@JsonProperty("format")
	private String format;

	@JsonProperty("usage")
	private String usage;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	// vestizione
	@JsonProperty("vestizione")
	private boolean dress;
	
	/**
	 * Restituisce l'utente
	 * 
	 * @return int utente
	 */
	public int getUser() {
		return user;
	}
	
	/**
	 * Imposta l'utente
	 * 
	 * @param user utente
	 */
	public void setUser(int user) {
		this.user = user;
	}
	
	/**
	 * Restituisce la lista degli ID MAG Solr da cancellare
	 * 
	 * @return List<String> lista degli ID MAG Solr da cancellare
 	 */
	public JsonSearchRequest getSearchRequest() {
		return searchRequest;
	}
	
	/**
	 * Imposta la lista degli ID MAG Solr da cancellare
	 * 
	 * @param mags lista degli ID MAG Solr da cancellare
	 */
	public void setSearchRequest(JsonSearchRequest searchRequest) {
		this.searchRequest = searchRequest;
	}
	
	/**
	 * Restituisce l'estensione del file compresso da creare
	 * 
	 * @return estensione del file compresso da creare
	 */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * Imposta l'estensione del file compresso da creare
	 * 
	 * @param extension estensione del file compresso da creare
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Restituisce il nome del file compresso da creare
	 * 
	 * @return String nome del file compresso da creare
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Imposta il nome del file compresso da creare
	 * 
	 * @param filename nome del file compresso da creare
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Restituisce true se i MAG devono essere vestiti
	 * 
	 * @return boolean true se i MAG devono essere vestiti
	 */
	public boolean getDress() {
		return dress;
	}
	
	/**
	 * Imposta la vestizione dei MAG
	 * 
	 * @param dress true se i MAG devono essere vestiti
	 */
	public void setDress(boolean dress) {
		this.dress = dress;
	}

}
