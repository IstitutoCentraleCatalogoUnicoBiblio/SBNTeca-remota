package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * JSON di risposta status e fine servizi di importazione/validazione
 * 
 *
 */
public class JsonImportReport implements Serializable {
	private static final long serialVersionUID = 7481624039253295929L;

	// job ID
	@JsonProperty("id_job")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String jobID;

	
	// numero di mag elaborati
	@JsonProperty("mag_elaborati")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer processedMagCount = null;
	
	// numero di mag da elaborare
	@JsonProperty("mag_totali")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer magCount = null;

	// numero di mag da elaborare
	@JsonProperty("oggetti_digitali")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer digitalObjectCount = null;
	
	// stato
	@JsonProperty("stato")
	private int status;
	
	// numero mag OK
	@JsonProperty("mag_OK")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer magOK = null;
	
	// numero mag KO
	@JsonProperty("mag_KO")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer magKO = null;

	@JsonProperty("mets_OK")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer metsOK = null;

	// numero mag KO
	@JsonProperty("mets_KO")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer metsKO = null;

	// messaggio
	@JsonProperty("messaggio")
	private String message;
	
	// data inizio
	@JsonProperty("tempo_inizio_processo")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String timestampStart = null;
	
	// data fine
	@JsonProperty("tempo_fine_processo")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String timestampEnd = null;

	// data inizio
	@JsonProperty("tempo_inizio_validazione")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String timestampStartValidation = null;
	
	// data fine
	@JsonProperty("tempo_fine_validazione")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String timestampEndValidation = null;
	
	// report per mag
	@JsonProperty("mags")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonReportMag> reportMags = new ArrayList<JsonReportMag>();
	
	
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
	 * Restituisce il numero dei MAG processati
	 * 
	 * @return Integer numero dei MAG processati
	 */
	public Integer getProcessedMagCount() {
		return processedMagCount;
	}
	
	/**
	 * Imposta il numero dei MAG processati
	 * 
	 * @param processedMagCount numero dei MAG processati
	 */
	public void setProcessedMagCount(Integer processedMagCount) {
		this.processedMagCount = processedMagCount;
	}

	/**
	 * Restituisce il numero dei MAG totali
	 * 
	 * @return Integer numero dei MAG totali
	 */
	public Integer getMagCount() {
		return magCount;
	}
	
	/**
	 * Imposta il numero dei MAG totali
	 * 
	 * @param magCount numero dei MAG totali
	 */
	public void setMagCount(Integer magCount) {
		this.magCount = magCount;
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
	
	/**
	 * Restituisce il messaggio di descrizione dello status del processo di importazione/validazione
	 * 
	 * @return String messaggio di descrizione dello status del processo di importazione/validazione
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Imposta il messaggio di descrizione dello status del processo di importazione/validazione
	 * 
	 * @param message messaggio di descrizione dello status del processo di importazione/validazione
	 */
	public void setMessage(String message) {
		this.message = message;
	} 

	/**
	 * Restituisce il numero di MAG validati correttamente
	 * 
	 * @return Integer numero di MAG validati correttamente
	 */
	public Integer getMagOK() {
		return magOK;
	}
	
	/**
	 * Imposta il numero di MAG validati correttamente
	 * 
	 * @param magOK
	 */
	public void setMagOK(Integer magOK) {
		this.magOK = magOK;
	}

	/**
	 * Restituisce il numero di MAG validati con errori
	 * 
	 * @return Integer numero di MAG validati con errori
	 */
	public Integer getMagKO() {
		return magKO;
	}
	
	/**
	 * Imposta il numero di MAG validati con errori
	 * 
	 * @param magKO numero di MAG validati con errori
	 */
	public void setMagKO(Integer magKO) {
		this.magKO = magKO;
	}
	
	/**
	 * Restituisce lo status del processo di importazione/validazione
	 * 
	 * @return int status del processo di importazione/validazione
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Imposta lo status del processo di importazione/validazione
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Restituisce il timestamp di fine processo di importazione/validazione
	 * 
	 * @return String timestamp di fine processo di importazione/validazione
	 */
	public String getTimestampEnd() {
		return this.timestampEnd;
	}

	/**
	 * Imposta il timestamp di fine processo di importazione/validazione
	 * 
	 * @param timestampEnd timestamp di fine processo di importazione/validazione
	 */
	public void setTimestampEnd(String timestampEnd) {
		this.timestampEnd = timestampEnd;
	}

	/**
	 * Restituisce il timestamp di inizio processo di importazione/validazione
	 * 
	 * @return String timestamp di inizio processo di importazione/validazione
	 */
	public String getTimestampStart() {
		return this.timestampStart;
	}

	/**
	 * Imposta il timestamp di inizio processo di importazione/validazione
	 * 
	 * @param timestampStart timestamp di inizio processo di importazione/validazione
	 */
	public void setTimestampStart(String timestampStart) {
		this.timestampStart = timestampStart;
	}

	/**
	 * Restituisce il timestamp di fine validazione
	 * 
	 * @return String timestamp di fine validazione
	 */
	public String getTimestampEndValidation() {
		return this.timestampEndValidation;
	}

	/**
	 * Imposta il timestamp di fine validazione
	 * 
	 * @param timestampEndValidation timestamp di fine validazione
	 */
	public void setTimestampEndValidation(String timestampEndValidation) {
		this.timestampEndValidation = timestampEndValidation;
	}

	/**
	 * Restituisce il timestamp di inizio validazione
	 * 
	 * @return timestamp di inizio validazione
	 */
	public String getTimestampStartValidation() {
		return this.timestampStartValidation;
	}

	/**
	 * Imposta il timestamp di inizio validazione
	 * 
	 * @param timestampStartValidation timestamp di inizio validazione
	 */
	public void setTimestampStartValidation(String timestampStartValidation) {
		this.timestampStartValidation = timestampStartValidation;
	}
	
	/**
	 * Restituisce i JSON delle validazioni sui singoli MAG
	 * 
	 * @return List<JsonReportMag> JSON delle validazioni sui singoli MAG
	 */
	public List<JsonReportMag> getReportMags() {
		return reportMags;
	}
	
	/**
	 * Imposta i JSON delle validazioni sui singoli MAG
	 * 
	 * @param reportMags JSON delle validazioni sui singoli MAG
	 */
	public void setReportMags(List<JsonReportMag> reportMags) {
		this.reportMags = reportMags;
	}

	public Integer getMetsOK() {
		return metsOK;
	}

	public void setMetsOK(Integer metsOK) {
		this.metsOK = metsOK;
	}

	public Integer getMetsKO() {
		return metsKO;
	}

	public void setMetsKO(Integer metsKO) {
		this.metsKO = metsKO;
	}
}
