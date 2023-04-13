package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonStatsRequest implements Serializable {
	private static final long serialVersionUID = -6959463620792369750L;
	
	// campo query 
	@JsonProperty("query_nome_campo")
	private String fieldQuery;

	// valore query 
	@JsonProperty("query_valore_campo")
	private String valueQuery;
	
	// lista faccette
	@JsonProperty("faccette")
	private List<String> facetFields = new ArrayList<String>();
	
	
	
	public String getFieldQuery() {
		return fieldQuery;
	}
	
	public void setFieldQuery(String fieldQuery) {
		this.fieldQuery = fieldQuery;
	}
	
	public String getValueQuery() {
		return valueQuery;
	}
	
	public void setValueQuery(String valueQuery) {
		this.valueQuery = valueQuery;
	}
	
	public List<String> getFacetFields() {
		return facetFields;
	}
	
	public void setFacetFields(List<String> facetFields) {
		this.facetFields = facetFields;
	}

}
