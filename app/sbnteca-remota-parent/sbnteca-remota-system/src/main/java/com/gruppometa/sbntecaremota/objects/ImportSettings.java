package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gruppometa.sbntecaremota.objects.validators.ExternalValidator;
import com.gruppometa.sbntecaremota.objects.validators.Validator;

/**
 * Oggetto contenente tutte le impostazione di configurazione necessarie al lancio di un job di importazione/validazione
 * 
 *
 */
public class ImportSettings implements Serializable {
	private static final long serialVersionUID = -3487574501768108779L;
	
	// flag di pubblicazione
	private int pubblica;
	
	// vestizione mag
	private int dressMag = 0;
	
	// sovrascrittura MAG
	private int overwrite = 1;
	
	// flag di aggiornamento
	private int update;
	
	// tipo di mag i valori possibili sono file, http, ftp, db
	private String typeMag;
	
	// usage di acquisizione
	private List<String> usageA = new ArrayList<String>();
	
	// usage di esportazione
	private List<String> usageE = new ArrayList<String>();
	
	// configurazione
	private Properties configuration;
	
	// utente
	private int userID;
	
	// job ID
	private String jobID;
	
	// tag root report xml
	private ImportReport report;
	
	// lista validatori
	private List<Validator> validators = new ArrayList<Validator>();
	
	// indicizzare o soltanto validare
	private boolean indexMags = true;
	
	

	/**
	 * Restituisce gli usage di acquisizione
	 * 
	 * @return String usage di acquisizione
	 */
	public List<String> getUsageA() {
		return usageA;
	}
	
	/**
	 * Imposta gli usage di acquisizione
	 * 
	 * @param usageA usage di acquisizione
	 */
	public void setUsageA(List<String> usageA) {
		this.usageA = usageA;
	}
	
	/**
	 * Restituisce gli usage di esportazione
	 * 
	 * @return String usage di esportazione
	 */
	public List<String> getUsageE() {
		return usageE;
	}
	
	/**
	 * Imposta gli usage di esportazione
	 * 
	 * @param usageE usage di esportazione
	 */
	public void setUsageE(List<String> usageE) {
		this.usageE = usageE;
	}
	
	/**
	 * Restituisce il flag di aggiornamento (1 se modalità aggiornamento)
	 * 
	 * @return int flag di aggiornamento
	 */
	public int getUpdate() {
		return update;
	}
	
	/**
	 * Imposta il flag di aggiornamento (1 se modalità aggiornamento)
	 * 
	 * @param update flag di aggiornamento
	 */
	public void setUpdate(int update) {
		this.update = update;
	}
	
	/**
	 * Restituisce il tipo di sorgente di provenienza del MAG (file system, ftp, http, db)
	 * 
	 * @return tipo di sorgente di provenienza del MAG (file system, ftp, http, db)
	 */
	public String getTypeMag() {
		return typeMag;
	}
	
	/**
	 * Imposta il tipo di sorgente di provenienza del MAG (file system, ftp, http, db)
	 * 
	 * @param typeMag tipo di sorgente di provenienza del MAG (file system, ftp, http, db)
	 */
	public void setTypeMag(String typeMag) {
		this.typeMag = typeMag;
	}

	/**
	 * Restituisce il flag di pubblicazione
	 * 
	 * @return int flag di pubblicazione
	 */
	public int getPubblica() {
		return pubblica;
	}
	
	/**
	 * Imposta il flag di pubblicazione
	 * 
	 * @param pubblica flag di pubblicazione
	 */
	public void setPubblica(int pubblica) {
		this.pubblica = pubblica;
	}
	
	/**
	 * Restituisce il flag di vestizione
	 * 
	 * @return int flag di vestizione
	 */
	public int getDressMag() {
		return dressMag;
	}
	
	/**
	 * Imposta il flag di vestizione
	 * 
	 * @param dressMag flag di vestizione
	 */
	public void setDressMag(int dressMag) {
		this.dressMag = dressMag;
	}
	
	/**
	 * Restituisce la mappa di configurazione del job di importazione/validazione
	 * 
	 * @return Properties mappa di configurazione del job di importazione/validazione
	 */
	public Properties getConfiguration() {
		return configuration;
	}
	
	/**
	 * Imposta la mappa di configurazione del job di importazione/validazione
	 * 
	 * @param configuration mappa di configurazione del job di importazione/validazione
	 */
	public void setConfiguration(Properties configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * Restituisce l'utente che ha richiesto il processo di importazione
	 * 
	 * @return int utente che ha richiesto il processo di importazione
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Imposta l'utente che ha richiesto il processo di importazione
	 * 
	 * @param userID utente che ha richiesto il processo di importazione
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * Restituisce l'oggetto di riepilogo (report slot) del processo di importazione/validazione
	 * 
	 * @return ImportReport oggetto di riepilogo (report slot) del processo di importazione/validazione
	 */
	public ImportReport getReport() {
		return report;
	}
	
	/**
	 * Imposta l'oggetto di riepilogo (report slot) del processo di importazione/validazione
	 * 
	 * @param report oggetto di riepilogo (report slot) del processo di importazione/validazione
	 */
	public void setReport(ImportReport report) {
		this.report = report;
	}

	/**
	 * Restituisce i validatori configurati per il processo di importazione/validazione
	 * 
	 * @return List<Validator> validatori configurati per il processo di importazione/validazione
	 */
	public List<Validator> getValidators() {
		return validators;
	}
	
	/**
	 * Imposta i validatori configurati per il processo di importazione/validazione
	 * 
	 * @param validators validatori configurati per il processo di importazione/validazione
	 */
	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}
	
	/**
	 * Processo di importazione o validazione
	 *  
	 * @return boolean vero se processo di importazione, falso se processo di validazione
	 */
	public boolean getIndexMags() {
		return indexMags;
	}
	
	/**
	 * Processo di importazione o validazione
	 * 
	 * @param indexMags vero se processo di importazione, falso se processo di validazione
	 */ 
	public void setIndexMags(boolean indexMags) {
		this.indexMags = indexMags;
	}
	
	/**
	 * Restituisce l'ID del job di importazione/validazione
	 * 
	 * @return String ID del job di importazione/validazione
	 */
	public String getJobID() {
		return jobID;
	}
	
	/**
	 * Imposta l'ID del job di importazione/validazione
	 * 
	 * @param jobID ID del job di importazione/validazione
	 */
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	/**
	 * Restituisce il flag di sovrascrittura MAG (in caso di MAG esistente)
	 * 
	 * @return Integer flag di sovrascrittura MAG 
	 */
	public int getOverwrite() {
		return overwrite;
	}
	
	/**
	 * Imposta il flag di sovrascrittura MAG (in caso di MAG esistente)
	 * 
	 * @param dressMag flag di sovrascrittura MAG 
	 */
	public void setOverwrite(int overwrite) {
		this.overwrite = overwrite;
	}

}
