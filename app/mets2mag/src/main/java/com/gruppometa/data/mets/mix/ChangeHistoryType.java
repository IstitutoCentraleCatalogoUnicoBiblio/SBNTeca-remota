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
 * <p>Classe Java per ChangeHistoryType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ChangeHistoryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ImageProcessing" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dateTimeProcessed" type="{http://www.loc.gov/mix/v20}typeOfDateType" minOccurs="0"/&gt;
 *                   &lt;element name="sourceData" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="processingAgency" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="processingRationale" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="ProcessingSoftware" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="processingSoftwareName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="processingSoftwareVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="processingOperatingSystemName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                             &lt;element name="processingOperatingSystemVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="processingActions" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PreviousImageMetadata" type="{http://www.loc.gov/mix/v20}typeOfPreviousImageMetadataType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangeHistoryType", propOrder = {
    "imageProcessings",
    "previousImageMetadatas"
})
public class ChangeHistoryType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ImageProcessing")
    protected List<ChangeHistoryType.ImageProcessing> imageProcessings;
    @XmlElement(name = "PreviousImageMetadata")
    protected List<TypeOfPreviousImageMetadataType> previousImageMetadatas;

    /**
     * Gets the value of the imageProcessings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imageProcessings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImageProcessings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChangeHistoryType.ImageProcessing }
     * 
     * 
     */
    public List<ChangeHistoryType.ImageProcessing> getImageProcessings() {
        if (imageProcessings == null) {
            imageProcessings = new ArrayList<ChangeHistoryType.ImageProcessing>();
        }
        return this.imageProcessings;
    }

    /**
     * Gets the value of the previousImageMetadatas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the previousImageMetadatas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreviousImageMetadatas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeOfPreviousImageMetadataType }
     * 
     * 
     */
    public List<TypeOfPreviousImageMetadataType> getPreviousImageMetadatas() {
        if (previousImageMetadatas == null) {
            previousImageMetadatas = new ArrayList<TypeOfPreviousImageMetadataType>();
        }
        return this.previousImageMetadatas;
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
     *         &lt;element name="dateTimeProcessed" type="{http://www.loc.gov/mix/v20}typeOfDateType" minOccurs="0"/&gt;
     *         &lt;element name="sourceData" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="processingAgency" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="processingRationale" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="ProcessingSoftware" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="processingSoftwareName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="processingSoftwareVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="processingOperatingSystemName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                   &lt;element name="processingOperatingSystemVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="processingActions" type="{http://www.loc.gov/mix/v20}stringType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "dateTimeProcessed",
        "sourceData",
        "processingAgencies",
        "processingRationale",
        "processingSoftwares",
        "processingActions"
    })
    public static class ImageProcessing
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected TypeOfDateType dateTimeProcessed;
        protected StringType sourceData;
        @XmlElement(name = "processingAgency")
        protected List<StringType> processingAgencies;
        protected StringType processingRationale;
        @XmlElement(name = "ProcessingSoftware")
        protected List<ChangeHistoryType.ImageProcessing.ProcessingSoftware> processingSoftwares;
        protected List<StringType> processingActions;

        /**
         * Recupera il valore della proprietà dateTimeProcessed.
         * 
         * @return
         *     possible object is
         *     {@link TypeOfDateType }
         *     
         */
        public TypeOfDateType getDateTimeProcessed() {
            return dateTimeProcessed;
        }

        /**
         * Imposta il valore della proprietà dateTimeProcessed.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeOfDateType }
         *     
         */
        public void setDateTimeProcessed(TypeOfDateType value) {
            this.dateTimeProcessed = value;
        }

        /**
         * Recupera il valore della proprietà sourceData.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getSourceData() {
            return sourceData;
        }

        /**
         * Imposta il valore della proprietà sourceData.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setSourceData(StringType value) {
            this.sourceData = value;
        }

        /**
         * Gets the value of the processingAgencies property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the processingAgencies property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProcessingAgencies().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StringType }
         * 
         * 
         */
        public List<StringType> getProcessingAgencies() {
            if (processingAgencies == null) {
                processingAgencies = new ArrayList<StringType>();
            }
            return this.processingAgencies;
        }

        /**
         * Recupera il valore della proprietà processingRationale.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getProcessingRationale() {
            return processingRationale;
        }

        /**
         * Imposta il valore della proprietà processingRationale.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setProcessingRationale(StringType value) {
            this.processingRationale = value;
        }

        /**
         * Gets the value of the processingSoftwares property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the processingSoftwares property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProcessingSoftwares().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ChangeHistoryType.ImageProcessing.ProcessingSoftware }
         * 
         * 
         */
        public List<ChangeHistoryType.ImageProcessing.ProcessingSoftware> getProcessingSoftwares() {
            if (processingSoftwares == null) {
                processingSoftwares = new ArrayList<ChangeHistoryType.ImageProcessing.ProcessingSoftware>();
            }
            return this.processingSoftwares;
        }

        /**
         * Gets the value of the processingActions property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the processingActions property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProcessingActions().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StringType }
         * 
         * 
         */
        public List<StringType> getProcessingActions() {
            if (processingActions == null) {
                processingActions = new ArrayList<StringType>();
            }
            return this.processingActions;
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
         *         &lt;element name="processingSoftwareName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="processingSoftwareVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="processingOperatingSystemName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
         *         &lt;element name="processingOperatingSystemVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
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
            "processingSoftwareName",
            "processingSoftwareVersion",
            "processingOperatingSystemName",
            "processingOperatingSystemVersion"
        })
        public static class ProcessingSoftware
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            protected StringType processingSoftwareName;
            protected StringType processingSoftwareVersion;
            protected StringType processingOperatingSystemName;
            protected StringType processingOperatingSystemVersion;

            /**
             * Recupera il valore della proprietà processingSoftwareName.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getProcessingSoftwareName() {
                return processingSoftwareName;
            }

            /**
             * Imposta il valore della proprietà processingSoftwareName.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setProcessingSoftwareName(StringType value) {
                this.processingSoftwareName = value;
            }

            /**
             * Recupera il valore della proprietà processingSoftwareVersion.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getProcessingSoftwareVersion() {
                return processingSoftwareVersion;
            }

            /**
             * Imposta il valore della proprietà processingSoftwareVersion.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setProcessingSoftwareVersion(StringType value) {
                this.processingSoftwareVersion = value;
            }

            /**
             * Recupera il valore della proprietà processingOperatingSystemName.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getProcessingOperatingSystemName() {
                return processingOperatingSystemName;
            }

            /**
             * Imposta il valore della proprietà processingOperatingSystemName.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setProcessingOperatingSystemName(StringType value) {
                this.processingOperatingSystemName = value;
            }

            /**
             * Recupera il valore della proprietà processingOperatingSystemVersion.
             * 
             * @return
             *     possible object is
             *     {@link StringType }
             *     
             */
            public StringType getProcessingOperatingSystemVersion() {
                return processingOperatingSystemVersion;
            }

            /**
             * Imposta il valore della proprietà processingOperatingSystemVersion.
             * 
             * @param value
             *     allowed object is
             *     {@link StringType }
             *     
             */
            public void setProcessingOperatingSystemVersion(StringType value) {
                this.processingOperatingSystemVersion = value;
            }

        }

    }

}
