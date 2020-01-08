package org.cdlib.util.marc;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * A container class used to hold a collection of <code>Field</code> objects
 * and provide an <code>Enumeration</code> over that collection.
 * The underlying data store is a <code>Vector</code>.
 *
 * Note: this class is not thread safe.
 *
 * @author <a href="mailto:david.loy@ucop.edu">David Loy</a>
 * @author <a href="mailto:shawnm@splorkin.com">Shawn McGovern</a>
 * @version $Id: FieldList.java,v 1.5 2003/01/03 18:06:32 smcgovrn Exp $
 */
public class FieldList implements Enumeration
{
	/**
	 * log4j Logger for this class.
	 */
    private static Logger log = Logger.getLogger(FieldList.class);

	/**
	 * CVS header string.
	 */
    public static final String cvsHeader = "$Header: /cvs/root/marcutil/java/org/cdlib/util/marc/FieldList.java,v 1.5 2003/01/03 18:06:32 smcgovrn Exp $";

	/**
	 * CVS version string.
	 */
    public static final String version = "$Revision: 1.5 $";

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
	 * The collection of fields.
	 */
    private Vector fields;

	/**
	 * The index used by the enumeration to iterate over the elements
	 * in this list.
	 */
    private int startEle = 0;


    /**
     * Create a new FieldList.
     */
    public FieldList()
    {
        fields = new Vector(10, 10);
    }


    /**
     * Remove all fields from this list.
     */
    public void clear()
	{
        fields.clear();
    }


    /**
     * Make a copy of the given field and save it in this list.
	 *
     * @param field the field to be added
     */
    public void setCopyField(Field inField)
    {
        Field newField = inField.copy();
        fields.addElement(newField);
    }


    /**
     * Add a field to end of this list.
	 *
     * @param f the field to be added
	 *
	 * @return the field that was added
     */
    public Field setField(Field f)
    {
      // and to vector of fields
      fields.addElement(f);
      return f;
    }


    /**
     * Return the Vector that backs this object.
	 *
     * @return the Vector that backs this object
     */
    public Vector toVector()
    {
        return fields;
    }


    //====================================================
    //       IMPLEMENTS ENUMERATION
    //====================================================

    /**
     * Add a field to end of this list.
	 *
     * @param fld field to be added to end list
     */
    public void addElement(Field fld)
    {
        fields.addElement(fld);
    }


    /**
     * Return the list entry at the given index.
	 *
     * @param index index of list entry to be returned
	 *
     * @return the list entry at the given index
     */
    public Field elementAt(int index)
    {
        return (Field)fields.elementAt(index);
    }


    /**
     * Initiate an enumeration and return a reference to this object.
	 *
	 * @return a reference to this object
     */
    public Enumeration elements()
    {
        startEnumeration();
        return this;
    }


    /**
     * Test if there are more elements in list accessible by the nextElement()
	 * method. Returns false when nextElement() would throw a NoSuchElementException
	 * exception.
     *
     * @return true if the are more elements in this enumeration
     */
    public boolean hasMoreElements()
    {
        return (startEle < fields.size());
    }


    /**
     * Return next element in the enumeration.
	 *
     * @return the next object in the enumeration
	 *
     * @exception NoSuchElementException if we have iterated past the end of the list
     */
    public Object nextElement() throws NoSuchElementException
    {
        if ((fields == null) || (startEle >= fields.size()))
        {
            throw new NoSuchElementException();
        }

        Object obj = (Object)fields.elementAt(startEle);
        startEle++;

        return obj;
    }


    /**
     * Delete the list entry at the specified index.
	 *
     * @param index index of entry to be deleted
     */
    public void removeElementAt(int index)
    {
        fields.removeElementAt(index);
    }


    /**
	 * Return the size of this list.
	 *
     * @return the size of this list
     */
    public int size()
    {
        return fields.size();
    }

    /**
     * Reset the enumeration index to zero.
     */
    private void startEnumeration()
    {
        startEle = 0;
    }

}
