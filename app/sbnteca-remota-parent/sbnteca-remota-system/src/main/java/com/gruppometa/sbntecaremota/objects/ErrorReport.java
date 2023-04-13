package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;

/**
 * Oggetto di riepilogo errore di validazione
 * 
 *
 */
public class ErrorReport implements Serializable {
	private static final long serialVersionUID = -6327089230193055474L;
	
	// tipo
	private String type;
	
	// messaggio
	private String message;
	
	
	
	/**
	 * Restituisce il tipo di segnalazione (WARNING, ERROR, FATAL_ERROR, WAIT)
	 * 
	 * @return String tipo di segnalazione (WARNING, ERROR, FATAL_ERROR, WAIT)
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Imposta il tipo di segnalazione (WARNING, ERROR, FATAL_ERROR, WAIT)
	 * 
	 * @param type tipo di segnalazione (WARNING, ERROR, FATAL_ERROR, WAIT)
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Restituisce il messaggio di descrizione dell'errore
	 * 
	 * @return String messaggio di descrizione dell'errore
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Imposta il messaggio di descrizione dell'errore
	 * 
	 * @param message messaggio di descrizione dell'errore
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
