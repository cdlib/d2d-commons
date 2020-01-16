package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Excption class for invalid MARC format.
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @version $Id: MarcFormatException.java,v 1.5 2002/10/29 00:24:50 smcgovrn Exp $
 */
public class MarcFormatException extends MarcException
 {
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcFormatException.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcFormatException.java,v 1.5 2002/10/29 00:24:50 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.5 $";

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

    /**
     * Instantiate a new MarcFormatException exception with the supplied
     * text.
     *
     * @param comment - programmer supplied comment describing error
     */
    public MarcFormatException(String comment)
    {
        super(comment);
    }

    /**
     * Instantiate a new MarcFormatException exception with the default
     * text.
     */
    public MarcFormatException()
    {
        super("Marc object is invalid");
    }

    /**
     * Instantiate a new MarcFormatException exception with the supplied
     * text and a reference to the invoking object.
     *
     * @param obj - this value for calling object
     * @param comment - programmer supplied comment describing error
     */
    public MarcFormatException(Object obj, String comment)
    {
        super(obj, comment);
    }
}
