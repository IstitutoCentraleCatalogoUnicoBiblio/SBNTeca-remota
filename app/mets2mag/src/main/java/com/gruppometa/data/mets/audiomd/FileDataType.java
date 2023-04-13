//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.audiomd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * The element is used to describe technical characteristics of an audio file.
 * 
 * <p>Classe Java per fileDataType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="fileDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="audioBlockSize" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="audioDataEncoding" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
 *         &lt;element name="messageDigest" type="{http://www.loc.gov/audioMD/}messageDigestType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="compression" type="{http://www.loc.gov/audioMD/}compressionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataRate" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataRateMode" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="Fixed"/&gt;
 *               &lt;enumeration value="Variable"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="firstSampleOffset" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="firstValidByteBlock" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="formatLocation" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="formatName" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="formatNote" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="formatVersion" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lastValidByteBlock" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="numSampleFrames" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="samplingFrequency" type="{http://www.w3.org/2001/XMLSchema}float" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="security" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
 *         &lt;element name="wordSize" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "fileDataType", propOrder = {
    "audioBlockSizes",
    "audioDataEncodings",
    "bitsPerSamples",
    "byteOrders",
    "messageDigests",
    "compressions",
    "dataRates",
    "dataRateModes",
    "firstSampleOffsets",
    "firstValidByteBlocks",
    "formatLocations",
    "formatNames",
    "formatNotes",
    "formatVersions",
    "lastValidByteBlocks",
    "numSampleFrames",
    "samplingFrequencies",
    "securities",
    "uses",
    "otherUses",
    "wordSizes"
})
public class FileDataType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "audioBlockSize", type = Integer.class)
    protected List<Integer> audioBlockSizes;
    @XmlElement(name = "audioDataEncoding")
    protected List<String> audioDataEncodings;
    @XmlElement(name = "bitsPerSample", type = Integer.class)
    protected List<Integer> bitsPerSamples;
    @XmlElement(name = "byteOrder", type = Integer.class)
    protected List<Integer> byteOrders;
    @XmlElement(name = "messageDigest")
    protected List<MessageDigestType> messageDigests;
    @XmlElement(name = "compression")
    protected List<CompressionType> compressions;
    @XmlElement(name = "dataRate", type = Integer.class)
    protected List<Integer> dataRates;
    @XmlElement(name = "dataRateMode")
    protected List<String> dataRateModes;
    @XmlElement(name = "firstSampleOffset", type = Integer.class)
    protected List<Integer> firstSampleOffsets;
    @XmlElement(name = "firstValidByteBlock", type = Integer.class)
    protected List<Integer> firstValidByteBlocks;
    @XmlElement(name = "formatLocation")
    protected List<String> formatLocations;
    @XmlElement(name = "formatName")
    protected List<String> formatNames;
    @XmlElement(name = "formatNote")
    protected List<String> formatNotes;
    @XmlElement(name = "formatVersion")
    protected List<String> formatVersions;
    @XmlElement(name = "lastValidByteBlock", type = Integer.class)
    protected List<Integer> lastValidByteBlocks;
    @XmlElement(type = Integer.class)
    protected List<Integer> numSampleFrames;
    @XmlElement(name = "samplingFrequency", type = Float.class)
    protected List<Float> samplingFrequencies;
    @XmlElement(name = "security")
    protected List<String> securities;
    @XmlElement(name = "use")
    protected List<String> uses;
    @XmlElement(name = "otherUse")
    protected List<String> otherUses;
    @XmlElement(name = "wordSize", type = Integer.class)
    protected List<Integer> wordSizes;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the audioBlockSizes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the audioBlockSizes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAudioBlockSizes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getAudioBlockSizes() {
        if (audioBlockSizes == null) {
            audioBlockSizes = new ArrayList<Integer>();
        }
        return this.audioBlockSizes;
    }

    /**
     * Gets the value of the audioDataEncodings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the audioDataEncodings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAudioDataEncodings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAudioDataEncodings() {
        if (audioDataEncodings == null) {
            audioDataEncodings = new ArrayList<String>();
        }
        return this.audioDataEncodings;
    }

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
     * Gets the value of the compressions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compressions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompressions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CompressionType }
     * 
     * 
     */
    public List<CompressionType> getCompressions() {
        if (compressions == null) {
            compressions = new ArrayList<CompressionType>();
        }
        return this.compressions;
    }

    /**
     * Gets the value of the dataRates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataRates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataRates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getDataRates() {
        if (dataRates == null) {
            dataRates = new ArrayList<Integer>();
        }
        return this.dataRates;
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
     * Gets the value of the firstSampleOffsets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the firstSampleOffsets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFirstSampleOffsets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getFirstSampleOffsets() {
        if (firstSampleOffsets == null) {
            firstSampleOffsets = new ArrayList<Integer>();
        }
        return this.firstSampleOffsets;
    }

    /**
     * Gets the value of the firstValidByteBlocks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the firstValidByteBlocks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFirstValidByteBlocks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getFirstValidByteBlocks() {
        if (firstValidByteBlocks == null) {
            firstValidByteBlocks = new ArrayList<Integer>();
        }
        return this.firstValidByteBlocks;
    }

    /**
     * Gets the value of the formatLocations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formatLocations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormatLocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFormatLocations() {
        if (formatLocations == null) {
            formatLocations = new ArrayList<String>();
        }
        return this.formatLocations;
    }

    /**
     * Gets the value of the formatNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formatNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormatNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFormatNames() {
        if (formatNames == null) {
            formatNames = new ArrayList<String>();
        }
        return this.formatNames;
    }

    /**
     * Gets the value of the formatNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formatNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormatNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFormatNotes() {
        if (formatNotes == null) {
            formatNotes = new ArrayList<String>();
        }
        return this.formatNotes;
    }

    /**
     * Gets the value of the formatVersions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formatVersions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormatVersions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFormatVersions() {
        if (formatVersions == null) {
            formatVersions = new ArrayList<String>();
        }
        return this.formatVersions;
    }

    /**
     * Gets the value of the lastValidByteBlocks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lastValidByteBlocks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLastValidByteBlocks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getLastValidByteBlocks() {
        if (lastValidByteBlocks == null) {
            lastValidByteBlocks = new ArrayList<Integer>();
        }
        return this.lastValidByteBlocks;
    }

    /**
     * Gets the value of the numSampleFrames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the numSampleFrames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNumSampleFrames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getNumSampleFrames() {
        if (numSampleFrames == null) {
            numSampleFrames = new ArrayList<Integer>();
        }
        return this.numSampleFrames;
    }

    /**
     * Gets the value of the samplingFrequencies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the samplingFrequencies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSamplingFrequencies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Float }
     * 
     * 
     */
    public List<Float> getSamplingFrequencies() {
        if (samplingFrequencies == null) {
            samplingFrequencies = new ArrayList<Float>();
        }
        return this.samplingFrequencies;
    }

    /**
     * Gets the value of the securities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the securities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecurities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSecurities() {
        if (securities == null) {
            securities = new ArrayList<String>();
        }
        return this.securities;
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

    /**
     * Gets the value of the wordSizes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wordSizes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWordSizes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getWordSizes() {
        if (wordSizes == null) {
            wordSizes = new ArrayList<Integer>();
        }
        return this.wordSizes;
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
