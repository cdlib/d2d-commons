package org.cdlib.domain.objects.bib;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface Identifier {
  
  @NotNull
  @NotEmpty
  public String getValue();
  
  @NotNull
  @NotEmpty
  public String getAuthority();

}
