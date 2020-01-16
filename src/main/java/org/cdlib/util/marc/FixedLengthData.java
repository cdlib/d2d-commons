package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Base class for MARC leader and fixed length fields.
 *
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: FixedLengthData.java,v 1.5 2002/11/12 21:41:54 smcgovrn Exp $
 */
public class FixedLengthData implements Comparable
{
 	/**
	 * log4j Logger for this class.
	 */
   private static Logger log = Logger.getLogger(FixedLengthData.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/FixedLengthData.java,v 1.5 2002/11/12 21:41:54 smcgovrn Exp $";

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
	 * character array to hold this field's data
	 */
    private char[] data;

    /**
     * Create object with all positions set to ASCII spaces.<br>
     * Backing store is a character array.<br>
     * @param len Data length
     */
    protected FixedLengthData(int len)
    {
        this(len, null, ' ');
    }

    /**
     * Create initialized object.<br>
     * The characters in s fill the first s.length() data positions.
     * The remaining positions are filled with ASCII spaces.<br>
     * Backing store is a character array.<br>
     * @param len Data length
     * @param s The data string to store
     * @exception java.lang.ArrayIndexOutOfBoundsException If len < s.length()
     */
    protected FixedLengthData(int len, String s)
    {
        this(len, s, ' ');
    }

    /**
     * Create initialized object.<br>
     * The characters in s fill the first s.length() data positions.
     * The remaining positions are filled with the character c.<br>
     * Backing store is a character array.<br>
     * @param len The length of this field
     * @param s The data string to store
     * @param c Fill character for this field
     * @exception java.lang.ArrayIndexOutOfBoundsException If len < s.length()
     */
    protected FixedLengthData(int len, String s, char c)
    {
        int sLen = (s == null ? 0 : s.length());

        if ( len < sLen || len < 0 )
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        data = new char[len];           // allocate a new data object
        if ( sLen > 0 )
        {
            setPos(0, s);               // initialize it with the string
        }

        int fillLen = len - sLen;
        if ( fillLen > 0)
        {
            setPos(sLen, len - 1, c);  // fill as needed
        }
    }

    /**
     * Set value at one or more positions. If the starting position exceeds
     * the length of this object's data array then no copying takes place.
     * If the last position falls beyond the end of this object's data array
     * then copying stops at the end of the array. If the starting position
     * is negative an ArrayIndexOutOfBoundsException is thrown.
     * @param first First position to be set. (Positions numbered from 0.)
     * @param last Last position set.
     * @param c Character used for position value.
     * @exception java.lang.ArrayIndexOutOfBoundsException If first < 0
     */
    protected void setPos(int first, int last, char c)
    {
        if (first < 0)
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        int max = Math.min(last + 1, data.length);

        for( int i = first; i < max; i++ )
        {
            data[i] = c;
        }
    }

    /**
     * Set value at one positions.
     * @param pos Position to be set. (Positions numbered from 0.)
     * @param c Character used for position value.
     * @exception java.lang.ArrayIndexOutOfBoundsException if <tt>pos</tt>
     * exceeds array boundaries.
     */
    protected void setPos(int pos, char c)
    {
        data[pos] = c;
    }

    /**
     * Set values of one or more positions.
     * @param start Starting position. (Positions numbered from 0.)
     * @param s String value to use
     * @exception java.lang.ArrayIndexOutOfBoundsException if the copy
     *            would result in an array underflow or overflow.
     */
    protected void setPos(int start, String s)
    {
        char[] buf = s.toCharArray();
        System.arraycopy(buf, 0, data, start, buf.length);
    }

    /**
     * Return character at the specified position.
     * @exception java.lang.ArrayIndexOutOfBoundsException
     * If <tt>pos</tt> exceeds array boundaries.
     */
    protected char getPos(int pos)
    {
        return data[pos];
    }

    /**
     * Return characters from first thruough last as a <code>String</code>.
     * @exception java.lang.ArrayIndexOutOfBoundsException If the copy
     * would result in an array underflow or overflow.
     */
    protected String getPos(int first, int last)
    {
        int len = last - first + 1;
        char[] buf = new char[len];
        System.arraycopy(data, first, buf, 0, len);
        return new String(buf);
    }

    /**
     * Return the character array that backs this object.
     *
     * @return the character array that backs this object.
     */
    protected char[] getChars()
    {
        return data;
    }


    /**
     * Return length of data
     */
    protected int length()
    {
        return data.length;
    }

    /**
     * Return String representing data for display
     */
    public String toString()
    {
        return new String(data);
    }

    /**
     * Return String representing data, for use in
     * creating MARC formatted records
     */
    protected String dump()
    {
        return new String(data);
    }

    /**
     * Compare this FixedLengthData object to another FixedLengthData object.
     *
     * @param fd the FixedLengthData object to compare
     *
     * @return zero if the argument's data is the same length as this object's, and
     *         the character arrays have the same contents; a number less that zero
     *         if this object's data length is shorter than the argument's data
     *         length or the characters in this object's data are lexicographically
     *         lower than the argument's data; a number greater than zero
     *         if this object's data length is longer than the argument's data
     *         length or the characters in this object's data are lexicographically
     *         higher than the argument's data.
     *
     * @throws NullPointerException if the argument is null
     */
    public int compareTo(FixedLengthData fd)
    {
        int    iRet = 0;
        char[] fdc  = fd.getChars();
        int    max  = Math.max(data.length, fdc.length);

        for ( int i = 0, j = 0; i < max && j < max; i++, j++)
        {
            if ( (iRet = data[i] - fdc[j]) != 0 )
            {
                break;
            }
        }

        if ( iRet == 0 )
        {
            iRet = data.length - fdc.length;
        }

        return iRet;
    }

    /**
     * Compare this FixedLengthData object with another object.
     * This method is here to satify the <code>java.lang.Comparable</code>
     * Interface. Please see that Interface's documentation for an expalanation
     * of the parameters, results, and exceptions.
     */
    public int compareTo(Object o)
    {
        return compareTo((FixedLengthData) o);
    }

}
