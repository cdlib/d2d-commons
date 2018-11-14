package org.cdlib.domain.objects.holdings;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.meta.ResourceMeta;

/**
 * Summarized list of lending candidates, as Items, and the Bib for which they
 * are relevant.
 */
public class Holdings {

  @Valid
  @NotNull(message = "Bibliographic information is required.")
  private Bib bib;

  @Valid
  @NotNull(message = "A list of holdings is required.")
  private List<Item> heldItems;

  @NotNull(message = "Resource metadata is requird.")
  private ResourceMeta resourceMeta;

  public Holdings() {
  }

  public Holdings(Holdings source) {
    if (source.bib != null) {
      this.bib = new Bib(source.bib);
    }
    if (source.heldItems != null) {
      this.heldItems = source.heldItems.stream().map(Item::new).collect(Collectors.toList());
    }
  }

  public Holdings(Bib bib, List<Item> heldItems, ResourceMeta resourceMeta) {
    this.bib = bib;
    this.heldItems = heldItems;
    this.resourceMeta = resourceMeta;
  }

  public Bib getBib() {
    return bib;
  }

  public void setBib(Bib bib) {
    this.bib = bib;
  }

  public List<Item> getHeldItems() {
    return heldItems;
  }

  public void setHeldItems(List<Item> heldItems) {
    this.heldItems = heldItems;
  }

  public ResourceMeta getResourceMeta() {
    return resourceMeta;
  }

  public void setResourceMeta(ResourceMeta meta) {
    resourceMeta = meta;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 41 * hash + Objects.hashCode(this.bib);
    hash = 41 * hash + Objects.hashCode(this.heldItems);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Holdings other = (Holdings) obj;
    if (!Objects.equals(this.bib, other.bib)) {
      return false;
    }
    // TODO: compare collections
    if (!Objects.equals(this.heldItems, other.heldItems)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Holdings{" + "bib=" + bib + ", heldItems=" + heldItems + ", resourceMeta=" + resourceMeta + '}';
  }

}
