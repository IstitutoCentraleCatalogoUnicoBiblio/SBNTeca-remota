package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonPropertyOrder({ "identifier", "title", "piece", "creator",
	"contributor", "collection", "type", "subject", "date", 
	"description", "format", "publisher", "level", "language", 
	"source", "relation", "coverage", "rights", "holdings", 
	"geo_coord", "not_date", "agency", "stprog", "access_rights", 
	"completeness", "creation", "last_update", "img_group", 
	"audio_group", "video_group" })
public class JsonMagDetailMetadata implements Serializable {
	private static final long serialVersionUID = -8234609889152100615L;
	
	// creazione
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String creation;
	
	// ultima modifica
	@JsonProperty("last_update")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastUpdate;
	
	// progetto
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String stprog;
	
	// collezione
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String collection;
	
	// agenzia
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String agency;
	
	// diritti accesso
	@JsonProperty("access_rights")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String accessRights;
	
	// completezza
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String completeness;
	
	// gruppi immagini
	@JsonProperty("img_group")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailImgGroup> imgGroups = new ArrayList<JsonMagDetailImgGroup>();

	// gruppi audio
	@JsonProperty("audio_group")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailAudioGroup> audioGroups = new ArrayList<JsonMagDetailAudioGroup>();

	// gruppi video
	@JsonProperty("video_group")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailVideoGroup> videoGroups = new ArrayList<JsonMagDetailVideoGroup>();

	// livello
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String level;
	
	// identifiers
	@JsonProperty("identifier")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> identifiers = new ArrayList<String>();
	
	// titoli
	@JsonProperty("title")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> titles = new ArrayList<String>();
	
	// autori
	@JsonProperty("creator")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> creators = new ArrayList<String>();
	
	// editori
	@JsonProperty("publisher")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> publishers = new ArrayList<String>();
	
	// soggetti
	@JsonProperty("subject")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> subjects = new ArrayList<String>();
	
	// descrizioni
	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> descriptions = new ArrayList<String>();
	
	// contributori
	@JsonProperty("contributor")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> contributors = new ArrayList<String>();
	
	// date pubblicazione
	@JsonProperty("date")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> dates = new ArrayList<String>();
	
	// tipi
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> types = new ArrayList<String>();
	
	// formati
	@JsonProperty("format")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> formats = new ArrayList<String>();
	
	// sorgenti
	@JsonProperty("source")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> sources = new ArrayList<String>();
	
	// lingue
	@JsonProperty("language")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> languages = new ArrayList<String>();
	
	// relazioni
	@JsonProperty("relation")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> relations = new ArrayList<String>();
	
	// coverages
	@JsonProperty("coverage")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> coverages = new ArrayList<String>();
	
	// diritti
	@JsonProperty("rights")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> rights = new ArrayList<String>();
	
	// piece
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JsonMagDetailPiece piece;
	
	// holdings
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<JsonMagDetailHoldings> holdings = new ArrayList<JsonMagDetailHoldings>();
	
	// coordinate geografiche
	@JsonProperty("geo_coord")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> geoCoord = new ArrayList<String>();
	
	// date notifica
	@JsonProperty("not_date")
	@JsonInclude(JsonInclude.Include.NON_NULL)
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
	
	public JsonMagDetailPiece getPiece() {
		return piece;
	}
	
	public void setPiece(JsonMagDetailPiece piece) {
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
	
	public List<JsonMagDetailHoldings> getHoldings() {
		return holdings;
	}
	
	public void setHoldings(List<JsonMagDetailHoldings> holdings) {
		this.holdings = holdings;
	}
	
	public String getCreation() {
		return creation;
	}
	
	public void setCreation(String creation) {
		this.creation = creation;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public String getStprog() {
		return stprog;
	}
	
	public void setStprog(String stprog) {
		this.stprog = stprog;
	}
	
	public String getCollection() {
		return collection;
	}
	
	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	public String getAgency() {
		return agency;
	}
	
	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	public String getAccessRights() {
		return accessRights;
	}
	
	public void setAccessRights(String accessRights) {
		this.accessRights = accessRights;
	}
	
	public String getCompleteness() {
		return completeness;
	}
	
	public void setCompleteness(String completeness) {
		this.completeness = completeness;
	}
	
	public List<JsonMagDetailImgGroup> getImgGroups() {
		return imgGroups;
	}
	
	public void setImgGroups(List<JsonMagDetailImgGroup> imgGroups) {
		this.imgGroups = imgGroups;
	}

	public List<JsonMagDetailAudioGroup> getAudioGroups() {
		return audioGroups;
	}
	
	public void setAudioGroups(List<JsonMagDetailAudioGroup> audioGroups) {
		this.audioGroups = audioGroups;
	}

	public List<JsonMagDetailVideoGroup> getVideoGroups() {
		return videoGroups;
	}
	
	public void setVideoGroups(List<JsonMagDetailVideoGroup> videoGroups) {
		this.videoGroups = videoGroups;
	}

}
