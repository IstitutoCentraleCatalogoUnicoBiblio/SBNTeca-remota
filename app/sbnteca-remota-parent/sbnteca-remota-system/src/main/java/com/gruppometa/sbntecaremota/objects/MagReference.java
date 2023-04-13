package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MagReference implements Serializable {
	private static final long serialVersionUID = 8802848063046437185L;
	
	// identifiers
	private List<String> identifiers = new ArrayList<String>();
	
	// issue
	private String issue;
	
	// year
	private String year;
	
	
	

	/**
	 * Restituisce il campo identifier del MAG
	 * 
	 * @return String campo identifier del MAG
	 */
	public List<String> getIdentifiers() {
		return identifiers;
	}
	
	/**
	 * Imposta il campo identifier del MAG
	 * 
	 * @param identifier campo identifier del MAG
	 */
	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * Restituisce il campo year per MAG di tipo seriali
	 * 
	 * @return String campo year per MAG di tipo seriali
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * Imposta il campo year per MAG di tipo seriali
	 * 
	 * @param year campo year per MAG di tipo seriali
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * Restituisce il campo issue per MAG di tipo seriali
	 * 
	 * @return String campo issue per MAG di tipo seriali
	 */
	public String getIssue() {
		return issue;
	}
	
	/**
	 * Imposta il campo issue per MAG di tipo seriali
	 * 
	 * @param issue campo issue per MAG di tipo seriali
	 */
	public void setIssue(String issue) {
		this.issue = issue;
	}

}
