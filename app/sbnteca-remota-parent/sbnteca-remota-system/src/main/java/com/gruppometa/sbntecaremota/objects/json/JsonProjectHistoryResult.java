/**
 * 
 */
package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonProjectHistoryResult implements Serializable {
	private static final long serialVersionUID = 3740744806160532400L;
	
	// status
	@JsonProperty("stato")
	private int status;

	// errore
	@JsonProperty("messaggio")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String message = "";
	
	// lista risultati
	@JsonProperty("cronologia")
	private List<JsonProjectHistory> history = new ArrayList<JsonProjectHistory>();
	
	

	/**
	 * Restituisce lo status del processo di upload
	 * 
	 * @return int status del processo di upload
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Imposta lo status del processo di upload
	 * 
	 * @param status status del processo di upload
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Restituisce il messaggio di descrizione dello status del processo di upload
	 * 
	 * @return String messaggio di descrizione dello status del processo di upload
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Imposta il messaggio di descrizione dello status del processo di upload
	 * 
	 * @param message messaggio di descrizione dello status del processo di upload
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Restituisce la cronologia del progetto
	 * 
	 * @return List<JsonProjectHistory> lista delle operazioni di cronologia
	 */
	public List<JsonProjectHistory> getHistory() {
		return history;
	}
	
	/**
	 * Imposta la cronologia del progetto
	 * 
	 * @param history lista delle operazioni di cronologia
	 */
	public void setHistory(List<JsonProjectHistory> history) {
		this.history = history;
	}

}
