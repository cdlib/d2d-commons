package org.cdlib.domain.objects.patron;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class ContactInfo {

    @NotNull
    @Size(min = 1, message = "A contact email address is required.")
    private List<@Email(groups = Default.class, message = "Not a well-formed email address.") String> emails;
    private String state;
    private String street;
    private String phoneNumber;
    private String postalCode;
    private String city;

    public ContactInfo() {
    }

    public ContactInfo(ContactInfo source) {
        if (source.emails != null) {
            this.emails = new ArrayList<>(source.emails);
        }
        this.state = source.state;
        this.street = source.street;
        this.phoneNumber = source.phoneNumber;
        this.postalCode = source.postalCode;
        this.city = source.city;
    }

    public List<String> getEmails() {
        return emails;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.emails);
        hash = 67 * hash + Objects.hashCode(this.state);
        hash = 67 * hash + Objects.hashCode(this.street);
        hash = 67 * hash + Objects.hashCode(this.phoneNumber);
        hash = 67 * hash + Objects.hashCode(this.postalCode);
        hash = 67 * hash + Objects.hashCode(this.city);
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
        final ContactInfo other = (ContactInfo) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.postalCode, other.postalCode)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.emails, other.emails)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ContactInfo{" + "emails=" + emails + ", state=" + state + ", street=" + street + ", phoneNumber=" + phoneNumber + ", postalCode=" + postalCode + ", city=" + city + '}';
    }

}
