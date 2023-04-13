package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * JSON di risposta servizio di pubblicazione MAG
 * 
 *
 */
public class JsonPublicationResult implements Serializable {
	private static final long serialVersionUID = -8915149928303931376L;
	
	// status
	@JsonProperty("stato")
	private int status;
	
	// messaggio
	@JsonProperty("messaggio")
	private String message;
	
	// lista mag pubblicati
	@JsonProperty("mag")
	private List<JsonPublicationMagResult> mags = new ArrayList<JsonPublicationMagResult>();
	
	
	
	/**
	 * Restituisce lo status finale del processo di pubblicazione
	 * 
	 * @return int status finale del processo di pubblicazione
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Imposta lo status finale del processo di pubblicazione
	 * 
	 * @param status status finale del processo di pubblicazione
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Restituisce il messaggio di descrizione dello status del processo di pubblicazione
	 * 
	 * @return messaggio di descrizione dello status del processo di pubblicazione
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Imposta il messaggio di descrizione dello status del processo di pubblicazione
	 * 
	 * @param message messaggio di descrizione dello status del processo di pubblicazione
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Restituisce la lista dei MAG con resoconto risultato
	 * 
	 * @return List<JsonPublicationMagResult> lista dei MAG
	 */
	public List<JsonPublicationMagResult> getMags() {
		return mags;
	}
	
	/**
	 * Imposta la lista dei MAG con resoconto risultato
	 * 
	 * @param mags lista dei MAG
	 */
	public void setMags(List<JsonPublicationMagResult> mags) {
		this.mags = mags;
	}

}
