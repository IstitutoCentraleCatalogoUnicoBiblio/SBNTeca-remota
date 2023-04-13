//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.audiomd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.gruppometa.data.mets.audiomd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AUDIOMD_QNAME = new QName("http://www.loc.gov/audioMD/", "AUDIOMD");
    private final static QName _AUDIOSRC_QNAME = new QName("http://www.loc.gov/audioMD/", "AUDIOSRC");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.gruppometa.data.mets.audiomd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoundChannelMapType }
     * 
     */
    public SoundChannelMapType createSoundChannelMapType() {
        return new SoundChannelMapType();
    }

    /**
     * Create an instance of {@link AudioType }
     * 
     */
    public AudioType createAudioType() {
        return new AudioType();
    }

    /**
     * Create an instance of {@link FileDataType }
     * 
     */
    public FileDataType createFileDataType() {
        return new FileDataType();
    }

    /**
     * Create an instance of {@link PhysicalDataType }
     * 
     */
    public PhysicalDataType createPhysicalDataType() {
        return new PhysicalDataType();
    }

    /**
     * Create an instance of {@link AudioInfoType }
     * 
     */
    public AudioInfoType createAudioInfoType() {
        return new AudioInfoType();
    }

    /**
     * Create an instance of {@link CalibrationInfoType }
     * 
     */
    public CalibrationInfoType createCalibrationInfoType() {
        return new CalibrationInfoType();
    }

    /**
     * Create an instance of {@link MessageDigestType }
     * 
     */
    public MessageDigestType createMessageDigestType() {
        return new MessageDigestType();
    }

    /**
     * Create an instance of {@link CompressionType }
     * 
     */
    public CompressionType createCompressionType() {
        return new CompressionType();
    }

    /**
     * Create an instance of {@link DimensionsType }
     * 
     */
    public DimensionsType createDimensionsType() {
        return new DimensionsType();
    }

    /**
     * Create an instance of {@link MaterialType }
     * 
     */
    public MaterialType createMaterialType() {
        return new MaterialType();
    }

    /**
     * Create an instance of {@link TrackingInfoType }
     * 
     */
    public TrackingInfoType createTrackingInfoType() {
        return new TrackingInfoType();
    }

    /**
     * Create an instance of {@link SoundChannelMapType.ChannelAssignment }
     * 
     */
    public SoundChannelMapType.ChannelAssignment createSoundChannelMapTypeChannelAssignment() {
        return new SoundChannelMapType.ChannelAssignment();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AudioType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AudioType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/audioMD/", name = "AUDIOMD")
    public JAXBElement<AudioType> createAUDIOMD(AudioType value) {
        return new JAXBElement<AudioType>(_AUDIOMD_QNAME, AudioType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AudioType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AudioType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/audioMD/", name = "AUDIOSRC")
    public JAXBElement<AudioType> createAUDIOSRC(AudioType value) {
        return new JAXBElement<AudioType>(_AUDIOSRC_QNAME, AudioType.class, null, value);
    }

}
