package org.cdlib.util.string;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.cdlib.util.HexUtils;

/**
 * A collection of utility functions to aid in <code>String</code>
 * manipulation.
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: StringUtil.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $
 */
public class StringUtil
{
	/**
	 * log4j Logger for this class.
	 */
    private static final Logger log = Logger.getLogger(StringUtil.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/string/StringUtil.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $";

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
     * Tests if all the characters of a string are digits
     * @param s the <code>String</code> to test
     */
    public static final boolean isNumeric(String s)
	{
        for (int i = 0; i < s.length(); i++)
		{
            if (! Character.isDigit(s.charAt(i)))
			{
				return false;
			}
        }
        return true;
    }

    /**
     * Tests if the character at the specified offest
     * in the given string is a digit.
     */
    public static final boolean isNumeric(String s, int i)
	{
        return (Character.isDigit(s.charAt(i)));
    }

    /**
     * Tests if the <code>String</code> passed is null or empty.
     */
    public static final boolean isEmpty(String s)
	{
		return (s == null || s.length() == 0);
	}

    /**
     * Tests if the <code>StringBuffer</code> passed is null or empty.
     */
    public static final boolean isEmpty(StringBuffer sb)
	{
		return (sb == null || sb.length() == 0);
	}

    /**
     * Tests if the<code>byte</code> array passed is null or empty.
     */
    public static final boolean isEmpty(byte[] b)
	{
		return (b == null || b.length == 0);
	}

    /**
     * Test if the <code>String</code> passed is not null and not empty.
     */
    public static final boolean isNotEmpty(String s)
	{
		return ! isEmpty(s);
	}

    /**
     * Test if the StringBuffer passed is not null and not empty
     */
    public static final boolean isNotEmpty(StringBuffer sb)
    {
        return ! isEmpty(sb);
    }

    /**
     * Test if the byte array passed is not null and not empty
     */
    public static final boolean isNotEmpty(byte[] b)
    {
        return ! isEmpty (b);
    }

    /**
     * Returns the number of blanks requested.
     * The empty string is returned if the number is <= 0
     */
    public static final String blanks(int len)
    {
        return blanks(len, ' ');
    }

    /**
     * Returns the number of the chars requested.
     * The empty string is returned if the number is less than or equal to zero.
     */
    public static final String blanks(int len, char c)
    {
        if (len <= 0) return new String();

        StringBuffer sb = new StringBuffer(len);
        for (int i = 0; i < len; i++)
		{
			sb.append(c);
		}

        return sb.toString();
    }

    /**
     * Squeeze the whitespace from a string
     */
    public static final String squeeze(String s)
    {
        StringTokenizer st = new StringTokenizer(s);
        StringBuffer    sb = new StringBuffer(s.length());

        while (st.hasMoreTokens())
		{
			sb.append(st.nextToken());
		}

        return (sb.toString());
    }

    /**
     * Squeeze a set of characters from a string
     */
    public static final String squeeze (String s, String toRemove)
    {
        StringTokenizer st = new StringTokenizer(s, toRemove);
        StringBuffer    sb = new StringBuffer(s.length());

        while (st.hasMoreTokens())
		{
			sb.append(st.nextToken());
		}

        return (sb.toString());
    }

	/**
	 * Returns a number to a zero padded <code>String</code>.
	 */
    public static final String pad(int ival, int size, char padchar)
    {
        String str = new Integer(ival).toString();
        if (str.length() >= size)
		{
			return str;
		}

        StringBuffer sb = new StringBuffer(size);
		int max = size - str.length();
        for (int i=0; i < max; i++)
		{
			sb.append(padchar);
		}

        sb.append(str);
        return sb.toString();
    }

    /**
     * Pad the string passed to a field the size passed.
     */
    public static final String pad(String s, int l)
    {
        return (s + blanks(l - s.length()));
    }

    /**
     * Pad the string passed to a field the size passed with the char passed.
     */
    public static final String pad(String s, int l, char c)
	{
        return (s + blanks(l-s.length(), c));
    }

    /**
     * Pad the string buffer passed to a field the size passed.
     */
    public static final void pad(StringBuffer sb, int l)
	{
        sb.append(blanks(l-sb.length()));
    }

    /**
     * Pad the string passed to a field the size passed with the char passed.
     */
    public static final void pad(StringBuffer sb, int l, char c)
	{
        sb.append(blanks(l-sb.length(), c));
    }

    /**
     * Left pad the string passed to a field the size passed.
     */
    public static final String leftPad(String s, int l)
	{
        return (blanks(l-s.length()) + s);
    }

    /**
     * Left pad the string passed to a field the size passed with the char passed
     */
    public static final String leftPad(String s, int l, char c)
	{
        return (blanks(l-s.length(), c) + s);
    }

    /**
     * Left pad the string buffer passed to a field the size passed
     */
    public static final void leftPad(StringBuffer sb, int l)
	{
        sb.insert(0, blanks(l-sb.length()));
    }

    /**
     * Left pad the string passed to a field the size passed with the char passed
     */
    public static final void leftPad(StringBuffer sb, int l, char c)
	{
        sb.insert (0, blanks(l-sb.length(), c));
    }

    /**
     * Right returns the right most characters of a string
     */
    public static final String right(String s, int l)
	{
        try
		{
            return s.substring(s.length()-l);
        }
		catch (StringIndexOutOfBoundsException e)
		{
            return "";
        }
    }

    /**
     * Left returns the left most characters of a string
     */
    public static final String left(String s, int l)
	{
        try
		{
            return s.substring (0, l);
        }
		catch (StringIndexOutOfBoundsException e)
		{
            return "";
        }
    }

    /**
     * Return the index of the earliest of the characters
     * in the second string. firstOf ("abc", "c1a") returns 1.
     * -1 is returned if none of the characters are found.
     */
    public static int firstOf(String search, String sought)
	{
        int min = -1;
        int cur = 0;
        for (int k = 0; k < sought.length(); k++)
		{
            cur = search.indexOf(sought.charAt(k));
            if (cur >= 0)
                min = (min > -1 ? (min < cur ? min : cur) : cur);
        }
        return min;
    }

    /**
     * Return the given date in the format YYYYMMSSHHMMSSTTT.
     */
    public static String getFormattedDate(Date d)
	{
        Calendar c = Calendar.getInstance();
        c.setTime (d);

        int year   = c.get(Calendar.YEAR);
        int month  = c.get(Calendar.MONTH) + 1;
        int day    = c.get(Calendar.DAY_OF_MONTH);
        int hour   = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int milli  = c.get(Calendar.MILLISECOND);

        return new String("" + year +
						  (month  < 10 ? "0" : "") + month +
						  (day    < 10 ? "0" : "") + day +
						  (hour   < 10 ? "0" : "") + hour +
						  (minute < 10 ? "0" : "") + minute +
						  (second < 10 ? "0" : "") + second +
						  (milli  < 10 ? "00" : (milli < 100 ? "0" : "" )) + milli);
    }

    /**
     * Adjoin two strings together using a specified delimiter,
     * if either is empty, just return the other.
     */
    public static String adjoin(String target, String source, String delimiter)
    {
        if (isEmpty(source))
		{
			return target;
		}

        if (isEmpty(target))
		{
			return source;
		}

        return target + delimiter + source;
    }

    /**
     * Adjoin a string to a stringBuffer separated by a specified delimiter,
     * if either is empty, just return the other.
     */
    public static StringBuffer adjoin(StringBuffer target, String source, String delimiter)
    {
        if (isEmpty(source))
		{
			return target;
		}

        if (isEmpty(target))
		{
			return new StringBuffer(source);
		}

        target.append(delimiter);
        target.append(source);
        return target;
    }

    /**
     * Convert a string to a long, and allow for an empty or null string
     */
    public static long parseLong(String s)
    {
		return (isEmpty(s) ? 0 : Long.parseLong(s));
    }

    /**
     * Convert a string to a int, and allow for an empty or null string
     */
    public static int parseInt(String s)
    {
        return (isEmpty(s) ?  0 : Integer.parseInt(s));
    }

    /**
     * processBackSpace allows backspaces in the line to delete the previous
     * characters as you would expect at a terminal.
     */
    public static String processBackSpace(String line, char backSpace)
    {
        String sb = line;
        String pre, post;
        // while there are backspaces
        int nextBack = sb.indexOf(backSpace);
        while (nextBack >= 0)
        {
            // Get the stuff before the backspace, and the character before it
            if (nextBack >= 2)
                pre = sb.substring (0, nextBack-1);
            else pre = "";

            // Get the stuff after the backspace
            if (nextBack < sb.length()-1)
                post = sb.substring (nextBack+1);
            else post = "";

            // Put the string back together
            sb = pre + post;
            nextBack = sb.indexOf (backSpace);
        }
        return sb;
    }

    /**
     * processBackSpace allows backspaces in the line to delete the previous
     * characters as you would expect at a terminal. This routine will process
     * backspaces the conventional backspace
     */
    public static String processBackSpace(String line)
    {
        return processBackSpace(processBackSpace(line, (char)127), '\b');
    }

    /**
     * Replace a substring value with another substring value.
     * @param in - string to have value replace
     * @param from - current substring value in 'in'
     * @param to - replacement value for 'from'
     * @return - new string with replaced substrings
     */
    public static String replace(String in, String from, String to)
    {
        StringBuffer sbuf = new StringBuffer();
        int start=0;
        int pos = 0;
        while (start < in.length())
		{
            pos = in.indexOf(from, start);

            if (pos >= 0)
			{
                sbuf.append(in.substring(start, pos));
                sbuf.append(to);
                start = pos + from.length();
            }
            else
			{
                sbuf.append(in.substring(start));
                start = in.length();
            }
        }
        return sbuf.toString();
    }

    /**
     * stripTelnetSpecials removes special characters from a strings that are
     * telnet control options.
     */
    public static String stripTelnetSpecials (String line)
	{
		int lsize = line.length();
        StringBuffer so = new StringBuffer(lsize);
        // For each character see if we include it or skip it
        for (int i = 0; i < lsize; i++)
		{
            int c = (int)line.charAt(i);
            // Everything gets included unless it starts 255
            if (c != 255)
			{
				so.append (line.charAt(i));
			}
			else
			{
                // Skip the 255 (and make sure we're not off the end
                if (++i < line.length())
				{
                    // Leave i pointing to the last charcter to ignore
                    switch ((int)line.charAt(i))
					{
                        // 255 250 x x x x ... 240
                        case 250:
                            for (; i < line.length(); i++)
							{
                                if ((int)line.charAt(i) == 240)
								{
									break;
								}
                            }
                            break;
                        // 255 251/252/253/254 x
                        case 251:
                        case 252:
                        case 253:
                        case 254:
                            i++;
                            break;
                        // 255 255 (encoded 255)
                        case 255:
                            i--;
                        default:
                    }
                }
            }
        }
        return (so.toString());
    }


    /**
     * Convert a slice of a character array of ascii digits to an <code>int</code>.
     * The ascii assumption allows us to bypass the normal rather expensive
     * Integer.parseInt(), and it's underlying Character.isDigit() invocations.
     *
     * @param source The characters to convert
     * @param start The index at which to start
     * @param len The number of characters to process
     * @exception NumberFormatException When any of the charaters processed
     * is not an ascii digit ('\u0030' - 'u0039').
     * @exception IllegalArgumentException When any of the following are true:<br>
     * 1. The source array is null<br>
     * 2. The start index is less that zero<br>
     * 3. The start index plus the number of characters to process exceeds
     *    the length of the source array.<br>
     */
    public static int ascii2int(char[] source, int start, int len)
    {
        int iRet = 0;
        int max = start + len;

        if ( source == null || start < 0 || source.length < max )
        {
            throw new IllegalArgumentException();
        }

        for ( int i = start; i < max; i++ )
        {
            char c = source[i];

            if ( c < '\u0030' || c > '\u0039' )
            {
                throw new NumberFormatException();
            }
            else
            {
                iRet = iRet * 10 + (int)(c & 0x000F);
            }
        }

        return iRet;
    }

    /**
     * Convert a character array of ascii digits to an <code>int</code>.
     *
     * @param source The characters to convert
     * @see #ascii2int(char[],int,int)
     */
    public static int ascii2int(char[] source)
    {
        return ascii2int(source, 0, source.length);
    }


    /**
     * Convert an ascii digit to an <code>int</code>.
     *
     * @param c The character to convert
     * @exception NumberFormatException When the character
     * is not an ascii digit ('\u0030' - 'u0039').
     * @see #ascii2int(char[],int,int)
     */
    public static int ascii2int(char c)
    {
        if ( c < '\u0030' || c > '\u0039' )
        {
            throw new NumberFormatException();
        }
        else
        {
            return (int)(c & 0x000F);
        }
    }

    /**
     * Convert a string of ascii digits to an <code>int</code>.
     *
     * @param source The <code>String</code> to convert
     * @see #ascii2int(char[],int,int)
     */
    public static int ascii2int(String source)
    {
        return ascii2int(source.toCharArray(), 0, source.length());
    }


    /**
     * Perform a case insensitive search for a target <code>String</code>
     * within a source <code>String</code> starting at the specified index.<br>
     *
     * Return the index of the first occurence of the target if it is found,
     * or -1 if it is not found.<br>
     *
     * The case insensitivity is implemented using
     * <code>java.lang.String.equalsIgnoreCase(String)</code>
     * as model. First the source and target are up cased, then
     * <code>java.lang.String.indexOf(String,int)</code> is performed.
     * If the result is greater than -1 it is returned. Otherwise, the source
     * and target are down cased, <code>java.lang.String.indexOf(String,int)</code>
     * is again performed, and the result is returned.<br>
     *
     * @param  source The <code>String</code> to search
     * @param  target The <code>String</code> to search for
     * @param  start The index at which to begin the search
     *
     * @return The starting index if found or -1 if not found
     * @throws java.lang.NullPointerException If either source or target is null
     *
     * @see    java.lang.String#equalsIgnoreCase(String)
     * @see    java.lang.String#indexOf(String,int)
     * @see    java.lang.String#regionMatches(boolean,int,String,int,int)
     */
    public static int indexOfIgnoreCase(String source, String target, int start)
    {
        int iRet =-1;
        int begin = Math.max(0, start);

        if ( (iRet = source.toUpperCase().indexOf(target.toUpperCase(), begin)) == -1 )
        {
            iRet = source.toLowerCase().indexOf(target.toLowerCase(), begin);
        }

        return iRet;
    }


    /**
     * Perform a case insensitive search for a target <code>String</code>
     * within a source <code>String</code>.<br>
     *
     * Return the index of the first occurence of the target if it is found,
     * or -1 if it is not found.<br>
     *
     * The case insensitivity is implemented using
     * <code>java.lang.String.equalsIgnoreCase(String)</code>
     * as model. First the source and target are up cased, then
     * <code>java.lang.String.indexOf(String,int)</code> is performed.
     * If the result is greater than -1 it is returned. Otherwise, the source
     * and target are down cased, <code>java.lang.String.indexOf(String,int)</code>
     * is again performed, and the result is returned.<br>
     *
     * @param  source The <code>String</code> to search
     * @param  target The <code>String</code> to search for
     *
     * @return The starting index if found or -1 if not found
     * @throws java.lang.NullPointerException If either source or target is null
     *
     * @see    java.lang.String#equalsIgnoreCase(String)
     * @see    java.lang.String#indexOf(String,int)
     * @see    java.lang.String#regionMatches(boolean,int,String,int,int)
     */
    public static int indexOfIgnoreCase(String source, String target)
    {
        return indexOfIgnoreCase(source, target, 0);
    }


    /**
     * Tests if a source <code>String</code> starts with the specified
     * prefix <code>String</code> in a case insensitive manner.
     *
     * Return the index of the first occurence of the target if it is found,
     * or -1 if it is not found.<br>
     *
     * The case insensitivity is implemented using
     * <code>java.lang.String.equalsIgnoreCase(String)</code>
     * as model. First the source and prefix are up cased, then
     * <code>java.lang.String.startsWith(String)</code> is performed.
     * If the result is true it is returned. Otherwise, the source and prefix
     * are down cased, <code>java.lang.String.startsWith(String,int)</code>
     * is again performed, and the result is returned.<br>
     *
     * @param  source The <code>String</code> to search
     * @param  prefix The <code>String</code> to search for
     *
     * @return true if the target begins with the prefix, otherwise false
     * @throws java.lang.NullPointerException If either source or prefix is null
     *
     * @see    java.lang.String#equalsIgnoreCase(String)
     * @see    java.lang.String#startsWith(String)
     * @see    java.lang.String#regionMatches(boolean,int,String,int,int)
     */
    public static boolean startsWithIgnoreCase(String source, String prefix)
    {
        return ( source.toUpperCase().startsWith(prefix.toUpperCase())
                 || source.toLowerCase().startsWith(prefix.toLowerCase()) );
    }


    /**
     * Remove leading and trailing backslashes from a <code>String</code>,
     * and replace internal backslashes with spaces.
     *
     * @param  term The <code>String</code> to strip.
     *
     * @return The resulting <code>String</code>,
     *         or null if the specified <code>String</code> is null.
     */
    public static String stripBackSlash(String term)
    {
        // Handle empty string
        if ( term == null || term.length() == 0)
        {
            return term;
        }

        char[] termchars = term.toCharArray();
        char[] termnew = new char[termchars.length];
        int end = termchars.length - 1;
        int i = 0;
        int j = 0;

        // Handle '\' at start - skip over it
        if ( termchars[0] == '\\' )
        {
            i++;
        }

        // Handle '\' at end - decrement the end index
        while ( end > -1 && termchars[end] == '\\' )
        {
            end--;
        }

        for ( ; i <= end; i++)
        {
            if (termchars[i] == '\\')
            {
                termnew[j++] = ' ';
            }
            else
            {
                termnew[j++] = termchars[i];
            }
        }
        return new String(termnew, 0, j);
    }


    /**
     * For each <code>String</code> in a <code>Vector</code> of <code>Strings</code>,
     * remove leading and trailing backslashes, and replace internal backslashes
     * with spaces.
     *
     * @param  values The <code>Vector</code> of <code>Strings</code> to strip.
     *
     * @return A <code>Vector</code> containing the resulting <code>Strings</code>,
     *         or null if the specified <code>Vector</code> is null.
     *
     * @throws java.lang.ClassCastException if any of element of the <code>Vector</code>
     *         is not a <code>String</code>.
     */
    public static Vector stripBackSlash(Vector values)
    {
        Vector newValues = null;
        String val       = null;
        int    vsize     = 0;

        if (values != null)
        {
            vsize = values.size();
            newValues = new Vector(vsize);
            for (int i = 0; i < vsize; i++)
            {
                val = stripBackSlash((String)values.elementAt(i));
                newValues.addElement(val);
            }
        }
        return newValues;
    }

    /**
     * Trim leading and trailing whitespace from a <code>Stringg/code>.
     * @param s the <code>String</code> to trim
     * @return the trimmed <code>String</code>
     * 
     * Modified : 12/12/2011 pjd
     * commented old code 
     * Replace with java 
     */
    public static String trimWhiteSpace(String s)
    
    {
    	
    	
 /*begin comment old code */       
       
 /*
        String sRet = null;
        try
 

        {
        	
            RE leadingWS = new RE("^\\s+");
            RE trailingWS = new RE("\\s+$");
            if ( log.isDebugEnabled() )
            {
                log.debug("original string = '" + s + "' hex = ' " + HexUtils.hexPrint(s) + "'");
            }

            sRet = leadingWS.subst(s, "");
            sRet = trailingWS.subst(sRet, "");

        	
  
        }
        catch (RESyntaxException e)
        {
            log.error("Regex Exception - original String returned - " + e.getMessage());
            sRet = s;
        }

        if ( log.isDebugEnabled() )
        {
            log.debug("trimmed string = '" + sRet + "' hex = ' " + HexUtils.hexPrint(sRet) + "'");
        }

        return sRet; */ 
        
        
        
        /*end comment*/  
        
        
        
        /* Modified : 12/12/2011 pjd 
         * replace with new code */  
    	String str = null; 
    	String leadingWS = "^\\s+";
		String trailingWS = "\\s+$";
		
		if (s != null) {

			Pattern replace = Pattern.compile(leadingWS);
			Matcher match = replace.matcher(s);
			str = match.replaceAll("");
			replace = Pattern.compile(trailingWS);
			match = replace.matcher(str);
			str = match.replaceAll("");
		} 

		return str;

        
    }

    /*
     * main provided to unit test methods.
     */
    public static void main(String[] args)
    {
        int max = (args == null ? 0 : args.length);
        for ( int i = 0; i < max; i++ )
        {
            System.out.println("args[" + i + "] = '" + args[i]
                               + "' - tws = '" + trimWhiteSpace(args[i]) + "'");
        }
        System.out.println("Processed " + max + " arguments");

    }

}
