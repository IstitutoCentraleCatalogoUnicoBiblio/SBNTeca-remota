package com.gruppometa.metaoaicat.crosswalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.solr.common.SolrDocument;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;

public class Solr2edm extends Crosswalk implements NeededFields{

	private String xmlField;

	public Solr2edm(Properties properties) {
		super("http://www.europeana.eu/schemas/edm http://europeanalabs.eu/svn/europeana/trunk/ROOT/src/main/webapp/schemas/edm/EDM.xsd");
		xmlField = properties.getProperty("Solr2edm.xmlField"); 
	}

	@Override
	public boolean isAvailableFor(Object nativeItem) {
		return nativeItem instanceof SolrDocument;
	}

	@Override
	public String createMetadata(Object nativeItem)
			throws CannotDisseminateFormatException {
		SolrDocument doc = (SolrDocument)nativeItem;	 
		return (String)doc.getFieldValue(xmlField);
	}

	public List<String> getNeededFields(){
		List<String> fields = new ArrayList<String>();
		fields.add("descSource");
		if(xmlField!=null)
			fields.add(xmlField);
		return fields;
	}


}
