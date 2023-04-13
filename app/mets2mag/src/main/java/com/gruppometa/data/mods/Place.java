//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per placeDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="placeDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}placeTerm" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="supplied" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="yes" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "placeDefinition", propOrder = {
    "placeTerms"
})
@XmlRootElement(name = "place")
public class Place implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "placeTerm", required = true)
    protected List<PlaceTerm> placeTerms;
    @XmlAttribute(name = "supplied")
    @XmlSchemaType(name = "anySimpleType")
    protected String supplied;

    /**
     * Gets the value of the placeTerms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the placeTerms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlaceTerms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlaceTerm }
     * 
     * 
     */
    public List<PlaceTerm> getPlaceTerms() {
        if (placeTerms == null) {
            placeTerms = new ArrayList<PlaceTerm>();
        }
        return this.placeTerms;
    }

    /**
     * Recupera il valore della proprietà supplied.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplied() {
        if (supplied == null) {
            return "yes";
        } else {
            return supplied;
        }
    }

    /**
     * Imposta il valore della proprietà supplied.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplied(String value) {
        this.supplied = value;
    }

}
