package org.cdlib.util;

import org.cdlib.test.NonBean;
import org.cdlib.test.SerializablePojo;
import org.cdlib.util.JSON.JSONConversionException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jferrie
 */
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
    assertEquals("{\"testString\":\"test String val\",\"testInt\":22}", result);
  }

  @Test(expected = JSONConversionException.class)
  public void failsOnNonBean() {
    JSON.serialize(nonBean);
  }

  @Test
  public void serializesString() {
    SerializablePojo obj = JSON.deserialize("{\"testString\":\"test String val\",\"testInt\":22}", SerializablePojo.class);
    assertEquals(22, obj.getTestInt());
  }

}
