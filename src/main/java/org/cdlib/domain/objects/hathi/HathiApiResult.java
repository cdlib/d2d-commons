package org.cdlib.domain.objects.hathi;

import java.util.List;
import org.cdlib.util.JSON;

public class HathiApiResult {
  List<HathiItemInfo> items;

  public List<HathiItemInfo> getItems() {
    return items;
  }

  public void setItems(List<HathiItemInfo> items) {
    this.items = items;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((items == null) ? 0 : items.hashCode());
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
    HathiApiResult other = (HathiApiResult) obj;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }
}
