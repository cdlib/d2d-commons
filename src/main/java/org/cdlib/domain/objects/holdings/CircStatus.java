package org.cdlib.domain.objects.holdings;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class CircStatus {

    private String localCirculationStatus;
    @NotNull(message = "Normalized circulation status is required.")
    private NormalizedCircStatus normalizedCircStatus;

    public CircStatus() {
    }

    public CircStatus(CircStatus source) {
        this.localCirculationStatus = source.localCirculationStatus;
        this.normalizedCircStatus = source.normalizedCircStatus;
    }

    public CircStatus(String localCirculationStatus, NormalizedCircStatus normalizedCircStatus) {
        this.localCirculationStatus = localCirculationStatus;
        this.normalizedCircStatus = normalizedCircStatus;
    }

    public String getLocalCirculationStatus() {
        return localCirculationStatus;
    }

    public void setLocalCirculationStatus(String localCirculationStatus) {
        this.localCirculationStatus = localCirculationStatus;
    }

    public NormalizedCircStatus getNormalizedCircStatus() {
        return normalizedCircStatus;
    }

    public void setNormalizedCircStatus(NormalizedCircStatus normalizedCircStatus) {
        this.normalizedCircStatus = normalizedCircStatus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.localCirculationStatus);
        hash = 23 * hash + Objects.hashCode(this.normalizedCircStatus);
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
        final CircStatus other = (CircStatus) obj;
        if (!Objects.equals(this.localCirculationStatus, other.localCirculationStatus)) {
            return false;
        }
        if (this.normalizedCircStatus != other.normalizedCircStatus) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CircStatus{" + "localCirculationStatus=" + localCirculationStatus + ", normalizedCircStatus=" + normalizedCircStatus + '}';
    }

}
