package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagDetailImgTarget implements Serializable {
	private static final long serialVersionUID = -8940377146173251066L;

	// tipo
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String targetType;
	
	// id
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String targetID;
	
	// image data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageData;
	
	// performance data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String performanceData;
	
	// profili
	@JsonInclude(JsonInclude.Include.NON_NULL)
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
