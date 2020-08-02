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
  
  // Multiple ways that the field value pair may be expressed in an openurl
  @NotEmpty
  public List<String> asEncodedOpenUrl();

}
