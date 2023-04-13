package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class JsonDraft implements Serializable {
	private static final long serialVersionUID = -3681191102750001231L;
	
	// id
	private String id;
	
	// identifier
	private String identifier = " - ";
	
	// year/part number
	@JsonProperty("year_part_number")
	private String yearPartNumber = " - ";

	// issue/part name
	@JsonProperty("issue_part_name")
	private String issuePartName = " - ";

	@JsonProperty("project")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String project = "";

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	// username
	private String username = " - ";
	
	// ultima modifica
	private String lastModified = " - ";

	private String documentFormat;

	private String vfsId;

	public String getVfsId() {
		return vfsId;
	}

	public void setVfsId(String vfsId) {
		this.vfsId = vfsId;
	}

	public String getDocumentFormat() {
		return documentFormat;
	}

	public void setDocumentFormat(String documentFormat) {
		this.documentFormat = documentFormat;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public String getYearPartNumber() {
		return yearPartNumber;
	}
	
	public void setYearPartNumber(String yearPartNumber) {
		this.yearPartNumber = yearPartNumber;
	}

	public String getIssuePartName() {
		return issuePartName;
	}
	
	public void setIssuePartName(String issuePartName) {
		this.issuePartName = issuePartName;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

}
