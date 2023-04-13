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
 * <p>Classe Java per componentUseType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="componentUseType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="image data"/&gt;
 *     &lt;enumeration value="associated alpha data"/&gt;
 *     &lt;enumeration value="unassociated alpha data"/&gt;
 *     &lt;enumeration value="range data"/&gt;
 *     &lt;enumeration value="unspecified data"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "componentUseType")
@XmlEnum
public enum ComponentUseType {

    @XmlEnumValue("image data")
    IMAGE_DATA("image data"),
    @XmlEnumValue("associated alpha data")
    ASSOCIATED_ALPHA_DATA("associated alpha data"),
    @XmlEnumValue("unassociated alpha data")
    UNASSOCIATED_ALPHA_DATA("unassociated alpha data"),
    @XmlEnumValue("range data")
    RANGE_DATA("range data"),
    @XmlEnumValue("unspecified data")
    UNSPECIFIED_DATA("unspecified data");
    private final String value;

    ComponentUseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ComponentUseType fromValue(String v) {
        for (ComponentUseType c: ComponentUseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
