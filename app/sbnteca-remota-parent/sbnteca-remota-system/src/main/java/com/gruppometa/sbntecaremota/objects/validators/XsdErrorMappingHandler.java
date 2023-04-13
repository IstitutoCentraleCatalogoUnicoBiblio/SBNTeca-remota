package com.gruppometa.sbntecaremota.objects.validators;

import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Mappatura errori validazione XSD
 * 

 *
 */
public class XsdErrorMappingHandler implements ErrorHandler {
	
	// flag di errore
	private String flagError;
	
	// lista errori di validazione
	private List<ValidationError> errors;
	
	
	public XsdErrorMappingHandler(String flagError, List<ValidationError> errors) {
		this.flagError = flagError;
		this.errors = errors;
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		if(errors != null)
			errors.add(new ValidationError(flagError, exception.getMessage()));
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		if(errors != null)
			errors.add(new ValidationError(ValidationError.ERROR, exception.getMessage()));
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		if(errors != null)
			errors.add(new ValidationError(ValidationError.WARNING, exception.getMessage()));
	}

}
