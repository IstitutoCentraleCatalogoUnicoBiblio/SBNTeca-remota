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
 * <p>Classe Java per samplingFrequencyPlaneType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="samplingFrequencyPlaneType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="camera/scanner focal plane"/&gt;
 *     &lt;enumeration value="object plane"/&gt;
 *     &lt;enumeration value="source object plane"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "samplingFrequencyPlaneType")
@XmlEnum
public enum SamplingFrequencyPlaneType {

    @XmlEnumValue("camera/scanner focal plane")
    CAMERA_SCANNER_FOCAL_PLANE("camera/scanner focal plane"),
    @XmlEnumValue("object plane")
    OBJECT_PLANE("object plane"),
    @XmlEnumValue("source object plane")
    SOURCE_OBJECT_PLANE("source object plane");
    private final String value;

    SamplingFrequencyPlaneType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SamplingFrequencyPlaneType fromValue(String v) {
        for (SamplingFrequencyPlaneType c: SamplingFrequencyPlaneType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
