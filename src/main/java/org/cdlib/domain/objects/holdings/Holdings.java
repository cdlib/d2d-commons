package org.cdlib.domain.objects.holdings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.Link;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.meta.ResourceMeta;
import org.cdlib.util.JSON;

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
  
  @Valid
  @NotNull
  private List<Link> linksToContent;
  
  @Valid
  @NotNull(message = "Resource metadata is requird.")
  private ResourceMeta resourceMeta;

  public Holdings() {
    resourceMeta = new ResourceMeta();
    heldItems = new ArrayList<>();
  }

  public Holdings(Holdings source) {
    if (source.bib != null) {
      this.bib = new Bib(source.bib);
    }
    if (source.heldItems != null) {
      this.heldItems.addAll(source.heldItems);
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

  public List<Link> getLinksToContent() {
    return linksToContent;
  }

  public void setLinksToContent(List<Link> linksToContent) {
    this.linksToContent = linksToContent;
  }

  public ResourceMeta getResourceMeta() {
    return resourceMeta;
  }

  public void setResourceMeta(ResourceMeta meta) {
    resourceMeta = meta;
  }

  @Override
  public int hashCode() {
    return Objects.hash(bib, heldItems, linksToContent, resourceMeta);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Holdings)) {
      return false;
    }
    Holdings other = (Holdings) obj;
    return Objects.equals(bib, other.bib) 
        && Objects.equals(heldItems, other.heldItems) 
        && Objects.equals(linksToContent, other.linksToContent) 
        && Objects.equals(resourceMeta, other.resourceMeta);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
