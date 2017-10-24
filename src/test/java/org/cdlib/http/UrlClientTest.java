package org.cdlib.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jferrie
 */
public class UrlClientTest {

  private final static String baseAddress = "http://pirs-stage.cdlib.org/pirs/patron?";
  private final static String campus = "UCB";
  private final static String barcode = "010000111";
  private final static String password = null;
  private static final Logger logger = LogManager.getLogger(UrlClientTest.class);

  @Test
  public void testUrlClient() {
    URLClient client = new URLClientImpl();
    StringBuilder address = new StringBuilder(baseAddress);
    address.append("&id=").append(barcode);
    address.append("&campus=").append(campus);
    if (password != null) {
      address.append("&pin=").append(password);
    }
    logger.debug("Authorization request: " + address);
    String authResult = client.doURLGet(address.toString());
    assertTrue(authResult.startsWith("<?xml"));
    logger.debug("Authorization result: " + authResult);
  }

}
