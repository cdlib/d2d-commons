package org.cdlib.domain.objects.bib;

import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PublicationEvent {

  private String date;
  private String place;
  private String publisher;

  public PublicationEvent() {}

  public PublicationEvent(PublicationEvent source) {
    this.date = source.date;
    this.place = source.place;
    this.publisher = source.publisher;
  }

  public String getDate() {
    return date;
  }

  public String getPlace() {
    return place;
  }

  public String getPublisher() {
    return publisher;
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

  @Override
  public int hashCode() {
    return Objects.hash(date, place, publisher);
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
    return Objects.equals(date, other.date) && Objects.equals(place, other.place) && Objects.equals(publisher, other.publisher);
  }

}
