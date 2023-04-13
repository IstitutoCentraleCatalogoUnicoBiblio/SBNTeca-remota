package com.gruppometa.sbntecaremota.model.iiif.v3;

import java.util.ArrayList;
import java.util.List;

public class Range extends JsonLdIdType{

    private LanguageMap label;
    private List<Object> items = new ArrayList<>();

    public Range(String id) {
        super(id, "Range");
    }

    public LanguageMap getLabel() {
        return label;
    }

    public void setLabel(LanguageMap label) {
        this.label = label;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}
