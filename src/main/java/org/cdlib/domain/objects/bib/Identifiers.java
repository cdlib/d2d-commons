package org.cdlib.domain.objects.bib;

import java.util.Objects;

public class Identifiers {

  private OclcNumber oclcNumber;
  private ISSN issn;
  private ISBN isbn;

  public ISBN getIsbn() {
    return isbn;
  }

  public void setIsbn(ISBN isbn) {
    this.isbn = isbn;
  }

  public ISSN getIssn() {
    return issn;
  }

  public void setIssn(ISSN issn) {
    this.issn = issn;
  }

  public OclcNumber getOclcNumber() {
    return oclcNumber;
  }

  public void setOclcNumber(OclcNumber oclcNumber) {
    this.oclcNumber = oclcNumber;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 37 * hash + Objects.hashCode(this.oclcNumber);
    hash = 37 * hash + Objects.hashCode(this.issn);
    hash = 37 * hash + Objects.hashCode(this.isbn);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Identifiers other = (Identifiers) obj;
    if (!Objects.equals(this.oclcNumber, other.oclcNumber)) {
      return false;
    }
    if (!Objects.equals(this.issn, other.issn)) {
      return false;
    }
    if (!Objects.equals(this.isbn, other.isbn)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Identifiers{" + "oclcNumber=" + oclcNumber + ", issn=" + issn + ", isbn=" + isbn + '}';
  }

}
