package org.cdlib.util.marc2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.cdlib.util.JSON;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;

/*
 * All subfields of an instance of a field.
 * 
 * Each subfield code is mapped to a list of String to capture values for repeating subfields.
 * 
 */
public class SubFields {

  private Map<Character, List<String>> subfields = new HashMap<>();

  public SubFields(Map<Character, List<String>> subfields) {
    this.subfields = subfields;
  }

  public SubFields(DataField dataField) {
    List<Subfield> sourceSubfields = dataField.getSubfields();
    Set<Character> codes = new HashSet<>();
    sourceSubfields.forEach(sf -> codes.add(sf.getCode()));
    for (char code : codes) {
      List<String> vals = sourceSubfields
                                         .stream()
                                         .filter(sf -> sf.getCode() == code)
                                         .map(sf -> sf.getData())
                                         .collect(Collectors.toList());
      subfields.put(code, vals);
    }
  }

  /*
   * Return a list of all values for a list of subfields. The subfields can be repeating or
   * non-repeating.
   */
  public Optional<List<String>> get(char... keys) {
    ArrayList<String> mergedList = new ArrayList<>();
    for (char c : keys) {
      if (subfields.get(c) != null) {
        mergedList.addAll(subfields.get(c));
      }
    }
    if (mergedList.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mergedList);
  }

  @Override
  public String toString() {
    return JSON.serialize(subfields);
  }

}
