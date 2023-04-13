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
 * The "Context" element describes who has what permissions and contraints
 *         within a specific set of circumstances. "Context" includes four attributes, " CONTEXTID" an
 *         optional attribute which can be used to assign a unique identifier for the circumstance
 *         described, "RIGHTSHOLDERIDS", an optional attribute which provides a means to specifically
 *         list the unique identifiers of one or more RightsHolders who are involved in the Context
 *         scenario to be described, "CONTEXTCLASS", and "OTHERCONTEXTTYPE". By means of the required
 *         "CONTEXTCLASS" attribute, the group is defined to which a given context relates. Enumerated
 *         values for this attribute include "ACADEMIC USER", "GENERAL PUBLIC","REPOSITORY MGR",
 *         "MANAGED GRP", "INSTITUTIONAL AFFILIATE", "OTHER". Local extensions to "OTHER" context
 *         classes can be described in the "OTHERCONTEXTTYPE" attribute, as desired. "Context" also
 *         contains three elements which further describe a specified context. The element "UserName"
 *         allows the naming of the typical user or group who would use the digital object within the
 *         described context. The kind of user being named can be described by means of the attribute
 *         "USERTYPE" which has values "INDIVIDUAL", "GROUP", "BOTH", or "UNDEFINED". The element
 *         "Permissions" describes a set of uses to which a digital object or part thereof can be put
 *         as defined by the rights holder in a given context. "Permissions" has nine attributes which
 *         describe the type of permissions including "DISCOVER", "DISPLAY", "COPY", "DUPLICATE",
 *         "MODIFY", "DELETE", "PRINT", "OTHER", and "OTHERPERMITTYPE" which allows the addition of
 *         local types of permissions not included in the general list. Any restrictions that need to
 *         be placed upon a set of permissions are described by the use of the "Constraints" element.
 *         The "Constraints" element contains a "ConstraintDescription" sub-element in which a
 *         narrative explanation can be given about the constraint. The "Constraints" element has an
 *         attribute "CONSTRAINTTYPE" which characterize the type of restrictions imposed upon the
 *         users within a given context. Enumerated values for the CONSTRAINTTYPE attributes include
 *         "QUALITY", "FORMAT", "UNIT", "WATERMARK", "PAYMENT", "COUNT", "ATTRIBUTION", "RE-USE",
 *         "TIME", "TRANSFERPERMISSIONS", "OTHER". Local extensions to "OTHER" can be described in the
 *         "OTHERCONSTRAINTTYPE" attribute, as desired. 
 * 
 * <p>Classe Java per ContextTYPE complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ContextTYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UserName" type="{http://cosimo.stanford.edu/sdr/metsrights/}UserNameTYPE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Permissions" type="{http://cosimo.stanford.edu/sdr/metsrights/}PermissionsTYPE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Constraints" type="{http://cosimo.stanford.edu/sdr/metsrights/}ConstraintsTYPE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="CONTEXTID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="RIGHTSHOLDERIDS" type="{http://www.w3.org/2001/XMLSchema}IDREFS" /&gt;
 *       &lt;attribute name="CONTEXTCLASS" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="ACADEMIC USER"/&gt;
 *             &lt;enumeration value="GENERAL PUBLIC"/&gt;
 *             &lt;enumeration value="REPOSITORY MGR"/&gt;
 *             &lt;enumeration value="MANAGED GRP"/&gt;
 *             &lt;enumeration value="INSTITUTIONAL AFFILIATE"/&gt;
 *             &lt;enumeration value="OTHER"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="OTHERCONTEXTTYPE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContextTYPE", propOrder = {
    "userNames",
    "permissions",
    "constraints"
})
public class ContextTYPE
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "UserName")
    protected List<UserNameTYPE> userNames;
    @XmlElement(name = "Permissions")
    protected List<PermissionsTYPE> permissions;
    @XmlElement(name = "Constraints")
    protected List<ConstraintsTYPE> constraints;
    @XmlAttribute(name = "CONTEXTID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String contextid;
    @XmlAttribute(name = "RIGHTSHOLDERIDS")
    @XmlIDREF
    @XmlSchemaType(name = "IDREFS")
    protected List<Object> rightsholderids;
    @XmlAttribute(name = "CONTEXTCLASS", required = true)
    protected String contextclass;
    @XmlAttribute(name = "OTHERCONTEXTTYPE")
    protected String othercontexttype;

    /**
     * Gets the value of the userNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserNameTYPE }
     * 
     * 
     */
    public List<UserNameTYPE> getUserNames() {
        if (userNames == null) {
            userNames = new ArrayList<UserNameTYPE>();
        }
        return this.userNames;
    }

    /**
     * Gets the value of the permissions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permissions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermissions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PermissionsTYPE }
     * 
     * 
     */
    public List<PermissionsTYPE> getPermissions() {
        if (permissions == null) {
            permissions = new ArrayList<PermissionsTYPE>();
        }
        return this.permissions;
    }

    /**
     * Gets the value of the constraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConstraintsTYPE }
     * 
     * 
     */
    public List<ConstraintsTYPE> getConstraints() {
        if (constraints == null) {
            constraints = new ArrayList<ConstraintsTYPE>();
        }
        return this.constraints;
    }

    /**
     * Recupera il valore della proprietà contextid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTEXTID() {
        return contextid;
    }

    /**
     * Imposta il valore della proprietà contextid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTEXTID(String value) {
        this.contextid = value;
    }

    /**
     * Gets the value of the rightsholderids property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rightsholderids property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRIGHTSHOLDERIDS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getRIGHTSHOLDERIDS() {
        if (rightsholderids == null) {
            rightsholderids = new ArrayList<Object>();
        }
        return this.rightsholderids;
    }

    /**
     * Recupera il valore della proprietà contextclass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTEXTCLASS() {
        return contextclass;
    }

    /**
     * Imposta il valore della proprietà contextclass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTEXTCLASS(String value) {
        this.contextclass = value;
    }

    /**
     * Recupera il valore della proprietà othercontexttype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTHERCONTEXTTYPE() {
        return othercontexttype;
    }

    /**
     * Imposta il valore della proprietà othercontexttype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTHERCONTEXTTYPE(String value) {
        this.othercontexttype = value;
    }

}
