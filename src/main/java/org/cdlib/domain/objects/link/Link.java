package org.cdlib.domain.objects.link;

import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.util.JSON;

/*
 * A link to a related resource.
 */
public class Link {
  
  @NotNull
  @NotEmpty
  private String href;
  
  @NotNull
  @NotEmpty
  private String mimeType;
  
  @NotNull
  private Map<String, String> properties;

  public Link() {}

  public Link(Link source) {
    this.href = source.href;
    this.mimeType = source.mimeType;
    this.properties = deepCopy(source.properties);
  }
  
  @SuppressWarnings("unchecked")
  private static Map<String, String> deepCopy(Map<String, String> properties) {
    String intermediate = JSON.serialize(properties);
    return JSON.deserialize(intermediate, properties.getClass());
  }

  public String getHref() {
    return href;
  }
  
  public String getMimeType() {
    return mimeType;
  }
  
  public Map<String, String> getProperties() {
    return properties;
  }

  public void setHref(String href) {
    this.href = href;
  } 
  
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public int hashCode() {
    return Objects.hash(href, mimeType, properties);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Link)) {
      return false;
    }
    Link other = (Link) obj;
    return Objects.equals(href, other.href) 
        && Objects.equals(mimeType, other.mimeType) 
        && Objects.equals(properties, other.properties);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
