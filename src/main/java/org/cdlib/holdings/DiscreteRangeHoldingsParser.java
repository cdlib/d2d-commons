package org.cdlib.holdings;

import static org.cdlib.holdings.Holdings.DOUBLED_2D_YEAR_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_2D_YEAR_TO_CURRENT_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_ALL_SHORT_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_BOTH_2D_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_BOTH_LEFT_2D_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_BOTH_RIGHT_2D_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_LEFT_2D_SHORT_RIGHT_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_LEFT_2D_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_LEFT_EXT_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_LEFT_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_PAREN_TO_CURRENT_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_RIGHT_2D_EXT_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_RIGHT_2D_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_RIGHT_EXT_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_RIGHT_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_YEAR_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.DOUBLED_YEAR_TO_CURRENT_PAT;
import static org.cdlib.holdings.Holdings.EXT_RANGE_PAT;
import static org.cdlib.holdings.Holdings.NORMAL_2D_YEAR_RANGE_PAT;
import static org.cdlib.holdings.Holdings.NORMAL_2D_YEAR_RANGE_W_WHITESPACE_PAT;
import static org.cdlib.holdings.Holdings.NORMAL_RANGE_PAT;
import static org.cdlib.holdings.Holdings.NORMAL_RANGE_W_WHITESPACE_PAT;
import static org.cdlib.holdings.Holdings.NORMAL_YEAR_TO_CURRENT_PAT;
import static org.cdlib.holdings.Holdings.PAREN_RANGE_PAT;
import static org.cdlib.holdings.Holdings.PAREN_RANGE_TO_CURRENT_PAT;
import static org.cdlib.holdings.Holdings.PAREN_SINGLE_YEAR_PAT;
import static org.cdlib.holdings.Holdings.SINGLE_YEAR_PAT;
import static org.cdlib.holdings.Holdings.STOP_WORDS;
import static org.cdlib.holdings.Holdings.TWO_COLON_RANGE_PAT;
import static org.cdlib.holdings.Holdings.TWO_PAREN_RANGE_PAT;
import static org.cdlib.holdings.Holdings.VOL_OR_N_W_WHITESPACE_PAT;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Implementation of HoldingsParser that identifies discrete ranges of holdings years. This enables
 * the parser to derive a list of years held and a list of years missing.
 *
 * Do not instantiate this class: use the {@link Holdings} getParser method to get an instance of a
 * {@link HoldingsParser}.
 *
 * @see HoldingsParser
 * @author jferrie
 * @since 1.0
 */
public class DiscreteRangeHoldingsParser implements HoldingsParser {

  private final List<Integer> yearsHeld = new ArrayList<>();
  private static final Logger logger = LoggerFactory.getLogger(DiscreteRangeHoldingsParser.class);
  private final String holdings;

  /**
   * Constructor parses the holdings strings on instantiation. For public instantiation use
   * Holdings.getParser method.
   *
   * The instance is immutable.
   *
   * @param holdings
   */
  DiscreteRangeHoldingsParser(String holdings) {
    this.holdings = prepare(holdings);
    parse();
  }

  /**
   * Pre-process holdings string to make it easier to parse.
   *
   * @param holdings
   * @return
   */
  static String prepare(String holdings) {
    logger.debug("prepareString is pre-processing " + holdings);
    // pad the holdings to make it easier for the regular
    // expressions: they don't need to account for the range expression
    // at the beginning or end of the line
    holdings = " " + holdings + " ";
    holdings = closeSpaces(holdings);
    holdings = truncateAddenda(holdings);
    holdings = holdings.replaceAll("\\d\\d\\?{1,2}-?", "");
    logger.debug("prepareString returned " + holdings);
    return holdings;
  }

  static private String closeSpaces(String holdings) {
    // close up expressions like No. 1111 -- see Redmine #5539
    // this works using back references to two capture groups in the match expression
    // the middle capture group (\\s+) is not included in the replace expression, removing the
    // whitespace
    holdings = VOL_OR_N_W_WHITESPACE_PAT.matcher(holdings).replaceAll("$1$3");
    // close up expression like (1998- 2009) -- see Planio #8462
    holdings = NORMAL_RANGE_W_WHITESPACE_PAT.matcher(holdings).replaceAll("$1$3");
    // close up expression like (1998- 09) -- see Planio #8462
    holdings = NORMAL_2D_YEAR_RANGE_W_WHITESPACE_PAT.matcher(holdings).replaceAll("$1$3");
    return holdings;
  }

  static private String truncateAddenda(String holdings) {
    // remove the end of string if it does not express holdings of issues
    for (String s : STOP_WORDS) {
      String upperS = s.toUpperCase();
      String upperHoldings = holdings.toUpperCase();
      int pos = upperHoldings.indexOf(upperS);
      if (pos >= 0) {
        holdings = holdings.substring(0, pos);
      }
    }
    return holdings;
  }

  private static final Pattern[] RANGES = {
      NORMAL_RANGE_PAT,
      PAREN_RANGE_PAT,
      TWO_PAREN_RANGE_PAT,
      EXT_RANGE_PAT,
      TWO_COLON_RANGE_PAT,
      DOUBLED_LEFT_EXT_RANGE_PAT,
      DOUBLED_RIGHT_EXT_RANGE_PAT,
      DOUBLED_YEAR_RANGE_PAT,
      DOUBLED_RIGHT_YEAR_RANGE_PAT,
      DOUBLED_LEFT_YEAR_RANGE_PAT,
      DOUBLED_YEAR_PAT,
      DOUBLED_RIGHT_2D_EXT_RANGE_PAT,
      DOUBLED_2D_YEAR_PAT,
      DOUBLED_RIGHT_2D_YEAR_RANGE_PAT,
      DOUBLED_LEFT_2D_YEAR_RANGE_PAT,
      DOUBLED_BOTH_2D_YEAR_RANGE_PAT,
      DOUBLED_BOTH_LEFT_2D_YEAR_RANGE_PAT,
      DOUBLED_BOTH_RIGHT_2D_YEAR_RANGE_PAT,
      NORMAL_2D_YEAR_RANGE_PAT,
      DOUBLED_ALL_SHORT_RANGE_PAT,
      DOUBLED_LEFT_2D_SHORT_RIGHT_PAT
  };

  private static final Pattern[] TO_CURRENT_RANGES = {NORMAL_YEAR_TO_CURRENT_PAT,
      DOUBLED_YEAR_TO_CURRENT_PAT,
      DOUBLED_2D_YEAR_TO_CURRENT_PAT,
      PAREN_RANGE_TO_CURRENT_PAT,
      DOUBLED_PAREN_TO_CURRENT_PAT
  };

  private static final Pattern[] SINGLE_YEARS = {PAREN_SINGLE_YEAR_PAT,
      SINGLE_YEAR_PAT
  };

  private void parse() {
    String lHoldings = holdings;

    // parse these first to avoid false to-current expressions created
    // by removing expressions following hyphens
    lHoldings = extractToCurrentRanges(lHoldings);
    lHoldings = extractRanges(lHoldings);
    extractSingleYears(lHoldings);
  }

  private String extractRanges(String lHoldings) {
    for (Pattern pattern : RANGES) {
      Matcher matcher = pattern.matcher(lHoldings);
      yearsHeld.addAll(getYearRanges(matcher, lHoldings));
      // after adding the matches strip all of the expressions from the holdings
      lHoldings = matcher.replaceAll(" ");
      logger.debug(" After replace " + "of " + pattern + " holdings are: " + lHoldings);
    }
    return lHoldings;
  }

  private String extractToCurrentRanges(String lHoldings) {
    for (Pattern pattern : TO_CURRENT_RANGES) {
      Matcher matcher = pattern.matcher(lHoldings);
      yearsHeld.addAll(getToCurrentYearRanges(matcher, lHoldings));
      lHoldings = matcher.replaceAll(" ");
    }
    return lHoldings;
  }

  private String extractSingleYears(String lHoldings) {
    for (Pattern pattern : SINGLE_YEARS) {
      Matcher matcher = pattern.matcher(lHoldings);
      yearsHeld.addAll(getSingleYears(matcher, lHoldings));
      lHoldings = matcher.replaceAll(" ");
    }
    return lHoldings;
  }

  List<Integer> getYears() {
    return yearsHeld;
  }

  @Override
  public List<Integer> getYearsHeld() throws HoldingsParserException {
    Collections.sort(yearsHeld);
    return removeDuplicateYears(yearsHeld);
  }

  @Override
  public String getEarliestYear() throws HoldingsParserException {
    if (yearsHeld.isEmpty()) {
      return "";
    } else {
      return Collections.min(yearsHeld).toString();
    }
  }

  @Override
  public String getLatestYear() throws HoldingsParserException {
    if (yearsHeld.isEmpty()) {
      return "";
    } else {
      return Collections.max(yearsHeld).toString();
    }
  }

  @Override
  public int getYearRange() throws HoldingsParserException {
    if (yearsHeld.isEmpty()) {
      return 0;
    } else {
      int earliest = Collections.min(yearsHeld);
      int latest = Collections.max(yearsHeld);
      return (latest - earliest) + 1;
    }
  }

  @Override
  public List<Integer> getMissingYears() throws HoldingsParserException {
    List<Integer> missingYears = new ArrayList<Integer>();
    int start = Collections.min(yearsHeld);
    int end = Collections.max(yearsHeld);
    for (int i = start; i <= end; i++) {
      if (!(yearsHeld.contains(i))) {
        missingYears.add(i);
      }
    }
    return missingYears;
  }

  /**
   * Checks for expressions that express a range of years with a specific end year (not the current
   * year). Returns a list of all years found in all of the ranges.
   *
   * @param matchExp A regular expression matches a string that expresses a range of years.
   * @return a list of years based on all year ranges that match the matchExp regular expression.
   */
  List<Integer> getYearRanges(Matcher matcher, String lHoldings) {
    ArrayList<Integer> list = new ArrayList<>();
    int nCaptures = matcher.groupCount();
    if (nCaptures < 2 || nCaptures > 3) {
      logger.error("Error in addYearRanges. Incorrect matching expression. Range expression must have at least two subgroups. It has " + matcher.groupCount());
      throw new IllegalArgumentException("Range expression must have two to three capture groups. It has " + matcher.groupCount());
    }
    while (matcher.find()) {
      logger.debug("Matcher " + matcher + " Matched on " + lHoldings.substring(matcher.start(), matcher.end()));
      String beginningYearStr = matcher.group(1);
      String endingYearStr = null;
      String penultYearStr = null;
      if (nCaptures == 2) {
        endingYearStr = matcher.group(2);
        penultYearStr = beginningYearStr;
      } else {
        endingYearStr = matcher.group(3);
        penultYearStr = matcher.group(2);
      }
      logger.debug("initial beginningYear: " + beginningYearStr + " initial penult = " + penultYearStr + " initial endingYear " + endingYearStr);
      Integer beginningYear = Integer.parseInt(beginningYearStr);
      Integer endingYear = Integer.parseInt(endingYearStr);
      if (penultYearStr.length() < 3) {
        penultYearStr = String.valueOf(toFourDigitYear(beginningYearStr, penultYearStr));
      }
      // turn any two-digit year on the right side of a range
      // to a four-digit year
      if (endingYearStr.length() < 3) {
        endingYear = toFourDigitYear(penultYearStr, endingYearStr);
      }
      logger.debug("Determined ending year in range: " + endingYearStr);
      if (Holdings.yearInRange(beginningYear) && (Holdings.yearInRange(endingYear))) {
        List<Integer> expYears = getYearsInRange(beginningYear, endingYear);
        if (expYears.isEmpty()) {
          logger.debug("Returned empty year list.");
        } else {
          list.addAll(getYearsInRange(beginningYear, endingYear));
        }
      } else {
        String warning = String.format("Excluded range %d or %d from parse of holdings %s because one was out of normal range.", beginningYear, endingYear, lHoldings);
        logger.debug(warning);
      }
    }
    logger.debug(String.format("addYearRanges found %s", list.toString()));
    return list;
  }

  /**
   * Parses the holdings string for single year expressions. Returns a List of all of the matching
   * years found in the holdings. Excludes years that are not in expected range.
   *
   * @param exp regex expression to match on
   * @return
   */
  List<Integer> getSingleYears(Matcher matcher, String lHoldings) {

    ArrayList<Integer> list = new ArrayList<Integer>();

    // This should not happen but the following code depends on it, so let's check
    if (matcher.groupCount() < 1) {
      logger.error("Error in addYearRanges. Incorrect matching expression. Range expression must have at least two subgroups. It has " + matcher.groupCount());
      throw new IllegalArgumentException("Range expression must have at least one subgroup. It has " + matcher.groupCount());
    }
    while (matcher.find()) {
      String found = matcher.group(1);
      logger.debug("Matcher: " + matcher + "Matched on " + lHoldings.substring(matcher.start(), matcher.end()));
      int foundInt = Integer.parseInt(found);
      if (Holdings.yearInRange(foundInt)) {
        list.add(foundInt);
      } else {
        String warning = String.format("Excluded year %d from parse of holdings %s because it was out of normal range.",
            foundInt, lHoldings);
        logger.debug(warning);
      }
    }
    logger.debug(String.format("addSingleYears found %s", list.toString()));
    return list;
  }

  /**
   * Parses the holdings string for range expressions ending in the current year.
   *
   * @param exp regex expression to match on
   * @return a List<Integer> of the years in all of the range expressions found
   *
   */
  List<Integer> getToCurrentYearRanges(Matcher matcher, String lHoldings) {
    ArrayList<Integer> list = new ArrayList<Integer>();
    while (matcher.find()) {
      logger.debug("Matcher " + matcher + " Matched on " + lHoldings.substring(matcher.start(), matcher.end()));
      int y1 = Integer.parseInt(matcher.group(1));
      int y2 = Integer.parseInt(Holdings.getCurrentHoldingsYear());
      if (Holdings.yearInRange(y1) && (Holdings.yearInRange(y2))) {
        list.addAll(getYearsInRange(y1, y2));
      } else {
        String warning = String.format("Excluded range %d or %d from parse of holdings %s because one was out of normal range.", y1, y2, lHoldings);
        logger.debug(warning);
      }
    }
    logger.debug(String.format("addToCurrentYearRanges found %s", list.toString()));
    return list;
  }

  /*
   * Returns a list of all years between start and end inclusive.
   * 
   * @param start the first year in the range
   * 
   * @parm end the last year in the range
   */
  List<Integer> getYearsInRange(int start, int end) {
    ArrayList<Integer> list = new ArrayList<>();
    if (end < start) {
      String rangeExp = String.valueOf(start) + "-" + String.valueOf(end);
      logger.warn(String.format("Range %s returned zero results because end of range precedes beginning of range.", rangeExp));
    } else {
      for (int i = start; i <= end; i++) {
        list.add(i);
      }
    }
    return list;
  }

  /*
   * Turns a two digit year to a four digit year by prepending the first two chars of the earlier year
   * to the later year.
   * 
   * For example, for the expression 1994/96 it prepends 19 to 96 so that the last year becomes 1996.
   * 
   * This works because two-digit years are only allowed when they have a four-digit year to the left,
   * for example 1996/98.
   * 
   * If the second year ends up being less than the first year, there may be an occurence of something
   * like 1999/01. In this case we attempt to fix it by adding 100 to the second year after prepending
   * 19 to get [1999, 2001]
   */

  static Integer toFourDigitYear(String firstYearString, String lastYearString) {

    if (firstYearString.length() != 4) {
      throw new IllegalArgumentException("First argument must be a four-digit integer.");
    }
    if (lastYearString.length() > 2) {
      throw new IllegalArgumentException("Second argument must be a length of one or two.");
    }

    // Replace the last x digits of the first year with the last year to get a candidate return value
    String left = firstYearString.substring(0, 4 - lastYearString.length());
    String fourDLastYearString = left + lastYearString;
    if (Integer.parseInt(fourDLastYearString) < Integer.parseInt(firstYearString)) {
      int centuryAdjust = lastYearString.length() == 1 ? 10 : 100;
      Integer fourDLastYear = Integer.parseInt(fourDLastYearString) + centuryAdjust;
      fourDLastYearString = fourDLastYear.toString();
      logger.debug("Increased year by 100 in converting 2-digit year.\nOriginal values are "
          + firstYearString + " and " + lastYearString
          + ". Read as " + firstYearString + " and " + fourDLastYearString + ".");
    }
    return Integer.parseInt(fourDLastYearString);
  }

  /*
   * Returns an array with only unique years (removing any duplicates).
   */
  List<Integer> removeDuplicateYears(List<Integer> fullList) {
    List<Integer> uniqueYearList = new ArrayList<>();
    for (int year : fullList) {
      if (!(uniqueYearList.contains(year))) {
        uniqueYearList.add(year);
      }
    }
    return uniqueYearList;
  }

}
