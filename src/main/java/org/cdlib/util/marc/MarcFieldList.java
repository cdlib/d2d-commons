package org.cdlib.util.marc;

import org.apache.log4j.Logger;
import org.cdlib.util.string.StringDisplay;
import org.cdlib.util.string.StringUtil;

/**
 * Class representing a collection of Marc fields.
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcFieldList.java,v 1.5 2003/01/03 18:06:32 smcgovrn Exp $
 */
public class MarcFieldList extends FieldList
{
 	/**
	 * log4j Logger for this class.
	 */
	private static Logger log = Logger.getLogger(MarcFieldList.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcFieldList.java,v 1.5 2003/01/03 18:06:32 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.5 $";

	/**
	 * Private indicator used to assure CVS information is logged
	 * only once.
	 */
    private static boolean versionLogged = false;

    /*
     * Static initializer used to log cvs header info.
     */
    {
        if ( !versionLogged )
        {
            log.info(cvsHeader);
            versionLogged = true;
        }
    }

	/**
	 * Enumeration value used to tell the dumpFields method to ascii
	 * format the fields.
	 */
	public static final int STANDARD = 1;

	/**
	 * Enumeration value used to tell the dumpFields method to hex
	 * format the fields.
	 */
	public static final int HEX = 2;


    /**
     * Builds new <code>Field</code> and adds it to this list.
     *
     * @param tag       tag of field to be added
     * @param s         field data with indicators
     * @param delimiter string value that is translated to subfield delimiter
     *
	 * @return the new <code>Field</code>
     */
    public Field addField(String tag, String s, String delimiter)
    {
        Field f = setField(String2Field(tag, s, delimiter));
        return f;
    }


    /**
     * Return a copy of this list that contains copies of all the elements
	 * in the list.
	 *
     * @return copy of this MarcFieldList
     */
    public MarcFieldList copy()
    {
        MarcFieldList returnList = new MarcFieldList();

        for (int i = 0; i < size(); i++)
		{
            Field field = (Field)elementAt(i);
            returnList.setCopyField(field);
        }

        return returnList;
    }


    /**
     * Do a formatted dump of fields.
     * @param header - display header for string output
     * @param type - enum value for display type:
     *  STANDARD - line format output
     *  HEX - hex format output
     */
    public String dumpFields(String header, int type)
    {
        Field field = null;
        StringBuffer dumpStr = new StringBuffer();
        dumpStr.append(StringDisplay.EOL + "Dump Fields: " + header + StringDisplay.EOL);
        for (int i=0; i<size(); i++) {
			field = (Field)elementAt(i);
			if (type == STANDARD) {
				dumpStr.append(field.toString() + StringDisplay.EOL);
			}
			else if (type == HEX) {
				dumpStr.append(
							   StringDisplay.stringPrthex(field.marcDump(), "Field:" + field.tag())
							   + StringDisplay.EOL);
			}
        }
        return dumpStr.toString();
    }

    /**
     * Do a formatted dump of fields.
     * @param header display header for string output
     */
    public String formatDump(String header)
    {
        StringBuffer dumpStr = new StringBuffer();
        dumpStr.append(StringDisplay.EOL + "Dump Fields: " + header + toString());
        return dumpStr.toString();
    }

    /**
     * Do a formatted hex dump of fields.
     * @param header display header for string output
     */
    public String formatHexDump(String header)
    {
        Field field = null;
        StringBuffer dumpStr = new StringBuffer();
        dumpStr.append(StringDisplay.EOL + "Dump Fields: " + header + StringDisplay.EOL);
        for (int i=0; i<size(); i++) {
			field = (Field)elementAt(i);
			dumpStr.append(
						   StringDisplay.stringPrthex(field.marcDump(), "Field:" + field.tag())
						   + StringDisplay.EOL);
        }
        return dumpStr.toString();
    }

    /**
     * Increment through list. All fields that are > than previous
     * tag are part of current increment within the current nxx.
     * Example:
     * 0 -> 100, 101, 102
     * 1 -> 200
     * 2 -> 200, 201
     * 3 -> 200
     * 4 -> 200, 201, 202
     * 5 -> 201, 202
     * 6 -> 300, 301, 302
     *
     * @param requestInc index of increment list to be extracted
     */
	public MarcFieldList getIncrement(int requestInc)
	{
		MarcFieldList newList = new MarcFieldList();

		if (size() == 0) return null;

		Field currentEle = elementAt(0);
		Field nextEle = null;
		boolean isSame = true;
		int currentInc = 0;
		newList.addElement(currentEle);

		for (int i=1; i < size(); i++) {
			nextEle = elementAt(i);
			isSame = isSameIncrement(currentEle, nextEle);
			if (isSame) {
				newList.addElement(nextEle);
			}
			else {
				if (currentInc == requestInc)
					return newList;
				currentInc++;
				newList.clear();
				newList.addElement(nextEle);
			}
			currentEle = nextEle;
		}
		if (currentInc == requestInc) return newList;
		else return null;
    }

    /**
     * Increment through list. Use input tag as start tag
     *
     * @param requestInc - index of increment list to be extracted
     */
	public MarcFieldList getIncrement(String startTag, int requestInc)
	{
        MarcFieldList newList = new MarcFieldList();

        if (size() == 0) return null;

        Field field = null;
        boolean match = false;
        String fieldTag = null;
        int i1 = 0;
        int tagInc = 0;
        for (i1 = 0; i1 < size(); i1++) {
            field = elementAt(i1);
            fieldTag = field.tag();
            if (breakLevel(startTag, fieldTag)) break;
            if (fieldTag.equals(startTag)) {
                if (requestInc == tagInc) match = true;
                tagInc++;
            }
            if (match) break;
        }

        if (!match) return null;

        newList.addElement(field);
        int i2 = 0;
        for (i2 = i1 + 1; i2 < size(); i2++)
		{
            field = elementAt(i2);
            fieldTag = field.tag();
            if (breakLevel(startTag, fieldTag)) break;
            if (fieldTag.equals(startTag)) break;
            newList.addElement(field);
        }

        if (newList.size() > 0) return newList;
        else return null;
    }

    /**
     * Return an ascii formatted dump of fields in the list.
	 *
	 * @return the ascii formatted String
     */
    public String toString()
    {
        Field field = null;
        StringBuffer dumpStr = new StringBuffer(500);
        dumpStr.append(StringDisplay.EOL);
		int max = this.size();
        for (int i = 0; i < max; i++)
		{
			field = (Field)elementAt(i);
			dumpStr.append(field.toString() + StringDisplay.EOL);
        }
        return dumpStr.toString();
    }


    private boolean isSameIncrement(Field fld1, Field fld2)
    {
        String tag1 = fld1.tag();
        String tag2 = fld2.tag();
        char tagLvl1 = tag1.charAt(0);
        char tagLvl2 = tag2.charAt(0);
        if (tagLvl1 != tagLvl2) return false;
        if (tag1.compareTo(tag2) >= 0) return false;
        return true;
    }

    private boolean breakLevel(String tag1, String tag2)
    {
        char tagLvl1 = tag1.charAt(0);
        char tagLvl2 = tag2.charAt(0);
        return (tagLvl1 != tagLvl2);
    }

    /**
     * Parses the supplied String into a Field object.
     *
     * @param tag       tag of field to be added
     * @param data      field data with indicators
     * @param delimiter subfield delimiter to replace with actual subfield delimiter.
     *                  If zero length or null then no replacement occurs.
     */
    private Field String2Field(String tag, String data, String delim)
    {
        Field f = null;
        if (tag.compareTo( "010" ) < 0)
        {
            f = new MarcFixedLengthField(tag, data.length(), data);
        }
        else
        {
            if (StringUtil.isEmpty(delim))
            {
                f = new MarcVblLengthField(tag, data);
            }
            else
            {
                f = new MarcVblLengthField(tag, data, delim);
            }
        }

        return f;
    }

}
