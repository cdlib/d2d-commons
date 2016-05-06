package org.cdlib.services.hparser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests static methods Holdings class. Note current year is hard-coded.
 *
 * @author jferrie
 */
public class ExpTest {

  @Test
  public void testSingleYear() {
    Pattern pattern = Pattern.compile(Holdings.SINGLE_YEAR, Pattern.CASE_INSENSITIVE);
    String holdings = " <2003-2004> ";
    Matcher matcher = pattern.matcher(holdings);
    ArrayList<Integer> list = new ArrayList<>();

    int i = 0;
    while (matcher.find()) {
      String found = matcher.group(1);
      int foundInt = Integer.parseInt(found);
      list.add(foundInt);
    }
    for (Integer j : list) {
      System.out.println(j);
    }
    assertTrue(list.get(0).equals(2003));
  }
}
