package org.cdlib.domain.objects.bib;

import java.util.Objects;
import org.cdlib.util.JSON;

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
    return Objects.hash(isbn, issn, oclcNumber);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof BibIdentifiers)) {
      return false;
    }
    BibIdentifiers other = (BibIdentifiers) obj;
    return Objects.equals(isbn, other.isbn) && Objects.equals(issn, other.issn) && Objects.equals(oclcNumber, other.oclcNumber);
  }
  
  @Override
  public String toString() {
    return JSON.serialize(this);
  }

 
}
