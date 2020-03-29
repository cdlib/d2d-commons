package org.cdlib.util.marc2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.cdlib.util.DeserializationException;
import org.marc4j.MarcException;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;

/*
 * 
 * Based on a MARC record, provide a list of fields based on a tag, or other criteria provided in a
 * Predicate.
 *
 */
public class FieldFilter {

  private Record record;

  public FieldFilter(Record rec) {
    record = rec;
  }

  public FieldFilter(String marcXml) throws DeserializationException {
    try {
      this.record = MarcRecordHelper.asRecord(marcXml);
    } catch (MarcException e) {
      throw new DeserializationException("Failure to parse marcXml " + marcXml, e, marcXml);
    }
  }

  /*
   * Provides a List of cdlib MarcDataFields based on tag without other conditions.
   */
  public Optional<List<MarcDataField>> marcDataFields(String tag) {
    return marcDataFields(tag, (s) -> true);
  }

  /*
   * Provides a List of cdlib MarcDataFields based on tag.
   */
  public Optional<List<MarcDataField>> marcDataFields(String tag, Predicate<MarcDataField> condition) {
    List<DataField> dataFields = record.getDataFields()
                                       .stream()
                                       .filter(df -> df.getTag().equals(tag))
                                       .collect(Collectors.toList());
    List<MarcDataField> marcDataFields = new ArrayList<>();
    dataFields.forEach(df -> marcDataFields.add(new MarcDataField(df)));
    List<MarcDataField> result = marcDataFields.stream()
                                               .filter(condition)
                                               .collect(Collectors.toList());
    return Optional.of(result);
  }

  public static Predicate<MarcDataField> hasSubfieldMatching(char key, String val) {
    return (df) -> {
      if (df.getSubFields() == null) {
        return false;
      }
      Optional<List<String>> resultVal = df.getSubFields().get(key);
      return resultVal.filter(list -> !list.isEmpty())
                      .map(list -> list.get(0).equals(val))
                      .orElse(false);
    };
  }
}
