package org.cdlib.util;

import org.apache.log4j.Logger;

/**
 * Provides a variety of utility methods for operating on data
 * at the byte level.
 *
 * Donated to the CDL by Shawn McGovern.
 *
 * @author <a href="mailto: shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: ByteUtils.java,v 1.3 2002/07/18 00:27:08 smcgovrn Exp $
 */
public class ByteUtils
{
    private static Logger log = Logger.getLogger(ByteUtils.class);

    /**
     * Contructor declared private to prevent instantiation.
     * This class contains only static methods and should never be intantiated.
     *
     */
    private ByteUtils()
    {
    }


    /**
     * Convert little endian <code>byte</code> array to a <code>long</code>.
     * So [1, 2, 3, 4, 5, 6, 7, 8] becomes 87654321.
     */
    public static long byteToLongLE(byte[] bytes)
    {
        long lRet = 0;
        int  count = 0;
        int  bLength = 0;
        long unsignedByte = 0;

        if ( bytes == null )
        {
            throw new NumberFormatException();
        }
        else
        {
            bLength = bytes.length;
            if ( bLength > 8 || bLength < 1 )
            {
                throw new NumberFormatException();
            }
        }

        while ( count < bLength )
        {
            unsignedByte = (bytes[count] < 0) ? (256 + bytes[count]) : bytes[count];
            lRet += unsignedByte << (count * 8);
            count++;
        }

        return lRet;
    }


    /**
     * Convert big endian <code>byte</code> array to a <code>long</code>.
     * So [1, 2, 3, 4, 5, 6, 7, 8] becomes 12345678.
     */
    public static long byteToLongBE(byte[] bytes)
    {
        long lRet = 0;
        int  count = 0;
        int  bLength = 0;
        long unsignedByte = 0;

        if ( bytes == null )
        {
            throw new NumberFormatException();
        }
        else
        {
            bLength = bytes.length;
            if ( bLength > 8 || bLength < 1 )
            {
                throw new NumberFormatException();
            }
        }

        while ( count < bLength )
        {
            unsignedByte = (bytes[count] < 0) ? (256 + bytes[count]) : bytes[count];
            lRet += unsignedByte << (((bLength - 1) - count) * 8);
            count++;
        }

        return lRet;
    }


    /**
     * Convert little endian <code>byte</code> array to an <code>int</code>.
     * So [1, 2, 3, 4] becomes 4321.
     */
    public static int byteToIntLE(byte[] bytes)
    {
        int iRet = 0;
        int count = 0;
        int bLength = 0;
        int unsignedByte = 0;

        if ( bytes == null )
        {
            throw new NumberFormatException();
        }
        else
        {
            bLength = bytes.length;
            if ( bLength > 4 || bLength < 1 )
            {
                throw new NumberFormatException();
            }
        }

        while ( count < bLength )
        {
            unsignedByte = (bytes[count] < 0) ? (256 + bytes[count]) : bytes[count];
            iRet += unsignedByte << (count * 8);
            count++;
        }

        return iRet;
    }


    /**
     * Convert big endian <code>byte</code> array to an <code>int</code>.
     * So [1, 2, 3, 4] becomes 1234.
     */
    public static int byteToIntBE(byte[] bytes)
    {
        int iRet = 0;
        int count = 0;
        int bLength = 0;
        int unsignedByte = 0;

        if ( bytes == null )
        {
            throw new NumberFormatException();
        }
        else
        {
            bLength = bytes.length;
            if ( bLength > 8 || bLength < 1 )
            {
                throw new NumberFormatException();
            }
        }

        while ( count < bLength )
        {
            unsignedByte = (bytes[count] < 0) ? (256 + bytes[count]) : bytes[count];
            iRet += unsignedByte << (((bLength - 1) - count) * 8);
            count++;
        }

        return iRet;
    }


    /**
     * Converts an <code>int</code> into a little endian <code>byte</code> array.
     */
    public static byte[] intToByte(int ii)
    {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            bytes[i] = (byte) (ii >> ((3 - i) * 8));
        }
        return bytes;
    }


    /**
     * Converts an <code>long</code> into a little endian <code>byte</code> array.
     */
    public static byte[] longToByte(long ll)
    {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++)
        {
            bytes[i] = (byte) (ll >> ((7 - i) * 8));
        }
        return bytes;
    }


    /**
     * Returns a hex formatted string of the bytes in the <code>byte</code> array.
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
     * suitable for over and under printing. E.g., [a, b, c, d] would hex print as
     * "61626364". This method returns ["6666", "1234"]. So that the results may be
     * printed as:<br>
     * abcd<br>
     * 6666<br>
     * 1234<br>
     */
    public static String[] hexPrint2(byte[] bytes)
    {
        String[]     sRet      = null;
        String       sLeft     = null;
        String       sRight    = null;
        StringBuffer sbLeft    = null;
        StringBuffer sbRight   = null;
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

            sbLeft = new StringBuffer( bLength );
            sbRight = new StringBuffer( bLength );

            while ( count < bLength )
            {
                //System.out.println("count = " + count);

                lefthalf = (bytes[count] & 0xF0) >> 4;
                righthalf = bytes[count] & 0x0F;

                //System.out.println("lefthalf = " + lefthalf);
                //System.out.println("righthalf = " + righthalf);

                sbLeft.append(nibbles[lefthalf]);
                sbRight.append(nibbles[righthalf]);
                count++;
            }

            sLeft = sbLeft.toString();
            sRight = sbRight.toString();
            sRet = new String[2];
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


    /**
     * Extract a section of a <code>byte</code> array.
     *
     * @param bytesIn
     * @param start
     * @param length
     *
     */
    public static byte[] slice(byte[] bytesIn, int start, int length)
    {
        byte[] bytesOut = null;
        int    bLength  = 0;

        if ( bytesIn == null )
        {
            throw new IllegalArgumentException();
        }
        else
        {
            bLength = bytesIn.length;
        }

        if ( start < 0 || start >= bLength || length < 1
             || (start + length) > bLength )
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        bytesOut = new byte[length];
        int i = start;
        int j = 0;

        while ( j < length )
        {
            bytesOut[j++] = bytesIn[i++];
        }

        return bytesOut;
    }


    /**
     * Extract a section of a <code>byte</code> array from the specified start
     * to the end of the array.
     *
     * @param bytesIn
     * @param start
     *
     */
    public static byte[] slice(byte[] bytesIn, int start)
    {
        byte[] bytesOut = null;
        int    bLength  = 0;

        if ( bytesIn == null )
        {
            throw new IllegalArgumentException();
        }
        else
        {
            bLength = bytesIn.length;
        }

        if ( start < 0 || start >= bLength )
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        bytesOut = slice(bytesIn, start, bLength - start);

        return bytesOut;
    }


    /**
     * Overlay a section of a byte array.
     *
     * @param target
     * @param src
     * @param start
     *
     */
    public static void overlay(byte[] target, byte[] src, int start)
    {
        int    targetLen = 0;
        int    srcLen    = 0;

        if ( target == null || src == null )
        {
            throw new IllegalArgumentException();
        }
        else
        {
            targetLen = target.length;
            srcLen = src.length;
        }

        if ( start < 0 || start >= targetLen || srcLen + start > targetLen )
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        int i = start;
        int j = 0;

        while ( j < srcLen )
        {
            target[i++] = src[j++];
        }

        return;
    }


    /**
     * Overlay a <code>byte</code> array from the beginning.
     *
     * @param target
     * @param src
     *
     */
    public static void overlay(byte[] target, byte[] src)
    {
        int    targetLen = 0;
        int    srcLen    = 0;

        if ( target == null || src == null )
        {
            throw new IllegalArgumentException();
        }

        if ( src.length > target.length )
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        overlay(target, src, 0);

        return;
    }


    /**
     * Append one byte array to another and return the result.
     *
     * @param target
     * @param src
     *
     */
    public static byte[] append(byte[] target, byte[] src)
    {
        byte[] bytesOut  = null;
        int    bytesLen  = 0;
        int    targetLen = 0;
        int    srcLen    = 0;

        if ( target == null )
        {
            targetLen = 0;
        }
        else
        {
            targetLen = target.length;
        }

        if ( src == null )
        {
            srcLen = 0;
        }
        else
        {
            srcLen = src.length;
        }

        bytesLen = targetLen + srcLen;
        bytesOut = new byte[bytesLen];

        int i = 0;
        int j = 0;

        while ( j < targetLen )
        {
            bytesOut[i++] = target[j++];
        }

        j = 0;

        while ( j < srcLen )
        {
            bytesOut[i++] = src[j++];
        }

        return bytesOut;
    }


    /**
     * Insert one byte array into another and return the resulting array.
     *
     * @param target
     * @param src
     * @param start
     *
     */
    public static byte[] insert(byte[] target, byte[] src, int start)
    {
        byte[] bytesOut  = null;
        int    bytesLen  = 0;
        int    targetLen = 0;
        int    srcLen    = 0;

        if ( target == null || src == null )
        {
            throw new IllegalArgumentException();
        }

        targetLen = target.length;
        srcLen = src.length;

        if ( start < 0 || start > targetLen )
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        bytesLen = targetLen + srcLen;
        bytesOut = new byte[bytesLen];

        int i = 0;
        int j = 0;

        while ( j < start )
        {
            bytesOut[i++] = target[j++];
        }

        j = 0;

        while ( j < srcLen )
        {
            bytesOut[i++] = src[j++];
        }

        j = start;

        while ( j < targetLen )
        {
            bytesOut[i++] = target[j++];
        }

        return bytesOut;
    }


    /**
     * Insert one byte array into another, at the beginning,
     * and return the resulting array.
     *
     * @param target
     * @param src
     *
     */
    public static byte[] insert(byte[] target, byte[] src)
    {
        byte[] bytesOut  = null;

        if ( target == null || src == null )
        {
            throw new IllegalArgumentException();
        }

        bytesOut = insert(target, src, 0);

        return bytesOut;
    }


    /**
     * Extract a null delimited string from a byte array.
     *
     * @param bytes
     * @param start
     * @param max
     *
     */
    public static String getCString(byte[] bytes, int start, int max)
    {
        String sRet    = null;
        byte[] sBytes = null;
        int    bLength = 0;
        int    count   = 0;
        int    i       = 0;
        int    len     = 0;


        if ( bytes == null )
        {
            throw new IllegalArgumentException();
        }
        else
        {
            bLength = bytes.length;
        }

        if ( start < 0 || start >= bLength || max < 0 )
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        i = start;


        while ( i < bLength )
        {
            if ( bytes[i++] == 0 )
            {
                if (count > max)
                {
                    throw new ArrayIndexOutOfBoundsException();
                }

                if ( count == 0 )
                {
                    sRet = new String();
                }
                else
                {
                    sBytes = slice(bytes, start, count);
                    sRet = new String(sBytes);
                }

                break;
            }
            count++;
        }

        return sRet;
    }


    /**
     * Extract a null delimited string from a byte array.
     *
     * @param bytes
     * @param start
     *
     */
    public static String getCString(byte[] bytes, int start)
    {
        String sRet    = null;
        int    bLength = 0;

        if ( bytes == null )
        {
            throw new IllegalArgumentException();
        }
        else
        {
            bLength = bytes.length;
        }

        if ( start < 0 || start >= bLength )
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        sRet = getCString(bytes, start, Integer.MAX_VALUE);

        return sRet;
    }


    /**
     * Extract a null delimited string from a byte array.
     *
     * @param bytes
     *
     */
    public static String getCString(byte[] bytes)
    {
        String sRet    = null;
        int    bLength = 0;

        if ( bytes == null )
        {
            throw new IllegalArgumentException();
        }
        else
        {
            bLength = bytes.length;
        }

        sRet = getCString(bytes, 0, Integer.MAX_VALUE);

        return sRet;
    }

}
// End of Byte class
