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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per format complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="format"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mime" type="{http://www.niso.org/pdfs/DataDict.pdf}img_mimetype"/&gt;
 *         &lt;element name="compression" type="{http://www.niso.org/pdfs/DataDict.pdf}compressiontype" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "format", propOrder = {
    "name",
    "mime",
    "compression"
})
public class Format
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ImgMimetype mime;
    @XmlSchemaType(name = "string")
    protected Compressiontype compression;

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà mime.
     * 
     * @return
     *     possible object is
     *     {@link ImgMimetype }
     *     
     */
    public ImgMimetype getMime() {
        return mime;
    }

    /**
     * Imposta il valore della proprietà mime.
     * 
     * @param value
     *     allowed object is
     *     {@link ImgMimetype }
     *     
     */
    public void setMime(ImgMimetype value) {
        this.mime = value;
    }

    /**
     * Recupera il valore della proprietà compression.
     * 
     * @return
     *     possible object is
     *     {@link Compressiontype }
     *     
     */
    public Compressiontype getCompression() {
        return compression;
    }

    /**
     * Imposta il valore della proprietà compression.
     * 
     * @param value
     *     allowed object is
     *     {@link Compressiontype }
     *     
     */
    public void setCompression(Compressiontype value) {
        this.compression = value;
    }

}
