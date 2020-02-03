package org.cdlib.util.marc;

import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import org.cdlib.util.HexUtils;
import org.cdlib.util.string.StringDisplay;
import org.cdlib.util.string.StringUtil;

/**
 * Class that implements a MARC variable length field.
 *
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcVblLengthField.java,v 1.14 2008/01/28 23:04:00 rkl Exp $
 */
public class MarcVblLengthField extends Field
{
 	/**
	 * log4j Logger for this class.
	 */
   private static Logger log = Logger.getLogger(MarcVblLengthField.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcVblLengthField.java,v 1.14 2008/01/28 23:04:00 rkl Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.14 $";

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
	 * Two byte indicator field.
	 */
    private FixedLengthData indicators;


    /**
     * Create an uninitialized field
     */
    public MarcVblLengthField()
    {
        super("", new Vector(15, 15));
        indicators = new FixedLengthData(2);
    }

    /**
     * Create a labeled, but otherwise uninitialized
     * field
     * @param tag Field tag, e.g., "245"
     */
    public MarcVblLengthField(String tag)
    {
        super(tag, new Vector(15, 15));
        indicators = new FixedLengthData(2);
    }

    /**
     * Create a labeled, initialized field
     * @param tag Field tag, e.g., "245"
     * @param indicators Field indicators
     * @param subfields List of subfields
     */
    public MarcVblLengthField(String tag,
                              FixedLengthData indicators,
                              Vector subfields)
    {
        super(tag, (subfields == null ? new Vector(15, 15) : subfields));
        this.indicators = indicators;
    }

    /**
     * Create a labeled, initialized field
     * @param tag Field tag, e.g., "245"
     * @param indicators Field indicators
     * @param subfields List of subfields
     */
    public MarcVblLengthField(String tag,
                              String indicators,
                              Vector subfields)
    {
        super(tag, (subfields == null ? new Vector(15, 15) : subfields));
        this.indicators = new FixedLengthData(2);
        try {
            setIndicators(indicators);
        }
        catch (MarcFormatException e) {
            log.warn(e.toString() + " tag=" + tag + " indicators=" + indicators);
        }
    }

    /**
     * Create a Variable MARC field
     * @param tag Field tag, e.g., "245"
     * @param data - field data
     * @exception MarcFormatException - invalid Marc Format
     */
    public MarcVblLengthField(String tag, String data)
        throws MarcFormatException
    {
        this(tag);
        setField(tag, data);
    }

    /**
     * Create a Variable MARC field
     * @param tag Field tag, e.g., "245"
     * @param data - field data
     * @param delim - delimiter string to represent Marc substring delimiter
     * @exception MarcFormatException - invalid Marc Format
     */
    public MarcVblLengthField(String tag, String data, String delim)
        throws MarcFormatException
    {
        this(tag);
        setField(tag, data, delim);
    }

    //====================================================
    //       PUBLIC
    //====================================================
    /**
     * Add a subfield
     */
    public final void addSubfield(MarcSubfield sf)
    {
        ((Vector)data).addElement(sf);
    }

    /**
     * Add a subfield
     */
    public MarcSubfield addSubfield(String tag, String indata)
    {
        if ((indata == null) || (tag == null)) return null;
        MarcSubfield sf = new MarcSubfield(tag, indata, (getTagint() > 99));
        ((Vector)data).addElement(sf);
        return sf;
    }

    /**
     * Add colection of marc subfields to this field's subfields.
     * Takes an array of <code>MarcSubfield</code> and adds
     * each non-null, none empty, subfield to the data Vector.
     *
     * @param subfields an array of subfields
     * @return the number of subfields added to this field's data
     */
    public int addSubfields(MarcSubfield[] subfields)
    {
        int cnt = 0;
        if ( subfields != null )
        {
            int max = subfields.length;
            for ( int i = 0; i < max; i++ )
            {
                if ( MarcSubfield.exists(subfields[i]) )
                {
                    if ( ((Vector)data).add(subfields[i]) )
                    {
                        cnt++;
                    }
                }
            }
        }

        return cnt;
    }


    /**
     * adds multiple strings from vector as a set of subfi
     * and the first subfield with this subfield tag
     * @param values - Vector of string values to add
     * @param subtag - subfield tag
     * @return string containing value of this tag/subfield
     */
    public void addSubfields(Vector values, String subtag)
    {
        if ((values == null)
            || (subtag == null)
            || (values.size() == 0)) return;
        String value = null;
        for (int i = 0; i < values.size(); i++)
        {
            value = (String)values.elementAt(i);
            addSubfield(subtag, value);
        }
    }

    /**
     * adds multiple strings from vector as a set of subfi
     * and the first subfield with this subfield tag
     * @param values - Vector of string values to add
     * @param subtag - subfield tag
     * @return string containing value of this tag/subfield
     */
    public String appendSubfields(
            Vector values,
            String delimiter)
    {
        if ((values == null)
            || (delimiter == null)
            || (values.size() == 0)) return null;
        String value = null;
        StringBuffer out = new StringBuffer("");
        for (int i=0; i<values.size(); i++) {
            value = (String)values.elementAt(i);
            if (out.length() > 0) out.append(delimiter);
            out.append(value);
        }
        return out.toString();
    }

    /**
     * adds multiple strings from vector as a set of subfi
     * and the first subfield with this subfield tag
     * @param values - Vector of string values to add
     * @param subtag - subfield tag
     * @return string containing value of this tag/subfield
     */
    public String appendSubfields(
            boolean include,
            String subtags,
            String delimiter)
    {
        Vector subs = subfields(subtags, include);
        MarcSubfield subfield = null;
        boolean match = false;
        int pos = 0;
        StringBuffer out = new StringBuffer("");
        for (int i = 0; i < subs.size(); i++)
        {
            subfield = (MarcSubfield)subs.elementAt(i);
            if (out.length() > 0) out.append(delimiter);
            out.append(subfield.value());
        }

        return out.toString();
    }

    /**
     * Return all but the subfields specified.
     * @return A vector containing the MarcSubfields of
     * this field or null if it has none (should never
     * happen).
     */
    public Vector allButSubfields(String codes)
    {
        if (codes == null) throw new IllegalArgumentException();

        Vector subflds = subfields();
        Vector result = new Vector (10, 10);
        for (int i = 0; i < subflds.size(); i++)
        {
            MarcSubfield sf = (MarcSubfield) subflds.elementAt(i);
            if (codes.indexOf(sf.tag()) == -1)
                result.addElement(sf);
        }
        return result;
    }

    /**
     * remove all subfields
     */
    public void clearSubfields()
    {
        ((Vector)data).removeAllElements();
    }

    /**
     * Create a labeled, initialized field
     * @return copy of this object
     */
    public Field copy()
    {
        MarcVblLengthField mvlField = new MarcVblLengthField(tag(), value().toString());
        mvlField.id = id;
        return mvlField;
    }

    /**
     * Build a new field deleting the subfields in codes
     *
     * @param codes - a string containing all of the subfield tags
     * to be deleted. Note each character is a subfield.
     *
     * @return A new field containing subfields in codes. null
     * returned if no remaining subfields
     */
    public MarcVblLengthField deleteSubfields(String codes)
    {
        Vector subFields = subfields(codes, false);
        if (subFields.size() == 0) return null;
        MarcVblLengthField field = new MarcVblLengthField(tag(), indicators(), subFields);
        return field;
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
     * Return the indicators as a string
     */
    public final String indicators()
    {
      return indicators.dump();
    }

    /**
     * Build a new field containing the subfields in codes
     *
     * @param codes - a string containing all of the subfield tags
     * to be extracted. Note each character is a subfield.
     *
     * @return A new field containing subfields in codes. null
     * returned if no remaining subfields
     */
    public MarcVblLengthField keepSubfields(String codes)
    {
        Vector subFields = subfields(codes, true);
        if (subFields.size() == 0) return null;
        MarcVblLengthField field = new MarcVblLengthField(tag(), indicators(), subFields);
        return field;
    }


    /**
     * Return length of this field, including subfields.
     */
    int length()
    {
        int len = 2;    // Indicators

        Enumeration e = ((Vector)data).elements();

        while( e.hasMoreElements() )
        {
            len += ((MarcSubfield)(e.nextElement())).length();
        }
        return len + 1; // End of field marker
    }

    /**
     * Return string representing field for incorporation
     * into a MARC record
     */
    public String marcDump()
    {
        StringBuffer sb = new StringBuffer(value());
        sb.append( Marc.EOF );
        return sb.toString();
    }

    /**
     * Set field data
     *
     * @param tag - the Marc field tag
     * @param data - Marc data - including subfields
     *
     * @return Field after setting data
     * found
     *
     * @exception MarcFormatException
     */
    public Field setField(String tag, String data)
        throws MarcFormatException
    {
        clearSubfields();
        this.tag = tag;
        setTagint();

        StringTokenizer t = new StringTokenizer(data, Marc.SFStr);

		int tokenCount = t.countTokens();


		if ( log.isDebugEnabled() )
		{
			log.debug("Field (" + tag + ") token count = " + tokenCount);
		}

		if ( tokenCount < 2 )
		{
			throw new MarcFormatException(this, "No subfields found");
		}

        try {
            setIndicators( t.nextToken() );
        }
        catch (MarcFormatException e) {
            log.warn(e.toString() + " tag=" + tag + " tokenCount=" + tokenCount + " data=" + data);
        }

        while( t.hasMoreTokens() )
        {
            String s = t.nextToken();
            if ( log.isDebugEnabled() )
            {
                log.debug("Raw SF = '" + s + "' hex SF = '" + HexUtils.hexPrint(s) + "'");
            }

            addSubfield( new MarcSubfield(s.charAt(0),                // subfield code
                                          s.substring(1, s.length()), // subfield contents
                                          (getTagint() > 99)          // trim contents?
                                          ));
        }
        return this;
    }

    /**
     * Set field data
     *
     * @param tag - the Marc field tag
     * @param delim - delimiter string to represent Marc substring delimiter
     * @param data - Marc data - including subfields
     *
     * @return Field after setting data
     * found
     *
     * @exception MarcFormatException
     */
    public Field setField(String tag, String data, String delim)
        throws MarcFormatException
    {
        String replaceField = StringUtil.replace(data, delim, Marc.SFStr);
        return setField(tag, replaceField);
    }

    /**
     * Set nth indicator to c
     * @param n Indicator number (1 or 2)
     * @param c Character value of indicator
     */
    final void setIndicator(int n, char c)
    {
        indicators.setPos(n - 1, c);
    }

    /**
     * Set nth indicator to value represented by s
     * @param n Indicator number (1 or 2)
     * @param s String value of indicator
     */
    final void setIndicator(int n, String s)
    {
        char c = s.charAt(0);
        setIndicator(n, c);
    }

    /**
     * Set indicators to values represented by s
     * @param s String value of indicators
     */
    public final void setIndicators(String s)
        throws MarcFormatException
    {
        if (s.length() != 2)
            throw new MarcFormatException(this, "Indicator size invalid");
        indicators.setPos(0, s);
    }

    /**
     * Return this field's indicators.
     *
     * @return this field's indicators
     */
    public final FixedLengthData getIndicators()
    {
        return this.indicators;
    }

    /**
     * Set tag
     * @param inTag value of tag
     */
    public void setTag(String inTag) throws MarcFormatException
    {
        if ( inTag.length() != 3 )
        {
            throw new MarcFormatException(this, "Tag size invalid");
        }

        super.setTag(inTag);
    }


    /**
     * Return the first instance of the specified subfield.
     *
     * @param sfcode the subfield code to select
     *
     * @return The matching <code>MarcSubfield</code>, or null if none was found
     *
     * @exception IllegalArgumentException if the subfield code is either null
     *                                     or not of length one
     */
    public MarcSubfield firstSubfield(String sfcode)
    {
        if ( sfcode == null || sfcode.length() != 1 )
        {
            throw new IllegalArgumentException();
        }

        MarcSubfield sRet = null;
        Vector subflds = subfields();
        int max = subflds.size();
        for (int i = 0; i < max; i++)
        {
            MarcSubfield sf = (MarcSubfield)subflds.elementAt(i);
            if (sf.tag().equals(sfcode))
            {
                sRet = sf;
                break;
            }
        }

      return sRet;
    }


    /**
     * Return the value of first instance of the specified subfield.
     *
     * @param sfcode the subfield code to select
     *
     * @return the value of the selected subfield, or null if none was found
     *
     * @exception IllegalArgumentException if the subfield code is null or
     *                                     not of length one
     */
    public String firstSubfieldValue(String sfcode)
    {
        // Validate input
        if ( sfcode == null || sfcode.length() != 1 )
        {
            throw new IllegalArgumentException();
        }

        // Return first match
        MarcSubfield sf = firstSubfield(sfcode);
        String value = null;
        if (sf != null)
        {
            value = sf.value();
        }

        return value;
    }

    /**
     * Return a <code>Vector</code> of all subfields matching the specified tag.
     *
     * @param tag A single character string representing the subfield tag
     * @return The <code>Vector</code> of subfields. The vector will be empty
     * if no such subfields are found.
     * @exception IllegalArgumentException The input was either null
     * or not a single character string
     */
    public Vector subfield(String tag)
    {
        // Validate input
        if ( tag == null || tag.length() != 1 )
        {
            throw new IllegalArgumentException();
        }

        // Find the matching subfields
        Vector subflds = subfields();
        Vector vRet = new Vector(30, 30);
        int max = subflds.size();

        for ( int i = 0; i < max; i++)
        {
            MarcSubfield sf = (MarcSubfield)subflds.elementAt(i);
            if (sf.tag().equals(tag))
            {
                vRet.addElement(sf);
            }
        }

        return vRet;
    }

    /**
     * Return all the subfields of this field
     *
     * @return A vector containing the MarcSubfields of
     * this field or and empty Vector if it has none (should never
     * happen - yeah right! Happened a weird marc record. 2/25/00 mjt)
     */
    public Vector subfields()
    {
        return ((((Vector)data).size() > 0) ? (Vector)data : new Vector (0));
    }

    /**
     * Return all the subfields of this field in a MarcSubfield array.
     *
     * @return a MarcSubfield array containing this fields subfields.
     */
    public MarcSubfield[] getSubfields()
    {
        MarcSubfield[] sf = new MarcSubfield[0];
        if (data != null )
        {
            sf = (MarcSubfield[])((Vector)data).toArray(sf);
        }

        return sf;
    }


    /**
     * Apply character translations to this field's subfield's values using the
     * given TranslateTable object.
     *
     * @param tt the TranslateTable object to use
     */
    public void translateValues(TranslateTable tt)
    {
        Enumeration enu = ((Vector)data).elements();
        while ( enu.hasMoreElements() )
        {
            ((MarcSubfield)enu.nextElement()).translateValue(tt);
        }
    }


    /**
     * Return the subfields specified. Depending on the second parm,
     * either only include the specified subfields or exclude those
     * subfields and include the rest.
     *
     * @param codes - a string containing all of the subfield tags
     *  to be extracted. Note each character is a subfield.
     *
     * @param include - flag indicating include=true, exclude=false
     *  for the codes values
     *
     * @return A vector containing the selected <code>MarcSubfield</code> objects,
     *         or null, if none were selected
     */
    public Vector subfields(String codes, boolean include)
    {
        if (codes == null)
        {
            throw new IllegalArgumentException();
        }

        if (! include)
        {
            return allButSubfields(codes);
        }

        Vector subflds = subfields();
        Vector result = new Vector(30, 30);

        int max = subflds.size();
        for ( int i = 0; i < max; i++ )
        {
            MarcSubfield subfld = (MarcSubfield)subflds.elementAt(i);
            if ( codes.indexOf(subfld.tag()) >= 0 )
            {
                result.addElement(subfld);
            }
        }
        return result;
    }

    /**
     * Return formatted string representing field
     * for display
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(500);

        sb.append(tag);
        sb.append('\t');
        sb.append(indicators());
        sb.append('\t');
        sb.append('[');

        Enumeration e = ((Vector)data).elements();

        while( e.hasMoreElements() )
        {
            sb.append(((MarcSubfield)(e.nextElement())).toString());
            sb.append(' ');
        }
        sb.append (']');
        return sb.toString();
    }

    /**
     * Return String for field
     */
    public String value()
    {
        String subfield = null;
        StringBuffer sb = new StringBuffer(500);
        sb.append(indicators.dump());

        Enumeration e = ((Vector)data).elements();

        while( e.hasMoreElements() )
        {
            subfield = ((MarcSubfield)(e.nextElement())).marcDump();
            if ((sb.length() + subfield.length()) > 9999) continue;
            sb.append(subfield);
        }
        return sb.toString();
    }


    /**
     * Return a <code>TreeMap</code> of all subfields with occurence counts.
     * The type structure is String x Integer.
     * @return The <code>TreeMap</code> of subfields and counts.
     * The <code>TreeMap</code> will be empty if no subfields are found.
     *
     */
    public TreeMap getSubFieldCountMap()
    {
        TreeMap sfCountMap = new TreeMap();
        int max = ((Vector)data).size();

        for ( int i = 0; i < max; i++ )
        {
            String sfCode = ((Field)((Vector)data).elementAt(i)).getTag();
			Integer sfcCount = (Integer)sfCountMap.get(sfCode);
			int newCount = (sfcCount == null ? 1 : sfcCount.intValue() + 1);
			sfCountMap.put(sfCode, new Integer(newCount));
        }

        return sfCountMap;
    }


    /**
     * Test that the target field has the same tag as this field.
     *
     * @param mvl the field to check
     *
     * @return true if the argument field has the same tag as this field
     * @throws NullPointerException if the argument is null
     */
    public boolean comparable(MarcVblLengthField mvl)
    {
        return this.tag.equals(mvl.tag);
    }

    /**
     * Compare this field's tag to another field's tag.
     *
     * @param mvl the field to compare
     *
     * @return 0 if the argument's tag is the same as this field's tag;
     *         a number less that zero if this field's tag is less than
     *         the argument's tag; a number greater than zero if this field's
     *         tag is greater that then the argument's tag.
     *
     * @throws NullPointerException if the argument is null or has a null tag
     */
    public int compareTag(MarcVblLengthField mvl)
    {
        return this.tag.compareTo(mvl.tag);
    }

    /**
     * Compare this field's value to another fields's value.
     *
     * @param mvl the field to compare
     *
     * @return 0 if the argument's value is the same as this field's indicators;
     *         a number less that zero if this field's indiactors are less than
     *         the argument's indicators; a number greater than zero if this field's
     *         value is greater than the argument's value.
     *
     * @throws NullPointerException if the argument is null, or has a null value
     */
    public int compareIndicators(MarcVblLengthField mvl)
    {
        return this.indicators.compareTo(mvl.getIndicators());
    }

    /**
     * Compare this field's value to another fields's value.
     *
     * @param mvl the field to compare
     *
     * @return 0 if the argument's value is the same as this field's value;
     *         a number less that zero if this field's value is less than
     *         the argument's value; a number greater than zero if this field's
     *         value is greater than the argument's value.
     *
     * @throws NullPointerException if the argument is null, or has a null value
     */
    public int compareValue(MarcVblLengthField mvl)
    {
        int            iRet     = 0;
        MarcSubfield[] sfThis   = this.getSubfields();
        MarcSubfield[] sfTarget = mvl.getSubfields();
        int            max      = Math.max(sfThis.length, sfTarget.length);

        for ( int i = 0, j = 0; i < max && j < max; i++, j++)
        {
            if ( (iRet = sfThis[i].compareTo(sfTarget[j])) != 0 )
            {
                break;
            }
        }

        if ( iRet == 0 )
        {
            iRet = sfThis.length - sfTarget.length;
        }

        return iRet;
    }

    /**
     * Compare this field to another field. First compare the tags,
     * and if they are equal, then compare the values.
     *
     * @param mvl the field to compare
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
    public int compareTo(MarcVblLengthField mvl)
    {
        int iRet = 0;

        if ( (iRet = this.compareTag(mvl)) == 0 )
        {
            if ( (iRet = this.compareIndicators(mvl)) == 0 )
            {
                iRet = this.compareValue(mvl);
            }
        }

        return iRet;
    }

    /**
     * Compare this MarcVblLengthField with another object.
     * This method is here to satify the <code>java.lang.Comparable</code>
     * Interface. Please see that Interface's documentation for an expalanation
     * of the parameters, results, and exceptions.
     */
    public int compareTo(Object o)
    {
        return compareTo((MarcVblLengthField) o);
    }

}
