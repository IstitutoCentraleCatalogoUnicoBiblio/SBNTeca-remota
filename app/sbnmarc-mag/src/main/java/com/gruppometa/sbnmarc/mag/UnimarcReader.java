package com.gruppometa.sbnmarc.mag;

import com.gruppometa.sbnmarc.mag.object.UnimarcRecord;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringWriter;

public class UnimarcReader {

    protected MarcReader reader;
    protected String output = "mag";
    protected MagTransformer magTransformer;
    protected String firstStylesheet;
    protected String secondStylesheet;

    public UnimarcReader(InputStream inputStream, String type){
        reader = new MarcStreamReader(inputStream, "UTF-8");
        magTransformer = new MagTransformer();
        if(type.equals("unimarc2mods")) {
            firstStylesheet ="/UNIMARC2MARC21.xsl";
            secondStylesheet = "marc2mods";
        }
        if(type.equals("marc2mods")) {
            firstStylesheet ="/MARC21slim2MODS3-7-withUtils.xsl";
        }
        if(type.equals("unimarc2mag")){
            firstStylesheet ="/marcxml2sbnmarc.xsl";
            secondStylesheet = "sbnmarc2mag";
        }
    }

    public boolean hasNext(){
        return reader.hasNext();
    }

    public UnimarcRecord next() throws Exception {
        return map(reader.next());
    }

    private UnimarcRecord map(Record next) throws Exception {
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        InputStream inputXsl = UnimarcClient.class.getResourceAsStream(
                firstStylesheet
                ////"/marcxml2sbnmarc.xsl"
                //"/UNIMARC2MARC21.xsl"
                //"/MARC21slim2MODS3-7-withUtils.xsl"
        );
        Source stylesheet =  new StreamSource(inputXsl);

        MarcXmlWriter writer = new MarcXmlWriter(result
                , stylesheet
        );
        UnimarcRecord record = new UnimarcRecord();
        record.setId(filter(((ControlField)next.getVariableField("001")).getData()));
        writer.write(next);
        try {
            writer.close();
        }
        catch (Exception e){}
        if(secondStylesheet==null)
            record.setData((stringWriter.toString()));
        else
            record.setData(magTransformer.transform(stringWriter.toString()
                //.replace("<collection",
                //"<collection xmlns=\"http://www.loc.gov/MARC21/slim\"")
                , secondStylesheet
                    //"marc2mods"
                    ));
        return record;
    }

    private String filter(String bid) {
        return bid.replace("IT\\ICCU\\","").replace("\\","");
    }
}
