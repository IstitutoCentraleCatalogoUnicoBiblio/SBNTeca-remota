package com.gruppometa.sbntecaremota.objects.validators;

import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface ResourceValidator {
	
	/**
	 * Validazione del singolo oggetto digitale
	 * 
	 * @param resourcePath path dell'oggetto digitale
	 * @param resource nodo oggetto digitale
	 * @param groups lista dei gruppi per la tipologia di oggetto (img, audio, video)
	 * @param currentMag documento MAG
	 * @param configuration configurazione validazione
	 * @return List<ValidationError> lista errori
	 */
	public List<ValidationError> validate(String resourcePath, 
			Element resource, List<Element> groups, 
			Document currentMag, Properties configuration);

}
