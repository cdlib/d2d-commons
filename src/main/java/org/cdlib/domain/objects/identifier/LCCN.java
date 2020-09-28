package org.cdlib.domain.objects.identifier;

import static org.cdlib.http.OpenUrlDeriver.encodeValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LCCN implements Identifier {
  
  private static final IdAuthority AUTHORITY = IdAuthority.LOC;
  private String value;
  private List<String> alternateValues = new ArrayList<>();
  private String type = "lccn";
  
  public LCCN() {}

  public LCCN(String value) {
    this.value = value;
  }
  
  public LCCN(LCCN source) {
    this.value = source.value;
  }

  @Override
  public String getAuthority() {
    return AUTHORITY.getUri();
  }

  @Override
  public String getValue() {
    return value;
  }
  
  @Override
  public String getType() {
    return type;
  }

  public void setValue(String value) {
    this.value = value;
  }
  
  @Override
  public List<String> getAlternateValues() {
    return alternateValues;
  }
  
  
  @Override
  public List<String> asEncodedOpenUrl() {
    List<String> result = new ArrayList<>();
    encodeValue("info:lccn/" + value).ifPresent((encoded) -> result.add("rft_id=" + encoded));
    encodeValue(value).ifPresent((encoded) -> result.add("rft.lccn=" + encoded));
    return result;
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
    if (!(obj instanceof LCCN)) {
      return false;
    }
    LCCN other = (LCCN) obj;
    return Objects.equals(alternateValues, other.alternateValues) && Objects.equals(value, other.value);
  }

}
