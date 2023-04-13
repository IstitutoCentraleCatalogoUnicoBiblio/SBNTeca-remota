package com.gruppometa.sbntecaremota.objects.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gruppometa.sbntecaremota.util.UtilXML;

public class UsageResourceValidator implements ResourceValidator {

	// lista statica usage consentiti
	private static List<String> validUsages = Arrays.asList("1", "2", "3", "4", "a", "b");
	
	// lista statica usage unici
	private static List<String> uniqueUsages = Arrays.asList("1", "2", "3", "4");

	@Override
	public List<ValidationError> validate(String resourcePath, Element resource, List<Element> groups,
			Document currentMag, Properties configuration) {
		
		// flag di gestione errori, default error
		String flagError = null;
		
		if(configuration.containsKey("Validator.UsageErrorFlag"))
			flagError = configuration.getProperty("Validator.UsageErrorFlag");
		
		else
			flagError = ValidationError.ERROR;
		
		if(ValidationError.OK.equals(flagError))
			return new ArrayList<ValidationError>();
		
		
		
		// validazione
		List<ValidationError> errors = new ArrayList<ValidationError>();
		List<Element> usageNodeList = UtilXML.searchInResource(resource, "http://www.iccu.sbn.it/metaAG1.pdf", "usage");
		int countUsages = 0;
		
		for (int i = 0; i < usageNodeList.size(); i++) {
			String usage =  usageNodeList.get(i).getTextContent().trim();
			
		    if(!validUsages.contains(usage))
			    errors.add(new ValidationError(flagError, "Usage '" + usage + "' non valido"));
		    
		    else if(uniqueUsages.contains(usage))
		    	countUsages++;
		}
		
		if(countUsages > 1)
			errors.add(new ValidationError(flagError, "Solo uno usage di tipo (1, 2, 3, 4) consentito"));
		
		return errors;
	}

}
