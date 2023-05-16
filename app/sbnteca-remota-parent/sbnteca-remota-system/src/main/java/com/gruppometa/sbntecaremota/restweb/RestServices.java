package com.gruppometa.sbntecaremota.restweb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.objects.ImportReport;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.db.*;
import com.gruppometa.sbntecaremota.objects.json.*;
import com.gruppometa.sbntecaremota.restweb.objects.*;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.Utility;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.solr.client.solrj.SolrServerException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Tale classe si occupa della definizione di tutti i servizi REST per Teca Digitale
 *
 *
 */
@Component
@Scope("request")
@Path("/rest")
@Tag(name = "Servizi generali")
public class RestServices {
	
	private static Logger logger = LoggerFactory.getLogger(RestServices.class);

	// servizio operazioni ricerca MAG
	@Autowired
	private MagSearchService magSearchService;

	// servizio dettaglio MAG
	@Autowired
	private MagDetailService magDetailService;

	// servizio import MAG
	@Autowired
	private MagImportService magImportService;

	// servizio progetti MAG
	@Autowired
	private MagProjectService magProjectService;

	@Autowired
	private MagUserService magUserService;

	@Autowired
	private MagEditorService magEditorService;

	@Autowired
	private DBOaiIdentifierDao dbOaiIdentifierDao;
	
	// lista filtri accettati
	private static List<String> PROJECT_QUERY_FILTERS = Arrays.asList("all", "new", "import");
	
	
	
	/**
	 * Il servizio dato in input il percorso principale dove sono caricate le 
	 * cartelle da poter importare in magteca, mostra a video le cartelle in essa presenti
	 * 
	 * Caricamento dei progetti disponibili per l'importazione/aggiornamento importazione/selezione progetto per creazione bozza
	 * 
	 * @return lista dei progetti
	 */
	@GET
	@Path("/projects")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")		
	public Response getProjects(@QueryParam("filter") String filter,
								@QueryParam("vfs") @DefaultValue("true") boolean vfsProjects) {
		if(filter == null || filter.isEmpty())
			filter = "all";
		
		if(!PROJECT_QUERY_FILTERS.contains(filter)) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Filtro non specificato correttamente").build();
		}
		
		return Response.ok(magProjectService.loadProjects(filter, vfsProjects)).build();
	}		

	/**
	 * Restituisce la rappresentazione a cartelle delle risorse contenute nel progetto
	 * Servizio PICKER EDITOR
	 * 
	 * @param project progetto di riferimento
	 * @param relativePath path relativo
	 * @param filter filtro (img, audio, video, ocr, doc)
	 * @param prefix filtro prefisso nome file
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/resources")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")		
	public Response getProjectResources(@QueryParam("project") String project, @QueryParam("rel") String relativePath, 
			@QueryParam("filter") String filter, @QueryParam("prefix") String prefix, @QueryParam("depth") int depth) throws SolrServerException {
		
		if(project == null || project.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Progetto non specificato").build();
		
		if(relativePath == null)
			relativePath = "";
		
		//return Response.ok(magProjectService.getProjectFiles(project, relativePath, filter, prefix, depth)).build();
		return Response.ok(magProjectService.getProjectFilesVirtuale(project, relativePath, filter, prefix, depth)).build();
	}		
	
	/**
	 * Restituisce le informazioni calcolate sull'immagine digitale
	 * ACQUISIZIONE AUTOMATICA DEI CAMPI DI UN OGGETTO DIGITALE EDITOR
	 * 
	 * @param relativePath path relativo della risorsa digitale
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/resource/info")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")		
	public Response getProjectResources(@QueryParam("rel") String relativePath) {
		if(relativePath == null || relativePath.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Path relativo della risorsa digitale non specificato").build();
		
		JsonResourceInfo info = magProjectService.getResourceInfo(relativePath);
		
		if(info == null)
			return Response.status(Response.Status.NOT_FOUND).entity("Risorsa specificata non esistente").build();
		
		return Response.ok(info).build();
	}




	@GET
	@Path("/check_id")
	@Produces(MediaType.TEXT_PLAIN+ "; charset=UTF-8")
	public Response checkId(@QueryParam("id") String id) {
		if(id.replaceAll(UtilSolr.REGEX_OTHER_SPECIAL_CHARACTERS,"-").equals(id))
			return Response.ok("The characters of the ID are ok. ID: "+id).build();
		else{
			Mag mag = UtilSolr.selectDocumentById(id);
			if(mag==null){
				return Response.status(Status.NOT_FOUND).build();
			}
			else{
				mag.setIdMag(id.replaceAll(UtilSolr.REGEX_OTHER_SPECIAL_CHARACTERS,"-"));
				List<Mag> list = new ArrayList<>();
				list.add(mag);
				List<String> oldIds = new ArrayList<>();
				oldIds.add(id);
				try {
					UtilSolr.insertListMag(list);
					UtilSolr.deleteMag(oldIds, true);
					return Response.ok("Created new id:"+mag.getIdMag()).build();
				} catch (Exception e) {
					return Response.status(Status.INTERNAL_SERVER_ERROR).build();
				}
			}
		}
	}

	@GET
	@Path("/oai_identifier")
	@Produces(MediaType.TEXT_PLAIN+ "; charset=UTF-8")
	public Response checkOaiIdentifer(@QueryParam("id") String id) {
		String oai = UtilSolr.getOaiIdentifier(id);
		String identifier = UtilSolr.getFirstIdentifier(id);
		String oaiNew = null;
		if(identifier==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		if(oai!=null && oai.trim().length()==0){

			// ricerca nel database e aggiungi agli oai identifiers del batch
			try {
				DBOaiIdentifier oi = new DBOaiIdentifier();
				oi.setIdentifier(identifier);

				DBOaiIdentifier search = dbOaiIdentifierDao.getOAIIdentifierByID(identifier);
				oi.setCount(search == null ? 0 : search.getCount() + 1);
				oaiNew = (oi.getIdentifier() + (oi.getCount() == 0 ? "" : "_" + oi.getCount()));
				dbOaiIdentifierDao.update(oi);
				UtilSolr.setFieldSolr(id, "oai_identifier",oaiNew , false);
			} catch (DaoException e) {
				logger.error(e.getMessage(), e);
			}
			return Response.ok("Created new oai_identifier:"+oaiNew).build();
		}
		else
			return Response.ok("Found oai_identifier: "+oai).build();
	}

	/**
	 * Restituisce il documento XML del MAG riferito al progetto
	 * Esegue una ricerca nel fle system, usato attraverso pulsante nella schermata di import 
	 * di selezione MAG per vedere il documento XML
	 * 
	 * @param project progetto di riferimento
	 * @param relativePath path relativo del MAG
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/mag")
	@Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")		
	public Response getMagXmlDocument(@QueryParam("project") String project,
									  @QueryParam("rel") String relativePath) {
		if(project == null || project.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Progetto non specificato").build();
		
		if(relativePath == null || relativePath.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Path relativo del MAG non specificato").build();
		
		try {
			return Response
					.ok(magProjectService.getMagXmlDocument(project, relativePath))
					.build();
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}		
	
	/**
	 * Il servizio preso in input le cartelle selezionate, il percorso di base dei file
	 * il tipo di mag, i valori dei flag di acquisizione ed esportazione, ed il valore
	 * del flag pubblica aggiorna i mag nelle cartelle selezionate
	 * 
	 * CREAZIONE PROCESSO IMPORT UPDATE
	 * 
	 * @param JsonImportConfiguration importMagConfig contiene le informazioni menzionate prima
	 * @return JSON di risposta presa in carico nuova importazione
	 * */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("/runUpdate")
	public Response updateMag(JsonImportConfiguration importMagConfig) {	
		// importazione
		logger.info("Servizio di aggiornamento MAG");
		return this.prepareImportJob(importMagConfig);
	}
	
	/**
	 * Servizio di presa in carico di un processo di importazione
	 * 
	 * CREAZIONE PROCESSO IMPORT
	 * 
	 * @param importMagConfig impostazioni del processo di importazione
	 * @return JSON di risposta per la presa in carico
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("/runImport")
	public Response prepareImportJob(JsonImportConfiguration importMagConfig) {
		return Response.ok(magImportService.initializeImport(importMagConfig, "imp", true)).build();
	}
	
	/**
	 * Servizio di recupero e ripresa in carico di un processo di importazione
	 * 
	 * RIPRENDI PROCESSO IMPORT STOPPATO
	 * NON USATO!!!
	 * 
	 * @param importMagConfig impostazioni del processo di importazione
	 * @return JSON di presa in carico di un riavvio di un processo
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/recoverImport")
	public Response recoverImportJob(@QueryParam("id") String jobID) {
		try {
			ImportReport reportXmlObject = magImportService.recoverImport(jobID);
			
			if(reportXmlObject == null) {
				logger.error("Processo di importazione terminato correttamente o attualmente in esecuzione");
				JsonImportReport report = new JsonImportReport();
				report.setStatus(-2);
				report.setMessage("Processo di importazione terminato correttamente o attualmente in esecuzione");
				return Response.ok(report).build();
			}
			
			// trasformazione oggetto report in oggetto json
			JsonImportReport report = Utility.toJsonReportObject(reportXmlObject);
			return Response.ok(report).build();
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-3);
			report.setMessage(e.getMessage());
			return Response.ok(report).build();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-4);
			report.setMessage(e.getMessage());
			return Response.ok(report).build();
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-5);
			report.setMessage(e.getMessage());
			return Response.ok(report).build();
		}
	}

	/**
	 * Servizio di effettiva esecuzione del processo di importazione
	 * 
	 * ESECUZIONE PROCESSO DI IMPORT
	 * 
	 * @param jobID ID del job di importazione
	 * @return JSON di fine importazione
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/import")
	public Response importJob(@QueryParam("id") String jobID) {
		return Response.ok(magImportService.launchImportJob(jobID, true)).build();
	}
	
	
	/**
	 * Servizio di controllo dello stato di un processo di importazione
	 * 
	 * STATO DEL PROCESSO DI IMPORT (REPORT PROCESSO IMPORT/IMPORT UPDATE)
	 * 
	 * @param jobID ID del job di importazione
	 * @param reportType tipo di report (ALL, WARNING, ERROR, LOG) (opzionale)
	 * @return JSON di riepilogo stato o report in formato XML/LOG
	 */
	@GET
	@Path("/import/status")
	public Response getImportJobStatus(@QueryParam("id") String jobID, @QueryParam("report") String reportType) {
		return magImportService.getReportStatus(jobID, reportType, true);
	}

	/**
	 * Servizio di presa in carico di un processo di validazione generale
	 * 
	 * CREAZIONE PROCESSO DI VALIDAZIONE
	 * 
	 * @param importMagConfig impostazioni per il lancio del processo di validazione
	 * @return JSON di risposta per la presa in carico
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("/runValidate")
	public Response prepareValidateJob(JsonImportConfiguration importMagConfig) {
		return Response.ok(magImportService.initializeImport(importMagConfig, "val", false)).build();
	}

	/**
	 * Servizio di effettiva esecuzione di un processo di validazione generale
	 * 
	 * ESECUZIONE PROCESSO DI VALIDAZIONE
	 * 
	 * @param jobID ID del job di validazione
	 * @return JSON di fine validazione
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/validate")
	public Response validate(@QueryParam("id") String jobID) {
		return Response.ok(magImportService.launchImportJob(jobID, false)).build();
	}

	/**
	 * Servizio di presa in carico di un processo di validazione MD5
	 * 
	 * CREAZIONE PROCESSO DI VALIDAZIONE MD5
	 * NON USATO!!!
	 * 
	 * @param importMagConfig impostazioni per il lancio del job di validazione
	 * @return JSON di risposta per il servizo di presa in carico
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("/runValidateMd5")
	public Response prepareValidateMd5Job(JsonImportConfiguration importMagConfig) {
		return Response.ok(magImportService.initializeImport(importMagConfig, "valmd5", false)).build();
	}

	/**
	 * Servizio di effettiva esecuzione di un processo di validazione MD5
	 * 
	 * ESECUZIONE PROCESSO DI VALIDAZIONE
	 * NON USATO!!!
	 * 
	 * @param jobID ID del job di validazione
	 * @return JSON di fine processo
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("/validateMd5")
	public Response validateMd5(@QueryParam("id") String jobID) {
		return Response.ok(magImportService.launchImportJob(jobID, false)).build();
	}

	/**
	 * Servizio di controllo dello stato di un processo di validazione (sia generale sia MD5)
	 * 
	 * STATO PROCESSO DI VALIDAZIONE (REPORT)
	 * 
	 * @param jobID ID del job di validazione 
	 * @param reportType tipo di report (ALL, WARNING, ERROR, LOG)
	 * @return risposta JSON o report in formato XML / LOG
	 */
	@GET
	@Path("/validate/status")
	public Response getValidationJobStatus(@QueryParam("id") String jobID, @QueryParam("report") String reportType) {
		return magImportService.getReportStatus(jobID, reportType, false);
	}
	
	/**
	 * Il servizio restituisce la lista dei MAG contenuti all'internod el progetto specificato
	 * 
	 * PULSANTE VISUALIZZA TUTTI/VISUALIZZA RECENTI, PER CARICARE E FILTRARE I MAG CARICATI TRAMITE UPLOAD
	 * RECENTI = MAG NON IMPORTATI OPPURE MAG IMPORTATI MA SUCCESSIVAMENTE MODIFICATI DA UPLOAD SUCCESSIVO
	 * 
	 * @param project nome del progetto
	 * @return List<String> lista dei path dei documenti MAG del progetto
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("/selectmag")
	public List<String> selectMag(@QueryParam("project") String project, @QueryParam("recent") Boolean recent) {
		if(project == null || project.isEmpty())
			return Collections.emptyList();
		
		if(recent == null)
			recent = false;
		
		return magProjectService.getMagsByProjectDirectory(project, recent);
	}
	
	/**
	 * Servizio di restituzione documento MAG XML
	 * 
	 * DOCUMENTO XML MAG INDICIZZATO IN SOLR (NO FILE SYSTEM!)
	 * 
	 * @param idMag ID MAG o path MAG
	 * @param code codice (0=MAG originale, 1=MAG acquisizione, 2=MAG esportazione)
	 * @return Documento documento XML
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")	
	@Path("getmagxml")
	public Response getMag(@QueryParam("id") String idMag, @QueryParam("code") int code) {
		// controllo parametri richiesta
		if(idMag == null || idMag.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("ID del MAG non specificato").build();
		
		if(code > 4) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Codice di versione "
					+ "del MAG richiesta non specificato correttamente").build();
		}
		
		try {
			return Response.ok(magSearchService.getMagXmlDocument(idMag, code)).build();
			
		} catch (FileNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}


	/**
	 * Servizio di cambio usage, che esegue un aggiornamento
	 * 
	 * CREAZIONE PROCESSO DI CAMBIO USAGE (EQUIVALE A PROCESSO DI IMPORT UPDATE)
	 * 
	 * @param usageConfig
	 * @return risposta del servizio, legato al servizo di importazione
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("/runChangeUsage")
	public Response changeUsage(JsonImportConfiguration usageConfig) {
		// avvia aggiornamento
		usageConfig.setImportUpdate(1);
		usageConfig.setOverwrite(1);
		return this.updateMag(usageConfig);
	}
	
	/**
	 * Servizio di cancellazione MAG
	 * 
	 * CANCELLAZIONE MAG INDICIZZATI IN SOLR
	 * 
	 * @param request JSON di richiesta cancellazione
	 * @return Response risposta servizio
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/deleteMag")
	public Response deleteMag(JsonDeleteRequest request) {
		logger.info("Servizio di cancellazione MAG in corso");
		
		try {
			// crea liste mag da cancellare e non trovati
			List<String> deleted = magSearchService.deleteMags(request);
			List<String> notFound = new ArrayList<String>();
			logger.info("Servizio di cancellazione MAG terminato, cancellati " + deleted.size() + " MAG");

			// risposta finale
			JsonDeleteReport reportOperation = new JsonDeleteReport();
			reportOperation.setProcessedMags(deleted.size());
			reportOperation.setDeletedMags(deleted);
			reportOperation.setMagsNotFound(notFound);
			return Response.ok(reportOperation).build();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).encoding(e.getMessage()).build();
		}
	}
	
	/**
	 * Il servizio si occupa di eseguire una ricerca su Solr, restituendo una lista di risultati
	 * 
	 * RICERCA AVANZATA MAG
	 * 
	 * @param request JSON di richiesta ricerca MAG
	 * @return JsonSearchResultList oggetto JSON di risposta
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/searchMag")
	public JsonSearchResultList searchMag(JsonSearchRequest request, 
			@QueryParam("facetOnly") Boolean facetOnly, @QueryParam("draft") Boolean isDraft) {
		
		return magSearchService.search(request, facetOnly != null ? 0 : request.getLength(), true, isDraft != null);
	}

	/**
	 * Il servizio si occupa di eseguire una ricerca delle bozze su Solr, restituendo una lista di risultati
	 * 
	 * RICERCA BOZZE EDITOR
	 * 
	 * @param request JSON di richiesta ricerca MAG
	 * @return List<JsonDraft> lista delle bozze
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/searchDraft")
	public List<JsonDraft> searchDraft(JsonSearchRequest request) {
		return magSearchService.searchDraft(request);
	}

	/**
	 * Il servizio si occupa di eseguire una ricerca delle bozze su Solr, restituendo una lista di risultati
	 * 
	 * CANCELLAZIONE BOZZA EDITOR
	 * 
	 * @param request JSON di richiesta ricerca MAG
	 * @return List<JsonDraft> lista delle bozze
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/deleteDraft")
	public Boolean deleteDraft(@QueryParam("id") String draftID) {
		return magSearchService.deleteDraft(draftID);
	}
	
	/**
	 * Restituisce le informazioni statistiche
	 * 
	 * ELABORATI STATISTICI
	 * 
	 * @param request oggetto di richiesta JSON
	 * @return lista delle faccette, con valori e risultati
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/stats")
	public List<JsonStatsResult> getStats(JsonStatsRequest request) {
		return magSearchService.getStats(request.getFieldQuery(), 
				request.getValueQuery(), request.getFacetFields());
	}

	/**
	 * Restituisce le informazioni statistiche sugli oggetti digitali
	 * 
	 * STATISTICHE NUMERICHE OGGETTI DIGITALI
	 * 
	 * @param request oggetto di richiesta JSON
	 * @return JsonDigitalObjectStatsTable tabella delle statistiche sugli oggetti digitali
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/digitalObjectStats")
	public Response getStats(JsonDigitalObjectStatsRequest request) {
		if(request.getGroup().isEmpty()) {
			return Response.
					status(Response.Status.BAD_REQUEST).
					entity("Deve essere specificato almeno un campo per il raggruppamento").
					build();
		}
		
		return Response.ok(magSearchService.getDigitalObjectStats(request.getGroup(), request.getFieldMap())).build();
	}

	/**
	 * Restituisce i primi n valori di un campo Solr
	 * 
	 * SERVIZIO AUTOCOMPLETE VALORI CAMPI PER EDITOR
	 * 
	 * @param fieldName nome campo
	 * @param size numero valori da restituire
	 * @param prefix prefisso per autocomplete
	 * @return Response risposta finale servizio
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/values")
	public Response getFieldValuesAutocomplete(@QueryParam("field") String fieldName, @QueryParam("N") Integer size, 
			@QueryParam("prefix") String prefix) {
		
		
		if(fieldName == null || fieldName.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Campo non specificato").build();
		
		if(size == null)
			size = Integer.MAX_VALUE;
		
		if(size <= 0)
			return Response.status(Response.Status.BAD_REQUEST).entity("Numero di valori negativo, non specificato correttamente").build();
		
		return Response.ok(magSearchService.getFieldValues(fieldName, size, prefix)).build();
	}

	/**
	 * Restituisce una lista dei possibili parent, data una query iniziale fulltext
	 * 
	 * RICERCA MAG PADRI COMPILAZIONE STRU EDITOR
	 * NON USATO!!!
	 * 
	 * @param content
	 * @return List<JsonMagPreview> lista delle preview
	 */
	@GET
	@Path("/parent_choice")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")		
	public List<JsonMagPreview> getProjectResources(@QueryParam("content") String content, @QueryParam("N") Integer resultSize) {
		if(resultSize == null)
			resultSize = 10;
		
		if(resultSize < 0)
			resultSize = Integer.MAX_VALUE;
		
		
		JsonSearchRequest request = new JsonSearchRequest();
		request.getFieldMap().put("text", Arrays.asList(content));
		List<JsonMagPreview> results = new ArrayList<JsonMagPreview>(magSearchService.search(request, resultSize, false, false).getMags());
		
		for(JsonMagPreview preview : results)
			preview.getPublicFlags().clear();
		
		return results;
	}		

	/**
	 * Esegue la normalizzazione di un certo numero di MAG, specificati tramite ID,
	 * con sostituzione di valori per una serie di campi
	 * 
	 * NORMALIZZAZIONE MAG SELEZIONATI DA RICERCA AVANZATA
	 * 
	 * @param request oggetto richiesta JSON
	 * @return JsonNormalizationResponse risposta contenente l'esito e i MAG modificati
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/normalize")
	public JsonNormalizationResponse normalize(JsonNormalizationRequest request) {
		return magSearchService.normalize(request);
	}
	
	/**
	 * Servizio di visualizzazione dei valori corrispondenti su un determinato campo
	 * sui MAG selezionati
	 * 
	 * LISTA DEI VALORI E CONTEGGIO DEI DOCUMENTI ASSOCIATI AD UN CAMPO 
	 * RISPETTO AI MAG SELEZIONATI DA RICERCA AVANZATA (NORMALIZZAZIONE VALORI)
	 * 
	 * @param fieldName campo Solr da valutare
	 * @param ids lista degli ID MAG selezionati per l'operazione
	 * @return List<JsonFieldValueCount> lista dei valori e numero di documenti per ciascun valore
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/normalizationFacet")
	public List<JsonFieldValueCount> normalizationSearchMag(@QueryParam("field") String fieldName, JsonSearchRequest request) {
		return magSearchService.getNormalizationFacet(fieldName, request);
	}

	/**
	 * Il servizio esegue la cancellazione di un progetto dalla directory di importazione
	 * 
	 * CANCELLAZIONE PROGETTO SU FILE SYSTEM
	 * 
	 * @return JsonDeleteProject resoconto finale
	 */
	@GET
	@Path("/deleteFolder/{project}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")		
	public JsonDeleteProject deleteFolder(@PathParam("project") String project) {
		JsonDeleteProject json = new JsonDeleteProject();
		File projectFile = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), project);
	    
		if(!projectFile.exists()) {
			logger.warn("Progetto già cancellato o non esistente");
//			json.setStatus(-1);
//			json.setMessage("Progetto già cancellato o non esistente");
//			return json;
		}
	    
		try {
			magProjectService.deleteProject(projectFile);
			
		} catch (Exception e) {
			logger.error("Cancellazione del progetto non riuscita", e);
			json.setStatus(-2);
			json.setMessage("Cancellazione del progetto non riuscita");
			return json;
		}
		
		json.setStatus(0);
		json.setMessage("Progetto correttamente cancellato");
		return json;
	}		
	
	/**
	 * Servizio di restituzione riepilogo dettaglio MAG
	 * 
	 * SCHEDA DI DETTAGLIO MAG 
	 * 
	 * @param idMag ID MAG o path MAG
	 * @return Documento documento XML
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("magdata")
	public Response getMag(@QueryParam("id") String idMag) {
		// parameto non impostato
		if(idMag == null || idMag.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("ID non specificato").build();
		
		
		try {
			return Response.ok(magDetailService.getMagDetail(idMag)).build();
			
		} catch (FileNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}


	/**
	 * Restituisce il file della risorsa digitale
	 * 
	 * VISUALIZZAZIONE OGGETTO DIGITALE DA SCHEDA DI DETTAGLIO
	 * 
	 * @param idMag id del MAG in Solr
	 * @param md5 md5 del file
	 * @return Response l'oggetto digitale
	 */
	@GET
	@Path("digitalResource/resource/{type}/{sequence}/{usage}")
	public Response getDigitalResource(@QueryParam("id") String idMag, @PathParam("type") String type,
			@PathParam("sequence") String sequenceNumber, @PathParam("usage") String usage, @Context HttpServletRequest request) {
		
		// controllo parametri richiesta
		if(idMag == null || idMag.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("ID del MAG non specificato").build();
		
		if(type == null || type.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Tipo di risorsa non specificato").build();
		
		if(sequenceNumber == null || sequenceNumber.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Numero di sequenza non specificato").build();
		
		if(usage == null || usage.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Usage non specificato").build();
		
		
		// servizio restituzione file
		try {
			DataResourceDelivery resource = magDetailService.getDigitalResource(idMag, type, sequenceNumber, usage);
			String mimeType = resource.getContentType();
			String disposition = "audio".endsWith(type) || "video".equals(type) ? "attachment" : "inline";
			
			long start = 0;
			long size = resource.getLength();
			long end = size - 1;
			String range = request.getHeader("Range");
			
			if(range != null) {
				range = range.replaceAll("bytes=", "");
				String[] parts = range.split("-", 2);
				
				if(parts.length > 0 && !parts[0].isEmpty())
					start = Long.parseLong(parts[0]);
				
				if(parts.length > 1 && !parts[1].isEmpty())
					end = Long.parseLong(parts[1]);
			}
			
			ResponseBuilder responseBuilder = Response.ok(resource.getStream()).
					header("Content-Transfer-Encoding", "binary").
					header("video".equals(type) ? "Content-Encoding" : "Content-Length", "video".equals(type) ? "identity" : end - start + 1).
					header("Content-Type", mimeType).
					header("Content-Disposition", disposition + "; filename=\"" + AudioCutterComponent.makeResponseExtension(resource.getResourceName()) + "\"").
					header("ETag", type + "/" + sequenceNumber + "/" + usage + "?id=" + idMag).
					header("Last-Modified", resource.getLastModified());
			
			if("audio".equals(type) || "video".equals(type)) {
				responseBuilder = responseBuilder.
						header("Accept-Ranges", "bytes").
						header("Content-Range", "bytes " + start + "-" + end + "/" + size);
			}
			
			return responseBuilder.build();
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Restituisce l'immagine, con resize
	 * 
	 * VISUALIZZAZIONE PREVIEW OGGETTO DIGITALE DA SCHEDA DI DETTAGLIO
	 * 
	 * @param idMag id del MAG in Solr
	 * @param md5 md5 del file
	 * @return Response l'oggetto digitale
	 */
	@GET
	@Produces("image/jpeg")
	@Path("digitalResource/preview/{resourceType}/{sequence}/{usage}")
	public Response getImagePreview(@QueryParam("id") String idMag, 
			@PathParam("resourceType") String type, @PathParam("sequence") String sequenceNumber, 
			@PathParam("usage") String usage, @QueryParam("width") int width, 
			@QueryParam("height") int height) {

		// controllo parametri richiesta
		if(idMag == null || idMag.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("ID del MAG non specificato").build();
		
		if(type == null || type.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Tipo di risorsa non specificato").build();
		
		if(sequenceNumber == null || sequenceNumber.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Numero di sequenza non specificato").build();
		
		if(usage == null || usage.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Usage non specificato").build();
		
		if(width < 0 || height < 0) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Dimensioni immagine "
					+ "di preview non specificate correttamente").build();
		}
		
		// stream preview
		try {
			return Response.ok(magDetailService.getImagePreviewDigitalResource(idMag, type, 
					sequenceNumber, usage, width, height)).build();
			
		} catch (IOException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	/**
	 * Restituisce i metadati della risorsa digitale
	 * 
	 * VISUALIZZAZIONE METADATI OGGETTO DIGITALE DA SCHEDA DI DETTAGLIO (POPUP)
	 * 
	 * @param idMag id del MAG in Solr
	 * @param md5 md5 del file
	 * @return i metadati della risorsa digitale
	 */
	@GET
	@Path("digitalResource/data/{type}/{sequence}/{usage}")
	public Response getDigitalResourceMetadata(@QueryParam("id") String idMag, 
			@PathParam("type") String type, @PathParam("sequence") String sequenceNumber, @PathParam("usage") String usage) {
		
		// controllo parametri richiesta
		if(idMag == null || idMag.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("ID del MAG non specificato").build();
		
		if(type == null || type.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Tipo di risorsa non specificato").build();
		
		if(sequenceNumber == null || sequenceNumber.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Numero di sequenza non specificato").build();
		
		if(usage == null || usage.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Usage non specificato").build();
		
		
		// restituzione metadati
		try {
			return Response.ok(magDetailService.
					getResourceDetail(idMag, type, sequenceNumber, usage)).build();
			
		} catch (FileNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	/**
	 * Servizio di pubblicazione delle risorse
	 * 
	 * SERVIZIO DI PUBBLICAZIONE DEI MAG SELEZIONATI DA RICERCA AVANZATA
	 * 
	 * @param request JSON di richiesta per la pubblicazione
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")	
	@Path("/publicMag")
	public JsonPublicationResult executePublic(JsonPublicationRequest request) {
		JsonPublicationResult result = new JsonPublicationResult();
		
		try {
			logger.info("Servizio di pubblicazione MAG in corso");
			result.setStatus(1);
			result.setMessage("Pubblicazione MAG in corso");
			result.setMags(magSearchService.publicate(request));
			logger.info("Processo di pubblicazione terminato");
			result.setStatus(0);
			result.setMessage("Processo di pubblicazione terminato");
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			result.setStatus(-1);
			result.setMessage("Problema di accesso database - " + e.getMessage());
			result.getMags().clear();
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
			result.setStatus(-2);
			result.setMessage("Problema di scrittura - " + e.getMessage());
			result.getMags().clear();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			result.setStatus(-2);
			result.setMessage("Problema di scrittura - " + e.getMessage());
			result.getMags().clear();
		}
		
		return result;
	}  
	
	/**
	 * Servizio di esportazione MAG
	 * 
	 * SERVIZIO DI EXPORT DEI MAG SELEZIONATI DA RICERCA AVANZATA
	 * 
	 * @param request JSON di richiesta esportazione
	 * @return Response risposta finale
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/exportMag")
	public Response exportMag(JsonExportRequest request) {
		logger.info("Servizio di esportazione MAG in corso");
		
		if(!Arrays.asList("zip", "tar", "tar.gz", "tar.bz2", "tar.Z").contains(request.getExtension())) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Estensione del file non riconosciuta").build();
		}
		
		JsonExportResponse response = magSearchService.export(request);
		
		if(response.getStatus() < 0)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
		
		return Response.ok(response).build();
	}

	/**
	 * Servizio di download export compresso
	 * 
	 * SERVIZIO DI DOWNLOAD DEL PACCHETTO CREATO DA SERVIZIO DI EXPORT
	 * 
	 * @param filename nome del file compresso
	 * @return Response risposta finale (download file)
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/export/download/{filename}")
	public Response downloadExport(@PathParam("filename") String filename) {
		if(filename == null || filename.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Nome del file "
					+ "obbligatorio non correttamente specificato").build();
		}
		
		InputStream is = magSearchService.downloadExportZip(filename);
		
		if(is == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		return Response.ok(is).build();
	}

	/**
	 * Servizio di restituzione di tutti gli export completati
	 * 
	 * LETTURA DEI PACCHETTI DI EXPORT DISPONIBILI PER IL DOWNLOAD
	 * 
	 * @return List<DBExport> lista degli export completati
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/export/completed")
	public List<JsonExport> allExports() {
		return magSearchService.getAllCompleteExports();
	}

	/**
	 * Servizio di cancellazione export compresso
	 * 
	 * CANCELLAZIONE PACCHETTO DI EXPORT
	 * 
	 * @param export export da cancellare
	 * @return Response risposta finale (true/false)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/export/delete/{file}")
	public Response deleteExport(@PathParam("file") String filename) {
		if(filename == null || filename.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Nome del file "
					+ "obbligatorio non correttamente specificato").build();
		}
		
		boolean deleted = magSearchService.deleteExportZip(filename);
		return Response.ok(deleted).build();
	}

	/**
	 * Il metodo cerca la cronologia di operazioni eseguite su un progetto
	 * 
	 * CRONOLOGIA PROGETTO
	 * 
	 * @param userID ID utente
	 * @param update processo di aggiornamento
	 * @return
	 */
	@GET
	@Path("/history/{project}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response projectHistory(@PathParam("project") String project) {	
		// parametro progetto
		if(project == null || project.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Progetto non specificato").build();
		}
		
		return Response.ok(magProjectService.getProjectHistory(project)).build();
	}

	/**
	 * Il metodo preso in input il nome della cartella da creare ed il 
	 * file da uplodare sul servere, crea una cartella sul server con il nome indicato
	 * dall'utente, copia il file e lo scompatta
	 * 
	 * CREAZIONE PROCESSO DI UPLOAD
	 * 
	 * @param userID ID utente
	 * @param update processo di aggiornamento
	 * @return
	 */
	@GET
	@Path("/runUpload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response createUploadProcess(@QueryParam("user") Integer userID, 
			@QueryParam("update") Boolean update, @QueryParam("createContainer") @DefaultValue("false") String createContainer) {
		
		if(userID == null)
			return this.uploadHttp400Error("ID utente non specificato", null);
		
		if(update == null)
			update = false;
		
		try {
			return Response.ok(magProjectService.createUploadProcess(userID, update,createContainer)).build();
			
		} catch (DaoException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Il metodo esegue l'upload di un file, in modalità nuovo progetto
	 * 
	 * ESECUZIONE PROCESSO DI UPLOAD
	 * 
	 * @param project nome del progetto
	 * @param processID ID del processo
	 * @param bytes bytes del file di upload
	 * @param formData dati upload
	 * @return
	 * @throws IOException 
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadFile(@FormDataParam("project") String project,
							   @FormDataParam("process") String processID, @FormDataParam("file") InputStream uploadStream,
							   @FormDataParam("file") FormDataContentDisposition formData) throws IOException {
		
		
		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);
		
		if(project == null || project.isEmpty())
			project = Utility.getFileNameWithoutZipExtension(formData.getFileName());
		
		if(uploadStream == null)
			return this.uploadHttp400Error("File non specificato", null);
		
		
		return Response.ok(magProjectService.upload(processID, project, 
				formData.getFileName(), uploadStream)).build();
	}	

	/**
	 * Il metodo prende in input l'ID del processo di upload per verificarne lo status
	 * 
	 * STATO PROCESSO DI UPLOAD
	 * 
	 * @param String processID ID del processo
	 * @return UploadReport
	 * */
	@GET
	@Path("/upload/status")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadStatus(@QueryParam("id") String processID) {	
		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);
		
		return Response.ok(magProjectService.getUploadStatus(processID)).build();
	}	
	
	/**
	 * Il metodo ricerca gli archivi all'interno della directory specificata
	 * 
	 * RICERCA PACCHETTI/LISTA MAG INTERNA A DIRECTORY PER UPLOAD LATO SERVER
	 * 
	 * @param baseDirectory directory di ricerca
	 * @param update true se in modalità aggiornamento, false se in modalità nuovo progetto
	 * @return Response risposta HTTP
	 * */
	@GET
	@Path("/upload/search")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response searchUploadFiles(@QueryParam("dir") String baseDirectory, @QueryParam("type") String type) {	
		if(baseDirectory == null || baseDirectory.isEmpty())
			return this.uploadHttp400Error("Directory di ricerca non specificata", null);
		
		if(type == null || type.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Tipo di ricerca non specificata").build();
		}
		
		if(!Arrays.asList("compress", "folder").contains(type)) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Tipo di ricerca sconosciuta").build();
		}
		
		try {
			List<String> results = "compress".equals(type) ? 
					magProjectService.searchFilesForUpload(baseDirectory) : 
						magProjectService.showFilesForUpload(baseDirectory, false);

			return Response.ok(results).build();
			
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		
	}

	@GET
	@Path("/uploadResources/search")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response searchUploadResourceFiles(@QueryParam("dir") String baseDirectory, @QueryParam("type") String type) {
		if(baseDirectory == null || baseDirectory.isEmpty())
			return this.uploadHttp400Error("Directory di ricerca non specificata", null);

		if(type == null || type.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Tipo di ricerca non specificata").build();
		}

		if(!Arrays.asList("compress", "folder").contains(type)) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Tipo di ricerca sconosciuta").build();
		}

		try {
			List<String> results = "compress".equals(type) ?
					magProjectService.searchFilesForUpload(baseDirectory) :
					magProjectService.showFilesForUpload(baseDirectory, true);

			return Response.ok(results).build();

		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

	/**
	 * Il metodo esegue l'upload di un file già caricato sul server, in modalità nuovo progetto
	 * 
	 * ESECUZIONE UPLOAD MODALITÀ SERVER
	 * 
	 * @param project nome progetto
	 * @param processID ID processo
	 * @param baseDirectory directory contenente il file da caricare
	 * @param file nome del file da caricare
	 * @return Response risposta HTTP
	 * @throws IOException
	 */
	@GET
	@Path("/uploadServer")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadFileServer(@QueryParam("project") String project, 
			@QueryParam("process") String processID, 
			@QueryParam("dir") String baseDirectory, 
			@QueryParam("file") String file) throws IOException {	
		
		
		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);

		if(file == null || file.isEmpty())
			return this.uploadHttp400Error("File non specificato", null);

		if(baseDirectory == null || baseDirectory.isEmpty())
			return this.uploadHttp400Error("File non specificato", null);
		
		if(project == null || project.isEmpty())
			project = file;
		
		return Response.ok(magProjectService.uploadFromServer(processID, project, 
				baseDirectory, file, false)).build();
	}	

	/**
	 * Il metodo esegue l'upload di un file già caricato sul server, in modalità aggiornamento progetto
	 * 
	 * ESECUZIONE UPLOAD AGGIORNAMENTO MODALITÀ SERVER
	 * 
	 * @param project nome progetto
	 * @param processID ID processo
	 * @param baseDirectory directory contenente il file da caricare
	 * @param file nome del file da caricare
	 * @return Response risposta HTTP
	 * @throws IOException
	 */
	@GET
	@Path("/uploadServer/update")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadUpdateFileServer(@QueryParam("project") String project, 
			@QueryParam("process") String processID, 
			@QueryParam("dir") String baseDirectory, 
			@QueryParam("file") String file) throws IOException {	

		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);

		if(file == null || file.isEmpty())
			return this.uploadHttp400Error("File non specificato", null);

		if(baseDirectory == null || baseDirectory.isEmpty())
			return this.uploadHttp400Error("File non specificato", null);
		
		if(project == null || project.isEmpty())
			return this.uploadHttp400Error("Nome del progetto non specificato", null);

		return Response.ok(magProjectService.uploadFromServer(processID, project, 
				baseDirectory, file, true)).build();
	}

	@GET
	@Path("/uploadServer/resources")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadResourcesFileServer(@QueryParam("project") String project,
										   @QueryParam("process") String processID,
										   @QueryParam("dir") String baseDirectory,
										   @QueryParam("file") String file) throws IOException {

		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);

		if(file == null || file.isEmpty())
			return this.uploadHttp400Error("File non specificato", null);

		if(baseDirectory == null || baseDirectory.isEmpty())
			return this.uploadHttp400Error("File non specificato", null);

		if(project == null || project.isEmpty())
			return this.uploadHttp400Error("Nome del progetto non specificato", null);

		return Response.ok(magProjectService.uploadFromServerResources(processID, project,
				baseDirectory, file, false)).build();
	}

	@GET
	@Path("/uploadServer/unimarc")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadUnimarcFileServer(@QueryParam("project") String project,
											  @QueryParam("process") String processID,
											  @QueryParam("dir") String baseDirectory,
											  @QueryParam("file") String file) throws IOException {

		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);

		if(file == null || file.isEmpty())
			return this.uploadHttp400Error("File non specificato", null);

		if(baseDirectory == null || baseDirectory.isEmpty())
			return this.uploadHttp400Error("File non specificato", null);

		if(project == null || project.isEmpty())
			return this.uploadHttp400Error("Nome del progetto non specificato", null);

		return Response.ok(magProjectService.uploadFromServerUnimarc(processID, project,
				baseDirectory, file)).build();
	}

	/**
	 * Il metodo esegue l'upload di un file, in modalità aggiornamento progetto
	 * 
	 * ESECUZIONE UPLOAD AGGIORNAMENTO
	 * 
	 * @param byte[] bytes i byte del file di upload
	 * @param FormDataContentDisposition formData campo file della form per l'upload
	 * @param String project nome del progetto
	 * @param String processID ID del processo
	 * @return UploadReport
	 * */
	@POST
	@Path("/upload/update")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadUpdateFile(@FormDataParam("file") InputStream uploadStream,
            @FormDataParam("file") FormDataContentDisposition formData,
            @FormDataParam("project") String project, @FormDataParam("process") String processID) {	
				
		
		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);
		
		if(project == null || project.isEmpty())
			return this.uploadHttp400Error("Nome del progetto non specificato", null);
		
		if(uploadStream == null)
			return this.uploadHttp400Error("File non specificato", null);
		
		
		return Response.ok(magProjectService.uploadUpdate(processID, project, 
				formData.getFileName(), uploadStream)).build();
	}

	@POST
	@Path("/upload/resources")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadUpdateResources(@FormDataParam("file") InputStream uploadStream,
									 @FormDataParam("file") FormDataContentDisposition formData,
									 @FormDataParam("project") String project, @FormDataParam("process") String processID) {


		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);

		if(project == null || project.isEmpty())
			return this.uploadHttp400Error("Nome del progetto non specificato", null);

		if(uploadStream == null)
			return this.uploadHttp400Error("File non specificato", null);


		return Response.ok(magProjectService.uploadResources(processID, project,
				formData.getFileName(), uploadStream, false)).build();
	}

	@POST
	@Path("/upload/unimarc")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response uploadUpdateUnimarc(@FormDataParam("file") InputStream uploadStream,
										  @FormDataParam("file") FormDataContentDisposition formData,
										  @FormDataParam("project") String project, @FormDataParam("process") String processID) {


		if(processID == null || processID.isEmpty())
			return this.uploadHttp400Error("ID del processo non specificato", null);

		if(project == null || project.isEmpty())
			return this.uploadHttp400Error("Nome del progetto non specificato", null);

		if(uploadStream == null)
			return this.uploadHttp400Error("File non specificato", null);


		return Response.ok(magProjectService.uploadUnimarc(processID, project,
				formData.getFileName(), uploadStream)).build();
	}

	/**
	 * Risposta 400
	 * 
	 * @param message messaggio
	 * @param userID ID utente
	 * @return risposta 400
	 */
	public static Response uploadHttp400Error(String message, Integer userID) {
		JsonUploadReport report = new JsonUploadReport();
		report.setProcessID("");
        report.setStatus(-4);
        report.setMessage(message);
        
        if(userID != null)
        	report.setUserID(userID);
        
        return Response.status(Status.BAD_REQUEST).entity(report).build();
	}

	/**
	 * Servizio di cancellazione utente
	 * 
	 * CANCELLAZIONE UTENTE
	 * 
	 * @param userID ID utente da cancellare
	 * @return JsonUserOpMessage esito dell'operazione di cancellazione
	 */
	@GET
	@Path("/user/delete/{id}")
	@Tag(name = "Gestione utenti")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public JsonUserOpMessage deleteUser(@PathParam("id") int userID) {
		JsonUserOpMessage message = new JsonUserOpMessage();
		message.setResult(JsonUserOpMessage.OK);
		message.setMessage("Operazione correttamente eseguita, utente ID " + userID + " cancellato");
		
		try {
			magUserService.deleteUser(userID);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			message.setResult(JsonUserOpMessage.KO);
			message.setMessage(e.getMessage());
		}
		
		return message;
	}
	
	/**
	 * Servizio di inserimento utente
	 * 
	 * INSERIMENTO UTENTE
	 * 
	 * @param jsonUser
	 * @return JsonUserOpMessage esito operazione di inserimento
	 */
	@POST
	@Path("/user/insert")
	@Tag(name = "Gestione utenti")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public JsonUserOpMessage insertUser(JsonPostUser jsonUser) {
		JsonUserOpMessage message = new JsonUserOpMessage();
		message.setResult(JsonUserOpMessage.OK);
		message.setMessage("Operazione correttamente eseguita, utente inserito");
		
		try {
			magUserService.insertUser(jsonUser);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			message.setResult(JsonUserOpMessage.KO);
			message.setMessage(e.getMessage());
		}
		
		return message;
	}

	/**
	 * Serivizio di aggiornamento utente
	 * 
	 * AGGIORNAMENTO UTENTE
	 * 
	 * @param jsonUser
	 * @return JsonUserOpMessage esito operazione di aggiornamento
	 */
	@POST
	@Path("/user/update")
	@Tag(name = "Gestione utenti")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public JsonUserOpMessage updateUser(JsonPostUser jsonUser) {
		JsonUserOpMessage message = new JsonUserOpMessage();
		message.setResult(JsonUserOpMessage.OK);
		message.setMessage("Operazione correttamente eseguita, utente aggiornato");
		
		try {
			magUserService.updateUser(jsonUser);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			message.setResult(JsonUserOpMessage.KO);
			message.setMessage(e.getMessage());
		}
		
		return message;
	}

	/**
	 * Servizio di restituzione utente, dato il suo username (usao per il login)
	 * 
	 * LOGIN UTENTE
	 * 
	 * @param username username
	 * @return Response risposta servizio
	 */
	@GET
	@Path("/user/login")
	@Tag(name = "Gestione utenti")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response login(@QueryParam("username") String username) {
		try {
			DBTecaUser user = magUserService.searchUserByUsername(username);
			
			if(user == null)
				return Response.ok("{}", MediaType.APPLICATION_JSON).build();
			
			return Response.ok(user).build();
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(e.getMessage()).build();
		}
	}

	/**
	 * Servizio di ricerca utente per email
	 * 
	 * NON USATO!!!
	 * 
	 * @param email email
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/user/searchByEmail")
	@Tag(name = "Gestione utenti")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response searchByEmail(@QueryParam("email") String email) {
		try {
			DBTecaUser user = magUserService.searchUserByEmail(email);
			
			if(user == null)
				return Response.status(Status.NOT_FOUND).build();
			
			return Response.ok(user).build();
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(e.getMessage()).build();
		}
	}

	/**
	 * Servizio di restituzione di tutti gli utenti
	 * 
	 * LISTA UTENTI
	 * 
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/user/all")
	@Tag(name = "Gestione utenti")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getAllUsers() {
		try {
			return Response.ok(magUserService.getAllUsers()).build();
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(e.getMessage()).build();
		}
	}

	/**
	 * Servizio di ricerca utente per ID
	 * 
	 * RICERCA UTENTI PER ID
	 * 
	 * @param id ID utente
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/user/search/{id}")
	@Tag(name = "Gestione utenti")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response searchByID(@PathParam("id") int id) {
		try {
			DBTecaUser user = magUserService.searchUserByID(id);
			
			if(user == null)
				return Response.status(Status.NOT_FOUND).build();
			
			return Response.ok(user).build();
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Servizio di inserimento nuovo ruolo
	 * 
	 * INSERIMENTO RUOLO
	 * 
	 * @param role ruolo utente
	 * @return JsonUserOpMessage esito dell'operazione di inserimento
	 */
	@POST
	@Path("/user/role/insert")
	@Tag(name = "Gestione utenti")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public JsonUserOpMessage insertRole(DBUserRole role) {
		JsonUserOpMessage message = new JsonUserOpMessage();
		
		try {
			if(role.getName() == null || role.getName().isEmpty()) {
				message.setResult(JsonUserOpMessage.KO);
				message.setMessage("Nome ruolo non specificato");
				return message;
			}
			
			magUserService.insertRole(role);
			message.setResult(JsonUserOpMessage.OK);
			message.setMessage("Operazione correttamente eseguita, ruolo utente inserito");
			return message;
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			message.setResult(JsonUserOpMessage.KO);
			message.setMessage(e.getMessage());
			return message;
		}
	}

	/**
	 * Servizio di inserimento nuovo ruolo
	 * 
	 * AGGIORNAMENTO RUOLO
	 * 
	 * @param role ruolo utente
	 * @return JsonUserOpMessage esito dell'operazione di inserimento
	 */
	@POST
	@Path("/user/role/update")
	@Tag(name = "Gestione utenti")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public JsonUserOpMessage updateRole(DBUserRole role) {
		JsonUserOpMessage message = new JsonUserOpMessage();
		
		try {
			magUserService.updateRole(role);
			message.setResult(JsonUserOpMessage.OK);
			message.setMessage("Operazione correttamente eseguita, ruolo utente aggiornato");
			return message;
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			message.setResult(JsonUserOpMessage.KO);
			message.setMessage(e.getMessage());
			return message;
		}
	}

	/**
	 * Servizio di inserimento nuovo ruolo
	 * 
	 * CANCELLAZIONE RUOLO
	 * 
	 * @param role ruolo utente
	 * @return JsonUserOpMessage esito dell'operazione di inserimento
	 */
	@GET	
	@Path("/user/role/delete/{id}")
	@Tag(name = "Gestione utenti")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public JsonUserOpMessage deleteRole(@PathParam("id") int roleID) {
		JsonUserOpMessage message = new JsonUserOpMessage();
		
		try {
			magUserService.deleteRole(roleID);
			message.setResult(JsonUserOpMessage.OK);
			message.setMessage("Operazione correttamente eseguita, ruolo utente eliminato");
			return message;
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			message.setResult(JsonUserOpMessage.KO);
			message.setMessage(e.getMessage());
			return message;
		}
	}

	/**
	 * Servizio di restituzione tutti i ruoli
	 * 
	 * LETTURA RUOLI
	 * 
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/user/role/all")
	@Tag(name = "Gestione utenti")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response allRoles() {
		try {
			return Response.ok(magUserService.getAllRoles()).build();
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Servizio di restituzione delle sezioni attive e degli eventuali contenuti
	 * 
	 * SERVIZIO LETTURA DATI PER EDITOR
	 * 
	 * @param magID ID MAG
	 * @param sectionList sezioni da filtrare (per estrazione contenuto)
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/editor/content")
	@Tag(name = "Editore MAG")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getEditorSectionData(@QueryParam("id") String magID, @QueryParam("sections") String sectionList) {
		if(magID == null || magID.isEmpty())
	        return Response.status(Status.BAD_REQUEST).entity("ID del MAG non specificato").build();
		
		if(sectionList == null || sectionList.isEmpty()) {
			try {
				return Response.ok(magEditorService.getActiveSections(magID)).build();
				
			} catch (FileNotFoundException e) {
				return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
			}
		}
		
		else {
			try {
				return Response.ok(magEditorService.getActiveSections(magID, Arrays.asList(sectionList.split(",")))).build();
				
			} catch (FileNotFoundException e) {
				return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
			}
		}
	}	

	/**
	 * Servizio di restituzione dei metadati delle risorse digitali
	 * 
	 * LETTURA DATI OGGETTO DIGITALE AMBITI D'USO (EDITOR)
	 * 
	 * @param magID ID MAG
	 * @param sectionList sezioni da filtrare (per estrazione contenuto)
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/editor/content/resource")
	@Tag(name = "Editore MAG")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getEditorSectionData(@QueryParam("id") String magID, @QueryParam("type") String type, 
			@QueryParam("sequence_number") String seqNumber, @QueryParam("usage") Integer usage) {
		
		if(magID == null || magID.isEmpty())
	        return Response.status(Status.BAD_REQUEST).entity("ID del MAG non specificato").build();
		
		if(type == null || type.isEmpty()) {
	        return Response.status(Status.BAD_REQUEST).entity("Tipo di risorsa digitale (img, audio, video, "
	        		+ "ocr, doc) non specificato").build();
		}
		
		else if(!Arrays.asList("img", "audio", "video", "ocr", "doc").contains(type))
			return Response.status(Status.BAD_REQUEST).entity("Tipo di risorsa digitale non riconosciuto").build();
		
		if(seqNumber == null)
	        return Response.status(Status.BAD_REQUEST).entity("Numero di sequenza non specificato").build();
		
		return Response.ok(magEditorService.getResourceVersions(magID, type, seqNumber, usage)).build();
	}	
	
	/**
	 * Servizio di creazione bozza MAG
	 * 
	 * CREAZIONE BOZZA EDITOR
	 * 
	 * @param userID ID utente
	 * @param project progetto selezionato
	 * @return Response risposta del servizio
	 */
	@GET
	@Path("/editor/create")
	@Tag(name = "Editore MAG")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response saveSectionMag(@QueryParam("user") Integer userID, @QueryParam("project") String project,
								   @QueryParam("label") String label) {
		if(userID == null)
			return Response.status(Status.BAD_REQUEST).entity("ID utente non specificato").build();
		
		if(project == null || project.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("Progetto non specificato").build();
		
		return Response.ok(magEditorService.saveGen(new JsonMagEditorGen(), null, project, userID, label)).build();
	}

	/**
	 * Servizio di restituzione delle sezioni attive e degli eventuali contenuti 
	 * 
	 * SALVATAGGIO SEZIONE BOZZA EDITOR
	 * 
	 * @param magID ID MAG
	 * @param section sezione da salvare
	 * @param json json contenente i metadati da salvare
	 * @return Response risposta del servizio
	 */
	@POST
	@Path("/editor/save")
	@Tag(name = "Editore MAG")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response saveSectionMag(@QueryParam("id") String magID, @QueryParam("user") Integer userID,
								   @QueryParam("section") String section, String json) {
		if((magID == null || magID.isEmpty()))
			return Response.status(Status.BAD_REQUEST).entity("L'identificativo del MAG non specificato").build();
		
		if(userID == null)
			return Response.status(Status.BAD_REQUEST).entity("ID utente non specificato").build();
		
		if(section == null || section.isEmpty())
	        return Response.status(Status.BAD_REQUEST).entity("Sezione da salvare non specificata").build();
		
		if(!Arrays.asList("gen", "bib", "stru", "img", "audio", "video", "ocr", "doc", "dis").contains(section))
			return Response.status(Status.BAD_REQUEST).entity("Sezione non riconosciuta").build();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			//mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			if("gen".equals(section)) {
				JsonMagEditorGen genObject = mapper.readValue(json, JsonMagEditorGen.class);
				return Response.ok(magEditorService.saveGen(genObject, magID, null, userID)).build();
			}
			
			else if("bib".equals(section)) {
				JsonMagEditorBib bibObject = mapper.readValue(json, JsonMagEditorBib.class);
				return Response.ok(magEditorService.saveBib(bibObject, magID, null, userID)).build();
			}
			
			else if("stru".equals(section)) {
				List<JsonMagEditorStru> struObjects = mapper.readValue(json, mapper.getTypeFactory().
						constructCollectionType(List.class, JsonMagEditorStru.class));
				
				return Response.ok(magEditorService.saveStru(struObjects, magID, null, userID)).build();
			}
			
			else if("img".equals(section)) {
				List<JsonMagEditorResource> imgObjects = mapper.readValue(json, mapper.getTypeFactory().
						constructCollectionType(List.class, JsonMagEditorResource.class));
				
				return Response.ok(magEditorService.saveImg(imgObjects, magID, null, userID)).build();
			}
			
			else if("audio".equals(section)) {
				List<JsonMagEditorResource> audioObjects = mapper.readValue(json, mapper.getTypeFactory().
						constructCollectionType(List.class, JsonMagEditorResource.class));
				
				return Response.ok(magEditorService.saveAudio(audioObjects, magID, null, userID)).build();
			}
			
			else if("video".equals(section)) {
				List<JsonMagEditorResource> videoObjects = mapper.readValue(json, mapper.getTypeFactory().
						constructCollectionType(List.class, JsonMagEditorResource.class));
				
				return Response.ok(magEditorService.saveVideo(videoObjects, magID, null, userID)).build();
			}

			else if("ocr".equals(section)) {
				List<JsonMagEditorResource> ocrObjects = mapper.readValue(json, mapper.getTypeFactory().
						constructCollectionType(List.class, JsonMagEditorResource.class));
				
				return Response.ok(magEditorService.saveOcr(ocrObjects, magID, null, userID)).build();
			}
			
			else if("doc".equals(section)) {
				List<JsonMagEditorResource> docObjects = mapper.readValue(json, mapper.getTypeFactory().
						constructCollectionType(List.class, JsonMagEditorResource.class));
				
				return Response.ok(magEditorService.saveDoc(docObjects, magID, null, userID)).build();
			}
			
			else if("dis".equals(section)) {
				JsonMagEditorDis disObject = mapper.readValue(json, JsonMagEditorDis.class);
				return Response.ok(magEditorService.saveDis(disObject, magID, null, userID)).build();
			}
			
			else
				return Response.status(Response.Status.NOT_FOUND).build();
			
		} catch (IOException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}	
	
	/**
	 * Servizio di validazione della bozza
	 * 
	 * SERVIZIO PRELIMINARE ALL'IMPORT DELLA BOZZA (PUBBLICA IN TECA EDITOR)
	 * 
	 * @param magID ID del MAG bozza
	 * @return Response risposta JSON del servizio
	 */
	@GET
	@Path("/editor/validate")
	@Tag(name = "Editore MAG")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getEditorSectionData(@QueryParam("id") String magID) {
		 return Response.ok(magEditorService.createEditorMagFS(magID)).build();
	}

	/**
	 * Servizio di validazione della bozza
	 * 
	 * SERVIZIO RICERCA BIB DA SBN
	 * 
	 * @param magID ID del MAG bozza
	 * @return Response risposta JSON del servizio
	 */
	@GET
	@Path("/editor/sbnRequest")
	@Tag(name = "Editore MAG")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getBibBySbnRequest(@QueryParam("identifier") String identifier) {
		 return Response.ok(magEditorService.getBibBySbnRequest(identifier)).build();
	}

	/**
	 * Servizio di verifica presenza del progetto per il passaggio in modalità editor
	 * 
	 * VERIFICA ESISTENZA DEL PROGETTO SU FILE SYSTEM PER MODIFICARE MAG IMPORTATO CON EDITOR
	 * 
	 * @param magID ID del MAG
	 * @return Response risposta del servizio (true/false)
	 */
	@GET
	@Path("/editor/checkProject")
	@Tag(name = "Editore MAG")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response checkProjectForEditor(@QueryParam("id") String magID) {
		 return Response.ok(magEditorService.checkProjectForEditor(magID)).build();
	}
	

	/**
	 * Il servizio si occupa di eseguire una ricerca su Solr, restituendo una lista di risultati
	 * 
	 * SERVIZIO DI RICERCA VIEWER CHERUBINI
	 * 
	 * @param request JSON di richiesta ricerca MAG
	 * @return JsonSearchResultList oggetto JSON di risposta
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/searchFE")
	public JsonSearchViewerResultList customSearchMag(JsonSearchRequest request, 
			@QueryParam("facetOnly") Boolean facetOnly) {
		
		return magSearchService.viewerSearch(request, facetOnly != null ? 0 : request.getLength(), true);
	}

	/**
	 * Servizio di restituzione riepilogo dettaglio MAG
	 * 
	 * SERVIZIO SCHEDA DETTAGLIO VIEWER CHERUBINI
	 * 
	 * @param idMag ID MAG o path MAG
	 * @return Documento documento XML
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("detailFE")
	public Response getDetailMag(@QueryParam("id") String idMag) {
		// parameto non impostato
		if(idMag == null || idMag.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("ID non specificato").build();
		
		
		try {
			return Response.ok(magDetailService.getServiceMagDetail(idMag)).build();
			
		} catch (FileNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	/**
	 * Restituisce i metadati della risorsa digitale
	 * 
	 * SERVIZIO METADATI OGGETTO DIGITALE VIEWER CHERUBINI
	 * 
	 * @param idMag id del MAG in Solr
	 * @param md5 md5 del file
	 * @return i metadati della risorsa digitale
	 */
	@GET
	@Path("detailResourceFE/{type}/{sequence}/{usage}")
	public Response getDetailResourceMetadata(@QueryParam("id") String idMag, 
			@PathParam("type") String type, @PathParam("sequence") String sequenceNumber, @PathParam("usage") String usage) {
		
		// controllo parametri richiesta
		if(idMag == null || idMag.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("ID del MAG non specificato").build();
		
		if(type == null || type.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Tipo di risorsa non specificato").build();
		
		if(sequenceNumber == null || sequenceNumber.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Numero di sequenza non specificato").build();
		
		if(usage == null || usage.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).entity("Usage non specificato").build();
		
		
		// restituzione metadati
		try {
			return Response.ok(magDetailService.getServiceResourceDetail(idMag, type, sequenceNumber, usage)).build();
			
		} catch (FileNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Il servizio si occupa di esportare i risulati di una ricerca, nel formato CSV
	 * 
	 * PULSANTE EXPORT CSV RISULTATI RICERCA AVANZATA
	 * 
	 * @param request JSON di richiesta ricerca MAG
	 * @return Response stream di risposta
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Produces(MediaType.APPLICATION_OCTET_STREAM + "; charset=UTF-8")
	@Path("/searchExportCsv")
	public Response exportCsv(JsonSearchRequest request) {
		ResponseBuilder responseBuilder = Response.ok(magSearchService.exportCsv(request)).
				header("Content-Disposition", "attachment; filename=\"MAG_Ricerca.csv\"");
		
		return responseBuilder.build();
	}
	
}
