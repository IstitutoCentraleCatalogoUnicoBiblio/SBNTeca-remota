package com.gruppometa.sbntecaremota.objects.validators;

import com.gruppometa.sbntecaremota.retrieve.MagPersistence;
import com.gruppometa.sbntecaremota.util.UtilXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MetsProfileValidator implements Validator, Metsable{

    private Logger logger = LoggerFactory.getLogger(MetsProfileValidator.class);
    @Override
    public ValidationResult validate(MagPersistence magPersistence, String currentPath, Document document, Properties configuration) {
        String flagError = ValidationError.WARNING;
        ValidationResult result = new ValidationResult();
        if(configuration.containsKey("Validator.ProfileErrorFlag"))
            flagError = configuration.getProperty("Validator.ProfileErrorFlag");
        try {
            XPath xPath = UtilXML.getXpathForMets();
            String[] expressions = new String[]{
                    "//mets:fileGrp[@USE='INTERNAL' or @USE='EXTERNAL']",
                    "//mets:fileGrp[@USE='IMAGE' or @USE='AUDIO' or @USE='VIDEO' " +
                            "or @USE='TEXT' or @USE='VIEWER' or @USE='MANIFEST']",
                    "//mets:fileSec",
                    "//mets:rightsMD",
                    "//mets:structMap[@TYPE='PHYSICAL']",
                    "//rights:RightsDeclarationMD"
            };
            for(String expression: expressions) {
                validate(currentPath, document, flagError,
                        expression,
                        result, xPath);
            }
        } catch (XPathExpressionException e) {
            logger.error("", e);
        }
        return result;
    }

    private void validate(String currentPath,
                          Document document, String flagError, String xPathExpression,
                          ValidationResult result, XPath xPath) throws XPathExpressionException {
        NodeList nodes;
        nodes = (NodeList) xPath.compile(xPathExpression)
                .evaluate(document, XPathConstants.NODESET);
        if(nodes.getLength()==0)
            result.getErrors().add(new ValidationError(flagError, "Nessun " + xPathExpression+
                    " presente nel file " + currentPath));
    }
}
