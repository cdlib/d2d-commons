package org.cdlib.domain.objects.bib;

import java.util.List;
import org.cdlib.util.JSON;

public class Author {
  
  private String primaryAuthor;
  private List<String> authors;
  private String corporateAuthor;
  
  public String getPrimaryAuthor() {
    return primaryAuthor;
  }
  
  public List<String> getAuthors() {
    return authors;
  }
  
  public String getCorporateAuthor() {
    return corporateAuthor;
  }
  
  public void setPrimaryAuthor(String primaryAuthor) {
    this.primaryAuthor = primaryAuthor;
  }
  
  public void setAuthors(List<String> authors) {
    this.authors = authors;
  }
  
  public void setCorporateAuthor(String corporateAuthor) {
    this.corporateAuthor = corporateAuthor;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((authors == null) ? 0 : authors.hashCode());
    result = prime * result + ((corporateAuthor == null) ? 0 : corporateAuthor.hashCode());
    result = prime * result + ((primaryAuthor == null) ? 0 : primaryAuthor.hashCode());
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
    Author other = (Author) obj;
    if (authors == null) {
      if (other.authors != null)
        return false;
    } else if (!authors.equals(other.authors))
      return false;
    if (corporateAuthor == null) {
      if (other.corporateAuthor != null)
        return false;
    } else if (!corporateAuthor.equals(other.corporateAuthor))
      return false;
    if (primaryAuthor == null) {
      if (other.primaryAuthor != null)
        return false;
    } else if (!primaryAuthor.equals(other.primaryAuthor))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
