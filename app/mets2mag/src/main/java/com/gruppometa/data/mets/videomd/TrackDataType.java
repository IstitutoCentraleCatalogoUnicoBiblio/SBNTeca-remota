//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.videomd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per trackDataType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="trackDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.loc.gov/videoMD/}mediaDataType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bitsPerSample" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
 *         &lt;element name="bitsPerPixelStored" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="codec" type="{http://www.loc.gov/videoMD/}codecType" minOccurs="0"/&gt;
 *         &lt;element name="compressionRatio" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="quality" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *               &lt;enumeration value="lossless"/&gt;
 *               &lt;enumeration value="lossy"/&gt;
 *               &lt;enumeration value="lossy_lossless"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="frame" type="{http://www.loc.gov/videoMD/}frameType" minOccurs="0"/&gt;
 *         &lt;element name="frameRate" type="{http://www.loc.gov/videoMD/}variableRateType" minOccurs="0"/&gt;
 *         &lt;element name="sampleRate" type="{http://www.loc.gov/videoMD/}variableRateType" minOccurs="0"/&gt;
 *         &lt;element name="sampling" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sampleCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
 *         &lt;element name="signalFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="num" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trackDataType", propOrder = {
    "bitsPerSample",
    "bitsPerPixelStored",
    "codec",
    "compressionRatio",
    "quality",
    "frame",
    "frameRate",
    "sampleRate",
    "sampling",
    "sampleCount",
    "signalFormat"
})
public class TrackDataType
    extends MediaDataType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger bitsPerSample;
    protected BigDecimal bitsPerPixelStored;
    protected CodecType codec;
    protected BigDecimal compressionRatio;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String quality;
    protected FrameType frame;
    protected VariableRateType frameRate;
    protected VariableRateType sampleRate;
    protected String sampling;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger sampleCount;
    protected String signalFormat;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "num")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger num;
    @XmlAttribute(name = "type")
    protected String type;

    /**
     * Recupera il valore della proprietà bitsPerSample.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBitsPerSample() {
        return bitsPerSample;
    }

    /**
     * Imposta il valore della proprietà bitsPerSample.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBitsPerSample(BigInteger value) {
        this.bitsPerSample = value;
    }

    /**
     * Recupera il valore della proprietà bitsPerPixelStored.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBitsPerPixelStored() {
        return bitsPerPixelStored;
    }

    /**
     * Imposta il valore della proprietà bitsPerPixelStored.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBitsPerPixelStored(BigDecimal value) {
        this.bitsPerPixelStored = value;
    }

    /**
     * Recupera il valore della proprietà codec.
     * 
     * @return
     *     possible object is
     *     {@link CodecType }
     *     
     */
    public CodecType getCodec() {
        return codec;
    }

    /**
     * Imposta il valore della proprietà codec.
     * 
     * @param value
     *     allowed object is
     *     {@link CodecType }
     *     
     */
    public void setCodec(CodecType value) {
        this.codec = value;
    }

    /**
     * Recupera il valore della proprietà compressionRatio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCompressionRatio() {
        return compressionRatio;
    }

    /**
     * Imposta il valore della proprietà compressionRatio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCompressionRatio(BigDecimal value) {
        this.compressionRatio = value;
    }

    /**
     * Recupera il valore della proprietà quality.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuality() {
        return quality;
    }

    /**
     * Imposta il valore della proprietà quality.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuality(String value) {
        this.quality = value;
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
     * Recupera il valore della proprietà sampling.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampling() {
        return sampling;
    }

    /**
     * Imposta il valore della proprietà sampling.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampling(String value) {
        this.sampling = value;
    }

    /**
     * Recupera il valore della proprietà sampleCount.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSampleCount() {
        return sampleCount;
    }

    /**
     * Imposta il valore della proprietà sampleCount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSampleCount(BigInteger value) {
        this.sampleCount = value;
    }

    /**
     * Recupera il valore della proprietà signalFormat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignalFormat() {
        return signalFormat;
    }

    /**
     * Imposta il valore della proprietà signalFormat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignalFormat(String value) {
        this.signalFormat = value;
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

    /**
     * Recupera il valore della proprietà num.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNum() {
        return num;
    }

    /**
     * Imposta il valore della proprietà num.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNum(BigInteger value) {
        this.num = value;
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

}
