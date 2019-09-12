package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.util.CollectionUtil;
import org.cdlib.util.JSON;

/**
 * 
 * Represents the ISSN identifiers associated with a bibliographic resource.
 * 
 */
public class ISSN implements Identifier {

  private List<String> precedingValues = new ArrayList<String>();
  private List<String> succeedingValues = new ArrayList<String>();
  private List<String> values = new ArrayList<String>();
  
  public ISSN() {}
  
  public ISSN(String value) {
    values.add(value);
  }
  
  @NotNull
  public List<String> getPrecedingValues() {
    return CollectionUtil.dedupedList(precedingValues);
  }
  
  @NotNull
  public List<String> getSucceedingValues() {
    return CollectionUtil.dedupedList(succeedingValues);
  }
  
  @NotNull
  @NotEmpty
  public String getValue() {
    if (values.isEmpty()) {
      return null;
    }
    return values.get(0);
  }
  
  @NotNull
  public List<String> getValues() {
    return CollectionUtil.dedupedList(values);
  }
  
  public void setPrecedingValues(List<String> precedingValues) {
    this.precedingValues = new ArrayList<String>(precedingValues);
  }

  public void setSucceedingValues(List<String> succeedingValues) {
    this.succeedingValues = new ArrayList<String>(succeedingValues);
  }

  public void setValues(List<String> values) {
    this.values = new ArrayList<String>(values);
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
