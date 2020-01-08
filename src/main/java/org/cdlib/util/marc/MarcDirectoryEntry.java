package org.cdlib.util.marc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

/**
 * Class that implements a MARC directory entry
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcDirectoryEntry.java,v 1.6 2002/11/12 21:41:54 smcgovrn Exp $
 */
public class MarcDirectoryEntry extends FixedLengthData
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcDirectoryEntry.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcDirectoryEntry.java,v 1.6 2002/11/12 21:41:54 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.6 $";

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
      * Create a directory entry from the specified tag, field length,
      * and field start position.<br>
      * Validate the parameters:<br>
      * 1. tag length equals 3<br>
      * 2. field length non-negative and less than 10,000<br>
      * 3. field start non-negative and less than 100,000<br>
      *
      * @exception MarcFormatException if any parameter is invalid
     * @throws IOException 
      */
     MarcDirectoryEntry(String tag, int length, int start)
         throws MarcFormatException
     {
         super(12, null, '0'); // pre-fill with zeros

         init(tag, length, start);
     }

     /**
      * Create a directory entry from a string representation.
      * Must be of the form xxx999999999.
     * @throws IOException 
      */
     MarcDirectoryEntry(String entry)
         throws MarcFormatException
     {
         super(12, null, '0'); // pre-fill with zeros

		 if ( log.isDebugEnabled() )
		 {
			 log.debug("Building directory entry from: '" + entry + "'");
		 }

         if ( entry == null || entry.length() != 12 )
         {
             throw new MarcFormatException(this, "invalid directory entry string: '" + entry + "'");
         }

         String tag = null;
         int length = 0;
         int start  = 0;

         try
         {
             tag = entry.substring(0,3);
             length = Integer.parseInt(entry.substring(3,7));
             start  = Integer.parseInt(entry.substring(7,12));
         }
         catch (NumberFormatException e)
         {
             throw new MarcFormatException(this, "invalid directory entry string: '" + entry + "'");
         }

         init(tag, length, start);
     }

     /**
      * Initialize a directory entry from the specified tag, field length,
      * and field start position.<br>
      * Validate the parameters:<br>
      * 1. tag length equals 3<br>
      * 2. field length non-negative and less than 10,000<br>
      * 3. field start non-negative and less than 100,000<br>
      *
      * @exception MarcFormatException if any parameter is invalid
      */
     
     

     
     protected void init(String tag, int length, int start)
         throws MarcFormatException
     {
		 if ( log.isDebugEnabled() )
		 {
			 log.debug("Initing directory entry from: tag='" + tag
					   + "' len='" + length + "' offset='" + start + "'");
		 }
         if ( tag == null || tag.length() != 3 )
         {
             throw new MarcFormatException(this, "invalid directory entry tag: '" + tag + "'");
         }

         if ( length < 0 || length > 9999 )
         {
             throw new MarcFormatException(this, "invalid directory entry length: " + length);
         }

         if ( start < 0 || start > 99999 )
         {   
        	throw new MarcFormatException(this, "invalid directory entry start: " + start);
         }

         setPos(0, tag);

         String s = Integer.toString(length);
         setPos(7 - s.length(), s);

         s = Integer.toString(start);
         setPos(12 - s.length(), s);
     }

     /**
      * Return field tag, e.g., "245"
      */
     String fieldTag()
     {
         return getPos(0, 2);
      }

     /**
      * Return length of field, including indicators,
      * terminators, and the like.
      */
     int fieldLength()
     {
         return Integer.parseInt(getPos(3, 6));
     }

     /**
      * Return starting character position of field,
      * relative to base address of record
      */
     int fieldStart()
     {
         return Integer.parseInt(getPos(7, 11));
     }

     /**
      * Return contents of entry, formatted for use
      * in MARC record.
      */
     String marcDump()
     {
         return super.dump();
     }
}
