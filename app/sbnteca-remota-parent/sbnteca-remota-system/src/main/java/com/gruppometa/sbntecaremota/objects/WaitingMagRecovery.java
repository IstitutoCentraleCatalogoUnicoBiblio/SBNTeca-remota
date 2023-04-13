package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * Tale classe si occupa della memorizzazione dei riferimenti esterni ad un MAG,
 * rilevati attraverso i processi di validazione, ed il riferimento al tag XML 
 * di costruzione del risultato finale per la validazione di tale MAG. 
 * Ogni MAG può avere 1 o più riferimenti esterni ed un riferimento al tag del risultato
 * finale. Il tag conterrà l'esito della validazione e le liste di errori e warning 
 * complessivamente rilevate, tra validazione generale e validazione per riferimenti
 * esterni
 * 
 *
 */
public class WaitingMagRecovery implements Serializable {
	private static final long serialVersionUID = -7808717124534198689L;
	
	// lista riferimenti esterni
	private List<ExternalMagReference> references = new ArrayList<ExternalMagReference>();
	
	// xml validation object
	private MagReport magReportObject;
	
	// import mag configuration
	private ImportMagConfiguration importConfiguration;
	
	// mag in attesa
	private String waitingMag;
	
	
	
	/**
	 * Restituisce il path del MAG in attesa
	 * 
	 * 
	 * @return path del MAG in attesa
	 */
	public String getWaitingMag() {
		return waitingMag;
	}
	
	/**
	 * Imposta il path del MAG in attesa
	 * 
	 * @param waitingMag path del MAG in attesa
	 */
	public void setWaitingMag(String waitingMag) {
		this.waitingMag = waitingMag;
	}
	
	/**
	 * Restituisce la lista dei riferimenti esterni
	 * 
	 * @return lista dei riferimenti esterni
	 */
	public List<ExternalMagReference> getReferences() {
		return references;
	}
	
	/**
	 * Imposta la lista dei riferimenti esterni
	 * 
	 * @param references lista dei riferimenti esterni
	 */
	public void setReferences(List<ExternalMagReference> references) {
		this.references = references;
	}
	
	/**
	 * Restituisce l'oggetto di riepilogo report per il MAG
	 * 
	 * @return MagReport oggetto di riepilogo report per il MAG
	 */
	public MagReport getMagReportObject() {
		return magReportObject;
	}
	
	/**
	 * Imposta l'oggetto di riepilogo report per il MAG
	 * 
	 * @param magReportObject oggetto di riepilogo report per il MAG
	 */
	public void setMagReportObject(MagReport magReportObject) {
		this.magReportObject = magReportObject;
	}

	/**
	 * Restituisce le configurazioni del MAG
	 * 
	 * @return ImportMagConfiguration configurazioni del MAG
	 */
	public ImportMagConfiguration getImportConfiguration() {
		return importConfiguration;
	}
	
	/**
	 * Imposta le configurazioni del MAG
	 * 
	 * @param importConfiguration configurazioni del MAG
	 */
	public void setImportConfiguration(ImportMagConfiguration importConfiguration) {
		this.importConfiguration = importConfiguration;
	}

}
