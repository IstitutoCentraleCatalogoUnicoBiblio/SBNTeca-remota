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
 * <p>Classe Java per doc_compressiontype.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="doc_compressiontype"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Uncompressed"/&gt;
 *     &lt;enumeration value="ZIP"/&gt;
 *     &lt;enumeration value="RAR"/&gt;
 *     &lt;enumeration value="GZ"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "doc_compressiontype")
@XmlEnum
public enum DocCompressiontype {

    @XmlEnumValue("Uncompressed")
    UNCOMPRESSED("Uncompressed"),
    ZIP("ZIP"),
    RAR("RAR"),
    GZ("GZ");
    private final String value;

    DocCompressiontype(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DocCompressiontype fromValue(String v) {
        for (DocCompressiontype c: DocCompressiontype.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
