package com.gruppometa.metaoaicat;

import java.util.Properties;

import org.apache.solr.common.SolrDocument;

public class SolrSoundRecordFactory extends SolrRecordFactory{
	String cutPart = "oai:192.168.10.31:22:RM0200:";
	public SolrSoundRecordFactory(Properties properties) {
		super(properties);		
		if(properties.getProperty("Identity.cutPart")!=null)
			cutPart = properties.getProperty("Identity.cutPart");
	}

	@Override
	public String fromOAIIdentifier(String identifier) {
		if(identifier.length()<prefix.length())
			return identifier;
		return cutPart+identifier.substring(prefix.length());
	}
	
	@Override
	public String getOAIIdentifier(Object nativeItem) {
		SolrDocument doc = (SolrDocument)nativeItem;
		String id = (String)doc.getFieldValue("id");
		if(id.startsWith(cutPart))
			id = id.substring(cutPart.length());
		return prefix+id;
	}
}
