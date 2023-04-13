package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class JsonEditorValidateResponse implements Serializable {
	private static final long serialVersionUID = -138293106277069299L;

	// progetto
	private String project;
	
	// nome file MAG creato
	private String mag;
	
	// identifier
	private String identifier;

	// year/part number
	@JsonProperty("year_part_number")
	private String yearPartNumber = " - ";

	// issue/part name
	@JsonProperty("issue_part_name")
	private String issuePartName = " - ";
	
	// errore
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private String error;
	

	/**
	 * Restituisce il nome del progetto
	 * 
	 * @return nome del progetto
	 */
	public String getProject() {
		return project;
	}
	
	/**
	 * Imposta il nome del progetto
	 * 
	 * @param name nome del progetto
	 */
	public void setProject(String project) {
		this.project = project;
	}
	
	/**
	 * Restituisce il nome del file creato
	 * 
	 * @return nome del file creato
	 */
	public String getMag() {
		return mag;
	}
	
	/**
	 * Imposta il nome del file creato
	 * 
	 * @param mags nome del file creato
	 */
	public void setMag(String mag) {
		this.mag = mag;
	}

	/**
	 * Restituisce l'identifier
	 * 
	 * @return identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Imposta l'identifier
	 * 
	 * @param identifier identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Restituisce il campo year/part_number
	 * 
	 * @return String campo year/part_number
	 */
	public String getYearPartNumber() {
		return yearPartNumber;
	}
	
	/**
	 * Imposta il campo year/part_number
	 * 
	 * @param yearPartNumber
	 */
	public void setYearPartNumber(String yearPartNumber) {
		this.yearPartNumber = yearPartNumber;
	}

	/**
	 * Restituisce il campo issue/part_name
	 * 
	 * @return String campo issue/part_name
	 */
	public String getIssuePartName() {
		return issuePartName;
	}

	/**
	 * Imposta il campo issue/part_name
	 * 
	 * @param issuePartName campo issue/part_name
	 */
	public void setIssuePartName(String issuePartName) {
		this.issuePartName = issuePartName;
	}

	/**
	 * Restituisce l'errore
	 * 
	 * @return errore
	 */
	public String getError() {
		return error;
	}
	
	/**
	 * Imposta l'errore
	 * 
	 * @param error errore
	 */
	public void setError(String error) {
		this.error = error;
	}

}
