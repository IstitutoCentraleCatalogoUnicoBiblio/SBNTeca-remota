package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonSearchViewerResultList implements Serializable {
	private static final long serialVersionUID = -3034824615463199302L;
	

	// MAG risultati
	@JsonProperty("mag")
	private List<List<JsonMagDetailField>> mags = new ArrayList<List<JsonMagDetailField>>();
	
	// faccette
	@JsonProperty("faccette")
	private List<JsonSearchResultFacet> facets = new ArrayList<JsonSearchResultFacet>();
	
	// numero risultati
	@JsonProperty("numero_risultati")
	private long numberResults = 0;
	
	
	public List<List<JsonMagDetailField>> getMags() {
		return mags;
	}
	
	public void setMags(List<List<JsonMagDetailField>> mags) {
		this.mags = mags;
	}
	
	public List<JsonSearchResultFacet> getFacets() {
		return facets;
	}
	
	public void setFacets(List<JsonSearchResultFacet> facets) {
		this.facets = facets;
	}
	
	public long getNumberResults() {
		return numberResults;
	}
	
	public void setNumberResults(long numberResults) {
		this.numberResults = numberResults;
	}

}
