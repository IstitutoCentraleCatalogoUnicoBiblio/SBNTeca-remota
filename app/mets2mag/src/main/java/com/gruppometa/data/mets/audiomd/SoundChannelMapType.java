//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.audiomd;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * soundChannelMapType: Complex type for describing the aural layout of
 * 				any audio tracks. The soundChannelMap element is used to wrap any number of
 * 				channelAssignment elements. This structure is used to describe the relationship of
 * 				sound channels to their destination. Generally, the destination will be a location
 * 				on the sound stage though it could also be something more specialized such as a time
 * 				code reader or even the channels of a mixing board. The channelAssignment element
 * 				maps a sound channel to its destination. There are 2 required attributes. The
 * 				channeNum identifies the sound channel of this audio object. Note that the first
 * 				channelNum is 0 while each subsequent channel is incremented by 1. The mapLocation
 * 				attribute specifies the destination.
 * 
 * <p>Classe Java per soundChannelMapType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="soundChannelMapType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="channelAssignment" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="CHANNELNUM" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                 &lt;attribute name="MAPLOCATION" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soundChannelMapType", propOrder = {
    "channelAssignment"
})
public class SoundChannelMapType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    protected SoundChannelMapType.ChannelAssignment channelAssignment;

    /**
     * Recupera il valore della proprietà channelAssignment.
     * 
     * @return
     *     possible object is
     *     {@link SoundChannelMapType.ChannelAssignment }
     *     
     */
    public SoundChannelMapType.ChannelAssignment getChannelAssignment() {
        return channelAssignment;
    }

    /**
     * Imposta il valore della proprietà channelAssignment.
     * 
     * @param value
     *     allowed object is
     *     {@link SoundChannelMapType.ChannelAssignment }
     *     
     */
    public void setChannelAssignment(SoundChannelMapType.ChannelAssignment value) {
        this.channelAssignment = value;
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
     *       &lt;attribute name="CHANNELNUM" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *       &lt;attribute name="MAPLOCATION" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ChannelAssignment
        implements Serializable
    {

        private final static long serialVersionUID = -1L;
        @XmlAttribute(name = "CHANNELNUM")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger channelnum;
        @XmlAttribute(name = "MAPLOCATION")
        protected String maplocation;

        /**
         * Recupera il valore della proprietà channelnum.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getCHANNELNUM() {
            return channelnum;
        }

        /**
         * Imposta il valore della proprietà channelnum.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCHANNELNUM(BigInteger value) {
            this.channelnum = value;
        }

        /**
         * Recupera il valore della proprietà maplocation.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMAPLOCATION() {
            return maplocation;
        }

        /**
         * Imposta il valore della proprietà maplocation.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMAPLOCATION(String value) {
            this.maplocation = value;
        }

    }

}
