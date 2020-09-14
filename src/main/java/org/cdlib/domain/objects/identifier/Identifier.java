package org.cdlib.domain.objects.identifier;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/*
 * Serializable representation of a bibliographic identifier.
 * 
 */
public interface Identifier {
  
  @NotEmpty (message = "Identifier value must not be null.")
  public String getValue();
  
  @NotEmpty (message = "Identifier authority must not be null.")
  public String getAuthority();
  
  @NotNull (message = "Identifier alternate values must not be null.")
  public List<String> getAlternateValues();
  
  /* 
   * Multiple ways that the field value pair may be expressed in an openurl 
   */
  @NotEmpty (message = "Identifier encoded open URL required.")
  public List<String> asEncodedOpenUrl();

}
