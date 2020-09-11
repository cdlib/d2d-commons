package org.cdlib.domain.objects.identifier;

import static org.cdlib.http.OpenUrlDeriver.encodeValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.cdlib.util.JSON;

/**
 * 
 * Represents the ISSN identifiers associated with a bibliographic resource.
 * 
 */
public class EISSN implements Identifier {

  private static final IdAuthority AUTHORITY = IdAuthority.ISSN;
  private String value;
  private List<String> alternateValues = new ArrayList<String>();

  public EISSN() {}

  public EISSN(EISSN source) {
    this.value = source.value;
    this.alternateValues = source.alternateValues;
  }

  public EISSN(String value) {
    this.value = value;
  }

  @Override
  public String getAuthority() {
    return AUTHORITY.getUri();
  }

  @Override
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public List<String> getAlternateValues() {
    return alternateValues.stream()
        .distinct()
        .collect(Collectors.toList());
  }
  
  @Override
  public List<String> asEncodedOpenUrl() {
    List<String> result = new ArrayList<>();
    encodeValue(value).ifPresent((encoded) -> result.add("rft.eissn=" + encoded));
    return result;
  }

  public void setAlternateValues(List<String> values) {
    this.alternateValues = new ArrayList<String>(values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alternateValues, encodeValue(value));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof EISSN)) {
      return false;
    }
    EISSN other = (EISSN) obj;
    return Objects.equals(alternateValues, other.alternateValues) && Objects.equals(value, other.value);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

  
}
