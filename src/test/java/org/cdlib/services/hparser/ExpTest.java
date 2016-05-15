package org.cdlib.services.hparser;

import java.util.ArrayList;
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

  @Test
  public void testDoubledYear() {
    yearTest(Holdings.DOUBLED_YEAR_RANGE, " 1-58(1987/88-1991/92) ", "1987", "1991", "92");
  }

  @Test
  public void testDoubledYear1() {
    yearTest(Holdings.DOUBLED_YEAR_RANGE, " 1-58(1884/5-1991/2)  ", "1884", "1991", "2");
  }

  @Test
  public void testDoubledYear2() {
    yearTest(Holdings.DOUBLED_YEAR, " 1-58(1987/88) ", "1987", "88");
  }

  @Test
  public void testDoubledYear3() {
    yearTest(Holdings.DOUBLED_ALL_SHORT_RANGE, " ser.:2:v.1-3(1923/4-26/7) ", "1923", "26", "7");
  }

  private void yearTest(String matchExp, String holdings, String... exp) {
    Pattern pattern = Pattern.compile(matchExp, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(holdings);
    while (matcher.find()) {
      for (int i = 1; i <= matcher.groupCount(); i++) {
        assertEquals(exp[i - 1], matcher.group(i));
        //System.out.println(matcher.group(i));
      }
    }
  }

  @Test
  public void testParenRange() {
    testExp(" 1.1 (1995-1996) ", Holdings.NORMAL_RANGE_PAT, new Integer[]{1995, 1996});
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
    testExp("library_holdings: BOUND : v.1-19; 1996/1997-1999/00.", Holdings.DOUBLED_2D_YEAR_RANGE_PAT, new Integer[]{1996, 1997, 1998, 1999, 2000});
  }

  @Test
  public void testShortYear7() {
    testExp(" 1-58(1884/5-1991/2) ", Holdings.DOUBLED_2D_YEAR_RANGE_PAT, new Integer[]{1884, 1885, 1886, 1887, 1888, 1889, 1890, 1891, 1892, 1893, 1894, 1895, 1896, 1897, 1898, 1899, 1900, 1901, 1902, 1903, 1904, 1905, 1906, 1907, 1908, 1909, 1910, 1911, 1912, 1913, 1914, 1915, 1916, 1917, 1918, 1919, 1920, 1921, 1922, 1923, 1924, 1925, 1926, 1927, 1928, 1929, 1930, 1931, 1932, 1933, 1934, 1935, 1936, 1937, 1938, 1939, 1940, 1941, 1942, 1943, 1944, 1945, 1946, 1947, 1948, 1949, 1950, 1951, 1952, 1953, 1954, 1955, 1956, 1957, 1958, 1959, 1960, 1961, 1962, 1963, 1964, 1965, 1966, 1967, 1968, 1969, 1970, 1971, 1972, 1973, 1974, 1975, 1976, 1977, 1978, 1979, 1980, 1981, 1982, 1983, 1984, 1985, 1986, 1987, 1988, 1989, 1990, 1991, 1992});
  }

  @Test
  public void testShortYear8() {
    testExp("(1998-09)", Holdings.DOUBLED_2D_YEAR_RANGE_PAT, new Integer[]{1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009});
  }

  @Test
  public void testShortYear9() {
    testExp("1.1  1000-1900 Ser vol; mark by v.; update cover date on receipt szstx s - 4", Holdings.NORMAL_RANGE_PAT, new Integer[]{});
  }

  @Test
  public void testShortYear10() {
    testExp(" ser.:2:v.1-3(1923/4-26/7) ", Holdings.DOUBLED_ALL_SHORT_RANGE_PAT, new Integer[]{1923, 1924, 1925, 1926, 1927});
  }

  @Test
  public void testShortYear11() {
    testExp(" ser.:2:v.1-3(1923/4-26) ", Holdings.DOUBLED_LEFT_2D_SHORT_RIGHT_PAT, new Integer[]{1923, 1924, 1925, 1926});
  }

  @Test
  public void testParenRangeExp() {
    testExp(" 1(1928)-17(1930) ", Holdings.TWO_PAREN_RANGE_PAT, new Integer[]{1928, 1929, 1930});
  }

  @Test
  public void testParenRangeExp1() {
    testExp(" 1(1928)-17(1930) ", Holdings.TWO_PAREN_RANGE_PAT, new Integer[]{1928, 1929, 1930});
  }

  @Test
  public void testParenRangeExp2() {
    testExp(" (1910-June 1912) ", Holdings.TWO_PAREN_RANGE_PAT, new Integer[]{1910, 1911, 1912});
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
