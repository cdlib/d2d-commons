package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.util.JSON;

/*
 * Collects various MARC related title data.
 * TODO: Extend to include full RDA title data?
 */
public class Title {

  // 245
  private String mainTitle;
  private String nonRomanizedTitle;
  private List<String> otherTitles;
  
  public Title() {
    otherTitles = new ArrayList<>();
  }

  /*
   * 
   * Title printed on the bibliographic instance, 
   * in the original character set
   * 
   */
  @NotNull
  @NotEmpty
  public String getMainTitle() {
    return mainTitle;
  }

  /*
   * 
   * title as transliterated into Latin characters;
   * if the canonical title is in Latin characters, 
   * this is the same as the canonical title
   * 
   */
  @NotNull
  @NotEmpty
  public String getNonRomanizedTitle() {
    return nonRomanizedTitle;
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


  public void setMainTitle(String canonicalTitle) {
    this.mainTitle = canonicalTitle;
  }

  public void setNonRomanizedTitle(String romanizedTitle) {
    this.nonRomanizedTitle = romanizedTitle;
  }

  public void setOtherTitles(List<String> otherTitles) {
    this.otherTitles = otherTitles;
  }
  

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mainTitle == null) ? 0 : mainTitle.hashCode());
    result = prime * result + ((nonRomanizedTitle == null) ? 0 : nonRomanizedTitle.hashCode());
    result = prime * result + ((otherTitles == null) ? 0 : otherTitles.hashCode());
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
    if (nonRomanizedTitle == null) {
      if (other.nonRomanizedTitle != null)
        return false;
    } else if (!nonRomanizedTitle.equals(other.nonRomanizedTitle))
      return false;
    if (otherTitles == null) {
      if (other.otherTitles != null)
        return false;
    } else if (!otherTitles.equals(other.otherTitles))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
