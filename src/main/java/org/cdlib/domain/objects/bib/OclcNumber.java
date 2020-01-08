package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.constraints.OCLCNumber;
import org.cdlib.util.CollectionUtil;

public class OclcNumber implements Identifier {

  private static final IdAuthority AUTHORITY = IdAuthority.OCLC;
  private List<String> altValues;
  
  @NotNull
  @NotEmpty
  @OCLCNumber(message = "OCLC number must be in valid OCLC format.")
  private String value;
  
  public OclcNumber() {
  }

  public OclcNumber(String value) {
    this.value = value;
    altValues = new ArrayList<>();
  }
  
  public OclcNumber(OclcNumber source) {
    this.value = source.value;
    this.altValues = source.altValues;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final OclcNumber other = (OclcNumber) obj;
    if (!Objects.equals(this.value, other.value)) {
      return false;
    }
    if (!Objects.equals(this.altValues, other.altValues)) {
      return false;
    }
    return true;
  }

  @Override
  public @NotNull String getAuthority() {
    return AUTHORITY.getUri();
  }

  public List<String> getAlternateValues() {
    return CollectionUtil.dedupedList(altValues);
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 73 * hash + Objects.hashCode(this.value);
    hash = 73 * hash + Objects.hashCode(this.altValues);
    return hash;
  }

  public void setAlternateValues(List<String> altValues) {
    this.altValues = altValues;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "OclcNumber{" + "value=" + value + ", formerValues=" + altValues + '}';
  }

}
