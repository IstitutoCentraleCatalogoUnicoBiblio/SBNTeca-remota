package com.gruppometa.sbntecaremota.objects.validators;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.gruppometa.sbntecaremota.retrieve.MagPersistence;

/**
 * Validazione schema XSD
 *
 *
 */
public class XsdValidator implements Validator {

	/**
	 * Validazione
	 * 
	 */
	@Override
	public ValidationResult validate(MagPersistence magPersistence, String currentPath, 
			Document document, Properties configuration) {
		
		// validazione XSD
		String pathXSD = XsdValidator.class.getResource("/schemaMag/metadigit.xsd").getFile();
		ValidationResult result = new ValidationResult();
		File schemaFile = new File(pathXSD);

		// schema non esistente al path
		if (!schemaFile.exists()) {
			result.getErrors().add(new ValidationError(ValidationError.FATAL_ERROR, 
					"File schema XSD non trovato"));
			
			return result;
		}
		
		// flag di errore, default error
		String flagError = ValidationError.WARNING;
		
		if(configuration.containsKey("Validator.SchemaErrorFlag"))
			flagError = configuration.getProperty("Validator.SchemaErrorFlag");
		
		try {
			// validazione XSD
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(schemaFile));
			javax.xml.validation.Validator validator = schema.newValidator();
			validator.setErrorHandler(new XsdErrorMappingHandler(flagError, result.getErrors()));
			validator.validate(new DOMSource(document));
			
		} catch (SAXException e) {
			result.getErrors().add(new ValidationError(ValidationError.FATAL_ERROR, e.getMessage()));
		} catch (IOException e) {
			result.getErrors().add(new ValidationError(ValidationError.FATAL_ERROR, e.getMessage()));
		}
		
		return result;
	}

}
