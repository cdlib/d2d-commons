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
    Pattern pattern = Pattern.compile(Holdings.NORMAL_RANGE, Pattern.CASE_INSENSITIVE);
    String holdings = "(1989-1990)(1997-NOV1998)";
    Matcher matcher = pattern.matcher(holdings);
    ArrayList<Integer> list = new ArrayList<>();
    System.out.println("count: " + matcher.groupCount());

    int i = 0;
    while (matcher.find()) {
      String found = matcher.group(1);
      String found1 = matcher.group(2);
      System.out.println("1: " + found + " 2: " + found1);
      int foundInt = Integer.parseInt(found);
      list.add(foundInt);
    }
    for (Integer j : list) {
      System.out.println(j);
    }
  }
}
