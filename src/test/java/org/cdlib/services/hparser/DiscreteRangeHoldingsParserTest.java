package org.cdlib.services.hparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Direct tests of DiscreteRangeHoldingParser methods.
 *
 * @author jferrie
 */
public class DiscreteRangeHoldingsParserTest {

  public DiscreteRangeHoldingsParserTest() {
  }

  /**
   * Test of getYearRange method.
   */
  @Test
  public void testAddSingleYears() {
    String holdings = "1.1 1990, 1995 3 1-3 g 1.2 1997 1-2 1-1 (2001) s - 5";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> resultList = hp.getSingleYears(Holdings.SINGLE_YEAR);
    Integer[] expectArray = new Integer[]{1990, 1995, 1997, 2001};
    List<Integer> expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);
  }

  /*
   * Test of addNormalYearRange method.
   */
  @Test
  public void testAddNormalYearRanges() {
    String holdings = "1.1 1982-1985 g 1.2 1995-1996 g 1.3 2001 g 1.4 2004-2005 Ser vol.; mark by yr. s - 2";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> resultList = hp.getYearRanges(Holdings.NORMAL_RANGE);
    Integer[] expectArray = new Integer[]{1982, 1983, 1984, 1985, 1995, 1996, 2004, 2005};
    List expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    // test again with a year overlap
    holdings = "1.1 1982-1985 g 1.2 1985-1987 g 1.3 2001 g 1.4 2004-2005 Ser vol.; mark by yr. s - 2";
    hp = new DiscreteRangeHoldingsParser(holdings);
    resultList = hp.getYearRanges(Holdings.NORMAL_RANGE);
    expectArray = new Integer[]{1982, 1983, 1984, 1985, 1985, 1986, 1987, 2004, 2005};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    // test with range that should be excluded
    holdings = "1.1 1- 1000/1700- 1.1 1-12 1000-1900 2000- Ser vol; mark by v.; update cover date on receipt szstx s - 4";
    hp = new DiscreteRangeHoldingsParser(holdings);
    resultList = hp.getYearRanges(Holdings.NORMAL_RANGE);
    expectArray = new Integer[]{};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);
  }

  /*
   * Test of addNormalYearRange method.
   */
  @Test
  public void testAddYearRanges() {
    String holdings = "1.1 1- 1000/1700- 1.1 1-12 1000-1900 2000/2001 Ser vol; mark by v.; update cover date on receipt szstx s - 4";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> resultList = hp.getYearRanges(Holdings.DOUBLED_YEAR);
    Integer[] expectArray = new Integer[]{2000, 2001};
    List expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    resultList = hp.getYearRanges(Holdings.DOUBLED_LEFT_YEAR_RANGE);
    expectArray = new Integer[]{};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    resultList = hp.getYearRanges(Holdings.DOUBLED_RIGHT_YEAR_RANGE);
    expectArray = new Integer[]{};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    resultList = hp.getYearRanges(Holdings.RANGE_WITH_ITEM_INFO);
    expectArray = new Integer[]{};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    resultList = hp.getYearRanges(Holdings.DOUBLED_YEAR_RANGE);
    expectArray = new Integer[]{};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);
  }

  /*
   * Test of addToCurrentYearRanges method.
   */
  @Test
  public void testAddtoCurrentYearRanges() {
    String holdings = "1.1 23- 2007- Ser vol; mark by v.; check-in by yr. only s - 4";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);

    List<Integer> resultList = hp.getToCurrentYearRanges(Holdings.NORMAL_YEAR_TO_CURRENT);
    Integer[] expectArray = new Integer[]{2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016};
    List expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    resultList = hp.getToCurrentYearRanges(Holdings.DOUBLED_YEAR_TO_CURRENT);
    expectArray = new Integer[]{};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    holdings = "1.1 1- 1000/1700- 1.1 1-12 1000-1900 Ser vol; mark by v.; update cover date on receipt szstx s - 4";
    hp = new DiscreteRangeHoldingsParser(holdings);

    resultList = hp.getToCurrentYearRanges(Holdings.NORMAL_YEAR_TO_CURRENT);
    expectArray = new Integer[]{};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);

    resultList = hp.getToCurrentYearRanges(Holdings.DOUBLED_YEAR_TO_CURRENT);
    expectArray = new Integer[]{};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, resultList);
  }

  @Test
  public void testGetYearsHeld() {
    String holdings = "1.1 23- 1994-1996 2007- Ser vol; mark by v.; check-in by yr. only s - 4";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> result = null;
    try {
      result = hp.getYearsHeld();
    } catch (HoldingsParserException e) {
      fail(e.getMessage());
    }
    Integer[] expectArray = new Integer[]{1994, 1995, 1996, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016};
    List expectList = Arrays.asList(expectArray);
    assertEquals(expectList, result);

    holdings = "2000/01 IP - - 4";
    hp = new DiscreteRangeHoldingsParser(holdings);
    result = null;
    try {
      result = hp.getYearsHeld();
    } catch (HoldingsParserException e) {
      fail(e.getMessage());
    }
    expectArray = new Integer[]{2000, 2001};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, result);

    holdings = "1999/01 IP - - 4";
    hp = new DiscreteRangeHoldingsParser(holdings);
    result = null;
    try {
      result = hp.getYearsHeld();
    } catch (HoldingsParserException e) {
      fail(e.getMessage());
    }
    expectArray = new Integer[]{1999, 2000, 2001};
    expectList = Arrays.asList(expectArray);
    assertEquals(expectList, result);
  }

  @Test
  public void testGetMissingYears() {
    String holdings = "1.1 23- 1994-1996 2007- Ser vol; mark by v.; check-in by yr. only s - 4";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> result = null;
    try {
      result = hp.getMissingYears();
    } catch (Throwable e) {
      fail(e.getMessage());
    }
    Integer[] expectArray = new Integer[]{1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006};
    List expectList = Arrays.asList(expectArray);
    assertEquals(expectList, result);
  }

  @Test
  public void testGetEarliest() {
    String holdings = "1.1 1- 1000/1700- 1.1 1-12 1000-1900 Ser vol; mark by v.; update cover date on receipt szstx s - 4";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    String expected = "";
    String result = null;
    try {
      result = hp.getEarliestYear();
    } catch (Throwable e) {
      fail(e.getMessage());
    }
    assertEquals(expected, result);
  }

  @Test
  public void testExpressions() {

    String holdings = " 1.1 (1995-1996) ";
    DiscreteRangeHoldingsParser hp = new DiscreteRangeHoldingsParser(holdings);
    List<Integer> years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.NORMAL_RANGE));
    Integer[] expectArray = new Integer[]{1995, 1996};
    List expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    holdings = "1.1 (1996-1990) ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.NORMAL_RANGE));
    expectArray = new Integer[]{1996};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    // if the years are reversed, use individual years, but not the range
    holdings = "  1(1934)-17(1928) ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.RANGE_WITH_ITEM_INFO));
    expectArray = new Integer[]{1934, 1928};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);
    
    holdings = " 1.1 (Oct.1995-Nov.1996) ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.RANGE_WITH_ITEM_INFO));
    expectArray = new Integer[]{1995, 1996};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    holdings = " 1.1 Oct.1995-1996 ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.NORMAL_RANGE));
    expectArray = new Integer[]{1995, 1996};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    holdings = " 1.1 (2010- ) ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getToCurrentYearRanges(Holdings.NORMAL_YEAR_TO_CURRENT));
    expectArray = new Integer[]{2010, 2011, 2012, 2013, 2014, 2015, 2016};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    holdings = " 1.1 v.2010-  ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getToCurrentYearRanges(Holdings.NORMAL_YEAR_TO_CURRENT));
    expectList = new ArrayList<Integer>();
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    holdings = " 1.1 (Oct.2010- ) ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getToCurrentYearRanges(Holdings.NORMAL_YEAR_TO_CURRENT));
    expectArray = new Integer[]{2010, 2011, 2012, 2013, 2014, 2015, 2016};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    holdings = " 1.1 1995/96 ";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR));
    expectArray = new Integer[]{1995, 1996};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    holdings = "A2004 1937; 1943/44 v.2 Filmed with earlier title PRINTING MASTER y n 2";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR));
    expectArray = new Integer[]{1937, 1943, 1944};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    Collections.sort(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

    holdings = "A2004 1937-1943/44 v.2 Filmed with earlier title PRINTING MASTER y n 2";
    hp = new DiscreteRangeHoldingsParser(holdings);
    years = hp.getYears();
    years.addAll(hp.getYearRanges(Holdings.DOUBLED_2D_YEAR));
    expectArray = new Integer[]{1937, 1938, 1939, 1940, 1941, 1942, 1943, 1944};
    expectList = Arrays.asList(expectArray);
    years = hp.removeDuplicateYears(years);
    Collections.sort(years);
    //System.out.println("For holdings = " + holdings + " Expecting " + expectList + "; got " + years);
    assertEquals(expectList, years);

  }
}
