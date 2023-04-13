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
 * <p>Classe Java per scannerSensorType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="scannerSensorType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="undefined"/&gt;
 *     &lt;enumeration value="MonochromeLinear"/&gt;
 *     &lt;enumeration value="ColorTriLinear"/&gt;
 *     &lt;enumeration value="ColorSequentialLinear"/&gt;
 *     &lt;enumeration value="MonochromeArea"/&gt;
 *     &lt;enumeration value="OneChipColourArea"/&gt;
 *     &lt;enumeration value="TwoChipColorArea"/&gt;
 *     &lt;enumeration value="ThreeChipColorArea"/&gt;
 *     &lt;enumeration value="ColorSequentialArea"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "scannerSensorType")
@XmlEnum
public enum ScannerSensorType {

    @XmlEnumValue("undefined")
    UNDEFINED("undefined"),
    @XmlEnumValue("MonochromeLinear")
    MONOCHROME_LINEAR("MonochromeLinear"),
    @XmlEnumValue("ColorTriLinear")
    COLOR_TRI_LINEAR("ColorTriLinear"),
    @XmlEnumValue("ColorSequentialLinear")
    COLOR_SEQUENTIAL_LINEAR("ColorSequentialLinear"),
    @XmlEnumValue("MonochromeArea")
    MONOCHROME_AREA("MonochromeArea"),
    @XmlEnumValue("OneChipColourArea")
    ONE_CHIP_COLOUR_AREA("OneChipColourArea"),
    @XmlEnumValue("TwoChipColorArea")
    TWO_CHIP_COLOR_AREA("TwoChipColorArea"),
    @XmlEnumValue("ThreeChipColorArea")
    THREE_CHIP_COLOR_AREA("ThreeChipColorArea"),
    @XmlEnumValue("ColorSequentialArea")
    COLOR_SEQUENTIAL_AREA("ColorSequentialArea");
    private final String value;

    ScannerSensorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ScannerSensorType fromValue(String v) {
        for (ScannerSensorType c: ScannerSensorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
