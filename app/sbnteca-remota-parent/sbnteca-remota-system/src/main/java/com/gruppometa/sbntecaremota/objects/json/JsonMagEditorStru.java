package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorStru implements Serializable {
	private static final long serialVersionUID = -1563246246852759505L;
	
	// sequence number
	@JsonProperty("sequence_number")
	private String sequenceNumber;
	
	// nomenclatura
	private String nomenclature;
	
	// start
	private String start;

	// stop
	private String stop;
	
	// lista stru
	private List<JsonMagEditorStru> stru = new ArrayList<JsonMagEditorStru>();

	// lista element
	private List<JsonMagEditorStruElement> element = new ArrayList<JsonMagEditorStruElement>();
	
	
	
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	public String getNomenclature() {
		return nomenclature;
	}
	
	public void setNomenclature(String nomenclature) {
		this.nomenclature = nomenclature;
	}
	
	public String getStart() {
		return start;
	}
	
	public void setStart(String start) {
		this.start = start;
	}

	public String getStop() {
		return stop;
	}
	
	public void setStop(String stop) {
		this.stop = stop;
	}
	
	public List<JsonMagEditorStru> getStru() {
		return stru;
	}
	
	public void setChildren(List<JsonMagEditorStru> stru) {
		this.stru = stru;
	}

	public List<JsonMagEditorStruElement> getElement() {
		return element;
	}
	
	public void setElement(List<JsonMagEditorStruElement> element) {
		this.element = element;
	}

}
