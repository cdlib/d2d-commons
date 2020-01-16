package org.cdlib.util.marc;

import org.apache.log4j.Logger;

import org.cdlib.util.HexUtils;
import org.cdlib.util.string.StringDisplay;
import org.cdlib.util.string.StringUtil;


/**
 * Class that implements a variable length subfield
 * of a MARC record. Inherits tag() from Field.
 *
 * @see org.cdlib.util.marc.Field#tag
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcSubfield.java,v 1.7 2002/10/22 21:28:08 smcgovrn Exp $
 */
public class MarcSubfield extends Field implements Comparable
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcSubfield.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcSubfield.java,v 1.7 2002/10/22 21:28:08 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.7 $";

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
	 * Short named alias for the system specific line end. Using this variable
	 * not only saves typing, but it also saves lookups to the system properties
	 * map.
	 */
    public static final String EOL = System.getProperty("line.separator");

    //====================================================
    //       CONSTRUCTORS
    //====================================================

     /**
      * Create an initialized MarcSubfield
      *
      * @param tag the subfield code
      * @param data the subfield contents
      * @param trim indicate if subfield contents should be trimmed
      */
     MarcSubfield(char tag, String data, boolean trim)
     {
         super(tag, ((data == null || ! trim) ? data : StringUtil.trimWhiteSpace(data)));
         if ( log.isDebugEnabled() )
         {
             log.debug("new SF data in = '" + data + "' hex = '" + HexUtils.hexPrint(data) + "'");
             log.debug("new SF data stored = '" + (String)this.data + "' hex = '" + HexUtils.hexPrint((String)this.data) + "'");
         }
     }

     /**
      * Create an initialized MarcSubfield.
      * The content string will be space trimmed.
      *
      * @param tag the subfield code
      * @param data the subfield contents
      */
     MarcSubfield(char tag, String data)
     {
         this(tag, data, true);
     }

     /**
      * Create an uninitialized MarcSubfield
      *
      * @param tag the subfield code
      */
     MarcSubfield(char tag)
     {
         super(tag, null);
     }

     /**
      * Create an initialized MarcSubfield
      *
      * @param tag the subfield code
      * @param data the subfield contents
      * @param trim indicate if subfield contents should be trimmed
      */
     MarcSubfield(String tag, String data, boolean trim)
     {
         super(tag, ((data == null || ! trim) ? data : StringUtil.trimWhiteSpace(data)));
         setTag(tag);
         if ( log.isDebugEnabled() )
         {
             log.debug("new SF data in = '" + data + "' hex = '" + HexUtils.hexPrint(data) + "'");
             log.debug("new SF data stored = '" + (String)this.data + "' hex = '" + HexUtils.hexPrint((String)this.data) + "'");
         }
     }

     /**
      * Create an initialized MarcSubfield
      * The content string will be space trimmed.
      *
      * @param tag the subfield code
      * @param data the subfield contents
      */
     MarcSubfield(String tag, String data)
     {
         this(tag, data, true);
     }

    //====================================================
    //       PUBLIC METHODS
    //====================================================

    /**
     * Create subfield of self - clone
     * @return copy of this object
     */
    public Field copy()
    {
        char id = tag().charAt(0);
        Field subField = new MarcSubfield(id, (String)data, false);
        return subField;
    }


    /**
     * hex formatted dump of Marc field
     * @param header - display header
     * @return string containing formatted output
     */
    public String formatHexDump()
    {
        return StringDisplay.stringPrthex(marcDump(), "Subfield: " + tag());
    }

    /**
     * Return length of subfield, including subfield
     * codes
     */
     int length()
     {
         return ((String)data).length() + 2;
     }

    /**
     * Return string represent subfield for incorporation
     * into a MARC record
     */
    public String marcDump()
    {
        StringBuffer sb = new StringBuffer(((String)data).length() + 10);

        sb.append(Marc.SF);
        sb.append(tag);
        sb.append((String)data);
        if ( log.isDebugEnabled() )
        {
            log.debug(new StringBuffer(sb.length() + 30)
                      .append("SF.marcDump: sf[")
                      .append(sb.length())
                      .append("] = '")
                      .append(sb.toString())
                      .append("'").toString());
        }

        return sb.toString();
    }


    /**
     * Set field data
     *
     * @param tag - the Marc field tag
     *
     * @return Field after setting data
     */
    public Field setField(String tag, String data)
    {
        log.warn("dubious method that hopefully is never invoked");

        setTag(tag.trim());
        String temp = (data == null ? data : data.trim());
        int len = (temp == null ? 0 : temp.length());
        this.data = new FixedLengthData(len, temp, ' ');
        return this;
    }

    /**
     * Set field data
     *
     * @param tag - the Marc field tag
     *
     * @return Field after setting data
     */
    public void setTag(String tag) throws MarcFormatException
    {
        if ( tag.length() != 1 )
        {
            throw new MarcFormatException(this, "Subfield tag size is not equal to 1");
        }

        super.setTag(tag);
    }

     /**
      * Return formatted string representing subfield
      * for display
      */
     public String toString()
     {
         StringBuffer sb = new StringBuffer(((String)data).length() + 10);

         sb.append(EOL);
         sb.append("   -$" );
         sb.append(tag);
         sb.append(' ');
         sb.append((String)data);

         return sb.toString();
     }

    /**
     * Return string representation of the content
     * of this subfield
     *
     * @see org.cdlib.util.marc.Field#tag
     */
    public final String value()
    {
        if ( log.isDebugEnabled() )
        {
            log.debug(new StringBuffer(((String)data).length() + 30)
                      .append("SF.value: v = '")
                      .append(data)
                      .append("'").toString());
        }
        return (String)data;
    }

    /**
     * Apply character translations to this subfield's value using the
     * given TranslateTable object.
     *
     * @param tt the TranslateTable object to use
     * @return the translated value
     */
    public String translateValue(TranslateTable tt)
    {
        data = tt.applyAll((String) data);
        return (String)data;
    }


    /**
     * Test that the target subfield has the same subfield code as this subfield.
     *
     * @param ms the subfield to check
     *
     * @return true if the argument subfield has the same subfield code as this subfield
     * @throws NullPointerException if the argument is null
     */
    public boolean comparable(MarcSubfield ms)
    {
        return this.tag.equals(ms.tag);
    }

    /**
     * Compare this subfield's subfield code to another subfield's subfield code.
     *
     * @param ms the subfield to compare
     *
     * @return 0 if the argument's subfield code is the same as this subfield's
     *         subfield code; a number less that zero if this subfield's subfield
     *         code is less than the argument's subfield code; a number greater than
     *         zero if this subfield's subfield code is greater that then the
     *         argument's subfield code.
     *
     * @throws NullPointerException if the argument is null, has a null tag,
     *         or has a null value, or this subfield has a null tag or value
     */
    public int compareTag(MarcSubfield ms)
    {
        return this.tag.compareTo(ms.tag);
    }

    /**
     * Compare this subfield' value to another subfield's value.
     *
     * @param ms the subfield to compare
     *
     * @return 0 if the argument's value is the same as this subfield's value;
     *         a number less that zero if this subfield's value is less than
     *         the argument's value; a number greater than zero if this subfield's
     *         value is greater than the argument's value.
     *
     * @throws NullPointerException if the argument is null, has a null tag,
     *         or has a null value, or this subfield has a null tag or value
     */
    public int compareValue(MarcSubfield ms)
    {
        return ((String)(this.data)).compareTo((String)(ms.data));
    }

    /**
     * Compare this subfield to another subfield. First compare the tags,
     * and if they are equal, then compare the values.
     *
     * @param ms the subfield to compare
     *
     * @return 0 if the argument's subfield code and value are the same as this
     *         subfield's; a number less that zero if either this subfield's subfield
     *         code is less than the argument's subfield code, or this subfield's
     *         value is less than the argument's value; a number greater than zero
     *         if either this subfield's subfield code is greater that then the
     *         argument's subfield code, or this subfield's value is greater than
     *         the argument's value.
     * @throws NullPointerException if the argument is null, has a null tag,
     *         or has a null value, or this subfield has a null tag or value
     */
    public int compareTo(MarcSubfield ms)
    {
        int iRet = 0;

        if ( (iRet = this.compareTag(ms)) == 0 )
        {
            iRet = this.compareValue(ms);
        }

        return iRet;
    }

    /**
     * Compare this MarcSubfield with another object.
     * This method is here to satify the <code>java.lang.Comparable</code>
     * Interface. Please see that Interface's documentation for an expalanation
     * of the parameters, results, and exceptions.
     */
    public int compareTo(Object o)
    {
        return compareTo((MarcSubfield) o);
    }


    //====================================================
    // Static methods
    //====================================================

    /*
     * Returns true iff specified string is not null and not empty.
     */
    public static boolean exists(String sfval)
    {
        return ( sfval != null && sfval.trim().length() > 0 );
    }


    /*
     * Returns true iff the specified subfield is not null and
     * the value of the specified subfield is not null and not empty.
     */
    public static boolean exists(MarcSubfield sf)
    {
        return ( sf != null && exists(sf.value()) );
    }

}
