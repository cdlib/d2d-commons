package org.cdlib.holdings;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;

/**
 * Loads an XML file from disk with a set of holdings records
 * used for testing utilities.
 * 
 * The holdings records have both the holdings and expected results, which are used
 * as the basis for JUnit tests.
 * 
 * The static main method is a convenience for creating a template for the XML file.
 * This probably will be used only when we want to change the schema and generate
 * a new template.
 * 
 * @author jferrie
 */
public class TestDataLoader {

    private Logger mLogger;

    public TestDataLoader() {
        mLogger = Logger.getLogger(TestDataLoader.class);
    }

    /*
     * Get the test data to use in jUnit tests.
     */
    public List<TestHoldingsItem> getTestHoldingsList() {
        return unmarshallTestData().testHoldingsList;
    }

    /*
     * Get the test data from disk and unmarshall it into a TestHoldingsList.
     * The tests will walk this object to execute tests.
     */
    private TestHoldingsList unmarshallTestData() {
        Object holdings;
        mLogger.debug("Unmarshalling test data.");
        try {
            // load this from the classpath so we don't have to worry about 
            // the differences between paths on different systems      
            InputStream is = getClass().getClassLoader().getResourceAsStream("test_holdings.xml");
            JAXBContext jc = JAXBContext.newInstance(TestHoldingsList.class, TestHoldingsItem.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            holdings = unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            mLogger.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
        return (TestHoldingsList) holdings;
    }

    /*
     * Create some temporary objects with dummy values
     * and use these to create a template file.
     * The file will be populated with test values and used to drive tests.
     */
    private static void marshallTestData() {
        TestHoldingsList theList = new TestHoldingsList();
        TestHoldingsItem item1 = new TestHoldingsItem();
        item1.holdings = "This is the data";
        item1.earliestYear = 1200;
        item1.latestYear = 2012;


        TestHoldingsItem item2 = new TestHoldingsItem();
        item2.holdings = "This is the data";
        item2.earliestYear = 1200;
        item2.latestYear = 2012;


        List<TestHoldingsItem> l = new ArrayList<TestHoldingsItem>();
        l.add(item1);
        l.add(item2);
        theList.testHoldingsList = l;

        Logger logger = Logger.getLogger(TestDataLoader.class);

        try {
            JAXBContext jc = JAXBContext.newInstance(TestHoldingsList.class, TestHoldingsItem.class);
            Marshaller marshaller = jc.createMarshaller();

            // We are only expecting to have to do this in a local Windows environment.
            // If it turns out this is not the case, this should be moved into a Properties config.
            OutputStream os = new FileOutputStream("c:/projects/HoldingsParser/doc/outTestData.xml");
            marshaller.marshal(theList, os);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
    /*
     * Create a template for test data.
     */
    public static void main(String[] args) {
        marshallTestData();
    }
}
