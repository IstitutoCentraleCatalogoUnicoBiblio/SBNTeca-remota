package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class JsonEditorSaveResponse implements Serializable {
	private static final long serialVersionUID = 4234368624858592195L;
	
	// id
	private String id;
	
	// salvato
	@JsonProperty("stato")
	private boolean saved;
	
	// errore
	@JsonProperty("errore")
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private String error;
	
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isSaved() {
		return saved;
	}
	
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}

}
