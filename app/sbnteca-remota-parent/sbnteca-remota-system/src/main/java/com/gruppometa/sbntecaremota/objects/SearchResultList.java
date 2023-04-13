package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultList implements Serializable {
	private static final long serialVersionUID = -5137800932832261884L;
	
	// MAG risultati
	private List<Mag> results = new ArrayList<Mag>();
	
	// total results
	private long totalResults = 0;
	
	// query solr
	private String solrQuery;
	
	// faccette
	private Map<String, List<String>> facets = new HashMap<String, List<String>>();

	// conteggio faccette
	private Map<String, List<Long>> countFacets = new HashMap<String, List<Long>>();
	
	
	public List<Mag> getResults() {
		return results;
	}
	
	public void setResults(List<Mag> results) {
		this.results = results;
	}
	
	public long getTotalResults() {
		return totalResults;
	}
	
	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}
	
	public String getSolrQuery() {
		return solrQuery;
	}
	
	public void setSolrQuery(String solrQuery) {
		this.solrQuery = solrQuery;
	}
	
	public List<String> getFacets() {
		return new ArrayList<String>(facets.keySet());
	}
	
	public List<String> getFacetValues(String facet) {
		if(this.hasFacet(facet))
			return facets.get(facet);
		
		return new ArrayList<String>();
	}
	
	public Long getCountFacet(String facetName, String facetValue) {
		if(!this.hasFacet(facetName))
			return -1L;
		
		int idx = facets.get(facetName).indexOf(facetValue);
		
		if(idx < 0)
			return -1L;
		
		return countFacets.get(facetName).get(idx);
	}
	
	public boolean hasFacet(String name) {
		return facets.containsKey(name);
	}
	
	public void addFacetValue(String name, String value, Long numDocs) {
		if(!this.hasFacet(name)) {
			facets.put(name, new ArrayList<String>());
			countFacets.put(name, new ArrayList<Long>());
		}
		
		facets.get(name).add(value);
		countFacets.get(name).add(numDocs);
	}
}
