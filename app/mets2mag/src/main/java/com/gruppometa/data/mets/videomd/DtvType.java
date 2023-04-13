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


/**
 * dtvType: complexType for recording Information about a high definition
 * 				TV video source item. dtvType has 1 attribute ID (XML ID) and 4 elements dtvAspectRatio, dtvNote, dtvResolution and dtvScan.
 * 
 * <p>Classe Java per dtvType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="dtvType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dtvAspectRatio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dtvNote" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dtvResolution" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dtvScan" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "dtvType", propOrder = {
    "dtvAspectRatio",
    "dtvNote",
    "dtvResolution",
    "dtvScan"
})
public class DtvType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(required = true)
    protected String dtvAspectRatio;
    @XmlElement(required = true)
    protected String dtvNote;
    @XmlElement(required = true)
    protected String dtvResolution;
    @XmlElement(required = true)
    protected String dtvScan;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà dtvAspectRatio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtvAspectRatio() {
        return dtvAspectRatio;
    }

    /**
     * Imposta il valore della proprietà dtvAspectRatio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtvAspectRatio(String value) {
        this.dtvAspectRatio = value;
    }

    /**
     * Recupera il valore della proprietà dtvNote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtvNote() {
        return dtvNote;
    }

    /**
     * Imposta il valore della proprietà dtvNote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtvNote(String value) {
        this.dtvNote = value;
    }

    /**
     * Recupera il valore della proprietà dtvResolution.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtvResolution() {
        return dtvResolution;
    }

    /**
     * Imposta il valore della proprietà dtvResolution.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtvResolution(String value) {
        this.dtvResolution = value;
    }

    /**
     * Recupera il valore della proprietà dtvScan.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtvScan() {
        return dtvScan;
    }

    /**
     * Imposta il valore della proprietà dtvScan.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtvScan(String value) {
        this.dtvScan = value;
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
