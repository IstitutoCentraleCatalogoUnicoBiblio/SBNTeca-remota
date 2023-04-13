package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationGen implements Serializable {
	private static final long serialVersionUID = -8234609889152100615L;
	
	// creazione
	@JsonProperty("data_creazione")
	private String creation;
	
	// ultima modifica
	@JsonProperty("ultima_modifica")
	private String lastUpdate;
	
	// progetto
	private String stprog;
	
	// collezione
	@JsonProperty("collezione")
	private String collection;
	
	// agenzia
	@JsonProperty("agenzia")
	private String agency;
	
	// diritti accesso
	@JsonProperty("accesso")
	private String accessRights;
	
	// completezza
	@JsonProperty("completezza")
	private String completeness;
	
	// gruppi immagini
	@JsonProperty("gruppi_immagini")
	private List<JsonMagPresentationImgGroup> imgGroups = new ArrayList<JsonMagPresentationImgGroup>();

	// gruppi audio
	@JsonProperty("gruppi_audio")
	private List<JsonMagPresentationAudioGroup> audioGroups = new ArrayList<JsonMagPresentationAudioGroup>();

	// gruppi video
	@JsonProperty("gruppi_video")
	private List<JsonMagPresentationVideoGroup> videoGroups = new ArrayList<JsonMagPresentationVideoGroup>();
	
	
	
	public String getCreation() {
		return creation;
	}
	
	public void setCreation(String creation) {
		this.creation = creation;
	}
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
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
	
	public List<JsonMagPresentationImgGroup> getImgGroups() {
		return imgGroups;
	}
	
	public void setImgGroups(List<JsonMagPresentationImgGroup> imgGroups) {
		this.imgGroups = imgGroups;
	}

	public List<JsonMagPresentationAudioGroup> getAudioGroups() {
		return audioGroups;
	}
	
	public void setAudioGroups(List<JsonMagPresentationAudioGroup> audioGroups) {
		this.audioGroups = audioGroups;
	}

	public List<JsonMagPresentationVideoGroup> getVideoGroups() {
		return videoGroups;
	}
	
	public void setVideoGroups(List<JsonMagPresentationVideoGroup> videoGroups) {
		this.videoGroups = videoGroups;
	}

}
