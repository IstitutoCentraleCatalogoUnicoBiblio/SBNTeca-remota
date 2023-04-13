package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonImportProject implements Serializable {
	private static final long serialVersionUID = -26229536787395380L;
	
	@JsonProperty("nome")
	private String name;
	
	@JsonProperty("tutto")
	private boolean all = true;
	
	@JsonProperty("mag")
	private List<String> mags = new ArrayList<String>();
	
	
	
	/**
	 * Restituisce il nome del progetto
	 * 
	 * @return nome del progetto
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Imposta il nome del progetto
	 * 
	 * @param name nome del progetto
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Restituisce true se bisogna importare tutto il progetto
	 * 
	 * @return true/false
	 */
	public boolean isAll() {
		return all;
	}
	
	/**
	 * true/false se importare il progetto completo
	 * 
	 * @param all true/false
	 */
	public void setAll(boolean all) {
		this.all = all;
	}
	
	/**
	 * Restituisce la lista dei MAG (usato per importazioni parziali del progetto)
	 * 
	 * @return lista dei MAG
	 */
	public List<String> getMags() {
		return mags;
	}
	
	/**
	 * Imposta la lista dei MAG (usato per importazioni parziali del progetto)
	 * 
	 * @param mags lista dei MAG
	 */
	public void setMags(List<String> mags) {
		this.mags = mags;
	}

}
