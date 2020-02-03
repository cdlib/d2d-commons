package org.cdlib.util.marc;

import org.apache.log4j.Logger;
import org.cdlib.util.string.StringUtil;

/**
 * Abstract representation of a marc field or subfield.
 * All concrete implementations of marc field like entities
 * should extend this this class.
 *
 * @author <a href="mailto:rmoon@library.berkeley.edu">Ralph Moon</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: Field.java,v 1.9 2003/01/03 18:06:31 smcgovrn Exp $
 */
public abstract class Field implements Comparable
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(Field.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/Field.java,v 1.9 2003/01/03 18:06:31 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.9 $";

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
     * Field tag or subfield code.
     */
    String tag;

    /**
     * Tag value as an int, not applicable to sub-fields.
     * Set by the constructor if the tag length is 3 and the tag
     * is numeric, otherwise set to -1.
     */
    int tagint;

    /**
     * Data for this field or subfield. For fixed length fields and subfields
     * this will be a String, for variable length fields this will be a Vector.
     */
    Object data;

    /**
     * Identifier for this field.
     */
    int id = -1;


    /**
     * Create an initialized Field.
     *
     * @param tag  the tag for this object
     * @param data the data for this object
     */
    Field(String tag, Object data)
    {
        this.tag = tag.trim();
        this.data = data;
        setTagint(this.tag);

        if ( log.isDebugEnabled() )
        {
            log.debug(new StringBuffer(50)
                      .append("F(S,O): t = '").append(tag)
                      .append("' ti = '").append(tagint)
                      .append("' d = '").append(data)
                      .append("'").toString());
        }
    }


    /**
     * Create an initialized Field.
     *
     * @param tag  the tag for this object
     * @param data the data for this object
     */
    Field(char tag, Object data)
    {
        this.tag = String.valueOf(tag);
        this.data = data;
        this.tagint = -1;

        if ( log.isDebugEnabled() )
        {
            log.debug(new StringBuffer(500)
                      .append("F(c,O): t = '").append(tag)
                      .append("' d = '").append(data)
                      .append("'").toString());
        }
    }


    /**
     * Create an initialized Field.
     *
     * @param tag  the tag for this object
     * @param data the data for this object
     */
    Field(char[] tag, Object data)
    {
        this.tag = (new String(tag)).trim();
        this.data = data;
        setTagint(this.tag);

        if ( log.isDebugEnabled() )
        {
            log.debug(new StringBuffer(500)
                      .append("F(c[],O): t = '").append(tag)
                      .append("' d = '").append(data)
                      .append("'").toString());
        }
    }


    /**
     * Create an new Field object with no data element.
     *
     * @param tag the tag for this object
     */
    Field(String tag)
    {
        this(tag, null);
        setTagint(this.tag);

        if ( log.isDebugEnabled() )
        {
            log.debug(new StringBuffer(50)
                      .append("F(S): t = '").append(tag)
                      .append("'").toString());
        }
    }

    /**
     * Create an new Field object with no data element.
     *
     * @param tag the tag for this object
     */
    Field(char tag)
    {
        this(tag, null);

        if ( log.isDebugEnabled() )
        {
            log.debug(new StringBuffer(50)
                      .append("F(c): t = '").append(tag)
                      .append("'").toString());
        }
    }


    /**
     * Create an new Field object with no data element.
     *
     * @param tag the tag for this object
     */
    Field(char[] tag)
    {
        this(tag, null);
        setTagint(this.tag);

        if ( log.isDebugEnabled() )
        {
            log.debug(new StringBuffer(50)
                      .append("F(c[]): t = '").append(tag)
                      .append("'").toString());
        }
    }


    /**
     * Set the tagint element for this object from the tag value.
     */
    protected void setTagint()
    {
        if ( tag == null || tag.length() != 3 )
        {
            tagint = -1;
        }
        else
        {
            try
            {
                tagint = StringUtil.ascii2int(tag);
            }
            catch (RuntimeException e)
            {
                tagint = -1;
            }
        }
    }


    /**
     * Set the tagint element for this object from the supplied tag value.
	 *
	 * @param tag the tag to use
     */
    private void setTagint(String tag)
    {
        if ( tag.length() != 3 )
        {
            this.tagint = -1;
        }
        else
        {
            try
            {
                this.tagint = StringUtil.ascii2int(tag);
            }
            catch (RuntimeException e)
            {
                this.tagint = -1;
            }
        }
    }


    /**
     * Set the tagint element for this object from the supplied tag value.
	 *
	 * @param tag the tag to use
     */
    private void setTagint(char[] tag)
    {
        if ( tag.length == 3 )
        {
            this.tagint = -1;
        }
        else
        {
            try
            {
                this.tagint = StringUtil.ascii2int(tag);
            }
            catch (RuntimeException e)
            {
                this.tagint = -1;
            }
        }
    }


    /**
     * Test that the target field has the same tag as this field.
     *
     * @param f the field to check
     *
     * @return true if the argument field has the same tag as this field
	 *
     * @throws NullPointerException if the argument is null
     */
    public boolean comparable(Field f)
    {
        return this.tag.equals(f.tag);
    }


    /**
     * Return the field id for this object.
     *
     * @return the field id for this object
     */
    public final int getId()
    {
        return id;
    }


    /**
     * Set the field id value for field.
     *
     * @param newId id to set for this field
     */
    public final void setId(int newId)
    {
        this.id = newId;
    }


    /**
     * Return the tag element for this object.
     *
     * @return the tag element for this object
     */
    public final String tag()
    {
        return tag;
    }


    /**
     * Return the tag element for this object.
     *
     * @return the tag element for this object
     */
    public final String getTag()
    {
        return tag;
    }


    /**
     * Set the tag element for this object.
	 *
	 * @param tag the tag to use
     */
    public void setTag(String tag)
    {
        this.tag = tag;
        setTagint();
    }


    /**
     * Return the tagint element for this object.
     *
     * @return the tagint element for this object
     */
    public final int getTagint()
    {
        return tagint;
    }


    //====================================================
    //       ABSTRACT METHODS
    //====================================================

    /**
     * Stub for method that returns a copy of this object.
     *
     * @return a copy of this object
     */
    public abstract Field copy();


    /**
     * Stub for method that returns a hex formatted String of this field.
     *
     * @return the hex formatted string of this field
     */
    public abstract String formatHexDump();


    /**
     * Stub for method that returns an ascii formatted String of this field
     * suitable for incorporation into a raw marc record.
     *
     * @return the ascii formatted String of this field
     */
    public abstract String marcDump();


    /**
     * Stub for method that sets  field
     */
    public abstract Field setField(String tag, String data);


    /**
     * Stub for method that returns String representing this field's
     * value without a field terminator.
     *
     * @return the field value String
     */
    public abstract String value();

}
