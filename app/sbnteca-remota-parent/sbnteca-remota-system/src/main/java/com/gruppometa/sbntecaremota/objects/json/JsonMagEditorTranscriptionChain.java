package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorTranscriptionChain implements Serializable {
	private static final long serialVersionUID = -4105327613273394984L;
	

	// tipo
	@JsonProperty("Type")
	private String type;
	
	// identificativo unico
	@JsonProperty("Unique_identifier")
	private String uniqueIdentifier;
	
	// commenti
	@JsonProperty("Comments")
	private String comments;
	
	// produttore
	@JsonProperty("device_manufacturer")
	private String deviceManufacturer;
	
	// model
	@JsonProperty("Model")
	private String model;
	
	// serial number
	@JsonProperty("Serial_Number")
	private String serialNumber;
	
	// software di cattura
	@JsonProperty("capture_software")
	private String captureSoftware;
	
	// impostazioni
	@JsonProperty("device_settings")
	private String deviceSettings;
	
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}
	
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}
	
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getCaptureSoftware() {
		return captureSoftware;
	}
	
	public void setCaptureSoftware(String captureSoftware) {
		this.captureSoftware = captureSoftware;
	}
	
	public String getDeviceSettings() {
		return deviceSettings;
	}
	
	public void setDeviceSettings(String deviceSettings) {
		this.deviceSettings = deviceSettings;
	}

}
