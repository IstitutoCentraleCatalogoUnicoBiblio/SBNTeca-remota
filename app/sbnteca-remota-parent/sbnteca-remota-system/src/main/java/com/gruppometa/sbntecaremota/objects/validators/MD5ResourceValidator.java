package com.gruppometa.sbntecaremota.objects.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;

public class MD5ResourceValidator implements ResourceValidator {

	@Override
	public List<ValidationError> validate(String resourcePath, 
			Element resource, List<Element> groups,
			Document currentMag, Properties configuration) {
		
		String flagError = null;
		
		if(configuration.containsKey("Validator.MD5ErrorFlag"))
			flagError = configuration.getProperty("Validator.MD5ErrorFlag");
		
		else
			flagError = ValidationError.ERROR;
		/*
		String nameFile = currentMag.getPath();
		String pathFolder = nameFile.substring(0, nameFile.lastIndexOf("/") + 1);
		String pathResource = pathFolder.concat(resource.getOriginalPath());*/

		if(ValidationError.OK.equals(flagError))
			return new ArrayList<ValidationError>();
		
		List<ValidationError> errors = new ArrayList<ValidationError>();
		List<Element> node = UtilXML.searchInResource(resource, "http://www.iccu.sbn.it/metaAG1.pdf", "md5");
		
		if(node.isEmpty())
			return Arrays.asList(new ValidationError(flagError, "MD5 non presente per file " + LabelMapper.getFilename(resourcePath)));
		
		if(!checkMD5(resourcePath, node.get(0).getTextContent().trim()))
			return Arrays.asList(new ValidationError(flagError, "MD5 non corretta per file " + LabelMapper.getFilename(resourcePath)));

		return errors;
	}

	/**
	 * Calcola la codifica MD5 e la confronta con quella presente nel documento MAG
	 * 
	 * @param filename file della risorsa digitale da verificare
	 * @param md5File MD5 della risorsa digitale nel MAG
	 * @return boolean true/false
	 */
	public boolean checkMD5(String filename, String md5File){		
		String md5FileCalculate = "";
		
		try {			
			md5FileCalculate = Utility.getMD5Checksum(filename);
			
		} catch (Exception e) {
			return false;
		}
		
		return md5File.equals(md5FileCalculate);
	}

}
