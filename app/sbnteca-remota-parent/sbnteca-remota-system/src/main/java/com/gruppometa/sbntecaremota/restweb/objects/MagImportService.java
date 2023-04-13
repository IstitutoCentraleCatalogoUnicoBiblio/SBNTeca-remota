package com.gruppometa.sbntecaremota.restweb.objects;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppometa.sbntecaremota.objects.*;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.gruppometa.sbntecaremota.objects.db.DBImportDetail;
import com.gruppometa.sbntecaremota.objects.db.DBImportDetailDao;
import com.gruppometa.sbntecaremota.objects.db.DBImportMagDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.objects.json.JsonImportConfiguration;
import com.gruppometa.sbntecaremota.objects.json.JsonImportProject;
import com.gruppometa.sbntecaremota.objects.json.JsonImportReport;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceTypes;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;

public class MagImportService {

	// dao dettagli job
	@Autowired
	private DBImportDetailDao importDetailDao;

	// import mag dao
	@Autowired
	private DBImportMagDao importMagDao;

	// home manager
	@Autowired
	private HomeManager homeManager;

	@Autowired
	private VfsFileSystem vfsFileSystem;

	// logger
	private static Logger logger = Logger.getLogger(MagImportService.class);
	
	
	/**
	 * Creazione job di importazione/validazione
	 * 
	 * CREAZIONE PROCESSO DI IMPORT E CREAZIONE THREAD
	 * 
	 * @param importMagConfig json di ingresso con i parametri di configurazione del job
	 * @param prefixJob prefisso per il job
	 * @param index per indicizzare (true) o soltanto validare (false)
	 * @return oggetto json di uscita contenente lo stato sul processo creato e il suo ID creato
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public JsonImportReport initializeImport(JsonImportConfiguration importMagConfig, String prefixJob, boolean index) {
		String baseFolder = ContentStatic.getProperties().getProperty("resourceDIRO");
		List<ImportMagProject> projects = new ArrayList<ImportMagProject>();
		
		// sorgente di default mag: file system
		if (importMagConfig.getSource() == null || importMagConfig.getSource().equals(""))
			importMagConfig.setSource(MagPersistenceTypes.FILE);
		
		// timestamp per costruire ID job
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String jobID = prefixJob + "_" + formatter.format(new Date(System.currentTimeMillis()));
		
		// impostazioni
		ImportSettings settings = new ImportSettings();
		settings.setJobID(jobID);
		settings.setIndexMags(index);
		settings.setDressMag(importMagConfig.getDressMag() != null ? importMagConfig.getDressMag() : -1);
		settings.setPubblica(importMagConfig.getPubblica() != null ? importMagConfig.getPubblica() : -1);
		settings.setUpdate(importMagConfig.getImportUpdate());
		settings.setTypeMag(importMagConfig.getSource());
		settings.setUserID(importMagConfig.getUserID());
		settings.setUsageA(importMagConfig.getUsageA());
		settings.setUsageE(importMagConfig.getUsageE());
		settings.setReport(new ImportReport());
		settings.setOverwrite(importMagConfig.getOverwrite());
		
		
		
		// configurazione generale (per importazione/validazione)
		Properties props = new Properties();
		
		for(Entry<Object, Object> entry : ContentStatic.getProperties().entrySet())
			props.setProperty((String) entry.getKey(), (String) entry.getValue());
		
		for(Entry<String, String> entry : importMagConfig.getConfiguration().entrySet())
			props.setProperty(entry.getKey(), entry.getValue());
		
		props.setProperty("Validator.UsageAcquisition", StringUtils.join(settings.getUsageA(), " "));
		settings.setConfiguration(props);
		
		Utility.configureValidation(settings);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			// lettura progetti e MAG
			for(JsonImportProject jsonProject : importMagConfig.getProjects()) {
				VfsFile projectFile =  vfsFileSystem.getProjectDirectory(baseFolder, jsonProject.getName());
				
				if(projectFile.exists()) {
					ImportMagProject proj = new ImportMagProject();
					proj.setName(jsonProject.getName());
					proj.setAll(jsonProject.isAll());
					List<String> pathMags = new ArrayList<String>();
					
					if(jsonProject.isAll()) {
						ProjectSummary summary = UtilXML.scanDir(projectFile);
						//pathMags.addAll(summary.getMags());
						for(String path: summary.getMags()){
							VfsFile magFile = vfsFileSystem.getContainerFile(projectFile, path, true);
							if(magFile.exists())
								pathMags.add(magFile.getPath());
						}
						proj.setTotalDigitalObjects(summary.getDigitalObjects().size());
					}
					
					else {
						for(String relativePath : jsonProject.getMags()) {
							VfsFile magFile;
							// creati in automatico....
							if(relativePath.startsWith("mets:")){
								magFile = vfsFileSystem.getVfsService().getVfsFileById(relativePath.substring(5),
										VfsFile.TYPE_CONTAINER);
							}
							else
								magFile = vfsFileSystem.getContainerFile(projectFile, relativePath, true);
							if(magFile.exists())
								pathMags.add(magFile.getPath());
						}
					}
						
					for(String realPath : pathMags) {
						ImportMagConfiguration magInfo = new ImportMagConfiguration();
						magInfo.setPath(realPath);
						
						// cambio usage
						if(importMagConfig.getDressMag() == null && importMagConfig.getPubblica() == null && 
								importMagConfig.getConfiguration().isEmpty()) {
							magInfo.setChangeUsage(true);
							String lastJobID = importMagDao.getLastCompleteImportJobByMag(realPath);
							DBImportDetail detailDB = importDetailDao.getImportDetailByJob(lastJobID);
							magInfo.setDressFlag(detailDB.getDressFlag());
							magInfo.setPublicFlag(detailDB.getPublicFlag());
							Properties conf = mapper.readValue(detailDB.getConfiguration(), Properties.class);
							conf.setProperty("Validator.UsageAcquisition", StringUtils.join(settings.getUsageA(), " "));
							magInfo.setConfiguration(conf);
						}
						
						// importazione/validazione con impostazioni generali
						else {
							magInfo.setDressFlag(importMagConfig.getDressMag());
							magInfo.setPublicFlag(importMagConfig.getPubblica());
							magInfo.setConfiguration(settings.getConfiguration());
						}

						if(!proj.getMags().contains(magInfo))
							proj.getMags().add(magInfo);
					}
					
					projects.add(proj);
				}
				
				else
					logger.warn("Progetto '" + jsonProject.getName() + "' non presente nel file system");
			}
		
			// trasformazione oggetto report in oggetto json
			return Utility.toJsonReportObject(homeManager.prepareImport(projects, settings));
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-1);
			report.setMessage(e.getMessage());
			return report;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-2);
			report.setMessage(e.getMessage());
			return report;
		}
	}

	/**
	 * Permette il lancio effettivo del thread di importazione/validazione
	 * 
	 * ESECUZIONE JOB DI IMPORT/IMPORT UPDATE/CAMBIO USAGE (LANCIO THREAD)
	 * 
	 * @param jobID ID del job
	 * @param index per indicizzare (true) o soltanto validare (false)
	 * @return oggetto json di uscita del servizio
	 */
	public JsonImportReport launchImportJob(String jobID, boolean index) {
		if(index && !jobID.startsWith("imp_")) {
			logger.error("L'ID del processo " + jobID + " non corrisponde ad un processo di importazione");
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-2);
			report.setMessage("L'ID del processo " + jobID + " non corrisponde ad un processo di importazione");
			return report;
		}
		
		else if(!index && !jobID.startsWith("val_") && !jobID.startsWith("valmd5_")) {
			logger.error("L'ID del processo " + jobID + " non corrisponde ad un processo di validazione");
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-2);
			report.setMessage("L'ID del processo " + jobID + " non corrisponde ad un processo di validazione");
			return report;
		}
		
		String processType = index ? "importazione" : "validazione";
		
		try {
			// ricerca report
			ImportReport reportObject = homeManager.importMags(jobID);

			// oggetto report non trovato
			if(reportObject == null) {
				logger.error("Processo di " + processType + " " + jobID + " non creato o inesistente");
				JsonImportReport report = new JsonImportReport();
				report.setStatus(-3);
				report.setMessage("Processo di " + processType + " " + jobID + " non creato o inesistente");
				return report;
			}
			
			// conversione in oggetto json
			return Utility.toJsonReportObject(reportObject);
			
		} catch (DaoException e) {
			logger.error(e.getMessage());
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-4);
			report.setMessage(e.getMessage());
			return report;
		} catch (InterruptedException e) {
			logger.error("Thread di " + processType + " bruscamente interrotto");
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-5);
			report.setMessage("Thread di " + processType + " bruscamente interrotto");
			return report;
		} catch (ParseException e) {
			logger.error("Formato data non corretto, usare 'yyyy-MM-dd HH:mm:ss.SSS'");
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-6);
			report.setMessage("Formato data non corretto, usare 'yyyy-MM-dd HH:mm:ss.SSS'");
			return report;
		} catch (IOException e) {
			logger.error("Errore di lettura report: " + e.getMessage());
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-7);
			report.setMessage("Errore di lettura report: " + e.getMessage());
			return report;
		}
	}

	/**
	 * Restituzione stato di un processo di importazione/validazione
	 * 
	 * STATUS E REPORT JOB DI IMPORT
	 * 
	 * @param jobID ID del job
	 * @param reportType tipo di report
	 * @param index per indicizzare (true) o soltanto validare (false)
	 * @return
	 */
	public Response getReportStatus(String jobID, String reportType, boolean index) {
		// capire se è un processo di importazione
		if(index && !jobID.startsWith("imp_")) {
			logger.error("L'ID del processo " + jobID + " non corrisponde ad un processo di importazione");
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-5);
			report.setMessage("L'ID del processo " + jobID + " non corrisponde ad un processo di importazione");
			return Response.ok(report, MediaType.APPLICATION_JSON).build();
		}
		
		else if(!index && !jobID.startsWith("val_") && !jobID.startsWith("valmd5_")) {
			logger.error("L'ID del processo " + jobID + " non corrisponde ad un processo di validazione");
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-5);
			report.setMessage("L'ID del processo " + jobID + " non corrisponde ad un processo di validazione");
			return Response.ok(report, MediaType.APPLICATION_JSON).build();
		}
		
		try {
			String processType = index ? "importazione" : "validazione";
			
			if(reportType == null) {
				// ricerca oggetto report
				ImportReport report = homeManager.getReport(jobID);
	
				// oggetto report non trovato, problema job
				if(report == null) {
					logger.error("Job di " + processType + " " + jobID + " non trovato o inesistente");
					JsonImportReport reportError = new JsonImportReport();
					reportError.setStatus(-2);
					reportError.setMessage("Job di " + processType + " " + jobID + " non trovato o inesistente");
					return Response.ok(reportError, MediaType.APPLICATION_JSON).build();
				}

				// conversione ad oggetto json
				return Response.ok(Utility.toJsonReportObject(report), MediaType.APPLICATION_JSON).build();
			}
			
			else {
				InputStream report = homeManager.getReport(jobID, reportType);
	
				// oggetto report non trovato, problema job
				if(report == null) {
					logger.error("Report del job di " + processType + " " + jobID + " non trovato o inesistente");
					JsonImportReport reportError = new JsonImportReport();
					reportError.setStatus(-2);
					reportError.setMessage("Report del job di " + processType + " " + jobID + " non trovato o inesistente");
					return Response.ok(reportError, MediaType.APPLICATION_JSON).build();
				}
				String fileFormat = "LOG".equals(reportType) ? "log" : "xml";
				
				return Response.ok(report, MediaType.APPLICATION_OCTET_STREAM).
						header("Content-Disposition", "attachment; filename=\"status." + fileFormat + "\"").
						build();
			}
			
		} catch (DaoException e) {
			logger.error(e.getMessage());
			JsonImportReport report = new JsonImportReport();
			report.setStatus(-3);
			report.setMessage(e.getMessage());
			return Response.ok(report).build();
		} catch (IOException e) {
			logger.error("Problemi di lettura report: " + e.getMessage());
			JsonImportReport reportError = new JsonImportReport();
			reportError.setStatus(-4);
			reportError.setMessage("Problemi di lettura report: " + e.getMessage());
			return Response.ok(reportError).build();
		}
	}

	/**
	 * Esegue il recovery di un processo di importazione
	 * 
	 * RIPRISTINO E RILANCIO JOB DI IMPORT
	 * NON USATO!!!
	 * 
	 * @param jobID ID del job di importazione da riattivare
	 * @return ImportReport report finale
	 * @throws DaoException
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public ImportReport recoverImport(String jobID) 
			throws DaoException, IOException, SolrServerException {
		
		// oggetto report
		return homeManager.recoverImport(jobID);
	}

}
