package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagPresentationStru implements Serializable {
	private static final long serialVersionUID = -1563246246852759505L;
	
	public static final String TYPE_STRU = "stru";
	public static final String TYPE_ELEMENT = "element";
	
	// sequence number
	@JsonProperty("numero_sequenza")
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private String sequenceNumber;
	
	// nomenclatura
	@JsonProperty("nomenclatura")
	private String nomenclature;
	
	// tipo
	@JsonProperty("tipo")
	private String type = TYPE_STRU;
	
	// identifier
	@JsonProperty("identifier")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String identifier;

	// piece
	@JsonProperty("unita")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JsonMagPresentationPiece piece;
	
	// file
	@JsonProperty("file")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String file;

	// tipo risorse
	@JsonProperty("tipo_risorse")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String resourceType;
	
	// start
	@JsonProperty("start")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String start;

	// stop
	@JsonProperty("stop")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String stop;
	
	// lista stru
	@JsonProperty("figli")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagPresentationStru> children = new ArrayList<JsonMagPresentationStru>();
	
	
	
	
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public JsonMagPresentationPiece getPiece() {
		return piece;
	}
	
	public void setPiece(JsonMagPresentationPiece piece) {
		this.piece = piece;
	}
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getResourceType() {
		return resourceType;
	}
	
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
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
	
	public List<JsonMagPresentationStru> getChildren() {
		return children;
	}
	
	public void setChildren(List<JsonMagPresentationStru> children) {
		this.children = children;
	}

}
