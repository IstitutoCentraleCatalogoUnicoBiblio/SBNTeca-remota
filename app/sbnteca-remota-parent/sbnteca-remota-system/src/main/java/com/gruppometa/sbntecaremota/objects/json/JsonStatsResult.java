package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonStatsResult implements Serializable {
	private static final long serialVersionUID = 506419516254737640L;
	
	// nome campo
	@JsonProperty("nome_campo")
	private String field;
	

	// nome campo
	@JsonProperty("risultati")
	private List<JsonStatsCountValue> results = new ArrayList<JsonStatsCountValue>();
	
	
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public List<JsonStatsCountValue> getResults() {
		return results;
	}
	
	public void setResults(List<JsonStatsCountValue> results) {
		this.results = results;
	}

}
