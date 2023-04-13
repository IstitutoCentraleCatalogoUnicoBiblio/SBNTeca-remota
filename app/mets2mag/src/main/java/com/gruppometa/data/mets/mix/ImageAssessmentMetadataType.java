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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ImageAssessmentMetadataType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ImageAssessmentMetadataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SpatialMetrics" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="samplingFrequencyPlane" type="{http://www.loc.gov/mix/v20}typeOfSamplingFrequencyPlaneType" minOccurs="0"/&gt;
 *                   &lt;element name="samplingFrequencyUnit" type="{http://www.loc.gov/mix/v20}typeOfSamplingFrequencyUnitType" minOccurs="0"/&gt;
 *                   &lt;element name="xSamplingFrequency" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                   &lt;element name="ySamplingFrequency" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ImageColorEncoding" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="BitsPerSample" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="bitsPerSampleValue" type="{http://www.loc.gov/mix/v20}positiveIntegerType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                             &lt;element name="bitsPerSampleUnit" type="{http://www.loc.gov/mix/v20}typeOfBitsPerSampleUnitType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="samplesPerPixel" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                   &lt;element name="extraSamples" type="{http://www.loc.gov/mix/v20}typeOfExtraSamplesType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="Colormap" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="colormapReference" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
 *                             &lt;element name="embeddedColormap" type="{http://www.loc.gov/mix/v20}base64BinaryType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="GrayResponse" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="grayResponseCurve" type="{http://www.loc.gov/mix/v20}nonNegativeIntegerType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                             &lt;element name="grayResponseUnit" type="{http://www.loc.gov/mix/v20}typeOfGrayResponseUnitType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="WhitePoint" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="whitePointXValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                             &lt;element name="whitePointYValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="PrimaryChromaticities" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="primaryChromaticitiesRedX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                             &lt;element name="primaryChromaticitiesRedY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                             &lt;element name="primaryChromaticitiesGreenX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                             &lt;element name="primaryChromaticitiesGreenY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                             &lt;element name="primaryChromaticitiesBlueX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                             &lt;element name="primaryChromaticitiesBlueY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TargetData" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="targetType" type="{http://www.loc.gov/mix/v20}typeOfTargetTypeType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="TargetID" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="targetManufacturer" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="targetName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="targetNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="targetMedia" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="externalTarget" type="{http://www.loc.gov/mix/v20}URIType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="performanceData" type="{http://www.loc.gov/mix/v20}URIType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageAssessmentMetadataType", propOrder = {
    "spatialMetrics",
    "imageColorEncoding",
    "targetData"
})
public class ImageAssessmentMetadataType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "SpatialMetrics")
    protected ImageAssessmentMetadataType.SpatialMetrics spatialMetrics;
    @XmlElement(name = "ImageColorEncoding")
    protected ImageAssessmentMetadataType.ImageColorEncoding imageColorEncoding;
    @XmlElement(name = "TargetData")
    protected ImageAssessmentMetadataType.TargetData targetData;

    /**
     * Recupera il valore della proprietà spatialMetrics.
     * 
     * @return
     *     possible object is
     *     {@link ImageAssessmentMetadataType.SpatialMetrics }
     *     
     */
    public ImageAssessmentMetadataType.SpatialMetrics getSpatialMetrics() {
        return spatialMetrics;
    }

    /**
     * Imposta il valore della proprietà spatialMetrics.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageAssessmentMetadataType.SpatialMetrics }
     *     
     */
    public void setSpatialMetrics(ImageAssessmentMetadataType.SpatialMetrics value) {
        this.spatialMetrics = value;
    }

    /**
     * Recupera il valore della proprietà imageColorEncoding.
     * 
     * @return
     *     possible object is
     *     {@link ImageAssessmentMetadataType.ImageColorEncoding }
     *     
     */
    public ImageAssessmentMetadataType.ImageColorEncoding getImageColorEncoding() {
        return imageColorEncoding;
    }

    /**
     * Imposta il valore della proprietà imageColorEncoding.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageAssessmentMetadataType.ImageColorEncoding }
     *     
     */
    public void setImageColorEncoding(ImageAssessmentMetadataType.ImageColorEncoding value) {
        this.imageColorEncoding = value;
    }

    /**
     * Recupera il valore della proprietà targetData.
     * 
     * @return
     *     possible object is
     *     {@link ImageAssessmentMetadataType.TargetData }
     *     
     */
    public ImageAssessmentMetadataType.TargetData getTargetData() {
        return targetData;
    }

    /**
     * Imposta il valore della proprietà targetData.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageAssessmentMetadataType.TargetData }
     *     
     */
    public void setTargetData(ImageAssessmentMetadataType.TargetData value) {
        this.targetData = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="BitsPerSample" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="bitsPerSampleValue" type="{http://www.loc.gov/mix/v20}positiveIntegerType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                   &lt;element name="bitsPerSampleUnit" type="{http://www.loc.gov/mix/v20}typeOfBitsPerSampleUnitType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="samplesPerPixel" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *         &lt;element name="extraSamples" type="{http://www.loc.gov/mix/v20}typeOfExtraSamplesType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="Colormap" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="colormapReference" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
     *                   &lt;element name="embeddedColormap" type="{http://www.loc.gov/mix/v20}base64BinaryType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="GrayResponse" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="grayResponseCurve" type="{http://www.loc.gov/mix/v20}nonNegativeIntegerType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                   &lt;element name="grayResponseUnit" type="{http://www.loc.gov/mix/v20}typeOfGrayResponseUnitType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="WhitePoint" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="whitePointXValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                   &lt;element name="whitePointYValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="PrimaryChromaticities" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="primaryChromaticitiesRedX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                   &lt;element name="primaryChromaticitiesRedY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                   &lt;element name="primaryChromaticitiesGreenX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                   &lt;element name="primaryChromaticitiesGreenY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                   &lt;element name="primaryChromaticitiesBlueX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                   &lt;element name="primaryChromaticitiesBlueY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bitsPerSample",
        "samplesPerPixel",
        "extraSamples",
        "colormap",
        "grayResponse",
        "whitePoints",
        "primaryChromaticities"
    })
    public static class ImageColorEncoding
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "BitsPerSample")
        protected ImageAssessmentMetadataType.ImageColorEncoding.BitsPerSample bitsPerSample;
        protected PositiveIntegerType samplesPerPixel;
        protected List<TypeOfExtraSamplesType> extraSamples;
        @XmlElement(name = "Colormap")
        protected ImageAssessmentMetadataType.ImageColorEncoding.Colormap colormap;
        @XmlElement(name = "GrayResponse")
        protected ImageAssessmentMetadataType.ImageColorEncoding.GrayResponse grayResponse;
        @XmlElement(name = "WhitePoint")
        protected List<ImageAssessmentMetadataType.ImageColorEncoding.WhitePoint> whitePoints;
        @XmlElement(name = "PrimaryChromaticities")
        protected List<ImageAssessmentMetadataType.ImageColorEncoding.PrimaryChromaticities> primaryChromaticities;

        /**
         * Recupera il valore della proprietà bitsPerSample.
         * 
         * @return
         *     possible object is
         *     {@link ImageAssessmentMetadataType.ImageColorEncoding.BitsPerSample }
         *     
         */
        public ImageAssessmentMetadataType.ImageColorEncoding.BitsPerSample getBitsPerSample() {
            return bitsPerSample;
        }

        /**
         * Imposta il valore della proprietà bitsPerSample.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageAssessmentMetadataType.ImageColorEncoding.BitsPerSample }
         *     
         */
        public void setBitsPerSample(ImageAssessmentMetadataType.ImageColorEncoding.BitsPerSample value) {
            this.bitsPerSample = value;
        }

        /**
         * Recupera il valore della proprietà samplesPerPixel.
         * 
         * @return
         *     possible object is
         *     {@link PositiveIntegerType }
         *     
         */
        public PositiveIntegerType getSamplesPerPixel() {
            return samplesPerPixel;
        }

        /**
         * Imposta il valore della proprietà samplesPerPixel.
         * 
         * @param value
         *     allowed object is
         *     {@link PositiveIntegerType }
         *     
         */
        public void setSamplesPerPixel(PositiveIntegerType value) {
            this.samplesPerPixel = value;
        }

        /**
         * Gets the value of the extraSamples property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the extraSamples property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExtraSamples().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TypeOfExtraSamplesType }
         * 
         * 
         */
        public List<TypeOfExtraSamplesType> getExtraSamples() {
            if (extraSamples == null) {
                extraSamples = new ArrayList<TypeOfExtraSamplesType>();
            }
            return this.extraSamples;
        }

        /**
         * Recupera il valore della proprietà colormap.
         * 
         * @return
         *     possible object is
         *     {@link ImageAssessmentMetadataType.ImageColorEncoding.Colormap }
         *     
         */
        public ImageAssessmentMetadataType.ImageColorEncoding.Colormap getColormap() {
            return colormap;
        }

        /**
         * Imposta il valore della proprietà colormap.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageAssessmentMetadataType.ImageColorEncoding.Colormap }
         *     
         */
        public void setColormap(ImageAssessmentMetadataType.ImageColorEncoding.Colormap value) {
            this.colormap = value;
        }

        /**
         * Recupera il valore della proprietà grayResponse.
         * 
         * @return
         *     possible object is
         *     {@link ImageAssessmentMetadataType.ImageColorEncoding.GrayResponse }
         *     
         */
        public ImageAssessmentMetadataType.ImageColorEncoding.GrayResponse getGrayResponse() {
            return grayResponse;
        }

        /**
         * Imposta il valore della proprietà grayResponse.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageAssessmentMetadataType.ImageColorEncoding.GrayResponse }
         *     
         */
        public void setGrayResponse(ImageAssessmentMetadataType.ImageColorEncoding.GrayResponse value) {
            this.grayResponse = value;
        }

        /**
         * Gets the value of the whitePoints property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the whitePoints property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWhitePoints().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImageAssessmentMetadataType.ImageColorEncoding.WhitePoint }
         * 
         * 
         */
        public List<ImageAssessmentMetadataType.ImageColorEncoding.WhitePoint> getWhitePoints() {
            if (whitePoints == null) {
                whitePoints = new ArrayList<ImageAssessmentMetadataType.ImageColorEncoding.WhitePoint>();
            }
            return this.whitePoints;
        }

        /**
         * Gets the value of the primaryChromaticities property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the primaryChromaticities property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPrimaryChromaticities().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImageAssessmentMetadataType.ImageColorEncoding.PrimaryChromaticities }
         * 
         * 
         */
        public List<ImageAssessmentMetadataType.ImageColorEncoding.PrimaryChromaticities> getPrimaryChromaticities() {
            if (primaryChromaticities == null) {
                primaryChromaticities = new ArrayList<ImageAssessmentMetadataType.ImageColorEncoding.PrimaryChromaticities>();
            }
            return this.primaryChromaticities;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="bitsPerSampleValue" type="{http://www.loc.gov/mix/v20}positiveIntegerType" maxOccurs="unbounded" minOccurs="0"/&gt;
         *         &lt;element name="bitsPerSampleUnit" type="{http://www.loc.gov/mix/v20}typeOfBitsPerSampleUnitType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "bitsPerSampleValues",
            "bitsPerSampleUnit"
        })
        public static class BitsPerSample
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "bitsPerSampleValue")
            protected List<PositiveIntegerType> bitsPerSampleValues;
            protected TypeOfBitsPerSampleUnitType bitsPerSampleUnit;

            /**
             * Gets the value of the bitsPerSampleValues property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the bitsPerSampleValues property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBitsPerSampleValues().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PositiveIntegerType }
             * 
             * 
             */
            public List<PositiveIntegerType> getBitsPerSampleValues() {
                if (bitsPerSampleValues == null) {
                    bitsPerSampleValues = new ArrayList<PositiveIntegerType>();
                }
                return this.bitsPerSampleValues;
            }

            /**
             * Recupera il valore della proprietà bitsPerSampleUnit.
             * 
             * @return
             *     possible object is
             *     {@link TypeOfBitsPerSampleUnitType }
             *     
             */
            public TypeOfBitsPerSampleUnitType getBitsPerSampleUnit() {
                return bitsPerSampleUnit;
            }

            /**
             * Imposta il valore della proprietà bitsPerSampleUnit.
             * 
             * @param value
             *     allowed object is
             *     {@link TypeOfBitsPerSampleUnitType }
             *     
             */
            public void setBitsPerSampleUnit(TypeOfBitsPerSampleUnitType value) {
                this.bitsPerSampleUnit = value;
            }

        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="colormapReference" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
         *         &lt;element name="embeddedColormap" type="{http://www.loc.gov/mix/v20}base64BinaryType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "colormapReference",
            "embeddedColormap"
        })
        public static class Colormap
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected URIType colormapReference;
            protected Base64BinaryType embeddedColormap;

            /**
             * Recupera il valore della proprietà colormapReference.
             * 
             * @return
             *     possible object is
             *     {@link URIType }
             *     
             */
            public URIType getColormapReference() {
                return colormapReference;
            }

            /**
             * Imposta il valore della proprietà colormapReference.
             * 
             * @param value
             *     allowed object is
             *     {@link URIType }
             *     
             */
            public void setColormapReference(URIType value) {
                this.colormapReference = value;
            }

            /**
             * Recupera il valore della proprietà embeddedColormap.
             * 
             * @return
             *     possible object is
             *     {@link Base64BinaryType }
             *     
             */
            public Base64BinaryType getEmbeddedColormap() {
                return embeddedColormap;
            }

            /**
             * Imposta il valore della proprietà embeddedColormap.
             * 
             * @param value
             *     allowed object is
             *     {@link Base64BinaryType }
             *     
             */
            public void setEmbeddedColormap(Base64BinaryType value) {
                this.embeddedColormap = value;
            }

        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="grayResponseCurve" type="{http://www.loc.gov/mix/v20}nonNegativeIntegerType" maxOccurs="unbounded" minOccurs="0"/&gt;
         *         &lt;element name="grayResponseUnit" type="{http://www.loc.gov/mix/v20}typeOfGrayResponseUnitType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "grayResponseCurves",
            "grayResponseUnit"
        })
        public static class GrayResponse
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "grayResponseCurve")
            protected List<NonNegativeIntegerType> grayResponseCurves;
            protected TypeOfGrayResponseUnitType grayResponseUnit;

            /**
             * Gets the value of the grayResponseCurves property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the grayResponseCurves property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGrayResponseCurves().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link NonNegativeIntegerType }
             * 
             * 
             */
            public List<NonNegativeIntegerType> getGrayResponseCurves() {
                if (grayResponseCurves == null) {
                    grayResponseCurves = new ArrayList<NonNegativeIntegerType>();
                }
                return this.grayResponseCurves;
            }

            /**
             * Recupera il valore della proprietà grayResponseUnit.
             * 
             * @return
             *     possible object is
             *     {@link TypeOfGrayResponseUnitType }
             *     
             */
            public TypeOfGrayResponseUnitType getGrayResponseUnit() {
                return grayResponseUnit;
            }

            /**
             * Imposta il valore della proprietà grayResponseUnit.
             * 
             * @param value
             *     allowed object is
             *     {@link TypeOfGrayResponseUnitType }
             *     
             */
            public void setGrayResponseUnit(TypeOfGrayResponseUnitType value) {
                this.grayResponseUnit = value;
            }

        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="primaryChromaticitiesRedX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *         &lt;element name="primaryChromaticitiesRedY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *         &lt;element name="primaryChromaticitiesGreenX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *         &lt;element name="primaryChromaticitiesGreenY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *         &lt;element name="primaryChromaticitiesBlueX" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *         &lt;element name="primaryChromaticitiesBlueY" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "primaryChromaticitiesRedX",
            "primaryChromaticitiesRedY",
            "primaryChromaticitiesGreenX",
            "primaryChromaticitiesGreenY",
            "primaryChromaticitiesBlueX",
            "primaryChromaticitiesBlueY"
        })
        public static class PrimaryChromaticities
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected RationalType primaryChromaticitiesRedX;
            protected RationalType primaryChromaticitiesRedY;
            protected RationalType primaryChromaticitiesGreenX;
            protected RationalType primaryChromaticitiesGreenY;
            protected RationalType primaryChromaticitiesBlueX;
            protected RationalType primaryChromaticitiesBlueY;

            /**
             * Recupera il valore della proprietà primaryChromaticitiesRedX.
             * 
             * @return
             *     possible object is
             *     {@link RationalType }
             *     
             */
            public RationalType getPrimaryChromaticitiesRedX() {
                return primaryChromaticitiesRedX;
            }

            /**
             * Imposta il valore della proprietà primaryChromaticitiesRedX.
             * 
             * @param value
             *     allowed object is
             *     {@link RationalType }
             *     
             */
            public void setPrimaryChromaticitiesRedX(RationalType value) {
                this.primaryChromaticitiesRedX = value;
            }

            /**
             * Recupera il valore della proprietà primaryChromaticitiesRedY.
             * 
             * @return
             *     possible object is
             *     {@link RationalType }
             *     
             */
            public RationalType getPrimaryChromaticitiesRedY() {
                return primaryChromaticitiesRedY;
            }

            /**
             * Imposta il valore della proprietà primaryChromaticitiesRedY.
             * 
             * @param value
             *     allowed object is
             *     {@link RationalType }
             *     
             */
            public void setPrimaryChromaticitiesRedY(RationalType value) {
                this.primaryChromaticitiesRedY = value;
            }

            /**
             * Recupera il valore della proprietà primaryChromaticitiesGreenX.
             * 
             * @return
             *     possible object is
             *     {@link RationalType }
             *     
             */
            public RationalType getPrimaryChromaticitiesGreenX() {
                return primaryChromaticitiesGreenX;
            }

            /**
             * Imposta il valore della proprietà primaryChromaticitiesGreenX.
             * 
             * @param value
             *     allowed object is
             *     {@link RationalType }
             *     
             */
            public void setPrimaryChromaticitiesGreenX(RationalType value) {
                this.primaryChromaticitiesGreenX = value;
            }

            /**
             * Recupera il valore della proprietà primaryChromaticitiesGreenY.
             * 
             * @return
             *     possible object is
             *     {@link RationalType }
             *     
             */
            public RationalType getPrimaryChromaticitiesGreenY() {
                return primaryChromaticitiesGreenY;
            }

            /**
             * Imposta il valore della proprietà primaryChromaticitiesGreenY.
             * 
             * @param value
             *     allowed object is
             *     {@link RationalType }
             *     
             */
            public void setPrimaryChromaticitiesGreenY(RationalType value) {
                this.primaryChromaticitiesGreenY = value;
            }

            /**
             * Recupera il valore della proprietà primaryChromaticitiesBlueX.
             * 
             * @return
             *     possible object is
             *     {@link RationalType }
             *     
             */
            public RationalType getPrimaryChromaticitiesBlueX() {
                return primaryChromaticitiesBlueX;
            }

            /**
             * Imposta il valore della proprietà primaryChromaticitiesBlueX.
             * 
             * @param value
             *     allowed object is
             *     {@link RationalType }
             *     
             */
            public void setPrimaryChromaticitiesBlueX(RationalType value) {
                this.primaryChromaticitiesBlueX = value;
            }

            /**
             * Recupera il valore della proprietà primaryChromaticitiesBlueY.
             * 
             * @return
             *     possible object is
             *     {@link RationalType }
             *     
             */
            public RationalType getPrimaryChromaticitiesBlueY() {
                return primaryChromaticitiesBlueY;
            }

            /**
             * Imposta il valore della proprietà primaryChromaticitiesBlueY.
             * 
             * @param value
             *     allowed object is
             *     {@link RationalType }
             *     
             */
            public void setPrimaryChromaticitiesBlueY(RationalType value) {
                this.primaryChromaticitiesBlueY = value;
            }

        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="whitePointXValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *         &lt;element name="whitePointYValue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "whitePointXValue",
            "whitePointYValue"
        })
        public static class WhitePoint
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected RationalType whitePointXValue;
            protected RationalType whitePointYValue;

            /**
             * Recupera il valore della proprietà whitePointXValue.
             * 
             * @return
             *     possible object is
             *     {@link RationalType }
             *     
             */
            public RationalType getWhitePointXValue() {
                return whitePointXValue;
            }

            /**
             * Imposta il valore della proprietà whitePointXValue.
             * 
             * @param value
             *     allowed object is
             *     {@link RationalType }
             *     
             */
            public void setWhitePointXValue(RationalType value) {
                this.whitePointXValue = value;
            }

            /**
             * Recupera il valore della proprietà whitePointYValue.
             * 
             * @return
             *     possible object is
             *     {@link RationalType }
             *     
             */
            public RationalType getWhitePointYValue() {
                return whitePointYValue;
            }

            /**
             * Imposta il valore della proprietà whitePointYValue.
             * 
             * @param value
             *     allowed object is
             *     {@link RationalType }
             *     
             */
            public void setWhitePointYValue(RationalType value) {
                this.whitePointYValue = value;
            }

        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="samplingFrequencyPlane" type="{http://www.loc.gov/mix/v20}typeOfSamplingFrequencyPlaneType" minOccurs="0"/&gt;
     *         &lt;element name="samplingFrequencyUnit" type="{http://www.loc.gov/mix/v20}typeOfSamplingFrequencyUnitType" minOccurs="0"/&gt;
     *         &lt;element name="xSamplingFrequency" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *         &lt;element name="ySamplingFrequency" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "samplingFrequencyPlane",
        "samplingFrequencyUnit",
        "xSamplingFrequency",
        "ySamplingFrequency"
    })
    public static class SpatialMetrics
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected TypeOfSamplingFrequencyPlaneType samplingFrequencyPlane;
        protected TypeOfSamplingFrequencyUnitType samplingFrequencyUnit;
        protected RationalType xSamplingFrequency;
        protected RationalType ySamplingFrequency;

        /**
         * Recupera il valore della proprietà samplingFrequencyPlane.
         * 
         * @return
         *     possible object is
         *     {@link TypeOfSamplingFrequencyPlaneType }
         *     
         */
        public TypeOfSamplingFrequencyPlaneType getSamplingFrequencyPlane() {
            return samplingFrequencyPlane;
        }

        /**
         * Imposta il valore della proprietà samplingFrequencyPlane.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeOfSamplingFrequencyPlaneType }
         *     
         */
        public void setSamplingFrequencyPlane(TypeOfSamplingFrequencyPlaneType value) {
            this.samplingFrequencyPlane = value;
        }

        /**
         * Recupera il valore della proprietà samplingFrequencyUnit.
         * 
         * @return
         *     possible object is
         *     {@link TypeOfSamplingFrequencyUnitType }
         *     
         */
        public TypeOfSamplingFrequencyUnitType getSamplingFrequencyUnit() {
            return samplingFrequencyUnit;
        }

        /**
         * Imposta il valore della proprietà samplingFrequencyUnit.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeOfSamplingFrequencyUnitType }
         *     
         */
        public void setSamplingFrequencyUnit(TypeOfSamplingFrequencyUnitType value) {
            this.samplingFrequencyUnit = value;
        }

        /**
         * Recupera il valore della proprietà xSamplingFrequency.
         * 
         * @return
         *     possible object is
         *     {@link RationalType }
         *     
         */
        public RationalType getXSamplingFrequency() {
            return xSamplingFrequency;
        }

        /**
         * Imposta il valore della proprietà xSamplingFrequency.
         * 
         * @param value
         *     allowed object is
         *     {@link RationalType }
         *     
         */
        public void setXSamplingFrequency(RationalType value) {
            this.xSamplingFrequency = value;
        }

        /**
         * Recupera il valore della proprietà ySamplingFrequency.
         * 
         * @return
         *     possible object is
         *     {@link RationalType }
         *     
         */
        public RationalType getYSamplingFrequency() {
            return ySamplingFrequency;
        }

        /**
         * Imposta il valore della proprietà ySamplingFrequency.
         * 
         * @param value
         *     allowed object is
         *     {@link RationalType }
         *     
         */
        public void setYSamplingFrequency(RationalType value) {
            this.ySamplingFrequency = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="targetType" type="{http://www.loc.gov/mix/v20}typeOfTargetTypeType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="TargetID" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="targetManufacturer" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="targetName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="targetNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="targetMedia" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="externalTarget" type="{http://www.loc.gov/mix/v20}URIType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="performanceData" type="{http://www.loc.gov/mix/v20}URIType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "targetTypes",
        "targetIDs",
        "externalTargets",
        "performanceDatas"
    })
    public static class TargetData
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "targetType")
        protected List<TypeOfTargetTypeType> targetTypes;
        @XmlElement(name = "TargetID")
        protected List<ImageAssessmentMetadataType.TargetData.TargetID> targetIDs;
        @XmlElement(name = "externalTarget")
        protected List<URIType> externalTargets;
        @XmlElement(name = "performanceData")
        protected List<URIType> performanceDatas;

        /**
         * Gets the value of the targetTypes property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the targetTypes property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTargetTypes().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TypeOfTargetTypeType }
         * 
         * 
         */
        public List<TypeOfTargetTypeType> getTargetTypes() {
            if (targetTypes == null) {
                targetTypes = new ArrayList<TypeOfTargetTypeType>();
            }
            return this.targetTypes;
        }

        /**
         * Gets the value of the targetIDs property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the targetIDs property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTargetIDs().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImageAssessmentMetadataType.TargetData.TargetID }
         * 
         * 
         */
        public List<ImageAssessmentMetadataType.TargetData.TargetID> getTargetIDs() {
            if (targetIDs == null) {
                targetIDs = new ArrayList<ImageAssessmentMetadataType.TargetData.TargetID>();
            }
            return this.targetIDs;
        }

        /**
         * Gets the value of the externalTargets property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the externalTargets property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExternalTargets().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link URIType }
         * 
         * 
         */
        public List<URIType> getExternalTargets() {
            if (externalTargets == null) {
                externalTargets = new ArrayList<URIType>();
            }
            return this.externalTargets;
        }

        /**
         * Gets the value of the performanceDatas property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the performanceDatas property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPerformanceDatas().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link URIType }
         * 
         * 
         */
        public List<URIType> getPerformanceDatas() {
            if (performanceDatas == null) {
                performanceDatas = new ArrayList<URIType>();
            }
            return this.performanceDatas;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="targetManufacturer" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="targetName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="targetNo" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="targetMedia" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "targetManufacturer",
            "targetName",
            "targetNo",
            "targetMedia"
        })
        public static class TargetID
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected StringType targetManufacturer;
            protected StringType targetName;
            protected StringType targetNo;
            protected StringType targetMedia;

            /**
             * Recupera il valore della proprietà targetManufacturer.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getTargetManufacturer() {
                return targetManufacturer;
            }

            /**
             * Imposta il valore della proprietà targetManufacturer.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setTargetManufacturer(StringType value) {
                this.targetManufacturer = value;
            }

            /**
             * Recupera il valore della proprietà targetName.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getTargetName() {
                return targetName;
            }

            /**
             * Imposta il valore della proprietà targetName.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setTargetName(StringType value) {
                this.targetName = value;
            }

            /**
             * Recupera il valore della proprietà targetNo.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getTargetNo() {
                return targetNo;
            }

            /**
             * Imposta il valore della proprietà targetNo.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setTargetNo(StringType value) {
                this.targetNo = value;
            }

            /**
             * Recupera il valore della proprietà targetMedia.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getTargetMedia() {
                return targetMedia;
            }

            /**
             * Imposta il valore della proprietà targetMedia.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setTargetMedia(StringType value) {
                this.targetMedia = value;
            }

        }

    }

}
