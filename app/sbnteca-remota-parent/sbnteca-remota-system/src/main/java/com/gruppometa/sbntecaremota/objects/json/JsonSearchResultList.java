package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonSearchResultList implements Serializable {
	private static final long serialVersionUID = -3034824615463199302L;
	

	// MAG risultati
	@JsonProperty("mag")
	private List<JsonMagPreview> mags = new ArrayList<JsonMagPreview>();
	
	// faccette
	@JsonProperty("faccette")
	private List<JsonSearchResultFacet> facets = new ArrayList<JsonSearchResultFacet>();

	// faccette
	@JsonProperty("solr_query")
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private String solrQuery;
	
	// numero risultati
	@JsonProperty("numero_risultati")
	private long numberResults = 0;
	
	
	public List<JsonMagPreview> getMags() {
		return mags;
	}
	
	public void setMags(List<JsonMagPreview> mags) {
		this.mags = mags;
	}
	
	public List<JsonSearchResultFacet> getFacets() {
		return facets;
	}
	
	public void setFacets(List<JsonSearchResultFacet> facets) {
		this.facets = facets;
	}
	
	public String getSolrQuery() {
		return solrQuery;
	}
	
	public void setSolrQuery(String solrQuery) {
		this.solrQuery = solrQuery;
	}
	
	public long getNumberResults() {
		return numberResults;
	}
	
	public void setNumberResults(long numberResults) {
		this.numberResults = numberResults;
	}

}
