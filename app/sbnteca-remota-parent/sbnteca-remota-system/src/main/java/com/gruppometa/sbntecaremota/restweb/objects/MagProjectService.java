package com.gruppometa.sbntecaremota.restweb.objects;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.gruppometa.sbntecaremota.objects.db.DBImportMagDao;
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
import com.gruppometa.sbntecaremota.objects.json.JsonProjectFile;
import com.gruppometa.sbntecaremota.objects.json.JsonProjectHistory;
import com.gruppometa.sbntecaremota.objects.json.JsonProjectHistoryResult;
import com.gruppometa.sbntecaremota.objects.json.JsonResourceInfo;
import com.gruppometa.sbntecaremota.objects.json.JsonUploadReport;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceTypes;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

public class MagProjectService {
	
	// dao progetto
	@Autowired
	private DBProjectDao projectDao;

	@Autowired
	private VfsFileSystem vfsFileSystem;

	// dao utenti
	@Autowired
	private DBTecaUserDao userDao;

	// dao teca process
	@Autowired
	private DBTecaProcessDao tecaProcessDao;

	// dao storia progetto dao
	@Autowired
	private DBProjectHistoryDao projectHistoryDao;

	// dao mag importati
	@Autowired
	private DBImportMagDao importMagDao;

	// delivery
	@Autowired
	private MagResourceDelivery delivery;

	@Autowired
	private MagImportService magImportService;

	// logger
	private static Logger logger = LoggerFactory.getLogger(MagProjectService.class);
	
	
	
	/**
	 * Restituisce la lista dei progetti caricati nel sistema
	 * 
	 * LETTURA PROGETTI DISPONIBILI (IMPORT/IMPORT UPDATE/CAMBIO USAGE/SELEZIONE PER CREAZIONE BOZZA)
	 * 
	 * @param filter filto da applicare (all, new, import)
	 * @return List<String> lista dei progetti
	 */
	public List<String> loadProjects(String filter, boolean vfsProject) {
		List<String> queryResults = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		final String baseFolder = ContentStatic.getProperties().getProperty("resourceDIRO");
		
		// query
		try {
			if("all".equals(filter))
				queryResults.addAll(projectDao.getAllProjects());
			
			else if("new".equals(filter))
				queryResults.addAll(projectDao.getNewProjects());
		
			else if("import".equals(filter))
				queryResults.addAll(projectDao.getImportedProjects());

			if(vfsProject) {
				queryResults.addAll(vfsFileSystem.getVfsService().getDirectories(null).stream().map(
						f -> f.getId()
				).distinct().collect(Collectors.toList()));
			}
		} catch(DaoException e) {
			logger.error(e.getMessage(), e);
		}
		
		// controllo effettiva esistenza del progetto
		for(String queryRes : queryResults) {
			if(new File(baseFolder, queryRes).exists() || vfsProject)
				result.add(queryRes);
		}

		result = result.stream().distinct().collect(Collectors.toList());

		// ordinamento per ultima operazione
	    Collections.sort(result, new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				return s1.toLowerCase().compareTo(s2.toLowerCase());
			}
		});
	    
	    return result;
	}
	
	/**
	 * Restituisce la lista dei MAG (o METS) contenuta nella directory del progetto specificato
	 * 
	 * LETTURA MAG CARICATI DA UPLOAD, CON SELEZIONE TUTTI O RECENTI (IMPORT/IMPORT UPDATE/CAMBO USAGE)
	 * RECENTI = MAG NON IMPORTATI OPPURE MAG IMPORTATI MA AGGIORNATI DA UPLOAD SUCCESSIVO
	 * 
	 * @param projectDir nome della directory del progetto
	 * @param recent true se devono essere cercati solo i MAG più recenti
	 * @return List<String> lista dei path relativi dei MAG
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public List<String> getMagsByProjectDirectory(String projectDir, boolean recent) {
		return delivery.getDocs(projectDir, recent);
	}
	
	/**
	 * Restituisce la lista dei files appartenenti al progetto specificato
	 * 
	 * PICKER EDITOR
	 * 
	 * @param project progetto
	 * @param relativePath path relativo (ricerca solo i figli)
	 * @param typeFilter filtro per il tipo di file (img, audio, video, ocr, doc)
	 * @param prefix filtra per prefisso nome del file
	 * @param depth numero di livelli di profondità da mostrare
	 * @return List<JsonProjectFile> lista dei files
	 */
	public List<JsonProjectFile> getProjectFiles(String project, String relativePath, 
			String typeFilter, String prefix, int depth) {
		
		List<JsonProjectFile> results = new ArrayList<JsonProjectFile>();
		File projectFile = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), project);
		
		if(!projectFile.exists())
			return results;
		
		if(!relativePath.isEmpty())
			projectFile = new File(projectFile, relativePath);
		
		if(JsonProjectFile.TYPE_OCR.equals(typeFilter))
			typeFilter = JsonProjectFile.TYPE_DOC;
		
		this.populateJson(results, projectFile.listFiles(), typeFilter, prefix, 1, depth);
		// JsonProjectFile.sort(results);
		return results;
	}

	public List<JsonProjectFile> getProjectFilesVirtualeOld(String project, String relativePath,
												 String typeFilter, String prefix, int depth) {

		List<JsonProjectFile> results = new ArrayList<JsonProjectFile>();
		VfsFile vfsFileProject = vfsFileSystem.getVfsService().getVfsFileById(project, VfsFile.TYPE_DIRECTORY);

		if(vfsFileProject==null)
			return results;

		if(!relativePath.isEmpty())
			vfsFileProject = vfsFileSystem.getVfsService().getContainerById(relativePath);
		if(!relativePath.isEmpty() && vfsFileProject==null)
			vfsFileProject = vfsFileSystem.getVfsService().getContainerByDirectoryAndFilename(project, relativePath);

		if(JsonProjectFile.TYPE_OCR.equals(typeFilter))
			typeFilter = JsonProjectFile.TYPE_DOC;

		this.populateJsonVirtuale(project, results, vfsFileSystem.getVfsService().getChildren(vfsFileProject), typeFilter, prefix, 1, depth);
		// JsonProjectFile.sort(results);
		return results;
	}
	public List<JsonProjectFile> getProjectFilesVirtuale(String project, String relativePath,
															String typeFilter, String prefix, int depth) throws SolrServerException {

		List<JsonProjectFile> results = new ArrayList<JsonProjectFile>();
		String idContainer = UtilSolr.getPathFromMagId(project);
		idContainer = UtilSolr.getMagIDFromVfsPath(idContainer);
		VfsFile vfsFileContainer = vfsFileSystem.getVfsService().getVfsFileById(idContainer, VfsFile.TYPE_CONTAINER);

		if(vfsFileContainer==null)
			return results;


		if(JsonProjectFile.TYPE_OCR.equals(typeFilter))
			typeFilter = JsonProjectFile.TYPE_DOC;

		this.populateJsonVirtuale(project, results, vfsFileSystem.getVfsService().getChildren(vfsFileContainer), typeFilter, prefix, 1, depth);
		// JsonProjectFile.sort(results);
		return results;
	}
	/**
	 * Esegue la ricerca nel file system con il popolamento del JSON con i files del progetto
	 * 
	 * @param fileList lista json da costruire
	 * @param files files da leggere
	 * @param relativePath path relativo
	 * @param typeFilter filtro per il tipo di file (img, audio, video, ocr, doc)
	 * @param prefix filtra per prefisso nome del file
	 * @param currentDepth livello corrente di profondità
	 * @param depth numero di livelli di profondità da mostrare
	 */
	private void populateJson(List<JsonProjectFile> fileList, File[] files, String typeFilter, 
			String prefix, int currentDepth, int depth) {
		
		if(files==null)
			return;
		for(File f : files) {
			JsonProjectFile jpf = new JsonProjectFile();
			boolean mag = false;
			
			if(f.isDirectory()) {
				jpf.setName(f.getName());
				jpf.setType(JsonProjectFile.TYPE_DIRECTORY);

				if((currentDepth < depth || depth <= 0 ) && f.listFiles()!=null && f.listFiles().length>0){
					this.populateJson(jpf.getChildren(), f.listFiles(), typeFilter, prefix, currentDepth + 1, depth);
				}
				if(!jpf.getChildren().isEmpty() || typeFilter == null || typeFilter.isEmpty() || currentDepth==depth)
					fileList.add(jpf);
				/*
				// ricerca sottodirectory se path relativo non specificato
				if(currentDepth < depth || depth <= 0 || (typeFilter != null && !typeFilter.isEmpty())) {
					this.populateJson(jpf.getChildren(), f.listFiles(), typeFilter, prefix, currentDepth + 1, depth);
					
					if(typeFilter == null || typeFilter.isEmpty() || (!jpf.getChildren().isEmpty() && typeFilter != null && !typeFilter.isEmpty()))
						fileList.add(jpf);
				}
				*/
			}
			
			else {
				try {
					String mime = Utility.getMime(f);
					
					if(mime.contains("xml"))
						mag = Utility.isMag(f);
					
					if(mime.startsWith("image"))
						jpf.setType(JsonProjectFile.TYPE_IMG);
					
					else if(mime.startsWith("audio"))
						jpf.setType(JsonProjectFile.TYPE_AUDIO);
					
					else if(mime.startsWith("video"))
						jpf.setType(JsonProjectFile.TYPE_VIDEO);
					
					else
						jpf.setType(JsonProjectFile.TYPE_DOC);
					
					
					// filtri per le risorse digitali
					if(!"Unknown".equals(jpf.getType()) && !mag && 
							(prefix == null || prefix.isEmpty() || f.getName().startsWith(prefix)) &&
							(typeFilter == null || typeFilter.isEmpty() || typeFilter.equals(jpf.getType()))) {
						
						String deliveryID = delivery.findIDByPath(f.getCanonicalPath());
						
						if(deliveryID != null) {
							jpf.setId(deliveryID);
							jpf.setLabel(FilenameUtils.removeExtension(f.getName()));
							jpf.setName("digitalObject/" + deliveryID + "/original?mode=preview");
							fileList.add(jpf);
						}
					}
					
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					jpf.setType("Unknown");
				}
			}
		}

		// aggiungi la directory se la directory possiede files e il filtro è specificato
		if(!fileList.isEmpty()) {
			Collections.sort(fileList, new Comparator<JsonProjectFile>() {

				@Override
				public int compare(JsonProjectFile jpf1, JsonProjectFile jpf2) {
					boolean jpfisDir1 = JsonProjectFile.TYPE_DIRECTORY.equals(jpf1.getType());
					boolean jpfisDir2 = JsonProjectFile.TYPE_DIRECTORY.equals(jpf2.getType());
					
					if(jpfisDir1 && jpfisDir2)
						return jpf1.getName().toLowerCase().compareTo(jpf2.getName().toLowerCase());
					
					else if(!jpfisDir1 && !jpfisDir2)
						return jpf1.getLabel().toLowerCase().compareTo(jpf2.getLabel().toLowerCase());
					
					else if(jpfisDir1 && !jpfisDir2)
						return 1;
					
					else
						return -1;
				}
				
			});
		}
	}

	private void populateJsonVirtuale(String project, List<JsonProjectFile> fileList, List<VfsFile> files, String typeFilter,
							  String prefix, int currentDepth, int depth) {

		if(files==null)
			return;
		for(VfsFile f : files) {
			JsonProjectFile jpf = new JsonProjectFile();
			boolean mag = false;

			if(f.getVfsType().equals(VfsFile.TYPE_CONTAINER)) {
				jpf.setName(f.getLabel()!=null?f.getLabel():f.getFilename());
				if(jpf.getName()==null)
					jpf.setName(f.getId());
				jpf.setType(JsonProjectFile.TYPE_DIRECTORY);
				List<VfsFile> children = vfsFileSystem.getVfsService().getChildren(f);
				if((currentDepth < depth || depth <= 0 ) && children!=null && children.size()>0){
					this.populateJsonVirtuale(project, jpf.getChildren(), children, typeFilter, prefix, currentDepth + 1, depth);
				}
				if(!jpf.getChildren().isEmpty() || typeFilter == null || typeFilter.isEmpty() || currentDepth==depth)
					fileList.add(jpf);
				/*
				// ricerca sottodirectory se path relativo non specificato
				if(currentDepth < depth || depth <= 0 || (typeFilter != null && !typeFilter.isEmpty())) {
					this.populateJson(jpf.getChildren(), f.listFiles(), typeFilter, prefix, currentDepth + 1, depth);

					if(typeFilter == null || typeFilter.isEmpty() || (!jpf.getChildren().isEmpty() && typeFilter != null && !typeFilter.isEmpty()))
						fileList.add(jpf);
				}
				*/
			}

			else {
				try {
					String mime = f.getContentType();

					if(mime.contains("xml"))
						mag = "mag".equals(f.getResourceType());

					if(mime.startsWith("image"))
						jpf.setType(JsonProjectFile.TYPE_IMG);

					else if(mime.startsWith("audio"))
						jpf.setType(JsonProjectFile.TYPE_AUDIO);

					else if(mime.startsWith("video"))
						jpf.setType(JsonProjectFile.TYPE_VIDEO);

					else
						jpf.setType(JsonProjectFile.TYPE_DOC);


					// filtri per le risorse digitali
					if(!"Unknown".equals(jpf.getType()) && !mag &&
							(prefix == null || prefix.isEmpty() || f.getLabel().startsWith(prefix)) &&
							(typeFilter == null || typeFilter.isEmpty() || typeFilter.equals(jpf.getType()))) {

						String deliveryID = f.getId();

						if(deliveryID != null) {
							jpf.setId(deliveryID);
							jpf.setLabel(f.getLabel());
							jpf.setName("digitalObject/" + deliveryID + "/original?mode=preview");
							fileList.add(jpf);
						}
					}

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					jpf.setType("Unknown");
				}
			}
		}

		// aggiungi la directory se la directory possiede files e il filtro è specificato
		if(!fileList.isEmpty()) {
			Collections.sort(fileList, new Comparator<JsonProjectFile>() {

				@Override
				public int compare(JsonProjectFile jpf1, JsonProjectFile jpf2) {
					boolean jpfisDir1 = JsonProjectFile.TYPE_DIRECTORY.equals(jpf1.getType());
					boolean jpfisDir2 = JsonProjectFile.TYPE_DIRECTORY.equals(jpf2.getType());

					if(jpfisDir1 && jpfisDir2)
						return jpf1.getName().toLowerCase().compareTo(jpf2.getName().toLowerCase());

					else if(!jpfisDir1 && !jpfisDir2)
						return jpf1.getLabel().toLowerCase().compareTo(jpf2.getLabel().toLowerCase());

					else if(jpfisDir1 && !jpfisDir2)
						return 1;

					else
						return -1;
				}

			});
		}
	}
	/**
	 * Restituisce le informazioni calcolate sulla risorsa
	 * 
	 * COMPILAZIONE AUTOMATICA METADATI AMBITI D'USO EDITOR
	 * 
	 * @param relativePath path relativo della risorsa all'interno del progetto
	 * @return JsonResourceInfo info risorsa
	 */
	public JsonResourceInfo getResourceInfo(String relativePath) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		String deliveryID = relativePath.replace("/original", "").
				replace("digitalObject/", "");
		
		String path = delivery.findPathByID(deliveryID);
		
		if(path == null)
			return null;
		
		// risorsa originale
		File resource = new File(path);
		
		// risorsa non esistente
		if(!resource.exists())
			return null;
		
		try {
			// metadati
			JsonResourceInfo info = new JsonResourceInfo();
			info.setId(deliveryID);
			info.setMd5(Utility.getMD5Checksum(resource.getPath()));
			info.setFilesize(resource.length());
			info.setExtension(FilenameUtils.getExtension(resource.getPath()));
			info.setMime(Utility.getMime(resource));
			
			BasicFileAttributes attr = Files.readAttributes(resource.toPath(), BasicFileAttributes.class);
			info.setDatetimecreated(formatter.format(new Date(attr.creationTime().toMillis())));
			
			if(info.getMime().startsWith("image")) {
				BufferedImage img = ImageIO.read(resource);
				info.setWidth(img.getWidth());
				info.setHeight(img.getHeight());
			}
			
			else if(info.getMime().startsWith("audio") || info.getMime().startsWith("video")) {
				FFprobe ffprobe = new FFprobe(ContentStatic.getProperties().getProperty("ffmpegCommand"));
				FFmpegProbeResult probeResult = ffprobe.probe(resource.getPath());
				double totalSecs = probeResult.getFormat().duration;

				int hours = (int) Math.floor(totalSecs / 3600);
				int minutes = (int) Math.floor((totalSecs % 3600) / 60);
				int seconds = (int) (Math.floor(totalSecs) % 60);
				// int milliseconds = (int) (Math.round((totalSecs - Math.floor(totalSecs)) * 1000));
				String durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				/*
				if(milliseconds != 0) {
					durationString += "." + milliseconds;
					
					while(durationString.endsWith("0"))
						durationString = durationString.substring(0, durationString.length() - 1);
				}
				*/
				info.setDuration(durationString);

				if("mp3".equals(info.getExtension()) && "audio/mpeg".equals(info.getMime()))
					info.setMime("audio/mp3");
				
				else if(info.getMime().contains("msvideo"))
					info.setMime("video/avi");
				
				else if("video/x-ms-wmv".equals(info.getMime()))
					info.setMime("video/wmv");
				
				else if(info.getMime().startsWith("video/mp") || info.getMime().startsWith("application/mp"))
					info.setMime("video/mpeg");
			}
			
			return info;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * Restituisce il MAG XML dato il progetto ed il path relativo
	 * 
	 * LETTURA DOCUMENTO MAG XML DA FILE SYSTEM (IMPORT/IMPORT UPDATE/CAMBIO USAGE)
	 * 
	 * @param project nome progetto
	 * @param relativePath path relativo
	 * @return Document documento XML se trovato
	 * @throws FileNotFoundException
	 */
	public Document getMagXmlDocument(String project, String relativePath) throws FileNotFoundException {
		VfsFile vfsFile = vfsFileSystem.getVfsService().getContainerByDirectoryAndId(project, relativePath);
		if(vfsFile!=null){
			return UtilXML.openMag(vfsFile.getVfsPath(), MagPersistenceTypes.FILE);
		}
		File projectFile = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), project);
		
		if(!projectFile.exists())
			throw new FileNotFoundException("Progetto non trovato");
		
		File mag = new File(projectFile, relativePath);
		
		if(!mag.exists())
			throw new FileNotFoundException("MAG non trovato");
			
		return UtilXML.openMag(mag.getPath(), MagPersistenceTypes.FILE);
	}
	
	/**
	 * Restituisce la cronologia di operazioni associate al progetto
	 * 
	 * CRONOLOGIA PROGETTI
	 * 
	 * @param project progetto
	 * @return JsonProjectHistoryResult cronologia del progetto selezionato
	 */
	public JsonProjectHistoryResult getProjectHistory(String project) {
		JsonProjectHistoryResult result = new JsonProjectHistoryResult();
		result.setStatus(0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		try {
			// ricerca il progetto
			DBProject projectDB = projectDao.getProjectByName(project);
			
			if(projectDB == null)
				throw new DaoException("Progetto non presente sul database");
			
			// ordina per cronologia decrescente
			List<DBProjectHistory> historyDB = new ArrayList<DBProjectHistory>();
			historyDB.addAll(projectDB.getProjectHistories());
			Collections.sort(historyDB, new Comparator<DBProjectHistory>() {

				@Override
				public int compare(DBProjectHistory ph1, DBProjectHistory ph2) {
					return - Long.compare(ph1.getTimestampOperation().longValue(), ph2.getTimestampOperation().longValue());
				}
			});
			
			// genera JSON
			for(DBProjectHistory projectOperation : historyDB) {
				JsonProjectHistory jsonProjectOp = new JsonProjectHistory();
				jsonProjectOp.setProcessedMag(projectOperation.getProcessedMag());
				jsonProjectOp.setIndexedMag(projectOperation.getIndexedMag());
				jsonProjectOp.setOperationType(projectOperation.getOperationType().toString());
				jsonProjectOp.setTimestamp(formatter.format(new Date(projectOperation.getTimestampOperation().longValue())));
				jsonProjectOp.setJobID(projectOperation.getTecaProcess().getId());
				
				DBTecaUser user = projectOperation.getTecaProcess().getTecaUser();
				
				if(user == null)
					throw new DaoException("Utente non trovato");
				
				jsonProjectOp.setUsername(user.getUsername() + (user.isDeleted() ? " [Cancellato]" : ""));
				result.getHistory().add(jsonProjectOp);
			} 
			
		} catch (DaoException e) {
			result.setStatus(-1);
			result.setMessage("Problema di accesso al database: " + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * Creazione processo di upload
	 * 
	 * CREAZIONE PROCESSO UPLOAD
	 * 
	 * @param userID ID utente
	 * @param update true se in modalità aggiornamento di progetto, false se nuovo progetto
	 * @return ProcessReport oggetto di riepilogo con esito messaggio e ID processo creato
	 * @throws DaoException 
	 */
	public JsonUploadReport createUploadProcess(int userID, boolean update, String createContainer) throws DaoException {
		// imposta dati processo
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());

		// utente
		DBTecaUser user = userDao.getUserByID(userID);
		
		if(user == null)
			throw new DaoException("Utente ID " + userID + " non trovato");
		
		// crea processo
		DBTecaProcess process = new DBTecaProcess();
		process.setId("upl_" +
				(update ? "update_" : "")+
				(("on".equalsIgnoreCase(createContainer)||"true".equalsIgnoreCase(createContainer))?"CreateContainer_":"")
				+ formatter.format(cal.getTime()));
		process.setTecaUser(user);
		process.setStatus(1);
		process.setMessage("Processo creato");
		process.setTimestampStart(new BigInteger(cal.getTimeInMillis() + ""));
		
		try {
			tecaProcessDao.insert(process);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			JsonUploadReport report = new JsonUploadReport();
			report.setProcessID(process.getId());
			report.setStatus(-1);
	        report.setMessage(e.getMessage());
	        return report;
		}
		
		logger.info("Processo di upload " + process.getId() + " creato");
		
        // creazione json report
        JsonUploadReport report = new JsonUploadReport();
        report.setProcessID(process.getId());
        report.setStatus(process.getStatus());
        report.setMessage(process.getMessage());
        return report;
	}
	
	/**
	 * Esegue l'upload di aggiornamento lato client
	 * 
	 * ESECUZIONE PROCESSO UPLOAD DI AGGIORNAMENTO
	 * 
	 * @param processID ID del processo
	 * @param project nome del progetto
	 * @param fileName nome del file upload
	 * @param uploadStream stream di upload
	 * @return JsonUploadReport json di risposta
	 */
	public JsonUploadReport uploadUpdate(String processID, String project, 
			String fileName, InputStream uploadStream) {
		
		File uploadFile = null;
		JsonUploadReport report = new JsonUploadReport();
		
		try {
			uploadFile = this.copyUploadStream(fileName, uploadStream);
			report = this.uploadUpdate(processID, project, fileName, uploadFile);
			
		} catch (IOException e) {
			logger.error("Copia del file non riuscita (1) "+e.getMessage()+ " file: "+fileName);
			report.setProcessID(processID);
	        report.setStatus(-7);
	        report.setMessage("Copia del file non riuscita");
	        return report;
		} finally {
			if(uploadFile != null)
				uploadFile.delete();
		}
		
		return report;
	}

	public JsonUploadReport uploadResources(String processID, String project,
										 String fileName, InputStream uploadStream, boolean isUnimarc) {

		File uploadFile = null;
		JsonUploadReport report = new JsonUploadReport();

		try {
			uploadFile = this.copyUploadStream(fileName, uploadStream);
			report = this.uploadResources(processID, project, fileName, uploadFile, isUnimarc);

		} catch (IOException e) {
			logger.error("Copia del file non riuscita (1) "+e.getMessage()+ " file: "+fileName);
			report.setProcessID(processID);
			report.setStatus(-7);
			report.setMessage("Copia del file non riuscita");
			return report;
		} finally {
			if(uploadFile != null)
				uploadFile.delete();
		}

		return report;
	}
	/**
	 * Esegue l'upload di un progetto in modalità aggiornamento
	 * 
	 * @param processID ID processo di upload
	 * @param project nome del progetto
	 * @param fileName nome del file
	 * @param uploadFile file da caricare
	 * @return ProcessReport esito del processo di upload con stato e messaggio
	 */
	public JsonUploadReport uploadUpdate(String processID, String project, 
			String fileName, File uploadFile) {
		
		DBTecaProcess process = null;
		DBProject projectDB = null;
		
		DBProjectHistory historyDB = new DBProjectHistory();
		
	    // creazione json report
	    JsonUploadReport report = new JsonUploadReport();
	    
		try {
			process = tecaProcessDao.findByID(processID);
			projectDB = projectDao.getProjectByName(project);
			
			if(projectDB == null)
				throw new DaoException("Progetto non presente sul database");
			
			if(process == null) {
				logger.error("Processo " + processID + " non esistente");
				report.setProcessID(processID);
		        report.setStatus(-1);
		        report.setMessage("Processo non esistente");
		        return report;
			}
			
			// controlla esistenza del path
			File file = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), Utility.toValidFileName(project));
			
			if (!file.exists()) {
				logger.error("Progetto \"" + project + "\" non esistente");
				report.setProcessID(process.getId());
		        report.setStatus(-2);
		        report.setMessage("Progetto \"" + project + "\" non esistente");
		        return report;
			}
			
			if(process.getStatus() != 1) {
				logger.error("Processo di upload \"" + processID + "\" già avviato in precedenza");
				report.setProcessID(process.getId());
		        report.setStatus(-6);
		        report.setMessage("Processo di upload già avviato in precedenza");
		        return report;
			}
			
		    report.setProcessID(process.getId());
		
			// lettura dei bytes
		    process.setStatus(2);
			process.setMessage("Uploading");
			logger.info("Processo di upload " + process.getId() + ": uploading");
			tecaProcessDao.update(process);
		    
		    if (!file.exists())
				file.mkdirs();		
		
			int numFiles = 1;
			
			if(Utility.isCompressed(fileName))
				numFiles = Utility.validateArchive(uploadFile, fileName);
			
			else if(uploadFile.isDirectory())
				numFiles = Utility.validateDirectory(uploadFile);
			
			process.setStatus(3);
			process.setMessage("0%");
			tecaProcessDao.update(process);
			
			// crea cronologia progetto
			historyDB.setProject(projectDB);
			historyDB.setTecaProcess(process);
			historyDB.setOperationType(ProjectOperation.UPLOAD_UPDATE);
			historyDB.setProcessedMag(0);
			historyDB.setIndexedMag(0);
			
			Map<String, Integer> totals = new HashMap<String, Integer>();

			totals.put("mets", 0);
			if(Utility.isCompressed(fileName)) {
				logger.info("Processo di upload " + process.getId() + ": unzipping");
				totals.putAll(Utility.uncompress(uploadFile, fileName, file, process, tecaProcessDao, delivery, numFiles, false));
			}
			
			else if(uploadFile.isDirectory()) {
				logger.info("Processo di upload " + process.getId() + ": copy");
				totals.putAll(Utility.uncompressed(uploadFile, file, process, tecaProcessDao, delivery, numFiles, false));
			}
			
			else {
				logger.info("Processo di upload " + process.getId() + ": copy");
				int numMags = Utility.uncompressed(uploadFile, fileName, file, process, delivery, magImportService);
				
				if(numMags > 0) {
					totals.put("mags", 1);
					totals.put("objects", 0);
				}
				
				else {
					totals.put("mags", 0);
					totals.put("objects", 1);
				}
			}
			
			process.setMessage("100%");
			tecaProcessDao.update(process);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			process.setStatus(0);
			
			process.setMessage("Upload terminato" + (totals.get("mags") == 0 && totals.get("mets") == 0 ? 
					" - Oggetti digitali: " + totals.get("objects") : ""));
			
			process.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
			tecaProcessDao.update(process);
			logger.info("Processo di upload " + process.getId() + " terminato");

			// aggiorna progetto
			projectDB.setLastOperation(ProjectOperation.UPLOAD_UPDATE);
			projectDB.setLastModified(process.getTimestampEnd());
			projectDao.update(projectDB);
			
			historyDB.setTimestampOperation(process.getTimestampEnd());
			historyDB.setProcessedMag(totals.get("mags"));
			projectHistoryDao.insert(historyDB);
			
			report.setStatus(process.getStatus());
			report.setMessage(process.getMessage());
			
		} catch (IOException e) {
			logger.error("Processo di upload " + process.getId() + " terminato con errore: " + 
					e.getMessage(), e);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			process.setStatus(-3);
			process.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
			process.setMessage(e.getMessage());
			historyDB.setStatus(-3);
			historyDB.setTimestampOperation(new BigInteger(cal.getTimeInMillis() + ""));
			
			try {
				tecaProcessDao.update(process);
				projectHistoryDao.insert(historyDB);
				report.setStatus(process.getStatus());
				report.setMessage(process.getMessage());
				
			} catch (DaoException e1) {
				logger.error("Processo di upload " + process.getId() + " terminato con errore: " + e1.getMessage(), e1);
				report.setStatus(-5);
				report.setMessage(e1.getMessage());
			}	
			
		} catch (DaoException e) {
			logger.error("Processo di upload " + process.getId() + " terminato con errore: " + 
					e.getMessage(), e);
			
			report.setStatus(-5);
			report.setMessage(e.getMessage());
		} 
        
        return report;
	}

	public JsonUploadReport uploadResources(String processID, String project,
										 String fileName, File uploadFile, boolean isUnimarc ) {

		DBTecaProcess process = null;
		DBProject projectDB = null;

		DBProjectHistory historyDB = new DBProjectHistory();

		// creazione json report
		JsonUploadReport report = new JsonUploadReport();

		try {
			process = tecaProcessDao.findByID(processID);
//			projectDB = projectDao.getProjectByName(project);
//
//			if(projectDB == null)
//				throw new DaoException("Progetto non presente sul database");

			if(process == null) {
				logger.error("Processo " + processID + " non esistente");
				report.setProcessID(processID);
				report.setStatus(-1);
				report.setMessage("Processo non esistente");
				return report;
			}

			// controlla esistenza del path
			File file = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), Utility.toValidFileName(project));
			if(!file.exists())
				file.mkdirs();
			if (!file.exists()) {
				logger.error("Progetto \"" + project + "\" non esistente");
				report.setProcessID(process.getId());
				report.setStatus(-2);
				report.setMessage("Progetto \"" + project + "\" non esistente");
				return report;
			}

			if(process.getStatus() != 1) {
				logger.error("Processo di upload \"" + processID + "\" già avviato in precedenza");
				report.setProcessID(process.getId());
				report.setStatus(-6);
				report.setMessage("Processo di upload già avviato in precedenza");
				return report;
			}

			report.setProcessID(process.getId());

			// lettura dei bytes
			process.setStatus(2);
			process.setMessage("Uploading");
			logger.info("Processo di upload " + process.getId() + ": uploading");
			tecaProcessDao.update(process);

//			if (!file.exists())
//				file.mkdirs();

			int numFiles = 1;

			if(Utility.isCompressed(fileName))
				numFiles = Utility.validateArchive(uploadFile, fileName);
			else if(uploadFile.isDirectory())
				numFiles = Utility.validateDirectory(uploadFile);
			else if(isUnimarc)
				numFiles = 1;

			process.setStatus(3);
			process.setMessage("0%");
			tecaProcessDao.update(process);

			// crea cronologia progetto
			historyDB.setProject(projectDB);
			historyDB.setTecaProcess(process);
			historyDB.setOperationType(isUnimarc?ProjectOperation.UPLOAD_UNIMARC:
					ProjectOperation.UPLOAD_RESOURCES);
			historyDB.setProcessedMag(0);
			historyDB.setIndexedMag(0);

			Map<String, Integer> totals = new HashMap<String, Integer>();
			totals.put("mets", 0);
			totals.put("unimarc", 0);
			if(Utility.isCompressed(fileName)) {
				logger.info("Processo di upload " + process.getId() + ": unzipping");
				totals.putAll(Utility.uncompress(uploadFile, fileName, file, process, tecaProcessDao, delivery, numFiles, true));
			}

			else if(uploadFile.isDirectory()) {
				logger.info("Processo di upload " + process.getId() + ": copy");
				totals.putAll(Utility.uncompressed(uploadFile, file, process, tecaProcessDao, delivery, numFiles, true));
			}

			else {
				logger.info("Processo di upload " + process.getId() + ": copy");
				int numMags = Utility.uncompressed(uploadFile, fileName, file, process, delivery, magImportService);

				if(isUnimarc){
					totals.put("unimarc", numMags);
				}
				if(numMags > 0) {
					totals.put("mags", 1);
					totals.put("objects", 0);
				}

				else {
					totals.put("mags", 0);
					totals.put("objects", 1);
				}
			}

			process.setMessage("100%");
			tecaProcessDao.update(process);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			process.setStatus(0);

			if(isUnimarc)
				process.setMessage("Upload terminato - Unimarc "+ totals.get("unimarc"));
			else
				process.setMessage("Upload terminato" + (totals.get("mags") == 0 && totals.get("mets") == 0 ?
					" - Oggetti digitali: " + totals.get("objects") : ""));

			process.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
			tecaProcessDao.update(process);
			logger.info("Processo di upload " + process.getId() + " terminato");

			// aggiorna progetto
//			projectDB.setLastOperation(ProjectOperation.UPLOAD_RESOURCES);
//			projectDB.setLastModified(process.getTimestampEnd());
//			projectDao.update(projectDB);

//			historyDB.setTimestampOperation(process.getTimestampEnd());
//			historyDB.setProcessedMag(totals.get("mags"));
//			projectHistoryDao.insert(historyDB);

			report.setStatus(process.getStatus());
			report.setMessage(process.getMessage());

		} catch (IOException e) {
			logger.error("Processo di upload " + process.getId() + " terminato con errore: " +
					e.getMessage(), e);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			process.setStatus(-3);
			process.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
			process.setMessage(e.getMessage());
			historyDB.setStatus(-3);
			historyDB.setTimestampOperation(new BigInteger(cal.getTimeInMillis() + ""));

			try {
				tecaProcessDao.update(process);
				projectHistoryDao.insert(historyDB);
				report.setStatus(process.getStatus());
				report.setMessage(process.getMessage());

			} catch (DaoException e1) {
				logger.error("Processo di upload " + process.getId() + " terminato con errore: " + e1.getMessage(), e1);
				report.setStatus(-5);
				report.setMessage(e1.getMessage());
			}

		} catch (DaoException e) {
			logger.error("Processo di upload " + process.getId() + " terminato con errore: " +
					e.getMessage(), e);

			report.setStatus(-5);
			report.setMessage(e.getMessage());
		}

		return report;
	}

	/**
	 * Esegue il primo upload lato client
	 * 
	 * ESECUZIONE PROCESSO UPLOAD
	 * 
	 * @param processID ID del processo
	 * @param project nome del progetto
	 * @param fileName nome del file upload
	 * @param uploadStream stream di upload
	 * @return JsonUploadReport json di risposta
	 */
	public JsonUploadReport upload(String processID, String project, 
			String fileName, InputStream uploadStream) {
		
		File uploadFile = null;
		JsonUploadReport report = new JsonUploadReport();
		
		try {
			uploadFile = this.copyUploadStream(fileName, uploadStream);
			report = this.upload(processID, project, fileName, uploadFile);
			
		} catch (IOException e) {
			logger.error("Copia del file non riuscita (2) "+e.getMessage() + " file: "+fileName);
			report.setProcessID(processID);
	        report.setStatus(-7);
	        report.setMessage("Copia del file non riuscita");
	        return report;
		} finally {
			if(uploadFile != null)
				uploadFile.delete();
		}
		
		return report;
	}
	
	/**
	 * Esegue l'upload di un progetto in modalità nuovo progetto
	 * 
	 * @param processID ID processo di upload
	 * @param project nome del progetto
	 * @param fileName nome del file
	 * @param uploadFile file da caricare
	 * @return ProcessReport esito del processo di upload con stato e messaggio
	 */
	public JsonUploadReport upload(String processID, String project, String fileName, File uploadFile) {
		// processo
		DBTecaProcess process = null;
		DBProject projectDB = new DBProject();
		DBProjectHistory historyDB = new DBProjectHistory();

        // creazione json report
        JsonUploadReport report = new JsonUploadReport();
		
		try {
			// processo
			process = tecaProcessDao.findByID(processID);
			
			if(process == null) {
				logger.error("Processo " + processID + " non esistente");
				report.setProcessID(processID);
		        report.setStatus(-1);
		        report.setMessage("Processo non esistente");
		        return report;
			}
			
			// processo già avviato
			if(process.getStatus() != 1) {
				logger.error("Processo di upload \"" + processID + "\" già avviato in precedenza");
				report.setProcessID(process.getId());
		        report.setStatus(-6);
		        report.setMessage("Processo di upload già avviato in precedenza");
		        return report;
			}
			
			// controlla esistenza del path
			File file = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), Utility.toValidFileName(project));
			
			if (file.exists()) {
				logger.error("Progetto \"" + project + "\" già esistente");
				report.setProcessID(process.getId());
		        report.setStatus(-2);
		        report.setMessage("Progetto \"" + project + "\" già esistente");
		        return report;
			}
			
	        report.setProcessID(process.getId());
	        
			// lettura dei bytes
	        process.setStatus(2);
			process.setMessage("Uploading");
			logger.info("Processo di upload " + process.getId() + ": uploading");
			tecaProcessDao.update(process);
			
			int numFiles = 1;
			
			if(Utility.isCompressed(fileName))
				numFiles = Utility.validateArchive(uploadFile, fileName);
			
			else if(uploadFile.isDirectory())
				numFiles = Utility.validateDirectory(uploadFile);
			
			else {
				logger.error("File caricato non compresso nè directory");
				report.setProcessID(process.getId());
		        report.setStatus(-4);
		        report.setMessage("File caricato non compresso");
		        return report;
			}
			
			if (!file.exists())
				file.mkdirs();	
			
			process.setStatus(3);
			process.setMessage("0%");
			logger.info("Processo di upload " + process.getId() + ": unzipping");
			tecaProcessDao.update(process);
			
			Map<String, Integer> totals = new HashMap<String, Integer>();
			
			if(Utility.isCompressed(fileName))
				totals.putAll(Utility.uncompress(uploadFile, fileName, file, process, tecaProcessDao, delivery, numFiles, false));
			
			else if(uploadFile.isDirectory())
				totals.putAll(Utility.uncompressed(uploadFile, file, process, tecaProcessDao, delivery, numFiles, false));
			
			process.setMessage("100%");
			tecaProcessDao.update(process);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			process.setStatus(0);
			
			process.setMessage("Upload terminato" + (totals.get("mags") == 0 && totals.get("mets") == 0 ? 
					" - Oggetti digitali: " + totals.get("objects") : ""));
			
			process.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
			tecaProcessDao.update(process);

			// crea progetto
			projectDB.setName(Utility.toValidFileName(project));
			projectDB.setStatus(0);
			projectDB.setLastOperation(ProjectOperation.UPLOAD);
			projectDB.setLastModified(process.getTimestampEnd());
			projectDao.insert(projectDB);

			// crea cronologia progetto
			historyDB.setProject(projectDB);
			historyDB.setTecaProcess(process);
			historyDB.setOperationType(ProjectOperation.UPLOAD);
			historyDB.setTimestampOperation(process.getTimestampEnd());
			historyDB.setStatus(0);
			historyDB.setProcessedMag(totals.get("mags"));
			historyDB.setIndexedMag(0);
			projectHistoryDao.insert(historyDB);
			
			logger.info("Processo di upload " + process.getId() + " terminato");
			report.setStatus(process.getStatus());
			report.setMessage(process.getMessage());
			
		} catch (IOException e) {
			logger.error("Processo di upload " + process.getId() + " terminato con errore: " + 
					e.getMessage(), e);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			process.setStatus(-3);
			process.setTimestampEnd(new BigInteger(cal.getTimeInMillis() + ""));
			process.setMessage(e.getMessage());
			
			try {
				File projectFile = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), 
						Utility.toValidFileName(project));
				
				this.deleteProject(projectFile);
				
				tecaProcessDao.update(process);
				report.setStatus(process.getStatus());
				report.setMessage(process.getMessage());
				
			} catch (Exception e1) {
				logger.error("Processo di upload " + process.getId() + " terminato con errore: " + e1.getMessage(), e1);
				report.setStatus(-5);
				report.setMessage(e1.getMessage());
			}
		} catch (DaoException e) {
			try {
				File projectFile = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), 
						Utility.toValidFileName(project));
				
				this.deleteProject(projectFile);
				
				logger.error("Processo di upload " + process.getId() + " terminato con errore: " + 
						e.getMessage(), e);
				
				report.setStatus(-5);
				report.setMessage(e.getMessage());
				
			} catch (Exception e1) {
				logger.error("Processo di upload " + process.getId() + " terminato con errore: " + e1.getMessage(), e1);
				report.setStatus(-5);
				report.setMessage(e1.getMessage());
			}
		}
        
        return report;
	}
	
	/**
	 * Esegue l'upload di un file già presente sul server
	 * 
	 * ESECUZIONE PROCESSO UPLOAD LATO SERVER
	 * 
	 * @param processID ID del processo di upload
	 * @param project progetto di riferimento
	 * @param baseDirectory directory che contiene il file da caricare
	 * @param fileName nome del file da caricare (compresa estensione)
	 * @param update true se in modalità aggiornamento, false se in modalità nuovo
	 * @return JsonUploadReport risposta finale upload
	 */
	public JsonUploadReport uploadFromServer(String processID, String project, String baseDirectory, 
			String fileName, boolean update) {
		
		File uploadFile = new File(baseDirectory, fileName);
		
		if(!uploadFile.isDirectory()) {
			if(project == null || project.isEmpty()) {
				project = fileName;
				
				List<String> zipExtensions = Arrays.asList(".tar.gz", ".tar.bz2", ".tar.Z", 
						".gz", ".bz2", ".Z", ".zip");
				
				for(String ext : zipExtensions)
					project = project.replaceAll(ext, "");
			}
		}
		
		return !update ? 
				this.upload(processID, project, fileName, uploadFile) : 
					this.uploadUpdate(processID, project, fileName, uploadFile);
	}
	
	/**
	 * Restituisce lo stato di un processo di upload
	 * 
	 * STATO PROCESSO DI UPLOAD
	 * 
	 * @param processID ID processo di upload
	 * @return ProcessReport stato e messaggio processo
	 */
	public JsonUploadReport getUploadStatus(String processID) {
		// ricerca per ID
		DBTecaProcess proc = null;
		
		try {
			proc = tecaProcessDao.findByID(processID);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			JsonUploadReport report = new JsonUploadReport();
			report.setStatus(-1);
	        report.setMessage(e.getMessage());
	        return report;
		}
		
		// report
		JsonUploadReport report = new JsonUploadReport();
		
		// processo trovato
		if(proc != null) {
			report.setProcessID(proc.getId());
			report.setStatus(proc.getStatus());
			report.setUserID(proc.getTecaUser().getId());
			report.setMessage(proc.getMessage());
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			
			if(proc.getTimestampStart() != null) {
				cal.setTimeInMillis(proc.getTimestampStart().longValue());
				report.setTimestampStart(formatter.format(cal.getTime()));
			}
			
			if(proc.getTimestampEnd() != null) {
				cal.setTimeInMillis(proc.getTimestampEnd().longValue());
				report.setTimestampEnd(formatter.format(cal.getTime()));
			}
		}
		
		// processo non trovato
		else {
			logger.error("Processo " + processID + " non esistente");
			report.setStatus(-2);
			report.setMessage("Processo non esistente");
		}
		
		return report;
	}
	
	/**
	 * Restituisce i file compressi presenti all'interno della directory specificata
	 * 
	 * LETTURA FILES COMPRESSI IN DIRECTORY PER UPLOAD LATO SERVER
	 * 
	 * @param baseDirectory directory
	 * @return List<String> file compressi trovati nella directory
	 */
	public List<String> searchFilesForUpload(String baseDirectory) {
		List<String> files = new ArrayList<String>();
		File dir = new File(baseDirectory);
		
		if(!dir.exists())
			return files;
		
		for(File child : dir.listFiles()) {
			if(!child.isDirectory() && Utility.isCompressed(child.getName()))
				files.add(child.getName());
		}
		
		Collections.sort(files);
		return files;
	}

	/**
	 * Restituisce i MAG o METS contenuti nella directory specificata
	 * 
	 * LETTURA MAG DISPONIBILI NELLA DIRECTORY PER UPLOAD LATO SERVER
	 * 
	 * @param baseDirectory directory
	 * @return List<String> MAG o METS trovati nella directory
	 * @throws IOException 
	 */
	public List<String> showFilesForUpload(String baseDirectory, boolean onlyDirectories) throws IOException {
		File dir = new File(baseDirectory);
		List<String> results = new ArrayList<String>();
		
		if(!dir.exists())
			return results;
		
		for(Iterator<File> it = FileUtils.listFilesAndDirs(dir, new WildcardFileFilter("*.*"), new WildcardFileFilter("*")).iterator(); it.hasNext(); ) {
			File f = it.next();
			if(onlyDirectories && f.isDirectory())
				results.add(f.getCanonicalPath().replaceAll(dir.getCanonicalPath() + "/", ""));
			else if(f.isFile() && f.getName().endsWith(".xml") && (Utility.isMag(f) || Utility.isMets(f)))
				results.add(f.getCanonicalPath().replaceAll(dir.getCanonicalPath() + "/", ""));
		}
		
		Collections.sort(results);
		return results;
	}
	
	/**
	 * Esegue la cancellazione di un progetto dal file system
	 * 
	 * CANCELLAZIONE PROGETTO
	 * 
	 * @param projectFile directory root del progetto da cancellare
	 * @throws IOException
	 * @throws DaoException 
	 */
	public void deleteProject(File projectFile) throws IOException, DaoException, SolrServerException {
		List<String> resourcePaths = new ArrayList<String>();
		this.searchOriginalDigitalResources(projectFile, resourcePaths);
		
		// cancellazione solr
		logger.info("Cancellazione MAG delivery in corso...");
		delivery.deleteDocsByProject(projectFile.getName());
		
		logger.info("Cancellazione riferimenti oggetti digitali delivery in corso...");
		// delivery.deleteOriginalResources(resourcePaths);
		delivery.deleteOriginalResources(projectFile);
		
		// cancellazione fs
		logger.info("Cancellazione cartella in corso...");
		FileUtils.deleteDirectory(projectFile);

		// cancellazione db
		logger.info("Cancellazione riferimenti database in corso...");
		DBProject project = projectDao.getProjectByName(projectFile.getName());
		importMagDao.deleteByProject(project);
		projectHistoryDao.deleteByProject(project);
		projectDao.delete(project);
		vfsFileSystem.getVfsService().deleteProject(project.getName());
	}
	
	/**
	 * Ricerca le risorse digitali originali presenti nel progetto
	 * 
	 * @param file file da elaborare
	 * @param magPaths lista dei path relativi dei MAG
	 * @param resourcePaths lista dei path delle risorse digitali
	 * @throws IOException
	 */
	private void searchOriginalDigitalResources(File file, List<String> resourcePaths) {
		if(file.isDirectory()) {
			for(File child : file.listFiles())
				this.searchOriginalDigitalResources(child, resourcePaths);
		}
		
		else {
			if(file.getName().endsWith("xml")) {
        		if(!Utility.isMag(file))
        			resourcePaths.add(file.getPath());
        	}

			else
				resourcePaths.add(file.getPath());
		}
	}
	
	/**
	 * Copia lo stream di upload in un file temporaneo
	 * 
	 * @param name nome del file upload
	 * @param uploadStream stream di upload
	 * @return File file copiato
	 * @throws IOException 
	 */
	private File copyUploadStream(String name, InputStream uploadStream) throws IOException {
		File uploadFile = new File(ContentStatic.getProperties().getProperty("resourceDIRO"), name);
		if(!uploadFile.exists())
			logger.error("File non esiste: "+uploadFile.getAbsolutePath());
		java.nio.file.Path target = Paths.get(uploadFile.toURI());
		Files.copy(uploadStream, target, StandardCopyOption.REPLACE_EXISTING);
		return uploadFile;
	}

    public JsonUploadReport uploadFromServerResources(String processID, String project, String baseDirectory, String fileName, boolean isUnimarc) {
		File uploadFile = new File(baseDirectory, fileName);

		if(!uploadFile.isDirectory()) {
			if(project == null || project.isEmpty()) {
				project = fileName;

				List<String> zipExtensions = Arrays.asList(".tar.gz", ".tar.bz2", ".tar.Z",
						".gz", ".bz2", ".Z", ".zip");

				for(String ext : zipExtensions)
					project = project.replaceAll(ext, "");
			}
		}

		return uploadResources(processID, project, fileName, uploadFile, isUnimarc);
    }

    public JsonUploadReport uploadFromServerUnimarc(String processID, String project, String baseDirectory, String file) {
		return uploadFromServerResources(processID, project, baseDirectory, file, true);
    }

	public Object uploadUnimarc(String processID, String project, String fileName, InputStream uploadStream) {
		return uploadResources(processID, project, fileName, uploadStream, true);
	}
}
