package com.gruppometa.metaoaicat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;
import com.gruppometa.internetculturale.config.ComponentFactoryImpl;

@Configuration
@ConfigurationProperties(prefix="configs")
public class Myconfiguration implements EnvironmentAware{
	protected String urlPrefix="/metaoaicat";
	protected Environment environment;
	
	@Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
	public String getUrlPrefix() {
		return urlPrefix;
	}
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	public String getDefaultServlet() {
		return defaultServlet;
	}

	public void setDefaultServlet(String defaultServlet) {
		this.defaultServlet = defaultServlet;
	}

	protected String defaultServlet = "OAIHandler";
	
	/*
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
	    return new ServletRegistrationBean(new RestServlet(),urlPrefix+"/rest/oaiset/*");
	}
	*/
	@Bean
	public ServletRegistrationBean servletRegistrationBean2(){
		ServletRegistrationBean servlet = new ServletRegistrationBean(oaiHandlerDati(),urlPrefix+"/OAIHandlerDati");
		servlet.setName("OAIHandlerDati");
	    return servlet;
	}
	@Bean
	public ServletRegistrationBean servletRegistrationBean3(){
		ServletRegistrationBean servlet = new ServletRegistrationBean(oaiHandler(),urlPrefix+"/OAIHandler");
		servlet.setName("OAIHandler");
	    return servlet;
	}

	@Bean String urlPrefix(){
		return urlPrefix;
	}

	@Bean String  firstConfig(){
		new MyObjectFactory(environment).createConfig();
		new MySkosObjectFactory(environment).createConfig();
		return "Hi!";
	}
	
	class MyObjectFactory extends ObjectFactory{
		protected Environment environment;
		public  MyObjectFactory(Environment environment){
			this.environment = environment;
		}
		public void createConfig(){
			org.hibernate.cfg.Configuration config =  createConfigConfiguration();
			config.getProperties().setProperty("hibernate.connection.password", environment.getProperty("configdb.password"));
			config.getProperties().setProperty("hibernate.connection.username", environment.getProperty("configdb.username"));
			config.getProperties().setProperty("hibernate.connection.url", environment.getProperty("configdb.url"));
			config.getProperties().setProperty("hibernate.default_catalog", environment.getProperty("configdb.dbname"));
		}
	}
	class MySkosObjectFactory extends com.gruppometa.culturaitalia.skos.ObjectFactory{
		protected Environment environment;
		public  MySkosObjectFactory(Environment environment){
			this.environment = environment;
		}
		public void createConfig(){
			org.hibernate.cfg.Configuration config =  createSemConfiguration();
			config.getProperties().setProperty("hibernate.connection.password", environment.getProperty("configdb.password"));
			config.getProperties().setProperty("hibernate.connection.username", environment.getProperty("configdb.username"));
			config.getProperties().setProperty("hibernate.connection.url", environment.getProperty("configdb.url"));
			config.getProperties().setProperty("hibernate.default_catalog", environment.getProperty("configdb.dbname"));
			config.getProperties().remove("hibernate.session_factory_name");
		}
	}

	@Bean
	public MetaOAIHandler oaiHandler(){
		firstConfig();
		MetaOAIHandler handler =  new MetaOAIHandler("OAIHandler");
		handler.setEnvironment(environment);
		return handler;
	}
	@Bean
	public MetaOAIHandler oaiHandlerDati(){
		firstConfig();
		MetaOAIHandler handler = new MetaOAIHandler("OAIHandlerDati");
		handler.setEnvironment(environment);
		return handler;
	}
	
	@Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // forward requests to /admin and /user to their index.html
                registry.addViewController(urlPrefix).setViewName(
                        "redirect:"+urlPrefix+"/"+getDefaultServlet()+"?verb=Identify");
                registry.addViewController(urlPrefix+"/").setViewName(
                        "redirect:"+urlPrefix+"/"+getDefaultServlet()+"?verb=Identify");
				registry.addViewController(urlPrefix+"/images/Solr_Logo_on_white.png").setViewName(
						"forward:/images/Solr_Logo_on_white.png");
            }
        };
    } 
}
