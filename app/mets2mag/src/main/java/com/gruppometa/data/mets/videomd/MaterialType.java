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
 * materialType: Complex Type for recording the physical characteristics
 * 				of the construction and material of an video source item. materialType has 1
 * 				attribute ID (XML ID)and 9 elements baseMaterial, binder, discSurface, oxide, activeLayer, reflectiveLayer, stockBrand, methodand usedSides.
 * 
 * <p>Classe Java per materialType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="materialType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="baseMaterial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="binder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="discSurface" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oxide" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="activeLayer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="reflectiveLayer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="stockBrand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="method" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usedSides" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "materialType", propOrder = {
    "baseMaterial",
    "binder",
    "discSurface",
    "oxide",
    "activeLayer",
    "reflectiveLayer",
    "stockBrand",
    "method",
    "usedSides"
})
public class MaterialType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected String baseMaterial;
    protected String binder;
    protected String discSurface;
    protected String oxide;
    protected String activeLayer;
    protected String reflectiveLayer;
    protected String stockBrand;
    protected String method;
    protected String usedSides;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà baseMaterial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseMaterial() {
        return baseMaterial;
    }

    /**
     * Imposta il valore della proprietà baseMaterial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseMaterial(String value) {
        this.baseMaterial = value;
    }

    /**
     * Recupera il valore della proprietà binder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBinder() {
        return binder;
    }

    /**
     * Imposta il valore della proprietà binder.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBinder(String value) {
        this.binder = value;
    }

    /**
     * Recupera il valore della proprietà discSurface.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscSurface() {
        return discSurface;
    }

    /**
     * Imposta il valore della proprietà discSurface.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscSurface(String value) {
        this.discSurface = value;
    }

    /**
     * Recupera il valore della proprietà oxide.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOxide() {
        return oxide;
    }

    /**
     * Imposta il valore della proprietà oxide.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOxide(String value) {
        this.oxide = value;
    }

    /**
     * Recupera il valore della proprietà activeLayer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveLayer() {
        return activeLayer;
    }

    /**
     * Imposta il valore della proprietà activeLayer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveLayer(String value) {
        this.activeLayer = value;
    }

    /**
     * Recupera il valore della proprietà reflectiveLayer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReflectiveLayer() {
        return reflectiveLayer;
    }

    /**
     * Imposta il valore della proprietà reflectiveLayer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReflectiveLayer(String value) {
        this.reflectiveLayer = value;
    }

    /**
     * Recupera il valore della proprietà stockBrand.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStockBrand() {
        return stockBrand;
    }

    /**
     * Imposta il valore della proprietà stockBrand.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStockBrand(String value) {
        this.stockBrand = value;
    }

    /**
     * Recupera il valore della proprietà method.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethod() {
        return method;
    }

    /**
     * Imposta il valore della proprietà method.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethod(String value) {
        this.method = value;
    }

    /**
     * Recupera il valore della proprietà usedSides.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsedSides() {
        return usedSides;
    }

    /**
     * Imposta il valore della proprietà usedSides.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsedSides(String value) {
        this.usedSides = value;
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
