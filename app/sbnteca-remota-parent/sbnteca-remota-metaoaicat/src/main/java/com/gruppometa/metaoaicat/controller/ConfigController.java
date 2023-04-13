package com.gruppometa.metaoaicat.controller;


import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gruppometa.culturaitalia.admin.objects.OaicatConfigItem;
import com.gruppometa.culturaitalia.admin.objects.OaicatConfiguration;
import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;
import com.gruppometa.metaoaicat.MetaOAIHandler;

@RestController
public class ConfigController {
	@Autowired
	MetaOAIHandler oaiHandlerDati;
	
	@Autowired
	MetaOAIHandler oaiHandler;

	protected Logger logger = org.slf4j.LoggerFactory.getLogger(ConfigController.class);
	@RequestMapping(path="/metaoaicat/rest/configuration/{servletName}")
	public OaicatConfiguration getConfiguration(@PathVariable String servletName){
		return ObjectFactory.getOaicatConfiguration(servletName);
	}

	@RequestMapping(path="/metaoaicat/rest/configuration/{servletName}/save", method=RequestMethod.POST)
	public ResponseMessage getConfiguration(
			@PathVariable String servletName,
			@RequestBody OaicatConfigItem configItem){
		ResponseMessage message = new ResponseMessage();
		try{
			OaicatConfiguration config = ObjectFactory.getOaicatConfiguration(servletName);
			boolean found = false;
			for(OaicatConfigItem item: config.getItems()){
				if(item.getName().equals(configItem.getName())){
					item.setValue(configItem.getValue());
					ObjectFactory.updateObject(item);
					found = true;
					break;
				}
			}
			if(found){
				message.setMessage("Saved");
			}
			else{
				message.setMessage("Not found");	
			}
		}
		catch(Exception e){
			logger.error("",e);
			message.setMessage(e.getMessage());
			message.setStatus("ko");
		}
		return message;
	}
	
	@RequestMapping(path="/metaoaicat/rest/configuration/{servletName}/reinit")
	public ResponseMessage reinit(@PathVariable String servletName){
		ResponseMessage message = new ResponseMessage();
		try {
			if(servletName.equalsIgnoreCase("OAIHandler"))
				oaiHandler.init();
			else
				oaiHandlerDati.init();
		} catch (ServletException e) {
			logger.error("",e);
			message.setMessage(e.getMessage());
			message.setStatus("ko");
		}
		return message;
	}
}
