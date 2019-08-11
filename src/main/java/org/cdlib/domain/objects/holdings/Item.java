package org.cdlib.domain.objects.holdings;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.bib.Carrier;
import org.cdlib.domain.objects.bib.Seriality;
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

  private String opacOclcHoldingsSymbol;
  
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
    this.opacOclcHoldingsSymbol = source.opacOclcHoldingsSymbol;
    this.opacShelvingLocationName = source.opacShelvingLocationName;
    this.callNumber = source.callNumber;
    this.copyNumber = source.copyNumber;
    this.summaryHoldings = source.summaryHoldings;
    this.massDigitizedContent = source.massDigitizedContent;
    this.oclcLhrCode = source.oclcLhrCode;
    this.localUseOnly = source.localUseOnly;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Item other = (Item) obj;
    if (callNumber == null) {
      if (other.callNumber != null)
        return false;
    } else if (!callNumber.equals(other.callNumber))
      return false;
    if (carrier != other.carrier)
      return false;
    if (circStatus == null) {
      if (other.circStatus != null)
        return false;
    } else if (!circStatus.equals(other.circStatus))
      return false;
    if (copyNumber == null) {
      if (other.copyNumber != null)
        return false;
    } else if (!copyNumber.equals(other.copyNumber))
      return false;
    if (localUseOnly != other.localUseOnly)
      return false;
    if (massDigitizedContent == null) {
      if (other.massDigitizedContent != null)
        return false;
    } else if (!massDigitizedContent.equals(other.massDigitizedContent))
      return false;
    if (oclcLhrCode == null) {
      if (other.oclcLhrCode != null)
        return false;
    } else if (!oclcLhrCode.equals(other.oclcLhrCode))
      return false;
    if (oclcNumber == null) {
      if (other.oclcNumber != null)
        return false;
    } else if (!oclcNumber.equals(other.oclcNumber))
      return false;
    if (opacOclcHoldingsSymbol == null) {
      if (other.opacOclcHoldingsSymbol != null)
        return false;
    } else if (!opacOclcHoldingsSymbol.equals(other.opacOclcHoldingsSymbol))
      return false;
    if (opacShelvingLocationName == null) {
      if (other.opacShelvingLocationName != null)
        return false;
    } else if (!opacShelvingLocationName.equals(other.opacShelvingLocationName))
      return false;
    if (seriality != other.seriality)
      return false;
    if (shelvingLocation == null) {
      if (other.shelvingLocation != null)
        return false;
    } else if (!shelvingLocation.equals(other.shelvingLocation))
      return false;
    if (summaryHoldings == null) {
      if (other.summaryHoldings != null)
        return false;
    } else if (!summaryHoldings.equals(other.summaryHoldings))
      return false;
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

  public String getOpacOclcHoldingsSymbol() {
    return opacOclcHoldingsSymbol;
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
    final int prime = 31;
    int result = 1;
    result = prime * result + ((callNumber == null) ? 0 : callNumber.hashCode());
    result = prime * result + ((carrier == null) ? 0 : carrier.hashCode());
    result = prime * result + ((circStatus == null) ? 0 : circStatus.hashCode());
    result = prime * result + ((copyNumber == null) ? 0 : copyNumber.hashCode());
    result = prime * result + (localUseOnly ? 1231 : 1237);
    result =
        prime * result + ((massDigitizedContent == null) ? 0 : massDigitizedContent.hashCode());
    result = prime * result + ((oclcLhrCode == null) ? 0 : oclcLhrCode.hashCode());
    result = prime * result + ((oclcNumber == null) ? 0 : oclcNumber.hashCode());
    result =
        prime * result + ((opacOclcHoldingsSymbol == null) ? 0 : opacOclcHoldingsSymbol.hashCode());
    result = prime * result
        + ((opacShelvingLocationName == null) ? 0 : opacShelvingLocationName.hashCode());
    result = prime * result + ((seriality == null) ? 0 : seriality.hashCode());
    result = prime * result + ((shelvingLocation == null) ? 0 : shelvingLocation.hashCode());
    result = prime * result + ((summaryHoldings == null) ? 0 : summaryHoldings.hashCode());
    return result;
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

  public void setOpacOclcHoldingsSymbol(String opacOclcHoldingsSymbol) {
    this.opacOclcHoldingsSymbol = opacOclcHoldingsSymbol;
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
    return "Item [callNumber=" + callNumber + ", carrier=" + carrier + ", circStatus=" + circStatus
        + ", copyNumber=" + copyNumber + ", localUseOnly=" + localUseOnly
        + ", massDigitizedContent=" + massDigitizedContent + ", oclcLhrCode=" + oclcLhrCode
        + ", oclcNumber=" + oclcNumber + ", opacOclcHoldingsSymbol=" + opacOclcHoldingsSymbol
        + ", opacShelvingLocationName=" + opacShelvingLocationName + ", seriality=" + seriality
        + ", shelvingLocation=" + shelvingLocation + ", summaryHoldings=" + summaryHoldings + "]";
  }
  
  


}
