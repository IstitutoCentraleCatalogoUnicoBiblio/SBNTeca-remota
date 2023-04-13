package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonNormalizationData implements Serializable {
	private static final long serialVersionUID = -8184294648125843423L;
	
	// nome campo
	@JsonProperty("campo")
	private String fieldName;
	
	// modifiche
	@JsonProperty("modifiche")
	private List<JsonNormalizationUpdateValue> updates = new ArrayList<JsonNormalizationUpdateValue>();
	
	
	
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public List<JsonNormalizationUpdateValue> getUpdates() {
		return updates;
	}
	
	public void setUpdates(List<JsonNormalizationUpdateValue> updates) {
		this.updates = updates;
	}

}
