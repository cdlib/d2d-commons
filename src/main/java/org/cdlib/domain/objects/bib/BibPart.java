package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
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
  private String title;
  private String volume;
  private PublicationEvent publicationEvent;
  private ResourceMeta resourceMeta;
  
  public BibPart() {
    identifiers = new ArrayList<>();
    container = new Bib();
    publicationEvent = new PublicationEvent();
  }
  
  public BibPart(BibPart source) {
    this.author = source.author;
    this.container = source.container;
    this.issue = source.issue;
    this.pages = source.pages;
    this.volume = source.volume;
    this.identifiers = deepCopy(identifiers);
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
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public void setVolume(String volume) {
    this.volume = volume;
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

  public PublicationEvent getPublicationEvent() {
    return publicationEvent;
  }

  public void setPublicationEvent(PublicationEvent publicationEvent) {
    this.publicationEvent = publicationEvent;
  }
  
}
