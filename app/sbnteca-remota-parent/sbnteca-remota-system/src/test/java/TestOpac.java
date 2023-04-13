import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbnmarc.mag.MagTransformer;
import com.gruppometa.sbnmarc.mag.UnimarcClient;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TestOpac {

    // @Test
    public void testMag(){
        Mag mag = new Mag();
        String id = "sdsds$s<\"";
        mag.getIdentifiers().add(id);
        mag.setCreation("2018-01-01T12:00:00");
        mag.setLevel("s");
        UtilSolr.createMagIdentifier(mag);
        Assert.assertEquals(id+"_"+mag.getLevel()+"_"+"20180101120000",mag.getIdMag());
    }
    // @Test
    public void test(){
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element el = doc.createElement("root");
            el.setTextContent("");
            doc.appendChild(el);
            el = doc.createElement("bla");
            el.setTextContent("");
            doc.getDocumentElement().appendChild(el);
            System.out.println(UtilXML.convertDocumentToString(doc));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
    public void test23(){
        //System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");
        UnimarcClient unimarcClient = new UnimarcClient();
        MagTransformer magTransformer = new MagTransformer();
        String identifier = "CSA0141277";
        try {
            String mag = unimarcClient.getOpacSbn2Mag(magTransformer, identifier);
            System.out.println(mag);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
