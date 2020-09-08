package org.cdlib.domain.objects.bibpart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.author.Author;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.identifier.Identifier;
import org.cdlib.util.JSON;

public class BibPart {
  
  private Author author;
  private Bib container;
  private List<Identifier> identifiers;
  private String issue;
  private String monthOfPublication;
  private String pages;
  private String seasonOfPublication;
  private String title;
  private String volume;
  private String yearOfPublication;
  
  public BibPart() {
    identifiers = new ArrayList<>();
    container = new Bib();
  }
  
  public BibPart(BibPart source) {
    this.yearOfPublication = source.yearOfPublication;
    this.author = source.author;
    this.container = source.container;
    this.issue = source.issue;
    this.pages = source.pages;
    this.volume = source.volume;
    this.identifiers = deepCopy(identifiers);
  }
  
  @NotEmpty
  public Author getAuthor() {
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
  
  public String getMonthOfPublication() {
    return monthOfPublication;
  }
  
  public String getPages() {
    return pages;
  }
  
  public String getSeasonOfPublication() {
    return seasonOfPublication;
  }
  
  @NotEmpty
  public String getTitle() {
    return title;
  }
  
  public String getVolume() {
    return volume;
  }

  @NotEmpty
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
  
  public void setMonthOfPublication(String monthOfPublication) {
    this.monthOfPublication = monthOfPublication;
  }
  
  public void setPages(String pageRange) {
    this.pages = pageRange;
  }
  
  public void setSeasonOfPublication(String seasonOfPublication) {
    this.seasonOfPublication = seasonOfPublication;
  }
  
  public void setTitle(String title) {
    this.title = title;
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
