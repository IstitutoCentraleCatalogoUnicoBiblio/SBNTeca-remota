import com.gruppometa.mets2mag.MetsConvertor;
import com.gruppometa.sbntecaremota.retrieve.AbstractResourceDelivery;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class TestCsv {

    private Logger logger = LoggerFactory.getLogger(TestCsv.class);

    @Test
    public void testCsv() throws Exception {

        String encoding = "UTF-8";
        String filename = TestCsv.class.getResource("/test.csv").getFile();

        BufferedReader br = new BufferedReader((Reader) (encoding != null ? new InputStreamReader(
                new FileInputStream(filename), encoding) : new FileReader(filename)));
        int i = 0;
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .builder()
                .setDelimiter('\t')
                .build().withFirstRecordAsHeader();
        Iterable<CSVRecord> records = csvFormat.parse(br);
        for (CSVRecord record : records) {
            logger.info("Get " + record.get("bid"));
            StringWriter stringWriter = new StringWriter();
            String modsString = new MetsConvertor().convertMods2Json(
                    AbstractResourceDelivery.makeMods(record).getMods().get(0), stringWriter);
            System.out.println(modsString+stringWriter.toString());
        }
    }
}
