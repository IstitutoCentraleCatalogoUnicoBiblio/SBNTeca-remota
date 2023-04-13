package com.gruppometa.sbntecaremota.objects.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.objects.ExternalMagReference;

public class ExternalValidatorImpl implements ExternalValidator {

	/**
	 * Validazione esterna
	 * 
	 */
	@Override
	public ValidationResult validateExternal(String waitingMag, 
			ExternalMagReference externalReference, 
			List<Document> candidateDocuments,
			Properties configuration) {
		
		ValidationResult result = new ValidationResult();
		String flagError = ValidationError.ERROR;
		
		if(configuration.containsKey("Validator.StruErrorFlag"))
			flagError = configuration.getProperty("Validator.StruErrorFlag");
		
		// se non ci sono documenti con quello stesso identifier
		if(candidateDocuments.isEmpty()) {
			result.getErrors().add(new ValidationError(ValidationError.ERROR, 
					"MAG esterno non indicizzato"));
		}
		
		// valida documenti
		else
			this.checkResources(candidateDocuments, externalReference, result, flagError);
		
		return result;
	}

	/**
	 * Verifica l'intervallo delle risorse per il MAG individuato
	 * 
	 * @param documents documenti MAG da valutare per la ricerca delle risorse
	 * @param externalReference riferimento esterno
	 * @param result esito validazione
	 * @param flagError flag di errore
	 */
	private void checkResources(List<Document> documents, ExternalMagReference externalReference, 
			ValidationResult result, String flagError) {
		
		int start = Integer.parseInt(externalReference.getStart());
		int stop = Integer.parseInt(externalReference.getStop());
		
		Set<Integer> intervalSet = new HashSet<Integer>();

		for(int i = start; i <= stop; i++)
			intervalSet.add(i);
		
		// i documenti dovrebbero puntare ad intervalli di sequence_number differenti
		for(Document doc : documents) {
			NodeList resourceNodeList = doc.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", externalReference.getResourceType());
			
			for(int i = 0; i < resourceNodeList.getLength(); i++) {
				Element resourceNode = (Element) resourceNodeList.item(i);
				
				int seqnumber = Integer.parseInt(resourceNode.getElementsByTagNameNS(
						"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number").item(0).getTextContent());
				
				// trovate risorse
				if(intervalSet.contains(seqnumber)) {		
					if(externalReference.getExternalDocument() == null) {
						externalReference.setExternalDocument(doc);
						result.getExternalReferences().add(externalReference);
					}
					
					intervalSet.remove(seqnumber);
				}
			}
		}
		
		// controllo finale
		for(Integer seqNumberNotFound : intervalSet) {
			result.getErrors().add(new ValidationError(flagError, "Non trovata risorsa digitale di "
					+ "tipo " + externalReference.getResourceType() + " con sequence number " + seqNumberNotFound));
		}
	}

}
