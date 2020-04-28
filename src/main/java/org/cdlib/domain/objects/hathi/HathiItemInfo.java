package org.cdlib.domain.objects.hathi;

import org.cdlib.util.JSON;

public class HathiItemInfo {
  String htid;
  String itemURL;
  String usRightsString;

  public String getHtid() {
    return htid;
  }

  public void setHtid(String htid) {
    this.htid = htid;
  }

  public String getItemURL() {
    return itemURL;
  }

  public void setItemURL(String itemURL) {
    this.itemURL = itemURL;
  }

  public String getUsRightsString() {
    return usRightsString;
  }

  public void setUsRightsString(String usRightsString) {
    this.usRightsString = usRightsString;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((htid == null) ? 0 : htid.hashCode());
    result = prime * result + ((itemURL == null) ? 0 : itemURL.hashCode());
    result = prime * result + ((usRightsString == null) ? 0 : usRightsString.hashCode());
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
    HathiItemInfo other = (HathiItemInfo) obj;
    if (htid == null) {
      if (other.htid != null)
        return false;
    } else if (!htid.equals(other.htid))
      return false;
    if (itemURL == null) {
      if (other.itemURL != null)
        return false;
    } else if (!itemURL.equals(other.itemURL))
      return false;
    if (usRightsString == null) {
      if (other.usRightsString != null)
        return false;
    } else if (!usRightsString.equals(other.usRightsString))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
