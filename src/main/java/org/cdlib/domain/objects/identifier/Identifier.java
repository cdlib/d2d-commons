package org.cdlib.domain.objects.identifier;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/*
 * Serializable representation of a bibliographic identifier.
 * 
 */
public interface Identifier {
  
  @NotEmpty
  public String getValue();
  
  @NotEmpty
  public String getAuthority();
  
  @NotNull
  public List<String> getAlternateValues();
  
  // used to name the identifier in OpenURL and other contexts, for example "pmid"
  @NotEmpty
  public String getDescriptor();

}
