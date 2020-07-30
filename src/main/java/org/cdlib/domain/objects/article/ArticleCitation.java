package org.cdlib.domain.objects.article;

import org.cdlib.domain.objects.bib.Bib;

public class ArticleCitation {
  
  private String articleYear;
  private String author;
  private Bib container;
  private String issue;
  private String pageRange;
  private String volume;
  
  public String getArticleYear() {
    return articleYear;
  }
  
  public String getAuthor() {
    return author;
  }
  
  public Bib getContainer() {
    return container;
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
  
  public void setIssue(String issue) {
    this.issue = issue;
  }
  
  public void setPageRange(String pageRange) {
    this.pageRange = pageRange;
  }
  
  public void setVolume(String volume) {
    this.volume = volume;
  }

}
