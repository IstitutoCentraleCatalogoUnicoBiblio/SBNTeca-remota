package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagEditorContent implements Serializable {
	private static final long serialVersionUID = 9031395085953601549L;
	
	// project
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private String project;
	
	// gen
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private JsonMagEditorGen gen;
	
	// bib
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JsonMagEditorBib bib;

	// stru
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagEditorStru> stru = new ArrayList<JsonMagEditorStru>();

	// gruppi img
	@JsonProperty("img_groups")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> imgGroups = new ArrayList<String>();
	
	// mappa gruppi img
	@JsonProperty("img_group_objects")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, JsonMagEditorImgGroup> imgGroupMap = 
			new HashMap<String, JsonMagEditorImgGroup>();

	// gruppi audio
	@JsonProperty("audio_groups")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> audioGroups = new ArrayList<String>();

	// mappa gruppi audio
	@JsonProperty("audio_group_objects")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, JsonMagEditorAudioGroup> audioGroupMap = 
			new HashMap<String, JsonMagEditorAudioGroup>();
	
	// gruppi img
	@JsonProperty("video_groups")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> videoGroups = new ArrayList<String>();

	// mappa gruppi video
	@JsonProperty("video_group_objects")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, JsonMagEditorVideoGroup> videoGroupMap = 
			new HashMap<String, JsonMagEditorVideoGroup>();

	// img
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagEditorResource> img = new ArrayList<JsonMagEditorResource>();

	// audio
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagEditorResource> audio = new ArrayList<JsonMagEditorResource>();

	// video
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagEditorResource> video = new ArrayList<JsonMagEditorResource>();

	// ocr 
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagEditorResource> ocr = new ArrayList<JsonMagEditorResource>();

	// doc
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagEditorResource> doc = new ArrayList<JsonMagEditorResource>();
	
	// dis
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JsonMagEditorDis dis;

	// warnings
	@JsonProperty("warning")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> warnings = new ArrayList<String>();
	
	
	
	public String getProject() {
		return project;
	}
	
	public void setProject(String project) {
		this.project = project;
	}
	
	public JsonMagEditorGen getGen() {
		return gen;
	}
	
	public void setGen(JsonMagEditorGen gen) {
		this.gen = gen;
	}

	public JsonMagEditorBib getBib() {
		return bib;
	}
	
	public void setBib(JsonMagEditorBib bib) {
		this.bib = bib;
	}

	public List<JsonMagEditorStru> getStru() {
		return stru;
	}
	
	public void setStru(List<JsonMagEditorStru> stru) {
		this.stru = stru;
	}
	
	public List<JsonMagEditorResource> getImg() {
		return img;
	}
	
	public List<String> getImgGroups() {
		return imgGroups;
	}
	
	public void setImgGroups(List<String> imgGroups) {
		this.imgGroups = imgGroups;
	}
	
	public Map<String, JsonMagEditorImgGroup> getImgGroupMap() {
		return imgGroupMap;
	}
	
	public void setImgGroupMap(Map<String, JsonMagEditorImgGroup> imgGroupMap) {
		this.imgGroupMap = imgGroupMap;
	}
	
	public void setImg(List<JsonMagEditorResource> img) {
		this.img = img;
	}

	public List<String> getAudioGroups() {
		return audioGroups;
	}
	
	public void setAudioGroups(List<String> audioGroups) {
		this.audioGroups = audioGroups;
	}

	public Map<String, JsonMagEditorAudioGroup> getAudioGroupMap() {
		return audioGroupMap;
	}
	
	public void setAudioGroupMap(Map<String, JsonMagEditorAudioGroup> audioGroupMap) {
		this.audioGroupMap = audioGroupMap;
	}
	
	public List<JsonMagEditorResource> getAudio() {
		return audio;
	}
	
	public void setAudio(List<JsonMagEditorResource> audio) {
		this.audio = audio;
	}

	public List<String> getVideoGroups() {
		return videoGroups;
	}
	
	public void setVideoGroups(List<String> videoGroups) {
		this.videoGroups = videoGroups;
	}

	public Map<String, JsonMagEditorVideoGroup> getVideoGroupMap() {
		return videoGroupMap;
	}
	
	public void setVideoGroupMap(Map<String, JsonMagEditorVideoGroup> videoGroupMap) {
		this.videoGroupMap = videoGroupMap;
	}
	
	public List<JsonMagEditorResource> getVideo() {
		return video;
	}
	
	public void setVideo(List<JsonMagEditorResource> video) {
		this.video = video;
	}
	
	public List<JsonMagEditorResource> getOcr() {
		return ocr;
	}
	
	public void setOcr(List<JsonMagEditorResource> ocr) {
		this.ocr = ocr;
	}

	public List<JsonMagEditorResource> getDoc() {
		return doc;
	}
	
	public void setDoc(List<JsonMagEditorResource> doc) {
		this.doc = doc;
	}
	
	public JsonMagEditorDis getDis() {
		return dis;
	}
	
	public void setDis(JsonMagEditorDis dis) {
		this.dis = dis;
	}

	public List<String> getWarnings() {
		return warnings;
	}
	
	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

}
