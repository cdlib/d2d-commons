package org.cdlib.util.marc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;

/**
 * This class stores a set of translation rules in the form of regular
 * expession matches and substitution strings and supplies methods to
 * apply those transalations to <code>Strings</code>.
 *
 *
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: TranslateTable.java,v 1.3 2002/10/22 21:28:08 smcgovrn Exp $
 */

public class TranslateTable
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(TranslateTable.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/TranslateTable.java,v 1.3 2002/10/22 21:28:08 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.3 $";

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
     * Data structure uses to hold the regular expressions to match
     * and the substitution for each match.
     */
    protected HashMap xlatRegExMap = null;

    /**
     * Create a new <code>TranslateTable</code> object without initializing
     * the mapping data structure.
     */
    public TranslateTable()
    {
    }

    /**
     * Create a new <code>TranslateTable</code> object and initialize
     * the mapping data structure.
     */
    public TranslateTable(String[][] xlatTbl)
    {
        makeMap(xlatTbl);
    }

    /**
     * Store a set of translation rules in a local <code>HashMap</code>.
     * This method takes an a array of <code>String</code> pairs. Each
     * pair contains a regular expression, to use for matching, and a
     * substitution <code>String</code> to use when a match is found.
     * The regular expression <code>String</code> is precompiled into
     * an org.apache.regexp.RE object, which is then stored as the key
     * to a <code>HashMap</code> entry, and the substitution <code>String</code>
     * is stored as the value of the entry.
     *
     * Note: the <code>HashMap</code> data structure does not preserve
     * order. This class could be recoded to use a <code>LinkedList</code>
     * of <code>Map.Entry</code> objects if such order becomes important.
     *
     * @param xlatTbl the array of string pairs (<code>String[][]</code>)
     * @return true unless an exception occurs, false if an exception occurs
     */
    public boolean makeMap(String[][] xlatTbl)
    {
        boolean rc = true;

        try
        {
            xlatRegExMap = new HashMap();

            int max = xlatTbl.length;
            for ( int i = 0; i < max; i++ )
            {
                log.debug("RegEx str = '" + xlatTbl[i][0] + "'");
                RE regex = new RE(xlatTbl[i][0]);
                xlatRegExMap.put(regex, xlatTbl[i][1]);
            }
        }
        catch (Exception e)
        {
            log.error("Exception: " + e.getMessage(), e);
            rc = false;
        }

        return rc;
    }

    /**
     * Iterate over the regular expessions stored in the translation map,
     * and make the substitution specifed in the value of the corresponding
     * map entry for each match found. This method traps and logs all
     * exceptions. In particular, if <code>makeMap()</code> has never
     * been successfully invoked, a <code>java.lang.NullPointerException</code>
     * will occur.
     *
     * @param str the string to translate
     * @return the translated string
     */
    public String applyAll(String str)
    {
        String sRet = str;
        try
        {
            Set mappings = xlatRegExMap.entrySet();
            Iterator iter = mappings.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry xlat = (Map.Entry)iter.next();
                RE regex = (RE)xlat.getKey();
                String sub = (String)xlat.getValue();
                sRet = regex.subst(sRet, sub, RE.REPLACE_ALL);
            }
        }
        catch (Exception e)
        {
            log.error("Exception: " + e.getMessage(), e);
        }

        return sRet;
    }

    /*
     * This main() method is supplied to unit test the class.
     */
    public static void main(String[] args)
    {
        String[][] xlatTblRaw =
            {
                {"\u00AF", "\u001B\u0062\u0030\u001B\u0073"},
                {"\u0091", "\u001B\u0062\u0031\u001B\u0073"},
                {"\u0092", "\u001B\u0062\u0032\u001B\u0073"},
                {"\u0093", "\u001B\u0062\u0033\u001B\u0073"},
                {"\u0094", "\u001B\u0062\u0034\u001B\u0073"},
                {"\u0095", "\u001B\u0062\u0035\u001B\u0073"},
                {"\u0096", "\u001B\u0062\u0036\u001B\u0073"},
                {"\u0097", "\u001B\u0062\u0037\u001B\u0073"},
                {"\u0098", "\u001B\u0062\u0038\u001B\u0073"},
                {"\u0099", "\u001B\u0062\u0039\u001B\u0073"},
                {"\u009A", "\u001B\u0062\u002B\u001B\u0073"},
                {"\u009B", "\u001B\u0062\u002D\u001B\u0073"},
                {"\u009C", "\u001B\u0062\u0028\u001B\u0073"},
                {"\u009D", "\u001B\u0062\u0029\u001B\u0073"},
                {"\u00C0", "\u001B\u0070\u0030\u001B\u0073"},
                {"\u00C1", "\u001B\u0070\u0031\u001B\u0073"},
                {"\u00C2", "\u001B\u0070\u0032\u001B\u0073"},
                {"\u00C3", "\u001B\u0070\u0033\u001B\u0073"},
                {"\u00C4", "\u001B\u0070\u0034\u001B\u0073"},
                {"\u00C5", "\u001B\u0070\u0035\u001B\u0073"},
                {"\u00C6", "\u001B\u0070\u0036\u001B\u0073"},
                {"\u00C7", "\u001B\u0070\u0037\u001B\u0073"},
                {"\u00C8", "\u001B\u0070\u0038\u001B\u0073"},
                {"\u00C9", "\u001B\u0070\u0039\u001B\u0073"},
                {"\u00FC", "\u001B\u0070\u002B\u001B\u0073"},
                {"\u00D0", "\u001B\u0070\u002D\u001B\u0073"},
                {"\u00D1", "\u001B\u0070\u0028\u001B\u0073"},
                {"\u007F", "\u001B\u0070\u0029\u001B\u0073"},
                {"\u00FD", "\u001B\u0067\u0061\u001B\u0073"},
                {"\u00CE", "\u001B\u0067\u0062\u001B\u0073"},
                {"\u00FF", "\u001B\u0067\u0063\u001B\u0073"},
            };

        String[][] xlatTbl =
            {
                {"\\xAF", "\u001B\u0062\u0030\u001B\u0073"},
                {"\\x91", "\u001B\u0062\u0031\u001B\u0073"},
                {"\\x92", "\u001B\u0062\u0032\u001B\u0073"},
                {"\\x93", "\u001B\u0062\u0033\u001B\u0073"},
                {"\\x94", "\u001B\u0062\u0034\u001B\u0073"},
                {"\\x95", "\u001B\u0062\u0035\u001B\u0073"},
                {"\\x96", "\u001B\u0062\u0036\u001B\u0073"},
                {"\\x97", "\u001B\u0062\u0037\u001B\u0073"},
                {"\\x98", "\u001B\u0062\u0038\u001B\u0073"},
                {"\\x99", "\u001B\u0062\u0039\u001B\u0073"},
                {"\\x9A", "\u001B\u0062\u002B\u001B\u0073"},
                {"\\x9B", "\u001B\u0062\u002D\u001B\u0073"},
                {"\\x9C", "\u001B\u0062\u0028\u001B\u0073"},
                {"\\x9D", "\u001B\u0062\u0029\u001B\u0073"},
                {"\\xC0", "\u001B\u0070\u0030\u001B\u0073"},
                {"\\xC1", "\u001B\u0070\u0031\u001B\u0073"},
                {"\\xC2", "\u001B\u0070\u0032\u001B\u0073"},
                {"\\xC3", "\u001B\u0070\u0033\u001B\u0073"},
                {"\\xC4", "\u001B\u0070\u0034\u001B\u0073"},
                {"\\xC5", "\u001B\u0070\u0035\u001B\u0073"},
                {"\\xC6", "\u001B\u0070\u0036\u001B\u0073"},
                {"\\xC7", "\u001B\u0070\u0037\u001B\u0073"},
                {"\\xC8", "\u001B\u0070\u0038\u001B\u0073"},
                {"\\xC9", "\u001B\u0070\u0039\u001B\u0073"},
                {"\\xFC", "\u001B\u0070\u002B\u001B\u0073"},
                {"\\xD0", "\u001B\u0070\u002D\u001B\u0073"},
                {"\\xD1", "\u001B\u0070\u0028\u001B\u0073"},
                {"\\x7F", "\u001B\u0070\u0029\u001B\u0073"},
                {"\\xFD", "\u001B\u0067\u0061\u001B\u0073"},
                {"\\xCE", "\u001B\u0067\u0062\u001B\u0073"},
                {"\\xFF", "\u001B\u0067\u0063\u001B\u0073"}
            };

        TranslateTable tt = new TranslateTable();
        if ( tt.makeMap(xlatTbl) )
        {
            System.out.println("build translate table - okay");
        }
        else
        {
            System.out.println("build translate table - falied");
            System.exit(1);
        }

        int xtlen = xlatTblRaw.length;
        StringBuffer sb1 = new StringBuffer(xtlen);
        StringBuffer sb2 = new StringBuffer(xtlen);
        StringBuffer sb3 = new StringBuffer(xtlen);

        for ( int i = 0; i < xtlen; i++ )
        {
            sb1.append(xlatTblRaw[i][0]);
            sb2.append(xlatTblRaw[i][1]);
            sb3.append(xlatTbl[i][0]);
        }

        String s1 = sb1.toString();
        String s2 = sb2.toString();
        String s3 = sb3.toString();

        String sout = tt.applyAll(s1);

        System.out.println("  s1 = '" + s1 + "'");
        System.out.println("  s2 = '" + s2 + "'");
        System.out.println("  s3 = '" + s3 + "'");
        System.out.println("sout = '" + sout + "'");

    }
    // end of main()

}
// TranlateTable class
