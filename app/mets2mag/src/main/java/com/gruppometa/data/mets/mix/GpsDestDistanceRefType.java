//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.mix;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per gpsDestDistanceRefType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="gpsDestDistanceRefType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="K"/&gt;
 *     &lt;enumeration value="M"/&gt;
 *     &lt;enumeration value="N"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "gpsDestDistanceRefType")
@XmlEnum
public enum GpsDestDistanceRefType {

    K,
    M,
    N;

    public String value() {
        return name();
    }

    public static GpsDestDistanceRefType fromValue(String v) {
        return valueOf(v);
    }

}
