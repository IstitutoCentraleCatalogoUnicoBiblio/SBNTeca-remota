package com.gruppometa.sbntecaremota.retrieve;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.gruppometa.data.mets.Mets;
import com.gruppometa.data.mods.*;
import com.gruppometa.mets2mag.MetsConvertor;
import com.gruppometa.sbnmarc.mag.UnimarcReader;
import com.gruppometa.sbnmarc.mag.object.UnimarcRecord;
import com.gruppometa.sbntecaremota.mets.MetsCreator;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.db.DBTecaProcess;
import com.gruppometa.sbntecaremota.objects.json.JsonImportConfiguration;
import com.gruppometa.sbntecaremota.objects.json.JsonImportReport;
import com.gruppometa.sbntecaremota.restweb.objects.MagImportService;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.Utility;

import static com.gruppometa.sbntecaremota.vfsfilesystem.MetadataCreator.makeMetadata;
import static com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem.createVfsFileOutput;
import static com.gruppometa.sbntecaremota.vfsfilesystem.VfsService.*;

public abstract class AbstractResourceDelivery implements MagResourceDelivery {

	// logger
	private static Logger logger = LoggerFactory.getLogger(AbstractResourceDelivery.class);
	
	// batch delivery
	private static int batchSize = 50;
	
	/**
	 * Crea i documenti da indicizzare su Solr ed effettua lo store delle risorse digitali
	 * 
	 * @param resourceList lista delle risorse
	 * @return List<SolrInputDocument> lista dei documenti Solr
	 */
	protected abstract List<SolrInputDocument> updateSolrDocuments(List<DeliveryResource> resourceList, 
			List<String> usageInternal, List<String> usageExternal);
	
	protected abstract List<SolrInputDocument> deleteFromStorage(String magID, List<SolrDocument> resourceDocuments);
	
	protected abstract void getResourceStream(SolrDocument document, DataResourceDelivery resourceDelivery, boolean original);


	public void insertResources(List<DeliveryResource> resourceList) {
		insertResources(resourceList, false);
	}
	@Override
	public void insertResources(List<DeliveryResource> resourceList, boolean onlyResources) {
		List<SolrInputDocument> solrDocs = new ArrayList<SolrInputDocument>();
		HashMap<String, VfsFile> containers = new HashMap<>();
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		// crea documenti Solr risorse digitali
		logger.info("Get "+resourceList.size()+" resources for insert.");
	    for(DeliveryResource resource : resourceList) {
	    	try {
				long now = System.currentTimeMillis();
	    		File originalFile = new File(resource.getHref());
				// TODO potrebbe esistere in update
				VfsFile vfsFileCheck = getObjectById(resource.getDeliveryID()); // derivato da path + md5 di contenuto
				SolrInputDocument doc = new SolrInputDocument();
				doc.setField("id", resource.getDeliveryID());
				doc.setField("original_path", resource.getHref());
				doc.setField("content_type", Utility.getMime(originalFile));
				String containerLabel = null;
				String container = null;
				if(vfsFileCheck!=null && vfsFileCheck.getContainer()!=null && vfsFileCheck.getContainer().size()>0){
					container = vfsFileCheck.getContainer().get(0);
				}
				else if(resource.getVfsContainer()!=null)
					container =  resource.getVfsContainer();
				if(container==null && onlyResources) {
					containerLabel = originalFile.getParentFile().getName();
					container = originalFile.getParentFile().getName() + "_" + resource.getVfsDirectory()+"_upload";
				}
				doc.setField("vfs_container", container);
				/**
				 * creazione contenitore
				 */
				if(onlyResources) {
					if (container!=null && !containers.containsKey(container)) {
						VfsFile vfsFile = new VfsFile();
						vfsFile.getDirectory().add(resource.getVfsDirectory());
						vfsFile.setId(container);
						vfsFile.setUsage(getListUsages(resource.getUsage()));
						vfsFile.setLabel(containerLabel!=null?containerLabel:container);
						vfsFile.setVfsType(VfsFile.TYPE_CONTAINER);
						containers.put(vfsFile.getId(), vfsFile);
					}
				}
				doc.setField("vfs_directory", resource.getVfsDirectory());
				if(vfsFileCheck!=null && vfsFileCheck.getVfsPath()!=null) {
					// TODO copy durante import
					//  Files.copy(Path.of(resource.getHref()), Path.of(vfsFileCheck.getVfsPath()), StandardCopyOption.REPLACE_EXISTING);
					doc.setField("vfs_path", vfsFileCheck.getVfsPath());
				}
				else {
					/**
					 * TODO potrebbe sovrascrivere....
					 */
					String destination = createVfsFileOutput(resource.getVfsDirectory(),container,resource.getHref());
					VfsFile vfsLastVersion = getVfsFileByVfsPath(destination, solr);
					String add = "";
					if(vfsLastVersion!=null) {
						add = ""+System.currentTimeMillis();
					}
					doc.setField("vfs_path",
							VfsService.copy2vfs(resource.getHref(), resource.getVfsDirectory(),container+add)
					);
				}
				doc.setField("vfs_filename", resource.getVfsFilename());
				doc.setField("vfs_usage_s", getListUsages(resource.getUsage()));
				doc.setField("label", getLabelFromFilename(resource.getVfsFilename()));
				doc.setField("order", getOrderFromFilename(resource.getVfsFilename()));
				if(VfsFile.TYPE_OBJECT.equals(resource.getVfsType())){
					try {
						makeMetadata(doc, resource);
					} catch (Exception e) {
						logger.error("", e);
					}
				}
				doc.setField("vfs_type", resource.getVfsType());
				logger.info("Prepared item "+resource.getDeliveryID()+ " in "+ (System.currentTimeMillis()-now)+"ms.");
				solrDocs.add(doc);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
	    }

	    // indicizzazione con batch
		logger.info("Get "+solrDocs.size()+" solrDocs for insert.");
	    try {
			/**
			 * inserisci containers mancanti
			 */
			for (String idContainer: containers.keySet()){
				if(VfsService.getContainerById(solr, idContainer)==null){
					solrDocs.add(VfsService.makeSolrInputDocument(containers.get(idContainer)));
				}
			}
			// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));
			
			// cancella prima se già esistono
			List<String> ids = new ArrayList<String>();
			
			for(DeliveryResource res : resourceList)
				ids.add(res.getDeliveryID());
			
			if(!ids.isEmpty()) {
				solr.deleteById(ids);
				solr.commit();
			}
			
			// inserimento
			List<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();
			
			while(!solrDocs.isEmpty()) {
				batch.add(solrDocs.remove(0));
				
				if(batch.size() == batchSize) {
					solr.add(batch);
					solr.commit();
					batch.clear();
				}
			}
			
			if(batch.size() > 0) {
				solr.add(batch);
				solr.commit();
				batch.clear();
			}
			
			solr.close();
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private String getListUsages(List<String> usage) {
		if(usage==null || usage.size()==0)
			return "3";
		else
			return usage.stream().collect(Collectors.joining(","));
	}


	@Override
	public void deleteOriginalResources(File projectFile) {
		if(projectFile != null) {
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
			
			// crea query
		    ModifiableSolrParams params = new ModifiableSolrParams();
		    String query = "original_path:" + ClientUtils.escapeQueryChars(projectFile.getPath() + "/") + "*";
		    params.set("q", query); 
		    params.set("rows", Integer.MAX_VALUE);
	
			try {
				// ricerca e cancella
				QueryResponse response = solr.query(params, METHOD.POST);
				SolrDocumentList docs = response.getResults();
				List<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();
				
				for(SolrDocument doc : docs) {
					SolrInputDocument inputDoc = new SolrInputDocument();
					inputDoc.setField("id", (String) doc.getFieldValue("id"));
					inputDoc.setField("_version_", 0);
					
					Map<String, String> updateOriginalPath = new HashMap<String, String>();
					updateOriginalPath.put("set", "");
					inputDoc.addField("original_path", updateOriginalPath);
					
					batch.add(inputDoc);
					
					if(batch.size() == batchSize) {
						solr.add(batch);
						solr.commit();
						batch.clear();
					}
				}
				
				if(batch.size() > 0) {
					solr.add(batch);
					solr.commit();
					batch.clear();
				}
				
				solr.close();
				
			} catch (SolrServerException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void deleteTecaResourcesNotVirtual(String magID, List<String> resourceIDs) {
		deleteTecaResources(magID, resourceIDs, false);
	}

	@Override
	public void deleteTecaResources(String magID, List<String> resourceIDs) {
		deleteTecaResources(magID, resourceIDs, true);
	}
	protected void deleteTecaResources(String magID, List<String> resourceIDs, boolean virtual) {
		if(resourceIDs != null && !resourceIDs.isEmpty()) {
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
	
			try {
				// ricerca e cancella
				List<SolrDocument> documentList = new ArrayList<SolrDocument>();
				int k = 0;
				
				while(resourceIDs.size() > k * batchSize) {
					int batch = batchSize;
					
					if(resourceIDs.size() < (k + 1) * batchSize)
						batch = resourceIDs.size() - (k * batchSize);
					
					documentList.addAll(solr.getById(resourceIDs.subList(k * batchSize, (k * batchSize) + batch)));
					k++;
				}
				logger.debug("Call delete for "+magID+ " and "+ resourceIDs.stream().collect(Collectors.joining(",")));
				List<SolrInputDocument> updateDocs =
						!virtual? deleteFromStorageNotVirtual(magID, documentList):
							deleteFromStorage(magID, documentList);
				
				if(!updateDocs.isEmpty()) {
					solr.add(updateDocs);
					solr.commit();
					updateDocs.clear();
				}
				
				solr.close();
				
			} catch (SolrServerException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public abstract List<SolrInputDocument> deleteFromStorageNotVirtual(String magID, List<SolrDocument> documentList) ;

	@Override
	public DataResourceDelivery getResourceByID(String deliveryID, boolean original) {
		try {
			// ricerca Solr delivery
			String url = ContentStatic.getProperties().getProperty("UrlDeliverySolr");
			//logger.info("Solr "+url);
			SolrClient solr = new HttpSolrClient.Builder(url).build();
			// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));		
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", "id:" + ClientUtils.escapeQueryChars(deliveryID) + "");
			QueryResponse solrResponse = solr.query(params);
			SolrDocumentList docList = solrResponse.getResults();
			
			// ID non presente
			if(docList.isEmpty()) {
				solr.close();
				return null;
			}
			
			else {
				// ricerca risorsa digitale
				SolrDocument doc = docList.get(0);
				DataResourceDelivery data = new DataResourceDelivery();
				data.setContentType((String)doc.getFieldValue("content_type"));
				
				if(doc.getFieldValue("resource_type") != null)
					data.setResourceType(doc.getFieldValue("resource_type").toString());

				if(doc.getFieldValue("vfs_usage_s") != null)
					data.setUsage(doc.getFieldValue("vfs_usage_s").toString());

				this.getResourceStream(doc, data, original);
				solr.close();
				
				if(data.getStream() == null) {
					logger.warn("No stream");
					return null;
				}
				
				return data;
			}
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public void importResources(List<DeliveryResource> resourceList, 
			List<String> usageInternal, List<String> usageExternal) {
		
		try {
			// ricerca Solr delivery
			List<SolrInputDocument> solrDocs = this.updateSolrDocuments(resourceList, usageInternal, usageExternal);
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
			// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));
			List<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();
			
			while(!solrDocs.isEmpty()) {
				batch.add(solrDocs.remove(0));
				
				if(batch.size() == batchSize) {
					solr.add(batch);
					solr.commit();
					batch.clear();
				}
			}
			
			if(batch.size() > 0) {
				solr.add(batch);
				solr.commit();
				batch.clear();
			}
			
			solr.close();
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public String findIDByPath(String path) {
		// inizializza query solr
		try {
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
			// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));		
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", "original_path:\"" + path + "\"");
			params.set("fl", "id");
			
			QueryResponse response = solr.query(params);
			SolrDocumentList docList = response.getResults();
			
			// risultato
			String id = docList.isEmpty() ? null : (String) docList.get(0).get("id");
			solr.close();
			return id;
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public String findPathByID(String id) {
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		
		// inizializza query solr
		try {
			// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));		
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", "id:\"" + id + "\"");
			params.set("fl", "original_path,vfs_path");
			
			QueryResponse response = solr.query(params);
			SolrDocumentList docList = response.getResults();
			
			// risultato
			String path = docList.isEmpty() ? null : (String) docList.get(0).get("original_path");

			if(path==null)
				path = docList.isEmpty() ? null : (String) docList.get(0).get("vfs_path");
			if(path.isEmpty())
				path = null;
			
			solr.close();
			return path;
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	private List<String> getImportedDocIDs(SolrClient solr, List<String> ids) {
		List<String> results = new ArrayList<String>();
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "last_import:[* TO *]");
		params.set("fl", "id");
		params.set("rows", Integer.MAX_VALUE);
		
		if(ids != null && !ids.isEmpty()) {
			try {
				QueryResponse response = solr.query(params);
				SolrDocumentList docList = response.getResults();
				
				for(SolrDocument doc : docList) {
					String id = doc.get("id").toString();
					
					if(ids.contains(id))
						results.add(id);
				}
				/*
				int k = 0;
				
				while(ids.size() > k * batchSize) {
					int batch = batchSize;
					
					if(ids.size() < (k + 1) * batchSize)
						batch = ids.size() - (k * batchSize);
					
					SolrDocumentList docList = solr.getById(ids.subList(k * batchSize, (k * batchSize) + batch), params);
					
					for(SolrDocument doc : docList)
						results.add(doc.get("id").toString());
					
					k++;
				}*/
				
			} catch (SolrServerException e) {
				logger.error(e.getMessage(), e);
				return results;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				return results;
			}
		}
		
		return results;
	}

	@Override
	public void uploadMags(Map<String, Date> uploadMap) {
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));
		
		List<String> magDeliveryIDs = new ArrayList<String>();
		
		for(String path : uploadMap.keySet()) {
			String magDeliveryID = getMagDeliveryID(path);

			magDeliveryIDs.add(magDeliveryID);
		}
		
		List<String> importedMagIDs = this.getImportedDocIDs(solr, magDeliveryIDs);
		List<SolrInputDocument> insertDocs = new ArrayList<SolrInputDocument>();
		List<SolrInputDocument> updateDocs = new ArrayList<SolrInputDocument>();
		
		for(Entry<String, Date> entry : uploadMap.entrySet()) {
			String magDeliveryID = getMagDeliveryID(entry.getKey());

			if(importedMagIDs.contains(magDeliveryID)) {
				SolrInputDocument inputDoc = new SolrInputDocument();
				inputDoc.setField("id", magDeliveryID);
				inputDoc.setField("_version_", 0);
				
				Map<String, Date> updateLastUpload = new HashMap<String, Date>();
				updateLastUpload.put("set", entry.getValue());
				inputDoc.addField("last_upload", updateLastUpload);
				
				Map<String, Boolean> updateRecent = new HashMap<String, Boolean>();
				updateRecent.put("set", true);
				inputDoc.addField("recent", updateRecent);
				updateDocs.add(inputDoc);
			}
			
			else {
				makeDeliveryDoc(insertDocs, entry, magDeliveryID, "mag", "true", true);
			}
		}

		commit(insertDocs, solr, updateDocs);
	}

	private void makeDeliveryDoc(List<SolrInputDocument> insertDocs, Entry<String, Date> entry, String magDeliveryID,
								 String type, String recent, boolean isUpload) {
		SolrInputDocument inputDoc = new SolrInputDocument();

		boolean isVirtual = (entry.getKey().startsWith(ContentStatic.getProperties().getProperty("resourceDIRV")));

		if(isVirtual){
			try {
				String id = UtilSolr.getMagIDFromVfsPath(entry.getKey());
				if(id == null)
					logger.error("Not found "+entry.getKey());
				VfsFile vfsFile = VfsService.getContainerById(id, null);
				inputDoc = VfsService.makeSolrInputDocument(vfsFile);
			} catch (SolrServerException e) {
				logger.error("", e);
				return;
			}
		}
		else {
			String project = getProjectFromPath(entry.getKey());
			inputDoc.setField("id", magDeliveryID);
			inputDoc.setField("vfs_container", magDeliveryID);
			inputDoc.setField("vfs_directory", project);
			inputDoc.setField("vfs_type", "container");
			inputDoc.setField("vfs_filename", getMagDeliveryID(entry.getKey())
					.replaceAll("^"+project+"\\/",""));
			inputDoc.setField("vfs_path", VfsService.copy2vfs(entry.getKey(), project, magDeliveryID));
			inputDoc.setField("original_path", entry.getKey());
		}
		inputDoc.setField("resource_type", type);
		inputDoc.setField("content_type", "application/xml");
		if(isUpload)
			inputDoc.setField("last_upload", entry.getValue());
		else
			inputDoc.setField("last_import", entry.getValue());
		try {
			makeMetadata(inputDoc, entry.getKey());
		} catch (Exception e) {
			logger.error("", e);
		}
		inputDoc.setField("recent", recent);
		insertDocs.add(inputDoc);
	}


	private String getProjectFromPath(String key) {
		String path = getMagDeliveryID(key);
		if(path.contains("/"))
			return path.substring(0, path.indexOf("/"));
		return path;
	}

	private String getMagDeliveryID(String path) {
		String magDeliveryID = path.replaceAll(ContentStatic.getProperties().getProperty("resourceDIRO"),
				"");

		if(magDeliveryID.startsWith("/"))
			magDeliveryID = magDeliveryID.substring(1);
		return magDeliveryID;
	}

	@Override
	public void importMags(Map<String, Date> importMap) {
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));
		
		List<String> magDeliveryIDs = new ArrayList<String>();
		
		for(String path : importMap.keySet()) {
			String magDeliveryID = getMagDeliveryID(path);

			magDeliveryIDs.add(magDeliveryID);
		}
		
		List<String> importedMagIDs = this.getImportedDocIDs(solr, magDeliveryIDs);
		List<SolrInputDocument> insertDocs = new ArrayList<SolrInputDocument>();
		List<SolrInputDocument> updateDocs = new ArrayList<SolrInputDocument>();
		
		for(Entry<String, Date> entry : importMap.entrySet()) {
			String magDeliveryID = getMagDeliveryID(entry.getKey());

			if(importedMagIDs.contains(magDeliveryID)) {
				SolrInputDocument inputDoc = new SolrInputDocument();
				inputDoc.setField("id", magDeliveryID);
				inputDoc.setField("_version_", 0);
				
				Map<String, Date> updateLastUpload = new HashMap<String, Date>();
				updateLastUpload.put("set", entry.getValue());
				inputDoc.addField("last_import", updateLastUpload);
				
				Map<String, Boolean> updateRecent = new HashMap<String, Boolean>();
				updateRecent.put("set", false);
				inputDoc.addField("recent", updateRecent);
				updateDocs.add(inputDoc);
			}
			
			else {
				makeDeliveryDoc(insertDocs, entry, magDeliveryID, "mag", "false", false);
			}
		}

		commit(insertDocs, solr, updateDocs);
	}

	private void commit(List<SolrInputDocument> insertDocs, SolrClient solr, List<SolrInputDocument> updateDocs) {
		try {
			List<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();

			while(!insertDocs.isEmpty()) {
				batch.add(insertDocs.remove(0));

				if(batch.size() == batchSize) {
					solr.add(batch);
					solr.commit();
					batch.clear();
				}
			}

			if(batch.size() > 0) {
				solr.add(batch);
				solr.commit();
				batch.clear();
			}

			while(!updateDocs.isEmpty()) {
				batch.add(updateDocs.remove(0));

				if(batch.size() == batchSize) {
					solr.add(batch);
					solr.commit();
					batch.clear();
				}
			}

			if(batch.size() > 0) {
				solr.add(batch);
				solr.commit();
				batch.clear();
			}

			solr.close();

		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteImportDocs(List<String> pathMags) {
		try {
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
			
			// costruisci documenti da aggiornare
			List<SolrInputDocument> solrDocs = new ArrayList<SolrInputDocument>();
			List<String> magDeliveryIDs = new ArrayList<String>();
			
			for(String path : pathMags) {
				String magDeliveryID = getMagDeliveryID(path);

				magDeliveryIDs.add(magDeliveryID);
			}
			
			List<String> importedMagIDs = this.getImportedDocIDs(solr, magDeliveryIDs);
			
			for(String magDeliveryID : magDeliveryIDs) {
				if(importedMagIDs.contains(magDeliveryID)) {
					SolrInputDocument inputDoc = new SolrInputDocument();
					inputDoc.setField("id", magDeliveryID);
					inputDoc.setField("_version_", 0);
					
					Map<String, Date> updateLastImport = new HashMap<String, Date>();
					updateLastImport.put("set", null);
					inputDoc.addField("last_import", updateLastImport);
					
					Map<String, Boolean> updateRecent = new HashMap<String, Boolean>();
					updateRecent.put("set", true);
					inputDoc.addField("recent", updateRecent);
					
					solrDocs.add(inputDoc);
				}
			}
			
			// query solr di aggiornamento
			List<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();
			
			while(!solrDocs.isEmpty()) {
				batch.add(solrDocs.remove(0));
				
				if(batch.size() == batchSize) {
					solr.add(batch);
					solr.commit();
					batch.clear();
				}
			}
			
			if(batch.size() > 0) {
				solr.add(batch);
				solr.commit();
				batch.clear();
			}
			
			solr.close();
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public List<String> getDocs(String project, boolean recent) {
		List<String> results = new ArrayList<String>();
		
		// inizializza query solr
		try {
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
			ModifiableSolrParams params = new ModifiableSolrParams();
			
			params.set("q", "resource_type:(mag OR mets) " + (recent ? "AND recent:true " : "") + "AND "
					+
					"(id:" + ClientUtils.escapeQueryChars(project + "/") + "* OR" +
					" vfs_directory:"+ClientUtils.escapeQueryChars(project)+" )");
			
			params.set("fl", "id");
			params.set("rows", Integer.MAX_VALUE);
			
			QueryResponse response = solr.query(params);
			SolrDocumentList docList = response.getResults();
			
			// risultato
			for(SolrDocument doc : docList) {
				String id = doc.get("id").toString();
				int idx = id.indexOf("/");
				results.add(idx >= 0 ? id.substring(idx) : id);
			}
			
			solr.close();
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
			return results;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return results;
		}
		
		return results;
	}

	@Override
	public void deleteDocsByProject(String project) {
		SolrClient readSolr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		SolrClient updateSolr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		
		// inizializza query solr
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", "resource_type:mag AND id:" + ClientUtils.escapeQueryChars(project + "/") + "*");
			params.set("fl", "id");
			params.set("start", 0);
			params.set("rows", batchSize);
			
			SolrDocumentList docList = readSolr.query(params).getResults();
			
			while(!docList.isEmpty()) {
				List<String> ids = new ArrayList<String>();
				
				for(SolrDocument doc : docList)
					ids.add(doc.getFieldValue("id").toString());
				
				updateSolr.deleteById(ids);
				updateSolr.commit();
				docList = readSolr.query(params).getResults();
			}
			
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(readSolr != null)
					readSolr.close();
				
				if(updateSolr != null)
					updateSolr.close();
				
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void uploadMets(Map<String, Date> uploadMap) {
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));
		
		List<String> magDeliveryIDs = new ArrayList<String>();
		
		for(String path : uploadMap.keySet()) {
			String magDeliveryID = getMagDeliveryID(path);

			magDeliveryIDs.add(magDeliveryID);
		}
		
		List<String> importedMetsIDs = this.getImportedDocIDs(solr, magDeliveryIDs);
		List<SolrInputDocument> insertDocs = new ArrayList<SolrInputDocument>();
		List<SolrInputDocument> updateDocs = new ArrayList<SolrInputDocument>();
		
		for(Entry<String, Date> entry : uploadMap.entrySet()) {
			String magDeliveryID = getMagDeliveryID(entry.getKey());

			if(importedMetsIDs.contains(magDeliveryID)) {
				SolrInputDocument inputDoc = new SolrInputDocument();
				inputDoc.setField("id", magDeliveryID);
				inputDoc.setField("_version_", 0);
				
				Map<String, Date> updateLastUpload = new HashMap<String, Date>();
				updateLastUpload.put("set", entry.getValue());
				inputDoc.addField("last_upload", updateLastUpload);
				
				Map<String, Boolean> updateRecent = new HashMap<String, Boolean>();
				updateRecent.put("set", true);
				inputDoc.addField("recent", updateRecent);
				updateDocs.add(inputDoc);
			}
			
			else {
				makeDeliveryDoc(insertDocs, entry, magDeliveryID, "mets", "true", true);
			}
		}

		commit(insertDocs, solr, updateDocs);
	}

	@Override
	public void importMets(Map<String, Date> importMap) {
		SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
		// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));
		
		List<String> magDeliveryIDs = new ArrayList<String>();
		
		for(String path : importMap.keySet()) {
			String magDeliveryID = getMagDeliveryID(path);

			magDeliveryIDs.add(magDeliveryID);
		}
		
		List<String> importedMetsIDs = this.getImportedDocIDs(solr, magDeliveryIDs);
		List<SolrInputDocument> insertDocs = new ArrayList<SolrInputDocument>();
		List<SolrInputDocument> updateDocs = new ArrayList<SolrInputDocument>();
		
		for(Entry<String, Date> entry : importMap.entrySet()) {
			String magDeliveryID = getMagDeliveryID(entry.getKey());

			if(importedMetsIDs.contains(magDeliveryID)) {
				SolrInputDocument inputDoc = new SolrInputDocument();
				inputDoc.setField("id", magDeliveryID);
				inputDoc.setField("_version_", 0);
				
				Map<String, Date> updateLastUpload = new HashMap<String, Date>();
				updateLastUpload.put("set", entry.getValue());
				inputDoc.addField("last_import", updateLastUpload);
				
				Map<String, Boolean> updateRecent = new HashMap<String, Boolean>();
				updateRecent.put("set", false);
				inputDoc.addField("recent", updateRecent);
				updateDocs.add(inputDoc);
			}
			
			else {
				makeDeliveryDoc(insertDocs, entry, magDeliveryID,"mets", "false", false);
			}
		}

		commit(insertDocs, solr, updateDocs);
	}

	@Override
	public int uploadUnimarc(DeliveryResource res, MagImportService magImportService, DBTecaProcess dbTecaProcess){
		logger.info("uploadUnimarc " + res.getHref());
		int loaded = 0;
		try {
			UnimarcReader reader = new UnimarcReader(new FileInputStream(res.getHref()),"unimarc2mods");
			while(reader.hasNext()){
				UnimarcRecord unimarcRecord = reader.next();
				logger.info("Get "+unimarcRecord.getId());
				List<Mag> mags = UtilSolr.selectDocumentByIdentifier(unimarcRecord.getId(), null,null);
				if(mags.size()>0 && mags.size()<2) {
					MetsConvertor metsConvertor = new MetsConvertor();
					ModsCollection mods = metsConvertor.readModsFromString(unimarcRecord.getData());
					Mets mets = metsConvertor.readMetsFromString(mags.get(0).getMetsOriginal());
					IdentifierDefinition identifierDefinition = new IdentifierDefinition();
					identifierDefinition.setValue(unimarcRecord.getId());
					identifierDefinition.setType("logicalId");
					mods.getMods().get(0).getAbstractsAndAccessConditionsAndClassifications().add(identifierDefinition);
					mets.getDmdSecs().get(0).getMdWrap().getXmlData().setMods(mods.getMods().get(0));
					String metsNew = metsConvertor.convertMets2Xml(mets);
					UtilSolr.setFieldSolr(mags.get(0).getIdMag(), "metsxmlOriginal", metsNew, true);
					VfsFile vfsFile = new VfsFile();
					vfsFile.setVfsPath(mags.get(0).getPath());
					VfsService.writeToVfs(vfsFile, metsNew);
					String idDelivery = UtilSolr.getMagIDFromVfsPath(vfsFile.getVfsPath());
					JsonImportConfiguration config = MetsCreator.getJsonImportConfiguration(
							mags.get(0).getProject(), "mets:"+idDelivery,"mets");
					JsonImportReport report = magImportService.initializeImport(config, "imp", true);
					report = magImportService.launchImportJob(report.getJobID(), true);
					loaded++;
				}
				else if (dbTecaProcess.getId().contains("CreateContainer")){
					logger.info("Creating container ...");
					MetsConvertor metsConvertor = new MetsConvertor();
					ModsCollection mods = metsConvertor.readModsFromString(unimarcRecord.getData());
					Mets mets = MetsCreator.getMets(unimarcRecord.getId()+"_"+System.currentTimeMillis(),
							new ArrayList<>());
					IdentifierDefinition identifierDefinition = new IdentifierDefinition();
					identifierDefinition.setValue(unimarcRecord.getId());
					identifierDefinition.setType("logicalId");
					mods.getMods().get(0).getAbstractsAndAccessConditionsAndClassifications().add(identifierDefinition);
					mets.getDmdSecs().get(0).getMdWrap().getXmlData().setMods(mods.getMods().get(0));
					String metsNew = metsConvertor.convertMets2Xml(mets);
					//UtilSolr.setFieldSolr(mags.get(0).getIdMag(), "metsxmlOriginal", metsNew, true);
					VfsFile vfsFile = new VfsFile();
					vfsFile.setVfsPath(null);
					vfsFile.setContentType("application/xml");
					vfsFile.setVfsType(VfsFile.TYPE_CONTAINER);
					vfsFile.setResourceType("mets");
					vfsFile.setFilename(unimarcRecord.getId()+".xml");
					vfsFile.setLabel(unimarcRecord.getId());
					vfsFile.getDirectory().add(res.getProject());
					VfsService.writeToVfs(vfsFile, metsNew);
					String idDelivery = unimarcRecord.getId()+"_"+System.currentTimeMillis();
					vfsFile.setId(idDelivery);
					VfsService.saveVfsFile(vfsFile);
					JsonImportConfiguration config = MetsCreator.getJsonImportConfiguration(
							"default", "mets:"+idDelivery,"mets");
					JsonImportReport report = magImportService.initializeImport(config, "imp", true);
					report = magImportService.launchImportJob(report.getJobID(), true);
					loaded++;
				}
				else 
					logger.info("No mag "+unimarcRecord.getId()+" found.");
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return loaded;
	}

	@Override
	public int uploadCsv(DeliveryResource res, MagImportService magImportService, DBTecaProcess dbTecaProcess){
		logger.info("uploadCsv " + res.getHref());
		int loaded = 0;
		try {
			String encoding = "UTF-8";
			String filename = res.getHref();
			BufferedReader br = new BufferedReader((Reader)(encoding != null ? new InputStreamReader(new FileInputStream(filename), encoding) : new FileReader(filename)));
			int i = 0;
			CSVFormat csvFormat = CSVFormat.DEFAULT
					.builder()
					.setDelimiter('\t')
					.build().withFirstRecordAsHeader();
			Iterable<CSVRecord> records = csvFormat.parse(br);
			for(CSVRecord record: records){
				logger.info("Get "+record.get("bid"));
				List<Mag> mags = UtilSolr.selectDocumentByIdentifier(record.get("bid"), null,null);
				if(mags.size()>0 && mags.size()<2) {
					MetsConvertor metsConvertor = new MetsConvertor();
					ModsCollection mods = makeMods(record);
					Mets mets = metsConvertor.readMetsFromString(mags.get(0).getMetsOriginal());
					IdentifierDefinition identifierDefinition = new IdentifierDefinition();
					identifierDefinition.setValue(record.get("bid"));
					identifierDefinition.setType("logicalId");
					mods.getMods().get(0).getAbstractsAndAccessConditionsAndClassifications().add(identifierDefinition);
					mets.getDmdSecs().get(0).getMdWrap().getXmlData().setMods(mods.getMods().get(0));
					String metsNew = metsConvertor.convertMets2Xml(mets);
					UtilSolr.setFieldSolr(mags.get(0).getIdMag(), "metsxmlOriginal", metsNew, true);
					VfsFile vfsFile = new VfsFile();
					vfsFile.setVfsPath(mags.get(0).getPath());
					VfsService.writeToVfs(vfsFile, metsNew);
					String idDelivery = UtilSolr.getMagIDFromVfsPath(vfsFile.getVfsPath());
					JsonImportConfiguration config = MetsCreator.getJsonImportConfiguration(
							mags.get(0).getProject(), "mets:"+idDelivery,"mets");
					JsonImportReport report = magImportService.initializeImport(config, "imp", true);
					report = magImportService.launchImportJob(report.getJobID(), true);
					loaded++;
				}
				else if (dbTecaProcess.getId().contains("CreateContainer")){
					logger.info("Creating container ...");
					MetsConvertor metsConvertor = new MetsConvertor();
					ModsCollection mods = makeMods(record);
					Mets mets = MetsCreator.getMets(record.get("bid")+"_"+System.currentTimeMillis(),
							new ArrayList<>());
					IdentifierDefinition identifierDefinition = new IdentifierDefinition();
					identifierDefinition.setValue(record.get("bid"));
					identifierDefinition.setType("logicalId");
					mods.getMods().get(0).getAbstractsAndAccessConditionsAndClassifications().add(identifierDefinition);
					mets.getDmdSecs().get(0).getMdWrap().getXmlData().setMods(mods.getMods().get(0));
					String metsNew = metsConvertor.convertMets2Xml(mets);
					VfsFile vfsFile = new VfsFile();
					vfsFile.setVfsPath(null);
					vfsFile.setContentType("application/xml");
					vfsFile.setVfsType(VfsFile.TYPE_CONTAINER);
					vfsFile.setResourceType("mets");
					vfsFile.setFilename(record.get("bid")+".xml");
					vfsFile.setLabel(record.get("bid"));
					vfsFile.getDirectory().add(res.getProject());
					VfsService.writeToVfs(vfsFile, metsNew);
					String idDelivery = record.get("bid")+"_"+System.currentTimeMillis();
					vfsFile.setId(idDelivery);
					VfsService.saveVfsFile(vfsFile);
					JsonImportConfiguration config = MetsCreator.getJsonImportConfiguration(
							"default", "mets:"+idDelivery, "mets");
					JsonImportReport report = magImportService.initializeImport(config, "imp", true);
					report = magImportService.launchImportJob(report.getJobID(), true);
					loaded++;
				}
				else
					logger.info("No mag "+record.get("bid")+" found.");
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return loaded;
	}

	public static ModsCollection makeMods(CSVRecord record) {
		ModsCollection modsCollection = new ModsCollection();
		Mods mods = new Mods();
		modsCollection.getMods().add(mods);
		int count = 0;
		if(record.get("creator")!=null){
			for(String str: record.get("creator").split("\\|")) {
				count = makePerson(mods, count, str,"autore");
			}
		}
		if(record.isMapped("contributor") && record.get("contributor")!=null){
			for(String str: record.get("contributor").split("\\|")) {
				count = makePerson(mods, count, str,"contributore");
			}
		}
		if(record.isMapped("topic") && record.get("topic")!=null){
			for(String str: record.get("topic").split("\\|")) {
				count++;
				Subject subject = new Subject();
				StringPlusLanguagePlusAuthority stringPlusLanguagePlusAuthority = new StringPlusLanguagePlusAuthority();
				stringPlusLanguagePlusAuthority.setValue(str);
				subject.getTopicsAndGeographicsAndTemporals().add(stringPlusLanguagePlusAuthority);
				mods.getAbstractsAndAccessConditionsAndClassifications().add(subject);
			}
		}
		if(record.get("title")!=null) {
			count = 0;
			for (String str : record.get("title").split("\\|")) {
				count = makeTitle(mods, count, str, null);
			}
		}
		if(record.isMapped("alternative") && record.get("alternative")!=null) {
			for (String str : record.get("alternative").split("\\|")) {
				count = makeTitle(mods, count, str, "alternative");
			}
		}
		if(record.get("publisher")!=null) {
			count = 0;
			for (String str : record.get("publisher").split("\\|")) {
				OriginInfo originInfo = new OriginInfo();
				Publisher publisher = new Publisher();
				publisher.setValue(str);
				originInfo.getPlacesAndPublishersAndDateIssueds().add(publisher);
				if (record.isMapped("issued")) {
					DateDefinition dateDefinition = new DateDefinition();
					dateDefinition.setValue(record.get("issued"));
					originInfo.getPlacesAndPublishersAndDateIssueds().add(dateDefinition);
				}
				mods.getAbstractsAndAccessConditionsAndClassifications().add(originInfo);
			}
		}
		return modsCollection;
	}

	private static int makeTitle(Mods mods, int count, String str, String type) {
		TitleInfo titleInfo = new TitleInfo();
		titleInfo.setID("TITLE_"+(++count));
		StringPlusLanguage title = new StringPlusLanguage();
		title.setValue(str);
		if(type!=null)
			titleInfo.setTypeTitleInfo(type);
		titleInfo.getTitlesAndSubTitlesAndPartNumbers().add(title);
		mods.getAbstractsAndAccessConditionsAndClassifications().add(titleInfo);
		return count;
	}

	private static int makePerson(Mods mods, int count, String str, String type) {
		Name name = new Name();
		name.setTypeDef("personal");
		name.setID("NAME_"+(++count));
		NamePart namePart = new NamePart();
		namePart.setValue(str);
		Role role = new Role();
		role.getRoleTerms().add(new RoleTerm());
		role.getRoleTerms().get(0).setValue(type);
		name.getNamePartsAndDisplayFormsAndAffiliations().add(role);
		name.getNamePartsAndDisplayFormsAndAffiliations().add(namePart);
		mods.getAbstractsAndAccessConditionsAndClassifications().add(name);
		return count;
	}

	@Override
	public String getContentType(String deliveryID) {
		String contentType = "";
		
		try {
			// ricerca Solr delivery
			SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
			// HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));		
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", "id:\"" + ClientUtils.escapeQueryChars(deliveryID) + "\"");
			params.set("fl", "content_type");
			QueryResponse solrResponse = solr.query(params);
			SolrDocumentList docList = solrResponse.getResults();
			
			// ID non presente
			if(docList.isEmpty()) {
				solr.close();
				return contentType;
			}
			
			// ricerca risorsa digitale
			SolrDocument doc = docList.get(0);
			contentType = doc.getFieldValue("content_type").toString();
			solr.close();
			return contentType;
			
		} catch (SolrServerException e) {
			logger.error(e.getMessage());
			return contentType;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return contentType;
		}
	}

}
