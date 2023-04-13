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
 * <p>Classe Java per sensingMethodType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="sensingMethodType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Not defined"/&gt;
 *     &lt;enumeration value="One-chip color area sensor"/&gt;
 *     &lt;enumeration value="Two-chip color area sensor"/&gt;
 *     &lt;enumeration value="Three-chip color area sensor"/&gt;
 *     &lt;enumeration value="Color sequential area sensor"/&gt;
 *     &lt;enumeration value="Trilinear sensor"/&gt;
 *     &lt;enumeration value="Color sequential linear sensor"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "sensingMethodType")
@XmlEnum
public enum SensingMethodType {

    @XmlEnumValue("Not defined")
    NOT_DEFINED("Not defined"),
    @XmlEnumValue("One-chip color area sensor")
    ONE_CHIP_COLOR_AREA_SENSOR("One-chip color area sensor"),
    @XmlEnumValue("Two-chip color area sensor")
    TWO_CHIP_COLOR_AREA_SENSOR("Two-chip color area sensor"),
    @XmlEnumValue("Three-chip color area sensor")
    THREE_CHIP_COLOR_AREA_SENSOR("Three-chip color area sensor"),
    @XmlEnumValue("Color sequential area sensor")
    COLOR_SEQUENTIAL_AREA_SENSOR("Color sequential area sensor"),
    @XmlEnumValue("Trilinear sensor")
    TRILINEAR_SENSOR("Trilinear sensor"),
    @XmlEnumValue("Color sequential linear sensor")
    COLOR_SEQUENTIAL_LINEAR_SENSOR("Color sequential linear sensor");
    private final String value;

    SensingMethodType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SensingMethodType fromValue(String v) {
        for (SensingMethodType c: SensingMethodType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
