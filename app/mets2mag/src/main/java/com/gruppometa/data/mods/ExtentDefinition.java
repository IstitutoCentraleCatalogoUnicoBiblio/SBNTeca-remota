//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mods;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per extentDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="extentDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}start" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}end" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}total" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}list" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "extentDefinition", propOrder = {
    "start",
    "end",
    "total",
    "list"
})
public class ExtentDefinition implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected StringPlusLanguage start;
    protected StringPlusLanguage end;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger total;
    protected StringPlusLanguage list;
    @XmlAttribute(name = "unit")
    protected String unit;

    /**
     * Recupera il valore della proprietà start.
     * 
     * @return
     *     possible object is
     *     {@link StringPlusLanguage }
     *     
     */
    public StringPlusLanguage getStart() {
        return start;
    }

    /**
     * Imposta il valore della proprietà start.
     * 
     * @param value
     *     allowed object is
     *     {@link StringPlusLanguage }
     *     
     */
    public void setStart(StringPlusLanguage value) {
        this.start = value;
    }

    /**
     * Recupera il valore della proprietà end.
     * 
     * @return
     *     possible object is
     *     {@link StringPlusLanguage }
     *     
     */
    public StringPlusLanguage getEnd() {
        return end;
    }

    /**
     * Imposta il valore della proprietà end.
     * 
     * @param value
     *     allowed object is
     *     {@link StringPlusLanguage }
     *     
     */
    public void setEnd(StringPlusLanguage value) {
        this.end = value;
    }

    /**
     * Recupera il valore della proprietà total.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotal() {
        return total;
    }

    /**
     * Imposta il valore della proprietà total.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotal(BigInteger value) {
        this.total = value;
    }

    /**
     * Recupera il valore della proprietà list.
     * 
     * @return
     *     possible object is
     *     {@link StringPlusLanguage }
     *     
     */
    public StringPlusLanguage getList() {
        return list;
    }

    /**
     * Imposta il valore della proprietà list.
     * 
     * @param value
     *     allowed object is
     *     {@link StringPlusLanguage }
     *     
     */
    public void setList(StringPlusLanguage value) {
        this.list = value;
    }

    /**
     * Recupera il valore della proprietà unit.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Imposta il valore della proprietà unit.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

}
