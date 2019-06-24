package org.cdlib.util;

import java.util.*;

public class StringUtil {

  private StringUtil() {
  }

  public final static String READABLE = "abcdefghijklmnopqrstuvwxyz"
          + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
          + " 1234567890`~!@#$%^&*()-_=+[{]}\\|'\";:/?.>,<";

  /**
   * isNumeric (String) tests if all the characters of a string are digits
   */
  public static final boolean isNumeric(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (!Character.isDigit(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * isNumeric (String, int) tests if the character, int, in a string is a digit
   */
  public static final boolean isNumeric(String s, int i) {
    if (Character.isDigit(s.charAt(i))) {
      return true;
    }
    return false;
  }

  /**
   * test if the string passed is null or empty
   */
  public static final boolean isEmpty(String s) {
    return s == null || s.equals("");
  }

  /**
   * test if the StringBuffer passed is null or empty
   */
  public static final boolean isEmpty(StringBuffer sb) {
    return sb == null || sb.length() == 0;
  }

  /**
   * test if the byte [] passed is null or empty
   */
  public static final boolean isEmpty(byte[] b) {
    return b == null || b.length == 0;
  }

  /**
   * test if the string passed is not null and not empty
   */
  public static final boolean isNotEmpty(String s) {
    return !isEmpty(s);
  }

  /**
   * test if the StringBuffer passed is not null and not empty
   */
  public static final boolean isNotEmpty(StringBuffer sb) {
    return !isEmpty(sb);
  }

  /**
   * test if the byte [] passed is not null and not empty
   */
  public static final boolean isNotEmpty(byte[] b) {
    return !isEmpty(b);
  }

  /**
   * blanks returns the number of blanks requested. The empty string is returned
   * if the number is <= 0
   */
  public static final String blanks(int l) {
    return blanks(l, ' ');
  }

  /**
   * blanks returns the number of the chars requested. The empty string is
   * returned if the number is <= 0
   */
  public static final String blanks(int l, char c) {
    if (l <= 0) {
      return "";
    }
    String s = String.valueOf(c);
    StringBuffer sb = new StringBuffer(l);
    for (int i = 0; i < l; i++) {
      sb.append(s);
    }
    return sb.toString();
  }

  /**
   * squeeze the blanks from a string
   */
  public static final String squeeze(String s) {
    StringTokenizer st = new StringTokenizer(s);
    StringBuilder sb = new StringBuilder(s.length());
    while (st.hasMoreTokens()) {
      sb.append(st.nextToken());
    }
    return (sb.toString());
  }

  /**
   * squeeze a set of characters from a string
   */
  public static final String squeeze(String s, String toRemove) {
    if (isEmpty(s)) {
      return "";
    }
    if (isEmpty(toRemove)) {
      return s;
    }
    StringTokenizer st = new StringTokenizer(s, toRemove);
    StringBuilder sb = new StringBuilder(s.length());
    while (st.hasMoreTokens()) {
      sb.append(st.nextToken());
    }
    return (sb.toString());
  }

  /**
   * blankout converts a set of characters to blanks. If a blank is included in
   * the characters to blank out, multiple blanks are also removed.
   */
  public static final String blankout(String s, String toBlankOut) {
    StringTokenizer st = new StringTokenizer(s, toBlankOut);
    StringBuffer sb = new StringBuffer(s.length());
    while (st.hasMoreTokens()) {
      if (sb.length() != 0) {
        sb.append(" ");
      }
      sb.append(st.nextToken());
    }
    return (sb.toString());
  }

  /**
   * blankoutAllBut converts all BUT a set of characters to blanks. That is,
   * only the characters supplied are left. Multiple blanks are removed.
   */
  public static final String blankoutAllBut(String s, String toLeave) {
    if (isEmpty(s)) {
      return "";
    }
    if (isEmpty(toLeave)) {
      return "";
    }
    StringBuffer sb = new StringBuffer(s.length());
    boolean handlingBlanks = false;
    // Loop through the string s one character at a time
    for (int i = 0; i < s.length(); i++) {
      // If it is one to leave, copy it
      if (toLeave.indexOf(s.charAt(i)) >= 0) {
        if (s.charAt(i) != ' '
                || !handlingBlanks) {
          sb.append(s.charAt(i));
        }
        // If it is a blank note it
        handlingBlanks = (s.charAt(i) == ' ');
      } else // Output a blank, if we haven't already done so
      if (!handlingBlanks) {
        sb.append(' ');
        handlingBlanks = true;
      }
    }
    return (sb.toString());
  }

  /**
   * compress multiple occurences of blanks/white space from a string
   */
  public static final String compress(String s) {
    StringTokenizer st = new StringTokenizer(s);
    StringBuffer sb = new StringBuffer(s.length());
    if (st.hasMoreTokens()) {
      sb.append(st.nextToken());
    }
    while (st.hasMoreTokens()) {
      sb.append(" ");
      sb.append(st.nextToken());
    }
    return (sb.toString());
  }

  /**
   * pad the string passed to a field the size passed
   */
  public static final String pad(String s, int l) {
    return s + blanks(l - s.length());
  }

  /**
   * pad the string passed to a field the size passed with the char passed
   */
  public static final String pad(String s, int l, char c) {
    return s + blanks(l - s.length(), c);
  }

  /**
   * pad the string buffer passed to a field the size passed
   */
  public static final void pad(StringBuffer sb, int l) {
    sb.append(blanks(l - sb.length()));
  }

  /**
   * pad the string passed to a field the size passed with the char passed
   */
  public static final void pad(StringBuffer sb, int l, char c) {
    sb.append(blanks(l - sb.length(), c));
  }

  /**
   * left pad the string passed to a field the size passed
   */
  public static final String leftPad(String s, int l) {
    return blanks(l - s.length()) + s;
  }

  /**
   * left pad the string passed to a field the size passed with the char passed
   */
  public static final String leftPad(String s, int l, char c) {
    return blanks(l - s.length(), c) + s;
  }

  /**
   * left pad the string buffer passed to a field the size passed
   */
  public static final void leftPad(StringBuffer sb, int l) {
    sb.insert(0, blanks(l - sb.length()));
  }

  /**
   * left pad the string passed to a field the size passed with the char passed
   */
  public static final void leftPad(StringBuffer sb, int l, char c) {
    sb.insert(0, blanks(l - sb.length(), c));
  }

  /**
   * right returns the right most characters of a string
   */
  public static final String right(String s, int l) {
    try {
      return s.substring(s.length() - l);
    } catch (StringIndexOutOfBoundsException e) {
      return s;
    }

  }

  /**
   * left returns the left most characters of a string
   */
  public static final String left(String s, int l) {
    try {
      return s.substring(0, l);
    } catch (StringIndexOutOfBoundsException e) {
      return s;
    }
  }

  /**
   * firstOf returns the index of the earliest of the characters in the second
   * string. firstOf ("abc", "c1a") returns 1. -1 is returned if none of the
   * characters are found.
   */
  public static int firstOf(String search, String sought) {
    int min = -1;
    int cur;
    for (int k = 0; k < sought.length(); k++) {
      cur = search.indexOf(sought.charAt(k));
      if (cur >= 0) {
        min = (min > -1 ? (min < cur ? min : cur) : cur);
      }
    }
    return min;
  }

 

  /**
   * adjoin two strings together using a specified delimiter, if either is
   * empty, just return the other.
   */
  public static String adjoin(String target, String source, String delimiter) {
    if (isEmpty(source)) {
      return target;
    }
    if (isEmpty(target)) {
      return source;
    }
    return target + delimiter + source;
  }

  /**
   * adjoin a string to a stringBuffer separated by a specified delimiter, if
   * either is empty, just return the other.
   */
  public static StringBuffer adjoin(StringBuffer target, String source, String delimiter) {
    if (isEmpty(source)) {
      return target;
    }
    if (isEmpty(target)) {
      return new StringBuffer(source);
    }
    target.append(delimiter);
    target.append(source);
    return target;
  }

  /**
   * Convert a string to a long, and allow for an empty or null string
   */
  public static long parseLong(String s) {
    if (isEmpty(s)) {
      return 0;
    } else {
      return Long.parseLong(s);
    }
  }

  /**
   * Convert a string to a int, and allow for an empty or null string
   */
  public static int parseInt(String s) {
    if (isEmpty(s)) {
      return 0;
    } else {
      return Integer.parseInt(s);
    }
  }

  /**
   * processBackSpace allows backspaces in the line to delete the previous
   * characters as you would expect at a terminal
   */
  public static String processBackSpace(String line, char backSpace) {
    String sb = line;
    String pre, post;
    // while there are backspaces
    int nextBack = sb.indexOf(backSpace);
    while (nextBack >= 0) {
      // Get the stuff before the backspace, and the character before it
      if (nextBack >= 2) {
        pre = sb.substring(0, nextBack - 1);
      } else {
        pre = "";
      }
      // Get the stuff after the backspace
      if (nextBack < sb.length() - 1) {
        post = sb.substring(nextBack + 1);
      } else {
        post = "";
      }
      // Put the string back together
      sb = pre + post;
      nextBack = sb.indexOf(backSpace);
    }
    return sb;
  }

  /**
   * processBackSpace allows backspaces in the line to delete the previous
   * characters as you would expect at a terminal. This routine will process
   * backspaces using the conventional backspace
   */
  public static String processBackSpace(String line) {
    return processBackSpace(
            processBackSpace(line,
                    (char) 127),
            '\b');
  }

  /**
   * stripTelnetSpecials removes special characters from a strings that are
   * telnet control options. (Some testing with this routine indicate it does
   * not always work. I think it has to do with the conversion between strings
   * and and back again.)
   */
  public static String stripTelnetSpecials(String line) {
    StringBuffer so = new StringBuffer(line.length());
    // For each character see if we include it or skip it
    for (int i = 0; i < line.length(); i++) {
      int c = (int) line.charAt(i);
      // Everything gets included unless it starts 255
      if (c != 255) {
        so.append(line.charAt(i));
      } else {
        // Skip the 255 (and make sure we're not off the end
        if (++i < line.length()) {
          // Leave i pointing to the last charcter to ignore
          switch ((int) line.charAt(i)) {
            // 255 250 x x x x ... 240
            case 250:
              for (; i < line.length(); i++) {
                if ((int) line.charAt(i) == 240) {
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
   * hasSpecialChars (String) creturn true if any of the characters in the
   * string passed have values over 127.
   */
  public static boolean hasSpecialChars(String in) {
    for (int i = 0; i < in.length(); i++) {
      if (in.charAt(i) > 127) {
        return true;
      }
    }
    return false;
  }

  /**
   * removeSpecialChars (String) return the passed string with any of the
   * characters with values over 127 removed.
   */
  public static String removeSpecialChars(String in) {
    StringBuffer out = new StringBuffer();
    for (int i = 0; i < in.length(); i++) {
      if (in.charAt(i) <= 127) {
        out.append(in.charAt(i));
      }
    }
    return out.toString();
  }

  /**
   * replaces all occurances of one string for another in a string. Example:
   * xchange ("This is text that repeats other text", "text", "stuff") returns
   * "This is stuff that repeats stuff"
   */
  public static String xchange(String in, String from, String to) {
    // handle empty parms
    if (isEmpty(from)) {
      return in;
    }
    if (isEmpty(in)) {
      return "";
    }
    // Start with the first test, so we can return immediately if the
    // from string isn't found
    int index = in.indexOf(from);
    if (index == -1) {
      return in;
    }
    // Create the output area we are building - use a StringBuffer for
    // efficiency
    StringBuffer out = new StringBuffer(in.length());
    // prev keeps track of the last position we replaced
    int prev = -1;
    // As long as we have found something...
    while (index >= 0) {
      // move the text from the prevous change to this occurance
      out.append(in.substring(prev + 1, index));
      // Move the new value
      out.append(to);
      // Skip the old value
      prev = index + from.length() - 1;
      // Look again
      index = in.indexOf(from, prev + 1);
    }
    // Move everything after the last occurance
    out.append(in.substring(prev + 1));
    return out.toString();
  }

  /**
   * toHex (String) returns the passed string in hex
   */
  public static String toHex(String inString) {
    StringBuffer outString = new StringBuffer(4 * inString.length());
    for (int i = 0; i < inString.length(); i++) {
      String hexChar = Integer.toHexString(inString.charAt(i));
      // Make the number of hex chars even
      if (hexChar.length() % 2 == 1) {
        outString.append("0");
      }
      outString.append(hexChar);
    }
    return outString.toString();
  }

  /**
   * toHex (byte []) returns the passed byte array in hex
   */
  public static String toHex(byte[] inBytes) {
    StringBuffer outString = new StringBuffer(4 * inBytes.length);
    for (int i = 0; i < inBytes.length; i++) {
      String hexChar;
      if (inBytes[i] < 0) {
        hexChar = Integer.toHexString(256 + inBytes[i]);
      } else {
        hexChar = Integer.toHexString(inBytes[i]);
      }
      // Make the number of hex chars even
      if (hexChar.length() % 2 == 1) {
        outString.append("0");
      }
      outString.append(hexChar);
    }
    return outString.toString();
  }

  /**
   * xClean cleans up certain special characters returned by the X-server
   *
   */
  public static String xClean(String inString) {
    if (isEmpty(inString)) {
      return "";
    }
    String outString = new String(inString);
    outString = StringUtil.xchange(outString, "%20", " ");
    outString = StringUtil.xchange(outString, "%40", "@");
    outString = StringUtil.xchange(outString, "%27", "'");
    outString = StringUtil.xchange(outString, "%2F", "/");
    return outString;
  }

  /**
   * toVDXDate - Converts a date string from yyyymmdd to dd-Mmm-yyyy
   *
   */
  public static String toVDXDate(String inString) {

    String[] month = {"???",
      "Jan", "Feb", "Mar", "Apr", "May", "Jun",
      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    if (isEmpty(inString)) {
      return "";
    }
    if (!isNumeric(inString)) {
      return inString;
    }
    // Make sure month is in range
    int i = (StringUtil.parseInt(inString.substring(4, 6)));
    if (i < 1 || i > 12) {
      return inString;
    }

    StringBuffer outString = new StringBuffer(11);
    //Set the day
    outString.append(inString.substring(6) + "-");
    //Set the month
    outString.append(month[i]);
    //Set the year
    outString.append("-" + inString.substring(0, 4));
    return outString.toString();
  }

  /**
   * unescape removes the %xx codes and replaces them with the character the
   * represent. (This was solen from the web @
   * http://www.w3.org/International/unescape.java)
   */
  public static String unescape(String s) {
    StringBuffer sbuf = new StringBuffer();
    int l = s.length();
    int ch = -1;
    int b, sumb = 0;
    for (int i = 0, more = -1; i < l; i++) {
      /* Get next byte b from URL segment s */
      switch (ch = s.charAt(i)) {
        case '%':
          ch = s.charAt(++i);
          int hb = (Character.isDigit((char) ch)
                  ? ch - '0'
                  : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
          ch = s.charAt(++i);
          int lb = (Character.isDigit((char) ch)
                  ? ch - '0'
                  : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
          b = (hb << 4) | lb;
          break;
        case '+':
          b = ' ';
          break;
        default:
          b = ch;
      }
      /* Decode byte b as UTF-8, sumb collects incomplete chars */
      if ((b & 0xc0) == 0x80) {			// 10xxxxxx (continuation byte)
        sumb = (sumb << 6) | (b & 0x3f);	// Add 6 bits to sumb
        if (--more == 0) {
          sbuf.append((char) sumb); // Add char to sbuf
        }
      } else if ((b & 0x80) == 0x00) {		// 0xxxxxxx (yields 7 bits)
        sbuf.append((char) b);			// Store in sbuf
      } else if ((b & 0xe0) == 0xc0) {		// 110xxxxx (yields 5 bits)
        sumb = b & 0x1f;
        more = 1;				// Expect 1 more byte
      } else if ((b & 0xf0) == 0xe0) {		// 1110xxxx (yields 4 bits)
        sumb = b & 0x0f;
        more = 2;				// Expect 2 more bytes
      } else if ((b & 0xf8) == 0xf0) {		// 11110xxx (yields 3 bits)
        sumb = b & 0x07;
        more = 3;				// Expect 3 more bytes
      } else if ((b & 0xfc) == 0xf8) {		// 111110xx (yields 2 bits)
        sumb = b & 0x03;
        more = 4;				// Expect 4 more bytes
      } else /*if ((b & 0xfe) == 0xfc)*/ {	// 1111110x (yields 1 bit)
        sumb = b & 0x01;
        more = 5;				// Expect 5 more bytes
      }
      /* We don't test if the UTF-8 encoding is well-formed */
    }
    return sbuf.toString();
  }

  /**
   * Returns a substring between the first occurrence of a start string and the
   * first occurrence of an end string.
   *
   * @param source the string to parse
   * @param start returned string segment begins after first occurrence of this
   * @param end returned string segment ends before first occurrence of this
   * @return the string between start and end
   * @throws IllegalArgumentException if any argument is empty or null
   */
  public static String between(String source, String start, String end) {
    if (isEmpty(source) || isEmpty(start) || isEmpty(end)) {
      throw new IllegalArgumentException("Unexpected null or empty value.");
    }
    int sectionStart = source.indexOf(start);
    int sectionEnd = source.indexOf(end);
    if (sectionStart == -1 || sectionEnd == -1 || sectionStart > sectionEnd) {
      return null;
    }
    String section = source.substring(sectionStart, sectionEnd);
    if (isEmpty(section)) {
      return null;
    }
    section = section.substring(start.length());
    return section;
  }

  /**
   * Keep only displayable ascii values
   */
  public static String stripNonDisplayAscii(String line) {
    StringBuffer so = new StringBuffer(line.length());
    // For each character see if we include it or skip it
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      // Include displayable ascii characters
      if ((c >= 32) && (c < 127)) {
        so.append(c);
      }
    }
    return (so.toString());
  }

  /**
   * Convert a slice of a character array of ascii digits to an
   * <code>int</code>. The ASCII assumption allows us to bypass the normal
   * rather expensive Integer.parseInt(), and it's underlying
   * Character.isDigit() invocations.
   *
   * @param source The characters to convert
   * @param start The index at which to start
   * @param len The number of characters to process
   * @exception NumberFormatException When any of the characters processed is
   * not an ascii digit ('\u0030' - 'u0039').
   * @exception IllegalArgumentException When any of the following are true:<br>
   * 1. The source array is null<br>
   * 2. The start index is less that zero<br>
   * 3. The start index plus the number of characters to process exceeds the
   * length of the source array.<br>
   */
  public static int ascii2int(char[] source, int start, int len) {
    int iRet = 0;
    int max = start + len;

    if (source == null || start < 0 || source.length < max) {
      throw new IllegalArgumentException();
    }

    for (int i = start; i < max; i++) {
      char c = source[i];

      if (c < '\u0030' || c > '\u0039') {
        throw new NumberFormatException();
      } else {
        iRet = iRet * 10 + (int) (c & 0x000F);
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
  public static int ascii2int(char[] source) {
    return ascii2int(source, 0, source.length);
  }

  /**
   * Convert an ascii digit to an <code>int</code>.
   *
   * @param c The character to convert
   * @exception NumberFormatException When the character is not an ascii digit
   * ('\u0030' - 'u0039').
   * @see #ascii2int(char[],int,int)
   */
  public static int ascii2int(char c) {
    if (c < '\u0030' || c > '\u0039') {
      throw new NumberFormatException();
    } else {
      return (int) (c & 0x000F);
    }
  }

  /**
   * Convert a string of ascii digits to an <code>int</code>.
   *
   * @param source The <code>String</code> to convert
   * @see #ascii2int(char[],int,int)
   */
  public static int ascii2int(String source) {
    return ascii2int(source.toCharArray(), 0, source.length());
  }

  /**
   * Perform a case insensitive search for a target <code>String</code> within a
   * source <code>String</code> starting at the specified index.<br>
   *
   * Return the index of the first occurrence of the target if it is found, or
   * -1 if it is not found.<br>
   *
   * The case insensitive is implemented using
   * <code>java.lang.String.equalsIgnoreCase(String)</code> as model. First the
   * source and target are up cased, then
   * <code>java.lang.String.indexOf(Sting,int)</code> is performed. If the
   * result is greater than -1 it is returned. Otherwise, the source and target
   * are down cased, <code>java.lang.String.indexOf(Sting,int)</code> is again
   * performed, and the result is returned.<br>
   *
   * @param source The <code>String</code> to search
   * @param target The <code>Stirng</code> to search for
   * @param start The index at which to begin the search
   *
   * @return The starting index if found or -1 if not found
   * @throws java.lang.NullPointerException If either source or target is null
   *
   * @see java.lang.String#equalsIgnoreCase(Sting)
   * @see java.lang.String#indexOf(Sting,int)
   * @see java.lang.String#regionMatches(boolean,int,String,int,int)
   */
  public static int indexOfIgnoreCase(String source, String target, int start) {
    int iRet = -1;
    int begin = Math.max(0, start);

    if ((iRet = source.toUpperCase().indexOf(target.toUpperCase(), begin)) == -1) {
      iRet = source.toLowerCase().indexOf(target.toLowerCase(), begin);
    }

    return iRet;
  }

  /**
   * Perform a case insenditive search for a target <code>String</code> within a
   * source <code>String</code>.<br>
   *
   * Return the index of the first occurence of the target if it is found, or -1
   * if it is not found.<br>
   *
   * The case insensitive is implemented using
   * <code>java.lang.String.equalsIgnoreCase(String)</code> as model. First the
   * source and target are up cased, then
   * <code>java.lang.String.indexOf(Sting,int)</code> is performed. If the
   * result is greater than -1 it is returned. Otherwise, the source and target
   * are down cased, <code>java.lang.String.indexOf(Sting,int)</code> is again
   * performed, and the result is returned.<br>
   *
   * @param source The <code>String</code> to search
   * @param target The <code>Stirng</code> to search for
   *
   * @return The starting index if found or -1 if not found
   * @throws java.lang.NullPointerException If either source or target is null
   *
   * @see java.lang.String#equalsIgnoreCase(Sting)
   * @see java.lang.String#indexOf(Sting,int)
   * @see java.lang.String#regionMatches(boolean,int,String,int,int)
   */
  public static int indexOfIgnoreCase(String source, String target) {
    return indexOfIgnoreCase(source, target, 0);
  }

  /**
   * Tests if a source <code>String</code> starts with the specified prefix
   * <code>String</code> in a case insensitive manner.
   *
   * Return the index of the first occurence of the target if it is found, or -1
   * if it is not found.<br>
   *
   * The case insensitive is implemented using
   * <code>java.lang.String.equalsIgnoreCase(String)</code> as model. First the
   * source and prefix are up cased, then
   * <code>java.lang.String.startsWith(Sting)</code> is performed. If the result
   * is true it is returned. Otherwise, the source and prefix are down cased,
   * <code>java.lang.String.startsWith(Sting,int)</code> is again performed, and
   * the result is returned.<br>
   *
   * @param source The <code>String</code> to search
   * @param prefix The <code>Stirng</code> to search for
   *
   * @return true if the target begins with the prefix, otherwise false
   * @throws java.lang.NullPointerException If either source or prefix is null
   *
   * @see java.lang.String#equalsIgnoreCase(Sting)
   * @see java.lang.String#startWith(Sting)
   * @see java.lang.String#regionMatches(boolean,int,String,int,int)
   */
  public static boolean startsWithIgnoreCase(String source, String prefix) {
    return (source.toUpperCase().startsWith(prefix.toUpperCase())
            || source.toLowerCase().startsWith(prefix.toLowerCase()));
  }

}
