package com.gruppometa.metaoaicat.crosswalk;

import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import ORG.oclc.oai.server.verb.OAIInternalServerError;
import com.gruppometa.metaoaicat.SolrOAISetsCatalog;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Properties;

public class Solr2MetsSimple extends Solr2mets {
    private static final Logger logger = LoggerFactory.getLogger(Solr2MetsSimple.class);

    protected Templates templates = null;
    protected String manifestUrl = null;
    protected String idField = "id";
    protected String previewUrl = null;
    protected boolean useTemplates = true; // non da l'errore del XSLT.

    public Solr2MetsSimple(Properties properties) throws OAIInternalServerError {
        super(properties);
        makeInit(properties, "Solr2MetsSimple");
    }

    @Override
    public String createMetadata(Object nativeItem)
            throws CannotDisseminateFormatException {
        String xmlRec = super.createMetadata(nativeItem);
        if(xmlRec==null)
            throw new CannotDisseminateFormatException("No mets");
        /**
         * controllo se è già un METS-SBN semplificato
         */
        if(xmlRec.contains("USE=\"VIEWER\"") || xmlRec.contains("USE=\"MANIFEST\""))
            return xmlRec;
        // TODO se non è simple, transformarlo
        // togliere i streams e la stru, mettere il viewer
        xmlRec = SolrFieldXSLT.filterCharacters(xmlRec);
        if(!xmlRec.startsWith("<?xml version"))
            xmlRec = "<?xml version=\"1.0\"?>\n"+xmlRec;
        StringReader stringReader = new StringReader(xmlRec);
        Source streamSource = null;
        StringWriter stringWriter = new StringWriter();
        try {
            streamSource = new StreamSource(stringReader);
            Transformer transformer = null;
            if(useTemplates) {
                if (templates == null)
                    transformer = TransformerFactory.newInstance().newTransformer();
                else
                    transformer = templates.newTransformer();
            }
            else {
                transformer = getTransformer();
            }
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            if (previewUrl != null)
                transformer.setParameter("previewUrl", previewUrl);
            if (manifestUrl != null)
                transformer.setParameter("manifestUrl", manifestUrl+URLEncoder.encode((String) ((SolrDocument) nativeItem).getFieldValue(idField), "UTF-8"));
            transformer.setParameter("id", URLEncoder.encode((String) ((SolrDocument) nativeItem).getFieldValue(idField), "UTF-8"));
            transformer.setParameter("idNative", ((SolrDocument) nativeItem).getFieldValue(idField));
            transformer.transform(streamSource, new StreamResult(stringWriter));
            // non serve: transformer.reset();
            stringWriter.close();
            return stringWriter.toString();
        }
        catch (Exception e){
            logger.error("", e);
        }
        return null;
    }

    protected Transformer getTransformer() throws OAIInternalServerError, TransformerConfigurationException {
        String xsltName = "mets2mets-simple.xslt";
        String crosswalkName = "mets2simple";
        TransformerFactory tFactory = TransformerFactory.newInstance();
        InputStream stream = SolrFieldXSLT.class.getResourceAsStream("/"
                + xsltName);
        if (stream == null)
            throw new OAIInternalServerError("XSLT " + xsltName + " not found for "+crosswalkName+".");
        if (xsltName != null) {
            StreamSource xslSource = new StreamSource(stream);
            return tFactory.newTransformer(xslSource);
        }
        return null;
    }

    protected void makeInit(Properties properties, String name)
            throws TransformerFactoryConfigurationError, OAIInternalServerError {
        String crosswalkName = name;
        try {
            String xsltName = properties.getProperty(crosswalkName+".xsltName");
            manifestUrl = properties.getProperty(crosswalkName+".manifestUrl");
            previewUrl = properties.getProperty(crosswalkName+".previewUrl");
//            if(properties.getProperty("SolrOAICatalog.idField")!=null)
//                idField = properties.getProperty("SolrOAICatalog.idField");
            TransformerFactory tFactory = TransformerFactory.newInstance();
            InputStream stream = SolrFieldXSLT.class.getResourceAsStream("/"
                    + xsltName);
            if (stream == null)
                throw new OAIInternalServerError("XSLT " + xsltName + " not found for "+crosswalkName+".");
            if (xsltName != null) {
                StreamSource xslSource = new StreamSource(stream);
                this.templates = tFactory.newTemplates(xslSource);
            } else {
                // this.transformer = tFactory.newTransformer();
                // this.transformer.setOutputProperty(
                // OutputKeys.OMIT_XML_DECLARATION, "yes");
                // this.transformer.setOutputProperty(
                // OutputKeys.INDENT, "yes");
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new OAIInternalServerError(e.getMessage());
        }
    }


}
