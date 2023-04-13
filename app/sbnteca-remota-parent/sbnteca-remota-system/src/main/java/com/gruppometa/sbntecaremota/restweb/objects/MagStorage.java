package com.gruppometa.sbntecaremota.restweb.objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
// import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.db.DBOaiIdentifier;
import com.gruppometa.sbntecaremota.objects.db.DBOaiIdentifierDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;
import com.gruppometa.sbntecaremota.util.DF;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.Utility;

/**
 * Tale clase si occupa della memorizzazione degli oggetti digitali legati ad un 
 * processo di importazione, sia su file system sia su Solr
 * 
 *
 */
public class MagStorage {
	
	// lista documenti
	private List<SolrInputDocument> importDocs;
	
	// lista delle risorse digitali copiate
	private Map<String, List<String>> digitalResources;
	
	// lista bozze da cancellare
	private List<String> drafts;
	
	// mappa mag importati
	private Map<String, Date> deliveryImportMags;

	// mappa mets importati
	private Map<String, Date> deliveryImportMets;
	
	// server solr
	private String connectionUrl;
	
	// percentuale di massimo storage su disco
	private int MBUsableThreshold;
	
	// cartella di storage risorse digitali
	private String resourceFolder;
	
	// cartella di storage solr
	private String solrFolder;
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(MagStorage.class);
	
	// server solr
	private SolrClient server;
	
	// dao calcolo identifier OAI
	private DBOaiIdentifierDao oaiIdentifierDao;
	
	// lista oai identifier da inserire
	private List<DBOaiIdentifier> oaiIdentifiers;
	
	// delivery
	private MagResourceDelivery delivery;
	
	
	
	/**
	 * Costruttore
	 * 
	 * @param connectionUrl URL di connession Solr
	 * @param MBUsableThreshold numero di megabytes minimo per il quale eseguire la scrittura su disco
	 * @param resourceFolder cartella di memorizzazione risorse
	 * @param solrFolder cartella di memorizzazione dati solr
	 */
	public MagStorage(String connectionUrl, int MBUsableThreshold, 
			String resourceFolder, String solrFolder, 
			DBOaiIdentifierDao oaiIdentifierDao, MagResourceDelivery delivery) {
		
		this.connectionUrl = connectionUrl;
		server = new HttpSolrClient.Builder(connectionUrl).build();
		importDocs = new ArrayList<SolrInputDocument>();
		digitalResources = new HashMap<String, List<String>>();
		drafts = new ArrayList<String>();
		deliveryImportMags = new HashMap<String, Date>();
		deliveryImportMets = new HashMap<String, Date>();
		oaiIdentifiers = new ArrayList<DBOaiIdentifier>();
		this.MBUsableThreshold = MBUsableThreshold;
		this.resourceFolder = resourceFolder;
		this.solrFolder = solrFolder;
		this.oaiIdentifierDao = oaiIdentifierDao;
		this.delivery = delivery;
	}
	
	/**
	 * Aggiunge un MAG alla lista dei MAG da indicizzare, creando le copie delle
	 * risorse digitali su file system
	 * 
	 * AGGIUNTA MAG AL BATCH DI INDICIZZAZIONE IMPORT
	 * 
	 * @param mag oggetto MAG
	 * @return true se il mag è stato aggiunto al batch
	 * @throws IOException 
	 */
	public boolean addMag(Mag mag) {
		Mag testMag = UtilSolr.selectDocumentByPath(mag.getPath());
		String id = testMag!=null? testMag.getIdMag(): null;
		boolean isDraft = false;
		if(testMag!=null && !testMag.getIdMag().startsWith("draft_"))
			mag.setIdMag(testMag.getIdMag());
		else {
			if(testMag!=null && testMag.getIdMag().startsWith("draft_"))
				isDraft = true;
			UtilSolr.createMagIdentifier(mag);
		}
		/**
		 * I.S.: bugfix: se l'identifier esiste nell'indice Solr,
		 * l'oai-identifier non dovrebbe cambiare, ma rimanre lo stesso
		 */
		/**
		 * se un draft allora lascia l'oai identifier lo stesso
		 */
		if(Utility.isMagFromEditor(mag.getPath()) || isDraft){
			String draftID = Utility.getDraftIDFromFileName(mag.getPath());
			String oai = UtilSolr.getOaiIdentifier(draftID);
			if(oai==null && id!=null)
				oai = UtilSolr.getOaiIdentifier(id);
			/**
			 * controllare la modifica degli identificativi?
			 */
			//mag.getIdentifiers()
			if(oai!=null && oai.trim().length()>0) {
				mag.setOaiIdentifier(oai);
			}
			else{
				logger.warn("Oai_identifier not found for draft "+draftID+ ". Creating a new one.");
				this.createOaiIdentifier(mag);
			}
		}
		else
			this.createOaiIdentifier(mag);

		importDocs.add(UtilSolr.createSolrDocument(mag));
		
		try {
			// se è una bozza aggiungi alle bozze
			if(Utility.isMagFromEditor(mag.getPath()))
				drafts.add(Utility.getDraftIDFromFileName(mag.getPath()));

			if(isDraft && id!=null)
				drafts.add(id);

			// altrimenti aggiungi alla lista dei MAG (o METS) da importare per il delivery
			else {
				if(Mag.MAG.equals(mag.getDocumentFormat()))
					deliveryImportMags.put(new File(mag.getPath()).getCanonicalPath(), mag.getTimestamp());
				
				else
					deliveryImportMets.put(new File(mag.getPath()).getCanonicalPath(), mag.getTimestamp());
			}
			
			return this.isStorable(resourceFolder) ? this.copyResources(mag) : false;
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * Esegue l'importazione dei MAG acquisiti con commit
	 * @throws SolrServerException 
	 * 
	 * COMMIT BATCH DI IMPORT
	 * 
	 * @throws IOException
	 */
	public boolean importMags() throws SolrServerException {
		try {
			if(!this.isStorable(solrFolder)) {
				clearAll(true);
				return false;
			}
			
			if(!importDocs.isEmpty()) {
				// commit batch solr
				server.add(importDocs);
				server.commit();
				
				// modifica delivery per mag importati
				delivery.importMags(deliveryImportMags);

				// modifica delivery per mets importati
				delivery.importMets(deliveryImportMets);
				
				// cancella bozza
				if(!drafts.isEmpty())
					server.deleteById(drafts);
				
				server.commit();
				
				// aggiornamento identifier OAI su database
				for(DBOaiIdentifier oi : oaiIdentifiers)
					oaiIdentifierDao.update(oi);
				
				clearAll(false);
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SolrServerException(e.getMessage(), e);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
		
		return true;
	}

	/**
	 * Restituisce vero se la percentuale di spazio utilizzato sul disco non supera la soglia configurata
	 *
	 * 
	 * @return vero o falso
	 * @throws IOException
	 */
	private boolean isStorable(String folder) throws IOException {
		/*
		// calcolo spazio libero
		long available = new DF(new File(folder), DF.DF_INTERVAL_DEFAULT).getAvailable();
		long MBUsable = (long) Math.floor(available / (double) (1024 * 1024));
		
		// spazio libero sufficiente
		boolean storable = MBUsable > MBUsableThreshold;
		
		if(!storable)
			logger.error("Spazio non sufficiente su disco");
		
		return storable;
		*/
		return true;
	}

	/**
	 * Esegue la copia delle risorse digitali sul file system del server
	 * 
	 * @param mag oggetto Mag
	 * @return boolean se la classe è riuscita a copiare i files oppure c'è mancanza di spazio
	 * @throws IOException
	 */
	private boolean copyResources(Mag mag) {
		String magID = mag.getIdMag();
		digitalResources.put(magID, new ArrayList<String>());
		
		for(DeliveryResource deliveryResource : mag.getDigitalResources())
			digitalResources.get(magID).add(deliveryResource.getDeliveryID());
		
		delivery.importResources(mag.getDigitalResources(), mag.getUsageA(), mag.getUsageE());
		return true;
	}
	
	/**
	 * Creazione nuovo identifier OAI
	 * 
	 * @param mag oggetto Mag contenente i metadati
	 */
	private void createOaiIdentifier(Mag mag) {
		// ricerca per ID solr
		Mag solrMag = UtilSolr.selectDocumentById(mag.getIdMag());
		
		if(solrMag != null && solrMag.getOaiIdentifier()!=null && solrMag.getOaiIdentifier().trim().length()>0) {
			mag.setOaiIdentifier(solrMag.getOaiIdentifier());
			return;
		}
		
		// primo identifier
		// I.S. forse da fare un trim() sull'identifier
		String identifier = mag.getIdentifiers().get(0);
		
		// ricerca nel batch
		for(DBOaiIdentifier oi : oaiIdentifiers) {
			if(identifier.equals(oi.getIdentifier())) {
				oi.setCount(oi.getCount() + 1);
				mag.setOaiIdentifier(oi.getIdentifier() + "_" + oi.getCount());
				return;
			}
		}
		
		// ricerca nel database e aggiungi agli oai identifiers del batch
		try {
			DBOaiIdentifier oi = new DBOaiIdentifier(); 
			oi.setIdentifier(identifier);
			
			DBOaiIdentifier search = oaiIdentifierDao.getOAIIdentifierByID(identifier);
			oi.setCount(search == null ? 0 : search.getCount() + 1);
			mag.setOaiIdentifier(oi.getIdentifier() + (oi.getCount() == 0 ? "" : "_" + oi.getCount()));
			oaiIdentifiers.add(oi);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Metodo di pulizia delle liste
	 * 
	 * @param deleteResources true se bisogna rimuovere gli oggetti digitali copiati (arresto)
	 */
	private void clearAll(boolean deleteResources) {
		importDocs.clear();
		deliveryImportMags.clear();
		deliveryImportMets.clear();
		drafts.clear();
		oaiIdentifiers.clear();
		
		if(deleteResources) {
			for(Entry<String, List<String>> entry : digitalResources.entrySet())
				delivery.deleteTecaResources(entry.getKey(), entry.getValue());
		}
		
		digitalResources.clear();
	}

}
