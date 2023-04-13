package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Oggetto di riepilogo della validazione di uno specifico MAG
 * 
 *
 */
public class MagReport implements Serializable {
	private static final long serialVersionUID = -8765303703913843081L;
	
	public static final String OK = "OK";
	public static final String KO = "KO";
	
	// path
	private String path;
	
	// esito
	private String result;
	private String documentFormat;

	public String getDocumentFormat() {
		return documentFormat;
	}

	public void setDocumentFormat(String documentFormat) {
		this.documentFormat = documentFormat;
	}

	// timestamp
	private String timestamp;
	
	// lista errori
	private List<ErrorReport> errors = new ArrayList<ErrorReport>();
	
	// status message
	private String statusMessage;
	
	
	
	/**
	 * Restituisce il path del MAG validato
	 *
	 * @return String path del MAG validato
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Imposta il path del MAG validato
	 * 
	 * @param path path del MAG validato
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Restituisce il risultato della validazione (OK/KO)
	 * 
	 * @return String risultato della validazione (OK/KO)
	 */
	public String getResult() {
		return result;
	}
	
	/**
	 * Imposta il risultato della validazione (OK/KO)
	 * 
	 * @param result risultato della validazione (OK/KO)
	 */
	public void setResult(String result) {
		this.result = result;
	}
	
	/**
	 * Restituisce il timestamp di validazione del MAG
	 * 
	 * @return String timestamp di validazione del MAG
	 */
	public String getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Imposta il timestamp di validazione del MAG
	 * 
	 * @param timestamp timestamp di validazione del MAG
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Restituisce la lista di riepilogo degli errori di validazione
	 * 
	 * @return List<ErrorReport> lista di riepilogo degli errori di validazione
	 */
	public List<ErrorReport> getErrors() {
		return errors;
	}
	
	/**
	 * Imposta la lista di riepilogo degli errori di validazione
	 * 
	 * @param errors lista di riepilogo degli errori di validazione
	 */
	public void setErrors(List<ErrorReport> errors) {
		this.errors = errors;
	}

	/**
	 * Restituisce un messaggio di status al termine delle validazione sul MAG
	 * 
	 * @return String statusMessage messaggio di status finale sul MAG
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	
	/**
	 * Imposta il messaggio di status al termine della validazione del MAG
	 * 
	 * @param statusMessage messaggio di status del MAG
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
