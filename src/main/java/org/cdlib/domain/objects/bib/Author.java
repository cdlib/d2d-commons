package org.cdlib.domain.objects.bib;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.util.JSON;

public class Author {
  
  private AuthorName primaryAuthor;
  private List<AuthorName> authors;
  private String corporateAuthor;
  
  @NotNull
  @NotEmpty
  public AuthorName getPrimaryAuthor() {
    return primaryAuthor;
  }
  
  @NotNull
  @NotEmpty
  public List<AuthorName> getAuthors() {
    return authors;
  }
  
  @NotEmpty
  public String getCorporateAuthor() {
    return corporateAuthor;
  }
  
  public void setPrimaryAuthor(AuthorName primaryAuthor) {
    this.primaryAuthor = primaryAuthor;
  }
  
  public void setAuthors(List<AuthorName> authors) {
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
