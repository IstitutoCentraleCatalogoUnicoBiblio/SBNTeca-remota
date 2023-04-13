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
 * <p>Classe Java per grayResponseUnitType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="grayResponseUnitType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Number represents tenths of a unit"/&gt;
 *     &lt;enumeration value="Number represents hundredths of a unit"/&gt;
 *     &lt;enumeration value="Number represents thousandths of a unit"/&gt;
 *     &lt;enumeration value="Number represents ten-thousandths of a unit"/&gt;
 *     &lt;enumeration value="Number represents hundred-thousandths of a unit"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "grayResponseUnitType")
@XmlEnum
public enum GrayResponseUnitType {

    @XmlEnumValue("Number represents tenths of a unit")
    NUMBER_REPRESENTS_TENTHS_OF_A_UNIT("Number represents tenths of a unit"),
    @XmlEnumValue("Number represents hundredths of a unit")
    NUMBER_REPRESENTS_HUNDREDTHS_OF_A_UNIT("Number represents hundredths of a unit"),
    @XmlEnumValue("Number represents thousandths of a unit")
    NUMBER_REPRESENTS_THOUSANDTHS_OF_A_UNIT("Number represents thousandths of a unit"),
    @XmlEnumValue("Number represents ten-thousandths of a unit")
    NUMBER_REPRESENTS_TEN_THOUSANDTHS_OF_A_UNIT("Number represents ten-thousandths of a unit"),
    @XmlEnumValue("Number represents hundred-thousandths of a unit")
    NUMBER_REPRESENTS_HUNDRED_THOUSANDTHS_OF_A_UNIT("Number represents hundred-thousandths of a unit");
    private final String value;

    GrayResponseUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GrayResponseUnitType fromValue(String v) {
        for (GrayResponseUnitType c: GrayResponseUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
