package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagEditorResource implements Serializable {
	private static final long serialVersionUID = -5060952348244890592L;
	
	// id
	private String id;
	
	// added
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	private boolean added = false;
	
	// updated
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	private boolean updated = false;
	
	// deleted
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	private boolean deleted = false;
	
	// ID holdings
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String holdingsID;
	
	// sequence number
	@JsonProperty("sequence_number")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sequenceNumber;
	
	// nomenclatura
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nomenclature;

	// file
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String file;
	
	// posti immagine
	@JsonProperty("side")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageSide;
	
	// scala immagine
	@JsonProperty("scale")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageScale;

	// lista target
	@JsonProperty("target")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagEditorImgTarget> targets = new ArrayList<JsonMagEditorImgTarget>();
	
	// note
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String note;

	// versioni per usage
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("usage")
	private Map<Integer, JsonMagEditorUsageResource> versions = new HashMap<Integer, JsonMagEditorUsageResource>();
	
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isAdded() {
		return added;
	}
	
	public void setAdded(boolean added) {
		this.added = added;
	}
	
	public boolean isUpdated() {
		return updated;
	}
	
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
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

	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}

	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	public String getImageSide() {
		return imageSide;
	}
	
	public void setImageSide(String imageSide) {
		this.imageSide = imageSide;
	}

	public String getImageScale() {
		return imageScale;
	}
	
	public void setImageScale(String imageScale) {
		this.imageScale = imageScale;
	}

	public List<JsonMagEditorImgTarget> getTargets() {
		return targets;
	}
	
	public void setTargets(List<JsonMagEditorImgTarget> targets) {
		this.targets = targets;
	}

	public String getHoldingsID() {
		return holdingsID;
	}
	
	public void setHoldingsID(String holdingsID) {
		this.holdingsID = holdingsID;
	}
	
	public Map<Integer, JsonMagEditorUsageResource> getVersions() {
		return versions;
	}
	
	public void setVersions(Map<Integer, JsonMagEditorUsageResource> versions) {
		this.versions = versions;
	}

}
