//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mods;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per detailDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="detailDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}number"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}caption"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}title"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="level" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailDefinition", propOrder = {
    "numbersAndCaptionsAndTitles"
})
@XmlRootElement(name = "detail")
public class Detail implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElementRefs({
        @XmlElementRef(name = "number", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "caption", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "title", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<StringPlusLanguage>> numbersAndCaptionsAndTitles;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "level")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger level;

    /**
     * Gets the value of the numbersAndCaptionsAndTitles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the numbersAndCaptionsAndTitles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNumbersAndCaptionsAndTitles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * 
     * 
     */
    public List<JAXBElement<StringPlusLanguage>> getNumbersAndCaptionsAndTitles() {
        if (numbersAndCaptionsAndTitles == null) {
            numbersAndCaptionsAndTitles = new ArrayList<JAXBElement<StringPlusLanguage>>();
        }
        return this.numbersAndCaptionsAndTitles;
    }

    /**
     * Recupera il valore della proprietà type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Imposta il valore della proprietà type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Recupera il valore della proprietà level.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLevel() {
        return level;
    }

    /**
     * Imposta il valore della proprietà level.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLevel(BigInteger value) {
        this.level = value;
    }

}
