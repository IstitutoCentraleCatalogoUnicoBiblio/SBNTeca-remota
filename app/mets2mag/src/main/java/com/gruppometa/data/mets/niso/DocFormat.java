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
 * 
 * 		Nonostante lo schema NISO sia nato per descrivere le caratteristiche tecniche di file contenenti immagini, per ragioni di omogenità si è scelto di usare il medesimo formato anche per file text-oriented 
 * 		
 * 
 * <p>Classe Java per docFormat complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="docFormat"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mime" type="{http://www.niso.org/pdfs/DataDict.pdf}doc_mimetype"/&gt;
 *         &lt;element name="compression" type="{http://www.niso.org/pdfs/DataDict.pdf}doc_compressiontype" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "docFormat", propOrder = {
    "name",
    "mime",
    "compression"
})
public class DocFormat
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected DocMimetype mime;
    @XmlSchemaType(name = "string")
    protected DocCompressiontype compression;

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
     *     {@link DocMimetype }
     *     
     */
    public DocMimetype getMime() {
        return mime;
    }

    /**
     * Imposta il valore della proprietà mime.
     * 
     * @param value
     *     allowed object is
     *     {@link DocMimetype }
     *     
     */
    public void setMime(DocMimetype value) {
        this.mime = value;
    }

    /**
     * Recupera il valore della proprietà compression.
     * 
     * @return
     *     possible object is
     *     {@link DocCompressiontype }
     *     
     */
    public DocCompressiontype getCompression() {
        return compression;
    }

    /**
     * Imposta il valore della proprietà compression.
     * 
     * @param value
     *     allowed object is
     *     {@link DocCompressiontype }
     *     
     */
    public void setCompression(DocCompressiontype value) {
        this.compression = value;
    }

}
