package org.cdlib.domain.objects.identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LCCN implements Identifier {
  
  private static final IdAuthority AUTHORITY = IdAuthority.LOC;
  private static final String DESCRIPTOR = "lccn";
  private String value;
  private List<String> alternateValues = new ArrayList<>();
  
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
  public String getDescriptor() {
    return DESCRIPTOR;
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
    return alternateValues;
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
