package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagDetailVideoGroup implements Serializable {
	private static final long serialVersionUID = -5390705824166639542L;

	// id
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String id;
	
	// dimensione video
	@JsonProperty("videosize")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String videoSize;
	
	// aspect ratio
	@JsonProperty("aspectratio")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String aspectRatio;
	
	// frame rate
	@JsonProperty("framerate")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String frameRate;

	// nome formato
	@JsonProperty("format_name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatName;
	
	// mime formato
	@JsonProperty("format_mime")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatMime;
	
	// formato video
	@JsonProperty("videoformat")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatVideo;
	
	// codifica formato
	@JsonProperty("encode")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatEncode;
	
	// tipo di stream video
	@JsonProperty("streamtype")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatStreamType;
	
	// codec
	@JsonProperty("codec")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatCodec;

	// tipo sorgente
	@JsonProperty("sourcetype")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sourceType;
	
	// agenzia
	@JsonProperty("transcriptionagency")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String transcriptionAgency;
	
	// data
	@JsonProperty("transcriptiondate")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String transcriptionDate;
	
	// tipo dispositivo
	@JsonProperty("devicesource")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String deviceSource;
	
	
	

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

}
