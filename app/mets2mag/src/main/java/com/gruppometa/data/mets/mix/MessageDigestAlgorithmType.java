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
 * <p>Classe Java per messageDigestAlgorithmType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="messageDigestAlgorithmType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Adler-32"/&gt;
 *     &lt;enumeration value="CRC32"/&gt;
 *     &lt;enumeration value="HAVAL"/&gt;
 *     &lt;enumeration value="MD5"/&gt;
 *     &lt;enumeration value="MNP"/&gt;
 *     &lt;enumeration value="SHA-1"/&gt;
 *     &lt;enumeration value="SHA-256"/&gt;
 *     &lt;enumeration value="SHA-384"/&gt;
 *     &lt;enumeration value="SHA-512"/&gt;
 *     &lt;enumeration value="TIGER"/&gt;
 *     &lt;enumeration value="WHIRLPOOL"/&gt;
 *     &lt;enumeration value="unknown"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "messageDigestAlgorithmType")
@XmlEnum
public enum MessageDigestAlgorithmType {

    @XmlEnumValue("Adler-32")
    ADLER_32("Adler-32"),
    @XmlEnumValue("CRC32")
    CRC_32("CRC32"),
    HAVAL("HAVAL"),
    @XmlEnumValue("MD5")
    MD_5("MD5"),
    MNP("MNP"),
    @XmlEnumValue("SHA-1")
    SHA_1("SHA-1"),
    @XmlEnumValue("SHA-256")
    SHA_256("SHA-256"),
    @XmlEnumValue("SHA-384")
    SHA_384("SHA-384"),
    @XmlEnumValue("SHA-512")
    SHA_512("SHA-512"),
    TIGER("TIGER"),
    WHIRLPOOL("WHIRLPOOL"),
    @XmlEnumValue("unknown")
    UNKNOWN("unknown");
    private final String value;

    MessageDigestAlgorithmType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MessageDigestAlgorithmType fromValue(String v) {
        for (MessageDigestAlgorithmType c: MessageDigestAlgorithmType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
