package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.identifier.Identifier;
import org.cdlib.domain.objects.meta.ResourceMeta;
import org.cdlib.util.JSON;

public class BibPart {
  
  private String author;
  private Bib container;
  private List<Identifier> identifiers;
  private String issue;
  private String pages;
  private PublicationEvent publicationEvent;
  private ResourceMeta resourceMeta;
  private String title;
  private String volume;
  
  public BibPart() {
    identifiers = new ArrayList<>();
    container = new Bib();
    publicationEvent = new PublicationEvent();
  }
  
  public BibPart(BibPart source) {
    this.author = source.author;
    this.container = new Bib(source.container);
    this.issue = source.issue;
    this.pages = source.pages;
    this.volume = source.volume;
    this.identifiers = deepCopy(identifiers);
    this.resourceMeta = source.resourceMeta;
  }
  
  @NotEmpty
  public String getAuthor() {
    return author;
  }
  
  @NotNull
  public Bib getContainer() {
    return container;
  }
  
  @NotNull
  public List<Identifier> getIdentifiers() {
    return identifiers;
  }
  
  public String getIssue() {
    return issue;
  }
  
  public String getPages() {
    return pages;
  }
  
  public PublicationEvent getPublicationEvent() {
    return publicationEvent;
  }
  
  public ResourceMeta getResourceMeta() {
    return resourceMeta;
  }
  
  @NotEmpty
  public String getTitle() {
    return title;
  }
  
  public String getVolume() {
    return volume;
  }
  
  public void setAuthor(String author) {
    this.author = author;
  }
  
  public void setContainer(Bib container) {
    this.container = container;
  }
  
  public void setIdentifiers(List<Identifier> identifiers) {
    this.identifiers = deepCopy(identifiers);
  }
  
  public void setIssue(String issue) {
    this.issue = issue;
  }
  
  public void setPages(String pageRange) {
    this.pages = pageRange;
  }
  
  public void setPublicationEvent(PublicationEvent publicationEvent) {
    this.publicationEvent = publicationEvent;
  }

  public void setResourceMeta(ResourceMeta resourceMeta) {
    this.resourceMeta = resourceMeta;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(author, container, identifiers, issue, pages, publicationEvent, resourceMeta, title, volume);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof BibPart)) {
      return false;
    }
    BibPart other = (BibPart) obj;
    return Objects.equals(author, other.author) && Objects.equals(container, other.container) && Objects.equals(identifiers, other.identifiers) && Objects.equals(issue, other.issue)
        && Objects.equals(pages, other.pages) && Objects.equals(publicationEvent, other.publicationEvent) && Objects.equals(resourceMeta, other.resourceMeta) && Objects.equals(title, other.title)
        && Objects.equals(volume, other.volume);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }
  
  private Identifier copyIdentifier(Identifier id) {
    String intermediate = JSON.serialize(id);
    return JSON.deserialize(intermediate, id.getClass());
  }
 
  private List<Identifier> deepCopy(List<Identifier> original) {
    return original.stream()
        .map(this::copyIdentifier)
        .collect(Collectors.toList());
  }

  
}
