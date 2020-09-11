package org.cdlib.domain.objects.identifier;

import java.util.ArrayList;
import java.util.List;
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
@Deprecated
public class BibIdentifiers {

  private ISBN isbn;
  private ISSN issn;
  private OclcNumber oclcNumber;
  private LCCN lccn;
  private EISSN eIssn;
  private EISBN eIsbn;

  public BibIdentifiers() {
  }
  
  public BibIdentifiers(BibIdentifiers source) {
    this.isbn = source.isbn;
    this.issn = source.issn;
    this.oclcNumber = source.oclcNumber;
    this.lccn = source.lccn;
    this.eIssn = source.eIssn;
    this.eIsbn = source.eIsbn;
  }
  
  public List<Identifier> asList() {
    List<Identifier> ids = new ArrayList<>();
    if (isbn != null) {
      ids.add(isbn);
    }
    if (issn != null) {
      ids.add(issn);
    }
    if (oclcNumber != null) {
      ids.add(oclcNumber);
    }
    if (lccn != null) {
      ids.add(lccn);
    }
    if (eIssn != null) {
      ids.add(eIssn);
    }
    if (eIsbn != null) {
      ids.add(eIsbn);
    }
    return ids;
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
  
  public LCCN getLccn() {
    return lccn;
  }

  public EISSN geteIssn() {
    return eIssn;
  }

  public void setLccn(LCCN lccn) {
    this.lccn = lccn;
  }

  public void seteIssn(EISSN eIssn) {
    this.eIssn = eIssn;
  }

  @Override
  public int hashCode() {
    return Objects.hash(eIssn, isbn, issn, lccn, oclcNumber);
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
    return Objects.equals(eIssn, other.eIssn) && Objects.equals(isbn, other.isbn) && Objects.equals(issn, other.issn) && Objects.equals(lccn, other.lccn)
        && Objects.equals(oclcNumber, other.oclcNumber);
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

  public EISBN geteIsbn() {
    return eIsbn;
  }

  public void seteIsbn(EISBN eIsbn) {
    this.eIsbn = eIsbn;
  }

 
}
