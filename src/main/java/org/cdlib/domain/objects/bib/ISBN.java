package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import org.cdlib.util.JSON;

public class ISBN {

  private List<String> values = new ArrayList<String>();
  
  public String getValue() {
    return values.get(0);
  }

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
    ISBN other = (ISBN) obj;
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
