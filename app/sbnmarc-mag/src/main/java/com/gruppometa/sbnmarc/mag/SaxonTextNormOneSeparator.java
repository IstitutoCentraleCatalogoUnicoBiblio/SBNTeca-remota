package com.gruppometa.sbnmarc.mag;


import com.gruppometa.sbnmarc.mag.profile.XmlProfile;

/**
 */
public class SaxonTextNormOneSeparator extends SaxonTextNormFunction{
    protected String separator = ". ";

    public SaxonTextNormOneSeparator(String functionName, String separator) {
        this.separator = separator;
        this.functionName = functionName;
        xmlProfile = createProfile();
        xmlProfile.init();
    }

    public SaxonTextNormOneSeparator(String functionName) {
        super(functionName);
    }

    @Override
    protected XmlProfile createProfile() {
        return new XmlProfile("/naProfile.xml"){
            protected String getValueSeparator(String fieldname, String code, String data, String[] subFieldsCodes) {
                return separator;
            }

        };
    }
}
