package org.cdlib.util.marc;

import java.util.Vector;

import org.apache.log4j.Logger;

import org.cdlib.util.string.StringDisplay;

/**
 * Class that implements a MARC variable length
 * field. Inherits tag() from Field.
 *
 * @see org.cdlib.util.marc.Field#tag
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcToXML.java,v 1.3 2002/07/17 06:07:25 smcgovrn Exp $
 */
public class MarcToXML
{
    private static Logger log = Logger.getLogger(Field.class);

    //====================================================
    //       VARIABLES
    //====================================================
    private StringBuffer xml = null;
    private int lvlcnt = -1;


    //====================================================
    //       CONSTRUCTORS
    //====================================================

    /**
     * Create an uninitialized field
     */
    public MarcToXML()
    {
        xml = new StringBuffer();
    }

    //====================================================
    //       PUBLIC
    //====================================================


    /**
     * Add leader
     */
    public void addLeaderOld( MarcLeader ml )
    {
        String leader = ml.marcDump();
        openStartTag("marcrecord");
        addAttribute("stat", leader.substring(5,6));
        addAttribute("type", leader.substring(6,7));
        addAttribute("blvl", leader.substring(7,8));
        addAttribute("ctl", leader.substring(8,9));
        addAttribute("code", leader.substring(9,10));
        addAttribute("elvl", leader.substring(17,18));
        addAttribute("cform", leader.substring(18,19));
        addAttribute("lnk", leader.substring(19,20));
        closeStartTag();
    }


    /**
     * end file - begin with xml and file header
     */
    public String endFile()
    {
          return new String(StringDisplay.EOL + "</marcfile>");
    }

    public void endRecord()
    {
          endTag("marcrecord");
    }


    public String getXML()
    {
        return xml.toString();
    }


    /**
     * start file - begin with xml and file header
     */
    public String startFile()
    {

        xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        openStartTag("marcfile");
        closeStartTag();
        return xml.toString();
    }

    /**
     * start file - begin with xml and file header
     */
    public void startRecord()
    {
        xml = new StringBuffer();
        lvlcnt = -1;
        openStartTag("marcrecord");
        closeStartTag();
    }

    /**
     * add field to XML string
     *
     * @param field - generic Marc field
     */
    public void addField(Field field)
    {
        if ( field.tag.compareTo( "010" ) < 0 )
        {
            addField((MarcFixedLengthField)field);
        }
        else
		{
            addField((MarcVblLengthField) field);
        }
    }


    /**
     * add field to XML string
     *
     * @param field - variable length field
     */

    public void addField(MarcVblLengthField field)
    {
        MarcSubfield subfield = null;
        openStartTag("fd");
        String data = field.value();
        addAttribute("name", field.tag());
        addAttribute("ind1", data.substring(0, 1));
        addAttribute("ind2", data.substring(1,2));
        closeStartTag();
        Vector subfields = field.subfields();
		int max = subfields.size();
        for ( int i = 0; i < max; i++ )
		{
            subfield = (MarcSubfield)subfields.elementAt(i);
            addSubfield(subfield);
        }
        endTag("fd");
    }

    /**
     * add field to XML string
     *
     * @param field - fixed length Marc field
     */
    public void addField(MarcFixedLengthField in)
    {
        String tag = in.tag();
        String data = in.value();
        addFixedField(tag, data);
    }


    /**
     * add leader to XML string
     *
     * @param in - leader object
     */
    public void addLeader(MarcLeader in)
    {
        String tag = "leader";
        String data = in.marcDump();
        addFixedField(tag, data);
    }


    /**
     * build field from string
     *
     * @param tag - name= value
     * @param field - string form of fixed Marc field or leader
     */
    public void addFixedField(String tag, String data)
    {
        openStartTag("fd");
        addAttribute("name", tag);
        addAttribute("ind1", null);
        addAttribute("ind2", null);
        closeStartTag();
        openStartTag("sf");
        addAttribute("name", null);
        closeStartTag();
        String replace = data.replace(' ', '|');
        xml.append(replace);
        endTag("sf");
        endTag("fd");
    }

    //====================================================
    //       PRIVATE METHODS
    //====================================================
    /**
     * Add Attribute to XML field
     *
     * @param attribute - xml attribute name
     * @param value - value of attribute
     */
    public void addAttribute(String attribute, String value)
    {
        if (value == null)
		{
			value = "";
		}
        else if (value.equals(" "))
		{
            value = "blank";
        }

        xml.append(" " + attribute + "=" + '"' + value + '"');
    }

    /**
     * add spacing for xml tag indentation
     */
    private StringBuffer addIndent()
    {
        StringBuffer retbuf = new StringBuffer();
        for ( int i = 0; i < lvlcnt; i++ )
		{
            retbuf.append("    ");
        }
        return retbuf;
    }

    /**
     * add subfield value to xml string
     *
     * @param subfield - marc subfield object
     */
    private void addSubfield( MarcSubfield subfield )
    {
        openStartTag("sf");
        addAttribute("name", subfield.tag);
        closeStartTag();
        xml.append(subfield.value());
        endTag("sf");
    }


    /**
     * end character(s) of the start tag
     */
    public void closeStartTag()
    {
          xml.append('>');
    }

    /**
     * add end tag to xml string
     */
    private void endTag(String tag)
    {
          xml.append("</" + tag + '>');
          lvlcnt--;
    }

    private void openStartTag(String tag)
    {
        lvlcnt++;
        xml.append(StringDisplay.EOL + addIndent() + '<' + tag);
    }

}
