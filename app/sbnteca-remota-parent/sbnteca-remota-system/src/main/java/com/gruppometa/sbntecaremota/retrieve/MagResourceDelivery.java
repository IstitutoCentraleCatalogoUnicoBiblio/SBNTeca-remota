package com.gruppometa.sbntecaremota.retrieve;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcess;
import com.gruppometa.sbntecaremota.restweb.objects.MagImportService;

public interface MagResourceDelivery {
	
	/**
	 * Inserimento oggetti digitali
	 * 
	 * INSERIMENTO OGGETTI DIGITALI NEL DELIVERY (UPLOAD)
	 * 
	 * @param resourceList lista oggetti digitali
	 */
	public void insertResources(List<DeliveryResource> resourceList, boolean onlyResources);

	public void insertResources(List<DeliveryResource> resourceList);
	
	/**
	 * Import oggetti digitali
	 * 
	 * IMPORTAZIONE OGGETTI DIGITALI (IMPORT/IMPORT UPDATE/CAMBIO USAGE)
	 * 
	 * @param resourceList lista oggetti digitali
	 * @param usageInternal usage interni
	 * @param usageExternal usage esterni
	 */
	public void importResources(List<DeliveryResource> resourceList, 
			List<String> usageInternal, List<String> usageExternal);
	 
	/**
	 * Cancella i riferimenti agli oggetti digitali importati (DIRECTORY /RESOURCE TECA)
	 * 
	 * CANCELLAZIONE OGGETTI DIGITALI IMPORTATI (CANCELLAZIONE MAG INDICIZZATI)
	 * 
	 * @param magID ID MAG
	 * @param resourceIDs ID oggetti digitali da cancellare
	 */
	public void deleteTecaResources(String magID, List<String> resourceIDs);
	public void deleteTecaResourcesNotVirtual(String magID, List<String> resourceIDs);
	
	/**
	 * Cancella i riferimenti agli oggetti digitali di un progetto (file system)
	 * 
	 * CANCELLAZIONE OGGETTI DIGITALI CARICATI (CANCELLAZIONE PROGETTO)
	 * 
	 * @param projectFile directory del progetto
	 */
	public void deleteOriginalResources(File projectFile);
	
	/**
	 * Restituzione riferimento oggetto digitale da delivery 
	 * (originale = file system, non originale = importato in /resource)
	 * 
	 * RIFERIMENTO OGGETTO DIGITALE
	 * 
	 * @param deliveryID ID oggetto digitale delivery
	 * @param original se orginale prende da file system progetto, altrimenti da file system importati Teca
	 * @return
	 */
	public DataResourceDelivery getResourceByID(String deliveryID, boolean original);
	
	/**
	 * Restituisce il content type di un oggetto digitale
	 * 
	 * @param deliveryID ID oggetto digitale delivery
	 * @return
	 */
	public String getContentType(String deliveryID);
	
	/**
	 * Restituisce l'ID dell'oggetto digitale dato il path originale (file system directory progetto)
	 * 
	 * @param path percorso orginale (directory progetto)
	 * @return ID dell'oggetto digitale
	 */
	public String findIDByPath(String path);
	
	/**
	 * Restituisce il path originale (file system directory progetto) dell'oggetto digitale dato l'ID dell'oggetto digitale
	 * 
	 * @param ID dell'oggetto digitale
	 * @return path percorso orginale (directory progetto)
	 */
	public String findPathByID(String id);
	
	/**
	 * Inserimento MAG nel delivery (UPLOAD)
	 * 
	 * @param uploadMap mappa con timestamp upload
	 */
	public void uploadMags(Map<String, Date> uploadMap);
	
	/**
	 * Inserimento METS nel delivery (UPLOAD)
	 * 
	 * @param uploadMap mappa con timestamp upload
	 */
	public void uploadMets(Map<String, Date> uploadMap);
	
	/**
	 * Import MAG nel delivery (IMPORT)
	 * 
	 * @param uploadMap mappa con timestamp import
	 */
	public void importMags(Map<String, Date> importMap);

	/**
	 * Import METS nel delivery (IMPORT)
	 * 
	 * @param uploadMap mappa con timestamp import
	 */
	public void importMets(Map<String, Date> importMap);
	
	/**
	 * Cancellazione timestamp di import dai MAG importati (CANCELLAZIONE MAG)
	 * 
	 * @param pathMags path dei MAG su file system directory progetto
	 */
	public void deleteImportDocs(List<String> pathMags);
	
	/**
	 * Cancellazione MAG per progetto (CANCELLAZIONE PROGETTO)
	 * 
	 * @param project nome progetto
	 */
	public void deleteDocsByProject(String project);
	
	/**
	 * Selezione MAG caricati per progetto (IMPORT/IMPORT UPDATE/CAMBIO USAGE)
	 * 
	 * @param project nome progetto
	 * @param recent true se recenti
	 * @return lista MAG
	 */
	public List<String> getDocs(String project, boolean recent);

	int uploadUnimarc(DeliveryResource res, MagImportService magImportService, DBTecaProcess dbTecaProcess);
	int uploadCsv(DeliveryResource res, MagImportService magImportService, DBTecaProcess dbTecaProcess);
}
