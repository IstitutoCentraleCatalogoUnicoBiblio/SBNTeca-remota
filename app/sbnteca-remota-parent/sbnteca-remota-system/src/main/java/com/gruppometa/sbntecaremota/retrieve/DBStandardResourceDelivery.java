package com.gruppometa.sbntecaremota.retrieve;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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

public class DBStandardResourceDelivery extends AbstractResourceDelivery {

	// DAO delivery
	private DBResourceDeliveryDao deliveryDao = new DBResourceDeliveryDao();
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(DBStandardResourceDelivery.class);

	@Override
	protected List<SolrInputDocument> updateSolrDocuments(List<DeliveryResource> resourceList, 
			List<String> usageInternal, List<String> usageExternal) {
		
		List<DBResourceDelivery> deliveryIDs = new ArrayList<DBResourceDelivery>();
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		
		try {
			for(DeliveryResource resource : resourceList) {
				DBResourceDelivery dbrd = new DBResourceDelivery();
				dbrd.setId(resource.getDeliveryID());
				dbrd.setResourcePath(null);
				dbrd.setName(new File(resource.getHref()).getName());
				dbrd.setUsageInternal(StringUtils.join(usageInternal, ","));
				dbrd.setUsageExternal(StringUtils.join(usageExternal, ","));
				
				FileInputStream is = new FileInputStream(resource.getHref());
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				IOUtils.copy(is, os);
				dbrd.setResourceBin(os.toByteArray());
				is.close();
				os.close();
				deliveryIDs.add(dbrd);
				
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", resource.getDeliveryID());
				
				Map<String, String> updateResourceType = new HashMap<String, String>();
				updateResourceType.put("set", resource.getType());
				doc.addField("resource_type", updateResourceType);

				Map<String, String> updateOriginalPath = new HashMap<String, String>();
				updateOriginalPath.put("set", resource.getHref());
				doc.addField("original_path", updateOriginalPath);

				Map<String, String> updateContentType = new HashMap<String, String>();
				updateContentType.put("set", Utility.getMime(new File(resource.getHref())));
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
		
		for(SolrDocument doc : resourceDocuments)
			deliveryIDs.add(doc.getFieldValue("id").toString());
		
		try {
			for(String deliveryID : deliveryIDs) {
				SolrInputDocument inputDoc = new SolrInputDocument();
				inputDoc.setField("id", deliveryID);
				inputDoc.setField("_version_", 0);
				
				Map<String, String> updateQuery = new HashMap<String, String>();
				updateQuery.put("set", "");
				inputDoc.addField("database_query", updateQuery);
				
				updateDocs.add(inputDoc);
				
				DBResourceDelivery resourceDB = deliveryDao.findByID(deliveryID);
				String relPath = magID + sep + resourceDB.getName();
				
				File backupResourceFile = new File(ContentStatic.getProperties().
						getProperty("pathMagCancellati"), relPath);
				
				backupResourceFile.getParentFile().mkdirs();
				
				ByteArrayInputStream bis = new ByteArrayInputStream(resourceDB.getResourceBin());
				FileOutputStream fos = new FileOutputStream(backupResourceFile);
				IOUtils.copy(bis, fos);
				bis.close();
	    		fos.close();
			}
			
			deliveryDao.delete(deliveryIDs);
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
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

			if(delivery != null && delivery.getResourceBin() != null) {
				byte[] bin = delivery.getResourceBin();
				resourceDelivery.setLength(bin.length);
				resourceDelivery.setStream(new ByteArrayInputStream(bin));
			}
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		} catch (FileNotFoundException e) {
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
