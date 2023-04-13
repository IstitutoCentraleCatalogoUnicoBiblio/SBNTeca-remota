package com.gruppometa.sbntecaremota.restweb.objects;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppometa.sbntecaremota.objects.validators.LabelMapper;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gruppometa.sbntecaremota.objects.ImportMagConfiguration;
import com.gruppometa.sbntecaremota.objects.ImportMagProject;
import com.gruppometa.sbntecaremota.objects.ImportReport;
import com.gruppometa.sbntecaremota.objects.ImportSettings;
import com.gruppometa.sbntecaremota.objects.ProjectSummary;
import com.gruppometa.sbntecaremota.objects.ValidationReport;
import com.gruppometa.sbntecaremota.objects.db.DBImportDetail;
import com.gruppometa.sbntecaremota.objects.db.DBImportDetailDao;
import com.gruppometa.sbntecaremota.objects.db.DBImportMag;
import com.gruppometa.sbntecaremota.objects.db.DBImportMagDao;
import com.gruppometa.sbntecaremota.objects.db.DBOaiIdentifierDao;
import com.gruppometa.sbntecaremota.objects.db.DBProject;
import com.gruppometa.sbntecaremota.objects.db.DBProjectDao;
import com.gruppometa.sbntecaremota.objects.db.DBProjectHistory;
import com.gruppometa.sbntecaremota.objects.db.DBProjectHistoryDao;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcess;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcessDao;
import com.gruppometa.sbntecaremota.objects.db.DBTecaUser;
import com.gruppometa.sbntecaremota.objects.db.DBTecaUserDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.objects.db.ProjectOperation;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;

/**
 * Si occupa della gestione dei servizi di importazione e validazione,
 * secondo una logica a 3 servizi: il servizio di presa in carico, il servizio di
 * effettiva esecuzione ed il servizio di controllo dello stato del processo.
 * Per ogni processo di importazione (o validazione) viene creato un thread che il 
 * manager gestisce
 *
 *
 */
public class HomeManager {
	
	// mappa delle importazioni
	private Map<String, ImportSettings> activeImports = new HashMap<String, ImportSettings>();
	
	// mappa thread da lanciare
	private Map<String, ImportMagRunnable> threadMap = new HashMap<String, ImportMagRunnable>();
	
	// teca process dao
	@Autowired
	private DBTecaProcessDao tecaProcessDao;

	@Autowired
	private VfsFileSystem vfsFileSystem;

	// import detail dao
	@Autowired
	private DBImportDetailDao importDetailDao;

	// teca process dao
	@Autowired
	private DBProjectDao projectDao;
	
	// import detail dao
	@Autowired
	private DBProjectHistoryDao projectHistoryDao;
	
	// import mag dao
	@Autowired
	private DBImportMagDao importMagDao;

	// user dao
	@Autowired
	private DBTecaUserDao userDao;

	// oai identifier dao
	@Autowired
	private DBOaiIdentifierDao oaiIdentifierDao;

	// delivery
	@Autowired
	private MagResourceDelivery delivery;
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(HomeManager.class);

	// output factory xml
	private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	
	
	
	/**
	 * Il metodo si occupa della presa in carico di un processo di importazione (o di validazione)
	 * 
	 * PREPARAZIONE PROCESSO DI IMPORT
	 * 
	 * @param mags i path dei MAG coinvolti
	 * @param settings impostazioni di lancio del processo
	 * @return ImportReport riferimento all'oggetto del report di riepilogo
	 * @throws DaoException
	 * @throws IOException
	 */
	public ImportReport prepareImport(List<ImportMagProject> projects, ImportSettings settings) throws DaoException, IOException {
		int numMags = 0;
		
		for(ImportMagProject p : projects)
			numMags += p.getMags().size();
		
		// utente
		DBTecaUser user = userDao.getUserByID(settings.getUserID());
		
		if(user == null)
			throw new DaoException("Utente ID " + settings.getUserID() + " non trovato");
		
		logger.info("Processo " + settings.getJobID() + " avviato, " + numMags + " MAG da elaborare");
		
		// crea job sul database
		DBTecaProcess tecaProcess = new DBTecaProcess();
		tecaProcess.setId(settings.getJobID());
		tecaProcess.setTecaUser(user);
		tecaProcess.setStatus(1);
		tecaProcess.setMessage("Processo " + settings.getJobID() + " avviato, " + numMags + " MAG da elaborare");
		tecaProcess.setTimestampStart(new BigInteger(System.currentTimeMillis() + ""));
		tecaProcessDao.insert(tecaProcess);
		
		// aggiungi dettagli di importazione al job generale
		ObjectMapper mapper = new ObjectMapper();
		DBImportDetail detail = new DBImportDetail();
		detail.setMag_OK(0);
		detail.setMag_KO(0);
		detail.setNumMag(numMags);
		detail.setProcessedMag(0);
		detail.setIndexedMag(0);
		detail.setFlagUpdate(settings.getUpdate());
		detail.setConfiguration(mapper.writeValueAsString(settings.getConfiguration()));
		detail.setPublicFlag(settings.getPubblica());
		detail.setDressFlag(settings.getDressMag());
		detail.setSource(settings.getTypeMag());
		detail.setUsageA(StringUtils.join(settings.getUsageA(), " "));
		detail.setUsageE(StringUtils.join(settings.getUsageE(), " "));
		detail.setTecaProcess(tecaProcess);
		importDetailDao.insert(detail);
		
		// salvataggio dei MAG da importare
		List<DBImportMag> magListJobAssociationDB = new ArrayList<DBImportMag>();
		DBProject projectDB = null;
		
		for(ImportMagProject p : projects) {
			projectDB = projectDao.getProjectByName(p.getName());
			
			if(projectDB == null) {
				projectDB = new DBProject();
				projectDB.setName(p.getName());
				projectDB.setStatus(0);
				projectDB.setLastOperation(ProjectOperation.UPLOAD);
				projectDB.setLastModified(new BigInteger("" + System.currentTimeMillis()));
				projectDao.insert(projectDB);
			}
			
			for(ImportMagConfiguration mag : p.getMags()) {
				DBImportMag magImport = new DBImportMag();
				magImport.setMag(mag.getPath());
				magImport.setTecaProcess(tecaProcess);
				magImport.setProject(projectDB);
				magListJobAssociationDB.add(magImport);
			}
			
			DBProjectHistory history = new DBProjectHistory();
			history.setProject(projectDB);
			history.setTecaProcess(tecaProcess);
			history.setImportType(p.isAll() ? 2 : 1);
			history.setIndexedMag(0);
			history.setProcessedMag(0);
			
			if(!settings.getIndexMags())
				history.setOperationType(ProjectOperation.VALIDATE);
			
			else if(settings.getUpdate() == 1)
				history.setOperationType(ProjectOperation.IMPORT_UPDATE);
			
			else
				history.setOperationType(ProjectOperation.IMPORT);
			
			history.setStatus(1);
			history.setTimestampOperation(new BigInteger(System.currentTimeMillis() + ""));
			projectHistoryDao.insert(history);
		}
		
		importMagDao.insert(magListJobAssociationDB);
		
		// inizio processo
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(tecaProcess.getTimestampStart().longValue());
		
		// aggiunta thread da lanciare
		ImportMagRunnable runnable = new ImportMagRunnable();
		runnable.setProcessDao(tecaProcessDao);
		runnable.setImportMagDao(importMagDao);
		runnable.setProjectDao(projectDao);
		runnable.setProjectHistoryDao(projectHistoryDao);
		runnable.setOaiIdentifierDao(oaiIdentifierDao);
		runnable.setProjects(projects);
		runnable.setJobID(settings.getJobID());
		runnable.setDataMap(activeImports);
		runnable.setVfsFileSystem(vfsFileSystem);
		runnable.setDelivery(delivery);
		threadMap.put(settings.getJobID(), runnable);

		// aggiorna report xml
		ImportReport xmlReport = new ImportReport();
		xmlReport.setId(settings.getJobID());
		xmlReport.setUser(settings.getUserID());
		xmlReport.setStart(formatter.format(cal.getTime()));
		xmlReport.setStatus(1);
		xmlReport.setStatusMessage("Processo " + settings.getJobID() + " avviato, " + numMags + " MAG da elaborare");
		xmlReport.setProcessedMag(0);
		xmlReport.setMagOK(0);
		xmlReport.setMagKO(0);
		xmlReport.setMetsOK(0);
		xmlReport.setMetsOK(0);
		xmlReport.setNumMag(numMags);
		settings.setReport(xmlReport);
		
		// creazione report xml
		synchronized (activeImports) {
			activeImports.put(settings.getJobID(), settings);
		}
		
		return xmlReport;
	}
	
	/**
	 * Tale metodo si occupa di recuperare le impostazioni del job di importazione, permettendo di riavviarlo
	 * 
	 * RECUPERO E RIAVVIO IMPORT
	 * NON USATO!!!
	 * 
	 * @param jobID ID del job di importazione
	 * @return ImportReport oggetto report del job di importazione per il formato XML
	 * @throws DaoException
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public ImportReport recoverImport(String jobID) throws DaoException, IOException, SolrServerException {
		// recupero dettagli job di importazione
		DBImportDetail detailDB = importDetailDao.getImportDetailByJob(jobID);
		ObjectMapper mapper = new ObjectMapper();
		
		// se il job è terminato correttamente restituisci null
		if(detailDB.getTecaProcess().getStatus() == 0)
			return null;
		
		// se il job è in esecuzione restituisci null
		if(activeImports.containsKey(jobID))
			return null;
		
		
		// recupero dei mag per il job da rilanciare
		List<DBImportMag> magListDB = importMagDao.getMagsByJob(jobID);
		
		// ricerca MAG in indice solr
		List<ImportMagProject> projects = new ArrayList<ImportMagProject>();
		List<String> indexedMags = UtilSolr.getPathMagsByJob(jobID);
		int numMags = 0;
		
		// ricostruzione progetti
		for(DBImportMag magDB : magListDB) {
			if(!indexedMags.contains(magDB.getMag())) {
				ImportMagProject currentProject = null;
				
				// ricerca il progetto di riferimento del MAG tra quelli rilevati
				for(ImportMagProject p : projects) {
					if(p.getName().equals(magDB.getProject().getName())) {
						currentProject = p;
						break;
					}
				}

				// ricerca nel database la storia di quel progetto relativamente al job
				DBProjectHistory projectJobHistory = projectHistoryDao.getProjectHistoryByNameAndJob(magDB.getProject().getName(), jobID);
				
				// non trovato sul database
				if(projectJobHistory == null)
					throw new DaoException("Progetto inesistente o non appartenente al job");
				
				// in caso di rilevamento progetto aggiungilo alla lista dei rilevati
				if(currentProject == null) {
					currentProject = new ImportMagProject();
					currentProject.setName(magDB.getProject().getName());
					projects.add(currentProject);
				}
				
				// imposta le informazioni
				currentProject.setAll(projectJobHistory.getImportType() == 2);
				
				ImportMagConfiguration magConfig = new ImportMagConfiguration();
				magConfig.setPath(magDB.getMag());
				magConfig.setConfiguration(mapper.readValue(detailDB.getConfiguration(), Properties.class));
				magConfig.setPublicFlag(detailDB.getPublicFlag());
				magConfig.setDressFlag(detailDB.getDressFlag());
				currentProject.getMags().add(magConfig);
				numMags++;
			}
		}

		// recupero dati in caso di lista vuota
		if(projects.isEmpty())
			return this.getReport(jobID);
		
		
		
		// recupero impostazioni del job
		// impostazioni generali
		ImportSettings settings = new ImportSettings();
		settings.setJobID(jobID);
		settings.setIndexMags(true);
		settings.setUpdate(detailDB.getFlagUpdate());
		settings.setPubblica(detailDB.getPublicFlag());
		settings.setDressMag(detailDB.getDressFlag());
		settings.setTypeMag(detailDB.getSource());
		settings.setUserID(detailDB.getTecaProcess().getTecaUser().getId());
		settings.setUsageA(Arrays.asList(detailDB.getUsageA().split(" ")));
		settings.setUsageE(Arrays.asList(detailDB.getUsageE().split(" ")));
		
		// inizio processo
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(detailDB.getTecaProcess().getTimestampStart().longValue());
		
		// carica oggetto report
		ImportReport xmlReport = new ImportReport();
		xmlReport.setId(settings.getJobID());
		xmlReport.setUser(settings.getUserID());
		xmlReport.setStart(formatter.format(cal.getTime()));
		xmlReport.setStatus(1);
		xmlReport.setStatusMessage("Processo " + settings.getJobID() + " riavviato, " + numMags + " MAG da elaborare");
		xmlReport.setProcessedMag(indexedMags.size());
		xmlReport.setMagOK(indexedMags.size());
		xmlReport.setMetsOK(indexedMags.size());
		xmlReport.setIndexedMag(indexedMags.size());
		xmlReport.setMagKO(0);
		xmlReport.setMetsKO(0);
		xmlReport.setNumMag(numMags + indexedMags.size());
		settings.setReport(xmlReport);
		
		// configurazione
		settings.setConfiguration(mapper.readValue(detailDB.getConfiguration(), Properties.class));
		Utility.configureValidation(settings);

		// modifica job sul database
		DBTecaProcess tecaProcess = detailDB.getTecaProcess();
		tecaProcess.setStatus(1);
		tecaProcess.setMessage("Processo " + settings.getJobID() + " riavviato, " + numMags + " MAG da elaborare");
		tecaProcess.setTimestampEnd(null);
		tecaProcessDao.update(tecaProcess);
		
		// modifica report XML
		settings.getReport().setStatus(1);
		settings.getReport().setStatusMessage("Processo " + settings.getJobID() + " riavviato, " + numMags + " MAG da elaborare");
		
		// aggiunta thread da lanciare
		synchronized (activeImports) {
			activeImports.put(jobID, settings);
		}
		
		ImportMagRunnable runnable = new ImportMagRunnable();
		runnable.setProcessDao(tecaProcessDao);
		runnable.setImportMagDao(importMagDao);
		runnable.setProjectDao(projectDao);
		runnable.setProjectHistoryDao(projectHistoryDao);
		runnable.setOaiIdentifierDao(oaiIdentifierDao);
		runnable.setProjects(projects);
		runnable.setJobID(jobID);
		runnable.setDataMap(activeImports);
		runnable.setDelivery(delivery);
		threadMap.put(settings.getJobID(), runnable);
		return settings.getReport();
	}
	
	/**
	 * Tale metodo esegue l'effettiva importazione (o validazione) dei MAG precedentemente 
	 * indicati nel servizio di presa in carica
	 * 
	 * LANCIO THREAD DI IMPORT
	 * 
	 * @param jobID ID del job di importazione (o validazione)
	 * @return ImportReport riferimento all'oggetto del report
	 * @throws DaoException
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public ImportReport importMags(String jobID) throws DaoException, ParseException, InterruptedException, IOException {
		// ricerca il thread in carico: thread non trovato
		if(threadMap.containsKey(jobID)) {
			ImportMagRunnable runnable = threadMap.get(jobID);
			threadMap.remove(jobID);
			
			// lancia thread e attendi la terminazione del job
			Thread thread = new Thread(runnable);
			thread.start();
			thread.join();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			
			synchronized (activeImports) {
				// recupera report XML come oggetto
				ImportSettings settings = activeImports.get(jobID);
				ImportReport report = settings.getReport();
				activeImports.remove(jobID);
				
				// salvataggio dettagli job di importazione
				DBImportDetail detail = importDetailDao.getImportDetailByJob(jobID);
				
				DBTecaProcess process = detail.getTecaProcess();
				process.setStatus(report.getStatus());
				process.setMessage(report.getStatusMessage());
				process.setTimestampEnd(new BigInteger(formatter.parse(report.getEnd()).getTime() + ""));
				tecaProcessDao.update(process);
				
				detail.setMag_OK(report.getMagOK());
				detail.setMag_KO(report.getMagKO());
				detail.setNumMag(report.getNumMag());
				detail.setProcessedMag(report.getProcessedMag());
				detail.setIndexedMag(report.getIndexedMag());
				detail.setTimestampStartValidation(new BigInteger(formatter.parse(report.getValidation().getStart()).getTime() + ""));
				detail.setTimestampEndValidation(new BigInteger(formatter.parse(report.getValidation().getEnd()).getTime() + ""));
				importDetailDao.update(detail);
				
				// ritorna oggetto report xml
				return report;
			}
		}
		
		return null;
	}
	
	/**
	 * Tale metodo esegue la richiesta di status di un processo di importazione (o validazione)
	 * 
	 * STATUS DEL JOB DI IMPORT
	 * 
	 * @param jobID ID del job di importazione (o validazione)
	 * @return ImportReport riferimento all'oggetto del report
	 * @throws DaoException
	 * @throws IOException
	 */
	public ImportReport getReport(String jobID) throws DaoException, IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		// attivo
		if(activeImports.containsKey(jobID)) {
			ImportReport report = new ImportReport();
			
			synchronized (activeImports) {
				logger.info("Recupero job " + jobID + " da processi in corso");
				ImportReport copy = activeImports.get(jobID).getReport();
				
				report.setId(copy.getId());
				report.setStatus(copy.getStatus());
				report.setStatusMessage(copy.getStatusMessage());
				report.setStart(copy.getStart());
				report.setEnd(copy.getEnd());
				report.setNumMag(copy.getNumMag());
				report.setProcessedMag(copy.getProcessedMag());
				report.setIndexedMag(copy.getIndexedMag());
				report.setMagOK(copy.getMagOK());
				report.setMagKO(copy.getMagKO());
				report.setMetsOK(copy.getMetsOK());
				report.setMetsKO(copy.getMetsKO());
				report.setUser(copy.getUser());
				
				if(copy.getValidation() != null) {
					report.setValidation(new ValidationReport());
					
					if(copy.getValidation().getStart() != null)
						report.getValidation().setStart(copy.getValidation().getStart());
					
					if(copy.getValidation().getEnd() != null)
						report.getValidation().setEnd(copy.getValidation().getEnd());
				}
				
				return report;
			}
		}
		
		else {
			// controlla nel database
			DBImportDetail detail = importDetailDao.getImportDetailByJob(jobID);
			
			// job assente o inesistente
			if(detail == null)
				return null;
			
			List<DBProjectHistory> projectsJob = projectHistoryDao.getProjectsHistoryByJob(jobID);
			
			// preleva oggetto xml report
			logger.info("Recupero job " + jobID + " da database");
			ImportReport report = new ImportReport();
			report.setId(detail.getTecaProcess().getId());
			report.setStatus(detail.getTecaProcess().getStatus());
			report.setStatusMessage(detail.getTecaProcess().getMessage());
			
			if(detail.getTecaProcess().getTimestampStart() != null)
				report.setStart(formatter.format(new Date(detail.getTecaProcess().getTimestampStart().longValue())));
			
			if(detail.getTecaProcess().getTimestampEnd() != null)
				report.setEnd(formatter.format(new Date(detail.getTecaProcess().getTimestampEnd().longValue())));
			
			report.setNumMag(detail.getNumMag());
			report.setProcessedMag(detail.getProcessedMag());
			report.setIndexedMag(detail.getIndexedMag());
			report.setMagOK(detail.getMag_OK());
			report.setMagKO(detail.getMag_KO());
			report.setMetsOK(detail.getMets_OK());
			report.setMetsKO(detail.getMets_KO());
			report.setUser(detail.getTecaProcess().getTecaUser().getId());
			
			// preleva numero di oggetti digitali
			for(DBProjectHistory project : projectsJob) {
				if(project.getProcessedMag() == 0 && project.getIndexedMag() == 0) {
					String projectName = project.getProject().getName();
					File projectFile = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), projectName);
					ProjectSummary summary = UtilXML.scanDir(projectFile);
					
					if(report.getDigitalObjectCount() == null)
						report.setDigitalObjectCount(0);
					
					report.setDigitalObjectCount(report.getDigitalObjectCount() + summary.getDigitalObjects().size());
				}
			}
			
			if(detail.getTimestampStartValidation() != null || detail.getTimestampEndValidation() != null) {
				report.setValidation(new ValidationReport());
				
				if(detail.getTimestampStartValidation() != null)
					report.getValidation().setStart(formatter.format(new Date(detail.getTimestampStartValidation().longValue())));
				
				if(detail.getTimestampEndValidation() != null)
					report.getValidation().setEnd(formatter.format(new Date(detail.getTimestampEndValidation().longValue())));
			}
			
			return report;
		}
	}
	
	/**
	 * Tale metodo restituisce lo stream del report di un processo di importazione (o validazione)
	 * 
	 * REPORT (XML O LOG) DEL JOB DI IMPORT
	 * 
	 * @param jobID ID del job di importazione (o validazione)
	 * @param type tipo di report da restituire (ALL, WARNING, ERROR, LOG)
	 * @return InputStream lo stream del report
	 * @throws DaoException
	 * @throws IOException
	 */
	public InputStream getReport(String jobID, String type) throws DaoException, IOException {
		ImportReport report = this.getReport(jobID);
		List<DBImportMag> magsDB = new ArrayList<DBImportMag>();
		
		if("LOG".equals(type) || "ALL".equals(type))
			magsDB.addAll(importMagDao.getMagsByJobResult(jobID));
		
		else if("WARNING".equals(type))
			magsDB.addAll(importMagDao.getMagsByJobWarnings(jobID));
		
		else if("ERROR".equals(type))
			magsDB.addAll(importMagDao.getMagsByJobErrors(jobID));

		filterMagImports(magsDB);

		return "LOG".equals(type) ? this.getLogStream(report, magsDB) : this.getXMLStream(report, type, magsDB);
	}

	private void filterMagImports(List<DBImportMag> mags){
		if(mags==null)
			return;
		mags.stream().forEach(m-> m.setMag(LabelMapper.getFilename(m.getMag())));
	}
	private void filterReport(ImportReport report) {
		if(report == null)
			return;
		report.getValidation().getMagReports().stream()
				.forEach(magReport-> magReport.setPath(LabelMapper.getFilename(magReport.getPath())));
	}

	/**
	 * Metodo di scrittura del file XML di report
	 * 
	 * @param report oggetto report
	 * @param type tipo di report (ALL, solo WARNING, solo ERROR)
	 * @param magsDB i mag legati al processo
	 * @return lo stream del file XML
	 */
	private InputStream getXMLStream(ImportReport report, String type, List<DBImportMag> magsDB) {
		StringWriter writer = new StringWriter();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		XMLStreamWriter xmlWriter = null;
		
		try {
			// scrittura file
			xmlWriter = outputFactory.createXMLStreamWriter(writer);
			xmlWriter.writeStartDocument();
			
			// tag import
			xmlWriter.writeCharacters("\n");
			xmlWriter.writeStartElement("import");
			
			if(report.getStart() != null)
				xmlWriter.writeAttribute("start", report.getStart());
			
			if(report.getEnd() != null)
				xmlWriter.writeAttribute("end", report.getEnd());
			
			
			// tag generali
			xmlWriter.writeCharacters("\n    ");
			xmlWriter.writeStartElement("user");
			xmlWriter.writeCharacters(report.getUser() + "");
			xmlWriter.writeEndElement();
			
			xmlWriter.writeCharacters("\n    ");
			xmlWriter.writeStartElement("status");
			xmlWriter.writeCharacters(report.getStatus() + "");
			xmlWriter.writeEndElement();
			
			xmlWriter.writeCharacters("\n    ");
			xmlWriter.writeStartElement("status-message");
			xmlWriter.writeCharacters(report.getStatusMessage());
			xmlWriter.writeEndElement();
			
			xmlWriter.writeCharacters("\n    ");
			xmlWriter.writeStartElement("num-mag");
			xmlWriter.writeCharacters(report.getNumMag() + "");
			xmlWriter.writeEndElement();
			
			xmlWriter.writeCharacters("\n    ");
			xmlWriter.writeStartElement("processed-mag");
			xmlWriter.writeCharacters(report.getProcessedMag() + "");
			xmlWriter.writeEndElement();
			
			xmlWriter.writeCharacters("\n    ");
			xmlWriter.writeStartElement("indexed-mag");
			xmlWriter.writeCharacters(report.getIndexedMag() + "");
			xmlWriter.writeEndElement();
			
			xmlWriter.writeCharacters("\n    ");
			xmlWriter.writeStartElement("magOK");
			xmlWriter.writeCharacters(report.getMagOK() + "");
			xmlWriter.writeEndElement();
			
			xmlWriter.writeCharacters("\n    ");
			xmlWriter.writeStartElement("magKO");
			xmlWriter.writeCharacters(report.getMagKO() + "");
			xmlWriter.writeEndElement();
			
			if(report.getDigitalObjectCount() != null) {
				xmlWriter.writeCharacters("\n    ");
				xmlWriter.writeStartElement("digital-objects");
				xmlWriter.writeCharacters(report.getDigitalObjectCount() + "");
				xmlWriter.writeEndElement();
			}
			
			if(report.getValidation() != null) {
				xmlWriter.writeCharacters("\n    ");
				xmlWriter.writeStartElement("validation");
				
				if(report.getValidation().getStart() != null)
					xmlWriter.writeAttribute("start", report.getValidation().getStart());
				
				if(report.getValidation().getEnd() != null)
					xmlWriter.writeAttribute("end", report.getValidation().getEnd());
				
				
				for(DBImportMag magDB : magsDB) {
					xmlWriter.writeCharacters("\n        ");
					xmlWriter.writeStartElement("mag-validation");
					xmlWriter.writeAttribute("timestamp", formatter.format(new Date(magDB.getValidationTime().longValue())));
					xmlWriter.writeAttribute("result", magDB.getResult());
					xmlWriter.writeAttribute("file", magDB.getMag());
					xmlWriter.writeAttribute("status", magDB.getStatusMessage());
					
					if(magDB.getFatalErrors() != null && !"WARNING".equals(type)) {
						String[] errors = magDB.getFatalErrors().split("\n");
						
						for(String e : errors) {
							xmlWriter.writeCharacters("\n            ");
							xmlWriter.writeStartElement("message");
							xmlWriter.writeAttribute("type", "FATAL_ERROR");
							xmlWriter.writeCharacters(e);
							xmlWriter.writeEndElement();
						}
					}

					if(magDB.getErrors() != null && !"WARNING".equals(type)) {
						String[] errors = magDB.getErrors().split("\n");
						
						for(String e : errors) {
							xmlWriter.writeCharacters("\n            ");
							xmlWriter.writeStartElement("message");
							xmlWriter.writeAttribute("type", "ERROR");
							xmlWriter.writeCharacters(e);
							xmlWriter.writeEndElement();
						}
					}
					
					if(magDB.getWarnings() != null && !"ERROR".equals(type)) {
						String[] warnings = magDB.getWarnings().split("\n");
						
						for(String w : warnings) {
							xmlWriter.writeCharacters("\n            ");
							xmlWriter.writeStartElement("message");
							xmlWriter.writeAttribute("type", "WARNING");
							xmlWriter.writeCharacters(w);
							xmlWriter.writeEndElement();
						}
					}
					
					xmlWriter.writeCharacters("\n        ");
					xmlWriter.writeEndElement();
				}
				
				xmlWriter.writeCharacters("\n    ");
				xmlWriter.writeEndElement();
			}
			
			// fine documento
			xmlWriter.writeCharacters("\n");
			xmlWriter.writeEndElement();
			xmlWriter.writeEndDocument();
			
		} catch (XMLStreamException e) {
			logger.error(e.getMessage(), e);
		} finally {
			// chiusura file
			if(xmlWriter != null) {
				try {
					xmlWriter.close();
					
				} catch (XMLStreamException e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			if(writer != null) {
				try {
					String xml = writer.toString();
					writer.close();
					return new ByteArrayInputStream(xml.getBytes());
					
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Metodo di scrittura del report in formato LOG
	 * 
	 * @param report oggetto report
	 * @param magsDB MAG del processo
	 * @return lo stream del file LOG
	 * @throws IOException
	 */
	private InputStream getLogStream(ImportReport report, List<DBImportMag> magsDB) throws IOException {
		StringWriter writer = new StringWriter();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		if(report.getStart() != null) {
			writer.write(report.getStart() + ": Processo " + report.getId() + " avviato dall'utente " + report.getUser() + "\n");
			
			if(report.getValidation() != null && report.getValidation().getStart() != null) {
				writer.write(report.getValidation().getStart() + ": Validazione MAG in corso ...\n");
				
				for(DBImportMag magDB : magsDB) {
					String timestamp = formatter.format(new Date(magDB.getValidationTime().longValue()));
					writer.write(timestamp + ": Validazione MAG " + magDB.getMag() + " - " + magDB.getResult() + "\n");
					writer.write(timestamp  + ": " + magDB.getStatusMessage());
					
					if(magDB.getFatalErrors() != null) {
						String[] fatalErrors = magDB.getFatalErrors().split("\n");
						
						for(String e : fatalErrors)
							writer.write(timestamp + ": FATAL ERROR - " + e + "\n");
					}
					
					if(magDB.getErrors() != null) {
						String[] errors = magDB.getErrors().split("\n");
						
						for(String e : errors)
							writer.write(timestamp + ": ERROR - " + e + "\n");
					}
					
					if(magDB.getWarnings() != null) {
						String[] warnings = magDB.getWarnings().split("\n");
						
						for(String w : warnings)
							writer.write(timestamp + ": WARNING - " + w + "\n");
					}
				}
				
				if(report.getValidation().getEnd() != null)
					writer.write(report.getValidation().getEnd() + ": Validazione MAG terminata\n");
			}
			
			if(report.getEnd() != null) {
				writer.write(report.getEnd() + ": Processo " + report.getId() + " terminato - " + 
						report.getStatusMessage() + " - " + report.getProcessedMag() + " / " + report.getNumMag() + " elaborati, "
						+ report.getMagOK() + " MAG OK, "
						+ report.getMagKO() + " MAG KO, "
						+ report.getMetsOK() + " METS OK, "
						+ report.getMetsKO() + " METS KO"
						+ (report.getIndexedMag() > 0 ? ", " +
						report.getIndexedMag() + " MAG importati" : "") + "\n");
			}
			
			else {
				writer.write(formatter.format(new Date(System.currentTimeMillis())) + ": Processo " + report.getId() + " in esecuzione - " + 
						report.getStatusMessage() + " - " + report.getProcessedMag() + " / " + report.getNumMag() + " elaborati, "
						+ report.getMagOK() + " MAG OK, "
						+ report.getMagKO() + " MAG KO, "
						+ report.getMetsOK() + " METS OK, "
						+ report.getMetsKO() + " METS KO"
						+ (report.getIndexedMag() > 0 ? ", " +
						report.getIndexedMag() + " MAG importati" : "") + "\n");
			}
		}
		
		String log = writer.toString();
		writer.close();
		return new ByteArrayInputStream(log.getBytes());
	}

}
