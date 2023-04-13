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
 * <p>Classe Java per BasicDigitalObjectInformationType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BasicDigitalObjectInformationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ObjectIdentifier" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="objectIdentifierType" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="objectIdentifierValue" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="fileSize" type="{http://www.loc.gov/mix/v20}nonNegativeIntegerType" minOccurs="0"/&gt;
 *         &lt;element name="FormatDesignation" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="formatName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="formatVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FormatRegistry" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="formatRegistryName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="formatRegistryKey" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="byteOrder" type="{http://www.loc.gov/mix/v20}typeOfByteOrderType" minOccurs="0"/&gt;
 *         &lt;element name="Compression" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="compressionScheme" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="compressionSchemeLocalList" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
 *                   &lt;element name="compressionSchemeLocalValue" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="compressionRatio" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Fixity" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="messageDigestAlgorithm" type="{http://www.loc.gov/mix/v20}typeOfMessageDigestAlgorithmType" minOccurs="0"/&gt;
 *                   &lt;element name="messageDigest" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
 *                   &lt;element name="messageDigestOriginator" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
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
@XmlType(name = "BasicDigitalObjectInformationType", propOrder = {
    "objectIdentifiers",
    "fileSize",
    "formatDesignation",
    "formatRegistry",
    "byteOrder",
    "compressions",
    "fixities"
})
public class BasicDigitalObjectInformationType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ObjectIdentifier")
    protected List<BasicDigitalObjectInformationType.ObjectIdentifier> objectIdentifiers;
    protected NonNegativeIntegerType fileSize;
    @XmlElement(name = "FormatDesignation")
    protected BasicDigitalObjectInformationType.FormatDesignation formatDesignation;
    @XmlElement(name = "FormatRegistry")
    protected BasicDigitalObjectInformationType.FormatRegistry formatRegistry;
    protected TypeOfByteOrderType byteOrder;
    @XmlElement(name = "Compression")
    protected List<BasicDigitalObjectInformationType.Compression> compressions;
    @XmlElement(name = "Fixity")
    protected List<BasicDigitalObjectInformationType.Fixity> fixities;

    /**
     * Gets the value of the objectIdentifiers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objectIdentifiers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectIdentifiers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BasicDigitalObjectInformationType.ObjectIdentifier }
     * 
     * 
     */
    public List<BasicDigitalObjectInformationType.ObjectIdentifier> getObjectIdentifiers() {
        if (objectIdentifiers == null) {
            objectIdentifiers = new ArrayList<BasicDigitalObjectInformationType.ObjectIdentifier>();
        }
        return this.objectIdentifiers;
    }

    /**
     * Recupera il valore della proprietà fileSize.
     * 
     * @return
     *     possible object is
     *     {@link NonNegativeIntegerType }
     *     
     */
    public NonNegativeIntegerType getFileSize() {
        return fileSize;
    }

    /**
     * Imposta il valore della proprietà fileSize.
     * 
     * @param value
     *     allowed object is
     *     {@link NonNegativeIntegerType }
     *     
     */
    public void setFileSize(NonNegativeIntegerType value) {
        this.fileSize = value;
    }

    /**
     * Recupera il valore della proprietà formatDesignation.
     * 
     * @return
     *     possible object is
     *     {@link BasicDigitalObjectInformationType.FormatDesignation }
     *     
     */
    public BasicDigitalObjectInformationType.FormatDesignation getFormatDesignation() {
        return formatDesignation;
    }

    /**
     * Imposta il valore della proprietà formatDesignation.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicDigitalObjectInformationType.FormatDesignation }
     *     
     */
    public void setFormatDesignation(BasicDigitalObjectInformationType.FormatDesignation value) {
        this.formatDesignation = value;
    }

    /**
     * Recupera il valore della proprietà formatRegistry.
     * 
     * @return
     *     possible object is
     *     {@link BasicDigitalObjectInformationType.FormatRegistry }
     *     
     */
    public BasicDigitalObjectInformationType.FormatRegistry getFormatRegistry() {
        return formatRegistry;
    }

    /**
     * Imposta il valore della proprietà formatRegistry.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicDigitalObjectInformationType.FormatRegistry }
     *     
     */
    public void setFormatRegistry(BasicDigitalObjectInformationType.FormatRegistry value) {
        this.formatRegistry = value;
    }

    /**
     * Recupera il valore della proprietà byteOrder.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfByteOrderType }
     *     
     */
    public TypeOfByteOrderType getByteOrder() {
        return byteOrder;
    }

    /**
     * Imposta il valore della proprietà byteOrder.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfByteOrderType }
     *     
     */
    public void setByteOrder(TypeOfByteOrderType value) {
        this.byteOrder = value;
    }

    /**
     * Gets the value of the compressions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compressions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompressions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BasicDigitalObjectInformationType.Compression }
     * 
     * 
     */
    public List<BasicDigitalObjectInformationType.Compression> getCompressions() {
        if (compressions == null) {
            compressions = new ArrayList<BasicDigitalObjectInformationType.Compression>();
        }
        return this.compressions;
    }

    /**
     * Gets the value of the fixities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fixities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFixities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BasicDigitalObjectInformationType.Fixity }
     * 
     * 
     */
    public List<BasicDigitalObjectInformationType.Fixity> getFixities() {
        if (fixities == null) {
            fixities = new ArrayList<BasicDigitalObjectInformationType.Fixity>();
        }
        return this.fixities;
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
     *         &lt;element name="compressionScheme" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="compressionSchemeLocalList" type="{http://www.loc.gov/mix/v20}URIType" minOccurs="0"/&gt;
     *         &lt;element name="compressionSchemeLocalValue" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="compressionRatio" type="{http://www.loc.gov/mix/v20}rationalType" minOccurs="0"/&gt;
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
        "compressionScheme",
        "compressionSchemeLocalList",
        "compressionSchemeLocalValue",
        "compressionRatio"
    })
    public static class Compression
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected StringType compressionScheme;
        protected URIType compressionSchemeLocalList;
        protected StringType compressionSchemeLocalValue;
        protected RationalType compressionRatio;

        /**
         * Recupera il valore della proprietà compressionScheme.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getCompressionScheme() {
            return compressionScheme;
        }

        /**
         * Imposta il valore della proprietà compressionScheme.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setCompressionScheme(StringType value) {
            this.compressionScheme = value;
        }

        /**
         * Recupera il valore della proprietà compressionSchemeLocalList.
         * 
         * @return
         *     possible object is
         *     {@link URIType }
         *     
         */
        public URIType getCompressionSchemeLocalList() {
            return compressionSchemeLocalList;
        }

        /**
         * Imposta il valore della proprietà compressionSchemeLocalList.
         * 
         * @param value
         *     allowed object is
         *     {@link URIType }
         *     
         */
        public void setCompressionSchemeLocalList(URIType value) {
            this.compressionSchemeLocalList = value;
        }

        /**
         * Recupera il valore della proprietà compressionSchemeLocalValue.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getCompressionSchemeLocalValue() {
            return compressionSchemeLocalValue;
        }

        /**
         * Imposta il valore della proprietà compressionSchemeLocalValue.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setCompressionSchemeLocalValue(StringType value) {
            this.compressionSchemeLocalValue = value;
        }

        /**
         * Recupera il valore della proprietà compressionRatio.
         * 
         * @return
         *     possible object is
         *     {@link RationalType }
         *     
         */
        public RationalType getCompressionRatio() {
            return compressionRatio;
        }

        /**
         * Imposta il valore della proprietà compressionRatio.
         * 
         * @param value
         *     allowed object is
         *     {@link RationalType }
         *     
         */
        public void setCompressionRatio(RationalType value) {
            this.compressionRatio = value;
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
     *         &lt;element name="messageDigestAlgorithm" type="{http://www.loc.gov/mix/v20}typeOfMessageDigestAlgorithmType" minOccurs="0"/&gt;
     *         &lt;element name="messageDigest" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="messageDigestOriginator" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
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
        "messageDigestAlgorithm",
        "messageDigest",
        "messageDigestOriginator"
    })
    public static class Fixity
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected TypeOfMessageDigestAlgorithmType messageDigestAlgorithm;
        protected StringType messageDigest;
        protected StringType messageDigestOriginator;

        /**
         * Recupera il valore della proprietà messageDigestAlgorithm.
         * 
         * @return
         *     possible object is
         *     {@link TypeOfMessageDigestAlgorithmType }
         *     
         */
        public TypeOfMessageDigestAlgorithmType getMessageDigestAlgorithm() {
            return messageDigestAlgorithm;
        }

        /**
         * Imposta il valore della proprietà messageDigestAlgorithm.
         * 
         * @param value
         *     allowed object is
         *     {@link TypeOfMessageDigestAlgorithmType }
         *     
         */
        public void setMessageDigestAlgorithm(TypeOfMessageDigestAlgorithmType value) {
            this.messageDigestAlgorithm = value;
        }

        /**
         * Recupera il valore della proprietà messageDigest.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getMessageDigest() {
            return messageDigest;
        }

        /**
         * Imposta il valore della proprietà messageDigest.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setMessageDigest(StringType value) {
            this.messageDigest = value;
        }

        /**
         * Recupera il valore della proprietà messageDigestOriginator.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getMessageDigestOriginator() {
            return messageDigestOriginator;
        }

        /**
         * Imposta il valore della proprietà messageDigestOriginator.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setMessageDigestOriginator(StringType value) {
            this.messageDigestOriginator = value;
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
     *         &lt;element name="formatName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="formatVersion" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
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
        "formatName",
        "formatVersion"
    })
    public static class FormatDesignation
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected StringType formatName;
        protected StringType formatVersion;

        /**
         * Recupera il valore della proprietà formatName.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getFormatName() {
            return formatName;
        }

        /**
         * Imposta il valore della proprietà formatName.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setFormatName(StringType value) {
            this.formatName = value;
        }

        /**
         * Recupera il valore della proprietà formatVersion.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getFormatVersion() {
            return formatVersion;
        }

        /**
         * Imposta il valore della proprietà formatVersion.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setFormatVersion(StringType value) {
            this.formatVersion = value;
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
     *         &lt;element name="formatRegistryName" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="formatRegistryKey" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
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
        "formatRegistryName",
        "formatRegistryKey"
    })
    public static class FormatRegistry
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected StringType formatRegistryName;
        protected StringType formatRegistryKey;

        /**
         * Recupera il valore della proprietà formatRegistryName.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getFormatRegistryName() {
            return formatRegistryName;
        }

        /**
         * Imposta il valore della proprietà formatRegistryName.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setFormatRegistryName(StringType value) {
            this.formatRegistryName = value;
        }

        /**
         * Recupera il valore della proprietà formatRegistryKey.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getFormatRegistryKey() {
            return formatRegistryKey;
        }

        /**
         * Imposta il valore della proprietà formatRegistryKey.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setFormatRegistryKey(StringType value) {
            this.formatRegistryKey = value;
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
     *         &lt;element name="objectIdentifierType" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
     *         &lt;element name="objectIdentifierValue" type="{http://www.loc.gov/mix/v20}stringType" minOccurs="0"/&gt;
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
        "objectIdentifierType",
        "objectIdentifierValue"
    })
    public static class ObjectIdentifier
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected StringType objectIdentifierType;
        protected StringType objectIdentifierValue;

        /**
         * Recupera il valore della proprietà objectIdentifierType.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getObjectIdentifierType() {
            return objectIdentifierType;
        }

        /**
         * Imposta il valore della proprietà objectIdentifierType.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setObjectIdentifierType(StringType value) {
            this.objectIdentifierType = value;
        }

        /**
         * Recupera il valore della proprietà objectIdentifierValue.
         * 
         * @return
         *     possible object is
         *     {@link StringType }
         *     
         */
        public StringType getObjectIdentifierValue() {
            return objectIdentifierValue;
        }

        /**
         * Imposta il valore della proprietà objectIdentifierValue.
         * 
         * @param value
         *     allowed object is
         *     {@link StringType }
         *     
         */
        public void setObjectIdentifierValue(StringType value) {
            this.objectIdentifierValue = value;
        }

    }

}
