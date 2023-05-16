package com.gruppometa.sbntecaremota.iiif;

import com.gruppometa.sbntecaremota.model.iiif.v3.AnnotationBody;
import com.gruppometa.sbntecaremota.model.iiif.v3.AnnotationPage;
import com.gruppometa.sbntecaremota.model.iiif.v3.Thumbnail;

import java.util.List;

public class CurrentMedia {

    private AnnotationPage annotationPage;
    private List<Thumbnail> thumbnails;
    private AnnotationBody annotationBody;
    private String usage;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAnnotationPage(AnnotationPage annotationPage) {
        this.annotationPage = annotationPage;
    }

    public AnnotationPage getAnnotationPage() {
        return annotationPage;
    }

    public void setThumbnails(List<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void setAnnotationBody(AnnotationBody annotationBody) {
        this.annotationBody = annotationBody;
    }

    public AnnotationBody getAnnotationBody() {
        return annotationBody;
    }


    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }
}

