package org.cdlib.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XPathFacade {

  private final XPath xpath;
  private final DocumentBuilder builder;
  private final Document document;
  private static final Logger LOG = LogManager.getLogger(XPathFacade.class);

  public XPathFacade(String xml) {
    LOG.debug("XPathHelper got xml: " + xml);
    try {
      Assert.hasText(xml, "XML has no content, cannot parse.");
      this.xpath = initXPath();
      this.builder = initDocumentBuilder();
      document = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
    } catch (IllegalArgumentException | SAXException | IOException e) {
      throw new XPathFacadeException("Could not parse xml " + xml, e);
    } catch (RuntimeException e) {
      throw new XPathFacadeException("Caught unexpected runtime exception: " + e.getMessage(), e);
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
      throw new XPathFacadeException(e);
    }
    return documentBuilder;
  }

  public String getContent(String path) {
    try {
      return xpath.compile(path).evaluate(document);
    } catch (XPathExpressionException e) {
      throw new XPathFacadeException(e);
    }
  }

  /**
   * @param path an expression that evaluates to a list of nodes
   * @return a List<String> with the text of the nodes extracted by the path
   * expression
   */
  public List<String> getNodeListContent(String path) {
    List<String> result = new ArrayList<>();
    try {
      NodeList nodeList = (NodeList) xpath.compile(path).evaluate(document, XPathConstants.NODESET);
      for (int i = 0; i <= nodeList.getLength() - 1; i++) {
        String content = nodeList.item(i).getTextContent();
        if (content != null) {
          result.add(nodeList.item(i).getTextContent());
        }
      }
      return result;
    } catch (XPathExpressionException e) {
      throw new XPathFacadeException(e);
    }
  }

}
