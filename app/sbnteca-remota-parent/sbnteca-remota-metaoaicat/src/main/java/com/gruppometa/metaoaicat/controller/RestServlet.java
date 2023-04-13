package com.gruppometa.metaoaicat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppometa.culturaitalia.admin.objects.OaiSet;
import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;

public class RestServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -645162143585514189L;
	protected Logger logger = LoggerFactory.getLogger(RestServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		List<OaiSet> list =  ObjectFactory.getOaiSets(); 
	    try {
	      // display to console
	      out.println(mapper.writeValueAsString(list));
	    } catch (Exception e) {
	    	logger.error("",e);
	    }
	    out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		response.setContentType("application/json");
		ResponseMessage message = new ResponseMessage();
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
	    try {
	    	OaiSet set = mapper.readValue(request.getReader(), OaiSet.class);
	    	if(set.getId()==0)
	    		ObjectFactory.saveObject(set);
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
	    try {
	    	out.println(mapper.writeValueAsString(message));
	    } catch (Exception e) {
	    	logger.error("",e);
	    }
	    out.close();
	}
	
	@Override
	 protected void doDelete(HttpServletRequest request,
             HttpServletResponse response)
            		 throws ServletException, IOException{
		response.setContentType("application/json");
		ResponseMessage message = new ResponseMessage();
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
	    try {
	    	if(request.getPathInfo()!=null){
	    		String id = request.getPathInfo().substring(request.getPathInfo().lastIndexOf("/")+1) ;
	    		OaiSet set = ObjectFactory.getOaiSet(Integer.parseInt(id));
	    		if(set!=null)
	    			ObjectFactory.deleteObject(set);
	    		else
	    			message.setMessage("Not found "+id);
	    	}
	    	else{
    			message.setMessage("No pathinfo");	    		
	    	}
	    } catch (Exception e) {	    	
	    	logger.error("",e);
	    	message.setMessage(e.getMessage());
	    	message.setStatus("ko");
	    }
	    try {
	    	out.println(mapper.writeValueAsString(message));
	    } catch (Exception e) {
	    	logger.error("",e);
	    }
	    out.close();
	}
	
	
	
}
