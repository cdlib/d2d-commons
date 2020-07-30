package org.cdlib.domain.objects.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.identifier.Identifier;
import org.cdlib.util.JSON;;

public class ArticleCitation {
  
  private String articleYear;
  private String author;
  private Bib container;
  private String issue;
  private String pageRange;
  private String volume;
  private List<Identifier> identifiers;
  
  public ArticleCitation() {
    identifiers = new ArrayList<>();
  }
  
  public ArticleCitation(ArticleCitation source) {
    this.articleYear = source.articleYear;
    this.author = source.author;
    this.container = source.container;
    this.issue = source.issue;
    this.pageRange = source.pageRange;
    this.volume = source.volume;
    this.identifiers = deepCopy(identifiers);
  }
  
  public String getArticleYear() {
    return articleYear;
  }
  
  public String getAuthor() {
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
  
  public String getPageRange() {
    return pageRange;
  }
  
  public String getVolume() {
    return volume;
  }
  
  public void setArticleYear(String articleYear) {
    this.articleYear = articleYear;
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
  
  private List<Identifier> deepCopy(List<Identifier> original) {
    return original.stream()
        .map(this::copyIdentifier)
        .collect(Collectors.toList());
  }
  
  private Identifier copyIdentifier(Identifier id) {
    String intermediate = JSON.serialize(id);
    return JSON.deserialize(intermediate, id.getClass());
  }
  
  public void setIssue(String issue) {
    this.issue = issue;
  }
  
  public void setPageRange(String pageRange) {
    this.pageRange = pageRange;
  }
  
  public void setVolume(String volume) {
    this.volume = volume;
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleYear, author, container, identifiers, issue, pageRange, volume);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof ArticleCitation)) {
      return false;
    }
    ArticleCitation other = (ArticleCitation) obj;
    return Objects.equals(articleYear, other.articleYear) && Objects.equals(author, other.author) && Objects.equals(container, other.container) && Objects.equals(identifiers, other.identifiers)
        && Objects.equals(issue, other.issue) && Objects.equals(pageRange, other.pageRange) && Objects.equals(volume, other.volume);
  }
  
  @Override
  public String toString() {
    return JSON.serialize(this);
  }
  
}
