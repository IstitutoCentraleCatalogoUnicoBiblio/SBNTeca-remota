package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON di definizione MAG per servizio di pubblicazione
 * 
 *
 */
public class JsonPublicationMag implements Serializable {
	private static final long serialVersionUID = -1164094051159324582L;
	
	// id mag
	private String id;
	
	// flag pubblicazione
	@JsonProperty("flag_pubblica")
	private int publicFlag;
	
	
	/**
	 * Restituisce l'ID MAG
	 * 
	 * @return String ID MAG
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Imposta l'ID MAG
	 * 
	 * @param id ID MAG
	 */
	public void setId(String id) {
		this.id = id;
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

}
