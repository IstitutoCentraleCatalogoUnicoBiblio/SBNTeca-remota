package com.gruppometa.sbntecaremota.objects;

import org.w3c.dom.Document;

public class ExternalMagReference extends MagReference {
	private static final long serialVersionUID = -8943142005380918223L;
	
	// tipo risorsa
	private String resourceType;
	
	// start
	private String start;
	
	// stop
	private String stop;
	
	// documento XML MAG
	private Document externalDocument = null;
	
	// path XML MAG esterno
	private String externalPath = null;
	
	
	
	/**
	 * Restituisce il tipo di risorsa riferita (img, audio, video, ocr, doc)
	 * 
	 * @return String tipo di risorsa riferita (img, audio, video, ocr, doc)
	 */
	public String getResourceType() {
		return resourceType;
	}
	
	/**
	 * Imposta il tipo di risorsa riferita (img, audio, video, ocr, doc)
	 * 
	 * @param resourceType tipo di risorsa riferita (img, audio, video, ocr, doc)
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	/**
	 * Restituisce il sequence_number di inizio intervallo
	 * 
	 * @return String sequence_number di inizio intervallo
	 */
	public String getStart() {
		return start;
	}
	
	/**
	 * Imposta il sequence_number di inizio intervallo
	 * 
	 * @param start sequence_number di inizio intervallo
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * Restituisce il sequence_number di fine intervallo
	 * 
	 * @return String sequence_number di fine intervallo
	 */
	public String getStop() {
		return stop;
	}
	
	/**
	 * Imposta il sequence_number di fine intervallo
	 * 
	 * @param stop sequence_number di fine intervallo
	 */
	public void setStop(String stop) {
		this.stop = stop;
	}
	
	/**
	 * Restituisce il documento MAG XML esterno
	 * 
	 * @return Document documento MAG XML esterno
	 */
	public Document getExternalDocument() {
		return externalDocument;
	}
	
	/**
	 * Imposta il documento MAG XML esterno
	 * 
	 * @param externalDocument documento MAG XML esterno
	 */
	public void setExternalDocument(Document externalDocument) {
		this.externalDocument = externalDocument;
	}

	/**
	 * Restituisce il path del MAG XML esterno
	 * 
	 * @return Document path del MAG XML esterno
	 */
	public String getExternalPath() {
		return externalPath;
	}
	
	/**
	 * Imposta il path del MAG XML esterno
	 * 
	 * @param externalPath path del MAG XML esterno
	 */
	public void setExternalPath(String externalPath) {
		this.externalPath = externalPath;
	}

}
