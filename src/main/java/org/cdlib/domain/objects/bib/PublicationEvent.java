package org.cdlib.domain.objects.bib;

public class PublicationEvent {
  
  // 008 -- first date: can use object
  // call it year?? Can be "unknown", e.g. 192U.
  // Indicate somehow that the date is unknown?
  
  // 260 gives strings for place and publisher
  // 008 gives you country code/state
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
    final int prime = 31;
    int result = 1;
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((place == null) ? 0 : place.hashCode());
    result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
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
    PublicationEvent other = (PublicationEvent) obj;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (place == null) {
      if (other.place != null)
        return false;
    } else if (!place.equals(other.place))
      return false;
    if (publisher == null) {
      if (other.publisher != null)
        return false;
    } else if (!publisher.equals(other.publisher))
      return false;
    return true;
  }
  
}
