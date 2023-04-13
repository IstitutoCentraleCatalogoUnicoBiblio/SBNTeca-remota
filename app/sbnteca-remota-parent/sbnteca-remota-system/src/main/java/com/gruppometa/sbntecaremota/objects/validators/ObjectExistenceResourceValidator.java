package com.gruppometa.sbntecaremota.objects.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.gruppometa.sbntecaremota.util.UtilSolr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ObjectExistenceResourceValidator implements ResourceValidator {

	@Override
	public List<ValidationError> validate(String resourcePath, Element resource,
			List<Element> groups, Document currentMag, Properties configuration) {
		String idClear = getCleanId(resourcePath);
		if(resourcePath == null || (!new File(resourcePath).exists() && (idClear == null || UtilSolr.getPathFromId(idClear)==null))) {
			return Arrays.asList(new ValidationError(ValidationError.FATAL_ERROR, 
					"Risorsa '" + resourcePath + "' non esistente"));
		}
		
		return new ArrayList<ValidationError>();
	}

	private String getCleanId(String resourcePath) {
		String prefix = "digitalObject/";
		String suffix = "/original";
		if(resourcePath.startsWith(prefix) && resourcePath.endsWith(suffix))
			return resourcePath.substring(prefix.length(), resourcePath.length()-suffix.length());
		return null;
	}

}
