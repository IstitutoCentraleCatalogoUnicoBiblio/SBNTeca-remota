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
 * <p>Classe Java per orientationType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="orientationType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="normal*"/&gt;
 *     &lt;enumeration value="normal, image flipped"/&gt;
 *     &lt;enumeration value="normal, rotated 180°"/&gt;
 *     &lt;enumeration value="normal, image flipped, rotated 180°"/&gt;
 *     &lt;enumeration value="normal, image flipped, rotated cw 90°"/&gt;
 *     &lt;enumeration value="normal, rotated ccw 90°"/&gt;
 *     &lt;enumeration value="normal, image flipped, rotated ccw 90°"/&gt;
 *     &lt;enumeration value="normal, rotated cw 90°"/&gt;
 *     &lt;enumeration value="unknown"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "orientationType")
@XmlEnum
public enum OrientationType {

    @XmlEnumValue("normal*")
    NORMAL("normal*"),
    @XmlEnumValue("normal, image flipped")
    NORMAL_IMAGE_FLIPPED("normal, image flipped"),
    @XmlEnumValue("normal, rotated 180\u00b0")
    NORMAL_ROTATED_180("normal, rotated 180\u00b0"),
    @XmlEnumValue("normal, image flipped, rotated 180\u00b0")
    NORMAL_IMAGE_FLIPPED_ROTATED_180("normal, image flipped, rotated 180\u00b0"),
    @XmlEnumValue("normal, image flipped, rotated cw 90\u00b0")
    NORMAL_IMAGE_FLIPPED_ROTATED_CW_90("normal, image flipped, rotated cw 90\u00b0"),
    @XmlEnumValue("normal, rotated ccw 90\u00b0")
    NORMAL_ROTATED_CCW_90("normal, rotated ccw 90\u00b0"),
    @XmlEnumValue("normal, image flipped, rotated ccw 90\u00b0")
    NORMAL_IMAGE_FLIPPED_ROTATED_CCW_90("normal, image flipped, rotated ccw 90\u00b0"),
    @XmlEnumValue("normal, rotated cw 90\u00b0")
    NORMAL_ROTATED_CW_90("normal, rotated cw 90\u00b0"),
    @XmlEnumValue("unknown")
    UNKNOWN("unknown");
    private final String value;

    OrientationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrientationType fromValue(String v) {
        for (OrientationType c: OrientationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
