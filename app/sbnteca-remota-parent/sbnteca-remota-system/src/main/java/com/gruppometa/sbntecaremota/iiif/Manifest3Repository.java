package com.gruppometa.sbntecaremota.iiif;

import com.gruppometa.sbntecaremota.model.iiif.v3.*;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Manifest3Repository {

    protected static Logger logger = LoggerFactory.getLogger(Manifest3Repository.class);

    protected String base;

    protected String jmmsBase;

    protected String baseIiif;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getJmmsBase() {
        return jmmsBase;
    }

    public void setJmmsBase(String jmmsBase) {
        this.jmmsBase = jmmsBase;
    }

    public String getBaseIiif() {
        return baseIiif;
    }

    public void setBaseIiif(String baseIiif) {
        this.baseIiif = baseIiif;
    }

    protected PathMapper pathMapper = new PathMapper();


    public String getManifestUrl(String id){
        return base+"/"+id+"/manifest.json";
    }

    public Manifest getManifest(String idReal,  String id) {
        return getManifest(idReal,"3", id);
    }
    @Cacheable(value = "manifest3Cache", key = "#p0 + #p1")
    public Manifest getManifest(String idReal, String usage, String id) {
        SolrClient solr = new HttpSolrClient.Builder(ContentStatic.getProperties().getProperty("UrlSolr")).build();
        long now = System.currentTimeMillis();
        Manifest manifest = null;
        manifest = new Manifest(getManifestUrl(id),"Manifest di "+idReal);
        manifest.setItems(new ArrayList<>());
        try {
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery("id:"+ ClientUtils.escapeQueryChars(idReal));
            solrQuery.setFields("id","magxmlExternal","usageExternal","documentFormat");
            String magExternal = null;
            QueryResponse solrResponse = solr.query(solrQuery);
            List<String> usages = null;
            if(solrResponse.getResults().getNumFound()>0) {
                magExternal = (String) solrResponse.getResults().get(0).getFieldValue("magxmlExternal");
                String documentFormat =  (String) solrResponse.getResults().get(0).getFieldValue("documentFormat");
                usages  = (List<String>) solrResponse.getResults().get(0).getFieldValue("usageExternal");
                if(usages!=null && !usages.contains(usage))
                    usage = "3";
                if("mets".equalsIgnoreCase(documentFormat) && !usage.equals("3"))
                    usage ="3";
            }
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser saxParser = factory.newSAXParser();

            if(magExternal==null)
                logger.error("Not found "+idReal+ " in solr of SbnTeca.");
            saxParser.parse(new InputSource(new StringReader(magExternal)),
                    new MagHandler3(id, manifest, baseIiif, base,jmmsBase,"tdi", usage, usages!=null && usages.contains("5") ));
                /**
                 * p.e. geo non sono vestiti
                 */
//                if(manifest.getItems()==null || manifest.getItems().size()==0){
//                    try {
//                        XPath xPath = Xml.instanceProvider();
//                        org.w3c.dom.Document magDoc =  Xml.read(file);
//                        logger.debug("Mag '" + idReal + "' non vestito.");
//                        Mag2Magdoc mag2Magdoc = new Mag2Magdoc();
//                        org.w3c.dom.Document  magDocVestito = mag2Magdoc.getVestito(magDoc, xPath, idReal, tecaUrl,"thumbnail",
//                                idReal,
//                                geoCallBackComponent,
//                                idReal
//                        );
//                        String xmlComposed = Xml.writeToString(magDocVestito);
//                        logger.debug("XML composed length: "+ xmlComposed.length());
//                        manifest = new Manifest(getManifestUrl(id),"Manifest di "+idReal);
//                        manifest.setItems(new ArrayList<>());
//                        saxParser.parse(new InputSource(new StringReader(xmlComposed)),
//                                new MagHandler3(id, manifest, baseIiif, base,jmmsBase,"ad", tecaUrl));
//                    }
//                    catch(Exception e){
//                        logger.error("", e);
//                    }
//                }

            adjustRanges(manifest);
            if(manifest.getMetadata()!=null) {
                manifest.setMetadata(manifest.getMetadata().stream()
                        .sorted((metaData, t1) -> {
                    int x = getOrder(metaData);
                    int y = getOrder(t1);
                    return Integer.compare(x, y);
                }).collect(Collectors.toList()));
            }
        }
        catch (Exception e){
            logger.error("Id: "+idReal+ " "+id, e);
            return null;
        }
        logger.debug("Produce manifest "+idReal+" in "+(System.currentTimeMillis()-now)+"ms.");
        return manifest;
    }

    /**
     * Range con un solo item devono diventare Canvas e non Range
     * @param manifest
     */
    public void adjustRanges(Manifest manifest) {
        if(manifest.getStructures()==null)
            return;
        for(Range range: manifest.getStructures())
            checkChildren(range);
        for (Range range : manifest.getStructures())
            checkChildrenLabel(manifest, range);
    }

    private void checkChildrenLabel(Manifest manifest, Range range) {
        if (range.getItems() == null)
            return;
        for (int i = 0; i < range.getItems().size(); i++) {
            if(range.getItems().get(i) instanceof Canvas){
                Canvas canvas = ((Canvas) range.getItems().get(i));
                canvas.setItems(null);
                canvas.setAnnotations(null);
//                if(range.getItems().size()==2 && i==0)
//                    range.getItems().remove(i+1);
//                Range range1 = new Range(canvas.getId()+"/range");
//                range1.setLabel(
//                        getLabel(manifest,((Canvas) range.getItems().get(i)).getId()));
//                range1.getItems().add(canvas);
//                range.getItems().set(i, range1);
            }
            else
                checkChildrenLabel(manifest, (Range) range.getItems().get(i));
        }
    }

    private LanguageMap getLabel(Manifest manifest, String id){
        Optional<Canvas> canvas =  manifest.getItems().stream().filter(c-> c.getId().equals(id)).findFirst();
        if(canvas.isPresent())
           return canvas.get().getLabel();
        else
           return new LanguageMap("none","Canvas");
    }

    private void checkChildren(Range range) {
        if(range.getItems()==null)
            return;
        for(int i =0 ; i< range.getItems().size(); i++){
            Object child = range.getItems().get(i);
            if(child instanceof Range && ((Range) child).getItems()!=null
                    &&
                    ((((Range) child).getItems()).size()==1
                    && ((Range) child).getItems().get(0) instanceof Canvas)
                    ||((((Range) child).getItems()).size()==2
                    && ((Range) child).getItems().get(0) instanceof Canvas
                    && ((Range) child).getItems().get(1) instanceof Canvas)
            ){
                Object childOneOnly = ((Range) child).getItems().get(0);
                logger.debug("Range " + ((Range) child).getId() + " -> Canvas " + ((Canvas) childOneOnly).getId());
                range.getItems().set(i, childOneOnly);
            }
            else if (child instanceof Range && ((Range) child).getItems()!=null){
                checkChildren((Range) child);
            }
        }
    }

    private int getOrder(MetaData metaData) {
        String label = metaData.getLabel().get("none")[0];
        if(label.equals("Titolo"))
            return 1;
        if(label.equals("Autori"))
            return 2;
        if(label.equals("Editore"))
            return 3;
        if(label.equals("Tipo documento"))
            return 4;
        if(label.equals("Livello bibliografico"))
            return 5;
        if(label.equals("Localizzazione"))
            return 6;
        if(label.equals("Collezione"))
            return 7;
        if(label.equals("Diritti"))
            return 8;
        return 10;
    }
}
