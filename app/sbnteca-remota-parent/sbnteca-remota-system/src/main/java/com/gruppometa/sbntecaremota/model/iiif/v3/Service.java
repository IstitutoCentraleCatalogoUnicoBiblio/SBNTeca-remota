package com.gruppometa.sbntecaremota.model.iiif.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"context", "id", "profile"})
public class Service extends JsonLdIdType{

    private String profile;

    @JsonProperty("@context")
    private String context;

    public Service(String id, String type) {
        super(id, type);
    }

    public Service(String id, String type, String context, String profile){
        super(id, type);
        this.context = context;
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
