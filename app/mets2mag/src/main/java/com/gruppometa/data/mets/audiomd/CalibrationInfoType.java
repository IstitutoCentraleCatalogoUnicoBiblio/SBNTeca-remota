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
 * calibrationInfoType: complexType for storing the type of calibration
 * 				related characteristics of an audio object, regardless of physical or digital
 * 				format. calibrationInfoType has 1 attribute ID (XML ID) and 4 elements calibrationExtInt, calibrationLocation, calibrationTimeStamp and calibrationType.
 * 			
 * 
 * <p>Classe Java per calibrationInfoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="calibrationInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="calibrationExtInt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="calibrationLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="calibrationTimeStamp" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="calibrationTrackType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "calibrationInfoType", propOrder = {
    "calibrationExtInt",
    "calibrationLocation",
    "calibrationTimeStamps",
    "calibrationTrackType"
})
public class CalibrationInfoType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected String calibrationExtInt;
    protected String calibrationLocation;
    @XmlElement(name = "calibrationTimeStamp")
    protected List<String> calibrationTimeStamps;
    protected String calibrationTrackType;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà calibrationExtInt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalibrationExtInt() {
        return calibrationExtInt;
    }

    /**
     * Imposta il valore della proprietà calibrationExtInt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalibrationExtInt(String value) {
        this.calibrationExtInt = value;
    }

    /**
     * Recupera il valore della proprietà calibrationLocation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalibrationLocation() {
        return calibrationLocation;
    }

    /**
     * Imposta il valore della proprietà calibrationLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalibrationLocation(String value) {
        this.calibrationLocation = value;
    }

    /**
     * Gets the value of the calibrationTimeStamps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the calibrationTimeStamps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCalibrationTimeStamps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCalibrationTimeStamps() {
        if (calibrationTimeStamps == null) {
            calibrationTimeStamps = new ArrayList<String>();
        }
        return this.calibrationTimeStamps;
    }

    /**
     * Recupera il valore della proprietà calibrationTrackType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalibrationTrackType() {
        return calibrationTrackType;
    }

    /**
     * Imposta il valore della proprietà calibrationTrackType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalibrationTrackType(String value) {
        this.calibrationTrackType = value;
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
