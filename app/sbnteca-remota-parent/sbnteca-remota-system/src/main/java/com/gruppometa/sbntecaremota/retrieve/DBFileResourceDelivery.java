package com.gruppometa.sbntecaremota.retrieve;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.objects.db.delivery.DBResourceDelivery;
import com.gruppometa.sbntecaremota.objects.db.delivery.DBResourceDeliveryDao;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.Utility;

public class DBFileResourceDelivery extends AbstractFileBasedResourceDelivery {

	// DAO delivery
	private DBResourceDeliveryDao deliveryDao = new DBResourceDeliveryDao();
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(DBFileResourceDelivery.class);
	

	
	@Override
	protected List<SolrInputDocument> updateSolrDocuments(List<DeliveryResource> resourceList, 
			List<String> usageInternal, List<String> usageExternal) {
		

		List<DBResourceDelivery> deliveryIDs = new ArrayList<DBResourceDelivery>();
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		
		try {
			for(DeliveryResource resource : resourceList) {
				StringBuilder builderUsages = new StringBuilder();
				
				for(String usage : resource.getUsage())
		    		builderUsages.append(usage);
		    	
			    // default = 3
			    if(builderUsages.length() == 0 && !"audio".equals(resource.getType()) && !"video".equals(resource.getType()))
			    	builderUsages.append("3");
			    
			    // stabilisce se in esportazione
			    boolean export = false;

			    for(String usage : usageExternal) {
			    	if(builderUsages.toString().contains(usage)) {
			    		export = true;
			    		break;
			    	}
			    }
			    
			    File originalFile = new File(resource.getHref());
			    String fileName = originalFile.getName();
			    int idx = fileName.indexOf(".");
			    String extension = idx < 0 ? fileName : fileName.substring(idx);

				// calcolo path relativo di sistema
				String relativeSystemPath = Utility.createPathFileOutput(builderUsages.toString(), 
						export, resource.getType(), resource.getDeliveryID() + extension, storeType);
				
				File systemFile = new File(ContentStatic.getProperties().
						getProperty("resourceDIR"), relativeSystemPath);
				
				boolean done = Utility.copyFileFS(originalFile.getPath(), systemFile.getPath(), true);
				
				if(!done)
					throw new IOException("Risorsa digitale '" + originalFile.getPath() + "' non copiata");
				
				
				
				DBResourceDelivery dbrd = new DBResourceDelivery();
				dbrd.setId(resource.getDeliveryID());
				dbrd.setName(new File(resource.getHref()).getName());
				dbrd.setUsageInternal(StringUtils.join(usageInternal, ","));
				dbrd.setUsageExternal(StringUtils.join(usageExternal, ","));
				dbrd.setResourceBin(null);
				dbrd.setResourcePath(systemFile.getPath());
				
				SolrInputDocument doc = new SolrInputDocument();
				Map<String, String> updateResourceType = new HashMap<String, String>();
				updateResourceType.put("set", resource.getType());
				doc.addField("resource_type", updateResourceType);

				Map<String, String> updateOriginalPath = new HashMap<String, String>();
				updateOriginalPath.put("set", originalFile.getCanonicalPath());
				doc.addField("original_path", updateOriginalPath);

				Map<String, String> updateContentType = new HashMap<String, String>();
				updateContentType.put("set", Utility.getMime(originalFile));
				doc.addField("content_type", updateContentType);
				
				Map<String, String> updateQuery = new HashMap<String, String>();
				
				updateQuery.put("set", "SELECT * FROM teca_digitale_delivery "
						+ "WHERE id = '" + resource.getDeliveryID() + "'");
				
				doc.addField("database_query", updateQuery);
				docs.add(doc);
			}
			
			deliveryDao.insertOrUpdate(deliveryIDs);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return docs;
	}

	@Override
	protected List<SolrInputDocument> deleteFromStorage(String magID, List<SolrDocument> resourceDocuments) {
		String sep = System.getProperty("file.separator");
		List<SolrInputDocument> updateDocs = new ArrayList<SolrInputDocument>();
		List<String> deliveryIDs = new ArrayList<String>();
		
		try {
			for(SolrDocument doc : resourceDocuments) {
				String deliveryID = doc.getFieldValue("id").toString();
				
				SolrInputDocument inputDoc = new SolrInputDocument();
				inputDoc.setField("id", deliveryID);
				inputDoc.setField("_version_", 0);
				
				Map<String, String> updateQuery = new HashMap<String, String>();
				updateQuery.put("set", "");
				inputDoc.addField("database_query", updateQuery);
				
				updateDocs.add(inputDoc);
				
				deliveryIDs.add(deliveryID);
				DBResourceDelivery dbrd = deliveryDao.findByID(deliveryID);
				
				if(dbrd != null) {
					File resource = new File(dbrd.getResourcePath());
					String relPath = magID + sep + resource.getName();
					
					File backupResourceFile = new File(ContentStatic.getProperties().
							getProperty("pathMagCancellati"), relPath);
					
					backupResourceFile.getParentFile().mkdirs();
		    		Utility.copyFileFS(resource.getPath(), backupResourceFile.getPath(), true);
		    		
					resource.delete();
				}
			}
			
			deliveryDao.delete(deliveryIDs);

			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
		
		return updateDocs;
	}

	@Override
	protected void getResourceStream(SolrDocument document, DataResourceDelivery resourceDelivery, boolean original) {
		try {
			if(original) {
				String originalPath = document.getFieldValue("original_path").toString();
				
				if(originalPath == null || originalPath.isEmpty())
					return;
				
				File file = new File(originalPath);
				resourceDelivery.setLength(file.length());
				resourceDelivery.setStream(new FileInputStream(file));
				return;
			}
			
			DBResourceDelivery delivery = deliveryDao.findByID(document.getFieldValue("id").toString());
			
			if(delivery != null && delivery.getResourcePath() != null && !delivery.getResourcePath().isEmpty()) {
				File resource = new File(delivery.getResourcePath());
				resourceDelivery.setLength(resource.length());
				resourceDelivery.setStream(new FileInputStream(resource));
			}
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteTecaResourcesNotVirtual(String magID, List<String> resourceIDs) {
		deleteTecaResources(magID, resourceIDs);
	}

	@Override
	public List<SolrInputDocument> deleteFromStorageNotVirtual(String magID, List<SolrDocument> documentList) {
		return deleteFromStorage(magID, documentList);
	}
}
