package com.gruppometa.metaoaicat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import com.gruppometa.metaoaicat.crosswalk.NeededFields;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.crosswalk.CrosswalkItem;
import ORG.oclc.oai.server.crosswalk.Crosswalks;
import ORG.oclc.oai.server.verb.BadArgumentException;
import ORG.oclc.oai.server.verb.BadResumptionTokenException;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import ORG.oclc.oai.server.verb.IdDoesNotExistException;
import ORG.oclc.oai.server.verb.NoItemsMatchException;
import ORG.oclc.oai.server.verb.NoMetadataFormatsException;
import ORG.oclc.oai.server.verb.NoSetHierarchyException;
import ORG.oclc.oai.server.verb.OAIInternalServerError;
import ORG.oclc.oai.util.OAIUtil;

/**
 * 
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class SolrOAICatalog extends ORG.oclc.oai.server.catalog.AbstractCatalog {

	protected Logger logger = LoggerFactory.getLogger(SolrOAICatalog.class);
	protected String solrUrl;
	protected String setFields;
	protected int maxListSize = 100;
	protected boolean fieldContainsUnderscore = false;
	protected boolean onlyNeededFields = false;
	protected String oai_identifierField = "id";
	protected Properties properties;
	protected boolean solr1Compatibility = true;

	public SolrOAICatalog(Properties properties)  throws IOException {
		this.properties = properties;
		if(properties!=null){
			solrUrl = properties.getProperty("SolrOAICatalog.solrUrl");
			if (solrUrl == null) {
				throw new IllegalArgumentException(
					"SolrOAICatalog.solrUrl is missing from the properties file");
			}
			setFields = properties.getProperty("SolrOAICatalog.setFields");
			if(properties.getProperty("SolrOAICatalog.fieldContainsUnderscore")!=null){
				try{
					fieldContainsUnderscore = Boolean.parseBoolean(
							properties.getProperty("SolrOAICatalog.fieldContainsUnderscore"));
				}
				catch(Exception e){
					logger.error("",e);
				}
			}
			if(properties.getProperty("SolrOAICatalog.onlyNeededFields")!=null){
				try{
					onlyNeededFields = Boolean.parseBoolean(properties.getProperty("SolrOAICatalog.onlyNeededFields"));
				}
				catch(Exception e){
					logger.error("",e);
				}
			}
			if(properties.getProperty("SolrOAICatalog.idField")!=null){
				oai_identifierField = properties.getProperty("SolrOAICatalog.idField");
			}
			if(properties.getProperty("SolrOAICatalog.solr1Compatibility")!=null){
				solr1Compatibility = Boolean.parseBoolean(properties.getProperty("SolrOAICatalog.solr1Compatibility"));
			}
			if(properties.getProperty("SolrOAICatalog.maxListSize")!=null){
				try{
					maxListSize = Integer.parseInt(properties.getProperty("SolrOAICatalog.maxListSize"));
				}
				catch(Exception e){
					logger.error("",e);
				}
			}
		}
	}

	@Override
	public Map listSets() throws NoSetHierarchyException,
			OAIInternalServerError {
		Map listSetsMap = new HashMap();
        ArrayList sets = new ArrayList();
        try{
        	List<String> solrSets = getSetsFromSolr();
        	for (Iterator iterator = solrSets.iterator(); iterator.hasNext();) {
        		String string = (String) iterator.next();
        		sets.add(makeSetSpec(makeSetSpecID(string), string,"descrizione..."));	
        	}
        }
        catch(Exception e){
        	logger.error("listSets",e);
        	throw new OAIInternalServerError(e.getMessage());
        }
        listSetsMap.put("sets", sets.iterator());
		return listSetsMap;
	}
	
	private String makeSetSpecID(String string) {
		if(fieldContainsUnderscore)
			return string;
		if(string==null)
			return null;
		else
			return string.replaceAll(" ", "_");
	}

	protected List<String> getSetsFromSolr() throws IOException, SolrServerException{
		ArrayList<String> ret = new ArrayList<String>();
		if(setFields==null || setFields.trim().length()==0)
			return ret;
		SolrQuery query = new SolrQuery("*:*");
		query.setFields(oai_identifierField);
		query.set("enableCBR", "false");
		query.set("clustering", "false");
		String[] f = setFields.split("\\|");
		for (int i = 0; i < f.length; i++) {
			query.add("facet.field", f[i].trim());			
		}
		// bug: 27-05-2010: why? query.setRows(10000000);
		query.add("facet", "true");
		query.add("facet.mincount", "1");
		query.add("facet.limit", "-1");
		QueryResponse res = getSolrServer().query(query);
		List<FacetField> flds = res.getFacetFields();
		if(flds!=null)
			for (Iterator iterator = flds.iterator(); iterator.hasNext();) {
				FacetField ff = (FacetField) iterator.next();
				List<Count> v = ff.getValues();
				if(v!=null)
					for (Iterator iterator2 = v.iterator(); iterator2.hasNext();) {
						Count count = (Count) iterator2.next();
						ret.add(count.getName());
				}
			}
		return ret;
	}
 
	@Override
	public Map listSets(String resumptionToken)
			throws BadResumptionTokenException, OAIInternalServerError {
		Map listSetsMap = new HashMap();
        ArrayList sets = new ArrayList();
        sets.add(makeSetSpec("prova", "test","Description"));
        listSetsMap.put("sets", sets.iterator());
		return listSetsMap;
	}

	public String makeSetSpec(String setSpec, String setName,  String setDescription){
		 StringBuffer sb = new StringBuffer();
	        sb.append("<set>");
	        sb.append("<setSpec>");
	        sb.append(OAIUtil.xmlEncode(setSpec));
	        sb.append("</setSpec>");
	        sb.append("<setName>");
	        sb.append(OAIUtil.xmlEncode(setName));
	        sb.append("</setName>");
	        if (setDescription != null) {
	            sb.append("<setDescription>");
	            sb.append(OAIUtil.xmlEncode(setDescription));
	            sb.append("</setDescription>");
	        }
	        sb.append("</set>");
	        return sb.toString();
	}
	
	@Override
	public Vector getSchemaLocations(String identifier)
			throws IdDoesNotExistException, NoMetadataFormatsException,
			OAIInternalServerError {
		  Crosswalks crosswalks = getCrosswalks();
          Iterator iterator = crosswalks.iterator();
          Vector schemas = new Vector();
          while (iterator.hasNext()) {
                  Map.Entry entry = (Map.Entry)iterator.next();
                  //String oaiSchemaLabel = (String)entry.getKey();
                  CrosswalkItem crosswalkItem = (CrosswalkItem)entry.getValue();
                  Crosswalk crosswalk = crosswalkItem.getCrosswalk();
			      if(!schemas.contains(crosswalk.getSchemaLocation()))
                  	schemas.add(crosswalk.getSchemaLocation());
          }
          return schemas;
	}
	
	public String getSchemaLocation(String metadataPrefix) {
		  Crosswalks crosswalks = getCrosswalks();
		  return crosswalks.getSchemaLocation(metadataPrefix);
 	}
	

	protected SolrClient getSolrServer() throws MalformedURLException{
		SolrClient server;
		if(solr1Compatibility)
			server = new  HttpSolrClient.Builder(solrUrl)
				.withResponseParser(new XMLResponseParser())
				.build();
		else
			server = new  HttpSolrClient.Builder(solrUrl)
					.build();
		return server;
	}

	@Override
	public Map listIdentifiers(String from, String until, String set,
			String metadataPrefix) throws BadArgumentException,
			CannotDisseminateFormatException, NoItemsMatchException,
			NoSetHierarchyException, OAIInternalServerError {
		return listIdentifiers(0, from, until, set, metadataPrefix);
	} 

	protected void makeNeededFields4Solr(SolrQuery query, boolean withCrosswalks){
		if(onlyNeededFields && getRecordFactory() instanceof SolrRecordFactory) {
			List<String> neededFields = ((SolrRecordFactory) getRecordFactory()).getNeededFields();
			if(withCrosswalks) {
				Enumeration names =  properties.propertyNames();
				while(names.hasMoreElements()){
					String key = (String) names.nextElement();
					if(key.endsWith("xmlField"))
						neededFields.add(properties.getProperty(key));
				}
			}
			query.setFields(neededFields.toArray(new String[neededFields.size()]));
		}
	}

	public Map listIdentifiers(int offset,String from, String until, String set,
			String metadataPrefix) throws BadArgumentException,
			CannotDisseminateFormatException, NoItemsMatchException,
			NoSetHierarchyException, OAIInternalServerError {
		Map listIdentifiersMap = new HashMap();
		ArrayList headers = new ArrayList();
		ArrayList identifiers = new ArrayList();
		int count;
		long recordsFound = 0;
		SolrQuery query = new SolrQuery(
				//"timestamp: [* TO NOW]"
				"*:*"
				);		
		try {
			query.set("enableCBR", "false");
			query.set("clustering", "false");
			query.setStart(offset);
			if((from!=null && from.length()==20 && from.equals(""))||(until!=null && until.length()==20)){
				if(from!=null && from.length()==20 && !from.equals("0001-01-01T00:00:00Z"))
					query.setQuery( query.getQuery()+" AND timestamp:["+q(from)+" TO *]");
				if(until!=null && until.length()==20 && !until.equals("9999-12-31T23:59:59Z"))
					query.setQuery( query.getQuery()+" AND timestamp:[* TO "+q(until)+"]");
			}
			filterSet(set, query, metadataPrefix);
			//query.set("*", "*"); 
			query.setRows(maxListSize);
			makeNeededFields4Solr(query,false);
			QueryResponse response = getSolrServer().query(query, SolrRequest.METHOD.POST);
			recordsFound = response.getResults().getNumFound();
			/* load the headers and identifiers ArrayLists. */
			for (count = 0; count < response.getResults().size(); ++count) {
				SolrDocument nativeItem = response.getResults().get(count);
				/*
				 * Use the RecordFactory to extract header/identifier pairs for
				 * each item
				 */
				// RecordFactory rf = getRecordFactory();
				Iterator setSpecs = getSetSpecs(nativeItem).iterator();
				String[] header = getRecordFactory().createHeader(nativeItem,
						setSpecs);
				headers.add(header[0]);
				identifiers.add(header[1]);
			}
		} catch (Exception e) {
			logger.error("listIdentiifers :",e);
			throw new OAIInternalServerError(e.getMessage());
		}

		if (count == 0) {
			logger.warn("Query no match:"+query.getQuery());
			throw new NoItemsMatchException();
		}
		if (count+offset < recordsFound) {
			logger.debug("Stato:"+(count+offset)+"<"+ recordsFound);
			// String resumptionId = getResumptionId();
			// resumptionResults.put(resumptionId, rs);

			StringBuffer resumptionTokenSb = makeResumptionToken(from, until,
					set, metadataPrefix, count+offset);

			/*****************************************************************
			 * Use the following line if you wish to include the optional
			 * resumptionToken attributes in the response. Otherwise, use the
			 * line after it that I've commented out.
			 *****************************************************************/
			listIdentifiersMap.put("resumptionMap",
					getResumptionMap(resumptionTokenSb.toString(), (int)recordsFound, offset));
			
		}
		// listIdentifiersMap.put("resumptionMap",
		// getResumptionMap(resumptionTokenSb.toString()));
		listIdentifiersMap.put("headers", headers.iterator());
		listIdentifiersMap.put("identifiers", identifiers.iterator());
		listIdentifiersMap.put("metadataPrefix", metadataPrefix);
		
		return listIdentifiersMap;
	}

	@Override
	public Map listRecords(String from, String until, String set,
			String metadataPrefix) throws BadArgumentException,
			CannotDisseminateFormatException, NoItemsMatchException,
			NoSetHierarchyException, OAIInternalServerError {
        Map listIdentifiersMap = listIdentifiers(from, until, set, metadataPrefix);
    	// bug di oaicat 05/09/2012: va considerato la mappa non il token        
        Map resumptionMap = (Map)listIdentifiersMap.get("resumptionMap");
        Iterator identifiers = (Iterator)listIdentifiersMap.get("identifiers");
        
        Map listRecordsMap = new HashMap();
        ArrayList records = new ArrayList();
        
        while (identifiers.hasNext()) {
            String identifier = (String)identifiers.next();
            try {
                records.add(getRecord(identifier, metadataPrefix));
            } catch (IdDoesNotExistException e) {
                throw new OAIInternalServerError("GetRecord failed to retrieve identifier '"
                        + identifier + "'");
            }
        }
        listRecordsMap.put("records", records.iterator());
        if (resumptionMap != null) {
            //listRecordsMap.put("resumptionToken", resumptionToken);
            listRecordsMap.put("resumptionMap", resumptionMap);
        }
        return listRecordsMap;
	}

	@Override
	public Map listRecords(String resumptionToken)
			throws BadResumptionTokenException, OAIInternalServerError {
        Map listIdentifiersMap = listIdentifiers(resumptionToken);
        Map resumptionMap = (Map)listIdentifiersMap.get("resumptionMap");
        Iterator identifiers = (Iterator)listIdentifiersMap.get("identifiers");
        String metadataPrefix = (String)listIdentifiersMap.get("metadataPrefix");
        
        Map listRecordsMap = new HashMap();
        ArrayList records = new ArrayList();
        
        while (identifiers.hasNext()) {
            String identifier = (String)identifiers.next();
            try {
                records.add(getRecord(identifier, metadataPrefix));
            } catch (IdDoesNotExistException e) {
                throw new OAIInternalServerError("GetRecord failed to retrieve identifier '"
                        + identifier + "'");
            } catch (CannotDisseminateFormatException e) {
                // someone cheated
                throw new BadResumptionTokenException();
            }
        }
        listRecordsMap.put("records", records.iterator());
        if (resumptionMap != null) {
            listRecordsMap.put("resumptionMap", resumptionMap);
        }
        return listRecordsMap;
	}

	protected void filterSet(String set, SolrQuery query, String metadataPrefix) throws ORG.oclc.oai.server.verb.BadArgumentException,
	CannotDisseminateFormatException{
		if(set!=null && set.trim().length()>0 && setFields!=null){
			String[] f = setFields.split("\\|");
			String setQuery ="";
			for (int i = 0; i < f.length; i++) {
				if(setQuery.length()>0)
					setQuery += " OR ";
				setQuery += " "+f[i]+":\""+q(unmakeSpecID(set))+"\"";
			}
			if(setQuery.trim().length()>0)
				query.setQuery( query.getQuery()+ " AND ("+setQuery+")" );
		}
	}

	private String unmakeSpecID(String set) {
		if(fieldContainsUnderscore)
			return set;
		if(set==null)
			return null;
		else
			return set.replaceAll("_", " ");
	}

	protected String q(String str) {
		if(str==null)
			return str;
		return str.replaceAll("\\\\", "\\\\\\\\")
				.replaceAll("\\:", "\\\\\\:")
				.replaceAll("\\(", "\\\\\\(")
				.replaceAll("\\)", "\\\\\\)")
				.replaceAll("\\[", "\\\\\\[")
				.replaceAll("\\]", "\\\\\\]")
				// bug 14-09-2012: quote anche lo spazio
				.replaceAll("\\ ", "\\\\\\ ")
				;
	}

	protected StringBuffer makeResumptionToken(String from, String until,
			String set, String metadataPrefix, int count)
			throws OAIInternalServerError {
		/*****************************************************************
		 * Construct the resumptionToken String however you see fit.
		 *****************************************************************/
		StringBuffer resumptionTokenSb = new StringBuffer();
		// resumptionTokenSb.append(resumptionId);
		// resumptionTokenSb.append("!");
		resumptionTokenSb.append(from);
		resumptionTokenSb.append("!");
		resumptionTokenSb.append(until);
		resumptionTokenSb.append("!");
		if (set == null)
			resumptionTokenSb.append(".");
		else
			try {
				resumptionTokenSb.append(URLEncoder.encode(set, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new OAIInternalServerError(e.getMessage());
			}
		resumptionTokenSb.append("!");
		resumptionTokenSb.append(Integer.toString(count));
		resumptionTokenSb.append("!");
		resumptionTokenSb.append(metadataPrefix);
		return resumptionTokenSb;
	}

	protected ArrayList getSetSpecs(SolrDocument nativeItem) {
		ArrayList setSpecs = new ArrayList();
		String[] f = setFields.split("\\|");
		for (int  i=0; f!=null && i < f.length;  i++) {
			Collection coll =  nativeItem.getFieldValues(f[i].trim());
			if(coll!=null){
				for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
					Object object = iterator.next();
					setSpecs.add(makeSetSpecID((String)object));	
				}
			}			
		}		
		return setSpecs;
	}
	
	protected String[] decodeResumptionToken(String resumptionToken) throws BadResumptionTokenException{
		if(resumptionToken==null)
			return null;
		String[] tokens = resumptionToken.split("\\!");
		if(tokens.length!=5)
			throw new BadResumptionTokenException();
		if(tokens[2].equals("."))
			tokens[2] = null;
		else{
			try {
				tokens[2] = URLDecoder.decode(tokens[2],"UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("",e);
				throw new BadResumptionTokenException();
			}
		}
		return tokens;
	}

	@Override
	public Map listIdentifiers(String resumptionToken)
			throws BadResumptionTokenException, OAIInternalServerError {
		if(resumptionToken==null)
			return null;
		String[] tokens = decodeResumptionToken(resumptionToken);		
		try {
			return listIdentifiers(Integer.parseInt(tokens[3]),tokens[0],tokens[1],tokens[2],tokens[4]);
		} catch(NoItemsMatchException e){
			throw new BadResumptionTokenException();		
		} catch (Exception e) {
			logger.error("",e.getMessage());
			throw new OAIInternalServerError(e.getMessage());
		}
	}

	@Override
	public String getRecord(String identifier, String metadataPrefix)
			throws IdDoesNotExistException, CannotDisseminateFormatException,
			OAIInternalServerError {
		return constructRecord(getSolrDocument(identifier, metadataPrefix), metadataPrefix);
	}

	protected String getMetadataPrefixQuery(String metadataPrefix){
		return "";
	}

	protected SolrDocument getSolrDocument(String identifier, String metadataPrefix) throws OAIInternalServerError, IdDoesNotExistException,
			CannotDisseminateFormatException{
		identifier = getRecordFactory().fromOAIIdentifier(identifier);
		SolrQuery query = new SolrQuery(oai_identifierField+":"+q(identifier));
		query.set("enableCBR", "false");
		query.set("clustering", "false");
		makeNeededFields4Solr(query, true);
		QueryResponse response;
		SolrQuery query2 = null;
		QueryResponse response2 = null;
		String query4Set = getMetadataPrefixQuery(metadataPrefix);
		if(query4Set.length()>0) {
			query2 = new SolrQuery(oai_identifierField+":" + q(identifier)+query4Set);
			query2.setFields(oai_identifierField);
			query2.set("enableCBR", "false");
			query2.set("clustering", "false");
		}
		try {
			response = getSolrServer().query(query);
			if(query2!=null)
				response2 = getSolrServer().query(query2);
		} catch (Exception e) {
			throw new OAIInternalServerError(e.getMessage());
		}
		if(response2!=null && response2.getResults().size()==0 && response.getResults().size()>0){
			throw new CannotDisseminateFormatException(metadataPrefix);
		}
		if(response.getResults().size()==0){
			throw new IdDoesNotExistException(identifier);
		}
		SolrDocument nativeItem = response.getResults().get(0);
		return nativeItem;
	}

	protected String constructRecord(SolrDocument nativeItem, String metadataPrefix)
			    throws CannotDisseminateFormatException, OAIInternalServerError {
			        String schemaURL = null;
			        ArrayList setSpecs = getSetSpecs(nativeItem);
			        Iterator abouts = getAbouts(nativeItem);
			        makeConstantFields(nativeItem, setSpecs.iterator());			        
			        if (metadataPrefix != null) {
			            if ((schemaURL = getCrosswalks().getSchemaURL(metadataPrefix)) == null)
			                throw new CannotDisseminateFormatException(metadataPrefix);
			        }
			        // 29-11-2016 senza setspec va bene
			        if(setSpecs.size()>0 && !matchMetadataPrefix(metadataPrefix, setSpecs.iterator()))
			            throw new CannotDisseminateFormatException(metadataPrefix);
			        return getRecordFactory().create(nativeItem, schemaURL, metadataPrefix, setSpecs.iterator(), abouts);
    }
	 	
	protected boolean matchMetadataPrefix(String metadataPrefix, Iterator setSpecs) {
		return true;
	}

	protected void makeConstantFields(SolrDocument nativeItem, Iterator setSpecs) {		
	}

	protected Iterator getAbouts(SolrDocument nativeItem) {
		ArrayList abouts = new ArrayList();
		return abouts.iterator();
	}

	@Override
	public void close() {

	}
}
