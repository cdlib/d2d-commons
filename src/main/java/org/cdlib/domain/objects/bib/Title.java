package org.cdlib.domain.objects.bib;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.cdlib.util.JSON;

/*
 * Collects various MARC related title data. TODO: Extend to include full RDA title data?
 */
public class Title {

  private String mainTitle;
  private String nonRomanizedTitle;
  private List<String> otherTitles;

  public Title(String mainTitle) {
    this.mainTitle = mainTitle;
    otherTitles = new ArrayList<>();
  }

  public Title() {
    otherTitles = new ArrayList<>();
  }

  /*
   * 
   * Title printed on the bibliographic instance, in the original character set
   * 
   */
  @NotNull
  @NotEmpty
  public String getMainTitle() {
    return mainTitle;
  }

  /*
   * 
   * title as transliterated into Latin characters; if the canonical title is in Latin characters,
   * this is the same as the canonical title
   * 
   */
  @NotEmpty
  public String getNonRomanizedTitle() {
    return nonRomanizedTitle;
  }

  /*
   * 
   * other titles that this bib may be known as this value may be an empty set, must not be null
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
    return Objects.hash(mainTitle, nonRomanizedTitle, otherTitles);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Title)) {
      return false;
    }
    Title other = (Title) obj;
    return Objects.equals(mainTitle, other.mainTitle) && Objects.equals(nonRomanizedTitle, other.nonRomanizedTitle) && Objects.equals(otherTitles, other.otherTitles);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

}
