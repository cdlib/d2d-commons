package org.cdlib.domain.objects.bib;

public class Identifiers<T extends StandardNumber> {

  private LCCN lccn;
  private OclcNumber oclcNumber;
  private T standardNumber;

  public Identifiers() {}

  public Identifiers(Identifiers<T> source) {
    this.lccn = source.lccn;
    this.oclcNumber = source.oclcNumber;
    this.standardNumber = source.standardNumber;
  }

  public LCCN getLccn() {
    return lccn;
  }

  public OclcNumber getOclcNumber() {
    return oclcNumber;
  }

  public StandardNumber getStandardNumber() {
    return standardNumber;
  }

  public void setLccn(LCCN lccn) {
    this.lccn = lccn;
  }

  public void setOclcNumber(OclcNumber oclcNumber) {
    this.oclcNumber = oclcNumber;
  }

  public void setStandardNumber(T standardNumber) {
    this.standardNumber = standardNumber;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((lccn == null) ? 0 : lccn.hashCode());
    result = prime * result + ((oclcNumber == null) ? 0 : oclcNumber.hashCode());
    result = prime * result + ((standardNumber == null) ? 0 : standardNumber.hashCode());
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
    Identifiers<T> other = (Identifiers<T>) obj;
    if (lccn == null) {
      if (other.lccn != null)
        return false;
    } else if (!lccn.equals(other.lccn))
      return false;
    if (oclcNumber == null) {
      if (other.oclcNumber != null)
        return false;
    } else if (!oclcNumber.equals(other.oclcNumber))
      return false;
    if (standardNumber == null) {
      if (other.standardNumber != null)
        return false;
    } else if (!standardNumber.equals(other.standardNumber))
      return false;
    return true;
  }

  

}
