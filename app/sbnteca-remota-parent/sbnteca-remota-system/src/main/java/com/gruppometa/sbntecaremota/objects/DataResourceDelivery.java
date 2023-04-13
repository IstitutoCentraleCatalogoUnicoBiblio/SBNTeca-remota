package com.gruppometa.sbntecaremota.objects;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

public class DataResourceDelivery implements Serializable {
	private static final long serialVersionUID = -6019312816655902497L;
	
	// stream
	private InputStream stream;
	
	// content type
	private String contentType;
	
	// resource type
	private String resourceType;
	
	// lunghezza file
	private long length;
	
	// nome file
	private String resourceName;
	
	// data ultima modifica
	private Date lastModified;
	
	
	
	/**
	 * Restituisce lo stream
	 * 
	 * @return InputStream stream
	 */
	public InputStream getStream() {
		return stream;
	}
	
	/**
	 * Imposta lo stream
	 * 
	 * @param stream stream
	 */
	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	/**
	 * Restituisce il content type della risorsa
	 * 
	 * @return String content type della risorsa
	 */
	public String getContentType() {
		return contentType;
	}
	
	/**
	 * Imposta il content type della risorsa
	 * 
	 * @param contentType content type della risorsa
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Restituisce il tipo di risorsa (img, audio, video, ocr, doc)
	 * 
	 * @return String tipo di risorsa (img, audio, video, ocr, doc)
	 */
	public String getResourceType() {
		return resourceType;
	}
	
	/**
	 * Imposta il tipo di risorsa (img, audio, video, ocr, doc)
	 * 
	 * @param resourceType tipo di risorsa (img, audio, video, ocr, doc)
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	/**
	 * Restituisce la lunghezza (in byte) della risorsa digitale
	 * 
	 * @return long lunghezza (in byte) della risorsa digitale
	 */
	public long getLength() {
		return length;
	}
	
	/**
	 * Imposta la lunghezza (in byte) della risorsa digitale
	 * 
	 * @param length lunghezza (in byte) della risorsa digitale
	 */
	public void setLength(long length) {
		this.length = length;
	}
	
	/**
	 * Restituisce il nome della risorsa digitale
	 * 
	 * @return String nome della risorsa digitale
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * Imposta il nome della risorsa digitale
	 * 
	 * @param resourceName nome della risorsa digitale
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	/**
	 * Restituisce la data di ultima modifica della risorsa digitale
	 * 
	 * @return Date data di ultima modifica della risorsa digitale
	 */
	public Date getLastModified() {
		return lastModified;
	}
	
	/**
	 * Imposta la data di ultima modifica della risorsa digitale
	 * 
	 * @param lastModified data di ultima modifica della risorsa digitale
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
