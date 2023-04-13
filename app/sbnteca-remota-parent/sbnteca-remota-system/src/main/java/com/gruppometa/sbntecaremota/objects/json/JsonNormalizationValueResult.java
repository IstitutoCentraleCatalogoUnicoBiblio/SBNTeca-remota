package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonNormalizationValueResult implements Serializable {
	private static final long serialVersionUID = 1930539460092997605L;
	

	// nuovo valore
	@JsonProperty("nuovo_valore")
	private String newValue;

	// numero dcoumenti modificati
	@JsonProperty("numero_documenti")
	private int numDocuments = 0;

	// message
	@JsonProperty("errore")
	private String error = "";
	

	
	public String getNewValue() {
		return newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public int getNumDocuments() {
		return numDocuments;
	}
	
	public void setNumDocuments(int numDocuments) {
		this.numDocuments = numDocuments;
	}

	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}

}
