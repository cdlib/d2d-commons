package org.cdlib.services.hparser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests static methods Holdings class. Note current year is hard-coded.
 * @author jferrie
 */
public class HoldingsTest {
    
    /**
     * Test of getCurrentHoldingsYear method, of class Holdings.
     */
    @Test
    public void testGetCurrentHoldingsYear() {
        
        assertEquals(Holdings.getCurrentHoldingsYear(), "2016");
    }
    
    @Test
    public void testGetEarliestYear() {
        HoldingsParser hp = Holdings.getParser("1.1 1- 1000/1700- 1.1 1-12 1000-1900 Ser vol; mark by v.; update cover date on receipt szstx s - 4");
        String expected = "";
        String result = null;
        try {
            result = hp.getEarliestYear();
        }
        catch (HoldingsParserException e) {
            fail(e.getMessage());
        }
        assertEquals(expected, result);      
    }

}
