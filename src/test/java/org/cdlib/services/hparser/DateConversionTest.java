package org.cdlib.services.hparser;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Direct tests of DiscreteRangeHoldingParser methods.
 *
 * @author jferrie
 */
public class DateConversionTest {

  public DateConversionTest() {
  }

  @Test
  public void testDateConversion() {

    Integer result = DiscreteRangeHoldingsParser.toFourDigitYear(1991, 2);
    // System.out.println(result);
    assertTrue(result.equals(1992));

    result = DiscreteRangeHoldingsParser.toFourDigitYear(1999, 1);
    // System.out.println(result);
    assertTrue(result.equals(2001));
  }

  @Test
  public void testExp() {
    String holdings = " 1.1 1995/6 ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = hp.getYears();
    for (int i : years) {
      // System.out.println(i);
    }
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR_PAT.matcher(holdings), holdings));
    Integer[] expectArray = new Integer[]{1995, 1996};
    List<Integer> expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);
  }
  
  @Test
  public void testExp1() {
    String holdings = " 1.1 1984/85 ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = hp.getYears();
    assertEquals(2, years.size());
    for (int i : years) {
      // System.out.println(i);
    }
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR_PAT.matcher(holdings), holdings));
    Integer[] expectArray = new Integer[]{1984, 1985};
    List<Integer> expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);
  }
}
