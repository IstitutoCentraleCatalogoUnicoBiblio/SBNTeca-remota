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
 * <p>Classe Java per targetdata complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="targetdata"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="targetType" type="{http://www.niso.org/pdfs/DataDict.pdf}targettype" minOccurs="0"/&gt;
 *         &lt;element name="targetID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="imageData" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *         &lt;element name="performanceData" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *         &lt;element name="profiles" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "targetdata", propOrder = {
    "targetType",
    "targetID",
    "imageData",
    "performanceData",
    "profiles"
})
public class Targetdata
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected BigInteger targetType;
    @XmlElement(required = true)
    protected String targetID;
    @XmlSchemaType(name = "anyURI")
    protected String imageData;
    @XmlSchemaType(name = "anyURI")
    protected String performanceData;
    @XmlSchemaType(name = "anyURI")
    protected String profiles;

    /**
     * Recupera il valore della proprietà targetType.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTargetType() {
        return targetType;
    }

    /**
     * Imposta il valore della proprietà targetType.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTargetType(BigInteger value) {
        this.targetType = value;
    }

    /**
     * Recupera il valore della proprietà targetID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetID() {
        return targetID;
    }

    /**
     * Imposta il valore della proprietà targetID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetID(String value) {
        this.targetID = value;
    }

    /**
     * Recupera il valore della proprietà imageData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageData() {
        return imageData;
    }

    /**
     * Imposta il valore della proprietà imageData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageData(String value) {
        this.imageData = value;
    }

    /**
     * Recupera il valore della proprietà performanceData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformanceData() {
        return performanceData;
    }

    /**
     * Imposta il valore della proprietà performanceData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformanceData(String value) {
        this.performanceData = value;
    }

    /**
     * Recupera il valore della proprietà profiles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfiles() {
        return profiles;
    }

    /**
     * Imposta il valore della proprietà profiles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfiles(String value) {
        this.profiles = value;
    }

}
