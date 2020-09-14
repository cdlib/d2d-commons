package org.cdlib.domain.objects.bib;

import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import org.cdlib.util.DateUtil;

public class PublicationEvent {

  private String date;
  private String place;
  private String publisher;
  private String season;
  private String year;
  private String month;

  public PublicationEvent() {}

  public PublicationEvent(PublicationEvent source) {
    this.date = source.date;
    this.place = source.place;
    this.publisher = source.publisher;
    this.season = source.season;
    this.year = source.year;
    this.month = source.month;
  }

  // This is the raw unnormalized date string as it may be expressed in the source
  // It could be in various formats
  public String getDate() {
    return date;
  }

  @NotEmpty(message = "PublicationEvent must have place.")
  public String getPlace() {
    return place;
  }

  @NotEmpty (message = "PublicationEvent must have publisher.")
  public String getPublisher() {
    return publisher;
  }

  public String getSeason() {
    return season;
  }

  @NotEmpty(message = "PublicationEvent must have year.")
  public String getYear() {
    if (DateUtil.looksLikeBibYear(year)) {
      return year;
    }
    return DateUtil.extractYear(date);
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, month, place, publisher, season, year);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof PublicationEvent)) {
      return false;
    }
    PublicationEvent other = (PublicationEvent) obj;
    return Objects.equals(date, other.date) && Objects.equals(month, other.month) && Objects.equals(place, other.place) && Objects.equals(publisher, other.publisher)
        && Objects.equals(season, other.season) && Objects.equals(year, other.year);
  }
  
}
