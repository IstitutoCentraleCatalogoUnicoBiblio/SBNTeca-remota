package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagEditorGen implements Serializable {
	private static final long serialVersionUID = -8234609889152100615L;
	
	// progetto
	private String stprog;
	
	// collezione
	private String collection;
	
	// agenzia
	private String agency;
	
	// diritti accesso
	@JsonProperty("access_rights")
	private String accessRights;
	
	// completezza
	private String completeness;
	
	// gruppi immagini
	@JsonProperty("img_group")
	private List<JsonMagEditorImgGroup> imgGroups = new ArrayList<JsonMagEditorImgGroup>();

	// gruppi audio
	@JsonProperty("audio_group")
	private List<JsonMagEditorAudioGroup> audioGroups = new ArrayList<JsonMagEditorAudioGroup>();

	// gruppi video
	@JsonProperty("video_group")
	private List<JsonMagEditorVideoGroup> videoGroups = new ArrayList<JsonMagEditorVideoGroup>();
	
	
	
	public String getStprog() {
		return stprog;
	}
	
	public void setStprog(String stprog) {
		this.stprog = stprog;
	}
	
	public String getCollection() {
		return collection;
	}
	
	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	public String getAgency() {
		return agency;
	}
	
	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	public String getAccessRights() {
		return accessRights;
	}
	
	public void setAccessRights(String accessRights) {
		this.accessRights = accessRights;
	}
	
	public String getCompleteness() {
		return completeness;
	}
	
	public void setCompleteness(String completeness) {
		this.completeness = completeness;
	}
	
	public List<JsonMagEditorImgGroup> getImgGroups() {
		return imgGroups;
	}
	
	public void setImgGroups(List<JsonMagEditorImgGroup> imgGroups) {
		this.imgGroups = imgGroups;
	}

	public List<JsonMagEditorAudioGroup> getAudioGroups() {
		return audioGroups;
	}
	
	public void setAudioGroups(List<JsonMagEditorAudioGroup> audioGroups) {
		this.audioGroups = audioGroups;
	}

	public List<JsonMagEditorVideoGroup> getVideoGroups() {
		return videoGroups;
	}
	
	public void setVideoGroups(List<JsonMagEditorVideoGroup> videoGroups) {
		this.videoGroups = videoGroups;
	}

}
