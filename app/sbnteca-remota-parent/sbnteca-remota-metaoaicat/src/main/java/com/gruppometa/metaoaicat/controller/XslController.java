package com.gruppometa.metaoaicat.controller;

import com.gruppometa.metaoaicat.util.BuildNumber;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 */
@RestController
public class XslController {

    protected static Logger logger = org.slf4j.LoggerFactory.getLogger(XslController.class);

    @Autowired
    String urlPrefix;

    @Autowired
    ApplicationContext ctx;

    String[] params = new String[]{
            "DEFAULT_PREFIX","MAG_ACTIVE","OAIDC_ACTIVE","PICO_ACTIVE",
            "METS_ACTIVE","METS_SIMPLE_ACTIVE","EDM_ACTIVE","TECA_HOME","CIDOC_CRM_ACTIVE",
            "BACK_COLOR","BORDER_COLOR"
    };

    String ret = null;

    @PostConstruct
    protected void postContructs(){
        Resource resource = ctx.getResource("classpath:static/metaoaicat/oai2-with-vars.xsl");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()),1024);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            br.close();
            String out = stringBuilder.toString();
            for (int i = 0 ; i< params.length; i++){
                String key = "xsl.param."+params[i];
                if(ctx.getEnvironment().getProperty(key)!=null){
                    out = out.replace(params[i],
                            ctx.getEnvironment().getProperty(key));
                }
                else{
                    logger.warn("Not found "+ params[i]+" in application.yml.");
                }
            }
            out = out.replace("BUILDNUMBER",BuildNumber.getBuildNumber())
                    .replace("CONTEXT_URL", urlPrefix.endsWith("/")?urlPrefix:(urlPrefix+"/"))
            ;

            ret =  out;
        }
        catch(Exception e){
            logger.error("",e);
            ret = e.getMessage();
        }

    }

    protected String getVersion(){
        try {
            InputStream inputStream = XslController.class.getResourceAsStream("/META-INF/MANIFEST.MF");
            Manifest manifest = new Manifest(inputStream);
            Attributes attributes = manifest.getMainAttributes();
            String version = attributes.getValue("Implementation-Version");
            return version;
        }
        catch (Exception e){
            return "";
        }
    }

    @RequestMapping(path="/metaoaicat/oai2.xsl", method= RequestMethod.GET)
    public String getXsl(){
        return ret;
    }

    @RequestMapping(path="/oaiProviderCT/oai2.xsl", method= RequestMethod.GET)
    public String getXslCI(){
        return ret;
    }
}
