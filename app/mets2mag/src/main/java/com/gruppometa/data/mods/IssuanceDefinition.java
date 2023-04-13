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
 * <p>Classe Java per issuanceDefinition.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="issuanceDefinition"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="continuing"/&gt;
 *     &lt;enumeration value="monographic"/&gt;
 *     &lt;enumeration value="single unit"/&gt;
 *     &lt;enumeration value="multipart monograph"/&gt;
 *     &lt;enumeration value="serial"/&gt;
 *     &lt;enumeration value="integrating resource"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "issuanceDefinition")
@XmlEnum
public enum IssuanceDefinition {

    @XmlEnumValue("continuing")
    CONTINUING("continuing"),
    @XmlEnumValue("monographic")
    MONOGRAPHIC("monographic"),
    @XmlEnumValue("single unit")
    SINGLE_UNIT("single unit"),
    @XmlEnumValue("multipart monograph")
    MULTIPART_MONOGRAPH("multipart monograph"),
    @XmlEnumValue("serial")
    SERIAL("serial"),
    @XmlEnumValue("integrating resource")
    INTEGRATING_RESOURCE("integrating resource");
    private final String value;

    IssuanceDefinition(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IssuanceDefinition fromValue(String v) {
        for (IssuanceDefinition c: IssuanceDefinition.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
