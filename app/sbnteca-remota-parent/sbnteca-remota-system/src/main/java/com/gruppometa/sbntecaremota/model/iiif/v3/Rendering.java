package com.gruppometa.sbntecaremota.model.iiif.v3;

public class Rendering extends JsonLdIdType{

    private LanguageMap label;
    private String format;

    public LanguageMap getLabel() {
        return label;
    }

    public void setLabel(LanguageMap label) {
        this.label = label;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
