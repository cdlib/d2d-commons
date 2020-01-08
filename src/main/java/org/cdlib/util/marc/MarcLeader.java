package org.cdlib.util.marc;

import java.util.HashMap;
import org.apache.log4j.Logger;
import org.cdlib.util.string.StringUtil;

/**
 * Class implementing a MARC MarcRecord leader.
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcLeader.java,v 1.7 2006/07/31 19:29:52 rkl Exp $
 */
public class MarcLeader extends FixedLengthData
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcLeader.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcLeader.java,v 1.7 2006/07/31 19:29:52 rkl Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.7 $";

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
	 * HashMap containing the descriptions and codes for all valid media.
	 * This map is keyed by a concatenation of the type and biliographic level
	 * fields from the leader. The values are <code>String</code> arrays of size
	 * two containing the media code and media desctiption.
	 */
    private static HashMap validMedia = initValidMedia();

	/**
	 * Initialize the validMedia map.
	 */
    private static HashMap initValidMedia()
    {
        if ( log.isDebugEnabled() )
        {
            log.debug("Initializing Valid Media Map");
        }

        if ( validMedia == null )
        {
            validMedia = new HashMap();
            String[] books    = {"BK", "Books"};
            String[] computer = {"CF", "Computer Files"};
            String[] maps     = {"MP", "Maps"};
            String[] mixed    = {"MX", "Mixed Materials"};
            String[] scores   = {"SC", "Scores"};
            String[] serials  = {"SE", "Serials"};
            String[] sound    = {"SR", "Sound Recordings"};
            String[] visual   = {"VM", "Visual Materials"};
            String[] unknown  = {"UN", "Unknown Media"};

            validMedia.put(null, unknown );
            validMedia.put("  ", unknown );
            validMedia.put("aa", books);
            validMedia.put("ab", serials);
            validMedia.put("ac", books);
            validMedia.put("ad", books);
            validMedia.put("am", books);
            validMedia.put("as", serials);
            validMedia.put("b ", mixed);
            validMedia.put("c ", scores);
            validMedia.put("d ", scores);
            validMedia.put("e ", maps);
            validMedia.put("f ", maps);
            validMedia.put("g ", visual);
            validMedia.put("i ", sound);
            validMedia.put("j ", sound);
            validMedia.put("k ", visual);
            validMedia.put("m ", computer);
            validMedia.put("o ", visual);
            validMedia.put("p ", mixed);
            validMedia.put("r ", visual);
            validMedia.put("t ", books);
        }
        return validMedia;
    }

     /**
      * Create an uninitialized MARC record leader.
      * (Positions 10-11 and 20-23 are set to "22"
      * and "4500" respectively.)
      * 
      */
    
    
    // Explicitly Defining other constructor
    String leader = null;
    
     /**
	 * @param len
	 * @param leader
	 */
	public MarcLeader(int len, String leader) {
		super(len);
		this.leader = leader;
	}

	public MarcLeader()
     {
         this(null);
         
         setPos(10, "22");
         setPos(20, "4500");
     }

     /**
      * Create an initialized MARC record leader.
      * @param s Characters constituting s are inserted
      * starting at first position. If less than 24
      * characters long, the remaining characters will
      * be ASCII blanks.
      */
     public MarcLeader(String s)
     {
         super(24, s);
     }

     /**
      * MarcRecord logical record length in leader.
      *
      * Note: if the lenght of the string representaion exceed 5 this will
      * throw an ArrayIndexOutOfBoundsException exception.
      */
     void setRecordLength(int len)
     {
         String s = Integer.toString(len);
         int nChars = s.length();

         setPos(0, "00000");
         setPos(5 - nChars, s);
     }

     /**
      * Return logical record length stored in leader.
      */
     final int getRecordLength()
         throws MarcFormatException
     {
         String s = getPos(0, 4);
         if (!StringUtil.isNumeric(s))
             throw new MarcFormatException(this, "non-numeric length");
         return Integer.valueOf(s).intValue();
     }

     /**
      * Store base address of record data in leader.
      */
     void setBaseAddress(int addr)
     {
         String s = Integer.toString(addr);
         int nChars = s.length();

         setPos(12, "00000");
         setPos(12 + 5 - nChars, s);
     }

     /**
      * Return base address of record data stored in leader.
      */
     final int getBaseAddress() throws MarcFormatException
     {
         String s = getPos(12, 16);
         if (!StringUtil.isNumeric(s))
             throw new MarcFormatException(this, "non-numeric base address");
         return Integer.valueOf(s).intValue();
     }

    /**
     * Return the record status code.
     */
    public char getRecordStatus()
    {
        return getPos(5);
    }

    /**
     * Set the record status code.
     */
    public void setRecordStatus(char c)
    {
        setPos(5, c);
    }

    /**
     * Return the delete status of this record.
     * @return true if records status is 'd', otherwise false
     */
    public boolean isDeleteRecord()
    {
        return ('d' == getPos(5));
    }

    /**
     * Return type of record (a = language material, c = Printed Music, etc.)
     */
    public final char getRecordType()
    {
        return getPos(6);
    }

    /**
     * Return type of record (a = language material, c = Printed Music, etc.)
     */
    public final char typeOfRecord()
    {
        return getPos(6);
    }

    /**
     * Verify the leader type code is valid.<br>
     * Valid marc types are:<br>
     * a - Language material<br>
     * c - Printed music<br>
     * d - Manuscript music<br>
     * e - Printed map<br>
     * f - Manuscript map<br>
     * g - Projected medium<br>
     * i - Nonmusical sound recoding<br>
     * j - Musical sound recording<br>
     * k - Two-dimensional nonprojectable graphic<br>
     * m - Computer file<br>
     * o - Kit<br>
     * p - Mixed material<br>
     * r - Three-dimensional artifacts or naturally occurring object<br>
     * t - Manuscript language material<br>
     */
    public final boolean hasValidType()
    {
        char rt = getRecordType();
        return (rt == 'a' || rt == 'c' || rt == 'd'
                || rt == 'e' || rt == 'f' || rt == 'g'
                || rt == 'i' || rt == 'j' || rt == 'k'
                || rt == 'm' || rt == 'o' || rt == 'p'
                || rt == 'r' || rt == 't');
    }


    /**
     * Return bibliographic level (m = monograph, etc.)
     */
    public final char bibLevel()
    {
        return getPos(7);
    }

    /**
     * Return bibliographic level (m = monograph, etc.)
     */
    public final char getBibLevel()
    {
        return getPos(7);
    }

    /**
     * Verify the leader bibliographic level is valid.<br>
     * Valid marc types are:<br>
     * a - Monographic component part
     * b - Serial component part
     * c - Collection
     * d - Subunit
     * I - Integrating resource
     * m - Monograph
     * s - Serial
     */
    public final boolean hasValidBibLevel()
    {
        char bl = getBibLevel();
        return (bl == 'a' || bl == 'b' || bl == 'c' || bl == 'd'
                || bl == 'i' || bl == 'I' || bl == 'm' || bl == 's');
    }

	/**
	 * Return the media code and description stings for this object
	 * using the record type and biblevel values to form a lookup key
	 * for the validMedia HashMap.
	 *
	 * @return a string array containing the media code and desctiption
	 */
    public String[] lookupMedia()
    {
        String[] media = null;

        char rt = getRecordType();
        char bl = getBibLevel();
        char[] mchars = {' ', ' '};
        String mkey = null;

        mchars[0] = rt;

        if ( rt == 'a' )
        {
            mchars[1] = bl;
        }
        else
        {
            mchars[1] = ' ';
        }

        mkey = new String(mchars);
        media = (String[])validMedia.get(mkey);
        if ( log.isDebugEnabled() )
        {
            log.debug("rt = '" + rt + "'  bl = '" + bl + "'  mkey = '" + mkey + "'");
        }

        if ( media == null )
        {
            media = (String[])validMedia.get(null);
        }

        return media;
    }

	/**
	 * Return the media code for this object.
	 * @return the media code
	 */
    public String getMediaCode()
    {
        String[] media = lookupMedia();
        String mc = media[0];
        return mc;
    }

	/**
	 * Return the media description for this object.
	 * @return the media description
	 */
    public String getMediaDesc()
    {
        String[] media = lookupMedia();
        String mdesc = media[1];
        return mdesc;
    }

	/**
	 * Return the validMedia map for this class.
	 * @return the validMedia HashMap
	 */
    public static HashMap getValidMedia()
    {
        return validMedia;
    }

    /**
     * Return contents of leader, formatted for use
     * in MARC record
     */
    String marcDump()
    {
        return super.dump();
    }

    /**
     * Return contents of leader, formatted for
     * display
     */
    public String toString()
    {
        return "LDR\t\t\"" + super.toString() + "\"";
    }

    /**
     * Return contents of leader, as string
     */
    public String value()
    {
        return super.toString();
    }
}
