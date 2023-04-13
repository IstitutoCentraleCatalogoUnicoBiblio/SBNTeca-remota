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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * frameType: Complex Type for recording the size of a frame in a digital
 * 				video file. frameType has 1 attribute ID (XML ID)and 6 elements pixelsHorizontal, pixelsVertical, frameRate, PAR, DAR and rotation.
 * 
 * <p>Classe Java per frameType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="frameType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pixelsHorizontal" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="pixelsVertical" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="frameRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="PAR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="DAR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rotation" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
@XmlType(name = "frameType", propOrder = {
    "pixelsHorizontal",
    "pixelsVertical",
    "frameRate",
    "par",
    "dar",
    "rotation"
})
public class FrameType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected BigInteger pixelsHorizontal;
    protected BigInteger pixelsVertical;
    protected BigDecimal frameRate;
    @XmlElement(name = "PAR")
    protected BigDecimal par;
    @XmlElement(name = "DAR")
    protected String dar;
    protected BigDecimal rotation;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà pixelsHorizontal.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPixelsHorizontal() {
        return pixelsHorizontal;
    }

    /**
     * Imposta il valore della proprietà pixelsHorizontal.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPixelsHorizontal(BigInteger value) {
        this.pixelsHorizontal = value;
    }

    /**
     * Recupera il valore della proprietà pixelsVertical.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPixelsVertical() {
        return pixelsVertical;
    }

    /**
     * Imposta il valore della proprietà pixelsVertical.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPixelsVertical(BigInteger value) {
        this.pixelsVertical = value;
    }

    /**
     * Recupera il valore della proprietà frameRate.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFrameRate() {
        return frameRate;
    }

    /**
     * Imposta il valore della proprietà frameRate.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFrameRate(BigDecimal value) {
        this.frameRate = value;
    }

    /**
     * Recupera il valore della proprietà par.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPAR() {
        return par;
    }

    /**
     * Imposta il valore della proprietà par.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPAR(BigDecimal value) {
        this.par = value;
    }

    /**
     * Recupera il valore della proprietà dar.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDAR() {
        return dar;
    }

    /**
     * Imposta il valore della proprietà dar.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDAR(String value) {
        this.dar = value;
    }

    /**
     * Recupera il valore della proprietà rotation.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRotation() {
        return rotation;
    }

    /**
     * Imposta il valore della proprietà rotation.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRotation(BigDecimal value) {
        this.rotation = value;
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
