package org.cdlib.holdings;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

/**
 * Test whether we can load some data and create a test data object.
 * @author jferrie
 */
public class TestDataLoaderTest {

    @Test
    public void testGetTestHoldings() {
        TestDataLoader tdl = new TestDataLoader();
        List<TestHoldingsItem> thl = tdl.getTestHoldingsList();
        assertNotNull(thl);
    }
}
