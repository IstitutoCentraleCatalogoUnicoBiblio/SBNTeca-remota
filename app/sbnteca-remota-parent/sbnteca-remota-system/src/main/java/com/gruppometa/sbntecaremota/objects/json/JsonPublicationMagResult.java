package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonPublicationMagResult implements Serializable {
	private static final long serialVersionUID = -7881755463262452692L;
	
	// id
	private String id;
	
	// pubblicato
	@JsonProperty("pubblicato")
	private int published;
	
	// message
	@JsonProperty("errore")
	private String error = "";
	
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getPublished() {
		return published;
	}
	
	public void setPublished(int published) {
		this.published = published;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}

}
