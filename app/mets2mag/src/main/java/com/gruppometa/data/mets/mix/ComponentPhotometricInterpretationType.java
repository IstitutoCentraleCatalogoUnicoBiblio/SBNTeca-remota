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
 * <p>Classe Java per componentPhotometricInterpretationType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="componentPhotometricInterpretationType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="R"/&gt;
 *     &lt;enumeration value="G"/&gt;
 *     &lt;enumeration value="B"/&gt;
 *     &lt;enumeration value="Y"/&gt;
 *     &lt;enumeration value="Cb"/&gt;
 *     &lt;enumeration value="Cr"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "componentPhotometricInterpretationType")
@XmlEnum
public enum ComponentPhotometricInterpretationType {

    R("R"),
    G("G"),
    B("B"),
    Y("Y"),
    @XmlEnumValue("Cb")
    CB("Cb"),
    @XmlEnumValue("Cr")
    CR("Cr");
    private final String value;

    ComponentPhotometricInterpretationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ComponentPhotometricInterpretationType fromValue(String v) {
        for (ComponentPhotometricInterpretationType c: ComponentPhotometricInterpretationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
