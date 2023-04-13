//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.videomd;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per mediaDataType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="mediaDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tracking" type="{http://www.loc.gov/videoMD/}trackingInfoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="language" type="{http://www.loc.gov/videoMD/}languageType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="security" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
 *         &lt;element name="dataRate" type="{http://www.loc.gov/videoMD/}variableRateType" minOccurs="0"/&gt;
 *         &lt;element name="timecode" type="{http://www.loc.gov/videoMD/}timecodeInfoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="use" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="Master"/&gt;
 *               &lt;enumeration value="Service"/&gt;
 *               &lt;enumeration value="Service_High"/&gt;
 *               &lt;enumeration value="Service_Low"/&gt;
 *               &lt;enumeration value="Preview"/&gt;
 *               &lt;enumeration value="Other"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="otherUse" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mediaDataType", propOrder = {
    "trackings",
    "duration",
    "languages",
    "security",
    "size",
    "dataRate",
    "timecodes",
    "uses",
    "otherUses"
})
@XmlSeeAlso({
    FileDataType.class,
    TrackDataType.class
})
public class MediaDataType implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "tracking")
    protected List<TrackingInfoType> trackings;
    protected String duration;
    @XmlElement(name = "language")
    protected List<String> languages;
    protected String security;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger size;
    protected VariableRateType dataRate;
    @XmlElement(name = "timecode")
    protected List<TimecodeInfoType> timecodes;
    @XmlElement(name = "use")
    protected List<String> uses;
    @XmlElement(name = "otherUse")
    protected List<String> otherUses;

    /**
     * Gets the value of the trackings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trackings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrackings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrackingInfoType }
     * 
     * 
     */
    public List<TrackingInfoType> getTrackings() {
        if (trackings == null) {
            trackings = new ArrayList<TrackingInfoType>();
        }
        return this.trackings;
    }

    /**
     * Recupera il valore della proprietà duration.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Imposta il valore della proprietà duration.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Gets the value of the languages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the languages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLanguages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLanguages() {
        if (languages == null) {
            languages = new ArrayList<String>();
        }
        return this.languages;
    }

    /**
     * Recupera il valore della proprietà security.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurity() {
        return security;
    }

    /**
     * Imposta il valore della proprietà security.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurity(String value) {
        this.security = value;
    }

    /**
     * Recupera il valore della proprietà size.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSize() {
        return size;
    }

    /**
     * Imposta il valore della proprietà size.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSize(BigInteger value) {
        this.size = value;
    }

    /**
     * Recupera il valore della proprietà dataRate.
     * 
     * @return
     *     possible object is
     *     {@link VariableRateType }
     *     
     */
    public VariableRateType getDataRate() {
        return dataRate;
    }

    /**
     * Imposta il valore della proprietà dataRate.
     * 
     * @param value
     *     allowed object is
     *     {@link VariableRateType }
     *     
     */
    public void setDataRate(VariableRateType value) {
        this.dataRate = value;
    }

    /**
     * Gets the value of the timecodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timecodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimecodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimecodeInfoType }
     * 
     * 
     */
    public List<TimecodeInfoType> getTimecodes() {
        if (timecodes == null) {
            timecodes = new ArrayList<TimecodeInfoType>();
        }
        return this.timecodes;
    }

    /**
     * Gets the value of the uses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getUses() {
        if (uses == null) {
            uses = new ArrayList<String>();
        }
        return this.uses;
    }

    /**
     * Gets the value of the otherUses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherUses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherUses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOtherUses() {
        if (otherUses == null) {
            otherUses = new ArrayList<String>();
        }
        return this.otherUses;
    }

}
