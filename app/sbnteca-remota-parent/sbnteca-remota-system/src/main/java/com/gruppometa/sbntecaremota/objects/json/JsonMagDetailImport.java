package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagDetailImport implements Serializable {
	private static final long serialVersionUID = -4870557023700858352L;
	
	// progetto
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String project;
	
	// ultima modifica
	@JsonProperty("last_modified")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastModified;
	
	// pubblicazione
	@JsonProperty("public_flag")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String publicFlag;
	
	
	
	public String geProject() {
		return project;
	}
	
	public void setProject(String project) {
		this.project = project;
	}
	
	public String getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	
	public String getPublicFlag() {
		return publicFlag;
	}
	
	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

}
