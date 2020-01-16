package org.cdlib.util.marc;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import org.cdlib.util.HexUtils;
import org.cdlib.util.string.StringDisplay;
import org.cdlib.util.string.StringUtil;


/**
 * Class representing a MARC record. Functionality includes parsing MARC
 * records from raw input, building a MARC record from individual fields,
 * and providing access to the fields that comprise the MARC record.
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcBaseRecord.java,v 1.12 2004/06/01 21:18:32 mreyes Exp $
 */
public class MarcBaseRecord implements MarcConstants
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcBaseRecord.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcBaseRecord.java,v 1.12 2004/06/01 21:18:32 mreyes Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.12 $";

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
    //       VARIABLES
    //====================================================
	/**
	 * The leader for this marc record.
	 */
    private MarcLeader leader = null;

	/**
	 * The fields contained in this marc record.
     *
     * This Vector should be replaced with a linked list of the Fields,
     * MarcFieldLists, but that is a significant rewrite, and so must
     * wait until somebody with the right combination of bugdet, vision,
     * and gumption, comes along.
     *
     * Note to future programmers: choose your data structures well; those
     * that follow you will live with the quality of your decisions, for
     * good, or for ill.
	 */
    private Vector fields = null;

	/**
	 * Sequence number used to assign unique field ids
	 */
    private int seq = 0;

	/**
	 * Ths status of this marc record. The valid values are:
	 * MARC_REC_NOT_BUILT when the record is incomplete,
	 * MARC_REC_CLEAN when the record is complete and clean, and
	 * MARC_REC_ALHPA_TAGS when the record contains some non-numeric tags.
	 *
	 * @see MarcConstants#MARC_REC_NOT_BUILT
	 * @see MarcConstants#MARC_REC_CLEAN
	 * @see MarcConstants#MARC_REC_ALHPA_TAGS
	 */
    private int status = MARC_REC_NOT_BUILT;

    //====================================================
    // Tag order enumeration constants
    //====================================================

	/**
	 * Add a new field after all existing fields.
	 */
    public static final int END_LIST       = 1;

	/**
	 * Insert a new field before the first field with a lexically greater tag.
	 */
    public static final int TAG_ORDER      = 2;

	/**
	 * Insert a new field at the end of its nXX group, e.g. a new 650 field would
	 * be placed at the end of any exising 6xx fields, before the first 7xx field.
	 */
    public static final int MARC_TAG_ORDER = 3;


    /**
     * Instatiate an uninitialized <code>MarcBaseRecord</code>.
     */
    public MarcBaseRecord()
    {
        leader = new MarcLeader();
        fields = new Vector(30, 10);
        seq    = 0;
        status = MARC_REC_NOT_BUILT;
    }


    /**
     * Instantiate a new  <code>MarcBaseRecord</code> from the supplied
     * <code>MarcBaseRecord</code>.
     */
    public MarcBaseRecord(MarcBaseRecord rec)
    {
        this();
        build(rec);
    }


    /**
     * Instatiate a new <code>MarcBaseRecord</code> using the fields in the
     * supplied <code>MarcFieldList</code> using the insertion order specified.
     *
     * @param list   <code>MarcFieldList</code> to use
     * @param order  field insertion order to use
	 *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public MarcBaseRecord(MarcFieldList list, int order)
    {
        this();
        setFields(list, order);
    }


    /**
     * Create a new <code>MarcBaseRecord</code> from a string of raw marc data,
     * inserting fields in the specified order.
     *
     * @param rawMarc string of marc data
     * @param order   field insertion order to use
     *
     * @exception MarcFormatException when the the raw marc data cannot be parsed
     *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public MarcBaseRecord(String rawMarc, int order)
    {
        this();
        status = build(rawMarc, order);
    }


    /**
     * Create a new <code>MarcBaseRecord</code> from a string of raw marc data.
     * Insertion order is defaulted to END_LIST.
	 *
     * @param rawMarc string of marc data
     *
     * @exception MarcFormatException when the the raw marc data cannot be parsed
     *
	 * @see #END_LIST
     */
    public MarcBaseRecord(String rawMarc)
    {
        this(rawMarc, END_LIST);
    }


    /**
     * Set the instance variables for this object from the supplied <code>MarcBaseRecord</code>.
     *
     * @param rec the <code>MarcBaseRecord</code> to use
     */
    public void build(MarcBaseRecord rec)
    {
        this.leader = rec.leader;
        this.fields = rec.fields;
        this.seq    = rec.seq;
        this.status = rec.status;
    }


    /**
     * Reset the fields of this object from those in the supplied <code>MarcFieldList</code>
	 * using the insertion order specified.
     *
     * @param list   <code>MarcFieldList</code> to use
     * @param order  field insertion order to use
	 *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public void build(MarcFieldList list, int order)
    {
        clear();
        setFields(list, order);
    }


    /**
     * Populate the fields for this marc record object by parsing the supplied
     * <code>String</code>. Fields are insterted in the order in which they
     * appear in the <code>String</code>.
     *
     * @param rawMarc string of marc data
     *
     * @exception MarcFormatException if any parse error occurs
     *
     * @see #build(String rawMarc, int order)
	 * @see #END_LIST
     */
    public int build(String rawMarc)
        throws MarcFormatException
    {
        status = build(rawMarc, END_LIST);
        return status;
    }


    /**
	 * Parse the raw marc data in the supplied <code>String</code> to create the fields
	 * for this marc objects. If this object already contains fields, those fields are
	 * cleared. This method checks for and end of record character in the last byte of
	 * the data buffer as indicated by the leader record length (the first five bytes
     * of the marc data), and for the end of field character at the end of each field
     * as defined by the directory offset and length.
	 *
     * @param rawMarc string of marc data
     * @param order   field insertion order to use
     *
     * @exception MarcFormatException if any parse error occurs
     *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public int build(String rawMarc, int order)
        throws MarcFormatException
    {
        int rc = MARC_REC_NOT_BUILT;

        // remove any previous fields
        if (fields.size() > 0)
        {
            clear();
        }

        // First make sure the marc data is at least 24 bytes (the length of the leader)
        int rmLen = rawMarc.length();
        if ( rmLen < 24 )
        {
            throw new MarcFormatException("Length of marc data is less than 24: " + rmLen);
        }

        // Validate record length and end-of-record terminator.
        char[] rlBuff = new char[5];
        rawMarc.getChars(0, 5, rlBuff, 0);
		int recordLen = 0;

		try
		{
			 recordLen = StringUtil.ascii2int(rlBuff);
		}
		catch (NumberFormatException e)
		{
			throw new MarcFormatException("Leader record length is not numeric: " + new String(rlBuff));
		}

		if (recordLen > rmLen)
		{
            throw new MarcFormatException("Record length from leader exceeds length of marc data: "
                                           + recordLen + " > " + rmLen);
		}

        if (Marc.EOR != rawMarc.charAt(recordLen - 1))
        {
            throw new MarcFormatException("End of record character missing");
        }

        // Extract the leader
        setLeader(new MarcLeader(rawMarc.substring(0, 24)));

        // Parse the directory
        int baseAddress = leader.getBaseAddress();
        int max = baseAddress - 1;
        MarcDirectory directory = new MarcDirectory();

		if ( log.isDebugEnabled() )
		{
			log.debug("Building directory");
		}

        for(int pos = 24; pos < max; pos += 12 )
        {
            directory.addEntry(new MarcDirectoryEntry(rawMarc.substring(pos, pos + 12)));
        }

        // Use the direcory entries to parse the rest of the string into fields.
        Enumeration dirEnum = directory.elements();
        int fieldEnd = 0;

        while( dirEnum.hasMoreElements() )
        {
            MarcDirectoryEntry entry = (MarcDirectoryEntry)(dirEnum.nextElement());

            String tag = entry.fieldTag();

            // Check for non-numeric tags.
            try
            {
				Integer.parseInt(tag);
            }
            catch (NumberFormatException e)
            {
                rc = MARC_REC_ALHPA_TAGS;
            }

            fieldEnd = baseAddress + entry.fieldStart() + entry.fieldLength() - 1;

            
     
            // Validate field terminator

            if (fieldEnd > (rawMarc.length() - 1)) {
                throw new MarcFormatException(this, "record length exceeded by field - tag:" + tag 
                    + " - fieldEnd:" + fieldEnd 
                    + " record length:" + rawMarc.length()); //!!!
            }

            // a valid pre Marc-21 format record
            else if ( (fieldEnd == (rawMarc.length() - 1))
                   && (Marc.EOR == rawMarc.charAt(fieldEnd)) ) { } 

            else if (fieldEnd + 2 > rawMarc.length())  {   
                throw new MarcFormatException(this, "field termination is invalid - tag:" + tag 
                    + " - fieldEnd:" + fieldEnd 
                    + " record length:" + rawMarc.length()); //!!!
            }

            else if (Marc.EOF != rawMarc.charAt(fieldEnd))
            {
                throw new MarcFormatException(this, "Field terminator not found: tag = "
                                              + tag + "  dir offset = " + entry.fieldStart()
                                              + "  dir length = " + entry.fieldLength()
                                              + "  field end = " + fieldEnd
                                              + " char found = '"
                                              + HexUtils.hexPrint(rawMarc.charAt(fieldEnd))
                                              + "'");
            }

            String data = rawMarc.substring(baseAddress + entry.fieldStart(), fieldEnd);

            if( tag.compareTo("010") < 0 )
            {
                setField(new MarcFixedLengthField(tag, entry.fieldLength() - 1, data), order);
            }
            else
            {
                setField(new MarcVblLengthField(tag, data), order);
            }
        }

        status = Math.max(rc, MARC_REC_CLEAN);
        return status;
    }


    /**
     * Get the status code for this record object. This should not be confused
     * with marc status in the leader record. The object status is used to indicate
     * whether the record has been built, and if it includes non-numeric field tags.
     *
     * @return this object's status code
     */
    public int getStatus()
    {
        return status;
    }


    /**
     * Return the record status code.
     *
     * @return the record status code
     *
     * @see org.cdlib.util.marc.MarcLeader#getRecordStatus()
     */
    public char getRecordStatusChar()
    {   log.debug(leader.getRecordStatus());
        return leader.getRecordStatus();
    }


    /**
     * Set the record status code.
     *
     * @see org.cdlib.util.marc.MarcLeader#setRecordStatus(char c)
     */
   public void setRecordStatus(char c)  
    {   System.out.println("Char to set"+c);
        leader.setRecordStatus(c);
    }


    /**
     * Return the delete status of this record.
     *
     * @return true if records status is 'd', otherwise false
     *
     * @see org.cdlib.util.marc.MarcLeader#isDeleteRecord()
     */
    public boolean isDeleteRecord()
    {
        return leader.isDeleteRecord();
    }


    /**
     * Get the leader record type code character.
     *
     * @return the leader record type code
     *
     * @see org.cdlib.util.marc.MarcLeader#typeOfRecord()
     */
    public char getRecordTypeChar()
    {    log.debug(leader.typeOfRecord());
        return leader.typeOfRecord();
    }


    /**
     * Verify the leader type code is valid.
     *
     * @return true if record type is valid, otherwise false
     *
     * @see org.cdlib.util.marc.MarcLeader#hasValidType()
     */
    public boolean hasValidType()
    {
        return leader.hasValidType();
    }


    /**
     * Get the leader bibliographic level.
     *
     * @return the leader bibliographic level
     *
     * @see org.cdlib.util.marc.MarcLeader#bibLevel()
     */
   public char getBibLevel()
    {    log.debug(leader.bibLevel());
        return leader.bibLevel();
    }


    /**
     * Verify the leader bibliographic level is valid.
     *
     * @return true if record type is valid, otherwise false
     *
     * @see org.cdlib.util.marc.MarcLeader#hasValidBibLevel()
     */
    public boolean hasValidBibLevel()
    {
        return leader.hasValidBibLevel();
    }


	/**
	 * Return the media code and description stings for this record
	 * using the record type and biblevel values to form a lookup key
	 * for the validMedia HashMap.
	 *
	 * @return a string array containing the media code and desctiption
     *
     * @see org.cdlib.util.marc.MarcLeader#lookupMedia()
	 */
    public String[] lookupMedia()
    {
        return leader.lookupMedia();
    }


	/**
	 * Return the media code for this record.
     *
	 * @return the media code
     *
     * @see org.cdlib.util.marc.MarcLeader#getMediaCode()
	 */
    public String getMediaCode()
    {
        return leader.getMediaCode();
    }


	/**
	 * Return the media description for this record.
     *
	 * @return the media description
     *
     * @see org.cdlib.util.marc.MarcLeader#getMediaDesc()
	 */
    public String getMediaDesc()
    {
        return leader.getMediaDesc();
    }


    /**
     * Creates an xml encoded string version of this marc record object.
     * This feature has never been fleshed out and should be considered
     * experimental.
     *
     * @return the xml encoding of this marc record
     *
     * @see org.cdlib.util.marc.MarcToXML
     */
    public String buildMarcXML()
    {
        MarcToXML xml = new MarcToXML();
        xml.startRecord();
        xml.addLeader(getLeader());
        Field field = null;
        MarcFieldList fields = getFields();
        int max = fields.size();
        for (int i = 0; i < max; i++)
        {
            field = fields.elementAt(i);
            xml.addField(field);
        }
        xml.endRecord();
        return xml.getXML();
    }


    /**
     * Delete fields in the speceifed tag range.
     *
     * @param startTag the starting tag of the range
     * @param endTag   the ending tag of the range
     *
     * @return the number of fields deleted
     */
    public int deleteFields(String startTag, String endTag)
    {
        int count = 0;

        Iterator iter = fields.iterator();
        while (iter.hasNext())
        {
            Field ele = (Field)iter.next();
            String tag = ele.tag();
            if ((tag.compareToIgnoreCase(startTag) >= 0)
                && (tag.compareToIgnoreCase(endTag) <= 0))
            {
                iter.remove();
                count++;
            }
        }

        return count;
    }

    //original save during rewrite of method - perhaps it should be
    //kept anyway as a counter example
//     public int deleteFields(String startTag, String endTag)
//     {
//         int count = 0;
//         int start;
//         int end;

//         // Get numeric values for the start and end tags.
//         start = getParmTag(startTag);
//         end = getParmTag(endTag);

//         Field ele = null;
//         int tag = 0;
//         boolean notdone = true;

//         // We must restart delete from first list element because index
//         // values modify after delete. Oh, the joys of using Vectors.
//         while(notdone)
//         {
//             notdone = false;
//             int max = fields.size();
//             for (int i = 0; i < max; i++)
//             {
//                 ele = (Field)fields.elementAt(i);
//                 tag = getRecordTag(ele.tag());
//                 if ((tag >= start) && (tag <= end))
//                 {
//                     fields.removeElementAt(i);
//                     count++;
//                     notdone = true;
//                     break;
//                 }
//             }
//         }
//         return count;
//     }


    /**
     * Delete each field given in the supplied <code>MarcFieldList</code>
     * from this marc record. The fields are matched using the <code>Field</code>
     * attribute <code>id</code>.
     *
     * @param list MarcFieldList containing the fields to remove
     *
     * @return the number of fields deleted
     */
    public int deleteFields(MarcFieldList list)
    {
        int count = 0;
        Field ele = null;
        int iLoc = -1;
        int max = list.size();

        for (int i=0; i< max; i++)
        {
            ele = (Field)list.elementAt(i);
            iLoc = findId(ele.getId());
            if (iLoc >= 0)  // field found
            {
                fields.removeElementAt(iLoc);
                count++;
            }
        }
        return count;
    }


    /**
     * Produce a formatted dump of Marc record suitable for printing.
     *
     * @param header display header
     *
     * @return string containing formatted output
     */
    public String formatDump(String header)
    {
        StringBuffer sb = new StringBuffer(5000);
        sb.append(StringDisplay.EOL);

        if (header != null && header.length() > 0)
        {
            sb.append(header);
            sb.append(StringDisplay.EOL);
        }
        sb.append(this.toString());

        return sb.toString();
    }


    /**
     * Produce a hex formatted dump of this marc record.
     *
     * @param header display header
     *
     * @return string containing formatted output
     */
    public String formatHexDump(String header)
    {
        StringBuffer sb = new StringBuffer(5000);
        sb.append(StringDisplay.EOL);
        sb.append("Hex Dump: ");
        sb.append(header);
        sb.append(StringDisplay.EOL);

        setNominalLength();
        setBaseAddress();
        sb.append(StringDisplay.stringPrthex(leader.marcDump(), "Leader"));

        Enumeration e = fields.elements();

        while( e.hasMoreElements() )
        {
            Field f = (Field)e.nextElement();
            sb.append(f.formatHexDump());
        }
        sb.append(StringDisplay.EOL);

        return sb.toString();
    }


    /**
     * Hex format given marc field.
     *
     * @param field the field to format
     *
     * @return string containing hex formatted output
     */
    public String formatHexField(Field field)
    {
        String outstr = StringDisplay.stringPrthex(field.marcDump(), "Field: " + field.tag());
        return outstr;
    }


    /**
     * Get bibliographic information from the leader.
     *
     * @return the bibliographic information from the leader
     *
     * @see org.cdlib.util.marc.FixedLengthData#getPos(int first, int last)
     */
    public String getBib()
        throws MarcParmException
    {
        return (leader.getPos(5, 9));
    }


    /**
     * Return all the fields contained in this marc record.
     *
     * @return <code>MarcFieldList</code> containing all fields
     *
     * @see #buildFieldList(Vector)
     */
    public MarcFieldList getFields()
    {
        return buildFieldList(fields);
    }


    /**
     * Find all instances of a field with a specified range of tags
     *
     * @param startTag the beginning tag in the range
     * @param endTag   the ending tag in the range
     *
     * @return MarcFieldList of fields whose tags are in the specified
     *         range of startTag through endTag, returns an empty list
     *         if there are no matching fields.
     */
    public MarcFieldList getFields(String startTag, String endTag)
    {
        MarcFieldList result = new MarcFieldList();
        int max = this.fields.size();

        if ( log.isDebugEnabled() )
        {
            Exception e = new Exception("Dummy exception");
            log.debug("startTag = " + startTag + " ; endTag = " + endTag);
        }

        for ( int i = 0; i < max; i++ )
        {
            Field f = (Field)fields.elementAt(i);
            String ftag = f.tag();

            if ( log.isDebugEnabled() )
            {
                log.debug("tagint = " + f.getTagint() + " ; tag = " + ftag);
            }

            if ( (ftag.compareToIgnoreCase(startTag) >= 0)
                 && (ftag.compareToIgnoreCase(endTag) <= 0) )
            {
                if ( log.isDebugEnabled() )
                {
                    log.debug("accepted (string) tag " + ftag);
                }
                result.addElement(f);
            }
        }
        return result;
    }


    /**
     * Find all instances of a field with a specified range of tags.
     * Tags are specifed with their numeric values to reduce needless
     * <code>String</code> to <code>int</code> conversions.
     *
     * @param startTag the beginning tag in the range
     * @param endTag   the ending tag in the range
     *
     * @return MarcFieldList of fields whose tags are in the specified
     *         range of startTag through endTag, returns an empty list
     *         if there are no matching fields. Only field with numeric
     *         tags are returned.
     */
    public MarcFieldList getFields(int startTag, int endTag)
    {
        MarcFieldList result = new MarcFieldList();
        int tagint = 0;
        int max = this.fields.size();

        if ( log.isDebugEnabled() )
        {
            //Exception e = new Exception("Dummy exception");
            log.debug("startTag = " + startTag + " ; endTag = " + endTag);
        }

        for ( int i = 0; i < max; i++ )
        {
            Field f = (Field)fields.elementAt(i);
            tagint = f.getTagint();
            if ( log.isDebugEnabled() )
            {
                log.debug("tagint = " + tagint + " ; tag = " + f.tag());
            }
            if ( (tagint >= startTag) && (tagint <= endTag) )
            {
                if ( log.isDebugEnabled() )
                {
                    log.debug("accepted (int) tag " + f.tag());
                }
                result.addElement(f);
            }
        }
        return result;
    }


    /**
     * Find first instance of a field with the specified tag.
     *
     * @param tag the tag to select
     *
     * @return The first Field with the specified tag, or null if there is none.
     */
    public Field getFirstField(String inTag)
    {
        Field result = null;
        int max = this.fields.size();

        for ( int i = 0; i < max; i++ )
        {
            Field f = (Field)fields.elementAt(i);
            if ( f.tag().equalsIgnoreCase(inTag) )
            {
                result = f;
                break;
            }
        }

        return result;
    }


    /**
     * Find first instance of a field with the specified tag.
     * Tag is specifed with its numeric value to reduce needless <code>String</code>
     * to <code>int</code> conversions.
     *
     * @param tag the tag to select
     *
     * @return The first Field with the specified tag, or null if there is none.
     */
    public Field getFirstField(int tag)
    {
        Field result = null;
        Field ele = null;
        int max = fields.size();

        for (int i=0; i < fields.size(); i++)
        {
            ele = (Field)fields.elementAt(i);
            if (ele.getTagint() == tag)
            {
                result= ele;
                break;
            }
        }

        return result;
    }


    /**
     * Return the first instance of the specified subfield
     * in the first instance of the specified field found
     * in the record. Most useful when this is known in
     * advance to be unique.
     *
     * @param tag    the tag to select
     * @param sfcode the subfield code to select
     *
     * @return The matching <code>MarcSubfield</code>, or null if none was found
     *
     * @throws IllegalArgumentException if the tag is less than "010",
     *           and is therefore not a variable length field,
     *           or if the subfield code is either null or not of length one.
     *
     *
     * @see MarcVblLengthField#firstSubfield(String tag)
     */
    public MarcSubfield getFirstSubfield(String tag, String sfcode)
    {
		// Variable length fields are all greater than 010
        if ( tag == null || tag.compareTo("010") < 0 )
        {
            throw new IllegalArgumentException();
        }

		// Get the first matching field
		MarcVblLengthField f = (MarcVblLengthField)getFirstField(tag);
        MarcSubfield subfld = null;

		if (f != null)
        {
            // And now get the first matching subfield if there is one
            subfld = f.firstSubfield(sfcode);
        }

		return subfld;
    }


    /**
     * Get the leader as a <code>MarcLeader</code> object.
     *
     * @return this record's leader
     */
    public MarcLeader getLeader()
    {  
        return leader;
    }


    /**
     * Get the leader as a <code>String</code>.
     *
     * @return string form of this record's leader
     */
    public String getLeaderValue()
    {
        return getLeader().value();
    }


    /**
     * Find all instances of a field whose tag and data match the supplied
     * regular expressions.
     *
     * @param tag  a regular expression to match the tag, e.g. "3\d5" finds 305, 315;
     *              null or "" matches all tags
     *
     * @param data  a regular expression used searched within field content;
     *               null or "" matches all content
     *
     * @return MarcFieldList of fields with mathcing tag and data elements
     *         or null if there are no matching fields.
     *
     * @see org.apache.regexp.RE
     */
    public MarcFieldList getRegExp(String tag, String data)
    {
		return getRegExp(tag, data, RE.MATCH_NORMAL);
    }


    /**
     * Find all instances of a field whose tag and data match the supplied
     * regular expressions.
     *
     * @param tag  a regular expression to match the tag, e.g. "3\d5" finds 305, 315;
     *              null or "" matches all tags
     *
     * @param data  a regular expression used searched within field content;
     *               null or "" matches all content
     *
     * @param matchFlags flags used to modify the regular expression matching,
     *                   e.g. RE.MATCH_CASEINDEPENDENT will make the match case
     *                   independent
     *
     * @return MarcFieldList of fields with mathcing tag and data elements
     *         or null if there are no matching fields.
     *
     * @see org.apache.regexp.RE
     */
    public MarcFieldList getRegExp(String tag, String data, int matchFlags )
    {
        MarcFieldList result = new MarcFieldList();

        try
        {
            // if RE field is null then treat as true for selection
            RE tagExp = null;
            RE dataExp = null;
            if (!StringUtil.isEmpty(tag))
            {
                tagExp = new RE(tag);
            }
            if (!StringUtil.isEmpty(data))
            {
                dataExp = new RE(data, matchFlags);
            }

            // Build the return value

            Field ele = null;
            int max = fields.size();

            for (int i=0; i < max; i++)
            {
                ele = (Field)fields.elementAt(i);
                if ( (tagExp == null) || tagExp.match(ele.tag()) )
                {
                    if ( (dataExp == null) || dataExp.match(ele.marcDump()) )
                    {
                        result.addElement(ele);
                    }
                }
            }
        }
        catch (RESyntaxException e)
        {
            throw new MarcParmException();
        }
        return result;
    }


    /**
     * Check the bibliographic level in the leader to see if this is a monograph record.
     * Note: this test is not adequate for all sources.
     *
     * @return true if this a monograph record, ohterwise false 
     */
    public final boolean isMonograph()          
    {
		return (leader.bibLevel() == 'm');
    }


    /**
     * Check the bibliographic level in the leader to see if this is a serial record.
     * Note: this test is not adequate for all sources.
     *
     * @return true if this a serial record, ohterwise false
     */
    public final boolean isSerial()
    {
		return (leader.bibLevel() == 's');
    }


    /**
     * Creates string version of this marc record.
     *
     * @return the marc record string
     */
    public String marcDump()
    {
        Enumeration     e   = fields.elements();
        StringBuffer    sb  = new StringBuffer(2500);
        MarcDirectory   dir = new MarcDirectory();

        int relativeAddress = 0;
        while( e.hasMoreElements() )
        {
            Field f = (Field) e.nextElement();
            String s = f.marcDump();
            if (s.length() > 9999) {
                throw new MarcSizeException("Field length exceeds maximum:" + s.length() + " - tag=" + f.tag);
            }
            sb.append(s);

            dir.addEntry(f.tag(), s.length(), relativeAddress);

            relativeAddress += s.length();
        }
        sb.append(Marc.EOR);

        setBaseAddress();
        setNominalLength(baseAddress() + relativeAddress + 1);
        String outDump = leader.marcDump() + dir.marcDump() + sb.toString();
        if (outDump.length() > 99999) {
            throw new MarcSizeException("Record length exceeds maximum:" + sb.length());
        }
        return outDump;
    }


    /**
     * Replace fields based on field id in list. If the field is not found
     * then add the field using the supplied insertion order.
     *
     * @param list   list of fields to be replaced in this marc record
     * @param order   field insertion order to use
     *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public void replaceFields(MarcFieldList list, int order)
    {
        Vector vlist = list.toVector();
        Field ele = null;
        int iLoc = -1;
        int max = vlist.size();

        for (int i = 0; i < max; i++)
        {
            ele = (Field)vlist.elementAt(i);
            iLoc = findId(ele.getId());
            if (iLoc >= 0)
            { // field found
                fields.setElementAt(ele, iLoc);
            }
            else
            { // field not found so add field using order
                setCopyField(ele, order);
            }
        }
    }


    /**
     * Set the bibliographic codes in the leader. The supplied <code>String</code> should be
     * 1 to 5 characters in length. The supplied <code>String</code> is used to overlay the
     * leader value beginning at offset 5, for the lenght of the <code>String</code>.
     *
     * @param bibinfo the new bibliographic codes
     *
     * @return the modified leader
     */
    
  
   public MarcLeader setBib(String bib)
    {
        if (StringUtil.isEmpty(bib) || (bib.length() > 5))
        {
            throw new MarcParmException(this, "parm length too long");
        }

        leader.setPos(5, bib);
        return leader;
    }


    /**
     * Add a copy of the supplied field to this marc record using the given
     * insertion order.
     *
     * @param f     the field to add
     * @param order the insertion order to use
     *
     * @return the field that was added
	 *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public Field setCopyField(Field f, int order)
    {
        Field cloneField = f.copy();
        return setField(cloneField, order);
    }


    /**
     * Add the supplied field to this marc record using the given
     * insertion order.
     *
     * @param f     the field to add
     * @param order the insertion order to use
     *
     * @return the field that was added
	 *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public Field setField(Field field, int order)
    {
		// set id in field as sequence
        field.setId(seq);
        seq++;

        if ( field.getTagint() == -1 )
        {
            status = Math.max(status, MARC_REC_ALHPA_TAGS);
        }


		// This code contains an evil bug, whereby if the user specifies
		// an unknown value for order the field is not added, but the method
		// returns successfully.
		// So I am rewriting it as a switch statement with a default action
		// and will remove this wretched code once the new code has been
		// tested.
//         if (order == END_LIST)
//         {
//             fields.addElement(field);
//             return field;
//         }
//         else if (order == TAG_ORDER)
//         {
//             Field ele = null;
//             int fmax = fields.size();
//             for ( int i = 0; i < fmax; i++ )
//             {
//                 ele = (Field)fields.elementAt(i);
//                 if ( ele.tag().compareTo(field.tag) > 0 )
//                 {
//                     fields.insertElementAt(field, i);
//                     return field;
//                 }
//             }
//             fields.addElement(field);
//         }
//         // add cloned field to end of nXX (1xx, 2xx)
//         else if (order == MARC_TAG_ORDER)
//         {
//             String inVal = field.tag().substring(0,1);
//             String tagVal = null;
//             Field ele = null;
//             int fmax = fields.size();
//             for ( int i = 0; i < fmax; i++ )
//             {
//                 ele = (Field)fields.elementAt(i);
//                 tagVal = ele.tag().substring(0,1);
//                 if ( tagVal.compareTo(inVal) > 0 )
//                 {
//                     fields.insertElementAt(field, i);
//                     return field;
//                 }
//             }
//             fields.addElement( field );
//         }

		// New and improved switch statement.

		// Loop index, defined here so we can use the value to insert the new field
		// outside of the search loop. This allows us to easily avoid multiple
		// return statements.
		int i = 0;
		int fmax = 0;

		switch ( order )
		{
		case END_LIST:
            fields.addElement(field);
			break;

		case TAG_ORDER:
            fmax = fields.size();
            for ( i = 0; i < fmax; i++ )
            {
                Field ele = (Field)fields.elementAt(i);
                if ( ele.tag().compareTo(field.tag) > 0 )
                {
                    break; // exit the for loop
                }
            }
            fields.insertElementAt(field, i);
			break;

		case MARC_TAG_ORDER:
            String inVal = field.tag().substring(0,1);
            fmax = fields.size();
            for ( i = 0; i < fmax; i++ )
            {
                Field ele = (Field)fields.elementAt(i);
                String tagVal = ele.tag().substring(0,1);
                if ( tagVal.compareTo(inVal) > 0 )
                {
					break; // exit the for loop
                }
            }
            fields.insertElementAt(field, i);
			break;

		default:
            fields.addElement(field);
			break;
		}

        return field;
    }


    /**
     * Add a field to this marc record using the supplied string values.
     *
     * @param tag  tag of field to be added
     * @param data  data containg indicators and subfields if necessary
     * @param delim  delimeter used to translate values in data to subfield codes
     * @param order  defines order fields are added to MARC record
	 *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public Field setField(String tag,
                          String data,
                          String delim,
                          int order)
    {
        Field field = null;
        if( tag.compareTo( "010" ) < 0 )
        {
            MarcFixedLengthField mfl = new MarcFixedLengthField(tag, data.length(), data);
            field = mfl;
        }
        else
        {
            MarcVblLengthField mvf = new MarcVblLengthField(tag, data, delim);
            field = mvf;
        }
        setField(field, order);
        return field;
    }


    /**
     * Add the Fields in the supplied <code>MarcFieldList</code>
     * to this marc record using the supplied insertion order.
     * The Fields are copied prior to being added, so changes to
     * the original set of fields will not affect the fields in
     * this marc record object.
     *
     * @param list   <code>MarcFieldList</code> to use
     * @param order  field insertion order to use
	 *
	 * @see #END_LIST
	 * @see #TAG_ORDER
	 * @see #MARC_TAG_ORDER
     */
    public void setFields(MarcFieldList list, int order)
    {
        Vector vlist = list.toVector();
        Field ele = null;
        int max = vlist.size();

        for (int i = 0; i < max; i++)
        {
            ele = (Field)vlist.elementAt(i);
            setCopyField(ele, order);
        }
    }


    /**
     * Set the leader from the supplied <code>MarcLeader</code> object.
     *
     * @param leader the new leader
     */
    public final void setLeader(MarcLeader leader)
    {
        this.leader = leader;
    }


    /**
     * Set leader from the supplied <code>String</code>.
     *
     * @param in - the new leader
     */
    public final void setLeaderValue(String in)
    {
        setLeader(new MarcLeader(in));
    }


    /**
     * Format the contents of this Marc record into a human readable
     * form and return the whole thing in a String.
     *
     * @return string containing formatted output
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(1500);
        sb.append(StringDisplay.EOL);

        setNominalLength();
        setBaseAddress();
        sb.append(leader.toString());

        Enumeration e = fields.elements();
        Field field = null;
        while( e.hasMoreElements() )
        {
            field = (Field)e.nextElement();
            sb.append(StringDisplay.EOL);
            sb.append(field.toString());
        }
        sb.append(StringDisplay.EOL);

        return sb.toString();
    }


    /**
     * Returns the record type from the leader.
     *
     * @return the record type
     */
    public final char typeOfRecord()
    {
        return leader.typeOfRecord();
    }


    /**
     * Apply character translations to this records variable length field's
     * subfield's values using the given TranslateTable object.
     *
     * @param tt the TranslateTable object to use
     */
    public void translateVblFields(TranslateTable tt)
    {
        int max = fields.size();
        for ( int i = 0; i < max; i++ )
        {
			Field fld = (Field)fields.elementAt(i);
            if ( fld instanceof MarcVblLengthField )
            {
                ((MarcVblLengthField)fld).translateValues(tt);
            }
        }
    }


	/**
	 * Return the number fields this record contains.
     *
	 * @return the field count
	 */
	public int getFieldCount()
	{
		return (fields == null ? 0 : fields.size());
	}


	/**
	 * Build a map keyed by field tag with the number of occurrences
	 * of that tag within this record and return that map.
	 * Because the map is <code>TreeMap</code> the entires may be
	 * retrieved in tag order.
	 *
	 * @return the map of field occurrence counts
	 */
	public TreeMap getFieldCountMap()
	{
		TreeMap fieldCountMap = new TreeMap();
        int max = fields.size();
        for ( int i = 0; i < max; i++ )
        {
            String ftag = ((Field)(fields.elementAt(i))).getTag();
			Integer fcnt = (Integer)fieldCountMap.get(ftag);
			int newCount = (fcnt == null ? 1 : fcnt.intValue() + 1);
			fieldCountMap.put(ftag, new Integer(newCount));
        }

		return fieldCountMap;
	}


    /**
     * Build a map of all the subfields and their counts contained in this record.
     *
     * @return the map of subfield occurrence counts
     */
	public TreeMap getSubFieldCountMap()
	{
		TreeMap sfCountMap = new TreeMap();
		TreeMap totalMap  = new TreeMap();

        int max = fields.size();
        for ( int i = 0; i < max; i++ )
        {
			Field fld = (Field)fields.elementAt(i);
            if ( fld instanceof MarcVblLengthField )
            {
                sfCountMap = ((MarcVblLengthField)fld).getSubFieldCountMap();
				if ( sfCountMap != null)
				{
					sumMaps(sfCountMap, totalMap);
				}
            }
        }

		return totalMap;
	}


    /**
     * Build a map of subfields and their counts for a particular field within
     * this record.
     *
     * @return the map of subfield occurrence counts
     */
	public TreeMap getSubFieldCountMap(String tag)
	{
		TreeMap sfCountMap = new TreeMap();
		TreeMap totalMap  = new TreeMap();

		if ( tag != null && tag.length() > 0 )
		{
			int max = fields.size();
			for ( int i = 0; i < max; i++ )
			{
				Field fld = (Field)fields.elementAt(i);
				if ( fld instanceof MarcVblLengthField )
				{
					if ( fld.getTag().equals(tag))
					{
						sfCountMap = ((MarcVblLengthField)fld).getSubFieldCountMap();
						if ( sfCountMap != null)
						{
							sumMaps(sfCountMap, totalMap);
						}
					}
				}
			}
		}

		return totalMap;
	}


    /**
     * Build a map of subfields and their counts for a set of fields within
     * this record. The result is a map of maps keyed by field tag.
     *
     * @return the map of subfield occurrence counts maps
     * The data type structure is String x Map(String x Integer)
     */
	public TreeMap getSubFieldCountMap(String[] tags)
	{
		TreeMap sfMutliTagMap = new TreeMap(); // tag x countMap

		if ( tags != null && tags.length > 0 )
		{
			int tcnt = tags.length;
			int max = fields.size();
			for ( int i = 0; i < max; i++ )
			{
				Field fld = (Field)fields.elementAt(i);
				if ( fld instanceof MarcVblLengthField )
				{
					for ( int j = 0; j < tcnt; j++ )
					{
						if ( fld.getTag().equals(tags[j]))
						{
							String ftag = fld.getTag();
							TreeMap sfDtlMap = (TreeMap)sfMutliTagMap.get(ftag);
							if ( sfDtlMap == null )
							{
								sfDtlMap = new TreeMap();
							}
							TreeMap sfCountMap = ((MarcVblLengthField)fld).getSubFieldCountMap();
							if ( sfCountMap != null)
							{
								sumMaps(sfCountMap, sfDtlMap);
							}
							sfMutliTagMap.put(ftag, sfDtlMap);
							break;
						}
					}
				}
			}
		}

		if ( log.isDebugEnabled() )
		{
			dumpMap(sfMutliTagMap, "SF_MultiTagMap");
		}

		return sfMutliTagMap;
	}


    /**
     * Build a map of subfields and their counts for all of the fields within
     * this record. The result is a map of maps keyed by field tag.
     *
     * @return the map of subfield occurrence counts maps
     * The data type structure is String x Map(String x Integer)
     */
	public TreeMap getSubFieldCountMapByField()
	{
		TreeMap sfSummaryMap = new TreeMap(); // tag x countMap

        int max = fields.size();
        for ( int i = 0; i < max; i++ )
        {
			Field fld = (Field)fields.elementAt(i);
            if ( fld instanceof MarcVblLengthField )
            {
				String ftag = fld.getTag();
				TreeMap sfDtlMap = (TreeMap)sfSummaryMap.get(ftag);
				if ( sfDtlMap == null )
				{
					sfDtlMap = new TreeMap();
				}
                TreeMap sfCountMap = ((MarcVblLengthField)fld).getSubFieldCountMap();
				if ( sfCountMap != null)
				{
					sumMaps(sfCountMap, sfDtlMap);
				}
				sfSummaryMap.put(ftag, sfDtlMap);
            }
        }

		if ( log.isDebugEnabled() )
		{
			dumpMap(sfSummaryMap, "SF_SummaryMap");
		}

		return sfSummaryMap;
	}


    /**
     * Accumulate the values of a one map into another map. Both maps
     * must be keyed by the same type and have values of type Integer.
     *
     * @param dataMap the source map to accumulate
     * @param totalMap the target map to receive the accumulated values
     * @return true if successful, false if either map is null
     */
	public boolean sumMaps(Map dataMap, Map totalMap)
	{
		boolean bRet = true;

		if ( dataMap == null )
		{
			bRet = false;
			log.error("One of the maps is null: dataMap");
		}

		if ( totalMap == null )
		{
			bRet = false;
			log.error("One of the maps is null: totalMap");
		}

		if ( bRet )
		{
			Set dataEntries = dataMap.entrySet();
			Iterator iter = dataEntries.iterator();
			while ( iter.hasNext() )
			{
				Map.Entry dataEntry = (Map.Entry)iter.next();

				Object dataKey = (Object)(dataEntry.getKey());
				Integer dataCount = (Integer)(dataEntry.getValue());
				Integer totalCount = (Integer)(totalMap.get(dataKey));

				int dataCnt = (dataCount == null ? 0 : dataCount.intValue());
				int totalCnt = (totalCount == null ? 0 : totalCount.intValue());

				totalCnt += dataCnt;
				totalMap.put(dataKey, new Integer(totalCnt));
			}
		}

		return bRet;
	}


    /**
     * A private method used during debugging to dump the contents of
     * a Map object to the log.
     *
     * @param map the Map to dump
     * @param name a name for the Map
     */
	private void dumpMap(Map map, String name)
	{
		int procCnt = 0;
		if ( map == null )
		{
			log.debug("Map " + name + " is null");
		}
		else
		{
			log.debug("Dumping " + name + " Map:");
			Set mapEntries = map.entrySet();
			if ( mapEntries.size() == 0)
			{
				log.debug("  " + name + " map is empty");
			}
			else
			{
				log.debug("  " + name + " map has " + mapEntries.size() + " entries");
				Iterator iter = mapEntries.iterator();
				while ( iter.hasNext() )
				{
					StringBuffer sb = new StringBuffer(100);
					Map.Entry entry = (Map.Entry)iter.next();
					Object key   = entry.getKey();
					Object value = entry.getValue();

					sb.append("  Key ");
					if ( key == null)
					{
						sb.append("= null ");
					}
					else
					{
						sb.append("(Type: ");
						sb.append(key.getClass().getName());
						sb.append(") = ' ");
						sb.append(key);
						sb.append(" ' ");
					}

					sb.append(" value ");
					if ( value == null)
					{
						sb.append("= null ");
					}
					else
					{
						sb.append("(Type: ");
						sb.append(value.getClass().getName());
						sb.append(") = ' ");
						sb.append(value);
					}
					log.debug(sb.toString());
					procCnt++;
				}
			}
		}
		log.debug("Processed " + procCnt + " entries for " + name);
	}


	/**
	 * Copy a Vector of Fields into a MarcFieldList.
     *
	 * Why this method is in this class, and not a MarcFieldList
     * constructor is a real mystery.
     *
	 * @param inList list to be copied
	 *
	 * @return MarcFieldList - copied fields from inList
	 */
	protected MarcFieldList buildFieldList(Vector inList)
	{
		MarcFieldList returnList = new MarcFieldList();

		int max = inList.size();
		for (int i = 0; i < max; i++)
		{
			Field fld = (Field)inList.elementAt(i);
			returnList.addElement(fld);
		}
		return returnList;
	}

	/**
	 * Reset this object to the uninitialized state.
	 */
	protected void clear()
	{
		leader = new MarcLeader();
        fields = new Vector(30, 10);
		seq    = 0;
		status = MARC_REC_NOT_BUILT;
	}


	/**
	 * Return current base address of data.
	 */
	private final int baseAddress()
	{
		return 24 + fields.size() * 12 + 1;
	}


	/**
	 * Return nominal record length, i.e., length
	 * as recorded in leader
	 */
	private final int nominalLength()
	{
		return leader.getRecordLength();
	}


	/**
	 * Return actual record length, i.e., sum of
	 * lengths of leader, directory, and fields
	 */
	private final int length()
	{
		int len = baseAddress();                   // MarcLeader
		Enumeration e = fields.elements();

		while( e.hasMoreElements() )
		{
			Object o = e.nextElement();
			if( o instanceof MarcFixedLengthField )
				len += ((MarcFixedLengthField)o).length();
			else if ( o instanceof MarcVblLengthField )
				len += ((MarcVblLengthField)o).length();
		}
		return len + 1;                 // MarcRecord terminator
	}


	/**
	 * Find the field with the matching field id and return its index
     * into the fields <code>Vector</code>. If no match is found, return -1.
     *
	 * @param id field id to search for
     *
     * @return the matching field's index, or -1 if no match is found
	 */
	private int findId(int id)
	{
        int iRet = -1;
		int max = fields.size();
		for (int i = 0; i < max; i++)
		{
			Field fld = (Field)fields.elementAt(i);
			if (fld.getId() == id)
            {
                iRet = i;
                break;
            }
		}
		return iRet;
	}


	/**
	 * Set base address of data in leader to current value.
	 */
	private final void setBaseAddress()
	{
		leader.setBaseAddress(baseAddress());
	}


	/**
	 * Set nominal record length.
	 */
	private final void setNominalLength(int len)
	{
		leader.setRecordLength(len);
	}


	/**
	 * Set nominal record length to current record length.
	 */
	private final void setNominalLength()
	{
		leader.setRecordLength(length());
	}


	/**
	 * Convert string version of tag to numeric form.
     *
	 * @param tag sting form of tag
	 *
	 * @return int form of string tag value
	 */
	private int string2Tag(String tag)
	{
		int n = -1;
		try
		{
			n = Integer.parseInt(tag);
			if (n < 0 || n > 999)
            {
                throw new IllegalArgumentException();
            }
		}
		catch (NumberFormatException nfe)
		{
			throw new IllegalArgumentException();
		}
		return n;
	}
}
