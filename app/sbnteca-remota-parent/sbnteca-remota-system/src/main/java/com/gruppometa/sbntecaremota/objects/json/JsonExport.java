package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;



/**
 * The persistent class for the export database table.
 * 
 */
public class JsonExport implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	@JsonProperty("nome_file")
	private String file;
	
	private String username;

	@JsonProperty("messaggio")
	private String message;

	@JsonProperty("stato")
	private int status;

	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}