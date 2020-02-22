package org.cdlib.domain.objects.bib;

public enum Carrier {
  
  ONLINE,
  PRINT,
  MICROFORM,
  BRAILLE,
  ELECTRONIC_NOT_ONLINE;
  
  public CarrierClass carrierClass() {
    if (this.equals(Carrier.ONLINE)) {
      return CarrierClass.ONLINE;
    }
    return CarrierClass.PHYSICAL;
  }

}
