//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.2 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.02.09 alle 11:27:53 AM CET 
//


package com.gruppometa.data.mets.niso;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.gruppometa.data.mets.niso package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.gruppometa.data.mets.niso
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImageCreation }
     * 
     */
    public ImageCreation createImageCreation() {
        return new ImageCreation();
    }

    /**
     * Create an instance of {@link Dimensions }
     * 
     */
    public Dimensions createDimensions() {
        return new Dimensions();
    }

    /**
     * Create an instance of {@link Format }
     * 
     */
    public Format createFormat() {
        return new Format();
    }

    /**
     * Create an instance of {@link Spatialmetrics }
     * 
     */
    public Spatialmetrics createSpatialmetrics() {
        return new Spatialmetrics();
    }

    /**
     * Create an instance of {@link Targetdata }
     * 
     */
    public Targetdata createTargetdata() {
        return new Targetdata();
    }

    /**
     * Create an instance of {@link DocFormat }
     * 
     */
    public DocFormat createDocFormat() {
        return new DocFormat();
    }

    /**
     * Create an instance of {@link ImageCreation.Scanningsystem }
     * 
     */
    public ImageCreation.Scanningsystem createImageCreationScanningsystem() {
        return new ImageCreation.Scanningsystem();
    }

}
