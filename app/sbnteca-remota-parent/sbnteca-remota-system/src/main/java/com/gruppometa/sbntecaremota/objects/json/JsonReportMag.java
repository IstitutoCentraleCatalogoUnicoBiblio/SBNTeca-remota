package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON di riepilogo validazione su singolo MAG
 * 
 *
 */
public class JsonReportMag implements Serializable {
	private static final long serialVersionUID = -3319223233291417595L;
	
	public static final String OK = "OK";
	public static final String KO = "KO";
	
	// mag
	private String mag;
	
	// esito
	@JsonProperty("esito")
	private String result;
	
	
	/**
	 * Restituisce il path del MAG
	 * 
	 * @return String path del MAG
	 */
	public String getMag() {
		return mag;
	}
	
	/**
	 * Imposta il path del MAG
	 * 
	 * @param mag path del MAG
	 */
	public void setMag(String mag) {
		this.mag = mag;
	}
	
	/**
	 * Restituisce il risultato di validazione (OK/KO)
	 * 
	 * @return risultato di validazione (OK/KO)
	 */
	public String getResult() {
		return result;
	}
	
	/**
	 * Imposta il risultato di validazione (OK/KO)
	 * 
	 * @param result risultato di validazione (OK/KO)
	 */
	public void setResult(String result) {
		this.result = result;
	}

}
