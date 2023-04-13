package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;

/**
 * Oggetto di riepilogo report
 * 
 *
 */
public class ImportReport implements Serializable {
	private static final long serialVersionUID = 7481624039253295929L;

	// ID utente
	private int user;
    
    // codice di stato
    private int status;
    
    // messaggio descrittivo di status
    private String statusMessage;
    
    // numero dei MAG da elaborare
    private int numMag = 0;
    
    // numero di MAG processati
    private int processedMag = 0;
    
    // numero di MAG OK
    private int magOK = 0;

    // numero di MAG OK
    private int metsOK = 0;

    // numero di MAG KO
    private int magKO = 0;

    // numero di MAG KO
    private int metsKO = 0;

    // numero di MAG indicizzati
    private int indexedMag = 0;
    
    // numero degli oggetti digitali
    private Integer digitalObjectCount = null;
    
    // ID job di importazione
    private String id;
    
    // tempo di inizio
    private String start;

    public int getMetsOK() {
        return metsOK;
    }

    public void setMetsOK(int metsOK) {
        this.metsOK = metsOK;
    }

    public int getMetsKO() {
        return metsKO;
    }

    public void setMetsKO(int metsKO) {
        this.metsKO = metsKO;
    }

    // tempo di fine
    private String end;
    
    // validazione
    private ValidationReport validation = new ValidationReport();
    
    
    /**
     * Restituisce l'utente
     * 
     * @return int utente
     */
    public int getUser() {
        return user;
    }

    /**
     * Imposta l'utente
     * 
     * @param value utente
     */
    public void setUser(int value) {
        this.user = value;
    }

    /**
     * Restituisce il codice di status
     * 
     * @return int codice di status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Imposta il codice di status
     * 
     * @param value codice di status
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * Restituisce il messaggio di descrizione dello status
     * 
     * @return messaggio di descrizione dello status
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Imposta il messaggio di descrizione dello status
     * 
     * @param value messaggio di descrizione dello status
     */
    public void setStatusMessage(String value) {
        this.statusMessage = value;
    }

    /**
     * Restituisce il numero di MAG da elaborare
     * 
     * @return int numero MAG da elaborare
     */
    public int getNumMag() {
        return numMag;
    }

    /**
     * Imposta il numero di MAG da elaborare
     * 
     * @param value numero di MAG da elaborare
     */
    public void setNumMag(int value) {
        this.numMag = value;
    }

    /**
     * Restituisce il numero di MAG già processati
     * 
     * @return int numero di MAG già processati
     */
    public int getProcessedMag() {
        return processedMag;
    }

    /**
     * Imposta il numero di MAG già processati
     * 
     * @param value numero di MAG già processati
     */
    public void setProcessedMag(int value) {
        this.processedMag = value;
    }

    /**
     * Restituisce il numero di MAG validati correttamente
     * 
     * @return numero di MAG validati correttamente
     */
    public int getMagOK() {
        return magOK;
    }

    /**
     * Imposta il numero di MAG validati correttamente
     * 
     * @param value numero di MAG validati correttamente
     */
    public void setMagOK(int value) {
        this.magOK = value;
    }

    /**
     * Restituisce il numero di MAG validati con errori
     * 
     * @return int numero di MAG validati con errori
     */
    public int getMagKO() {
        return magKO;
    }

    /**
     * Imposta il numero di MAG validati con errori
     * 
     * @param value numero di MAG validati con errori
     */ 
    public void setMagKO(int value) {
        this.magKO = value;
    }
    
    /**
     * Restituisce il numero di MAG indicizzati
     * 
     * @return int numero di MAG indicizzati
     */
    public int getIndexedMag() {
    	return indexedMag;
    }
    
    /**
     * Imposta il numero di MAG indicizzati
     * 
     * @param indexedMag numero di MAG indicizzati
     */
    public void setIndexedMag(int indexedMag) {
    	this.indexedMag = indexedMag;
    }

    /**
     * Restituisce l'ID del processo di importazione/validazione
     * 
     * @return String ID del processo di importazione/validazione
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta l'ID del processo di importazione/validazione
     * 
     * @param value ID del processo di importazione/validazione
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Restituisce il tempo di inizio del processo di importazione/validazione
     * 
     * @return String tempo di inizio del processo di importazione/validazione
     */
    public String getStart() {
        return start;
    }

    /**
     * Imposta il tempo di inizio del processo di importazione/validazione
     * 
     * @param value tempo di inizio del processo di importazione/validazione
     */
    public void setStart(String value) {
        this.start = value;
    }

    /**
     * Restituisce il tempo di fine del processo di importazione/validazione
     * 
     * @return String tempo di fine del processo di importazione/validazione
     */
    public String getEnd() {
        return end;
    }

    /**
     * Imposta il tempo di fine del processo di importazione/validazione
     * 
     * @param value tempo di fine del processo di importazione/validazione
     */
    public void setEnd(String value) {
        this.end = value;
    }
    
    /**
     * Restituisce l'oggetto di riepilogo del processo di validazione
     * 
     * @return ValidationReport oggetto di riepilogo del processo di validazione
     */
    public ValidationReport getValidation() {
    	return validation;
    }
    
    /**
     * Imposta l'oggetto di riepilogo del processo di validazione
     * 
     * @param validation oggetto di riepilogo del processo di validazione
     */
    public void setValidation(ValidationReport validation) {
    	this.validation = validation;
    }

	/**
	 * Restituisce il numero degli oggetti digitali
	 * 
	 * @return Integer numero degli oggetti digitali
	 */
	public Integer getDigitalObjectCount() {
		return digitalObjectCount;
	}
	
	/**
	 * Imposta il numero degli oggetti digitali
	 * 
	 * @param magCount numero degli oggetti digitali
	 */
	public void setDigitalObjectCount(Integer digitalObjectCount) {
		this.digitalObjectCount = digitalObjectCount;
	}

}
