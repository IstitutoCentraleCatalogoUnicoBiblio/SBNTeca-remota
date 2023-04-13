package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


/**
 * JSON di richiesta cancellazione
 * 
 *
 */
public class JsonDeleteRequest implements Serializable {
	private static final long serialVersionUID = -7938531006979589040L;
	
	// utente
	@JsonProperty("utente")
	private int user;
	
	// mags
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

}
