package com.gruppometa.sbntecaremota.util;

import ch.qos.logback.core.joran.spi.XMLUtil;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalizationUtility {

	// mappa namespace
	private static Map<String, String> namespaceMap = new HashMap<String, String>();
	
	// mappatura ordine tags
	private static Map<String, List<String>> orderMap = new HashMap<String, List<String>>();
	
	// mappatura campi
	private static Map<String, List<String>> pathMap = new HashMap<String, List<String>>();
	
	static {
		orderMap.put("metadigit", Arrays.asList("gen", "bib", "stru", "img", "audio", 
				"video", "ocr", "doc"));
		
		orderMap.put("gen", Arrays.asList("stprog", "collection", "agency", "access_rights", 
				"completeness", "img_group", "audio_group", "video_group"));
		
		orderMap.put("bib", Arrays.asList("identifier", "title", "creator", "publisher", 
				"subject", "description", "contributor", "date", "type", "format", "source",
				"language", "relation", "coverage", "rights", "holdings"));
		
		orderMap.put("holdings", Arrays.asList("library", "inventory_number", "shelfmark"));
		
		orderMap.put("img", Arrays.asList("sequence_number", "nomenclature", "usage", "side", 
				"scale", "file", "md5", "filesize", "image_dimensions", "image_metrics", "ppi", 
				"dpi", "format", "scanning", "datetimecreated", "target", "altimg", "note"));
		
		orderMap.put("altimg", Arrays.asList("usage", "file", "md5", "filesize", "image_dimensions", 
				"image_metrics", "ppi", "dpi", "format", "scanning", "datetimecreated"));
		
		
		
		namespaceMap.put("metadigit", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("gen", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("stprog", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("agency", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("collection", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("bib", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("identifier", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("title", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("creator", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("contributor", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("publisher", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("subject", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("description", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("type", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("date", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("format", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("source", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("language", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("relation", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("coverage", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("rights", "http://purl.org/dc/elements/1.1/");
		namespaceMap.put("holdings", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("library", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("img", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("audio", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("video", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("doc", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("ocr", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("xml", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("sequence_number", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("nomenclature", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("usage", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("file", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("md5", "http://www.iccu.sbn.it/metaAG1.pdf");
		namespaceMap.put("filesize", "http://www.iccu.sbn.it/metaAG1.pdf");
		
		
		
		pathMap.put("stprog", Arrays.asList("metadigit", "gen", "stprog"));
		pathMap.put("agency", Arrays.asList("metadigit", "gen", "agency"));
		pathMap.put("collection", Arrays.asList("metadigit", "gen", "collection"));
		pathMap.put("identifier", Arrays.asList("metadigit", "bib", "identifier"));
		pathMap.put("title", Arrays.asList("metadigit", "bib", "title"));
		pathMap.put("creator", Arrays.asList("metadigit", "bib", "creator"));
		pathMap.put("publisher", Arrays.asList("metadigit", "bib", "publisher"));
		pathMap.put("subject", Arrays.asList("metadigit", "bib", "subject"));
		pathMap.put("description", Arrays.asList("metadigit", "bib", "description"));
		pathMap.put("contributor", Arrays.asList("metadigit", "bib", "contributor"));
		pathMap.put("date", Arrays.asList("metadigit", "bib", "date"));
		pathMap.put("type", Arrays.asList("metadigit", "bib", "type"));
		pathMap.put("format", Arrays.asList("metadigit", "bib", "format"));
		pathMap.put("source", Arrays.asList("metadigit", "bib", "source"));
		pathMap.put("language", Arrays.asList("metadigit", "bib", "language"));
		pathMap.put("relation", Arrays.asList("metadigit", "bib", "relation"));
		pathMap.put("coverage", Arrays.asList("metadigit", "bib", "coverage"));
		pathMap.put("rights", Arrays.asList("metadigit", "bib", "rights"));
		pathMap.put("library", Arrays.asList("metadigit", "bib", "holdings", "library"));
	}
	
	public static List<String> getOrderContainer(String containerTag) {
		return orderMap.containsKey(containerTag) ? 
				orderMap.get(containerTag) : new ArrayList<String>();
	}

	public static String getNamespaceTag(String tagName) {
		return namespaceMap.get(tagName);
	}
	
	public static List<String> getPathXml(String fieldID) {
		return pathMap.containsKey(fieldID) ? 
				pathMap.get(fieldID) : new ArrayList<String>();
	}
	
	public static String getSolrOriginalField(String solrField) {
		String originalField = solrField;
		
		if(originalField.endsWith("Facet"))
			originalField = originalField.substring(0, originalField.lastIndexOf("Facet"));
		
		if(originalField.endsWith("String"))
			originalField = originalField.substring(0, originalField.lastIndexOf("String"));
		
		return originalField;
	}
	
	public static String getSolrOriginalValueField(String solrField) {
		if(solrField.endsWith("Facet"))
			return solrField.replaceAll("Facet", "String");
		
		else if(solrField.endsWith("String"))
			return solrField;
		
		return solrField + "String";
	}

	public static XPathExpression getXPathXmlForMets(String fieldName) throws XPathExpressionException {
		if(fieldName.equals("type"))
			return UtilXML.getXpathForMets().compile("//mods:typeOfResource");
		if(fieldName.equals("format"))
			return UtilXML.getXpathForMets().compile("//mods:TODO");
		if(fieldName.equals("subject"))
			return UtilXML.getXpathForMets().compile("//mods:subject/mods:geographic|" +
					"//mods:subject/mods:topic|" +
					"//mods:subject/mods:genre|" +
					"//mods:subject/mods:name/mods:namePart|" +
					"//mods:subject/mods:titleInfo/mods:title");
		if(fieldName.equals("date"))
			return UtilXML.getXpathForMets().compile("//mods:dateIssued|//mods:dateCreated|//mods:copyrightDate");
		if(fieldName.equals("creator"))
			return UtilXML.getXpathForMets().compile("//mods:name/mods:namePart[not(@type)]");
		if(fieldName.equals("contributor"))
			return UtilXML.getXpathForMets().compile("//mods:name/mods:namePart[not(@type)]");
		if(fieldName.equals("title"))
			return UtilXML.getXpathForMets().compile("//mods:titleInfo/mods:title/mods:NO_SUPPORT");
		if(fieldName.equals("rights"))
			return UtilXML.getXpathForMets().compile("//mods:TODO");
		if(fieldName.equals("library"))
			return UtilXML.getXpathForMets().compile("//mods:physicalLocation");
		if(fieldName.equals("publisher"))
			return UtilXML.getXpathForMets().compile("//mods:NO_SUPPORT");
		if(fieldName.equals("collection"))
			return UtilXML.getXpathForMets().compile("//mods:relatedItem[@otherType='digitalCollection']/mods:titleInfo/mods:title");
		if(fieldName.equals("language"))
			return UtilXML.getXpathForMets().compile("//mods:language");
		return null;
	}
}
