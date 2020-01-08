package org.cdlib.util.marc;

/**
 * Interface containing various MARC constants
 *
 * Changed from a class to an interface.
 * Added String constants for each character constant. This allows the
 * JVM to use the canonical value from the String pool, instead of
 * creating a new String every time we need a String representation.
 * Shawn McGovern 2002/05/08
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: Marc.java,v 1.2 2002/07/16 23:22:35 smcgovrn Exp $
 */
public interface Marc
{
     /**
      * End of Record marker
      */
     char EOR = '\u001D';

     /**
      * End of Record marker as a String
      */
     String EORStr = "\u001D";

     /**
      * End of Field marker
      */
     char EOF = '\u001E';

     /**
      * End of Field marker as a String
      */
     String EOFStr = "\u001E";

     /**
      * Subfield marker
      */
     char SF = '\u001F';

     /**
      * Subfield marker as a String
      */
     String SFStr = "\u001F";
}
