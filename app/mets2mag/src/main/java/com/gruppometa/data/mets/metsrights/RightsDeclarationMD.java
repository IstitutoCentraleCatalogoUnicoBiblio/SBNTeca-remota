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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RightsDeclareMDTYPE complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RightsDeclareMDTYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RightsDeclaration" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RightsHolder" type="{http://cosimo.stanford.edu/sdr/metsrights/}RightsHolderTYPE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Context" type="{http://cosimo.stanford.edu/sdr/metsrights/}ContextTYPE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="RIGHTSDECID" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="RIGHTSCATEGORY"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="COPYRIGHTED"/&gt;
 *             &lt;enumeration value="LICENSED"/&gt;
 *             &lt;enumeration value="PUBLIC DOMAIN"/&gt;
 *             &lt;enumeration value="CONTRACTUAL"/&gt;
 *             &lt;enumeration value="OTHER"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="OTHERCATEGORYTYPE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RightsDeclareMDTYPE", propOrder = {
    "rightsDeclarations",
    "rightsHolders",
    "contexts"
})
@XmlRootElement(name = "RightsDeclarationMD")
public class RightsDeclarationMD
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "RightsDeclaration")
    protected List<Object> rightsDeclarations;
    @XmlElement(name = "RightsHolder")
    protected List<RightsHolderTYPE> rightsHolders;
    @XmlElement(name = "Context")
    protected List<ContextTYPE> contexts;
    @XmlAttribute(name = "RIGHTSDECID")
    protected String rightsdecid;
    @XmlAttribute(name = "RIGHTSCATEGORY")
    protected String rightscategory;
    @XmlAttribute(name = "OTHERCATEGORYTYPE")
    protected String othercategorytype;

    /**
     * Gets the value of the rightsDeclarations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsDeclarations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsDeclarations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRightsDeclarations() {
        if (rightsDeclarations == null) {
            rightsDeclarations = new ArrayList<Object>();
        }
        return this.rightsDeclarations;
    }

    /**
     * Gets the value of the rightsHolders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsHolders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRightsHolders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RightsHolderTYPE }
     * 
     * 
     */
    public List<RightsHolderTYPE> getRightsHolders() {
        if (rightsHolders == null) {
            rightsHolders = new ArrayList<RightsHolderTYPE>();
        }
        return this.rightsHolders;
    }

    /**
     * Gets the value of the contexts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contexts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContexts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContextTYPE }
     * 
     * 
     */
    public List<ContextTYPE> getContexts() {
        if (contexts == null) {
            contexts = new ArrayList<ContextTYPE>();
        }
        return this.contexts;
    }

    /**
     * Recupera il valore della proprietà rightsdecid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRIGHTSDECID() {
        return rightsdecid;
    }

    /**
     * Imposta il valore della proprietà rightsdecid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRIGHTSDECID(String value) {
        this.rightsdecid = value;
    }

    /**
     * Recupera il valore della proprietà rightscategory.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRIGHTSCATEGORY() {
        return rightscategory;
    }

    /**
     * Imposta il valore della proprietà rightscategory.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRIGHTSCATEGORY(String value) {
        this.rightscategory = value;
    }

    /**
     * Recupera il valore della proprietà othercategorytype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTHERCATEGORYTYPE() {
        return othercategorytype;
    }

    /**
     * Imposta il valore della proprietà othercategorytype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTHERCATEGORYTYPE(String value) {
        this.othercategorytype = value;
    }

}
