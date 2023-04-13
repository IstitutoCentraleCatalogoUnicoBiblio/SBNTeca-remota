package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationVideoGroup implements Serializable {
	private static final long serialVersionUID = -5390705824166639542L;

	// id
	private String id;
	
	// dimensione video
	@JsonProperty("dimensioni_video")
	private String videoSize;
	
	// aspect ratio
	@JsonProperty("rapporto")
	private String aspectRatio;
	
	// frame rate
	@JsonProperty("velocita_frame")
	private String frameRate;

	// nome formato
	@JsonProperty("nome_formato")
	private String formatName;
	
	// mime formato
	@JsonProperty("mime_formato")
	private String formatMime;
	
	// formato video
	@JsonProperty("video_formato")
	private String formatVideo;
	
	// codifica formato
	@JsonProperty("codifica_formato")
	private String formatEncode;
	
	// tipo di stream video
	@JsonProperty("tipo_stream_formato")
	private String formatStreamType;
	
	// codec
	@JsonProperty("codec_formato")
	private String formatCodec;
	
	
	

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

}
