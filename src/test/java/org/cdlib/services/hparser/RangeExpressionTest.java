package org.cdlib.services.hparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Direct tests of DiscreteRangeHoldingParser methods.
 *
 * @author jferrie
 */
public class RangeExpressionTest {

  public RangeExpressionTest() {
  }
  
  @Test
  public void testParenRangeExpressions() {
    
    String holdings = " 1(1928)-17(1930) ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = new ArrayList<>();
    years.addAll(hp.getYearRanges(Holdings.TWO_PAREN_RANGE_PAT.matcher(holdings), holdings));
    Integer[] expectArray = new Integer[]{1928,1929,1930};
    List expectList = Arrays.asList(expectArray);
    assertEquals(expectList, years);
    
    holdings = " v.1(1928)-v.17(1930) ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = new ArrayList<>();
    years.addAll(hp.getYearRanges(Holdings.TWO_PAREN_RANGE_PAT.matcher(holdings), holdings));
    expectArray = new Integer[]{1928,1929,1930};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, years);
    
  }
  
  @Test
  public void testParenRangeExp() {
    String holdings = " (1910-June 1912) ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = new ArrayList<>();
    years.addAll(hp.getYearRanges(Holdings.PAREN_RANGE_PAT.matcher(holdings), holdings));
    Integer[] expectArray = new Integer[]{1910,1911,1912};
    List expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);
  }
}
