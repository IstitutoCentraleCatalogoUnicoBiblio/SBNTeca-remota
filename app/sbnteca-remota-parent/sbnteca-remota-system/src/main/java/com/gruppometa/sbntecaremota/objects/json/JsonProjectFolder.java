package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

/**
 * JSON di restituzione dati progetto Magteca caricato
 * 
 *
 */
public class JsonProjectFolder implements Serializable {
	private static final long serialVersionUID = -6302874380434346842L;
	
	// nome cartella
	private String folderName;
	
	// path
	private String path;
	
	
	/**
	 * Restituisce il nome del progetto
	 * 
	 * @return String nome del progetto
	 */
	public String getFolderName() {
		return folderName;
	}
	
	/**
	 * Imposta il nome del progetto
	 * 
	 * @param folderName nome del progetto
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	/**
	 * Restituisce il path del progetto Magteca
	 * 
	 * @return String path del progetto Magteca
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Imposta il path del progetto Magteca
	 * 
	 * @param path path del progetto Magteca
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
