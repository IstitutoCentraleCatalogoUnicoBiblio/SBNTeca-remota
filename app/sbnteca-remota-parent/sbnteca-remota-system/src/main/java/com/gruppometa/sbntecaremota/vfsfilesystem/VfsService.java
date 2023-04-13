package com.gruppometa.sbntecaremota.vfsfilesystem;

import com.gruppometa.data.mets.*;
import com.gruppometa.mets2mag.MetsConvertor;
import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.objects.json.*;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.Utility;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpRetryException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.gruppometa.sbntecaremota.util.Utility.*;
import static com.gruppometa.sbntecaremota.vfsfilesystem.MetadataCreator.makeMetadata;
import static com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem.createVfsFileOutput;

@Component
public class VfsService {

    protected static Logger logger = LoggerFactory.getLogger(VfsService.class);

    protected boolean createMissingProjects = true;
    protected String urlBase;

    public static void saveVfsFile(VfsFile vfsFile) throws SolrServerException, IOException {
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        solr.add(makeSolrInputDocument(vfsFile));
        solr.commit();
        logger.debug("Saved vfsFile, type:"+vfsFile.getVfsType()+", id:"+vfsFile.getId());
    }

    public void save(VfsFile vfsItem) throws SolrServerException, IOException {
        saveVfsFile(vfsItem);
    }

    public String getUrlBase() {
        return urlBase;
    }

    protected MetsConvertor metsConvertor = new MetsConvertor();

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public static boolean exitsInVfs(DeliveryResource resource) {
        return exitsInVfs(resource.getHref());
    }

    public static boolean exitsInVfs(String href) {
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        QueryResponse solrResponse;
        solrQuery.setRows(1);
        solrQuery.setQuery("vfs_path:"+ClientUtils.escapeQueryChars(href));
        try {
            solrResponse = solr.query(solrQuery);
            return solrResponse.getResults().getNumFound()>0;
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return false;
    }

    public List<VfsFile> getDirectories(String filter){
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        QueryResponse solrResponse;
        List<VfsFile> vfsItems = new ArrayList<>();
        try {
            solrQuery = new SolrQuery();
            solrQuery.setRows(1000000);
            solrQuery.setQuery("vfs_type:directory AND -id:app-data");
            solrQuery.addSort(SolrQuery.SortClause.asc("label"));
            solrQuery.addSort(SolrQuery.SortClause.asc("id"));
            solrResponse = solr.query(solrQuery);
            HashSet<String> keys = new HashSet<>();
            for(SolrDocument solrDocument: solrResponse.getResults()){
                vfsItems.add(mapVfsFileResource(null, solrDocument));
                keys.add((String) solrDocument.getFieldValue("id"));
            }
            solrQuery.setQuery("*:*");
            solrQuery.setRows(0);
            solrQuery.setFacet(true);
            solrQuery.set("facet.field","vfs_directory");
            solrResponse = solr.query(solrQuery);
            List<SolrInputDocument> solrInputDocuments = new ArrayList<>();
            for(FacetField.Count count: solrResponse.getFacetFields().get(0).getValues()){
                if(!keys.contains(count.getName())
                        && !count.getName().equals("app-data")
                        && !count.getName().equals("")
                        && count.getCount()>0
                ) {
                    VfsFile vfsItem = new VfsFile();
                    vfsItem.setId(count.getName());
                    vfsItem.setVfsType(VfsFile.TYPE_DIRECTORY);
                    vfsItems.add(vfsItem);
                    if(createMissingProjects )
                        solrInputDocuments.add(makeSolrInputDocument(vfsItem));
                }
            }
            if(solrInputDocuments.size()>0) {
                solr.add(solrInputDocuments);
                solr.commit();
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return vfsItems;
    }

    public VfsFile getDirectoryByOriginalPath(String id){
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("original_path:"+ClientUtils.escapeQueryChars(id));
        solrQuery.setRows(1);
        solrQuery.setFacet(false);
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                VfsFile vfsItem = new VfsFile();
                vfsItem.setId((String) solrDocument.getFieldValue("id"));
                vfsItem.setVfsType(VfsFile.TYPE_DIRECTORY);
                return vfsItem;
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        VfsFile vfsItem = new VfsFile();
        vfsItem.setId(id);
        vfsItem.setVfsType(VfsFile.TYPE_DIRECTORY);
        return vfsItem;
    }

    public List<VfsFile> getContainers(String idDirectory, int start, int rows){
        return getContainers(idDirectory, null, null, start, rows, false);
    }

    public long getContainersCount(String idDirectory, String q){
        long count = 0;
        String originalePath = null;
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRows(0);
        solrQuery.setQuery(
                "vfs_type:"+ClientUtils.escapeQueryChars(VfsFile.TYPE_CONTAINER)+
                        ((idDirectory!=null)?
                                (" AND vfs_directory:"+ ClientUtils.escapeQueryChars(idDirectory)):"") +
                        " AND -vfs_directory:app-data "+
                        (q!=null?(" AND text:*"+ClientUtils.escapeQueryChars(q)+"*"):"")+
                        (originalePath!=null?(" AND " +
                                "(" +
                                "original_path:"+ClientUtils.escapeQueryChars(originalePath)+
                                " OR original_path:*"+ClientUtils.escapeQueryChars(idDirectory+originalePath)+
                                " OR vfs_filename:"+ClientUtils.escapeQueryChars(cut(originalePath))+"" +
                                " OR vfs_path:"+ClientUtils.escapeQueryChars(originalePath)+"" +
                                ")"):""));
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            count = solrResponse.getResults().getNumFound();
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return count;
    }
    public List<VfsFile> getContainers(String idDirectory, String originalePath, String q, int start, int rows,
                                       boolean filterUploaded){
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(
                "vfs_type:"+ClientUtils.escapeQueryChars(VfsFile.TYPE_CONTAINER)+
                        ((idDirectory!=null)?
                (" AND vfs_directory:"+ ClientUtils.escapeQueryChars(idDirectory)):"") +
                " AND -vfs_directory:app-data "+
                        (q!=null?(" AND text:*"+ClientUtils.escapeQueryChars(q)+"*"):"")+
                (originalePath!=null?(" AND " +
                        "(" +
                        "original_path:"+ClientUtils.escapeQueryChars(originalePath)+
                        " OR original_path:*"+ClientUtils.escapeQueryChars(idDirectory+originalePath)+
                        " OR vfs_filename:"+ClientUtils.escapeQueryChars(cut(originalePath))+"" +
                        " OR vfs_path:"+ClientUtils.escapeQueryChars(originalePath)+"" +
                        ")"):""));
        solrQuery.setRows(rows);
        solrQuery.setStart(start);
        List<VfsFile> vfsItems = new ArrayList<>();
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                VfsFile vfsFile = mapVfsFileResource(idDirectory, solrDocument);
                checkPublicAndDraft(vfsFile);
                if(!filterUploaded || vfsFile.getIdPublic()!=null || vfsFile.getVfsPath()==null)
                    vfsItems.add(vfsFile);
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return vfsItems;
    }

    private static void checkPublicAndDraft(VfsFile vfsFile) {
        if(vfsFile.getVfsPath()==null)
            return;
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(//"-draft:1 AND " +
                "path:" + ClientUtils.escapeQueryChars(vfsFile.getVfsPath()));
        solrQuery.setFields("id","draft");
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            for (SolrDocument solrDocument : solrResponse.getResults()) {
                String id = (String) solrDocument.getFieldValue("id");
                vfsFile.setIdPublic(id);
                if("1".equals(""+solrDocument.getFieldValue("draft")))
                    vfsFile.setDraft(true);
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }
    public List<VfsFile> getResources(String idContainer, int start, int rows){
        return getResources(idContainer, start, rows, false);
    }
    public List<VfsFile> getResources(String idContainer, int start, int rows, boolean unused){
        boolean withUrlBase = false;
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("vfs_type:"+VfsFile.TYPE_OBJECT+" " +
                "AND vfs_container:"+ ClientUtils.escapeQueryChars(idContainer)
                +(unused?" AND -used:true":"")
        );
        solrQuery.setRows(rows);
        solrQuery.setStart(start);
        solrQuery.setFacet(false);
        solrQuery.setSorts(List.of(SolrQuery.SortClause.asc("order"),
                SolrQuery.SortClause.asc("vfs_filename")
                ));
        List<VfsFile> vfsItems = new ArrayList<>();
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                VfsFile vfsFile = mapVfsFileResource(idContainer, solrDocument);
                vfsFile.setUrl((withUrlBase?urlBase:"")+"digitalObject/"+vfsFile.getId());
                vfsFile.setPreview((withUrlBase?urlBase:"")+"digitalObject/"+vfsFile.getId()+"?cache=false&mode=preview&w=145&h=145");
                vfsItems.add(vfsFile);
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return vfsItems;
    }

    public static VfsFile mapVfsFileResource(String idContainer, SolrDocument solrDocument) {
        VfsFile vfsItem = new VfsFile();
        vfsItem.setId((String) solrDocument.getFieldValue("id"));
        vfsItem.setFilename((String) solrDocument.getFieldValue("vfs_filename"));
        vfsItem.setVfsPath((String) solrDocument.getFieldValue("vfs_path"));
        vfsItem.setVfsType((String) solrDocument.getFieldValue("vfs_type"));
        vfsItem.setContainer((ArrayList) solrDocument.getFieldValue("vfs_container"));
        vfsItem.setDirectory((ArrayList) solrDocument.getFieldValue("vfs_directory"));
        vfsItem.setDraftOf((String) solrDocument.getFieldValue("draft_of"));
        vfsItem.setOriginalPath((String) solrDocument.getFieldValue("original_path"));
        vfsItem.setContentType((String) solrDocument.getFieldValue("content_type"));
        vfsItem.setMd5((String) solrDocument.getFieldValue("md5"));
        vfsItem.setLabel((String) solrDocument.getFieldValue("label"));
        vfsItem.setOrder((Integer) solrDocument.getFieldValue("order"));
        vfsItem.setUsed((String) solrDocument.getFieldValue("used"));
        vfsItem.setUsage((String) solrDocument.getFieldValue("vfs_usage_s"));
        vfsItem.setFileSize((String) solrDocument.getFieldValue("filesize"));
        vfsItem.setWidth((String) solrDocument.getFieldValue("width"));
        vfsItem.setHeight((String) solrDocument.getFieldValue("height"));
        vfsItem.setDuration((String) solrDocument.getFieldValue("duration"));
        vfsItem.setTecaPath((String) solrDocument.getFieldValue("teca_path"));
        vfsItem.setResourceType((String) solrDocument.getFieldValue("resource_type"));
        vfsItem.setFrontespizio((String) solrDocument.getFieldValue("frontespizio"));
        vfsItem.setNote((String) solrDocument.getFieldValue("note"));
        vfsItem.setTimestamp((Date) solrDocument.getFieldValue("timestamp"));
        vfsItem.setParent(idContainer);
        return vfsItem;
    }

    public static SolrInputDocument makeSolrInputDocument(VfsFile vfsItem){
        SolrInputDocument solrInputFields = new SolrInputDocument();
        solrInputFields.setField("id", vfsItem.getId());
        solrInputFields.setField("original_path", vfsItem.getOriginalPath());
        solrInputFields.setField("resource_type", vfsItem.getResourceType());
        if(!StringUtils.isEmpty(vfsItem.getLabel()))
            solrInputFields.setField("label", vfsItem.getLabel());
        else
            solrInputFields.setField("label", vfsItem.getFilename());
        solrInputFields.setField("vfs_path", vfsItem.getVfsPath());
        solrInputFields.setField("vfs_container", vfsItem.getContainer());
        solrInputFields.setField("vfs_filename", vfsItem.getFilename());
        solrInputFields.setField("vfs_directory", vfsItem.getDirectory());
        solrInputFields.setField("vfs_type", vfsItem.getVfsType());
        solrInputFields.setField("content_type", vfsItem.getContentType());
        solrInputFields.setField("md5", vfsItem.getMd5());
        solrInputFields.setField("order", vfsItem.getOrder());
        solrInputFields.setField("used", vfsItem.getUsed());
        solrInputFields.setField("vfs_usage_s", vfsItem.getUsage());
        solrInputFields.setField("filesize", vfsItem.getFileSize());
        solrInputFields.setField("width", vfsItem.getWidth());
        solrInputFields.setField("height", vfsItem.getHeight());
        solrInputFields.setField("duration", vfsItem.getDuration());
        solrInputFields.setField("draft_of", vfsItem.getDraftOf());
        solrInputFields.setField("frontespizio", vfsItem.getFrontespizio());
        return solrInputFields;
    }


    public VfsFile getResourceByVfsPathAndHref(String vfsPath, String href, boolean update) {
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = getSolrQuery();
        solrQuery.setQuery("vfs_type:"+VfsFile.TYPE_CONTAINER+
                        " AND vfs_path:"+ ClientUtils.escapeQueryChars(vfsPath)
        //        + " AND vfs_filename:"+ClientUtils.escapeQueryChars(href)
        );
        VfsFile container = null;
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                container = mapVfsFileResource(null, solrDocument);
                break;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        if(container!=null) {
            String draftof = container.getDraftOf();
            if(container.getDraftOf()!=null){
                solrQuery.setQuery("vfs_type:" + VfsFile.TYPE_CONTAINER + " AND" +
                        " (vfs_container:"+ClientUtils.escapeQueryChars(draftof)
                        + " OR id:"+ClientUtils.escapeQueryChars(draftof)
                        +" OR vfs_path:"+ClientUtils.escapeQueryChars(draftof)+ ")");
                container = null;
                try {
                    QueryResponse solrResponse = solr.query(solrQuery);
                    for(SolrDocument solrDocument: solrResponse.getResults()){
                        container = mapVfsFileResource(null, solrDocument);
                        break;
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
            if(container!=null) {
                VfsFile vfsFile = getVfsFile(href, solr, solrQuery, container, update);
                if (vfsFile != null) return vfsFile;
            }
            else{
                logger.warn("Original Container not found "+draftof);
            }
        }
        else{
            logger.warn("Container not found "+vfsPath);
        }
        return null;
    }

    public VfsFile getResourceByIdAndHref(String id, String href) {
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = getSolrQuery();
        return getVfsFile(href, solr, solrQuery, getContainerById(id, VfsFile.TYPE_CONTAINER), true);
    }


    private SolrQuery getSolrQuery() {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRows(1);
        solrQuery.setStart(0);
        solrQuery.setFacet(false);
        return solrQuery;
    }

    public static VfsFile getVfsFileByVfsPath(String vfs_path) {
        return getContainerByVfsPath(vfs_path);
    }
    public static VfsFile getVfsFileByVfsPath(String vfs_path, SolrClient solrClient) {
        return getContainerByVfsPath(vfs_path, solrClient);
    }
    public static VfsFile getContainerByVfsPath(String vfs_path) {
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        return getContainerByVfsPath(vfs_path, solr);
    }
    public static VfsFile getContainerByVfsPath(String vfs_path, SolrClient solrClient) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("vfs_path:"+ClientUtils.escapeQueryChars(vfs_path));
        solrQuery.setRows(10);
        try {
            QueryResponse solrResponse = solrClient.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                return mapVfsFileResource(null, solrDocument);
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }
    public VfsFile getContainerByDirectoryAndFilename(String project, String vfs_path) {
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("vfs_filename:"+ClientUtils.escapeQueryChars(vfs_path)+
            " AND vfs_directory:"+ClientUtils.escapeQueryChars(project));
        solrQuery.setRows(10);
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                return mapVfsFileResource(null, solrDocument);
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    public VfsFile getContainerByDirectoryAndId(String project, String vfs_path) {
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("(id:"+ClientUtils.escapeQueryChars(project+vfs_path)+
                " OR id:"+ClientUtils.escapeQueryChars(vfs_path)+
                ") AND vfs_directory:"+ClientUtils.escapeQueryChars(project));
        solrQuery.setRows(10);
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                return mapVfsFileResource(null, solrDocument);
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    public VfsFile getContainerById(String id){
        return getContainerById(id, null);
    }

    public static VfsFile getObjectById(String id){
        return getContainerById(id, null);
    }

    public VfsFile getVfsFileById(String id, String vfsType) {
        return getContainerById(id, vfsType);
    }

    public static VfsFile getContainerById(String id, String vfsType) {
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("id:"+ClientUtils.escapeQueryChars(id));
        solrQuery.setRows(10);
        try {
            QueryResponse solrResponse = solrClient.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                VfsFile vfsFile = mapVfsFileResource(null, solrDocument);
                checkPublicAndDraft(vfsFile);
                return vfsFile;
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        if(VfsFile.TYPE_DIRECTORY.equals(vfsType)){
            VfsFile vfsFile = new VfsFile();
            vfsFile.setVfsType(VfsFile.TYPE_DIRECTORY);
            vfsFile.setId(id);
            return vfsFile;
        }
        return null;
    }

    public static VfsFile getContainerByVfsPath(String path, String vfsType) {
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("vfs_path:"+ClientUtils.escapeQueryChars(path));
        solrQuery.setRows(10);
        try {
            QueryResponse solrResponse = solrClient.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                VfsFile vfsFile = mapVfsFileResource(null, solrDocument);
                checkPublicAndDraft(vfsFile);
                return vfsFile;
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    public static VfsFile getContainerById(SolrClient solrClient, String id) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("id:"+ClientUtils.escapeQueryChars(id));
        solrQuery.setRows(10);
        try {
            QueryResponse solrResponse = solrClient.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                return mapVfsFileResource(null, solrDocument);
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    public static VfsFile getVfsFileByOriginalPath(String originalPath, SolrClient solrClient) {
        //SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("original_path:"+ClientUtils.escapeQueryChars(originalPath));
        solrQuery.setSort(SolrQuery.SortClause.desc("timestamp"));
        solrQuery.setRows(10);
        try {
            QueryResponse solrResponse = solrClient.query(solrQuery);
            if(solrResponse.getResults().getNumFound()>1){
                logger.warn("Found more than one file for "+originalPath);
            }
            for(SolrDocument solrDocument: solrResponse.getResults()){
                return mapVfsFileResource(null, solrDocument);
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    public String deleteDraftFile(String path){
        VfsFile vfsFile = getVfsFileByVfsPath(path);
        if(vfsFile!=null) {
            VfsFile vfsFileMain = null;
            if(vfsFile.getDraftOf()!=null) {
                try {
                    vfsFileMain = getContainerById(vfsFile.getDraftOf());
                    String id = UtilSolr.getMagIDFromPath(vfsFileMain.getPath());
                    UtilSolr.setFieldSolr(id, "draft", "0", true);
                } catch (SolrServerException e) {
                    logger.error("", e);
                }
            }
            if(deleteObjectById(vfsFile.getId(), VfsFile.TYPE_CONTAINER).getStatus() == 0)
                return vfsFileMain.getVfsPath();
        }
        else {
            logger.error("File not found "+path);
        }
        return null;
    }

    public boolean deleteFile(VfsFile vfsFile){
        if(vfsFile.getVfsPath()==null){
            logger.warn("No path for "+vfsFile.getId());
            return true;
        }
        File file = new File(vfsFile.getVfsPath());
        if(file.exists()){
            if(!file.delete()) {
                logger.error("Cannot delete file " + file.getAbsolutePath());
                return false;
            }
        }
        return true;
    }
    public JsonDeleteProject deleteObjectById(String id){
        return deleteObjectById(id, VfsFile.TYPE_OBJECT);
    }

    public JsonDeleteProject deleteObjectById(String id, String type) {
        JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        try {
            VfsFile  vfsFile = getObjectById(id);
            if(vfsFile==null) {
                jsonDeleteProject.setStatus(-2);
                jsonDeleteProject.setMessage("Oggetto con ID "+id+ " non trovato.");
                return jsonDeleteProject;
            }
            if(deleteFile(vfsFile)) {
                solrClient.deleteByQuery("id:" + ClientUtils.escapeQueryChars(id) +
                        " AND vfs_type:" + type);
                solrClient.commit();
            }
        } catch (Exception e) {
            logger.error("", e);
            jsonDeleteProject.setStatus(-2);
            jsonDeleteProject.setMessage(e.getMessage());
        }
        return jsonDeleteProject;
    }

    public JsonDeleteProject deleteContainerById(String id) {
        JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        try {
            VfsFile  vfsFile = getObjectById(id);
            if(vfsFile==null) {
                jsonDeleteProject.setStatus(-2);
                jsonDeleteProject.setMessage("Container con ID "+id+ " non trovato.");
                return jsonDeleteProject;
            }
            List<VfsFile> resources = getResources(id,0,1000000);
            for (VfsFile vfsFileResource :resources) {
                JsonDeleteProject response = deleteObjectById(vfsFileResource.getId());
                if(response.getStatus()!=0){
                    logger.error(response.getMessage());
                    return response;
                }
            }
            String mag = UtilSolr.getMagIDFromPath(vfsFile.getPath());
            if(mag!=null){
                // solo se è stato pubblicato
                UtilSolr.setFieldSolr(mag, "deleted","1", true);
            }
            if(deleteFile(vfsFile)) {
                solrClient.deleteByQuery("id:" + ClientUtils.escapeQueryChars(id) + " AND vfs_type:" + VfsFile.TYPE_CONTAINER);
                solrClient.commit();
            }
        } catch (Exception e) {
            logger.error("", e);
            jsonDeleteProject.setStatus(-2);
            jsonDeleteProject.setMessage(e.getMessage());
        }
        return jsonDeleteProject;
    }

    public Object deleteDirectoryById(String id) {
        JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        try {
            solrClient.deleteByQuery("id:"+ClientUtils.escapeQueryChars(id)+ " AND vfs_type:"+VfsFile.TYPE_DIRECTORY);
            solrClient.commit();
        } catch (Exception e) {
            logger.error("", e);
            jsonDeleteProject.setStatus(-2);
            jsonDeleteProject.setMessage(e.getMessage());
        }
        return jsonDeleteProject;
    }

    public JsonDeleteProject updateVfsFile(VfsFile vfsFile){
        return updateVfsFile(vfsFile, false);
    }

    public JsonDeleteProject updateVfsFile(VfsFile vfsFile, boolean extraUpdates) {
        JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        try {
            SolrInputDocument doc = new SolrInputDocument();
            /**
             * creazione
             */
            if("".equals(vfsFile.getId())){
                vfsFile.setId(vfsFile.getLabel());
                if(VfsFile.TYPE_OBJECT.equals(vfsFile.getVfsType())) {
                    vfsFile.setContainer(List.of(vfsFile.getParent()));
                }
                doc = makeSolrInputDocument(vfsFile);
            }
            else if( "0".equals(vfsFile.getId())){
                vfsFile.setId(
                        getResourceDeliveryID(new File(""+System.currentTimeMillis()+"/"+vfsFile.getLabel()), false));
                if(VfsFile.TYPE_OBJECT.equals(vfsFile.getVfsType())) {
                    vfsFile.setContainer(List.of(vfsFile.getParent()));
                }
                doc = makeSolrInputDocument(vfsFile);
            }
            /**
             * modifica
             */
            else{
                doc.addField("id", vfsFile.getId());
                jsonDeleteProject.setMessage(vfsFile.getId());
                Map<String, String> update = new HashMap<>();
                update.put("set", vfsFile.getLabel());
                doc.addField("label", update);
                update = new HashMap<>();
                update.put("set", vfsFile.getParent());
                doc.addField("vfs_container", update);
                update = new HashMap<>();
                update.put("set", vfsFile.getFrontespizio());
                doc.addField("frontespizio", update);
                update = new HashMap<>();
                update.put("set", vfsFile.getUsage());
                doc.addField("vfs_usage_s", update);
                if(extraUpdates) {
                    update = new HashMap<>();
                    update.put("set", vfsFile.getResourceType());
                    doc.addField("resource_type", update);
                    update = new HashMap<>();
                    update.put("set", vfsFile.getVfsPath());
                    doc.addField("vfs_path", update);
                    update = new HashMap<>();
                    update.put("set", vfsFile.getContentType());
                    doc.addField("content_type", update);
                }
            }
            solrClient.add(doc);
            solrClient.commit();
        } catch (Exception e) {
            logger.error("", e);
            jsonDeleteProject.setStatus(-2);
            jsonDeleteProject.setMessage(e.getMessage());
        }
        return jsonDeleteProject;
    }

    public JsonDeleteProject upload(String idContainer, String fileName, InputStream uploadStream) {
        VfsFile vfsFile = new VfsFile();
        vfsFile.setFilename(fileName);
        vfsFile.setOrder(getOrderFromFilename(fileName));
        vfsFile.setLabel(getLabelFromFilename(fileName));
        vfsFile.getContainer().add(idContainer);
        vfsFile.setVfsType(VfsFile.TYPE_OBJECT);
        return upload(vfsFile, fileName, uploadStream);
    }

    public JsonDeleteProject upload(VfsFile vfsFile, String fileName, InputStream uploadStream) {
        JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
        String path = "/upload/"+vfsFile.getContainer().get(0)+"/"+System.currentTimeMillis()+"/"+fileName;
        vfsFile.setVfsPath(createVfsFileOutput(
                getFirst(vfsFile.getDirectory()), getFirst(vfsFile.getContainer()),
                path));
        try {
            if(isNewVfsFile(vfsFile)) {
                vfsFile.setId(getResourceDeliveryID(new File(path), false));
            }
            else{
                // TODO cancella il vecchio file se esiste
            }
            File file = new File(vfsFile.getVfsPath());
            file.getParentFile().mkdirs();
            java.nio.file.Path target = Paths.get(file.getAbsolutePath());
            logger.info("VFS: Write content to "+file.getAbsolutePath());
            Files.copy(uploadStream, target);
            try {
//                vfsFile.setFileSize(Utility.getFileSize(file.getAbsolutePath()));
//                vfsFile.setMd5(Utility.getMD5Checksum(file.getAbsolutePath()));
                vfsFile.setContentType(Files.probeContentType(Paths.get(path)));
                makeMetadata(vfsFile, file.getAbsolutePath());
            } catch (Exception e) {
                logger.error("", e);
            }
            save(vfsFile);
        } catch (Exception e) {
            logger.error("", e);
            jsonDeleteProject.setStatus(-2);
            jsonDeleteProject.setMessage(e.getMessage());
        }
        return jsonDeleteProject;
    }

    private static String getFirst(List<String> container) {
        if(container==null || container.size()==0)
            return "";
        else
            return container.get(0);
    }

    private boolean isNewVfsFile(VfsFile vfsFile) {
        return "0".equals(vfsFile.getId()) || StringUtils.isEmpty(vfsFile.getId());
    }

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public JsonSearchResultList search(JsonSearchRequest request, int resultSize, boolean facets) {
        logger.debug("Ricerca VFS in corso");

        SolrClient server = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();

        // query solr
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(buildQuery(request)+ " AND ((vfs_type:object AND vfs_container:*) OR vfs_type:container OR vfs_type:directory)");
        solrQuery.setRows(resultSize);
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField("content_type","vfs_type");
        solrQuery.setStart(request.getStart());
        try {
            QueryResponse solrResponse = server.query(solrQuery);

            // costruzione json risultati
            JsonSearchResultList resultList = new JsonSearchResultList();
            resultList.setSolrQuery(solrQuery.getQuery());
            resultList.setNumberResults(solrResponse.getResults().getNumFound());

            for (SolrDocument solrDocument : solrResponse.getResults()) {
                JsonMagPreview magPreview = new JsonMagPreview();
                magPreview.setId((String) solrDocument.getFieldValue("id"));
                magPreview.getIdentifiers().add((String) solrDocument.getFieldValue("id"));
                magPreview.getTitles().add((String)solrDocument.getFieldValue("label"));
                magPreview.setYearPartNumber((String)solrDocument.getFieldValue("vfs_type"));
                if(solrDocument.getFieldValue("timestamp")!=null)
                    magPreview.setIssuePartName(formatter.format((Date)solrDocument.getFieldValue("timestamp")));
                if(solrDocument.getFirstValue("vfs_container")!=null)
                    magPreview.setIdContainer((String) solrDocument.getFirstValue("vfs_container"));
                String idContainer = magPreview.getIdContainer();
                if("object".equals(magPreview.getYearPartNumber()) && !StringUtils.isEmpty(idContainer)){
                    String path = UtilSolr.getPathFromId(idContainer);
                    String documentFormat = UtilSolr.getValueByField(idContainer, "id", "resource_type", "UrlDeliverySolr");
                    magPreview.setIdMag(UtilSolr.getMagIDFromPath(path));
                    magPreview.setDocumentFormat(documentFormat);
                }
                else {
                    magPreview.setIdMag(UtilSolr.getMagIDFromPath((String) solrDocument.getFirstValue("vfs_path")));
                    magPreview.setDocumentFormat((String) solrDocument.getFirstValue("resource_type"));
                }
                magPreview.setProject((String) solrDocument.getFirstValue("vfs_directory"));
//
//                if (mag.getYear() != null && !mag.getYear().isEmpty())
//                    magPreview.setYearPartNumber(mag.getYear());
//
//                if (mag.getPartNumber() != null && !mag.getPartNumber().isEmpty())
//                    magPreview.setYearPartNumber(mag.getPartNumber());
//
//                if (mag.getIssue() != null && !mag.getIssue().isEmpty())
//                    magPreview.setIssuePartName(mag.getIssue());
//
//                if (mag.getPartName() != null && !mag.getPartName().isEmpty())
//                    magPreview.setIssuePartName(mag.getPartName());
//
                magPreview.setLevel((String) solrDocument.getFieldValue("content_type"));
//                magPreview.getPublicFlags().add(mag.getPublishFlag() + "");
                resultList.getMags().add(magPreview);
            }

            // costruzione faccette
            for (FacetField facet : solrResponse.getFacetFields()) {
                JsonSearchResultFacet jsonFacet = new JsonSearchResultFacet();
                jsonFacet.setName(facet.getName());

                // ordinamento per ordine alfabetico solo se per popolare list box (no per risultati)
                List<String> facetValues = new ArrayList<String>();

                if (resultSize == 0)
                    Collections.sort(facetValues);

                for (FacetField.Count count : facet.getValues()) {
                        JsonSearchResultFacetValue jsonFacetValue = new JsonSearchResultFacetValue();
                        jsonFacetValue.setValue(count.getName());
                        jsonFacetValue.setSize(count.getCount());
                        jsonFacet.getValues().add(jsonFacetValue);
                }

                resultList.getFacets().add(jsonFacet);
            }

            logger.debug("Ricerca VFS terminata, trovati " + resultList.getNumberResults() + " VFS");
            return resultList;
        }
        catch (Exception e){
            logger.error("", e);
            return null;
        }
    }

    private String buildQuery(JsonSearchRequest request) {
        String query = "-vfs_directory:app-data AND -id:app-data";
        if(request.getFieldMap()!=null) {
            for (String key : request.getFieldMap().keySet()) {
                for(String value : request.getFieldMap().get(key)) {
                    if(StringUtils.isEmpty(value))
                        continue;
                    if (query.length() > 0)
                        query += " AND ";
                    query += key+":"+ClientUtils.escapeQueryChars(value);
                }
            }
        }
        if(request.getFieldDateMap()!=null) {
            for (String key : request.getFieldDateMap().keySet()) {
                List<String> values = request.getFieldDateMap().get(key);
                if (!StringUtils.isEmpty(values.get(0))) {
                    if (query.length() > 0)
                        query += " AND ";
                    query += key + ":[" + ClientUtils.escapeQueryChars(values.get(0))+ "T00:00:00.000Z" + " TO *]";
                }
                if (!StringUtils.isEmpty(values.get(1))) {
                    if (query.length() > 0)
                        query += " AND ";
                    query += key + ":[* TO " + ClientUtils.escapeQueryChars(values.get(1))+ "T24:00:00.000Z" + "]";
                }
            }
        }
        if(request.getFacetFilters()!=null){
            for (String key : request.getFacetFilters().keySet()) {
                for(String value : request.getFacetFilters().get(key)) {
                    if(StringUtils.isEmpty(value))
                        continue;
                    if (query.length() > 0)
                        query += " AND ";
                    query += key+":"+ClientUtils.escapeQueryChars(value);
                }
            }
        }
        return query;
    }

    public static void writeToVfs(VfsFile vfsFile, String metsString) throws IOException {
        if(StringUtils.isEmpty(vfsFile.getVfsPath())) {
            /**
             * nuovo file
             */
            String path = "/mets/" + vfsFile.getId() + "/" + System.currentTimeMillis() + "/";
            vfsFile.setVfsPath(createVfsFileOutput(getFirst(vfsFile.getDirectory()), getFirst(vfsFile.getContainer()), path));
        }
        File file = new File(vfsFile.getVfsPath());
        file.getParentFile().mkdirs();
        logger.info("VFS: Write content to "+file.getAbsolutePath());
        FileUtils.writeStringToFile(new File(file.getAbsolutePath()), metsString, "UTF-8");
    }

    public static String copy2vfs(String path, String project, String container) {
        String destination = createVfsFileOutput(project,container,path);
        logger.info("VFS: Write "+path+" to "+destination);
        boolean newVersion = false;
        boolean done = false;
        if(newVersion) {
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(path);
                out = new FileOutputStream(destination);
                IOUtils.copy(in, out);
                done = true;
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                if (in != null)
                    IOUtils.closeQuietly(in);
                if (out != null)
                    IOUtils.closeQuietly(out);
            }
        }
        else
            done = Utility.copyFileFS(path, destination, true);
        if(done)
            return destination;
        return null;
    }

    public static String getLabelFromFilename(String vfsFilename) {
        if(vfsFilename==null)
            return null;
        Matcher matcher = Pattern.compile("^(.*)_([0-9]{4})_(.*)\\.(.*)$").matcher(vfsFilename);
        if(matcher.matches()){
            return matcher.group(3);
        }
        matcher = Pattern.compile("^(.*)_([0-9]{4})\\.(.*)$").matcher(vfsFilename);
        if(matcher.matches()){
            return cutZeros(matcher.group(2));
        }
        return vfsFilename;
    }

    public static Integer getOrderFromFilename(String vfsFilename) {
        if(vfsFilename==null)
            return null;
        Matcher matcher = Pattern.compile("^(.*)_([0-9]{4})_(.*)\\.(.*)$").matcher(vfsFilename);
        if(matcher.matches()){
            return Integer.parseInt(cutZeros(matcher.group(2)));
        }
        matcher = Pattern.compile("^(.*)_([0-9]{4})\\.(.*)$").matcher(vfsFilename);
        if(matcher.matches()){
            return Integer.parseInt(cutZeros(matcher.group(2)));
        }
        return null;
    }

    public static String cutZeros(String numberString) {
        if(numberString==null)
            return null;
        while(numberString.startsWith("0"))
            numberString = numberString.substring(1);
        return numberString;
    }

    private VfsFile getVfsFile(String href, SolrClient solr, SolrQuery solrQuery, VfsFile container, boolean updateFile) {
        long now = System.currentTimeMillis();
        if(container==null){
            logger.error("container is null");
            return null;
        }
        String postfix = "";
        if(href.contains("?")) {
            postfix = href.substring(href.indexOf("?"));
            href = href.substring(0, href.indexOf("?"));
        }
        String idContainer = container.getId();
        solrQuery.setQuery("vfs_type:" + VfsFile.TYPE_OBJECT + " AND " +
                (!href.startsWith("digitalObject/")?("  vfs_container:" + ClientUtils.escapeQueryChars(idContainer) + "  AND "):"") +
                (    href.startsWith("digitalObject/")?
                        ("id:"+ClientUtils.escapeQueryChars(checkOriginal(href.substring("digitalObject/".length())))):
                ("(vfs_filename:" + ClientUtils.escapeQueryChars(href))
                        + " OR vfs_filename:" + ClientUtils.escapeQueryChars(cut(href))+" "
                        + " OR vfs_filename:" + ClientUtils.escapeQueryChars(cutFirstLevel(href))+")"
                ));
        try {
            QueryResponse solrResponse = solr.query(solrQuery);
            for (SolrDocument solrDocument : solrResponse.getResults()) {
                //logger.info("File prepared "+solrDocument.getFieldValue("id")+" in "+(System.currentTimeMillis()-now)+"ms.");
                return addPostFix( mapVfsFileResource(null, solrDocument), postfix);
            }

            File file = searchFile(container, href);
            if (file.exists()) {
                VfsFile vfsFile = getVfsFileByOriginalPath(clearPath(file.getCanonicalFile().getAbsolutePath()), solr);
                if(vfsFile!=null){
                    if(!vfsFile.getContainer().contains(container.getId()) && vfsFile.getContainer().size()==0) {
                        vfsFile.getContainer().add(container.getId());
                        UtilSolr.setFieldSolrDelivery(vfsFile.getId(), "vfs_container", container.getId(),
                                "used","true", true);
                    }
                    if(vfsFile.getContainer().size()>1 || !vfsFile.getContainer().contains(container.getId())){
                        logger.info("Copy resource....");
                        String md5 = Utility.getMD5(container.getId());
                        String idNew = vfsFile.getId()+ "_"+md5;
                        vfsFile.getContainer().remove(container.getId());
                        vfsFile.setUsed("true");
                        save(vfsFile);
                        vfsFile.setId(idNew);
                        vfsFile.getContainer().clear();
                        vfsFile.getContainer().add(container.getId());
                        Files.copy(Path.of(vfsFile.getVfsPath()), Path.of(vfsFile.getVfsPath()+"_"+md5),
                                StandardCopyOption.REPLACE_EXISTING);
                        vfsFile.setVfsPath(vfsFile.getVfsPath()+"_"+md5);
                        vfsFile.setUsed("true");
                        save(vfsFile);
                    }
                    long nowMeta = System.currentTimeMillis();
                    if(updateFile){
                        try {
                            String md5 = Utility.getMD5Checksum(file.getAbsolutePath());
                            if(md5.equals(vfsFile.getMd5())){
                                logger.debug("File "+vfsFile.getId()+" not changed.");
                                nowMeta = System.currentTimeMillis() - nowMeta;
                            }
                            else {
                                Files.copy(file.toPath(), Path.of(vfsFile.getVfsPath()), StandardCopyOption.REPLACE_EXISTING);
                                nowMeta = System.currentTimeMillis();
                                MetadataCreator.makeMetadata(vfsFile, vfsFile.getVfsPath(), md5);
                                nowMeta = System.currentTimeMillis() - nowMeta;
                                save(vfsFile);
                            }
                        }
                        catch(Exception e){
                            logger.error("", e);
                        }
                    }
                    else
                        nowMeta = 0;
                    logger.info("File prepared "+vfsFile.getId()+" in "+(System.currentTimeMillis()-now)+"ms."
                            + (nowMeta!=0?(" Metadata "+nowMeta+"ms."):""));
                    return addPostFix(vfsFile, postfix);
                }
                else {
                    logger.warn("Object not found vfs_container:" + idContainer + " AND vfs_filename:" + href);
                    vfsFile = new VfsFile();
                    vfsFile.setTakeFromOriginalPath(true);
                    Utility.getResourceDeliveryID(file);
                    vfsFile.setOriginalPath(file.getAbsolutePath());
                    vfsFile.getContainer().add(idContainer);
                    return addPostFix(vfsFile, postfix);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    private String checkOriginal(String substring) {
        if(substring==null)
            return null;
        if(substring.endsWith("/original"))
            return substring.substring(0, substring.length()-"/original".length());
        return substring;
    }

    private String cutFirstLevel(String href) {
        String str = cut(href);
        if(str.contains("/")) {
            String str2 = str.substring(str.indexOf("/") + 1);
            if (str2.length() > 3)
                return str2;
        }
        return str;
    }

    private String cut(String href) {
        if(href==null)
            return null;
        if(href.startsWith("./"))
            return href.substring(2);
        if(href.startsWith("/"))
            return href.substring(1);
        return href;
    }

    private VfsFile addPostFix(VfsFile mapVfsFileResource, String postfix) {
        mapVfsFileResource.setPreview(postfix);
        return mapVfsFileResource;
    }

    private File searchFile(VfsFile container, String href) {
        File file = new File(new File(container.getOriginalPath()).getParentFile().getAbsolutePath(), href);
        if(file.exists())
            return file;
        if(href.startsWith("../../../../"))
            href = href.replace("../../../../","../../../");
        String parentDir = new File(container.getOriginalPath()).getParentFile().getAbsolutePath();
        File candidate = new File(parentDir, href);
        if(candidate.exists())
            return candidate;
        if(href.startsWith("../../../"))
            href = Arrays.stream(href.split("/")).
                    collect(Collectors.toList()).subList(6,  href.split("/").length).stream().
                    collect(Collectors.joining("/"));
        candidate = new File(parentDir, href);
        return candidate;
    }

    private String clearPath(String absolutePath) {
        return absolutePath.replace("/./","/");
    }

    public void makeAsUsed(List<VfsFile> resources, String value) {
        if(resources==null)
            return;
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        List<SolrInputDocument> docs = new ArrayList<>();
        for(VfsFile vfsFile: resources){
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", vfsFile.getId());
            Map<String, String> update = new HashMap<>();
            update.put("set", value);
            doc.addField("used", update);
            docs.add(doc);
        }
        try {
            if(docs.size()>0) {
                solrClient.add(docs);
                solrClient.commit();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void updateLabelAndOrder(List<VfsFile> resources) {
        if(resources==null)
            return;
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        List<SolrInputDocument> docs = new ArrayList<>();
        for(VfsFile vfsFile: resources){
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", vfsFile.getId());
            Map<String, String> update = new HashMap<>();
            update.put("set", vfsFile.getLabel());
            doc.addField("label", update);
            update = new HashMap<>();
            update.put("set", ""+vfsFile.getOrder());
            doc.addField("order", update);
            docs.add(doc);
        }
        try {
            if(docs.size()>0) {
                solrClient.add(docs);
                solrClient.commit();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void makeAsUnused(List<VfsFile> resources) {
        makeAsUsed(resources, "false");
    }

    public void makeAsUsed(List<VfsFile> resources) {
        makeAsUsed(resources, "true");
    }

    public void makeAsUsed(List<VfsFile> resources, Mets mets) {
        List<String> ids = metsConvertor.getResourcesIds( mets );
        makeAsUsed(resources.stream().filter(r-> ids.contains(r.getId())).collect(Collectors.toList()), "true");
    }

    public void updateOrderAndLabel(Mets metsForUpdate) {
        List<VfsFile> vfsFiles = new ArrayList<>();
        metsForUpdate.getStructMaps().stream()
                .filter(s -> "PHYSICAL".equalsIgnoreCase(s.getTYPE()))
                .forEach(s -> makeDiv(s.getDiv(), vfsFiles));
        updateLabelAndOrder(vfsFiles);
    }

    private void makeDiv(DivType div, List<VfsFile> vfsFiles) {
        if("FILE".equalsIgnoreCase(div.getTYPE())){
            if(div.getFptrs().size()>0
                    && div.getFptrs().get(0).getFILEID() instanceof FileType
                    && ((FileType)div.getFptrs().get(0).getFILEID()).getFLocats().size()>0
            ) {
                VfsFile vfsFile = new VfsFile();
                vfsFile.setId(MetsConvertor.filterDigitalObject(
                        ((FileType) div.getFptrs().get(0).getFILEID()).getFLocats().get(0).getHref()));
                vfsFile.setOrder(div.getORDER().intValue());
                vfsFile.setLabel(div.getLabelOrderLabel());
                vfsFiles.add(vfsFile);
            }
        }
        div.getDivs().stream().forEach(d-> makeDiv(d, vfsFiles));
    }

    public List<VfsFile> getChildren(VfsFile vfsFileProject) {
        if(vfsFileProject==null)
            return null;
        String id = vfsFileProject.getId();
        if(!StringUtils.isEmpty(vfsFileProject.getDraftOf()))
            id = vfsFileProject.getDraftOf();
        if(vfsFileProject.getVfsType().equals(VfsFile.TYPE_DIRECTORY))
            return getContainers(id, 0,10000);
        if(vfsFileProject.getVfsType().equals(VfsFile.TYPE_CONTAINER))
            return getResources(id, 0,10000);
        return null;
    }

    public List<String> getUnusedFiles(String filter) throws SolrServerException, IOException {
        List<String> files = new ArrayList<>();
        List<String> filesUsed = new ArrayList<>();
        File directory = new File(ContentStatic.getProperties().getProperty("resourceDIRV"));
        makeDirectory(files, directory);
        int size = 100;
        for(int step = 0; (step*size)<files.size(); step++ ){
            int next = (step+1)*size;
            List<String> list = files.subList(step*size, next>files.size()?files.size():next);
            SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setFields("vfs_path");
            solrQuery.setQuery("vfs_path:"+list.stream()
                    .map(s-> ClientUtils.escapeQueryChars(s))
                    .collect(Collectors.joining(" OR vfs_path:")));
            solrQuery.setRows(size*100);
            QueryResponse solrResponse = solrClient.query(solrQuery, SolrRequest.METHOD.POST);
            for(SolrDocument doc: solrResponse.getResults()){
                filesUsed.add((String) doc.getFieldValue("vfs_path"));
            }
        }
        logger.debug("Found "+ filesUsed.size()+ " used files.");
        files.removeIf(f-> filesUsed.contains(f));
        return files;
    }

    private void makeDirectory(List<String> files, File directory) {
        File[] filesNew = directory.listFiles();
        for(File file: filesNew){
            if(file.isDirectory())
                makeDirectory(files, file);
            else
                files.add(file.getAbsolutePath());
        }
    }

    public JsonDeleteProject deleteContainerByMagId(String magID) throws SolrServerException {
        VfsFile containerFile = getContainerByVfsPath(UtilSolr.getPathFromMagId(magID));
        if(containerFile!=null){
            return deleteContainerById(containerFile.getId());
        }
        else
            logger.warn("Not found "+magID);
        return null;
    }

    public void deleteProject(String name) throws SolrServerException, IOException {
        List<VfsFile> vfsFilesUpload = getContainers(name, null,null,0,100000, true);
        List<String> ids = vfsFilesUpload.stream().map(file->file.getId()).collect(Collectors.toList());
        List<VfsFile> vfsFilesAll = getContainers(name, null,null,0,100000, false);
        vfsFilesAll.removeIf(v->ids.contains(v.getId()));
        vfsFilesUpload.stream().forEach(v->deleteObjectById(v.getId()));
        if(vfsFilesAll.size()==0)
            deleteDirectoryById(name);
        /**
         * delete orphanies
         */
        SolrClient solrClient = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlDeliverySolr")).build();
        solrClient.deleteByQuery("vfs_directory:"+ClientUtils.escapeQueryChars(name)+ " AND -vfs_container:*");
        solrClient.commit();
    }
}
