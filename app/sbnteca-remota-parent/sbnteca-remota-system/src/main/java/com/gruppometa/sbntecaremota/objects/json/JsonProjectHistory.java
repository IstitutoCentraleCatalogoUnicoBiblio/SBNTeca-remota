package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonProjectHistory implements Serializable {
	private static final long serialVersionUID = 8978458794153414086L;
	
	// username
	private String username;
	
	// timestamp
	@JsonProperty("data")
	private String timestamp;
	
	// tipo operazione
	@JsonProperty("operazione")
	private String operationType;
	
	// job id
	@JsonProperty("id_job")
	private String jobID;
	
	// mag processati
	@JsonProperty("mag_elaborati")
	private int processedMag;
	
	// mag indicizzati
	@JsonProperty("mag_indicizzati")
	private int indexedMag;
	
	

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getJobID() {
		return jobID;
	}
	
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public int getIndexedMag() {
		return this.indexedMag;
	}

	public void setIndexedMag(int indexedMag) {
		this.indexedMag = indexedMag;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public int getProcessedMag() {
		return this.processedMag;
	}

	public void setProcessedMag(int processedMag) {
		this.processedMag = processedMag;
	}

}
