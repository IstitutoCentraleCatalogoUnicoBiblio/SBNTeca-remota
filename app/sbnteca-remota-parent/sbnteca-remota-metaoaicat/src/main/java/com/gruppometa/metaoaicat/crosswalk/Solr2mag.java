package com.gruppometa.metaoaicat.crosswalk;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.solr.common.SolrDocument;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruppometa.metaoaicat.SolrOAISetsCatalog;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;

public class Solr2mag extends Crosswalk  implements NeededFields{

	protected static Logger logger = LoggerFactory.getLogger(Solr2mag.class);
	private String xmlField;

	public Solr2mag(Properties properties) {
		super("http://www.iccu.sbn.it/metaAG1.pdf http://www.iccu.sbn.it/directories/metadigit201/metadigit.xsd");
		xmlField = properties.getProperty("Solr2mag.xmlField"); 
	}

	@Override
	public boolean isAvailableFor(Object nativeItem) {
		return nativeItem instanceof SolrDocument;
	}

	@Override
	public String createMetadata(Object nativeItem)
			throws CannotDisseminateFormatException {
		SolrDocument doc = (SolrDocument)nativeItem;			
		return makeConstants(doc,(String)doc.getFieldValue(xmlField));
	}

	public List<String> getNeededFields(){
		List<String> fields = new ArrayList<String>();
		if(xmlField!=null)
			fields.add(xmlField);
		return fields;
	}
	
	protected static boolean isBefore(String name, String candidate){
		String tags[] = new String[]{"dc:identifier",
				"dc:title",
				"dc:creator","dc:publisher","dc:subject","dc:description",
				"dc:contributor","dc:date" ,"dc:type" ,"dc:format" ,"dc:source" ,"dc:language" ,
				"dc:relation" ,"dc:coverage" ,"dc:rights","dc:holdings" };
		int pos = -1, posCandidate=-1;
		for (int i = 0; i < tags.length; i++) {
			if(tags[i].equals(name)){
				pos = i;
				break;
			}
		}
		for (int i = 0; i < tags.length; i++) {
			if(tags[i].equals(candidate)){
				posCandidate = i;
				break;
			}
		}
		logger.debug("name="+name+" candidate="+candidate+ " ret="+(posCandidate<pos));
		return posCandidate<pos;
	}
	
	protected static String normalizeDcName(String name){
		//logger.debug(name);
		if(name==null)
			return null;
		if(name.startsWith("dc:"))
			return name;
		else
			return "dc:"+name;
	}
	
	protected static int searchPosition(List elements, String tagName){
		int ret = elements.size();		
		for (int i = 0; i < elements.size(); i++) {
			Element el = (Element)elements.get(i);
			if(isBefore(normalizeDcName(el.getName()),tagName)){
				ret = i;
				break;
			}
		}
		for (int i = 0; i < elements.size(); i++) {
			Element el = (Element)elements.get(i);
			if(normalizeDcName(el.getName()).equals(tagName)){
				ret =i+1;
			}
		}
		return ret;
	}
	public static String makeConstants(SolrDocument doc, String xml) throws CannotDisseminateFormatException {
		if(xml==null || doc==null)
			return null;
		
		try{
			Document docXml = new SAXReader().read(new StringReader(xml));
			Element root = docXml.getRootElement();
			Iterator it = root.elementIterator("bib");
			if(it.hasNext())
				root = (Element)it.next();
			List children = root.elements();
			if(doc.getFieldValues("constant")!=null){
			for (Iterator iterator = doc.getFieldValues("constant").iterator(); iterator
					.hasNext();) {
				String v = (String) iterator.next();
				String[] vals = v.split(SolrOAISetsCatalog.FIELD_SEPARATOR_REGEX,2);
				String[] attr = vals[0].split(" ");
				/**
				 * non edm
				 */
				if(!attr[0].startsWith("edm:")){
					Element el =  new DocumentFactory().createElement(attr[0]);
					children.add(searchPosition(children, attr[0]),el);
					for (int i = 1; i < attr.length; i++) {
						String[] attrDef = attr[i].split("=",2);
						el.addAttribute(attrDef[0], Solr2pico.strip(attrDef[1]));
					}
					el.setText(validate(doc,vals[1]));			
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
			logger .error("",e);
			throw new  CannotDisseminateFormatException("error in mag");
		}
		
		return xml;
	}

	private static String validate(SolrDocument doc, String string) {
		if(!string.contains("${") || !string.contains("}"))
			return string;
		String var1 = string.substring(string.indexOf("${")+2);
		String var2 = var1.substring(0, var1.indexOf("}"));
		String part1 = string.substring(0,string.indexOf("${"));
		String part2 = string.substring(string.indexOf("}")+1);
		Object fieldObject = null;
		String fieldValue = "";
		if(var2.contains(":") && var2.startsWith("urlencode:")){
			var2 = var2.substring(10);
			fieldObject = doc.getFieldValue(var2);
			fieldValue = fieldObject!=null?(String)fieldObject:"noValue{"+var2+"}";
			try {
				return  part1+ URLEncoder.encode(fieldValue,"UTF-8")+ validate(doc, part2);
			} catch (UnsupportedEncodingException e) {
				return part1+fieldValue+ validate(doc, part2);
			}			
		}
		else
			fieldObject = doc.getFieldValue(var2);
			fieldValue = fieldObject!=null?(String)fieldObject:"noValue{"+var2+"}";
			return part1+fieldValue+ validate(doc, part2);
	}


}
