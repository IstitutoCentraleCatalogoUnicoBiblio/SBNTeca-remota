package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonNormalizationRequest implements Serializable {
	private static final long serialVersionUID = 6175202745231846436L;
	
	
	// ricerca
	@JsonProperty("ricerca_avanzata")
	private JsonSearchRequest searchRequest;
	
	// dati normalizzazione
	@JsonProperty("normalizzazione")
	private List<JsonNormalizationData> normalizationData = new ArrayList<JsonNormalizationData>();
	
	
	
	public JsonSearchRequest getSearchRequest() {
		return searchRequest;
	}
	
	public void setSearchRequest(JsonSearchRequest searchRequest) {
		this.searchRequest = searchRequest;
	}
	
	public List<JsonNormalizationData> getNormalizationData() {
		return normalizationData;
	}
	
	public void setNormalizationData(List<JsonNormalizationData> normalizationData) {
		this.normalizationData = normalizationData;
	}

}
