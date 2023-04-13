package com.gruppometa.sbntecaremota.objects.validators;

import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabelMapper {
    protected static Logger logger = LoggerFactory.getLogger(LabelMapper.class);

    public static String getFilename(String resourcePath){
        try {
            String add = "";
            String draft = UtilSolr.getValueByField(resourcePath,
                    "vfs_path", "draft_of","UrlDeliverySolr");
            if(!StringUtils.isEmpty(draft)){
                resourcePath = UtilSolr.getValueByField(draft, "id", "vfs_path", "UrlDeliverySolr");
                add = "_draft";
            }
            String path = UtilSolr.getValueByField(resourcePath,
                    "vfs_path", "original_path", "UrlDeliverySolr");
            if (StringUtils.isEmpty(path))
                path = UtilSolr.getValueByField(resourcePath,
                        "vfs_path", "vfs_filename", "UrlDeliverySolr");
            if(!StringUtils.isEmpty(path)) {
                String directory = ContentStatic.getProperties().getProperty("resourceDIRO");
                if(path.startsWith(directory))
                    path = path.substring(directory.length()+1);
                return path+add;
            }
        } catch (SolrServerException e) {
            logger.error("", e);
        }
        return resourcePath;
    }
}
