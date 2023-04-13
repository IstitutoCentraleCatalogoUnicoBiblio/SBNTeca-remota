package com.gruppometa.sbntecaremota.objects.validators;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.objects.ExternalMagReference;
import com.gruppometa.sbntecaremota.retrieve.MagPersistence;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceTypes;
import com.gruppometa.sbntecaremota.util.UtilXML;

/**
 * Validazione sezione STRU di un MAG
 * 
 *
 */
public class GenericStruValidator implements Validator {

	/**
	 * Validazione
	 * 
	 */
	@Override
	public ValidationResult validate(MagPersistence magPersistence, String currentPath, 
			Document document, Properties configuration) {
		
		ValidationResult result = new ValidationResult();
		String flagError = ValidationError.ERROR;
		
		if(configuration.containsKey("Validator.StruErrorFlag"))
			flagError = configuration.getProperty("Validator.StruErrorFlag");
		
		// ricerca sui nodi di tipo element
		NodeList elementNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "element");
		
		for(int i = 0; i < elementNodeList.getLength(); i++) {
			Element elementElem = (Element) elementNodeList.item(i);
			String resourceType = "img";
			int start = Integer.MIN_VALUE;
			int stop = Integer.MIN_VALUE;
			
			// tipo di risorse
			NodeList resourceNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "resource");
		   
		    if(resourceNode.getLength() > 0)
			    resourceType = resourceNode.item(0).getTextContent();
		    
		    // start e stop
		    NodeList startNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "start");
			NodeList stopNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "stop");
			   
			if(startNode.getLength() > 0) {
				NamedNodeMap attr = startNode.item(0).getAttributes();
				start = Integer.parseInt(getAttrValue(attr, "sequence_number"));
				
				if(stopNode.getLength() > 0) {
					attr = stopNode.item(0).getAttributes();			
					stop = Integer.parseInt(getAttrValue(attr, "sequence_number"));
				}
				else
					stop = start;
			}
			
			if(start != Integer.MIN_VALUE) {
				NodeList identifierNodeList = elementElem.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", "identifier");
				NodeList issueNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "issue");
				NodeList yearNode = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "year");
				
				// riferimenti esterni, attesa
				if(identifierNodeList.getLength() > 0) {
					String dcIdentifier = identifierNodeList.item(0).getTextContent();
					
					result.getErrors().add(new ValidationError(ValidationError.WAIT, 
							"Presenza mag esterno con ID " + dcIdentifier));
					
					
					ExternalMagReference externalReference = new ExternalMagReference();
					externalReference.getIdentifiers().add(dcIdentifier);
					externalReference.setResourceType(resourceType);
					externalReference.setStart(start + "");
					externalReference.setStop(stop + "");
					
					if(issueNode.getLength() > 0)
						externalReference.setIssue(issueNode.item(0).getTextContent());
					
					if(yearNode.getLength() > 0)
						externalReference.setYear(yearNode.item(0).getTextContent());
					
					result.getExternalReferences().add(externalReference);
				}
				
				// riferimenti interni o espliciti al file
				else {
					NodeList fileNodeList = elementElem.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
					
					// assenza di riferimenti ricerca nel documento da validare
					if(fileNodeList.getLength() == 0)
						this.checkResources(document, resourceType, start, stop, flagError, result.getErrors());
						
					// riferimento esplicito a file (raro)
					else {
						Element fileNode = (Element) fileNodeList.item(0);
						String href = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href");
						
						if(href == null || href.isEmpty())
							result.getErrors().add(new ValidationError(ValidationError.FATAL_ERROR, "Riferimento al file mancante"));
						
						else {
							File externalFile = new File(new File(currentPath).getParent(), href);
							
							if(!externalFile.exists()) {
								result.getErrors().add(new ValidationError(ValidationError.FATAL_ERROR, 
										"File esterno " + externalFile.getPath() + " non esistente"));
							}
							
							else {
								Document externalDoc = UtilXML.openMag(externalFile.getPath(), MagPersistenceTypes.FILE);
								
								if(externalDoc == null) {
									result.getErrors().add(new ValidationError(ValidationError.FATAL_ERROR, 
											"Impossibile aprire file esterno " + externalFile.getPath()));
								}
								
								else if(!ValidationError.OK.equals(flagError))
									this.checkResources(externalDoc, resourceType, start, stop, flagError, result.getErrors());
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Preleva valore dell'attributo dalla mappa degli attributi di un nodo elemento
	 * 
	 * @param attr mappa degli attributi
	 * @param string attributo da cercare
	 * @return
	 */
	private String getAttrValue(NamedNodeMap attr, String string) {
		for (int j = 0; j < attr.getLength(); j++) {
			Node node = attr.item(j);
			
		    if (node.getLocalName().equals(string))
		        return node.getTextContent();
		}
		
		return "";
	}
	
	/**
	 * Verifica l'intervallo delle risorse per il MAG individuato
	 * 
	 * @param document MAG da cui cercare le risorse
	 * @param resourceType tipo di risorse da ricercare
	 * @param start estremo inferiore intervallo di sequence number da verificare
	 * @param stop estremo superiore intervallo di sequence number da verificare
	 * @param flagError flag di errore
	 * @param errors lista errori di validazione
	 */
	private void checkResources(Document document, String resourceType, int start, int stop, String flagError, List<ValidationError> errors) {
		Set<Integer> intervalSet = new HashSet<Integer>();
		NodeList resourceNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
		
		for(int i = start; i <= stop; i++)
			intervalSet.add(i);
		
		for(int i = 0; i < resourceNodeList.getLength(); i++) {
			Element resourceNode = (Element) resourceNodeList.item(i);
			
			int seqnumber = Integer.parseInt(resourceNode.getElementsByTagNameNS(
					"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number").item(0).getTextContent());
			
			if(intervalSet.contains(seqnumber))					  
				intervalSet.remove(seqnumber);
		}
		
		for(Integer seqNumberNotFound : intervalSet) {
			errors.add(new ValidationError(flagError, "Non trovata risorsa digitale di "
					+ "tipo " + resourceType + " con sequence number " + seqNumberNotFound));
		}
	}

}
