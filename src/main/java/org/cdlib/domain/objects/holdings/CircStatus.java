package org.cdlib.domain.objects.holdings;

import javax.validation.constraints.NotNull;

public class CircStatus {

    private String localCirculationStatus;
    @NotNull(message = "Normalized circulation status is required.")
    private CircAvailability localAvailability;
    private CircAvailability iLLAvailability;

    public CircStatus() {
    }

    public CircStatus(CircStatus source) {
        this.localCirculationStatus = source.localCirculationStatus;
        this.localAvailability = source.localAvailability;
    }

    public CircStatus(String localCirculationStatus, CircAvailability normalizedCircStatus) {
        this.localCirculationStatus = localCirculationStatus;
        this.localAvailability = normalizedCircStatus;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      CircStatus other = (CircStatus) obj;
      if (iLLAvailability != other.iLLAvailability)
        return false;
      if (localAvailability != other.localAvailability)
        return false;
      if (localCirculationStatus == null) {
        if (other.localCirculationStatus != null)
          return false;
      } else if (!localCirculationStatus.equals(other.localCirculationStatus))
        return false;
      return true;
    }

    public CircAvailability getiLLAvailability() {
      return iLLAvailability;
    }

    public CircAvailability getLocalAvailability() {
        return localAvailability;
    }

    public String getLocalCirculationStatus() {
        return localCirculationStatus;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((iLLAvailability == null) ? 0 : iLLAvailability.hashCode());
      result = prime * result + ((localAvailability == null) ? 0 : localAvailability.hashCode());
      result = prime * result
          + ((localCirculationStatus == null) ? 0 : localCirculationStatus.hashCode());
      return result;
    }

    public void setiLLAvailability(CircAvailability iLLAvailability) {
      this.iLLAvailability = iLLAvailability;
    }

    public void setLocalAvailability(CircAvailability normalizedCircStatus) {
        this.localAvailability = normalizedCircStatus;
    }

    public void setLocalCirculationStatus(String localCirculationStatus) {
        this.localCirculationStatus = localCirculationStatus;
    }

    @Override
    public String toString() {
      return "CircStatus [localCirculationStatus=" + localCirculationStatus + ", localAvailability="
          + localAvailability + ", iLLAvailability=" + iLLAvailability + "]";
    }

}
