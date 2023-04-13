package com.gruppometa.sbntecaremota.model.iiif.v3;

public class Thumbnail extends JsonLdIdType{

    protected String format;
    protected int width;
    protected int height;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Thumbnail(String id) {
        super(id, "Image");
    }
}
