package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.cdlib.domain.constraints.OCLCNumber;
import org.cdlib.util.CollectionUtil;

public class OclcNumber {

  @OCLCNumber(message = "OCLC number must be in valid OCLC format.")
  private String value;
  private List<String> formerValues;
  
  public OclcNumber() {
  }

  public OclcNumber(String value) {
    this.value = value;
    formerValues = new ArrayList<>();
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public List<String> getFormerValues() {
    return CollectionUtil.dedupedList(formerValues);
  }

  public void setFormerValues(List<String> formerValues) {
    this.formerValues = formerValues;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 73 * hash + Objects.hashCode(this.value);
    hash = 73 * hash + Objects.hashCode(this.formerValues);
    return hash;
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
    if (!Objects.equals(this.formerValues, other.formerValues)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "OclcNumber{" + "value=" + value + ", formerValues=" + formerValues + '}';
  }

}
