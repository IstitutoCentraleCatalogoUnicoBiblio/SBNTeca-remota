package com.gruppometa.sbntecaremota.model.iiif.v3;

public class Annotation extends JsonLdIdType{

    private String motivation = "painting";
    private String timeMode;
    private AnnotationBody body;
    private String target;

    public Annotation(String id) {
        super(id, "Annotation");
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getTimeMode() {
        return timeMode;
    }

    public void setTimeMode(String timeMode) {
        this.timeMode = timeMode;
    }

    public AnnotationBody getBody() {
        return body;
    }

    public void setBody(AnnotationBody body) {
        this.body = body;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
