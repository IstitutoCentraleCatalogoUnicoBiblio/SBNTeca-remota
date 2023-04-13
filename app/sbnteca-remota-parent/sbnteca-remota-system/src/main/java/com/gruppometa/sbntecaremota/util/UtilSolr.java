package com.gruppometa.sbntecaremota.util;

import com.gruppometa.sbntecaremota.objects.DigitalObjectStats;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.SearchData;
import com.gruppometa.sbntecaremota.objects.SearchResultList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lista di metodi di utilità per Solr
 *
 *
 */
public class UtilSolr {
	
	// facet fields search
	private static List<String> facetedFieldSearch = Arrays.asList("project", "collection", "agency", "type", "typeDigitale");

	private static Logger logger = LoggerFactory.getLogger(UtilSolr.class);
	
	/**
	 * Indicizzazione di una lista di MAG
	 * 
	 * @param listMag lista dei MAG
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public static void insertListMag(List<Mag> listMag) throws SolrServerException, IOException {
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		// HttpSolrServer server = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlSolr"));
		SolrInputDocument solrDoc = null;
		int j = 0;
		ArrayList<SolrInputDocument> listSolrDocument = new ArrayList<SolrInputDocument>();
		
		for (Mag mag : listMag) {
			solrDoc = createSolrDocument(mag);
			listSolrDocument.add(solrDoc);
			j++;
			
			if(j == 50) {
				server.add(listSolrDocument);
			    server.commit();			
			    listSolrDocument.clear();
				j = 0;			
			}
		}
		
		if (j > 0) {
			server.add(listSolrDocument);
			server.commit();
			listSolrDocument.clear();
		}
		
		server.close();
	}
	
	/**
	 * Creazione documento Solr da indicizzare
	 * 
	 * @param mag bean contenente i metadati del MAG
	 * @return SolrInputDocument documento Solr per indicizzazione
	 */
	public static SolrInputDocument createSolrDocument(Mag mag) {	
		mag.setTimestamp(new Date(System.currentTimeMillis()));
		mag.setTimestampCreated(mag.getTimestamp());
		
		SolrInputDocument solrDoc = new SolrInputDocument();
		solrDoc.addField("id", mag.getIdMag());
		
		if(mag.getOaiIdentifier() != null)
			solrDoc.addField("oai_identifier", mag.getOaiIdentifier());
		
		if(mag.getProject() != null)
			solrDoc.addField("project", mag.getProject());
		
		if(mag.getPath() != null)
			solrDoc.addField("path", mag.getPath());
		
		solrDoc.addField("deleted", mag.isDeleted() ? 1 : 0);
		solrDoc.addField("draft", mag.isDraft() ? 1 : 0);
		solrDoc.addField("timestamp", mag.getTimestamp());
		solrDoc.addField("timestampCreated", mag.getTimestampCreated());
		
		if(mag.getMagProject() != null)
			solrDoc.addField("magxmlProject", mag.getMagProject());
		
		if(mag.getMagOriginal() != null)
			solrDoc.addField("magxmlOriginal", mag.getMagOriginal());

		if(mag.getMetsOriginal() != null)
			solrDoc.addField("metsxmlOriginal", mag.getMetsOriginal());

		if(mag.getMetsExternal() != null)
			solrDoc.addField("metsxmlExternal", mag.getMetsExternal());

		if(mag.getMagInternal() != null)
			solrDoc.addField("magxmlInternal", mag.getMagInternal());
		
		if(mag.getMagExternal() != null)
			solrDoc.addField("magxmlExternal", mag.getMagExternal());
		
		if(mag.getMagTot() != null)
			solrDoc.addField("magxmlTotale", mag.getMagTot());
		
		solrDoc.addField("publicFlag", mag.getPublishFlag());
		
		if(mag.getCreation() != null)
			solrDoc.addField("creationString", mag.getCreation());
		
		if(mag.getLastUpdate() != null)
			solrDoc.addField("lastUpdateString", mag.getLastUpdate());
		
		if(mag.getCollection() != null)
			solrDoc.addField("collection", mag.getCollection());
		
		if(mag.getAgency() != null)
			solrDoc.addField("agency", mag.getAgency());
		
		if(mag.getStprog() != null)
			solrDoc.addField("stprog", mag.getStprog());
		
		if(mag.getMagPreview() != null)
			solrDoc.addField("magPreview", mag.getMagPreview());
		
		solrDoc.addField("access_rightsString", mag.getAccessRights() + "");	
		solrDoc.addField("completenessString", mag.getCompleteness() + "");	
		
		if(mag.getLevel() != null)
			solrDoc.addField("levelString", mag.getLevel());
		
		if(mag.getIdJob() != null)
			solrDoc.addField("idJob", mag.getIdJob());
		
		if(mag.getDocumentFormat() != null)
			solrDoc.addField("documentFormat", mag.getDocumentFormat());
		
		solrDoc.addField("numberImg", mag.getNumberImg());
		solrDoc.addField("numberAudio", mag.getNumberAudio());
		solrDoc.addField("numberVideo", mag.getNumberVideo());
		solrDoc.addField("numberDoc", mag.getNumberDoc());
		solrDoc.addField("numberOcr", mag.getNumberOcr());
		
		if(mag.getIssue() != null)
			solrDoc.addField("issue", mag.getIssue());	
		
		if(mag.getYear() != null)
			solrDoc.addField("year", mag.getYear());	
		
		if(mag.getStpiecePer() != null)
			solrDoc.addField("stpiece_per", mag.getStpiecePer());	
		
		if(mag.getPartNumber() != null)
			solrDoc.addField("part_number", mag.getPartNumber());	
		
		if(mag.getPartName() != null)
			solrDoc.addField("part_name", mag.getPartName());	
		
		if(mag.getStpieceVol() != null)
			solrDoc.addField("stpiece_vol", mag.getStpieceVol());	
		
		if(mag.getUsageA() != null) {
			for(String usageA : mag.getUsageA())
				solrDoc.addField("usageInternal", usageA);
		}
		
		if(mag.getUsageE() != null) {
			for(String usageE : mag.getUsageE())
				solrDoc.addField("usageExternal", usageE);
		}
		
		if(mag.getTitles() != null) {
			for(String title : mag.getTitles())
				solrDoc.addField("title", title);
		}
		
		if(mag.getIdentifiers() != null) {
			for(String identifier : mag.getIdentifiers())
				solrDoc.addField("identifier", identifier);
		}
		
		if(mag.getLibraries() != null) {
			for(String library : mag.getLibraries())
				solrDoc.addField("library", library);
		}
		
		if(mag.getInventoryNumbers() != null) {
			for(String inventory : mag.getInventoryNumbers())
				solrDoc.addField("inventory_number", inventory);
		}
		
		if(mag.getShelfmarks() != null) {
			for(String shelfmark : mag.getShelfmarks())
				solrDoc.addField("shelfmark", shelfmark);
		}
		
		if(mag.getCreators() != null) {
			for(String creator : mag.getCreators())
				solrDoc.addField("creator", creator);
		}

		if(mag.getCoverages() != null) {
			for(String coverage : mag.getCoverages())
				solrDoc.addField("coverage", coverage);
		}
		
		if(mag.getSubjects() != null) {
			for(String subject : mag.getSubjects())
				solrDoc.addField("subject", subject);
		}
		
		if(mag.getRelations() != null) {
			for(String relation : mag.getRelations())
				solrDoc.addField("relation", relation);
		}
		
		if(mag.getPublishers() != null) {
			for(String publisher : mag.getPublishers())
				solrDoc.addField("publisher", publisher);
		}
		
		if(mag.getNotDate() != null) {
			for(String notDate : mag.getNotDate())
				solrDoc.addField("not_date", notDate);
		}

		if(mag.getGeoCoord() != null) {
			for(String geo : mag.getGeoCoord())
				solrDoc.addField("geo_coord", geo);
		}
		
		if(mag.getLanguages() != null) {
			for(String language : mag.getLanguages())
				solrDoc.addField("language", language);
		}

		if(mag.getFormats() != null) {
			for(String format : mag.getFormats())
				solrDoc.addField("format", format);
		}
		
		if(mag.getDigitalTypes() != null) {
			for(String digitalType : mag.getDigitalTypes())
				solrDoc.addField("typeDigitale", digitalType);
		}

		if(mag.getMimeTypes() != null) {
			for(String mimeType : mag.getMimeTypes())
				solrDoc.addField("mimeType", mimeType);
		}

		if(mag.getDescriptions() != null) {
			for(String description : mag.getDescriptions())
				solrDoc.addField("description", description);
		}

		if(mag.getTypes() != null) {
			for(String type : mag.getTypes())
				solrDoc.addField("type", type);
		}

		if(mag.getSources() != null) {
			for(String source : mag.getSources())
				solrDoc.addField("source", source);
		}
		
		if(mag.getRights() != null) {
			for(String rights : mag.getRights())
				solrDoc.addField("rights", rights);
		}

		if(mag.getDates() != null) {
			for(String date : mag.getDates())
				solrDoc.addField("date", date);
		}

		if(mag.getContributors() != null) {
			for(String contributor : mag.getContributors())
				solrDoc.addField("contributor", contributor);
		}
		
		if(mag.getStruNomenclatures() != null) {
			for(String nomenclature : mag.getStruNomenclatures())
				solrDoc.addField("nomenclatureStru", nomenclature);
		}
		
		if(mag.getElementNomenclatures() != null) {
			for(String nomenclature : mag.getElementNomenclatures())
				solrDoc.addField("nomenclatureElement", nomenclature);
		}
		
		return solrDoc;
	}
	
	/**
	 * Ricerca i MAG indicizzati per il processo di importazione contrassegnato dal job
	 * 
	 * @param jobID ID del job di imortazione
	 * @return List<String> lista dei percorsi dei MAG
	 * @throws SolrServerException
	 */
	public static List<String> getPathMagsByJob(String jobID) throws SolrServerException {
		// inizializza query solr
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		//HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlSolr"));		
		ModifiableSolrParams params = new ModifiableSolrParams();
		List<String> paths = new ArrayList<String>();
		params.set("q", "idJob:" + jobID);
		params.set("fl", "path");
		
		QueryResponse response;
		try {
			response = solr.query(params);
			SolrDocumentList docList = response.getResults();
			
			// risultati
			for(SolrDocument solrDoc : docList)
				paths.add((String) solrDoc.get("path"));
			
			solr.close();
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return paths;
	}
	
	/**
	 * Restituisce i risultati di una query Solr
	 * 
	 * @param advancedSearch mappe di ricerca avanzata
	 * @param solr client solr
	 * @param start inizio risultati (per paginazione)
	 * @param numResults numero max di risultati
	 * @return
	 */
	public static SearchResultList searchDocument(SearchData advancedSearch, 
			SolrClient solr, int start, int numResults) {
		
		SearchResultList resultList = new SearchResultList();	
	    String query = buildSolrQuery(advancedSearch);
	    String oaiQuery = query.replaceAll("( AND )?deleted:[01]", "");
	    
	    if(oaiQuery.isEmpty())
	    	oaiQuery = "publicFlag:1";
	    
	    else {
	    	if(oaiQuery.contains("publicFlag"))
	    		oaiQuery = oaiQuery.replaceAll("publicFlag:(\\\"[01]\\\"|[01])", "publicFlag:1");
	    	
	    	else
	    		oaiQuery += " AND publicFlag:1";
	    }
	    
	    resultList.setSolrQuery(oaiQuery);
	    ModifiableSolrParams params = new ModifiableSolrParams();
	    params.set("q", query); 
	    params.set("rows", numResults);
	    
	    if(advancedSearch.getSort() != null && !advancedSearch.getSort().isEmpty())
	    	params.set("sort", advancedSearch.getSort());
	    
	    if(start >= 0)
	    	params.set("start", start);
	    
	    String facetSuffix = "Facet";
	    
	    if(advancedSearch.getFacetType() == SearchData.STRING)
	    	facetSuffix = "String";
	    
	    if(advancedSearch.hasFacets()) {
		    params.set("facet", true);
		    params.set("facet.mincount", 1);
		    params.set("facet.sort", "count");
		    params.set("facet.limit", -1);
		    params.add("facet.field", "project" + facetSuffix);
		    params.add("facet.field", "type" + facetSuffix);
		    params.add("facet.field", "typeDigitale" + facetSuffix);
		    params.add("facet.field", "levelString");
			params.add("facet.field", "documentFormat");
			params.add("facet.field", "agency" + facetSuffix);
		    params.add("facet.field", "collection" + facetSuffix);
		    
		    if(numResults > 0) {
			    params.add("facet.field", "library" + facetSuffix);
			    params.add("facet.field", "creator" + facetSuffix);
			    params.add("facet.field", "contributor" + facetSuffix);
			    params.add("facet.field", "publisher" + facetSuffix);
			    params.add("facet.field", "date" + facetSuffix);
			    params.add("facet.field", "subject" + facetSuffix);
			    params.add("facet.field", "language" + facetSuffix);
		    }
	    }
	    
	    if(advancedSearch.getFilterDoc() == SearchData.ID)
	    	params.set("fl", "id,deleted,publicFlag");
	    
	    else if(advancedSearch.getFilterDoc() == SearchData.UPDATE_DOC) {
	    	params.set("fl", "id,deleted,publicFlag,levelString,path,magxmlProject,"
	    			+ "metsxmlExternal,metsxmlOriginal,magxmlOriginal,magxmlInternal,magXmlExternal,magxmlTotale," +
					"documentFormat");
	    }
	    
	    else if(advancedSearch.getFilterDoc() == SearchData.PREVIEW) {
	    	params.set("fl", "id,deleted,identifier,year,issue,part_number,part_name,"
	    			+ "title,project,stprog,collection,agency,access_rightsString,"
	    			+ "completenessString,creator,publisher,subject,description,"
	    			+ "date,type,format,language,library,contributor,"
	    			+ "magPreview,levelString,publicFlag,documentFormat,path,timestamp");
	    }
	    
		try {
			QueryResponse response = solr.query(params, METHOD.POST);
			SolrDocumentList listDocument = response.getResults();
			resultList.setTotalResults(listDocument.getNumFound());
			
			if (listDocument.size() > 0) {
				for(int i = 0; i < listDocument.size(); i++){
					Mag mag = getMagFromQuerySolr(listDocument.get(i));
					resultList.getResults().add(mag);
				}
			}
			
			if(response.getFacetFields() != null) {
				for(FacetField ff : response.getFacetFields()) {
					for(Count count : ff.getValues())
						resultList.addFacetValue(ff.getName(), count.getName(), count.getCount());
				}
			}
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return resultList;
	}
	
	/**
	 * Il metodo converte un documento solr in un oggetto di tipo Mag
	 * 
	 * @param SolrDocument rappresentazioni di mag su solr
	 * @return Mag mag recuperato da Solr, contenente i metadati
	 * */
	private static Mag getMagFromQuerySolr(SolrDocument solrDocument) {
		Mag mag = new Mag();
		mag.setIdMag(solrDocument.getFieldValue("id").toString());
		mag.setDeleted(Integer.parseInt(solrDocument.getFieldValue("deleted").toString()) == 1 ? true : false);
		
		if(solrDocument.getFieldValue("draft") != null)
			mag.setDraft(Integer.parseInt(solrDocument.getFieldValue("draft").toString()) == 1 ? true : false);
		
		mag.setOaiIdentifier(solrDocument.getFieldValue("oai_identifier") != null ? solrDocument.getFieldValue("oai_identifier").toString() : "");
		mag.setTimestamp(solrDocument.getFieldValue("timestamp") != null ? (Date) solrDocument.getFieldValue("timestamp") : null);
		mag.setTimestampCreated(solrDocument.getFieldValue("timestampCreated") != null ? (Date) solrDocument.getFieldValue("timestampCreated") : null);
		mag.setTimestampDeleted(solrDocument.getFieldValue("timestampDeleted") != null ? (Date) solrDocument.getFieldValue("timestampDeleted") : null);
		mag.setProject(solrDocument.getFieldValue("project") != null ? solrDocument.getFieldValue("project").toString() : "");
		mag.setIdJob(solrDocument.getFieldValue("idJob") != null ? solrDocument.getFieldValue("idJob").toString() : "");
		mag.setPublishFlag(solrDocument.getFieldValue("publicFlag") != null ? Integer.parseInt(solrDocument.getFieldValue("publicFlag").toString()) : 0);
		mag.setPath(solrDocument.getFieldValue("path") != null ? solrDocument.getFieldValue("path").toString() : "");
		mag.setDocumentFormat(solrDocument.getFieldValue("documentFormat") != null ? solrDocument.getFieldValue("documentFormat").toString() : "");
		
		mag.setCreation(solrDocument.getFieldValue("creationString") != null ? solrDocument.getFieldValue("creationString").toString() : "");
		mag.setLastUpdate(solrDocument.getFieldValue("lastUpdateString") != null ? solrDocument.getFieldValue("lastUpdateString").toString() : "");
		mag.setStprog(solrDocument.getFieldValue("stprog") == null ? "" : solrDocument.getFieldValue("stprog").toString());
		mag.setAgency(solrDocument.getFieldValue("agency") != null ? solrDocument.getFieldValue("agency").toString() : "");
		mag.setCollection(solrDocument.getFieldValue("collection") != null ? solrDocument.getFieldValue("collection").toString() : "");
		
		
		mag.setAccessRights(solrDocument.getFieldValue("access_rightsString") != null ? 
				Integer.parseInt(solrDocument.getFieldValue("access_rightsString").toString()) : 0);
		
		mag.setCompleteness(solrDocument.getFieldValue("completenessString") != null ? 
				Integer.parseInt(solrDocument.getFieldValue("completenessString").toString()) : 0);
		
		
		if(solrDocument.getFieldValues("levelString") != null) {
			for(Object level : solrDocument.getFieldValues("levelString"))
				mag.setLevel(level.toString());
		}
		
		else
			mag.setLevel(null);

		
		mag.setMagProject(solrDocument.getFieldValue("magxmlProject") != null ? 
				solrDocument.getFieldValue("magxmlProject").toString() : "");
		
		mag.setMagInternal(solrDocument.getFieldValue("magxmlInternal") != null ? 
				solrDocument.getFieldValue("magxmlInternal").toString() : "");
		
		mag.setMagOriginal(solrDocument.getFieldValue("magxmlOriginal") != null ? 
				solrDocument.getFieldValue("magxmlOriginal").toString() : "");

		mag.setMetsOriginal((String)solrDocument.getFieldValue("metsxmlOriginal"));
		mag.setMetsExternal((String)solrDocument.getFieldValue("metsxmlExternal"));

		mag.setMagExternal(solrDocument.getFieldValue("magxmlExternal") != null ? 
				solrDocument.getFieldValue("magxmlExternal").toString() : "");
		
		mag.setMagTot(solrDocument.getFieldValue("magxmlTotale") != null ? 
				solrDocument.getFieldValue("magxmlTotale").toString() : "");
		
		mag.setNumberImg(solrDocument.getFieldValue("numberImg") != null ? 
				Integer.parseInt(solrDocument.getFieldValue("numberImg").toString()) : 0);
		
		mag.setNumberAudio(solrDocument.getFieldValue("numberAudio") != null ? 
				Integer.parseInt(solrDocument.getFieldValue("numberAudio").toString()) : 0);
		
		mag.setNumberVideo(solrDocument.getFieldValue("numberVideo") != null ? 
				Integer.parseInt(solrDocument.getFieldValue("numberVideo").toString()) : 0);
		
		mag.setNumberDoc(solrDocument.getFieldValue("numberDoc") != null ? 
				Integer.parseInt(solrDocument.getFieldValue("numberDoc").toString()) : 0);
		
		mag.setNumberOcr(solrDocument.getFieldValue("numberOcr") != null ?
				Integer.parseInt(solrDocument.getFieldValue("numberOcr").toString()) : 0);
		
		

		if(solrDocument.getFieldValues("magPreview") != null) {
			for(Object magPreview : solrDocument.getFieldValues("magPreview"))
				mag.setMagPreview((String) magPreview); 
		}
		
		if(solrDocument.getFieldValue("issue") != null) {
			for(Object issue : solrDocument.getFieldValues("issue"))
				mag.setIssue(issue.toString());
		}
		
		if(solrDocument.getFieldValue("year") != null) {
			for(Object year : solrDocument.getFieldValues("year"))
				mag.setYear(year.toString());
		}
		
		if(solrDocument.getFieldValue("stpiece_per") != null) {
			for(Object stpiece_per : solrDocument.getFieldValues("stpiece_per"))
				mag.setStpiecePer(stpiece_per.toString());
		}
		
		if(solrDocument.getFieldValue("part_number") != null) {
			for(Object part_number : solrDocument.getFieldValues("part_number"))
				mag.setPartNumber(part_number.toString());
		}
		
		if(solrDocument.getFieldValue("part_name") != null) {
			for(Object part_name : solrDocument.getFieldValues("part_name"))
				mag.setPartName(part_name.toString());
		}
		
		if(solrDocument.getFieldValue("stpiece_vol") != null) {
			for(Object stpiece_vol : solrDocument.getFieldValues("stpiece_vol"))
				mag.setStpieceVol(stpiece_vol.toString());
		}
		
		if(solrDocument.getFieldValues("usageInternal") != null) {
			for(Object usageA : solrDocument.getFieldValues("usageInternal"))
				mag.getUsageA().add((String) usageA); 
		}

		if(solrDocument.getFieldValues("usageExternal") != null) {
			for(Object usageE : solrDocument.getFieldValues("usageExternal"))
				mag.getUsageE().add((String) usageE); 
		}
		
		if(solrDocument.getFieldValues("title") != null) {
			for(Object title : solrDocument.getFieldValues("title"))
				mag.getTitles().add((String) title); 
		}
		
		if(solrDocument.getFieldValues("contributor") != null) {
			for(Object contributor : solrDocument.getFieldValues("contributor"))
				mag.getContributors().add((String) contributor); 
		}
		
		if(solrDocument.getFieldValues("library") != null) {
			for(Object contributor : solrDocument.getFieldValues("library"))
				mag.getLibraries().add((String) contributor); 
		}

		if(solrDocument.getFieldValues("identifier") != null) {
			for(Object identifier : solrDocument.getFieldValues("identifier"))
				mag.getIdentifiers().add((String) identifier); 
		}
		
		if(solrDocument.getFieldValues("inventory_number") != null) {
			for(Object inventory : solrDocument.getFieldValues("inventory_number"))
				mag.getInventoryNumbers().add((String) inventory); 
		}
		
		if(solrDocument.getFieldValues("shelfmark") != null) {
			for(Object shelfmark : solrDocument.getFieldValues("shelfmark"))
				mag.getShelfmarks().add((String) shelfmark); 
		}
		
		if(solrDocument.getFieldValues("creator") != null) {
			for(Object creator : solrDocument.getFieldValues("creator"))
				mag.getCreators().add((String) creator); 
		}
		
		if(solrDocument.getFieldValues("coverage") != null) {
			for(Object coverage : solrDocument.getFieldValues("coverage"))
				mag.getCoverages().add((String) coverage); 
		}

		if(solrDocument.getFieldValues("subject") != null) {
			for(Object subject : solrDocument.getFieldValues("subject"))
				mag.getSubjects().add((String) subject); 
		}
		
		if(solrDocument.getFieldValues("relation") != null) {
			for(Object relation : solrDocument.getFieldValues("relation"))
				mag.getRelations().add((String) relation); 
		}
		
		if(solrDocument.getFieldValues("publisher") != null) {
			for(Object publisher : solrDocument.getFieldValues("publisher"))
				mag.getPublishers().add((String) publisher); 
		}
		
		if(solrDocument.getFieldValues("not_date") != null) {
			for(Object notDate : solrDocument.getFieldValues("not_date"))
				mag.getNotDate().add((String) notDate); 
		}
		
		if(solrDocument.getFieldValues("geo_coord") != null) {
			for(Object geo : solrDocument.getFieldValues("geo_coord"))
				mag.getGeoCoord().add((String) geo); 
		}

		if(solrDocument.getFieldValues("language") != null) {
			for(Object language : solrDocument.getFieldValues("language"))
				mag.getLanguages().add((String) language); 
		}
		
		if(solrDocument.getFieldValues("format") != null) {
			for(Object format : solrDocument.getFieldValues("format"))
				mag.getFormats().add((String) format); 
		}

		if(solrDocument.getFieldValues("typeDigitale") != null) {
			for(Object digitalType : solrDocument.getFieldValues("typeDigitale"))
				mag.getDigitalTypes().add((String) digitalType); 
		}
		
		if(solrDocument.getFieldValues("mimeType") != null) {
			for(Object mimeType : solrDocument.getFieldValues("mimeType"))
				mag.getMimeTypes().add((String) mimeType); 
		}


		if(solrDocument.getFieldValues("description") != null) {
			for(Object description : solrDocument.getFieldValues("description"))
				mag.getDescriptions().add((String) description); 
		}

		if(solrDocument.getFieldValues("type") != null) {
			for(Object type : solrDocument.getFieldValues("type"))
				mag.getTypes().add((String) type); 
		}

		if(solrDocument.getFieldValues("source") != null) {
			for(Object source : solrDocument.getFieldValues("source"))
				mag.getSources().add((String) source); 
		}

		if(solrDocument.getFieldValues("rights") != null) {
			for(Object rights : solrDocument.getFieldValues("rights"))
				mag.getRights().add((String) rights); 
		}

		if(solrDocument.getFieldValues("date") != null) {
			for(Object date : solrDocument.getFieldValues("date"))
				mag.getDates().add((String) date); 
		}
		
		if(solrDocument.getFieldValues("nomenclatureStru") != null) {
			for(Object nomenclature : solrDocument.getFieldValues("nomenclatureStru"))
				mag.getStruNomenclatures().add((String) nomenclature); 
		}

		if(solrDocument.getFieldValues("nomenclatureElement") != null) {
			for(Object nomenclature : solrDocument.getFieldValues("nomenclatureElement"))
				mag.getElementNomenclatures().add((String) nomenclature); 
		}
		
		return mag;
	}
	
	/**
	 * Cancellazione di una lista di ID MAG da Solr
	 * 
	 * @param ids lista di ID MAG da Solr
	 * @return int codice di uscita (0=OK, -1=K0)
	 */
	public static int deleteMag(List<String> ids, boolean commit) {
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		//HttpSolrServer server = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlSolr"));        
		
		try {
			if (ids != null && !ids.isEmpty()){
				server.deleteById(ids);		
				
				if(commit) {
					server.commit(true, true);
					UpdateRequest req = new UpdateRequest();
					req.setAction(UpdateRequest.ACTION.COMMIT, false, false );				  
					UpdateResponse rsp = req.process(server);
					// server.optimize(true, true);	
				}
			}
			
			server.close();
			return 0;
		      			 	  			 	 				  
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);			
		}			
		
		return -1;
	}

	public static String getOaiIdentifier(String idDraft) {
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		//HttpSolrServer server = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlSolr"));
		String ret = null;
		try {
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("id:\""+ClientUtils.escapeQueryChars(idDraft)+"\"");
			QueryResponse queryResponse = server.query(solrQuery);
			if(queryResponse.getResults().getNumFound()>0)
				ret = (String)queryResponse.getResults().get(0).getFirstValue("oai_identifier");
			server.close();
			return ret;

		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public static String getFirstIdentifier(String idDraft) {
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		//HttpSolrServer server = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlSolr"));
		String ret = null;
		try {
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("id:\""+ClientUtils.escapeQueryChars(idDraft)+"\"");
			QueryResponse queryResponse = server.query(solrQuery);
			if(queryResponse.getResults().getNumFound()>0)
				ret = (String)queryResponse.getResults().get(0).getFirstValue("identifier");
			server.close();
			return ret;

		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Cancellazione MAG (aggiornamento Solr)
	 *  
	 * @param ids ID MAG da cancellare
	 * @param server server Solr
	 */
	public static void flagDeleteMag(List<String> ids, SolrClient server) {
		try {
			if(ids != null && !ids.isEmpty()) {
				for(String id : ids) {
					SolrInputDocument inputDoc = new SolrInputDocument();
					inputDoc.addField("id", id);
					inputDoc.addField("timestampDeleted", new Date(System.currentTimeMillis()));
					
					Map<String, Integer> updateDeleted = new HashMap<String, Integer>();
					updateDeleted.put("set", 1);
					inputDoc.addField("deleted", updateDeleted);
	
					Map<String, Date> updateTimestamp = new HashMap<String, Date>();
					updateTimestamp.put("set", new Date(System.currentTimeMillis()));
					inputDoc.addField("timestamp", updateTimestamp);
					
					server.add(inputDoc);
				}
				
				server.commit();			
			}
		      			 	  			 	 				  
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);			
		}
	}

	public static void setFieldSolr(String id, String fieldName, String value, boolean updatetimeStamp) {
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		try {
			SolrInputDocument inputDoc = new SolrInputDocument();
			inputDoc.addField("id", id);

			Map<String, String> update = new HashMap<String, String>();
			update.put("set", value);
			inputDoc.addField(fieldName, update);

			if(updatetimeStamp) {
				Map<String, Date> updateTimestamp = new HashMap<String, Date>();
				updateTimestamp.put("set", new Date(System.currentTimeMillis()));
				inputDoc.addField("timestamp", updateTimestamp);
			}
			server.add(inputDoc);

			server.commit();

		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void setFieldSolrDelivery(String id, String fieldName, String value, boolean updatetimeStamp) {
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		try {
			SolrInputDocument inputDoc = new SolrInputDocument();
			inputDoc.addField("id", id);

			Map<String, String> update = new HashMap<String, String>();
			update.put("set", value);
			inputDoc.addField(fieldName, update);

			if(updatetimeStamp) {
				Map<String, Date> updateTimestamp = new HashMap<String, Date>();
				updateTimestamp.put("set", new Date(System.currentTimeMillis()));
				inputDoc.addField("timestamp", updateTimestamp);
			}
			server.add(inputDoc);

			server.commit();

		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void setFieldSolrDelivery(String id, String fieldName, String value, String fieldName2, String value2, boolean updatetimeStamp) {
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		try {
			SolrInputDocument inputDoc = new SolrInputDocument();
			inputDoc.addField("id", id);

			Map<String, String> update = new HashMap<String, String>();
			update.put("set", value);
			inputDoc.addField(fieldName, update);

			Map<String, String> update2 = new HashMap<String, String>();
			update2.put("set", value2);
			inputDoc.addField(fieldName2, update2);

			if(updatetimeStamp) {
				Map<String, Date> updateTimestamp = new HashMap<String, Date>();
				updateTimestamp.put("set", new Date(System.currentTimeMillis()));
				inputDoc.addField("timestamp", updateTimestamp);
			}
			server.add(inputDoc);

			server.commit();

		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Pubblicazione MAG (aggiornamento Solr)
	 * 
	 * @param idMap mappa ID, con chiave ID MAG da aggiornare e valore costituito dal flag di pubblicazione
	 * @param solrServer server solr
	 */
	public static void publicateMag(Map<String, Integer> idMap, SolrClient solrServer) {
		try {
			if(idMap != null && !idMap.isEmpty()) {
				for(Entry<String, Integer> entry : idMap.entrySet()) {
					String id = entry.getKey();
					int publicFlag = entry.getValue();
					SolrInputDocument inputDoc = new SolrInputDocument();
					inputDoc.addField("id", id);

					Map<String, Date> updateTimestamp = new HashMap<String, Date>();
					updateTimestamp.put("set", new Date(System.currentTimeMillis()));
					inputDoc.addField("timestamp", updateTimestamp);

					Map<String, Integer> updatePublicFlag = new HashMap<String, Integer>();
					updatePublicFlag.put("set", publicFlag);
					inputDoc.addField("publicFlag", updatePublicFlag);
					
					solrServer.add(inputDoc);
				}
				
				solrServer.commit();
			}
		      			 	  			 	 				  
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);			
		}
	}

	/**
	 * Restituzione dei emtadati di un MAG da Solr dato l'ID
	 * 
	 * @param identifier identifier DC MAG
	 * @param issue issue piece MAG
	 * @param year year piece MAG
	 * 
	 * @return List<Mag> lista oggetti MAG
	 */
	public static List<Mag> selectDocumentByIdentifier(String identifier, String issue, String year) {		
		List<Mag> results = new ArrayList<Mag>();	
		
		if (identifier != null && !identifier.isEmpty()) {	
			SearchData advancedSearch = new SearchData();
			advancedSearch.getBaseSearch().put("identifier", Arrays.asList(identifier));
			
			if(issue != null && !issue.isEmpty())
				advancedSearch.getBaseSearch().put("issue", Arrays.asList(issue));
			
			if(year != null && !year.isEmpty())
				advancedSearch.getBaseSearch().put("year", Arrays.asList(year));
			
			advancedSearch.setIncludeDeleted(SearchData.EXCLUDE);
			advancedSearch.setIncludeDrafts(SearchData.EXCLUDE);
			advancedSearch.setFacets(false);
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();	
			
			List<Mag> searchResults = searchDocument(advancedSearch, solr, 0, Integer.MAX_VALUE).getResults();
			
			if(searchResults != null) {
				for(Mag mag : searchResults) {
					if(identifier.equals(mag.getIdentifiers().get(0)))
						results.add(mag);
				}
			}
			
			try {
				solr.close();
				
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}	
		
		return results;
	}
	
	/**
	 * Restituzione dei emtadati di un MAG da Solr dato l'ID
	 * 
	 * @param idDocument ID MAG su Solr
	 * @return Mag oggetto MAG
	 */
	public static Mag selectDocumentById(String idDocument) {		
		List<Mag> listMag = null;	
		
		if (idDocument != null && !idDocument.equals("")) {	
			SearchData advancedSearch = new SearchData();
			advancedSearch.getFilteredIDs().add(idDocument);
			advancedSearch.setIncludeDeleted(SearchData.INCLUDE);
			advancedSearch.setIncludeDrafts(SearchData.INCLUDE);
			advancedSearch.setFacets(false);
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();	
			
			listMag = searchDocument(advancedSearch, solr, 0, 1).getResults();
			
			if (listMag != null && listMag.size() > 0)
				return listMag.get(0);	
			
			try {
				solr.close();
				
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}	
		
		return null;
	}
	public static Mag selectDocumentByPath(String path) {
		return selectDocumentByPath(path, "");
	}
	public static Mag selectDocumentByPath(String path, String fields) {
		List<Mag> listMag = null;

		if (path != null && !path.equals("")) {
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();

			SolrQuery solrQuery = new SolrQuery("path:"+ClientUtils.escapeQueryChars(path));
			solrQuery.setRows(2);
			solrQuery.setFields("id,metsxmlExternal"+fields
			//		+",magxmlExternal"
			);
			try {
				QueryResponse solrResponse = solr.query(solrQuery);
				Mag mag = null;
				for (SolrDocument document: solrResponse.getResults()){
					mag = new Mag();
					mag.setIdMag((String) document.get("id"));
					mag.setMetsExternal((String) document.get("metsxmlExternal"));
					mag.setMagOriginal((String) document.get("magxmlOriginal"));
					//mag.setMagExternal((String) document.get("magxmlExternal"));
				}
				solr.close();
				return mag;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * Esegue una query Solr per ottenere l'ID del documento MAG, dato il path 
	 * 
	 * @param magPath path del documento MAG
	 * @return String ID del documento MAG
	 * @throws SolrServerException
	 */
	public static String getMagIDFromPath(String magPath) throws SolrServerException {
		return getMagIDFromPath(magPath, "path", "UrlSolr");
	}

	public static String getMagIDFromVfsPath(String magPath) throws SolrServerException {
		return getMagIDFromPath(magPath, "vfs_path", "UrlDeliverySolr");
	}

	public static String getMagIDFromPath(String magPath, String pathFieldname, String solrInstance) throws SolrServerException {
		// inizializza query solr
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty(solrInstance)).build();
		//HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlSolr"));		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", pathFieldname+":\"" + magPath + "\"");
		params.set("fl", "id");
		
		try {
			QueryResponse response = solr.query(params);
			SolrDocumentList docList = response.getResults();

			// risultato
			String id = docList.isEmpty() ? null : (String) docList.get(0).get("id");
			solr.close();
			return id;
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static String getValueByField(String value, String fieldname, String fieldReturn, String solrInstance) throws SolrServerException {
		// inizializza query solr
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty(solrInstance)).build();
		//HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlSolr"));
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", fieldname+":" + ClientUtils.escapeQueryChars(value) + "");
		params.set("fl", fieldReturn);

		try {
			QueryResponse response = solr.query(params);
			SolrDocumentList docList = response.getResults();

			// risultato
			String id = docList.isEmpty() ? null : (String) docList.get(0).get(fieldReturn);
			solr.close();
			return id;

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Esegue una query Solr per ottenere il conteggio dei valori distinti per un campo, eventualmente filtrato
	 * 
	 * @param fieldQuery nome campo query
	 * @param valueQuery valore campo query
	 * @param tokenize tokenizza il valore specificato
	 * @param facet nome campo faccetta
	 * @param facetValueSize numero di valori per ogni faccetta 
	 * @param sort ordinamento risultati
	 * @return SearchResultList lista risultati (solo faccette)
	 * @throws SolrServerException
	 */
	public static SearchResultList getCount(String fieldQuery, String valueQuery, boolean tokenize,
			List<String> facets, int facetValueSize, String sort) throws SolrServerException {
		
		// inizializza query solr
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlSolr"));	
		
		String correctedValueQuery = null;
		
		if(!tokenize)
			correctedValueQuery = quoteString(valueQuery);
		
		else {
			List<String> tokens = getSolrTokens(valueQuery);
			correctedValueQuery = tokens.size() > 1 ? "(" : "";
			boolean init = true;
			
			for(String token : tokens) {
				if(init)
					init = false;
				
				else
					correctedValueQuery += " AND ";
				
				correctedValueQuery += token;
			}
			
			correctedValueQuery += tokens.size() > 1 ? ")" : "";
		}
		
		String solrQuery = (!fieldQuery.isEmpty() ? 
				fieldQuery + ":" + correctedValueQuery + " AND " : "") +  
				"(draft:0 OR -draft[* TO *]) AND deleted:0";
		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", solrQuery);
		params.set("facet", true);
		params.set("facet.query", solrQuery);
		params.set("facet.sort", sort);
		params.set("facet.mincount", 1);
		params.set("facet.limit", facetValueSize);
		params.set("rows", 0);
		
		for(String facet : facets)
			params.add("facet.field", facet);
		
		SearchResultList results = new SearchResultList();
		
		try {
			QueryResponse response = solr.query(params, METHOD.POST);
			
			results.setSolrQuery(solrQuery);
			List<FacetField> solrFacets = response.getFacetFields();
			
			for(FacetField ff : solrFacets)
				for(Count count : ff.getValues())
					results.addFacetValue(ff.getName(), count.getName(), count.getCount());
			
			solr.close();
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return results;
	}
	
	/**
	 * Costruisce dinamicamente la query Solr basandaosi su una mappa avente come chiave il 
	 * nome del campo Solr per valori la lista dei possibili valori
	 * 
	 * @param advanced mappa dati ricerca avanzata
	 * @return query Solr codificata
	 */
	private static String buildSolrQuery(SearchData advancedSearch) {
		StringBuilder query = new StringBuilder();
		
		// ricerca base
		for(Entry<String, List<String>> entry : advancedSearch.getBaseSearch().entrySet()) {
			if(entry.getValue().size() == 0)
				continue;
			
			if(query.length() > 0)
				query.append(" AND ");
			
			String[] fields = entry.getKey().split("\\/");
			
			if(fields.length == 1) {
				String searchFieldName = facetedFieldSearch.contains(entry.getKey()) ? 
						(entry.getKey() + "Facet") : entry.getKey();
						
				query.append(searchFieldName + ":");
				List<String> tokens = new ArrayList<String>();
				
				if(!advancedSearch.getFacetTypeFields().contains(entry.getKey())) {
					for(String value : entry.getValue())
						tokens.addAll(getSolrTokens(value));
				}
				
				else {
					for(String value : entry.getValue()) {
						if(value.contains(" "))
							tokens.add(quoteString(value.replace(" ","_")));
						else
							tokens.add(quoteString(value));
					}
				}
				
				if(tokens.size() == 1)
					query.append(tokens.get(0));
				
				else {
					query.append("(");
					boolean init = true;
					boolean operator = false;
					
					for(String value : tokens) {
						if("AND".equals(value) || "OR".equals(value) || "NOT".equals(value)) {
							operator = true;
							query.append(" " + value + " ");
						}

						else {
							if(operator)
								operator = false;
							
							else if(!init)
								query.append(" AND ");
							
							query.append(value);
							
							if(init)
								init = false;
						}
					}
					
					query.append(")");
				}
			}
			
			else {
				List<String> tokens = new ArrayList<String>();
				
				for(String value : entry.getValue())
					tokens.addAll(getSolrTokens(value));
				
				query.append("(");
				
				for(int i = 0; i < fields.length; i++) {
					if(i > 0)
						query.append(" OR ");
					
					query.append(fields[i] + ":");
					
					if(tokens.size() == 1)
						query.append(tokens.get(0));
					
					else {
						query.append("(");
						boolean init = true;
						
						for(String value : tokens) {
							if(init)
								init = false;
							
							else
								query.append(" AND ");
							
							query.append(value);
						}
						
						query.append(")");
					}
				}
				
				query.append(")");
			}
		}
		
		// aggiunta filtri faccette
		if(advancedSearch.getFacetSearch() != null) {
			for(Entry<String, List<String>> entry : advancedSearch.getFacetSearch().entrySet()) {
				if(entry.getValue().size() == 0)
					continue;
				
				if(query.length() > 0)
					query.append(" AND ");
				
				String facetFieldName = entry.getKey().endsWith("Format") || entry.getKey().endsWith("String") || entry.getKey().endsWith("Facet") ?
						entry.getKey() : (entry.getKey() + "Facet");
						
				query.append(facetFieldName + ":");
				List<String> tokens = new ArrayList<String>();
				
				for(String value : entry.getValue())
					tokens.add(quoteString(value));
				
				if(tokens.size() == 1)
					query.append(tokens.get(0));
				
				else {
					query.append("(");
					boolean init = true;
					
					for(String value : tokens) {
						if(init)
							init = false;
						
						else
							query.append(" AND ");
						
						query.append(value);
					}
					
					query.append(")");
				}
			}
		}
		
		// aggiunta intervalli date
		if(advancedSearch.getDateIntervalSearch() != null) {
			for(Entry<String, List<String>> entry : advancedSearch.getDateIntervalSearch().entrySet()) {
				if(entry.getValue().size() != 2)
					continue;
				
				if(query.length() > 0)
					query.append(" AND ");
				
				query.append(entry.getKey() + ":"
						+ "[" + entry.getValue().get(0) + " TO " + entry.getValue().get(1) + "]");
			}
		}
		
		// aggiunta id da filtrare
		if(advancedSearch.getFilteredIDs() != null && !advancedSearch.getFilteredIDs().isEmpty()) {
			if(query.length() > 0)
				query.append(" AND ");
			
			query.append("id:(");
			boolean init = true;
			
			for(String id : advancedSearch.getFilteredIDs()) {
				if(init)
					init = false;
				
				else
					query.append(" OR ");
				
				query.append(ClientUtils.escapeQueryChars(id));
			}
			
			query.append(")");
		}

		// aggiunta id da escludere
		if(advancedSearch.getExcludedIDs() != null && !advancedSearch.getExcludedIDs().isEmpty()) {
			if(query.length() > 0)
				query.append(" AND ");
			
			query.append("-id:(");
			boolean init = true;
			
			for(String id : advancedSearch.getExcludedIDs()) {
				if(init)
					init = false;
				
				else
					query.append(" OR ");
				
				query.append(ClientUtils.escapeQueryChars(id));
			}
			
			query.append(")");
		}
		
		// includi bozze
		switch(advancedSearch.getIncludeDrafts()) {
			case SearchData.ONLY:
				if(query.length() > 0)
					query.append(" AND ");
				
				query.append("draft:1");
				break;
				
			case SearchData.EXCLUDE:
				if(query.length() > 0)
					query.append(" AND ");
				
				query.append("(draft:0 OR -draft[* TO *])");
				break;
		}
		
		// includi cancellati
		switch(advancedSearch.getIncludeDeleted()) {
			case SearchData.ONLY:
				if(query.length() > 0)
					query.append(" AND ");
			
				query.append("deleted:1");
				break;
				
			case SearchData.EXCLUDE:
				if(query.length() > 0)
					query.append(" AND ");
			
				query.append("deleted:0");
				break;
		}
		
		return query.length() == 0 ? "*:*" : query.toString();
	}
	
	/**
	 * Esegue la normalizzazione Solr
	 * 
	 * @param server server Solr
	 * @param magIDs ID dei MAG coinvolti
	 * @param fieldName nome campo di normalizzazione
	 * @param oldValue valore attuale, da sostituire
	 * @param newValue valore, nuovo, da applicare
	 * @throws IOException 
	 * @throws SolrServerException problema con il server Solr
	 */
	public static void normalize(SolrClient server, List<Mag> mags, String fieldName, 
			String oldValue, String newValue) throws SolrServerException, IOException, XPathExpressionException {
		
		if(mags != null && !mags.isEmpty()) {
			List<String> pathXml = NormalizationUtility.getPathXml(fieldName);
			XPathExpression xPathMets = NormalizationUtility.getXPathXmlForMets(fieldName);
			for(Mag magObj : mags) {
				// normalizza campo solr indicizzato
				SolrInputDocument inputDoc = new SolrInputDocument();
				inputDoc.addField("id", magObj.getIdMag());
				inputDoc.addField("timestamp", new Date(System.currentTimeMillis()));
				
				// campo vuoto, aggiungi campo
				if(oldValue == null)
					inputDoc.addField(NormalizationUtility.getSolrOriginalField(fieldName), newValue);
				
				// campo presente, aggiornamento
				else {
					Map<String, List<String>> normalizationField = new HashMap<String, List<String>>();
					normalizationField.put("set", new ArrayList<String>(getNewFieldValues(magObj, fieldName, oldValue, newValue)));
					inputDoc.addField(NormalizationUtility.getSolrOriginalField(fieldName), normalizationField);
				}
				
				// normalizza versione MAG originale
				Map<String, String> projectNormalization = new HashMap<String, String>();
				Document projectDoc = UtilXML.convertStringToDocumentXML(magObj.getMagProject());
				projectDoc = UtilXML.normalizeDocument(projectDoc, pathXml, oldValue, newValue);
				projectNormalization.put("set", UtilXML.convertDocumentToString(projectDoc));
				inputDoc.addField("magxmlProject", projectNormalization);
		
				// normalizza versione MAG originale
				Map<String, String> originalNormalization = new HashMap<String, String>();
				Document originalDoc = UtilXML.convertStringToDocumentXML(magObj.getMagOriginal());
				originalDoc = UtilXML.normalizeDocument(originalDoc, pathXml, oldValue, newValue);
				originalNormalization.put("set", UtilXML.convertDocumentToString(originalDoc));
				inputDoc.addField("magxmlOriginal", originalNormalization);

				// normalizza versione MAG interna
				Map<String, String> internalNormalization = new HashMap<String, String>();
				Document internalDoc = UtilXML.convertStringToDocumentXML(magObj.getMagInternal());
				internalDoc = UtilXML.normalizeDocument(internalDoc, pathXml, oldValue, newValue);
				internalNormalization.put("set", UtilXML.convertDocumentToString(internalDoc));
				inputDoc.addField("magxmlInternal", internalNormalization);
				
				// normalizza versione MAG esterna
				Map<String, String> externalNormalization = new HashMap<String, String>();
				Document externalDoc = UtilXML.convertStringToDocumentXML(magObj.getMagExternal());
				externalDoc = UtilXML.normalizeDocument(externalDoc, pathXml, oldValue, newValue);
				externalNormalization.put("set", UtilXML.convertDocumentToString(externalDoc));
				inputDoc.addField("magxmlExternal", externalNormalization);
				
				// normalizza versione MAG esterna
				Map<String, String> totNormalization = new HashMap<String, String>();
				Document totDoc = UtilXML.convertStringToDocumentXML(magObj.getMagTot());
				totDoc = UtilXML.normalizeDocument(totDoc, pathXml, oldValue, newValue);
				totNormalization.put("set", UtilXML.convertDocumentToString(totDoc));
				inputDoc.addField("magxmlTotale", totNormalization);

				if(!StringUtils.isEmpty(magObj.getMetsOriginal())){
					Document metsDoc = UtilXML.convertStringToDocumentXML(magObj.getMetsOriginal());
					metsDoc = UtilXML.normalizeDocument(metsDoc, xPathMets, oldValue, newValue, fieldName);
					if(metsDoc!=null) {
						Map<String, String> metsNorm = new HashMap<String, String>();
						metsNorm.put("set", UtilXML.convertDocumentToString(metsDoc));
						inputDoc.addField("metsxmlOriginal", metsNorm);
					}
				}
				if(!StringUtils.isEmpty(magObj.getMetsExternal())){
					Document metsDoc = UtilXML.convertStringToDocumentXML(magObj.getMetsExternal());
					metsDoc = UtilXML.normalizeDocument(metsDoc, xPathMets, oldValue, newValue, fieldName);
					if(metsDoc!=null) {
						Map<String, String> metsNormExt = new HashMap<String, String>();
						metsNormExt.put("set", UtilXML.convertDocumentToString(metsDoc));
						inputDoc.addField("metsxmlExternal", metsNormExt);
					}
				}
				server.add(inputDoc);
			}
		
			server.commit();
		}
	}

	/**
	 * Restituisce i risultati di una query Solr per scopi di normalizzazione
	 * 
	 * @param advancedSearch mappe di ricerca avanzata
	 * @param solr client solr
	 * @param start inizio risultati (per paginazione)
	 * @param numResults numero max di risultati
	 * @param solrField campo Solr da normalizzare
	 * @param value valore originale del campo
	 * @return
	 */
	public static SearchResultList searchDocumentNormalize(SearchData advancedSearch, 
			SolrClient solr, int start, int numResults, String solrField, String value) {
		
		String solrOriginalValueField = NormalizationUtility.getSolrOriginalValueField(solrField);
		SearchResultList resultList = new SearchResultList();	
	    String query = buildSolrQuery(advancedSearch);
	    
	    query = ("*:*".equals(query) ? "" : query + " AND ") + 
	    		solrOriginalValueField + ":" + ClientUtils.escapeQueryChars(value);
	    
	    resultList.setSolrQuery(query);
	    
	    ModifiableSolrParams params = new ModifiableSolrParams();
	    params.set("q", query); 
	    params.set("rows", numResults);
	    
	    if(advancedSearch.getSort() != null && !advancedSearch.getSort().isEmpty())
	    	params.set("sort", advancedSearch.getSort());
	    
	    if(start >= 0)
	    	params.set("start", start);
	    
	    params.set("fl", "id," + solrField + ",deleted,publicFlag,path,magxmlProject,"
	    		+ "metsxmlExternal,metsxmlOriginal,magxmlOriginal,magxmlInternal,magxmlExternal,magxmlTotale,documentFormat");

		try {
			QueryResponse response = solr.query(params, METHOD.POST);
			SolrDocumentList listDocument = response.getResults();
			resultList.setTotalResults(listDocument.getNumFound());
			
			if (listDocument.size() > 0) {
				for(int i = 0; i < listDocument.size(); i++){
					Mag mag = getMagFromQuerySolr(listDocument.get(i));
					resultList.getResults().add(mag);
				}
			}
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return resultList;
	}
	
	
	/**
	 * Ricerca veloce da applicare per scopo di normalizzazione
	 * 
	 * @param server server Solr
	 * @param advancedSearch mappa dati ricerca avanzata
	 * @param fieldName nome del campo Solr da valutare
	 * @return elenco dei valori
	 */
	public static SearchResultList searchFieldValues(SolrClient server, SearchData advancedSearch, String fieldName) {
		SearchResultList result = new SearchResultList();	
	    QueryResponse response = null;	
	    ModifiableSolrParams params = new ModifiableSolrParams();
	    String query = buildSolrQuery(advancedSearch);
	    params.set("q", query); 
	    params.set("rows", 0);
	    params.set("facet", true);
	    params.set("facet.mincount", 1);
	    params.set("facet.limit", -1);
	    params.set("facet.sort", "count");
	    params.set("facet.field", fieldName);

		try {
			response = server.query(params, METHOD.POST);
			
			for(FacetField ff : response.getFacetFields()) {
				for(Count count : ff.getValues())
					result.addFacetValue(ff.getName(), count.getName(), count.getCount());
			}
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}

	/**
	 * Restituisce il numero dei documenti per i quali il valore è assente
	 * 
	 * @param server server Solr
	 * @param fieldName nome campo
	 * @param advancedSearch mappa dati ricerca avanzata
	 * @param start inizio query
	 * @param length lunghezza risultati
	 * 
	 * @return List<Mag> lista dei MAG
	 */
	public static SearchResultList searchNullValues(SolrClient server, String fieldName, 
			SearchData advancedSearch, int start, int length) {
		
		SearchResultList resultList = new SearchResultList();
	    QueryResponse response = null;	
	    ModifiableSolrParams params = new ModifiableSolrParams();
	    params.set("q", buildSolrQuery(advancedSearch) + " AND -" + fieldName + ":[* TO *]");
	    params.set("start", start);
	    params.set("rows", length);

		try {
			response = server.query(params);
			SolrDocumentList results = response.getResults();
			resultList.setTotalResults(results.getNumFound());
			
			for(SolrDocument doc : results)
				resultList.getResults().add(UtilSolr.getMagFromQuerySolr(doc));
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return resultList;
	}
	
	private static Set<String> getNewFieldValues(Mag mag, String fieldName, String oldValue, String newValue) {
		Set<String> values = new HashSet<String>();
		String originalField = NormalizationUtility.getSolrOriginalField(fieldName);
		
		if(originalField == null)
			return values;
		
		else if("stprog".equals(originalField)) {
			if(oldValue.equals(mag.getStprog())) {
				values.add(newValue);
			}
			
			else
				values.add(mag.getStprog());
		}
		
		else if("collection".equals(originalField)) {
			if(oldValue.equals(mag.getCollection()))
				values.add(newValue);
			
			else
				values.add(mag.getCollection());
		}

		else if("library".equals(originalField)) {
			for(String library : mag.getLibraries()) {
				if(oldValue.equals(library))
					values.add(newValue);
				
				else
					values.add(library);
			}
		}
		
		else if("agency".equals(originalField)) {
			if(oldValue.equals(mag.getAgency()))
				values.add(newValue);
			
			else
				values.add(mag.getAgency());
		}

		else if("type".equals(originalField)) {
			for(String type : mag.getTypes()) {
				if(oldValue.equals(type))
					values.add(newValue);
				
				else
					values.add(type);
			}
		}

		else if("language".equals(originalField)) {
			for(String language : mag.getLanguages()) {
				if(oldValue.equals(language))
					values.add(newValue);
				
				else
					values.add(language);
			}
		}

		else if("creator".equals(originalField)) {
			for(String creator : mag.getCreators()) {
				if(oldValue.equals(creator))
					values.add(newValue);
				
				else
					values.add(creator);
			}
		}

		else if("contributor".equals(originalField)) {
			for(String contributor : mag.getContributors()) {
				if(oldValue.equals(contributor))
					values.add(newValue);
				
				else
					values.add(contributor);
			}
		}

		else if("publisher".equals(originalField)) {
			for(String publisher : mag.getPublishers()) {
				if(oldValue.equals(publisher))
					values.add(newValue);
				
				else
					values.add(publisher);
			}
		}

		else if("subject".equals(originalField)) {
			for(String subject : mag.getSubjects()) {
				if(oldValue.equals(subject))
					values.add(newValue);
				
				else
					values.add(subject);
			}
		}
		
		else if("title".equals(originalField)) {
			for(String title : mag.getTitles()) {
				if(oldValue.equals(title))
					values.add(newValue);
				
				else
					values.add(title);
			}
		}
		
		else if("date".equals(originalField)) {
			for(String date : mag.getDates()) {
				if(oldValue.equals(date))
					values.add(newValue);
				
				else
					values.add(date);
			}
		}
		
		else if("rights".equals(originalField)) {
			for(String rights : mag.getRights()) {
				if(oldValue.equals(rights))
					values.add(newValue);
				
				else
					values.add(rights);
			}
		}
		
		return values;
	}

	public static String REGEX_OTHER_SPECIAL_CHARACTERS = "[\\+\\\"\\:\\&\\'\\*\\%\\#\\$\\<\\>\\?\\@]+";

	/**Costruisce l'identificativo di un MAG usando i valori dei nodi dc:identifier, piece e mag:gen creation,
	 * anche in caso di documenti di tipo serie o raccolte va bene, dato che il tempo di creazione è univoco per ogni mag
	 * 
	 * @param Mag oggetto MAG
	 * @return ID Solr
	 * */

	public static String createMagIdentifier(Mag mag) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String creationDate = null;

		if(mag.getCreation() != null) {
			try {
				Date date = formatter.parse(mag.getCreation());
				formatter.applyPattern("yyyyMMddHHmmss");
				creationDate = formatter.format(date);
				
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		StringBuffer idBuffer = new StringBuffer();
		
		for(String identifier : mag.getIdentifiers()) {
			if(idBuffer.length() > 0)
				idBuffer.append("_");
			
			idBuffer.append(identifier
					.replaceAll("[ \t\n\r\f\\/\\\\]+", "_")
					.replaceAll(REGEX_OTHER_SPECIAL_CHARACTERS,"-")
			);
		}
		
		idBuffer.append("_" + mag.getLevel());
		
		String piece = "";

		if("s".equals(mag.getLevel())) {
			if(mag.getYear() != null && !mag.getYear().isEmpty())
				piece += mag.getYear().replaceAll("[ \n\t\r\f\\/\\\\]+", "");
			
			if(mag.getIssue() != null && !mag.getIssue().isEmpty()) {
				if(!piece.isEmpty())
					piece += "-";
				
				piece += mag.getIssue().replaceAll("[ \n\t\r\f\\/\\\\]+", "");
			}
		}

		else if("m".equals(mag.getLevel())) {
			if(mag.getPartNumber() != null && !mag.getPartNumber().isEmpty())
				piece += mag.getPartNumber().replaceAll("[ \n\t\r\f\\/\\\\]+", "");
			
			if(mag.getPartName() != null && !mag.getPartName().isEmpty()) {
				if(!piece.isEmpty())
					piece += "-";
				
				piece += mag.getPartName().replaceAll("[ \n\t\r\f\\/\\\\]+", "");
			}
		}
		
		if(!piece.isEmpty())
			idBuffer.append("_" + piece.replaceAll(REGEX_OTHER_SPECIAL_CHARACTERS,"-"));
		
		if(creationDate != null)
			idBuffer.append("_" + creationDate);
		
		mag.setIdMag(idBuffer.toString());
		logger.info("Identificativo MAG '" + mag.getPath() + "' - " + mag.getIdMag());		
		return idBuffer.toString();
	}
	
	/**
	 * Restituisce i tokens contenuti in un testo
	 * 
	 * @param value testo
	 * @return List<String> lista dei tokens
	 */
	public static List<String> getSolrTokens(String value) {
		List<String> list = new ArrayList<String>();
		
		if(value==null || value.isEmpty())
			return list;
		
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(value);
		
		while (m.find())
			list.add(quoteString(m.group(1).replace("\"", "")));
		
		return list;
	}

	/**
	 * Esegue verifiche sulla necessità di eseguire il quote di una stringa
	 * 
	 * @param value stringa da valutare
	 * @return String stringa elaborata
	 */
	private static String quoteString(String value) {
		if(value.endsWith("*"))
			return ClientUtils.escapeQueryChars(value.substring(0, value.length() - 1)) + "*";
		
		if(value.contains(" "))
			return "\"" + ClientUtils.escapeQueryChars(value) + "\"";
		else
			return ClientUtils.escapeQueryChars(value);
	}
	
	/**
	 * Esegue la query di calcolo delle statistiche per gli oggetti digitali
	 * 
	 * @param group lista dei campi da raggruppare
	 * @param queryData filtri di query
	 * @return List<DigitalObjectStats> tabella delle statistiche
	 */
	public static List<DigitalObjectStats> getDigitalObjectsStats(List<String> group, 
			Map<String, List<String>> queryData) {
		
		// costruzione query di raggruppamento
		String fieldList = "";
		
		for(String groupField : group) {
			if(!fieldList.isEmpty())
				fieldList += ",";
			
			fieldList += groupField.endsWith("String") ? groupField : groupField.
					replace("Facet", "").concat("String");
		}
		
		// costruzione query
		String query = "deleted:0 AND (draft:0 OR -draft[* TO *])";
		
		for(Entry<String, List<String>> entry : queryData.entrySet()) {
			if(entry.getValue() != null && !entry.getValue().isEmpty()) {
				if(entry.getValue().size() == 1) {
					query += " AND " + NormalizationUtility.getSolrOriginalValueField(entry.getKey()) + ":" + 
							quoteString(entry.getValue().get(0));
				}
				
				else {
					query += " AND " + NormalizationUtility.getSolrOriginalValueField(entry.getKey()) + ":(";
					
					for(int i = 0; i < entry.getValue().size(); i++) {
						if(i > 0)
							query += " AND ";
						
						query += quoteString(entry.getValue().get(i));
					}
					
					query += ")";
				}
					
			}
		}
		
		List<DigitalObjectStats> statsResults = new ArrayList<DigitalObjectStats>();
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", query);
		params.set("rows", 0);
		params.set("stats", true);
		params.add("stats.field", "{!tag=digital_object sum=true}numberImg");
		params.add("stats.field", "{!tag=digital_object sum=true}numberAudio");
		params.add("stats.field", "{!tag=digital_object sum=true}numberVideo");
		params.add("stats.field", "{!tag=digital_object sum=true}numberOcr");
		params.add("stats.field", "{!tag=digital_object sum=true}numberDoc");
		params.set("facet", true);
		params.add("facet.pivot", "{!stats=digital_object facet.mincount=1 facet.sort=index}" + fieldList);
		
		// esecuzione query
		try {
			QueryResponse response = solr.query(params, METHOD.POST);
			NamedList<List<PivotField>> pivots = response.getFacetPivot();
			
			for(Entry<String, List<PivotField>> entry : pivots) {
				if(entry.getValue() != null) {
					populateDigitalObjectStats(statsResults, entry.getValue(),
							new ArrayList<String>(), group.size());
				}
			}
			
			solr.close();
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		}
		
		return statsResults;
	}
	
	/**
	 * Popola la tabella delle statistiche degli oggetti digitali
	 * scorrendo i risultati della query Solr
	 * 
	 * @param rows righe della tabella finale
	 * @param pivots lista dei pivots da leggere
	 * @param currentGroup lista dei valori raggruppati attualmente
	 * @param n numero di valori da raggruppare
	 */
	private static void populateDigitalObjectStats(List<DigitalObjectStats> rows, 
			List<PivotField> pivots, List<String> currentGroup, int n) {
		
		
		for(PivotField p : pivots) {
			List<String> newGroup = new ArrayList<String>(currentGroup);
			newGroup.add(p.getValue().toString());
			
			// crea riga per la tabella finale
			if(newGroup.size() == n) {
				DigitalObjectStats row = new DigitalObjectStats();
				row.setValues(newGroup);
				row.setCount(p.getCount());
				
				for(FieldStatsInfo statsField : p.getFieldStatsInfo().values()) {
					int sum = (int) Math.round(Double.parseDouble(statsField.getSum().toString()));
					
					if("numberImg".equals(statsField.getName()))
						row.setNumberImg(sum);
					
					else if("numberAudio".equals(statsField.getName()))
						row.setNumberAudio(sum);
					
					else if("numberVideo".equals(statsField.getName()))
						row.setNumberVideo(sum);
					
					else if("numberOcr".equals(statsField.getName()))
						row.setNumberOcr(sum);
					
					else if("numberDoc".equals(statsField.getName()))
						row.setNumberDoc(sum);
				}
				
				rows.add(row);
			}
			
			// passa al livello di profondità successivo
			else if(p.getPivot() != null)
				populateDigitalObjectStats(rows, p.getPivot(), newGroup, n);
		}
	}

	public static String getPathFromId(String id) {
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "id:" + ClientUtils.escapeQueryChars(id));
		params.set("fl", "id,vfs_path");

		try {
			QueryResponse response = solr.query(params);
			SolrDocumentList docList = response.getResults();

			// risultato
			String path = docList.isEmpty() ? null : (String) docList.get(0).get("vfs_path");
			solr.close();
			return path;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}
	public static boolean exists(SolrClient solr , String id) {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "id:" + ClientUtils.escapeQueryChars(id));
		params.set("fl", "id");

		try {
			QueryResponse response = solr.query(params);
			SolrDocumentList docList = response.getResults();

			// risultato
			boolean exists = docList.isEmpty() ? false : true;
			return exists;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}

	}

	public static String getPathFromMagId(String id) {
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "id:" + ClientUtils.escapeQueryChars(id));
		params.set("fl", "id,path");

		try {
			QueryResponse response = solr.query(params);
			SolrDocumentList docList = response.getResults();

			// risultato
			String path = docList.isEmpty() ? null : (String) docList.get(0).get("path");
			solr.close();
			return path;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

}
