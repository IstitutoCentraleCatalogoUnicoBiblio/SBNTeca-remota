package com.gruppometa.mets2mag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MetsValidator {
	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	protected static final Logger logger = LoggerFactory.getLogger(MetsValidator.class);

	public void parseString(String string) throws SAXException, IOException {
		parseInputSource(new InputSource(new StringReader(string)));
	}

	public void parseInputSource(InputSource inputsource) throws SAXException, IOException {
		try {
			boolean validate = true;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(validate);
			factory.setNamespaceAware(true);
			SAXParser parser;
			parser = factory.newSAXParser();
			if (validate) {
				parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
						"http://www.w3.org/2001/XMLSchema");
				parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
				//InputStream xmlFile = MagValidator.class.getResourceAsStream("/mag/metadigit.xsd");
				URL xmlFile = MetsValidator.class.getResource("/mets/mets.xsd");
				parser.setProperty(JAXP_SCHEMA_SOURCE, xmlFile.toExternalForm());
			}
			// parser.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
			// "file:///D:/progetti/promedia-git2/pmedia-importer/src/main/resources/legfisco.xsd");

			XMLReader reader = null;
			reader = parser.getXMLReader();
			reader.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					if(systemId.equals("http://www.loc.gov/standards/mets/mets.xsd"))
						return new InputSource(new InputStreamReader(MetsValidator.class.getResourceAsStream("/mets/mets.xsd")));
					if(systemId.equals("https://www.loc.gov/standards/rights/METSRights.xsd"))
						return new InputSource(new InputStreamReader(MetsValidator.class.getResourceAsStream("/mets/METSRights.xsd")));
					if(systemId.equals("http://www.loc.gov/standards/xlink/xlink.xsd"))
						return new InputSource(new InputStreamReader(MetsValidator.class.getResourceAsStream("/mets/xlink.xsd")));
					if(systemId.equals("http://www.loc.gov/mods/v3/mods-3-7.xsd"))
						return new InputSource(new InputStreamReader(MetsValidator.class.getResourceAsStream("/mets/mods-3-7.xsd")));
					if(systemId.equals("http://www.loc.gov/mods/xml.xsd"))
						return new InputSource(new InputStreamReader(MetsValidator.class.getResourceAsStream("/mets/xml.xsd")));
					if(systemId.equals("http://www.niso.org/pdfs/DataDict.pdf"))
						return new InputSource(new InputStreamReader(MetsValidator.class.getResourceAsStream("/mets/niso-mag.xsd")));
					if(!systemId.contains(".jar!/mets/")) // carciamento dal jar
						logger.error("Caricare "+systemId);
					return null;
				}
			});
			reader.setErrorHandler(new ErrorHandler() {
					public void error(SAXParseException arg0) throws SAXException {
						throw new SAXException(new Exception("(1) "+arg0.getMessage() + " line " + arg0.getLineNumber()
								+ " column " + arg0.getColumnNumber()));
					}
					public void fatalError(SAXParseException arg0) throws SAXException {
						throw new SAXException(new Exception("(2) "+arg0.getMessage() + " line " + arg0.getLineNumber()
								+ " column " + arg0.getColumnNumber()));
					}
					public void warning(SAXParseException arg0) throws SAXException {
						throw new SAXException(new Exception("(3) "+arg0.getMessage() + " line " + arg0.getLineNumber()
								+ " column " + arg0.getColumnNumber()));
					}
				});
			// validateFile(file);
			reader.parse(inputsource);
		} catch (ParserConfigurationException e) {
			new SAXException(e);
		}
	}

	public void validate(String metadataAsString) throws SAXException, IOException {
		parseString(metadataAsString);
	}

	public void validate(InputSource inputSource) throws SAXException, IOException {
		parseInputSource(inputSource);
	}

	public void validate(InputStream inputStream) throws SAXException, IOException {
		int bufferSize = 1024;
		char[] buffer = new char[bufferSize];
		StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
			out.append(buffer, 0, numRead);
		}
		parseString(out.toString());

	}

}
