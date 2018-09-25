package org.cdlib.domain.objects.holdings;

import org.cdlib.domain.objects.bib.Seriality;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.consortium.ShelvingLocation;

/**
 * A mono or serial item held at a lending institution.
 */
public class Item {

  private String oclcNumber;

  @NotNull(message = "Item carrier is required.")
  private Carrier carrier;

  @Valid
  @NotNull(message = "Item circulation status is required.")
  private CircStatus circStatus;

  @NotNull(message = "Item seriality is required.")
  private Seriality seriality;

  @NotNull(message = "Item shelving location is required.")
  @Valid
  private ShelvingLocation shelvingLocation;
  
  private String opacShelvingLocationName;

  @NotNull(message = "Item call number is required.")
  private String callNumber;

  private String summaryHoldings;
  
  private String lhrCode;

  public Item() {
  }

  public Item(@Valid Item source) {
    this.oclcNumber = source.oclcNumber;
    this.carrier = source.carrier;
    this.circStatus = new CircStatus(source.circStatus);
    this.seriality = source.seriality;
    this.shelvingLocation = new ShelvingLocation(source.shelvingLocation);
    this.opacShelvingLocationName = source.opacShelvingLocationName;
    this.callNumber = source.callNumber;
    this.summaryHoldings = source.summaryHoldings;
  }

  public String getCallNumber() {
    return callNumber;
  }

  public Carrier getCarrier() {
    return carrier;
  }

  public CircStatus getCircStatus() {
    return circStatus;
  }

  public Seriality getSeriality() {
    return seriality;
  }

  public String getOclcNumber() {
    return oclcNumber;
  }

  public ShelvingLocation getShelvingLocation() {
    return shelvingLocation;
  }

  public String getLhrCode() {
    return lhrCode;
  }
  
  /**
   * The identifier of the shelving location in the local institution's system.
   */
  public String getOpacShelvingLocationName() {
    return opacShelvingLocationName;
  }

  public void setSummaryHoldings(String summaryHoldings) {
    this.summaryHoldings = summaryHoldings;
  }

  public void setCallNumber(String callNumber) {
    this.callNumber = callNumber;
  }

  public void setCarrier(Carrier carrier) {
    this.carrier = carrier;
  }

  public void setCircStatus(CircStatus circStatus) {
    this.circStatus = circStatus;
  }

  public void setSeriality(Seriality seriality) {
    this.seriality = seriality;
  }

  public void setOclcNumber(String oclcNumber) {
    this.oclcNumber = oclcNumber;
  }

  public void setShelvingLocation(ShelvingLocation shelvingLocation) {
    this.shelvingLocation = shelvingLocation;
  }
  
  public void setOpacShelvingLocationName(String shelf) {
    this.opacShelvingLocationName = shelf;
  }

  public void setLhrCode(String lhrCode) {
    this.lhrCode = lhrCode;
  }

  public String getSummaryHoldings() {
    return summaryHoldings;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 61 * hash + Objects.hashCode(this.oclcNumber);
    hash = 61 * hash + Objects.hashCode(this.carrier);
    hash = 61 * hash + Objects.hashCode(this.circStatus);
    hash = 61 * hash + Objects.hashCode(this.seriality);
    hash = 61 * hash + Objects.hashCode(this.shelvingLocation);
    hash = 61 * hash + Objects.hashCode(this.opacShelvingLocationName);
    hash = 61 * hash + Objects.hashCode(this.callNumber);
    hash = 61 * hash + Objects.hashCode(this.summaryHoldings);
    hash = 61 * hash + Objects.hashCode(this.lhrCode);
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
    final Item other = (Item) obj;
    if (!Objects.equals(this.oclcNumber, other.oclcNumber)) {
      return false;
    }
    if (!Objects.equals(this.opacShelvingLocationName, other.opacShelvingLocationName)) {
      return false;
    }
    if (!Objects.equals(this.callNumber, other.callNumber)) {
      return false;
    }
    if (!Objects.equals(this.summaryHoldings, other.summaryHoldings)) {
      return false;
    }
    if (!Objects.equals(this.lhrCode, other.lhrCode)) {
      return false;
    }
    if (this.carrier != other.carrier) {
      return false;
    }
    if (!Objects.equals(this.circStatus, other.circStatus)) {
      return false;
    }
    if (this.seriality != other.seriality) {
      return false;
    }
    if (!Objects.equals(this.shelvingLocation, other.shelvingLocation)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Item{" + "oclcNumber=" + oclcNumber + ", carrier=" + carrier + ", circStatus=" + circStatus + ", seriality=" + seriality + ", shelvingLocation=" + shelvingLocation + ", opacShelvingLocationName=" + opacShelvingLocationName + ", callNumber=" + callNumber + ", summaryHoldings=" + summaryHoldings + ", lhrId=" + lhrCode + '}';
  }

}
