//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.niso;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per compressiontype.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="compressiontype"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Uncompressed"/&gt;
 *     &lt;enumeration value="CCITT 1D"/&gt;
 *     &lt;enumeration value="CCITT Group 3"/&gt;
 *     &lt;enumeration value="CCITT Group 4"/&gt;
 *     &lt;enumeration value="LZW"/&gt;
 *     &lt;enumeration value="JPG"/&gt;
 *     &lt;enumeration value="PNG"/&gt;
 *     &lt;enumeration value="DJVU"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "compressiontype")
@XmlEnum
public enum Compressiontype {

    @XmlEnumValue("Uncompressed")
    UNCOMPRESSED("Uncompressed"),
    @XmlEnumValue("CCITT 1D")
    CCITT_1_D("CCITT 1D"),
    @XmlEnumValue("CCITT Group 3")
    CCITT_GROUP_3("CCITT Group 3"),
    @XmlEnumValue("CCITT Group 4")
    CCITT_GROUP_4("CCITT Group 4"),
    LZW("LZW"),
    JPG("JPG"),
    PNG("PNG"),
    DJVU("DJVU");
    private final String value;

    Compressiontype(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Compressiontype fromValue(String v) {
        for (Compressiontype c: Compressiontype.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
