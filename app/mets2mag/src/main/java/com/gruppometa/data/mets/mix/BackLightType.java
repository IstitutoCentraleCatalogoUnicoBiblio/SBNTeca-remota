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
 * <p>Classe Java per backLightType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="backLightType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Front light"/&gt;
 *     &lt;enumeration value="Backlight 1"/&gt;
 *     &lt;enumeration value="Backlight 2"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "backLightType")
@XmlEnum
public enum BackLightType {

    @XmlEnumValue("Front light")
    FRONT_LIGHT("Front light"),
    @XmlEnumValue("Backlight 1")
    BACKLIGHT_1("Backlight 1"),
    @XmlEnumValue("Backlight 2")
    BACKLIGHT_2("Backlight 2");
    private final String value;

    BackLightType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BackLightType fromValue(String v) {
        for (BackLightType c: BackLightType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
