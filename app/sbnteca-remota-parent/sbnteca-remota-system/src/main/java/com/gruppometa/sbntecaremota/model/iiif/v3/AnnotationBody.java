package com.gruppometa.sbntecaremota.model.iiif.v3;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnotationBody extends JsonLdIdType{

    private String format;
    private Service service;
    private Integer height;
    private Integer width;
    private Double duration;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @JsonProperty("language")
    private String originalLanguage;

    public AnnotationBody(String id, String type) {
        super(id, type);
    }

}
