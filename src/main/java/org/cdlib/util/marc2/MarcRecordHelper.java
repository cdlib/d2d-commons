package org.cdlib.util.marc2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
 * 
 * This class sticks to the mechanics of extracting data from the MARC record
 * and avoids semantics (so it would not have getTitle or getControlNumber, for example).
 * 
 * The methods return Optional<T>, which is empty if the MARC record lacks the field or subfield specified,
 * but will throw IllegalArgumentException if the arguments are invalid (such as negative integer indexes, or empty
 * tag or subfield names.
 * 
 */
public class MarcRecordHelper {

  private Record record;

  public MarcRecordHelper(Record rec) {
    record = rec;
  }

  public MarcRecordHelper(String marcXml) throws DeserializationException {
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
   * Optionally returns the character at the specified index of the specified MARC control field.
   */
  public Optional<Character> controlFieldChar(String tag, int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Argument " + index + " cannot be negative.");
    }
    if (tag.isEmpty()) {
      throw new IllegalArgumentException("Tag cannot be empty.");
    }
    Optional<String> fieldValOpt = controlFieldVal(tag);
    if (fieldValOpt.isPresent()) {
      String fieldVal = fieldValOpt.get();
      if (index + 1 > fieldVal.length()) {
        return Optional.empty();
      }
      return Optional.of(fieldValOpt.get().charAt(index));
    } else {
      return Optional.empty();
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
  public Optional<char[]> controlFieldSegment(String tag, int index, int ubound) {
    Optional<String> ctrlFieldValOpt = controlFieldVal(tag);
    if (!ctrlFieldValOpt.isPresent()) {
      return Optional.empty();
    }
    String ctrlFieldVal = ctrlFieldValOpt.get();
    return fromSegment(ctrlFieldVal, index, ubound);
  }

  private Optional<char[]> fromSegment(String source, int beginIndex, int endIndex) {
    if (beginIndex < 0 || beginIndex >= endIndex) {
      throw new IllegalArgumentException("Begin and end index cannot logically specify a string segment.");
    }
    String segment = "";
    if (beginIndex + 1 > source.length() || endIndex + 1 > source.length()) {
      return Optional.empty();
    }
    segment = source.substring(beginIndex, endIndex);
    return Optional.of(segment.toCharArray());
  }

  public Optional<String> controlFieldVal(String tag) {
    if (tag.isEmpty()) {
      throw new IllegalArgumentException("Tag cannot be empty.");
    }
    VariableField varField = record.getVariableField(tag);
    if (varField == null || !(varField instanceof ControlField)) {
      return Optional.empty();
    }
    ControlField ctrlField = (ControlField) varField;
    return Optional.of(ctrlField.getData());
  }

  /*
   * Returns the char at an index of the leader.
   */
  public Optional<Character> leaderChar(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("index cannot be negative");
    }
    String leader = record.getLeader().marshal();
    if (index + 1 > leader.length()) {
      return Optional.empty();
    }
    return Optional.of(leader.charAt(index));
  }

  public Optional<char[]> leaderSegment(int beginIndex, int endIndex) {
    String leader = record.getLeader().marshal();
    return fromSegment(leader, beginIndex, endIndex);
  }

  /*
   * Returns the first value of a list of subfields values. Normally this would be used with
   * non-repeating fields.
   */
  public Optional<String> subfieldVal(String tag, String subfield) {
    if (tag.isEmpty() || subfield.isEmpty()) {
      throw new IllegalArgumentException("arguments cannot be empty");
    }
    Optional<List<String>> valuesOpt = subfieldVals(tag, subfield);
    if (!valuesOpt.isPresent()) {
      return Optional.empty();
    }
    List<String> values = subfieldVals(tag, subfield).get();
    if (values.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(values.get(0));
  }

  /*
   * Returns a list of values for a specific subfield of a specific field.
   * 
   * Normally this would be used with repeating fields.
   * 
   * Returns an empty List if no values are found.
   */
  public Optional<List<String>> subfieldVals(String tag, String subfieldName) {
    List<String> fieldValues = new ArrayList<String>();
    List<VariableField> dataFields = record.getVariableFields(tag);

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
    } else {
      return Optional.empty();
    }
    return Optional.of(fieldValues);
  }

}
