package org.cdlib.util;

/**
 * Provides a variety of utility methods for with hex data.
 *
 * Donated to the CDL by Shawn McGovern.
 *
 * @author <a href="mailto: shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: HexUtils.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $
 */
public class HexUtils
{
    /**
     * Contructor declared private to prevent instantiation.
     *
     * This class contains only static methods and should never be intantiated.
     */
    private HexUtils()
    {
    }


    /**
     * Returns a hex format string of the specified <code>String</code>.
     *
     * @param str the <code>String</code> to hexprint
     * @return the hex printed <code>String</code>
     */
    public static String hexPrint(String str)
    {
        return (str == null ? null : hexPrint(str.getBytes()));
    }


    /**
     * Returns a hex format string of the specified <code>Character</code>.
     *
     * @param cc the <code>Character</code> to hexprint
     * @return the hex printed <code>String</code>
     */
    public static String hexPrint(Character cc)
    {
        return (cc == null ? null : hexPrint(cc.charValue()));
    }


    /**
     * Returns a hex format string of the specified <code>char</code>.
     * The character specified is first converted to a <code>byte</code> array.
     * To allow single byte ascii characters to print as one byte
     * if the high-order byte is 0x00 then that byte is discarded.
     *
     * @param c the <code>char</code> to hexprint
     * @return the hex printed <code>String</code>
     */
    public static String hexPrint(char c)
    {
        byte[] b = null;
        if ( (c & '\uFF00') > 0 )
        {
            b = new byte[2];
            b[0] = (byte)(c >> 8);
            b[1] = (byte)c;
        }
        else
        {
            b = new byte[1];
            b[0] = (byte)c;
        }

        return (hexPrint(b));
    }


    /**
     * Returns a hex formatted string of the bytes in a <code>byte</code> array.
     *
     * @param bytes the <code>byte</code> array
     * @return the hex printed <code>String</code>
     */
    public static String hexPrint(byte[] bytes)
    {
        String       sRet      = null;
        StringBuffer sb        = null;
        int          bLength   = (bytes == null) ? 0 : bytes.length;
        int          count     = 0;
        int          lefthalf  = 0;
        int          righthalf = 0;
        char[]       nibbles   =
        {
            '0', '1','2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

        if ( bLength > 0 )
        {
            //System.out.println("bLength = " + bLength);

            sb = new StringBuffer( bLength * 2 );

            while ( count < bLength )
            {
                //System.out.println("count = " + count);

                lefthalf = (bytes[count] & 0xF0) >> 4;
                righthalf = bytes[count] & 0x0F;

                //System.out.println("lefthalf = " + lefthalf);
                //System.out.println("righthalf = " + righthalf);

                sb.append(nibbles[lefthalf]);
                sb.append(nibbles[righthalf]);
                count++;
            }

            sRet = sb.toString();
        }

        return sRet;
    }


    /**
     * Returns two hex formatted strings of the bytes in the <code>byte</code> array
     * suitable for over and under printing.
     * E.g., [a, b, c, d] would hex print as "61626364".
     * This method returns ["6666", "1234"].
     * So that the results may be printed as:<br>
     *
     * abcd<br>
     * 6666<br>
     * 1234<br>
     *
     * This method is a convenience method that invokes hexPrintChar2 to do the work.
     */
    public static String[] hexPrint2(byte[] bytes)
    {
        String[]     sRet      = null;
        String       sLeft     = null;
        String       sRight    = null;
        int          bLength   = (bytes == null) ? 0 : bytes.length;

        if ( bLength > 0 )
        {
            char[][] hexchars = hexPrintChar2(bytes);
            sLeft   = new String(hexchars[0]);
            sRight  = new String(hexchars[1]);
            sRet    = new String[2];
            sRet[0] = sLeft;
            sRet[1] = sRight;
        }

        return sRet;
    }


    /**
     * Translate the bytes in the <code>byte</code> array into two character
     * arrays containing the right and left halfs of the hex formatted values
     * suitable for over and under printing.
     * E.g., [a, b, c, d] would hex print as "61626364".
     * This method returns [['6', '6', '6', '6'], ['1', '2', '3', '4'].
     * So that the results may be printed as:<br>
     *
     * abcd<br>
     * 6666<br>
     * 1234<br>
     */
    public static char[][] hexPrintChar2(byte[] bytes)
    {
        char[][]     cRet      = null;
        char[]       cLeft     = null;
        char[]       cRight    = null;
        int          bLength   = (bytes == null) ? 0 : bytes.length;
        int          count     = 0;
        int          lefthalf  = 0;
        int          righthalf = 0;
        char[]       nibbles   =
        {
            '0', '1','2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

        if ( bLength > 0 )
        {
            //System.out.println("bLength = " + bLength);

            cLeft = new char[bLength];
            cRight = new char[bLength];

            while ( count < bLength )
            {
                //System.out.println("count = " + count);

                lefthalf = (bytes[count] & 0xF0) >> 4;
                righthalf = bytes[count] & 0x0F;

                //System.out.println("lefthalf = " + lefthalf);
                //System.out.println("righthalf = " + righthalf);

                cLeft[count] = (nibbles[lefthalf]);
                cRight[count] = (nibbles[righthalf]);
                count++;
            }

            cRet = new char[2][];
            cRet[0] = cLeft;
            cRet[1] = cRight;
        }

        return cRet;
    }

    /*
     * main method provided to unit test methods.
     * Feel free to add and delete code as needed.
     */
    public static void main(String[] args)
    {
        char c = '\u03F4';
        System.out.println("Char(\\u03F4) = '" + c + "' Hex = '" + hexPrint(c) + "'");
        c = '\u0025';
        System.out.println("Char(\\u0025) = '" + c + "' Hex = '" + hexPrint(c) + "'");
        Character cc = new Character('$');
        System.out.println("Character($) = '" + cc.toString() + "' Hex = '" + hexPrint(cc) + "'");
    }

}
// End of HexUtils class
