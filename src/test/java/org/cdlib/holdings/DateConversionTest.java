package org.cdlib.holdings;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
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
  public void testExp() {
    String holdings = " 1.1 1995/6 ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR_PAT.matcher(holdings), holdings));
    Integer[] expectArray = new Integer[]{1995, 1996};
    List<Integer> expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    assertEquals(expectList, years);
  }
  
  @Test
  public void testExp1() {
    String holdings = " 1.1 1984/85 ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = hp.getYears();
    assertEquals(2, years.size());
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR_PAT.matcher(holdings), holdings));
    Integer[] expectArray = new Integer[]{1984, 1985};
    List<Integer> expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    assertEquals(expectList, years);
  }
  
  @Test
  public void testExp2() {
    String holdings = " 1.1 1999/01 ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = hp.getYears();
    assertEquals(3, years.size());
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR_PAT.matcher(holdings), holdings));
    Integer[] expectArray = new Integer[]{1999, 2000, 2001};
    List<Integer> expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    assertEquals(expectList, years);
  }
  
  @Test
  public void testExp3() {
    String holdings = " 1.1 1999/1 ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = hp.getYears();
    assertEquals(3, years.size());
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR_PAT.matcher(holdings), holdings));
    Integer[] expectArray = new Integer[]{1999, 2000, 2001};
    List<Integer> expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    assertEquals(expectList, years);
  }
}
