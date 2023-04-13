package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagDetailPiece implements Serializable {
	private static final long serialVersionUID = -2643202406314362590L;
	
	// anno
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String year;
	
	// pubblicazione
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String issue;
	
	// periodicità
	@JsonProperty("stpiece_per")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String stpiecePer;
	
	// numero di unità
	@JsonProperty("part_number")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String partNumber;
	
	// nome unità
	@JsonProperty("part_name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String partName;
	
	// volume
	@JsonProperty("stpiece_vol")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String stpieceVol;
	
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	} 
	
	public String getIssue() {
		return issue;
	}
	
	public void setIssue(String issue) {
		this.issue = issue;
	}
	
	public String getStpiecePer() {
		return stpiecePer;
	}
	
	public void setStpiecePer(String stpiecePer) {
		this.stpiecePer = stpiecePer;
	}
	
	public String getPartNumber() {
		return partNumber;
	}
	
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	} 
	
	public String getPartName() {
		return partName;
	}
	
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	public String getStpieceVol() {
		return stpieceVol;
	}
	
	public void setStpieceVol(String stpieceVol) {
		this.stpieceVol = stpieceVol;
	}

}
