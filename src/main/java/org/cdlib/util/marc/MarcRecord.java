package org.cdlib.util.marc;

import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.cdlib.util.string.StringUtil;

/**
 * Class representing a MARC record. Functionality is provided for both
 * parsing MARC records and providing access to the data they contain.
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: MarcRecord.java,v 1.7 2003/03/19 22:22:27 loy Exp $
 */
public class MarcRecord extends MarcBaseRecord
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(MarcRecord.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/MarcRecord.java,v 1.7 2003/03/19 22:22:27 loy Exp $";

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
     * The default order in which to add new fields.
     *
	 * @see MarcBaseRecord#TAG_ORDER
     */
    private int defaultOrder = TAG_ORDER;


    /**
     * Instantiate a new <code>MarcRecord</code> object with default leader values:
     * status is 'n', type is 'a', and bibliographic level is 'm'.
     *
     * @see MarcBaseRecord#MarcBaseRecord()
     */
    public MarcRecord()
    {
        super();
        setRecordStatus("n");
        setRecordType("a");
        setBibLvl("m");
        setDescCatForm("a");
    }


    /**
     * Instantiate a new <code>MarcRecord</code> object from
     * the suppied <code>MarcRecord</code> object.
     *
     * @see MarcBaseRecord#MarcBaseRecord(MarcBaseRecord rec)
     */
    public MarcRecord(MarcRecord rec)
    {
        super(rec);
        this.defaultOrder = rec.defaultOrder;
    }


    /**
     * Instatiate a new <code>MarcRecord</code> object using the fields in the
     * supplied <code>MarcRecord</code> using the insertion order specified.
     *
     * @param list   <code>MarcFieldList</code> to use
     * @param order  field insertion order to use
	 *
     * @see MarcBaseRecord#MarcBaseRecord(MarcFieldList list, int order)
	 * @see MarcBaseRecord#END_LIST
	 * @see MarcBaseRecord#TAG_ORDER
	 * @see MarcBaseRecord#MARC_TAG_ORDER
     */
    public MarcRecord(MarcFieldList list, int order)
    {
        super(list, order);
    }


    /**
     * Create a new <code>MarcRecord</code> object from a string of raw marc data,
     * inserting fields in the specified order.
     *
     * @param rawMarc string of marc data
     * @param order   field insertion order to use
     *
     * @exception MarcFormatException when the the raw marc data cannot be parsed
     *
     * @see MarcBaseRecord#MarcBaseRecord(String rawMarc, int order)
	 * @see MarcBaseRecord#END_LIST
	 * @see MarcBaseRecord#TAG_ORDER
	 * @see MarcBaseRecord#MARC_TAG_ORDER
     */
    public MarcRecord(String rawMarc, int order)
    {
        this();
        build(rawMarc, order);
    }


    /**
     * Create a new <code>MarcRecord</code> object from a string of raw marc data.
     * Insertion order is defaulted to END_LIST.
	 *
     * @param rawMarc string of marc data
     *
     * @exception MarcFormatException when the the raw marc data cannot be parsed
     *
     * @see MarcBaseRecord#MarcBaseRecord(String rawMarc)
	 * @see MarcBaseRecord#END_LIST
     */
    public MarcRecord(String rawMarc)
    {
        this(rawMarc, END_LIST);
    }


    /**
     * Initialize this object from another <code>MarcRecord</code> object.
     *
     * @see MarcBaseRecord#build(MarcBaseRecord rec)
     */
    public void build(MarcRecord rec)
    {
        super.build(rec);
        this.defaultOrder = rec.defaultOrder;
    }


    /**
     * Add a copy of the supplied field to the end of this record.
     *
     * @param f the field to be added
     *
     * @see MarcBaseRecord#setCopyField(Field f, int order)
     * @see Field#copy()
     */
    public Field addField(Field f)
    {
        return setCopyField(f, END_LIST);
    }


    /**
     * Find all instances of a field with a specified tag or range of tags.
     * This method invokes <code>MarcBaseRecord.getFields(String startTag, String endTag)</code>
     * after converting the argument to a range beginning a stargTag and ending with endTag.
     *
     * @param tag Either a single tag like "650" or a wildcard expression,
     *            e.g. "6XX" or "6xx". Or a range of tags, e.g. "800-830".
     *
     * @return MarcFieldList of fields whose tags are in the requested
     *         range, or null if there are no matching fields.
     *
     * @exception IllegalArgumentException if the argument is null,
     *            or the length is less than 3, or greater that 7.
     *
     * @see MarcBaseRecord#getFields(String startTag, String endTag)
     */
    public MarcFieldList allFields(String tag)
    {
        if ( tag == null || tag.length() < 3 || tag.length() > 7 )
        {
            throw new IllegalArgumentException();
        }

        String startTag = getStartTag(tag);
        String endTag   = getEndTag(tag);

        if ( log.isDebugEnabled() )
        {
            log.debug("tag = '" + tag
                      + "' startTag = '" + startTag
                      + "' endTag = '" + endTag + "'");
        }

        return getFields(startTag, endTag);
    }


    /**
     * Get the beginning tag for a wildcard range, e.g. "6xx" return "600",
     * invoke <code>getRangeStartTag(String)</code> if a the argument is a
     * hyphenated range, e.g. "920-955".
     *
     * @param tag the range to parse
     *
     * @return the starting tag for the range
     *
     * @see #getRangeStartTag(String)
     */
    private String getStartTag(String tag)
    {
        String startTag = null;
        int    tagLen = 0;

        if ( tag != null && (tagLen = tag.length()) > 0 )
        {
            if ( tag.indexOf("-") > -1)
            {
                startTag = getRangeStartTag(tag);
            }
            else
            {
                char[] tc = new char[tagLen];
                tag.getChars(0, tagLen, tc, 0);

                for ( int i = 0; i < tagLen; i++ )
                {
                    if ( tc[i] == 'x' || tc[i] == 'X' )
                    {
                        tc[i] = '0';
                    }
                }
                startTag = new String(tc);
            }
        }

        return startTag;
    }


    /**
     * Get the beginning tag from a hyphenated range, e.g. "920-955" returns "920".
     *
     * @param tag the range to parse
     *
     * @return the starting tag for the range
     */
    private String getRangeStartTag(String tag)
    {
        String startTag = null;
        int    endIndex = tag.indexOf("-");

        if ( endIndex > 0 )
        {
            startTag = tag.substring(0, endIndex);
        }

        return startTag;
    }


    /**
     * Get the ending tag for a wildcard range, e.g. "6xx" returns "699",
     * invoke <code>getRangeStartTag(String)</code> if a the argument is a
     * hyphenated range, e.g. "920-955".
     *
     * @param tag the range to parse
     *
     * @return the ending tag for the range
     *
     * @see #getRangeEndTag(String)
     */
    private String getEndTag(String tag)
    {
        String endTag = null;
        int    tagLen = 0;

        if ( tag != null && (tagLen = tag.length()) > 0 )
        {
            if ( tag.indexOf("-") > -1)
            {
                endTag = getRangeEndTag(tag);
            }
            else
            {
                char[] tc = new char[tagLen];
                tag.getChars(0, tagLen, tc, 0);

                for ( int i = 0; i < tagLen; i++ )
                {
                    if ( tc[i] == 'x' || tc[i] == 'X' )
                    {
                        tc[i] = '9';
                    }
                }
                endTag = new String(tc);
            }
        }

        return endTag;
    }


    /**
     * Get the ending tag from a hyphenated range, e.g. "920-955" returns "955".
     *
     * @param tag the range to parse
     *
     * @return the ending tag for the range
     */
    private String getRangeEndTag(String tag)
    {
        String endTag     = null;
        int    startIndex = tag.indexOf("-") + 1;

        if ( startIndex > 0 )
        {
            endTag = tag.substring(startIndex);
        }

        return endTag;
    }


    /**
     * Return a MarcFieldList containing all fields in this MarcRecord object.
     *
     * @return a MarcFieldList containing the fields for this MarcRecord
     *
     * @see MarcBaseRecord#getFields()
     */
    public MarcFieldList allFields()
    {
        return getFields();
    }


    /**
     * Get the fields within the specified tag range.
     *
     * @param fromTag the beginning tag in the range
     * @param fromTag the ending tag in the range
     *
     * @see MarcBaseRecord#getFields(String startTag, String endTag)
     */
    public MarcFieldList allFields(String fromTag, String toTag)
    {
        return getFields(fromTag, toTag);
    }


    /**
     * Get the fields within the specified tag range with numeric tags.
     *
     * @param fromTag the beginning tag in the range
     * @param fromTag the ending tag in the range
     *
     * @see MarcBaseRecord#getFields(int startTag, int endTag)
     */
    public MarcFieldList allFields(int fromTag, int toTag)
    {
        return getFields(fromTag, toTag);
    }


    /**
     * Delete all instances of a field with a specified tag
     * or within a range of tags.
     *
     * @param fields Either a single tag like "650" or a wildcard expression,
     *               e.g. "6XX" or "6xx". Or a range of tags, e.g. "800-830".
     *
     * @exception IllegalArgumentException if the argument is null,
     *            or the length is less than 3, or greater that 7.
     *
     * @see #allFields(String tag)
     * @see MarcBaseRecord#deleteFields(MarcFieldList list)
     */
    public int deleteFields(String fields)
    {
        MarcFieldList mfl = allFields(fields);
        return deleteFields(mfl);
    }


    /**
     * Extracts the requested fields and subfields with default delimiters,
     * "|" for the field delimiter, and " " for the subfield delimiter.
     * The subfields in the subfieldlist will be included in the output.
     *
     * @param tags              blank separated list of tags to select
     *
     * @param subfieldlist      a string containing the subfield codes to either
     *                          include or exclude, depending upon the include parameter
     *
     * @return the string containing the extracted data
     *
     * @see #extractMarcFields(String, String, boolean, String, String)
     */
    public String extractMarcFields(String tags,
                                    String subfieldlist)
    {
        return extractMarcFields(tags, subfieldlist, true);
    }


    /**
     * Extracts the requested fields and subfields with default delimiters,
     * "|" for the field delimiter, and " " for the subfield delimiter.
     * The include parameter dictates whether subfields in the subfieldlist
     * will be included in the output, or excluded from the output.
     *
     * @param tags              blank separated list of tags to select
     *
     * @param subfieldlist      a string containing the subfield codes to either
     *                          include or exclude, depending upon the include parameter
     *
     * @param include           flag indicating whether to include (true) or
     *                          exclude (false) the specifed subfields
     *
     * @return the string containing the extracted data
     *
     * @see #extractMarcFields(String, String, boolean, String, String)
     */
    public String extractMarcFields(String tags,
                                    String subfieldlist,
                                    boolean include)
    {
        return extractMarcFields(tags, subfieldlist, include, "|");
    }

    /**
     * Extracts the requested fields and subfields using the specified
     * field delimiter, and " " for the subfield delimiter.
     * The include parameter dictates whether subfields in the subfieldlist
     * will be included in the output, or excluded from the output.
     *
     * @param tags              blank separated list of tags to select
     *
     * @param subfieldlist      a string containing the subfield codes to either
     *                          include or exclude, depending upon the include parameter
     *
     * @param include           flag indicating whether to include (true) or
     *                          exclude (false) the specifed subfields
     *
     * @param fieldDelimiter    delimiter to be used between extracted fields
     *
     * @return the string containing the extracted data
     *
     * @see #extractMarcFields(String, String, boolean, String, String)
     */
    public String extractMarcFields(String tags,
                                    String subfieldlist,
                                    boolean include,
                                    String fieldDelimiter)
    {
        return extractMarcFields(tags, subfieldlist, include, fieldDelimiter, " ");
    }


    /**
     * Returns a <code>String</code> containing the contents of the fields
     * specified in the tag list. The subfieldcodes list is used as a filter.
     * if the include parameter is true, the values of the subfields in the
     * subfieldlist are included, otherwise the values of the subfields not
     * in the subfieldlist are included. Fields are separated by the specified
     * fieldDelimiter, and subfields are separated by the subfieldDelimiter.
     *
     * @param tags              blank separated list of tags to select
     *
     * @param subfieldlist      a string containing the subfield codes to extract
     *                          or exclude, depending upon the include parameter
     *
     * @param include           flag indicating whether to include (true) or
     *                          exclude (false) the specifed subfields
     *
     * @param fieldDelimiter    delimiter to be used between extracted fields
     *
     * @param subfieldDelimiter delimiter to be used between extracted subfields
     *
     * @return the string containing the extracted data
     */
    public String extractMarcFields(String tags,
                                    String subfieldlist,
                                    boolean include,
                                    String fieldDelimiter,
                                    String subfieldDelimiter)
    {
        String result = new String();
        StringTokenizer st = new StringTokenizer (tags);
        while ( st.hasMoreTokens() )
        {
            String token = st.nextToken();
            MarcFieldList fv = allFields(token);
            if ( fv != null )
            {
                for ( int i = 0; i < fv.size(); i++ )
                {
                    Field f = (Field)fv.elementAt(i);
                    // If this is a fixed field, ignore the subfield codes
                    if ( f.tag.compareTo("010") < 0 )
                    {
                        MarcFixedLengthField temp = (MarcFixedLengthField)f;
                        result = StringUtil.adjoin(result,
                                                   ((FixedLengthData)temp.data).dump(),
                                                   fieldDelimiter);
                    }
                    else
                    {
                        MarcVblLengthField temp = (MarcVblLengthField)f;
                        Vector sv = temp.subfields(subfieldlist, include);
                        if ( sv.size() > 0 )
                        {
                            StringBuffer sb = new StringBuffer(200);
                            sb.append(((MarcSubfield)sv.elementAt(0)).value());
                            for ( int j = 1; j < sv.size(); j++ )
                            {
                                sb.append(subfieldDelimiter);
                                sb.append(((MarcSubfield)sv.elementAt(j)).value());
                            }
                            result = StringUtil.adjoin(result,
                                                       sb.toString(),
                                                       fieldDelimiter);
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * Returns a <code>Vector</code> of subfields from a given list of tags.
     *
     * @param tags         blank separated list of tags to search
     *
     * @param subfieldlist a string containing the subfield codes to extract
     *                     or exclude, depending upon the include parameter
     *
     * @param include      flag indicating whether to include (true) or
     *                     exclude (false) the specifed subfields
     *
     * @return A vector containing the selected <code>MarcSubfield</code> objects,
     *         or null, if none were selected
     */
    public Vector extractMarcSubfields(String tags,
                                       String subfieldcodes,
                                       boolean include)
    {
        Vector result = new Vector (25);
        StringTokenizer st = new StringTokenizer (tags);
        while ( st.hasMoreTokens() )
        {
            String token = st.nextToken();
            MarcFieldList fv = allFields (token);
            if ( fv != null )
            {
                int fmax = fv.size();
                for ( int i = 0; i < fmax; i++ )
                {
                    Vector sv = ((MarcVblLengthField)fv.elementAt(i)).subfields(subfieldcodes, include);
                    int smax = sv.size();
                    for ( int j = 0; j < smax; j++)
                    {
                        result.addElement(sv.elementAt(j));
                    }
                }
            }
        }
        return result;
    }


    /**
     * Return the Marc bibliographic level from the leader.
     *
     * @return the Marc bibliographic level
     */
    public String getBibLvl()
    {
        return (getLeader().getPos(7, 7));
    }


    /**
     * Return the Marc encoding scheme from the leader.
     *
     * @return the Marc encoding scheme
     */
    public String getCodingScheme()
    {
        return (getLeader().getPos(9, 9));
    }


    /**
     * Return the Marc control type from the leader.
     *
     * @return the Marc control type
     */
    public String getControlType()
    {
        return (getLeader().getPos(8, 8));
    }


    /**
     * Return the default field insertion order for this object.
     *
     * @return the new default field insertion order
	 *
	 * @see MarcBaseRecord#END_LIST
	 * @see MarcBaseRecord#TAG_ORDER
	 * @see MarcBaseRecord#MARC_TAG_ORDER
     */
    public int getDefaultOrder()
    {
        return defaultOrder;
    }


    /**
     * Return the Marc descriptive cataloging form code from the leader.
     *
     * @return the Marc descriptive cataloging form code
     */
    public String getDescCatForm()
    {
        return (getLeader().getPos(18, 18));
    }


    /**
     * Return the Marc encoding level from the leader.
     *
     * @return the Marc encoding level
     */
    public String getEncLvl()
    {
        return (getLeader().getPos(17, 17));
    }


    /**
     * Return the Marc bibliographic level from the leader.
     *
     * @return the Marc bibliographic level
     */
    public String getLinkRec()
    {
        return (getLeader().getPos(19, 19));
    }

    /**
     * Adds a new fixed length field to this marc record, using the tag
     * and data provided, and returns a reference to the new field.
     *
     * @return the new <code>MarcFixedLengthField</code>
     *
     * @exception MarcParmException</code> if the tag is greater than "009"
     */
    public MarcFixedLengthField getNewFixField(String tag, String indata)
    {
        MarcFixedLengthField field = null;

        if( tag.compareTo("010") < 0 )
        {
            field = new MarcFixedLengthField(tag, indata);
        }
        else
        {
            throw new MarcParmException(this, "invalid tag for fixed length field: " + tag);
        }

        setField(field, defaultOrder);

        return field;
    }


    /**
     * Adds a new fixed length field to this marc record, using the tag provided,
     * and returns a reference to the new field.
     *
     * @return the new <code>MarcFixedLengthField</code>
     *
     * @exception MarcParmException if the tag is greater than "009"
     */
    public MarcFixedLengthField getNewFixField(String tag)
    {
        MarcFixedLengthField field = null;

        if( tag.compareTo("010") < 0 )
        {
            field = new MarcFixedLengthField(tag, " ");
        }
        else
        {
            throw new MarcParmException(this, "invalid tag for fixed length field: " + tag);
        }

        setField(field, defaultOrder);

        return field;
    }


    /**
     * Adds a new variable length field to this marc record, using the tag
     * and indicators provided, and returns a reference to the new field.
     *
     * @return the new <code>MarcVblLengthField</code>
     *
     * @exception MarcParmException if the tag is less than "010"
     */
    public MarcVblLengthField getNewVblField(String tag, String indicators)
    {
        MarcVblLengthField field = null;

        if( tag.compareTo("010") < 0 )
        {
            throw new MarcParmException(this, "invalid tag for variable length field: " + tag);
        }
        else
        {
            field = new MarcVblLengthField(tag);
            try {
                field.setIndicators(indicators);
            }
            catch (MarcFormatException e) {
                log.warn(e.toString() + " tag=" + tag + " indicators=" + indicators);
            }
        }

        setField(field, defaultOrder);

        return field;
    }

    /**
     * Return the Marc record status from the leader.
     *
     * @return the Marc record type
     */
    public String getRecordStatus()
    {
        return (getLeader().getPos(5, 5));
    }


    /**
     * Return the Marc record type from the leader.
     *
     * @return the Marc record type
     */
    public String getRecordType()
    {
        return (getLeader().getPos(6, 6));
    }


    /**
     * The first field with the specified tag is selected. If the tag is less
     * than "010", i.e. a fixed length field, then the value of this field
     * is returned. Otherwise, the value of the first subfield specified is
     * returned. If the requested field is a variable length field and the
     * first occurrence of that field does not contain the the request subfield
     * then a null string is returned.
     *
     * @param tag    tag to select
     * @param sfcode subfield code to select
     *
     * @return A String containing the selected value, or null if no match
	 *         was found
     */
    public String getFirstValue(String tag,
                                String sfcode)
    {
        String value = null;
        Field  field = getFirstField(tag);

        if (field != null)
		{
            if( tag.compareTo( "010" ) < 0 )
            {
                value = field.value();
            }
            else
            {
                MarcSubfield subfld = ((MarcVblLengthField)field).firstSubfield(sfcode);
                if (subfld != null)
                {
                    value = subfld.value();
                }
            }
		}

        return value;
    }


    /**
     * Returns the value of all fields with this tag, if the the tag is less
     * than "010", i.e. a fixed length field. If the the tag indicates a
     * variable length field then the values of the the specified subfields
     * found within the selected fields are returned.
     *
     * @param tag    tag to select
     * @param sflist subfield codes to select
     *
     * @return a <code>Vector</code> of Strings containing selected values
     */
    public Vector getValues(String tag,
                            String sflist)
    {
        Vector        retList = new Vector();
        MarcFieldList fields  = allFields(tag);
        Field         field   = null;
        String        value   = null;

        if (fields != null)
        {
            int fmax = fields.size();
            if( tag.compareTo("010") < 0 )
            {
                for ( int i = 0; i < fmax; i++ )
                {
                    field = (Field)fields.elementAt(i);
                    value = field.value();
                    if ( ! StringUtil.isEmpty(value) )
                    {
                        retList.addElement(value);
                    }
                }
            }
            else
            {
                for ( int i = 0; i < fmax; i++ )
                {
                    field = (Field)fields.elementAt(i);
                    // for each tag matching tag and subfield matching
                    Vector sublist = ((MarcVblLengthField)field).subfields(sflist, true);
                    MarcSubfield subfield = null;
                    int smax = sublist.size();
                    for ( int sub = 0; sub < smax; sub++ )
                    {
                        subfield = (MarcSubfield)sublist.elementAt(sub);
                        value = subfield.value();
                        if ( ! StringUtil.isEmpty(value) )
                        {
                            retList.addElement(value);
                        }
                    }
                }
            }
        }

        return (retList.size() == 0 ? null : retList);
    }


    /**
     * Set the Marc bibliographic level in the leader.
     *
     * @param in the new bibliographic level
     */
    public void setBibLvl(String in)
    {
        getLeader().setPos(7, in);
    }


    /**
     * Set the Marc encoding scheme in the leader.
     *
     * @param in the new encoding scheme
     */
    public void setCodingScheme(String in)
    {
        getLeader().setPos(9, in);
    }


    /**
     * Set the Marc control type in the leader.
     *
     * @param in the new control type
     */
    public void setControlType(String in)
    {
        getLeader().setPos(8, in);
    }


    /**
     * Set the default field insertion order for this object.
	 *
     * @param in the new default field insertion order
	 *
	 * @see MarcBaseRecord#END_LIST
	 * @see MarcBaseRecord#TAG_ORDER
	 * @see MarcBaseRecord#MARC_TAG_ORDER
     */
    public void setDefaultOrder(int in)
    {
        defaultOrder = in;
    }


    /**
     * Set the Marc descriptive cataloging form code in the leader.
	 *
     * @param in the new descriptive cataloging form code
     */
    public void setDescCatForm(String in)
    {
        getLeader().setPos(18, in);
    }


    /**
     * Set the Marc encoding level in the leader.
	 *
     * @param in the new encoding level
     */
    public void setEncLvl(String in)
    {
        getLeader().setPos(17, in);
    }


    /**
     * Set the Marc link record indicator in the leader.
	 *
     * @param in the new link record indicator
     */
    public void setLinkRec(String in)
    {
        getLeader().setPos(19, in);
    }


    /**
     * Set Marc record status in the leader.
     *
     * @param in the new record status
     */
    public void setRecordStatus(String in)
    {
        getLeader().setPos(5, in);
    }

    /**
     * Set the Marc record Type in the leader.
	 *
     * @param in the new record type
     */
    public void setRecordType(String in)
    {
        getLeader().setPos(6, in);
    }

}
