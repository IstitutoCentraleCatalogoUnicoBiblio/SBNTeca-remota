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
public class JsonDigitalObjectStatsRequest implements Serializable {
	private static final long serialVersionUID = -1780875151367029271L;

	// lista entries
	@JsonProperty("ricerca")
	private Map<String, List<String>> fieldMap = new HashMap<String, List<String>>();

	// lista entries
	@JsonProperty("raggruppamenti")
	private List<String> group = new ArrayList<String>();
	
	
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
	 * Restituisce la lista dei campi da raggruppare
	 * 
	 * @return List<String> lista dei campi da raggruppare
	 */
	public List<String> getGroup() {
		return group;
	}
	
	/**
	 * Imposta la lista dei campi da raggruppare
	 * 
	 * @param group lista dei campi da raggruppare
	 */
	public void setGroup(List<String> group) {
		this.group = group;
	}

}
