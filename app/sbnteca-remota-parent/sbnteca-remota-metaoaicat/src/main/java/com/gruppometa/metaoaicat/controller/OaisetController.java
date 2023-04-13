package com.gruppometa.metaoaicat.controller;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gruppometa.culturaitalia.admin.objects.OaiSet;
import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;

@RestController
public class OaisetController {
	
	protected Logger logger = org.slf4j.LoggerFactory.getLogger(OaisetController.class);
	@RequestMapping(path="/metaoaicat/rest/oaiset", method=RequestMethod.GET)
	public List<OaiSet> getLists(){
		List<OaiSet> list =  ObjectFactory.getOaiSets(); 
		return list;
	}
	
	@RequestMapping(path="/metaoaicat/rest/oaiset", method=RequestMethod.POST)
	public ResponseMessage update(
			@RequestBody OaiSet set){
		ResponseMessage message = new ResponseMessage();
		try {
	    	if(set.getId()==0) {
				ObjectFactory.saveObject(set);
			}
	    	else{
	    		OaiSet set2 = ObjectFactory.getOaiSet(set.getId());
	    		BeanUtils.copyProperties(set2, set);
	    		ObjectFactory.updateObject(set2);
	    	}
	    } catch (Exception e) {	    	
	    	logger.error("",e);
	    	message.setMessage(e.getMessage());
	    	message.setStatus("ko");
	    }
		return message;
	}

	@RequestMapping(path="/metaoaicat/rest/oaiset/{id}", method=RequestMethod.DELETE)
	public ResponseMessage delete(@PathVariable int id){
		ResponseMessage message = new ResponseMessage();
		OaiSet set = ObjectFactory.getOaiSet(id);
		if(set!=null)
			ObjectFactory.deleteObject(set);
		else{
			message.setMessage("Not found "+id);
			message.setStatus("ko");
		}			
		return message;
	}	
}
