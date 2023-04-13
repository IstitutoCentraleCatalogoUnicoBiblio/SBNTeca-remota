package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationImport implements Serializable {
	private static final long serialVersionUID = -4870557023700858352L;
	
	// progetto
	@JsonProperty("progetto")
	private String project;
	
	// ultima modifica
	@JsonProperty("ultima_modifica")
	private String lastModified;
	
	// pubblicazione
	@JsonProperty("pubblicazione")
	private String publicFlag;

	@JsonProperty("oaiidentifier")
	private String oaiIdentifier;

	public String getOaiIdentifier() {
		return oaiIdentifier;
	}

	public void setOaiIdentifier(String oaiIdentifier) {
		this.oaiIdentifier = oaiIdentifier;
	}

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
