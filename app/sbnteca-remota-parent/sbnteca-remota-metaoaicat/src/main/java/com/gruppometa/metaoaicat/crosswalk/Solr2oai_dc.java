package com.gruppometa.metaoaicat.crosswalk;

import java.util.Iterator;
import java.util.Properties;

import org.apache.solr.common.SolrDocument;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import ORG.oclc.oai.util.OAIUtil;

public class Solr2oai_dc extends Crosswalk{

	public Solr2oai_dc(Properties properties) {
		super("http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd");
	}

	@Override
	public boolean isAvailableFor(Object nativeItem) {
		return nativeItem instanceof SolrDocument;
	}

	@Override
	public String createMetadata(Object nativeItem)
			throws CannotDisseminateFormatException {
		SolrDocument doc = (SolrDocument)nativeItem;
		StringBuffer sb = new StringBuffer();
		sb.append("<oai_dc:dc xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" " +
				"xmlns:dc=\"http://purl.org/dc/elements/1.1/\" " +
				"xmlns:dcterms=\"http://purl.org/dc/terms/\" " +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xsi:schemaLocation=\""
			  + getSchemaLocation()
			  + "\">");
		for (Iterator<String> iterator = doc.getFieldNames().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			makeField(doc, sb, name,"dc_");
			makeField(doc, sb, name,"dcterms_"); 
		}
		sb.append("</oai_dc:dc>");
		return sb.toString();
	}

	private void makeField(SolrDocument doc, StringBuffer sb, String name, String prefix) {
		if(name.startsWith(prefix)&& name.endsWith("_t")
				&& doc.getFieldValues(name)!=null){
			String nameXML = name.replaceFirst("_", ":");
			nameXML = nameXML.substring(0, nameXML.length()-2);
			for (@SuppressWarnings("rawtypes")
			Iterator iterator2 = doc.getFieldValues(name).iterator(); iterator2
				.hasNext();) {
				Object val = (Object)iterator2.next();
				sb.append("\n\t<"+nameXML+">");
				sb.append(OAIUtil.xmlEncode(""+val));
				sb.append("</"+nameXML+">");
			}
		}
	}

}
