package org.cdlib.domain.objects.bib;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LCCN implements Identifier {
  
  private String authority;
  private String value;

  public LCCN(String value, String authority) {
    this.value = value;
    this.authority = authority;
  }

  @Override
  public @NotNull @NotEmpty String getAuthority() {
    return authority;
  }

  @Override
  public @NotNull @NotEmpty String getValue() {
    return value;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
