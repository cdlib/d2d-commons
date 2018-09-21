package org.cdlib.domain.objects.consortium;

import java.util.List;
import java.util.Set;

/**
 * Data about the UC consortium as a whole.
 * 
 * For example, the list of lending institutions that loans to the consortium, 
 * and the list of member borrowing institutions.
 * 
 */
public class UCConsortium {

    private Set<InstitutionCode> lenders;
    private List<ShelvingLocation> shelvingLocations;

    public UCConsortium() {
    }

    public UCConsortium(Set<InstitutionCode> lenders, List<ShelvingLocation> shelvingLocations) {
        this.lenders = lenders;
        this.shelvingLocations = shelvingLocations;
    }

    public Set<InstitutionCode> getLenders() {
        return lenders;
    }

    public void setLenders(Set<InstitutionCode> lenders) {
        this.lenders = lenders;
    }

    public List<ShelvingLocation> getShelvingLocations() {
        return shelvingLocations;
    }

    public void setShelvingLocations(List<ShelvingLocation> shelvingLocations) {
        this.shelvingLocations = shelvingLocations;
    }

}
