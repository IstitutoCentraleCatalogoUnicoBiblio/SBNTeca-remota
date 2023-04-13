//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.metsrights;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PermissionsTYPE complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PermissionsTYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="DISCOVER" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="DISPLAY" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="COPY" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="DUPLICATE" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="MODIFY" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="DELETE" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="PRINT" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="OTHER" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="OTHERPERMITTYPE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PermissionsTYPE")
public class PermissionsTYPE
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "DISCOVER")
    protected Boolean discover;
    @XmlAttribute(name = "DISPLAY")
    protected Boolean display;
    @XmlAttribute(name = "COPY")
    protected Boolean copy;
    @XmlAttribute(name = "DUPLICATE")
    protected Boolean duplicate;
    @XmlAttribute(name = "MODIFY")
    protected Boolean modify;
    @XmlAttribute(name = "DELETE")
    protected Boolean delete;
    @XmlAttribute(name = "PRINT")
    protected Boolean print;
    @XmlAttribute(name = "OTHER")
    protected Boolean other;
    @XmlAttribute(name = "OTHERPERMITTYPE")
    protected String otherpermittype;

    /**
     * Recupera il valore della proprietà discover.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDISCOVER() {
        return discover;
    }

    /**
     * Imposta il valore della proprietà discover.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDISCOVER(Boolean value) {
        this.discover = value;
    }

    /**
     * Recupera il valore della proprietà display.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDISPLAY() {
        return display;
    }

    /**
     * Imposta il valore della proprietà display.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDISPLAY(Boolean value) {
        this.display = value;
    }

    /**
     * Recupera il valore della proprietà copy.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCOPY() {
        return copy;
    }

    /**
     * Imposta il valore della proprietà copy.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCOPY(Boolean value) {
        this.copy = value;
    }

    /**
     * Recupera il valore della proprietà duplicate.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDUPLICATE() {
        return duplicate;
    }

    /**
     * Imposta il valore della proprietà duplicate.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDUPLICATE(Boolean value) {
        this.duplicate = value;
    }

    /**
     * Recupera il valore della proprietà modify.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMODIFY() {
        return modify;
    }

    /**
     * Imposta il valore della proprietà modify.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMODIFY(Boolean value) {
        this.modify = value;
    }

    /**
     * Recupera il valore della proprietà delete.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDELETE() {
        return delete;
    }

    /**
     * Imposta il valore della proprietà delete.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDELETE(Boolean value) {
        this.delete = value;
    }

    /**
     * Recupera il valore della proprietà print.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPRINT() {
        return print;
    }

    /**
     * Imposta il valore della proprietà print.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPRINT(Boolean value) {
        this.print = value;
    }

    /**
     * Recupera il valore della proprietà other.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOTHER() {
        return other;
    }

    /**
     * Imposta il valore della proprietà other.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOTHER(Boolean value) {
        this.other = value;
    }

    /**
     * Recupera il valore della proprietà otherpermittype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTHERPERMITTYPE() {
        return otherpermittype;
    }

    /**
     * Imposta il valore della proprietà otherpermittype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTHERPERMITTYPE(String value) {
        this.otherpermittype = value;
    }

}
