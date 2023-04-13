package com.gruppometa.sbntecaremota.iiif;

import com.gruppometa.sbntecaremota.model.iiif.v3.AnnotationBody;
import com.gruppometa.sbntecaremota.model.iiif.v3.AnnotationPage;
import com.gruppometa.sbntecaremota.model.iiif.v3.Thumbnail;

public class CurrentMedia {

    private AnnotationPage annotationPage;
    private Thumbnail thumbnail;
    private AnnotationBody annotationBody;
    private String usage;

    public void setAnnotationPage(AnnotationPage annotationPage) {
        this.annotationPage = annotationPage;
    }

    public AnnotationPage getAnnotationPage() {
        return annotationPage;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
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
