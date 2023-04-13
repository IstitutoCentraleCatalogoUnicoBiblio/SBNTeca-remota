package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorStruElement implements Serializable {
	private static final long serialVersionUID = -5897067661824135900L;
	
	// nomenclatura
	private String nomenclature;

	// identifier
	private String identifier;

	// piece
	private List<JsonMagEditorPiece> piece = new ArrayList<JsonMagEditorPiece>();
	
	// file
	private String file;

	// tipo risorse
	@JsonProperty("resource")
	private String resourceType;
	
	// start
	private String start;

	// stop
	private String stop;
	


	public String getNomenclature() {
		return nomenclature;
	}
	
	public void setNomenclature(String nomenclature) {
		this.nomenclature = nomenclature;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<JsonMagEditorPiece> getPiece() {
		return piece;
	}
	
	public void setPiece(List<JsonMagEditorPiece> piece) {
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

}
