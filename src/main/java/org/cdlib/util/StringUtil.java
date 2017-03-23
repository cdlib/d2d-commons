package org.cdlib.util;

import java.util.StringTokenizer;

public class StringUtil {

  /**
   * not instantiable
   */
  private StringUtil() {
  }

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
  public static final String blanks(int len) {
    return blanks(len, ' ');
  }

  /**
   * blanks returns the number of the chars requested. The empty string is
   * returned if the number is <= 0
   */
  public static final String blanks(int len, char c) {
    if (len <= 0) {
      return new String();
    }

    StringBuffer sb = new StringBuffer(len);
    for (int i = 0; i < len; i++) {
      sb.append(c);
    }

    return sb.toString();
  }

  /**
   * squeeze the whitespace from a string
   *
   */
  public static final String squeeze(String s) {
    if (isEmpty(s)) {
      return "";
    }
    s = s.trim();
    StringTokenizer st = new StringTokenizer(s);
    StringBuffer sb = new StringBuffer(s.length());

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
    s = s.trim();
    StringTokenizer st = new StringTokenizer(s, toRemove);
    StringBuffer sb = new StringBuffer(s.length());

    while (st.hasMoreTokens()) {
      sb.append(st.nextToken());
    }

    return (sb.toString());
  }

  public static final String pad(int ival, int size, char padchar) {
    String str = new Integer(ival).toString();
    if (str.length() >= size) {
      return str;
    }
    StringBuffer sb = new StringBuffer(size);
    for (int i = 0; i < size - str.length(); i++) {
      sb.append(padchar);
    }
    sb.append(str);
    return sb.toString();
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
      return "";
    }

  }

  /**
   * left returns the left most characters of a string
   */
  public static final String left(String s, int l) {
    try {
      return s.substring(0, l);
    } catch (StringIndexOutOfBoundsException e) {
      return "";
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
   * backspaces the conventional backspace
   */
  public static String processBackSpace(String line) {
    return processBackSpace(processBackSpace(line, (char) 127), '\b');
  }

  /**
   * replace a substring value with another substring value replaces all
   * occurrences in the in string
   *
   * @param in - string to have value replace
   * @param from - current substring value in 'in'
   * @param to - replacement value for 'from'
   * @return - new string with replaced substrings
   */
  public static String replace(
          String in,
          String from,
          String to) {
    StringBuffer sbuf = new StringBuffer();
    int start = 0;
    int pos = 0;
    while (start < in.length()) {
      pos = in.indexOf(from, start);

      if (pos >= 0) {
        sbuf.append(in.substring(start, pos));
        sbuf.append(to);
        start = pos + from.length();
      } else {
        sbuf.append(in.substring(start));
        start = in.length();
      }
    }
    return sbuf.toString();
  }

  /**
   * stripTelnetSpecials removes special characters from a strings that are
   * TELNET control options.
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
   * Strip control characters
   */
  public static String stripControls(String line) {
    StringBuffer so = new StringBuffer(line.length());
    // For each character see if we include it or skip it
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      // Strip ascii control characters
      if (c >= 32) {
        so.append(c);
      }
    }
    return (so.toString());
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

}
