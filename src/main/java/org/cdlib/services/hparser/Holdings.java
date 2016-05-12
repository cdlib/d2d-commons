package org.cdlib.services.hparser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * This class is the entry point to the holding parser utilities. 
 * Instantiate a holdings parser using the getParser factory method.
 * 
 * @author jferrie
 */
public class Holdings {

    /**
     * The lowest integer that will be treated as a year.
     */
    private static final int LBOUND_YEAR = 1620;
    
    /**
     * Substring representing a 4-digit year referenced in subsequent match expressions.
     * Example:
     * 1956
     * 
     * Note that the year range must be validated, as this expression will match years outside 
     * the possible holdings range.
     * 
     */
    private static final String BASE_YEAR = "\\d{4}";
    
    /*
     * Base year expression for 2-digit years
     */
    private static final String TWO_DIGIT_YEAR = "\\d{2}";
    
    private static final String VOL_NO = "v\\.|n\\.|no\\.|vol\\.|ser\\.|\\d?1st|\\d?\\dth|\\d?2nd|\\d?3rd";
    
    /**
     * Substring representing rule for what can be on the left boundary
     * of the base year.
     * 
     * Examples:
     * Whitespace char or ","
     * 
     * but not:
     * 
     * $, - v. vol. no. No. Vol.
     * 
     * At the same time, it should match on:
     * 
     * Oct.1968, Nov.1968
     * 
     */
    static final String LBOUND = "(?<!vol\\.|\\Wv\\.|no\\.|nos\\.|\\-|/|\\$|\\d|\\w)";
    
    /**
     * Substring representing rule for what cannot be on the right boundary
     * of many expressions.
     * 
     * Examples of legal right boundaries:
     * A whitespace char,
     * ,
     * ;
     * :
     * )
     */
    private static final String RBOUND = "(?!\\d|\\-|/)";
    
    /**
     * For single years a trailing hyphen is permitted.
     * This allows the occasional 2003-FEB expression.
     * However, this means that the range expressions should be processed before the single year expressions to remove the 2003- .
     */
    private static final String RBOUND_SINGLE = "(?!\\d|/)";
    
    /**
     * Substring representing rule for what can be on the right boundary
     * of a year-to-current expression
     * 
     * Examples:
     * Whitespace char only
     */
    private static final String YTC_RBOUND = "(?=\\s|\\))";
    
    /**
     * Matches for a single year in a holdings statement.
     * 
     * The expression can begin with a comma, whitespace, or an opening parenthesis.
     * It can end with punctuation, whitespace, or a closing parenthesis. 
     * 
     * It cannot begin with $, - or /, or end in - or /. This excludes single years
     * that are part of a doubled year expression or a range. This is necessary so that the 
     * range can be validated as a whole before it is included.
     * 
     * For example, these are not matched:
     * 
     * -2000
     * /1850
     * $1899
     * 18999
     * 1899/
     *
     */
    static final String SINGLE_YEAR = String.format("%s(%s)%s", LBOUND, BASE_YEAR, RBOUND_SINGLE);
    static final Pattern SINGLE_YEAR_PAT = Pattern.compile(SINGLE_YEAR, Pattern.CASE_INSENSITIVE);
    
    static final String PAREN_SINGLE_YEAR = String.format("\\((%s)\\)", BASE_YEAR, RBOUND);
    static final Pattern PAREN_SINGLE_YEAR_PAT = Pattern.compile(PAREN_SINGLE_YEAR, Pattern.CASE_INSENSITIVE);
    
    /**
     * A doubled year expression.
     * 
     * example: 1994/1995
     */
    static final String DOUBLED_YEAR = String.format("%s(%s)/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern DOUBLED_YEAR_PAT = Pattern.compile(DOUBLED_YEAR, Pattern.CASE_INSENSITIVE);
    /*
     * A doubled year expression where the right year has two digits.
     * 
     * Example: 1968/69
     */
    static final String DOUBLED_2D_YEAR = String.format("%s(%s)/(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    static final Pattern DOUBLED_2D_YEAR_PAT = Pattern.compile(DOUBLED_2D_YEAR, Pattern.CASE_INSENSITIVE);
    /**
     * Match expression for a range of years in a holdings statement with 
     * a beginning and an end, and no additional text (such as month/day information or other holdings data)
     * within the expression.
     * 
     * example: 1990-1995
     */
    static final String NORMAL_RANGE = String.format("%s(%s)-(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern NORMAL_RANGE_PAT = Pattern.compile(NORMAL_RANGE, Pattern.CASE_INSENSITIVE);
    
    /**
     * Match expression when there is space after the hyphen.
     * These need to be recognized so that they can be closed up during preprocessing.
     */
    static final String NORMAL_RANGE_W_WHITESPACE = String.format("(%s%s-)(\\s)(%s%s)", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern NORMAL_RANGE_W_WHITESPACE_PAT = Pattern.compile(NORMAL_RANGE_W_WHITESPACE, Pattern.CASE_INSENSITIVE);
    
    /**
     * Normal range with a 2-digit end year.
     * 
     * example: 1990-95
     */
    static final String NORMAL_2D_YEAR_RANGE = String.format("%s(%s)-(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    static final Pattern NORMAL_2D_YEAR_RANGE_PAT = Pattern.compile(NORMAL_2D_YEAR_RANGE, Pattern.CASE_INSENSITIVE);
    
    /**
     * Normal 2-digit range with a space after the hyphen.
     * These need to be caught and closed up during preprocessing.
     */
    static final String NORMAL_2D_YEAR_RANGE_W_WHITESPACE = String.format("(%s%s-)(\\s)(%s%s)", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    static final Pattern NORMAL_2D_YEAR_RANGE_W_WHITESPACE_PAT = Pattern.compile(NORMAL_2D_YEAR_RANGE_W_WHITESPACE, Pattern.CASE_INSENSITIVE);
    
    /**
     * A more liberal range match expression permits more characters in the range,
     * as long as it is bound in parentheses.
     * 
     * Currently assumes that expressions in parentheses are never volumes or numbers, only years.
     * 
     * Examples:
     * 
     * (1910-June 1968)
     * 
     */
    static final String PAREN_RANGE = String.format("\\([^\\)]*%s(%s)-[^\\)\\d]*(%s)\\)", LBOUND, BASE_YEAR, BASE_YEAR);
    static final Pattern PAREN_RANGE_PAT = Pattern.compile(PAREN_RANGE, Pattern.CASE_INSENSITIVE);
    
   /**
    *  1(1928)-17(1972);
    */
    static final String TWO_PAREN_RANGE = String.format("(?:%s)?\\d*\\((%s)\\)-(?:%s)?\\d*\\((%s)\\)", VOL_NO, BASE_YEAR, VOL_NO, BASE_YEAR);
    static final Pattern TWO_PAREN_RANGE_PAT = Pattern.compile(TWO_PAREN_RANGE, Pattern.CASE_INSENSITIVE);
    
    /**
     * <1989:9:1-1990:3:1><1997:5:1-1998:3:15>
     */
    static final String TWO_COLON_RANGE = String.format("(%s):\\d+:\\d+-(%s):\\d+:\\d+", BASE_YEAR, BASE_YEAR);
    static final Pattern TWO_COLON_RANGE_PAT = Pattern.compile(TWO_COLON_RANGE, Pattern.CASE_INSENSITIVE);
    
    /*
     * Looks for a year enclosed in parens, followed by a hyphen.
     * 
     * Matches (2003)- 
     * 
     * However, looks ahead one space for number and volume expressions to exclude:
     * 
     * v19(2003)- v25(2004)
     * 
     * Also excludes 10th(1998)- 22nd(1999) and 10th(1998)- 12th(1999)
     */
    static final String PAREN_RANGE_TO_CURRENT = String.format("\\([^\\)]*%s(%s)\\)\\-(?:\\s|,|\\||;)(?!%s)", LBOUND, BASE_YEAR, VOL_NO);
    static final Pattern PAREN_RANGE_TO_CURRENT_PAT = Pattern.compile(PAREN_RANGE_TO_CURRENT, Pattern.CASE_INSENSITIVE);
    
    /*
     * A range beginning and ending with two doubled year expressions.
     * 
     * example: 2005/2006-2009/2010
     */
    static final String DOUBLED_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern DOUBLED_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_YEAR_RANGE, Pattern.CASE_INSENSITIVE);
    /*
     * A doubled year range in which the rightmost year has two digits.
     * 
     * example: 2005/2006-2002/10
     */
    static final String DOUBLED_2D_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    static final Pattern DOUBLED_2D_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_2D_YEAR_RANGE, Pattern.CASE_INSENSITIVE);
    
    /*
     * A doubled year range in which the both doubles have two digit years on the right.
     * 
     * example: 2005/06-2009/10
     */
    static final String DOUBLED_BOTH_2D_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    static final Pattern DOUBLED_BOTH_2D_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_BOTH_2D_YEAR_RANGE, Pattern.CASE_INSENSITIVE);
    
    /*
     * A range with a double year on the right.
     * 
     * example: 2000-2006/2007
     */
    static final String DOUBLED_RIGHT_YEAR_RANGE = String.format("%s(%s)\\-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern DOUBLED_RIGHT_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_RIGHT_YEAR_RANGE, Pattern.CASE_INSENSITIVE);
    
    /*
     * A range with a doubled year on the right.
     * The rightmost year having two digits.
     * 
     * Example: 2000-2006/07
     */
    static final String DOUBLED_RIGHT_2D_YEAR_RANGE = String.format("%s(%s)\\-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    static final Pattern DOUBLED_RIGHT_2D_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_RIGHT_2D_YEAR_RANGE, Pattern.CASE_INSENSITIVE);
    
    /*
     * A range with a double year on the left.
     * 
     * example: 2006-2010/2011
     */
    static final String DOUBLED_LEFT_YEAR_RANGE = String.format("%s(%s)/%s\\-(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern DOUBLED_LEFT_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_LEFT_YEAR_RANGE, Pattern.CASE_INSENSITIVE);
    
    /*
     * A range with a double year on the left, in which the second year on the left
     * has two digits.
     * 
     * Example: 2009/10-2011
     */
    static final String DOUBLED_LEFT_2D_YEAR_RANGE = String.format("%s(%s)/%s\\-(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, BASE_YEAR, RBOUND);
    static final Pattern DOUBLED_LEFT_2D_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_LEFT_2D_YEAR_RANGE, Pattern.CASE_INSENSITIVE);
    
    /*
     * A range doubled on both sides in which the left double has a two-digit year.
     * 
     * Example: 1990/96-1997/2002
     */
    static final String DOUBLED_BOTH_LEFT_2D_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern DOUBLED_BOTH_LEFT_2D_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_BOTH_LEFT_2D_YEAR_RANGE, Pattern.CASE_INSENSITIVE);

    /*
     * A range doubled on both sides in which the right double has a two-digit year.
     * 
     * Example: 1981/1982-1999/00
     */
    static final String DOUBLED_BOTH_RIGHT_2D_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    static final Pattern DOUBLED_BOTH_RIGHT_2D_YEAR_RANGE_PAT = Pattern.compile(DOUBLED_BOTH_RIGHT_2D_YEAR_RANGE, Pattern.CASE_INSENSITIVE);

    /**
     * An "extended" range, that is, a triple using hyphens.
     * 
     * Example:
     * 
     * 1960-1962-1964
     */
    static final String EXT_RANGE = String.format("%s(%s)\\-%s\\-(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern EXT_RANGE_PAT = Pattern.compile(EXT_RANGE, Pattern.CASE_INSENSITIVE);

    /**
     * Extended range with double on the left.
     * 
     * Example:
     * 
     * 1958/1959-1960-1964
     */
    static final String DOUBLED_LEFT_EXT_RANGE = String.format("%s(%s)/%s\\-%s\\-(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern DOUBLED_LEFT_EXT_RANGE_PAT = Pattern.compile(DOUBLED_LEFT_EXT_RANGE, Pattern.CASE_INSENSITIVE);

    /**
     * Extended range with double on the right.
     * 
     * Example:
     * 
     * 1959-1960-1962/1963
     */
    static final String DOUBLED_RIGHT_EXT_RANGE = String.format("%s(%s)\\-%s\\-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern DOUBLED_RIGHT_EXT_RANGE_PAT = Pattern.compile(DOUBLED_RIGHT_EXT_RANGE, Pattern.CASE_INSENSITIVE);
    
    /**
     * Extended range with double on the right with a two-digit year.
     * 
     * Example:
     * 
     * 1959-1960-1962/63
     */
    static final String DOUBLED_RIGHT_2D_EXT_RANGE = String.format("%s(%s)\\-%s\\-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    static final Pattern DOUBLED_RIGHT_2D_EXT_RANGE_PAT = Pattern.compile(DOUBLED_RIGHT_2D_EXT_RANGE, Pattern.CASE_INSENSITIVE);
    
    /**
     * A range ending in the current year.
     * 
     * Match expression for a range of years ending in the current year.
     *
     */
    static final String NORMAL_YEAR_TO_CURRENT = String.format("%s(%s)\\-%s", LBOUND, BASE_YEAR, YTC_RBOUND);
    static final Pattern NORMAL_YEAR_TO_CURRENT_PAT = Pattern.compile(NORMAL_YEAR_TO_CURRENT, Pattern.CASE_INSENSITIVE);
    
    /**
     * A range beginning with a doubled year and ending with the current year.
     * 
     * Matches a range of years beginning with a doubled year and ending in the current year.
     * 
     * Example: 2005/2006-
     */
    static final String DOUBLED_YEAR_TO_CURRENT = String.format("%s(%s)/%s\\-%s", LBOUND, BASE_YEAR, BASE_YEAR, YTC_RBOUND);
    static final Pattern DOUBLED_YEAR_TO_CURRENT_PAT = Pattern.compile(DOUBLED_YEAR_TO_CURRENT, Pattern.CASE_INSENSITIVE);
   
    /**
     * A doubled year-to-current expression in parentheses.
     * 
     * example: (1994/1995)-
     */
    static final String DOUBLED_PAREN_TO_CURRENT = String.format("\\(%s(%s)/(%s)%s\\)\\-%s", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND, YTC_RBOUND);
    static final Pattern DOUBLED_PAREN_TO_CURRENT_PAT = Pattern.compile(DOUBLED_PAREN_TO_CURRENT, Pattern.CASE_INSENSITIVE);
  
    /**
     * A range beginning with a doubled year and ending with the current year.
     * 
     * Matches a range of years beginning with a doubled year and ending in the current year.
     * 
     * Second year has two digits.
     * 
     * Example: 2005/06-
     */
    static final String DOUBLED_2D_YEAR_TO_CURRENT = String.format("%s(%s)/%s\\-%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, YTC_RBOUND);
    static final Pattern DOUBLED_2D_YEAR_TO_CURRENT_PAT = Pattern.compile(DOUBLED_2D_YEAR_TO_CURRENT, Pattern.CASE_INSENSITIVE);
  
    /*
     * A range of volume or date info with no spaces
     * Has a single rather than a doubled year expression
     * 
     * examples: 
     * v.1(1978)-v.4(1982)
     * Vols.1(1981)-v.26:no.1(2006)
     */
    static final String RANGE_WITH_ITEM_INFO = String.format("%s\\S+\\((%s)\\)-\\S+\\((%s)\\)%s", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND);
    static final Pattern RANGE_WITH_ITEM_INFO_PAT = Pattern.compile(RANGE_WITH_ITEM_INFO, Pattern.CASE_INSENSITIVE);
    
    /**
     * A volume or number followed by whitespace, then by digits.
     * These need to be closed up in the holdings string before looking for expressions.
     */
    static final String VOL_OR_N_W_WHITESPACE = "(no\\.|nos\\.|v\\.|vol\\.)(\\s+)([\\d+])";
    static final Pattern VOL_OR_N_W_WHITESPACE_PAT = Pattern.compile(VOL_OR_N_W_WHITESPACE, Pattern.CASE_INSENSITIVE);
    
    /**
     * The portion of the string following these words is truncated.
     * These words begin phrases that will include dates that do not correspond to actual holdings.
     * 
     * Note that missing will need to be used for removing years.
     */
    static final String[] STOP_WORDS = {"INDEX", "SUPP", "MISSING"};

    /*
     * Prevents instantiation of this class, which is intended to be static only.
     */
    private Holdings() {
    }

    /**
     * Gets a HoldingsParser instance used to analyze holdings strings.
     * 
     * @param holdings a PAPR project holdings string
     * @return an instance of a HoldingsParser
     */
    public static HoldingsParser getParser(String holdings) {
        return new DiscreteRangeHoldingsParser(holdings);
    }

    /**
     * Gets a HoldingsParser instance used to analyze holdings strings.
     * 
     * @param holdings a PAPR project holdings string
     * @return an instance of a HoldingsParser
     * @param discreteRanges -- deprecated parameter is ignored
     * @deprecated
     */
    public static HoldingsParser getParser(String holdings, boolean discreteRanges) {
        return new DiscreteRangeHoldingsParser(holdings);
    }

    /**
     * Calculates the current year plus one, 
     * which is the convention for determining the last year 
     * of a holdings range that continues to the present.
     * 
     * @return a String representing the current year for holdings.
     */
    protected static String getCurrentHoldingsYear() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        Date holdingsYear = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(holdingsYear);
    }

    /**
     * Test whether a year is within a valid range of years.
     * 
     * @param year the year to test
     * @return true if the year is in range, false otherwise
     */
    protected static boolean yearInRange(int year) {
        if (year >= LBOUND_YEAR && year <= Integer.parseInt(getCurrentHoldingsYear())) {
            return true;
        } else {
            return false;
        }
    }
}
