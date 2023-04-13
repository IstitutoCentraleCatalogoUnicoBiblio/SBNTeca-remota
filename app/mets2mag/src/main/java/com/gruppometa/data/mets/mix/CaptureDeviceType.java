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
 * <p>Classe Java per captureDeviceType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="captureDeviceType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="transmission scanner"/&gt;
 *     &lt;enumeration value="reflection print scanner"/&gt;
 *     &lt;enumeration value="digital still camera"/&gt;
 *     &lt;enumeration value="still from video"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "captureDeviceType")
@XmlEnum
public enum CaptureDeviceType {

    @XmlEnumValue("transmission scanner")
    TRANSMISSION_SCANNER("transmission scanner"),
    @XmlEnumValue("reflection print scanner")
    REFLECTION_PRINT_SCANNER("reflection print scanner"),
    @XmlEnumValue("digital still camera")
    DIGITAL_STILL_CAMERA("digital still camera"),
    @XmlEnumValue("still from video")
    STILL_FROM_VIDEO("still from video");
    private final String value;

    CaptureDeviceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CaptureDeviceType fromValue(String v) {
        for (CaptureDeviceType c: CaptureDeviceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
