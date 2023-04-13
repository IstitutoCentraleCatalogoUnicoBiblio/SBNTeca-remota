package com.gruppometa.sbntecaremota.model.iiif.v3;

import java.util.ArrayList;
import java.util.List;

public class AnnotationPage  extends JsonLdIdType{

    private List<Annotation> items = new ArrayList<>();

    public List<Annotation> getItems() {
        return items;
    }

    public void setItems(List<Annotation> items) {
        this.items = items;
    }

    public AnnotationPage(String id) {
        super(id, "AnnotationPage");
    }

}
