package com.gruppometa.sbntecaremota.restweb.objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.gruppometa.sbntecaremota.objects.validators.*;
import com.gruppometa.sbntecaremota.restweb.AudioCutterComponent;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.gruppometa.sbntecaremota.objects.ErrorReport;
import com.gruppometa.sbntecaremota.objects.ExternalMagReference;
import com.gruppometa.sbntecaremota.objects.ImportMagConfiguration;
import com.gruppometa.sbntecaremota.objects.ImportMagProject;
import com.gruppometa.sbntecaremota.objects.ImportReport;
import com.gruppometa.sbntecaremota.objects.ImportSettings;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.MagReport;
import com.gruppometa.sbntecaremota.objects.ValidationReport;
import com.gruppometa.sbntecaremota.objects.WaitingMagRecovery;
import com.gruppometa.sbntecaremota.objects.db.DBImportMag;
import com.gruppometa.sbntecaremota.objects.db.DBImportMagDao;
import com.gruppometa.sbntecaremota.objects.db.DBOaiIdentifierDao;
import com.gruppometa.sbntecaremota.objects.db.DBProject;
import com.gruppometa.sbntecaremota.objects.db.DBProjectDao;
import com.gruppometa.sbntecaremota.objects.db.DBProjectHistory;
import com.gruppometa.sbntecaremota.objects.db.DBProjectHistoryDao;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcess;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcessDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.retrieve.MagPersistence;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceFactory;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;

/**
 * Rappresenta il thread responsabile della gestione di un processo di importazione o validazione
 * 
 * THREAD DI IMPORT
 *
 *
 */
public class ImportMagRunnable implements Runnable {
	
	// lista mag path
	private List<ImportMagProject> projects;
	
	// job id
	private String jobID;
	
	// impostazioni di importazione
	private Map<String, ImportSettings> dataMap;
	
	// process dao
	private DBTecaProcessDao processDao;

	// import mag dao
	private DBImportMagDao importMagDao;

	// project dao
	private DBProjectDao projectDao;

	// project history dao
	private DBProjectHistoryDao projectHistoryDao;

	// oai identifier dao
	private DBOaiIdentifierDao oaiIdentifierDao;

	private VfsFileSystem vfsFileSystem;

	// delivery
	private MagResourceDelivery delivery;

	// output factory xml
	private static XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(ImportMagRunnable.class);
	
	
	/**
	 * Imposta lista dei progetti
	 * 
	 * @param mags lista dei progetti
	 */
	public void setProjects(List<ImportMagProject> projects) {
		this.projects = projects;
	}
	
	/**
	 * Imposta mappa condivisa dei dati di riepilogo del thread
	 * 
	 * @param dataMap mappa condivisa
	 */
	public void setDataMap(Map<String, ImportSettings> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * Imposta il componente DAO per la tabella teca_process
	 * 
	 * @param processDao componente DAO
	 */
	public void setProcessDao(DBTecaProcessDao processDao) {
		this.processDao = processDao;
	}

	/**
	 * Imposta il componente DAO per la tabella import_mag
	 * 
	 * @param importMagDao componente DAO
	 */
	public void setImportMagDao(DBImportMagDao importMagDao) {
		this.importMagDao = importMagDao;
	}

	/**
	 * Imposta il componente DAO per la tabella project
	 * 
	 * @param importDetailDao componente DAO per la tabella project
	 */
	public void setProjectDao(DBProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	/**
	 * Imposta il componente DAO per la tabella project_history
	 * 
	 * @param importMagDao componente DAO per la tabella project_history
	 */
	public void setProjectHistoryDao(DBProjectHistoryDao projectHistoryDao) {
		this.projectHistoryDao = projectHistoryDao;
	}

	/**
	 * Imposta il componente DAO per la tabella oai_identifier
	 * 
	 * @param oaiIdentifierDao componente DAO per la tabella oai_identifier
	 */
	public void setOaiIdentifierDao(DBOaiIdentifierDao oaiIdentifierDao) {
		this.oaiIdentifierDao = oaiIdentifierDao;
	}

	/**
	 * Imposta il delivery delle risorse
	 * 
	 * @param delivery delivery delle risorse
	 */
	public void setDelivery(MagResourceDelivery delivery) {
		this.delivery = delivery;
	}
	
	/**
	 * Imposta l'ID del job
	 * 
	 * @param jobID ID del job
	 */
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}
	
	@Override
	/**
	 * Esecuzione del thread
	 * 
	 */
	public void run() {
		ImportSettings settings = null;
		
		synchronized (dataMap) {
			settings = Utility.copyImportSettings(dataMap.get(jobID));
		}
		
		// inizializzazioni varie
		ImportReport report = settings.getReport();
		Properties conf = settings.getConfiguration();
		String directoryReportSlot = new File(conf.getProperty("reportDIR"), jobID).getPath();
		String documentFormat = conf.getProperty("Validator.DocumentFormat", Mag.MAG);
		MagPersistence getMag = MagPersistenceFactory.create(settings.getTypeMag());
		getMag.setVfsFileSystem(vfsFileSystem);
		
		MagStorage magStorage = new MagStorage(conf.getProperty("UrlSolr"), 
				Integer.parseInt(conf.getProperty("MBUsableThreshold")), 
				conf.getProperty("resourceDIR"), conf.getProperty("solrDIR"), 
				oaiIdentifierDao, delivery);
		
		int batchImportSize = Integer.parseInt(conf.getProperty("BatchImportSize", "100"));
		
		// ricerca processo nel DB
		DBTecaProcess jobDB = null;
		
		try {
			jobDB = processDao.findByID(jobID);
			
			if(jobDB == null)
				throw new DaoException("Job di importazione " + jobID + " non presente nel database");
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
		
		// formato data
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		report.setStatus(2);
		report.setStatusMessage("Validazione MAG in corso ...");
		// scrittura log e file XML report
		//logger.info("Validazione MAG in corso ...");
		
		ValidationReport vp = new ValidationReport();
		vp.setStart(formatter.format(cal.getTime()));
		report.setValidation(vp);
		jobDB.setStatus(report.getStatus());
	
		// modifica report xml inizio fase di validazione
		synchronized(dataMap) {
			dataMap.put(jobID, Utility.copyImportSettings(settings));
		}
		
		updateDatabase(jobDB);
		
		// per ogni mag da validare
		while(!projects.isEmpty()) {
			ImportMagProject p = projects.remove(0);
			
			if(p.getTotalDigitalObjects() > 0 && p.getMags().isEmpty()) {
				if(report.getDigitalObjectCount() == null)
					report.setDigitalObjectCount(0);
				
				report.setDigitalObjectCount(report.getDigitalObjectCount() + p.getTotalDigitalObjects());

				// salvataggio
				synchronized(dataMap) {
					dataMap.put(jobID, Utility.copyImportSettings(settings));
				}
			}
			
			// inizializzazioni
			List<WaitingMagRecovery> externalReferences = new ArrayList<WaitingMagRecovery>();
			List<MagReport> magInfoQueue = new ArrayList<MagReport>();
			int processedMagProject = 0;
			int indexedMagProject = 0;
			
			try {
				for(ImportMagConfiguration magConfig : p.getMags()) {
					Document document = null;
					String currentPath = magConfig.getPath();
					// carica informazioni e apri documento
					document = Mag.MAG.equals(documentFormat) ?
							getMag.openMag(currentPath) : getMag.openMetsAsMag(currentPath, conf);
					/*
					// TODO se è cambio usage dovrei prendere i mag da Solr
					boolean testFeature = false;
					Mag magOriginale = null;
					if(magConfig.isChangeUsage() && testFeature) {
						// read the mag
						magOriginale = getMag.getMag(p.getName(), currentPath, document, null, settings,
								magConfig.getPublicFlag(), magConfig.getDressFlag(), documentFormat);
						String idMag = UtilSolr.createMagIdentifier(magOriginale);
						if(idMag!=null) {
							Mag mag = UtilSolr.selectDocumentById(idMag);
							if(mag!=null)
								document = UtilXML.convertStringToDocumentXML(mag.getMagTot());
						}
						if(document==null){
							logger.info("Document "+currentPath+ " non found in Solr.");
							continue;
						}
					}
					*/
					this.logSimpleMessageReport("Validazione in corso MAG/METS " + LabelMapper.getFilename(currentPath),
							report, settings);
					
					// oggetto report MAG XML
					MagReport magInfo = new MagReport();
					magInfo.setPath(currentPath);
					magInfo.setDocumentFormat(documentFormat);
					// inizializzazioni ciclo
					boolean block = false;
					int j = 0;
					List<ValidationError> magValidationErrors = new ArrayList<ValidationError>();
					
					// catena di validatori
					while(j < settings.getValidators().size() && !block) {
						// ogni validatore genera errori, warning, attese
						Validator validator = settings.getValidators().get(j);
						//logger.info("Validazione: "+ validator.getClass().getCanonicalName());
						Document documentToValidate = document;
						if(validator instanceof Metsable){
							if(!Mag.METS.equals(documentFormat)) {
								j++;
								continue;
							}
							else
								documentToValidate = getMag.openMag(currentPath);
						}
						ValidationResult result = validator.validate(getMag, currentPath, documentToValidate, magConfig.getConfiguration());
						magValidationErrors.addAll(result.getErrors());
						
						if(!result.getExternalReferences().isEmpty()) {
							// aggiungi riferimento a MAG esterno
							WaitingMagRecovery recovery = Utility.
									getRecoveryByWaitingMag(currentPath, externalReferences);
							
							if(recovery == null) {
								recovery = new WaitingMagRecovery();
								recovery.setMagReportObject(magInfo);
								recovery.setImportConfiguration(magConfig);
								recovery.setWaitingMag(currentPath);
								externalReferences.add(recovery);
							}
							
							for(ExternalMagReference externalReference : result.getExternalReferences())
								recovery.getReferences().add(externalReference);
						}
						
						// controlla se bloccare le validazioni (pre presenza di errori fatali o di attese)
						block = checkBlock(result.getErrors());
						j++;
					}
		
					// per ogni errore di validazione rilevato
					for(ValidationError err : magValidationErrors) {
						// se si tratta di una attesa a causa di files esterni memorizza il riferimento
						// al file esterno coi suoi dati in una mappa, che ha per chiave il path del MAG di attesa
						// e per valori la lista dei riferimenti
						if(!ValidationError.WAIT.equals(err.getStatus())) {
							ErrorReport msg = new ErrorReport();
							msg.setType(err.getStatus());
							msg.setMessage(err.getMessage());
							magInfo.getErrors().add(msg);
						}
						
						// scrittura su log dell'errore
						// writeReportLog(logFile, formatter.format(new Date(System.currentTimeMillis())) +  ": " + err.getStatus() + " - " + err.getMessage());
						logger.info(err.getStatus() + " - " + err.getMessage());
						if("ERROR".equalsIgnoreCase(err.getStatus()) && logger.isTraceEnabled()){
							logger.trace(UtilXML.convertDocumentToString(document));
						}
					}
					
					// mag in attesa
					WaitingMagRecovery recovery = Utility.
							getRecoveryByWaitingMag(currentPath, externalReferences);
					
					// controlla presenza/assenza errori
					boolean errorPresence = checkErrors(magInfo.getErrors());
					
					// cancella i riferimenti esterni se ci sono già errori
					if(errorPresence && recovery != null)
						externalReferences.remove(recovery);
					
					// determina se il MAG è corretto o no sulla base degli errori
					if(recovery == null || errorPresence) {
						// modifica report
						this.logAfterValidation(magInfo, errorPresence, report, settings, cal, formatter);
						processedMagProject++;
						String documentFormatDraft = null;
						// se è una bozza cancella
						boolean isDraft = false;
						if((vfsFileSystem.isDraft(currentPath) || Utility.isMagFromEditor(currentPath))
								&& MagReport.OK.equals(magInfo.getResult())
						) {
							isDraft = true;
							//Utility.deleteFile(currentPath);
							Mag magDraft = UtilSolr.selectDocumentById(Utility.getDraftIDFromFileName(currentPath));
							if(magDraft==null)
								magDraft = UtilSolr.selectDocumentByPath(currentPath);
							documentFormatDraft = magDraft==null?settings.getTypeMag():magDraft.getDocumentFormat();
							currentPath = vfsFileSystem.getVfsService().deleteDraftFile(currentPath);
						}
						if(settings.getIndexMags() && MagReport.OK.equals(magInfo.getResult())) {
							// recupero MAG
							Mag magObj = getMag.getMag(p.getName(), currentPath, document, null, settings, 
									magConfig.getPublicFlag(), magConfig.getDressFlag(), documentFormat);
							if(Mag.METS.equals(magObj.getDocumentFormat())) {
								getMag.createMetsFile(magObj, currentPath);
							}

							// mantieni origine del file come METS (condizione solo per editor)
							if(Mag.METS.equals(documentFormatDraft) && Mag.MAG.equals(magObj.getDocumentFormat()))
								magObj.setDocumentFormat(documentFormatDraft);

							Mag magSolr = getMag(currentPath, magObj);
							if(magSolr == null || (magSolr != null && (magSolr.isDeleted() || settings.getOverwrite() == 1))) {
								if(magSolr != null) {
									// mag cancellato, cancella eventualmente anche la directory dal backup
									if(magSolr.isDeleted()) {
										String backupRootDir = ContentStatic.getProperties().getProperty("pathMagCancellati");
										File backupMagDir = new File(backupRootDir, magSolr.getIdMag());
										
										if(backupMagDir.exists())
											try {
												FileUtils.deleteDirectory(backupMagDir);
												
											} catch (IOException e) {
												logger.error(e.getMessage(), e);
											}
									}
	
									// cancella risorse digitali dal MAG precedente
									else {
										if(!isDraft)
											deleteResources(magObj, magSolr);
									}
									
									if(magSolr.isDeleted())
										magInfo.setStatusMessage("Importazione");
									
									else {
										magInfo.setStatusMessage(settings.getUpdate() == 0 ? 
												"Già importato, sovrascrittura" : "Già importato, aggiornamento");
									}
								}
								
								else
									magInfo.setStatusMessage("Importazione");
								
								magInfoQueue.add(magInfo);
								boolean stored = magStorage.addMag(magObj);
								
								if(!stored) {
									this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), "Spazio non sufficiente su disco");
									deleteSlotXML(directoryReportSlot);
									return;
								}
								
								indexedMagProject++;
							}
							
							else if(magSolr != null && !magSolr.isDeleted() && settings.getOverwrite() == 0)
								magInfo.setStatusMessage("Già importato, salta re-importazione");
						}
						
						if(!settings.getIndexMags())
							magInfo.setStatusMessage("Validazione");
						
						if(MagReport.KO.equals(magInfo.getResult()))
							magInfo.setStatusMessage("Non validato");
						
						try {
							DBImportMag magDB = importMagDao.getMagByJobPath(jobID, magInfo.getPath());
							magDB.setResult(magInfo.getResult());
							magDB.setValidationTime(new BigInteger(cal.getTimeInMillis() + ""));
							magDB.setStatusMessage(magInfo.getStatusMessage());
							
							StringBuilder warnings = new StringBuilder();
							StringBuilder errors = new StringBuilder();
							StringBuilder fatalErrors = new StringBuilder();
							
							for(ErrorReport error : magInfo.getErrors()) {
								if(ValidationError.WARNING.equals(error.getType())) {
									if(warnings.length() > 0)
										warnings.append('\n');
									
									warnings.append(error.getMessage());
								}
								
								else if(ValidationError.ERROR.equals(error.getType())) {
									if(errors.length() > 0)
										errors.append('\n');
									
									errors.append(error.getMessage());
								}
								
								else if(ValidationError.FATAL_ERROR.equals(error.getType())) {
									if(fatalErrors.length() > 0)
										fatalErrors.append('\n');
									
									fatalErrors.append(error.getMessage());
								}
							}
							
							if(warnings.length() > 0)
								magDB.setWarnings(warnings.toString());
		
							if(errors.length() > 0)
								magDB.setErrors(errors.toString());
							
							if(fatalErrors.length() > 0)
								magDB.setFatalErrors(fatalErrors.toString());
							
							importMagDao.update(magDB);
							
						} catch (DaoException e) {
							logger.error(e.getMessage(), e);
						}
						
						if(report.getValidation().getMagReports().size() == batchImportSize) {
							if(settings.getIndexMags()) {
								// scrittura dei files di report
								this.logSimpleMessageReport("Indicizzazione di " + magInfoQueue.size() + " MAG in corso", 
										report, settings);
								
								try {
									boolean stored = magStorage.importMags();
		
									if(!stored) {
										this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), "Spazio non sufficiente su disco");
										deleteSlotXML(directoryReportSlot);
										return;
									}
									
								} catch (SolrServerException e) {
									logger.error(e.getMessage(), e);
									this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), e.getMessage());
									deleteSlotXML(directoryReportSlot);
									return;
								}
								
								
								// scrittura dei files di report
								this.logIndexedMagMessageReport("Indicizzazione di " + magInfoQueue.size() + " MAG completata", 
										magInfoQueue.size(), report, settings);
							}
							
							this.logClearSlotBatchReport(magInfoQueue, directoryReportSlot, report, settings);
						}
						
						// scrittura dei files di report
						this.logSimpleMessageReport("MAG " + LabelMapper.getFilename(currentPath) + " - "  + magInfo.getResult(),
								report, settings);
					}
				}
	
				// indicizza se ci sono documenti figli
				if(report.getValidation().getMagReports().size() > 0 && externalReferences.size() > 0) {
					if(settings.getIndexMags()) {
						// scrittura dei files di report
						this.logSimpleMessageReport("Indicizzazione di " + magInfoQueue.size() + " MAG in corso", 
								report, settings);
						
						try {
							boolean stored = magStorage.importMags();
	
							if(!stored) {
								this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), "Spazio non sufficiente su disco");
								deleteSlotXML(directoryReportSlot);
								return;
							}
							
						} catch (SolrServerException e) {
							logger.error(e.getMessage(), e);
							this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), e.getMessage());
							deleteSlotXML(directoryReportSlot);
							return;
						}
						
						
						// scrittura dei files di report
						this.logIndexedMagMessageReport("Indicizzazione di " + magInfoQueue.size() + " MAG completata", 
								magInfoQueue.size(), report, settings);
					}
					
					this.logClearSlotBatchReport(magInfoQueue, directoryReportSlot, report, settings);
				}
				
				// per ogni MAG in attesa di validazione a causa di files esterni
				while(!externalReferences.isEmpty()) {
					// path del MAG in attesa
					WaitingMagRecovery recovery = externalReferences.remove(0);
					ImportMagConfiguration magConfig = recovery.getImportConfiguration();
					String waitingMag = recovery.getWaitingMag();
					
					this.logSimpleMessageReport("Validazione in corso per riferimenti esterni MAG " + waitingMag, 
							report, settings);
		
					// oggetto report MAG XML
					MagReport magInfo = recovery.getMagReportObject();
					ExternalValidator extValidator = new ExternalValidatorImpl();
					
					// per ogni riferimento esterno
					for(ExternalMagReference ref : recovery.getReferences()) {
						List<Document> candidates = new ArrayList<Document>();
						String externalIdentifier = ref.getIdentifiers().get(0);
						
						List<Mag> candidateObjects = UtilSolr.selectDocumentByIdentifier(externalIdentifier, 
								ref.getIssue(), ref.getYear());
							
						for(Mag obj : candidateObjects) {
							ref.setExternalPath(obj.getPath());
							Document candidateDoc = UtilXML.convertStringToDocumentXML(obj.getMagProject());
							
							if(candidateDoc != null)
								candidates.add(candidateDoc);
						}
						
						ValidationResult result = extValidator.validateExternal(waitingMag, ref, 
								candidates, magConfig.getConfiguration());
					
						// per ogni errore di validazione rilevato
						for(ValidationError err : result.getErrors()) {
							ErrorReport msg = new ErrorReport();
							msg.setType(err.getStatus());
							msg.setMessage(err.getMessage());
							magInfo.getErrors().add(msg);
							
							// scrittura su log dell'errore
							logger.info(err.getStatus() + " - " + err.getMessage());
						}
					}
					
					magInfo.setResult(magInfo.getErrors().isEmpty() ? MagReport.OK : MagReport.KO);
					this.logAfterValidation(magInfo, checkErrors(magInfo.getErrors()), report, settings, cal, formatter);
					processedMagProject++;
					
					if(settings.getIndexMags() && MagReport.OK.equals(magInfo.getResult())) {
						Document waitingDoc = Mag.MAG.equals(documentFormat) ? 
								getMag.openMag(waitingMag) : getMag.openMetsAsMag(waitingMag, conf);
						
						Mag magObj = getMag.getMag(p.getName(), waitingMag, waitingDoc, 
								recovery.getReferences(), settings, magConfig.getPublicFlag(), 
								magConfig.getDressFlag(), documentFormat);
						if(Mag.METS.equals(documentFormat)) {
							getMag.createMetsFile(magObj, waitingMag);
						}

						if(Utility.isMagFromEditor(waitingMag)) {
							Mag magDraft = UtilSolr.selectDocumentById(Utility.getDraftIDFromFileName(waitingMag));
							
							// mantieni origine del file come METS (condizione solo per editor)
							if(Mag.METS.equals(magDraft.getDocumentFormat()) && Mag.MAG.equals(magObj.getDocumentFormat()))
								magObj.setDocumentFormat(magDraft.getDocumentFormat());
						}
						
						Mag magSolr = getMag(waitingMag,magObj);
						
						if(magSolr == null || (magSolr != null && (magSolr.isDeleted() || settings.getOverwrite() == 1))) {
							if(magSolr != null) {
								// mag cancellato, cancella evenutalmente anche la directory dal backup
								if(magSolr.isDeleted()) {
									String backupRootDir = ContentStatic.getProperties().getProperty("pathMagCancellati");
									File backupMagDir = new File(backupRootDir, magSolr.getIdMag());
									
									if(backupMagDir.exists())
										try {
											FileUtils.deleteDirectory(backupMagDir);
											
										} catch (IOException e) {
											logger.error(e.getMessage(), e);
										}
								}
	
								// cancella risorse digitali dal MAG precedente 
								else {
									if(!vfsFileSystem.isDraft(magInfo.getPath()) && !Utility.isMagFromEditor(magInfo.getPath())) {
										deleteResources(magObj, magSolr);
									}
								}
								
								if(magSolr.isDeleted())
									magInfo.setStatusMessage("Importazione");
								
								else {
									magInfo.setStatusMessage(settings.getUpdate() == 0 ? 
											"Già importato, sovrascrittura" : "Già importato, aggiornamento");
								}
							}
							
							else
								magInfo.setStatusMessage("Importazione");
							
							magInfoQueue.add(magInfo);
							boolean stored = magStorage.addMag(magObj);
							
							if(!stored) {
								this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), "Spazio non sufficiente su disco");
								deleteSlotXML(directoryReportSlot);
								return;
							}
							
							indexedMagProject++;
						}
						
						else if(magSolr != null && !magSolr.isDeleted() && settings.getOverwrite() == 0)
							magInfo.setStatusMessage("Già importato, salta re-importazione");
					}
		
					if(!settings.getIndexMags())
						magInfo.setStatusMessage("Validazione");
		
					if(MagReport.KO.equals(magInfo.getResult()))
						magInfo.setStatusMessage("Non validato");
					
					// se è una bozza cancella
					if((vfsFileSystem.isDraft(waitingMag) || Utility.isMagFromEditor(waitingMag))
							&& MagReport.OK.equals(magInfo.getResult())
					) {
						vfsFileSystem.getVfsService().deleteDraftFile(waitingMag);
						//Utility.deleteFile(waitingMag);
					}
		
					try {
						DBImportMag magDB = importMagDao.getMagByJobPath(jobID, magInfo.getPath());
						magDB.setResult(magInfo.getResult());
						magDB.setValidationTime(new BigInteger(cal.getTimeInMillis() + ""));
						magDB.setStatusMessage(magInfo.getStatusMessage());
						
						StringBuilder warnings = new StringBuilder();
						StringBuilder errors = new StringBuilder();
						StringBuilder fatalErrors = new StringBuilder();
						
						for(ErrorReport error : magInfo.getErrors()) {
							if(ValidationError.WARNING.equals(error.getType())) {
								if(warnings.length() > 0)
									warnings.append('\n');
								
								warnings.append(error.getMessage());
							}
							
							else if(ValidationError.ERROR.equals(error.getType())) {
								if(errors.length() > 0)
									errors.append('\n');
								
								errors.append(error.getMessage());
							}
							
							else if(ValidationError.FATAL_ERROR.equals(error.getType())) {
								if(fatalErrors.length() > 0)
									fatalErrors.append('\n');
								
								fatalErrors.append(error.getMessage());
							}
						}
						
						if(warnings.length() > 0)
							magDB.setWarnings(warnings.toString());
		
						if(errors.length() > 0)
							magDB.setErrors(errors.toString());
						
						if(fatalErrors.length() > 0)
							magDB.setFatalErrors(fatalErrors.toString());
						
						importMagDao.update(magDB);
						
					} catch (DaoException e) {
						logger.error(e.getMessage(), e);
					}
					
					if(report.getValidation().getMagReports().size() == batchImportSize) {
						if(settings.getIndexMags()) {
							// scrittura dei files di report
	
							this.logSimpleMessageReport("Indicizzazione di " + magInfoQueue.size() + " MAG in corso", 
									report, settings);
							
							try {
								boolean stored = magStorage.importMags();
		
								if(!stored) {
									this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), "Spazio non sufficiente su disco");
									deleteSlotXML(directoryReportSlot);
									return;
								}
								
							} catch (SolrServerException e) {
								logger.error(e.getMessage(), e);
								this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), e.getMessage());
								deleteSlotXML(directoryReportSlot);
								return;
							}
							
							this.logIndexedMagMessageReport("Indicizzazione di " + magInfoQueue.size() + " MAG completata", 
									magInfoQueue.size(), report, settings);
						}
						
						this.logClearSlotBatchReport(magInfoQueue, directoryReportSlot, report, settings);
					}
		
					this.logSimpleMessageReport("MAG " + waitingMag + " - "  + magInfo.getResult(), 
							report, settings);
				}
		
				if(report.getValidation().getMagReports().size()  > 0) {
					if(settings.getIndexMags()) {
						this.logSimpleMessageReport("Indicizzazione di " + magInfoQueue.size() + " MAG in corso", 
								report, settings);
						
						try {
							boolean stored = magStorage.importMags();
		
							if(!stored) {
								this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), "Spazio non sufficiente su disco");
								deleteSlotXML(directoryReportSlot);
								return;
							}
							
						} catch (SolrServerException e) {
							logger.error(e.getMessage(), e);
							this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), e.getMessage());
							deleteSlotXML(directoryReportSlot);
							return;
						}
						
						this.logIndexedMagMessageReport("Indicizzazione di " + magInfoQueue.size() + " MAG completata", 
								magInfoQueue.size(), report, settings);
					}
					
					this.logClearSlotBatchReport(magInfoQueue, directoryReportSlot, report, settings);
				}
				
				DBProject projectDB = null;
				DBProjectHistory projectJobDB = null;
	
				try {
					projectDB = projectDao.getProjectByName(p.getName());
					projectJobDB = projectHistoryDao.getProjectHistoryByNameAndJob(p.getName(), jobID);
					
					if(projectDB == null)
						throw new DaoException("Progetto " + p.getName() + " non presente nel database");
	
					if(projectJobDB == null)
						throw new DaoException("Progetto " + p.getName() + " non presente nel database o non appartenente al job " + jobID);
					
				} catch (DaoException e) {
					logger.error(e.getMessage(), e);
				}
				
				if(projectDB != null && projectJobDB != null) {
					if(settings.getIndexMags())
						projectDB.setStatus(p.isAll() ? 2 : 1);
					
					projectDB.setLastModified(projectJobDB.getTimestampOperation());
					projectDB.setLastOperation(projectJobDB.getOperationType());
					
					projectJobDB.setProject(projectDB);
					projectJobDB.setStatus(0);
					projectJobDB.setTimestampOperation(new BigInteger(System.currentTimeMillis() + ""));
					projectJobDB.setProcessedMag(processedMagProject);
					projectJobDB.setIndexedMag(indexedMagProject);
					
					try {
						projectDao.update(projectDB);
						projectHistoryDao.update(projectJobDB);
						
					} catch (DaoException e) {
						logger.error(e.getMessage(), e);
					}
				}
				
			} catch(Exception e1) {
				logger.error(e1.getMessage(), e1);
				this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), e1.getMessage());
				deleteSlotXML(directoryReportSlot);
				return;
			} catch(OutOfMemoryError e1) {
				p.getMags().clear();
				projects.clear();
				logger.error(e1.getMessage(), e1);
				
				String message = "Errore critico, impossibile indicizzare altri MAG. "
						+ "Provare a cambiare i parametri di configurazione, diminuendo "
						+ "il valore del parametro 'Indicizzazioni dopo N elaborazioni', "
						+ "oppure contattando l'assistenza tecnica";
				
				this.shutdown(formatter, magInfoQueue, settings.getIndexMags(), message);
				deleteSlotXML(directoryReportSlot);
				return;
			}
		}
		
		// ora fine validazione
		cal.setTimeInMillis(System.currentTimeMillis());

		// scrittura report files
		report.getValidation().setEnd(formatter.format(cal.getTime()));
		
		String msg = settings.getIndexMags() ?
			"Importazione terminata - "
					+ (report.getMagOK()- report.getMetsOK()) + " / " + (report.getNumMag()-report.getMetsKO()- report.getMetsOK()) + " MAG importati "+
					+ report.getMetsOK() + " / " + (report.getMetsOK()+ report.getMetsKO()) + " METS importati"
				:
			"Validazione terminata - "
					+ report.getMagOK() + " / " + report.getNumMag() + " MAG validi / "+
					+ report.getMetsOK() + " / " + (report.getMetsOK()+ report.getMetsKO()) + " METS validi";

		report.setStatusMessage(msg);
		
		synchronized (dataMap) {
			dataMap.put(jobID, Utility.copyImportSettings(settings));
		}
		
		logger.info(msg);
		
		// fine processo scrittura report xml
		cal.setTimeInMillis(System.currentTimeMillis());
		report.setEnd(formatter.format(cal.getTime()));
		report.setStatus(0);
		
		jobDB.setStatus(report.getStatus());
		jobDB.setMessage(report.getStatusMessage());
		
		synchronized (dataMap) {
			dataMap.put(jobID, Utility.copyImportSettings(settings));
		}
		
		logger.info("Processo " + jobID + " correttamente terminato");
		
		// modifica database
		jobDB.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
		deleteSlotXML(directoryReportSlot);
		updateDatabase(jobDB);
	}

	private void deleteResources(Mag mag, Mag magSolr) {
		if(!StringUtils.isEmpty(magSolr.getMagOriginal())) {
			Document docOld = UtilXML.convertStringToDocumentXML(magSolr.getMagOriginal());
			Document docNew = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
			List<String> oldIds = UtilXML.getResourceIDs(docOld);
			List<String> newIds = UtilXML.getResourceIDs(docNew);
			delivery.deleteTecaResourcesNotVirtual(magSolr.getIdMag(),
					oldIds.stream().filter(s -> !newIds.contains(s)).collect(Collectors.toList()));
			// delete all _cut del vecchio documento
			if(mag.getUsageE()==null || !mag.getUsageE().contains("5"))
				delivery.deleteTecaResourcesNotVirtual(magSolr.getIdMag(),
					oldIds.stream().map(resID-> resID+ AudioCutterComponent.SUFFIX).collect(Collectors.toList()));
			else
				delivery.deleteTecaResourcesNotVirtual(magSolr.getIdMag(),
						oldIds.stream().filter(s -> !newIds.contains(s))
								.map(resID-> resID+ AudioCutterComponent.SUFFIX)
								.collect(Collectors.toList()));
		}
	}

	private Mag getMag(String currentPath, Mag magObj) {
		Mag magSolr = UtilSolr.selectDocumentByPath(currentPath);
		if(magSolr==null)
			magSolr = UtilSolr.selectDocumentById(UtilSolr.createMagIdentifier(magObj));
		else {
			magSolr = UtilSolr.selectDocumentById(magSolr.getIdMag());
			UtilSolr.createMagIdentifier(magObj);// magSolr = UtilSolr.selectDocumentById(magSolr.getIdMag());
		}
		return magSolr;
	}

	// controlla l'esistenza di errori
	/**
	 * Verifica la presenza di errori (FATAL_ERROR, ERROR)
	 * 
	 * @param errors
	 * @return
	 */
	private boolean checkErrors(List<ErrorReport> errors) {
		for(ErrorReport msg : errors) {
			if(ValidationError.ERROR.equals(msg.getType()) || ValidationError.FATAL_ERROR.equals(msg.getType()))
				return true;
		}
		
		return false;
	}
	
	// controlla l'esistenza di errori fatali
	/**
	 * Verifica la presenza di errori di tipo FATAL_ERROR
	 * 
	 * @param valErrors
	 * @return
	 */
	private boolean checkBlock(List<ValidationError> valErrors) {
		for(ValidationError valErr : valErrors) {
			if(ValidationError.FATAL_ERROR.equals(valErr.getStatus()))
				return true;
		}
		
		return false;
	}

	// metodo di modifica oggetto database
	/**
	 * Aggiornamento database
	 * 
	 * @param job entità processo per la tabella teca_process
	 */
	private void updateDatabase(DBTecaProcess job) {
		try {
			processDao.update(job);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	// metodo di arresto per problemi di scrittura
	/**
	 * Aggiornamento dati in caso di anomalie
	 * 
	 * @param formatter
	 * @param magInfoQueue
	 * @param index
	 * @param message
	 */
	private void shutdown(SimpleDateFormat formatter, List<MagReport> magInfoQueue, boolean index, String message) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		int magOKQueue = 0;
		int magKOQueue = 0;

		int metsOKQueue = 0;
		int metsKOQueue = 0;

		// cancella i files digitali memorizzati dall'ultima coda e calcola i MAG validati
		for(MagReport mr : magInfoQueue) {
			if(MagReport.OK.equals(mr.getResult())) {
				if(Mag.METS.equals(mr.getDocumentFormat()))
					metsOKQueue++;
				magOKQueue++;
			}
			else {
				if(Mag.METS.equals(mr.getDocumentFormat()))
					metsKOQueue++;
				magKOQueue++;
			}
		}
		
		magInfoQueue.clear();

		// scrittura dei files di report
		synchronized(dataMap) {
			ImportReport report = dataMap.get(jobID).getReport();
			report.setMagOK(report.getMagOK() - magOKQueue);
			report.setMagKO(report.getMagKO() - magKOQueue);
			report.setMetsOK(report.getMetsOK() - metsOKQueue);
			report.setMetsKO(report.getMetsKO() - metsKOQueue);
			report.setProcessedMag(report.getProcessedMag() - (magOKQueue + magKOQueue));
			report.setStatus(-1);
			report.setStatusMessage(message);
			report.getValidation().setEnd(formatter.format(cal.getTime()));
			report.setEnd(formatter.format(cal.getTime()));
			// writeXmlReportFile(xmlFile, report);

			for(ImportMagProject p : projects) {
				DBProject projectDB = null;
				DBProjectHistory projectJobDB = null;
	
				try {
					projectDB = projectDao.getProjectByName(p.getName());
					projectJobDB = projectHistoryDao.getProjectHistoryByNameAndJob(p.getName(), jobID);
					
					if(projectDB == null)
						throw new DaoException("Progetto " + p.getName() + " non presente nel database");
	
					if(projectJobDB == null)
						throw new DaoException("Progetto " + p.getName() + " non presente nel database o non appartenente al job " + jobID);
					
				} catch (DaoException e) {
					logger.error(e.getMessage(), e);
				}
				
				if(projectDB != null && projectJobDB != null) {
					projectJobDB.setStatus(-1);
					projectJobDB.setTimestampOperation(new BigInteger(System.currentTimeMillis() + ""));
					projectJobDB.setProcessedMag(report.getProcessedMag());
					projectJobDB.setIndexedMag(report.getIndexedMag());
					
					projectDB.setLastModified(projectJobDB.getTimestampOperation());
					projectDB.setLastOperation(projectJobDB.getOperationType());
					
					try {
						projectHistoryDao.update(projectJobDB);
						projectDao.update(projectDB);
						
					} catch (DaoException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		
		// writeReportLog(logFile, formatter.format(cal.getTime()) +  ": " + msg);
		logger.info(message);
	}

	// metodo di scrittura report XML
	/**
	 * Scrittura report parziale
	 * 
	 * @param directory directory dei report parziali del job
	 * @param report riepilogo del job
	 */
	private void writeSlotXML(String directory, ImportReport report) {
		// crea directory report slot
		File dir = new File(directory);
		
		if(!dir.exists())
			dir.mkdirs();
		
		// creazione file
		int numSlot = dir.list().length + 1;
		File slotXML = new File(dir, "slot_" + numSlot + ".xml");
		FileWriter writer = null;
		XMLStreamWriter xmlWriter = null;
		
		try {
			// scrittura file
			writer = new FileWriter(slotXML, Charset.forName("UTF-8"));
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
				
				
				for(MagReport reportMag : report.getValidation().getMagReports()) {
					xmlWriter.writeCharacters("\n        ");
					xmlWriter.writeStartElement("mag-validation");
					xmlWriter.writeAttribute("timestamp", reportMag.getTimestamp());
					xmlWriter.writeAttribute("result", reportMag.getResult());
					xmlWriter.writeAttribute("file", reportMag.getPath());
					xmlWriter.writeAttribute("status", reportMag.getStatusMessage());
					
					for(ErrorReport error : reportMag.getErrors()) {
						if(ValidationError.FATAL_ERROR.equals(error.getType()) || ValidationError.ERROR.equals(error.getType()) || ValidationError.WARNING.equals(error.getType())) {
							xmlWriter.writeCharacters("\n            ");
							xmlWriter.writeStartElement("message");
							xmlWriter.writeAttribute("type", error.getType());
							if(error.getMessage()!=null)
								xmlWriter.writeCharacters(error.getMessage());
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
		} catch (IOException e) {
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
					writer.close();
					
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	// metodo di cancellazione report slot
	/**
	 * Cancella i report parziali dalla directory dei report del job
	 * 
	 * @param directory directory dei report parziali del job
	 */
	private void deleteSlotXML(String directory) {
		File dir = new File(directory);
		
		if(dir.listFiles() != null) {
			for(File slotXML : dir.listFiles())
				slotXML.delete();
		}
		
		if(dir.exists())
			dir.delete();
	}
	
	/**
	 * Imposta un messaggio di stato sul report
	 * 
	 * @param message messaggio
	 * @param report oggetto report
	 * @param settings oggetto impostazioni
	 */
	private void logSimpleMessageReport(String message, ImportReport report, ImportSettings settings) {
		logger.info(message);
		report.setStatusMessage(message);
		
		// validazione in corso mag specifico
		synchronized(dataMap) {
			dataMap.put(jobID, Utility.copyImportSettings(settings));
		}
	}

	/**
	 * Aggiorna il numero di MAG indicizzati sul report dopo il batch di importazione
	 * 
	 * @param message messaggio
	 * @param indexedMags numero di MAG indicizzati dopo il batch
	 * @param report oggetto report
	 * @param settings oggetto impostazioni
	 */
	private void logIndexedMagMessageReport(String message, int indexedMags, ImportReport report, ImportSettings settings) {
		logger.info("Indicizzazione di " + indexedMags + " MAG completata");
		report.setIndexedMag(report.getIndexedMag() + indexedMags);
		report.setStatusMessage("Indicizzazione di " + indexedMags + " MAG completata");
		
		synchronized(dataMap) {
			dataMap.put(jobID, Utility.copyImportSettings(settings));
		}
	}
	
	/**
	 * Salva repor XML dello slot di importazione e aggiorna oggetto report
	 * 
	 * @param magInfoQueue coda dei MAG del batch di importazione
	 * @param directoryReportSlot directory per la memorizzazione dei report XML degli slot
	 * @param report oggetto report 
	 * @param settings oggetto impostazioni
	 */
	private void logClearSlotBatchReport(List<MagReport> magInfoQueue, String directoryReportSlot, 
			ImportReport report, ImportSettings settings) {
		
		writeSlotXML(directoryReportSlot, report);
		report.getValidation().getMagReports().clear();
		
		synchronized(dataMap) {
			dataMap.put(jobID, Utility.copyImportSettings(settings));
		}
		
		magInfoQueue.clear();
	}
	
	/**
	 * Aggiorna risultato validazione su oggetto report
	 * 
	 * @param magInfo ingo MAG sottopososto a validazione
	 * @param report oggetto report
	 * @param settings oggetto impostazioni
	 * @param cal oggetto calendario
	 * @param formatter formattatore data
	 */
	private void logAfterValidation(MagReport magInfo, boolean errors, ImportReport report, 
			ImportSettings settings, Calendar cal, SimpleDateFormat formatter) {
		
		magInfo.setResult(errors ? MagReport.KO : MagReport.OK);
		cal.setTimeInMillis(System.currentTimeMillis());
		magInfo.setTimestamp(formatter.format(cal.getTime()));
		report.setProcessedMag(report.getProcessedMag() + 1);
		
		if (MagReport.KO.equals(magInfo.getResult()))
			report.setMagKO(report.getMagKO() + 1);
		else
			report.setMagOK(report.getMagOK() + 1);
		if(Mag.METS.equals(magInfo.getDocumentFormat())){
			if (MagReport.KO.equals(magInfo.getResult()))
				report.setMetsKO(report.getMetsKO() + 1);
			else
				report.setMetsOK(report.getMetsOK() + 1);
		}
		
		report.getValidation().getMagReports().add(magInfo);
		
		synchronized (dataMap) {
			dataMap.put(jobID, Utility.copyImportSettings(settings));
		}
	}

	public void setVfsFileSystem(VfsFileSystem vfsFileSystem) {
		this.vfsFileSystem = vfsFileSystem;
	}
}
