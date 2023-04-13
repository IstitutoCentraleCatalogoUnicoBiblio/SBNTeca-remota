package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchData implements Serializable {
	private static final long serialVersionUID = 1696046753322105675L;
	
	// constants for including draft/deleted
	public static final int ONLY = 1;
	public static final int INCLUDE = 2;
	public static final int EXCLUDE = 3;
	
	// constants for filtering fields
	public static final int ID = 10;
	public static final int PREVIEW = 11;
	public static final int UPDATE_DOC = 12;
	public static final int NORMALIZE = 13;
	public static final int ALL = 14;
	
	// constants for faceting type
	public static final int FACET = 21;
	public static final int STRING = 22;
	
	// ricerca base
	private Map<String, List<String>> baseSearch = 
			new HashMap<String, List<String>>();
	
	// ricerca faccette
	private Map<String, List<String>> facetSearch = 
			new HashMap<String, List<String>>();
	
	// ricerca base
	private Map<String, List<String>> dateIntervalSearch = 
			new HashMap<String, List<String>>();
	
	// lista id filtrati
	private List<String> filteredIDs = new ArrayList<String>();
	
	// lista id da escludere
	private List<String> excludedIDs = new ArrayList<String>();
	
	// filtri campi
	private int filterDoc = ALL;
	
	// includi draft
	private int includeDrafts;
	
	// includi cancellati
	private int includeDeleted;
	
	// faccette
	private boolean facets;
	
	// ordina per
	private String sort;
	
	// tipo di faccette
	private int facetType = FACET;
	
	// campi faccette
	private List<String> facetTypeFields = new ArrayList<String>();
	
	
	
	
	public Map<String, List<String>> getBaseSearch() {
		return baseSearch;
	}
	
	public void setBaseSearch(Map<String, List<String>> baseSearch) {
		this.baseSearch = baseSearch;
	}
	
	public Map<String, List<String>> getFacetSearch() {
		return facetSearch;
	}
	
	public void setFacetSearch(Map<String, List<String>> facetSearch) {
		this.facetSearch = facetSearch;
	}
	
	public Map<String, List<String>> getDateIntervalSearch() {
		return dateIntervalSearch;
	}
	
	public void setDateIntervalSearch(Map<String, List<String>> dateIntervalSearch) {
		this.dateIntervalSearch = dateIntervalSearch;
	}
	
	public List<String> getFilteredIDs() {
		return filteredIDs;
	}
	
	public void setFilteredIDs(List<String> filteredIDs) {
		this.filteredIDs = filteredIDs;
	}

	public List<String> getExcludedIDs() {
		return excludedIDs;
	}
	
	public void setExcludedIDs(List<String> excludedIDs) {
		this.excludedIDs = excludedIDs;
	}
	
	public int getFilterDoc() {
		return filterDoc;
	}
	
	public void setFilterDoc(int filterDoc) {
		this.filterDoc = filterDoc;
	}
	
	public boolean hasFacets() {
		return facets;
	}
	
	public void setFacets(boolean facets) {
		this.facets = facets;
	}
	
	public int getIncludeDrafts() {
		return includeDrafts;
	}
	
	public void setIncludeDrafts(int includeDrafts) {
		this.includeDrafts = includeDrafts;
	}

	public int getIncludeDeleted() {
		return includeDeleted;
	}
	
	public void setIncludeDeleted(int includeDeleted) {
		this.includeDeleted = includeDeleted;
	}
	
	public String getSort() {
		return sort;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public int getFacetType() {
		return facetType;
	}
	
	public void setFacetType(int facetType) {
		this.facetType = facetType;
	}
	
	public List<String> getFacetTypeFields() {
		return facetTypeFields;
	}
	
	public void setFacetTypeFields(List<String> facetTypeFields) {
		this.facetTypeFields = facetTypeFields;
	}

}
