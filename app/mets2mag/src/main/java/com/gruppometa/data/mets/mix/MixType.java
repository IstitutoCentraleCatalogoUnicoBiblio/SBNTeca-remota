//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.mix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per mixType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="mixType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BasicDigitalObjectInformation" type="{http://www.loc.gov/mix/v20}BasicDigitalObjectInformationType" minOccurs="0"/&gt;
 *         &lt;element name="BasicImageInformation" type="{http://www.loc.gov/mix/v20}BasicImageInformationType" minOccurs="0"/&gt;
 *         &lt;element name="ImageCaptureMetadata" type="{http://www.loc.gov/mix/v20}ImageCaptureMetadataType" minOccurs="0"/&gt;
 *         &lt;element name="ImageAssessmentMetadata" type="{http://www.loc.gov/mix/v20}ImageAssessmentMetadataType" minOccurs="0"/&gt;
 *         &lt;element name="ChangeHistory" type="{http://www.loc.gov/mix/v20}ChangeHistoryType" minOccurs="0"/&gt;
 *         &lt;element name="Extension" type="{http://www.loc.gov/mix/v20}extensionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mixType", propOrder = {
    "basicDigitalObjectInformation",
    "basicImageInformation",
    "imageCaptureMetadata",
    "imageAssessmentMetadata",
    "changeHistory",
    "extensions"
})
@XmlSeeAlso({
    Mix.class
})
public class MixType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "BasicDigitalObjectInformation")
    protected BasicDigitalObjectInformationType basicDigitalObjectInformation;
    @XmlElement(name = "BasicImageInformation")
    protected BasicImageInformationType basicImageInformation;
    @XmlElement(name = "ImageCaptureMetadata")
    protected ImageCaptureMetadataType imageCaptureMetadata;
    @XmlElement(name = "ImageAssessmentMetadata")
    protected ImageAssessmentMetadataType imageAssessmentMetadata;
    @XmlElement(name = "ChangeHistory")
    protected ChangeHistoryType changeHistory;
    @XmlElement(name = "Extension")
    protected List<ExtensionType> extensions;

    /**
     * Recupera il valore della proprietà basicDigitalObjectInformation.
     * 
     * @return
     *     possible object is
     *     {@link BasicDigitalObjectInformationType }
     *     
     */
    public BasicDigitalObjectInformationType getBasicDigitalObjectInformation() {
        return basicDigitalObjectInformation;
    }

    /**
     * Imposta il valore della proprietà basicDigitalObjectInformation.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicDigitalObjectInformationType }
     *     
     */
    public void setBasicDigitalObjectInformation(BasicDigitalObjectInformationType value) {
        this.basicDigitalObjectInformation = value;
    }

    /**
     * Recupera il valore della proprietà basicImageInformation.
     * 
     * @return
     *     possible object is
     *     {@link BasicImageInformationType }
     *     
     */
    public BasicImageInformationType getBasicImageInformation() {
        return basicImageInformation;
    }

    /**
     * Imposta il valore della proprietà basicImageInformation.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicImageInformationType }
     *     
     */
    public void setBasicImageInformation(BasicImageInformationType value) {
        this.basicImageInformation = value;
    }

    /**
     * Recupera il valore della proprietà imageCaptureMetadata.
     * 
     * @return
     *     possible object is
     *     {@link ImageCaptureMetadataType }
     *     
     */
    public ImageCaptureMetadataType getImageCaptureMetadata() {
        return imageCaptureMetadata;
    }

    /**
     * Imposta il valore della proprietà imageCaptureMetadata.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageCaptureMetadataType }
     *     
     */
    public void setImageCaptureMetadata(ImageCaptureMetadataType value) {
        this.imageCaptureMetadata = value;
    }

    /**
     * Recupera il valore della proprietà imageAssessmentMetadata.
     * 
     * @return
     *     possible object is
     *     {@link ImageAssessmentMetadataType }
     *     
     */
    public ImageAssessmentMetadataType getImageAssessmentMetadata() {
        return imageAssessmentMetadata;
    }

    /**
     * Imposta il valore della proprietà imageAssessmentMetadata.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageAssessmentMetadataType }
     *     
     */
    public void setImageAssessmentMetadata(ImageAssessmentMetadataType value) {
        this.imageAssessmentMetadata = value;
    }

    /**
     * Recupera il valore della proprietà changeHistory.
     * 
     * @return
     *     possible object is
     *     {@link ChangeHistoryType }
     *     
     */
    public ChangeHistoryType getChangeHistory() {
        return changeHistory;
    }

    /**
     * Imposta il valore della proprietà changeHistory.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeHistoryType }
     *     
     */
    public void setChangeHistory(ChangeHistoryType value) {
        this.changeHistory = value;
    }

    /**
     * Gets the value of the extensions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extensions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtensions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType }
     * 
     * 
     */
    public List<ExtensionType> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<ExtensionType>();
        }
        return this.extensions;
    }

}
