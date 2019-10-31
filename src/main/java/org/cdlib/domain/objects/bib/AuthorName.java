package org.cdlib.domain.objects.bib;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AuthorName {
  
  private String lastName;
  private String firstName;
  private String middleName;
  
  @NotNull
  @NotEmpty
  public String getLastName() {
    return lastName;
  }
  
  @NotEmpty
  public String getFirstName() {
    return firstName;
  }
  
  @NotEmpty
  public String getMiddleName() {
    return middleName;
  }
  
}
