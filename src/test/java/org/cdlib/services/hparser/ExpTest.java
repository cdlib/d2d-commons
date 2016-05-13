package org.cdlib.services.hparser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ExpTest {

  @Test
  public void testShortYear() {
    yearTest(String.format("(%s)/(%s)", Holdings.BASE_YEAR, Holdings.SHORT_YEAR), " 1921/23 ", "1921", "23");
  }

  @Test
  public void testShortYear1() {
    yearTest(String.format("(%s)/(%s)", Holdings.BASE_YEAR, Holdings.SHORT_YEAR), " 1921/2 ", "1921", "2");
  }

  @Test
  public void testSingleYear() {
    yearTest(Holdings.PAREN_RANGE, " (1997-NOV1998) ", "1997", "1998");
  }

  private void yearTest(String matchExp, String holdings, String... exp) {
    Pattern pattern = Pattern.compile(matchExp, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(holdings);
    while (matcher.find()) {
      for (int i = 1; i <= matcher.groupCount(); i++) {
        assertEquals(exp[i - 1], matcher.group(i));
      }
    }
  }
  
  @Test
  public void testParenRange() {
    testExp(" 1.1 (1995-1996) ", Holdings.NORMAL_RANGE_PAT, new Integer[]{1995,1996});
  }
  
  @Test
  public void testShortYear3() {
    testExp("1.1 1995/96", Holdings.DOUBLED_2D_YEAR_PAT, new Integer[]{1995, 1996});
  }
  
  @Test
  public void testShortYear4() {
    testExp("1.1 1999/00", Holdings.DOUBLED_2D_YEAR_PAT, new Integer[]{1999, 2000});
  }
  
  @Test
  public void testShortYear5() {
    testExp("1.1 1999/0", Holdings.DOUBLED_2D_YEAR_PAT, new Integer[]{1999, 2000});
  }
  
  @Test
  public void testShortYear6() {
    testExp("library_holdings: BOUND : v.1-19; 1996/1997-1999/00.", Holdings.DOUBLED_2D_YEAR_RANGE_PAT, new Integer[]{1996,1997,1998,1999, 2000});
  }
  
    
  
  private void testExp(String holdings, Pattern pattern, Integer[] expected) {
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = hp.getYears();
    years.addAll(hp.getYearRanges(pattern.matcher(holdings), holdings));
    List expectList = Arrays.asList(expected);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);
  }
}
