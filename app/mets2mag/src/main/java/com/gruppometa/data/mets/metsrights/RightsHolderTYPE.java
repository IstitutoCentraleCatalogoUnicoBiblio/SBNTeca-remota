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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 *  Characteristics of person or institution holding some rights to a given
 *         digital asset or part thereof. Has two attributes "RIGHTSHOLDERID" used to uniquely identify
 *         the rights holder, and CONTEXTIDS which provides a way to specifically link one or more
 *         context situations as described within the CONTEXT element. 
 * 
 * <p>Classe Java per RightsHolderTYPE complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RightsHolderTYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RightsHolderName" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RightsHolderComments" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RightsHolderContact" type="{http://cosimo.stanford.edu/sdr/metsrights/}RightsHolderContactTYPE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="RIGHTSHOLDERID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="CONTEXTIDS" type="{http://www.w3.org/2001/XMLSchema}IDREFS" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RightsHolderTYPE", propOrder = {
    "rightsHolderNames",
    "rightsHolderComments",
    "rightsHolderContacts"
})
public class RightsHolderTYPE
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "RightsHolderName")
    protected List<Object> rightsHolderNames;
    @XmlElement(name = "RightsHolderComments")
    protected List<Object> rightsHolderComments;
    @XmlElement(name = "RightsHolderContact")
    protected List<RightsHolderContactTYPE> rightsHolderContacts;
    @XmlAttribute(name = "RIGHTSHOLDERID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String rightsholderid;
    @XmlAttribute(name = "CONTEXTIDS")
    @XmlIDREF
    @XmlSchemaType(name = "IDREFS")
    protected List<Object> contextids;

    /**
     * Gets the value of the rightsHolderNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsHolderNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsHolderNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRightsHolderNames() {
        if (rightsHolderNames == null) {
            rightsHolderNames = new ArrayList<Object>();
        }
        return this.rightsHolderNames;
    }

    /**
     * Gets the value of the rightsHolderComments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsHolderComments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsHolderComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRightsHolderComments() {
        if (rightsHolderComments == null) {
            rightsHolderComments = new ArrayList<Object>();
        }
        return this.rightsHolderComments;
    }

    /**
     * Gets the value of the rightsHolderContacts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsHolderContacts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsHolderContacts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RightsHolderContactTYPE }
     * 
     * 
     */
    public List<RightsHolderContactTYPE> getRightsHolderContacts() {
        if (rightsHolderContacts == null) {
            rightsHolderContacts = new ArrayList<RightsHolderContactTYPE>();
        }
        return this.rightsHolderContacts;
    }

    /**
     * Recupera il valore della proprietà rightsholderid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRIGHTSHOLDERID() {
        return rightsholderid;
    }

    /**
     * Imposta il valore della proprietà rightsholderid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRIGHTSHOLDERID(String value) {
        this.rightsholderid = value;
    }

    /**
     * Gets the value of the contextids property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contextids property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCONTEXTIDS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getCONTEXTIDS() {
        if (contextids == null) {
            contextids = new ArrayList<Object>();
        }
        return this.contextids;
    }

}
