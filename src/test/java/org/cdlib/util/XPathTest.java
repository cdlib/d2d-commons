package org.cdlib.util;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class XPathTest {

  private static final String tiny_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>hello</test>";
   private static final String xml_with_children = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test><boy>junior</boy><boy>bob</boy></test>";
  private XPathFacade xPathHelper;

  @Before
  public void init() {
    xPathHelper = new XPathFacade(tiny_xml);
  }

  @Test
  public void testParseSimpleXML() {
    assertEquals("hello", xPathHelper.getContent("/test"));
  }
  
  @Test
  public void testParseChildXML() {
    xPathHelper = new XPathFacade(xml_with_children);
    List<String> addresses = xPathHelper.getNodeListContent("/test/child::*");
    assertEquals("junior", addresses.get(0));
    assertEquals("bob", addresses.get(1));
  }

}
