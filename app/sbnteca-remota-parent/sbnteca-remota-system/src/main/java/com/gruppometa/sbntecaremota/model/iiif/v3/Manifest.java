package com.gruppometa.sbntecaremota.model.iiif.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"context", "id", "type"})
public class Manifest extends JsonLdIdType {

    private static final String context = //{"http://www.w3.org/ns/anno.jsonld",
            "http://iiif.io/api/presentation/3/context.json";//};

    private List<Canvas> items = new ArrayList<>();
    private String navDate;
    private Canvas start;
    private LanguageMap label = new LanguageMap();
    private LanguageMap summary = new LanguageMap();
    private Thumbnail thumbnail;
    private List<MetaData> metadata = new ArrayList<>();
    private List<Range> structures = new ArrayList<>();
    private List<Rendering> rendering;
    public List<Rendering> getRendering() {
        return rendering;
    }

    public void setRendering(List<Rendering> rendering) {
        this.rendering = rendering;
    }

    public List<Range> getStructures() {
        return structures;
    }

    public void setStructures(List<Range> structures) {
        this.structures = structures;
    }

    private String viewingDirection = "left-to-right";
    private String behavior = "paged";

    public List<MetaData> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetaData> metaData) {
        this.metadata = metaData;
    }

    public String getNavDate() {
        return navDate;
    }

    public void setNavDate(String navDate) {
        this.navDate = navDate;
    }

    public Canvas getStart() {
        return start;
    }

    public void setStart(Canvas start) {
        this.start = start;
    }

    public LanguageMap getLabel() {
        return label;
    }

    public void setLabel(LanguageMap label) {
        this.label = label;
    }

    public LanguageMap getSummary() {
        return summary;
    }

    public void setSummary(LanguageMap summary) {
        this.summary = summary;
    }


    public String getViewingDirection() {
        return viewingDirection;
    }

    public void setViewingDirection(String viewingDirection) {
        this.viewingDirection = viewingDirection;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public List<Canvas> getItems() {
        return items;
    }

    public void setItems(List<Canvas> items) {
        this.items = items;
    }

    public Manifest(String manifestId) {
        super(manifestId, "Manifest");
    }

    public Manifest(String manifestId, String title) {
        super(manifestId, "Manifest");
    }


    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonProperty("@context")
    public String getContext() {
        return context;
    }


}
