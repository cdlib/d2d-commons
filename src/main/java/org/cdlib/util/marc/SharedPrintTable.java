package org.cdlib.util.marc;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.cdlib.util.marc.SharedPrint;

/**
 * Container class to hold the shared print table used to create field 793
 *
 * @author Mark Reyes
 * @version $Id: SharedPrintTable.java,v 1.2 2005/05/27 17:50:36 rkl Exp $
 */

public class SharedPrintTable
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(SharedPrintTable.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/SharedPrintTable.java,v 1.2 2005/05/27 17:50:36 rkl Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.2 $";

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
     * A Map of <code>Shared Print</code> objects. 
     */
    private HashMap sptab = null;

    /**
     * Create a new, uninitialized, SharedPrintTable object. This object may be
     * inititialized by a subsequent invocation of the <code>initFromFile</code>
     * method.
     */
    public SharedPrintTable()
    {
        sptab = new HashMap(1500);
    }

    /**
     * Create a new SharedPrintTable object using the file name supplied to
     * initialize the shared print map.
     */
    public SharedPrintTable(String fname)
    {
        try
        {
            sptab = new HashMap(1500);
            initFromFile(fname);
        }
        catch (Exception e)
        {
            log.error("Exception loading shared print table: " + e.getMessage(), e);
            sptab = null;
            throw new RuntimeException();
        }
    }

    /**
     * Create a new SharedPrintTable object, initializing the shared print map from
     * the entries in the supplied <code>Map</code>. The entries are copied
     * using the Map.putAll(Map) method, so subsequent changes to that map
     * are not reflected in this object.
     */
    public SharedPrintTable(Map sp)
    {
        sptab = new HashMap(1500);
        sptab.putAll(sp);
    }

    /**
     * Fill the shared print map using the the file specified, after first
     * removing any existing entries.
     *
     * @throws NullPointerException if the file name is null
     * @throws FileNotFoundException if the named file cannot be found
     * @throws IOException if any IO error occurs
     */
    public void initFromFile(String fname)
        throws Exception
    {
        log.info("Loading shared print table from file: '" + fname + "'");

        this.clear(); // First remove any existing entries

        LineNumberReader spfile = new LineNumberReader(new FileReader(fname), 32 * 1024);
        String spline;

        while ( (spline = spfile.readLine()) != null )
        {
            SharedPrint sp = new SharedPrint(spline);
            sptab.put(sp.searchKey, sp);
        }
        spfile.close();
    }

    /**
     * Add a new shared print to this <code>SharedPrintTable</code>, or replace the
     * old value if it already exists. The semantics are the same as those
     * of Map.put(Object key, Object value), except that the key is obtained
     * from the searchKey field of the supplied <code>SharedPrint</code> object,
     * and, therefore null values are not allowed.
     */
    public SharedPrint put(SharedPrint sp)
    {
        return (sp == null ? null : (SharedPrint)sptab.put(sp.searchKey, sp));
    }

    /**
     * Remove all the entries in the shared print map.
     */
    public void clear()
    {
        if ( sptab != null )
        {
            sptab.clear();
        }
    }

    /**
     * Returns true if the shared print map contains the specified search key.
     * @param key the search key to be tested
     * @return true if the shared print map contains the search key
     */
    public boolean exists(String key)
    {
        if ( log.isDebugEnabled() )
        {
            log.debug("shared print table: looking for key = '" + key + "'");
        }
        return (sptab != null && sptab.containsKey(key));
    }

    /**
     * Retrieve the <code>SharedPrint</code> associated with the specifed search key.
     */
    public SharedPrint lookup(String key)
    {
        SharedPrint sp = null;
        if ( sptab != null )
        {
            sp = (SharedPrint)sptab.get(key);
        }
        return sp;
    }


    public int dumpToFile(String fname)
    {
        int count = 0;
        PrintWriter out = null;

        try
        {
            out = new PrintWriter(new FileWriter(fname));
            TreeSet  keys = new TreeSet(sptab.keySet());
            Iterator iter = keys.iterator();
            while ( iter.hasNext() )
            {
                String key = (String)iter.next();
                SharedPrint sp = (SharedPrint)sptab.get(key);
                String val = sp.toString();
                out.println(val);
                count++;
            }
        }
        catch (IOException e)
        {
            // ignore
        }
        finally
        {
            if ( out != null )
            {
                out.close();
            }
        }


        return count;
    }

    public static void main (String[] args)
    {
        if ( args.length != 2 )
        {
            System.out.println("Usage: SharedPrintTable intable outtable");
            System.exit(4);
        }
        else
        {
            SharedPrintTable mysptab = new SharedPrintTable(args[0]);
            int count = mysptab.dumpToFile(args[1]);
            System.out.println("Wrote " + count + " records to dumped shared print table");
            System.exit(0);
        }
    }

}
// end of SharedPrintTable class
