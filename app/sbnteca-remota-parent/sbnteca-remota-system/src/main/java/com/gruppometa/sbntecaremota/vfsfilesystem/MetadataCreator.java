package com.gruppometa.sbntecaremota.vfsfilesystem;

import com.gruppometa.sbntecaremota.objects.DeliveryResource;
import com.gruppometa.sbntecaremota.util.Utility;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.gruppometa.sbntecaremota.util.Utility.getWidthHeight;

public class MetadataCreator {

    protected static Logger logger = LoggerFactory.getLogger(MetadataCreator.class);

    public static void makeMetadata(VfsFile vfsFile,  DeliveryResource resource) throws Exception {
        makeMetadata(vfsFile, resource, null);
    }

    public static void makeMetadata(VfsFile vfsFile,  DeliveryResource resource, String md5) throws Exception {
        SolrInputDocument solrInputFields = new SolrInputDocument();
        makeMetadata(solrInputFields, resource, md5);
        vfsFile.setMd5((String) solrInputFields.getFieldValue("md5"));
        vfsFile.setFileSize((String) solrInputFields.getFieldValue("filesize"));
        vfsFile.setWidth((String) solrInputFields.getFieldValue("width"));
        vfsFile.setHeight((String) solrInputFields.getFieldValue("height"));
        vfsFile.setDuration((String) solrInputFields.getFieldValue("duration"));
    }

    public static void makeMetadata(SolrInputDocument document,  DeliveryResource resource) throws Exception {
        makeMetadata(document, resource, null);
    }
    public static void makeMetadata(SolrInputDocument document,  DeliveryResource resource, String md5) throws Exception {
        document.setField("filesize", Utility.getFileSize(resource.getHref()));
        if(md5!=null)
            document.setField("md5",md5);
        else
            document.setField("md5", Utility.getMD5Checksum(resource.getHref()));
        try {
            String type = resource.getType();
            if(type==null && document.get("content_type")!=null)
                type = (String) document.getFieldValue("content_type");
            if(type==null)
                type = "unknown";
            if (type.contains("image")) {
                String[] widthHeight = getWidthHeight(resource.getHref());
                document.setField("width", widthHeight[0]);
                document.setField("height", widthHeight[1]);
            }
           if (type.contains("video")) {
                document.setField("duration", Utility.getDuration(resource.getHref()));
            }
            if (type.contains("audio")) {
                document.setField("duration", Utility.getDuration(resource.getHref()));
            }
        }
        catch (Exception e){
            document.setField("error_s", e.getMessage());
        }
    }
    public static void makeMetadata(VfsFile vfsFile,  String href) throws Exception {
        makeMetadata(vfsFile, href, null);
    }
    public static void makeMetadata(VfsFile vfsFile, String href, String md5) throws Exception {
        DeliveryResource resource = new DeliveryResource();
        resource.setHref(href);
        resource.setType(vfsFile.getContentType());
        makeMetadata(vfsFile, resource, md5);
    }

    public static void makeMetadata(SolrInputDocument document,  String href) throws Exception {
        DeliveryResource resource = new DeliveryResource();
        resource.setHref(href);
        makeMetadata(document, resource);
    }


}

