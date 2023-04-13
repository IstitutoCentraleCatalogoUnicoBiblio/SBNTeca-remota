package com.gruppometa.sbntecaremota.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Oggetto contenente i metadati di una risosa digitale
 * 
 *
 */
public class DeliveryResource {
	
	private String deliveryID;
	private String href;
	private List<String> usage = new ArrayList<String>();
	private String type = null;
	private String project;
	private String vfsPath;

	public String getVfsPath() {
		return vfsPath;
	}

	public void setVfsPath(String vfsPath) {
		this.vfsPath = vfsPath;
	}

	private String vfsDirectory;
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private String vfsType;
	private String vfsFilename;
	private String vfsContainer;

	public String getVfsDirectory() {
		return vfsDirectory;
	}

	public void setVfsDirectory(String vfsDirectory) {
		this.vfsDirectory = vfsDirectory;
	}

	public String getVfsType() {
		return vfsType;
	}

	public void setVfsType(String vfsType) {
		this.vfsType = vfsType;
	}

	public String getVfsFilename() {
		return vfsFilename;
	}

	public void setVfsFilename(String vfsFilename) {
		this.vfsFilename = vfsFilename;
	}

	public String getVfsContainer() {
		return vfsContainer;
	}

	public void setVfsContainer(String vfsContainer) {
		this.vfsContainer = vfsContainer;
	}

	/**
	 * Restituisce l'ID del delivery
	 * 
	 * @return String ID del delivery
	 */
	public String getDeliveryID() {
		return deliveryID;
	}
	
	/**
	 * Imposta l'ID del delivery
	 * 
	 * @param deliveryID ID del delivery
	 */
	public void setDeliveryID(String deliveryID) {
		this.deliveryID = deliveryID;
	}
	
	/**
	 * Restituisce il path relativo della risorsa digitale
	 * 
	 * @return String path relativo della risorsa digitale
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Imposta il path relativo della risorsa digitale
	 * 
	 * @param href path relativo della risorsa digitale
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Restituisce il progetto di riferimento
	 * 
	 * @return String progetto di riferimento
	 */
	public String getProject() {
		return project;
	}

	/**
	 * Imposta il progetto di riferimento
	 * 
	 * @param project progetto di riferimento
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * Restituisce la lista degli usages consentiti per la risorsa digitale
	 * 
	 * @return List<String> lista degli usages consentiti per la risorsa digitale
	 */
	public List<String> getUsage() {
		return usage;
	}

	/**
	 * Imposta la lista degli usages consentiti per la risorsa digitale
	 * 
	 * @param usage lista degli usages consentiti per la risorsa digitale
	 */
	public void setUsage(List<String> usage) {
		this.usage = usage;
	}

	/**
	 * Restituisce il tipo di file per la risorsa digitale
	 * 
	 * @return String tipo di file per la risorsa digitale
	 */
	public String getType() {
		return type;
	}

	/**
	 * Imposta il tipo di file per la risorsa digitale
	 * 
	 * @param fileType tipo di file per la risorsa digitale
	 */
	public void setType(String type) {
		this.type = type;
	}

}
