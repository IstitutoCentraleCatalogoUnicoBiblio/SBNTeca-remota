package com.gruppometa.sbntecaremota.upgrade;

import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.objects.ExternalMagReference;
import com.gruppometa.sbntecaremota.objects.validators.GenericStruValidator;
import com.gruppometa.sbntecaremota.objects.validators.ValidationResult;
import com.gruppometa.sbntecaremota.retrieve.FileMagPersistence;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceFactory;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceTypes;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;
import com.gruppometa.sbntecaremota.vfsfilesystem.MetadataCreator;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem.createVfsFileOutput;
import static com.gruppometa.sbntecaremota.vfsfilesystem.VfsService.getLabelFromFilename;
import static com.gruppometa.sbntecaremota.vfsfilesystem.VfsService.writeToVfs;

@Component
public class UpgradeComponent {
    protected static Logger logger = LoggerFactory.getLogger(UpgradeComponent.class);

    protected FileMagPersistence fileMagPersistence;

    @Value("${upgrade.src.solr-url}")
    private String srcSolrUrl;
    @Value("${upgrade.src.solr-username}")
    private String srcSolrUsername;
    @Value("${upgrade.enabled:false}")
    private boolean enabled;
    @Value("${upgrade.src.solr-password}")
    private String srcSolrPassword;
    @Value("${upgrade.src.host}")
    private String srcHost;
    @Value("${upgrade.src.host.username}")
    private String srcHostUsername;
    @Value("${upgrade.src.keyfile}")
    private String srcKeyfile;
    @Value("${upgrade.src.directory}")
    private String srcDirectory;
    @Value("${upgrade.src.directory-filter:/home}")
    private String srcDirectoryFilter;

    private Map<String, String> stato = new HashMap<>();


    @Autowired
    protected VfsFileSystem vfsFileSystem;

    protected boolean stopped = false;

    SSHClient sshClient;
    SFTPClient sftpClient;

    public Map<String, String> getStato(){
        return stato;
    }

    @PostConstruct
    protected void postConstruct(){
        fileMagPersistence = (FileMagPersistence) MagPersistenceFactory.create(MagPersistenceTypes.FILE);
        fileMagPersistence.setVfsFileSystem(vfsFileSystem);
        stato.put("stato","idle");
    }

    public String getSrcSolrUrl() {
        return srcSolrUrl;
    }

    public void setSrcSolrUrl(String srcSolrUrl) {
        this.srcSolrUrl = srcSolrUrl;
    }

    protected SolrClient getClient(String core){
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                srcSolrUsername, srcSolrPassword);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create()
                .addInterceptorFirst(new PreemptiveAuthInterceptor())
                .setDefaultCredentialsProvider(provider).build();
        HttpSolrClient solrDesc = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty(
                core.equals("tecadigitale")?
                "UrlSolr":"UrlDeliverySolr"))
                .withHttpClient(client)
                .build();
        solrDesc.setFollowRedirects(true);
        return solrDesc;
    }

    private SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(srcHost);
        String username = srcHostUsername;
        File privateKey = new File(srcKeyfile);
        KeyProvider keys = client.loadKeys(privateKey.getPath());
        client.authPublickey(username, keys);
        //client.authPassword(username, password);
        return client;
    }

    @Async
    public void copyData(String query, int start, int rows, boolean all, boolean forceUpdate, boolean onlyToDress){
        try {
            stato.put("stato", "init");
            stato.put("startTime", "" + System.currentTimeMillis());
            stopped = false;
            int items = 0;
            int objects = 0;
            openSftpSession();
            SolrSchema schemaTecaditigale = new SolrSchema("solr-schemas/schema.xml");
            SolrClient solrSrc = new HttpSolrClient.Builder(srcSolrUrl + "tecadigitale").build();
            SolrClient solrSrcDelivery = new HttpSolrClient.Builder(srcSolrUrl + "tecadigitale_delivery").build();
            SolrClient solrDesc = getClient("tecadigitale");
            SolrClient solrDescDelivery = getClient("delivery");
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setRows(rows);
            if (StringUtils.isEmpty(query))
                solrQuery.setQuery("*:*");
            else
                solrQuery.setQuery(query);
            QueryResponse queryResponse;
            int records = 0;
            int skipped = 0;
            do {
                solrQuery.setStart(start);
                queryResponse = solrSrc.query(solrQuery);
                stato.put("total", "" + queryResponse.getResults().getNumFound());
                records = 0;
                for (SolrDocument solrDocument : queryResponse.getResults()) {
                    records++;
                    if (!forceUpdate && UtilSolr.exists(solrDesc, (String) solrDocument.getFieldValue("id"))) {
                        logger.info("Skipping " + (String) solrDocument.getFieldValue("id") + " because it exists.");
                        skipped++;
                        stato.put("skipped", "" + skipped);
                        continue;
                    }
                    SolrInputDocument doc = toSolrInputDocument(solrDocument);
                    String[] copyFields = schemaTecaditigale.getCopyFields();
                    for (String field : copyFields) {
                        if (doc.get(field) != null)
                            doc.removeField(field);
                    }
                    doc.removeField("_version_");
                    logger.info("Add " + doc.getFieldValue("id") + " to " + ContentStatic.getProperties().getProperty("UrlSolr"));
                    boolean deleted = "1".equals("" + doc.getFieldValue("deleted"));
                    boolean isSpoglio = "a".equals("" + doc.getFieldValue("levelString"));
                    if (deleted)
                        logger.debug("Get deleted item " + doc.getFieldValue("id"));
                    /**
                     * prendi magExternal e tutti gli oggetti digitali, copia i file nel sistema virtuale
                     */
                    List<String> resources = new ArrayList<>();
                    if (!deleted) {
                        addResources(doc, resources, "magxmlExternal");
                        addResources(doc, resources, "magxmlInternal");
                    }
                    Map<String, String> hrefMap = new HashMap<>();
                    /**
                     * spogli vestiti che potrebbero puntare a un oggetto della madre
                     */
                    if (!deleted && isSpoglio && resources.size() > 0) {
                        String[] fields = new String[]{"magxmlExternal", "magxmlInternal"};
                        String md5Id = Utility.getMD5((String) doc.getFieldValue("id"));
                        for (String field : fields) {
                            String fieldValue = (String) doc.getFieldValue(field);
                            for (String idResource : resources) {
                                String key = "digitalObject/" + idResource;
                                String value = "digitalObject/" + md5Id + "_" + idResource;
                                hrefMap.put(key, value);
                                fieldValue = fieldValue.replace(key, value);
                            }
                            doc.setField(field, fieldValue);
                        }
                    }
                    else if(onlyToDress){
                        logger.info("Skipping " + (String) solrDocument.getFieldValue("id") + " because no dress needed.");
                        skipped++;
                        stato.put("skipped", "" + skipped);
                        continue;
                    }
                    /**
                     * vestire: dress the MAG
                     */
                    List<String> originalPaths = new ArrayList<>();
                    if (!deleted && resources.size() == 0) {
                        String[] fields = new String[]{"magxmlExternal", "magxmlOriginal", "magxmlInternal", "magxmlProject"};
                        for (String field : fields) {
                            if(!StringUtils.isEmpty((String) doc.getFieldValue(field))) {
                                Document docMag = UtilXML.convertStringToDocumentXML((String) doc.getFieldValue(field));
                                GenericStruValidator genericStruValidator = new GenericStruValidator();
                                ValidationResult validationResult = genericStruValidator.validate(fileMagPersistence, "", docMag, ContentStatic.getProperties());
                                getExternalDocuments(originalPaths, validationResult.getExternalReferences(), field);
                                String path = (String) doc.getFieldValue("path");
                                fileMagPersistence.setRelativize(false);
                                fileMagPersistence.dressMag(path, docMag, validationResult.getExternalReferences(), hrefMap);
                                if (!field.equals("magxmlOriginal") && !field.equals("magxmlProject"))
                                    addResources(docMag, resources);
                                doc.setField(field, UtilXML.convertDocumentToString(docMag));
                            }
                        }
                    }
                    /**
                     * cartella
                     */
                    if (!deleted) {
                        SolrQuery solrQueryProject = new SolrQuery();
                        solrQueryProject.setRows(1);
                        solrQueryProject.setQuery("id:" + ClientUtils.escapeQueryChars((String) doc.getFieldValue("project")));
                        QueryResponse queryResponseProject = solrSrcDelivery.query(solrQueryProject);
                        if (queryResponseProject.getResults().getNumFound() == 0) {
                            SolrInputDocument solrInputFields = new SolrInputDocument();
                            solrInputFields.setField("id", doc.getFieldValue("project"));
                            solrInputFields.setField("vfs_type", "directory");
                            solrDescDelivery.add(solrInputFields);
                        }
                    }

                    /***
                     * contenitore
                     */
                    String idContainer = null;
                    String original_path = null;
                    if (!deleted) {
                        if(!StringUtils.isEmpty((String) doc.getFieldValue("path"))) {
                            SolrQuery solrQueryContainer = new SolrQuery();
                            solrQueryContainer.setRows(1);
                            original_path = (String) doc.getFieldValue("path");
                            solrQueryContainer.setQuery("original_path:" + ClientUtils.escapeQueryChars(original_path));
                            QueryResponse queryResponseContainer = solrSrcDelivery.query(solrQueryContainer);
                            for (SolrDocument solrDocumentContainer : queryResponseContainer.getResults()) {
                                idContainer = (String) solrDocumentContainer.getFieldValue("id");
                                SolrInputDocument docContainer = makeContainerDoc(doc, idContainer, solrDocumentContainer);
                                solrDescDelivery.add(docContainer);
                            }
                        }
                        /**
                         * se viene cancellato il progetto il container non c'è più.
                         */
                        if(StringUtils.isEmpty(original_path))
                            original_path = doc.getFieldValue("id") +"_no_path";
                        if (idContainer == null) {
                            idContainer = original_path.replace("/storage/tecadigitale/projects","")+"_add";
                            SolrDocument solrDocumentContainer = new SolrDocument();
                            solrDocumentContainer.setField("id", idContainer);
                            solrDocumentContainer.setField("content_type","application/xml");
                            solrDocumentContainer.setField("resource_type","mag");
                            solrDocumentContainer.setField("last_upload","NOW");
                            solrDocumentContainer.setField("last_import","NOW");
                            solrDocumentContainer.setField("note_s","Record was deleted.");
                            solrDocumentContainer.setField("original_path", original_path);
                            SolrInputDocument docContainer = makeContainerDoc(doc, idContainer, solrDocumentContainer);
                            solrDescDelivery.add(docContainer);
                            //throw new IOException("container not found for " + doc.getFieldValue("path"));
                        }
                    }
                    /**
                     * risorse
                     */
                    if (!deleted && resources != null && resources.size() > 0) {
                        SolrQuery solrQueryResources = new SolrQuery();
                        solrQueryResources.setRows(100000);
                        addKeysToResources(resources, hrefMap);
                        List<List<String>> partions = getPartition(resources,100);
                        for(List<String> partion: partions) {
                            solrQueryResources.setQuery(makeQueryString(partion, hrefMap));
                            QueryResponse queryResponseResources = solrSrcDelivery.query(solrQueryResources, SolrRequest.METHOD.POST);
                            for (SolrDocument solrDocumentResource : queryResponseResources.getResults()) {
                                SolrInputDocument docResource = toSolrInputDocument(solrDocumentResource);
                                docResource.removeField("_version_");
                                String key = "digitalObject/" + docResource.getFieldValue("id");
                                if (hrefMap.containsKey(key))
                                    docResource.setField("id", hrefMap.get(key).replace("digitalObject/", ""));
                                String teca_path = (String) docResource.getFieldValue("teca_path");
                                if (teca_path != null) {
                                    String destination = createVfsFileOutput(
                                            (String) doc.getFieldValue("project"), idContainer,
                                            teca_path);
                                    copyRemote(teca_path, destination);
                                    //String vfs_path = VfsService.copy2vfs(teca_path);
                                    //if(vfs_path==null){
                                    //    logger.error("Errore per "+teca_path);
                                    //}
                                    docResource.setField("vfs_type", "object");
                                    docResource.setField("vfs_path", destination);
                                    docResource.setField("used", "true");
                                    docResource.setField("vfs_container", idContainer);
                                    docResource.setField("vfs_filename", getFilename(original_path, docResource, originalPaths));
                                    docResource.setField("label", getLabelFromFilename(
                                            new File((String) docResource.getFieldValue("original_path")).getName()));
                                    docResource.removeField("teca_path");
                                    DeliveryResource resource = new DeliveryResource();
                                    resource.setHref(destination);
                                    resource.setType((String) docResource.getFieldValue("content_type"));
                                    MetadataCreator.makeMetadata(docResource, resource);
                                }
                                solrDescDelivery.add(docResource);
                                objects++;
                            }
                        }
                    }
                    solrDesc.add(doc);
                    items++;
                    stato.put("items", "" + items);
                    stato.put("objects", "" + objects);
                    stato.put("runTime", "" +
                            DurationFormatUtils.formatDuration(
                                    System.currentTimeMillis() - Long.parseLong(stato.get("startTime")), "**H:mm:ss**", true));
                }
                solrDescDelivery.commit();
                solrDesc.commit();
                start += rows;
            }
            while (all && records > 0 && !stopped);
            closeSftpSession();
            stato.put("stato", "fine");
            stato.put("runTime", "" +
                    DurationFormatUtils.formatDuration(
                            System.currentTimeMillis() - Long.parseLong(stato.get("startTime")), "**H:mm:ss**", true));
            stato.put("stopped", "" + stopped);
        }
        catch (Exception e) {
            logger.error("", e);
            stato.put("stato", "ko");
            stato.put("messaggio", e.getMessage());
        }
    }

    private SolrInputDocument makeContainerDoc(SolrInputDocument doc, String idContainer, SolrDocument solrDocumentContainer) throws IOException {
        SolrInputDocument docContainer = toSolrInputDocument(solrDocumentContainer);
        docContainer.removeField("_version_");
        docContainer.setField("vfs_type", "container");
        docContainer.setField("vfs_container", idContainer);
        docContainer.setField("vfs_directory", doc.getFieldValue("project"));
        String destination = createVfsFileOutput(
                (String) doc.getFieldValue("project"), idContainer,
                (String) doc.getFieldValue("path"));
        doc.setField("path", destination);
        VfsFile vfsFile = new VfsFile();
        vfsFile.setVfsPath(destination);
        writeToVfs(vfsFile, (String) doc.getFieldValue("magxmlOriginal"));
        docContainer.setField("vfs_path", destination);
        docContainer.removeField("teca_path");
        return docContainer;
    }

    private void addKeysToResources(List<String> resources, Map<String, String> hrefMap) {
        hrefMap.keySet().stream().forEach( key-> {
            if(!resources.contains(key))
                resources.add(key);
        });
    }


    public static List<List<String>> getPartition(List<String> resources, int partitionSize) {
        List<List<String>> partitions = new ArrayList<>();
        if(resources==null)
            return null;
        if(resources.size()<=partitionSize) {
            partitions.add(resources);
        }
        else{
            for (int i=0; i<resources.size(); i += partitionSize) {
                partitions.add(resources.subList(i, Math.min(i + partitionSize, resources.size())));
            }
        }
        return partitions;
    }

    private String makeQueryString(List<String> resources, Map<String, String> hrefMap) {
        String add = "";
//        if(hrefMap.size()>0){
//            add = "id:"+ hrefMap.keySet().stream()
//                    .map(r-> ClientUtils.escapeQueryChars(r.replace("digitalObject/","")))
//                    .collect(Collectors.joining(" OR id:"));
//        }
        String first = "id:" + resources.stream()
                //.filter(r -> !hrefMap.containsKey("digitalObject/"+r))
                .map(r -> {
                    String value = r;
                    if(hrefMap.containsKey(r))
                        value = r.replace("digitalObject/","");
                    return ClientUtils.escapeQueryChars(value);
                })
                .collect(Collectors.joining(" OR id:"));
        String queryString = first.length()>3?first:"";
        if(queryString.length()>0 && add.length()>0)
            queryString += " OR ";
        queryString += add;
        return queryString;
    }

    private String getFilename(String original_path, SolrInputDocument docResource, List<String> pathFathers) {
        String pathResource = ((String) docResource.getFieldValue("original_path"));
        String parent = new File(original_path).getParentFile().getAbsolutePath();
        if(pathResource.startsWith(parent))
            return pathResource.substring(parent.length()+1);
        for(String path: pathFathers){
            if(new File(path).getParentFile()!=null) {
                String candidate = new File(path).getParentFile().getAbsolutePath();
                if (pathResource.startsWith(candidate))
                    return pathResource.substring(candidate.length() + 1);
            }
        }
        return pathResource;
    }


    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    private void getExternalDocuments(List<String> originalPath, List<ExternalMagReference> externalReferences, String fieldname) throws SolrServerException, IOException {
        if(externalReferences==null)
            return;
        SolrClient solrSrc = new HttpSolrClient.Builder(srcSolrUrl+"tecadigitale").build();
        for(ExternalMagReference externalMagReference: externalReferences){
            String identifier = externalMagReference.getIdentifiers().get(0);
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setFields("id,path,"+fieldname);
            String add2Query = "";
            if(!StringUtils.isEmpty(externalMagReference.getIssue())){
                add2Query+=" AND issue:"+ClientUtils.escapeQueryChars(externalMagReference.getIssue());
            }
            if(!StringUtils.isEmpty(externalMagReference.getYear())){
                add2Query+=" AND year:"+ClientUtils.escapeQueryChars(externalMagReference.getYear());
            }
            solrQuery.setQuery("identifier:"+ClientUtils.escapeQueryChars(identifier)+" AND -levelString:a"+add2Query);
            QueryResponse solrResponse = solrSrc.query(solrQuery);
            for(SolrDocument solrDocument: solrResponse.getResults()){
                externalMagReference.setExternalDocument(UtilXML.convertStringToDocumentXML((String) solrDocument.getFieldValue(fieldname)));
                externalMagReference.setExternalPath((String) solrDocument.getFieldValue("path"));
                if(externalMagReference.getExternalPath()!=null && !originalPath.contains(externalMagReference.getExternalPath()))
                    originalPath.add(externalMagReference.getExternalPath());
                logger.info("Get document father "+solrDocument.getFirstValue("id")+ " field "+fieldname);
                break;
            }
        }
    }

    private void addResources(SolrInputDocument doc, List<String> resources, String fieldName) {
        if (StringUtils.isEmpty((String) doc.getFieldValue(fieldName))) {
            logger.info("No " + fieldName + " for " + doc.getFieldValue("id"));
            return;
        }
        Document document = UtilXML.convertStringToDocumentXML((String) doc.getFieldValue(fieldName));
        addResources(document, resources);
    }

    private void addResources(Document document, List<String> resources) {
        NodeList nodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf","file");
        for(int i = 0; i< nodeList.getLength(); i++) {
            Element file = (Element) nodeList.item(i);
            String href = file.getAttributeNS("http://www.w3.org/TR/xlink", "href");
            if (StringUtils.isEmpty(href))
                href = file.getAttributeNS("http://www.w3.org/1999/xlink", "href");
            String resource = null;
            if(href.startsWith("digitalObject/"))
                resource = href.substring("digitalObject/".length());
            else
                logger.debug("Skipping resource "+href);
            if(resource!=null && !resources.contains(resource)) {
                logger.debug("Get resource " + resource);
                resources.add(resource);
            }
        }
    }

    private void openSftpSession() throws IOException {
        sshClient =  setupSshj();
        sftpClient = sshClient.newSFTPClient();
    }

    private void closeSftpSession() throws IOException {
        sftpClient.close();
        sshClient.disconnect();
    }

    private void copyRemote(String teca_path, String destination) throws IOException {
        String remoteFile = teca_path.startsWith(srcDirectoryFilter) ? teca_path : (srcDirectory + teca_path);
        String localDir = destination;
        try {
            File file = new File(localDir);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            logger.debug(remoteFile + "->" + localDir);
            sftpClient.get(remoteFile, localDir);
        }
        catch (IOException e){
            logger.error("Error while '"+remoteFile + "'->" + localDir, e);
            throw e;
        }
    }

    public static SolrInputDocument toSolrInputDocument(SolrDocument d) {
        SolrInputDocument doc = new SolrInputDocument();
        for (String name : d.getFieldNames()) {
            doc.addField(name, d.getFieldValue(name));
        }
        return doc;
    }

    public void setStato(Map<String, String> stato) {
        this.stato = stato;
    }

    static class PreemptiveAuthInterceptor implements HttpRequestInterceptor {

        public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
            AuthState authState = (AuthState) context.getAttribute(HttpClientContext.TARGET_AUTH_STATE);

            // If no auth scheme available yet, try to initialize it
            // preemptively
            if (authState.getAuthScheme() == null) {
                CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(HttpClientContext.CREDS_PROVIDER);
                HttpHost targetHost = (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
                Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
                if (creds == null) {
                    throw new HttpException("No credentials for preemptive authentication");
                }
                authState.update(new BasicScheme(), creds);
            }
        }
    }
}
