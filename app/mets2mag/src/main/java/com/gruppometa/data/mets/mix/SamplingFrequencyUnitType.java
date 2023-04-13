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
 * <p>Classe Java per samplingFrequencyUnitType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="samplingFrequencyUnitType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="no absolute unit of measurement"/&gt;
 *     &lt;enumeration value="in."/&gt;
 *     &lt;enumeration value="cm"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "samplingFrequencyUnitType")
@XmlEnum
public enum SamplingFrequencyUnitType {

    @XmlEnumValue("no absolute unit of measurement")
    NO_ABSOLUTE_UNIT_OF_MEASUREMENT("no absolute unit of measurement"),
    @XmlEnumValue("in.")
    IN("in."),
    @XmlEnumValue("cm")
    CM("cm");
    private final String value;

    SamplingFrequencyUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SamplingFrequencyUnitType fromValue(String v) {
        for (SamplingFrequencyUnitType c: SamplingFrequencyUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
