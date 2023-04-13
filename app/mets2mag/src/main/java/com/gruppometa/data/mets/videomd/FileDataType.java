//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.videomd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * A type for describing technical characteristics of a video file.
 * 
 * <p>Classe Java per fileDataType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="fileDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.loc.gov/videoMD/}mediaDataType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bitsPerSample" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="byteOrder" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
 *               &lt;enumeration value="0"/&gt;
 *               &lt;enumeration value="1"/&gt;
 *               &lt;enumeration value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="color" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="B&amp;W"/&gt;
 *               &lt;enumeration value="Color"/&gt;
 *               &lt;enumeration value="Grayscale"/&gt;
 *               &lt;enumeration value="B&amp;W with grayscale sequences"/&gt;
 *               &lt;enumeration value="B&amp;W with color sequences"/&gt;
 *               &lt;enumeration value="Grayscale with B&amp;W sequences"/&gt;
 *               &lt;enumeration value="Grayscale with color sequences"/&gt;
 *               &lt;enumeration value="Color with B&amp;W sequences"/&gt;
 *               &lt;enumeration value="Color with grayscale sequences"/&gt;
 *               &lt;enumeration value="Other"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="otherColor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="messageDigest" type="{http://www.loc.gov/videoMD/}messageDigestType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;element name="compression" type="{http://www.loc.gov/videoMD/}compressionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="track" type="{http://www.loc.gov/videoMD/}trackDataType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="dataRateUnit" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataRateMode" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="Fixed"/&gt;
 *               &lt;enumeration value="Variable"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="frame" type="{http://www.loc.gov/videoMD/}frameType" minOccurs="0"/&gt;
 *         &lt;element name="frameRate" type="{http://www.loc.gov/videoMD/}variableRateType" minOccurs="0"/&gt;
 *         &lt;element name="sampleRate" type="{http://www.loc.gov/videoMD/}variableRateType" minOccurs="0"/&gt;
 *         &lt;element name="location" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="type" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;enumeration value="URN"/&gt;
 *                       &lt;enumeration value="URL"/&gt;
 *                       &lt;enumeration value="PURL"/&gt;
 *                       &lt;enumeration value="HANDLE"/&gt;
 *                       &lt;enumeration value="DOI"/&gt;
 *                       &lt;enumeration value="OTHER"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="otherType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="format" type="{http://www.loc.gov/videoMD/}formatType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="sampling" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="signalFormat" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="sound" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="Yes"/&gt;
 *               &lt;enumeration value="No"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileDataType", propOrder = {
    "bitsPerSamples",
    "byteOrders",
    "color",
    "otherColor",
    "messageDigests",
    "compressionsAndTracks",
    "dataRateUnits",
    "dataRateModes",
    "frame",
    "frameRate",
    "sampleRate",
    "locations",
    "formats",
    "samplings",
    "signalFormats",
    "sounds"
})
public class FileDataType
    extends MediaDataType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "bitsPerSample", type = Integer.class)
    protected List<Integer> bitsPerSamples;
    @XmlElement(name = "byteOrder", type = Integer.class)
    protected List<Integer> byteOrders;
    protected String color;
    protected String otherColor;
    @XmlElement(name = "messageDigest")
    protected List<MessageDigestType> messageDigests;
    @XmlElements({
        @XmlElement(name = "compression", type = CompressionType.class),
        @XmlElement(name = "track", type = TrackDataType.class)
    })
    protected List<Serializable> compressionsAndTracks;
    @XmlElement(name = "dataRateUnit")
    protected List<String> dataRateUnits;
    @XmlElement(name = "dataRateMode")
    protected List<String> dataRateModes;
    protected FrameType frame;
    protected VariableRateType frameRate;
    protected VariableRateType sampleRate;
    @XmlElement(name = "location")
    protected List<FileDataType.Location> locations;
    @XmlElement(name = "format")
    protected List<FormatType> formats;
    @XmlElement(name = "sampling")
    protected List<String> samplings;
    @XmlElement(name = "signalFormat")
    protected List<String> signalFormats;
    @XmlElement(name = "sound")
    protected List<String> sounds;

    /**
     * Gets the value of the bitsPerSamples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bitsPerSamples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBitsPerSamples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getBitsPerSamples() {
        if (bitsPerSamples == null) {
            bitsPerSamples = new ArrayList<Integer>();
        }
        return this.bitsPerSamples;
    }

    /**
     * Gets the value of the byteOrders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the byteOrders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getByteOrders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getByteOrders() {
        if (byteOrders == null) {
            byteOrders = new ArrayList<Integer>();
        }
        return this.byteOrders;
    }

    /**
     * Recupera il valore della proprietà color.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Imposta il valore della proprietà color.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Recupera il valore della proprietà otherColor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherColor() {
        return otherColor;
    }

    /**
     * Imposta il valore della proprietà otherColor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherColor(String value) {
        this.otherColor = value;
    }

    /**
     * Gets the value of the messageDigests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageDigests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageDigests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageDigestType }
     * 
     * 
     */
    public List<MessageDigestType> getMessageDigests() {
        if (messageDigests == null) {
            messageDigests = new ArrayList<MessageDigestType>();
        }
        return this.messageDigests;
    }

    /**
     * Gets the value of the compressionsAndTracks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compressionsAndTracks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompressionsAndTracks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CompressionType }
     * {@link TrackDataType }
     * 
     * 
     */
    public List<Serializable> getCompressionsAndTracks() {
        if (compressionsAndTracks == null) {
            compressionsAndTracks = new ArrayList<Serializable>();
        }
        return this.compressionsAndTracks;
    }

    /**
     * Gets the value of the dataRateUnits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataRateUnits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataRateUnits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataRateUnits() {
        if (dataRateUnits == null) {
            dataRateUnits = new ArrayList<String>();
        }
        return this.dataRateUnits;
    }

    /**
     * Gets the value of the dataRateModes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataRateModes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataRateModes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataRateModes() {
        if (dataRateModes == null) {
            dataRateModes = new ArrayList<String>();
        }
        return this.dataRateModes;
    }

    /**
     * Recupera il valore della proprietà frame.
     * 
     * @return
     *     possible object is
     *     {@link FrameType }
     *     
     */
    public FrameType getFrame() {
        return frame;
    }

    /**
     * Imposta il valore della proprietà frame.
     * 
     * @param value
     *     allowed object is
     *     {@link FrameType }
     *     
     */
    public void setFrame(FrameType value) {
        this.frame = value;
    }

    /**
     * Recupera il valore della proprietà frameRate.
     * 
     * @return
     *     possible object is
     *     {@link VariableRateType }
     *     
     */
    public VariableRateType getFrameRate() {
        return frameRate;
    }

    /**
     * Imposta il valore della proprietà frameRate.
     * 
     * @param value
     *     allowed object is
     *     {@link VariableRateType }
     *     
     */
    public void setFrameRate(VariableRateType value) {
        this.frameRate = value;
    }

    /**
     * Recupera il valore della proprietà sampleRate.
     * 
     * @return
     *     possible object is
     *     {@link VariableRateType }
     *     
     */
    public VariableRateType getSampleRate() {
        return sampleRate;
    }

    /**
     * Imposta il valore della proprietà sampleRate.
     * 
     * @param value
     *     allowed object is
     *     {@link VariableRateType }
     *     
     */
    public void setSampleRate(VariableRateType value) {
        this.sampleRate = value;
    }

    /**
     * Gets the value of the locations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileDataType.Location }
     * 
     * 
     */
    public List<FileDataType.Location> getLocations() {
        if (locations == null) {
            locations = new ArrayList<FileDataType.Location>();
        }
        return this.locations;
    }

    /**
     * Gets the value of the formats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormatType }
     * 
     * 
     */
    public List<FormatType> getFormats() {
        if (formats == null) {
            formats = new ArrayList<FormatType>();
        }
        return this.formats;
    }

    /**
     * Gets the value of the samplings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the samplings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSamplings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSamplings() {
        if (samplings == null) {
            samplings = new ArrayList<String>();
        }
        return this.samplings;
    }

    /**
     * Gets the value of the signalFormats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signalFormats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignalFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSignalFormats() {
        if (signalFormats == null) {
            signalFormats = new ArrayList<String>();
        }
        return this.signalFormats;
    }

    /**
     * Gets the value of the sounds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sounds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSounds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSounds() {
        if (sounds == null) {
            sounds = new ArrayList<String>();
        }
        return this.sounds;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="type" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *             &lt;enumeration value="URN"/&gt;
     *             &lt;enumeration value="URL"/&gt;
     *             &lt;enumeration value="PURL"/&gt;
     *             &lt;enumeration value="HANDLE"/&gt;
     *             &lt;enumeration value="DOI"/&gt;
     *             &lt;enumeration value="OTHER"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="otherType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Location
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "type", required = true)
        protected String type;
        @XmlAttribute(name = "otherType")
        protected String otherType;

        /**
         * Recupera il valore della proprietà value.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Imposta il valore della proprietà value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
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
         * Recupera il valore della proprietà otherType.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOtherType() {
            return otherType;
        }

        /**
         * Imposta il valore della proprietà otherType.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOtherType(String value) {
            this.otherType = value;
        }

    }

}
