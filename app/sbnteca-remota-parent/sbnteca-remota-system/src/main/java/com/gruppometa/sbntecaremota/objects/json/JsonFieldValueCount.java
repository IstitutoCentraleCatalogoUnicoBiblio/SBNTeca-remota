package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonFieldValueCount implements Serializable {
	private static final long serialVersionUID = -3822748558329444821L;
	
	// valore
	@JsonProperty("valore")
	private String value;
	
	// numero documenti
	@JsonProperty("numero_documenti")
	private long numDocuments = 0;
	
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public long getNumDocuments() {
		return numDocuments;
	}
	
	public void setNumDocuments(long numDocuments) {
		this.numDocuments = numDocuments;
	}

}
