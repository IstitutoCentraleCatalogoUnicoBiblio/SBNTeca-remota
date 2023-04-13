package com.gruppometa.sbntecaremota.retrieve;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.gruppometa.sbntecaremota.objects.validators.ValidationError;
import com.gruppometa.mets2mag.saxon.SaxonHelper;

public class HTTPMagPersistence extends AbstractMagPersistence {

	// validation errors
	private List<ValidationError> errors = new ArrayList<ValidationError>();
	
	@Override
	public Document openMag(String id) {
		try {
			errors.clear();
			InputStream stream = new URL(id).openStream();
			Document doc = docBuilder.parse(new InputSource(stream));
			stream.close();
			return doc;
			
		} catch (FileNotFoundException e) {
			errors.add(new ValidationError(ValidationError.FATAL_ERROR, "File '" + id + "' non trovato"));
			return null;
		} catch (SAXException e) {
			errors.add(new ValidationError(ValidationError.FATAL_ERROR, e.getMessage()));
			return null;
		} catch (IOException e) {
			errors.add(new ValidationError(ValidationError.FATAL_ERROR, e.getMessage()));
			return null;
		}
	}

	@Override
	public List<ValidationError> getOpeningErrors() {
		return errors;
	}

	@Override
	public Document openMetsAsMag(String id, Properties configuration) {
		Transformer transformer = SaxonHelper.getInstance().getTransformer();  
		InputStream stream = null;
		StringWriter stringWriter = null;
		
		try {                                                  
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		    
		    if(configuration.containsKey("Mets.stprog") && !configuration.getProperty("Mets.stprog").isEmpty())
		    	transformer.setParameter("stprog", configuration.getProperty("Mets.stprog")); 
		    
		    if(configuration.containsKey("Mets.collection") && !configuration.getProperty("Mets.collection").isEmpty())
		    	transformer.setParameter("collection", configuration.getProperty("Mets.collection"));    
		    
		    if(configuration.containsKey("Mets.agency") && !configuration.getProperty("Mets.agency").isEmpty())
		    	transformer.setParameter("agency", configuration.getProperty("Mets.agency")); 
		    
		    if(configuration.containsKey("Mets.access_rights") && !configuration.getProperty("Mets.access_rights").isEmpty())
		    	transformer.setParameter("access_rights", configuration.getProperty("Mets.access_rights"));     
		    
		    if(configuration.containsKey("Mets.completeness") && !configuration.getProperty("Mets.completeness").isEmpty())
		    	transformer.setParameter("completeness", configuration.getProperty("Mets.completeness"));            
		    
		    stream = new URL(id).openStream();
		    stringWriter = new StringWriter();
		    transformer.transform(new StreamSource(stream), new StreamResult(stringWriter));
		   
			stream.close();
			stringWriter.close();
			
			Document doc = docBuilder.parse(new InputSource(new StringReader(stringWriter.toString())));
		    return doc;                                                           

		} catch (FileNotFoundException e) {
			errors.add(new ValidationError(ValidationError.FATAL_ERROR, "File '" + id + "' non trovato"));
			return null;
		} catch (SAXException e) {
			errors.add(new ValidationError(ValidationError.FATAL_ERROR, e.getMessage()));
			return null;
		} catch (IOException e) {
			errors.add(new ValidationError(ValidationError.FATAL_ERROR, e.getMessage()));
			return null;
		} catch (TransformerException e) {
			errors.add(new ValidationError(ValidationError.FATAL_ERROR, e.getMessage()));
			return null;
		} finally {
			try {
				if(stream != null) 
					stream.close();
				
				if(stringWriter != null) 
					stringWriter.close();
					
			} catch (IOException e) {
				errors.add(new ValidationError(ValidationError.FATAL_ERROR, e.getMessage()));
				return null;
			}
		}
	}

}
