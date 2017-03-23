package org.cdlib.util;

import java.util.Hashtable;
import java.util.Vector;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * XMLUtil manages a number of static utility methods that assist in the parsing of
 * a DOM object. 
 * The class itself also allows the sharing of a single XML DocumentBuilder.
 * 
 * @author mjt (after Loy's code)
 */
 public class XMLUtil {
	 

    private DocumentBuilder db = null;

    public XMLUtil()
    {
        super();
        init();
    }

 
    private void init() 
    {
        try {
            DocumentBuilderFactory dbf = 
                    DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // if the Document builder can't be constructed
            // something is seriously wrong, so throw a runtime exception
            // this is fatal
            throw new RuntimeException(e);
        }
    }

    public static Vector<String> extractList(Element elem, String name )  
    {
        NodeList nl = elem.getElementsByTagName(name);
        Vector<String> values = new Vector<String> (nl.getLength());
        String retval = null;
        for (int n=0; n<nl.getLength(); n++) {
            Element element = (Element)nl.item(n);
            retval = getSimpleElementText(element);
            if (retval != null) 
            	values.addElement(retval);;
        }
        return values;
    }   

    public static Vector<Element> extractElementList(Element elem, String name )  
    {
        NodeList nl = elem.getElementsByTagName(name);
        Vector<Element> values = new Vector<Element> (nl.getLength());
        for (int n=0; n<nl.getLength(); n++) {
            Element element = (Element)nl.item(n);
            if (element != null) 
            	values.addElement(element);;
        }
        return values;
    }   

    public static String getNodeString(Element elem, String name, int inx)  
    {
        NodeList nl = elem.getElementsByTagName(name);
        if (inx >= nl.getLength()) 
        	return null;
        Element element = (Element)nl.item(inx);
        return getSimpleElementText(element);
    }   

    public static Vector<Element> getChildList(Element elem, String elementName)  
    {
        if (elem == null) return null;
        NodeList nl = elem.getChildNodes();
        if (nl == null) return null;
        Vector<Element> retlist = new Vector<Element> (100);
        for (int n=0; n<nl.getLength(); n++) {
            Element element = (Element)nl.item(n);
            if (elementName.equals(element.getTagName())) {
                retlist.addElement(element);
            }
        }
        if (retlist.size() == 0) 
        	return null;
        return retlist;
    }

    public static Hashtable<String, Element> getChildHash(Element elem, String elementName, String attrName)  
    {
        if (elem == null) 
        	return null;
        NodeList nl = elem.getChildNodes();
        if (nl == null) 
        	return null;
        Hashtable<String, Element> retlist = new Hashtable<String, Element> (100);
        for (int n=0; n<nl.getLength(); n++) {
            Node child = nl.item(n);
            if ( child instanceof Element ) {
                Element element = (Element)child;
                if (!elementName.equals(element.getTagName())) 
                	continue;
                String keyValue = element.getAttribute(attrName);
                if (keyValue == null) 
                	continue;
                retlist.put(keyValue,element);
            }
        }
        if (retlist.size() == 0) 
        	return null;
        return retlist;
    }

    public static Hashtable<String, String> getChildHash(Element elem)  
    {
        if (elem == null) 
        	return null;
        NodeList nl = elem.getChildNodes();
        if (nl == null) 
        	return null;
        Hashtable<String, String> retlist = new Hashtable<String, String> (nl.getLength());
        for (int n=0; n<nl.getLength(); n++) {
            Node child = nl.item(n);
            if ( child instanceof Element ) {
                Element element = (Element)child;
                String key = element.getTagName();
                String value = getSimpleElementText(element);
                retlist.put(key, value);
            }
        }
        if (retlist.size() == 0)  
        	return null;
        return retlist;
    }

    public static Element getFirstNode(Element elem, String name )  
    {
        NodeList nl = elem.getElementsByTagName(name);
        if (nl.getLength() > 0)
        	return (Element)nl.item(0);
        return null;
    }   

    public static String getDoubleValue(Element elem, String name1, String name2)
    {
        Element tmpele = getFirstNode(elem, name1);
        if (tmpele == null) return null;
        return getFirstValue(tmpele, name2);
    }

    public static String getFirstValue(Element elem, String name )  
    {
        String retval = getSimpleElementText(getFirstNode(elem, name));
        return retval;
    }   

    public static String getSimpleElementText( Element node )  
    {
        if (node == null) 
        	return null;
        StringBuffer sb = new StringBuffer();
        NodeList children = node.getChildNodes();
        for(int i=0; i<children.getLength(); i++) {
            Node child = children.item(i);
            if ( child instanceof Text ) {
               sb.append( child.getNodeValue() );
            }
        }
        return sb.toString();
    }

    public Element setDOM(String response)
    {

        try {
            if ((response == null) || (db == null)) 
            	return null;
            ByteArrayInputStream iStream 
                = new ByteArrayInputStream(response.getBytes("utf-8"));
            Document doc = doParse(iStream);
            if (doc == null) {
                return null;
            }
            Element top = doc.getDocumentElement();
            return top;
        }
        catch (Exception ex) {
        }
        return null;
    }

    public synchronized Document doParse(InputStream istream)
    {
        try {
            return db.parse(istream);
        }
        catch (Exception ex) {
            
        }
        return null;
    }

}
