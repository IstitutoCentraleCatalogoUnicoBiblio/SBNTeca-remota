package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class JsonResourceInfo implements Serializable {
	private static final long serialVersionUID = 2753128410391447408L;
	
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private String id;
	
	// filesize
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long filesize = null;
	
	// md5
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String md5 = null;
	
	// larghezza immagine
	@JsonProperty("imagewidth")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer width = null;
	
	// altezza immagine
	@JsonProperty("imagelength")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer height = null;
	
	// durata
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String duration = null;
	
	// estensione
	@JsonProperty("format_name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String extension = null;

	// MIME
	@JsonProperty("format_mime")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String mime = null;

	// MIME
	@JsonProperty("datetimecreated")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String datetimecreated = null;
	
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Long getFilesize() {
		return filesize;
	}
	
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	
	public String getMd5() {
		return md5;
	}
	
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public Integer getWidth() {
		return width;
	}
	
	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMime() {
		return mime;
	}
	
	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getDatetimecreated() {
		return datetimecreated;
	}
	
	public void setDatetimecreated(String datetimecreated) {
		this.datetimecreated = datetimecreated;
	}

}
