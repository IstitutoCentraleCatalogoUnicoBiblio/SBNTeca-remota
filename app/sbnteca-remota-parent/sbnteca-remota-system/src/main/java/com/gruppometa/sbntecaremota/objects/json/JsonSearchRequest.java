package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HSON di richiesta ricerca MAG su Solr
 * 
 *
 */
public class JsonSearchRequest implements Serializable {
	private static final long serialVersionUID = -2713694426891743949L;
	
	// lista entries
	@JsonProperty("ricerca")
	private Map<String, List<String>> fieldMap = new HashMap<String, List<String>>();

	// lista entries
	@JsonProperty("filtri_faccette")
	private Map<String, List<String>> facetFilters = new HashMap<String, List<String>>();

	// lista entries
	@JsonProperty("ricerca_date")
	private Map<String, List<String>> fieldDateMap = new HashMap<String, List<String>>();
	
	// lista id da filtrare
	@JsonProperty("id")
	private List<String> ids = new ArrayList<String>();

	// lista id da filtrare
	@JsonProperty("not_id")
	private List<String> notIds = new ArrayList<String>();
	
	// start
	@JsonProperty("inizio")
	private int start = 0;
	
	// lunghezza pagina
	@JsonProperty("lunghezza_risultati")
	private int length = Integer.MAX_VALUE;
	
	
	
	/**
	 * Restituisce la mappa dei campi di ricerca
	 * 
	 * @return mappa dei campi di ricerca
	 */
	public Map<String, List<String>> getFieldMap() {
		return fieldMap;
	}
	
	/**
	 * Imposta la mappa dei campi di ricerca
	 * 
	 * @param fieldMap mappa dei campi di ricerca
	 */
	public void setFieldMap(Map<String, List<String>> fieldMap) {
		this.fieldMap = fieldMap;
	}
	

	/**
	 * Restituisce la mappa dei filtri delle faccette
	 * 
	 * @return mappa dei filtri delle faccette
	 */
	public Map<String, List<String>> getFacetFilters() {
		return facetFilters;
	}
	
	/**
	 * Imposta la mappa dei filtri delle faccette
	 * 
	 * @param facetFilters mappa dei filtri delle faccette
	 */
	public void setFacetFilters(Map<String, List<String>> facetFilters) {
		this.facetFilters = facetFilters;
	}
	
	/**
	 * Restituisce la mappa dei campi di ricerca per intervalli di date
	 * 
	 * @return Map<String, List<String>> mappa dei campi di ricerca per intervalli di date
	 */
	public Map<String, List<String>> getFieldDateMap() {
		return fieldDateMap;
	}
	
	/**
	 * Imposta la mappa dei campi di ricerca per intervalli di date
	 * 
	 * @param fieldDateMap mappa dei campi di ricerca per intervalli di date
	 */
	public void setFieldDateMap(Map<String, List<String>> fieldDateMap) {
		this.fieldDateMap = fieldDateMap;
	}
	
	/**
	 * Restituisce gli ID da filtrare
	 * 
	 * @return List<String> ID da filtrare
	 */
	public List<String> getIds() {
		return ids;
	}
	
	/**
	 * Imposta gli ID da filtrare
	 * 
	 * @param ids ID da filtrare
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**
	 * Restituisce gli ID da escludere
	 * 
	 * @return List<String> ID da escludere
	 */
	public List<String> getNotIds() {
		return notIds;
	}
	
	/**
	 * Imposta gli ID da escludere
	 * 
	 * @param ids ID da escludere
	 */
	public void setNotIds(List<String> notIds) {
		this.notIds = notIds;
	}
	
	/**
	 * Restituisce l'indice di inizio risultati
	 * 
	 * @return int indice di inizio risultati
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Imposta l'indice di inizio risultati
	 * 
	 * @param start indice di inizio risultati
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * Restituisce la lunghezza dei risultati
	 * 
	 * @return int lunghezza dei risultati
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Imposta la lunghezza dei risultati
	 * 
	 * @param length lunghezza dei risultati
	 */
	public void setLength(int length) {
		this.length = length;
	}

}
