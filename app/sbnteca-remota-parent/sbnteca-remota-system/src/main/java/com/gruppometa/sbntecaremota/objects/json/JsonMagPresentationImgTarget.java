package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationImgTarget implements Serializable {
	private static final long serialVersionUID = -8940377146173251066L;
	
	// tipo
	@JsonProperty("tipo")
	private String targetType;
	
	// id
	@JsonProperty("id")
	private String targetID;
	
	// image data
	@JsonProperty("immagine")
	private String imageData;
	
	// performance data
	@JsonProperty("performance")
	private String performanceData;
	
	// profili
	@JsonProperty("profili")
	private String profiles;
	
	
	
	public String getTargetType() {
		return targetType;
	}
	
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
	public String getTargetID() {
		return targetID;
	}
	
	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}
	
	public String getImageData() {
		return imageData;
	}
	
	public void setImageData(String imageData) {
		this.imageData = imageData;
	}
	
	public String getPerformanceData() {
		return performanceData;
	}
	
	public void setPerformanceData(String performanceData) {
		this.performanceData = performanceData;
	}
	
	public String getProfiles() {
		return profiles;
	}
	
	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

}
