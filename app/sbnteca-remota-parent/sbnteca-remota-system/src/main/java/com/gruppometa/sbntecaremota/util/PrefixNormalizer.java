package com.gruppometa.sbntecaremota.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class PrefixNormalizer {
	
	// tag esclusi
	private static List<String> excludedTags = Arrays.asList("holdings", "library", 
			"inventory_number", "shelfmark", "piece", "year", "issue", "stpiece_per", 
			"part_name", "part_number", "stpiece_vol");
	

    public static void main(String[] args){
        PrefixNormalizer prefixNormalizer = new PrefixNormalizer();
        String filename = "/var/lib/magteca/import/PROVA OCR/CFI0375171_19550327_068_086.xml";
        if(args.length>0)
            filename = args[0];
        try {
            /**
             * parsing
             */
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            fac.setNamespaceAware(true);
            DocumentBuilder builder = fac.newDocumentBuilder();
            Document docXml = builder.parse(new InputSource(new FileReader(filename)));

            /**
             * modifica namespace
             */
            String magNameSpace = "http://www.iccu.sbn.it/metaAG1.pdf";
            String magPrefix = "mag";
            prefixNormalizer.fixPrefixes(docXml, magNameSpace, magPrefix);
            /**
             * stampa
             */
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(docXml);
            transformer.transform(source, result);
            String xmlString = result.getWriter().toString();
            System.out.println(xmlString);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Cambia i prefissi a quello selezionato
     * @param document
     * @param namespace
     * @param prefix
     * @throws Exception
     */
    public void fixPrefixes(Document document, String namespace, String prefix) throws Exception{
        Element element = document.getDocumentElement();
        String otherNameSpace = checkPrefixInUseForOtherNamespace(element, namespace, prefix);
        if(otherNameSpace!=null)
            throw new Exception("prefix '"+prefix+"' in use for "+otherNameSpace);
        else {
            element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, namespace);
            changePrefix(element, namespace, prefix);
        }

    }

    /**
     * controllo se esiste un altro namespace con lo stesso prefisso nel documento XML
     * @param documentElement
     * @param namespace
     * @param prefix
     * @return
     */
    public String checkPrefixInUseForOtherNamespace(Element documentElement, String namespace, String prefix) {
        if(documentElement.getNamespaceURI()!=null && !documentElement.getNamespaceURI().equals(namespace)
                && documentElement.getPrefix().equals(prefix))
            return documentElement.getNamespaceURI();
        if(documentElement.getChildNodes()==null)
            return null;
        NodeList list = documentElement.getChildNodes();
        for(int i=0; i< list.getLength();i++) {
            if (list.item(i) instanceof Element) {
                String ret = checkPrefixInUseForOtherNamespace((Element) list.item(i), namespace, prefix);
                if(ret!=null)
                    return ret;
            }
        }
        return null;
    }

    /**
     * cambia il prefisso per un  certo namespace
     * @param documentElement
     * @param namespace
     * @param prefix
     */
    public void changePrefix(Element documentElement, String namespace, String prefix) {
    	if(documentElement.getNamespaceURI()!=null && documentElement.getNamespaceURI().equals(namespace)) {
        	if(!excludedTags.contains(documentElement.getLocalName()))
        		documentElement.setPrefix(prefix);
        }
        if(documentElement.getChildNodes()==null)
            return;
        NodeList list = documentElement.getChildNodes();
        for(int i=0; i< list.getLength();i++)
            if(list.item(i) instanceof Element)
                changePrefix((Element) list.item(i), namespace, prefix);
    }
}
