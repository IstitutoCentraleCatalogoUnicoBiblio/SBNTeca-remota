package com.gruppometa.sbntecaremota.objects.validators;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.retrieve.MagPersistence;

/**
 * Validazione per i MAG seriali
 *
 *
 */
public class SerialMagValidator implements Validator {

	/**
	 * Validazione
	 * 
	 */
	@Override
	public ValidationResult validate(MagPersistence magPersistence, String currentPath, 
			Document document, Properties configuration) {
		
		// flag di errore, default error
		String flagError = ValidationError.ERROR;
		
		if(configuration.containsKey("Validator.SerialErrorFlag"))
			flagError = configuration.getProperty("Validator.SerialErrorFlag");
		
		ValidationResult result = new ValidationResult();
		NodeList bibNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "bib");
		
		if(bibNodeList.getLength() == 0) {
			result.getErrors().add(new ValidationError(flagError, "Il mag non contiene la sezione BIB"));
			return result;
		}
		
		Element bibNode = (Element) bibNodeList.item(0);
		String nodeValue = bibNode.getAttribute("level");
		
		// se il mag è seriale controlla esistenza tag piece
		if ("s".equals(nodeValue)) {
			NodeList magPieceNodeList = bibNode.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", "piece");
			
			if (magPieceNodeList.getLength() == 0) {
				result.getErrors().add(new ValidationError(flagError, 
						"Il mag di tipo serie non valorizza il nodo piece"));
			}
		}
		
		return result;
	}

}
