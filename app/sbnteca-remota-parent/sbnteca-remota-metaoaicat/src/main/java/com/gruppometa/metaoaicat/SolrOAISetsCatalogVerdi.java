package com.gruppometa.metaoaicat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.solr.common.SolrDocument;

import com.gruppometa.culturaitalia.admin.objects.OaiSet;
import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;

public class SolrOAISetsCatalogVerdi extends SolrOAISetsCatalog{

	public SolrOAISetsCatalogVerdi(Properties properties) throws IOException {
		super(properties);
	}
	
	/**
	 * query speciale in oaiset_limiter con limiter = text
	 * update oaiset_limiter set value ='i* AND ((text:"Giuseppe" "Verdi") OR (text:documenti AND text:aziendali
	 *  AND locationStringOnlyBib:archivio\\ storico\\ ricordi\\ \\-\\ milano*))' where id = 5;
	 *
	 *  Update 2018-05-31:
	 *  update oaiset_limiter set value='i* AND (text:Giuseppe  "Verdi") AND -descSourceLevel2:"MagTeca - ICCU - vecchia"' where id = 5;
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	protected ArrayList getSetSpecs(SolrDocument nativeItem) {		
		ArrayList setSpecs = new ArrayList();
		List<OaiSet> solrSets = ObjectFactory.getOaiSets(servletName);
		for (Iterator iterator = solrSets.iterator(); iterator.hasNext();) {
			OaiSet oaiSet = (OaiSet) iterator.next();
			setSpecs.add(oaiSet.getSpec());
		}
		return setSpecs;
	}
}
