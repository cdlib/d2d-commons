package org.cdlib.util.marc2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.cdlib.util.DeserializationException;
import org.marc4j.MarcException;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

/*
 * A wrapper class around a Marc4J record.
 * 
 * This class provides method that simplifies and limits the boilerplate of direct calls to the
 * Marc4J Record methods.
 * 
 * This class sticks to the mechanics of extracting data from the MARC record and avoids semantics
 * (so it would not have getTitle or getControlNumber, for example).
 * 
 * The methods return Optional<T>, which is empty if the MARC record lacks the field or subfield
 * specified, but will throw IllegalArgumentException if the arguments are invalid (such as negative
 * integer indexes, or empty tag or subfield names.
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
   * 
   * If the control field repeats, the character is from the first field found.
   */
  public Optional<Character> controlFieldChar(String tag, int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Argument " + index + " cannot be negative.");
    }
    if (tag.isEmpty()) {
      throw new IllegalArgumentException("Tag cannot be empty.");
    }
    return controlFieldVal(tag).map(s -> s.charAt(index));
  }

  /*
   * Returns a segment of a control field value.
   * 
   * For example, if control field has value "cats"
   * 
   * If index = 1 and ubound = 3 the method returns {'a','t'}
   * 
   */
  public Optional<char[]> controlFieldSegment(String tag, int beginIndex, int endIndex) {
    return controlFieldVal(tag).flatMap(s -> fromSegment(s, beginIndex, endIndex));
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
    return Optional.ofNullable(segment.toCharArray());
  }

  public Optional<String> controlFieldVal(String tag) {
    if (tag.isEmpty()) {
      throw new IllegalArgumentException("Tag cannot be empty.");
    }
    
    return record.getControlFields().stream()
        .filter(cf -> cf.getTag().contentEquals(tag))
        .findFirst()
        .map(cf -> cf.getData());
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
    return Optional.ofNullable(leader.charAt(index));
  }

  public Optional<char[]> leaderSegment(int beginIndex, int endIndex) {
    String leader = record.getLeader().marshal();
    return fromSegment(leader, beginIndex, endIndex);
  }

  /*
   * Returns the first value of a list of subfields values. Normally this would be used with
   * non-repeating fields.
   */
  public Optional<String> subfieldVal(String tag, char subfieldCode) {
    if (tag.isEmpty()) {
      throw new IllegalArgumentException("tag cannot be empty");
    }
    Optional<List<String>> valuesOpt = subfieldVals(tag, subfieldCode);
    if (!valuesOpt.isPresent()) {
      return Optional.empty();
    }
    List<String> values = subfieldVals(tag, subfieldCode).get();
    if (values.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(values.get(0));
  }

  /*
   * Returns a list of values for a specific subfield of a specific field.
   * 
   * Normally this would be used with repeating fields and repeating subfields.
   * 
   * Returns an empty List if no values are found.
   */
  public Optional<List<String>> subfieldVals(String tag, char subfieldCode) {
    List<String> fieldValues = new ArrayList<String>();
    
    List<DataField> dataFields = record.getDataFields().stream()
        .filter(hasTagAndSubfield(tag, subfieldCode))
        .collect(Collectors.toList());
    
    for (DataField field : dataFields) {
      List<Subfield> subfields = field.getSubfields(subfieldCode);
      for (Subfield sb : subfields) {
        if (sb != null) {
          fieldValues.add(sb.getData().trim());
        }
      }
    }
    if (fieldValues.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(fieldValues);
  }
  
  private static Predicate<DataField> hasTagAndSubfield(String tag, char subfieldCode) {
    return df -> df.getTag().equals(tag) && !df.getSubfields(subfieldCode).isEmpty();
  }


}
