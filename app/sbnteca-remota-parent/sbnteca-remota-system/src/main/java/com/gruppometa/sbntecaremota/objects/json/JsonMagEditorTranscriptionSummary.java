package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorTranscriptionSummary implements Serializable {
	private static final long serialVersionUID = 5675447999068387560L;
	
	// raggruppamento
	private String grouping;
	
	// lista ricorsiva
	@JsonProperty("transcriptionsummary")
	private List<JsonMagEditorTranscriptionSummary> summaries = 
			new ArrayList<JsonMagEditorTranscriptionSummary>();
	
	
	// descrizione
	@JsonProperty("data_description")
	private String dataDescription;
	
	// unità
	@JsonProperty("data_unit")
	private String dataUnit;
	
	// valore
	@JsonProperty("data_value")
	private String dataValue;
	
	
	
	public String getGrouping() {
		return grouping;
	}
	
	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}
	
	public List<JsonMagEditorTranscriptionSummary> getSummaries() {
		return summaries;
	}
	
	public void setSummaries(List<JsonMagEditorTranscriptionSummary> summaries) {
		this.summaries = summaries;
	}
	
	public String getDataDescription() {
		return dataDescription;
	}
	
	public void setDataDescription(String dataDescription) {
		this.dataDescription = dataDescription;
	}

	public String getDataUnit() {
		return dataUnit;
	}
	
	public void setDataUnit(String dataUnit) {
		this.dataUnit = dataUnit;
	}

	public String getDataValue() {
		return dataValue;
	}
	
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

}
