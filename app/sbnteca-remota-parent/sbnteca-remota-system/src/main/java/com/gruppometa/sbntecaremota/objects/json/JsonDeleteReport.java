package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON di risposta servizio di cancellazione
 * 
 *
 */
public class JsonDeleteReport implements Serializable {
	private static final long serialVersionUID = -229896593233907973L;
	
	// numero mag elaborati
	@JsonProperty("mag_elaborati")
	private int processedMags = 0;
	
	// cancellati
	@JsonProperty("mag_cancellati")
	private List<String> deletedMags = new ArrayList<String>();
	
	// numero mag non trovati
	@JsonProperty("mag_inesistenti")
	private List<String> magsNotFound = new ArrayList<String>();
	
	

	/**
	 * Restituisce il numero dei MAG processati
	 * 
	 * @return int numero dei MAG processati
	 */
	public int getProcessedMags() {
		return processedMags;
	}
	
	/**
	 * Imposta il numero dei MAG processati
	 * 
	 * @param processedMags numero dei MAG processati
	 */
	public void setProcessedMags(int processedMags) {
		this.processedMags = processedMags;
	}
	
	/**
	 * Restituisce la lista degli ID MAG cancellati
	 * 
	 * @return List<String> lista degli ID MAG cancellati
	 */
	public List<String> getDeletedMags() {
		return deletedMags;
	}
	
	/**
	 * Imposta la lista degli ID MAG cancellati
	 * 
	 * @param deletedMags lista degli ID MAG cancellati
	 */
	public void setDeletedMags(List<String> deletedMags) {
		this.deletedMags = deletedMags;
	}
	
	/**
	 * Restituisce la lista degli ID MAG non trovati
	 * 
	 * @return List<String> lista degli ID MAG non trovati
	 */
	public List<String> getMagsNotFound() {
		return magsNotFound;
	}
	
	/**
	 * Imposta la lista degli ID MAG non trovati
	 * 
	 * @param magsNotFound lista degli ID MAG non trovati
	 */
	public void setMagsNotFound(List<String> magsNotFound) {
		this.magsNotFound = magsNotFound;
	}

}
