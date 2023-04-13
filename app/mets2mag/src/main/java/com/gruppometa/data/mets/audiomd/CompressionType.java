//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.audiomd;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * compressionType: complexType for recording the type of compression
 * 				used for a digital audio file. compressionType has 1 attribute ID XML ID) and 4 elements codecCreatorApp, codecCreatorAppVersion, codecName and codecQuality.
 * 
 * <p>Classe Java per compressionType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="compressionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codecCreatorApp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codecCreatorAppVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codecName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codecQuality" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *               &lt;enumeration value="lossless"/&gt;
 *               &lt;enumeration value="lossy"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compressionType", propOrder = {
    "codecCreatorApp",
    "codecCreatorAppVersion",
    "codecName",
    "codecQuality"
})
public class CompressionType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected String codecCreatorApp;
    protected String codecCreatorAppVersion;
    protected String codecName;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String codecQuality;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà codecCreatorApp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodecCreatorApp() {
        return codecCreatorApp;
    }

    /**
     * Imposta il valore della proprietà codecCreatorApp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodecCreatorApp(String value) {
        this.codecCreatorApp = value;
    }

    /**
     * Recupera il valore della proprietà codecCreatorAppVersion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodecCreatorAppVersion() {
        return codecCreatorAppVersion;
    }

    /**
     * Imposta il valore della proprietà codecCreatorAppVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodecCreatorAppVersion(String value) {
        this.codecCreatorAppVersion = value;
    }

    /**
     * Recupera il valore della proprietà codecName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodecName() {
        return codecName;
    }

    /**
     * Imposta il valore della proprietà codecName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodecName(String value) {
        this.codecName = value;
    }

    /**
     * Recupera il valore della proprietà codecQuality.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodecQuality() {
        return codecQuality;
    }

    /**
     * Imposta il valore della proprietà codecQuality.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodecQuality(String value) {
        this.codecQuality = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

}
