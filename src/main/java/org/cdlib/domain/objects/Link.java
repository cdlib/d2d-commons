package org.cdlib.domain.objects;

import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.bib.Identifier;
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
  @NotEmpty
  private Identifier id; 
  
  @NotNull
  private Map<String, Object> properties;

  public Link() {}

  public Link(Link source) {
    this.href = source.href;
    this.mimeType = source.mimeType;
    this.id = source.id; 
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
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public String getHref() {
    return href;
  }
  
  public String getMimeType() {
    return mimeType;
  }
  
  public Map<String, Object> getProperties() {
    return properties;
  }
  
  public Identifier getId() {
    return id;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((href == null) ? 0 : href.hashCode());
    result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    result = prime * result + ((properties == null) ? 0 : id.hashCode());
    return result;
  }

  public void setHref(String href) {
    this.href = href;
  } 
  
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public void setProperties(Map<String, Object> properties) {
    this.properties = properties;
  }
  
  public void setId(Identifier id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }


}
