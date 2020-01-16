package org.cdlib.xml;

import java.util.Vector;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * DOMParser handles a number of the general utility functions for parsing
 * XML.
 *
 * @author loy/mjt
 */

// Modifications:
//  5/13/09 - Removed unused code and cleaned up existing methods. (MJT)

public class DOMParser {

    private static Logger LOGGER = Logger.getLogger(DOMParser.class);

    public DocumentBuilder db = null;

    /**
     * DOMParser constructor 
     */
    public DOMParser() {
        setup();
    }

    private boolean setup() {
        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * extractList - Return a list of Text objects with a given tag name.
     * @param elem - the top node to search from
     * @param name - the tag name to find
     * @return Vector<String>
     */
    public Vector<String> extractList(Element elem, String name) {
        if (elem == null) {
            return null;
        }
        NodeList nl = elem.getElementsByTagName(name);
        Vector<String> values = new Vector(nl.getLength());
        String retval;
        for (int n = 0; n < nl.getLength(); n++) {
            Element element = (Element) nl.item(n);
            retval = getSimpleElementText(element);
            if (retval != null) {
                values.addElement(retval);
            }
        }
        return values;
    }

    /**
     * extractElementList - returns a list of elements with the given tag name
     * @param elem - the top node to search from
     * @param name - the sought tag
     * @return Vector<Element>
     */
    public Vector<Element> extractElementList(Element elem, String name) {
        if (elem == null) {
            return null;
        }
        NodeList nl = elem.getElementsByTagName(name);
        Vector<Element> values = new Vector(nl.getLength());
        for (int n = 0; n < nl.getLength(); n++) {
            if (nl.item(n).getNodeType() == Node.ELEMENT_NODE) {
                values.addElement((Element) nl.item(n));
            }
        }
        return values;
    }

    /**
     * getChildList - returns a list of immediate children elements
     * with the given tag name
     * @param elem - the node to search the children
     * @param name - the sought tag
     * @return Vector<Element>
     */
    public Vector<Element> getChildList(Element elem, String elementName) {
        if (elem == null) {
            return null;
        }
        NodeList nl = elem.getChildNodes();
        if (nl == null) {
            return null;
        }
        Vector<Element> retlist = new Vector(100);
        for (int n = 0; n < nl.getLength(); n++) {
            if (nl.item(n).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nl.item(n);
                if (elementName.equals(element.getTagName())) {
                    retlist.addElement(element);
                }
            }
        }
        if (retlist.size() == 0) {
            return null;
        }
        return retlist;
    }

    /**
     * getFirstNode returns the first element with the given name,
     * starting with the passed node. If necessary, the element tree
     * will be searched beyond the current node.
     * @param elem - the parent node to search
     * @param name - the sought tag
     * @return sought Element
     */
    public Element getFirstNode(Element elem, String name) {
        NodeList nl = elem.getElementsByTagName(name);
        for (int n = 0; n < nl.getLength(); n++) {
            if (nl.item(n).getNodeType() == Node.ELEMENT_NODE) {
                return (Element) nl.item(n);
            }
        }
        return null;
    }

    /**
     * getFirstChildNode is like getFirstNode except the sought 
     * tag must be an immediate child of the current node. 
     * @param elem - the parent node to search
     * @param name - the sought tag
     * @return sought child Element
     */
    public Element getFirstChildNode(Element elem, String name) {
        NodeList nl = elem.getChildNodes();
        for (int n = 0; n < nl.getLength(); n++) {
            if (nl.item(n).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nl.item(n);
                if (element.getTagName().equals(name)) {
                    return element;
                }
            }
        }
        return null;
    }

    public String getDoubleValue(Element elem, String name1, String name2) {
        Element tmpele = getFirstNode(elem, name1);
        if (tmpele == null) {
            return null;
        }
        return getFirstValue(tmpele, name2);
    }

    public String getFirstValue(Element elem, String name) {
        String retval = getSimpleElementText(getFirstNode(elem, name));
        return retval;
    }

    /**
     * getFirstChildValue returns the text from first child of the geven node
     * @param node - parent node
     * @return the combined text from the first child
     */
    public String getFirstChildValue(Element elem, String name) {
        String retval = getSimpleElementText(getFirstChildNode(elem, name));
        return retval;
    }

    /**
     * getSimpleElementText returns all the text from the children of the 
     * given node
     * @param node - parent node
     * @return the combined text from the immediate children
     */
    public String getSimpleElementText(Element node) {
        if (node == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Text) {
                sb.append(child.getNodeValue());
            }
        }
        return sb.toString();
    }

    /**
     * setDOM - parse the XML passed as a string
     * @param response - the XML to parse
     * @return the Document form the parse
     */
    public Element setDOM(String response) {

        try {
            if ((response == null) || (db == null)) {
                return null;
            }
            ByteArrayInputStream iStream =
                    new ByteArrayInputStream(response.getBytes("utf-8"));
            Document doc = doParse(iStream);
            if (doc == null) {
                LOGGER.debug("NULL parse:" + response);
                return null;
            }
            Element top = doc.getDocumentElement();
            return top;
        } catch (Exception ex) {
            LOGGER.error(ex);
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * doParse - parse the XML passed as an InptStream
     * @param response - the XML to parse
     * @return the Document form the parse
     */
    public synchronized Document doParse(InputStream istream) {
        try {
            return db.parse(istream);
        } catch (java.io.IOException ex) {
            LOGGER.debug("DOMParser error:" + ex.toString());
        } catch (Exception ex) {
            LOGGER.error(ex);
            ex.printStackTrace();
        }
        return null;
    }
}
