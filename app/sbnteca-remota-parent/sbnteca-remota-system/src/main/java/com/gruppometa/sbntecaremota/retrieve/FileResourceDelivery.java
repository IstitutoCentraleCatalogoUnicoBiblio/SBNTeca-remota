package com.gruppometa.sbntecaremota.retrieve;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsService;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.Utility;

public class FileResourceDelivery extends AbstractFileBasedResourceDelivery {

	// logger
	private static Logger logger = LoggerFactory.getLogger(FileResourceDelivery.class);

	private boolean backupDeletedFiles = false;

	protected VfsFileSystem vfsFileSystem;

	public VfsFileSystem getVfsFileSystem() {
		return vfsFileSystem;
	}

	public void setVfsFileSystem(VfsFileSystem vfsFileSystem) {
		this.vfsFileSystem = vfsFileSystem;
	}

	@Override
	protected List<SolrInputDocument> updateSolrDocuments(List<DeliveryResource> resourceList, 
			List<String> usageInternal, List<String> usageExternal) {
		
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

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
			/**
			 * non copiare le risorse già presenti
			 */
		    if(VfsService.exitsInVfs(resource)){
				continue;
			}
		    File originalFile = new File(resource.getHref());
		    String fileName = originalFile.getName();
		    int idx = fileName.indexOf(".");
		    String extension = idx < 0 ? fileName : fileName.substring(idx);

			// calcolo path relativo di sistema
			String relativeSystemPath = Utility.createPathFileOutput(builderUsages.toString(), 
					export, resource.getType(), resource.getDeliveryID() + extension, storeType);
			
		    
			try {
				File systemFile = new File(ContentStatic.getProperties().
						getProperty("resourceDIR"), relativeSystemPath);
				
				boolean done = Utility.copyFileFS(originalFile.getPath(), systemFile.getPath(), true);
				
				if(!done)
					throw new IOException("Risorsa digitale '" + originalFile.getPath() + "' non copiata");
				
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", resource.getDeliveryID());
				
				Map<String, String> updateResourceType = new HashMap<String, String>();
				updateResourceType.put("set", resource.getType());
				doc.addField("resource_type", updateResourceType);

				Map<String, String> updateProject = new HashMap<String, String>();
				updateProject.put("add", resource.getVfsDirectory());
				doc.addField("vfs_directory", updateProject);

				Map<String, String> updateContainer = new HashMap<String, String>();
				updateContainer.put("add", resource.getVfsContainer());
				doc.addField("vfs_container", updateContainer);

				Map<String, String> updateFilename = new HashMap<String, String>();
				updateFilename.put("set", resource.getVfsFilename());
				doc.addField("vfs_filename", updateFilename);

				Map<String, String> labelFilename = new HashMap<String, String>();
				labelFilename.put("set", resource.getLabel());
				doc.addField("label", labelFilename);

				updateFilename = new HashMap<String, String>();
				updateFilename.put("set", resource.getVfsPath());
				doc.addField("vfs_path", updateFilename);

				Map<String, String> updateVfsType = new HashMap<String, String>();
				updateVfsType.put("set", resource.getVfsType());
				doc.addField("vfs_type", updateVfsType);

				Map<String, String> updateContentType = new HashMap<String, String>();
				updateContentType.put("set", Utility.getMime(originalFile));
				doc.addField("content_type", updateContentType);
				
				Map<String, String> updateOriginalPath = new HashMap<String, String>();
				updateOriginalPath.put("set", originalFile.getCanonicalPath());
				doc.addField("original_path", updateOriginalPath);

				Map<String, String> updateTecaPath = new HashMap<String, String>();
				updateTecaPath.put("set", systemFile.getCanonicalPath());
				doc.addField("teca_path", updateTecaPath);
				
				docs.add(doc);
				
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
	    }
	    
	    return docs;
	}

	@Override
	protected List<SolrInputDocument> deleteFromStorage(String magID, List<SolrDocument> resourceDocuments) {
		try {
			vfsFileSystem.getVfsService().deleteContainerByMagId(magID);
			//deleteFromStorageOld(magID, resourceDocuments);
			logger.info("Delete " + magID);
		}
		catch(Exception e){
			logger.error("", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<SolrInputDocument> deleteFromStorageNotVirtual(String magID, List<SolrDocument> resourceDocuments) {
		String sep = System.getProperty("file.separator");
		List<SolrInputDocument> updateDocs = new ArrayList<SolrInputDocument>();
		
		for(SolrDocument document : resourceDocuments) {
			String path = (String) document.getFieldValue("vfs_path");
			if(path !=null){
				vfsFileSystem.getVfsService().deleteObjectById((String) document.getFieldValue("id"));
			}
			else {
				if(path==null)
					path = (String) document.getFieldValue("teca_path");
				if(path != null) {
					SolrInputDocument inputDoc = new SolrInputDocument();
					inputDoc.setField("id", (String) document.getFieldValue("id"));
					inputDoc.setField("_version_", 0);

					Map<String, String> updateTecaPath = new HashMap<String, String>();
					updateTecaPath.put("set", "");
					inputDoc.addField("teca_path", updateTecaPath);

					updateDocs.add(inputDoc);

					File resource = new File(path);
					if (backupDeletedFiles) {
						String relPath = magID + sep + resource.getName();

						File backupResourceFile = new File(ContentStatic.getProperties().
								getProperty("pathMagCancellati"), relPath);

						backupResourceFile.getParentFile().mkdirs();
						Utility.copyFileFS(resource.getPath(), backupResourceFile.getPath(), true);
					}
					if (resource.exists())
						resource.delete();
				}
			}
		}
		
		return updateDocs;
	}

	@Override
	protected void getResourceStream(SolrDocument document, DataResourceDelivery resourceDelivery, boolean original) {
		String solrField = null;
		
		if(original)
			solrField = "original_path";
		else
			solrField = "teca_path";
		if(solrField != null && (document.getFieldValue(solrField) == null)
				|| StringUtils.isEmpty((String) document.getFieldValue(solrField)))
			solrField = "vfs_path";
		if(solrField != null && document.getFieldValue(solrField) != null) {
			String path = document.getFieldValue(solrField).toString();
			
			if(path == null || path.isEmpty())
				return;
			
			File resource = new File(path);
			File resourceVirtuale = new File(document.getFieldValue("vfs_path").toString());
			
			try {

				if(resource.exists()) {
					resourceDelivery.setResourceName(resource.getName());
					resourceDelivery.setLength(resource.length());
					logger.debug("Use "+resource.getAbsolutePath());
					resourceDelivery.setStream(new FileInputStream(resource));
				}
				else if(resourceVirtuale.exists()) {
					resourceDelivery.setResourceName(resourceVirtuale.getName());
					resourceDelivery.setLength(resourceVirtuale.length());
					logger.debug("Use "+resourceVirtuale.getAbsolutePath());
					resourceDelivery.setStream(new FileInputStream(resourceVirtuale));
				}
				else
					logger.warn("File non esiste '"+ resource.getAbsolutePath()+"' o '"+resourceVirtuale.getAbsolutePath()+"'");
				
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
