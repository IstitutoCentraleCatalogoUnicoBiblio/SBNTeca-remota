package com.gruppometa.sbnmarc.mag;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

/**
 */
public class MagTransformer {
    protected static final Logger logger = LoggerFactory.getLogger(MagTransformer.class);

    public String transform(String xmlSbnMarc) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document doc = documentBuilderFactory
                .newDocumentBuilder().parse(new InputSource( new StringReader(xmlSbnMarc) ));
        return transform(doc);
    }

    public String transform(String xmlSbnMarc, String type) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document doc = documentBuilderFactory.newDocumentBuilder()
                .parse(new InputSource( new StringReader(xmlSbnMarc) ));
        return transform(doc, type);
    }

    public String transform(Document docSbnMarc) throws Exception {
        return transform(docSbnMarc, "mag");
    }
    public String transform(Document docSbnMarc, String type) throws Exception {
        boolean clean = true;
        Transformer transformer =null;
        Transformer transformer2 =null;
        try {
            transformer =  SaxonHelper.getInstance().getTransformer(type,"1.0");
            transformer2 =  SaxonHelper.getInstance().getTransformer("clean","1.0");
            //transformer.setParameter("type", tipoScheda);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            DOMSource source = new DOMSource(docSbnMarc);
            transformer.transform(source, result);
            if(clean) {
                docSbnMarc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(writer.toString())));
                source = new DOMSource(docSbnMarc);
                writer = new StringWriter();
                result = new StreamResult(writer);
                transformer2.transform(source, result);
            }
            return writer.toString();
        } catch (TransformerConfigurationException e1) {
            logger.error("",e1);
            throw new Exception(e1.getMessage());
        }
    }
}
