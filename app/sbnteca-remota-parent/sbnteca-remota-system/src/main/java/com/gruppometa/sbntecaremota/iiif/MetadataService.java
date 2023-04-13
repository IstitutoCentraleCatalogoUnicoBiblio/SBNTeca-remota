package com.gruppometa.sbntecaremota.iiif;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class MetadataService {

    public static final String EDITORE = "Editore";
    public static final String DIRITTI = "Diritti";
    public static final String COLLEZIONE = "Collezione";
    public static final String LOCALIZZAZIONE = "Localizzazione";
    public static final String TITOLO = "Titolo";
    public static final String LEVELL_BIBLIOGRAFICO = "Livello bibliografico";
    public static final String AUTORI = "Autori";
    public static final String TIPO_DOCUMENTO = "Tipo documento";
    public static final String PUBBLICAZIONE = "Pubblicazione";
    public static final String FORMATO = "Descrizione fisica";

    public static final String EDITORE_EN = "Publish";
    public static final String DIRITTI_EN = "Rights";
    public static final String COLLEZIONE_EN = "Collection";
    public static final String LOCALIZZAZIONE_EN = "Library";
    public static final String TITOLO_EN = "Title";
    public static final String LEVELL_BIBLIOGRAFICO_EN = "Bibliographic level";
    public static final String AUTORI_EN = "Authors";
    public static final String FORMATO_EN = "Format";
    public static final String PUBBLICAZIONE_EN = "Publish";


    protected Map<String,String> labelTranslationEn2It = new HashMap<>();

    protected final static Logger logger = LoggerFactory.getLogger(MetadataService.class);

    @Value("${metadata-service.enable: true}")
    protected boolean metadataServiceEnable;

    @PostConstruct
    public void init(){
        labelTranslationEn2It.put(EDITORE_EN, EDITORE);
        labelTranslationEn2It.put(COLLEZIONE_EN, COLLEZIONE);
        labelTranslationEn2It.put(LOCALIZZAZIONE_EN, LOCALIZZAZIONE);
        labelTranslationEn2It.put(DIRITTI_EN, DIRITTI);
        labelTranslationEn2It.put(TITOLO_EN, TITOLO);
        labelTranslationEn2It.put(LEVELL_BIBLIOGRAFICO_EN, LEVELL_BIBLIOGRAFICO);
        labelTranslationEn2It.put(AUTORI_EN, AUTORI);
        labelTranslationEn2It.put(FORMATO_EN, TIPO_DOCUMENTO);
        labelTranslationEn2It.put(PUBBLICAZIONE_EN, EDITORE);
    }


}
