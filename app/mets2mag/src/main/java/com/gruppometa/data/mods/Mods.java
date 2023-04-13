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
 * <p>Classe Java per modsDefinition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="modsDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;group ref="{http://www.loc.gov/mods/v3}modsGroup" maxOccurs="unbounded"/&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="version"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="3.7"/&gt;
 *             &lt;enumeration value="3.6"/&gt;
 *             &lt;enumeration value="3.5"/&gt;
 *             &lt;enumeration value="3.4"/&gt;
 *             &lt;enumeration value="3.3"/&gt;
 *             &lt;enumeration value="3.2"/&gt;
 *             &lt;enumeration value="3.1"/&gt;
 *             &lt;enumeration value="3.0"/&gt;
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
@XmlType(name = "modsDefinition", propOrder = {
    "abstractsAndAccessConditionsAndClassifications"
})
@XmlRootElement(name = "mods")
public class Mods
    implements Serializable
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
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "version")
    protected String version;

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
     * Recupera il valore della proprietà version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Imposta il valore della proprietà version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
