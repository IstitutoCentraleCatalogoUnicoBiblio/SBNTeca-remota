package com.gruppometa.sbntecaremota.objects.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFileSystem;
import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.retrieve.MagPersistence;
import com.gruppometa.sbntecaremota.util.UtilXML;

public class ResourceListValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(ResourceListValidator.class);
	// lista tipologie oggetti digitali
	private static List<String> types = Arrays.asList("img", "audio", "video", "ocr", "doc");
	
	// lista tipi per gruppi
	private static List<String> groupTypes = Arrays.asList("img", "audio", "video");
	
	// lista validatori files
	private List<ResourceValidator> resourceValidators = Arrays.asList(
			new ObjectExistenceResourceValidator(), new UsageResourceValidator(), 
			new MD5ResourceValidator(), new MimeResourceValidator(), 
			new ObjectDimensionResourceValidator());


	@Override
	public ValidationResult validate(MagPersistence magPersistence, 
			String currentPath, Document document, Properties configuration) {
		
		ValidationResult result = new ValidationResult();
		
		// verifica usage di acquisizione
		List<String> acquisitionUsages = Arrays.asList(configuration.getProperty("Validator.UsageAcquisition").split(" "));
		
		if(!configuration.containsKey("Validator.UsageAcquisition")) {
			result.getErrors().add(new ValidationError(ValidationError.FATAL_ERROR, "Usage di acquisizione non specificati"));
			return result;
		}
		
		String pathFolder = new File(currentPath).getParent();
		
		for(String resourceType : types) {
			// preleva eventuali gruppi
			List<Element> groupNodeList = new ArrayList<Element>();

			if(groupTypes.contains(resourceType)) {
				NodeList typeGroupNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", resourceType + "_group");
				
				for(int i = 0; i < typeGroupNodeList.getLength(); i++)
					groupNodeList.add((Element) typeGroupNodeList.item(i));
			}
			
			// ricerca per tipologia risorsa
			NodeList resourceNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
			List<Element> singleObjectNodeList = new ArrayList<Element>();
			
			for(int i = 0; i < resourceNodeList.getLength(); i++) {
				Element resourceNode = (Element) resourceNodeList.item(i);
				
				// oggetti di tipo img, ocr, doc
				if(!"audio".equals(resourceType) && !"video".equals(resourceType)) {
					// prendi nodo root oggetto digitale
					if(this.processResource(resourceNode, acquisitionUsages))
						singleObjectNodeList.add(resourceNode);
					
					// se tipologia risorsa img verifica anche sulle altimg
					if("img".equals(resourceType)) {
						NodeList altimgNodeList = resourceNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
						
						for(int j = 0; j < altimgNodeList.getLength(); j++) {
							Element altimgNode = (Element) altimgNodeList.item(j);
							
							if(this.processResource(altimgNode, acquisitionUsages))
								singleObjectNodeList.add(altimgNode);
						}
					}
				}
				
				// oggetti di tipo audio e video (considerare solo i nodi proxies per le validazioni)
				else {
					NodeList proxiesNodeList = resourceNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
					
					for(int j = 0; j < proxiesNodeList.getLength(); j++) {
						Element proxiesNode = (Element) proxiesNodeList.item(j);
						
						if(this.processResource(proxiesNode, acquisitionUsages))
							singleObjectNodeList.add(proxiesNode);
					}
				}
			}

			VfsFileSystem vfsFileSystem = magPersistence.getVfsFileSystem();
			// validazioni oggetti digitali
			for(Element objectNode : singleObjectNodeList) {
				List<Element> fileNode = UtilXML.searchInResource(objectNode, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
				
				if(fileNode.isEmpty())
					result.getErrors().add(new ValidationError(ValidationError.FATAL_ERROR, "Riferimento al file mancante"));
				
				else {
					Element file = (Element) fileNode.get(0);
					String href = file.getAttributeNS("http://www.w3.org/TR/xlink", "href");
					if(StringUtils.isEmpty(href))
						href = file.getAttributeNS("h\ttp://www.w3.org/1999/xlink", "href");
					VfsFile resourceFile = vfsFileSystem.getResourceFile(currentPath, pathFolder, href);
					String resourcePath = href == null ? null :
							(resourceFile==null?null:
									resourceFile.getPath());
					boolean fatalErrors = false;
					
					for(ResourceValidator resourceValidator : resourceValidators) {
						//logger.info("Validator: "+ resourceValidator.getClass().getCanonicalName());
						List<ValidationError> errors = null;
						if(resourcePath==null) {
							errors = new ArrayList<>();
							errors.add(new ValidationError());
							errors.get(0).setMessage("Risorsa '"+href+"' non esistente.");
							errors.get(0).setStatus(ValidationError.FATAL_ERROR);
						}
						else
							errors = resourceValidator.validate(resourcePath, objectNode,
								groupNodeList, document, configuration);
						
						for(ValidationError error : errors) {
							result.getErrors().add(error);
							
							if(ValidationError.FATAL_ERROR.equals(error.getStatus()))
								fatalErrors = true;
						}
						
						if(fatalErrors)
							break;
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Verifica se controllare la risorsa digitale attraverso il controllo degli usage
	 * 
	 * @param resource nodo risorsa digitale
	 * @param configuration proprietà configurazione
	 * @return
	 */
	private boolean processResource(Node resource, List<String> acquisitionUsages) {
		List<String> resourceUsages = new ArrayList<String>();
		
		if("audio".equals(resource.getLocalName()) || "video".equals(resource.getLocalName()))
			return false;
		
		List<Element> usages = UtilXML.searchInResource(resource, "http://www.iccu.sbn.it/metaAG1.pdf", "usage");
		
		for (Node usageNode : usages)
			resourceUsages.add(usageNode.getTextContent().trim());
		
		if(resourceUsages.isEmpty())
			resourceUsages.add("3");
		
		for(String usage : resourceUsages) {
			if(acquisitionUsages.contains(usage))
				return true;
		}
		
		return false;
	}

}
