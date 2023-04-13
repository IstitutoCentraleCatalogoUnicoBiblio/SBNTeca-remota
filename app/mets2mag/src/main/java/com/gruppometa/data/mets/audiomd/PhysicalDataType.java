//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.audiomd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * The physicalData element describes the physical nature
 * 				of an audio object if it exists as such (i.e. it is not a file). This will generally
 * 				apply to analog items but may also be used to describe digital formats such as open
 * 				reel digital, DAT, ADAT tape, CD-R, etc....
 * 
 * <p>Classe Java per physicalDataType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="physicalDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EBUStorageMediaCodes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dimensions" type="{http://www.loc.gov/audioMD/}dimensionsType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="disposition" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="equalization" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="generation" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="groove" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="material" type="{http://www.loc.gov/audioMD/}materialType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="noiseReduction" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="physFormat" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="speed" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="speedAdjustment" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="speedNote" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="trackFormat" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="tracking" type="{http://www.loc.gov/audioMD/}trackingInfoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "physicalDataType", propOrder = {
    "ebuStorageMediaCodes",
    "conditions",
    "dimensions",
    "dispositions",
    "equalizations",
    "generations",
    "grooves",
    "materials",
    "noiseReductions",
    "physFormats",
    "speeds",
    "speedAdjustments",
    "speedNotes",
    "trackFormats",
    "trackings",
    "notes"
})
public class PhysicalDataType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "EBUStorageMediaCodes")
    protected List<String> ebuStorageMediaCodes;
    @XmlElement(name = "condition")
    protected List<String> conditions;
    protected List<DimensionsType> dimensions;
    @XmlElement(name = "disposition")
    protected List<String> dispositions;
    @XmlElement(name = "equalization")
    protected List<String> equalizations;
    @XmlElement(name = "generation")
    protected List<String> generations;
    @XmlElement(name = "groove")
    protected List<String> grooves;
    @XmlElement(name = "material")
    protected List<MaterialType> materials;
    @XmlElement(name = "noiseReduction")
    protected List<String> noiseReductions;
    @XmlElement(name = "physFormat")
    protected List<String> physFormats;
    @XmlElement(name = "speed")
    protected List<String> speeds;
    @XmlElement(name = "speedAdjustment")
    protected List<String> speedAdjustments;
    @XmlElement(name = "speedNote")
    protected List<String> speedNotes;
    @XmlElement(name = "trackFormat")
    protected List<String> trackFormats;
    @XmlElement(name = "tracking")
    protected List<TrackingInfoType> trackings;
    @XmlElement(name = "note")
    protected List<String> notes;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the ebuStorageMediaCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ebuStorageMediaCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEBUStorageMediaCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEBUStorageMediaCodes() {
        if (ebuStorageMediaCodes == null) {
            ebuStorageMediaCodes = new ArrayList<String>();
        }
        return this.ebuStorageMediaCodes;
    }

    /**
     * Gets the value of the conditions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conditions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConditions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getConditions() {
        if (conditions == null) {
            conditions = new ArrayList<String>();
        }
        return this.conditions;
    }

    /**
     * Gets the value of the dimensions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dimensions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDimensions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DimensionsType }
     * 
     * 
     */
    public List<DimensionsType> getDimensions() {
        if (dimensions == null) {
            dimensions = new ArrayList<DimensionsType>();
        }
        return this.dimensions;
    }

    /**
     * Gets the value of the dispositions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dispositions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDispositions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDispositions() {
        if (dispositions == null) {
            dispositions = new ArrayList<String>();
        }
        return this.dispositions;
    }

    /**
     * Gets the value of the equalizations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the equalizations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEqualizations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEqualizations() {
        if (equalizations == null) {
            equalizations = new ArrayList<String>();
        }
        return this.equalizations;
    }

    /**
     * Gets the value of the generations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenerations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getGenerations() {
        if (generations == null) {
            generations = new ArrayList<String>();
        }
        return this.generations;
    }

    /**
     * Gets the value of the grooves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grooves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrooves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getGrooves() {
        if (grooves == null) {
            grooves = new ArrayList<String>();
        }
        return this.grooves;
    }

    /**
     * Gets the value of the materials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the materials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaterials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaterialType }
     * 
     * 
     */
    public List<MaterialType> getMaterials() {
        if (materials == null) {
            materials = new ArrayList<MaterialType>();
        }
        return this.materials;
    }

    /**
     * Gets the value of the noiseReductions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the noiseReductions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNoiseReductions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNoiseReductions() {
        if (noiseReductions == null) {
            noiseReductions = new ArrayList<String>();
        }
        return this.noiseReductions;
    }

    /**
     * Gets the value of the physFormats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the physFormats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhysFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPhysFormats() {
        if (physFormats == null) {
            physFormats = new ArrayList<String>();
        }
        return this.physFormats;
    }

    /**
     * Gets the value of the speeds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the speeds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpeeds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSpeeds() {
        if (speeds == null) {
            speeds = new ArrayList<String>();
        }
        return this.speeds;
    }

    /**
     * Gets the value of the speedAdjustments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the speedAdjustments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpeedAdjustments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSpeedAdjustments() {
        if (speedAdjustments == null) {
            speedAdjustments = new ArrayList<String>();
        }
        return this.speedAdjustments;
    }

    /**
     * Gets the value of the speedNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the speedNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpeedNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSpeedNotes() {
        if (speedNotes == null) {
            speedNotes = new ArrayList<String>();
        }
        return this.speedNotes;
    }

    /**
     * Gets the value of the trackFormats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trackFormats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrackFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTrackFormats() {
        if (trackFormats == null) {
            trackFormats = new ArrayList<String>();
        }
        return this.trackFormats;
    }

    /**
     * Gets the value of the trackings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trackings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrackings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrackingInfoType }
     * 
     * 
     */
    public List<TrackingInfoType> getTrackings() {
        if (trackings == null) {
            trackings = new ArrayList<TrackingInfoType>();
        }
        return this.trackings;
    }

    /**
     * Gets the value of the notes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNotes() {
        if (notes == null) {
            notes = new ArrayList<String>();
        }
        return this.notes;
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
