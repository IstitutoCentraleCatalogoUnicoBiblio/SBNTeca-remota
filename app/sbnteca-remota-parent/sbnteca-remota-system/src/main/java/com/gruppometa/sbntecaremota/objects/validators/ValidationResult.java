package com.gruppometa.sbntecaremota.objects.validators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gruppometa.sbntecaremota.objects.ExternalMagReference;

public class ValidationResult implements Serializable {
	private static final long serialVersionUID = 948637097980406918L;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;

	// lista riferimenti esterni
	private List<ExternalMagReference> externalReferences = new ArrayList<ExternalMagReference>();
	
	// lista errori
	private List<ValidationError> errors = new ArrayList<ValidationError>();
	
	
	
	/**
	 * Restituisce la lista dei riferimenti esterni
	 * 
	 * @return List<ExternalMagReference> lista riferimenti esterni
	 */
	public List<ExternalMagReference> getExternalReferences() {
		return externalReferences;
	}
	
	/**
	 * Imposta la lista dei riferimenti esterni
	 * 
	 * @param externalReferences lista dei riferimenti esterni
	 */
	public void setExternalReferences(List<ExternalMagReference> externalReferences) {
		this.externalReferences = externalReferences;
	}
	
	/**
	 * Restituisce la lista degli errori di validazione
	 * 
	 * @return List<ValidationError> errori di validazione
	 */
	public List<ValidationError> getErrors() {
		return errors;
	}
	
	/**
	 * Imposta la lista degli errori di validazione
	 * 
	 * @param errors lista degli errori di validazione
	 */
	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}

}
