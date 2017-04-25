package org.cdlib.services.hparser;

import org.junit.Before;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * JUnit tests for HoldingsParser methods. These tests are run using the test data in test_holdings.xml.
 * The test data is loaded and this class is instantiated by JUnit once for each item in the set of test data.
 * 
 * @author jferrie
 */
@RunWith(Parameterized.class)
public class HoldingsParserTest {

    private String mHoldings;
    private Integer mEarliestYear;
    private Integer mLatestYear;
    private Logger mLogger;

    /*
     * JUnit calls this contructor once for each item in the Collection returned by getHoldingsList
     */
    public HoldingsParserTest(String holdings, Integer earliestYear, Integer latestYear) {
        this.mHoldings = holdings;
        this.mEarliestYear = earliestYear;
        this.mLatestYear = latestYear;
    }

    @Before
    public void setUp() {
        this.mLogger = LogManager.getLogger(HoldingsParserTest.class);
    }

    /*
     * This method is provided so that JUnit can run multiple parameterized tests.
     * It builds an array of Object, with each array holding the params to instantiate the test.
     * JUnit calls this method and instantiates each test using a constructor that expects
     * the values provided in the array.
     * 
     * The test parameters are loaded from a test data XML, populating the TestHoldingsList
     * that is used to build the Collection of arrays.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> getHoldingsList() {
        TestDataLoader testDataLoader = new TestDataLoader();
        List<TestHoldingsItem> testHoldingsList = testDataLoader.getTestHoldingsList();
        ArrayList<Object[]> testParamList = new ArrayList<Object[]>();
        Integer latest;
        for (TestHoldingsItem testHoldingsItem : testHoldingsList) {
            // In the test data, -1 means calculate the current holdings year
            if (testHoldingsItem.latestYear == -1) {
                latest = Integer.parseInt(Holdings.getCurrentHoldingsYear());
            } else {
                latest = testHoldingsItem.latestYear;
            }
            Object[] parameters = {testHoldingsItem.holdings, testHoldingsItem.earliestYear, latest};
            testParamList.add(parameters);
        }
        return testParamList;
    }

    @Test
    public void testGetEarliestYear() throws Exception {
        try {
            String expResult = this.mEarliestYear.toString();

            // get the right value to compare to for an empty string result
            expResult = (expResult.equals("0")) ? "" : expResult;

            if (expResult.equals("1700")) {
                mLogger.error("Found record: expected result is: " + mEarliestYear);
            }
            HoldingsParser hp = Holdings.getParser(mHoldings);
            String result = hp.getEarliestYear();
            assertEquals(expResult, result);

        } catch (Exception e) {
            mLogger.debug("Error on holdings " + mHoldings + e.getStackTrace());
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetLatestYear() throws Exception {
        try {
            String expResult = mLatestYear.toString();

            // get the right value to compare to for an empty string result
            expResult = (expResult.equals("0")) ? "" : expResult;
            HoldingsParser hp = Holdings.getParser(mHoldings);
            String result = hp.getLatestYear();
            assertEquals(expResult, result);
        } catch (Exception e) {
            mLogger.debug("Error on holdings " + mHoldings + e.getStackTrace());
            fail(e.getMessage());
        }
    }

 /*   @Test
    public void testGetRange() throws Exception {
        int expected;
        // if there were no holdings expected in the results these
        // will be empty in the test data and set to 0
        if (mLatestYear == 0 && mEarliestYear == 0) {
            expected = 0;
        } else {
            expected = (mLatestYear - mEarliestYear) + 1;
        }
        try {
            HoldingsParser hp = Holdings.getParser(mHoldings);
            int result = hp.getYearRange();
            assertEquals(expected, result);
        } catch (Exception e) {
            mLogger.debug("Error on holdings " + mHoldings + e.getStackTrace());
            fail(e.getMessage());
        }
    } */
}
