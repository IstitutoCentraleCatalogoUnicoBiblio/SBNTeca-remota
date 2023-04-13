package com.gruppometa.sbntecaremota.objects.validators;

import java.util.Properties;

import org.w3c.dom.Document;

import com.gruppometa.sbntecaremota.retrieve.MagPersistence;

/**
 * Validazione controllo di esistenza di un MAG
 * 

 *
 */
public class MagExistenceValidator implements Validator {

	/**
	 * Validazione
	 * 
	 */
	@Override
	public ValidationResult validate(MagPersistence magPersistence, String currentPath, 
			Document document, Properties configuration) {
		
		ValidationResult result = new ValidationResult();
		
		// documento non esistente
		if(document == null)
			result.getErrors().addAll(magPersistence.getOpeningErrors());
		
		return result;
	}

}
