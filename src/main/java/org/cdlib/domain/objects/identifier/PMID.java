package org.cdlib.domain.objects.identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.cdlib.util.CollectionUtil;
import org.cdlib.util.JSON;

public class PMID implements Identifier {

  private static final IdAuthority AUTHORITY = IdAuthority.NLM;
  private static final String DESCRIPTOR = "pmid";
  private List<String> alternateValues = new ArrayList<String>();
  private String value;
  
  public PMID() {}
  
  public PMID(String value) {
    this.value = value;
  }
  
  public PMID(PMID source) {
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
    if (!(obj instanceof PMID)) {
      return false;
    }
    PMID other = (PMID) obj;
    return Objects.equals(alternateValues, other.alternateValues) && Objects.equals(value, other.value);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}