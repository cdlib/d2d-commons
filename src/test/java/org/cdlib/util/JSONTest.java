package org.cdlib.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.cdlib.domain.objects.link.Link;
import org.cdlib.test.NonBean;
import org.cdlib.test.SerializablePojo;
import org.cdlib.util.JSON.JSONConversionException;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.core.type.TypeReference;

public class JSONTest {
  
  private SerializablePojo pojo;
  private NonBean nonBean;
  
  @Before
  public void init() {
    pojo = new SerializablePojo();
    nonBean = new NonBean();
  }

  @Test(expected = JSONConversionException.class)
  public void failsOnNull() {
    JSON.serialize(null);
  }

  @Test
  public void serializesSimplePojo() {
    String result = JSON.serialize(pojo);
    assertNotNull(result);
    assertEquals("{\"testString\":\"test String val 好比不上\",\"testInt\":22}", result);
  }

  @Test(expected = JSONConversionException.class)
  public void failsOnNonBean() {
    JSON.serialize(nonBean);
  }

  @Test
  public void deserializesString() {
    SerializablePojo obj = JSON.deserialize("{\"testString\":\"test String val 好比不上\",\"testInt\":22}", SerializablePojo.class);
    assertEquals(22, obj.getTestInt());
    assertEquals("test String val 好比不上", obj.getTestString());
  }
  
  @Test
  public void deserializesGenericCollection() {
    List<Link> links = new ArrayList<>();
    Link link0 = new Link();
    link0.setHref("href0");
    link0.setMimeType("type0");
    Link link1 = new Link();
    link1.setHref("href1");
    link1.setMimeType("type1");
    links.add(link0);
    links.add(link1);
    String json = JSON.serialize(links);
    TypeReference<List<Link>> ref = new TypeReference<List<Link>> () {};
    List<Link> deserialized = JSON.deserialize(json, ref);
    assertEquals(links, deserialized);
    assertEquals(json, JSON.serialize(deserialized));
  }
 
}
