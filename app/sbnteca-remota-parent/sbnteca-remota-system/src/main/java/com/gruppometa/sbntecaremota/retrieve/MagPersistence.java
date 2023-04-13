package com.gruppometa.sbntecaremota.retrieve;

import java.util.List;
import java.util.Properties;

import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import org.w3c.dom.Document;

import com.gruppometa.sbntecaremota.objects.ExternalMagReference;
import com.gruppometa.sbntecaremota.objects.ImportSettings;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.validators.ValidationError;

public interface MagPersistence {
	/**
	 * Recupero metadati MAG
	 * 
	 * CREA OGGETTO MAG/METS PER INDICIZZAZIONE SOLR
     * 
     * @param path path del documento MAG
     * @param document documento MAG
     * @param externalDocuments mappa dei documenti riferiti
     * @param settings impostazioni di importazione
     * @param publicFlag flag di pubblicazione proprio del MAG
     * @param dressFlag flag di vestizione proprio del MAG
     * @param documentFormat formato del documento (MAG, METS)
     * @return Mag bean di riepilogo metadati MAG
	 */
    Mag getMag(String project, String path, Document document,
			List<ExternalMagReference> externalReferences, ImportSettings settings, 
			int publicFlag, int dressFlag, String documentFormat) throws Exception;
   
   /**
    * Apertura file documento MAG
    * 
    * APERTURA FILE MAG
    * 
    * @param id ID
    * @return Document documento MAG
    */
   Document openMag(String id);

   void createMetsFile(Mag currentMag, String pathOfMetsOrMag);

   /**
    * Apertura file documento METS
    * 
    * APERTURA FILE METS
    * 
    * @param id ID del documento
    * @param configuration configurazione import (metadati GEN)
    * 
    * @return Document documento MAG
    */
   Document openMetsAsMag(String id, Properties configuration);
   
   /**
    * Restituisce la lista degli errori in apertura del file
    * 
    * ERRORI APERTURA FILE
    * 
    * @return List<ValidationError> lista errori di validazione
    */
   List<ValidationError> getOpeningErrors();

   VfsFileSystem getVfsFileSystem();

   void setVfsFileSystem(VfsFileSystem vfsFileSystem);

}
