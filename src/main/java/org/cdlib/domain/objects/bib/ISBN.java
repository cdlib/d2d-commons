package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import org.cdlib.util.CollectionUtil;
import org.cdlib.util.JSON;

public class ISBN implements Identifier {

  private static final IdAuthority AUTHORITY = IdAuthority.ISBN;
  private static final String DESCRIPTOR = "isbn";
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
    return CollectionUtil.dedupedList(alternateValues);
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
    if (!(obj instanceof ISBN)) {
      return false;
    }
    ISBN other = (ISBN) obj;
    return Objects.equals(alternateValues, other.alternateValues) && Objects.equals(value, other.value);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
