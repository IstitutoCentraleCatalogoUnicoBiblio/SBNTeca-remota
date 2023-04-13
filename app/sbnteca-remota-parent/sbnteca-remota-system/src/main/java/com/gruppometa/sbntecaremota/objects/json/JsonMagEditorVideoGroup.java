package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorVideoGroup implements Serializable {
	private static final long serialVersionUID = -5390705824166639542L;

	// id
	private String id;
	
	// dimensione video
	@JsonProperty("videosize")
	private String videoSize;
	
	// aspect ratio
	@JsonProperty("aspectratio")
	private String aspectRatio;
	
	// frame rate
	@JsonProperty("framerate")
	private String frameRate;

	// nome formato
	@JsonProperty("format_name")
	private String formatName;
	
	// mime formato
	@JsonProperty("format_mime")
	private String formatMime;
	
	// formato video
	@JsonProperty("videoformat")
	private String formatVideo;
	
	// codifica formato
	@JsonProperty("encode")
	private String formatEncode;
	
	// tipo di stream video
	@JsonProperty("streamtype")
	private String formatStreamType;
	
	// codec
	@JsonProperty("codec")
	private String formatCodec;

	// tipo sorgente
	@JsonProperty("sourcetype")
	private String sourceType;
	
	// agenzia
	@JsonProperty("transcriptionagency")
	private String transcriptionAgency;
	
	// data
	@JsonProperty("transcriptiondate")
	private String transcriptionDate;
	
	// tipo dispositivo
	@JsonProperty("devicesource")
	private String deviceSource;
	
	// lista chains
	@JsonProperty("transcriptionchain")
	private List<JsonMagEditorTranscriptionChain> chains = 
			new ArrayList<JsonMagEditorTranscriptionChain>();
	
	
	@JsonProperty("transcriptionsummary")
	private List<JsonMagEditorTranscriptionSummary> summaries = 
			new ArrayList<JsonMagEditorTranscriptionSummary>();
	

	@JsonProperty("transcriptiondata")
	private List<JsonMagEditorTranscriptionData> data = 
			new ArrayList<JsonMagEditorTranscriptionData>();
	
	
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getVideoSize() {
		return videoSize;
	}
	
	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}
	
	public String getAspectRatio() {
		return aspectRatio;
	}
	
	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
	
	public String getFrameRate() {
		return frameRate;
	}
	
	public void setFrameRate(String frameRate) {
		this.frameRate = frameRate;
	}

	public String getFormatName() {
		return formatName;
	}
	
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public String getFormatMime() {
		return formatMime;
	}
	
	public void setFormatMime(String formatMime) {
		this.formatMime = formatMime;
	}
	
	public String getFormatVideo() {
		return formatVideo;
	}
	
	public void setFormatVideo(String formatVideo) {
		this.formatVideo = formatVideo;
	}

	public String getFormatEncode() {
		return formatEncode;
	}
	
	public void setFormatEncode(String formatEncode) {
		this.formatEncode = formatEncode;
	}

	public String getFormatStreamType() {
		return formatStreamType;
	}
	
	public void setFormatStreamType(String formatStreamType) {
		this.formatStreamType = formatStreamType;
	}
	
	public String getFormatCodec() {
		return formatCodec;
	}
	
	public void setFormatCodec(String formatCodec) {
		this.formatCodec = formatCodec;
	}

	public String getSourceType() {
		return sourceType;
	}
	
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	public String getTranscriptionAgency() {
		return transcriptionAgency;
	}
	
	public void setTranscriptionAgency(String transcriptionAgency) {
		this.transcriptionAgency = transcriptionAgency;
	}

	public String getTranscriptionDate() {
		return transcriptionDate;
	}
	
	public void setTranscriptionDate(String transcriptionDate) {
		this.transcriptionDate = transcriptionDate;
	}
	
	public String getDeviceSource() {
		return deviceSource;
	}
	
	public void setDeviceSource(String deviceSource) {
		this.deviceSource = deviceSource;
	}
	
	public List<JsonMagEditorTranscriptionChain> getChains() {
		return chains;
	}
	
	public void setChains(List<JsonMagEditorTranscriptionChain> chains) {
		this.chains = chains;
	}

	public List<JsonMagEditorTranscriptionSummary> getSummaries() {
		return summaries;
	}
	
	public void setSummaries(List<JsonMagEditorTranscriptionSummary> summaries) {
		this.summaries = summaries;
	}

	public List<JsonMagEditorTranscriptionData> getData() {
		return data;
	}
	
	public void setData(List<JsonMagEditorTranscriptionData> data) {
		this.data = data;
	}

}
