package com.gruppometa.sbntecaremota.model.iiif.v3;

public class MetaData extends JsonLdIdType{

    private LanguageMap label;
    private LanguageMap value;

    /**
     * Create a new MetaData object
     * @param label languagemap containing metadata field label information in various languages
     * @param value languagemap containing metadata field value information in various lanuages
     */
    public MetaData(LanguageMap label, LanguageMap value) {
        this.label = label;
        this.value = value;
    }

    public LanguageMap getLabel() {
        return label;
    }

    public LanguageMap getValue() {
        return value;
    }

    /**
     * @return textual representation of the contents of the metadata object (for debugging purposes)
     */
    @Override
    public String toString() {
        return "label: " + label + " value: " + value;
    }

}
