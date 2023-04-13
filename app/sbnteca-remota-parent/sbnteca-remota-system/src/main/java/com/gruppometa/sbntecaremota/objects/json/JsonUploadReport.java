package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * JSON di risposta servizio di status processo di upload
 * 
 *
 */
public class JsonUploadReport implements Serializable {
	private static final long serialVersionUID = -2945962242480425383L;
	
	// id processo
	@JsonProperty("id_processo")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String processID = null;
	
	// data inizio
	@JsonProperty("tempo_inizio")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String timestampStart = null;
	
	// data fine
	@JsonProperty("tempo_fine")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String timestampEnd = null;
	
	// status
	@JsonProperty("stato")
	private int status;
	
	// utente
	@JsonProperty("id_utente")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer userID = null;
	
	// errore
	@JsonProperty("messaggio")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String message = "";
	
	
	/**
	 * Restituisce l'ID del processo di upload
	 * 
	 * @return String ID del processo di upload
	 */
	public String getProcessID() {
		return processID;
	}
	
	/**
	 * Imposta l'ID del processo di upload
	 * 
	 * @param processID ID del processo di upload
	 */
	public void setProcessID(String processID) {
		this.processID = processID;
	}
	
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
	 * Restituisce l'utente
	 * 
	 * @return utente
	 */
	public Integer getUserID() {
		return userID;
	}
	
	/**
	 * Imposta l'utente
	 * 
	 * @param userID utente
	 */
	public void setUserID(Integer userID) {
		this.userID = userID;
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
	 * Restituisce il timestamp di fine processo di upload
	 * 
	 * @return String timestamp di fine processo di upload
	 */
	public String getTimestampEnd() {
		return this.timestampEnd;
	}

	/**
	 * Imposta il timestamp di fine processo di upload
	 * 
	 * @param timestampEnd timestamp di fine processo di upload
	 */
	public void setTimestampEnd(String timestampEnd) {
		this.timestampEnd = timestampEnd;
	}

	/**
	 * Restituisce il timestamp di inizio processo di upload
	 * 
	 * @return String timestamp di inizio processo di upload
	 */
	public String getTimestampStart() {
		return this.timestampStart;
	}

	/**
	 * Imposta il timestamp di inizio processo di upload
	 * 
	 * @param timestampStart timestamp di inizio processo di upload
	 */
	public void setTimestampStart(String timestampStart) {
		this.timestampStart = timestampStart;
	}

}
