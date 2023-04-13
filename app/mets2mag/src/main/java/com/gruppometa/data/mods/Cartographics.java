//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per cartographicsDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="cartographicsDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}scale" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}projection" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}coordinates" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}cartographicExtension" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.loc.gov/mods/v3}authorityAttributeGroup"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cartographicsDefinition", propOrder = {
    "scale",
    "projection",
    "coordinates",
    "cartographicExtensions"
})
@XmlRootElement(name = "cartographics")
public class Cartographics implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected StringPlusLanguage scale;
    protected StringPlusLanguage projection;
    protected List<StringPlusLanguage> coordinates;
    @XmlElement(name = "cartographicExtension")
    protected List<ExtensionDefinition> cartographicExtensions;
    @XmlAttribute(name = "authority")
    protected String authority;
    @XmlAttribute(name = "authorityURI")
    @XmlSchemaType(name = "anyURI")
    protected String authorityURI;
    @XmlAttribute(name = "valueURI")
    @XmlSchemaType(name = "anyURI")
    protected String valueURI;

    /**
     * Recupera il valore della proprietà scale.
     * 
     * @return
     *     possible object is
     *     {@link StringPlusLanguage }
     *     
     */
    public StringPlusLanguage getScale() {
        return scale;
    }

    /**
     * Imposta il valore della proprietà scale.
     * 
     * @param value
     *     allowed object is
     *     {@link StringPlusLanguage }
     *     
     */
    public void setScale(StringPlusLanguage value) {
        this.scale = value;
    }

    /**
     * Recupera il valore della proprietà projection.
     * 
     * @return
     *     possible object is
     *     {@link StringPlusLanguage }
     *     
     */
    public StringPlusLanguage getProjection() {
        return projection;
    }

    /**
     * Imposta il valore della proprietà projection.
     * 
     * @param value
     *     allowed object is
     *     {@link StringPlusLanguage }
     *     
     */
    public void setProjection(StringPlusLanguage value) {
        this.projection = value;
    }

    /**
     * Gets the value of the coordinates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coordinates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoordinates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringPlusLanguage }
     * 
     * 
     */
    public List<StringPlusLanguage> getCoordinates() {
        if (coordinates == null) {
            coordinates = new ArrayList<StringPlusLanguage>();
        }
        return this.coordinates;
    }

    /**
     * Gets the value of the cartographicExtensions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cartographicExtensions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCartographicExtensions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionDefinition }
     * 
     * 
     */
    public List<ExtensionDefinition> getCartographicExtensions() {
        if (cartographicExtensions == null) {
            cartographicExtensions = new ArrayList<ExtensionDefinition>();
        }
        return this.cartographicExtensions;
    }

    /**
     * Recupera il valore della proprietà authority.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * Imposta il valore della proprietà authority.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthority(String value) {
        this.authority = value;
    }

    /**
     * Recupera il valore della proprietà authorityURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorityURI() {
        return authorityURI;
    }

    /**
     * Imposta il valore della proprietà authorityURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorityURI(String value) {
        this.authorityURI = value;
    }

    /**
     * Recupera il valore della proprietà valueURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueURI() {
        return valueURI;
    }

    /**
     * Imposta il valore della proprietà valueURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueURI(String value) {
        this.valueURI = value;
    }

}
