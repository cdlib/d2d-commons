package org.cdlib.domain.objects.author;

public class Author {
  
  private String fullName;
  private String firstName;
  private String initials;
  private String firstInitial;
  private String middleInitial;
  
  public String getFullName() {
    return fullName;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public String getInitials() {
    return initials;
  }
  
  public String getFirstInitial() {
    return firstInitial;
  }
  
  public String getMiddleInitial() {
    return middleInitial;
  }
  
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public void setInitials(String initials) {
    this.initials = initials;
  }
  
  public void setFirstInitial(String firstInitial) {
    this.firstInitial = firstInitial;
  }
  
  public void setMiddleInitial(String middleInitial) {
    this.middleInitial = middleInitial;
  }
  
}
