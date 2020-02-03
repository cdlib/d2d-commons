package org.cdlib.util.string;

import org.apache.log4j.Logger;

/**
 *
 * Based heavily on the Fmt class by Jef Poskanzer <jef@acme.com>
 *
 * Visit his ACME Labs Java page at http: *www.acme.com/java/
 * This code from: http://informagen.com/Java/F/F.java.html
 *
 * Doing  fprintf-like formatting in Java
 *
 * It is apparently impossible to declare a Java method that accepts
 * variable numbers of any type of argument.  You can declare it to take
 * Objects, but numeric variables and constants are not in fact Objects.
 *
 * However, using the built-in string concatenation, it's almost as
 * convenient to make a series of single-argument formatting routines.
 *
 * Class F can format the following datatypes:
 *
 *     boolean, byte, char, short, int, long, float, double,
 *     Boolean, Byte, Character, Short, Integer, Long, Float,
 *     Date, Color, StringBuffer, String, and Object
 *
 * For each data type there is a set of overloaded methods, each returning
 * a formatted String.  There's the plain formatting version:
 *
 *      F.f(x)
 *
 *
 * There's a version specifying a field width:
 *
 *      F.f(x, width)
 *
 *
 * There's a version which takes formatting flags:
 *
 *      F.f(x, flags)
 *
 *
 * And there's a version that takes both:
 *
 *      F.f(x, width, flags)
 *
 *
 * Currently available flags are:
 *
 *      F.RJ - right justify (the default)
 *      F.LJ - left justify
 *      F.CJ - center justify
 *      F.ZF - zero fill
 *      F.RF - repeat fill
 *      F.HX - hexadecimal
 *      F.OC - octal
 *      F.BN - binary
 *      F.LC - lowercase
 *      F.UC - uppercase
 *      F.XC - OK to exceed field width
 *      F.TR - truncate string to width
 *
 * Right justify is the default for all formatting.
 *
 * Center justify must be used with a width and will not work with
 *   zero or repeat fill or left justify. In these cases it is ignored.
 *
 * The HX, OC and BN flags produce unsigned output. Hexidecimal representation
 *  have an "0x" prefix and octal representation have a "0" prefix.
 *
 * Repeat fill is useful for making string of the same character
 *
 * Uppercase and lowecase can be used on numbers which contain letter ie Hexadecimal
 *   or exponential.
 *
 * Use XC when the field width isn't equal to zero ie "DEFAULT" but you don't might if
 *  the value exceed its field boundaries.
 *
 * Use TR with care with numeric fields as the output could be misinterpreted. XC will
 *  override TR.
 *
 *
 * For formatting dates there are a set of predefined format strings for convienence.
 *   See the documentation for the class "java.text.SimpleDateFormat" for an extensive
 *   description of the format used in date templates.
 *
 *      F.SYBASEDATE = "MMM dd yyyy hh:mm:ss:SSSaa"  Sybase datetime stamp
 *      F.DATE       = "dd-MMM-yyyy"                 Three character month
 *      F.TIME       = "hh:mmaa"                     AM/PM 12 hour time
 *      F.MILTIME    = "HHmm"                        24 hour time ie Military
 *      F.MONTH      = "MMMM"                        Full month name
 *      F.WEEKDAY    = "EEEE"                        Full weekday name
 *
 *
 * For real numbers, ie doubles and floats, there's a significant-figures parameter.
 *
 *      F.f(d, width, decimalDigits)
 *
 *
 * If the value of decimalDigits is negative the value will be treated as the number of
 *  significant figures to be printed. Significant figures are used in scientific calculations
 *  in order to express intermediate and final calculation with the proper significance
 *  from the measured values.
 *
 *
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @version $Id: F.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $
 */

public class F
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(F.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/string/F.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $";

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

    // Flags ----------------------------------------------------------------------

    public static final int DEFAULT = Integer.MAX_VALUE;    // Use the default width/sigfigs

    public static final short RJ =    1;    // Right justify
    public static final short LJ =    2;    // Left justify
    public static final short CJ =    4;    // Center justify

    public static final short ZF =    8;    // Zero-fill
    public static final short RF =   16;    // Repeat-fill

    public static final short HX =   32;    // Hexadecimal
    public static final short OC =   64;    // Octal
    public static final short BN =  128;    // Binary

    public static final short UC =  256;    // Uppercase
    public static final short LC =  512;    // Lowercase

    public static final short XC = 1024;    // OK to exceed field width
    public static final short TR = 2048;    // Truncate string to width



    // Convienent date/time templates --------------------------------------------
    // Re: "java.text.SimpleDateFormat" for a guide to using the symbols

    public static final String SYBASEDATE = "MMM dd yyyy hh:mm:ss:SSSaa";   // Sybase datetime stamp
    public static final String DATE       = "dd-MMM-yyyy";                  // Three character month
    public static final String TIME       = "hh:mmaa";                      // AM/PM 12 hour time
    public static final String MILTIME    = "HHmm";                         // 24 hour time ie Military
    public static final String MONTH      = "MMMM";                         // Full month name
    public static final String WEEKDAY    = "EEEE";                         // Full weekday name


    // boolean --------------------------------------------------------------------

    public static String f(boolean b) { return f(b, DEFAULT, RJ); }
    public static String f(boolean b, short flags) { return f(b, DEFAULT, flags); }
    public static String f(boolean b, int width) { return f(b, width, RJ); }

    public static String f(boolean b, int width, short flags) {

        if (((flags & HX) != 0 ) | ((flags & OC) != 0 ) | ((flags & BN) != 0 ))
            return f((b) ? (byte)1 : (byte)0, width, flags);
        else
            return f((b) ? "true" : "false", width, flags);
    }


    // byte -----------------------------------------------------------------------

    public static String f(byte b) { return f(b, DEFAULT, RJ); }
    public static String f(byte b, short flags) { return f(b, DEFAULT, flags); }
    public static String f(byte b, int width) { return f(b, width, RJ); }

    public static String f(byte b, int width, short flags) {

        if (((flags & HX) != 0 ) | ((flags & OC) != 0 ) | ((flags & BN) != 0 ))
            return f((b&0xff), width, flags);
        else
            return f(Integer.toString(b&0xff), width, flags);
    }


    // char -----------------------------------------------------------------------

    public static String f(char c) { return f(c, DEFAULT, RJ); }
    public static String f(char c, short flags) { return f(c, DEFAULT, flags); }
    public static String f(char c, int width) { return f(c, width, RJ); }

    public static String f(char c, int width, short flags) {

        boolean hexadecimal = ((flags & HX) != 0);
        boolean octal       = ((flags & OC) != 0);
        boolean binary      = ((flags & BN) != 0);

        if (hexadecimal)
            return f("0x" + f(Integer.toHexString(c & 0xffff), 4, ZF), width, flags);
        else if (octal | binary)
            return f((short)c, width, flags);
        else
            return f(new Character(c).toString(), width, flags);
    }



    // short -----------------------------------------------------------------------

    public static String f(short s) {return f(s, DEFAULT, RJ); }
    public static String f(short s, short flags) {return f(s, DEFAULT, flags); }
    public static String f(short s, int width) { return f(s, width, RJ); }

    public static String f(short s, int width, short flags) {

        if (((flags & HX) != 0 ) | ((flags & OC) != 0 ) | ((flags & BN) != 0 ))
            return f((s & 0xffff), width, flags);
        else
            return f(Integer.toString(s), width, flags);
    }



    // int -----------------------------------------------------------------------

    public static String f(int i) { return f(i, DEFAULT, RJ); }
    public static String f(int i, short flags) { return f(i, DEFAULT, flags); }
    public static String f(int i, int width) { return f(i, width, RJ); }

    public static String f(int i, int width, short flags) {

        if (((flags & HX) != 0 ) | ((flags & OC) != 0 ) | ((flags & BN) != 0 ))
            return f((i & 0xffffffffL), width, flags );
        else
            return f(Integer.toString(i), width, flags);
    }


    // long -----------------------------------------------------------------------

    public static String f(long l) { return f(l, DEFAULT, RJ); }
    public static String f(long l, short flags) { return f(l, DEFAULT, flags); }
    public static String f(long l, int width) { return f(l, width, RJ ); }

    public static String f(long l, int width, short flags) {

        boolean hexadecimal = ((flags & HX) != 0 );
        boolean octal = ((flags & OC) != 0 );
        boolean binary = ((flags & BN) != 0 );

        if (hexadecimal)
            return f("0x" + Long.toHexString(l), width, flags);
        else if (octal)
            return f("0" + Long.toOctalString(l), width, flags);
        else if (binary)
            return f(Long.toBinaryString(l), width, flags);
        else
            return f(Long.toString(l), width, flags);
    }



    // Float and float -----------------------------------------------------------------------

    public static String f(Float f, int width, int decimalDigits, short flags) {
        return ( f == null ) ? f("null", width, flags) : f(f.floatValue(), width, decimalDigits, flags);
    }

    public static String f(float f) { return f(f, DEFAULT, DEFAULT, RJ); }
    public static String f(float f, short flags) { return f(f, DEFAULT, DEFAULT, flags); }
    public static String f(float f, int width) { return f(f, width, DEFAULT, RJ); }
    public static String f(float f, int width, short flags) { return f(f, width, DEFAULT, flags); }
    public static String f(float f, int width, int decimalDigits) { return f(f, width, decimalDigits, RJ); }

    public static String f(float f, int width, int decimalDigits, short flags) {


        if (((flags & HX) != 0 ) | ((flags & OC) != 0 ) | ((flags & BN) != 0 ))
            return f(Float.floatToIntBits(f), width, flags);


        if ( decimalDigits == DEFAULT )
            return f(Float.toString( f ), width, (short)flags);
        else
            return f(sigFig(Float.toString(f), decimalDigits), width, flags);
    }



    // Double and double -----------------------------------------------------------------------

    public static String f(Double d, int width, int decimalDigits, short flags) {
        return ( d == null ) ? f("null", width, flags) : f(d.doubleValue(), width, decimalDigits, flags);
    }

    public static String f(double d) { return f(d, DEFAULT, DEFAULT, RJ); }
    public static String f(double d, short flags) { return f(d, DEFAULT, DEFAULT, flags); }
    public static String f(double d, int width) { return f(d, width, DEFAULT, RJ); }
    public static String f(double d, int width, short flags) { return f(d, width, DEFAULT, flags); }
    public static String f(double d, int width, int decimalDigits) { return f(d, width, decimalDigits, RJ); }

    public static String f(double d, int width, int decimalDigits, short flags) {

        if (((flags & HX) != 0 ) | ((flags & OC) != 0 ) | ((flags & BN) != 0 ))
            return f(Double.doubleToLongBits(d), width, flags);

        if ( decimalDigits == DEFAULT )
            return f(Double.toString(d), width, flags);
        else
            return f(sigFig(Double.toString(d), decimalDigits), width, flags );
    }



    // Date -----------------------------------------------------------------------

    public static String f(java.util.Date d, String template) { return f(d, template, RJ); }
    public static String f(java.util.Date d, String template, int width) { return f(d, template, width, RJ); }
    public static String f(java.util.Date d, String template, short flags) { return f(d, template, DEFAULT, flags); }

    public static String f(java.util.Date d, String template, int width, short flags) {
        if ( d == null )
            return f("null", width, flags);
        else {
            java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(template);
            return f(dateFormatter.format(d), width, flags);
        }
    }



    // Color -----------------------------------------------------------------------

    public static String f(java.awt.Color c, int width, short flags) {

        if ( c == null)
            return f("null", width, flags);
        else {
            short binHexFlags = (short)((flags & HX) | (flags & OC) | (flags & BN));

            return f(f(c.getRed(), DEFAULT, binHexFlags) + "," +
                     f(c.getGreen(), DEFAULT, binHexFlags)+ ","  +
                     f(c.getBlue(), DEFAULT, binHexFlags), width, flags);
        }

    }



    // Object -----------------------------------------------------------------------

    public static String f(Object o) { return f(o, DEFAULT, RJ); }
    public static String f(Object o, int width) { return f(o, width, RJ); }
    public static String f(Object o, short flags) { return f(o, DEFAULT, flags); }

    public static String f(Object o, int width, short flags) {

        if ( o == null )
            return f("null", width, flags);
        else {

            if (o instanceof java.lang.String)
                return f((String)o, width, flags);
            else if (o instanceof java.lang.Boolean)
                return f(((Boolean)o).booleanValue(), width, flags);
            else if (o instanceof java.lang.Byte)
                return f(((Byte)o).byteValue(), width, flags);
            else if (o instanceof java.lang.Character)
                return f(((Character)o).charValue(), width, flags);
            else if (o instanceof java.lang.Short)
                return f(((Short)o).shortValue(), width, flags);
            else if (o instanceof java.lang.Integer)
                return f(((Integer)o).intValue(), width, flags);
            else if (o instanceof java.lang.Long)
                return f(((Long)o).longValue(), width, flags);
            else if (o instanceof java.lang.Float)
                return f(((Float)o).floatValue(), width, flags);
            else if (o instanceof java.lang.Double)
                return f(((Double)o).doubleValue(), width, flags);
            else if (o instanceof java.lang.StringBuffer)
                return f((java.lang.StringBuffer)o, width, flags);
            else if (o instanceof java.awt.Color)
                return f((java.awt.Color)o, width, flags);
            else
                return f(o.toString(), width, flags);
        }
    }

    // StringBuffer ------------------------------------------------------------------

    public static String f(StringBuffer sb, int width, short flags) {
        return ( sb == null) ? f("null", width, flags) : f(sb.toString(), width, flags);
    }

    // String ------------------------------------------------------------------------
    //
    //  String overloaded methods could be dispatched via the Object however
    //  as they are heavily used they are explicitly declared.


    public static String f(String s, int width, short flags) {

        width = (width < 0) ? -width : width;

        if ( s == null )
            return f("null", width, flags);


        int len = s.length();

        boolean zeroFill      = ((flags & ZF) != 0);
        boolean repeatFill    = ((flags & RF) != 0);

        boolean rightJustify  = ((flags & RJ) != 0);
        boolean leftJustify   = ((flags & LJ) != 0);
        boolean centerJustify = ((flags & CJ) != 0);

        boolean upperCase     = ((flags & UC) != 0);
        boolean lowerCase     = ((flags & LC) != 0);

        boolean okToExceed    = ((flags & XC) != 0);
        boolean truncate      = ((flags & TR) != 0);


        // Width is zero and we are not allowed to exceed it

        if ( width == 0 && !okToExceed )
            return "";


        // Width is not zero but the string is too big and we are not allowed to
        //   exceed the width or truncate the output, print a string of asterisks instead.

        if ( (width != DEFAULT) && (width < len) && (okToExceed == false) && (truncate == false) )
            return f("*", width, RF);


        // No special formatting to be done, this is the usual case so it
        //  is worthwhile taking the time to test here and just returning.

        if ( (width <= len || width == DEFAULT) &&
            !zeroFill &&
            !repeatFill &&
            !centerJustify &&
            !upperCase &&
            !lowerCase &&
            !truncate)
                return s;


        // Determine reasonable behavior from conflicting flags

        if ( rightJustify && (leftJustify || centerJustify) )
            rightJustify = false;

        if ( centerJustify && (repeatFill || zeroFill || leftJustify) )
            centerJustify = false;

        if ( repeatFill && zeroFill )
            repeatFill = false;

        if ( truncate && (okToExceed || (width == DEFAULT)) )
            truncate = false;


        // Zero and repeat fill, leading spaces

        int fillWidth = 0;

        if ( width != DEFAULT )
            fillWidth = (repeatFill) ? width : width - len;

        fillWidth = (fillWidth < 0) ? 0 : fillWidth;

        StringBuffer buffer = new StringBuffer(fillWidth + len);

        int i=0;
        while ( i<fillWidth ) {
            if ( zeroFill )
                buffer.append('0');
            else if ( repeatFill )
                buffer.append(s.charAt(i%len));
            else
                buffer.append(' ');
            i++;
        }

        String fill = buffer.toString();


        // Assemble the final string using the fill string created above

        buffer.setLength(0);

        if ( leftJustify ) {
              buffer.append(s).append(fill);
        } else if ( centerJustify ) {
              buffer.append(fill.substring(0,fillWidth/2)).append(s).append(fill.substring(fillWidth/2));
        } else if ( zeroFill && s.startsWith( "-" ) )
            buffer.append("-").append(fill).append(s.substring(1));
        else if ( repeatFill )
            buffer.append(fill);
        else
            buffer.append(fill).append(s);


        // Truncate

        if ( truncate && width != DEFAULT )
            buffer.setLength(width);


        // Deal with the case flags

        if ( upperCase )
            return buffer.toString().toUpperCase();
        else if ( lowerCase )
            return buffer.toString().toLowerCase();
        else
            return buffer.toString();


    }


    //-------------------------------------------------------------------------------------------
    // Internal routine, sigFig.


    private static String sigFig(String s, int decimalDigits) {

        // First dissect the floating-point number string into optional sign,
        // integer part, fraction part, and optional exponent.

        // Sign - may be '-' or '+' or nothing

        String sign;
        String unsigned;

        if ( s.startsWith( "-" ) || s.startsWith( "+" )) {
            sign = s.substring(0, 1);
            unsigned = s.substring(1);
        } else {
            sign = "";
            unsigned = s;
        }



        // Exponent -  may be 'e' or 'E' format

        String mantissa;
        String exponent;

        int eIndex = unsigned.indexOf('e');

        if ( eIndex == -1 )
            eIndex = unsigned.indexOf('E');

        if ( eIndex == -1) {
            mantissa = unsigned;
            exponent = "";
        } else {
            mantissa = unsigned.substring(0, eIndex);
            exponent = unsigned.substring(eIndex);
        }


        // Number and fraction

        StringBuffer number, fraction;

        int dotIndex = mantissa.indexOf('.');

        if ( dotIndex == -1) {
            number = new StringBuffer(mantissa);
            fraction = new StringBuffer("");
        } else {
            number = new StringBuffer(mantissa.substring(0, dotIndex));
            fraction = new StringBuffer(mantissa.substring(dotIndex + 1));
        }


        int numFigs = number.length();
        int fracFigs = fraction.length();

        // Don't count leading zeros in the fraction.
        if ((numFigs == 0 || number.equals("0")) && fracFigs > 0) {

            numFigs = 0;

            for ( int i = 0; i < fraction.length(); ++i) {
                if ( fraction.charAt( i ) != '0' )
                    break;
                --fracFigs;
            }
        }


        int mantFigs = numFigs + fracFigs;

        // if decimalDigits is positive return significant figures otherwise treat
        //  decimalDigits a precision.


        if ( decimalDigits < 0 || decimalDigits == DEFAULT) {

            int sigFigs = -decimalDigits;

            // Special case!!!
            if ( decimalDigits == DEFAULT)
                sigFigs = 0;


            if ( sigFigs > mantFigs) {                                  // We want more figures; just append zeros to the fraction.

                for ( int i = mantFigs; i < sigFigs; ++i )
                    fraction.append( '0' );

            } else if ( sigFigs < mantFigs && sigFigs >= numFigs) {     // Want fewer figures in the fraction; chop.

                fraction.setLength(fraction.length() - ( fracFigs - ( sigFigs - numFigs ) ) );

            } else if ( sigFigs < numFigs) {                                // Want fewer figures in the number; turn them to zeros.

                fraction.setLength(0);
                for ( int i = sigFigs; i < numFigs; ++i )
                    number.setCharAt( i, '0' );
            }


            if ( fraction.length() == 0 )
                return sign + number + exponent;
            else
                return sign + number + "." + fraction + exponent;

        } else {

            // Treat sigFig as a measure of precision

            if ( exponent.length()>0 && exponent.charAt(1) == '-' ) {

                number.append(fraction);
                fraction.setLength(0);
                fraction.append(f("0", Integer.parseInt(exponent.substring(2))-1, RF)).append(number);

                fracFigs = fraction.length();
                exponent = "";
                number.setLength(0);
                number.append("0");
            }


            for (int i = fracFigs; i < decimalDigits; ++i )
                fraction.append('0');

            fraction.setLength(decimalDigits);

            return  sign + number + "." + fraction + exponent;
        }


    }



}
