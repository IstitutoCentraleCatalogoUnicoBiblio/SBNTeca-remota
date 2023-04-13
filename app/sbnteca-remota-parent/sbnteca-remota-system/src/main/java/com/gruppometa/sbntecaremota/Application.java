package com.gruppometa.sbntecaremota;


import com.gruppometa.sbntecaremota.util.ContentStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.util.Properties;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {SolrAutoConfiguration.class})
@ImportResource("classpath:applicationContext.xml")
@EnableAsync
public class Application {
    protected static final Logger logger = LoggerFactory.getLogger(Application.class);
    @Autowired
    protected Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);;
    }

    @PostConstruct
    protected void init(){
        ContentStatic.loadConfiguration("config.properties");
        for(Object key : ContentStatic.getProperties().keySet()){
            if(environment.getProperty((String) key)!=null){
                logger.trace(""+key+" = "+environment.getProperty((String) key));
                ContentStatic.getProperties().setProperty((String) key, environment.getProperty((String) key));
            }
            else
                logger.trace("No "+key+"");
        }
        ContentStatic.loadBlacklistMimeTypes("mime_blacklist.txt");
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory>
    containerCustomizer(){
        return new EmbeddedTomcatCustomizer();
    }

    private static class EmbeddedTomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

        @Override
        public void customize(TomcatServletWebServerFactory factory) {
            factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
                //connector.setAttribute("relaxedPathChars", "<>[\\]^`{|}");
                connector.setAttribute("relaxedQueryChars", "[]");
            });
        }
    }

}
