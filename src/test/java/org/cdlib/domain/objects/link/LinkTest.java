package org.cdlib.domain.objects.link;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class LinkTest {
  
  @Test
  public void testDeepCopy() {
    Link original = testLink();
    Link copy = new Link(original);
    assertFalse(copy == original);
    assertFalse(copy.getProperties() == original.getProperties());
    assertTrue("result".equals(copy.getProperties().get("test")));
    assertFalse(copy.getProperties().get("test") == original.getProperties().get("test"));
  }
  
  private Link testLink() {
    Link testLink = new Link();
    testLink.setHref("href");
    testLink.setMimeType("mimeType");
    Map<String, Object> testProperties = new HashMap<>();
    testProperties.put("test", "result");
    testLink.setProperties(testProperties);
    return testLink;
  }

}
