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

/**
 * Container class to hold the location table used to validate
 * holdings locations.
 *
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: LocationTable.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $
 */

public class LocationTable
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(LocationTable.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/LocationTable.java,v 1.4 2002/10/22 21:28:07 smcgovrn Exp $";

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

    /**
     * A Map of <code>Location</code> objects keyed the location field
     * of the entry.
     */
    private HashMap loctab = null;

    /**
     * Create a new, uninitialized, LocationTable object. This object may be
     * inititialized by a subsequent invocation of the <code>initFromFile</code>
     * method.
     */
    public LocationTable()
    {
        loctab = new HashMap(3000);
    }

    /**
     * Create a new LocationTable object using the file name supplied to
     * initialize the locations map.
     */
    public LocationTable(String fname)
    {
        try
        {
            loctab = new HashMap(3000);
            initFromFile(fname);
        }
        catch (Exception e)
        {
            log.error("Exception loading location table: " + e.getMessage(), e);
            loctab = null;
            throw new RuntimeException();
        }
    }

    /**
     * Create a new LocationTable object, initializing the location map from
     * the entries in the supplied <code>Map</code>. The entries are copied
     * using the Map.putAll(Map) method, so subsequent changes to that map
     * are not reflected in this object.
     */
    public LocationTable(Map lt)
    {
        loctab = new HashMap(3000);
        loctab.putAll(lt);
    }

    /**
     * Fill the location map using the the file specified, after first
     * removing any existing entries.
     *
     * @throws NullPointerException if the file name is null
     * @throws FileNotFoundException if the named file cannot be found
     * @throws IOException if any IO error occurs
     */
    public void initFromFile(String fname)
        throws Exception
    {
        log.info("Loading location table from file: '" + fname + "'");

        this.clear(); // First remove any existing entries

        LineNumberReader locfile = new LineNumberReader(new FileReader(fname), 32 * 1024);
        String locline;

        while ( (locline = locfile.readLine()) != null )
        {
            Location loc = new Location(locline);
            loctab.put(loc.location, loc);
            if ( log.isDebugEnabled() && loc.location.startsWith("GLAD") )
            {
                log.debug("location table: added key = '" + loc.location + "'");
            }
        }
        locfile.close();
    }

    /**
     * Add a new location to this <code>LocationTable</code>, or replace the
     * old value if it already exists. The semantics are the same as those
     * of Map.put(Object key, Object value), except that the key is obtained
     * from the location field of the supplied <code>Location</code> object,
     * and, therefore null values are not allowed.
     */
    public Location put(Location loc)
    {
        return (loc == null ? null : (Location)loctab.put(loc.location, loc));
    }

    /**
     * Remove all the entries in the location map.
     */
    public void clear()
    {
        if ( loctab != null )
        {
            loctab.clear();
        }
    }

    /**
     * Returns true if the location map contains the specified location id.
     * @param key the location id to be tested
     * @return true if the location map contains the location id
     */
    public boolean exists(String key)
    {
        if ( log.isDebugEnabled() )
        {
            log.debug("location table: looking for key = '" + key + "'");
        }
        return (loctab != null && loctab.containsKey(key));
    }

    /**
     * Retrieve the <code>Location</code> associated with the specifed location id.
     */
    public Location lookup(String key)
    {
        Location loc = null;
        if ( loctab != null )
        {
            loc = (Location)loctab.get(key);
        }
        return loc;
    }


    public int dumpToFile(String fname)
    {
        int count = 0;
        PrintWriter out = null;

        try
        {
            out = new PrintWriter(new FileWriter(fname));
            TreeSet  keys = new TreeSet(loctab.keySet());
            Iterator iter = keys.iterator();
            while ( iter.hasNext() )
            {
                String key = (String)iter.next();
                Location loc = (Location)loctab.get(key);
                String val = loc.toString();
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
            System.out.println("Usage: LocationTable intable outtable");
            System.exit(4);
        }
        else
        {
            LocationTable myloctab = new LocationTable(args[0]);
            int count = myloctab.dumpToFile(args[1]);
            System.out.println("Wrote " + count + " records to dumped location table");
            System.exit(0);
        }
    }

}
// end of LocationTable class
