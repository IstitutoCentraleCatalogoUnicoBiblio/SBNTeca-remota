package com.gruppometa.sbntecaremota.objects.validators;

import ch.qos.logback.core.joran.spi.XMLUtil;
import com.gruppometa.sbntecaremota.retrieve.MagPersistence;
import com.gruppometa.sbntecaremota.util.UtilXML;
import org.eclipse.persistence.internal.oxm.record.DOMInputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.util.Properties;

public class MetsSchemaValidator implements Validator, Metsable{

    protected static final Logger logger = LoggerFactory.getLogger(MetsSchemaValidator.class);

    @Override
    public ValidationResult validate(MagPersistence magPersistence, String currentPath, Document document, Properties configuration) {
        // flag di errore, default error
        String flagError = ValidationError.WARNING;
        ValidationResult result = new ValidationResult();
        if(configuration.containsKey("Validator.SchemaErrorFlag"))
            flagError = configuration.getProperty("Validator.SchemaErrorFlag");

        com.gruppometa.mets2mag.MetsValidator metsValidator = new com.gruppometa.mets2mag.MetsValidator();
        try {
            //InputSource inputSource = new DOMInputSource(document);
            metsValidator.validate(UtilXML.convertDocumentToString(document));
            logger.debug("Mets validation passes.");
        }
        catch(Exception e) {
            logger.error("", e);
            result.getErrors().add(new ValidationError(flagError,
                    e.getMessage()==null?e.getClass().getCanonicalName():e.getMessage()));
        }
        return result;
    }
}
