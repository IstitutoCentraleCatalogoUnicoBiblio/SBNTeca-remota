//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.audiomd;

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
 * audioType: complexType for encapsulating and organizing within a
 * 				singleparent element the individual audio metadata elements that describe an object
 * 				or portion of an object. The four individual elements that comprise an audio object
 * 				are documented under their corresponding types.
 * 
 * <p>Classe Java per audioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="audioType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fileData" type="{http://www.loc.gov/audioMD/}fileDataType" minOccurs="0"/&gt;
 *         &lt;element name="physicalData" type="{http://www.loc.gov/audioMD/}physicalDataType" minOccurs="0"/&gt;
 *         &lt;element name="audioInfo" type="{http://www.loc.gov/audioMD/}audioInfoType" minOccurs="0"/&gt;
 *         &lt;element name="calibrationInfo" type="{http://www.loc.gov/audioMD/}calibrationInfoType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="ANALOGDIGITALFLAG" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *             &lt;enumeration value="Analog"/&gt;
 *             &lt;enumeration value="PhysDigital"/&gt;
 *             &lt;enumeration value="FileDigital"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "audioType", propOrder = {
    "fileData",
    "physicalData",
    "audioInfo",
    "calibrationInfo"
})
public class AudioType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected FileDataType fileData;
    protected PhysicalDataType physicalData;
    protected AudioInfoType audioInfo;
    protected CalibrationInfoType calibrationInfo;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "ANALOGDIGITALFLAG", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String analogdigitalflag;

    /**
     * Recupera il valore della proprietà fileData.
     * 
     * @return
     *     possible object is
     *     {@link FileDataType }
     *     
     */
    public FileDataType getFileData() {
        return fileData;
    }

    /**
     * Imposta il valore della proprietà fileData.
     * 
     * @param value
     *     allowed object is
     *     {@link FileDataType }
     *     
     */
    public void setFileData(FileDataType value) {
        this.fileData = value;
    }

    /**
     * Recupera il valore della proprietà physicalData.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalDataType }
     *     
     */
    public PhysicalDataType getPhysicalData() {
        return physicalData;
    }

    /**
     * Imposta il valore della proprietà physicalData.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalDataType }
     *     
     */
    public void setPhysicalData(PhysicalDataType value) {
        this.physicalData = value;
    }

    /**
     * Recupera il valore della proprietà audioInfo.
     * 
     * @return
     *     possible object is
     *     {@link AudioInfoType }
     *     
     */
    public AudioInfoType getAudioInfo() {
        return audioInfo;
    }

    /**
     * Imposta il valore della proprietà audioInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link AudioInfoType }
     *     
     */
    public void setAudioInfo(AudioInfoType value) {
        this.audioInfo = value;
    }

    /**
     * Recupera il valore della proprietà calibrationInfo.
     * 
     * @return
     *     possible object is
     *     {@link CalibrationInfoType }
     *     
     */
    public CalibrationInfoType getCalibrationInfo() {
        return calibrationInfo;
    }

    /**
     * Imposta il valore della proprietà calibrationInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link CalibrationInfoType }
     *     
     */
    public void setCalibrationInfo(CalibrationInfoType value) {
        this.calibrationInfo = value;
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
     * Recupera il valore della proprietà analogdigitalflag.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANALOGDIGITALFLAG() {
        return analogdigitalflag;
    }

    /**
     * Imposta il valore della proprietà analogdigitalflag.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANALOGDIGITALFLAG(String value) {
        this.analogdigitalflag = value;
    }

}
