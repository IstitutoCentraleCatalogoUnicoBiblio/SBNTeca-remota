package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


/**
 * JSON di richiesta servizio di pubblicazione
 * 
 *
 */
public class JsonPublicationRequest implements Serializable {
	private static final long serialVersionUID = 8593416488606382553L;
	
	// utente
	@JsonProperty("utente")
	private int user;
	
	// flag pubblicazione
	@JsonProperty("flag_pubblica")
	private int publicFlag;
	
	// ricerca avanzata
	@JsonProperty("ricerca_avanzata")
	private JsonSearchRequest searchRequest;
	
	
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
	 * Restituisce il flag di pubblicazione
	 * 
	 * @return int flag di pubblicazione
	 */
	public int getPublicFlag() {
		return publicFlag;
	}
	
	/**
	 * Imposta il flag di pubblicazione
	 * 
	 * @param publicFlag flag di pubblicazione
	 */
	public void setPublicFlag(int publicFlag) {
		this.publicFlag = publicFlag;
	}
	
	/**
	 * Restituisce il json contenente i dati della ricerca avanzata
	 * 
	 * @return List<JsonPublicationMag> dati della ricerca avanzata
	 */
	public JsonSearchRequest getSearchRequest() {
		return searchRequest;
	}
	
	/**
	 * Imposta il json contenente i dati della ricerca avanzata
	 * 
	 * @param searchRequest dati della ricerca avanzata
	 */
	public void setSearchRequest(JsonSearchRequest searchRequest) {
		this.searchRequest = searchRequest;
	}

}
