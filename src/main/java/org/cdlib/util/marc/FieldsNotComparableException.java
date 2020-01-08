package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Excption class used to indicate two fields are not comparable.
 * This should usually be thrown if the tags differ, but could also
 * be thrown if the subtypes differ.
 *
 * @author <a href="mailto: shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: FieldsNotComparableException.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $
 */
public class FieldsNotComparableException extends MarcException
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(FieldsNotComparableException.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/FieldsNotComparableException.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $";

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
     * Instantiate a new FieldsNotComparableException exception with the supplied
     * text.
     *
     * @param comment - description of the error the occured
     */
    public FieldsNotComparableException(String comment)
    {
        super(comment);
    }

    /**
     * Instantiate a new FieldsNotComparableException exception with the default
     * text.
     */
    public FieldsNotComparableException()
    {
        super("fields are not comparable");
    }

    /**
     * Instantiate a new FieldsNotComparableException exception with the supplied
     * text and a reference to the invoking object.
     *
     * @param obj - this value for calling object
     * @param comment - description of the error the occured
     */
    public FieldsNotComparableException(Object obj, String comment)
    {
        super(obj, comment);
    }
}
