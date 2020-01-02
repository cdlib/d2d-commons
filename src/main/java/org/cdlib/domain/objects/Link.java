package org.cdlib.domain.objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((href == null) ? 0 : href.hashCode());
    result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
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
    return true;
  } 

}
