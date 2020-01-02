package org.cdlib.domain.objects.bib;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.util.JSON;

public class Title {

  private String mainTitle;
  private String romanizedTitle;
  private List<String> otherTitles;
  private String precedingTitle;
  private String succeedingTitle;

  /*
   * 
   * title printed on the bibliographic instance, in the original character set
   * 
   */
  @NotNull
  @NotEmpty
  public String getMainTitle() {
    return mainTitle;
  }

  /*
   * 
   * title as transliterated into latin characters
   * if the canonical title is in latin characters, 
   * this is the same as the canonical title
   * 
   */
  @NotNull
  @NotEmpty
  public String getRomanizedTitle() {
    return romanizedTitle;
  }

  /*
   * 
   * other titles that this bib may be known as
   * this value may be an empty set, must not be null
   * 
   */
  @NotNull
  public List<String> getOtherTitles() {
    return otherTitles;
  }

  /*
   * 
   * the preceding title of a continuing resource
   * this value may be null, and must not be an empty string
   * 
   */
  @NotEmpty
  public String getPrecedingTitle() {
    return precedingTitle;
  }

  /*
   * 
   * the succeeding title of a continuing resource
   * this value may be null, and must not be an empty string
   * 
   */
  @NotEmpty
  public String getSucceedingTitle() {
    return succeedingTitle;
  }

  public void setMainTitle(String canonicalTitle) {
    this.mainTitle = canonicalTitle;
  }

  public void setRomanizedTitle(String romanizedTitle) {
    this.romanizedTitle = romanizedTitle;
  }

  public void setOtherTitles(List<String> otherTitles) {
    this.otherTitles = otherTitles;
  }

  public void setPrecedingTitle(String precedingTitle) {
    this.precedingTitle = precedingTitle;
  }

  public void setSucceedingTitle(String succeedingTitle) {
    this.succeedingTitle = succeedingTitle;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mainTitle == null) ? 0 : mainTitle.hashCode());
    result = prime * result + ((otherTitles == null) ? 0 : otherTitles.hashCode());
    result = prime * result + ((precedingTitle == null) ? 0 : precedingTitle.hashCode());
    result = prime * result + ((romanizedTitle == null) ? 0 : romanizedTitle.hashCode());
    result = prime * result + ((succeedingTitle == null) ? 0 : succeedingTitle.hashCode());
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
    Title other = (Title) obj;
    if (mainTitle == null) {
      if (other.mainTitle != null)
        return false;
    } else if (!mainTitle.equals(other.mainTitle))
      return false;
    if (otherTitles == null) {
      if (other.otherTitles != null)
        return false;
    } else if (!otherTitles.equals(other.otherTitles))
      return false;
    if (precedingTitle == null) {
      if (other.precedingTitle != null)
        return false;
    } else if (!precedingTitle.equals(other.precedingTitle))
      return false;
    if (romanizedTitle == null) {
      if (other.romanizedTitle != null)
        return false;
    } else if (!romanizedTitle.equals(other.romanizedTitle))
      return false;
    if (succeedingTitle == null) {
      if (other.succeedingTitle != null)
        return false;
    } else if (!succeedingTitle.equals(other.succeedingTitle))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
