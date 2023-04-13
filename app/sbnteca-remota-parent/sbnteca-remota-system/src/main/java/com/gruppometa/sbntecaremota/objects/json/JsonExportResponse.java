package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonExportResponse implements Serializable {
	private static final long serialVersionUID = -8803794516904280328L;
	
	// nome del file creato
	@JsonProperty("nome_file")
	private String filename;
	
	// stato
	@JsonProperty("stato")
	private int status;
	
	// messaggio
	@JsonProperty("messaggio")
	private String message;
	
	// lista MAG esportati
	@JsonProperty("mag_esportati")
	private List<String> exportedMags = new ArrayList<String>();

	// lista MAG esportati
	@JsonProperty("mag_non_esportati")
	private List<String> notExportedMags = new ArrayList<String>();
	
	
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<String> getExportedMags() {
		return exportedMags;
	}
	
	public void setExportedMags(List<String> exportedMags) {
		this.exportedMags = exportedMags;
	}

	public List<String> getNotExportedMags() {
		return notExportedMags;
	}
	
	public void setNotExportedMags(List<String> notExportedMags) {
		this.notExportedMags = notExportedMags;
	}

}
