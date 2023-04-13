package culturaItalia.skosloader;

import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        SAXReader reader = new SAXReader();
        try {
//			reader.read(new URL(
//					//"http://151.12.80.67/oai/interfaccia.jsp?verb=ListSets"
//					//"http://vast-lab.org/achille/iccd-prova.xml"
//					));
		} catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
}
