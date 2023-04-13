package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonNormalizationResponse implements Serializable {
	private static final long serialVersionUID = 8922053725223057350L;
	
	// messaggio
	@JsonProperty("messaggio")
	private String message;
	
	// risultato normalizzazione
	@JsonProperty("normalizzazione")
	private List<JsonNormalizationFieldResult> normalizationResults = new ArrayList<JsonNormalizationFieldResult>();
	
	
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<JsonNormalizationFieldResult> getNormalizationResults() {
		return normalizationResults;
	}
	
	public void setNormalizationResults(List<JsonNormalizationFieldResult> normalizationResults) {
		this.normalizationResults = normalizationResults;
	}

}
