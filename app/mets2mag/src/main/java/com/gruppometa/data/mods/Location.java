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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per locationDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="locationDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}physicalLocation" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}shelfLocator" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}url" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}holdingSimple" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}holdingExternal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.loc.gov/mods/v3}languageAttributeGroup"/&gt;
 *       &lt;attribute name="displayLabel" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="altRepGroup" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "locationDefinition", propOrder = {
    "physicalLocations",
    "shelfLocators",
    "urls",
    "holdingSimple",
    "holdingExternal"
})
@XmlRootElement(name = "location")
public class Location implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "physicalLocation")
    protected List<PhysicalLocation> physicalLocations;
    @XmlElement(name = "shelfLocator")
    protected List<StringPlusLanguage> shelfLocators;
    @XmlElement(name = "url")
    protected List<Url> urls;
    protected HoldingSimple holdingSimple;
    protected ExtensionDefinition holdingExternal;
    @XmlAttribute(name = "displayLabel")
    protected String displayLabel;
    @XmlAttribute(name = "altRepGroup")
    protected String altRepGroup;
    @XmlAttribute(name = "lang")
    protected String langAttr;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;
    @XmlAttribute(name = "script")
    protected String script;
    @XmlAttribute(name = "transliteration")
    protected String transliteration;

    /**
     * Gets the value of the physicalLocations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the physicalLocations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhysicalLocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PhysicalLocation }
     * 
     * 
     */
    public List<PhysicalLocation> getPhysicalLocations() {
        if (physicalLocations == null) {
            physicalLocations = new ArrayList<PhysicalLocation>();
        }
        return this.physicalLocations;
    }

    /**
     * Gets the value of the shelfLocators property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shelfLocators property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShelfLocators().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringPlusLanguage }
     * 
     * 
     */
    public List<StringPlusLanguage> getShelfLocators() {
        if (shelfLocators == null) {
            shelfLocators = new ArrayList<StringPlusLanguage>();
        }
        return this.shelfLocators;
    }

    /**
     * Gets the value of the urls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the urls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUrls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Url }
     * 
     * 
     */
    public List<Url> getUrls() {
        if (urls == null) {
            urls = new ArrayList<Url>();
        }
        return this.urls;
    }

    /**
     * Recupera il valore della proprietà holdingSimple.
     * 
     * @return
     *     possible object is
     *     {@link HoldingSimple }
     *     
     */
    public HoldingSimple getHoldingSimple() {
        return holdingSimple;
    }

    /**
     * Imposta il valore della proprietà holdingSimple.
     * 
     * @param value
     *     allowed object is
     *     {@link HoldingSimple }
     *     
     */
    public void setHoldingSimple(HoldingSimple value) {
        this.holdingSimple = value;
    }

    /**
     * Recupera il valore della proprietà holdingExternal.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionDefinition }
     *     
     */
    public ExtensionDefinition getHoldingExternal() {
        return holdingExternal;
    }

    /**
     * Imposta il valore della proprietà holdingExternal.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionDefinition }
     *     
     */
    public void setHoldingExternal(ExtensionDefinition value) {
        this.holdingExternal = value;
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
     * Recupera il valore della proprietà langAttr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLangAttr() {
        return langAttr;
    }

    /**
     * Imposta il valore della proprietà langAttr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLangAttr(String value) {
        this.langAttr = value;
    }

    /**
     * Recupera il valore della proprietà lang.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Imposta il valore della proprietà lang.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Recupera il valore della proprietà script.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScript() {
        return script;
    }

    /**
     * Imposta il valore della proprietà script.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScript(String value) {
        this.script = value;
    }

    /**
     * Recupera il valore della proprietà transliteration.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransliteration() {
        return transliteration;
    }

    /**
     * Imposta il valore della proprietà transliteration.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransliteration(String value) {
        this.transliteration = value;
    }

}
