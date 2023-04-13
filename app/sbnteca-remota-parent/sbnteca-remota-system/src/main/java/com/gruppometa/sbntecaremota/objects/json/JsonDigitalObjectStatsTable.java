package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonDigitalObjectStatsTable implements Serializable {
	private static final long serialVersionUID = -7972088804156835609L;
	
	// lista campi
	@JsonProperty("raggruppamenti")
	private List<String> fields = new ArrayList<String>();
	
	// lista righe tabelle
	@JsonProperty("record")
	private List<JsonDigitalObjectStatsRow> rows = new ArrayList<JsonDigitalObjectStatsRow>();
	
	
	
	public List<String> getFields() {
		return fields;
	}
	
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	public List<JsonDigitalObjectStatsRow> getRows() {
		return rows;
	}
	
	public void setRows(List<JsonDigitalObjectStatsRow> rows) {
		this.rows = rows;
	}
	
}
