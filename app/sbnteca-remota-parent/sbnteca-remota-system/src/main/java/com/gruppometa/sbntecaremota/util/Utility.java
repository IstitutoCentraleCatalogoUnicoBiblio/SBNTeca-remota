package com.gruppometa.sbntecaremota.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;

import com.gruppometa.sbntecaremota.objects.validators.*;
import com.gruppometa.sbntecaremota.restweb.objects.MagImportService;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.twmacinta.util.MD5;
import com.twmacinta.util.MD5InputStream;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;

import com.gruppometa.sbntecaremota.objects.ImportReport;
import com.gruppometa.sbntecaremota.objects.ImportSettings;
import com.gruppometa.sbntecaremota.objects.MagReport;
import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.objects.ValidationReport;
import com.gruppometa.sbntecaremota.objects.WaitingMagRecovery;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcess;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcessDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.objects.json.JsonImportReport;
import com.gruppometa.sbntecaremota.objects.json.JsonReportMag;
import com.gruppometa.sbntecaremota.retrieve.FileStorageDeliveryOptions;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;

import javax.imageio.ImageIO;

/**
 * Metodi di utilità generale per file system
 *
 *
 */
public class Utility {
	private static Logger logger = Logger.getLogger(Utility.class);
	
	// batch upload
	private static final int UPLOAD_BATCH_FILES = 1000;
    
	
	/**
	 * Il metodo restuisce l'intero a 2 cifre
	 * 
	 * @param num numero
	 * @return String numero a 2 cifre
	 */
	public static String checkDoubleNumber(String num){
		if (num.length()==1)
			return "0".concat(num);
		
		return num;
	}
	
	/**
	 * Il metodo dato il tempo espresso in millisecondi sotto forma di long restituisce l'output
	 * sotto forma di stringa
	 * 
	 * @param Long tempo espresso in millisecondi
	 * @return String tempo convertito in stringa
	 * */	
	public static String convertTimeMillisToString(Long date) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(date);
	    int mYear = calendar.get(Calendar.YEAR);
	    int mMonth = calendar.get(Calendar.MONTH);
	    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
	    int hour=calendar.get(Calendar.HOUR_OF_DAY);
	    int minute=calendar.get(Calendar.MINUTE);
	    int seconds=calendar.get(Calendar.SECOND);
	    String result=mDay+" "+mMonth+" "+mYear+" "+hour+":"+minute+":"+seconds;
	    return result;
	}
	
	/**
	 * Il metodo dato il tempo espresso in millisecondi espresso sottoforma di long
	 * restituisce una stringa che indica quanto tempo è trascorso espresso 
	 * in giorni, ore, minuti e secondi 
	 * 
	 * @param long tempo espresso in millisecondi
	 * @return String tempo trascorso in giorni, ore, minuti, secondi
	 * */
	public static String getTime(long millis){
	    String[] units = {" Days ", " Hours ", " Minutes ", " Seconds "};
	    Long[] values = new Long[units.length];
	    
	    if(millis < 0)
	    	throw new IllegalArgumentException("Duration must be greater than zero!");

	    values[0] = TimeUnit.MILLISECONDS.toDays(millis);
	    millis -= TimeUnit.DAYS.toMillis(values[0]);
	    values[1] = TimeUnit.MILLISECONDS.toHours(millis);
	    millis -= TimeUnit.HOURS.toMillis(values[1]);
	    values[2] = TimeUnit.MILLISECONDS.toMinutes(millis);
	    millis -= TimeUnit.MINUTES.toMillis(values[2]);
	    values[3] = TimeUnit.MILLISECONDS.toSeconds(millis);

	    StringBuilder sb = new StringBuilder(64);
	    boolean startPrinting = false;
	    
	    for(int i = 0; i < units.length; i++){
	        if( !startPrinting && values[i] != 0)
	            startPrinting = true;
	        
	        if(startPrinting) {
	            sb.append(values[i]);
	            sb.append(units[i]);
	        }
	    }

	    return(sb.toString());
	}
	
	/**
	 * Il metodo legge i bytes da uno stream
	 * 
	 * @param InputStream file di input da leggere
	 * */
	public static byte[] readBytes(InputStream is) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		
		try {
			IOUtils.copy(is, byteStream);
			byte[] result = byteStream.toByteArray();
			byteStream.close();
			return result;
			
		} catch (IOException e) {}
		
		return null;
	}
	
	/**
	 * Il metodo esegue la copia del file sul server
	 * 
	 * @param InputStream file di input da copiare
	 * @param String percorso del file da copiare
	 * @throws IOException 
	 * */
	public static void saveFile(InputStream is, String fileLocation) throws IOException {
		OutputStream outpuStream = new FileOutputStream(new File(fileLocation));
		IOUtils.copy(is, outpuStream);
		outpuStream.close();
	}
	
	/**
	 * Restituisce true se il file è compresso
	 * 
	 * @param file file
	 * @return true se il file è compresso
	 */
	public static boolean isCompressed(String file) {
		String ext = FilenameUtils.getExtension(file);
		return "zip".equals(ext) ||"tar".equals(ext) || "gz".equals(ext) || "bz2".equals(ext) || "Z".equals(ext);
	}
	
	/**
	 * Restituisce il nome del file inviato senza estensioni
	 * 
	 * @param file nome file
	 * @return nome del file senza estensioni
	 */
	public static String getFileNameWithoutZipExtension(String file) {
		String finalFileName = file;
		List<String> zipExtensions = Arrays.asList(".tar.gz", ".tar.bz2", 
				".tar.Z", ".tar", ".zip", ".gz", ".bz2", ".Z");
		
		for(String ext : zipExtensions) {
			if(finalFileName.endsWith(ext))
				return finalFileName.substring(0, finalFileName.lastIndexOf(ext));
		}
		
		return finalFileName;
	}
	
	/**
	 * Validazione (leggibilità) del file compresso
	 * 
	 * @param file file compresso
	 * @param fileName nome del file compresso
	 * @return int numero di files da estrarre
	 * @throws IOException
	 */
	public static int validateArchive(File file, String fileName) throws IOException {
		ArchiveStreamFactory arcFactory = new ArchiveStreamFactory();
		
    	// ricerca tipo di archivio (eccezione se archivio non riconosciuto)
    	String archive = null;
    	
    	if(fileName.contains(".zip"))
    		archive = ArchiveStreamFactory.ZIP;
    	
    	else if(fileName.contains(".tar"))
    		archive = ArchiveStreamFactory.TAR;
    	
    	
    	
    	// ricerca tipo compressione (opzionale)
    	CompressorStreamFactory comprFactory = new CompressorStreamFactory();
    	String compression = null;
    	
		if(fileName.contains(".bz2"))
			compression = CompressorStreamFactory.BZIP2;
		
		else if(fileName.contains(".gz"))
			compression = CompressorStreamFactory.GZIP;
		
		else if(fileName.contains(".Z"))
			compression = CompressorStreamFactory.Z;
		
		
		if(archive == null)
			return 1;
		
		else {
			ArchiveInputStream zipStream = null;
			int fileNum = 0;
			
			try {
				zipStream = arcFactory.createArchiveInputStream(archive, compression == null ? 
						new FileInputStream(file) : comprFactory.
								createCompressorInputStream(compression, new FileInputStream(file)));

		    	
		    	while(zipStream.getNextEntry() != null)
		    		fileNum++;
		    	
		    	zipStream.close();
		    	return fileNum;
				
			} catch (Exception e) {
				if(e instanceof EOFException)
					throw new IOException("File " + fileName + " corrotto o troncato");
				
				else throw new IOException("Problemi di lettura file compresso o "
						+ "tipo di compressione non corrispondente all'estensione del file compresso", e);
			}
		}
	}
	
	/**
	 * Validazione della directory di upload
	 * 
	 * @param file file compresso
	 * 
	 * @return int numero di files da estrarre
	 * @throws IOException
	 */
	public static int validateDirectory(File file) throws IOException {
		return FileUtils.listFiles(file, null, true).size();
	}
	
	/**
	 * Decompressione del file ZIP e caricamento nella directory di progetto dei files scompattati
	 * 
	 * @param File file compresso
	 * @param fileName nome del file compresso
	 * @param dest directory di progetto
	 * @param uplProcess info processo di upload
	 * @param processDao componente DAO per processo di upload
	 * @param numFiles numero di files da estrarre
	 * 
	 * @return int numero MAG identificati
	 * @throws IOException
	 * @throws DaoException
	 */
    public static Map<String, Integer> uncompress(File file, String fileName, File dest, DBTecaProcess uplProcess, 
    		DBTecaProcessDao processDao, MagResourceDelivery delivery, int numFiles, boolean onlyResources)
    				throws IOException, DaoException {
    	
    	Map<String, Integer> totals = new HashMap<String, Integer>();
    	totals.put("mags", 0);
    	totals.put("mets", 0);
    	totals.put("objects", 0);
    	
	    ArchiveStreamFactory arcFactory = new ArchiveStreamFactory();
	    CompressorInputStream cis = null;
	    ArchiveInputStream ais = null;
	    List<DeliveryResource> resources = new ArrayList<DeliveryResource>();
	    Map<String, Date> uploadMagDeliveryMap = new HashMap<String, Date>();
	    Map<String, Date> uploadMetsDeliveryMap = new HashMap<String, Date>();
	    int count = 0;
	    
	    try {
	    	// ricerca tipo di archivio (eccezione se archivio non riconosciuto)
	    	String archive = null;
	    	
	    	if(fileName.contains(".zip"))
	    		archive = ArchiveStreamFactory.ZIP;
	    	
	    	else if(fileName.contains(".tar"))
	    		archive = ArchiveStreamFactory.TAR;
	    	
	    	
	    	
	    	// ricerca tipo compressione (opzionale)
	    	CompressorStreamFactory comprFactory = new CompressorStreamFactory();
	    	String compression = null;
	    	
			if(fileName.contains(".bz2"))
				compression = CompressorStreamFactory.BZIP2;
			
			else if(fileName.contains(".gz"))
				compression = CompressorStreamFactory.GZIP;
			
			else if(fileName.contains(".Z"))
				compression = CompressorStreamFactory.Z;
			
			
	    	// decompressione archivio
	    	if(archive != null) {
		    	ais = arcFactory.createArchiveInputStream(archive, compression == null ? new FileInputStream(file) : 
	    					comprFactory.createCompressorInputStream(compression, new FileInputStream(file)));
		    	
		    	ArchiveEntry entry = null;
		    	int fileRead = 0;
		    	
		    	while((entry = ais.getNextEntry()) != null) {
		    		fileRead++;
			    	int percent = Math.round((fileRead * 100.f) / numFiles);
			    	uplProcess.setMessage(percent + "%");
			    	processDao.update(uplProcess);
			    	File destPath = new File(dest, entry.getName());
			    	File parentDir = destPath.getParentFile();
			    	
			    	// crea directory genitore
			    	if(!parentDir.exists())
			    		parentDir.mkdirs();
			    	
			    	// salvataggio
			    	if(entry.isDirectory()) {
			    		if(!destPath.exists())
			    			destPath.mkdir();
			    	}

			    	else {
			        	Utility.saveFile(ais, destPath.getPath());
			        	
			        	if(ContentStatic.getBlacklistMimeTypes().contains(Utility.getMime(destPath)))
			        		destPath.delete();
			        	
			        	else {
				        	count++;
				        	
				        	if(destPath.getName().endsWith("xml")) {
				        		if(Utility.isMag(destPath)) {
				        			totals.put("mags", totals.get("mags") + 1);
				        			uploadMagDeliveryMap.put(destPath.getCanonicalPath(), new Date(System.currentTimeMillis()));
				        		}
	
				        		else if(Utility.isMets(destPath)) {
				        			totals.put("mets", totals.get("mets") + 1);
				        			uploadMetsDeliveryMap.put(destPath.getCanonicalPath(), new Date(System.currentTimeMillis()));
				        		}
				        		
				        		else {
									extracted(dest, destPath, resources);
									totals.put("objects", totals.get("objects") + 1);
					        	}
				        	}
				        	
				        	else {
								extracted(dest, destPath, resources);
								totals.put("objects", totals.get("objects") + 1);
				        	}
			        	}
			    	}
			    	
				    if(count == UPLOAD_BATCH_FILES) {
					    delivery.insertResources(resources, onlyResources);
					    resources.clear();
					    delivery.uploadMags(uploadMagDeliveryMap);
					    uploadMagDeliveryMap.clear();
					    delivery.uploadMets(uploadMetsDeliveryMap);
					    uploadMetsDeliveryMap.clear();
					    count = 0;
				    }
		    	}
	    	}
	    	
	    	// decompressione semplice 1 file GZ, Z, BZ2
	    	else {
	    		String realFileName = fileName.substring(0, fileName.lastIndexOf("."));
	    		cis = comprFactory.createCompressorInputStream(compression, new FileInputStream(file));
	    		File destPath = new File(dest, realFileName);
	    		Utility.saveFile(cis, destPath.getPath());
	    		count++;
	        	
	        	if(destPath.getName().endsWith("xml")) {
	        		if(Utility.isMag(destPath)) {
	        			totals.put("mags", totals.get("mags") + 1);
	        			uploadMagDeliveryMap.put(destPath.getCanonicalPath(), new Date(System.currentTimeMillis()));
	        		}
	        		
	        		else if(Utility.isMets(destPath)) {
	        			totals.put("mets", totals.get("mets") + 1);
	        			uploadMetsDeliveryMap.put(destPath.getCanonicalPath(), new Date(System.currentTimeMillis()));
	        		}

		        	else {
						extracted(dest, destPath, resources);
						totals.put("objects", totals.get("objects") + 1);
		        	}
	        	}

	        	else {
					extracted(dest, destPath, resources);
					totals.put("objects", totals.get("objects") + 1);
	        	}
	    	}
			
	    } catch (ArchiveException e) {
			throw new IOException(e.getMessage());
		} catch (CompressorException e) {
			throw new IOException(e.getMessage());
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		} finally {
			// chiusura stream
			if(cis != null)
				cis.close();
			
			if(ais != null)
				ais.close();
		}
	    
	    if(count > 0) {
		    delivery.insertResources(resources, onlyResources);
		    delivery.uploadMags(uploadMagDeliveryMap);
		    delivery.uploadMets(uploadMetsDeliveryMap);
	    }
	    
	    return totals;
	}


	/**
	 * Copia nella directory dei files già scompattati
	 * 
	 * @param file file compresso o directory
	 * @param dest directory di progetto
	 * @param uplProcess info processo di upload
	 * @param processDao componente DAO per processo di upload
	 * @param numFiles numero di files da estrarre
	 * 
	 * @return int numero MAG identificati
	 * @throws IOException
	 * @throws DaoException
	 */
    public static Map<String, Integer> uncompressed(File file, File dest, DBTecaProcess uplProcess, 
    		DBTecaProcessDao processDao, MagResourceDelivery delivery, int numFiles, boolean onlyResources)
    				throws IOException, DaoException {
    	
    	Map<String, Integer> totals = new HashMap<String, Integer>();
    	totals.put("mags", 0);
    	totals.put("mets", 0);
    	totals.put("objects", 0);
    	
	    List<DeliveryResource> resources = new ArrayList<DeliveryResource>();
	    Map<String, Date> uploadMagDeliveryMap = new HashMap<String, Date>();
	    Map<String, Date> uploadMetsDeliveryMap = new HashMap<String, Date>();
	    int count = 0;
	    
	    try {
	    	int fileRead = 0;
	    	Iterator<File> it = FileUtils.listFiles(file, null, true).iterator();
		    	
	    	while(it.hasNext()) {
	    		fileRead++;
		    	int percent = Math.round((fileRead * 100.f) / numFiles);
		    	uplProcess.setMessage(percent + "%");
		    	processDao.update(uplProcess);
		    	File inputFile = it.next();
		    	String relativePath = inputFile.getCanonicalPath().replaceAll(file.getCanonicalPath() + "/", "");
		    	File destPath = new File(dest, relativePath);
		    	File parentDir = destPath.getParentFile();
		    	
		    	// crea directory genitore
		    	if(!parentDir.exists())
		    		parentDir.mkdirs();
		    	
		    	// salvataggio
		    	if(inputFile.isDirectory()) {
		    		if(!destPath.exists())
		    			destPath.mkdir();
		    	}
	
		    	else {
		    		FileUtils.copyFile(inputFile, destPath);
		    		
		    		if(ContentStatic.getBlacklistMimeTypes().contains(Utility.getMime(destPath)))
		    			destPath.delete();
		    		
		    		else {
			    		count++;
			        	
			        	if(!onlyResources && destPath.getName().endsWith("xml")) {
			        		if(Utility.isMag(destPath)) {
			        			totals.put("mags", totals.get("mags") + 1);
			        			uploadMagDeliveryMap.put(destPath.getCanonicalPath(), new Date(System.currentTimeMillis()));
			        		}
			        		
			        		else if(Utility.isMets(destPath)) {
			        			totals.put("mets", totals.get("mets") + 1);
			        			uploadMagDeliveryMap.put(destPath.getCanonicalPath(), new Date(System.currentTimeMillis()));
			        		}
			        		
			        		else {
								extracted(dest, destPath, resources);
								totals.put("objects", totals.get("objects") + 1);
				        	}
			        	}
			        	
			        	else {
							extracted(dest, destPath, resources);
							totals.put("objects", totals.get("objects") + 1);
			        	}
		    		}
		    	}

			    if(count == UPLOAD_BATCH_FILES) {
				    delivery.insertResources(resources, onlyResources);
				    resources.clear();
				    delivery.uploadMags(uploadMagDeliveryMap);
				    uploadMagDeliveryMap.clear();
				    delivery.uploadMets(uploadMetsDeliveryMap);
				    uploadMetsDeliveryMap.clear();
				    count = 0;
			    }
	    	}
	    	
	    } catch (Exception e) {
			throw new IOException(e.getMessage());
		}

	    if(count > 0) {
		    delivery.insertResources(resources, onlyResources);
		    delivery.uploadMags(uploadMagDeliveryMap);
		    delivery.uploadMets(uploadMetsDeliveryMap);
	    }
	    
	    return totals;
	}

	private static void extracted(File dest, File destPath, List<DeliveryResource> resources) throws Exception {
		DeliveryResource res = new DeliveryResource();
		res.setProject(dest.getName());
		res.setHref(destPath.getCanonicalPath());
		res.setDeliveryID(getResourceDeliveryID(destPath));
		res.setVfsType(VfsFile.TYPE_OBJECT);
		/**
		 * container è sconosciuto al momento
		 */
//		res.setVfsContainer(
//				res.getProject()+"/"+
//				destPath.getParentFile().getName());
		res.setVfsFilename(makeFilename(dest, destPath));
		res.setVfsDirectory(dest.getName());
		resources.add(res);
	}

	private static String makeFilename(File dest, File destPath) {
		if(dest==null || destPath==null)
			return null;
		if(destPath.getAbsolutePath().startsWith(dest.getAbsolutePath())) {
			String ret =  destPath.getAbsolutePath().substring(dest.getAbsolutePath().length());
			if(ret.startsWith("/"))
				return ret.substring(1);
		}
		return destPath.getName();
	}

	/**
     * Copia il file in upload già scompattato
     * 
     * @param uploadFile file caricato in upload già scompattato
     * @param dest file directory del progetto
     * @param fileName percorso relativo del file
     * @return numero di MAG rilevati
     * @throws IOException
     */
    public static int uncompressed(File uploadFile, String fileName, File dest, DBTecaProcess dbTecaProcess,
								   MagResourceDelivery delivery, MagImportService magImportService) throws IOException {
    	
	    try {
			// salva
	    	InputStream is = new FileInputStream(uploadFile);
	    	File destPath = new File(dest, fileName);
			Utility.saveFile(is, destPath.getPath());
			is.close();
			
			if(ContentStatic.getBlacklistMimeTypes().contains(Utility.getMime(destPath))) {
				destPath.delete();
				return 1;
			}
			
			// verifica
			else if(destPath.getName().endsWith("xml")) {
        		if(Utility.isMag(destPath)) {
        			Map<String, Date> uploadMagDeliveryMap = new HashMap<String, Date>();
        			uploadMagDeliveryMap.put(destPath.getCanonicalPath(), new Date(System.currentTimeMillis()));
        			delivery.uploadMags(uploadMagDeliveryMap);
        			return 1;
        		}
        		
        		else {
        			DeliveryResource res = new DeliveryResource();
    	        	res.setProject(dest.getName());
    	        	res.setHref(destPath.getCanonicalPath());
    	        	res.setDeliveryID(getResourceDeliveryID(destPath));
    	        	delivery.insertResources(Arrays.asList(res));
        		}
        	}
			else if(destPath.getName().endsWith("mrc")) {
				DeliveryResource res = new DeliveryResource();
				res.setProject(dest.getName());
				res.setHref(destPath.getCanonicalPath());
				res.setDeliveryID(getResourceDeliveryID(destPath));
				return delivery.uploadUnimarc(res, magImportService, dbTecaProcess);
			}
			else if(destPath.getName().endsWith("csv")) {
				DeliveryResource res = new DeliveryResource();
				res.setProject(dest.getName());
				res.setHref(destPath.getCanonicalPath());
				res.setDeliveryID(getResourceDeliveryID(destPath));
				return delivery.uploadCsv(res, magImportService, dbTecaProcess);
			}
        	else {
        		DeliveryResource res = new DeliveryResource();
	        	res.setProject(dest.getName());
	        	res.setHref(destPath.getCanonicalPath());
	        	res.setDeliveryID(getResourceDeliveryID(destPath));
	        	delivery.insertResources(Arrays.asList(res));
        	}
        	
        	return 0;
			
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
    }
    
    /**
     * Restituisce l'ID del delivery per una risorsa digitale
     * 
     * @param file file della risorsa digitale
     * @return String ID delivery
     * @throws Exception
     */
	public static String getResourceDeliveryID(File file) throws Exception {
		return getResourceDeliveryID(file, true);
	}

    public static String getResourceDeliveryID(File file, boolean checkExists) throws Exception {
    	if(checkExists && !file.exists())
    		return "";
    	
    	String resourceName = file.getName();
    	int idx = resourceName.indexOf(".");
    	resourceName = idx < 0 ? resourceName : resourceName.substring(0, idx);
    	resourceName = resourceName
				.replaceAll("[ \n\r\t\f]+", "_")
				.replaceAll("\\+","_")
				.toLowerCase();
    	
    	return resourceName + "_" + Utility.getMD5(file.getParentFile()!=null?
				file.getParentFile().getCanonicalPath():file.getCanonicalPath()) + "_" +
    			((file.exists() && !file.isDirectory()) ? Utility.getMD5Checksum(file.getPath()) : "");
    } 
	
    /**
     * Dato il percorso di un file, il metodo restituisce il suo nome
     * 
     * @param String percorso del file
     * @return String nome del file recuperato
     * */
	public static String getNameFile(String path) {
		return path.substring(path.lastIndexOf("/") + 1, path.length());
	}
	
	/**
	 * Il metodo converte gli elementi di un arrayList, sotto forma stringa,
	 * dove ogni elemento dell'array è separato dal carattare separatore separatorValue
	 * 
	 * @param ArrayList<String> elenco dei dati in ingresso
	 * @param String carattere di separatore tra gli elementi nella stringa finale
	 * @return String contiene gli elementi dell'array memorizzati in stringa separati dal carattere
	 * separatorValue
	 * */
	public static String fromArrayListToString(ArrayList<String> listValue, String separatorValue) {
		String result="";
		
		if (listValue!=null && listValue.size()>0){
			for (String s: listValue)
				result=result.concat(s).concat(separatorValue);
		}
		
		if (result.endsWith(separatorValue))
			result=result.substring(0,result.length()-1);
		
		return result;
	}
	
	/**
	 * Il metodo crea un link simbolico
	 * 
	 * @param String path input
	 * @param String path output
	 * @return boolean restituisce true se il link è stato creato, false altrimenti
	 * */
	public static boolean createSymbolicLink(String input,String output){
		Path newLink = Paths.get(output);
		Path target = Paths.get(output);
		
		try {
		    Files.createLink(newLink, target);
		    
		} catch (IOException x) {
		    System.err.println(x);
		} catch (UnsupportedOperationException x) {
		    // Some file systems do not support symbolic links.
		    System.err.println(x);
		}
		return true;
	}
	
	/**
	 * Il metodo deleteFile cancella il file specificato nel percorso
	 * 
	 * @param String percorso del file da cancellare
	 * @return boolean true se il file è stato cancellato, false altrimenti
	 * */
	public static boolean deleteFile(String pathFile) {
		Path path = Paths.get(pathFile);
		
		try {
			Files.delete(path);
			return true;
		}
		catch (FileAlreadyExistsException e) {
			e.printStackTrace();
			logger.error("Errore nella cancellazione del file "+pathFile);
			//logger.error(e.printStackTrace());
			return false;
		}
		catch (IOException e) {
			e.printStackTrace();
			logger.error("Errore nella cancellazione del file "+pathFile);
			return false;
		}
	}
    
	/**
	 * Il metodo copia il file specificato dal percorso fileInput 
	 * 
	 * nel file specificato nel file fileOutput
	 * @param String percorso del file di input
	 * @param String percorso del file di output
	 * @param boolean overwrite sovrascrivi se il file già esiste
	 * @return boolean true se copiato correttamente, false altrimenti
	 * */
	public static boolean copyFileFS(String fileInput, String fileOutput, boolean overwrite) {
		File file = new File(fileInput);
		
		// file input non esistente
		if (!file.exists())
			return false;
		
		// crea sottodirectory output
		file = new File(fileOutput);
		InputStream is = null;
		OutputStream os = null;
		
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		
		if (overwrite || !file.exists()) { 
			try {
				is = new FileInputStream(fileInput);
				os = new FileOutputStream(fileOutput);
				IOUtils.copy(is, os);
				
			} catch(IOException e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				try {
					if(is != null)
						is.close();
					
					if(os != null)
						os.close();
					
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			return true;
		}
		
		else
			return false;
	}
	
	/**
	 * Il metodo scarica la risorsa dall'url specificato nel parametro url
	 * 
	 * @param URL percorso della risorse da salvare
	 * @param String percorso locale della risorsa
	 * */
	public static void downloadFromUrl(URL url, String localFilename) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;
		 
        try {
	        URLConnection urlConn = url.openConnection();
            is = urlConn.getInputStream();
            fos = new FileOutputStream(localFilename);
            byte[] buffer = new byte[4096];
            int len;
           
           while ((len = is.read(buffer)) > 0)
               fos.write(buffer, 0, len);
               
	    } finally {
	        try {
	            if (is != null) 
	        	    is.close();
	            
	        } finally {
	            if (fos != null)
	                fos.close();
	        }
		}
	}
	
	/**
	 * Il metodo carica il file di configurazione
	 * 
	 * @param percorso del file di configurazione
	 * @return Properties 
	 * */
	public static Properties getConfiguration(String inputConfigFile) {
		Properties pros=null;
		
		try {
			 pros=new Properties();
		     ClassLoader loader = Thread.currentThread().getContextClassLoader();
		     InputStream is= loader.getResourceAsStream(inputConfigFile);
		     pros.load(is);
		     is.close();
		     
		} catch(IOException e){}
		
		return pros;
	}
	
	/**
	 * Il metodo preso in input il percorso della risorsa e restituisce l'md5 della risorsa
	 * 
	 * @param String percorso della risorsa
	 * @return String md5 calcolato della risorsa
	 * */
	public static String getMD5Checksum(String filename) throws Exception {
		FileInputStream fin = null;
		String ret = null;
		boolean fast = true;
		try{
			fin = new FileInputStream(filename);
			if(fast) {
				//MD5InputStream inputStream = new MD5InputStream(fin);
				//ret = MD5.asHex(inputStream.hash());
				ret = MD5.asHex(MD5.getHash(new File(filename)));
			}
			else
				ret = DigestUtils.md5Hex(fin);
		}
		finally {
			if(fin!=null)
				fin.close();
		}
		return ret;

	}
	public static String getMD5Checksum_old(String filename) throws Exception {
	       byte[] b = createChecksum(filename);
	       String result = "";
	       
	       for (int i=0; i < b.length; i++)
	           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	       
	       return result;
    }

	public static String asHex(byte[] bytes){
		String result = "";

		for (int i=0; i < bytes.length; i++)
			result += Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );

		return result;
	}
	/**
	 * Il metodo preso in input il percorso della risorsa e restituisce l'md5 della stringa
	 * 
	 * @param String percorso della risorsa
	 * @return String md5 calcolato della risorsa
	 * */
	public static String getMD5(String string) throws Exception {
	       byte[] b = MessageDigest.getInstance("MD5").digest(string.getBytes());
	       String result = "";
	       
	       for (int i=0; i < b.length; i++)
	           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	       
	       return result;
    }
	
	/**
	 * Il metodo preso in input il percorso della risorsa restituisce la checksum calcolata
	 * 
	 * @param String percoro della risorsa
	 * @return byte[] checksum
	 * */
	public static byte[] createChecksum(String filename) throws Exception {
//		byte[] b = Files.readAllBytes(Paths.get(filename));
//		byte[] hash = MessageDigest.getInstance("MD5").digest(b);
//		return hash;
	       InputStream fis =  new FileInputStream(filename);
	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);

	           if (numRead > 0)
	               complete.update(buffer, 0, numRead);

	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
    }
		
	/**
	 * Il metodo dato in input il percorso della directory, la crea su disco
	 * @param String percorso della directory da creare
	 * */
	public static void createDir(String dirName){
		if (dirName!=null && !dirName.equals("")){
			File file = new File(dirName);
			
			if (!file.exists())
				file.mkdirs();
		}
	}
	
	/**
	 * Il metodo dato in input il percorso di file delle properties lo carica in memoria
	 * 
	 * @param String percorso del file di properties da caricare in memoria
	 * @return Properties file caricato in memoria
	 * */
	public static Properties readFileProperties(String filename){
		Properties prop = new Properties();
    	File f=new File(filename); 
    	InputStream input = null;
    	
    	try {
    		input = Thread.currentThread().getContextClassLoader().
    				getResourceAsStream(filename);
    		
    		if(input==null) {
    	        System.out.println("Sorry, unable to find " + filename);
    		    return null;
    		}
 
    		//load a properties file from class path, inside static method
    		prop.load(input);    	
    		
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null) {
        		try {
					input.close();
					
				}  catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
    	
    	return prop;
 
	}
	
	/**
	 * Carica in memoria la blacklist dei MIME types non consentiti
	 * 
	 * @param filename file di configurazione
	 * @return blacklist
	 */
	public static List<String> readBlacklistMimeTypes(String filename){
		List<String> blacklist = new ArrayList<String>();
    	File f=new File(filename); 
    	BufferedReader reader = null;
    	
    	try {
    		reader = new BufferedReader(new InputStreamReader(Thread.currentThread().
    				getContextClassLoader().getResourceAsStream(filename)));
    		
    		String line = "";
    		
    		while((line = reader.readLine()) != null)
    			blacklist.add(line.trim());
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
        } finally{
        	if(reader!=null) {
        		try {
					reader.close();
					
				}  catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
    	
    	return blacklist;
	}


	/**
	 * Crea il percorso relativo completo del sistema
	 * 
	 * @param usageResource usage della risorsa
	 * @param export se modalità esportazione
	 * @param resourceType tipo di risorsa
	 * @param fileName nome del file
	 * @return path relativo completo del sistema
	 */
	public static String createPathFileOutput(String usageResource, boolean export, String resourceType, 
			String fileName, String storageOption) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int anno = cal.get(Calendar.YEAR);
		int mese = cal.get(Calendar.MONTH) + 1;
		int giorno = cal.get(Calendar.DAY_OF_MONTH);					
		int ora = cal.get(Calendar.HOUR_OF_DAY);
		int minuto = cal.get(Calendar.MINUTE);		
		
		String sep = System.getProperty("file.separator");
		StringBuilder relativePath = new StringBuilder();
		relativePath.append((export ? "external" : "internal") + sep);
		
		if(FileStorageDeliveryOptions.TYPE_USAGE.equals(storageOption))
			relativePath.append(resourceType + sep + "usage" + usageResource + sep);
		
		else if(FileStorageDeliveryOptions.USAGE_TYPE.equals(storageOption))
			relativePath.append("usage" + usageResource + sep + resourceType + sep);
		
		relativePath.append(anno + sep + 
				Utility.checkDoubleNumber(String.valueOf(mese)) + sep + 
				Utility.checkDoubleNumber(String.valueOf(giorno)) + sep + 
				Utility.checkDoubleNumber(String.valueOf(ora)) + sep + 
				Utility.checkDoubleNumber(String.valueOf(minuto)) + sep + 
				fileName);
		
		return relativePath.toString();
	}
	
    /**
     * Controlla che i messaggi di validazione appartengano ai seguenti tipi (FATAL_ERROR, ERROR, WARNING, WAIT)
     * 
     * @param errors errori di validazione
     * @return boolean restituisce vero se non ci sono tipi di errori diversi da quelli elencati
     */
    public static boolean checkErrorType(List<ValidationError> errors) {
    	List<String> errorTypes = Arrays.asList(new String[] { ValidationError.FATAL_ERROR, 
    			ValidationError.ERROR, ValidationError.WARNING, ValidationError.WAIT });
    	
    	
    	for(ValidationError error : errors) {
			if(!errorTypes.contains(error.getStatus()))
				return false;
		}
		
		return true;
    }
    
    /**
     * Controlla la presenza di messaggi di errore nei messaggi di validazione
     * 
     * @param errors errori di validazione
     * @return boolean restituisce vero se ci sono errori
     */
	public static boolean checkErrors(List<ValidationError> errors) {
		for(ValidationError error : errors) {
			if(ValidationError.ERROR.equals(error.getStatus()) || ValidationError.FATAL_ERROR.equals(error.getStatus()))
				return true;
		}
		
		return false;
	} 
	
	/**
	 * Tale classe si occupa della serializzazione da oggetto report (per il formato XML) a 
	 * oggetto report (per il formato JSON)
	 * 
	 * @param xmlReportObject rappresenta l'oggetto root del documento XML
	 * @return JsonImportReport report in formato JSON
	 */
	public static JsonImportReport toJsonReportObject(ImportReport xmlReportObject) {
		JsonImportReport jsonReport = new JsonImportReport();
		jsonReport.setJobID(xmlReportObject.getId());
		jsonReport.setStatus(xmlReportObject.getStatus());
		jsonReport.setMessage(xmlReportObject.getStatusMessage());
		jsonReport.setTimestampStart(xmlReportObject.getStart());
		jsonReport.setMagCount(xmlReportObject.getNumMag());
		
		if(xmlReportObject.getEnd() != null)
			jsonReport.setTimestampEnd(xmlReportObject.getEnd());
		
		jsonReport.setProcessedMagCount(xmlReportObject.getProcessedMag());
		jsonReport.setMagOK(xmlReportObject.getMagOK());
		jsonReport.setMagKO(xmlReportObject.getMagKO());
		jsonReport.setMetsOK(xmlReportObject.getMetsOK());
		jsonReport.setMetsKO(xmlReportObject.getMetsKO());

		if(xmlReportObject.getDigitalObjectCount() != null)
			jsonReport.setDigitalObjectCount(xmlReportObject.getDigitalObjectCount());
		
		if(xmlReportObject.getValidation() != null) {
			jsonReport.setTimestampStartValidation(xmlReportObject.getValidation().getStart());
			
			if(xmlReportObject.getValidation().getEnd() != null)
				jsonReport.setTimestampEndValidation(xmlReportObject.getValidation().getEnd());
			
			if(!xmlReportObject.getValidation().getMagReports().isEmpty()) {
				for(Iterator<MagReport> it = xmlReportObject.getValidation().getMagReports().iterator(); it.hasNext(); ) {
					MagReport magInfo = it.next();
					JsonReportMag jsonMagReport = new JsonReportMag();
					jsonMagReport.setMag(LabelMapper.getFilename( magInfo.getPath() ));
					jsonMagReport.setResult(magInfo.getResult());
					jsonReport.getReportMags().add(jsonMagReport);
				}
			}
		}
		
		return jsonReport;
	}
	
	/**
	 * Configura la catena di validazione
	 * 
	 * @param settings struttura dati di configurazione per processi di importazione/validazione
	 */
	public static void configureValidation(ImportSettings settings) {
		// validatori
		settings.getValidators().add(new MetsSchemaValidator());
		settings.getValidators().add(new MetsProfileValidator());
		settings.getValidators().add(new MagExistenceValidator());
		settings.getValidators().add(new XsdValidator());
		settings.getValidators().add(new SerialMagValidator());
		settings.getValidators().add(new SequenceNumberValidator());
		settings.getValidators().add(new GenericStruValidator());
		settings.getValidators().add(new ResourceListValidator());
	}
	
	/**
	 * Trasforma la stringa in un nome valido per file, sostituendo gli eventuali caratteri
	 * non consentiti con il carattere '_'
	 * 
	 * @param name nome da convertire
	 * @return String nome converito in un nome file
	 */
	public static String toValidFileName(String name) {
		return name.replaceAll("[#<\\$\\+%>!`&\\*\\‘\\'\\|\\{\\?\\“\\\"=\\}\\/\\:\\@]", "_");
	}
	
	/**
	 * Metodo che esegue la copia dell'oggetto impostazioni, importante per la lettura
	 * e la scrittura della mappa condivisa dei job attivi
	 * 
	 * @param original oggetto impostazioni originale
	 * @return l'oggetto copia impostazioni
	 */
	public static ImportSettings copyImportSettings(ImportSettings original) {
		ImportSettings copy = new ImportSettings();
		copy.setConfiguration(original.getConfiguration());
		copy.setDressMag(original.getDressMag());
		copy.setIndexMags(original.getIndexMags());
		copy.setJobID(original.getJobID());
		copy.setOverwrite(original.getOverwrite());
		copy.setPubblica(original.getPubblica());
		copy.setReport(copyImportReport(original.getReport()));
		copy.setTypeMag(original.getTypeMag());
		copy.setUpdate(original.getUpdate());
		copy.setUsageA(original.getUsageA());
		copy.setUsageE(original.getUsageE());
		copy.setUserID(original.getUserID());
		copy.setValidators(original.getValidators());
		return copy;
	}
	
	/**
	 * Metodo che esegue la copia dell'oggetto report, importante per la lettura
	 * e la scrittura della mappa condivisa dei job attivi
	 * 
	 * @param original ogget report originale
	 * @return oggetto report copia
	 */
	public static ImportReport copyImportReport(ImportReport original) {
		// copia oggeto validatore
		ValidationReport copyValidation = null;
		
		if(original.getValidation() != null) {
			copyValidation = new ValidationReport();
			copyValidation.setStart(original.getValidation().getStart());
			copyValidation.getMagReports().addAll(original.getValidation().getMagReports());
			copyValidation.setEnd(original.getValidation().getEnd());
		}
		
		// copia oggetto report
		ImportReport copy = new ImportReport();
		copy.setEnd(original.getEnd());
		copy.setId(original.getId());
		copy.setIndexedMag(original.getIndexedMag());
		copy.setMagKO(original.getMagKO());
		copy.setMagOK(original.getMagOK());
		copy.setMetsKO(original.getMetsKO());
		copy.setMetsOK(original.getMetsOK());
		copy.setNumMag(original.getNumMag());
		copy.setProcessedMag(original.getProcessedMag());
		copy.setDigitalObjectCount(original.getDigitalObjectCount());
		copy.setStart(original.getStart());
		copy.setStatus(original.getStatus());
		copy.setStatusMessage(original.getStatusMessage());
		copy.setUser(original.getUser());
		copy.setValidation(copyValidation);
		return copy;
	}
	
	/**
	 * Restituisce il content type (mime) del file analizzato
	 * 
	 * @param file file da analizzare
	 * @return String mime type
	 * @throws IOException
	 */
	public static String getMime(File file) throws IOException {
		return new Tika().detect(file);
	}
	
	/**
	 * Restituisce true se c'è un algoritmo di compressione di secondo livello (BZIP2, GZ, Z)
	 * 
	 * @param extension estensione del file da comprimere
	 * @return boolean true se c'è un algoritmo di compressione di secondo livello (BZIP2, GZ, Z)
	 */
	public static boolean hasCompressor(String extension) {
		return extension.contains("bz2") || extension.contains("gz") || extension.contains("Z");
	}
	
	/**
	 * Creazione del compressore
	 * 
	 * @param outputStream stream di output del file da creare
	 * @param extension estensione del file compresso da creare
	 * @return CompressorOutputStream compressore
	 * @throws CompressorException
	 */
	public static CompressorOutputStream createCompressor(OutputStream outputStream, String extension) throws CompressorException {
		String compressorType = CompressorStreamFactory.GZIP;
		
		if(extension.contains("bz2"))
			compressorType = CompressorStreamFactory.BZIP2;
		
		else if(extension.contains("Z"))
			compressorType = CompressorStreamFactory.Z;
		
		CompressorStreamFactory factory = new CompressorStreamFactory();
		return factory.createCompressorOutputStream(compressorType, outputStream);
	}
	
	/**
	 * Restituisce il riferimento del file da comprimere
	 * 
	 * @param path path relativo del file da comprimere
	 * @param extension estensione del file compresso
	 * @param filesize dimensioni del file
	 * @return ArchiveEntry riferimento del file da comprimere
	 */
	public static ArchiveEntry createArchiveEntry(String path, String extension, long filesize) {
		if(extension.contains("zip"))
			return new ZipArchiveEntry(path);
		
		else {
			TarArchiveEntry entry = new TarArchiveEntry(path);
			entry.setSize(filesize);
			return entry;
		}
	}
	
	/**
	 * Creazione dell'archivio
	 * 
	 * @param outputStream stream di output del file da creare
	 * @param extension estensione del file compresso da creare
	 * @return ArchiveOutputStream lo stream dell'archivio
	 * @throws ArchiveException
	 */
	public static ArchiveOutputStream createArchive(OutputStream outputStream, String extension) throws ArchiveException {
		String archiveType = ArchiveStreamFactory.ZIP;
		
		if(extension.contains("tar"))
			archiveType = ArchiveStreamFactory.TAR;
		
		ArchiveStreamFactory factory = new ArchiveStreamFactory();
		return factory.createArchiveOutputStream(archiveType, outputStream);
	}
	
	/**
	 * Restituisce true se il file MAG è stato creato attraverso l'editor
	 * 
	 * @param path path del file
	 * @return boolean true se il file MAG è stato creato attraverso l'editor, false altrimenti
	 */
	public static boolean isMagFromEditor(String path) {
		File file = new File(path);
		return file.getName().startsWith("teca_digitale_editor");
	}
	
	/**
	 * Restituisce l'ID della bozza MAG dato il file
	 * 
	 * @param path path del file
	 * @return String ID della bozza MAG
	 */
	public static String getDraftIDFromFileName(String path) {
		File file = new File(path);
		return file.getName().replace("teca_digitale_editor[", "").replace("].xml", "");
	}
	
	/**
	 * Restituisce true se il file XML è un MAG
	 * 
	 * @param xmlFile file XML
	 * @return boolean true se il file è un MAG, false se è un oggetto digitale
	 */
	public static boolean isMag(File xmlFile) {
		boolean isMag = false;
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(xmlFile));
			String line = null;
			
			while((line = reader.readLine()) != null && !isMag) {
				if(line.contains("<metadigit") || line.contains("<mag:metadigit"))
					isMag = true;
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return isMag;
		} finally {
			if(reader != null) {
				try {
					reader.close();
					
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					return isMag;
				}
			}
		}
		
		return isMag;
	}

	/**
	 * Restituisce true se il file XML è un METS
	 * 
	 * @param xmlFile file XML
	 * @return boolean true se il file è un METS, false se è un oggetto digitale
	 */
	public static boolean isMets(File xmlFile) {
		boolean isMag = false;
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(xmlFile));
			String line = null;
			
			while((line = reader.readLine()) != null && !isMag) {
				if(line.contains("<mets ") || line.contains("<METS:mets "))
					isMag = true;
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return isMag;
		} finally {
			if(reader != null) {
				try {
					reader.close();
					
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					return isMag;
				}
			}
		}
		
		return isMag;
	}

	/**
	 * Restituisce l'oggetto di recovery nella lista in base al path del MAG
	 * 
	 * @param pathMag path del MAG
	 * @param recoveryList lista oggetti recovery
	 * @return oggetto reovery
	 */
	public static WaitingMagRecovery getRecoveryByWaitingMag(String pathMag, List<WaitingMagRecovery> recoveryList) {
		for(WaitingMagRecovery recovery : recoveryList) {
			if(pathMag.equals(recovery.getWaitingMag()))
				return recovery;
		}
		
		return null;
	}

	public static String getFileSize(String path) {
		Path thePath = Paths.get(path);
		try {
			long size = Files.size(thePath);
			return ""+size;
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}

	public static String[] getWidthHeight(String path) throws IOException {
		ImageIO.setUseCache(false);
		BufferedImage image = ImageIO.read(new File(path));
		int width = image.getWidth();
		int height = image.getHeight();
		return new String[]{""+width,""+height};
	}

	public static String getDuration(String path) throws IOException {
		FFprobe ffprobe = new FFprobe(ContentStatic.getProperties().getProperty("ffmpegCommand"));
		FFmpegProbeResult probeResult = ffprobe.probe(path);
		double totalSecs = probeResult.getFormat().duration;

		int hours = (int) Math.floor(totalSecs / 3600);
		int minutes = (int) Math.floor((totalSecs % 3600) / 60);
		int seconds = (int) (Math.floor(totalSecs) % 60);
		// int milliseconds = (int) (Math.round((totalSecs - Math.floor(totalSecs)) * 1000));
		String durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		return durationString;
	}
}
