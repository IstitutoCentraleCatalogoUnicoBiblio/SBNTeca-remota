package com.gruppometa.sbntecaremota.mets;

import com.gruppometa.data.mets.Mets;
import com.gruppometa.mets2mag.MetsConvertor;
import com.gruppometa.sbntecaremota.objects.ImportSettings;
import com.gruppometa.sbntecaremota.objects.validators.Metsable;
import com.gruppometa.sbntecaremota.objects.validators.ValidationError;
import com.gruppometa.sbntecaremota.objects.validators.ValidationResult;
import com.gruppometa.sbntecaremota.objects.validators.Validator;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.util.Properties;

@Component
public class MetsValidator {

    protected static final Logger logger = LoggerFactory.getLogger(MetsCreator.class);

    protected MetsConvertor metsConvertor = new MetsConvertor();

    protected com.gruppometa.mets2mag.MetsValidator metsValidator = new com.gruppometa.mets2mag.MetsValidator();

    public ValidationResult validateMets(String metsJson) {
        try {
            Mets metsObject = metsConvertor.convertJson2Mets(metsJson);
            return validateMets(metsObject);
        }
        catch (Exception e){
            ValidationResult validationResult = new ValidationResult();
            validationResult.setStatus("OK");
            validationResult.setStatus("ERROR");
            validationResult.getErrors().add(
                    makeError(e.getMessage()!=null?
                            e.getMessage():clear(
                            e.getCause()!=null?e.getCause().getMessage():"No message")));
            logger.error("", e);
            return validationResult;
        }
    }

    public ValidationResult validateMetsXml(String metsJson) {
        try {
            Mets metsObject = metsConvertor.readMetsFromString(metsJson);
            return validateMets(metsObject);
        }
        catch (Exception e){
            ValidationResult validationResult = new ValidationResult();
            validationResult.setStatus("OK");
            validationResult.setStatus("ERROR");
            validationResult.getErrors().add(
                    makeError(e.getMessage()!=null?
                            e.getMessage():clear(
                            e.getCause()!=null?e.getCause().getMessage():"No message")));
            logger.error("", e);
            return validationResult;
        }
    }

    public ValidationResult validateMets(Mets metsObject){
        ValidationResult validationResult = new ValidationResult();
        validationResult.setStatus("OK");
        try{
            ImportSettings importSettings = new ImportSettings();
            Utility.configureValidation(importSettings);;
            Properties configuration = ContentStatic.getProperties();
            Document document = UtilXML.convertStringToDocumentXML(metsConvertor.convertMets2Xml(metsObject));
            for(Validator validator: importSettings.getValidators()){
                if(validator instanceof Metsable) {
                    ValidationResult result = validator.validate(null, metsObject.getID(),
                            document, configuration);
                    validationResult.getErrors().addAll(result.getErrors());
                }
            }
        }
        catch (Exception e){
            validationResult.setStatus("ERROR");
            validationResult.getErrors().add(
                    makeError(e.getMessage()!=null?
                            e.getMessage():clear(
                            e.getCause()!=null?e.getCause().getMessage():"No message")));
            logger.error("", e);
        }
        return validationResult;
    }

    private ValidationError makeError(String message) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(message);
        validationError.setStatus(ValidationError.ERROR);
        return validationError;
    }

    private String clear(String message) {
        if(message==null)
            return null;
        return message.trim();
    }

}
