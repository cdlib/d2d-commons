package org.cdlib.domain.objects.patron;

/**
 * The patron categories that are expected to be returned by the Patron service.
 *
 */
public enum PatronCategory {
  
  FACULTY ("faculty"),
  STAFF ("staff"),
  GRADUATE ("graduate"),
  UNDERGRADUATE ("undergraduate"),
  VISITING ("visiting"),
  RETIREE ("retiree"),
  OTHER ("other");
  
  private String designation;
  
  PatronCategory (String designation) {
    this.designation = designation;
  }
  
  public String getDesignation() {
    return designation;
  }
  
  /**
   * Looks up a patron category based on a String designation.
   * Can return null if the designation does not map to an enumerated category.
   * 
   * @param designation the string designating the patron category
   * @return the patron category enum or null if not found
   */
  public static PatronCategory byDesignation(String designation) {
    for (PatronCategory category : PatronCategory.values()) {
      if (designation.equalsIgnoreCase(category.getDesignation())) {
        return category;
      }
    }
    return null;
  }
  

}
