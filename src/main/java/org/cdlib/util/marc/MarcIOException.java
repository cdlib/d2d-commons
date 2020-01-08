package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Excption class for invalid MARC format.
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @version $Id: MarcIOException.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $
 */
public class MarcIOException extends MarcException
 {
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcIOException.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcIOException.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $";

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
     * Instantiate a new MarcIOException exception with the supplied
     * text.
     *
     * @param comment - programmer supplied comment describing error
     */
    public MarcIOException(String comment)
    {
        super(comment);
    }

    /**
     * Instantiate a new MarcIOException exception with the default
     * text.
     */
    public MarcIOException()
    {
        super("unspecified cause for MarcIOException");
    }

    /**
     * Instantiate a new MarcIOException exception with the supplied
     * text and a reference to the invoking object.
     *
     * @param obj - this value for calling object
     * @param comment - programmer supplied comment describing error
     */
    public MarcIOException(Object obj, String comment)
    {
        super( obj, comment);
    }
}
