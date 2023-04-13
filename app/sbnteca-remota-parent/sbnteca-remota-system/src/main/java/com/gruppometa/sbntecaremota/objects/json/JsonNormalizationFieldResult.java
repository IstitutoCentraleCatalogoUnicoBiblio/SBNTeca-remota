package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonNormalizationFieldResult implements Serializable {
	private static final long serialVersionUID = 8502120615880276042L;
	
	// nome campo
	@JsonProperty("campo")
	private String fieldName;
	
	// valori modificati
	@JsonProperty("modifiche")
	private List<JsonNormalizationValueResult> updates = new ArrayList<JsonNormalizationValueResult>();
	

	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public List<JsonNormalizationValueResult> getUpdates() {
		return updates;
	}
	
	public void setUpdates(List<JsonNormalizationValueResult> updates) {
		this.updates = updates;
	}
	

}
