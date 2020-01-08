package org.cdlib.util.string;

import org.apache.log4j.Logger;

/**
 * A utility class with two static methods to hex print a <code>String</code>.
 *
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @version $Id: StringDisplay.java,v 1.5 2002/10/22 21:28:08 smcgovrn Exp $
 */
public class StringDisplay
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(StringDisplay.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/string/StringDisplay.java,v 1.5 2002/10/22 21:28:08 smcgovrn Exp $";

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
	 * Short named alias for the system specific line end. Using this variable
	 * not only saves typing, but it also saves lookups to the system properties
	 * map.
	 */
    public static final String EOL = System.getProperty("line.separator");

    /**
     * Print displayable hex dump for string.
     *
     * @param str    string to dump
     * @param header header to print out
     */
    public static String stringPrthex(String str, String header)
    {
        return stringPrthex(str, header, 30);
    }

    /**
     * Print displayable hex dump for string.
     *
     * @param str      string to dump
     * @param header   header to print out
     * @param lineSize number of str characters in dump
     */
    public static String stringPrthex(String str,
                                      String header,
                                      int lineSize)
    {
        int ix, ix2, ixend;
        char mc;
        int size = str.length();

        StringBuffer buf = new StringBuffer();
        if ( header.length() > 0 )
        {
            buf.append(EOL + header);
            buf.append("  len=" + size);
        }

        for ( ix=0; ix<size; ix+=lineSize )
        {
            buf.append(EOL);
            ixend = (ix+lineSize>=size) ? size-ix : lineSize;
            buf.append(F.f(ix, 3,  F.ZF) + " C: ");
            for (ix2=0; ix2<ixend; ix2++)
            {
                mc = str.charAt(ix+ix2);
                if ( mc == 0x1f ) buf.append("-$");
                else if ( mc == 0x1e ) buf.append("|-");
                else if ( mc >= 0x20 ) buf.append(" " + mc);
                else buf.append("..");
            }
            buf.append(EOL);

            buf.append(F.f(ix, 3, F.ZF) + " X: ");

            for ( ix2=0; ix2<ixend; ix2++ )
            {
                mc = str.charAt(ix+ix2);
                int ic = (int)mc;
                String hex = new Integer(ic).toHexString(ic);
                buf.append(hex);
            }
            buf.append(EOL);
        }
        return buf.toString();
    }

}
