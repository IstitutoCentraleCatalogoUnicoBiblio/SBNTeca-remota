package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportMagProject implements Serializable {
	private static final long serialVersionUID = 8730685381598749813L;

	// nome progetto
	private String name;
	
	// importa tutto progetto
	private boolean all = true;
	
	// digital objects
	private int totalDigitalObjects = 0;
	
	// lista dei MAG (solo se progetto importato parzialmente)
	private List<ImportMagConfiguration> mags = new ArrayList<ImportMagConfiguration>();
	
	
	
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
	 * Restituisce il numero totale di oggetti digitali per progetto
	 * 
	 * @return int numero totale di oggetti digitali per progetto
	 */
	public int getTotalDigitalObjects() {
		return totalDigitalObjects;
	}
	
	/**
	 * Imposta il numero totale di oggetti digitali per progetto
	 * 
	 * @param totalDigitalObjects numero totale di oggetti digitali per progetto
	 */
	public void setTotalDigitalObjects(int totalDigitalObjects) {
		this.totalDigitalObjects = totalDigitalObjects;
	}
	
	/**
	 * Restituisce la lista dei MAG (usato per importazioni parziali del progetto)
	 * 
	 * @return lista dei MAG
	 */
	public List<ImportMagConfiguration> getMags() {
		return mags;
	}
	
	/**
	 * Imposta la lista dei MAG (usato per importazioni parziali del progetto)
	 * 
	 * @param mags lista dei MAG
	 */
	public void setMags(List<ImportMagConfiguration> mags) {
		this.mags = mags;
	}

}
