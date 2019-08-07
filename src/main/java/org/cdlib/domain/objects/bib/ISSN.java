package org.cdlib.domain.objects.bib;

import java.util.List;
import org.cdlib.util.JSON;

/**
 * 
 * Represents the ISSN identifiers associated with a bibliographic resource.
 * 
 */
public class ISSN {

  private List<String> precedingValues;
  private List<String> succeedingValues;
  private List<String> values;
  
  public List<String> getPrecedingValues() {
    return precedingValues;
  }
  
  public List<String> getSucceedingValues() {
    return succeedingValues;
  }
  
  public String getValue() {
    return values.get(0);
  }
  
  public List<String> getValues() {
    return values;
  }
  
  public void setPrecedingValues(List<String> precedingValues) {
    this.precedingValues = precedingValues;
  }

  public void setSucceedingValues(List<String> succeedingValues) {
    this.succeedingValues = succeedingValues;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((precedingValues == null) ? 0 : precedingValues.hashCode());
    result = prime * result + ((succeedingValues == null) ? 0 : succeedingValues.hashCode());
    result = prime * result + ((values == null) ? 0 : values.hashCode());
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
    ISSN other = (ISSN) obj;
    if (precedingValues == null) {
      if (other.precedingValues != null)
        return false;
    } else if (!precedingValues.equals(other.precedingValues))
      return false;
    if (succeedingValues == null) {
      if (other.succeedingValues != null)
        return false;
    } else if (!succeedingValues.equals(other.succeedingValues))
      return false;
    if (values == null) {
      if (other.values != null)
        return false;
    } else if (!values.equals(other.values))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
