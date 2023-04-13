package com.gruppometa.sbntecaremota.objects.validators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;

public class MimeResourceValidator implements ResourceValidator {

	// mappa keywords content types
	private static Map<String, String> keywordMap = new HashMap<String, String>();
	
	static {
		keywordMap.put("text/plain", "plain");
		keywordMap.put("text/xml", "xml");
		keywordMap.put("text/html", "html");
		keywordMap.put("text/rtf", "rtf");
		keywordMap.put("application/pdf", "pdf");
		keywordMap.put("application/msword", "msword");
		keywordMap.put("image/jpeg", "jpeg");
		keywordMap.put("image/tiff", "tiff");
		keywordMap.put("image/gif", "gif");
		keywordMap.put("image/png", "png");
		keywordMap.put("image/vnd.djvu", "djvu");
		keywordMap.put("audio/wav", "wav");
		keywordMap.put("audio/mpeg", "mpeg");
		keywordMap.put("audio/mpg", "mpeg");
		keywordMap.put("audio/mp3", "mpeg");
		keywordMap.put("audio/x-mpeg", "mpeg");
		keywordMap.put("audio/midi", "midi");
		keywordMap.put("audio/x-realaudio", "realaudio");
		keywordMap.put("video/x-ms-asf", "ms-asf");
		keywordMap.put("video/avi", "avi");
		keywordMap.put("video/mpeg", "mpeg");
		keywordMap.put("video/wmv", "wmv");
		keywordMap.put("video/vnd.rn-realvideo", "realvideo");
	}
	

	@Override
	public List<ValidationError> validate(String resourcePath, 
			Element resource, List<Element> groups,
			Document currentMag, Properties configuration) {

		// flag di errore, default error
		String flagError = null;
		
		if(configuration.containsKey("Validator.MIMEErrorFlag"))
			flagError = configuration.getProperty("Validator.MIMEErrorFlag");
		
		else
			flagError = ValidationError.ERROR;
		

		if(ValidationError.OK.equals(flagError))
			return new ArrayList<ValidationError>();
		
		// cerca i parametri
		List<ValidationError> errors = new ArrayList<ValidationError>();
		List<Element> formatNode = UtilXML.searchInResource(resource, "http://www.iccu.sbn.it/metaAG1.pdf", "format");
		
		if(!formatNode.isEmpty()) {
			String fileType = resource.getLocalName();
			
			if("proxies".equals(fileType) || "altimg".equals(fileType))
				fileType = resource.getParentNode().getLocalName();
			
			// String name = searchFormat("name", formatNode.get(0), currentMag, fileType, groups).toLowerCase();
			// checkExtension(resourcePath, name, ValidationError.WARNING, errors);
			
			String mimeType = searchFormat("mime", formatNode.get(0), currentMag, fileType, groups).toLowerCase();
			checkMime(resourcePath, mimeType, flagError, errors);
		}
		
		return errors;
	}

	/**
	 * Verifica il MIME type
	 * 
	 * @param pathFile path del documento MAG da verificare
	 * @param magMime MIME descritto nel MAG
	 * @param flagError flag di errore
	 * @param errors lista errori di validazione
	 * @return boolean true/false
	 */
	// calcolo validità MIME
	private void checkMime(String pathFile, String magMime, String flagError, List<ValidationError> errors) {
		try {
			String fileMime = Utility.getMime(new File(pathFile));
			
			if(keywordMap.containsKey(magMime)) {
				if(!fileMime.contains(keywordMap.get(magMime))) {
					errors.add(new ValidationError(flagError, "MIME '" + magMime + "' "
							+ "non corrispondente per il file '" + pathFile + "'"));
				}
			}
			
			else {
				boolean found = false;
				
				for(String keyword : keywordMap.values()) {
					if(fileMime.contains(keyword)) {
						found = true;
						break;
					}
				}
				
				if(!found) {
					errors.add(new ValidationError(flagError, "MIME '" + magMime + "' "
							+ "non corrispondente per il file '" + pathFile + "'"));
				}
			}
			
		} catch (IOException e) {
			errors.add(new ValidationError(ValidationError.ERROR, "Problema di calcolo MIME "
					+ "per il file '" + pathFile + "'"));
		}
	}
	
	/**
	 * Verifica l'estensione del file
	 * 
	 * @param pathFile path del file da validare
	 * @param magExtension estensione definita nel MAG
	 * @param flagError flag di errore
	 * @param errors lista errori di validazione
	 */
	private void checkExtension(String pathFile, String magExtension, String flagError, List<ValidationError> errors) {
		String extension = FilenameUtils.getExtension(pathFile);
		
		if(extension == null || extension.equals("")) {
			errors.add(new ValidationError(flagError, "Estensione per il file '" + pathFile + "' non definita"));
			return;
		}
		
		if(!extension.equalsIgnoreCase(magExtension)) {
			errors.add(new ValidationError(flagError, "Estensione '" + extension + "' non coincidente "
					+ "con l'estensione definita nel MAG '" + magExtension + "' per il file '" + pathFile + "'"));
		}
	}

	/**
	 * Ricerca il formato nel MAG
	 * 
	 * @param tagName nome tag da cercare (NAME, MIME) del MAG
	 * @param ctx contesto DOM di ricerca
	 * @param doc documento MAG
	 * @param fileType tipo di oggetto digitale (IMG, AUDIO, VIDEO, OCR, DOC)
	 * @param groups gruppi di risorse digitali
	 * @return Valore del campo da cercare
	 */
	private String searchFormat(String tagName, Element ctx, Document doc, String fileType, List<Element> groups) {
		// cerca estensione e mime nell'elemento della risorsa
		String namespace = "audio".equals(fileType) || "video".equals(fileType) ? 
				"http://www.iccu.sbn.it/metaAG1.pdf" : "http://www.niso.org/pdfs/DataDict.pdf";
		
		NodeList formatParamNode = ctx.getElementsByTagNameNS(namespace, tagName);
		
		if(formatParamNode.getLength() > 0)
			return formatParamNode.item(0).getTextContent();
		
		// ricerca nel gruppo di appartenenza
		else {
			if("audio".equals(fileType) || "video".equals(fileType) || "img".equals(fileType)) {
				Element resourceNode = (Element) ctx;
				
				if(resourceNode.hasAttribute(fileType + "groupID")) {
					String groupID = resourceNode.getAttribute(fileType + "groupID");
					Element groupNode = this.searchGroupByID(groupID, groups);
					
					if(groupNode == null)
						return "";
					
					NodeList formatParamList = groupNode.getElementsByTagNameNS(namespace, tagName);
					return formatParamList.getLength() > 0 ? formatParamList.item(0).getTextContent() : "";
				}
				
				else
					return "";
			}
			
			return "";
		}
	}
	
	/**
	 * Ricerca il gruppo tra quelli presenti per ID
	 * 
	 * @param groupID ID da cercare
	 * @param groupNodeList lista dei nodi di tipo gruppo
	 * @return Element nodo gruppo se trovato, null altrimenti
	 */
	private Element searchGroupByID(String groupID, List<Element> groupNodeList) {
		for(Element groupNode : groupNodeList) {
			if(groupID.equals(groupNode.getAttribute("ID")))
				return groupNode;
		}
		
		return null;
	}

}
