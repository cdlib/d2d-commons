package org.cdlib.util.marc;

import org.apache.log4j.Logger;
import org.cdlib.util.string.StringUtil;

/**
 * Container class for the location entries that comprise the holdings
 * location table.
 *
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: Location.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $
 */

public class Location
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(Location.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/Location.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $";

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

	/*
	 * Location fields
	 */
    public String location   = null;
    public String type       = null;
    public String region     = null;
    public String suppress   = null;
    public String campus     = null;
    public String cmp        = null;
    public String primary    = null;
    public String campusName = null;
    public String prefix     = null;
    public String notes      = null;

	/*
	 * Location field lengths
	 */
    public static final int locationLen   = 24;
    public static final int typeLen       = 1;
    public static final int regionLen     = 1;
    public static final int suppressLen   = 1;
    public static final int campusLen     = 5;
    public static final int cmpLen        = 15;
    public static final int primaryLen    = 9;
    public static final int campusNameLen = 50;
    public static final int prefixLen     = 15;
    public static final int notesLen      = 70;

	/**
	 * Instantiate a empty <code>Location</code> object.
	 * Use the <code>setLocFields(String loc)</code> method to initialize
	 * the object.
	 */
    public Location()
    {
    }

	/**
	 * Instantiate a <code>Location</code> object with an entry from the location table
	 * text file. The <code>String</code> is broken into its constituent part based
	 * on fixed displacements, and those parts are stored in this object's class
	 * variables.
	 */
    public Location(String loc)
    {
		setLocFields(loc);
    }

	/**
	 * Set, or reset, this object's class variables with an entry from the location table
	 * text file. The <code>String</code> is broken into its constituent part based
	 * on fixed displacements, and those parts are stored in this object's class
	 * variables.
	 */
	public void setLocFields(String loc)
	{
        String[] locparts = parseLoc(loc);

        location   = locparts[0];
        type       = locparts[1];
        region     = locparts[2];
        suppress   = locparts[3];
        campus     = locparts[4];
        cmp        = locparts[5];
        primary    = locparts[6];
        campusName = locparts[7];
        prefix     = locparts[8];
        notes      = locparts[9];
	}

	/**
	 * Split a location table entry <code>String</code> into its constituent fields
	 * and return those fields in a <code>String</code> array.
	 *
	 * @param loc the location table entry
	 * @return the location fields
	 */
    public String[] parseLoc(String loc)
    {
        String[] locparts = new String[10];
        int beg = 0;
        int end = 0;
        int max = (loc == null) ? 0 : loc.length();

        // get the location field
        end += locationLen;
        locparts[0] = parsePart(loc, beg, end).trim();

        // get the type field
        beg = end;
        end += typeLen;
        locparts[1] = parsePart(loc, beg, end).trim();

        // get the region field
        beg = end;
        end += regionLen;
        locparts[2] = parsePart(loc, beg, end).trim();

        // get the suppress field
        beg = end;
        end += suppressLen;
        locparts[3] = parsePart(loc, beg, end).trim();

        // get the campus field
        beg = end;
        end += campusLen;
        locparts[4] = parsePart(loc, beg, end).trim();

        // get the cmp field
        beg = end;
        end += cmpLen;
        locparts[5] = parsePart(loc, beg, end).trim();

        // get the primary field
        beg = end;
        end += primaryLen;
        locparts[6] = parsePart(loc, beg, end).trim();

        // get the campus name field
        beg = end;
        end += campusNameLen;
        locparts[7] = parsePart(loc, beg, end).trim();

        // get the prefix field
        beg = end;
        end += prefixLen;
        locparts[8] = parsePart(loc, beg, end).trim();

        beg = end;
        end += notesLen;
        locparts[9] = parsePart(loc, beg, end).trim();

        return locparts;
    }

	/**
	 * Extract a field from a location entry <code>String</code> using the
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
             || beg >= end
             || sLen < end )
        {
            sRet = new String();
        }
        else
        {
            sRet = s.substring(beg, end);
        }

        return sRet;
    }

	/**
	 * Convert the fields in the this object to a <code>String</code>.
	 * All fields are left padded with spaces to the correct length.
	 *
	 * @return the location entry <code>String</code>
	 */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(200);

        sb.append(StringUtil.pad(location, locationLen));
        sb.append(StringUtil.pad(type, typeLen));
        sb.append(StringUtil.pad(region, regionLen));
        sb.append(StringUtil.pad(suppress, suppressLen));
        sb.append(StringUtil.pad(campus, campusLen));
        sb.append(StringUtil.pad(cmp, cmpLen));
        sb.append(StringUtil.pad(primary, primaryLen));
        sb.append(StringUtil.pad(campusName, campusNameLen));
        sb.append(StringUtil.pad(prefix, prefixLen));
        sb.append(StringUtil.pad(notes, notesLen));

        return sb.toString();
    }

}
// end of Location class
