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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per originInfoDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="originInfoDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}place"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}publisher"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}dateIssued"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}dateCreated"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}dateCaptured"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}dateValid"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}dateModified"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}copyrightDate"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}dateOther"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}edition"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}issuance"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}frequency"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attGroup ref="{http://www.loc.gov/mods/v3}languageAttributeGroup"/&gt;
 *       &lt;attribute name="displayLabel" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="altRepGroup" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="eventType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "originInfoDefinition", propOrder = {
    "placesAndPublishersAndDateIssueds"
})
@XmlRootElement(name = "originInfo")
public class OriginInfo implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElementRefs({
        @XmlElementRef(name = "place", namespace = "http://www.loc.gov/mods/v3", type = Place.class, required = false),
        @XmlElementRef(name = "publisher", namespace = "http://www.loc.gov/mods/v3", type = Publisher.class, required = false),
        @XmlElementRef(name = "dateIssued", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "dateCreated", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "dateCaptured", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "dateValid", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "dateModified", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "copyrightDate", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "dateOther", namespace = "http://www.loc.gov/mods/v3", type = DateOther.class, required = false),
        @XmlElementRef(name = "edition", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "issuance", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "frequency", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false)
    })
    protected List<Serializable> placesAndPublishersAndDateIssueds;
    @XmlAttribute(name = "displayLabel")
    protected String displayLabel;
    @XmlAttribute(name = "altRepGroup")
    protected String altRepGroup;
    @XmlAttribute(name = "eventType")
    protected String eventType;
    @XmlAttribute(name = "lang")
    protected String langAttr;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;
    @XmlAttribute(name = "script")
    protected String script;
    @XmlAttribute(name = "transliteration")
    protected String transliteration;

    /**
     * Gets the value of the placesAndPublishersAndDateIssueds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the placesAndPublishersAndDateIssueds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlacesAndPublishersAndDateIssueds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Place }
     * {@link Publisher }
     * {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link DateDefinition }{@code >}
     * {@link DateOther }
     * {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusSupplied }{@code >}
     * {@link JAXBElement }{@code <}{@link IssuanceDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link StringPlusLanguagePlusAuthority }{@code >}
     * 
     * 
     */
    public List<Serializable> getPlacesAndPublishersAndDateIssueds() {
        if (placesAndPublishersAndDateIssueds == null) {
            placesAndPublishersAndDateIssueds = new ArrayList<Serializable>();
        }
        return this.placesAndPublishersAndDateIssueds;
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
     * Recupera il valore della proprietà eventType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Imposta il valore della proprietà eventType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventType(String value) {
        this.eventType = value;
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
