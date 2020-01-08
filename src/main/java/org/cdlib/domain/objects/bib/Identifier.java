package org.cdlib.domain.objects.bib;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/*
 * Serializable representation of a bibliographic identifier.
 * 
 */
public interface Identifier {
  
  @NotNull
  @NotEmpty
  public String getValue();
  
  @NotNull
  @NotEmpty
  public String getAuthority();
  
  public List<String> getAlternateValues();

}
