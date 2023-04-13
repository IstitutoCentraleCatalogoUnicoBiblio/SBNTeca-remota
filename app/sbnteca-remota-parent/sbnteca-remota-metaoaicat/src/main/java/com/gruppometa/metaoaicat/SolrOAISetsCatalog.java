package com.gruppometa.metaoaicat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.gruppometa.culturaitalia.admin.objects.OaiSet;
import com.gruppometa.culturaitalia.admin.objects.OaiSetConstant;
import com.gruppometa.culturaitalia.admin.objects.OaiSetProfile;
import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;

import ORG.oclc.oai.server.crosswalk.Crosswalks;
import ORG.oclc.oai.server.verb.BadArgumentException;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import ORG.oclc.oai.server.verb.IdDoesNotExistException;
import ORG.oclc.oai.server.verb.NoMetadataFormatsException;
import ORG.oclc.oai.server.verb.NoSetHierarchyException;
import ORG.oclc.oai.server.verb.OAIInternalServerError;

public class SolrOAISetsCatalog extends SolrOAICatalog{

	protected String servletName = null;
	protected Properties properties;
	protected String FILTER_FOR_METADATAPREFIX_PREF = "SolrOAICatalog.metadataFilter.";
    protected String ALL_QUERY = "SolrOAICatalog.allQuery";
	public SolrOAISetsCatalog(Properties properties) throws IOException {
		super(properties);
		servletName = properties.getProperty("servletName");
		this.properties = properties;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map listSets() throws NoSetHierarchyException,
			OAIInternalServerError {
		Map listSetsMap = new HashMap();
        ArrayList sets = new ArrayList();
        try{
        	List<OaiSet> solrSets = ObjectFactory.getOaiSets(servletName);
        	for (Iterator iterator = solrSets.iterator(); iterator.hasNext();) {
        		OaiSet set = (OaiSet) iterator.next();
        		sets.add(makeSetSpec(set.getSpec(),set.getName(), set.getDescription_it()));	
        	}
        }
        catch(Exception e){
        	logger.error("listSets",e);
        	throw new OAIInternalServerError(e.getMessage());
        }
        listSetsMap.put("sets", sets.iterator());
		return listSetsMap;
	}

	@Override
	protected String getMetadataPrefixQuery(String metadataPrefix){
		String ret = "";
		if(metadataPrefix!=null && properties.getProperty(FILTER_FOR_METADATAPREFIX_PREF+metadataPrefix)!=null)
			return " AND ("+properties.getProperty(FILTER_FOR_METADATAPREFIX_PREF+metadataPrefix)+")";
		return ret;
	}
	
	@Override
	protected void filterSet(String set, SolrQuery query, String metadataPrefix) throws BadArgumentException,
		CannotDisseminateFormatException{
		OaiSet oaiset = ObjectFactory.getOaiSet(set, servletName);  
		if(oaiset!=null){
			if(match( oaiset, metadataPrefix)){
				String setQuery = makeFilter4Set(query, oaiset);
				if(setQuery.trim().length()>0)
					query.setQuery( query.getQuery()+ " AND ("+setQuery+")" + getMetadataPrefixQuery(metadataPrefix));
			}
			else{
				throw new CannotDisseminateFormatException(metadataPrefix);
			}
		}
        /**
         * nuovo: SolrOAICatalog.allQuery per definire una quey generale
         */
		else if(properties.getProperty(ALL_QUERY)!=null && properties.getProperty(ALL_QUERY).length()>0){
            query.setQuery( query.getQuery()+ " AND ("+properties.getProperty(ALL_QUERY)+")" + getMetadataPrefixQuery(metadataPrefix));
        }
		else{
			List<OaiSet> sets = ObjectFactory.getOaiSets(servletName);
			String setQuery = "";
			boolean foundMatch = false; 
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = sets.iterator(); iterator
					.hasNext();) {
				OaiSet oaiSet2 = (OaiSet) iterator.next();
				if(match(oaiSet2, metadataPrefix)){
					String q = makeFilter4Set(query, oaiSet2);
					if(setQuery.length()>2)
						setQuery += " OR ("+q+")";
					else
						setQuery = q;
					foundMatch = true;
				}
			}
			if(!foundMatch){
				logger.debug("servletName="+servletName + " sets="+sets.size() + " metadataPrefix="+metadataPrefix+
						" = " + ((sets.size()==1 && sets.get(0).getProfiles()!=null && sets.get(0).getProfiles().size()>0)
								?(sets.get(0).getProfiles().get(0)):""));
				throw new CannotDisseminateFormatException(metadataPrefix);
			}
			if(setQuery.trim().length()>0)
				query.setQuery( query.getQuery()+ " AND ("+setQuery+")" + getMetadataPrefixQuery(metadataPrefix));
		}
	}

	protected boolean match(OaiSet oaiset, String metadataPrefix) {
		if(oaiset==null)
			return false;
		if(oaiset.getProfiles()==null || oaiset.getProfiles().size()==0)
			return true;
		for(OaiSetProfile profile: oaiset.getProfiles()) {
			if(profile.getName().equalsIgnoreCase(metadataPrefix))
				return true;
		} 
		return false;
	}

	protected String makeFilter4Set(SolrQuery query, OaiSet oaiset) {
		String[] f = oaiset.getFields();
		String[] v = oaiset.getValues(); 
		String setQuery ="";
		for (int i = 0; i < f.length; i++) { 
			if(setQuery.length()>0)
				setQuery += " AND ";
			String[] vs = v[i].split("\\|"); 
			String setQueryInner ="";
			for (int j = 0; j < vs.length; j++) {
				if(setQueryInner.length()>0)
					setQueryInner += " OR ";
				setQueryInner += " "+f[i]+":" +
					(vs[j].contains("*")?
						(vs[j]):
						("\""+q(vs[j])+"\"")) +
								"";	
			}
			setQuery += "("+setQueryInner+")";
		}
		if(oaiset.getSolrquery()!=null && oaiset.getSolrquery().length()>0){
			if(setQuery.length()>0)
				setQuery +=" AND ";
			setQuery+=oaiset.getSolrquery();
		}
		return setQuery;
		//logger.debug("SolrQuery: "+query.getQuery());
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	protected ArrayList getSetSpecs(SolrDocument nativeItem) {		
		ArrayList setSpecs = new ArrayList();
		List<OaiSet> solrSets = ObjectFactory.getOaiSets(servletName);
		for (Iterator iterator = solrSets.iterator(); iterator.hasNext();) {
			OaiSet oaiSet = (OaiSet) iterator.next();
			if(isPartOf(nativeItem, oaiSet) && matchSolrQuery(nativeItem, oaiSet))
				if(StringUtils.isEmpty(oaiSet.getSpec())){
					logger.error("setspec is empty for set with name '" + oaiSet.getName()+ "'.");
				}
				else
					setSpecs.add(oaiSet.getSpec());
		}
		return setSpecs;
	}
	
	

	protected boolean matchSolrQuery(SolrDocument nativeItem, OaiSet oaiSet) {
		if(oaiSet.getSolrquery()==null || oaiSet.getSolrquery().length()==0)
			return true;
		else{
			String id = getRecordFactory().getOAIIdentifier(nativeItem);
			String identifier = getRecordFactory().fromOAIIdentifier(id);
			SolrQuery query = new SolrQuery(oai_identifierField+":"+q(identifier)+
			   " AND ("+oaiSet.getSolrquery()+")");
            // 29-11-2016: basta l'id
            query.setFields(oai_identifierField);
			query.set("enableCBR", "false");
			query.set("clustering", "false");
			QueryResponse response;
			try {
				response = getSolrServer().query(query);
			} catch (Exception e) {
				logger.error("",e);
				return false;
			}
			if(response.getResults().size()==0){
				logger.debug("Id/"+oai_identifierField+" " +identifier+" not match "+oaiSet.getSpec());
				return false;
			}
			else
				return true;
		}
	}

	protected ArrayList<List<OaiSetProfile>> getSetProfiles(SolrDocument nativeItem) {		
		ArrayList<List<OaiSetProfile>> setSpecs = new ArrayList<List<OaiSetProfile>>();
		List<OaiSet> solrSets = ObjectFactory.getOaiSets(servletName);
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = solrSets.iterator(); iterator.hasNext();) {
			OaiSet oaiSet = (OaiSet) iterator.next();
			if(isPartOf(nativeItem, oaiSet))
				setSpecs.add(oaiSet.getProfiles());
		}
		return setSpecs;
	}
	
	public static String getEdmType(SolrDocument nativeItem, String servletName) {		
		String ret = null;
		List<OaiSet> solrSets = ObjectFactory.getOaiSets(servletName);
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = solrSets.iterator(); iterator.hasNext();) {
			OaiSet oaiSet = (OaiSet) iterator.next();
			if(oaiSet.getType()!=null && oaiSet.getType().length()>0 && isPartOf(nativeItem, oaiSet)){
				if(ret==null)
					ret = oaiSet.getType();
				else // ci sono più di un set/tipo per il record....
					return null;
			}
		}
		return ret;
	}
	public static boolean isPartOf(SolrDocument nativeItem, OaiSet oaiSet) {
		boolean ret = true;
		String[] f = oaiSet.getFields();
		String[] v = oaiSet.getValues();
		for (int i = 0; i < f.length; i++) {
			String[] vs = v[i].split("\\|");
			boolean match = false;
			Collection<Object> values =  nativeItem.getFieldValues(f[i]);
			for (int j = 0; j < vs.length; j++) {
				if(match(values,vs[j]))
					match = true;
			}
			if(!match)
				return false;
		}
		return ret;
	}
	protected static String lim = "\" \"";
	
	protected static boolean match(Collection<Object> values, String string) {
		if(values==null)
			return false;
		if(values.contains(string))
			return true;
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = values.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object instanceof String) {
				String str = (String) object;
				if(string.contains("*") && str.contains(unquote(string)))
					return true;	
				if(string.contains(lim)){
					//int pos = string.indexOf(lim);
					//if(str.contains(unquote(string.substring(0,pos)))
					//		&& str.contains(unquote(string.substring(pos+lim.length()))))
						return true;
				}
										
			}
		}
		return false;
	}

	protected static String unquote(String string) {
		if(string.contains("*"))
			return string.replace("*", "").replace("\\","");
		return string;
	}

	public static final String FIELD_SEPARATOR = "|";
	public static final String FIELD_SEPARATOR_REGEX = "\\|";
	@Override
	protected void makeConstantFields(SolrDocument nativeItem, @SuppressWarnings("rawtypes") Iterator setSpecs) {
		if(setSpecs==null)
			return;
		while(setSpecs.hasNext()){
			String setspec = (String)setSpecs.next();
			OaiSet set = ObjectFactory.getOaiSet(setspec, servletName);
			if(set.getConstants()!=null)
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = set.getConstants().iterator(); iterator
						.hasNext();) {
					OaiSetConstant constant = (OaiSetConstant) iterator.next();
					nativeItem.addField("constant", constant.getFieldName()+FIELD_SEPARATOR+constant.getValue());
				}
		}
	}

	@Override
	protected boolean matchMetadataPrefix(String metadataPrefix,
			@SuppressWarnings("rawtypes") Iterator setSpecs) {
		if(setSpecs==null)
			return false;	 	
		while(setSpecs.hasNext()){
			String setspec = (String)setSpecs.next();
			OaiSet set = ObjectFactory.getOaiSet(setspec, servletName);    
			if(match(set, metadataPrefix))
				return true;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Vector getSchemaLocations(String identifier)
			throws IdDoesNotExistException, NoMetadataFormatsException,
			OAIInternalServerError {
		SolrDocument doc = null;
		try {
			doc = getSolrDocument(identifier,null);
		} catch (CannotDisseminateFormatException e) {
			logger.error("",e);
		}
		ArrayList<List<OaiSetProfile>> sets = getSetProfiles(doc);
		Vector schemas = new Vector();
		for(List<OaiSetProfile> profiles: sets){
			if(profiles==null || profiles.size()==0)
				return super.getSchemaLocations(identifier);
			else{
				for(OaiSetProfile profile: profiles){
					String schema = getSchemaLocation(profile.getName());
					if(!schemas.contains(schema))
						schemas.add(schema);
				}
			}
		}
		return schemas;
	}

	@Override
	public Crosswalks getCrosswalks() {
		return super.getCrosswalks();
	}

	
}
