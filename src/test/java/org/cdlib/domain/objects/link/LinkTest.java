package org.cdlib.domain.objects.link;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.cdlib.domain.objects.bib.Carrier;
import org.junit.Test;

public class LinkTest {
  
  @Test
  public void testDeepCopy() {
    Link original = testLink();
    Link copy = new Link(original);
    assertFalse(copy == original);
    assertFalse(copy.getProperties() == original.getProperties());
    assertTrue(copy.getProperties().equals(original.getProperties()));
    assertTrue("result".equals(copy.getProperties().get("test")));
    assertFalse(copy.getProperties().get("test") == original.getProperties().get("test"));
    assertEquals(copy.getProperties().get("carrier"), original.getProperties().get("carrier"));
    assertEquals("BRAILLE", copy.getProperties().get("carrier"));
  }
  
  private Link testLink() {
    Link testLink = new Link();
    testLink.setHref("href");
    testLink.setMimeType("mimeType");
    Map<String, String> testProperties = new HashMap<>();
    testProperties.put("test", "result");
    testProperties.put("carrier", Carrier.BRAILLE.name());
    testLink.setProperties(testProperties);
    return testLink;
  }

}
