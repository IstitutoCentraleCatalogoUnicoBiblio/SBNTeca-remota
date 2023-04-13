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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per spatialmetrics complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="spatialmetrics"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="samplingfrequencyunit" type="{http://www.niso.org/pdfs/DataDict.pdf}samplingfrequencyunittype"/&gt;
 *         &lt;element name="samplingfrequencyplane" type="{http://www.niso.org/pdfs/DataDict.pdf}samplingfrequencyplanetype"/&gt;
 *         &lt;element name="xsamplingfrequency" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="ysamplingfrequency" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="photometricinterpretation" type="{http://www.niso.org/pdfs/DataDict.pdf}photometricinterpretationtype"/&gt;
 *         &lt;element name="bitpersample" type="{http://www.niso.org/pdfs/DataDict.pdf}bitpersampletype"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spatialmetrics", propOrder = {
    "samplingfrequencyunit",
    "samplingfrequencyplane",
    "xsamplingfrequency",
    "ysamplingfrequency",
    "photometricinterpretation",
    "bitpersample"
})
public class Spatialmetrics
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(required = true)
    protected BigInteger samplingfrequencyunit;
    @XmlElement(required = true)
    protected BigInteger samplingfrequencyplane;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger xsamplingfrequency;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger ysamplingfrequency;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Photometricinterpretationtype photometricinterpretation;
    @XmlElement(required = true)
    protected String bitpersample;

    /**
     * Recupera il valore della proprietà samplingfrequencyunit.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSamplingfrequencyunit() {
        return samplingfrequencyunit;
    }

    /**
     * Imposta il valore della proprietà samplingfrequencyunit.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSamplingfrequencyunit(BigInteger value) {
        this.samplingfrequencyunit = value;
    }

    /**
     * Recupera il valore della proprietà samplingfrequencyplane.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSamplingfrequencyplane() {
        return samplingfrequencyplane;
    }

    /**
     * Imposta il valore della proprietà samplingfrequencyplane.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSamplingfrequencyplane(BigInteger value) {
        this.samplingfrequencyplane = value;
    }

    /**
     * Recupera il valore della proprietà xsamplingfrequency.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getXsamplingfrequency() {
        return xsamplingfrequency;
    }

    /**
     * Imposta il valore della proprietà xsamplingfrequency.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setXsamplingfrequency(BigInteger value) {
        this.xsamplingfrequency = value;
    }

    /**
     * Recupera il valore della proprietà ysamplingfrequency.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getYsamplingfrequency() {
        return ysamplingfrequency;
    }

    /**
     * Imposta il valore della proprietà ysamplingfrequency.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setYsamplingfrequency(BigInteger value) {
        this.ysamplingfrequency = value;
    }

    /**
     * Recupera il valore della proprietà photometricinterpretation.
     * 
     * @return
     *     possible object is
     *     {@link Photometricinterpretationtype }
     *     
     */
    public Photometricinterpretationtype getPhotometricinterpretation() {
        return photometricinterpretation;
    }

    /**
     * Imposta il valore della proprietà photometricinterpretation.
     * 
     * @param value
     *     allowed object is
     *     {@link Photometricinterpretationtype }
     *     
     */
    public void setPhotometricinterpretation(Photometricinterpretationtype value) {
        this.photometricinterpretation = value;
    }

    /**
     * Recupera il valore della proprietà bitpersample.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBitpersample() {
        return bitpersample;
    }

    /**
     * Imposta il valore della proprietà bitpersample.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBitpersample(String value) {
        this.bitpersample = value;
    }

}
