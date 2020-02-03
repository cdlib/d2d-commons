package org.cdlib.util.marc;

import org.apache.log4j.Logger;
import org.cdlib.util.string.StringUtil;

/**
 * Container class for the shared print entries that comprise the holdings
 * shared print table.
 *
 * @author Mark Reyes
 * @version $Id: SharedPrint.java,v 1.2 2005/05/27 17:50:36 rkl Exp $
 */

public class SharedPrint
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(SharedPrint.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/SharedPrint.java,v 1.2 2005/05/27 17:50:36 rkl Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.2 $";

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

	/*
	 * shared print fields
	 */
    public String searchKey   = null;
    public String f793a       = null;
    public String f793p       = null;

	/*
	 * shared print field lengths
	 */
    public static final int searchKeyLen   = 30;
    public static final int f793aLen       = 50;
    public static final int f793pLen       = 80;

	/**
	 * Instantiate a empty <code>Shared Print</code> object.
	 * Use the <code>setSharedPrintFields(String sp)</code> method to initialize
	 * the object.
	 */
    public SharedPrint()
    {
    }

	/**
	 * Instantiate a <code>SharedPrint</code> object with an entry from the Shared Print table
	 * text file. The <code>String</code> is broken into its constituent part based
	 * on fixed displacements, and those parts are stored in this object's class
	 * variables.
	 */
    public SharedPrint(String sp)
    {
		setSharedPrintFields(sp);
    }

    	/**
	 * Set, or reset, this object's class variables with an entry from the Shared Print table
	 * text file. The <code>String</code> is broken into its constituent part based
	 * on fixed displacements, and those parts are stored in this object's class
	 * variables.
	 */
    public void setSharedPrintFields(String sp)
    {
        String[] spparts = parseSharedPrint(sp);

        searchKey   = spparts[0];
        f793a       = spparts[1];
        f793p       = spparts[2];
	}

	/**
	 * Split a Shared Print table entry <code>String</code> into its constituent fields
	 * and return those fields in a <code>String</code> array.
	 *
	 * @param  the Shared Print table entry
	 * @return the Shared Print fields
	 */
    public String[] parseSharedPrint(String sp)
    {
        String[] spparts = new String[3];
        int beg = 0;
        int end = 0;
        int max = (sp == null) ? 0 : sp.length();

        // get the search key field
        end += searchKeyLen;
        spparts[0] = parsePart(sp, beg, end).trim();

        // get the f793a field
        beg = end;
        end += f793aLen;
        spparts[1] = parsePart(sp, beg, end).trim();

        // get the f793p field
        beg = end;
        end += f793pLen;
        spparts[2] = parsePart(sp, beg, end).trim();

        return spparts;
    }

	/**
	 * Extract a field from a table entry <code>String</code> using the
	 * supplied offsets for the beginning and end of the field.
	 *
	 * @param s the <code>String</code> to parse
	 * @param beg the beginning offset
	 * @param end the ending offset
	 * @return the field <code>String</code>
	 */
    public String parsePart(String s, int beg, int end)
    {
        String sRet = null;
        int    sLen = (s == null) ? -1 : s.length();

        if ( sLen < 1
             || beg < 0
             || beg >= sLen
             || beg >= end )
        {
            sRet = new String();
        }
        else
        {
            sRet = (sLen < end) ? s.substring(beg, sLen) : s.substring(beg, end);
        }

        return sRet;
    }

	/**
	 * Convert the fields in the this object to a <code>String</code>.
	 * All fields are left padded with spaces to the correct length.
	 *
	 * @return the shared print entry <code>String</code>
	 */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(200);

        sb.append(StringUtil.pad(searchKey, searchKeyLen));
        sb.append(StringUtil.pad(f793a, f793aLen));
        sb.append(StringUtil.pad(f793p, f793pLen));

        return sb.toString();
    }

}
// end of SharedPrint class
