package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Exception class used to indicate invalid parameters were passed to a
 * method or constructor.
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @version $Id: MarcParmException.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $
 */
public class MarcParmException extends MarcException
 {
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcParmException.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcParmException.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $";

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
     * Instantiate a new MarcParmException exception with the supplied
     * text.
     *
     * @param comment - programmer supplied comment describing error
     */
    public MarcParmException(String comment)
    {
        super(comment);
    }

    /**
     * Instantiate a new MarcParmException exception with the default
     * text.
     */
    public MarcParmException()
    {
        super("unspecified cause for MarcParmException");
    }

    /**
     * Instantiate a new MarcParmException exception with the supplied
     * text and a reference to the invoking object.
     *
     * @param obj - this value for calling object
     * @param comment - programmer supplied comment describing error
     */
    public MarcParmException(Object obj, String comment)
    {
        super( obj, comment);
    }
}
