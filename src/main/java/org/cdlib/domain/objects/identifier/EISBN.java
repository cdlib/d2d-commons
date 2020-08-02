package org.cdlib.domain.objects.identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static org.cdlib.http.OpenUrl.encodeValue;
import org.cdlib.util.CollectionUtil;
import org.cdlib.util.JSON;

public class EISBN implements Identifier {

  private static final IdAuthority AUTHORITY = IdAuthority.ISBN;
  private List<String> alternateValues = new ArrayList<String>();
  private String value;
  
  public EISBN() {}
  
  public EISBN(String value) {
    this.value = value;
  }
  
  public EISBN(EISBN source) {
    this.value = source.value;
    this.alternateValues = source.alternateValues;
  }

  @Override
  public String getAuthority() {
    return AUTHORITY.getUri();
  }
  
  @Override
  public List<String> asEncodedOpenUrl() {
    List<String> result = new ArrayList<>();
    result.add("rft.id=" + value);
    return result;
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
    return CollectionUtil.dedupedList(alternateValues);
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
    if (!(obj instanceof EISBN)) {
      return false;
    }
    EISBN other = (EISBN) obj;
    return Objects.equals(alternateValues, other.alternateValues) && Objects.equals(value, other.value);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
