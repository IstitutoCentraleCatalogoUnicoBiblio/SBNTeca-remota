package com.gruppometa.metaoaicat.crosswalk;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.solr.common.SolrDocument;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruppometa.metaoaicat.SolrOAISetsCatalog;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;

public class Solr2pico extends Crosswalk  implements NeededFields{

	protected static Logger logger = LoggerFactory.getLogger(Solr2pico.class);
	private String xmlField;

	public Solr2pico(Properties properties) {
		super("http://pico.sns.it/pico/1.1/ http://pico.sns.it/pico/1.1/pico.xsd");
		xmlField = properties.getProperty("Solr2pico.xmlField"); 
	}

	@Override
	public boolean isAvailableFor(Object nativeItem) {
		return nativeItem instanceof SolrDocument;
	}

	@Override
	public String createMetadata(Object nativeItem)
			throws CannotDisseminateFormatException {		
		SolrDocument doc = (SolrDocument)nativeItem;	 
		String xml =  (String)doc.getFieldValue(xmlField);
		xml = makeConstants(doc, xml);
		return xml;
	}

	public List<String> getNeededFields(){
		List<String> fields = new ArrayList<String>();
		if(xmlField!=null)
			fields.add(xmlField);
		return fields;
	}

	public static String makeConstants(SolrDocument doc, String xml) throws CannotDisseminateFormatException {
		if(xml==null || doc==null)
			return null;
		
		try{
			Document docXml = new SAXReader().read(new StringReader(xml));
			if(doc.getFieldValues("constant")!=null){
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = doc.getFieldValues("constant").iterator(); iterator
					.hasNext();) {
				String v = (String) iterator.next();
				String[] vals = v.split(SolrOAISetsCatalog.FIELD_SEPARATOR_REGEX,2);
				String[] attr = vals[0].split(" ");
				/**
				 * non edm
				 */
				if(!attr[0].startsWith("edm:")){
					Element el = docXml.getRootElement().addElement(attr[0]);
					for (int i = 1; i < attr.length; i++) {
						String[] attrDef = attr[i].split("=",2);
						el.addAttribute(attrDef[0], strip(attrDef[1]));
					}
					el.setText(vals[1]);			
				}
			}
			}
			StringWriter writer = new StringWriter();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setSuppressDeclaration(true);
			XMLWriter writerxml = new XMLWriter(writer, format);
			writerxml.write(docXml);
			xml = writer.toString();
		}
		catch(Exception e){
			logger.error("",e);
			throw new  CannotDisseminateFormatException("error in pico");
		}
		
		return xml;
	}

	public static String strip(String string) {
		if(string==null)
			return null;
		return string.replaceAll("\"", "");
	}


}
