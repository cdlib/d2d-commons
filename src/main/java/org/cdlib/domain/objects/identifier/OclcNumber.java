package org.cdlib.domain.objects.identifier;

import static org.cdlib.http.OpenUrl.encodeValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.constraints.OCLCNumber;
import org.cdlib.util.CollectionUtil;
import org.cdlib.util.JSON;

public class OclcNumber implements Identifier {

  private static final IdAuthority AUTHORITY = IdAuthority.OCLC;
  private List<String> altValues = new ArrayList<>();
  
  @NotEmpty
  @OCLCNumber(message = "OCLC number must be in valid OCLC format.")
  private String value;
  
  public OclcNumber() {}

  public OclcNumber(OclcNumber source) {
    this.value = source.value;
    this.altValues = source.altValues;
  }
  
  public OclcNumber(String value) {
    this.value = value;
  }

  public List<String> getAlternateValues() {
    return CollectionUtil.dedupedList(altValues);
  }

  @Override
  public @NotNull String getAuthority() {
    return AUTHORITY.getUri();
  }

  @Override
  public String getValue() {
    return value;
  }
  
  @Override
  public List<String> asEncodedOpenUrl() {
    List<String> result = new ArrayList<>();
    result.add("rft_id=" + encodeValue("info:oclcnum/" + value));
    result.add("rft.oclcnum=" + encodeValue(value));
    return result;
  }

  public void setAlternateValues(List<String> altValues) {
    this.altValues = altValues;
  }

  public void setValue(String value) {
    this.value = value;
  }
  

  @Override
  public int hashCode() {
    return Objects.hash(altValues, value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof OclcNumber)) {
      return false;
    }
    OclcNumber other = (OclcNumber) obj;
    return Objects.equals(altValues, other.altValues) && Objects.equals(value, other.value);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
