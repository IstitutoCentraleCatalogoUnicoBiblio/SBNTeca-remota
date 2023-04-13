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
 * <p>Classe Java per gpsDifferentialType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="gpsDifferentialType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Measurement without differential correction"/&gt;
 *     &lt;enumeration value="Differential correction applied"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "gpsDifferentialType")
@XmlEnum
public enum GpsDifferentialType {

    @XmlEnumValue("Measurement without differential correction")
    MEASUREMENT_WITHOUT_DIFFERENTIAL_CORRECTION("Measurement without differential correction"),
    @XmlEnumValue("Differential correction applied")
    DIFFERENTIAL_CORRECTION_APPLIED("Differential correction applied");
    private final String value;

    GpsDifferentialType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GpsDifferentialType fromValue(String v) {
        for (GpsDifferentialType c: GpsDifferentialType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
