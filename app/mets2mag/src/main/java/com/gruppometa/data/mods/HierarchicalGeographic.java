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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per hierarchicalGeographicDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="hierarchicalGeographicDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}extraTerrestrialArea"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}continent"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}country"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}province"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}region"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}state"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}territory"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}county"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}city"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}citySection"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}island"/&gt;
 *         &lt;element ref="{http://www.loc.gov/mods/v3}area"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attGroup ref="{http://www.loc.gov/mods/v3}authorityAttributeGroup"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hierarchicalGeographicDefinition", propOrder = {
    "extraTerrestrialAreasAndContinentsAndCountries"
})
@XmlRootElement(name = "hierarchicalGeographic")
public class HierarchicalGeographic implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElementRefs({
        @XmlElementRef(name = "extraTerrestrialArea", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "continent", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "country", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "province", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "region", namespace = "http://www.loc.gov/mods/v3", type = Region.class, required = false),
        @XmlElementRef(name = "state", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "territory", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "county", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "city", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "citySection", namespace = "http://www.loc.gov/mods/v3", type = CitySection.class, required = false),
        @XmlElementRef(name = "island", namespace = "http://www.loc.gov/mods/v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "area", namespace = "http://www.loc.gov/mods/v3", type = Area.class, required = false)
    })
    protected List<Serializable> extraTerrestrialAreasAndContinentsAndCountries;
    @XmlAttribute(name = "authority")
    protected String authority;
    @XmlAttribute(name = "authorityURI")
    @XmlSchemaType(name = "anyURI")
    protected String authorityURI;
    @XmlAttribute(name = "valueURI")
    @XmlSchemaType(name = "anyURI")
    protected String valueURI;

    /**
     * Gets the value of the extraTerrestrialAreasAndContinentsAndCountries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extraTerrestrialAreasAndContinentsAndCountries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtraTerrestrialAreasAndContinentsAndCountries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * {@link JAXBElement }{@code <}{@link StringPlusLanguage }{@code >}
     * {@link Region }
     * {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * {@link CitySection }
     * {@link JAXBElement }{@code <}{@link HierarchicalPart }{@code >}
     * {@link Area }
     * 
     * 
     */
    public List<Serializable> getExtraTerrestrialAreasAndContinentsAndCountries() {
        if (extraTerrestrialAreasAndContinentsAndCountries == null) {
            extraTerrestrialAreasAndContinentsAndCountries = new ArrayList<Serializable>();
        }
        return this.extraTerrestrialAreasAndContinentsAndCountries;
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
