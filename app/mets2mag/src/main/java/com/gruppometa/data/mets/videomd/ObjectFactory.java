//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.videomd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.gruppometa.data.mets.videomd package. 
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

    private final static QName _VIDEOMD_QNAME = new QName("http://www.loc.gov/videoMD/", "VIDEOMD");
    private final static QName _VIDEOSRC_QNAME = new QName("http://www.loc.gov/videoMD/", "VIDEOSRC");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.gruppometa.data.mets.videomd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FileDataType }
     * 
     */
    public FileDataType createFileDataType() {
        return new FileDataType();
    }

    /**
     * Create an instance of {@link VideoType }
     * 
     */
    public VideoType createVideoType() {
        return new VideoType();
    }

    /**
     * Create an instance of {@link MediaDataType }
     * 
     */
    public MediaDataType createMediaDataType() {
        return new MediaDataType();
    }

    /**
     * Create an instance of {@link TrackDataType }
     * 
     */
    public TrackDataType createTrackDataType() {
        return new TrackDataType();
    }

    /**
     * Create an instance of {@link PhysicalDataType }
     * 
     */
    public PhysicalDataType createPhysicalDataType() {
        return new PhysicalDataType();
    }

    /**
     * Create an instance of {@link VideoInfoType }
     * 
     */
    public VideoInfoType createVideoInfoType() {
        return new VideoInfoType();
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
     * Create an instance of {@link VariableRateType }
     * 
     */
    public VariableRateType createVariableRateType() {
        return new VariableRateType();
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
     * Create an instance of {@link DtvType }
     * 
     */
    public DtvType createDtvType() {
        return new DtvType();
    }

    /**
     * Create an instance of {@link FormatType }
     * 
     */
    public FormatType createFormatType() {
        return new FormatType();
    }

    /**
     * Create an instance of {@link CodecType }
     * 
     */
    public CodecType createCodecType() {
        return new CodecType();
    }

    /**
     * Create an instance of {@link StringVersion }
     * 
     */
    public StringVersion createStringVersion() {
        return new StringVersion();
    }

    /**
     * Create an instance of {@link FrameType }
     * 
     */
    public FrameType createFrameType() {
        return new FrameType();
    }

    /**
     * Create an instance of {@link MaterialType }
     * 
     */
    public MaterialType createMaterialType() {
        return new MaterialType();
    }

    /**
     * Create an instance of {@link TimecodeInfoType }
     * 
     */
    public TimecodeInfoType createTimecodeInfoType() {
        return new TimecodeInfoType();
    }

    /**
     * Create an instance of {@link TrackingInfoType }
     * 
     */
    public TrackingInfoType createTrackingInfoType() {
        return new TrackingInfoType();
    }

    /**
     * Create an instance of {@link FileDataType.Location }
     * 
     */
    public FileDataType.Location createFileDataTypeLocation() {
        return new FileDataType.Location();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VideoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VideoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/videoMD/", name = "VIDEOMD")
    public JAXBElement<VideoType> createVIDEOMD(VideoType value) {
        return new JAXBElement<VideoType>(_VIDEOMD_QNAME, VideoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VideoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VideoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/videoMD/", name = "VIDEOSRC")
    public JAXBElement<VideoType> createVIDEOSRC(VideoType value) {
        return new JAXBElement<VideoType>(_VIDEOSRC_QNAME, VideoType.class, null, value);
    }

}
