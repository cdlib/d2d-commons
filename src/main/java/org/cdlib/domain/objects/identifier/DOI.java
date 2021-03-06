package org.cdlib.domain.objects.identifier;

import static org.cdlib.http.OpenUrlDeriver.encodeValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.cdlib.util.JSON;

public class DOI implements Identifier {

  private static final IdAuthority AUTHORITY = IdAuthority.IDF;
  private List<String> alternateValues = new ArrayList<String>();
  private String value;
  private String type ="doi";

  public DOI() {}

  public DOI(String value) {
    this.value = value;
  }

  public DOI(DOI source) {
    this.value = source.value;
    this.alternateValues = source.alternateValues;
  }

  @Override
  public List<String> asEncodedOpenUrl() {
    List<String> result = new ArrayList<>();
    encodeValue("info:doi/" + value).ifPresent((encoded) -> result.add("rft_id=" + encoded));
    encodeValue(value).ifPresent((encoded) -> result.add("rft.doi=" + encoded));
    return result;
  }

  @Override
  public String getAuthority() {
    return AUTHORITY.getUri();
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public List<String> getAlternateValues() {
    return alternateValues.stream().distinct().collect(Collectors.toList());
  }
  
  @Override
  public String getType() {
    return type;
  }

  public void setAlternateValues(List<String> values) {
    this.alternateValues = new ArrayList<String>(values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alternateValues, value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof DOI)) {
      return false;
    }
    DOI other = (DOI) obj;
    return Objects.equals(alternateValues, other.alternateValues) && Objects.equals(value, other.value);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
