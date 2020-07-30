package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

  private static final IdAuthority AUTHORITY = IdAuthority.ISSN;
  private static final String DESCRIPTOR = "issn";
  private String value;
  private List<String> alternateValues = new ArrayList<String>();

  public ISSN() {}

  public ISSN(ISSN source) {
    this.value = source.value;
    this.alternateValues = source.alternateValues;
  }

  public ISSN(String value) {
    this.value = value;
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
  public String toString() {
    return JSON.serialize(this);
  }

  
}
