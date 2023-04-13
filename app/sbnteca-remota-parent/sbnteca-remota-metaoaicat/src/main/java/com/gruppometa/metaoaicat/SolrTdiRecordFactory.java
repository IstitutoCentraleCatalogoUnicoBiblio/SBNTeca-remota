package com.gruppometa.metaoaicat;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class SolrTdiRecordFactory extends SolrRecordFactory{
    HashMap<String, String> mappingTdiMagteca = new HashMap<>();
    HashMap<String, String> mappingMagtecaTdi = new HashMap<>();

    protected static final Logger LOGGER = LoggerFactory.getLogger(SolrTdiRecordFactory.class);

    public SolrTdiRecordFactory(Properties properties) {
        super(properties);
        loadMappingFiles();
    }
    protected void loadMappingFiles() {
        loadMappingFile("/mapping-magteca-tdi.csv", mappingMagtecaTdi, false);
        loadMappingFile("/mapping-magteca-tdi.csv", mappingTdiMagteca, true);
        loadMappingFile("/mapping-magteca-tdi-manual.csv", mappingMagtecaTdi, false);
        loadMappingFile("/mapping-magteca-tdi-manual.csv", mappingTdiMagteca, true);
    }

    protected void loadMappingFile(String name, HashMap<String, String> map, boolean reverse) {
        try{
            InputStream fstream = SolrTdiRecordFactory.class.getResourceAsStream(name);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            String lastStr=null;
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                String[] strings = strLine.split("\t");
                if(strings.length==2){
                    if(reverse)
                        map.put(strings[1],strings[0]);
                    else
                        map.put(strings[0],strings[1]);
                }
            }
            //Close the input stream
            in.close();
            LOGGER.info("Loaded "+ name + " [reverse="+reverse+"]");
        }
        catch(Exception e){
            LOGGER.error("",e);
        }
    }

    protected static String SWITCH_PREFIX = "N:";
    @Override
    public String fromOAIIdentifier(String identifier) {
        if(identifier.length()<prefix.length())
            return identifier;
        String oaiId = identifier.substring(prefix.length()+1);
        if(!oaiId.startsWith(SWITCH_PREFIX)) {
            if (mappingMagtecaTdi.containsKey(oaiId))
                return mappingMagtecaTdi.get(oaiId);
        }
        oaiId = oaiId.substring(2);
        oaiId = oaiId.replace("\\\\", "\\");
        return oaiId;
    }

    @Override
    public String getOAIIdentifier(Object nativeItem) {
        SolrDocument doc = (SolrDocument)nativeItem;
        String solrId = (String)doc.getFieldValue(idField);
        if(solrId==null){
            LOGGER.error("Item without "+idField+ " id="+doc.getFieldValue("id"));
            solrId = (String) doc.getFieldValue("id");
        }
        if(mappingTdiMagteca.containsKey(solrId))
            solrId = mappingTdiMagteca.get(solrId);
        else
            solrId = SWITCH_PREFIX+solrId.replace("\\","\\\\");
        return prefix+":"+solrId;
    }

    @Override
    public List<String> getNeededFields(){
        List<String> fields = new ArrayList<String>();
        fields.add(idField);
        fields.add("timestamp");
        if(deleteField!=null)
            fields.add(deleteField);
        fields.add("id");
        return fields;
    }
}
