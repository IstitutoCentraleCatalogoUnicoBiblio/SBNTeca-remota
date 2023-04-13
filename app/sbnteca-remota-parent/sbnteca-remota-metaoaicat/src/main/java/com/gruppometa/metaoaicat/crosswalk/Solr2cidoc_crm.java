package com.gruppometa.metaoaicat.crosswalk;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import ORG.oclc.oai.server.verb.OAIInternalServerError;
import org.apache.solr.common.SolrDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Solr2cidoc_crm extends SolrFieldXSLT  {

	private String xmlField;

	public Solr2cidoc_crm(Properties properties) throws OAIInternalServerError {
		super( properties,"Solr2cidoc_crm");
		xmlField = properties.getProperty("Solr2cidoc_crm.xmlField");
	}


}
