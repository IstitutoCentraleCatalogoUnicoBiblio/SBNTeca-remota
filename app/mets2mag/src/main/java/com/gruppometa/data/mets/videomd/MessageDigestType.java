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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * messageDigestType: Complex Type for recording the type of
 * 				messageDigest used for a digital video file. messageDigestType has 1 attribute ID (XML ID) and 3
 * 				elements messageDigestDatetime, messageDigestAlgorithm and messageDigest.
 * 
 * <p>Classe Java per messageDigestType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="messageDigestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="messageDigestDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="messageDigestAlgorithm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="messageDigest" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "messageDigestType", propOrder = {
    "messageDigestDatetime",
    "messageDigestAlgorithm",
    "messageDigest"
})
public class MessageDigestType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar messageDigestDatetime;
    @XmlElement(required = true)
    protected String messageDigestAlgorithm;
    @XmlElement(required = true)
    protected String messageDigest;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà messageDigestDatetime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMessageDigestDatetime() {
        return messageDigestDatetime;
    }

    /**
     * Imposta il valore della proprietà messageDigestDatetime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMessageDigestDatetime(XMLGregorianCalendar value) {
        this.messageDigestDatetime = value;
    }

    /**
     * Recupera il valore della proprietà messageDigestAlgorithm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageDigestAlgorithm() {
        return messageDigestAlgorithm;
    }

    /**
     * Imposta il valore della proprietà messageDigestAlgorithm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageDigestAlgorithm(String value) {
        this.messageDigestAlgorithm = value;
    }

    /**
     * Recupera il valore della proprietà messageDigest.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageDigest() {
        return messageDigest;
    }

    /**
     * Imposta il valore della proprietà messageDigest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageDigest(String value) {
        this.messageDigest = value;
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
