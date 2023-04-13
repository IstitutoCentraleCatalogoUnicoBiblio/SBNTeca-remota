package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;


public class JsonDeleteProject implements Serializable {
	private static final long serialVersionUID = -5818605872139094405L;

	// status
	@JsonProperty("stato")
	private int status;

	// errore
	@JsonProperty("messaggio")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String message = "";
	
	

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

}
