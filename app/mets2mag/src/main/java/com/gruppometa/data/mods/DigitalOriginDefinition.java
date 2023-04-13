//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mods;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per digitalOriginDefinition.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="digitalOriginDefinition"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="born digital"/&gt;
 *     &lt;enumeration value="reformatted digital"/&gt;
 *     &lt;enumeration value="digitized microfilm"/&gt;
 *     &lt;enumeration value="digitized other analog"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "digitalOriginDefinition")
@XmlEnum
public enum DigitalOriginDefinition {

    @XmlEnumValue("born digital")
    BORN_DIGITAL("born digital"),
    @XmlEnumValue("reformatted digital")
    REFORMATTED_DIGITAL("reformatted digital"),
    @XmlEnumValue("digitized microfilm")
    DIGITIZED_MICROFILM("digitized microfilm"),
    @XmlEnumValue("digitized other analog")
    DIGITIZED_OTHER_ANALOG("digitized other analog");
    private final String value;

    DigitalOriginDefinition(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DigitalOriginDefinition fromValue(String v) {
        for (DigitalOriginDefinition c: DigitalOriginDefinition.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
