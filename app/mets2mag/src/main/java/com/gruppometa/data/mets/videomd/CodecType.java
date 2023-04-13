//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.videomd;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per codecType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="codecType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.loc.gov/videoMD/}formatType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codecID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="channelCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
 *         &lt;element name="endianness" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="scanType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="scanOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codecType", propOrder = {
    "codecID",
    "channelCount",
    "endianness",
    "scanType",
    "scanOrder",
    "sign"
})
public class CodecType
    extends FormatType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected String codecID;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger channelCount;
    protected String endianness;
    protected String scanType;
    protected String scanOrder;
    protected String sign;

    /**
     * Recupera il valore della proprietà codecID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodecID() {
        return codecID;
    }

    /**
     * Imposta il valore della proprietà codecID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodecID(String value) {
        this.codecID = value;
    }

    /**
     * Recupera il valore della proprietà channelCount.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getChannelCount() {
        return channelCount;
    }

    /**
     * Imposta il valore della proprietà channelCount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setChannelCount(BigInteger value) {
        this.channelCount = value;
    }

    /**
     * Recupera il valore della proprietà endianness.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndianness() {
        return endianness;
    }

    /**
     * Imposta il valore della proprietà endianness.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndianness(String value) {
        this.endianness = value;
    }

    /**
     * Recupera il valore della proprietà scanType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScanType() {
        return scanType;
    }

    /**
     * Imposta il valore della proprietà scanType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScanType(String value) {
        this.scanType = value;
    }

    /**
     * Recupera il valore della proprietà scanOrder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScanOrder() {
        return scanOrder;
    }

    /**
     * Imposta il valore della proprietà scanOrder.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScanOrder(String value) {
        this.scanOrder = value;
    }

    /**
     * Recupera il valore della proprietà sign.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSign() {
        return sign;
    }

    /**
     * Imposta il valore della proprietà sign.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSign(String value) {
        this.sign = value;
    }

}
