package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonSearchResultFacetValue implements Serializable {
	private static final long serialVersionUID = -3378855890717696862L;
	
	// valore faccetta
	@JsonProperty("valore")
	private String value;
	
	// numero documenti
	@JsonProperty("numero_documenti")
	private long size;
	
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}

}
