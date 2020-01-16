package org.cdlib.util.marc;

/**
 * A collection of constants used by the cdl marc processing class library.
 *
 *
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcConstants.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $
 */

public interface MarcConstants
{
    /**
     * The marc record was read, and parsed into a MarcRecord object, successfully.
     */
    int MARC_READ_SUCCESS        = 0;

    /**
     * The marc record read is badly formatted or unparseable.
     */
    int MARC_READ_INVALID_FORMAT = 1;

    /**
     * The marc record read contains an invalid record length in the leader.
     */
    int MARC_READ_INVALID_LENGTH = 2;

    /**
     * Reading the marc file failed.
     */
    int MARC_READ_FAILED         = 3;

    /**
     * Marc record was not built for some reason.
     */
    int MARC_REC_NOT_BUILT  = -1;

    /**
     * Marc record was built cleanly.
     */
    int MARC_REC_CLEAN      = 0;

    /**
     * Marc record contains nonnumeric tags.
     */
    int MARC_REC_ALHPA_TAGS = 1;

}
