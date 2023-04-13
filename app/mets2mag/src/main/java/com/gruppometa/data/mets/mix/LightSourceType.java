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
 * <p>Classe Java per lightSourceType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="lightSourceType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Daylight"/&gt;
 *     &lt;enumeration value="Fluorescent"/&gt;
 *     &lt;enumeration value="Tungsten (incandescent light)"/&gt;
 *     &lt;enumeration value="Flash"/&gt;
 *     &lt;enumeration value="Fine weather"/&gt;
 *     &lt;enumeration value="Cloudy weather"/&gt;
 *     &lt;enumeration value="Shade"/&gt;
 *     &lt;enumeration value="Daylight fluorescent (D 5700 - 7100K)"/&gt;
 *     &lt;enumeration value="Day white fluorescent (N 4600 - 5400K)"/&gt;
 *     &lt;enumeration value="Cool white fluorescent (W 3900 - 4500K)"/&gt;
 *     &lt;enumeration value="White fluorescent (WW 3200 - 3700K)"/&gt;
 *     &lt;enumeration value="Standard light A"/&gt;
 *     &lt;enumeration value="Standard light B"/&gt;
 *     &lt;enumeration value="Standard light C"/&gt;
 *     &lt;enumeration value="D55"/&gt;
 *     &lt;enumeration value="D65"/&gt;
 *     &lt;enumeration value="D75"/&gt;
 *     &lt;enumeration value="D50"/&gt;
 *     &lt;enumeration value="ISO studio tungsten"/&gt;
 *     &lt;enumeration value="other light source"/&gt;
 *     &lt;enumeration value="unknown"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "lightSourceType")
@XmlEnum
public enum LightSourceType {

    @XmlEnumValue("Daylight")
    DAYLIGHT("Daylight"),
    @XmlEnumValue("Fluorescent")
    FLUORESCENT("Fluorescent"),
    @XmlEnumValue("Tungsten (incandescent light)")
    TUNGSTEN_INCANDESCENT_LIGHT("Tungsten (incandescent light)"),
    @XmlEnumValue("Flash")
    FLASH("Flash"),
    @XmlEnumValue("Fine weather")
    FINE_WEATHER("Fine weather"),
    @XmlEnumValue("Cloudy weather")
    CLOUDY_WEATHER("Cloudy weather"),
    @XmlEnumValue("Shade")
    SHADE("Shade"),
    @XmlEnumValue("Daylight fluorescent (D 5700 - 7100K)")
    DAYLIGHT_FLUORESCENT_D_5700_7100_K("Daylight fluorescent (D 5700 - 7100K)"),
    @XmlEnumValue("Day white fluorescent (N 4600 - 5400K)")
    DAY_WHITE_FLUORESCENT_N_4600_5400_K("Day white fluorescent (N 4600 - 5400K)"),
    @XmlEnumValue("Cool white fluorescent (W 3900 - 4500K)")
    COOL_WHITE_FLUORESCENT_W_3900_4500_K("Cool white fluorescent (W 3900 - 4500K)"),
    @XmlEnumValue("White fluorescent (WW 3200 - 3700K)")
    WHITE_FLUORESCENT_WW_3200_3700_K("White fluorescent (WW 3200 - 3700K)"),
    @XmlEnumValue("Standard light A")
    STANDARD_LIGHT_A("Standard light A"),
    @XmlEnumValue("Standard light B")
    STANDARD_LIGHT_B("Standard light B"),
    @XmlEnumValue("Standard light C")
    STANDARD_LIGHT_C("Standard light C"),
    @XmlEnumValue("D55")
    D_55("D55"),
    @XmlEnumValue("D65")
    D_65("D65"),
    @XmlEnumValue("D75")
    D_75("D75"),
    @XmlEnumValue("D50")
    D_50("D50"),
    @XmlEnumValue("ISO studio tungsten")
    ISO_STUDIO_TUNGSTEN("ISO studio tungsten"),
    @XmlEnumValue("other light source")
    OTHER_LIGHT_SOURCE("other light source"),
    @XmlEnumValue("unknown")
    UNKNOWN("unknown");
    private final String value;

    LightSourceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LightSourceType fromValue(String v) {
        for (LightSourceType c: LightSourceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
