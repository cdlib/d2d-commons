package org.cdlib.util.marc;

import java.util.Enumeration;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
  * Class that implements a MARC record directory.
  *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcDirectory.java,v 1.5 2002/11/12 21:41:54 smcgovrn Exp $
  */
public class MarcDirectory
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcDirectory.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcDirectory.java,v 1.5 2002/11/12 21:41:54 smcgovrn Exp $";

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
	 * A <code>Vector</code> to hold the directory entries.
	 */
    private Vector data;

    /**
     * Create a new directory
     */
    MarcDirectory()
    {
        data = new Vector(300, 100);
    }

    /**
     * Add an entry to the directory
     */
    void addEntry(MarcDirectoryEntry e)
    {
        data.addElement(e);
    }

    /**
     * Add an entry to the directory.
     *
     * @param tag Field tag
     * @param length Field length, including terminator
     * @param start Starting character position, relative to base address
     */
    void addEntry(String tag, int length, int start)
    {
        addEntry( new MarcDirectoryEntry(tag, length, start));
    }

    /**
     * Return an enumeration of the directory entries.
     *
     */
    Enumeration elements()
    {
        return data.elements();
    }

    /**
     * Return representation of directory for use
     * in constructing a MARC record
     */
    String marcDump()
    {
        StringBuffer sb = new StringBuffer();
        Enumeration e = data.elements();

        while( e.hasMoreElements() )
        {
            sb.append( ((MarcDirectoryEntry)(e.nextElement())).marcDump() );
        }

        sb.append( Marc.EOF );

        return sb.toString();
    }
}
