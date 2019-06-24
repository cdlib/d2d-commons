/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.cdlib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * 
 * @author jferrie
 */
public final class DateUtil {

  public static final String PIR_DATETIME = "yyyyMMddhhmmssSSS";
  public static final String CITATION_YEAR = "yyyy";

  private DateUtil() {};

  public static String formatRequestDate(Date dt) {
    SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.PIR_DATETIME);
    return formatter.format(dt);
  }

  public static String formatCitationDate(Date dt) {
    SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.CITATION_YEAR);
    return formatter.format(dt);
  }

  public static String formatCitationYear(Date dt) {
    SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.CITATION_YEAR);
    return formatter.format(dt);
  }

  /**
   * get the date in the format YYYYMMSSHHMMSSTTT
   */
  public static String getFormattedDate(Date d) {
    Calendar c = Calendar.getInstance();
    c.setTime(d);

    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    int second = c.get(Calendar.SECOND);
    int milli = c.get(Calendar.MILLISECOND);

    return new String("" + year + (month < 10 ? "0" : "") + month + (day < 10 ? "0" : "") + day
        + (hour < 10 ? "0" : "") + hour + (minute < 10 ? "0" : "") + minute
        + (second < 10 ? "0" : "") + second + (milli < 10 ? "00" : (milli < 100 ? "0" : ""))
        + milli);
  }

  /**
   * get the milliseconds between two YYYYMMDDHHMMSSTTT dates
   */
  public static long dateInterval(String dateStart, String dateEnd) {
    long longStart = milliDate(dateStart);
    long longEnd = milliDate(dateEnd);
    if (longStart == 0 || longEnd == 0)
      return 0;
    return longEnd - longStart;
  }

  /**
   * Convert a YYYYMMDDHHMMSSTTT date to milliseconds
   */
  public static long milliDate(String date) {
    Calendar cal = new GregorianCalendar();
    try {
      cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)),
          Integer.parseInt(date.substring(6, 8)), Integer.parseInt(date.substring(8, 10)),
          Integer.parseInt(date.substring(10, 12)), Integer.parseInt(date.substring(12, 14)));

      long milli = cal.getTimeInMillis() + Long.parseLong(date.substring(14));

      return milli;

    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * Takes a value of type JAN (case insensitive) or a value of type 01 for the month. Returns a
   * value of type 01.
   */
  public static String monthToValue(String in) {
    if (in == null) {
      return null;
    }
    String[] months =
        {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    String[] nums = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    int inx = 0;
    int limit = nums.length;
    for (inx = 0; inx < limit; inx++) {
      if (in.equals(nums[inx])) {
        return in;
      }
    }
    String date_mmm = in.toLowerCase();

    limit = months.length;
    for (inx = 0; inx < limit; inx++) {
      if (date_mmm.equals(months[inx])) {
        break;
      }
    }
    if (inx == months.length) {
      return null;
    } else {
      return nums[inx];
    }
  }

  /**
   * Finds something that looks like year in the string passed. The String passed in is supposed to
   * be like:
   * 
   * 2004 200410 20041015 Spring 2004 2004-10 (with various separator chars) 2004-10-15 (with
   * various separator chars)
   */
  public static String extractYear(String date) {
    date = date.trim();
    if (date.length() < 4) {
      return "";
    }
    StringTokenizer st = new StringTokenizer(date, " ,./-");
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (looksLikeBibYear(token))
        return token;
    }
    // if that doesn't work, try the beginning
    // 20041019
    String first4 = date.substring(0, 4);
    if (looksLikeBibYear(first4)) {
      return first4;
    }
    // if that doesn't work, try the end
    // Spring 2004
    String last4 = date.substring(date.length() - 4, date.length());
    if (looksLikeBibYear(last4)) {
      return last4;
    }
    return "";
  }

  public static final int GUTTENBERG_YEAR = 1454;
  private static final int LBOUND = GUTTENBERG_YEAR;
  private static final int UBOUND = Integer.valueOf(nextYear());

  /**
   * 
   * Determines whether a string looks like a year in the form YYYY.
   * 
   * the year must be later than a reasonable date for published material, as set in LBOUND.
   * 
   * It must not be later than the year following the current year, as set in UBOUND.
   * 
   * @param s a String that may or may not resemble a year
   * @return true if the String resembles a year according to the criteria
   */
  public static boolean looksLikeBibYear(String s) {
    if (s.length() == 4 && (StringUtil.isNumeric(s))) {
      if (Integer.valueOf(s) >= LBOUND && Integer.valueOf(s) <= UBOUND) {
        return true;
      }
    }
    return false;
  }
  
  public static String nextYear() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.YEAR, 1);
    return new SimpleDateFormat("yyyy").format(cal.getTime());
  }

  /**
   * get the date in the format YYYY/MM/SS HH:MM:SS.TTT
   */
  public static String getDisplayDate(Date d) {
    Calendar c = Calendar.getInstance();
    c.setTime(d);

    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    int second = c.get(Calendar.SECOND);
    int milli = c.get(Calendar.MILLISECOND);

    return new String("" + year + "/" + (month < 10 ? "0" : "") + month + "/"
        + (day < 10 ? "0" : "") + day + " " + (hour < 10 ? "0" : "") + hour + ":"
        + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second + "."
        + (milli < 10 ? "00" : (milli < 100 ? "0" : "")) + milli);
  }

}
