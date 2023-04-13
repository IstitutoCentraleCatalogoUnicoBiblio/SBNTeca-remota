package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.Properties;

public class ImportMagConfiguration implements Serializable {
	private static final long serialVersionUID = -2467148658211095645L;
	
	// path
	private String path;
	
	// flag vestizione
	private int dressFlag;
	
	// flag pubblicazione
	private int publicFlag;

	public boolean isChangeUsage() {
		return isChangeUsage;
	}

	public void setChangeUsage(boolean changeUsage) {
		isChangeUsage = changeUsage;
	}

	// configurazione
	private Properties configuration;


	private boolean isChangeUsage = false;
	
	/**
	 * Restituisce il path assoluto del MAG da importare/validare
	 * 
	 * @return path assoluto del MAG da importare/validare
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Imposta il path assoluto del MAG da importare/validare
	 * 
	 * @param path path assoluto del MAG da importare/validare
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Restituisce il flag di vestizione del MAG
	 * 
	 * @return flag di vestizione
	 */
	public int getDressFlag() {
		return dressFlag;
	}
	
	/**
	 * Imposta il flag di vestizione del MAG
	 * 
	 * @param dressFlag flag di vestizione del MAG
	 */
	public void setDressFlag(int dressFlag) {
		this.dressFlag = dressFlag;
	}
	
	/**
	 * Restituisce il flag di pubblicazione del MAG
	 * 
	 * @return flag di pubblicazione del MAG
	 */
	public int getPublicFlag() {
		return publicFlag;
	}
	
	/**
	 * Imposta il flag di pubblicazione del MAG
	 * 
	 * @param publicFlag flag di pubblicazione del MAG
	 */
	public void setPublicFlag(int publicFlag) {
		this.publicFlag = publicFlag;
	}
	
	/**
	 * Restituisce la configurazione del MAG
	 * 
	 * @return configurazione del MAG
	 */
	public Properties getConfiguration() {
		return configuration;
	}
	
	/**
	 * Imposta la configurazione del MAG
	 * 
	 * @param configuration configurazione del MAG
	 */
	public void setConfiguration(Properties configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ImportMagConfiguration))
			return false;
		
		ImportMagConfiguration other = (ImportMagConfiguration) obj;
		return this.path.equals(other.path);
	}

}
