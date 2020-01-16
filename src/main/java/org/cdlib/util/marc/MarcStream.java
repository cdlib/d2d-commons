package org.cdlib.util.marc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.cdlib.util.string.StringUtil;

/**
 * An IO class used to read a file of Marc records.
 * Wraps an <code>InputStreamReader</code>.
 *
 * Warning this class, in addition to being badly named, is badly written,
 * and easily broken, so excersize caution when changing.
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcStream.java,v 1.8 2004/05/17 21:56:56 mreyes Exp $
 */
public class MarcStream implements MarcConstants
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcStream.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcStream.java,v 1.8 2004/05/17 21:56:56 mreyes Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.8 $";

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
    private static int STARTINCNT = -1;

    private File              fileIn        = null;
    private String            fileName      = null;
    private FileInputStream   fin           = null;
    private InputStreamReader isr           = null;
    private StringBuffer      strbuf        = null;
    private int               currentCnt    = STARTINCNT;
    private int               lastInCnt     = STARTINCNT;
    private MarcRecord        currentRecord = null;
    private int               fileOffset    = 0;


    //====================================================
    //       CONSTRUCTORS
    //====================================================

    /**
     * Instantiate a new MarcStream object.
     *
     * @param infile - string containing name of marc file to be input
     */
    public MarcStream(String infile)
        throws MarcIOException
    {
        fileName = infile;
        start(infile);
        log.debug("Started new MarcStream successfully");
    }

    /**
     * Instantiate a new MarcStream object.
     *
     * @param fileObj - File object defining marc file to be input
     */
    public MarcStream(File fileObj)
        throws MarcIOException
    {
        if (fileObj == null) {
            throw new MarcIOException("MarcStream - no File object provided on constructor");
        }
        fileIn = fileObj;
        open();
        log.debug("Started new MarcStream successfully");
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
     * Return the current string buffer. This will contain the last record
     * read, whether or not that record could be parsed.
     * @return the current string buffer
     */
    public StringBuffer getReadBuffer()
    {
        return strbuf;
    }

    /**
     * Get nth record (start 0).
     *
     * @param inCnt - sequence number to return
     * @return MarcRecord for requested sequence number - null=not found
     */
    public MarcRecord getRecord(int inCnt)
        throws MarcParmException, MarcFormatException, Exception
    {
        log.debug("looking for record #" + inCnt);
        log.debug("current rec #" + currentCnt);
        log.debug("last record #" + lastInCnt);

        if ( inCnt < 0 )
        {
            String msg = new String("Invalid record requested - record #: " + inCnt);
            log.error(msg);
            throw new MarcParmException(this, msg);
        }

        if ( lastInCnt == STARTINCNT )
        {
            start();
        }

        if ( lastInCnt == inCnt )
        {
            return currentRecord;
        }

        if ( lastInCnt > inCnt )
        {
            start();
        }

        MarcRecord marcRec = null;
        while ( currentCnt < inCnt )
        {
            try
            {
                marcRec = next();
                lastInCnt++;
                log.debug("got next record - current rec #" + currentCnt);
                log.debug("got next record - last record #" + lastInCnt);
            }
            catch (MarcEndOfFileException e)
            {
                lastInCnt++;
                log.debug("trapped MarcEndOfFileException - current rec #" + currentCnt);
                log.debug("trapped MarcEndOfFileException - last record #" + lastInCnt);
                throw e;
            }
            catch (MarcFormatException e)
            {
                lastInCnt++;
                log.debug("trapped MarcFormatException - current rec #" + currentCnt);
                log.debug("trapped MarcFormatException - last record #" + lastInCnt);
            }
            catch (Exception e)
            {
                lastInCnt++;
                log.debug("trapped Exception - " + e.getClass().getName()
                          + " - current rec #" + currentCnt);
                log.debug("trapped Exception - " + e.getClass().getName()
                          + " - last record #" + lastInCnt);
                throw e;
            }
        }

        return marcRec;
    }


    /**
     * Get nth record (start 0) into the referenced MarcRecord.
     *
     * @param inCnt sequence number to return
     * @param marcRec the marc record to populate
     * @return the status code for this operation
     */
    public int getRecord(int inCnt, MarcRecord marcRec)
        throws MarcParmException, MarcFormatException, Exception
    {
        int rc = -1;

        log.debug("looking for record #" + inCnt);
        log.debug("current rec #" + currentCnt);
        log.debug("last record #" + lastInCnt);

        if ( inCnt < 0 )
        {
            String msg = new String("Invalid record requested - record #: " + inCnt);
            log.error(msg);
            throw new MarcParmException(this, msg);
        }

        if ( lastInCnt == STARTINCNT )
        {
            start();
        }

        if ( lastInCnt == inCnt )
        {
            marcRec.build(currentRecord);
            return MARC_READ_SUCCESS;
        }

        if ( lastInCnt > inCnt )
        {
            start();
        }

        while ( currentCnt < inCnt )
        {
            try
            {
                rc = next(marcRec);
                lastInCnt++;
                log.debug("got next record - current rec #" + currentCnt);
                log.debug("got next record - last record #" + lastInCnt);
            }
            catch (MarcEndOfFileException e)
            {
                lastInCnt++;
                log.debug("trapped MarcEndOfFileException - current rec #" + currentCnt);
                log.debug("trapped MarcEndOfFileException - last record #" + lastInCnt);
                throw e;
            }
            catch (MarcFormatException e)
            {
                lastInCnt++;
                log.debug("trapped MarcFormatException - current rec #" + currentCnt);
                log.debug("trapped MarcFormatException - last record #" + lastInCnt);
                throw e;
            }
            catch (Exception e)
            {
                lastInCnt++;
                log.debug("trapped Exception - " + e.getClass().getName()
                          + " - current rec #" + currentCnt);
                log.debug("trapped Exception - " + e.getClass().getName()
                          + " - last record #" + lastInCnt);
                throw e;
            }
        }

        return rc;
    }

    /**
     * Return next MarcRecord built from input stream.
     *
     * @return MarcRecord object
     *
     * @exception MarcEndOfFileException if no more Marc records
     * @exception MarcInvalidStateException if the input stream is not open
     */
    public MarcRecord next()
    {
        if ( isr == null )
		{
			throw new MarcInvalidStateException();
		}

        strbuf = new StringBuffer();
        byte[] bvalue = new byte[20];
        String svalue = null;
        char cvalue = 0;
        int reclen = 0;

        try
        {
            // find 5 numeric bytes as the beginning length of the Marc record
            //TODO: this allows alphas to sprinkled in the length field -
            // not what the framers intended! - no, actually it skips over
            // until it finds a string of 5 digits - can this be right???
            // MAYBE: fix it to inspect only the first 5 characters
            int ivalue = -1;
            for ( int i = 0; true; i++ )
            {
                ivalue = isr.read();

                if ( ivalue == -1 )
				{
					break;
				}

                cvalue = (char)ivalue;

                // is numeric character?
                if ( Character.isDigit(cvalue) )
                {
                    strbuf.append(cvalue);
                }
                else
                {
                    log.debug("found non-digit - char = '" + cvalue + "'");
                    strbuf = new StringBuffer();
                }
                if ( strbuf.length() == 5 )
				{
					break;
				}
            }

            if ( strbuf.length() < 5 )
            {
                // log an error if we are not at end of file,
                // or we are at end of file, but found a partial record
                if ( ivalue > -1 || strbuf.length() != 0 )
                {
                    log.debug("failed to find length field - buf len = " + strbuf.length());
                }

                close();
                throw new MarcEndOfFileException();
            }

            // read in rest of record
            reclen = new Integer(strbuf.toString()).intValue();

            char[] buf = new char[reclen];
            ivalue = isr.read(buf, 0, reclen-5);
            if ( ivalue == -1 )
            {
                log.debug("found end of file");
                close();
                throw new MarcEndOfFileException();
            }

            String str = new String(buf, 0, reclen-5);
            strbuf.append(str);
            currentCnt++;

            // build a marc record from input string
            String inrec = new String(strbuf);
            log.debug("buffer = " + inrec);
            MarcRecord marc = new MarcRecord(inrec);
            currentRecord = marc;
            return marc;
        }
        catch (MarcIOException e)
        {
			if ( log.isDebugEnabled() )
			{
				log.error("MarcIOException - " + e.getMessage(), e);
			}
			else
			{
				log.error("MarcIOException - " + e.getMessage());
			}
            throw e;
        }
        catch (MarcParmException e)
        {
			if ( log.isDebugEnabled() )
			{
				log.error("MarcParmException - " + e.getMessage(), e);
			}
			else
			{
				log.error("MarcParmException - " + e.getMessage());
			}
            throw e;
        }
        catch (MarcFormatException e)
        {
			if ( log.isDebugEnabled() )
			{
				log.error("MarcFormatException - " + e.getMessage(), e);
			}
            throw e;
        }
        catch (java.io.IOException e)
        {
			if ( log.isDebugEnabled() )
			{
				log.error("IOException - " + e.getMessage(), e);
			}
			else
			{
				log.error("IOException - " + e.getMessage());
			}
            throw new MarcIOException(e.getMessage());
        }
    }


    /**
     * Return next MarcRecord built from input stream.
     *
     * @param marcRec A reference to a marc record to populate
     * @return MarcRecord object
     *
     * @exception MarcEndOfFileException if no more Marc records
     * @exception MarcInvalidStateException if the input stream is not open
     */
    public int next(MarcRecord marcRec)
    {
        if ( isr == null )
		{
			throw new MarcInvalidStateException();
		}

        strbuf = new StringBuffer();
        byte[] bvalue = new byte[20];
        String svalue = null;
        char cvalue = 0;
        int reclen = 0;
        int rc = -1;

        try
        {
            // find 5 numeric bytes as the beginning length of the Marc record
            //TODO: this allows alphas to sprinkled in the length field -
            // not what the framers intended! - no, actually it skips over
            // until it finds a string of 5 digits - can this be right???
            // MAYBE: fix it to inspect only the first 5 characters
            int ivalue = -1;
            for ( int i = 0; true; i++ )
            {
                ivalue = isr.read();

                if (ivalue == -1)
				{
					break;
				}

                cvalue = (char)ivalue;

                // is numeric character?
                if ( Character.isDigit(cvalue) )
                {
                    strbuf.append(cvalue);
                }
                else
                {
                    log.debug("found non-digit - char = '" + cvalue + "'");
                    strbuf = new StringBuffer();
                }
                if ( strbuf.length() == 5 )
				{
					break;
				}
            }

            if ( strbuf.length() < 5 )
            {
                // log an error if we are not at end of file,
                // or we are at end of file, but found a partial record
                if ( ivalue > -1 || strbuf.length() != 0 )
                {
                    log.debug("failed to find length field - buf len = " + strbuf.length());
                }

                close();
                throw new MarcEndOfFileException();
            }

            // read in rest of record
            reclen = new Integer(strbuf.toString()).intValue();

            char[] buf = new char[reclen];
            ivalue = isr.read(buf, 0, reclen-5);
            if ( ivalue == -1 )
            {
                log.debug("found end of file");
                close();
                throw new MarcEndOfFileException();
            }

            String str = new String(buf, 0, reclen-5);
            strbuf.append(str);
            currentCnt++;

            // build a marc record from input string
            String inrec = new String(strbuf);
            log.debug("buffer = " + inrec);
            marcRec.build(inrec);
            currentRecord = marcRec;
            return MARC_READ_SUCCESS;
        }
        catch (MarcIOException e)
        {
			if ( log.isDebugEnabled() )
			{
				log.error("MarcIOException - " + e.getMessage(), e);
			}
			else
			{
				log.error("MarcIOException - " + e.getMessage());
			}
            throw e;
        }
        catch (MarcParmException e)
        {
			if ( log.isDebugEnabled() )
			{
				log.error("MarcParmException - " + e.getMessage(), e);
			}
			else
			{
				log.error("MarcParmException - " + e.getMessage());
			}
            throw e;
        }
        catch (MarcFormatException e)
        {
			if ( log.isDebugEnabled() )
			{
				log.error("MarcFormatException - " + e.getMessage(), e);
			}
            throw e;
        }
        catch (java.io.IOException e)
        {
			if ( log.isDebugEnabled() )
			{
				log.error("IOException - " + e.getMessage(), e);
			}
			else
			{
				log.error("IOException - " + e.getMessage());
			}
            throw new MarcIOException(e.getMessage());
        }
    }


    /**
     * Creates a new <code>File</code> object using the path specified
     * and opens that file for input.
     *
     * @param infile the path to the input file
     */
    public void start(String infile)
    {
        if ( StringUtil.isEmpty(infile) )
		{
			throw new MarcIOException(this, "No file name provided");
		}

        fileIn = new File(infile);
        open();
    }


    /**
     * Uses the path specified in the fileName global variable to
     * setup our input file and opens the file.
     */
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
                String msg = new String("No file name provided");
                log.error(msg);
                throw new MarcIOException(this, msg);
            }
            else
            {
                fileIn = new File(fileName);
            }
        }
        open();
    }


    /**
     * Close the input stream.
     */
    public void close()
    {
        try
        {
            isr.close();
        }
        catch (Exception e)
        {
        }
        fin = null;
    }


    /**
     * Initialize the current count and last input record number variables.
     */
    public void init()
    {
        currentCnt = STARTINCNT;
        lastInCnt = STARTINCNT;
    }


    /**
     * Open the input stream using the iso-8859-1 code page.
     */
    public void open()
    {
        init();
        try
        {
            fin = new FileInputStream(fileIn);
            //isr = new InputStreamReader(fin, "UTF8");
            isr = new InputStreamReader(fin, "iso-8859-1");
        }
        catch (Exception e)
        {
            String msg = new String("Error processing file: " + fileIn);
            log.error(msg, e);
            throw new MarcIOException(this, msg);
        }
    }

}
