package org.cdlib.domain.objects.identifier;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/*
 * Serializable representation of a bibliographic identifier.
 * 
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DOI.class, name = "doi"),
        @JsonSubTypes.Type(value = EISBN.class, name = "eisbn"),
        @JsonSubTypes.Type(value = EISSN.class, name = "eisnn"),
        @JsonSubTypes.Type(value = ISBN.class, name = "isbn"),
        @JsonSubTypes.Type(value = ISSN.class, name = "issn"),
        @JsonSubTypes.Type(value = LCCN.class, name = "lccn"),
        @JsonSubTypes.Type(value = OclcNumber.class, name = "oclcn"),
        @JsonSubTypes.Type(value = PMID.class, name = "pmid")
})
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
  
  public String getType();

}
