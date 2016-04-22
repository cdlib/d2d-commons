package org.cdlib.services.hparser;

import java.util.List;

/**
 * Parses a holdings string and provides methods to access information about the holdings derived from the String.
 * 
 * The HoldingsParser is the public API to the holdings parser utility.
 * To get an instance of a HoldingsParser, use the {@link Holdings}.getParser method.
 * 
 * Note on possible optimization: if performance turns out to be an issue we can compile match expressions
 * into Pattern objects as static variables. These patterns can be passed to the parsing routines instead 
 * of the matching expressions. This way the matching expressions are compiled into patterns only once.
 * 
 * @see Holdings
 * @author jferrie
 */
public interface HoldingsParser {

    /**
     * Gets the earliest year in the holdings range.
     * 
     * @return a String representing the earliest year identified in the holdings.
     * @throws HoldingsParserException
     */
    String getEarliestYear() throws HoldingsParserException;

    /**
     * Gets the latest year in the holdings range.
     * 
     * @return get the latest year in holdings range
     * @throws HoldingsParserException
     */
    String getLatestYear() throws HoldingsParserException;

    /**
     * Gets the number of years in the holdings range. 
     * This is the number of continuous years: it does not subtract gap years.
     * 
     * @return the number of continuous years in the holdings range.
     * @throws HoldingsParserException
     */
    int getYearRange() throws HoldingsParserException;

    /**
     * Gets all of the years within the holdings range for which there are holdings.
     * 
     * NOT TESTED.
     * 
     * @return a list of the years held derived from the holdings statement.
     * @throws HoldingsParserException
     */
    List<Integer> getYearsHeld() throws HoldingsParserException;

    /**
     * Gets a List<Integer> of the years not held within the holdings range.
     * 
     * NOT TESTED.
     * 
     * @return a list of gap years, missing from the range of years expressed in the holdings statement.
     * @throws HoldingsParserException
     */
    List<Integer> getMissingYears() throws HoldingsParserException;
}
