package org.cdlib.domain.objects;

import java.util.Map;
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
  private Map<String, Object> properties;

  public Link() {}

  public Link(Link source) {
    this.href = source.href;
    this.mimeType = source.mimeType;
  }
  
  public String getHref() {
    return href;
  }
  
  public String getMimeType() {
    return mimeType;
  }
  
  public void setHref(String href) {
    this.href = href;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  } 
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((href == null) ? 0 : href.hashCode());
    result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Link other = (Link) obj;
    if (href == null) {
      if (other.href != null)
        return false;
    } else if (!href.equals(other.href))
      return false;
    if (mimeType == null) {
      if (other.mimeType != null)
        return false;
    } else if (!mimeType.equals(other.mimeType))
      return false;
    if (properties == null) {
      if (other.properties != null)
        return false;
    } else if (!properties.equals(other.properties))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }


}
