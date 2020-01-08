package org.cdlib.util.marc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.cdlib.util.string.StringUtil;

/**
 * A <code>Reader</code> class to used read a file of Marc records.
 * Wraps a <code>FileInputStream</code>.
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcReader.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $
 */
public class MarcReader
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcReader.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcReader.java,v 1.4 2002/10/22 21:28:08 smcgovrn Exp $";

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
    //       PRIVATE VARIABLES
    //====================================================
    private static int      STARTINCNT    = -1;
    private File            fileIn        = null;
    private String          fileName      = null;
    private FileInputStream fin           = null;
    private StringBuffer    strbuf        = null;
    private int             currentCnt    = STARTINCNT;
    private int             lastInCnt     = STARTINCNT;
    private MarcRecord      currentRecord = null;


    //====================================================
    //       CONSTRUCTORS
    //====================================================

    /**
     * constructor for MarcReader
     *
     * @param infile - string containing name of marc file to be input
     */
    public MarcReader(String infile)
        throws MarcIOException
    {
        start(infile);
    }

    /**
     * constructor for MarcReader
     *
     * @param infile - preset FileInputStream for input
     */
    public MarcReader(FileInputStream infile)
    {
        fin = infile;
    }

    //====================================================
    //       PUBLIC METHODS
    //====================================================

    /**
     * the record count for the last returned record
     * @return int containing record count
     */
    public int getCurrentCnt()
    {
        return currentCnt;
    }

    /**
     * get nth record (start 0)
     *
     * @param inCnt - sequence number to return
     * @return MarcRecord for requested sequence number - null=not found
     */
    public MarcRecord getRecord(int inCnt)
        throws MarcParmException
    {
        if ( inCnt < 0 )
		{
            throw new MarcParmException(this, "Invalid parm value: " + inCnt);
        }

        if ( lastInCnt == STARTINCNT )
		{
            start();
        }

        MarcRecord marcRec = null;

        if ( lastInCnt == inCnt )
		{
            return currentRecord;
        }

        if ( lastInCnt > inCnt )
		{
            start();
        }

        for ( marcRec = next(); marcRec != null; marcRec = next() )
		{
            if ( currentCnt == inCnt )
			{
				break;
			}
        }
        lastInCnt = currentCnt;
        return marcRec;
    }

    /**
     * Return next MarcRecord built from input stream
     * @return MarcRecord object - null if no more Marc records
     */
    public MarcRecord next()
    {
        if (fin == null)
		{
			return null;
		}

        strbuf = new StringBuffer();
        byte[] bvalue = new byte[20];
        String svalue = null;
        char   cvalue = 0;
        int    reclen = 0;

        try
		{
            // find 5 numeric bytes as the beginning length of the Marc record
            for ( int i=0; i < fin.available(); i++ )
			{
                fin.read(bvalue, 0, 1);
                cvalue = (char)bvalue[0];

                // is numeric character?
                if ( Character.isDigit(cvalue) )
				{
                    strbuf.append(cvalue);
                }
                else
				{
                    strbuf = new StringBuffer();
                }
                if ( strbuf.length() == 5 )
				{
					break;
				}
            }
            if ( strbuf.length() < 5 )
			{
                close();
                return null;
            }

            // read in rest of record
            reclen = new Integer(strbuf.toString()).intValue();
            if ( reclen > (fin.available() + 5) )
			{
                close();
                return null;
            }

            byte[] buf = new byte[reclen];
            fin.read(buf, 0, reclen-5);
            String str = new String(buf, 0, reclen-5);
            strbuf.append(str);
            currentCnt++;

            // build a marc record from input string
            MarcRecord marc = new MarcRecord(strbuf.toString());
            currentRecord = marc;
            return marc;
        }
        catch (MarcIOException e)
		{
            throw e;
        }
        catch (MarcParmException e)
		{
            throw e;
        }
        catch (MarcFormatException e)
		{
            log.error("Exception - " + e.getMessage(), e);
            return null;
        }
        catch (IOException e)
		{
            throw new MarcIOException();
        }
    }

    public void start(String infile)
    {
        if ( StringUtil.isEmpty(infile) )
		{
			throw new MarcIOException(this, "No file name provided");
		}

        fileIn = new File(infile);
        open();
    }

    public void start()
    {
        // file was just started
        if ( (fin != null) && (currentCnt == STARTINCNT) )
		{
			return;
		}

        // if no saved File value then try to make one
        if ( fileIn == null )
		{
            // unable to create File because no file name previously supplied
            if ( StringUtil.isEmpty(fileName) )
			{
                throw new MarcIOException(this, "No file name provided");
            }
            else
			{
                fileIn = new File(fileName);
            }
        }
        open();
    }

    //====================================================
    //       PRIVATE METHODS
    //====================================================
    private void close()
    {
        try
		{
            fin.close();
        }
        catch (Exception e)
		{
		}
        fin = null;
    }

    private void init()
    {
        currentCnt = STARTINCNT;
        lastInCnt = STARTINCNT;
    }

    private void open()
    {
        init();
        try
		{
            fin = new FileInputStream(fileIn);
        }
        catch (Exception e)
		{
                throw new MarcIOException(this, "Error processing file:"+fileIn);
        }
    }

}
