//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.videomd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
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
 *         &lt;element name="colorBurst" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dimensions" type="{http://www.loc.gov/videoMD/}dimensionsType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="disposition" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dtv" type="{http://www.loc.gov/videoMD/}dtvType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="generation" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="material" type="{http://www.loc.gov/videoMD/}materialType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="numberCarriers" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="physFormat" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="signalFormat" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="timecode" type="{http://www.loc.gov/videoMD/}timecodeInfoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="tracking" type="{http://www.loc.gov/videoMD/}trackingInfoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="videodiscType" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="CLV"/&gt;
 *               &lt;enumeration value="CAV"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="videotapeType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
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
    "colorBursts",
    "conditions",
    "dimensions",
    "dispositions",
    "dtvs",
    "generations",
    "materials",
    "numberCarriers",
    "physFormats",
    "signalFormats",
    "timecodes",
    "trackings",
    "videodiscTypes",
    "videotapeTypes",
    "notes"
})
public class PhysicalDataType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "EBUStorageMediaCodes")
    protected List<String> ebuStorageMediaCodes;
    @XmlElement(name = "colorBurst")
    protected List<String> colorBursts;
    @XmlElement(name = "condition")
    protected List<String> conditions;
    protected List<DimensionsType> dimensions;
    @XmlElement(name = "disposition")
    protected List<String> dispositions;
    @XmlElement(name = "dtv")
    protected List<DtvType> dtvs;
    @XmlElement(name = "generation")
    protected List<String> generations;
    @XmlElement(name = "material")
    protected List<MaterialType> materials;
    protected List<String> numberCarriers;
    @XmlElement(name = "physFormat")
    protected List<String> physFormats;
    @XmlElement(name = "signalFormat")
    protected List<String> signalFormats;
    @XmlElement(name = "timecode")
    protected List<TimecodeInfoType> timecodes;
    @XmlElement(name = "tracking")
    protected List<TrackingInfoType> trackings;
    @XmlElement(name = "videodiscType")
    protected List<String> videodiscTypes;
    @XmlElement(name = "videotapeType")
    protected List<String> videotapeTypes;
    @XmlElement(name = "note")
    protected List<String> notes;

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
     * Gets the value of the colorBursts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the colorBursts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColorBursts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getColorBursts() {
        if (colorBursts == null) {
            colorBursts = new ArrayList<String>();
        }
        return this.colorBursts;
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
     * Gets the value of the dtvs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dtvs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDtvs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtvType }
     * 
     * 
     */
    public List<DtvType> getDtvs() {
        if (dtvs == null) {
            dtvs = new ArrayList<DtvType>();
        }
        return this.dtvs;
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
     * Gets the value of the numberCarriers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the numberCarriers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNumberCarriers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNumberCarriers() {
        if (numberCarriers == null) {
            numberCarriers = new ArrayList<String>();
        }
        return this.numberCarriers;
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
     * Gets the value of the signalFormats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signalFormats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignalFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSignalFormats() {
        if (signalFormats == null) {
            signalFormats = new ArrayList<String>();
        }
        return this.signalFormats;
    }

    /**
     * Gets the value of the timecodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timecodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimecodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimecodeInfoType }
     * 
     * 
     */
    public List<TimecodeInfoType> getTimecodes() {
        if (timecodes == null) {
            timecodes = new ArrayList<TimecodeInfoType>();
        }
        return this.timecodes;
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
     * Gets the value of the videodiscTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the videodiscTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVideodiscTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getVideodiscTypes() {
        if (videodiscTypes == null) {
            videodiscTypes = new ArrayList<String>();
        }
        return this.videodiscTypes;
    }

    /**
     * Gets the value of the videotapeTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the videotapeTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVideotapeTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getVideotapeTypes() {
        if (videotapeTypes == null) {
            videotapeTypes = new ArrayList<String>();
        }
        return this.videotapeTypes;
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

}
