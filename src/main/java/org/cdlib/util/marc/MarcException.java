package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Class representing a MARC processing Exception.
 * This is the base class for all other Marc Exceptions.
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @version $Id: MarcException.java,v 1.5 2002/10/22 21:28:07 smcgovrn Exp $
 */
public class MarcException extends RuntimeException
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcException.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcException.java,v 1.5 2002/10/22 21:28:07 smcgovrn Exp $";

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
	 * The message content for this object.
	 */
    private String localDetail = null;

    //====================================================
    //       CONSTRUCTORS
    //====================================================
    /**
     * Instantiate a new MarcException exception with the supplied
     * text.
     *
     * @param comment - programmer supplied comment describing error
     */
    public MarcException(String comment)
    {
        super(comment);
        localDetail = new String(comment);
    }

    /**
     * Instantiate a new MarcException exception with the default
     * text.
     */
    public MarcException()
    {
        super("unspecified cause for MarcException");
    }

    /**
     * Instantiate a new MarcException exception with the supplied
     * text and a reference to the invoking object.
     *
     * @param obj - this value for calling object
     * @param comment - programmer supplied comment describing error
     */
    public MarcException(Object obj, String comment)
    {
        super(comment);
        
    }

    /**
	 * Return the name of the class that threw this exception, if supplied,
	 * along with the message.
     */
    public String getDetail()
    {
        return localDetail;
    }
}
