package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import org.cdlib.util.CollectionUtil;
import org.cdlib.util.JSON;

public class ISBN implements Identifier {

  private static final IdAuthority AUTHORITY = IdAuthority.ISBN;
  private List<String> alternateValues = new ArrayList<String>();
  private String value;
  
  public ISBN() {}
  
  public ISBN(String value) {
    this.value = value;
  }
  
  public ISBN(ISBN source) {
    this.value = source.value;
    this.alternateValues = source.alternateValues;
  }

  @Override
  public String getAuthority() {
    return AUTHORITY.getUri();
  }

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
    final int prime = 31;
    int result = 1;
    result = prime * result + ((alternateValues == null) ? 0 : alternateValues.hashCode());
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
    ISBN other = (ISBN) obj;
    if (alternateValues == null) {
      if (other.alternateValues != null)
        return false;
    } else if (!alternateValues.equals(other.alternateValues))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
