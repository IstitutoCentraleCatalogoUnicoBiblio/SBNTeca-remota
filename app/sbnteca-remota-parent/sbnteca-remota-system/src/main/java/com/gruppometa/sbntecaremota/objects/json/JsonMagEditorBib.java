package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorBib implements Serializable {
	private static final long serialVersionUID = 4013852452141239953L;
	
	// livello
	private String level;
	
	// identifiers
	@JsonProperty("identifiers")
	private List<Map<String, String>> identifiers = new ArrayList<Map<String, String>>();
	
	// titoli
	@JsonProperty("titles")
	private List<Map<String, String>> titles = new ArrayList<Map<String, String>>();
	
	// autori
	@JsonProperty("creators")
	private List<Map<String, String>> creators = new ArrayList<Map<String, String>>();
	
	// editori
	@JsonProperty("publishers")
	private List<Map<String, String>> publishers = new ArrayList<Map<String, String>>();
	
	// soggetti
	@JsonProperty("subjects")
	private List<Map<String, String>> subjects = new ArrayList<Map<String, String>>();
	
	// descrizioni
	@JsonProperty("descriptions")
	private List<Map<String, String>> descriptions = new ArrayList<Map<String, String>>();
	
	// contributori
	@JsonProperty("contributors")
	private List<Map<String, String>> contributors = new ArrayList<Map<String, String>>();
	
	// date pubblicazione
	@JsonProperty("dates")
	private List<Map<String, String>> dates = new ArrayList<Map<String, String>>();
	
	// tipi
	@JsonProperty("types")
	private List<Map<String, String>> types = new ArrayList<Map<String, String>>();
	
	// formati
	@JsonProperty("formats")
	private List<Map<String, String>> formats = new ArrayList<Map<String, String>>();
	
	// sorgenti
	@JsonProperty("sources")
	private List<Map<String, String>> sources = new ArrayList<Map<String, String>>();
	
	// lingue
	@JsonProperty("languages")
	private List<Map<String, String>> languages = new ArrayList<Map<String, String>>();
	
	// relazioni
	@JsonProperty("relations")
	private List<Map<String, String>> relations = new ArrayList<Map<String, String>>();
	
	// coverages
	@JsonProperty("coverages")
	private List<Map<String, String>> coverages = new ArrayList<Map<String, String>>();
	
	// diritti
	private List<Map<String, String>> rights = new ArrayList<Map<String, String>>();
	
	// piece
	private List<JsonMagEditorPiece> piece = new ArrayList<JsonMagEditorPiece>();;
	
	// holdings
	private List<JsonMagEditorHoldings> holdings = new ArrayList<JsonMagEditorHoldings>();
	
	// coordinate geografiche
	@JsonProperty("geo_coords")
	private List<Map<String, String>> geoCoord = new ArrayList<Map<String, String>>();
	
	// date notifica
	@JsonProperty("not_dates")
	private List<Map<String, String>> notDates = new ArrayList<Map<String, String>>();
	
	
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public List<Map<String, String>> getIdentifiers() {
		return identifiers;
	}
	
	public void setIdentifiers(List<Map<String, String>> identifiers) {
		this.identifiers = identifiers;
	}

	public List<Map<String, String>> getTitles() {
		return titles;
	}
	
	public void setTitles(List<Map<String, String>> titles) {
		this.titles = titles;
	}

	public List<Map<String, String>> getCreators() {
		return creators;
	}
	
	public void setCreators(List<Map<String, String>> creators) {
		this.creators = creators;
	}

	public List<Map<String, String>> getPublishers() {
		return publishers;
	}
	
	public void setPublishers(List<Map<String, String>> publishers) {
		this.publishers = publishers;
	}

	public List<Map<String, String>> getSubjects() {
		return subjects;
	}
	
	public void setSubjects(List<Map<String, String>> subjects) {
		this.subjects = subjects;
	}

	public List<Map<String, String>> getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(List<Map<String, String>> descriptions) {
		this.descriptions = descriptions;
	}

	public List<Map<String, String>> getContributors() {
		return contributors;
	}
	
	public void setContributors(List<Map<String, String>> contributors) {
		this.contributors = contributors;
	}

	public List<Map<String, String>> getDates() {
		return dates;
	}
	
	public void setDates(List<Map<String, String>> dates) {
		this.dates = dates;
	}

	public List<Map<String, String>> getTypes() {
		return types;
	}
	
	public void setTypes(List<Map<String, String>> types) {
		this.types = types;
	}

	public List<Map<String, String>> getFormats() {
		return formats;
	}
	
	public void setFormats(List<Map<String, String>> formats) {
		this.formats = formats;
	}

	public List<Map<String, String>> getSources() {
		return sources;
	}
	
	public void setSources(List<Map<String, String>> sources) {
		this.sources = sources;
	}

	public List<Map<String, String>> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<Map<String, String>> languages) {
		this.languages = languages;
	}

	public List<Map<String, String>> getRelations() {
		return relations;
	}
	
	public void setRelations(List<Map<String, String>> relations) {
		this.relations = relations;
	}

	public List<Map<String, String>> getCoverages() {
		return coverages;
	}
	
	public void setCoverages(List<Map<String, String>> coverages) {
		this.coverages = coverages;
	}

	public List<Map<String, String>> getRights() {
		return rights;
	}
	
	public void setRights(List<Map<String, String>> rights) {
		this.rights = rights;
	}
	
	public List<JsonMagEditorPiece> getPiece() {
		return piece;
	}
	
	public void setPiece(List<JsonMagEditorPiece> piece) {
		this.piece = piece;
	}
	
	public List<JsonMagEditorHoldings> getHoldings() {
		return holdings;
	}
	
	public void setHoldings(List<JsonMagEditorHoldings> holdings) {
		this.holdings = holdings;
	}

	public List<Map<String, String>> getGeoCoord() {
		return geoCoord;
	}
	
	public void setGeoCoord(List<Map<String, String>> geoCoord) {
		this.geoCoord = geoCoord;
	}

	public List<Map<String, String>> getNotDates() {
		return notDates;
	}
	
	public void setNotDates(List<Map<String, String>> notDates) {
		this.notDates = notDates;
	}

}
