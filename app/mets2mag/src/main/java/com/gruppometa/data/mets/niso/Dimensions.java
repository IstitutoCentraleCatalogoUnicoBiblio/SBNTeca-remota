//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.niso;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per dimensions complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="dimensions"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="imagelength" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;element name="imagewidth" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;element name="source_xdimension" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="source_ydimension" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dimensions", propOrder = {
    "imagelength",
    "imagewidth",
    "sourceXdimension",
    "sourceYdimension"
})
@XmlRootElement(name = "dimensions")
public class Dimensions
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger imagelength;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger imagewidth;
    @XmlElement(name = "source_xdimension")
    protected Double sourceXdimension;
    @XmlElement(name = "source_ydimension")
    protected Double sourceYdimension;

    /**
     * Recupera il valore della proprietà imagelength.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getImagelength() {
        return imagelength;
    }

    /**
     * Imposta il valore della proprietà imagelength.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setImagelength(BigInteger value) {
        this.imagelength = value;
    }

    /**
     * Recupera il valore della proprietà imagewidth.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getImagewidth() {
        return imagewidth;
    }

    /**
     * Imposta il valore della proprietà imagewidth.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setImagewidth(BigInteger value) {
        this.imagewidth = value;
    }

    /**
     * Recupera il valore della proprietà sourceXdimension.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSourceXdimension() {
        return sourceXdimension;
    }

    /**
     * Imposta il valore della proprietà sourceXdimension.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSourceXdimension(Double value) {
        this.sourceXdimension = value;
    }

    /**
     * Recupera il valore della proprietà sourceYdimension.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSourceYdimension() {
        return sourceYdimension;
    }

    /**
     * Imposta il valore della proprietà sourceYdimension.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSourceYdimension(Double value) {
        this.sourceYdimension = value;
    }

}
