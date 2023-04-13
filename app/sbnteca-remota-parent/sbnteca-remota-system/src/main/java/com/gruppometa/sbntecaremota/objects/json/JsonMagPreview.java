package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({ "title", "creator", "collection", "identifier",
	"type", "subject", "preview", "id", "format", "year_part_number", 
	"issue_part_name", "level", "public_flag","idContainer" })
public class JsonMagPreview implements Serializable {
	private static final long serialVersionUID = -267684574299600974L;
	
	// ID
	private String id;
	
	// identifiers
	@JsonProperty("identifier")
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
	private List<String> identifiers = new ArrayList<String>();
	
	// titoli
	@JsonProperty("title")
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private List<String> titles = new ArrayList<String>();

	// titoli
	@JsonProperty("creator")
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
	private List<String> creators = new ArrayList<String>();
	
	// soggetti
	@JsonProperty("subject")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> subjects = new ArrayList<String>();

	// soggetti
	@JsonProperty("type")
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
	private List<String> types = new ArrayList<String>();
	
	// titoli
	@JsonProperty("format")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> formats = new ArrayList<String>();
	
	// year/part number
	@JsonProperty("year_part_number")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String yearPartNumber = "";

	public String getDocumentFormat() {
		return documentFormat;
	}

	public void setDocumentFormat(String documentFormat) {
		this.documentFormat = documentFormat;
	}

	@JsonProperty("documentFormat")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String documentFormat = "";

	// issue/part name
	@JsonProperty("issue_part_name")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String issuePartName = "";

	// livello bibliografico
	@JsonProperty("level")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String level = "";

	// collezione
	@JsonProperty("collection")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String collection = "";
	
	// preview
	@JsonProperty("preview")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String preview = "";
	
	// pubblicato
	@JsonProperty("public_flag")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> publicFlags = new ArrayList<String>();

	@JsonProperty("idContainer")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String idContainer = "";

	@JsonProperty("idMag")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String idMag = "";

	@JsonProperty("project")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String project = "";

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getIdMag() {
		return idMag;
	}

	public void setIdMag(String idMag) {
		this.idMag = idMag;
	}

	public String getIdContainer() {
		return idContainer;
	}

	public void setIdContainer(String idContainer) {
		this.idContainer = idContainer;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> getIdentifiers() {
		return identifiers;
	}
	
	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}

	public List<String> getTitles() {
		return titles;
	}
	
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	
	public List<String> getCreators() {
		return creators;
	}
	
	public void setCreators(List<String> creators) {
		this.creators = creators;
	}
	
	public List<String> getSubjects() {
		return subjects;
	}
	
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public List<String> getTypes() {
		return types;
	}
	
	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<String> getFormats() {
		return formats;
	}
	
	public void setFormats(List<String> formats) {
		this.formats = formats;
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
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getCollection() {
		return collection;
	}
	
	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	public String getPreview() {
		return preview;
	}
	
	public void setPreview(String preview) {
		this.preview = preview;
	}

	public List<String> getPublicFlags() {
		return publicFlags;
	}
	
	public void setPublicFlags(List<String> publicFlags) {
		this.publicFlags = publicFlags;
	}

}
