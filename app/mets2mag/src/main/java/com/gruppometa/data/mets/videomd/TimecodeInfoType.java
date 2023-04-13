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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * timecodeInfoType: Complex Type for recording tracking information
 * 				about a video source item. timecodeInfoType has 1 attribute ID (XML ID) and 3 elements timecodeRecordMethod, timecodeType and timecodeInitialValue.
 * 
 * <p>Classe Java per timecodeInfoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="timecodeInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="timecodeRecordMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="timecodeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="timecodeInitialValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "timecodeInfoType", propOrder = {
    "timecodeRecordMethod",
    "timecodeType",
    "timecodeInitialValue"
})
public class TimecodeInfoType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected String timecodeRecordMethod;
    protected String timecodeType;
    protected String timecodeInitialValue;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà timecodeRecordMethod.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimecodeRecordMethod() {
        return timecodeRecordMethod;
    }

    /**
     * Imposta il valore della proprietà timecodeRecordMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimecodeRecordMethod(String value) {
        this.timecodeRecordMethod = value;
    }

    /**
     * Recupera il valore della proprietà timecodeType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimecodeType() {
        return timecodeType;
    }

    /**
     * Imposta il valore della proprietà timecodeType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimecodeType(String value) {
        this.timecodeType = value;
    }

    /**
     * Recupera il valore della proprietà timecodeInitialValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimecodeInitialValue() {
        return timecodeInitialValue;
    }

    /**
     * Imposta il valore della proprietà timecodeInitialValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimecodeInitialValue(String value) {
        this.timecodeInitialValue = value;
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
