package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonNormalizationUpdateValue implements Serializable {
	private static final long serialVersionUID = 137337514351860012L;

	// vecchio valore
	@JsonProperty("valore_attuale")
	private String oldValue;
	
	// nuovo valore
	@JsonProperty("valore_nuovo")
	private String newValue;
	
	

	public String getOldValue() {
		return oldValue;
	}
	
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

}
