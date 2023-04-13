package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagDetailStru implements Serializable {
	private static final long serialVersionUID = -1563246246852759505L;
	
	// sequence number
	@JsonProperty("sequence_number")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sequenceNumber;
	
	// nomenclatura
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nomenclature;
	
	// start
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String start;

	// stop
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String stop;
	
	// lista stru
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailStru> stru = new ArrayList<JsonMagDetailStru>();

	// lista element
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailStruElement> element = new ArrayList<JsonMagDetailStruElement>();
	
	
	
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
	
	public List<JsonMagDetailStru> getStru() {
		return stru;
	}
	
	public void setChildren(List<JsonMagDetailStru> stru) {
		this.stru = stru;
	}

	public List<JsonMagDetailStruElement> getElement() {
		return element;
	}
	
	public void setElement(List<JsonMagDetailStruElement> element) {
		this.element = element;
	}

}
