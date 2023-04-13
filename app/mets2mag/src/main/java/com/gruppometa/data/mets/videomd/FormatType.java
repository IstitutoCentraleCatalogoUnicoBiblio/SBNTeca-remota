//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.videomd;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per formatType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="formatType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="creatorApp" type="{http://www.loc.gov/videoMD/}string.version" minOccurs="0"/&gt;
 *         &lt;element name="creatorLib" type="{http://www.loc.gov/videoMD/}string.version" minOccurs="0"/&gt;
 *         &lt;element name="creatorLibDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="creatorLibSettings" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="encodingDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaggedDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="commercialName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mimetype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="profile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="settings" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "formatType", propOrder = {
    "annotation",
    "creatorApp",
    "creatorLib",
    "creatorLibDate",
    "creatorLibSettings",
    "name",
    "encodingDate",
    "taggedDate",
    "commercialName",
    "mimetype",
    "profile",
    "settings",
    "version"
})
@XmlSeeAlso({
    CodecType.class
})
public class FormatType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected String annotation;
    protected StringVersion creatorApp;
    protected StringVersion creatorLib;
    protected String creatorLibDate;
    protected String creatorLibSettings;
    @XmlElement(required = true)
    protected String name;
    protected String encodingDate;
    @XmlElement(name = "TaggedDate")
    protected String taggedDate;
    protected String commercialName;
    protected String mimetype;
    protected String profile;
    protected String settings;
    protected String version;

    /**
     * Recupera il valore della proprietà annotation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Imposta il valore della proprietà annotation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

    /**
     * Recupera il valore della proprietà creatorApp.
     * 
     * @return
     *     possible object is
     *     {@link StringVersion }
     *     
     */
    public StringVersion getCreatorApp() {
        return creatorApp;
    }

    /**
     * Imposta il valore della proprietà creatorApp.
     * 
     * @param value
     *     allowed object is
     *     {@link StringVersion }
     *     
     */
    public void setCreatorApp(StringVersion value) {
        this.creatorApp = value;
    }

    /**
     * Recupera il valore della proprietà creatorLib.
     * 
     * @return
     *     possible object is
     *     {@link StringVersion }
     *     
     */
    public StringVersion getCreatorLib() {
        return creatorLib;
    }

    /**
     * Imposta il valore della proprietà creatorLib.
     * 
     * @param value
     *     allowed object is
     *     {@link StringVersion }
     *     
     */
    public void setCreatorLib(StringVersion value) {
        this.creatorLib = value;
    }

    /**
     * Recupera il valore della proprietà creatorLibDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorLibDate() {
        return creatorLibDate;
    }

    /**
     * Imposta il valore della proprietà creatorLibDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorLibDate(String value) {
        this.creatorLibDate = value;
    }

    /**
     * Recupera il valore della proprietà creatorLibSettings.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorLibSettings() {
        return creatorLibSettings;
    }

    /**
     * Imposta il valore della proprietà creatorLibSettings.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorLibSettings(String value) {
        this.creatorLibSettings = value;
    }

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà encodingDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncodingDate() {
        return encodingDate;
    }

    /**
     * Imposta il valore della proprietà encodingDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncodingDate(String value) {
        this.encodingDate = value;
    }

    /**
     * Recupera il valore della proprietà taggedDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaggedDate() {
        return taggedDate;
    }

    /**
     * Imposta il valore della proprietà taggedDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaggedDate(String value) {
        this.taggedDate = value;
    }

    /**
     * Recupera il valore della proprietà commercialName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommercialName() {
        return commercialName;
    }

    /**
     * Imposta il valore della proprietà commercialName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommercialName(String value) {
        this.commercialName = value;
    }

    /**
     * Recupera il valore della proprietà mimetype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimetype() {
        return mimetype;
    }

    /**
     * Imposta il valore della proprietà mimetype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimetype(String value) {
        this.mimetype = value;
    }

    /**
     * Recupera il valore della proprietà profile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfile() {
        return profile;
    }

    /**
     * Imposta il valore della proprietà profile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfile(String value) {
        this.profile = value;
    }

    /**
     * Recupera il valore della proprietà settings.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSettings() {
        return settings;
    }

    /**
     * Imposta il valore della proprietà settings.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSettings(String value) {
        this.settings = value;
    }

    /**
     * Recupera il valore della proprietà version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Imposta il valore della proprietà version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
