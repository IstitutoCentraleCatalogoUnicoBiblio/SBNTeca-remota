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
 * <p>Classe Java per videoInfoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="videoInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aspectRatio" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="closedCaptioningNote" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="closedCaptioningType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dimensions" type="{http://www.loc.gov/videoMD/}dimensionsType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="frame" type="{http://www.loc.gov/videoMD/}frameType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "videoInfoType", propOrder = {
    "aspectRatios",
    "closedCaptioningNotes",
    "closedCaptioningTypes",
    "dimensions",
    "durations",
    "frames",
    "notes"
})
public class VideoInfoType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "aspectRatio")
    protected List<String> aspectRatios;
    @XmlElement(name = "closedCaptioningNote")
    protected List<String> closedCaptioningNotes;
    @XmlElement(name = "closedCaptioningType")
    protected List<String> closedCaptioningTypes;
    protected List<DimensionsType> dimensions;
    @XmlElement(name = "duration")
    protected List<String> durations;
    @XmlElement(name = "frame")
    protected List<FrameType> frames;
    @XmlElement(name = "note")
    protected List<String> notes;

    /**
     * Gets the value of the aspectRatios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aspectRatios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAspectRatios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAspectRatios() {
        if (aspectRatios == null) {
            aspectRatios = new ArrayList<String>();
        }
        return this.aspectRatios;
    }

    /**
     * Gets the value of the closedCaptioningNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the closedCaptioningNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClosedCaptioningNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getClosedCaptioningNotes() {
        if (closedCaptioningNotes == null) {
            closedCaptioningNotes = new ArrayList<String>();
        }
        return this.closedCaptioningNotes;
    }

    /**
     * Gets the value of the closedCaptioningTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the closedCaptioningTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClosedCaptioningTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getClosedCaptioningTypes() {
        if (closedCaptioningTypes == null) {
            closedCaptioningTypes = new ArrayList<String>();
        }
        return this.closedCaptioningTypes;
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
     * Gets the value of the durations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the durations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDurations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDurations() {
        if (durations == null) {
            durations = new ArrayList<String>();
        }
        return this.durations;
    }

    /**
     * Gets the value of the frames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the frames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFrames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FrameType }
     * 
     * 
     */
    public List<FrameType> getFrames() {
        if (frames == null) {
            frames = new ArrayList<FrameType>();
        }
        return this.frames;
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
