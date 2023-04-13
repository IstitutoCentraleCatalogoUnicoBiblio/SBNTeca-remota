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


/**
 * <p>Classe Java per ConstraintsTYPE complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ConstraintsTYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ConstraintDescription" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="CONSTRAINTTYPE"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="QUALITY"/&gt;
 *             &lt;enumeration value="FORMAT"/&gt;
 *             &lt;enumeration value="UNIT"/&gt;
 *             &lt;enumeration value="WATERMARK"/&gt;
 *             &lt;enumeration value="PAYMENT"/&gt;
 *             &lt;enumeration value="COUNT"/&gt;
 *             &lt;enumeration value="RE-USE"/&gt;
 *             &lt;enumeration value="ATTRIBUTION"/&gt;
 *             &lt;enumeration value="TIME"/&gt;
 *             &lt;enumeration value="TRANSFERPERMISSIONS"/&gt;
 *             &lt;enumeration value="OTHER"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="OTHERCONSTRAINTTYPE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConstraintsTYPE", propOrder = {
    "constraintDescriptions"
})
public class ConstraintsTYPE
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ConstraintDescription")
    protected List<Object> constraintDescriptions;
    @XmlAttribute(name = "CONSTRAINTTYPE")
    protected String constrainttype;
    @XmlAttribute(name = "OTHERCONSTRAINTTYPE")
    protected String otherconstrainttype;

    /**
     * Gets the value of the constraintDescriptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constraintDescriptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstraintDescriptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getConstraintDescriptions() {
        if (constraintDescriptions == null) {
            constraintDescriptions = new ArrayList<Object>();
        }
        return this.constraintDescriptions;
    }

    /**
     * Recupera il valore della proprietà constrainttype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONSTRAINTTYPE() {
        return constrainttype;
    }

    /**
     * Imposta il valore della proprietà constrainttype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONSTRAINTTYPE(String value) {
        this.constrainttype = value;
    }

    /**
     * Recupera il valore della proprietà otherconstrainttype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTHERCONSTRAINTTYPE() {
        return otherconstrainttype;
    }

    /**
     * Imposta il valore della proprietà otherconstrainttype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTHERCONSTRAINTTYPE(String value) {
        this.otherconstrainttype = value;
    }

}
