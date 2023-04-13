//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.mix;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per exposureProgramType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="exposureProgramType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Not defined"/&gt;
 *     &lt;enumeration value="Manual"/&gt;
 *     &lt;enumeration value="Normal program"/&gt;
 *     &lt;enumeration value="Aperture priority"/&gt;
 *     &lt;enumeration value="Shutter priority"/&gt;
 *     &lt;enumeration value="Creative program (biased toward depth of field)"/&gt;
 *     &lt;enumeration value="Action program (biased toward fast shutter speed)"/&gt;
 *     &lt;enumeration value="Portrait mode (for closeup photos with the background out of focus)"/&gt;
 *     &lt;enumeration value="Landscape mode (for landscape photos with the background in focus)"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "exposureProgramType")
@XmlEnum
public enum ExposureProgramType {

    @XmlEnumValue("Not defined")
    NOT_DEFINED("Not defined"),
    @XmlEnumValue("Manual")
    MANUAL("Manual"),
    @XmlEnumValue("Normal program")
    NORMAL_PROGRAM("Normal program"),
    @XmlEnumValue("Aperture priority")
    APERTURE_PRIORITY("Aperture priority"),
    @XmlEnumValue("Shutter priority")
    SHUTTER_PRIORITY("Shutter priority"),
    @XmlEnumValue("Creative program (biased toward depth of field)")
    CREATIVE_PROGRAM_BIASED_TOWARD_DEPTH_OF_FIELD("Creative program (biased toward depth of field)"),
    @XmlEnumValue("Action program (biased toward fast shutter speed)")
    ACTION_PROGRAM_BIASED_TOWARD_FAST_SHUTTER_SPEED("Action program (biased toward fast shutter speed)"),
    @XmlEnumValue("Portrait mode (for closeup photos with the background out of focus)")
    PORTRAIT_MODE_FOR_CLOSEUP_PHOTOS_WITH_THE_BACKGROUND_OUT_OF_FOCUS("Portrait mode (for closeup photos with the background out of focus)"),
    @XmlEnumValue("Landscape mode (for landscape photos with the background in focus)")
    LANDSCAPE_MODE_FOR_LANDSCAPE_PHOTOS_WITH_THE_BACKGROUND_IN_FOCUS("Landscape mode (for landscape photos with the background in focus)");
    private final String value;

    ExposureProgramType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExposureProgramType fromValue(String v) {
        for (ExposureProgramType c: ExposureProgramType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
