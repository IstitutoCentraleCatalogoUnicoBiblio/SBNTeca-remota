//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.metsrights;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Information about the contact point for rights holder organization, as
 *         identified by the RIGHTSHOLDERID. The contact point for a resource may be an agency or
 *         organization representing the rights holder rather than the rights holder per se. Each
 *         RightsHolderContactTYPE may contain a RightsHolderContactDesignation,
 *         RightsHolderContactAddress element, a RightsHolderContactPhone element, and a
 *         RightsHolderContactEmail element. 
 * 
 * <p>Classe Java per RightsHolderContactTYPE complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RightsHolderContactTYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RightsHolderContactDesignation" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RightsHolderContactAddress" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RightsHolderContactPhone" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="PHONETYPE"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;enumeration value="FAX"/&gt;
 *                       &lt;enumeration value="BUSINESS"/&gt;
 *                       &lt;enumeration value="MOBILE"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="RightsHolderContactEmail" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RightsHolderContactTYPE", propOrder = {
    "rightsHolderContactDesignations",
    "rightsHolderContactAddresses",
    "rightsHolderContactPhones",
    "rightsHolderContactEmails"
})
public class RightsHolderContactTYPE
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "RightsHolderContactDesignation")
    protected List<Object> rightsHolderContactDesignations;
    @XmlElement(name = "RightsHolderContactAddress")
    protected List<Object> rightsHolderContactAddresses;
    @XmlElement(name = "RightsHolderContactPhone")
    protected List<RightsHolderContactTYPE.RightsHolderContactPhone> rightsHolderContactPhones;
    @XmlElement(name = "RightsHolderContactEmail")
    protected List<Object> rightsHolderContactEmails;

    /**
     * Gets the value of the rightsHolderContactDesignations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsHolderContactDesignations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsHolderContactDesignations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRightsHolderContactDesignations() {
        if (rightsHolderContactDesignations == null) {
            rightsHolderContactDesignations = new ArrayList<Object>();
        }
        return this.rightsHolderContactDesignations;
    }

    /**
     * Gets the value of the rightsHolderContactAddresses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsHolderContactAddresses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsHolderContactAddresses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRightsHolderContactAddresses() {
        if (rightsHolderContactAddresses == null) {
            rightsHolderContactAddresses = new ArrayList<Object>();
        }
        return this.rightsHolderContactAddresses;
    }

    /**
     * Gets the value of the rightsHolderContactPhones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsHolderContactPhones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsHolderContactPhones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RightsHolderContactTYPE.RightsHolderContactPhone }
     * 
     * 
     */
    public List<RightsHolderContactTYPE.RightsHolderContactPhone> getRightsHolderContactPhones() {
        if (rightsHolderContactPhones == null) {
            rightsHolderContactPhones = new ArrayList<RightsHolderContactTYPE.RightsHolderContactPhone>();
        }
        return this.rightsHolderContactPhones;
    }

    /**
     * Gets the value of the rightsHolderContactEmails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsHolderContactEmails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsHolderContactEmails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRightsHolderContactEmails() {
        if (rightsHolderContactEmails == null) {
            rightsHolderContactEmails = new ArrayList<Object>();
        }
        return this.rightsHolderContactEmails;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="PHONETYPE"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *             &lt;enumeration value="FAX"/&gt;
     *             &lt;enumeration value="BUSINESS"/&gt;
     *             &lt;enumeration value="MOBILE"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class RightsHolderContactPhone
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "PHONETYPE")
        protected String phonetype;

        /**
         * Recupera il valore della proprietà value.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Imposta il valore della proprietà value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Recupera il valore della proprietà phonetype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPHONETYPE() {
            return phonetype;
        }

        /**
         * Imposta il valore della proprietà phonetype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPHONETYPE(String value) {
            this.phonetype = value;
        }

    }

}
