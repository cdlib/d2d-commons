package org.cdlib.holdings;

/**
 * Checked exception for exceptions thrown by the holdings parser classes.
 * 
 * This is used to wrap any internally caught checked exceptions that are to be handled by the HoldingsParser caller.
 * 
 * @author jferrie
 */
public class HoldingsParserException extends Exception {

    /**
     * Constructs an instance of <code>HoldingsParserException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     */
    public HoldingsParserException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>HoldingsParserException</code> that wraps another exception.
     * 
     * @param e the Exception to wrap
     */
    public HoldingsParserException(Exception e) {
        super(e);
    }
}
