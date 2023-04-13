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
 * <p>Classe Java per citySectionDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="citySectionDefinition"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.loc.gov/mods/v3&gt;hierarchicalPart"&gt;
 *       &lt;attribute name="citySectionType" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "citySectionDefinition")
@XmlRootElement(name = "citySection")
public class CitySection
    extends HierarchicalPart
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "citySectionType")
    @XmlSchemaType(name = "anySimpleType")
    protected String citySectionType;

    /**
     * Recupera il valore della proprietà citySectionType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitySectionType() {
        return citySectionType;
    }

    /**
     * Imposta il valore della proprietà citySectionType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitySectionType(String value) {
        this.citySectionType = value;
    }

}
