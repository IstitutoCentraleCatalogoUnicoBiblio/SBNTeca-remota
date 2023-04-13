package com.gruppometa.sbntecaremota.objects.validators;

import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;

import com.gruppometa.sbntecaremota.objects.ExternalMagReference;

/**
 * Validazione speciale per riferimenti esterni
 * 
 *
 */
public interface ExternalValidator {
	
	/**
	 * Calidazione per riferimenti esterni
	 * 
	 * @param waitingMag path del MAG di cui validare i riferimenti esterni
	 * @param externalReferences lista dei riferimenti esterni da validare
	 * @param jobMagReferences lista dei riferimenti dei MAG elaborati dal job corrente
	 * @param configuration configurazione
	 * @return
	 */
	public ValidationResult validateExternal(String waitingMag, 
			ExternalMagReference externalReference, 
			List<Document> candidateDocuments,
			Properties configuration);

}
