package org.cdlib.domain.objects.hathi;

import javax.validation.constraints.NotEmpty;
import org.cdlib.domain.objects.consortium.MemberCode;

/*
 * A record for an bibliographic item from the hathitrust
 * overlap reports. Such items are represented both in the 
 * member institution catalog, and in the HathiTrust collection.
 * 
 */
public class OverlapItem {
  
  private String oclcNumber;
  private String sysId;
  private String type;
  private String access;
  private String rights;
  private MemberCode institution;
  
  @NotEmpty
  public String getOclcNumber() {
    return oclcNumber;
  }
  
  public String getSysId() {
    return sysId;
  }
  
  @NotEmpty
  public String getType() {
    return type;
  }
  
  public String getAccess() {
    return access;
  }
  
  public String getRights() {
    return rights;
  }
  
  @NotEmpty
  public MemberCode getInstitution() {
    return institution;
  }
  
  public void setOclcNumber(String oclcNumber) {
    this.oclcNumber = oclcNumber;
  }
  
  public void setSysId(String sysId) {
    this.sysId = sysId;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public void setAccess(String access) {
    this.access = access;
  }
  
  public void setRights(String rights) {
    this.rights = rights;
  }
  
  public void setInstitution(MemberCode institution) {
    this.institution = institution;
  }

}