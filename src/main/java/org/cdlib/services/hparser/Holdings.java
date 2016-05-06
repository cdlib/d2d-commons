package org.cdlib.services.hparser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is the entry point to the holding parser utilities. 
 * Instantiate a holdings parser using the getParser factory method.
 * 
 * @author jferrie
 */
public class Holdings {

    private static final int LBOUND_YEAR = 1800;
    
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
    /**
     * Substring representing rule for what can be on the left boundary
     * of an expression (other than year-to-current expressions).
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
    private static final String LBOUND = "(?<!vol\\.|\\Wv\\.|no\\.|nos\\.|\\-|/|\\$|\\d|\\w)";
    // following allows volumes - see Redmine #5552
    //private static final String LBOUND = "(?<!no\\.|nos\\.|\\-|/|\\$|\\d|\\w)";
    /**
     * Substring representing rule for what can be on the right boundary
     * of an expression (other than year-to-current expressions).
     * 
     * Examples:
     * A whitespace char,
     * ,
     * ;
     * :
     */
    private static final String RBOUND = "(?!\\d|\\-|/)";
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
     * 
     *
     */
    static final String SINGLE_YEAR = String.format("%s(%s)(?!\\d|/)", LBOUND, BASE_YEAR);
    /**
     * A doubled year expression.
     * 
     * example: 1994/1995
     */
    static final String DOUBLED_YEAR = String.format("%s(%s)/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND);
    /*
     * A doubled year expression where the right year has two digits.
     * 
     * Example: 1968/69
     */
    static final String DOUBLED_2D_YEAR = String.format("%s(%s)/(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    /**
     * Match expression for a range of years in a holdings statement with 
     * a beginning and an end, and no additional text (such as month/day information or other holdings data)
     * within the expression.
     * 
     * example: 1990-1995
     */
    static final String NORMAL_RANGE = String.format("%s(%s)-(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND);
    /**
     * Match expression when there is space after the hyphen.
     * These need to be recognized so that they can be closed up during preprocessing.
     */
    static final String NORMAL_RANGE_W_WHITESPACE = String.format("(%s%s-)(\\s)(%s%s)", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND);
    
    /**
     * Normal range with a 2-digit end year.
     * 
     * example: 1990-95
     */
    static final String NORMAL_2D_YEAR_RANGE = String.format("%s(%s)-(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    /**
     * Normal 2-digit range with a space after the hyphen.
     * These need to be caught and closed up during preprocessing.
     */
    static final String NORMAL_2D_YEAR_RANGE_W_WHITESPACE = String.format("(%s%s-)(\\s)(%s%s)", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
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
    static final String PAREN_RANGE = String.format("\\([^\\)]*%s(%s)-[^\\)]*%s(%s)\\)", LBOUND, BASE_YEAR, LBOUND, BASE_YEAR);
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
    static final String PAREN_RANGE_TO_CURRENT = String.format("\\([^\\)]*%s(%s)\\)\\-\\s(?!v\\.|n\\.|no\\.|vol\\.|ser\\.|\\d?1st|\\d?\\dth|\\d?2nd|\\d?3rd)", LBOUND, BASE_YEAR);
    /*
     * A range beginning and ending with two doubled year expressions.
     * 
     * example: 2005/2006-2009/2010
     */
    static final String DOUBLED_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    /*
     * A doubled year range in which the rightmost year has two digits.
     * 
     * example: 2005/2006-2002/10
     */
    static final String DOUBLED_2D_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    /*
     * A doubled year range in which the both doubles have two digit years on the right.
     * 
     * example: 2005/06-2009/10
     */
    static final String DOUBLED_BOTH_2D_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);

    /*
     * A range with a double year on the right.
     * 
     * example: 2006/2007-2010
     */
    static final String DOUBLED_RIGHT_YEAR_RANGE = String.format("%s(%s)\\-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    /*
     * A range with a doubled year on the right.
     * The rightmost year having two digits.
     * 
     * Example: 2000-2006/07
     */
    static final String DOUBLED_RIGHT_2D_YEAR_RANGE = String.format("%s(%s)\\-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    /*
     * A range with a double year on the left.
     * 
     * example: 2006-2010/2011
     */
    static final String DOUBLED_LEFT_YEAR_RANGE = String.format("%s(%s)/%s\\-(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    /*
     * A range with a double year on the left, in which the second year on the left
     * has two digits.
     * 
     * Example: 2009/10-2011
     */
    static final String DOUBLED_LEFT_2D_YEAR_RANGE = String.format("%s(%s)/%s\\-(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, BASE_YEAR, RBOUND);
    /*
     * A range doubled on both sides in which the left double has a two-digit year.
     * 
     * Example: 1990/96-1997/2002
     */
    static final String DOUBLED_BOTH_LEFT_2D_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, TWO_DIGIT_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    /*
     * A range doubled on both sides in which the right double has a two-digit year.
     * 
     * Example: 1981/1982-1999/00
     */
    static final String DOUBLED_BOTH_RIGHT_2D_YEAR_RANGE = String.format("%s(%s)/%s-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    /**
     * An "extended" range, that is, a triple using hyphens.
     * 
     * Example:
     * 
     * 1960-1962-1964
     */
    static final String EXT_RANGE = String.format("%s(%s)\\-%s\\-(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    /**
     * Extended range with double on the left.
     * 
     * Example:
     * 
     * 1958/1959-1960-1964
     */
    static final String DOUBLED_LEFT_EXT_RANGE = String.format("%s(%s)/%s\\-%s\\-(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    /**
     * Extended range with double on the right.
     * 
     * Example:
     * 
     * 1959-1960-1962/1963
     */
    static final String DOUBLED_RIGHT_EXT_RANGE = String.format("%s(%s)\\-%s\\-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, BASE_YEAR, RBOUND);
    /**
     * Extended range with double on the right with a two-digit year.
     * 
     * Example:
     * 
     * 1959-1960-1962/63
     */
    static final String DOUBLED_RIGHT_2D_EXT_RANGE = String.format("%s(%s)\\-%s\\-%s/(%s)%s", LBOUND, BASE_YEAR, BASE_YEAR, BASE_YEAR, TWO_DIGIT_YEAR, RBOUND);
    /**
     * A range ending in the current year.
     * 
     * Match expression for a range of years ending in the current year.
     *
     */
    static final String NORMAL_YEAR_TO_CURRENT = String.format("%s(%s)\\-%s", LBOUND, BASE_YEAR, YTC_RBOUND);
    /**
     * A range beginning with a doubled year and ending with the current year.
     * 
     * Matches a range of years beginning with a doubled year and ending in the current year.
     * 
     * Example: 2005/2006-
     */
    static final String DOUBLED_YEAR_TO_CURRENT = String.format("%s(%s)/%s\\-%s", LBOUND, BASE_YEAR, BASE_YEAR, YTC_RBOUND);
    /**
     * A doubled year-to-current expression in parentheses.
     * 
     * example: (1994/1995)-
     */
    static final String DOUBLED_PAREN_TO_CURRENT = String.format("\\(%s(%s)/(%s)%s\\)\\-%s", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND, YTC_RBOUND);
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
    /*
     * A range of volume or date info with no spaces
     * Has a single rather than a doubled year expression
     * 
     * examples: 
     * v.1(1978)-v.4(1982)
     * Vols.1(1981)-v.26:no.1(2006)
     */
    static final String RANGE_WITH_ITEM_INFO = String.format("%s\\S+\\((%s)\\)-\\S+\\((%s)\\)%s", LBOUND, BASE_YEAR, BASE_YEAR, RBOUND);
    
    /**
     * A volume or number followed by whitespace, then by digits.
     * These need to be closed up in the holdings string before looking for expressions.
     */
    static final String VOL_OR_N_W_WHITESPACE = "(no\\.|nos\\.|v\\.|vol\\.)(\\s+)([\\d+])";
    
    
    static final String[] STOP_WORDS = {"INDEX"};

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
