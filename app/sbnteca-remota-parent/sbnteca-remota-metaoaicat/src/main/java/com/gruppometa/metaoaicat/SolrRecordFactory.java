package com.gruppometa.metaoaicat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.solr.common.SolrDocument;

import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;

public class SolrRecordFactory extends ORG.oclc.oai.server.catalog.RecordFactory{

	protected String prefix="oai:localhost:8080";
	protected String deleteField = "deleted";
	protected String idField = "id";

	public SolrRecordFactory(Properties properties) {
		super(properties);
		if(properties.getProperty("Identity.prefix")!=null)
			prefix = properties.getProperty("Identity.prefix");
		if(properties.getProperty("deleteField")!=null)
			deleteField = properties.getProperty("deleteField");
		if(properties.getProperty("SolrOAICatalog.idField")!=null)
			idField = properties.getProperty("SolrOAICatalog.idField");
	}

	@Override
	public String fromOAIIdentifier(String identifier) {
		if(identifier.length()<prefix.length())
			return identifier;
		return identifier.substring(prefix.length()+1);
	}

	@Override
	public String quickCreate(Object nativeItem, String schemaURL,
			String metadataPrefix) throws IllegalArgumentException,
			CannotDisseminateFormatException {
		return null;
	}

	protected boolean urlencodeId = false; // non dovrebbe servire,
	// c'è invece un problema nell'interfaccia con XSLT

	@Override
	public String getOAIIdentifier(Object nativeItem) {
		SolrDocument doc = (SolrDocument)nativeItem;
		String id = (String) doc.getFieldValue(idField);
		try {
			return prefix+":"+
					(urlencodeId?(URLEncoder.encode(id,"UTF-8")):id);
		} catch (UnsupportedEncodingException e) {
			return id;
		}
	}

	DateFormat formatt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	@Override
	public String getDatestamp(Object nativeItem) {
		SolrDocument doc = (SolrDocument)nativeItem;
		return formatt.format((Date)doc.getFieldValue("timestamp"));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getSetSpecs(Object nativeItem)
			throws IllegalArgumentException {
		return null;
	} 

	@Override
	public boolean isDeleted(Object nativeItem) {
		SolrDocument doc = (SolrDocument)nativeItem;
		return (deleteField!=null && doc.getFieldValue(deleteField)!=null 
				&& doc.getFieldValue(deleteField).toString().equalsIgnoreCase("1") );
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getAbouts(Object nativeItem) {
		return null;
	}

	public List<String> getNeededFields(){
		List<String> fields = new ArrayList<String>();
		fields.add(idField);
		fields.add("timestamp");
		fields.add("descSource");
		if(deleteField!=null)
			fields.add(deleteField);
		return fields;
	}

}
