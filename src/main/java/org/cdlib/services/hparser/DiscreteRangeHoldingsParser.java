package org.cdlib.services.hparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.cdlib.services.hparser.Holdings.*;

import org.apache.log4j.Logger;

/**
 * Implementation of HoldingsParser that identifies discrete ranges of holdings
 * years. This enables the parser to derive a list of years held and a list of
 * years missing.
 *
 * Do not instantiate this class: use the {@link Holdings} getParser method to
 * get an instance of a {@link HoldingsParser}.
 *
 * @see HoldingsParser
 * @author jferrie
 * @since 1.0
 */
public class DiscreteRangeHoldingsParser implements HoldingsParser {

  private final List<Integer> yearsHeld = new ArrayList<>();
  private static final Logger logger = Logger.getLogger(DiscreteRangeHoldingsParser.class);
  private final String holdings;

  /**
   * Constructor parses the holdings strings on instantiation. Do not
   * instantiate outside of package. Use Holdings.getParser method.
   *
   * The instance is immutable.
   *
   * @param holdings
   */
  DiscreteRangeHoldingsParser(String holdings) {
    this.holdings = prepareString(holdings);
    parse();
  }

  /**
   * Pre-process holdings string to make it easier to parse.
   *
   * @param holdings
   * @return
   */
  static String prepareString(String holdings) {
    logger.debug("prepareString is pre-processing " + holdings);
    holdings = closeSpaces(holdings);
    holdings = truncateAddenda(holdings);
    logger.debug("prepareString returned " + holdings);
    return holdings;
  }

  static private String closeSpaces(String holdings) {
    // pad the holdings to make it easier for the regular
    // expressions: they don't need to account for the range expression
    // at the beginning or end of the line
    holdings = " " + holdings + " ";

    // close up expressions like No. 1111 -- see Redmine #5539
    String matchExp = VOL_OR_N_W_WHITESPACE;
    // this works using back references to two capture groups in the match expression
    // the middle capture group (\\s+) is not included in the replace expression, removing the whitespace
    holdings = Pattern.compile(matchExp, Pattern.CASE_INSENSITIVE).matcher(holdings).replaceAll("$1$3");

    // close up expression like (1998- 2009) -- see Planio #8462
    matchExp = NORMAL_RANGE_W_WHITESPACE;
    holdings = Pattern.compile(matchExp, Pattern.CASE_INSENSITIVE).matcher(holdings).replaceAll("$1$3");

    // close up expression like (1998- 09) -- see Planio #8462
    matchExp = NORMAL_2D_YEAR_RANGE_W_WHITESPACE;
    holdings = Pattern.compile(matchExp, Pattern.CASE_INSENSITIVE).matcher(holdings).replaceAll("$1$3");
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

  /*
   * Examine different types of expressions in the holdings string
   * From each derive a list of years
   * Add the years to the years instance variable
   * Sort the results
   * 
   */
  private void parse() {
    List<Integer> years = yearsHeld;
    years.addAll(getYearRanges(NORMAL_RANGE));
    years.addAll(getYearRanges(NORMAL_2D_YEAR_RANGE));
    years.addAll(getYearRanges(DOUBLED_YEAR_RANGE));
    years.addAll(getYearRanges(DOUBLED_RIGHT_YEAR_RANGE));
    years.addAll(getYearRanges(DOUBLED_LEFT_YEAR_RANGE));
    years.addAll(getYearRanges(DOUBLED_YEAR));
    years.addAll(getYearRanges(RANGE_WITH_ITEM_INFO));
    years.addAll(getYearRanges(PAREN_RANGE));
    years.addAll(getYearRanges(EXT_RANGE));
    years.addAll(getYearRanges(DOUBLED_LEFT_EXT_RANGE));
    years.addAll(getYearRanges(DOUBLED_RIGHT_EXT_RANGE));
    years.addAll(getYearRanges(DOUBLED_RIGHT_2D_EXT_RANGE));
    years.addAll(getYearRanges(DOUBLED_2D_YEAR));
    years.addAll(getYearRanges(DOUBLED_RIGHT_2D_YEAR_RANGE));
    years.addAll(getYearRanges(DOUBLED_LEFT_2D_YEAR_RANGE));
    years.addAll(getYearRanges(DOUBLED_BOTH_2D_YEAR_RANGE));
    years.addAll(getYearRanges(DOUBLED_BOTH_LEFT_2D_YEAR_RANGE));
    years.addAll(getYearRanges(DOUBLED_BOTH_RIGHT_2D_YEAR_RANGE));
    years.addAll(getSingleYears(SINGLE_YEAR));
    years.addAll(getToCurrentYearRanges(NORMAL_YEAR_TO_CURRENT));
    years.addAll(getToCurrentYearRanges(DOUBLED_YEAR_TO_CURRENT));
    years.addAll(getToCurrentYearRanges(DOUBLED_2D_YEAR_TO_CURRENT));
    years.addAll(getToCurrentYearRanges(PAREN_RANGE_TO_CURRENT));
    years.addAll(getToCurrentYearRanges(DOUBLED_PAREN_TO_CURRENT));
    if (!years.isEmpty()) {
      years = removeDuplicateYears(years);
      Collections.sort(years);
    }
  }

  List<Integer> getYears() {
    return yearsHeld;
  }

  @Override
  public List<Integer> getYearsHeld() throws HoldingsParserException {
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
   * Checks for expressions that express a range of years with a specific end
   * year (not the current year). Returns a list of all years found in all of
   * the ranges.
   *
   * @param matchExp A regular expression matches a string that expresses a
   * range of years.
   * @return a list of years based on all year ranges that match the matchExp
   * regular expression.
   */
  List<Integer> getYearRanges(String matchExp) {
    logger.debug(String.format("\nStarting HoldingsParser addYearRanges: match exp=%s, holdings=%s", matchExp, holdings));
    Pattern pattern = Pattern.compile(matchExp, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(holdings);
    ArrayList<Integer> list = new ArrayList<>();

    // This should not happen but the following code depends on it, so let's check
    if (matcher.groupCount() < 2) {
      logger.error("Error in addYearRanges. Incorrect matching expression. Range expression must have at least two subgroups. It has " + matcher.groupCount());
      throw new IllegalArgumentException("Range expression must have at least two subgroups. It has " + matcher.groupCount());
    }
    int i = 0;
    while (matcher.find()) {
      logger.debug(i++ + ": Matched on " + holdings.substring(matcher.start(), matcher.end()));
      int y1 = Integer.parseInt(matcher.group(1));
      int y2 = Integer.parseInt(matcher.group(2));
      // turn any two-digit year on the right side of a range
      // to a four-digit year
      if (y2 < 100) {
        y2 = twoDigitToFourDigitYear(y1, y2);
      }
      if (Holdings.yearInRange(y1) && (Holdings.yearInRange(y2))) {
        List<Integer> expYears = getYearsInRange(y1, y2);
        if (expYears.isEmpty()) {
          logger.debug("Returned empty year list.");
        } else {
          list.addAll(getYearsInRange(y1, y2));
        }
      } else {
        String warning = String.format("Excluded range %d or %d from parse of holdings %s because one was out of normal range.", y1, y2, holdings);
        logger.debug(warning);
      }
    }
    logger.debug(String.format("addYearRanges found %s", list.toString()));
    return list;
  }

  /**
   * Parses the holdings string for single year expressions. Returns a List of
   * all of the matching years found in the holdings. Excludes years that are
   * not in expected range.
   *
   * TODO: add more validation of expressions and years?
   *
   * @param exp regex expression to match on
   * @return
   */
  List<Integer> getSingleYears(String exp) {
    logger.debug(String.format("\nStarting HoldingsParser addSingleYears: match exp=%s, holdings=%s", exp, holdings));
    Pattern pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(holdings);
    ArrayList<Integer> list = new ArrayList<Integer>();

    // This should not happen but the following code depends on it, so let's check
    if (matcher.groupCount() < 1) {
      logger.error("Error in addYearRanges. Incorrect matching expression. Range expression must have at least two subgroups. It has " + matcher.groupCount());
      throw new IllegalArgumentException("Range expression must have at least one subgroup. It has " + matcher.groupCount());
    }
    int i = 0;
    while (matcher.find()) {
      String found = matcher.group(1);
      logger.debug(i++ + ": Matched on " + holdings.substring(matcher.start(), matcher.end()));
      int foundInt = Integer.parseInt(found);
      if (Holdings.yearInRange(foundInt)) {
        list.add(foundInt);
      } else {
        String warning = String.format("Excluded year %d from parse of holdings %s because it was out of normal range.",
                foundInt, holdings);
        logger.debug(warning);
      }
    }
    logger.debug(String.format("addSingleYears found %s", list.toString()));
    return list;
  }

  /**
   * Parses the holdings string for range expressions ending in the current
   * year.
   *
   * @param exp regex expression to match on
   * @return a List<Integer> of the years in all of the range expressions found
   *
   */
  List<Integer> getToCurrentYearRanges(String exp) {
    logger.debug(String.format("\nStarting HoldingsParser addToCurrentYearRanges: match exp=%s, holdings=%s", exp, holdings));
    Pattern pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(holdings);
    ArrayList<Integer> list = new ArrayList<Integer>();
    int i = 0;
    while (matcher.find()) {
      logger.debug(i++ + ": Matched on " + holdings.substring(matcher.start(), matcher.end()));
      int y1 = Integer.parseInt(matcher.group(1));
      int y2 = Integer.parseInt(Holdings.getCurrentHoldingsYear());
      if (Holdings.yearInRange(y1) && (Holdings.yearInRange(y2))) {
        list.addAll(getYearsInRange(y1, y2));
      } else {
        String warning = String.format("Excluded range %d or %d from parse of holdings %s because one was out of normal range.", y1, y2, holdings);
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
   * @parm end the last year in the range
   */
  List<Integer> getYearsInRange(int start, int end) {
    ArrayList<Integer> list = new ArrayList<>();
    if (end < start) {
      logger.info(String.format("Range for string %s returned zero results because end of range precedes beginning of range.", holdings));
    } else {
      for (int i = start; i <= end; i++) {
        list.add(i);
      }
    }
    return list;
  }

  /*
   * Turns a two digit year to a four digit year by prepending the first two 
   * chars of the earlier year to the later year.
   * 
   * For example, for the expression 1994/96 it prepends 19 to 96 so that the 
   * last year becomes 1996.
   * 
   * This works because two-digit years are only allowed when they have 
   * a four-digit year to the left, for example 1996/98.
   * 
   * If the second year ends up being less than the first year, there may be a rare 
   * occurence of something like 1999/01. In this case we attempt to fix it 
   * by adding 100 to the second year after prepending 19 to get [1999, 2001]
   */
  private Integer twoDigitToFourDigitYear(Integer firstYear, Integer lastYear) {
    String firstYearString = firstYear.toString();
    String lastYearString = lastYear.toString();
    if (lastYearString.length() > 2) {
      throw new IllegalArgumentException("Second argument must be a two-digit integer.");
    }
    // The toString method returns "1" when we want the string "01", so we will pad it with a zero
    // to get our two-digit year -- also possible to handle this with String.format method I think
    if (lastYearString.length() == 1) {
      lastYearString = "0" + lastYearString;
    }

    // Do the same for firstYearString
    if (firstYearString.length() == 1) {
      firstYearString = "0" + firstYearString;
    }

    // get the first two digits from the previous year
    // and prepend them to the last year
    String fourDLastYearString = firstYearString.substring(0, 2) + lastYearString;

    // if by some chance the last year is smaller than the first year
    // then it is probably something like 1999/01, so we adjust it up 100 years
    if (Integer.parseInt(fourDLastYearString) < Integer.parseInt(firstYearString)) {
      Integer fourDLastYear = Integer.parseInt(fourDLastYearString) + 100;
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

  /*
   * Returns List with all four-digit years.
   * 
   * NOTE: so far this is not needed as none of the methods produces a list containing 2-digit years.
   */
  private List<Integer> replaceTwoDigitYears(List<Integer> mixedList) {
    List<Integer> fourDigitYearList = new ArrayList<>();
    int previousYear = 0;
    // A two-digit year will always be on the right side of a range expression
    // so there logically must be a date that precedes it
    if (mixedList.get(0) < 100) {
      throw new IllegalStateException("First year in list cannot have two digits.");
    }
    for (int year : mixedList) {
      // if this is a two-digit year
      // replace it with four digit year
      // by prepending the first two digits from the previous year
      if (year < 100) {
        fourDigitYearList.add(twoDigitToFourDigitYear(previousYear, year));
      } else {
        fourDigitYearList.add(year);
      }
      previousYear = year;
    }
    return fourDigitYearList;
  }
}
