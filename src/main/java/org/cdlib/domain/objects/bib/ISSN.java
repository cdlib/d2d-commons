package org.cdlib.domain.objects.bib;

import java.util.List;
import java.util.Objects;

/**
 * Represents an ISSN identifier.
 */
public class ISSN implements Identifier {

  private String value;
  private List<String> formerValues;
  
  public ISSN() {
  }

  public ISSN(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

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
    final ISSN other = (ISSN) obj;
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
