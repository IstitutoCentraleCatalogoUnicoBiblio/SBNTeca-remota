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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * structLinkType: Complex Type for Structural Map Linking
 * 				The Structural Map Linking section allows for the specification of hyperlinks between different components of a METS structure delineated in a structural map.  structLink contains a single, repeatable element, smLink.  Each smLink element indicates a hyperlink between two nodes in the structMap.  The structMap nodes recorded in smLink are identified using their XML ID attribute	values.
 * 			
 * 
 * <p>Classe Java per structLinkType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="structLinkType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element name="smLink"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;attribute ref="{http://www.w3.org/1999/xlink}arcrole"/&gt;
 *                 &lt;attribute ref="{http://www.w3.org/1999/xlink}title"/&gt;
 *                 &lt;attribute ref="{http://www.w3.org/1999/xlink}show"/&gt;
 *                 &lt;attribute ref="{http://www.w3.org/1999/xlink}actuate"/&gt;
 *                 &lt;attribute ref="{http://www.w3.org/1999/xlink}to use="required""/&gt;
 *                 &lt;attribute ref="{http://www.w3.org/1999/xlink}from use="required""/&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="smLinkGrp"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="smLocatorLink" maxOccurs="unbounded" minOccurs="2"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attGroup ref="{http://www.w3.org/1999/xlink}locatorLink"/&gt;
 *                           &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="smArcLink" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attGroup ref="{http://www.w3.org/1999/xlink}arcLink"/&gt;
 *                           &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                           &lt;attribute name="ARCTYPE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="ADMID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attGroup ref="{http://www.w3.org/1999/xlink}extendedLink"/&gt;
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;attribute name="ARCLINKORDER" default="unordered"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;enumeration value="ordered"/&gt;
 *                       &lt;enumeration value="unordered"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "structLinkType", propOrder = {
    "smLinksAndSmLinkGrps"
})
@XmlSeeAlso({
    com.gruppometa.data.mets.MetsType.StructLink.class
})
public class StructLinkType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElements({
        @XmlElement(name = "smLink", type = StructLinkType.SmLink.class),
        @XmlElement(name = "smLinkGrp", type = StructLinkType.SmLinkGrp.class)
    })
    protected List<Serializable> smLinksAndSmLinkGrps;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the smLinksAndSmLinkGrps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the smLinksAndSmLinkGrps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSmLinksAndSmLinkGrps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StructLinkType.SmLink }
     * {@link StructLinkType.SmLinkGrp }
     * 
     * 
     */
    public List<Serializable> getSmLinksAndSmLinkGrps() {
        if (smLinksAndSmLinkGrps == null) {
            smLinksAndSmLinkGrps = new ArrayList<Serializable>();
        }
        return this.smLinksAndSmLinkGrps;
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
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute ref="{http://www.w3.org/1999/xlink}arcrole"/&gt;
     *       &lt;attribute ref="{http://www.w3.org/1999/xlink}title"/&gt;
     *       &lt;attribute ref="{http://www.w3.org/1999/xlink}show"/&gt;
     *       &lt;attribute ref="{http://www.w3.org/1999/xlink}actuate"/&gt;
     *       &lt;attribute ref="{http://www.w3.org/1999/xlink}to use="required""/&gt;
     *       &lt;attribute ref="{http://www.w3.org/1999/xlink}from use="required""/&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SmLink implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlAttribute(name = "ID")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;
        @XmlAttribute(name = "arcrole", namespace = "http://www.w3.org/1999/xlink")
        protected String arcrole;
        @XmlAttribute(name = "title", namespace = "http://www.w3.org/1999/xlink")
        protected String title;
        @XmlAttribute(name = "show", namespace = "http://www.w3.org/1999/xlink")
        protected String show;
        @XmlAttribute(name = "actuate", namespace = "http://www.w3.org/1999/xlink")
        protected String actuate;
        @XmlAttribute(name = "to", namespace = "http://www.w3.org/1999/xlink", required = true)
        protected String to;
        @XmlAttribute(name = "from", namespace = "http://www.w3.org/1999/xlink", required = true)
        protected String from;

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
         * 
         * 								 xlink:arcrole - the role of the link, as per the xlink specification.  See http://www.w3.org/TR/xlink/
         * 							
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
         * 
         * 								xlink:title - a title for the link (if needed), as per the xlink specification.  See http://www.w3.org/TR/xlink/
         * 							
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
         * 
         * 								xlink:show - see the xlink specification at http://www.w3.org/TR/xlink/
         * 							
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
         * 
         * 								xlink:actuate - see the xlink specification at http://www.w3.org/TR/xlink/
         * 							
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
         * 
         * 								xlink:to - the value of the label for the element in the structMap you are linking to.
         * 							
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTo() {
            return to;
        }

        /**
         * Imposta il valore della proprietà to.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTo(String value) {
            this.to = value;
        }

        /**
         * 
         * 								xlink:from - the value of the label for the element in the structMap you are linking from.
         * 							
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFrom() {
            return from;
        }

        /**
         * Imposta il valore della proprietà from.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFrom(String value) {
            this.from = value;
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
     *         &lt;element name="smLocatorLink" maxOccurs="unbounded" minOccurs="2"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attGroup ref="{http://www.w3.org/1999/xlink}locatorLink"/&gt;
     *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="smArcLink" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attGroup ref="{http://www.w3.org/1999/xlink}arcLink"/&gt;
     *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *                 &lt;attribute name="ARCTYPE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="ADMID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attGroup ref="{http://www.w3.org/1999/xlink}extendedLink"/&gt;
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute name="ARCLINKORDER" default="unordered"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *             &lt;enumeration value="ordered"/&gt;
     *             &lt;enumeration value="unordered"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "smLocatorLinks",
        "smArcLinks"
    })
    public static class SmLinkGrp implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "smLocatorLink", required = true)
        protected List<StructLinkType.SmLinkGrp.SmLocatorLink> smLocatorLinks;
        @XmlElement(name = "smArcLink", required = true)
        protected List<StructLinkType.SmLinkGrp.SmArcLink> smArcLinks;
        @XmlAttribute(name = "ID")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;
        @XmlAttribute(name = "ARCLINKORDER")
        protected String arclinkorder;
        @XmlAttribute(name = "type", namespace = "http://www.w3.org/1999/xlink")
        protected String type;
        @XmlAttribute(name = "role", namespace = "http://www.w3.org/1999/xlink")
        protected String role;
        @XmlAttribute(name = "title", namespace = "http://www.w3.org/1999/xlink")
        protected String title;

        /**
         * Gets the value of the smLocatorLinks property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the smLocatorLinks property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSmLocatorLinks().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StructLinkType.SmLinkGrp.SmLocatorLink }
         * 
         * 
         */
        public List<StructLinkType.SmLinkGrp.SmLocatorLink> getSmLocatorLinks() {
            if (smLocatorLinks == null) {
                smLocatorLinks = new ArrayList<StructLinkType.SmLinkGrp.SmLocatorLink>();
            }
            return this.smLocatorLinks;
        }

        /**
         * Gets the value of the smArcLinks property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the smArcLinks property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSmArcLinks().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StructLinkType.SmLinkGrp.SmArcLink }
         * 
         * 
         */
        public List<StructLinkType.SmLinkGrp.SmArcLink> getSmArcLinks() {
            if (smArcLinks == null) {
                smArcLinks = new ArrayList<StructLinkType.SmLinkGrp.SmArcLink>();
            }
            return this.smArcLinks;
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
         * Recupera il valore della proprietà arclinkorder.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getARCLINKORDER() {
            if (arclinkorder == null) {
                return "unordered";
            } else {
                return arclinkorder;
            }
        }

        /**
         * Imposta il valore della proprietà arclinkorder.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setARCLINKORDER(String value) {
            this.arclinkorder = value;
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
                return "extended";
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
         * 
         * 										The structMap arc link element <smArcLink> is of xlink:type "arc" It can be used to establish a traversal link between two <div> elements as identified by <smLocatorLink> elements within the same smLinkGrp element. The associated xlink:from and xlink:to attributes identify the from and to sides of the arc link by referencing the xlink:label attribute values on the participating smLocatorLink elements.
         * 									
         * 
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attGroup ref="{http://www.w3.org/1999/xlink}arcLink"/&gt;
         *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
         *       &lt;attribute name="ARCTYPE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="ADMID" type="{http://www.w3.org/2001/XMLSchema}IDREFS" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class SmArcLink
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlAttribute(name = "ID")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String id;
            @XmlAttribute(name = "ARCTYPE")
            protected String arctype;
            @XmlAttribute(name = "ADMID")
            @XmlIDREF
            @XmlSchemaType(name = "IDREFS")
            protected List<Object> admids;
            @XmlAttribute(name = "type", namespace = "http://www.w3.org/1999/xlink")
            protected String type;
            @XmlAttribute(name = "arcrole", namespace = "http://www.w3.org/1999/xlink")
            protected String arcrole;
            @XmlAttribute(name = "title", namespace = "http://www.w3.org/1999/xlink")
            protected String title;
            @XmlAttribute(name = "show", namespace = "http://www.w3.org/1999/xlink")
            protected String show;
            @XmlAttribute(name = "actuate", namespace = "http://www.w3.org/1999/xlink")
            protected String actuate;
            @XmlAttribute(name = "from", namespace = "http://www.w3.org/1999/xlink")
            protected String from;
            @XmlAttribute(name = "to", namespace = "http://www.w3.org/1999/xlink")
            protected String to;

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
             * Recupera il valore della proprietà arctype.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getARCTYPE() {
                return arctype;
            }

            /**
             * Imposta il valore della proprietà arctype.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setARCTYPE(String value) {
                this.arctype = value;
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
             * Recupera il valore della proprietà type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                if (type == null) {
                    return "arc";
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
             * Recupera il valore della proprietà from.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFrom() {
                return from;
            }

            /**
             * Imposta il valore della proprietà from.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFrom(String value) {
                this.from = value;
            }

            /**
             * Recupera il valore della proprietà to.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTo() {
                return to;
            }

            /**
             * Imposta il valore della proprietà to.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTo(String value) {
                this.to = value;
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
         *       &lt;attGroup ref="{http://www.w3.org/1999/xlink}locatorLink"/&gt;
         *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class SmLocatorLink
            implements Serializable
        {

            private final static long serialVersionUID = -1L;
            @XmlAttribute(name = "ID")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String id;
            @XmlAttribute(name = "type", namespace = "http://www.w3.org/1999/xlink")
            protected String type;
            @XmlAttribute(name = "href", namespace = "http://www.w3.org/1999/xlink", required = true)
            @XmlSchemaType(name = "anyURI")
            protected String href;
            @XmlAttribute(name = "role", namespace = "http://www.w3.org/1999/xlink")
            protected String role;
            @XmlAttribute(name = "title", namespace = "http://www.w3.org/1999/xlink")
            protected String title;
            @XmlAttribute(name = "label", namespace = "http://www.w3.org/1999/xlink")
            protected String label;

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
             * Recupera il valore della proprietà type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                if (type == null) {
                    return "locator";
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
             * Recupera il valore della proprietà label.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLabel() {
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
            public void setLabel(String value) {
                this.label = value;
            }

        }

    }

}
