package org.cdlib.domain.objects.consortium;

public class ShelvingLocationId {

    private String matchValue;
    private OpacInstitution opac;

    public ShelvingLocationId() {
    }

    public ShelvingLocationId(OpacInstitution opac, String name) {
        this.opac = opac;
        this.matchValue = name;
    }
    
    public ShelvingLocationId(OpacInstitution opac, String oclcHoldingsSymbol, String lhrCode) {
      this.opac = opac;
      this.matchValue = oclcHoldingsSymbol + "." + lhrCode;
  }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ShelvingLocationId other = (ShelvingLocationId) obj;
      if (matchValue == null) {
        if (other.matchValue != null)
          return false;
      } else if (!matchValue.equals(other.matchValue))
        return false;
      if (opac != other.opac)
        return false;
      return true;
    }

    public String getMatchValue() {
        return matchValue;
    }

    public OpacInstitution getOpacCode() {
        return opac;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((matchValue == null) ? 0 : matchValue.hashCode());
      result = prime * result + ((opac == null) ? 0 : opac.hashCode());
      return result;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    public void setOpacCode(OpacInstitution opac) {
        this.opac = opac;
    }

    @Override
    public String toString() {
      return "ShelvingLocationId [opac=" + opac + ", matchValue=" + matchValue + "]";
    }




}
