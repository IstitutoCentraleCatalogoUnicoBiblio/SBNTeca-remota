package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonStatsCountValue implements Serializable {
	private static final long serialVersionUID = -4335561043457440090L;
	
	// valore
	@JsonProperty("valore")
	private String value;
	
	// numero documenti
	@JsonProperty("numero_documenti")
	private long documents = 0;
	
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public long getDocuments() {
		return documents;
	}
	
	public void setDocuments(long documents) {
		this.documents = documents;
	}

}
