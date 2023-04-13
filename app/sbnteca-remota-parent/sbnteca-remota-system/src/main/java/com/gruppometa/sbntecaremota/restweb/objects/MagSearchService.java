package com.gruppometa.sbntecaremota.restweb.objects;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppometa.sbnmarc.mag.SaxonHelper;
import com.gruppometa.sbntecaremota.iiif.Manifest3Repository;
import com.gruppometa.sbntecaremota.iiif.PathMapper;
import com.gruppometa.sbntecaremota.model.iiif.v3.Manifest;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsService;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.StringUtils;
import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.objects.DigitalObjectStats;
import com.gruppometa.sbntecaremota.objects.ExternalMagReference;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.SearchData;
import com.gruppometa.sbntecaremota.objects.SearchResultList;
import com.gruppometa.sbntecaremota.objects.db.DBDeleteMag;
import com.gruppometa.sbntecaremota.objects.db.DBDeleteMagDao;
import com.gruppometa.sbntecaremota.objects.db.DBExport;
import com.gruppometa.sbntecaremota.objects.db.DBExportDao;
import com.gruppometa.sbntecaremota.objects.db.DBPublicationMag;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcess;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcessDao;
import com.gruppometa.sbntecaremota.objects.db.DBTecaUser;
import com.gruppometa.sbntecaremota.objects.db.DBTecaUserDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.objects.json.JsonDeleteRequest;
import com.gruppometa.sbntecaremota.objects.json.JsonDigitalObjectStatsRow;
import com.gruppometa.sbntecaremota.objects.json.JsonDigitalObjectStatsTable;
import com.gruppometa.sbntecaremota.objects.json.JsonDraft;
import com.gruppometa.sbntecaremota.objects.json.JsonExport;
import com.gruppometa.sbntecaremota.objects.json.JsonExportRequest;
import com.gruppometa.sbntecaremota.objects.json.JsonExportResponse;
import com.gruppometa.sbntecaremota.objects.json.JsonFieldValueCount;
import com.gruppometa.sbntecaremota.objects.json.JsonMagDetailField;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPreview;
import com.gruppometa.sbntecaremota.objects.json.JsonNormalizationData;
import com.gruppometa.sbntecaremota.objects.json.JsonNormalizationFieldResult;
import com.gruppometa.sbntecaremota.objects.json.JsonNormalizationRequest;
import com.gruppometa.sbntecaremota.objects.json.JsonNormalizationResponse;
import com.gruppometa.sbntecaremota.objects.json.JsonNormalizationUpdateValue;
import com.gruppometa.sbntecaremota.objects.json.JsonNormalizationValueResult;
import com.gruppometa.sbntecaremota.objects.json.JsonPublicationMagResult;
import com.gruppometa.sbntecaremota.objects.json.JsonPublicationRequest;
import com.gruppometa.sbntecaremota.objects.json.JsonSearchRequest;
import com.gruppometa.sbntecaremota.objects.json.JsonSearchResultFacet;
import com.gruppometa.sbntecaremota.objects.json.JsonSearchResultFacetValue;
import com.gruppometa.sbntecaremota.objects.json.JsonSearchResultList;
import com.gruppometa.sbntecaremota.objects.json.JsonSearchViewerResultList;
import com.gruppometa.sbntecaremota.objects.json.JsonStatsCountValue;
import com.gruppometa.sbntecaremota.objects.json.JsonStatsResult;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.NormalizationUtility;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;

public class MagSearchService {

	// logger
	private static Logger logger = LoggerFactory.getLogger(MagSearchService.class);

	// dao teca process
	@Autowired
	private DBTecaProcessDao tecaProcessDao;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected Manifest3Repository manifest3Repository;

	// dao delete mag
	@Autowired
	private DBDeleteMagDao deleteMagDao;

	// dao delete mag
	@Autowired
	private DBExportDao exportDao;

	// dao utenti
	@Autowired
	private DBTecaUserDao userDao;
	
	// delivery
	@Autowired
	private MagResourceDelivery delivery;
	
	// batch pubblicazione
	@Autowired
	private Integer publicationBatchSize;

	// batch cancellazione
	@Autowired
	private Integer deleteBatchSize;

	// batch esportazione
	@Autowired
	private Integer exportBatchSize;

	// batch normalizzazione
	@Autowired
	private Integer normalizationBatchSize;
	
	
	
	/**
	 * Popola le mappe a partire dalla richiesta della ricerca avanzata
	 * 
	 * @param request richiesta
	 * @param hasFacets true se devono essere incluse le faccette
	 * @param filterDoc costante di filtraggio campi documento
	 * @param includeDeleted costante di inclusione cancellati
	 * @param includeDrafts costante di inclusione bozze
	 * @param sort campo di ordinamento (opzionale)
	 */
	private SearchData populateAdvancedSearch(JsonSearchRequest request, boolean hasFacets, 
			int filterDoc, int includeDeleted, int includeDrafts, String sort) {
		
		SearchData advancedSearch = new SearchData();
		advancedSearch.setFacets(hasFacets);
		advancedSearch.setFilterDoc(filterDoc);
		advancedSearch.setIncludeDeleted(includeDeleted);
		advancedSearch.setIncludeDrafts(includeDrafts);
		advancedSearch.setSort(sort);
		advancedSearch.setFacetTypeFields(Arrays.asList("project", "type", "typeDigitale", 
				"levelString", "collection", "agency"));
		
		for(Entry<String, List<String>> fieldEntry : request.getFieldMap().entrySet()) {
			if(!fieldEntry.getValue().isEmpty())
				advancedSearch.getBaseSearch().put(fieldEntry.getKey(), fieldEntry.getValue());
		}
		
		for(Entry<String, List<String>> fieldEntry : request.getFacetFilters().entrySet()) {
			if(!fieldEntry.getValue().isEmpty())
				advancedSearch.getFacetSearch().put(fieldEntry.getKey(), fieldEntry.getValue());
		}
		
		for(Entry<String, List<String>> fieldEntry : request.getFieldDateMap().entrySet()) {
			if(!fieldEntry.getValue().isEmpty()) {
				List<String> correctValues = new ArrayList<String>();
				
				for(int i = 0; i < fieldEntry.getValue().size(); i++) {
					String value = fieldEntry.getValue().get(i);
					
					if(i == 0)
						correctValues.add(value.isEmpty() ? "*" : value + "T00:00:00.000Z");
					
					else if(i == 1)
						correctValues.add(value.isEmpty() ? "NOW" : value + "T23:59:59.999Z");
				}
				
				advancedSearch.getDateIntervalSearch().put(fieldEntry.getKey(), correctValues);
			}
		}
		
		advancedSearch.getFilteredIDs().addAll(request.getIds());
		advancedSearch.getExcludedIDs().addAll(request.getNotIds());
		return advancedSearch;
	}
	
	/**
	 * Esegue la pubblicazione dei MAG
	 * 
	 * PUBBLICAZIONE MAG INDICIZZATI
	 * 
	 * @param request richiesta di pubblicazione
	 * @throws DaoException
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public List<JsonPublicationMagResult> publicate(JsonPublicationRequest request) 
			throws DaoException, SolrServerException, IOException {
		
		List<JsonPublicationMagResult> result = new ArrayList<JsonPublicationMagResult>();
		
		// crea processo
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());

		// utente
		DBTecaUser user = userDao.getUserByID(request.getUser());
		
		if(user == null)
			throw new DaoException("Utente ID " + request.getUser() + " non trovato");
		
		
		DBTecaProcess process = new DBTecaProcess();
		process.setId("publ_" + formatter.format(cal.getTime()));
		process.setTecaUser(user);
		process.setStatus(1);
		process.setMessage("Processo di pubblicazione in esecuzione");
		process.setTimestampStart(new BigInteger(cal.getTimeInMillis() + ""));
		tecaProcessDao.insert(process);

		
		SolrClient readServer = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		SolrClient updateServer = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		long t0 = System.currentTimeMillis();
		List<String> ids = new ArrayList<String>();
		Map<String, Integer> idMap = new HashMap<String, Integer>();
		
		// query solr
		SearchData advancedSearch = this.populateAdvancedSearch(request.getSearchRequest(), 
				false, SearchData.ID, SearchData.EXCLUDE, SearchData.EXCLUDE, "timestamp desc");
		
		SearchResultList magResults = UtilSolr.searchDocument(advancedSearch, readServer, 0, Integer.MAX_VALUE);
		
		// prendi ID
		if(!magResults.getResults().isEmpty()) {
			for(Mag magUpdate : magResults.getResults()) {
				JsonPublicationMagResult magRes = new JsonPublicationMagResult();
				magRes.setId(magUpdate.getIdMag());

				//Cancello le risorse dalla pubblicazione in esterno
				if (request.getPublicFlag() == 0 && magUpdate.getPublishFlag() != 0) {
					magRes.setPublished(0);
					magUpdate.setPublishFlag(0);
					ids.add(magUpdate.getIdMag());
					idMap.put(magUpdate.getIdMag(), 0);
				}
				
				//Pubblico le risorse
				else if(request.getPublicFlag() == 1 && magUpdate.getPublishFlag() != 1) {
					magRes.setPublished(1);
					magUpdate.setPublishFlag(1);
					ids.add(magUpdate.getIdMag());
					idMap.put(magUpdate.getIdMag(), 1);
				}
				
				// MAG già non pubblicati
				else if(request.getPublicFlag() == 0 && magUpdate.getPublishFlag() == 0) {
					magRes.setPublished(0);
					magRes.setError("Mag già non pubblicato");
				}
				
				// MAG già pubblicati
				else if(request.getPublicFlag() == 1 && magUpdate.getPublishFlag() == 1) {
					magRes.setPublished(1);
					magRes.setError("Mag già pubblicato");
				}
				
				result.add(magRes);
			}
		}
		
		// modifica ID
		while(!ids.isEmpty()) {
			int count = 0;
			Map<String, Integer> batchIdMap = new HashMap<String, Integer>();
			
			while(count < publicationBatchSize && !ids.isEmpty()) {
				String id = ids.remove(0);
				batchIdMap.put(id, idMap.remove(id));
				count++;
			}
			
			if(!batchIdMap.isEmpty())
				UtilSolr.publicateMag(batchIdMap, updateServer);
		}
		
		if(readServer != null)
			readServer.close();
		
		if(updateServer != null)
			readServer.close();
			
		
		
		// update processo
		cal.setTimeInMillis(System.currentTimeMillis());
		process.setStatus(0);
		process.setMessage("Processo di pubblicazione terminato");
		process.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
		tecaProcessDao.update(process);
		
		long t1 = System.currentTimeMillis();
		logger.info("Processo di pubblicazione terminato in " + (t1 - t0) + " ms");
		List<DBPublicationMag> magsDB = new ArrayList<DBPublicationMag>();
		
		for(JsonPublicationMagResult mag : result) {
			DBPublicationMag magDB = new DBPublicationMag();
			magDB.setIdMag(mag.getId());
			magDB.setTecaProcess(process);
			magDB.setPublicate(mag.getPublished());
			magsDB.add(magDB);
		}
		
		return result;
	}
	
	/**
	 * Esegue la cancellazione dei MAG da Solr e file system
	 * 
	 * CANCELLAZIONE MAG INDICIZZATI
	 *  
	 * @param request richiesta di cancellazione
	 * @return List<String> lista dei MAG cancellati
	 * @throws DaoException 
	 */
	public List<String> deleteMags(JsonDeleteRequest request) throws Exception {
		// crea processo
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());

		// utente
		DBTecaUser user = userDao.getUserByID(request.getUser());
		
		if(user == null)
			throw new DaoException("Utente ID " + request.getUser() + " non trovato");
		
		DBTecaProcess process = new DBTecaProcess();
		process.setId("del_" + formatter.format(cal.getTime()));
		process.setTecaUser(user);
		process.setStatus(1);
		process.setMessage("Processo di cancellazione in esecuzione");
		process.setTimestampStart(new BigInteger(cal.getTimeInMillis() + ""));
		
		try {
			tecaProcessDao.insert(process);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
		
		List<String> deleted = new ArrayList<String>();
		SolrClient readServer = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		SolrClient updateServer = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		int count = 0;
		
		// query solr
		SearchData advancedSearch = this.populateAdvancedSearch(request.getSearchRequest(), false, 
				SearchData.UPDATE_DOC, SearchData.EXCLUDE, SearchData.EXCLUDE, "timestamp desc");
		
		SearchResultList magResults = UtilSolr.searchDocument(advancedSearch, readServer, count, deleteBatchSize);
		
		try {
			while(!magResults.getResults().isEmpty()) {
				// crea liste mag da cancellare e non trovati
				List<String> pathMagsDelete = new ArrayList<String>();
				
				for (Mag mag : magResults.getResults()) {
					Document doc = UtilXML.convertStringToDocumentXML(mag.getMagInternal());
					
					if(!"a".equals(mag.getLevel()))
						delivery.deleteTecaResources(mag.getIdMag(), UtilXML.getResourceIDs(doc));
					
					deleted.add(mag.getIdMag());
					
					if(!Utility.isMagFromEditor(mag.getPath()))
						pathMagsDelete.add(new File(mag.getPath()).getCanonicalPath());
				}
				
				// cancella delivery
				if(!pathMagsDelete.isEmpty())
					delivery.deleteImportDocs(pathMagsDelete);
				
				count += deleteBatchSize;
				magResults = UtilSolr.searchDocument(advancedSearch, readServer, count, deleteBatchSize);
			}
			
			// cancellazione documenti solr
			if(!deleted.isEmpty()) {
				int i = 0;

				/**
				 * bugfix: prima non aggiunge mai più di deleteBatchSize
				 */
				while(i < deleted.size()) {
					count = 0;
					List<String> batchDeleted = new ArrayList<String>();
					while (count < deleteBatchSize && i < deleted.size()) {
						batchDeleted.add(deleted.get(i));
						count++;
						i++;
					}

					if (!batchDeleted.isEmpty())
						UtilSolr.flagDeleteMag(batchDeleted, updateServer);
				}
			}
			
			readServer.close();
			updateServer.close();
			
			// update processo
			cal.setTimeInMillis(System.currentTimeMillis());
			process.setStatus(0);
			process.setMessage("Processo di cancellazione terminato");
			process.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
			tecaProcessDao.update(process);

			// salva sul database id mag cancellati
			List<DBDeleteMag> deletedMagDB = new ArrayList<DBDeleteMag>();
			
			for(String deletedMag : deleted) {
				DBDeleteMag magDB = new DBDeleteMag();
				magDB.setTecaProcess(process);
				magDB.setIdMag(deletedMag);
				deletedMagDB.add(magDB);
			}
			
			deleteMagDao.insert(deletedMagDB);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return deleted;
	}
	
	/**
	 * Servizio di ricerca MAG
	 * 
	 * RICERCA AVANZATA
	 * 
	 * @param request oggetto di richiesta contenente i campi di ricerca
	 * @param resultSize numero di risultati richiesti
	 * @param facets true per restituzione soltanto delle faccette
	 * @param isDraft true per inclusione MAG di tipo draft
	 * @return JsonSearchResultList lista dei risultati e faccette
	 */
	public JsonSearchResultList search(JsonSearchRequest request, int resultSize, boolean facets, boolean isDraft) {
		// costruzione query solr
		logger.debug("Ricerca MAG in corso");
		
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		
		// query solr
		SearchData advancedSearch = this.populateAdvancedSearch(request, facets, SearchData.PREVIEW, 
				SearchData.EXCLUDE, isDraft ? SearchData.ONLY : SearchData.EXCLUDE, null);
		
		SearchResultList magResults = UtilSolr.searchDocument(advancedSearch, server, request.getStart(), resultSize);
		
		// costruzione json risultati
		JsonSearchResultList resultList = new JsonSearchResultList();
		resultList.setSolrQuery(magResults.getSolrQuery());
		resultList.setNumberResults(magResults.getTotalResults());
		
		for(Mag mag : magResults.getResults()) {
			JsonMagPreview magPreview = new JsonMagPreview();
			magPreview.setId(mag.getIdMag());
			magPreview.getIdentifiers().addAll(mag.getIdentifiers());
			magPreview.getTitles().addAll(mag.getTitles());
			
			if(mag.getYear() != null && !mag.getYear().isEmpty())
				magPreview.setYearPartNumber(mag.getYear());
			
			if(mag.getPartNumber() != null && !mag.getPartNumber().isEmpty())
				magPreview.setYearPartNumber(mag.getPartNumber());
			
			if(mag.getIssue() != null && !mag.getIssue().isEmpty())
				magPreview.setIssuePartName(mag.getIssue());
			
			if(mag.getPartName() != null && !mag.getPartName().isEmpty())
				magPreview.setIssuePartName(mag.getPartName());
			magPreview.setDocumentFormat(mag.getDocumentFormat());
			magPreview.setLevel(mag.getLevel());
			magPreview.getPublicFlags().add(mag.getPublishFlag() + "");
			try {
				magPreview.setIdContainer(UtilSolr.getMagIDFromVfsPath(mag.getPath()));
				magPreview.setProject(mag.getProject());
			} catch (SolrServerException e) {
				logger.error("", e);
			}
			resultList.getMags().add(magPreview);
		}
		
		// costruzione faccette
		for(String facet : magResults.getFacets()) {
			JsonSearchResultFacet jsonFacet = new JsonSearchResultFacet();
			jsonFacet.setName(facet);
			
			// ordinamento per ordine alfabetico solo se per popolare list box (no per risultati)
			List<String> facetValues = new ArrayList<String>(magResults.getFacetValues(facet));
			
			if(resultSize == 0)
				Collections.sort(facetValues);
			
			for(String facetValue : facetValues) {
				if(!facetValue.isEmpty()) {
					JsonSearchResultFacetValue jsonFacetValue = new JsonSearchResultFacetValue();
					jsonFacetValue.setValue(facetValue);
					jsonFacetValue.setSize(magResults.getCountFacet(facet, facetValue));
					jsonFacet.getValues().add(jsonFacetValue);
				}
			}
			
			resultList.getFacets().add(jsonFacet);
		}
		
		logger.debug("Ricerca MAG terminata, trovati " + resultList.getNumberResults() + " MAG");
		return resultList;
	}

	/**
	 * Servizio di ricerca MAG customizzato
	 * 
	 * RICERCA AVANZATA VIEWER CHERUBINI
	 * 
	 * @param request oggetto di richiesta contenente i campi di ricerca
	 * @param resultSize numero di risultati richiesti
	 * @param facetOnly true per restituzione soltanto delle faccette
	 * @return JsonSearchResultList lista dei risultati e faccette
	 */
	public JsonSearchViewerResultList viewerSearch(JsonSearchRequest request, int resultSize, boolean facets) {
		// costruzione query solr
		logger.info("Ricerca MAG in corso");
		
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		
		final List<String> facetList = Arrays.asList("collection", "type", "subject", "creator",
				"contributor", "date", "typeDigitale");
		
		Map<String, String> facetLabelMap = new HashMap<String, String>();
		facetLabelMap.put("collection", "Collezione");
		facetLabelMap.put("type", "Tipo");
		facetLabelMap.put("subject", "Soggetto");
		facetLabelMap.put("creator", "Autore");
		facetLabelMap.put("contributor", "Contributore");
		facetLabelMap.put("date", "Data");
		facetLabelMap.put("typeDigitale", "Formato digitale");
		
		// query solr
		SearchData advancedSearch = this.populateAdvancedSearch(request, facets, SearchData.PREVIEW, 
				SearchData.EXCLUDE, SearchData.EXCLUDE, null);
		
		advancedSearch.setFacetTypeFields(new ArrayList<String>());
		SearchResultList magResults = UtilSolr.searchDocument(advancedSearch, server, request.getStart(), resultSize);
		
		// costruzione json risultati
		JsonSearchViewerResultList resultList = new JsonSearchViewerResultList();
		resultList.setNumberResults(magResults.getTotalResults());
		
		for(Mag mag : magResults.getResults()) {
			List<JsonMagDetailField> magJsonObj = new ArrayList<JsonMagDetailField>();
			magJsonObj.add(this.createMetadataJsonField("id", "ID", mag.getIdMag()));
			magJsonObj.add(this.createMetadataJsonField("identifier", "Identificatori", mag.getIdentifiers()));
			magJsonObj.add(this.createMetadataJsonField("title", "Titoli", mag.getTitles()));
			magJsonObj.add(this.createMetadataJsonField("creator", "Autori", mag.getCreators()));
			magJsonObj.add(this.createMetadataJsonField("subject", "Soggetti", mag.getSubjects()));
			magJsonObj.add(this.createMetadataJsonField("type", "Tipi", mag.getTypes()));
			
			magJsonObj.add(this.createMetadataJsonField("collection", "Collezione", 
					mag.getCollection() != null ? mag.getCollection() : ""));
			
			if(mag.getMagPreview() != null)
				magJsonObj.add(this.createMetadataJsonField("preview", "Preview", "digitalObject/" + mag.getMagPreview() + "?mode=preview"));
			
			Collections.sort(magJsonObj, new Comparator<JsonMagDetailField>() {
				
				// order
				private List<String> order = Arrays.asList("title", "creator", "collection", 
						"identifier", "type", "subject", "preview", "id");

				@Override
				public int compare(JsonMagDetailField f1, JsonMagDetailField f2) {
					return Integer.compare(order.indexOf(f1.getName()), order.indexOf(f2.getName()));
				}
			});
			
			resultList.getMags().add(magJsonObj);
		}
		
		// costruzione faccette
		for(String facet : magResults.getFacets()) {
			String fieldName = facet.replaceAll("String", "").replaceAll("Facet", "");
			
			if(facetList.contains(fieldName)) {
				JsonSearchResultFacet jsonFacet = new JsonSearchResultFacet();
				jsonFacet.setName(fieldName);
				jsonFacet.setLabel(facetLabelMap.get(fieldName));
				
				// ordinamento per ordine alfabetico solo se per popolare list box (no per risultati)
				List<String> facetValues = new ArrayList<String>(magResults.getFacetValues(facet));
				
				if(resultSize == 0)
					Collections.sort(facetValues);
				
				for(String facetValue : facetValues) {
					JsonSearchResultFacetValue jsonFacetValue = new JsonSearchResultFacetValue();
					jsonFacetValue.setValue(facetValue);
					jsonFacetValue.setSize(magResults.getCountFacet(facet, facetValue));
					jsonFacet.getValues().add(jsonFacetValue);
				}
				
				resultList.getFacets().add(jsonFacet);
			}
		}
		
		// ordina faccette secondo ordine
		Collections.sort(resultList.getFacets(), new Comparator<JsonSearchResultFacet>() {
			
			@Override
			public int compare(JsonSearchResultFacet facet1, JsonSearchResultFacet facet2) {
				return Integer.compare(facetList.indexOf(facet1.getName()), 
						facetList.indexOf(facet2.getName()));
			}
		});
		
		logger.info("Ricerca MAG terminata, trovati " + resultList.getNumberResults() + " MAG");
		return resultList;
	}

	/**
	 * Servizio di ricerca bozze
	 * 
	 * RICERCA BOZZE (EDITOR)
	 * 
	 * @param request oggetto di richiesta contenente i campi di ricerca
	 * @return List<JsonDraft> lista dei draft
	 */
	public List<JsonDraft> searchDraft(JsonSearchRequest request) {
		// costruzione query solr
		logger.debug("Ricerca MAG in corso");
		SearchData advancedSearch = this.populateAdvancedSearch(request, false, 
				SearchData.PREVIEW, SearchData.EXCLUDE, SearchData.ONLY, null);
		
		
		// costruzione json risultati
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		List<JsonDraft> results = new ArrayList<JsonDraft>();
		List<DBTecaProcess> draftsDB = new ArrayList<DBTecaProcess>();
		
		try {
			draftsDB.addAll(tecaProcessDao.getDrafts());
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			return results;
		}
		
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		int count = 0;

		// query solr
		SearchResultList magResults = UtilSolr.searchDocument(advancedSearch, server, count, 30);
		
		while(!magResults.getResults().isEmpty()) {
			for(Mag mag : magResults.getResults()) {
				JsonDraft draft = new JsonDraft();
				draft.setId(mag.getIdMag());
				
				if(!mag.getIdentifiers().isEmpty())
					draft.setIdentifier(mag.getIdentifiers().get(0));
				
				if(mag.getIssue() != null && !mag.getIssue().isEmpty())
					draft.setIssuePartName(mag.getIssue());
				
				if(mag.getYear() != null && !mag.getYear().isEmpty())
					draft.setYearPartNumber(mag.getYear());
				
				if(mag.getPartName() != null && !mag.getPartName().isEmpty())
					draft.setIssuePartName(mag.getPartName());
				
				if(mag.getPartNumber() != null && !mag.getPartNumber().isEmpty())
					draft.setYearPartNumber(mag.getPartNumber());

				draft.setDocumentFormat(!StringUtils.isEmpty(mag.getDocumentFormat())?mag.getDocumentFormat():"mag");
				try{
					draft.setVfsId(UtilSolr.getMagIDFromVfsPath(mag.getPath()));
					draft.setProject(mag.getProject());
				}
				catch (Exception e){
					logger.error("", e);
				}

				for(DBTecaProcess tpDB : draftsDB) {
					if(tpDB.getId().equals(mag.getIdMag())) {
						draft.setUsername(tpDB.getTecaUser() != null ? 
								tpDB.getTecaUser().getUsername() : "");
						
						draft.setLastModified(formatter.
								format(new Date(tpDB.getTimestampEnd().longValue())));
					}
				}
				if("mets".equals(draft.getDocumentFormat()) &&
						(StringUtils.isEmpty(draft.getLastModified())|| " - ".equals(draft.getLastModified()))
				){
					draft.setLastModified(formatter.format(mag.getTimestamp()));
				}
				
				results.add(draft);
			}
			
			count += 30;
			magResults = UtilSolr.searchDocument(advancedSearch, server, count, 30);
		}
		
		try {
			server.close();
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		logger.debug("Ricerca bozze terminata, trovate " + results.size() + " bozze");
		return results;
	}
	
	/**
	 * Servizio di cancellazione della bozza
	 * 
	 * CANCELLAZIONE BOZZA (EDITOR)
	 * 
	 * @param draftID ID della bozza da cancellare
	 * @return boolean true se la bozza è stata correttamente cancellata
	 */
	public boolean deleteDraft(String draftID) {
		try {
			UtilSolr.deleteMag(Arrays.asList(draftID), true);
			DBTecaProcess draftDB = tecaProcessDao.findByID(draftID);
			tecaProcessDao.deleteDraft(draftDB);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Esegue la normalizzazione su un certo numero di MAG
	 * 
	 * NORMALIZZAZIONE MAG INDICIZZATI
	 * 
	 * @param request oggetto di richiesta contenente i parametri
	 * @return JsonNormalizationResponse esito della normalizzazione
	 */
	public JsonNormalizationResponse normalize(JsonNormalizationRequest request) {
		JsonNormalizationResponse response = new JsonNormalizationResponse();
		SolrClient readServer = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		SolrClient updateServer = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
			
		for(JsonNormalizationData normalizeData : request.getNormalizationData()) {
			String solrField = NormalizationUtility.getSolrOriginalField(normalizeData.getFieldName());
			
			JsonNormalizationFieldResult fieldResult = new JsonNormalizationFieldResult();
			fieldResult.setFieldName(solrField);
			
			for(JsonNormalizationUpdateValue update : normalizeData.getUpdates()) {
				SearchData advancedSearch = this.populateAdvancedSearch(request.getSearchRequest(), 
						false, SearchData.UPDATE_DOC, SearchData.EXCLUDE, SearchData.EXCLUDE, null);
				
				JsonNormalizationValueResult valueResult = new JsonNormalizationValueResult();
				
				try {
					if(update.getOldValue() != null) {
						valueResult.setNewValue(update.getNewValue());
						
						if(update.getOldValue().equals(update.getNewValue())) {
							SearchResultList magResults = UtilSolr.searchDocumentNormalize(advancedSearch, 
									readServer, 0, 0, solrField, update.getOldValue());
							
							valueResult.setNumDocuments((int) magResults.getTotalResults());
						}
						
						else {
							int numDocs = 0;
							
							SearchResultList magResults = UtilSolr.searchDocumentNormalize(advancedSearch, 
									readServer, 0, normalizationBatchSize, solrField, update.getOldValue());
							
							while(!magResults.getResults().isEmpty()) {
								UtilSolr.normalize(updateServer, magResults.getResults(), 
										solrField, update.getOldValue(), update.getNewValue());
								
								numDocs += magResults.getResults().size();
								
								magResults = UtilSolr.searchDocumentNormalize(advancedSearch, 
										readServer, 0, normalizationBatchSize, solrField, update.getOldValue());
							}
							
							valueResult.setNumDocuments(numDocs);
						}
					}
					
					else {
						// query ricerca vuoti
						valueResult.setNewValue(update.getNewValue());
						int numDocs = 0;
						
						List<Mag> batchMags = UtilSolr.searchNullValues(readServer, solrField, 
								advancedSearch, 0, normalizationBatchSize).getResults();
						
						while(!batchMags.isEmpty()) {
							UtilSolr.normalize(updateServer, batchMags, solrField,  
									update.getOldValue(), update.getNewValue());
							
							numDocs += batchMags.size();
							
							batchMags = UtilSolr.searchNullValues(readServer, solrField, 
									advancedSearch, 0, normalizationBatchSize).getResults();
						}
						
						valueResult.setNumDocuments(numDocs);
					}
					
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					valueResult.setError(e.getMessage());
				}
				
				fieldResult.getUpdates().add(valueResult);
			}
			
			response.getNormalizationResults().add(fieldResult);
		}
		
		try {
			readServer.close();
			updateServer.close();
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		response.setMessage("Processo di normalizzazione completato");
		return response;
	}
	
	/**
	 * Restituisce i valori di un campo da normalizzare, con valore e numero di documenti
	 * 
	 * LETTURA DEI VALORI E CONTEGGIO DEI DOCUMENTI PER SINGOLO VALORE PER CAMPO DI NORMALIZZAZIONE
	 * 
	 * @param fieldName campo da valutare
	 * @param request ID dei MAG selezionati
	 * @return
	 */
	public List<JsonFieldValueCount> getNormalizationFacet(String fieldName, JsonSearchRequest request) {
		String solrField = fieldName.endsWith("String") ? fieldName : fieldName + "String";
		
		SearchData advancedSearch = this.populateAdvancedSearch(request, true, 
				SearchData.ID, SearchData.EXCLUDE, SearchData.EXCLUDE, null);
		
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		SearchResultList queryResult = UtilSolr.searchFieldValues(server, advancedSearch, solrField);
		
		// ricerca NULL
		int count = 0;
		long nullDocsCount = UtilSolr.searchNullValues(server, solrField, 
				advancedSearch, count, 0).getTotalResults();
		
		if(nullDocsCount > 0)
			queryResult.addFacetValue(solrField, null, new Long(nullDocsCount));
		
		
		// costruzione json risultato
		List<JsonFieldValueCount> results = new ArrayList<JsonFieldValueCount>();
		
		for(String facetValue : queryResult.getFacetValues(solrField)) {
			if(facetValue == null || !facetValue.isEmpty()) {
				JsonFieldValueCount jfvc = new JsonFieldValueCount();
				jfvc.setValue(facetValue);
				jfvc.setNumDocuments(queryResult.getCountFacet(solrField, facetValue));
				results.add(jfvc);
			}
		}
		
		Collections.sort(results, new Comparator<JsonFieldValueCount>() {

			@Override
			public int compare(JsonFieldValueCount jfvc1, JsonFieldValueCount jfvc2) {
				return Long.compare(jfvc2.getNumDocuments(), jfvc1.getNumDocuments());
			}
		});
		
		// chiusura connessione solr
		try {
			server.close();
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return results;
	}
	
	/**
	 * Restituisce le statistiche sui documenti
	 * 
	 * ELABORATI STATISTICI
	 * 
	 * @param fieldStats campo di cui applicare le statistiche
	 * @param valueStats valore del campo su cui applicare le statistiche
	 * @param facetFields faccette in base alla query campo=valore
	 * @return risultati
	 */
	public List<JsonStatsResult> getStats(String fieldStats, String valueStats, 
			List<String> facetFields) {
		
		List<JsonStatsResult> results = new ArrayList<JsonStatsResult>();
		List<String> realFacetFields = new ArrayList<String>();
		
		for(String ff : facetFields)
			realFacetFields.add(ff + (ff.startsWith("level") ? "" : "String"));
		
		try {
			SearchResultList solrResults = UtilSolr.getCount(fieldStats, 
					valueStats, false, realFacetFields, -1, "count");
			
			for(String field : solrResults.getFacets()) {
				JsonStatsResult result = new JsonStatsResult();
				result.setField(field.startsWith("level") ? field : field.replaceAll("String", ""));
				
				for(String fieldValue : solrResults.getFacetValues(field)) {
					if(fieldValue != null && !fieldValue.isEmpty()) {
						JsonStatsCountValue jscv = new JsonStatsCountValue();
						jscv.setValue(fieldValue);
						jscv.setDocuments(solrResults.getCountFacet(field, fieldValue));
						result.getResults().add(jscv);
					}
				}
				
				results.add(result);
			}
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		}
		
		return results;
	}
	
	/**
	 * Restituisce il documento MAG, dato ID e codice di versione
	 * 
	 * LETTURA DOCUMENTO XML MAG DA INDICE SOLR (OPERAZIONI DI RICERCA AVANZATA)
	 * 
	 * @param magID ID del MAG, se codice di versione null è il path relativo del MAG
	 * @param version codice di versione (0 MAG originale, 1 acquisizione, 2 esportazione, 3 totale)
	 * @return Document documento MAG XML
	 * @throws FileNotFoundException
	 */
	public Document getMagXmlDocument(String magID, int version) throws FileNotFoundException {
		Document doc = null;
		Mag mag = UtilSolr.selectDocumentById(magID);

		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");
		
		switch(version) {
			case 0: 
				doc = mag.getMagProject() != null ? 
						UtilXML.convertStringToDocumentXML(mag.getMagProject()) : null;
				break;
			
			case 1: 
				doc = mag.getMagOriginal() != null ? 
						UtilXML.convertStringToDocumentXML(mag.getMagOriginal()) : null;
				break;
				
			case 2:
				doc = mag.getMagInternal() != null ? 
						UtilXML.convertStringToDocumentXML(mag.getMagInternal()) : null;
				break;
				
			case 3:
				doc = mag.getMagExternal() != null ? 
						UtilXML.convertStringToDocumentXML(mag.getMagExternal()) : null;
				break;
				
			case 4:
				doc = mag.getMagTot() != null ? 
						UtilXML.convertStringToDocumentXML(mag.getMagTot()) : null;
				break;
		}
		
	    return doc;
	}

	public Document getMetsXmlDocument(String magID, int version) throws FileNotFoundException {
		Document doc = null;
		Mag mag = UtilSolr.selectDocumentById(magID);

		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");

		switch(version) {
			case 1:
				doc = mag.getMetsOriginal() != null ?
						UtilXML.convertStringToDocumentXML(mag.getMetsOriginal()) : null;
				break;

			case 3:
			default:
				doc = mag.getMetsExternal() != null ?
						UtilXML.convertStringToDocumentXML(mag.getMetsExternal()) : null;
				break;
		}

		return doc;
	}

	/**
	 * Esegue l'esportazione dei MAG selezionati creando il file compresso contenente l'export
	 * 
	 * EXPORT MAG INDICIZZATI
	 * 
	 * @param request richiesta di esportazione
	 * @return JsonExportResponse json di risposta
	 */
	public JsonExportResponse export(JsonExportRequest request) {
		// creazione archivio
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		File compressedFile = new File(ContentStatic.getProperties().getProperty("resourceDIRE"), 
				(request.getFilename() != null && !request.getFilename().isEmpty() ? request.getFilename() : "export_" + formatter.
						format(new Date(System.currentTimeMillis()))) + "." + request.getExtension());
		
		if(!compressedFile.getParentFile().exists())
			compressedFile.getParentFile().mkdirs();
		
		
		SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
		ArchiveOutputStream archiveStream = null;
		JsonExportResponse response = new JsonExportResponse();
		response.setFilename(compressedFile.getName());
		DBExport exportDB = new DBExport();
		
		try {
			// utente
			DBTecaUser user = userDao.getUserByID(request.getUser());
			
			if(user == null) {
				server.close();
				throw new DaoException("Utente ID " + request.getUser() + " non trovato");
			}
			
			// primo inserimento nel database
			exportDB.setTecaUser(user);
			exportDB.setStatus(1);
			exportDB.setMessage("0%");
			exportDB.setFile(compressedFile.getName());
			exportDao.insert(exportDB);
			
			
			archiveStream = Utility.createArchive(Utility.hasCompressor(request.getExtension()) ? 
					Utility.createCompressor(new FileOutputStream(compressedFile), request.getExtension()) : 
						new FileOutputStream(compressedFile), request.getExtension());
			

			// query solr
			int count = 0;
			
			SearchData advancedSearch = this.populateAdvancedSearch(request.getSearchRequest(), false, 
					SearchData.UPDATE_DOC, SearchData.EXCLUDE, SearchData.EXCLUDE, null);
			
			SearchResultList magResults = UtilSolr.searchDocument(advancedSearch, server, count, exportBatchSize);
			
			List<Mag> exportMags = magResults.getResults();
			double partial = 0.0d;

			while(!exportMags.isEmpty()) {
				for(Mag mag : exportMags) {
					boolean addToCompressed = true;
					String magIDForFile = mag.getIdMag().replaceAll("\\/", "_").replaceAll("\\\\", "_");
					Document document = UtilXML.convertStringToDocumentXML(mag.getMagInternal());

					Document documentMets = null;
					if(Mag.METS.equals(mag.getDocumentFormat())){
						documentMets = UtilXML.convertStringToDocumentXML(mag.getMetsOriginal());
					}
					
					if(request.getDress())
						this.exportDress(document, server);

					if(!"iiif".equalsIgnoreCase(request.getFormat())) {
						NodeList fileNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
						Map<String, Integer> fileNameMap = new HashMap<String, Integer>();

						// per ogni risorsa digitale
						for (int i = 0; i < fileNodeList.getLength(); i++) {
							Element fileNode = (Element) fileNodeList.item(i);
							String type = fileNode.getParentNode().getLocalName();

							if (!Arrays.asList("img", "audio", "video", "ocr", "doc").contains(type))
								type = fileNode.getParentNode().getParentNode().getLocalName();

							String deliveryID = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href").
									replaceAll("digitalObject/", "").replaceAll("/original", "");

							DataResourceDelivery resourceData = delivery.getResourceByID(deliveryID, false);

							if (resourceData == null) {
								addToCompressed = false;
								logger.error("Delivery ID '" + deliveryID + "' non trovato");
								break;
							}

							InputStream resourceStream = resourceData.getStream();
							String resourceFileName = this.getExportFileName(resourceData.getResourceName());

							if (!fileNameMap.containsKey(resourceFileName))
								fileNameMap.put(resourceFileName, 1);

							else {
								int progressive = fileNameMap.get(resourceFileName) + 1;
								fileNameMap.put(resourceFileName, progressive);
								int idx = resourceFileName.lastIndexOf(".");

								if (idx < 0)
									resourceFileName += "_" + String.format("%05d", progressive);

								else {
									resourceFileName = resourceFileName.substring(0, idx) +
											"_" + String.format("%05d", progressive) + resourceFileName.substring(idx);
								}
							}

							String resourcePathNew = "resources/" + type + "/" + resourceFileName;

							// copia risorse
							archiveStream.putArchiveEntry(Utility.createArchiveEntry(magIDForFile + "/" + resourcePathNew,
									request.getExtension(), resourceData.getLength()));

							IOUtils.copy(resourceStream, archiveStream);
							resourceStream.close();
							archiveStream.closeArchiveEntry();

							// aggiorna documento
							fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().
									lookupPrefix("http://www.w3.org/TR/xlink") + ":href", resourcePathNew);
						}
					}
					// copia MAG
					if(!addToCompressed)
						response.getNotExportedMags().add(mag.getIdMag());
					
					else {
						byte[] bytes = null;
						String extensionOfFiles = ".xml";
						if(!Mag.IIIF.equalsIgnoreCase(request.getFormat())) {
							bytes = UtilXML.convertDocumentToByteArray(documentMets != null ? documentMets : document);
						}
						else{
							Manifest manifest = manifest3Repository.getManifest(mag.getIdMag(), request.getUsage(), PathMapper.encode(mag.getIdMag()));
							if(manifest==null){
								logger.error("Manifest not found for "+mag.getIdMag());
							}
							else {
								bytes = objectMapper.writeValueAsBytes(manifest);
								extensionOfFiles = ".json";
							}
						}
						ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

						/***
						 * i manifest sono senza oggetti digitali e cartelle
						 */
						String fullPath = null;
						if(Mag.IIIF.equalsIgnoreCase(request.getFormat()))
							fullPath = magIDForFile +extensionOfFiles;
						else
							fullPath = magIDForFile + "/" + magIDForFile +extensionOfFiles;
						archiveStream.putArchiveEntry(
								Utility.createArchiveEntry(fullPath, request.getExtension(), bytes.length));
						
						IOUtils.copy(bais, archiveStream);
						bais.close();

						archiveStream.closeArchiveEntry();
						response.getExportedMags().add(mag.getIdMag());
					}
					
					partial++;
					exportDB.setMessage(((int) Math.round(partial * 100 / magResults.getTotalResults())) + "%");
					exportDao.update(exportDB);
				}
				
				count += exportBatchSize;
				magResults = UtilSolr.searchDocument(advancedSearch, server, count, exportBatchSize);
				exportMags = magResults.getResults();
			}
			
			server.close();

			exportDB.setStatus(0);
			exportDB.setMessage("File creato");
			exportDao.update(exportDB);
			
			response.setStatus(0);
			response.setMessage("File creato");
			
		} catch (ArchiveException e) {
			logger.error(e.getMessage(), e);
			response.setStatus(-1);
			response.setMessage(e.getMessage());
			exportDB.setStatus(-1);
			exportDB.setMessage(e.getMessage());
			
			try {
				exportDao.update(exportDB);
				
			} catch (DaoException de) {
				logger.error(e.getMessage(), e);
				response.setStatus(-2);
				response.setMessage(e.getMessage());
			}
			
		} catch (CompressorException e) {
			logger.error(e.getMessage(), e);
			response.setStatus(-1);
			response.setMessage(e.getMessage());
			exportDB.setStatus(-1);
			exportDB.setMessage(e.getMessage());
			
			try {
				exportDao.update(exportDB);
				
			} catch (DaoException de) {
				logger.error(e.getMessage(), e);
				response.setStatus(-2);
				response.setMessage(e.getMessage());
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			response.setStatus(-1);
			response.setMessage(e.getMessage());
			exportDB.setStatus(-1);
			exportDB.setMessage(e.getMessage());
			
			try {
				exportDao.update(exportDB);
				
			} catch (DaoException de) {
				logger.error(e.getMessage(), e);
				response.setStatus(-2);
				response.setMessage(e.getMessage());
			}
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			response.setStatus(-2);
			response.setMessage(e.getMessage());
		} finally {
			if(archiveStream != null) {
				try {
					archiveStream.close();
					
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		return response;
	}
	
	/**
	 * Esegui la vestizione in caso di export
	 * 
	 * @param document documento da vestire
	 */
	private void exportDress(Document document, SolrClient server) {
		List<ExternalMagReference> references = new ArrayList<ExternalMagReference>();
		List<String> groupTypes = Arrays.asList("img_group", "audio_group", "video_group");
		
		// ricerca sui nodi di tipo element
		NodeList elementNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "element");
		
		for(int i = 0; i < elementNodeList.getLength(); i++) {
			Element elementElem = (Element) elementNodeList.item(i);
			String resourceType = "img";
			int start = Integer.MIN_VALUE;
			int stop = Integer.MIN_VALUE;
			
			// tipo di risorse
			NodeList resourceNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "resource");
		   
		    if(resourceNode.getLength() > 0)
			    resourceType = resourceNode.item(0).getTextContent();
		    
		    // start e stop
		    NodeList startNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "start");
			NodeList stopNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "stop");
			   
			if(startNode.getLength() > 0) {
				NamedNodeMap attr = startNode.item(0).getAttributes();
				start = Integer.parseInt(getAttrValue(attr, "sequence_number"));
				
				if(stopNode.getLength() > 0) {
					attr = stopNode.item(0).getAttributes();			
					stop = Integer.parseInt(getAttrValue(attr, "sequence_number"));
				}
				else
					stop = start;
			}
			
			if(start != Integer.MIN_VALUE) {
				NodeList identifierNodeList = elementElem.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", "identifier");
				NodeList issueNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "issue");
				NodeList yearNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "year");
				
				// riferimenti esterni
				if(identifierNodeList.getLength() > 0) {
					String dcIdentifier = identifierNodeList.item(0).getTextContent();
					
					ExternalMagReference externalReference = new ExternalMagReference();
					externalReference.getIdentifiers().add(dcIdentifier);
					externalReference.setResourceType(resourceType);
					externalReference.setStart(start + "");
					externalReference.setStop(stop + "");
					
					if(issueNode.getLength() > 0)
						externalReference.setIssue(issueNode.item(0).getTextContent());
					
					if(yearNode.getLength() > 0)
						externalReference.setYear(yearNode.item(0).getTextContent());
					
					
					// query solr
					SearchData advancedSearch = new SearchData();
					advancedSearch.setFacets(false);
					advancedSearch.setFilterDoc(SearchData.UPDATE_DOC);
					advancedSearch.setIncludeDeleted(SearchData.EXCLUDE);
					advancedSearch.setIncludeDrafts(SearchData.EXCLUDE);
					advancedSearch.getBaseSearch().put("identifier", externalReference.getIdentifiers());
					
					if(externalReference.getIssue() != null)
						advancedSearch.getBaseSearch().put("issue/part_name", Arrays.asList(externalReference.getIssue()));

					if(externalReference.getYear() != null)
						advancedSearch.getBaseSearch().put("year/part_number", Arrays.asList(externalReference.getYear()));
					
					SearchResultList magResults = UtilSolr.searchDocument(advancedSearch, server, 0, 1);
					
					if(!magResults.getResults().isEmpty())
						externalReference.setExternalDocument(UtilXML.convertStringToDocumentXML(magResults.getResults().get(0).getMagInternal()));
					
					
					// se MAG esterno esistente
					if(externalReference.getExternalDocument() != null)
						references.add(externalReference);
				}
			}
		}
		
		// vestizione
		for(ExternalMagReference ref : references) {
			Map<String, List<String>> groupNames = new HashMap<String, List<String>>();
			Map<String, List<Element>> groups = new HashMap<String, List<Element>>();
			Map<String, List<Element>> resources = new HashMap<String, List<Element>>();
			
			// recupera le risorse e i gruppi già appartenenti al MAG da validare
			this.recoverOwnResources(document, groupNames, groups, resources, 
					groupTypes, Arrays.asList("img", "audio", "video", "ocr", "doc"));
			
			// aggiungi le risorse dall'esterno
			this.addExternalResources(document, ref.getExternalDocument(), groupNames, 
					groups, resources, groupTypes, ref.getResourceType(), 
					Integer.parseInt(ref.getStart()), Integer.parseInt(ref.getStop()));
			
			// aggiorna il documento con risorse aggiuntive dall'esterno
			this.dressUpdateMag(document, groups, resources);
		}
	}

	/**
	 * Recupera le risorse e i gruppi del MAG elaborato
	 * 
	 * @param document documento MAG XML
	 * @param groupNames mappa dei nomi dei gruppi
	 * @param groups mappa dei gruppi
	 * @param resources mappa delle risorse
	 * @param groupTypes lista tipi di gruppi (img_group, audio_group, video_group)
	 * @param resourceTypes lista tipi di gruppi (img, audio, video, ocr, doc)
	 */
	private void recoverOwnResources(Document document, 
			Map<String, List<String>> groupNames, 
			Map<String, List<Element>> groups,
			Map<String, List<Element>> resources, 
			List<String> groupTypes, List<String> resourceTypes) {
		
		for(String groupType : groupTypes) {
			groups.put(groupType, new ArrayList<Element>());
			groupNames.put(groupType, new ArrayList<String>());
			
			NodeList groupNodeList = document.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", groupType);
			
			for(int i = 0; i < groupNodeList.getLength(); i++) {
				Element groupNode = (Element) groupNodeList.item(i);
				String groupID = groupNode.getAttribute("ID");
				
				if(!groupNames.get(groupType).contains(groupID))
					groupNames.get(groupType).add(groupID);
				
				groups.get(groupType).add(groupNode);
				groupNode.getParentNode().removeChild(groupNode);
			}
		}
		
		for(String resourceType : resourceTypes) {
			resources.put(resourceType, new ArrayList<Element>());
			
			NodeList resourceNodeList = document.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
			
			for(int i = 0; i < resourceNodeList.getLength(); i++) {
				Element resourceNode = (Element) resourceNodeList.item(i);
				resources.get(resourceType).add((Element) resourceNodeList.item(i));
				resourceNode.getParentNode().removeChild(resourceNode);
			}
		}
	}

	/**
	 * Aggiunge le risorse e gli eventuali gruppi, esterni al MAG in elaborazione
	 * 
	 * @param document documento MAG da vestire
	 * @param externalDocument documento MAG esterno
	 * @param groupNames nomi gruppi già elaborati
	 * @param groups gruppi estratti
	 * @param resources risorse estratte
	 * @param groupTypes tipi di gruppi
	 * @param resourceType tipo di risorsa da cercare
	 * @param start estremo inferiore intervallo di ricerca sequence number nel documento MAG esterno
	 * @param stop estremo superiore intervallo di ricerca sequence number nel documento MAG esterno
	 */
	private void addExternalResources(Document document, 
			Document externalDocument, 
			Map<String, List<String>> groupNames, 
			Map<String, List<Element>> groups,
			Map<String, List<Element>> resources, 
			List<String> groupTypes, String resourceType, int start, int stop) {

		// preleva gruppi da esterno
		for(String groupType : groupTypes) {
			NodeList groupNodeList = externalDocument.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", groupType);
			
			for(int i = 0; i < groupNodeList.getLength(); i++) {
				Element groupNode = (Element) groupNodeList.item(i);
				String groupID = groupNode.getAttribute("ID");
				
				if(!groupNames.get(groupType).contains(groupID)) {
					groupNames.get(groupType).add(groupID);
					
					groups.get(groupType).add((Element) document.
							importNode(groupNode, true));
				}
			}
		}
		
		NodeList resourceNodeList = externalDocument.getElementsByTagNameNS(
				"http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
		
		// preleva risorse
		for(int i = 0; i < resourceNodeList.getLength(); i++) {
			Element resourceNode = (Element) resourceNodeList.item(i);
			
			// lettura sequence number
			int seqNumber = Integer.parseInt(resourceNode.
					getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", 
							"sequence_number").item(0).getTextContent());
			
			if(seqNumber >= start && seqNumber <= stop) {
				resources.get(resourceType).add((Element) document.
						importNode(resourceNode, true));
			}
		}
	}

	/**
	 * Verifica la presenza di gruppi da prelevare dal MAG esterno
	 * 
	 * @param document documento da aggiornare
	 * @param groupNames ID gruppi estratti
	 * @param groups gruppi estratti
	 * @param resourceType tipo di risorsa in elaborazione
	 * @param resourceNode tag risorsa
	 * @param groupNodeList lista gruppi
	 *//*
	private void verifyExternalGroups(Document document, 
			Map<String, List<String>> groupNames, 
			Map<String, List<Element>> groups,
			String resourceType, Element resourceNode,
			NodeList groupNodeList) {
		
		if(resourceNode.hasAttribute(resourceType + "groupID")) {
			String groupID = resourceNode.getAttribute(resourceType + "groupID");
			
			if(groupNames.get(resourceType + "_group").contains(groupID)) {
				for(int i = 0; i < groupNodeList.getLength(); i++) {
					Element groupNode = (Element) groupNodeList.item(i);
					
					if(groupID.equals(groupNode.getAttribute("ID"))) {
						groups.get(resourceType + "_group").add((Element) document.
								importNode(groupNode, true));
						
						groupNames.get(resourceType + "_group").add(groupID);
					}
				}
			}
		}
	}*/
	
	/**
	 * Aggiorna il documento per la vestizione
	 * 
	 * @param document documento MAG da vestire
	 * @param groups gruppi da aggiungere
	 * @param resources risorse da aggiungere
	 */
	private void dressUpdateMag(Document document, Map<String, List<Element>> groups, Map<String, List<Element>> resources) {
		Element genNode = (Element) document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen").item(0);
		
		for(Element imgGroup : groups.get("img_group"))
			genNode.appendChild(imgGroup);
		
		for(Element audioGroup : groups.get("audio_group"))
			genNode.appendChild(audioGroup);
		
		for(Element videoGroup : groups.get("video_group"))
			genNode.appendChild(videoGroup);
		
		genNode.normalize();
		
		
		
		Element metadigitNode = (Element) document.getElementsByTagNameNS(
				"http://www.iccu.sbn.it/metaAG1.pdf", "metadigit").item(0);
		
		for(Element img : resources.get("img"))
			metadigitNode.appendChild(img);
		
		for(Element audio : resources.get("audio"))
			metadigitNode.appendChild(audio);
		
		for(Element video : resources.get("video"))
			metadigitNode.appendChild(video);
		
		for(Element ocr : resources.get("ocr"))
			metadigitNode.appendChild(ocr);
		
		for(Element doc : resources.get("doc"))
			metadigitNode.appendChild(doc);
		
		metadigitNode.normalize();
	}

	/**
	 * Preleva valore dell'attributo dalla mappa degli attributi di un nodo elemento
	 * 
	 * @param attr mappa degli attributi
	 * @param string attributo da cercare
	 * @return
	 */
	private String getAttrValue(NamedNodeMap attr, String string) {
		for (int j = 0; j < attr.getLength(); j++) {
			Node node = attr.item(j);
			
		    if (node.getLocalName().equals(string))
		        return node.getTextContent();
		}
		
		return "";
	}
	
	/**
	 * Invia il file di export da scaricare
	 * 
	 * DOWNLOAD PACCHETTO EXPORT (PANNELLO EXPORT)
	 * 
	 * @param fileName nome del file di export
	 * @return InputStream stream per il download
	 */
	public InputStream downloadExportZip(String fileName) {
		File compressedFile = new File(ContentStatic.getProperties().getProperty("resourceDIRE"), fileName);
		
		if(!compressedFile.exists())
			return null;
		
		try {
			return new FileInputStream(compressedFile);
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Cancella l'export selezionato
	 * 
	 * CANCELLAZIONE PACCHETTO EXPORT (PANNELLO EXPORT)
	 * 
	 * @param filename file export da cancellare
	 * @return true se è stato cancellato
	 */ 
	public boolean deleteExportZip(String filename) {
		try {
			DBExport export = exportDao.getExportByFilename(filename);
			File compressedFile = new File(ContentStatic.getProperties().getProperty("resourceDIRE"), filename);
			
			if(compressedFile.exists() && export != null) {
				boolean result = compressedFile.delete();
				
				if(result) {
					try {
						exportDao.delete(export);
						
					} catch (DaoException e) {
						logger.error(e.getMessage(), e);
					}
				}
				
				return result;
			}
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
		
		return true;
	}
	
	/**
	 * Restituisce la lista degli export completati, eseguendo la query sul database
	 * 
	 * LETTURA PACCHETTI EXPORT DISPONIBILI (PANNELLO EXPORT)
	 * 
	 * @return List<JsonExport> lista degli export completati
	 */
	public List<JsonExport> getAllCompleteExports() {
		List<JsonExport> results = new ArrayList<JsonExport>();
		
		try {
			List<DBExport> exportsDB = exportDao.getAllCompleteExports();
			
			for(DBExport expDB : exportsDB) {
				File file = new File(ContentStatic.getProperties().getProperty("resourceDIRE"), expDB.getFile());
				
				if(file.exists()) {
					JsonExport jsonExp = new JsonExport();
					jsonExp.setId(expDB.getId());
					jsonExp.setFile(expDB.getFile());
					jsonExp.setUsername(expDB.getTecaUser().getUsername());
					jsonExp.setStatus(expDB.getStatus());
					jsonExp.setMessage(expDB.getMessage());
					results.add(jsonExp);
				}
			}
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			return results;
		}
		
		return results;
	}

	/**
	 * Restituisce i primi n valori indicizzati per il campo specificato 
	 * 
	 * AUTOCOMPLETE PER COMPILAZIONE CAMPI EDITOR
	 * 
	 * @param field nome campo
	 * @param n numero valori indicizzati
	 * @param prefix prefisso per autocomplete
	 * @return List<String> la lista degli n valori
	 */
	public List<String> getFieldValues(String field, int n, String prefix) {
		List<String> results = new ArrayList<String>();
		String realField = NormalizationUtility.getSolrOriginalField(field) + "String";
		
		try {
			String prefixSearch = "";
			
			if(prefix != null && !prefix.isEmpty()) {
				String[] tokens = prefix.split("[ \t\r\n\f]+");
				
				for(int i = 0; i < tokens.length; i++) {
					if(i > 0)
						prefixSearch += " ";
					
					prefixSearch += tokens[i] + "*";
				}
			}
			
			SearchResultList solrResults = UtilSolr.getCount(prefix != null && !prefix.isEmpty() ? field : "", 
					prefix != null && !prefix.isEmpty() ? prefixSearch : "", true, Arrays.asList(realField), n, "index");
			
			results.addAll(solrResults.getFacetValues(realField));
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		}
		
		return results;
	}
	
	/**
	 * Servizio di restituzione delle statistiche sugli oggetti digitali
	 * 
	 * STATISTICHE OGGETTI DIGITALI (APPOSITO MENÙ)
	 * 
	 * @param group lista dei campi da raggruppare
	 * @param queryData filtri
	 * @return JsonDigitalObjectStatsTable tabelle delle statistiche
	 */
	public JsonDigitalObjectStatsTable getDigitalObjectStats(List<String> group, 
			Map<String, List<String>> queryData) {
		
		// creazione tabella json
		JsonDigitalObjectStatsTable table = new JsonDigitalObjectStatsTable();
		table.getFields().addAll(group);
		
		// query Solr
		List<DigitalObjectStats> stats = UtilSolr.getDigitalObjectsStats(group, queryData);
		
		// costruzione righe tabella
		for(DigitalObjectStats row : stats) {
			JsonDigitalObjectStatsRow jsonRow = new JsonDigitalObjectStatsRow();
			jsonRow.getValues().addAll(row.getValues());
			jsonRow.setCount(row.getCount());
			jsonRow.setNumberImg(row.getNumberImg());
			jsonRow.setNumberAudio(row.getNumberAudio());
			jsonRow.setNumberVideo(row.getNumberVideo());
			jsonRow.setNumberOcr(row.getNumberOcr());
			jsonRow.setNumberDoc(row.getNumberDoc());
			table.getRows().add(jsonRow);
		}
		
		return table;
	}
	
	/***
	 * Esportazione risultati ricerca in formato CSV
	 * 
	 * EXPORT FORMATO CSV RISULTATI RICERCA AVANZATA
	 * 
	 * @param request richiesta di ricerca
	 * @return Reader stream del CSV
	 */
	public Reader exportCsv(JsonSearchRequest request) {
		request.setStart(0);
		JsonSearchResultList resultList = this.search(request, Integer.MAX_VALUE, false, false);
		StringBuilder csvBuilder = new StringBuilder();
		
		csvBuilder.append("\"Identifier\",\"Titolo\",\"Anno / Numero parte\","
				+ "\"Estremi fasc. / Titolo parte\",\"Livello\",\"Pubbl.\"\n");
		
		for(JsonMagPreview mag : resultList.getMags()) {
			csvBuilder.append("\"");
			
			for(int i = 0; i < mag.getIdentifiers().size(); i++) {
				if(i > 0)
					csvBuilder.append(" | ");
				
				csvBuilder.append(mag.getIdentifiers().get(i));
			}
			
			csvBuilder.append("\",");
			csvBuilder.append("\"");
			
			for(int i = 0; i < mag.getTitles().size(); i++) {
				if(i > 0)
					csvBuilder.append(" | ");
				
				csvBuilder.append(mag.getTitles().get(i));
			}
			
			csvBuilder.append("\",");
			csvBuilder.append("\"");
			
			if(mag.getYearPartNumber() != null && !mag.getYearPartNumber().isEmpty())
				csvBuilder.append(mag.getYearPartNumber());
				
			csvBuilder.append("\",");
			csvBuilder.append("\"");
			
			if(mag.getIssuePartName() != null && !mag.getIssuePartName().isEmpty())
				csvBuilder.append(mag.getIssuePartName());
				
			csvBuilder.append("\",");
			csvBuilder.append("\"");
			csvBuilder.append(mag.getLevel());
			csvBuilder.append("\",");
			csvBuilder.append("\"");
			
			for(int i = 0; i < mag.getPublicFlags().size(); i++) {
				if(i > 0)
					csvBuilder.append(" | ");
				
				csvBuilder.append(mag.getPublicFlags().get(i).equals("1") ? "Si" : "No");
			}
			
			csvBuilder.append("\"\n");
		}
		
		return new StringReader(csvBuilder.toString());
	}

	/**
	 * Crea oggetto JSON campo singolo valore
	 * 
	 * @param name nome reale campo
	 * @param label label campo
	 * @param value valore campo
	 * @return JsonMagDetailField oggetto JSON
	 */
	@SuppressWarnings("unchecked")
	private JsonMagDetailField createMetadataJsonField(String name, String label, Object value) {
		JsonMagDetailField object = new JsonMagDetailField();
		object.setName(name);
		object.setLabel(label);
		
		if(value instanceof String)
			object.setValue(value);
		
		else
			object.setValues((List<Object>) value);
		
		return object;
	}
	
	/**
	 * Calcola il path finale dell'oggetto digitale in export
	 * 
	 * @param resourceFile nome file risorsa (delivery)
	 * @return path finale
	 */
	private String getExportFileName(String resourceFile) {
		String[] pointParts = resourceFile.split("\\.");
		StringBuilder result = new StringBuilder();
		
		if(pointParts.length > 0) {
			String[] underscoreParts = pointParts[0].split("\\_");
			
			for(int i = 0; i < underscoreParts.length - 2; i++) {
				if(i > 0)
					result.append("_");
				
				result.append(underscoreParts[i]);
			}
			
			for(int i = 1; i < pointParts.length; i++)
				result.append("." + pointParts[i]);
		}
		
		return result.toString();
	}

}
