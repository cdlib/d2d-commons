package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LCCN implements Identifier {
  
  private static final IdAuthority AUTHORITY = IdAuthority.LOC;
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
  public @NotNull @NotEmpty String getAuthority() {
    return AUTHORITY.getUri();
  }

  @Override
  public @NotNull @NotEmpty String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    LCCN other = (LCCN) obj;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }

  @Override
  public List<String> getAlternateValues() {
    return alternateValues;
  }

  public void setAlternateValues(List<String> alternateValues) {
    this.alternateValues = alternateValues;
  }

}
