package com.gruppometa.sbntecaremota.objects.validators;

import java.io.Serializable;

/**
 * Errore di validazione
 * 

 *
 */
public class ValidationError implements Serializable {
	private static final long serialVersionUID = -5595281340404609052L;
	
	public static final String FATAL_ERROR = "FATAL_ERROR";
	public static final String ERROR = "ERROR";
	public static final String WARNING = "WARNING";
	public static final String WAIT = "WAIT";
	public static final String OK = "OK";
	
	// stato
	private String status;
	
	// messaggio
	private String message = null;
	
	
	
	public ValidationError() {
		super();
	}
	
	public ValidationError(String status) {
		this();
		this.status = status;
	}
	
	public ValidationError(String status, String message) {
		this(status);
		this.message = message;
	}
	
	/**
	 * Restituisce il tipo di segnalazione (WARNING, ERROR, FATAL_ERROR, WAIT)
	 * 
	 * @return String tipo di segnalazione (WARNING, ERROR, FATAL_ERROR, WAIT)
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Imposta il tipo di segnalazione (WARNING, ERROR, FATAL_ERROR, WAIT)
	 * 
	 * @param status tipo di segnalazione (WARNING, ERROR, FATAL_ERROR, WAIT)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Restituisce il messaggio di errore
	 * 
	 * @return String messaggio di errore
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Imposta il messaggio di errore
	 * 
	 * @param message messaggio di errore
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
