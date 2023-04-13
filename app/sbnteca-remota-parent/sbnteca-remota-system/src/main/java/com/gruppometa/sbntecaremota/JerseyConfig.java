package com.gruppometa.sbntecaremota;

import com.gruppometa.sbntecaremota.mets.MetsProvider;
import com.gruppometa.sbntecaremota.restweb.*;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;

@Component
public class JerseyConfig extends ResourceConfig {

    protected Logger logger = LoggerFactory.getLogger(JerseyConfig.class);
    @Value("${upgrade.enabled:false}")
    protected boolean upgradeEnabled;

    @PostConstruct
    protected void postConstruct(){
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
        register(RestServices.class);
        register(ResourceDownload.class);
        register(MultiPartFeature.class);
        register(VirtualFileSystemResource.class);
        register(ManifestResource.class);
        register(OpenApiResource.class);
        register(MetsResource.class);
        register(LoginResource.class);
        if(upgradeEnabled)
            register(UpgradeResource.class);
        //packages("com.gruppometa.sbntecaremota.restweb");
        register(MetsProvider.class);
        URL url = JerseyConfig.class.getResource("/lib/MD5.so");
        if(url!=null) {
            logger.info("Md5 lib = "+url.getFile());
            System.setProperty("com.twmacinta.util.MD5.NATIVE_LIB_FILE",url.getFile());
        }
    }
}
