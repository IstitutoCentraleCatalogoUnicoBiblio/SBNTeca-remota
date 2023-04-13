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
import javax.xml.bind.annotation.XmlType;


/**
 * dimensionsType: Complex Type for recording the dimensions of an object. 
 * 				dimensionsType has the following attributes: 
 * 				1. DEPTH:  The depth of the object in the unit of measure indicated in dimensions_unit, e.g., 7, 12.; 
 * 				2. DIAMETER: The diameter of any circular object expressed in the unit of measure indicated in dimensions_unit, e.g., 3.5, 5, 7.; 
 * 				3. GAUGE: Gauge or width of source tape, including indication of unit of measure, e.g., 8 mm, 0.5 inch, 0.25 inch, etc..; 
 * 				4. HEIGHT: Height of the object in the unit of measure indicated in dimensions_unit, e.g., 23.; 
 * 				5. LENGTH: Length of source open-reel tape recording, including indication of unit of measure, e.g., 700 feet, 1200 feet, etc..; 
 * 				6. NOTE: Desciption of odd-shaped objects that cannot be described using the standard dimensions fields.; 
 * 				7. THICKNESS: The thickness of the medium unwound (e.g. tape); 
 * 				8. UNITS: Unit of measurement of the source object, e.g., inches.; 
 * 				9. WIDTH: Width of any non-circular object expressed in the unit of measure indicated in dimensions_unit, e.g., 3.5, 5, 7.
 * 
 * <p>Classe Java per dimensionsType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="dimensionsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="DEPTH" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *       &lt;attribute name="DIAMETER" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *       &lt;attribute name="GAUGE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="HEIGHT" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *       &lt;attribute name="LENGTH" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="NOTE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="THICKNESS" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="UNITS" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="WIDTH" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dimensionsType")
public class DimensionsType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "DEPTH")
    protected Float depth;
    @XmlAttribute(name = "DIAMETER")
    protected Float diameter;
    @XmlAttribute(name = "GAUGE")
    protected String gauge;
    @XmlAttribute(name = "HEIGHT")
    protected Float height;
    @XmlAttribute(name = "LENGTH")
    protected String length;
    @XmlAttribute(name = "NOTE")
    protected String note;
    @XmlAttribute(name = "THICKNESS")
    protected String thickness;
    @XmlAttribute(name = "UNITS")
    protected String units;
    @XmlAttribute(name = "WIDTH")
    protected Float width;

    /**
     * Recupera il valore della proprietà depth.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDEPTH() {
        return depth;
    }

    /**
     * Imposta il valore della proprietà depth.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDEPTH(Float value) {
        this.depth = value;
    }

    /**
     * Recupera il valore della proprietà diameter.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDIAMETER() {
        return diameter;
    }

    /**
     * Imposta il valore della proprietà diameter.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDIAMETER(Float value) {
        this.diameter = value;
    }

    /**
     * Recupera il valore della proprietà gauge.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGAUGE() {
        return gauge;
    }

    /**
     * Imposta il valore della proprietà gauge.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGAUGE(String value) {
        this.gauge = value;
    }

    /**
     * Recupera il valore della proprietà height.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getHEIGHT() {
        return height;
    }

    /**
     * Imposta il valore della proprietà height.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setHEIGHT(Float value) {
        this.height = value;
    }

    /**
     * Recupera il valore della proprietà length.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLENGTH() {
        return length;
    }

    /**
     * Imposta il valore della proprietà length.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLENGTH(String value) {
        this.length = value;
    }

    /**
     * Recupera il valore della proprietà note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOTE() {
        return note;
    }

    /**
     * Imposta il valore della proprietà note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOTE(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della proprietà thickness.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTHICKNESS() {
        return thickness;
    }

    /**
     * Imposta il valore della proprietà thickness.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTHICKNESS(String value) {
        this.thickness = value;
    }

    /**
     * Recupera il valore della proprietà units.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITS() {
        return units;
    }

    /**
     * Imposta il valore della proprietà units.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITS(String value) {
        this.units = value;
    }

    /**
     * Recupera il valore della proprietà width.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getWIDTH() {
        return width;
    }

    /**
     * Imposta il valore della proprietà width.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setWIDTH(Float value) {
        this.width = value;
    }

}
