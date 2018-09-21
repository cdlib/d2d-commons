package org.cdlib.domain.objects.patron;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.consortium.MemberCode;

/**
 * An individual affiliated with a consortium member institution and authorized
 * to make a fulfillment request for a library resource.
 */
public final class Patron {

    @NotNull(message = "Patron affiliation is required.")
    private MemberCode affiliation;
    @NotNull(message = "Patron barcode is required.")
    private String barcode;
    
    /**
     * This takes values like Undergraduate, Graduate, Faculty.
     */
    @NotNull(message = "Patron category is required.")
    private String category;
    @NotNull(message = "Patron contact info is required.")
    @Valid
    private ContactInfo contactInfo;
    private String alternateId;
    private String subAffiliation;
    private LocalDate expiryDate;
    private Boolean honors;
    
    /**
     * This is the local value of the patron's category.
     */
    private String secondaryCode;
    
    private String password;
    private String department;
    private String firstName;
    @NotNull(message = "Patron last name is required.")
    private String lastName;
    private Boolean canUseLocalDeliveryService;

    public Patron() {
    }

    public Patron(Patron source) {
        this.affiliation = source.affiliation;
        this.barcode = source.barcode;
        this.category = source.category;
        if (source.contactInfo != null) {
            this.contactInfo = new ContactInfo(source.contactInfo);
        }
        this.alternateId = source.alternateId;
        this.subAffiliation = source.subAffiliation;
        this.expiryDate = source.expiryDate;
        this.honors = source.honors;
        this.secondaryCode = source.secondaryCode;
        this.password = source.password;
        this.department = source.department;
        this.firstName = source.firstName;
        this.lastName = source.lastName;
        this.canUseLocalDeliveryService = source.canUseLocalDeliveryService;
    }

    public MemberCode getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(MemberCode affiliation) {
        this.affiliation = affiliation;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAlternateId() {
        return alternateId;
    }

    public void setAlternateId(String alternateId) {
        this.alternateId = alternateId;
    }

    public String getSubAffiliation() {
        return subAffiliation;
    }

    public void setSubAffiliation(String subAffiliation) {
        this.subAffiliation = subAffiliation;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getHonors() {
        return honors;
    }

    public void setHonors(Boolean honors) {
        this.honors = honors;
    }

    public String getSecondaryCode() {
        return secondaryCode;
    }

    public void setSecondaryCode(String secondaryCode) {
        this.secondaryCode = secondaryCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getCanUseLocalDeliveryService() {
        return canUseLocalDeliveryService;
    }

    public void setCanUseLocalDeliveryService(Boolean canUseLocalDeliveryService) {
        this.canUseLocalDeliveryService = canUseLocalDeliveryService;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.affiliation);
        hash = 13 * hash + Objects.hashCode(this.barcode);
        hash = 13 * hash + Objects.hashCode(this.category);
        hash = 13 * hash + Objects.hashCode(this.contactInfo);
        hash = 13 * hash + Objects.hashCode(this.alternateId);
        hash = 13 * hash + Objects.hashCode(this.subAffiliation);
        hash = 13 * hash + Objects.hashCode(this.expiryDate);
        hash = 13 * hash + Objects.hashCode(this.honors);
        hash = 13 * hash + Objects.hashCode(this.secondaryCode);
        hash = 13 * hash + Objects.hashCode(this.password);
        hash = 13 * hash + Objects.hashCode(this.department);
        hash = 13 * hash + Objects.hashCode(this.firstName);
        hash = 13 * hash + Objects.hashCode(this.lastName);
        hash = 13 * hash + Objects.hashCode(this.canUseLocalDeliveryService);
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
        final Patron other = (Patron) obj;
        if (!Objects.equals(this.barcode, other.barcode)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.alternateId, other.alternateId)) {
            return false;
        }
        if (!Objects.equals(this.subAffiliation, other.subAffiliation)) {
            return false;
        }
        if (!Objects.equals(this.secondaryCode, other.secondaryCode)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.department, other.department)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (this.affiliation != other.affiliation) {
            return false;
        }
        if (!Objects.equals(this.contactInfo, other.contactInfo)) {
            return false;
        }
        if (!Objects.equals(this.expiryDate, other.expiryDate)) {
            return false;
        }
        if (!Objects.equals(this.honors, other.honors)) {
            return false;
        }
        if (!Objects.equals(this.canUseLocalDeliveryService, other.canUseLocalDeliveryService)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Patron{" + "affiliation=" + affiliation + ", barcode=" + barcode + ", category=" + category + ", contactInfo=" + contactInfo + ", alternateId=" + alternateId + ", subAffiliation=" + subAffiliation + ", expiryDate=" + expiryDate + ", honors=" + honors + ", secondaryCode=" + secondaryCode + ", password=" + password + ", department=" + department + ", firstName=" + firstName + ", lastName=" + lastName + ", canUseLocalDeliveryService=" + canUseLocalDeliveryService + '}';
    }

}
