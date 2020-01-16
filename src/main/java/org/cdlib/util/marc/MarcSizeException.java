package org.cdlib.util.marc;

import org.apache.log4j.Logger;

/**
 * Exception class used to unequivocally indicate end of file.
 *
 * @author <a href="mailto: shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcSizeException.java,v 1.1 2004/06/01 21:18:32 mreyes Exp $
 */
public class MarcSizeException extends MarcException
 {
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcSizeException.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcSizeException.java,v 1.1 2004/06/01 21:18:32 mreyes Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.1 $";

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
    public MarcSizeException(String comment)
    {
        super(comment);
    }

    /**
     * Marc End of File Exception
     */
    public MarcSizeException()
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
    public MarcSizeException(Object obj, String comment)
    {
        super( obj, comment);
    }
}
