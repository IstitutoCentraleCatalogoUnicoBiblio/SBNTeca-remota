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
 * <p>Classe Java per meteringModeType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="meteringModeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Average"/&gt;
 *     &lt;enumeration value="Center weighted average"/&gt;
 *     &lt;enumeration value="Spot"/&gt;
 *     &lt;enumeration value="Multispot"/&gt;
 *     &lt;enumeration value="Pattern"/&gt;
 *     &lt;enumeration value="Partial"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "meteringModeType")
@XmlEnum
public enum MeteringModeType {

    @XmlEnumValue("Average")
    AVERAGE("Average"),
    @XmlEnumValue("Center weighted average")
    CENTER_WEIGHTED_AVERAGE("Center weighted average"),
    @XmlEnumValue("Spot")
    SPOT("Spot"),
    @XmlEnumValue("Multispot")
    MULTISPOT("Multispot"),
    @XmlEnumValue("Pattern")
    PATTERN("Pattern"),
    @XmlEnumValue("Partial")
    PARTIAL("Partial");
    private final String value;

    MeteringModeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MeteringModeType fromValue(String v) {
        for (MeteringModeType c: MeteringModeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
