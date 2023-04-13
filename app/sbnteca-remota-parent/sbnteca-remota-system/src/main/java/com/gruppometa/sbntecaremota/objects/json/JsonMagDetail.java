package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagDetail implements Serializable {
	private static final long serialVersionUID = 9031395085953601549L;
	
	// gen
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<JsonMagDetailField> metadata = new ArrayList<JsonMagDetailField>();
	
	// stru
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailStru> stru = new ArrayList<JsonMagDetailStru>();
	
	// lista img
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailResource> img = new ArrayList<JsonMagDetailResource>();

	// lista audio
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailResource> audio = new ArrayList<JsonMagDetailResource>();

	// lista video
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailResource> video = new ArrayList<JsonMagDetailResource>();

	// lista ocr
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailResource> ocr = new ArrayList<JsonMagDetailResource>();

	// lista doc
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailResource> doc = new ArrayList<JsonMagDetailResource>();
	
	

	public List<JsonMagDetailField> getMetadata() {
		return metadata;
	}
	
	public void setMetadata(List<JsonMagDetailField> metadata) {
		this.metadata = metadata;
	}
	
	public List<JsonMagDetailStru> getStru() {
		return stru;
	}
	
	public void setStru(List<JsonMagDetailStru> stru) {
		this.stru = stru;
	}
	
	public List<JsonMagDetailResource> getImg() {
		return img;
	}
	
	public void setImg(List<JsonMagDetailResource> img) {
		this.img = img;
	}

	public List<JsonMagDetailResource> getAudio() {
		return audio;
	}
	
	public void setAudio(List<JsonMagDetailResource> audio) {
		this.audio = audio;
	}

	public List<JsonMagDetailResource> getVideo() {
		return video;
	}
	
	public void setVideo(List<JsonMagDetailResource> video) {
		this.video = video;
	}

	public List<JsonMagDetailResource> getOcr() {
		return ocr;
	}
	
	public void setOcr(List<JsonMagDetailResource> ocr) {
		this.ocr = ocr;
	}

	public List<JsonMagDetailResource> getDoc() {
		return doc;
	}
	
	public void setDoc(List<JsonMagDetailResource> doc) {
		this.doc = doc;
	}

}
