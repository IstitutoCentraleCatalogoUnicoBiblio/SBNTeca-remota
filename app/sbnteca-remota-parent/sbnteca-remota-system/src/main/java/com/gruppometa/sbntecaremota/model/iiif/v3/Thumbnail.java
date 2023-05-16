package com.gruppometa.sbntecaremota.model.iiif.v3;

public class Thumbnail extends JsonLdIdType{

    protected String format;
    protected Integer width;
    protected Integer height;
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Thumbnail(String id, String type) {
        super(id, type);
    }
    public Thumbnail(String id) {
        super(id, "Image");
    }
}
