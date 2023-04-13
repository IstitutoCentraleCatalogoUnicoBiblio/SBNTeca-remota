//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import com.gruppometa.data.mets.audiomd.AudioType;
import com.gruppometa.data.mets.metsrights.RightsDeclarationMD;
import com.gruppometa.data.mets.mix.Mix;
import com.gruppometa.data.mets.niso.Dimensions;
import com.gruppometa.data.mets.videomd.VideoType;
import com.gruppometa.data.mods.Mods;


/**
 * mdSecType: Complex Type for Metadata Sections
 * 			A generic framework for pointing to/including metadata within a METS document, a la Warwick Framework.
 * 			
 * 
 * <p>Classe Java per mdSecType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="mdSecType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="mdRef" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attGroup ref="{http://www.loc.gov/METS/}LOCATION"/&gt;
 *                 &lt;attGroup ref="{http://www.loc.gov/METS/}FILECORE"/&gt;
 *                 &lt;attGroup ref="{http://www.w3.org/1999/xlink}simpleLink"/&gt;
 *                 &lt;attGroup ref="{http://www.loc.gov/METS/}METADATA"/&gt;
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;attribute name="LABEL" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="XPTR" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="mdWrap" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;choice&gt;
 *                   &lt;element name="binData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *                   &lt;element name="xmlData" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;choice&gt;
 *                               &lt;element ref="{http://www.niso.org/pdfs/DataDict.pdf}dimensions" minOccurs="0"/&gt;
 *                               &lt;element ref="{http://www.loc.gov/mix/v20}mix" minOccurs="0"/&gt;
 *                               &lt;element ref="{http://www.loc.gov/audioMD/}AUDIOMD" minOccurs="0"/&gt;
 *                               &lt;element ref="{http://www.loc.gov/videoMD/}VIDEOMD" minOccurs="0"/&gt;
 *                               &lt;element ref="{http://www.loc.gov/mods/v3}mods" minOccurs="0"/&gt;
 *                               &lt;element ref="{http://cosimo.stanford.edu/sdr/metsrights/}RightsDeclarationMD" minOccurs="0"/&gt;
 *                             &lt;/choice&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/choice&gt;
 *                 &lt;attGroup ref="{http://www.loc.gov/METS/}FILECORE"/&gt;
 *                 &lt;attGroup ref="{http://www.loc.gov/METS/}METADATA"/&gt;
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;attribute name="LABEL" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/all&gt;
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="GROUPID" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ADMID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" /&gt;
 *       &lt;attribute name="CREATED" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *       &lt;attribute name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mdSecType", propOrder = {

})
public class MdSecType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected MdSecType.MdRef mdRef;
    protected MdSecType.MdWrap mdWrap;
    @XmlAttribute(name = "ID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "GROUPID")
    protected String groupid;
    @XmlAttribute(name = "ADMID")
    @XmlIDREF
    @XmlSchemaType(name = "IDREFS")
    protected List<Object> admids;
    @XmlAttribute(name = "CREATED")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar created;
    @XmlAttribute(name = "STATUS")
    protected String status;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Recupera il valore della proprietà mdRef.
     * 
     * @return
     *     possible object is
     *     {@link MdSecType.MdRef }
     *     
     */
    public MdSecType.MdRef getMdRef() {
        return mdRef;
    }

    /**
     * Imposta il valore della proprietà mdRef.
     * 
     * @param value
     *     allowed object is
     *     {@link MdSecType.MdRef }
     *     
     */
    public void setMdRef(MdSecType.MdRef value) {
        this.mdRef = value;
    }

    /**
     * Recupera il valore della proprietà mdWrap.
     * 
     * @return
     *     possible object is
     *     {@link MdSecType.MdWrap }
     *     
     */
    public MdSecType.MdWrap getMdWrap() {
        return mdWrap;
    }

    /**
     * Imposta il valore della proprietà mdWrap.
     * 
     * @param value
     *     allowed object is
     *     {@link MdSecType.MdWrap }
     *     
     */
    public void setMdWrap(MdSecType.MdWrap value) {
        this.mdWrap = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà groupid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGROUPID() {
        return groupid;
    }

    /**
     * Imposta il valore della proprietà groupid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGROUPID(String value) {
        this.groupid = value;
    }

    /**
     * Gets the value of the admids property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the admids property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getADMIDS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getADMIDS() {
        if (admids == null) {
            admids = new ArrayList<Object>();
        }
        return this.admids;
    }

    /**
     * Recupera il valore della proprietà created.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCREATED() {
        return created;
    }

    /**
     * Imposta il valore della proprietà created.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCREATED(XMLGregorianCalendar value) {
        this.created = value;
    }

    /**
     * Recupera il valore della proprietà status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUS() {
        return status;
    }

    /**
     * Imposta il valore della proprietà status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUS(String value) {
        this.status = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
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
     *       &lt;attGroup ref="{http://www.loc.gov/METS/}LOCATION"/&gt;
     *       &lt;attGroup ref="{http://www.loc.gov/METS/}FILECORE"/&gt;
     *       &lt;attGroup ref="{http://www.w3.org/1999/xlink}simpleLink"/&gt;
     *       &lt;attGroup ref="{http://www.loc.gov/METS/}METADATA"/&gt;
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute name="LABEL" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="XPTR" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class MdRef
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlAttribute(name = "ID")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;
        @XmlAttribute(name = "LABEL")
        protected String label;
        @XmlAttribute(name = "XPTR")
        protected String xptr;
        @XmlAttribute(name = "LOCTYPE", required = true)
        protected String loctype;
        @XmlAttribute(name = "OTHERLOCTYPE")
        protected String otherloctype;
        @XmlAttribute(name = "MIMETYPE")
        protected String mimetype;
        @XmlAttribute(name = "SIZE")
        protected Long size;
        @XmlAttribute(name = "CREATED")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar created;
        @XmlAttribute(name = "CHECKSUM")
        protected String checksum;
        @XmlAttribute(name = "CHECKSUMTYPE")
        protected String checksumtype;
        @XmlAttribute(name = "type", namespace = "http://www.w3.org/1999/xlink")
        protected String type;
        @XmlAttribute(name = "href", namespace = "http://www.w3.org/1999/xlink")
        @XmlSchemaType(name = "anyURI")
        protected String href;
        @XmlAttribute(name = "role", namespace = "http://www.w3.org/1999/xlink")
        protected String role;
        @XmlAttribute(name = "arcrole", namespace = "http://www.w3.org/1999/xlink")
        protected String arcrole;
        @XmlAttribute(name = "title", namespace = "http://www.w3.org/1999/xlink")
        protected String title;
        @XmlAttribute(name = "show", namespace = "http://www.w3.org/1999/xlink")
        protected String show;
        @XmlAttribute(name = "actuate", namespace = "http://www.w3.org/1999/xlink")
        protected String actuate;
        @XmlAttribute(name = "MDTYPE", required = true)
        protected String mdtype;
        @XmlAttribute(name = "OTHERMDTYPE")
        protected String othermdtype;
        @XmlAttribute(name = "MDTYPEVERSION")
        protected String mdtypeversion;

        /**
         * Recupera il valore della proprietà id.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getID() {
            return id;
        }

        /**
         * Imposta il valore della proprietà id.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Recupera il valore della proprietà label.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLABEL() {
            return label;
        }

        /**
         * Imposta il valore della proprietà label.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLABEL(String value) {
            this.label = value;
        }

        /**
         * Recupera il valore della proprietà xptr.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXPTR() {
            return xptr;
        }

        /**
         * Imposta il valore della proprietà xptr.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXPTR(String value) {
            this.xptr = value;
        }

        /**
         * Recupera il valore della proprietà loctype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLOCTYPE() {
            return loctype;
        }

        /**
         * Imposta il valore della proprietà loctype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLOCTYPE(String value) {
            this.loctype = value;
        }

        /**
         * Recupera il valore della proprietà otherloctype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOTHERLOCTYPE() {
            return otherloctype;
        }

        /**
         * Imposta il valore della proprietà otherloctype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOTHERLOCTYPE(String value) {
            this.otherloctype = value;
        }

        /**
         * Recupera il valore della proprietà mimetype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMIMETYPE() {
            return mimetype;
        }

        /**
         * Imposta il valore della proprietà mimetype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMIMETYPE(String value) {
            this.mimetype = value;
        }

        /**
         * Recupera il valore della proprietà size.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getSIZE() {
            return size;
        }

        /**
         * Imposta il valore della proprietà size.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setSIZE(Long value) {
            this.size = value;
        }

        /**
         * Recupera il valore della proprietà created.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCREATED() {
            return created;
        }

        /**
         * Imposta il valore della proprietà created.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCREATED(XMLGregorianCalendar value) {
            this.created = value;
        }

        /**
         * Recupera il valore della proprietà checksum.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCHECKSUM() {
            return checksum;
        }

        /**
         * Imposta il valore della proprietà checksum.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCHECKSUM(String value) {
            this.checksum = value;
        }

        /**
         * Recupera il valore della proprietà checksumtype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCHECKSUMTYPE() {
            return checksumtype;
        }

        /**
         * Imposta il valore della proprietà checksumtype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCHECKSUMTYPE(String value) {
            this.checksumtype = value;
        }

        /**
         * Recupera il valore della proprietà type.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            if (type == null) {
                return "simple";
            } else {
                return type;
            }
        }

        /**
         * Imposta il valore della proprietà type.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Recupera il valore della proprietà href.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHref() {
            return href;
        }

        /**
         * Imposta il valore della proprietà href.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHref(String value) {
            this.href = value;
        }

        /**
         * Recupera il valore della proprietà role.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRole() {
            return role;
        }

        /**
         * Imposta il valore della proprietà role.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRole(String value) {
            this.role = value;
        }

        /**
         * Recupera il valore della proprietà arcrole.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getArcrole() {
            return arcrole;
        }

        /**
         * Imposta il valore della proprietà arcrole.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setArcrole(String value) {
            this.arcrole = value;
        }

        /**
         * Recupera il valore della proprietà title.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitle() {
            return title;
        }

        /**
         * Imposta il valore della proprietà title.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitle(String value) {
            this.title = value;
        }

        /**
         * Recupera il valore della proprietà show.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getShow() {
            return show;
        }

        /**
         * Imposta il valore della proprietà show.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setShow(String value) {
            this.show = value;
        }

        /**
         * Recupera il valore della proprietà actuate.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getActuate() {
            return actuate;
        }

        /**
         * Imposta il valore della proprietà actuate.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setActuate(String value) {
            this.actuate = value;
        }

        /**
         * Recupera il valore della proprietà mdtype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMDTYPE() {
            return mdtype;
        }

        /**
         * Imposta il valore della proprietà mdtype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMDTYPE(String value) {
            this.mdtype = value;
        }

        /**
         * Recupera il valore della proprietà othermdtype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOTHERMDTYPE() {
            return othermdtype;
        }

        /**
         * Imposta il valore della proprietà othermdtype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOTHERMDTYPE(String value) {
            this.othermdtype = value;
        }

        /**
         * Recupera il valore della proprietà mdtypeversion.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMDTYPEVERSION() {
            return mdtypeversion;
        }

        /**
         * Imposta il valore della proprietà mdtypeversion.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMDTYPEVERSION(String value) {
            this.mdtypeversion = value;
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
     *       &lt;choice&gt;
     *         &lt;element name="binData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
     *         &lt;element name="xmlData" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;choice&gt;
     *                     &lt;element ref="{http://www.niso.org/pdfs/DataDict.pdf}dimensions" minOccurs="0"/&gt;
     *                     &lt;element ref="{http://www.loc.gov/mix/v20}mix" minOccurs="0"/&gt;
     *                     &lt;element ref="{http://www.loc.gov/audioMD/}AUDIOMD" minOccurs="0"/&gt;
     *                     &lt;element ref="{http://www.loc.gov/videoMD/}VIDEOMD" minOccurs="0"/&gt;
     *                     &lt;element ref="{http://www.loc.gov/mods/v3}mods" minOccurs="0"/&gt;
     *                     &lt;element ref="{http://cosimo.stanford.edu/sdr/metsrights/}RightsDeclarationMD" minOccurs="0"/&gt;
     *                   &lt;/choice&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/choice&gt;
     *       &lt;attGroup ref="{http://www.loc.gov/METS/}FILECORE"/&gt;
     *       &lt;attGroup ref="{http://www.loc.gov/METS/}METADATA"/&gt;
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute name="LABEL" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "xmlData",
        "binData"
    })
    public static class MdWrap
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        protected MdSecType.MdWrap.XmlData xmlData;
        protected byte[] binData;
        @XmlAttribute(name = "ID")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;
        @XmlAttribute(name = "LABEL")
        protected String label;
        @XmlAttribute(name = "MIMETYPE")
        protected String mimetype;
        @XmlAttribute(name = "SIZE")
        protected Long size;
        @XmlAttribute(name = "CREATED")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar created;
        @XmlAttribute(name = "CHECKSUM")
        protected String checksum;
        @XmlAttribute(name = "CHECKSUMTYPE")
        protected String checksumtype;
        @XmlAttribute(name = "MDTYPE", required = true)
        protected String mdtype;
        @XmlAttribute(name = "OTHERMDTYPE")
        protected String othermdtype;
        @XmlAttribute(name = "MDTYPEVERSION")
        protected String mdtypeversion;

        /**
         * Recupera il valore della proprietà xmlData.
         * 
         * @return
         *     possible object is
         *     {@link MdSecType.MdWrap.XmlData }
         *     
         */
        public MdSecType.MdWrap.XmlData getXmlData() {
            return xmlData;
        }

        /**
         * Imposta il valore della proprietà xmlData.
         * 
         * @param value
         *     allowed object is
         *     {@link MdSecType.MdWrap.XmlData }
         *     
         */
        public void setXmlData(MdSecType.MdWrap.XmlData value) {
            this.xmlData = value;
        }

        /**
         * Recupera il valore della proprietà binData.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getBinData() {
            return binData;
        }

        /**
         * Imposta il valore della proprietà binData.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setBinData(byte[] value) {
            this.binData = value;
        }

        /**
         * Recupera il valore della proprietà id.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getID() {
            return id;
        }

        /**
         * Imposta il valore della proprietà id.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Recupera il valore della proprietà label.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLABEL() {
            return label;
        }

        /**
         * Imposta il valore della proprietà label.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLABEL(String value) {
            this.label = value;
        }

        /**
         * Recupera il valore della proprietà mimetype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMIMETYPE() {
            return mimetype;
        }

        /**
         * Imposta il valore della proprietà mimetype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMIMETYPE(String value) {
            this.mimetype = value;
        }

        /**
         * Recupera il valore della proprietà size.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getSIZE() {
            return size;
        }

        /**
         * Imposta il valore della proprietà size.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setSIZE(Long value) {
            this.size = value;
        }

        /**
         * Recupera il valore della proprietà created.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCREATED() {
            return created;
        }

        /**
         * Imposta il valore della proprietà created.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCREATED(XMLGregorianCalendar value) {
            this.created = value;
        }

        /**
         * Recupera il valore della proprietà checksum.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCHECKSUM() {
            return checksum;
        }

        /**
         * Imposta il valore della proprietà checksum.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCHECKSUM(String value) {
            this.checksum = value;
        }

        /**
         * Recupera il valore della proprietà checksumtype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCHECKSUMTYPE() {
            return checksumtype;
        }

        /**
         * Imposta il valore della proprietà checksumtype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCHECKSUMTYPE(String value) {
            this.checksumtype = value;
        }

        /**
         * Recupera il valore della proprietà mdtype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMDTYPE() {
            return mdtype;
        }

        /**
         * Imposta il valore della proprietà mdtype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMDTYPE(String value) {
            this.mdtype = value;
        }

        /**
         * Recupera il valore della proprietà othermdtype.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOTHERMDTYPE() {
            return othermdtype;
        }

        /**
         * Imposta il valore della proprietà othermdtype.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOTHERMDTYPE(String value) {
            this.othermdtype = value;
        }

        /**
         * Recupera il valore della proprietà mdtypeversion.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMDTYPEVERSION() {
            return mdtypeversion;
        }

        /**
         * Imposta il valore della proprietà mdtypeversion.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMDTYPEVERSION(String value) {
            this.mdtypeversion = value;
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
         *         &lt;choice&gt;
         *           &lt;element ref="{http://www.niso.org/pdfs/DataDict.pdf}dimensions" minOccurs="0"/&gt;
         *           &lt;element ref="{http://www.loc.gov/mix/v20}mix" minOccurs="0"/&gt;
         *           &lt;element ref="{http://www.loc.gov/audioMD/}AUDIOMD" minOccurs="0"/&gt;
         *           &lt;element ref="{http://www.loc.gov/videoMD/}VIDEOMD" minOccurs="0"/&gt;
         *           &lt;element ref="{http://www.loc.gov/mods/v3}mods" minOccurs="0"/&gt;
         *           &lt;element ref="{http://cosimo.stanford.edu/sdr/metsrights/}RightsDeclarationMD" minOccurs="0"/&gt;
         *         &lt;/choice&gt;
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
            "rightsDeclarationMD",
            "mods",
            "videomd",
            "audiomd",
            "mix",
            "dimensions"
        })
        public static class XmlData
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "RightsDeclarationMD", namespace = "http://cosimo.stanford.edu/sdr/metsrights/")
            protected RightsDeclarationMD rightsDeclarationMD;
            @XmlElement(namespace = "http://www.loc.gov/mods/v3")
            protected Mods mods;
            @XmlElement(name = "VIDEOMD", namespace = "http://www.loc.gov/videoMD/")
            protected VideoType videomd;
            @XmlElement(name = "AUDIOMD", namespace = "http://www.loc.gov/audioMD/")
            protected AudioType audiomd;
            @XmlElement(namespace = "http://www.loc.gov/mix/v20")
            protected Mix mix;
            @XmlElement(namespace = "http://www.niso.org/pdfs/DataDict.pdf")
            protected Dimensions dimensions;

            /**
             * Recupera il valore della proprietà rightsDeclarationMD.
             * 
             * @return
             *     possible object is
             *     {@link RightsDeclarationMD }
             *     
             */
            public RightsDeclarationMD getRightsDeclarationMD() {
                return rightsDeclarationMD;
            }

            /**
             * Imposta il valore della proprietà rightsDeclarationMD.
             * 
             * @param value
             *     allowed object is
             *     {@link RightsDeclarationMD }
             *     
             */
            public void setRightsDeclarationMD(RightsDeclarationMD value) {
                this.rightsDeclarationMD = value;
            }

            /**
             * Recupera il valore della proprietà mods.
             * 
             * @return
             *     possible object is
             *     {@link Mods }
             *     
             */
            public Mods getMods() {
                return mods;
            }

            /**
             * Imposta il valore della proprietà mods.
             * 
             * @param value
             *     allowed object is
             *     {@link Mods }
             *     
             */
            public void setMods(Mods value) {
                this.mods = value;
            }

            /**
             * Recupera il valore della proprietà videomd.
             * 
             * @return
             *     possible object is
             *     {@link VideoType }
             *     
             */
            public VideoType getVIDEOMD() {
                return videomd;
            }

            /**
             * Imposta il valore della proprietà videomd.
             * 
             * @param value
             *     allowed object is
             *     {@link VideoType }
             *     
             */
            public void setVIDEOMD(VideoType value) {
                this.videomd = value;
            }

            /**
             * Recupera il valore della proprietà audiomd.
             * 
             * @return
             *     possible object is
             *     {@link AudioType }
             *     
             */
            public AudioType getAUDIOMD() {
                return audiomd;
            }

            /**
             * Imposta il valore della proprietà audiomd.
             * 
             * @param value
             *     allowed object is
             *     {@link AudioType }
             *     
             */
            public void setAUDIOMD(AudioType value) {
                this.audiomd = value;
            }

            /**
             * Recupera il valore della proprietà mix.
             * 
             * @return
             *     possible object is
             *     {@link Mix }
             *     
             */
            public Mix getMix() {
                return mix;
            }

            /**
             * Imposta il valore della proprietà mix.
             * 
             * @param value
             *     allowed object is
             *     {@link Mix }
             *     
             */
            public void setMix(Mix value) {
                this.mix = value;
            }

            /**
             * Recupera il valore della proprietà dimensions.
             * 
             * @return
             *     possible object is
             *     {@link Dimensions }
             *     
             */
            public Dimensions getDimensions() {
                return dimensions;
            }

            /**
             * Imposta il valore della proprietà dimensions.
             * 
             * @param value
             *     allowed object is
             *     {@link Dimensions }
             *     
             */
            public void setDimensions(Dimensions value) {
                this.dimensions = value;
            }

        }

    }

}
