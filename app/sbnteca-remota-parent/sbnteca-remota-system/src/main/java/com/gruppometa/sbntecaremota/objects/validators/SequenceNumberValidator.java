package com.gruppometa.sbntecaremota.objects.validators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.retrieve.MagPersistence;

/**
 * Validazione dei sequence number
 *
 *
 */
public class SequenceNumberValidator implements Validator {

	/**
	 * Validazione
	 * 
	 */
	@Override
	public ValidationResult validate(MagPersistence magPersistence, String currentPath, 
			Document document, Properties configuration) {
		
		// flag gestione error, default error
		ValidationResult result = new ValidationResult();
		String flagError = null;
		
		if(configuration.containsKey("Validator.SequenceNumberErrorFlag"))
			flagError = configuration.getProperty("Validator.SequenceNumberErrorFlag");
		
		else
			flagError = ValidationError.ERROR;
		

		if(ValidationError.OK.equals(flagError))
			return result;
		
		
		// verifica sequence number per tipo di risorsa
		result.getErrors().addAll(checkSeqNumber(document, "img", flagError));
		result.getErrors().addAll(checkSeqNumber(document, "audio", flagError));
		result.getErrors().addAll(checkSeqNumber(document, "video", flagError));
		result.getErrors().addAll(checkSeqNumber(document, "ocr", flagError));
		result.getErrors().addAll(checkSeqNumber(document, "doc", flagError));
		
		// verifica per sezioni STRU
		List<Element> struList = new ArrayList<Element>();
		NodeList struNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "stru");
		
		for(int i = 0; i < struNodeList.getLength(); i++) {
			Element struElem = (Element) struNodeList.item(i);
			
			if(!"stru".equals(struElem.getParentNode().getLocalName()))
				struList.add(struElem);
		}
		
		result.getErrors().addAll(checkSeqNumberStru(struList, flagError));
		return result;
	}

	/**
	 * Controllo dell'unicita (per tipologia di risorsa) e del tipo di dato numerico intero del sequence number
	 * 
	 * @param doc documento MAG da validare
	 * @param node  tipologia di risorsa (DOC, IMG, AUDIO, VIDEO, OCR)
	 * @param flagError tipo di errore in caso segnalazione di anomalie sui sequence number (WARNING, ERROR, FATAL_ERROR)
	 * @return List<ValidationError> lista delle segnalazioni di errore
	 */
	private List<ValidationError> checkSeqNumber(Document doc, String node, String flagError) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		NodeList resourceNodes = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", node);
		
		for(int i = 0; i < resourceNodes.getLength(); i++) {
			NodeList nodes = ((Element) resourceNodes.item(i)).getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number");
			Set<Integer> setSeqNumer = new HashSet<Integer>();
			
			for (int j = 0; j < nodes.getLength(); j++) {
				String seqNumber = nodes.item(j).getTextContent();
				
				if (seqNumber != null && !seqNumber.equals("")) {			
					try {
						int seqnum = Integer.parseInt(seqNumber);
						
						if (setSeqNumer.add(seqnum) == false)
							errors.add(new ValidationError(flagError, "Sequence number " + seqnum + " non univoco"));
						
					} catch(NumberFormatException e) {
						errors.add(new ValidationError(flagError, "Numero di sequenza errato"));
					}
				}			
			}
		}
		
		return errors;		
	}
	
	/**
	 * Verifica errori su sequence number nella sezione stru
	 * 
	 * @param struList lista stru fratello stesso livello gerarchico
	 * @param flagError flag di errore
	 * @return List<ValidationError> lista errori di validazione
	 */
	private List<ValidationError> checkSeqNumberStru(List<Element> struList, String flagError) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		Set<Integer> setSeqNumer = new HashSet<Integer>();
		
		for(Element struElem : struList) {
			List<Element> struChildrenStru = new ArrayList<Element>();
			NodeList struChildren = struElem.getChildNodes();
			
			for(int i = 0; i < struChildren.getLength(); i++) {
				Node struChild = struChildren.item(i);
				
				if(struChild.getNodeType() == Document.ELEMENT_NODE) {
					if("sequence_number".equals(struChild.getLocalName())) {
						String seqNumber = struChild.getTextContent();
						
						if(seqNumber != null && !seqNumber.equals("")) {			
							try {
								int seqnum = Integer.parseInt(seqNumber);
								
								if(setSeqNumer.add(seqnum) == false)			
									errors.add(new ValidationError(flagError, "Sequence number " + seqnum + " non univoco"));
								
							} catch(NumberFormatException e) {
								errors.add(new ValidationError(flagError, "Numero di sequenza errato"));
							}
						}			
					}
					
					else if("stru".equals(struChild.getLocalName()))
						struChildrenStru.add((Element) struChild);
				}
			}
			
			if(!struChildrenStru.isEmpty())
				errors.addAll(this.checkSeqNumberStru(struChildrenStru, flagError));
		}
		
		return errors;
	}

}
