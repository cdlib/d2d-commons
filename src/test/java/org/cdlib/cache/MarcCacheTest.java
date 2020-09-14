package org.cdlib.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.cdlib.domain.objects.identifier.ISBN;
import org.cdlib.domain.objects.identifier.ISSN;
import org.cdlib.domain.objects.identifier.Identifier;
import org.cdlib.domain.objects.identifier.OclcNumber;
import org.junit.Test;

public class MarcCacheTest {

  private Cache<Identifier, String> marcCache;
  
  @Test
  public void removeEldestTest() {
    marcCache = new Cache<Identifier, String>(2);
    marcCache.put(new ISBN("12345"), "A");
    marcCache.put(new ISSN("6789-1234"), "b");
    marcCache.put(new OclcNumber("12345"), "c");
    
    assertEquals(2, marcCache.size());
    assertNull(marcCache.get(new ISBN("12345")));
    assertEquals("b", marcCache.get(new ISSN("6789-1234")));
  }
}
