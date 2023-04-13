//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mods;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per regionDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="regionDefinition"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.loc.gov/mods/v3&gt;hierarchicalPart"&gt;
 *       &lt;attribute name="regionType" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "regionDefinition")
@XmlRootElement(name = "region")
public class Region
    extends HierarchicalPart
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "regionType")
    @XmlSchemaType(name = "anySimpleType")
    protected String regionType;

    /**
     * Recupera il valore della proprietà regionType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionType() {
        return regionType;
    }

    /**
     * Imposta il valore della proprietà regionType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionType(String value) {
        this.regionType = value;
    }

}
