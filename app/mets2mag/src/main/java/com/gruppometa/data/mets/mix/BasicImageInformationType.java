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
 * <p>Classe Java per BasicImageInformationType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BasicImageInformationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BasicImageCharacteristics" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="imageWidth" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                   &lt;element name="imageHeight" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                   &lt;element name="PhotometricInterpretation" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="colorSpace" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="ColorProfile" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="IccProfile" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="iccProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                                 &lt;element name="iccProfileVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                                 &lt;element name="iccProfileURI" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="LocalProfile" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="localProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                                 &lt;element name="localProfileURL" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="embeddedProfile" type="{http://www.loc.gov/mix/v20}base64BinaryType" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="YCbCr" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="YCbCrSubSampling" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="yCbCrSubsampleHoriz" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleHorizType" minOccurs="0"/&gt;
 *                                                 &lt;element name="yCbCrSubsampleVert" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleVertType" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="yCbCrPositioning" type="{http://www.loc.gov/mix/v20}typeOfYCbCrPositioningType" minOccurs="0"/&gt;
 *                                       &lt;element name="YCbCrCoefficients" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="lumaRed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                                 &lt;element name="lumaGreen" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                                 &lt;element name="lumaBlue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="ReferenceBlackWhite" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Component" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="componentPhotometricInterpretation" type="{http://www.loc.gov/mix/v20}typeOfComponentPhotometricInterpretationType"/&gt;
 *                                                 &lt;element name="footroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
 *                                                 &lt;element name="headroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
 *         &lt;element name="SpecialFormatCharacteristics" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="JPEG2000" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="CodecCompliance" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="codec" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="codecVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="codestreamProfile" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                       &lt;element name="complianceClass" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="EncodingOptions" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Tiles" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="tileWidth" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                                                 &lt;element name="tileHeight" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="qualityLayers" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                                       &lt;element name="resolutionLevels" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="MrSID" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="zoomLevels" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Djvu" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="djvuFormat" type="{http://www.loc.gov/mix/v20}typeOfDjvuFormatType" minOccurs="0"/&gt;
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
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasicImageInformationType", propOrder = {
    "basicImageCharacteristics",
    "specialFormatCharacteristics"
})
public class BasicImageInformationType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "BasicImageCharacteristics")
    protected BasicImageInformationType.BasicImageCharacteristics basicImageCharacteristics;
    @XmlElement(name = "SpecialFormatCharacteristics")
    protected BasicImageInformationType.SpecialFormatCharacteristics specialFormatCharacteristics;

    /**
     * Recupera il valore della proprietà basicImageCharacteristics.
     * 
     * @return
     *     possible object is
     *     {@link BasicImageInformationType.BasicImageCharacteristics }
     *     
     */
    public BasicImageInformationType.BasicImageCharacteristics getBasicImageCharacteristics() {
        return basicImageCharacteristics;
    }

    /**
     * Imposta il valore della proprietà basicImageCharacteristics.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicImageInformationType.BasicImageCharacteristics }
     *     
     */
    public void setBasicImageCharacteristics(BasicImageInformationType.BasicImageCharacteristics value) {
        this.basicImageCharacteristics = value;
    }

    /**
     * Recupera il valore della proprietà specialFormatCharacteristics.
     * 
     * @return
     *     possible object is
     *     {@link BasicImageInformationType.SpecialFormatCharacteristics }
     *     
     */
    public BasicImageInformationType.SpecialFormatCharacteristics getSpecialFormatCharacteristics() {
        return specialFormatCharacteristics;
    }

    /**
     * Imposta il valore della proprietà specialFormatCharacteristics.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicImageInformationType.SpecialFormatCharacteristics }
     *     
     */
    public void setSpecialFormatCharacteristics(BasicImageInformationType.SpecialFormatCharacteristics value) {
        this.specialFormatCharacteristics = value;
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
     *         &lt;element name="imageWidth" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *         &lt;element name="imageHeight" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *         &lt;element name="PhotometricInterpretation" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="colorSpace" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="ColorProfile" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="IccProfile" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="iccProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                                       &lt;element name="iccProfileVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                                       &lt;element name="iccProfileURI" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="LocalProfile" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="localProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                                       &lt;element name="localProfileURL" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="embeddedProfile" type="{http://www.loc.gov/mix/v20}base64BinaryType" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="YCbCr" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="YCbCrSubSampling" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="yCbCrSubsampleHoriz" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleHorizType" minOccurs="0"/&gt;
     *                                       &lt;element name="yCbCrSubsampleVert" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleVertType" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="yCbCrPositioning" type="{http://www.loc.gov/mix/v20}typeOfYCbCrPositioningType" minOccurs="0"/&gt;
     *                             &lt;element name="YCbCrCoefficients" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="lumaRed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                                       &lt;element name="lumaGreen" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                                       &lt;element name="lumaBlue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="ReferenceBlackWhite" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Component" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="componentPhotometricInterpretation" type="{http://www.loc.gov/mix/v20}typeOfComponentPhotometricInterpretationType"/&gt;
     *                                       &lt;element name="footroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
     *                                       &lt;element name="headroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
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
        "imageWidth",
        "imageHeight",
        "photometricInterpretation"
    })
    public static class BasicImageCharacteristics
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected PositiveIntegerType imageWidth;
        protected PositiveIntegerType imageHeight;
        @XmlElement(name = "PhotometricInterpretation")
        protected BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation photometricInterpretation;

        /**
         * Recupera il valore della proprietà imageWidth.
         * 
         * @return
         *     possible object is
         *     {@link PositiveIntegerType }
         *     
         */
        public PositiveIntegerType getImageWidth() {
            return imageWidth;
        }

        /**
         * Imposta il valore della proprietà imageWidth.
         * 
         * @param value
         *     allowed object is
         *     {@link PositiveIntegerType }
         *     
         */
        public void setImageWidth(PositiveIntegerType value) {
            this.imageWidth = value;
        }

        /**
         * Recupera il valore della proprietà imageHeight.
         * 
         * @return
         *     possible object is
         *     {@link PositiveIntegerType }
         *     
         */
        public PositiveIntegerType getImageHeight() {
            return imageHeight;
        }

        /**
         * Imposta il valore della proprietà imageHeight.
         * 
         * @param value
         *     allowed object is
         *     {@link PositiveIntegerType }
         *     
         */
        public void setImageHeight(PositiveIntegerType value) {
            this.imageHeight = value;
        }

        /**
         * Recupera il valore della proprietà photometricInterpretation.
         * 
         * @return
         *     possible object is
         *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation }
         *     
         */
        public BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation getPhotometricInterpretation() {
            return photometricInterpretation;
        }

        /**
         * Imposta il valore della proprietà photometricInterpretation.
         * 
         * @param value
         *     allowed object is
         *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation }
         *     
         */
        public void setPhotometricInterpretation(BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation value) {
            this.photometricInterpretation = value;
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
         *         &lt;element name="colorSpace" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="ColorProfile" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="IccProfile" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="iccProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                             &lt;element name="iccProfileVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                             &lt;element name="iccProfileURI" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="LocalProfile" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="localProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                             &lt;element name="localProfileURL" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="embeddedProfile" type="{http://www.loc.gov/mix/v20}base64BinaryType" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="YCbCr" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="YCbCrSubSampling" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="yCbCrSubsampleHoriz" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleHorizType" minOccurs="0"/&gt;
         *                             &lt;element name="yCbCrSubsampleVert" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleVertType" minOccurs="0"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="yCbCrPositioning" type="{http://www.loc.gov/mix/v20}typeOfYCbCrPositioningType" minOccurs="0"/&gt;
         *                   &lt;element name="YCbCrCoefficients" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="lumaRed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                             &lt;element name="lumaGreen" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
         *                             &lt;element name="lumaBlue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
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
         *         &lt;element name="ReferenceBlackWhite" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Component" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="componentPhotometricInterpretation" type="{http://www.loc.gov/mix/v20}typeOfComponentPhotometricInterpretationType"/&gt;
         *                             &lt;element name="footroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
         *                             &lt;element name="headroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
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
            "colorSpace",
            "colorProfile",
            "yCbCr",
            "referenceBlackWhites"
        })
        public static class PhotometricInterpretation
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected StringType colorSpace;
            @XmlElement(name = "ColorProfile")
            protected BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile colorProfile;
            @XmlElement(name = "YCbCr")
            protected BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr yCbCr;
            @XmlElement(name = "ReferenceBlackWhite")
            protected List<BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ReferenceBlackWhite> referenceBlackWhites;

            /**
             * Recupera il valore della proprietà colorSpace.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getColorSpace() {
                return colorSpace;
            }

            /**
             * Imposta il valore della proprietà colorSpace.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setColorSpace(StringType value) {
                this.colorSpace = value;
            }

            /**
             * Recupera il valore della proprietà colorProfile.
             * 
             * @return
             *     possible object is
             *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile }
             *     
             */
            public BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile getColorProfile() {
                return colorProfile;
            }

            /**
             * Imposta il valore della proprietà colorProfile.
             * 
             * @param value
             *     allowed object is
             *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile }
             *     
             */
            public void setColorProfile(BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile value) {
                this.colorProfile = value;
            }

            /**
             * Recupera il valore della proprietà yCbCr.
             * 
             * @return
             *     possible object is
             *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr }
             *     
             */
            public BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr getYCbCr() {
                return yCbCr;
            }

            /**
             * Imposta il valore della proprietà yCbCr.
             * 
             * @param value
             *     allowed object is
             *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr }
             *     
             */
            public void setYCbCr(BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr value) {
                this.yCbCr = value;
            }

            /**
             * Gets the value of the referenceBlackWhites property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the referenceBlackWhites property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getReferenceBlackWhites().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ReferenceBlackWhite }
             * 
             * 
             */
            public List<BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ReferenceBlackWhite> getReferenceBlackWhites() {
                if (referenceBlackWhites == null) {
                    referenceBlackWhites = new ArrayList<BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ReferenceBlackWhite>();
                }
                return this.referenceBlackWhites;
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
             *         &lt;element name="IccProfile" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="iccProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *                   &lt;element name="iccProfileVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *                   &lt;element name="iccProfileURI" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="LocalProfile" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="localProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *                   &lt;element name="localProfileURL" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="embeddedProfile" type="{http://www.loc.gov/mix/v20}base64BinaryType" minOccurs="0"/&gt;
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
                "iccProfile",
                "localProfile",
                "embeddedProfile"
            })
            public static class ColorProfile
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "IccProfile")
                protected BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.IccProfile iccProfile;
                @XmlElement(name = "LocalProfile")
                protected BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.LocalProfile localProfile;
                protected Base64BinaryType embeddedProfile;

                /**
                 * Recupera il valore della proprietà iccProfile.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.IccProfile }
                 *     
                 */
                public BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.IccProfile getIccProfile() {
                    return iccProfile;
                }

                /**
                 * Imposta il valore della proprietà iccProfile.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.IccProfile }
                 *     
                 */
                public void setIccProfile(BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.IccProfile value) {
                    this.iccProfile = value;
                }

                /**
                 * Recupera il valore della proprietà localProfile.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.LocalProfile }
                 *     
                 */
                public BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.LocalProfile getLocalProfile() {
                    return localProfile;
                }

                /**
                 * Imposta il valore della proprietà localProfile.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.LocalProfile }
                 *     
                 */
                public void setLocalProfile(BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ColorProfile.LocalProfile value) {
                    this.localProfile = value;
                }

                /**
                 * Recupera il valore della proprietà embeddedProfile.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Base64BinaryType }
                 *     
                 */
                public Base64BinaryType getEmbeddedProfile() {
                    return embeddedProfile;
                }

                /**
                 * Imposta il valore della proprietà embeddedProfile.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Base64BinaryType }
                 *     
                 */
                public void setEmbeddedProfile(Base64BinaryType value) {
                    this.embeddedProfile = value;
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
                 *         &lt;element name="iccProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
                 *         &lt;element name="iccProfileVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
                 *         &lt;element name="iccProfileURI" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
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
                    "iccProfileName",
                    "iccProfileVersion",
                    "iccProfileURI"
                })
                public static class IccProfile
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected StringType iccProfileName;
                    protected StringType iccProfileVersion;
                    protected URIType iccProfileURI;

                    /**
                     * Recupera il valore della proprietà iccProfileName.
                     * 
                     * @return
                     *     possible object is
                     *     {@link StringType }
                     *     
                     */
                    public StringType getIccProfileName() {
                        return iccProfileName;
                    }

                    /**
                     * Imposta il valore della proprietà iccProfileName.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link StringType }
                     *     
                     */
                    public void setIccProfileName(StringType value) {
                        this.iccProfileName = value;
                    }

                    /**
                     * Recupera il valore della proprietà iccProfileVersion.
                     * 
                     * @return
                     *     possible object is
                     *     {@link StringType }
                     *     
                     */
                    public StringType getIccProfileVersion() {
                        return iccProfileVersion;
                    }

                    /**
                     * Imposta il valore della proprietà iccProfileVersion.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link StringType }
                     *     
                     */
                    public void setIccProfileVersion(StringType value) {
                        this.iccProfileVersion = value;
                    }

                    /**
                     * Recupera il valore della proprietà iccProfileURI.
                     * 
                     * @return
                     *     possible object is
                     *     {@link URIType }
                     *     
                     */
                    public URIType getIccProfileURI() {
                        return iccProfileURI;
                    }

                    /**
                     * Imposta il valore della proprietà iccProfileURI.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link URIType }
                     *     
                     */
                    public void setIccProfileURI(URIType value) {
                        this.iccProfileURI = value;
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
                 *         &lt;element name="localProfileName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
                 *         &lt;element name="localProfileURL" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
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
                    "localProfileName",
                    "localProfileURL"
                })
                public static class LocalProfile
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected StringType localProfileName;
                    protected URIType localProfileURL;

                    /**
                     * Recupera il valore della proprietà localProfileName.
                     * 
                     * @return
                     *     possible object is
                     *     {@link StringType }
                     *     
                     */
                    public StringType getLocalProfileName() {
                        return localProfileName;
                    }

                    /**
                     * Imposta il valore della proprietà localProfileName.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link StringType }
                     *     
                     */
                    public void setLocalProfileName(StringType value) {
                        this.localProfileName = value;
                    }

                    /**
                     * Recupera il valore della proprietà localProfileURL.
                     * 
                     * @return
                     *     possible object is
                     *     {@link URIType }
                     *     
                     */
                    public URIType getLocalProfileURL() {
                        return localProfileURL;
                    }

                    /**
                     * Imposta il valore della proprietà localProfileURL.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link URIType }
                     *     
                     */
                    public void setLocalProfileURL(URIType value) {
                        this.localProfileURL = value;
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
             *         &lt;element name="Component" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="componentPhotometricInterpretation" type="{http://www.loc.gov/mix/v20}typeOfComponentPhotometricInterpretationType"/&gt;
             *                   &lt;element name="footroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
             *                   &lt;element name="headroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
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
                "components"
            })
            public static class ReferenceBlackWhite
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "Component")
                protected List<BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ReferenceBlackWhite.Component> components;

                /**
                 * Gets the value of the components property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the components property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getComponents().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ReferenceBlackWhite.Component }
                 * 
                 * 
                 */
                public List<BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ReferenceBlackWhite.Component> getComponents() {
                    if (components == null) {
                        components = new ArrayList<BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.ReferenceBlackWhite.Component>();
                    }
                    return this.components;
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
                 *         &lt;element name="componentPhotometricInterpretation" type="{http://www.loc.gov/mix/v20}typeOfComponentPhotometricInterpretationType"/&gt;
                 *         &lt;element name="footroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
                 *         &lt;element name="headroom" type="{http://www.loc.gov/mix/v20}rationalType"/&gt;
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
                    "componentPhotometricInterpretation",
                    "footroom",
                    "headroom"
                })
                public static class Component
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(required = true)
                    protected TypeOfComponentPhotometricInterpretationType componentPhotometricInterpretation;
                    @XmlElement(required = true)
                    protected RationalType footroom;
                    @XmlElement(required = true)
                    protected RationalType headroom;

                    /**
                     * Recupera il valore della proprietà componentPhotometricInterpretation.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TypeOfComponentPhotometricInterpretationType }
                     *     
                     */
                    public TypeOfComponentPhotometricInterpretationType getComponentPhotometricInterpretation() {
                        return componentPhotometricInterpretation;
                    }

                    /**
                     * Imposta il valore della proprietà componentPhotometricInterpretation.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TypeOfComponentPhotometricInterpretationType }
                     *     
                     */
                    public void setComponentPhotometricInterpretation(TypeOfComponentPhotometricInterpretationType value) {
                        this.componentPhotometricInterpretation = value;
                    }

                    /**
                     * Recupera il valore della proprietà footroom.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getFootroom() {
                        return footroom;
                    }

                    /**
                     * Imposta il valore della proprietà footroom.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setFootroom(RationalType value) {
                        this.footroom = value;
                    }

                    /**
                     * Recupera il valore della proprietà headroom.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getHeadroom() {
                        return headroom;
                    }

                    /**
                     * Imposta il valore della proprietà headroom.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setHeadroom(RationalType value) {
                        this.headroom = value;
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
             *         &lt;element name="YCbCrSubSampling" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="yCbCrSubsampleHoriz" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleHorizType" minOccurs="0"/&gt;
             *                   &lt;element name="yCbCrSubsampleVert" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleVertType" minOccurs="0"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="yCbCrPositioning" type="{http://www.loc.gov/mix/v20}typeOfYCbCrPositioningType" minOccurs="0"/&gt;
             *         &lt;element name="YCbCrCoefficients" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="lumaRed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *                   &lt;element name="lumaGreen" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
             *                   &lt;element name="lumaBlue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
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
                "yCbCrSubSampling",
                "yCbCrPositioning",
                "yCbCrCoefficients"
            })
            public static class YCbCr
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "YCbCrSubSampling")
                protected BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrSubSampling yCbCrSubSampling;
                protected TypeOfYCbCrPositioningType yCbCrPositioning;
                @XmlElement(name = "YCbCrCoefficients")
                protected BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrCoefficients yCbCrCoefficients;

                /**
                 * Recupera il valore della proprietà yCbCrSubSampling.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrSubSampling }
                 *     
                 */
                public BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrSubSampling getYCbCrSubSampling() {
                    return yCbCrSubSampling;
                }

                /**
                 * Imposta il valore della proprietà yCbCrSubSampling.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrSubSampling }
                 *     
                 */
                public void setYCbCrSubSampling(BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrSubSampling value) {
                    this.yCbCrSubSampling = value;
                }

                /**
                 * Recupera il valore della proprietà yCbCrPositioning.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TypeOfYCbCrPositioningType }
                 *     
                 */
                public TypeOfYCbCrPositioningType getYCbCrPositioning() {
                    return yCbCrPositioning;
                }

                /**
                 * Imposta il valore della proprietà yCbCrPositioning.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TypeOfYCbCrPositioningType }
                 *     
                 */
                public void setYCbCrPositioning(TypeOfYCbCrPositioningType value) {
                    this.yCbCrPositioning = value;
                }

                /**
                 * Recupera il valore della proprietà yCbCrCoefficients.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrCoefficients }
                 *     
                 */
                public BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrCoefficients getYCbCrCoefficients() {
                    return yCbCrCoefficients;
                }

                /**
                 * Imposta il valore della proprietà yCbCrCoefficients.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrCoefficients }
                 *     
                 */
                public void setYCbCrCoefficients(BasicImageInformationType.BasicImageCharacteristics.PhotometricInterpretation.YCbCr.YCbCrCoefficients value) {
                    this.yCbCrCoefficients = value;
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
                 *         &lt;element name="lumaRed" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
                 *         &lt;element name="lumaGreen" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
                 *         &lt;element name="lumaBlue" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
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
                    "lumaRed",
                    "lumaGreen",
                    "lumaBlue"
                })
                public static class YCbCrCoefficients
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected RationalType lumaRed;
                    protected RationalType lumaGreen;
                    protected RationalType lumaBlue;

                    /**
                     * Recupera il valore della proprietà lumaRed.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getLumaRed() {
                        return lumaRed;
                    }

                    /**
                     * Imposta il valore della proprietà lumaRed.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setLumaRed(RationalType value) {
                        this.lumaRed = value;
                    }

                    /**
                     * Recupera il valore della proprietà lumaGreen.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getLumaGreen() {
                        return lumaGreen;
                    }

                    /**
                     * Imposta il valore della proprietà lumaGreen.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setLumaGreen(RationalType value) {
                        this.lumaGreen = value;
                    }

                    /**
                     * Recupera il valore della proprietà lumaBlue.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RationalType }
                     *     
                     */
                    public RationalType getLumaBlue() {
                        return lumaBlue;
                    }

                    /**
                     * Imposta il valore della proprietà lumaBlue.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RationalType }
                     *     
                     */
                    public void setLumaBlue(RationalType value) {
                        this.lumaBlue = value;
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
                 *         &lt;element name="yCbCrSubsampleHoriz" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleHorizType" minOccurs="0"/&gt;
                 *         &lt;element name="yCbCrSubsampleVert" type="{http://www.loc.gov/mix/v20}typeOfYCbCrSubsampleVertType" minOccurs="0"/&gt;
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
                    "yCbCrSubsampleHoriz",
                    "yCbCrSubsampleVert"
                })
                public static class YCbCrSubSampling
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected TypeOfYCbCrSubsampleHorizType yCbCrSubsampleHoriz;
                    protected TypeOfYCbCrSubsampleVertType yCbCrSubsampleVert;

                    /**
                     * Recupera il valore della proprietà yCbCrSubsampleHoriz.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TypeOfYCbCrSubsampleHorizType }
                     *     
                     */
                    public TypeOfYCbCrSubsampleHorizType getYCbCrSubsampleHoriz() {
                        return yCbCrSubsampleHoriz;
                    }

                    /**
                     * Imposta il valore della proprietà yCbCrSubsampleHoriz.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TypeOfYCbCrSubsampleHorizType }
                     *     
                     */
                    public void setYCbCrSubsampleHoriz(TypeOfYCbCrSubsampleHorizType value) {
                        this.yCbCrSubsampleHoriz = value;
                    }

                    /**
                     * Recupera il valore della proprietà yCbCrSubsampleVert.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TypeOfYCbCrSubsampleVertType }
                     *     
                     */
                    public TypeOfYCbCrSubsampleVertType getYCbCrSubsampleVert() {
                        return yCbCrSubsampleVert;
                    }

                    /**
                     * Imposta il valore della proprietà yCbCrSubsampleVert.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TypeOfYCbCrSubsampleVertType }
                     *     
                     */
                    public void setYCbCrSubsampleVert(TypeOfYCbCrSubsampleVertType value) {
                        this.yCbCrSubsampleVert = value;
                    }

                }

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
     *         &lt;element name="JPEG2000" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="CodecCompliance" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="codec" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="codecVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="codestreamProfile" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                             &lt;element name="complianceClass" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="EncodingOptions" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Tiles" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="tileWidth" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *                                       &lt;element name="tileHeight" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="qualityLayers" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *                             &lt;element name="resolutionLevels" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
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
     *         &lt;element name="MrSID" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="zoomLevels" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Djvu" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="djvuFormat" type="{http://www.loc.gov/mix/v20}typeOfDjvuFormatType" minOccurs="0"/&gt;
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
        "jpeg2000",
        "mrSID",
        "djvu"
    })
    public static class SpecialFormatCharacteristics
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "JPEG2000")
        protected BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 jpeg2000;
        @XmlElement(name = "MrSID")
        protected BasicImageInformationType.SpecialFormatCharacteristics.MrSID mrSID;
        @XmlElement(name = "Djvu")
        protected BasicImageInformationType.SpecialFormatCharacteristics.Djvu djvu;

        /**
         * Recupera il valore della proprietà jpeg2000.
         * 
         * @return
         *     possible object is
         *     {@link BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 }
         *     
         */
        public BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 getJPEG2000() {
            return jpeg2000;
        }

        /**
         * Imposta il valore della proprietà jpeg2000.
         * 
         * @param value
         *     allowed object is
         *     {@link BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 }
         *     
         */
        public void setJPEG2000(BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 value) {
            this.jpeg2000 = value;
        }

        /**
         * Recupera il valore della proprietà mrSID.
         * 
         * @return
         *     possible object is
         *     {@link BasicImageInformationType.SpecialFormatCharacteristics.MrSID }
         *     
         */
        public BasicImageInformationType.SpecialFormatCharacteristics.MrSID getMrSID() {
            return mrSID;
        }

        /**
         * Imposta il valore della proprietà mrSID.
         * 
         * @param value
         *     allowed object is
         *     {@link BasicImageInformationType.SpecialFormatCharacteristics.MrSID }
         *     
         */
        public void setMrSID(BasicImageInformationType.SpecialFormatCharacteristics.MrSID value) {
            this.mrSID = value;
        }

        /**
         * Recupera il valore della proprietà djvu.
         * 
         * @return
         *     possible object is
         *     {@link BasicImageInformationType.SpecialFormatCharacteristics.Djvu }
         *     
         */
        public BasicImageInformationType.SpecialFormatCharacteristics.Djvu getDjvu() {
            return djvu;
        }

        /**
         * Imposta il valore della proprietà djvu.
         * 
         * @param value
         *     allowed object is
         *     {@link BasicImageInformationType.SpecialFormatCharacteristics.Djvu }
         *     
         */
        public void setDjvu(BasicImageInformationType.SpecialFormatCharacteristics.Djvu value) {
            this.djvu = value;
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
         *         &lt;element name="djvuFormat" type="{http://www.loc.gov/mix/v20}typeOfDjvuFormatType" minOccurs="0"/&gt;
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
            "djvuFormat"
        })
        public static class Djvu
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected TypeOfDjvuFormatType djvuFormat;

            /**
             * Recupera il valore della proprietà djvuFormat.
             * 
             * @return
             *     possible object is
             *     {@link TypeOfDjvuFormatType }
             *     
             */
            public TypeOfDjvuFormatType getDjvuFormat() {
                return djvuFormat;
            }

            /**
             * Imposta il valore della proprietà djvuFormat.
             * 
             * @param value
             *     allowed object is
             *     {@link TypeOfDjvuFormatType }
             *     
             */
            public void setDjvuFormat(TypeOfDjvuFormatType value) {
                this.djvuFormat = value;
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
         *         &lt;element name="CodecCompliance" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="codec" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="codecVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="codestreamProfile" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                   &lt;element name="complianceClass" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="EncodingOptions" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Tiles" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="tileWidth" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
         *                             &lt;element name="tileHeight" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="qualityLayers" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
         *                   &lt;element name="resolutionLevels" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
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
            "codecCompliance",
            "encodingOptions"
        })
        public static class JPEG2000
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "CodecCompliance")
            protected BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .CodecCompliance codecCompliance;
            @XmlElement(name = "EncodingOptions")
            protected BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions encodingOptions;

            /**
             * Recupera il valore della proprietà codecCompliance.
             * 
             * @return
             *     possible object is
             *     {@link BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .CodecCompliance }
             *     
             */
            public BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .CodecCompliance getCodecCompliance() {
                return codecCompliance;
            }

            /**
             * Imposta il valore della proprietà codecCompliance.
             * 
             * @param value
             *     allowed object is
             *     {@link BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .CodecCompliance }
             *     
             */
            public void setCodecCompliance(BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .CodecCompliance value) {
                this.codecCompliance = value;
            }

            /**
             * Recupera il valore della proprietà encodingOptions.
             * 
             * @return
             *     possible object is
             *     {@link BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions }
             *     
             */
            public BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions getEncodingOptions() {
                return encodingOptions;
            }

            /**
             * Imposta il valore della proprietà encodingOptions.
             * 
             * @param value
             *     allowed object is
             *     {@link BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions }
             *     
             */
            public void setEncodingOptions(BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions value) {
                this.encodingOptions = value;
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
             *         &lt;element name="codec" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="codecVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="codestreamProfile" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
             *         &lt;element name="complianceClass" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
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
                "codec",
                "codecVersion",
                "codestreamProfile",
                "complianceClass"
            })
            public static class CodecCompliance
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                protected StringType codec;
                protected StringType codecVersion;
                protected StringType codestreamProfile;
                protected StringType complianceClass;

                /**
                 * Recupera il valore della proprietà codec.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getCodec() {
                    return codec;
                }

                /**
                 * Imposta il valore della proprietà codec.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setCodec(StringType value) {
                    this.codec = value;
                }

                /**
                 * Recupera il valore della proprietà codecVersion.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getCodecVersion() {
                    return codecVersion;
                }

                /**
                 * Imposta il valore della proprietà codecVersion.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setCodecVersion(StringType value) {
                    this.codecVersion = value;
                }

                /**
                 * Recupera il valore della proprietà codestreamProfile.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getCodestreamProfile() {
                    return codestreamProfile;
                }

                /**
                 * Imposta il valore della proprietà codestreamProfile.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setCodestreamProfile(StringType value) {
                    this.codestreamProfile = value;
                }

                /**
                 * Recupera il valore della proprietà complianceClass.
                 * 
                 * @return
                 *     possible object is
                 *     {@link StringType }
                 *     
                 */
                public StringType getComplianceClass() {
                    return complianceClass;
                }

                /**
                 * Imposta il valore della proprietà complianceClass.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link StringType }
                 *     
                 */
                public void setComplianceClass(StringType value) {
                    this.complianceClass = value;
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
             *         &lt;element name="Tiles" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="tileWidth" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
             *                   &lt;element name="tileHeight" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="qualityLayers" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
             *         &lt;element name="resolutionLevels" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
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
                "tiles",
                "qualityLayers",
                "resolutionLevels"
            })
            public static class EncodingOptions
                implements Serializable
            {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "Tiles")
                protected BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions.Tiles tiles;
                protected PositiveIntegerType qualityLayers;
                protected PositiveIntegerType resolutionLevels;

                /**
                 * Recupera il valore della proprietà tiles.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions.Tiles }
                 *     
                 */
                public BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions.Tiles getTiles() {
                    return tiles;
                }

                /**
                 * Imposta il valore della proprietà tiles.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions.Tiles }
                 *     
                 */
                public void setTiles(BasicImageInformationType.SpecialFormatCharacteristics.JPEG2000 .EncodingOptions.Tiles value) {
                    this.tiles = value;
                }

                /**
                 * Recupera il valore della proprietà qualityLayers.
                 * 
                 * @return
                 *     possible object is
                 *     {@link PositiveIntegerType }
                 *     
                 */
                public PositiveIntegerType getQualityLayers() {
                    return qualityLayers;
                }

                /**
                 * Imposta il valore della proprietà qualityLayers.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link PositiveIntegerType }
                 *     
                 */
                public void setQualityLayers(PositiveIntegerType value) {
                    this.qualityLayers = value;
                }

                /**
                 * Recupera il valore della proprietà resolutionLevels.
                 * 
                 * @return
                 *     possible object is
                 *     {@link PositiveIntegerType }
                 *     
                 */
                public PositiveIntegerType getResolutionLevels() {
                    return resolutionLevels;
                }

                /**
                 * Imposta il valore della proprietà resolutionLevels.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link PositiveIntegerType }
                 *     
                 */
                public void setResolutionLevels(PositiveIntegerType value) {
                    this.resolutionLevels = value;
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
                 *         &lt;element name="tileWidth" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
                 *         &lt;element name="tileHeight" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
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
                    "tileWidth",
                    "tileHeight"
                })
                public static class Tiles
                    implements Serializable
                {

                    private final static long serialVersionUID = -1L;
                    protected PositiveIntegerType tileWidth;
                    protected PositiveIntegerType tileHeight;

                    /**
                     * Recupera il valore della proprietà tileWidth.
                     * 
                     * @return
                     *     possible object is
                     *     {@link PositiveIntegerType }
                     *     
                     */
                    public PositiveIntegerType getTileWidth() {
                        return tileWidth;
                    }

                    /**
                     * Imposta il valore della proprietà tileWidth.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link PositiveIntegerType }
                     *     
                     */
                    public void setTileWidth(PositiveIntegerType value) {
                        this.tileWidth = value;
                    }

                    /**
                     * Recupera il valore della proprietà tileHeight.
                     * 
                     * @return
                     *     possible object is
                     *     {@link PositiveIntegerType }
                     *     
                     */
                    public PositiveIntegerType getTileHeight() {
                        return tileHeight;
                    }

                    /**
                     * Imposta il valore della proprietà tileHeight.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link PositiveIntegerType }
                     *     
                     */
                    public void setTileHeight(PositiveIntegerType value) {
                        this.tileHeight = value;
                    }

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
         *         &lt;element name="zoomLevels" type="{http://www.loc.gov/mix/v20}positiveIntegerType" minOccurs="0"/&gt;
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
            "zoomLevels"
        })
        public static class MrSID
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected PositiveIntegerType zoomLevels;

            /**
             * Recupera il valore della proprietà zoomLevels.
             * 
             * @return
             *     possible object is
             *     {@link PositiveIntegerType }
             *     
             */
            public PositiveIntegerType getZoomLevels() {
                return zoomLevels;
            }

            /**
             * Imposta il valore della proprietà zoomLevels.
             * 
             * @param value
             *     allowed object is
             *     {@link PositiveIntegerType }
             *     
             */
            public void setZoomLevels(PositiveIntegerType value) {
                this.zoomLevels = value;
            }

        }

    }

}
