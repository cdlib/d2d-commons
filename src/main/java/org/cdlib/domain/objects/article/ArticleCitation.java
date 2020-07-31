package org.cdlib.domain.objects.article;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.cdlib.domain.objects.author.Author;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.identifier.Identifier;
import org.cdlib.util.JSON;;

public class ArticleCitation {
  
  private Author author;
  private Bib container;
  private String endPage;
  private List<Identifier> identifiers;
  private String issue;
  private String monthOfPublication;
  private String pages;
  private String seasonOfPublication;
  private String startPage;
  private String title;
  private String volume;
  private String yearOfPublication;
  
  public ArticleCitation() {
    identifiers = new ArrayList<>();
  }
  
  public ArticleCitation(ArticleCitation source) {
    this.yearOfPublication = source.yearOfPublication;
    this.author = source.author;
    this.container = source.container;
    this.issue = source.issue;
    this.pages = source.pages;
    this.volume = source.volume;
    this.identifiers = deepCopy(identifiers);
  }
  
  public Author getAuthor() {
    return author;
  }
  
  public Bib getContainer() {
    return container;
  }
  
  public List<Identifier> getIdentifiers() {
    return identifiers;
  }
  
  public String getIssue() {
    return issue;
  }

  public String getPages() {
    return pages;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getVolume() {
    return volume;
  }
  
  public String getYearOfPublication() {
    return yearOfPublication;
  }
  
  public void setAuthor(Author author) {
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
  
  public void setVolume(String volume) {
    this.volume = volume;
  }
  
  public void setYearOfPublication(String articleYear) {
    this.yearOfPublication = articleYear;
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
