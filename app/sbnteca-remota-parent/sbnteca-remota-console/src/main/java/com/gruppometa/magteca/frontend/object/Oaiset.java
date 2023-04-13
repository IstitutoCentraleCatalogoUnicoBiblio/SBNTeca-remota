package com.gruppometa.magteca.frontend.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Oaiset implements Serializable {
	private static final long serialVersionUID = 3188127901244119523L;
	
	// id
	private long id;
	
	// identificativo
	private String spec;
	
	// nome
	private String name;
	
	// progetto
	private String project;
	
	// descrizione in italiano
	@JsonProperty("description_it")
	private String descriptionIt;

	// descrizione in inglese
	@JsonProperty("description_en")
	private String descriptionEn;
	
	// nome servlet
	private String servletName;
	
	// solrquery
	private String solrquery;
	
	// tipo
	private String type;
	
	// costanti
	private List<String> constants = new ArrayList<String>();
	
	// valori
	private List<String> values = new ArrayList<String>();

	// campi
	private List<String> fields = new ArrayList<String>();
	
	// lista limiters
	private List<OaisetLimiter> limiters = new ArrayList<OaisetLimiter>();
	
	// lista profili
	private List<OaisetProfile> profiles = new ArrayList<OaisetProfile>();
	
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSpec() {
		return spec;
	}
	
	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProject() {
		return project;
	}
	
	public void setProject(String project) {
		this.project = project;
	}
	
	public String getDescriptionIt() {
		return descriptionIt;
	}
	
	public void setDescriptionIt(String descriptionIt) {
		this.descriptionIt = descriptionIt;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}
	
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	
	public String getServletName() {
		return servletName;
	}
	
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	
	public String getSolrquery() {
		return solrquery;
	}
	
	public void setSolrquery(String solrquery) {
		this.solrquery = solrquery;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List<String> getConstants() {
		return constants;
	}
	
	public void setConstants(List<String> constants) {
		this.constants = constants;
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public List<String> getFields() {
		return fields;
	}
	
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	public List<OaisetLimiter> getLimiters() {
		return limiters;
	}
	
	public void setLimiters(List<OaisetLimiter> limiters) {
		this.limiters = limiters;
	}
	
	public List<OaisetProfile> getProfiles() {
		return profiles;
	}
	
	public void setProfiles(List<OaisetProfile> profiles) {
		this.profiles = profiles;
	}

}
