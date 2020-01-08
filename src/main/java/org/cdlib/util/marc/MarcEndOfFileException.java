package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Exception class used to unequivocally indicate end of file.
 *
 * @author <a href="mailto: shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcEndOfFileException.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $
 */
public class MarcEndOfFileException extends MarcException
 {
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcEndOfFileException.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcEndOfFileException.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $";

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
     * Marc End of File Exception
     *
     * @param comment - programmer supplied comment describing error
     */
    public MarcEndOfFileException(String comment)
    {
        super(comment);
    }

    /**
     * Marc End of File Exception
     */
    public MarcEndOfFileException()
    {
        super("end of file");
    }

    /**
     * Marc End of File Exception
     *
     * @param obj - this value for calling object
     *
     * @param comment - programmer supplied comment describing error
     */
    public MarcEndOfFileException(Object obj, String comment)
    {
        super( obj, comment);
    }
}
