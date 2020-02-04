package org.cdlib.domain.objects.bib;

/*
 * Brings together all bibliographic identifiers.
 * 
 * Any identifier can be null, meaning that a Bib 
 * lacks an identifier of that type.
 * 
 * If any identifier is not null, then it must have a value 
 * and an authority.
 */
public class BibIdentifiers {

  private ISBN isbn;
  private ISSN issn;
  private OclcNumber oclcNumber;

  public BibIdentifiers() {
    
  }
  
  public BibIdentifiers(BibIdentifiers source) {
    this.isbn = source.isbn;
    this.issn = source.issn;
    this.oclcNumber = source.oclcNumber;
  }

  public ISBN getIsbn() {
    return isbn;
  }

  public ISSN getIssn() {
    return issn;
  }

  public OclcNumber getOclcNumber() {
    return oclcNumber;
  }

  public void setIsbn(ISBN isbn) {
    this.isbn = isbn;
  }

  public void setIssn(ISSN issn) {
    this.issn = issn;
  }

  public void setOclcNumber(OclcNumber oclcNumber) {
    this.oclcNumber = oclcNumber;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
    result = prime * result + ((issn == null) ? 0 : issn.hashCode());
    result = prime * result + ((oclcNumber == null) ? 0 : oclcNumber.hashCode());
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
    BibIdentifiers other = (BibIdentifiers) obj;
    if (isbn == null) {
      if (other.isbn != null)
        return false;
    } else if (!isbn.equals(other.isbn))
      return false;
    if (issn == null) {
      if (other.issn != null)
        return false;
    } else if (!issn.equals(other.issn))
      return false;
    if (oclcNumber == null) {
      if (other.oclcNumber != null)
        return false;
    } else if (!oclcNumber.equals(other.oclcNumber))
      return false;
    return true;
  }

}
