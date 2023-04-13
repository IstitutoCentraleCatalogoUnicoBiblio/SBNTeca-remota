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
 * <p>Classe Java per gpsAltitudeRefType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="gpsAltitudeRefType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Sea level"/&gt;
 *     &lt;enumeration value="Sea level reference (negative value)"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "gpsAltitudeRefType")
@XmlEnum
public enum GpsAltitudeRefType {

    @XmlEnumValue("Sea level")
    SEA_LEVEL("Sea level"),
    @XmlEnumValue("Sea level reference (negative value)")
    SEA_LEVEL_REFERENCE_NEGATIVE_VALUE("Sea level reference (negative value)");
    private final String value;

    GpsAltitudeRefType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GpsAltitudeRefType fromValue(String v) {
        for (GpsAltitudeRefType c: GpsAltitudeRefType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
