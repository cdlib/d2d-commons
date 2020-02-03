package org.cdlib.util.marc;

import org.apache.log4j.Logger;
import org.cdlib.util.string.StringDisplay;

/**
 * A class that implements MARC fixed length fields.
 * Fields are identified by their labels, e.g., "008".
 * Inherits tag() from Field.
 *
 * @see org.cdlib.util.marc.Field
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcFixedLengthField.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $
 */
public class MarcFixedLengthField extends Field implements Comparable
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcFixedLengthField.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcFixedLengthField.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.4 $";

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
     * Create an unitialized field
     * @param tag Field identifer, e.g., "008"
     * @param len Field length in bytes
     */
    public MarcFixedLengthField(String tag, int len)
    {
        super(tag, new FixedLengthData(len, null, ' '));
    }

    /**
     * Create an initialized field
     * @param tag Field identifer, e.g., "008"
     * @param len Field length in bytes
     * @param s String copied into field from position 0
     */
    public MarcFixedLengthField(String tag, int len, String s)
    {
        super(tag, new FixedLengthData(len, s, ' '));
    }

    /**
     * Create an initialized field
     * @param tag Field identifer, e.g., "008"
     * @param len Field length in bytes
     * @param s String copied into field from position 0
     * @param c Fill character
     */
    public MarcFixedLengthField(String tag, int len, String s, char c)
    {
        super(tag, new FixedLengthData(len, s, c));
    }

    /**
     * Create an initialized field using the whole length of the field passed
     * @param tag Field identifer, e.g., "008"
     * @param s String copied into field from position 0
     */
    public MarcFixedLengthField(String tag, String s)
    {
        super(tag, new FixedLengthData(s.length(), s, ' '));
    }

    /**
     * Create a labeled, initialized field
     * @return copy of this object
     */
    public Field copy()
    {
        MarcFixedLengthField fixField = new MarcFixedLengthField(tag(), value());
        fixField.id = id;
        return fixField;
    }

    /**
     * hex formatted dump of Marc field
     * @param header - display header
     * @return string containing formatted output
     */
    public String formatHexDump()
    {
        String outstr = StringDisplay.stringPrthex(marcDump(), "Field: " + tag());
        return outstr;
    }

    /**
     * Return field value
     *
     * @see org.cdlib.util.marc.Field#tag
     */
    public final String value()
    {
        return ((FixedLengthData)data).dump();
    }

    /**
     * Return formatted string representing field
     * for display
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append(tag);
        sb.append("\t\t\"");
        sb.append(((FixedLengthData)data).dump());
        sb.append('\"');

        return sb.toString();
     }

    /**
     * Return string representing field for incorporation
     * into a MARC record
     */
    public String marcDump()
    {
        StringBuffer sb = new StringBuffer();

        sb.append(((FixedLengthData)data).dump());
        sb.append(Marc.EOF);

        return sb.toString();
     }


    /**
     * Set field data
     *
     * @param s - the Marc field data string
     *
     * @return Field after setting data
     */
    public Field setData(String s)
    {
        this.data = new FixedLengthData(s.length(), s, ' ');
        return this;
    }

    /**
     * Set field data
     *
     * @param tag - the Marc field tag
     * @param s - the Marc field data string
     *
     * @return Field after setting data
     */
    public Field setField(String tag, String s)
    {
        this.tag = tag;
        //this.data = new String(s);
        this.data = s.intern();
        return this;
    }

    /**
     * Return length of field, including field
     * terminator
     */
    int length()
    {
        return ((FixedLengthData)data).length() + 1;
    }

    /**
     * Test that the target field has the same tag as this field.
     *
     * @param mfl the field to check
     *
     * @return true if the argument field has the same tag as this field
     * @throws NullPointerException if the argument is null
     */
    public boolean comparable(MarcFixedLengthField mfl)
    {
        return this.tag.equals(mfl.tag);
    }

    /**
     * Compare this field's tag to another field's tag.
     *
     * @param mfl the field to compare
     *
     * @return 0 if the argument's tag is the same as this field's tag;
     *         a number less that zero if this field's tag is less than
     *         the argument's tag; a number greater than zero if this field's
     *         tag is greater that then the argument's tag.
     *
     * @throws NullPointerException if the argument is null or has a null tag
     */
    public int compareTag(MarcFixedLengthField mfl)
    {
        return this.tag.compareTo(mfl.tag);
    }

    /**
     * Compare this field's value to another fields's value.
     *
     * @param mfl the field to compare
     *
     * @return 0 if the argument's value is the same as this field's value;
     *         a number less that zero if this field's value is less than
     *         the argument's value; a number greater than zero if this field's
     *         value is greater than the argument's value.
     *
     * @throws NullPointerException if the argument is null, or has a null value
     */
    public int compareValue(MarcFixedLengthField mfl)
    {
        return ((String)(this.data)).compareTo((String)(mfl.data));
    }

    /**
     * Compare this field to another field. First compare the tags,
     * and if they are equal, then compare the values.
     *
     * @param mfl the field to compare
     *
     * @return 0 if the argument's tag and value are the same as this field's;
     *         a number less that zero if either this field's tag is less than
     *         the argument's tag, or this subfield's value is less than the
     *         argument's value; a number greater than zero if either this field's
     *         tag is greater that then the argument's tag, or this field's
     *         value is greater than the argument's value.
     *
     * @throws NullPointerException if the argument is null, has a null tag,
     *         or has a null value
     */
    public int compareTo(MarcFixedLengthField mfl)
    {
        int iRet = 0;

        if ( (iRet = this.compareTag(mfl)) == 0 )
        {
            iRet = this.compareValue(mfl);
        }

        return iRet;
    }

    /**
     * Compare this MarcFixedLengthField with another object.
     * This method is here to satify the <code>java.lang.Comparable</code>
     * Interface. Please see that Interface's documentation for an expalanation
     * of the parameters, results, and exceptions.
     */
    public int compareTo(Object o)
    {
        return compareTo((MarcFixedLengthField) o);
    }

}
