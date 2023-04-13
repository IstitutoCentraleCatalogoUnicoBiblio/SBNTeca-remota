package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonSearchResultFacet implements Serializable {
	private static final long serialVersionUID = 4411184714940887029L;
	
	// nome faccetta
	@JsonProperty("nome")
	private String name;
	
	// nome faccetta
	@JsonProperty("titolo")
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private String label;
	
	// valori
	@JsonProperty("valori")
	private List<JsonSearchResultFacetValue> values = new ArrayList<JsonSearchResultFacetValue>();
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	} 
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public List<JsonSearchResultFacetValue> getValues() {
		return values;
	}
	
	public void setValues(List<JsonSearchResultFacetValue> values) {
		this.values = values;
	}

}
