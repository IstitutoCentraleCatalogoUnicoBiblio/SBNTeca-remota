package com.gruppometa.culturaitalia.skos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SkosLoader {
	protected Logger logger = LoggerFactory.getLogger(SkosLoader.class);
	protected Exception exception;
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public void loadUrl(String url){
		try{
			exception = null;
			DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
			fac.setNamespaceAware(true);
			DocumentBuilder builder =  fac.newDocumentBuilder();
			Document doc =  builder.parse(url);
			//DOMSource source = new DOMSource(doc);
			XPathFactory xpathFac = XPathFactory.newInstance();			
			XPath xPath = xpathFac.newXPath();		
			xPath.setNamespaceContext(new MyNamespaceContext());
			XPathExpression xpathExpression = xPath.compile("//rdf:Description");
			XPathExpression xpathExpressionType = xPath.compile("rdf:type/@rdf:resource");
			XPathExpression xpathExpressionLabels = xPath.compile("skos:prefLabel");
			XPathExpression xpathExpressionTheme = xPath.compile("pico:theme");
			XPathExpression xpathExpressionBroader = xPath.compile("skos:broader/@rdf:resource");
			String thesaurusVersion = "4.3";
			int thesaurusRevision = 0;
			
			NodeList set = (NodeList) xpathExpression.evaluate(doc.getDocumentElement(), XPathConstants.NODESET);
			for (int i =0; i<set.getLength();i++) {
				Node node = set.item(i);
				String type = xpathExpressionType.evaluate(node);
				String about = node.getAttributes().getNamedItem("rdf:about")!=null?node.getAttributes().getNamedItem("rdf:about").getNodeValue():null;
				/**
				 * la version del thesaurus
				 */
				if(type.equals("http://www.w3.org/2004/02/skos/core#ConceptScheme")){
					if(about!=null && about.contains("/")){
						String tmp = about.substring(about.lastIndexOf("/")+1);
						String[] v = tmp.split("\\.");
						if(v.length==3)
							thesaurusRevision = Integer.parseInt(v[2]);
						else if (v.length>=2)
							thesaurusVersion = v[0]+"."+v[1];							
					}
				}
				if(type.equals("http://www.w3.org/2004/02/skos/core#Concept")){
					SkosItem skosItem = new SkosItem();
					skosItem.setThesaurusVersion(thesaurusVersion);
					skosItem.setRevision(thesaurusRevision);
					skosItem.setName(about);					
					NodeList labels = (NodeList)xpathExpressionLabels.evaluate(node,XPathConstants.NODESET);
					for (int j = 0; j < labels.getLength(); j++) {
						Node label = labels.item(j);
						if(label!=null && label.getAttributes()!=null && label.getAttributes().getNamedItem("xml:lang")!=null
									&& label.getAttributes().getNamedItem("xml:lang").getNodeValue().equals("it"))
							skosItem.setLabelIt(label.getTextContent());
						if(label!=null && label.getAttributes()!=null && label.getAttributes().getNamedItem("xml:lang")!=null
								&& label.getAttributes().getNamedItem("xml:lang").getNodeValue().equals("en"))
						skosItem.setLabelEn(label.getTextContent());
							//logger.debug(label.getTextContent());		
					}
					ArrayList<String> themesList = new ArrayList<String>();
					NodeList themes = (NodeList)xpathExpressionTheme.evaluate(node,XPathConstants.NODESET);
					for (int j = 0; j < themes.getLength(); j++) {
						Node theme = themes.item(j);
						themesList.add(theme.getTextContent());
					}
					skosItem.setThemes(themesList);
					skosItem.setBroader(xpathExpressionBroader.evaluate(node));
					logger.debug(skosItem.toString());
					ObjectFactory.save(skosItem, true);
				}
				//logger.debug(type);				
			}
		}
		catch(XPathExpressionException e){
			exception = e;
			logger.error("",e);
		}
		catch(IOException e){
			exception = e;
			logger.error("",e);
		}
		catch(SAXException e){
			exception = e;
			logger.error("",e);
		}
		catch(ParserConfigurationException e){
			exception = e;
			logger.error("",e);
		}
	}
	public class MyNamespaceContext implements NamespaceContext
    {
        public String getNamespaceURI(String prefix)
        {
            if (prefix.equals("rdf"))
                return "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
            else if (prefix.equals("skos"))
                return "http://www.w3.org/2004/02/skos/core#";
            else if (prefix.equals("pico"))
                return "http://purl.org/pico";
            else
                return XMLConstants.NULL_NS_URI;
        }
        
        public String getPrefix(String namespace)
        {
            if (namespace.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#"))
                return "rdf";
            else if (namespace.equals("http://www.w3.org/2004/02/skos/core#"))
                return "skos";
            else if (namespace.equals("http://purl.org/pico"))
                return "pico";
            else
                return null;
        }

        public Iterator getPrefixes(String namespace)
        {
            return null;
        }
    }  
}
