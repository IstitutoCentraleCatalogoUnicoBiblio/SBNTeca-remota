package com.gruppometa.metaoaicat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ORG.oclc.oai.server.OAIHandler;
import ch.qos.logback.classic.Logger;

import com.gruppometa.culturaitalia.admin.objects.OaicatConfigItem;
import com.gruppometa.culturaitalia.admin.objects.OaicatConfiguration;
import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;
import org.springframework.core.env.*;

public class MetaOAIHandler extends OAIHandler{

	protected String name;

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	protected Environment environment;

	protected String initXML = "<?xml version=\"1.0\"?>\n";
	
	public MetaOAIHandler(String name) {
		this.name = name;
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {    
		if(request.getParameter("getstatus")!=null){
			response.setContentType("application/xml");
			response.getWriter().append(initXML);
			if(((HashMap)attributesMap.get("global")).get("OAIHandler.version")!=null)
				response.getWriter().append("<status>online</status>");
			else
				response.getWriter().append("<status>offline</status>");
		}
		else if(request.getParameter("changestatus")!=null){
			response.setContentType("application/xml");
			response.getWriter().append(initXML);
			String status = "false";
			if(((HashMap)attributesMap.get("global")).get("OAIHandler.version")!=null){
				status = "true";
			}
			ObjectFactory.getOaicatConfiguration(name);
			OaicatConfiguration oaiconfig = ObjectFactory.getOaicatConfiguration(name);			
			for (Iterator iterator = oaiconfig.getItems().iterator(); iterator.hasNext();) {
				OaicatConfigItem item = (OaicatConfigItem) iterator.next();
				if(item.getName().equals("OAIHandler.serviceUnavailable") && !item.getValue().equals(status)){
					item.setValue(status);
					ObjectFactory.updateObject(item);
					try {
						attributesMap.clear();
						init();
					} catch (Exception e) {
						log.error(e);
						status = "error";
					}
					break;
				}
			}
			if(status.equalsIgnoreCase("true"))
				status = "offline";
			else if(!status.equals("error"))
				status = "online";
			response.getWriter().append("<status>"+status+"</status>");
		}
		else if(request.getParameter("reloadConfig")!=null){
			attributesMap.clear();
			response.getWriter().append(initXML);
			try {
				init();  
				response.setContentType("application/xml");
				response.getWriter().append("<message>Ok</message>");
			} catch (ServletException e) {
				e.printStackTrace();
				log.error(e);
				response.setContentType("application/xml");
				response.getWriter().append("<message>Ko</message>");
		    }
		}
		else
			super.doGet(request, response);
	}


	@Override
	protected boolean isServiceUnavailable(Properties properties) {
        if (properties.getProperty("OAIHandler.serviceUnavailable") == null ||!"true".equalsIgnoreCase(properties.getProperty("OAIHandler.serviceUnavailable"))) {
            return false;
        }
        return true;
    }
	private Log log = LogFactory.getLog(MetaOAIHandler.class);
	  
	
	@Override
	public void init() throws ServletException {	
		if(attributesMap.get("global")!=null){
			log.debug("no init need?");
		}
		Properties properties = new Properties();
		try {
			OaicatConfiguration oaiconfig = ObjectFactory.getOaicatConfiguration(name);
			int i = 0;
			for (Iterator iterator = oaiconfig.getItems().iterator(); iterator.hasNext();) {
				OaicatConfigItem item = (OaicatConfigItem) iterator.next();
				if(item!=null){
					if(item.getName()!=null && item.getValue()!=null  ) {
						properties.setProperty(item.getName(), item.getValue());
						String k = name+"."+item.getName();
						if(getEnvironment()!=null && getEnvironment().getProperty(k)!=null){
							properties.setProperty(item.getName(),getEnvironment().getProperty(k));
						}
					}
					else
						log.error(""+item.getName()+" "+item.getValue());
				}
				else{
					log.error("item ("+i+") null.");
				}
				i++;
			}
			log.info("Checking for properties from spring boot application configuration.");
			MutablePropertySources propertySources = ((AbstractEnvironment)getEnvironment()).getPropertySources();
			for(Iterator it = propertySources.iterator(); it.hasNext();){
				PropertySource propertySource = (PropertySource) it.next();
				if(	propertySource instanceof MapPropertySource){
					String[]  names = ((MapPropertySource)propertySource).getPropertyNames();
					for (int j=0; j<names.length; j++){
						if(names[j].startsWith(name+".") && properties.getProperty(names[j])==null){
							properties.setProperty(names[j].substring(name.length()+1),getEnvironment().getProperty(names[j]));
							log.info("Set property "+ names[j].substring(name.length()+1)+ "="+getEnvironment().getProperty(names[j]));
						}
					}
				}
				/*if(propertySource instanceof EnumerablePropertySource){
					String[]  names = ((EnumerablePropertySource)propertySource).getPropertyNames();
					for (int j=0; j<names.length; j++){
						log.debug("Get "+ names[j]);
					}
				}*/
			}
			properties.setProperty("servletName", name);
			HashMap  attributes = getAttributes(properties);
			log.info("Store global properties");
	        attributesMap.put("global", attributes);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error(e);
	        throw new ServletException(e.getMessage());
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 69268493817339848L;

	
}