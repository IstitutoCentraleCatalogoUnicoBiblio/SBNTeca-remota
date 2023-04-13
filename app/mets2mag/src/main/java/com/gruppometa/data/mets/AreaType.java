//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * areaType: Complex Type for Area Linking
 * 				The area element provides for more sophisticated linking between a div element and content files representing that div, be they text, image, audio, or video files.  An area element can link a div to a point within a file, to a one-dimension segment of a file (e.g., text segment, image line, audio/video clip), or a two-dimensional section of a file 	(e.g, subsection of an image, or a subsection of the  video display of a video file.  The area element has no content; all information is recorded within its various attributes.
 * 			
 * 
 * <p>Classe Java per areaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="areaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attGroup ref="{http://www.loc.gov/METS/}ORDERLABELS"/&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="FILEID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *       &lt;attribute name="SHAPE"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="RECT"/&gt;
 *             &lt;enumeration value="CIRCLE"/&gt;
 *             &lt;enumeration value="POLY"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="COORDS" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="BEGIN" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="END" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="BETYPE"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="BYTE"/&gt;
 *             &lt;enumeration value="IDREF"/&gt;
 *             &lt;enumeration value="SMIL"/&gt;
 *             &lt;enumeration value="MIDI"/&gt;
 *             &lt;enumeration value="SMPTE-25"/&gt;
 *             &lt;enumeration value="SMPTE-24"/&gt;
 *             &lt;enumeration value="SMPTE-DF30"/&gt;
 *             &lt;enumeration value="SMPTE-NDF30"/&gt;
 *             &lt;enumeration value="SMPTE-DF29.97"/&gt;
 *             &lt;enumeration value="SMPTE-NDF29.97"/&gt;
 *             &lt;enumeration value="TIME"/&gt;
 *             &lt;enumeration value="TCF"/&gt;
 *             &lt;enumeration value="XPTR"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="EXTENT" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="EXTTYPE"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="BYTE"/&gt;
 *             &lt;enumeration value="SMIL"/&gt;
 *             &lt;enumeration value="MIDI"/&gt;
 *             &lt;enumeration value="SMPTE-25"/&gt;
 *             &lt;enumeration value="SMPTE-24"/&gt;
 *             &lt;enumeration value="SMPTE-DF30"/&gt;
 *             &lt;enumeration value="SMPTE-NDF30"/&gt;
 *             &lt;enumeration value="SMPTE-DF29.97"/&gt;
 *             &lt;enumeration value="SMPTE-NDF29.97"/&gt;
 *             &lt;enumeration value="TIME"/&gt;
 *             &lt;enumeration value="TCF"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="ADMID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" /&gt;
 *       &lt;attribute name="CONTENTIDS" type="{http://www.loc.gov/METS/}URIs" /&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "areaType")
public class AreaType implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "FILEID", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object fileid;
    @XmlAttribute(name = "SHAPE")
    protected String shape;
    @XmlAttribute(name = "COORDS")
    protected String coords;
    @XmlAttribute(name = "BEGIN")
    protected String begin;
    @XmlAttribute(name = "END")
    protected String end;
    @XmlAttribute(name = "BETYPE")
    protected String betype;
    @XmlAttribute(name = "EXTENT")
    protected String extent;
    @XmlAttribute(name = "EXTTYPE")
    protected String exttype;
    @XmlAttribute(name = "ADMID")
    @XmlIDREF
    @XmlSchemaType(name = "IDREFS")
    protected List<Object> admids;
    @XmlAttribute(name = "CONTENTIDS")
    protected List<String> contentids;
    @XmlAttribute(name = "ORDER")
    protected BigInteger order;
    @XmlAttribute(name = "ORDERLABEL")
    protected String orderlabel;
    @XmlAttribute(name = "LABEL")
    protected String labelOrderLabel;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

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
     * Recupera il valore della proprietà fileid.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getFILEID() {
        return fileid;
    }

    /**
     * Imposta il valore della proprietà fileid.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setFILEID(Object value) {
        this.fileid = value;
    }

    /**
     * Recupera il valore della proprietà shape.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSHAPE() {
        return shape;
    }

    /**
     * Imposta il valore della proprietà shape.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSHAPE(String value) {
        this.shape = value;
    }

    /**
     * Recupera il valore della proprietà coords.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOORDS() {
        return coords;
    }

    /**
     * Imposta il valore della proprietà coords.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOORDS(String value) {
        this.coords = value;
    }

    /**
     * Recupera il valore della proprietà begin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBEGIN() {
        return begin;
    }

    /**
     * Imposta il valore della proprietà begin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBEGIN(String value) {
        this.begin = value;
    }

    /**
     * Recupera il valore della proprietà end.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEND() {
        return end;
    }

    /**
     * Imposta il valore della proprietà end.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEND(String value) {
        this.end = value;
    }

    /**
     * Recupera il valore della proprietà betype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBETYPE() {
        return betype;
    }

    /**
     * Imposta il valore della proprietà betype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBETYPE(String value) {
        this.betype = value;
    }

    /**
     * Recupera il valore della proprietà extent.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXTENT() {
        return extent;
    }

    /**
     * Imposta il valore della proprietà extent.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXTENT(String value) {
        this.extent = value;
    }

    /**
     * Recupera il valore della proprietà exttype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXTTYPE() {
        return exttype;
    }

    /**
     * Imposta il valore della proprietà exttype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXTTYPE(String value) {
        this.exttype = value;
    }

    /**
     * Gets the value of the admids property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the admids property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getADMIDS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getADMIDS() {
        if (admids == null) {
            admids = new ArrayList<Object>();
        }
        return this.admids;
    }

    /**
     * Gets the value of the contentids property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contentids property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCONTENTIDS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCONTENTIDS() {
        if (contentids == null) {
            contentids = new ArrayList<String>();
        }
        return this.contentids;
    }

    /**
     * Recupera il valore della proprietà order.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getORDER() {
        return order;
    }

    /**
     * Imposta il valore della proprietà order.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setORDER(BigInteger value) {
        this.order = value;
    }

    /**
     * Recupera il valore della proprietà orderlabel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDERLABEL() {
        return orderlabel;
    }

    /**
     * Imposta il valore della proprietà orderlabel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDERLABEL(String value) {
        this.orderlabel = value;
    }

    /**
     * Recupera il valore della proprietà labelOrderLabel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelOrderLabel() {
        return labelOrderLabel;
    }

    /**
     * Imposta il valore della proprietà labelOrderLabel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelOrderLabel(String value) {
        this.labelOrderLabel = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
