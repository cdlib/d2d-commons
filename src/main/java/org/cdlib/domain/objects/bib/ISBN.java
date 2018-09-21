package org.cdlib.domain.objects.bib;

import java.util.List;
import java.util.Objects;

public class ISBN implements Identifier {

  private String value;
  private List<String> formerValues;
  
  public ISBN() {
  }

  public ISBN(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public List<String> getFormerValues() {
    return formerValues;
  }

  public void setFormerValues(List<String> formerValues) {
    this.formerValues = formerValues;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(this.value);
    hash = 23 * hash + Objects.hashCode(this.formerValues);
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
    final ISBN other = (ISBN) obj;
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
    return "ISSN{" + "value=" + value + ", formerValues=" + formerValues + '}';
  }

}
