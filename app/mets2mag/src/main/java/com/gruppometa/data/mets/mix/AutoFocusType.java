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
 * <p>Classe Java per autoFocusType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="autoFocusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Auto Focus Used"/&gt;
 *     &lt;enumeration value="Auto Focus Interrupted"/&gt;
 *     &lt;enumeration value="Near Focused"/&gt;
 *     &lt;enumeration value="Soft Focused"/&gt;
 *     &lt;enumeration value="Manual"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "autoFocusType")
@XmlEnum
public enum AutoFocusType {

    @XmlEnumValue("Auto Focus Used")
    AUTO_FOCUS_USED("Auto Focus Used"),
    @XmlEnumValue("Auto Focus Interrupted")
    AUTO_FOCUS_INTERRUPTED("Auto Focus Interrupted"),
    @XmlEnumValue("Near Focused")
    NEAR_FOCUSED("Near Focused"),
    @XmlEnumValue("Soft Focused")
    SOFT_FOCUSED("Soft Focused"),
    @XmlEnumValue("Manual")
    MANUAL("Manual");
    private final String value;

    AutoFocusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutoFocusType fromValue(String v) {
        for (AutoFocusType c: AutoFocusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
