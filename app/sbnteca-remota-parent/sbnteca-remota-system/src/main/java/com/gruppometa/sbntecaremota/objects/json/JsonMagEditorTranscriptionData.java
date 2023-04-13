package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorTranscriptionData implements Serializable {
	private static final long serialVersionUID = 7150694493293161714L;

	// raggruppamento
	private String grouping;
	
	// lista ricorsiva
	@JsonProperty("transcriptiondata")
	private List<JsonMagEditorTranscriptionData> data = 
			new ArrayList<JsonMagEditorTranscriptionData>();
	
	
	// descrizione
	@JsonProperty("data_description")
	private String dataDescription;
	
	// unità
	@JsonProperty("data_unit")
	private String dataUnit;
	
	// valore
	@JsonProperty("data_value")
	private String dataValue;
	
	// intervallo inizio
	@JsonProperty("interval_start")
	private String intervalStart;
	
	// intervallo fine
	@JsonProperty("interval_stop")
	private String intervalStop;
	
	
	
	public String getGrouping() {
		return grouping;
	}
	
	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}
	
	public List<JsonMagEditorTranscriptionData> getData() {
		return data;
	}
	
	public void setData(List<JsonMagEditorTranscriptionData> data) {
		this.data = data;
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
	
	public String getIntervalStart() {
		return intervalStart;
	}
	
	public void setIntervalStart(String intervalStart) {
		this.intervalStart = intervalStart;
	}

	public String getIntervalStop() {
		return intervalStop;
	}
	
	public void setIntervalStop(String intervalStop) {
		this.intervalStop = intervalStop;
	}

}
