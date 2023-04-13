package com.gruppometa.sbntecaremota.model.iiif.v3;

import java.util.ArrayList;
import java.util.List;

public class Canvas extends JsonLdIdType{

    private LanguageMap label;
    private Integer height;
    private Integer width;
    private Double duration;
    private LanguageMap requiredStatement; // attribution
    private Rights rights;

    public List<Thumbnail> getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(List<Thumbnail> thumbnail) {
        this.thumbnail = thumbnail;
    }

    private List<Thumbnail> thumbnail;

    private List<Rendering> rendering;
    public List<Rendering> getRendering() {
        return rendering;
    }

    public void setRendering(List<Rendering> rendering) {
        this.rendering = rendering;
    }


    private List<AnnotationPage> items = new ArrayList<>();
    private List<AnnotationPage> annotations = new ArrayList<>(); // full text identifiers

    public Canvas(String id) {
        super(id, "Canvas");
    }

    public List<AnnotationPage> getItems() {
        return items;
    }

    public void setItems(List<AnnotationPage> items) {
        this.items = items;
    }

    public List<AnnotationPage> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationPage> annotations) {
        this.annotations = annotations;
    }

    public LanguageMap getLabel() {
        return label;
    }

    public void setLabel(LanguageMap label) {
        this.label = label;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public LanguageMap getRequiredStatement() {
        return requiredStatement;
    }

    public void setRequiredStatement(LanguageMap requiredStatement) {
        this.requiredStatement = requiredStatement;
    }

    public Rights getRights() {
        return rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }

}
