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
 * <p>Classe Java per typeOfResourceDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="typeOfResourceDefinition"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.loc.gov/mods/v3&gt;stringPlusLanguagePlusAuthority"&gt;
 *       &lt;attribute name="collection" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="yes" /&gt;
 *       &lt;attribute name="manuscript" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="yes" /&gt;
 *       &lt;attribute name="displayLabel" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="altRepGroup" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="usage" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="primary" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeOfResourceDefinition")
@XmlRootElement(name = "typeOfResource")
public class TypeOfResource
    extends StringPlusLanguagePlusAuthority
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "collection")
    @XmlSchemaType(name = "anySimpleType")
    protected String collection;
    @XmlAttribute(name = "manuscript")
    @XmlSchemaType(name = "anySimpleType")
    protected String manuscript;
    @XmlAttribute(name = "displayLabel")
    protected String displayLabel;
    @XmlAttribute(name = "altRepGroup")
    protected String altRepGroup;
    @XmlAttribute(name = "usage")
    @XmlSchemaType(name = "anySimpleType")
    protected String usage;

    /**
     * Recupera il valore della proprietà collection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollection() {
        if (collection == null) {
            return "yes";
        } else {
            return collection;
        }
    }

    /**
     * Imposta il valore della proprietà collection.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollection(String value) {
        this.collection = value;
    }

    /**
     * Recupera il valore della proprietà manuscript.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManuscript() {
        if (manuscript == null) {
            return "yes";
        } else {
            return manuscript;
        }
    }

    /**
     * Imposta il valore della proprietà manuscript.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManuscript(String value) {
        this.manuscript = value;
    }

    /**
     * Recupera il valore della proprietà displayLabel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayLabel() {
        return displayLabel;
    }

    /**
     * Imposta il valore della proprietà displayLabel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayLabel(String value) {
        this.displayLabel = value;
    }

    /**
     * Recupera il valore della proprietà altRepGroup.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltRepGroup() {
        return altRepGroup;
    }

    /**
     * Imposta il valore della proprietà altRepGroup.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltRepGroup(String value) {
        this.altRepGroup = value;
    }

    /**
     * Recupera il valore della proprietà usage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsage() {
        if (usage == null) {
            return "primary";
        } else {
            return usage;
        }
    }

    /**
     * Imposta il valore della proprietà usage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsage(String value) {
        this.usage = value;
    }

}
