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

  @NotNull(message = "Item call number is required.")
  private String callNumber;

  @NotNull(message = "Item carrier is required.")
  private Carrier carrier;

  @Valid
  @NotNull(message = "Item circulation status is required.")
  private CircStatus circStatus;
  
  private String copyNumber;

  private boolean localUseOnly;

  private Boolean massDigitizedContent;

  private String oclcLhrCode;

  private String oclcNumber;

  private String opacShelvingLocationName;

  @NotNull(message = "Item seriality is required.")
  private Seriality seriality;

  @NotNull(message = "Item shelving location is required.")
  @Valid
  private ShelvingLocation shelvingLocation;
  
  private String summaryHoldings;

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
    this.massDigitizedContent = source.massDigitizedContent;
    this.oclcLhrCode = source.oclcLhrCode;
    this.localUseOnly = source.localUseOnly;
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
    if (!Objects.equals(this.oclcLhrCode, other.oclcLhrCode)) {
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

  /**
   * The call number of the item specified in the OPAC record.
   */
  public String getCallNumber() {
    return callNumber;
  }

  /**
   * Specified whether the item is physical or electronic.
   *
   */
  public Carrier getCarrier() {
    return carrier;
  }

  /**
   *
   * Values that specify whether the item is available to circulate.
   *
   */
  public CircStatus getCircStatus() {
    return circStatus;
  }

  public String getCopyNumber() {
    return copyNumber;
  }

  /**
   * The code for the the items local holdings record as specified by OCLC.
   *
   * If an LHR for the item exists, this value holds the LHR code as provided by
   * OCLC. This value is used to look up the shelving location in the
   * CDL-managed table.
   *
   */
  public String getOclcLhrCode() {
    return oclcLhrCode;
  }
  
  public String getOclcNumber() {
    return oclcNumber;
  }

  /**
   * The name of the shelving location as known to OCLC WorldCat Local.
   *
   * This value is used to look up the shelving location in the CDL-managed
   * table in the cases where there is no LHR for the item at OCLC.
   *
   * This value is the same as the display text for the location in WorldCat
   * Local.
   */
  public String getOpacShelvingLocationName() {
    return opacShelvingLocationName;
  }

  /**
   * Specifies whether the item is published as a serial, or as a monograph.
   */
  public Seriality getSeriality() {
    return seriality;
  }

  /**
   * The shelving location where the item is held.
   *
   * This value is obtained from the CDL-managed location table. If the shelving
   * location for the requested item cannot be found in the table, a default
   * value will be provided in this field.
   *
   */
  public ShelvingLocation getShelvingLocation() {
    return shelvingLocation;
  }

  /**
   * The summary holdings statement for a serial title.
   *
   * This value is built from fields in the LHR, and is only available if there
   * is an LHR for the serial.
   */
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
    hash = 61 * hash + Objects.hashCode(this.oclcLhrCode);
    return hash;
  }

  public boolean isLocalUseOnly() {
    return localUseOnly;
  }

  public Boolean isMassDigitizedContent() {
    return massDigitizedContent;
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

  public void setCopyNumber(String copyNumber) {
    this.copyNumber = copyNumber;
  }

  public void setLocalUseOnly(boolean localUseOnly) {
    this.localUseOnly = localUseOnly;
  }

  public void setMassDigitizedContent(Boolean massDigitizedContent) {
    this.massDigitizedContent = massDigitizedContent;
  }

  public void setOclcLhrCode(String oclcLhrCode) {
    this.oclcLhrCode = oclcLhrCode;
  }

  public void setOclcNumber(String oclcNumber) {
    this.oclcNumber = oclcNumber;
  }

  public void setOpacShelvingLocationName(String shelf) {
    this.opacShelvingLocationName = shelf;
  }

  public void setSeriality(Seriality seriality) {
    this.seriality = seriality;
  }

  public void setShelvingLocation(ShelvingLocation shelvingLocation) {
    this.shelvingLocation = shelvingLocation;
  }

  public void setSummaryHoldings(String summaryHoldings) {
    this.summaryHoldings = summaryHoldings;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    append(sb, "oclcNumber", oclcNumber);
    append(sb, "carrier", carrier);
    append(sb, "circStatus", circStatus);
    append(sb, "seriality", seriality);
    append(sb, "shelvingLocation", shelvingLocation);
    append(sb, "opacShelvingLocationName", opacShelvingLocationName);
    append(sb, "oclcLhrCode", oclcLhrCode);
    append(sb, "callNumber", callNumber);
    append(sb, "summaryHoldings", summaryHoldings);
    append(sb, "massDigitizedContent", massDigitizedContent);

    return "Item{" + sb.toString() + '}';

  }

  private StringBuilder append(StringBuilder sb, String label, Object var) {
    if (var == null) {
      return sb;
    }
    String varString = var.toString();
    if (varString.isEmpty()) {
      return sb;
    }
    if (sb.length() != 0) {
      sb.append(", ");
    }
    sb.append(label).append("=").append(varString);
    return sb;
  }
}
