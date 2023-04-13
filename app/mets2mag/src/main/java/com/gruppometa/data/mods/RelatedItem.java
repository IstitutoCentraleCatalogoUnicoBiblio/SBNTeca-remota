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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per relatedItemDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="relatedItemDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;group ref="{http://www.loc.gov/mods/v3}modsGroup" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;attGroup ref="{http://www.w3.org/1999/xlink}simpleLink"/&gt;
 *       &lt;attribute name="type"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="preceding"/&gt;
 *             &lt;enumeration value="succeeding"/&gt;
 *             &lt;enumeration value="original"/&gt;
 *             &lt;enumeration value="host"/&gt;
 *             &lt;enumeration value="constituent"/&gt;
 *             &lt;enumeration value="series"/&gt;
 *             &lt;enumeration value="otherVersion"/&gt;
 *             &lt;enumeration value="otherFormat"/&gt;
 *             &lt;enumeration value="isReferencedBy"/&gt;
 *             &lt;enumeration value="references"/&gt;
 *             &lt;enumeration value="reviewOf"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="otherType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="otherTypeAuth" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="otherTypeAuthURI" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="otherTypeURI" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="displayLabel" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "relatedItemDefinition", propOrder = {
    "abstractsAndAccessConditionsAndClassifications"
})
@XmlRootElement(name = "relatedItem")
public class RelatedItem implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElements({
        @XmlElement(name = "abstract", type = Abstract.class),
        @XmlElement(name = "accessCondition", type = AccessCondition.class),
        @XmlElement(name = "classification", type = Classification.class),
        @XmlElement(name = "extension", type = ExtensionDefinition.class),
        @XmlElement(name = "genre", type = Genre.class),
        @XmlElement(name = "identifier", type = IdentifierDefinition.class),
        @XmlElement(name = "language", type = LanguageDefinition.class),
        @XmlElement(name = "location", type = Location.class),
        @XmlElement(name = "name", type = Name.class),
        @XmlElement(name = "note", type = NoteDefinition.class),
        @XmlElement(name = "originInfo", type = OriginInfo.class),
        @XmlElement(name = "part", type = Part.class),
        @XmlElement(name = "physicalDescription", type = PhysicalDescription.class),
        @XmlElement(name = "recordInfo", type = RecordInfo.class),
        @XmlElement(name = "relatedItem", type = RelatedItem.class),
        @XmlElement(name = "subject", type = Subject.class),
        @XmlElement(name = "tableOfContents", type = TableOfContents.class),
        @XmlElement(name = "targetAudience", type = TargetAudience.class),
        @XmlElement(name = "titleInfo", type = TitleInfo.class),
        @XmlElement(name = "typeOfResource", type = TypeOfResource.class)
    })
    protected List<Serializable> abstractsAndAccessConditionsAndClassifications;
    @XmlAttribute(name = "type")
    protected String typeRel;
    @XmlAttribute(name = "otherType")
    protected String otherType;
    @XmlAttribute(name = "otherTypeAuth")
    protected String otherTypeAuth;
    @XmlAttribute(name = "otherTypeAuthURI")
    protected String otherTypeAuthURI;
    @XmlAttribute(name = "otherTypeURI")
    protected String otherTypeURI;
    @XmlAttribute(name = "displayLabel")
    protected String displayLabel;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "type", namespace = "http://www.w3.org/1999/xlink")
    protected String type;
    @XmlAttribute(name = "href", namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anyURI")
    protected String href;
    @XmlAttribute(name = "role", namespace = "http://www.w3.org/1999/xlink")
    protected String role;
    @XmlAttribute(name = "arcrole", namespace = "http://www.w3.org/1999/xlink")
    protected String arcrole;
    @XmlAttribute(name = "title", namespace = "http://www.w3.org/1999/xlink")
    protected String title;
    @XmlAttribute(name = "show", namespace = "http://www.w3.org/1999/xlink")
    protected String show;
    @XmlAttribute(name = "actuate", namespace = "http://www.w3.org/1999/xlink")
    protected String actuate;

    /**
     * Gets the value of the abstractsAndAccessConditionsAndClassifications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abstractsAndAccessConditionsAndClassifications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbstractsAndAccessConditionsAndClassifications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Abstract }
     * {@link AccessCondition }
     * {@link Classification }
     * {@link ExtensionDefinition }
     * {@link Genre }
     * {@link IdentifierDefinition }
     * {@link LanguageDefinition }
     * {@link Location }
     * {@link Name }
     * {@link NoteDefinition }
     * {@link OriginInfo }
     * {@link Part }
     * {@link PhysicalDescription }
     * {@link RecordInfo }
     * {@link RelatedItem }
     * {@link Subject }
     * {@link TableOfContents }
     * {@link TargetAudience }
     * {@link TitleInfo }
     * {@link TypeOfResource }
     * 
     * 
     */
    public List<Serializable> getAbstractsAndAccessConditionsAndClassifications() {
        if (abstractsAndAccessConditionsAndClassifications == null) {
            abstractsAndAccessConditionsAndClassifications = new ArrayList<Serializable>();
        }
        return this.abstractsAndAccessConditionsAndClassifications;
    }

    /**
     * Recupera il valore della proprietà typeRel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeRel() {
        return typeRel;
    }

    /**
     * Imposta il valore della proprietà typeRel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeRel(String value) {
        this.typeRel = value;
    }

    /**
     * Recupera il valore della proprietà otherType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherType() {
        return otherType;
    }

    /**
     * Imposta il valore della proprietà otherType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherType(String value) {
        this.otherType = value;
    }

    /**
     * Recupera il valore della proprietà otherTypeAuth.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherTypeAuth() {
        return otherTypeAuth;
    }

    /**
     * Imposta il valore della proprietà otherTypeAuth.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherTypeAuth(String value) {
        this.otherTypeAuth = value;
    }

    /**
     * Recupera il valore della proprietà otherTypeAuthURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherTypeAuthURI() {
        return otherTypeAuthURI;
    }

    /**
     * Imposta il valore della proprietà otherTypeAuthURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherTypeAuthURI(String value) {
        this.otherTypeAuthURI = value;
    }

    /**
     * Recupera il valore della proprietà otherTypeURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherTypeURI() {
        return otherTypeURI;
    }

    /**
     * Imposta il valore della proprietà otherTypeURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherTypeURI(String value) {
        this.otherTypeURI = value;
    }

    /**
     * Recupera il valore della proprietà displayLabel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayLabel() {
        return displayLabel;
    }

    /**
     * Imposta il valore della proprietà displayLabel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayLabel(String value) {
        this.displayLabel = value;
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
     * Recupera il valore della proprietà type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        if (type == null) {
            return "simple";
        } else {
            return type;
        }
    }

    /**
     * Imposta il valore della proprietà type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Recupera il valore della proprietà href.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHref() {
        return href;
    }

    /**
     * Imposta il valore della proprietà href.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHref(String value) {
        this.href = value;
    }

    /**
     * Recupera il valore della proprietà role.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Imposta il valore della proprietà role.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Recupera il valore della proprietà arcrole.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArcrole() {
        return arcrole;
    }

    /**
     * Imposta il valore della proprietà arcrole.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArcrole(String value) {
        this.arcrole = value;
    }

    /**
     * Recupera il valore della proprietà title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Imposta il valore della proprietà title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Recupera il valore della proprietà show.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShow() {
        return show;
    }

    /**
     * Imposta il valore della proprietà show.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShow(String value) {
        this.show = value;
    }

    /**
     * Recupera il valore della proprietà actuate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActuate() {
        return actuate;
    }

    /**
     * Imposta il valore della proprietà actuate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActuate(String value) {
        this.actuate = value;
    }

}
