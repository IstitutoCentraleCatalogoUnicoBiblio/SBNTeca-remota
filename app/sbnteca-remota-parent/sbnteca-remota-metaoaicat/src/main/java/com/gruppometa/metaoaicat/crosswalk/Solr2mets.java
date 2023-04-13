package com.gruppometa.metaoaicat.crosswalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.solr.common.SolrDocument;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;

public class Solr2mets extends Crosswalk  implements NeededFields{

	private String xmlField;

	public Solr2mets(Properties properties) {
		super("http://www.loc.gov/METS_Profile/ http://www.loc.gov/standards/mets/profile_docs/mets.profile.v1-2.xsd");
		xmlField = properties.getProperty("Solr2mets.xmlField"); 
	}

	@Override
	public boolean isAvailableFor(Object nativeItem) {
		return nativeItem instanceof SolrDocument;
	}

	@Override
	public String createMetadata(Object nativeItem)
			throws CannotDisseminateFormatException {
		SolrDocument doc = (SolrDocument)nativeItem;	 
		String ret = (String)doc.getFieldValue(xmlField);
		if(ret!=null)
			ret = ret.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");
		else
			throw  new CannotDisseminateFormatException("No METS");
		return ret;
	}

	public List<String> getNeededFields(){
		List<String> fields = new ArrayList<String>();
		if(xmlField!=null)
			fields.add(xmlField);
		return fields;
	}

}
