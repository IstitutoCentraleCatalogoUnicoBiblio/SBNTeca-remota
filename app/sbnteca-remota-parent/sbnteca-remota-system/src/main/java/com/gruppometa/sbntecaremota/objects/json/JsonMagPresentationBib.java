package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationBib implements Serializable {
	private static final long serialVersionUID = 4013852452141239953L;
	
	// livello
	@JsonProperty("livello_bibliografico")
	private String level;
	
	// identifiers
	@JsonProperty("identifier")
	private List<String> identifiers = new ArrayList<String>();
	
	// titoli
	@JsonProperty("titoli")
	private List<String> titles = new ArrayList<String>();
	
	// autori
	@JsonProperty("autori")
	private List<String> creators = new ArrayList<String>();
	
	// editori
	@JsonProperty("editori")
	private List<String> publishers = new ArrayList<String>();
	
	// soggetti
	@JsonProperty("soggetti")
	private List<String> subjects = new ArrayList<String>();
	
	// descrizioni
	@JsonProperty("descrizioni")
	private List<String> descriptions = new ArrayList<String>();
	
	// contributori
	@JsonProperty("contributori")
	private List<String> contributors = new ArrayList<String>();
	
	// date pubblicazione
	@JsonProperty("date_pubblicazione")
	private List<String> dates = new ArrayList<String>();
	
	// tipi
	@JsonProperty("tipi")
	private List<String> types = new ArrayList<String>();
	
	// formati
	@JsonProperty("formati")
	private List<String> formats = new ArrayList<String>();
	
	// sorgenti
	@JsonProperty("sorgenti")
	private List<String> sources = new ArrayList<String>();
	
	// lingue
	@JsonProperty("lingue")
	private List<String> languages = new ArrayList<String>();
	
	// relazioni
	@JsonProperty("relazioni")
	private List<String> relations = new ArrayList<String>();
	
	// coverages
	@JsonProperty("coperture")
	private List<String> coverages = new ArrayList<String>();
	
	// diritti
	@JsonProperty("diritti")
	private List<String> rights = new ArrayList<String>();
	
	// piece
	@JsonProperty("unita")
	private JsonMagPresentationPiece piece;
	
	// holdings
	@JsonProperty("localizzazione")
	private List<JsonMagPresentationHoldings> holdings = new ArrayList<JsonMagPresentationHoldings>();
	
	// coordinate geografiche
	@JsonProperty("geo")
	private List<String> geoCoord = new ArrayList<String>();
	
	// date notifica
	@JsonProperty("date_notifica")
	private List<String> notDates = new ArrayList<String>();
	
	
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
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

	public List<String> getPublishers() {
		return publishers;
	}
	
	public void setPublishers(List<String> publishers) {
		this.publishers = publishers;
	}

	public List<String> getSubjects() {
		return subjects;
	}
	
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	public List<String> getContributors() {
		return contributors;
	}
	
	public void setContributors(List<String> contributors) {
		this.contributors = contributors;
	}

	public List<String> getDates() {
		return dates;
	}
	
	public void setDates(List<String> dates) {
		this.dates = dates;
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

	public List<String> getSources() {
		return sources;
	}
	
	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	public List<String> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public List<String> getRelations() {
		return relations;
	}
	
	public void setRelations(List<String> relations) {
		this.relations = relations;
	}

	public List<String> getCoverages() {
		return coverages;
	}
	
	public void setCoverages(List<String> coverages) {
		this.coverages = coverages;
	}

	public List<String> getRights() {
		return rights;
	}
	
	public void setRights(List<String> rights) {
		this.rights = rights;
	}
	
	public JsonMagPresentationPiece getPiece() {
		return piece;
	}
	
	public void setPiece(JsonMagPresentationPiece piece) {
		this.piece = piece;
	}
	
	public List<String> getGeoCoord() {
		return geoCoord;
	}
	
	public void setGeoCoord(List<String> geoCoord) {
		this.geoCoord = geoCoord;
	}

	public List<String> getNotDates() {
		return notDates;
	}
	
	public void setNotDates(List<String> notDates) {
		this.notDates = notDates;
	}
	
	public List<JsonMagPresentationHoldings> getHoldings() {
		return holdings;
	}
	
	public void setHoldings(List<JsonMagPresentationHoldings> holdings) {
		this.holdings = holdings;
	}

}
