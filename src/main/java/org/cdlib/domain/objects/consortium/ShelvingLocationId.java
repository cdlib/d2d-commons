package org.cdlib.domain.objects.consortium;

import java.util.Objects;

public class ShelvingLocationId {

    private OpacInstitution opac;
    private String matchValue;

    public ShelvingLocationId() {
    }

    public ShelvingLocationId(OpacInstitution opac, String name) {
        this.opac = opac;
        this.matchValue = name;
    }

    public OpacInstitution getOpacCode() {
        return opac;
    }

    public void setOpacCode(OpacInstitution opac) {
        this.opac = opac;
    }

    public String getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.opac);
        hash = 17 * hash + Objects.hashCode(this.matchValue);
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
        final ShelvingLocationId other = (ShelvingLocationId) obj;
        if (!Objects.equals(this.matchValue, other.matchValue)) {
            return false;
        }
        if (this.opac != other.opac) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShelvingLocationId{" + "opac=" + opac + ", name=" + matchValue + '}';
    }

}
