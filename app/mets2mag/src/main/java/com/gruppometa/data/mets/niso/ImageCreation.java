//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.niso;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per image_creation complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="image_creation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sourcetype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="scanningagency" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="devicesource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="scanningsystem" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="scanner_manufacturer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="scanner_model" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="capture_software" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "image_creation", propOrder = {
    "sourcetype",
    "scanningagency",
    "devicesource",
    "scanningsystem"
})
public class ImageCreation
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected String sourcetype;
    protected Object scanningagency;
    protected String devicesource;
    protected ImageCreation.Scanningsystem scanningsystem;

    /**
     * Recupera il valore della proprietà sourcetype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourcetype() {
        return sourcetype;
    }

    /**
     * Imposta il valore della proprietà sourcetype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourcetype(String value) {
        this.sourcetype = value;
    }

    /**
     * Recupera il valore della proprietà scanningagency.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getScanningagency() {
        return scanningagency;
    }

    /**
     * Imposta il valore della proprietà scanningagency.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setScanningagency(Object value) {
        this.scanningagency = value;
    }

    /**
     * Recupera il valore della proprietà devicesource.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevicesource() {
        return devicesource;
    }

    /**
     * Imposta il valore della proprietà devicesource.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevicesource(String value) {
        this.devicesource = value;
    }

    /**
     * Recupera il valore della proprietà scanningsystem.
     * 
     * @return
     *     possible object is
     *     {@link ImageCreation.Scanningsystem }
     *     
     */
    public ImageCreation.Scanningsystem getScanningsystem() {
        return scanningsystem;
    }

    /**
     * Imposta il valore della proprietà scanningsystem.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageCreation.Scanningsystem }
     *     
     */
    public void setScanningsystem(ImageCreation.Scanningsystem value) {
        this.scanningsystem = value;
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
     *         &lt;element name="scanner_manufacturer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="scanner_model" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="capture_software" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "scannerManufacturer",
        "scannerModel",
        "captureSoftware"
    })
    public static class Scanningsystem
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "scanner_manufacturer", required = true)
        protected String scannerManufacturer;
        @XmlElement(name = "scanner_model", required = true)
        protected String scannerModel;
        @XmlElement(name = "capture_software", required = true)
        protected String captureSoftware;

        /**
         * Recupera il valore della proprietà scannerManufacturer.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getScannerManufacturer() {
            return scannerManufacturer;
        }

        /**
         * Imposta il valore della proprietà scannerManufacturer.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setScannerManufacturer(String value) {
            this.scannerManufacturer = value;
        }

        /**
         * Recupera il valore della proprietà scannerModel.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getScannerModel() {
            return scannerModel;
        }

        /**
         * Imposta il valore della proprietà scannerModel.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setScannerModel(String value) {
            this.scannerModel = value;
        }

        /**
         * Recupera il valore della proprietà captureSoftware.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCaptureSoftware() {
            return captureSoftware;
        }

        /**
         * Imposta il valore della proprietà captureSoftware.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCaptureSoftware(String value) {
            this.captureSoftware = value;
        }

    }

}
