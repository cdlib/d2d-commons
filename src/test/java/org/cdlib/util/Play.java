package org.cdlib.util;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.cdlib.domain.objects.bib.Title;
import org.junit.Test;

public class Play {
  
  @Test
  public void givenOptional_whenMapWorks_thenCorrect() {
      List<String> companyNames = Arrays.asList(
        "paypal", "oracle", "", "microsoft", "", "apple");
      Optional<List<String>> listOptional = Optional.of(companyNames);
   
      int size = listOptional
        .map(lst -> lst.size())
        .orElse(0);
      assertEquals(6, size);
  }
  
  @Test
  public void testOrElseVerbose() {
      String st = "hello";
      Optional<String> stOptional = Optional.of(st);
      String result;
      if (stOptional.isPresent()) {
        result = stOptional.get();
      } else {
        result = null;
      }
      assertEquals(st, result);
  }
  
  @Test
  public void testOrElse() {
      String st = "hello";
      Optional<String> stOptional = Optional.of(st);
      String st1 = stOptional.orElse(null);
      assertEquals(st, st1);
  }
  
  @Test
  public void testOrElseWithNull() {
      String st = null;
      Optional<String> stOptional = Optional.ofNullable(st);
      String st1 = stOptional.orElse("");
      assertTrue(st1.isEmpty());
  }
  
  private static Title title(String main) {
    Title result = new Title();
    result.setMainTitle(main);
    return result;
  }
  
  

}
