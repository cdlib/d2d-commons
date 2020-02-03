package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Excption class used to indicate an invalid parser state.
 *
 * @author <a href="mailto: shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcInvalidStateException.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $
 */
public class MarcInvalidStateException extends MarcException
 {
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcInvalidStateException.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcInvalidStateException.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.4 $";

	/**
	 * Private indicator used to assure CVS information is logged
	 * only once.
	 */
    private static boolean versionLogged = false;

    /*
     * Static initializer used to log cvs header info.
     */
    {
        if ( !versionLogged )
        {
            log.info(cvsHeader);
            versionLogged = true;
        }
    }

    //====================================================
    //       CONSTRUCTORS
    //====================================================
    /**
     * Instantiate a new MarcInvalidStateException exception with the supplied
     * text.
     *
     * @param comment - programmer supplied comment describing error
     */
    public MarcInvalidStateException(String comment)
    {
        super(comment);
    }

    /**
     * Instantiate a new MarcInvalidStateException exception with the default
     * text.
     */
    public MarcInvalidStateException()
    {
        super("invalid parser state");
    }

    /**
     * Instantiate a new MarcInvalidStateException exception with the supplied
     * text and a reference to the invoking object.
     *
     * @param obj - this value for calling object
     * @param comment - programmer supplied comment describing error
     */
    public MarcInvalidStateException(Object obj, String comment)
    {
        super( obj, comment);
    }
}
