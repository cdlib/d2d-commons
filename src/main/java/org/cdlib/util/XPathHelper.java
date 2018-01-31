package org.cdlib.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author jferrie
 */
public class XPathHelper {

  private final XPath xpath;
  private final DocumentBuilder builder;
  private final Document document;
  private static final Logger LOG = LogManager.getLogger(XPathHelper.class);

  public XPathHelper(String xml) {
    LOG.debug("XPathHelper got xml: " + xml);
    Assert.hasText(xml, "XML has no content, cannot parse.");
    this.xpath = initXPath();
    this.builder = initDocumentBuilder();
    try {
      document = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
    } catch (SAXException | IOException e) {
      throw new RuntimeException("Could not parse xml " + xml, e);
    }
  }

  private XPath initXPath() {
    XPathFactory xPathfactory = XPathFactory.newInstance();
    return xPathfactory.newXPath();
  }

  private DocumentBuilder initDocumentBuilder() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;
    try {
      documentBuilder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    return documentBuilder;
  }


  /**
   *
   * Returns content
   */
  public String getContent(String path) {
    try {
      return xpath.compile(path).evaluate(document);
    } catch (XPathExpressionException e) {
      throw new RuntimeException(e);
    }
  }

}
