package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Oggetto di riepilogo di validazione (slot)
 * 
 *
 */
public class ValidationReport implements Serializable {
	private static final long serialVersionUID = -6752413280844085874L;
	
	// inizio
	private String start;
	
	// fine
	private String end;
	
    // lista report MAG
    private List<MagReport> magReports = new ArrayList<MagReport>();
    
    

    /**
     * Restituisce il tempo di inizio della validazione
     * 
     * @return String tempo di inizio della validazione
     */
    public String getStart() {
        return start;
    }

    /**
     * Imposta il tempo di inizio della validazione
     * 
     * @param value tempo di inizio della validazione
     */
    public void setStart(String value) {
        this.start = value;
    }

    /**
     * Imposta il tempo di fine della validazione
     * 
     * @return String tempo di fine della validazione
     */
    public String getEnd() {
        return end;
    }

    /**
     * Imposta il tempo di fine della validazione
     * 
     * @param value tempo di fine della validazione
     */
    public void setEnd(String value) {
        this.end = value;
    }
    
    /**
     * Restituisce la lista di riepilogo dei MAG elaborati (slot)
     * 
     * @return List<MagReport> lista di riepilogo dei MAG elaborati (slot)
     */
    public List<MagReport> getMagReports() {
    	return magReports;
    }
    
    /**
     * Imposta la lista di riepilogo dei MAG elaborati (slot)
     * 
     * @param magReports lista di riepilogo dei MAG elaborati (slot)
     */
    public void setMagReports(List<MagReport> magReports) {
    	this.magReports = magReports;
    }

}
