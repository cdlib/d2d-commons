package org.cdlib.util.marc2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.cdlib.util.DeserializationException;
import org.marc4j.MarcException;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

/*
 * A wrapper class around a Marc4J record.
 * 
 * This class provides method that simplifies and limits the boilerplate of direct calls to the
 * Marc4J Record methods.
 */
public class Marc4jHelper {

  private Record record;

  public Marc4jHelper(Record rec) {
    record = rec;
  }

  public Marc4jHelper(String marcXml) throws DeserializationException {
    try {
      this.record = asRecord(marcXml);
    } catch (MarcException e) {
      throw new DeserializationException("Failure to parse marcXml " + marcXml, e, marcXml);
    }
  }

  private Record asRecord(String marcXml) {
    Record marcRecord = null;
    InputStream is = new ByteArrayInputStream(marcXml.getBytes());
    MarcXmlReader reader = new MarcXmlReader(is);
    while (reader.hasNext()) {
      marcRecord = reader.next();
    }
    return marcRecord;
  }

  /*
   * Returns the character at the specified index of the specified MARC control field.
   */
  public char controlFieldChar(String field, int index) throws MarcDataReferenceException {
    String ctrlFieldStr = controlFieldVal(field);
    try {
      return ctrlFieldStr.charAt(index);
    } catch (StringIndexOutOfBoundsException e) {
      throw new MarcDataReferenceException("No data at position " + index, e);
    }
  }

  /*
   * Returns a segment of a control field value.
   * 
   * For example, if control field has value "cats"
   * 
   * If index = 1 and ubound = 3 the method returns {'a','t'}
   *  
   */
  public char[] controlFieldSegment(String field, int index, int ubound) throws MarcDataReferenceException {
    String ctrlFieldStr = controlFieldVal(field);
    return fromSegment(ctrlFieldStr, index, ubound);
  }
  
  private char[] fromSegment(String ctrlFieldStr, int index, int ubound) throws MarcDataReferenceException {
    String segment = "";
    try {
      segment = ctrlFieldStr.substring(index, ubound);
    } catch (StringIndexOutOfBoundsException e) {
      throw new MarcDataReferenceException(String.format("No segment found starting at %s through %s ", index, ubound));
    }
    return segment.toCharArray();
  }
  
  public String controlFieldVal(String field) throws MarcDataReferenceException {
    VariableField varField = record.getVariableField(field);
    if (varField == null || !(varField instanceof ControlField)) {
      throw new MarcDataReferenceException("Record has no control field " + field);
    }
    ControlField ctrlField = (ControlField) varField;
    return ctrlField.getData();
  }

  /*
   * Returns the char at an index of the leader.
   */
  public char leaderChar(int index) throws MarcDataReferenceException {
    String leader = record.getLeader().marshal();
    try {
      return leader.charAt(index);
    } catch (StringIndexOutOfBoundsException e) {
      throw new MarcDataReferenceException("char at leader index " + index + " not found.", e);
    }
  }
  
  public char[] leaderSegment(int index, int ubound) throws MarcDataReferenceException {
    String leader = record.getLeader().marshal();
    return fromSegment(leader, index, ubound);
  }

  /*
   * Returns the first value of a list of subfields values. Normally this would be used with
   * non-repeating fields.
   */
  public String subfieldVal(String field, String subfield) throws MarcDataReferenceException {
    List<String> values = subfieldVals(field, subfield);
    if (values.isEmpty()) {
      throw new MarcDataReferenceException(String.format("No data for field and subfield %s %s", field, subfield));
    }
    return values.get(0);
  }

  /*
   * Returns a list of values for a specific subfield of a specific field.
   * 
   * Normally this would be used with repeating fields.
   * 
   * Returns an empty List if no values are found.
   */
  public List<String> subfieldVals(String fieldName, String subfieldName) {
    List<String> fieldValues = new ArrayList<String>();
    List<VariableField> dataFields = record.getVariableFields(fieldName);

    if (!dataFields.isEmpty()) {
      for (VariableField field : dataFields) {
        DataField df = (DataField) field;
        List<Subfield> subfields = df.getSubfields(subfieldName);
        if (!subfields.isEmpty()) {
          for (Subfield sf : subfields) {
            fieldValues.add(sf.getData().trim());
          }
        }
      }
    }
    return fieldValues;
  }

}
