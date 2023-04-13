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
 * <p>Classe Java per photometricinterpretationtype.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="photometricinterpretationtype"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="WhiteIsZero"/&gt;
 *     &lt;enumeration value="BlackIsZero"/&gt;
 *     &lt;enumeration value="RGB"/&gt;
 *     &lt;enumeration value="Palette color"/&gt;
 *     &lt;enumeration value="Transparency Mask"/&gt;
 *     &lt;enumeration value="CMYK"/&gt;
 *     &lt;enumeration value="YCbCr"/&gt;
 *     &lt;enumeration value="CIELab"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "photometricinterpretationtype")
@XmlEnum
public enum Photometricinterpretationtype {

    @XmlEnumValue("WhiteIsZero")
    WHITE_IS_ZERO("WhiteIsZero"),
    @XmlEnumValue("BlackIsZero")
    BLACK_IS_ZERO("BlackIsZero"),
    RGB("RGB"),
    @XmlEnumValue("Palette color")
    PALETTE_COLOR("Palette color"),
    @XmlEnumValue("Transparency Mask")
    TRANSPARENCY_MASK("Transparency Mask"),
    CMYK("CMYK"),
    @XmlEnumValue("YCbCr")
    Y_CB_CR("YCbCr"),
    @XmlEnumValue("CIELab")
    CIE_LAB("CIELab");
    private final String value;

    Photometricinterpretationtype(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Photometricinterpretationtype fromValue(String v) {
        for (Photometricinterpretationtype c: Photometricinterpretationtype.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
