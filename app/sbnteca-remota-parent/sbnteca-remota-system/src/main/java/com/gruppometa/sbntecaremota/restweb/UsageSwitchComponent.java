package com.gruppometa.sbntecaremota.restweb;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsService;
import inet.ipaddr.IPAddressString;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gruppometa.sbntecaremota.restweb.AudioCutterComponent.*;
import static org.springframework.util.StringUtils.hasText;

@Component
public class UsageSwitchComponent{

    @Value("${usages.switch.changes:}")
    protected String changes;

    @Value("${usages.switch.ips.whitelist:}")
    protected String whitelist;

    @Value("${usages.switch.ips.blacklist:}")
    protected String blacklist;

    @Value("${usages.switch.active:false}")
    protected boolean active;

    @Value("${usages.switch.mediatypes:audio}")
    protected String mediaTypes;

    @Value("${usages.switch.notfoundException:true}")
    protected boolean notfoundException;

    @Autowired
    protected VfsService vfsService;
    protected List<IPAddressString> ipAddressStringsBlack = new ArrayList<>();
    protected List<IPAddressString> ipAddressStringsWhite = new ArrayList<>();

    protected Map<String, String> usageChangeMap = new HashMap<>();

    protected static final Logger logger = LoggerFactory.getLogger(UsageSwitchComponent.class);

    @PostConstruct
    protected void postConstruct(){
        if(whitelist!=null) {
            String[] ips = whitelist.split(",| ");
            for(String ip : ips)
                if(hasText(ip))
                    ipAddressStringsWhite.add(new IPAddressString(ip));
        }
        if(blacklist!=null) {
            String[] ips = blacklist.split(",| ");
            for(String ip : ips)
                if(hasText(ip))
                    ipAddressStringsBlack.add(new IPAddressString(ip));
        }
        if(changes!=null){
            String[] changesParts = changes.split(",| ");
            for(String change: changesParts){
                String[] parts = change.split("->");
                usageChangeMap.put(parts[0], parts[1]);
            }
        }
        logger.debug("UsageSwitchComponent: active="+active);

    }

    public boolean haveToChange(DataResourceDelivery data, String mode, HttpServletRequest request, String thumbCall) {
        if(!active)
            return false;
        if(data==null || !mediaTypes.contains(getResourceTypeByContentType(data.getContentType()))
                || thumbCall!=null // anche essi sono preview
                || "preview".equalsIgnoreCase(mode)) // preview per audio è un immagine)
            return false;
        if(!StringUtils.hasText(changes) || data.getUsage()==null || !usageChangeMap.containsKey(data.getUsage()))
            return false;
        logger.debug("Remote IP = " +request.getRemoteAddr()+", X-Forwarded-For:"+ request.getHeader("X-Forwarded-For"));
        Boolean isBlack = null;
        if(containsIp(request.getHeader("X-Forwarded-For"), ipAddressStringsBlack) && request.getHeader("X-Forwarded-For")!=null)
            isBlack = true;
        if(request.getHeader("X-Forwarded-For")==null && containsIp(request.getRemoteAddr(), ipAddressStringsBlack))
            isBlack = true;
        Boolean isWhite = null;
        if(containsIp(request.getHeader("X-Forwarded-For"), ipAddressStringsWhite) && request.getHeader("X-Forwarded-For")!=null)
            isWhite = true;
        if(request.getHeader("X-Forwarded-For")==null && containsIp(request.getRemoteAddr(), ipAddressStringsWhite))
            isWhite = true;
        if(isWhite!=null && isWhite && isBlack==null)
            return false;
        if(isBlack!=null && isBlack)
            return true;
        if(isWhite!=null && !isWhite)
            return true;
        return false;
    }

    public String getIdForMappedUsage(String deliveryID, String usage) throws ForbiddenUsageException, NotFoundUsageException {
        if("0".equals(usageChangeMap.get(usage)))
            throw new ForbiddenUsageException();
        try {
            // ricerca Solr delivery
            String url = ContentStatic.getProperties().getProperty("UrlDeliverySolr");
            //logger.info("Solr "+url);
            SolrClient solr = new HttpSolrClient.Builder(url).build();
            // HttpSolrServer solr = new HttpSolrServer(ContentStatic.getProperties().getProperty("UrlDeliverySolr"));
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set("q", "created_from_s:" + ClientUtils.escapeQueryChars(deliveryID) + " AND vfs_usage_s:"+usageChangeMap.get(usage));
            params.set("fl","id");
            QueryResponse solrResponse = solr.query(params);
            SolrDocumentList docList = solrResponse.getResults();

            if(docList.isEmpty()){
                VfsFile vfsFile = vfsService.getVfsFileById(deliveryID, "");
                String label = vfsFile.getLabel();
                String container = vfsFile.getContainer().get(0);
                String query = "(label:" + ClientUtils.escapeQueryChars(label)+
                        "   OR   label:" + ClientUtils.escapeQueryChars(label+LABEL_SUFFIX)+")"+
                        "   AND vfs_container:"+ ClientUtils.escapeQueryChars(container)
                        + " AND vfs_usage_s:"+usageChangeMap.get(usage);
                logger.debug("Query: "+query);
                params.set("q", query);
                solrResponse = solr.query(params);
                docList = solrResponse.getResults();
            }
            // ID non presente
            if(docList.isEmpty()) {
                solr.close();
                if(notfoundException)
                    throw new NotFoundUsageException();
                return null;
            }
            else {
                // ricerca risorsa digitale
                SolrDocument doc = docList.get(0);
                String data = (String) doc.getFieldValue("id");
                solr.close();
                return data;
            }
        } catch (SolrServerException e) {
            logger.error("", e);
            return null;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

}
