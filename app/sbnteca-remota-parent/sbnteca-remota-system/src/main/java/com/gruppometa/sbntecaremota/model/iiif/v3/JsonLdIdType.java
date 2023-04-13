package com.gruppometa.sbntecaremota.model.iiif.v3;

import java.io.Serializable;

public class JsonLdIdType implements Serializable {

    private static final long serialVersionUID = -2716881573312952L;

    private String id;
    private String type;

    public JsonLdIdType() {
    }

    public JsonLdIdType(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }
}
