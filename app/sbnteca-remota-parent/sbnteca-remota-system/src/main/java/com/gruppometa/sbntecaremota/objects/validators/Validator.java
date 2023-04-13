package com.gruppometa.sbntecaremota.objects.validators;

import java.util.Properties;

import org.w3c.dom.Document;

import com.gruppometa.sbntecaremota.retrieve.MagPersistence;

/**
 * Validatore
 *
 *
 */
public interface Validator {
	
	/**
	 * Validazione di un documento MAG
	 * 
	 * @param magPersistence lettore documento XML MAG da persistenza
	 * @param currentPath path del documento
	 * @param document documento MAG
	 * @param configuration mappa di configurazione
	 * @return ValidationResult esito validazione (errori e riferimenti esterni)
	 */
	public ValidationResult validate(MagPersistence magPersistence, String currentPath, 
			Document document, Properties configuration);

}
