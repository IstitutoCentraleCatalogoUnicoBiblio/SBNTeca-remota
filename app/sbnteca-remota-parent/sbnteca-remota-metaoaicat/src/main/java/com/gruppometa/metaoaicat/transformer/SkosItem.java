package com.gruppometa.metaoaicat.transformer;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xalan.extensions.ExpressionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.gruppometa.culturaitalia.skos.ObjectFactory;

import java.util.List;

public class SkosItem {
	protected static Logger logger = LoggerFactory.getLogger(SkosItem.class);

	protected static com.gruppometa.culturaitalia.skos.SkosItem getItem(String skositemName){
		com.gruppometa.culturaitalia.skos.SkosItem item = ObjectFactory.getSkosItem(skositemName);
		if(item==null)
			item = ObjectFactory.getSkosItem(skositemName.replace("4.2#", "4.1#"));
		if(item==null)
			item = ObjectFactory.getSkosItem(skositemName.replace("4.1#", "4.2#"));
		if(item==null)
			item = ObjectFactory.getSkosItem(skositemName.replace("4.0#", "4.1#"));
		if(item==null)
			item = ObjectFactory.getSkosItem(skositemName.replace("4.0#", "4.2#"));
		if(item==null){
			logger.debug("Item nont found:"+skositemName);
			return null;
		}
		else
			return item;
	}
	public static Node createSubject(ExpressionContext context, String skositemName){
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		fac.setNamespaceAware(true);		
		try {
			com.gruppometa.culturaitalia.skos.SkosItem item = getItem(skositemName);
			if(item==null)
				return null;

			org.w3c.dom.Document doc2 = fac.newDocumentBuilder().newDocument();
			
			String nsSkos = "http://www.w3.org/2004/02/skos/core#";
			String nsEdm = "http://www.europeana.eu/schemas/edm/";
			String nsRdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
			String nsXml = "http://www.w3.org/XML/1998/namespace";
			String owl = "http://www.w3.org/2002/07/owl#";

			boolean timeSpan = isTimeSpan(item);
			boolean addBroader = !timeSpan;
			boolean addNarrower = !timeSpan;

			org.w3c.dom.Element concept = null;
			if(timeSpan)
				concept = doc2.createElementNS(nsEdm, "edm:TimeSpan");
			else
				concept = doc2.createElementNS(nsSkos, "skos:Concept");
			concept.setAttributeNS(nsRdf , "rdf:about", skositemName);
			
			org.w3c.dom.Element prefLabelIt = doc2.createElementNS(nsSkos,"skos:prefLabel");
			prefLabelIt.setAttributeNS(nsXml, "xml:lang", "it");
			prefLabelIt.setTextContent(item.getLabelIt());
			
			org.w3c.dom.Element prefLabelEn = doc2.createElementNS(nsSkos,"skos:prefLabel");
			prefLabelEn.setAttributeNS(nsXml, "xml:lang", "en");
			prefLabelEn.setTextContent(item.getLabelEn());

			org.w3c.dom.Element broader = doc2.createElementNS(nsSkos,"skos:broader");
			broader.setAttributeNS(nsRdf, "rdf:resource", item.getBroader());

			doc2.appendChild(concept);
			concept.appendChild(prefLabelIt);
			concept.appendChild(prefLabelEn);
//			if(timeSpan){
//				org.w3c.dom.Element same = doc2.createElementNS(nsSkos,"owl:sameAs");
//				broader.setAttributeNS(nsRdf, "rdf:resource", skositemName);
//			}
			if(addBroader)
				concept.appendChild(broader);
			if(addNarrower){
				List<com.gruppometa.culturaitalia.skos.SkosItem> children =  ObjectFactory.getChildren(item);
				for (com.gruppometa.culturaitalia.skos.SkosItem it: children){
					org.w3c.dom.Element narrower = doc2.createElementNS(nsSkos,"skos:narrower");
					narrower.setAttributeNS(nsRdf, "rdf:resource", it.getName());
					concept.appendChild(narrower);
				}
			}
			return concept; 
		} catch (Exception e) {
			logger.error("",e);
		} 
		return null;
	}

	private static boolean isTimeSpan(com.gruppometa.culturaitalia.skos.SkosItem item) {
		if(item.getLabelIt()!=null && item.getLabelIt().toLowerCase().contains("quando"))
			return true;
		com.gruppometa.culturaitalia.skos.SkosItem last = item;
		com.gruppometa.culturaitalia.skos.SkosItem broader = ObjectFactory.getSkosItem(item.getBroader());
		while(broader!=null) {
			last = broader;
			broader = ObjectFactory.getSkosItem(broader.getBroader());
		}
		if(last!=null && last.getLabelIt()!=null && last.getLabelIt().toLowerCase().contains("quando"))
			return true;
		return false;
	}

	public static String getLabel(ExpressionContext context, String skositemName, String language) {

		com.gruppometa.culturaitalia.skos.SkosItem item = getItem(skositemName);
		if(item==null)
			return null;
		return language.equalsIgnoreCase("en") ? item.getLabelEn() : item.getLabelIt();
	}
}
