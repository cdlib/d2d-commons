package org.cdlib.domain.objects.consortium;

import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.constraints.VDXCode;

/**
 * 
 * An office that participates in interlibrary borrowing.
 * 
 * The office can be the location where the patron will pick up a physically loaned item, 
 * or it can be the location where the loan is processed at the borrowing institution.
 *
 */
public class OfficeLocation {

    @Email(message = "Office location email must be a valid email.")
    private String email;
    @NotNull(message = "Office location VDX code is required.")
    @VDXCode(message = "Office location VDX code must be a valid value.")
    private String vdxCode;

    public OfficeLocation() {
    }

    public OfficeLocation(OfficeLocation source) {
        this.email = source.email;
        this.vdxCode = source.vdxCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVdxCode() {
        return vdxCode;
    }

    public void setVdxCode(String vdxCode) {
        this.vdxCode = vdxCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.vdxCode);
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
        final OfficeLocation other = (OfficeLocation) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.vdxCode, other.vdxCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OfficeLocation{" + "email=" + email + ", vdxCode=" + vdxCode + '}';
    }

}
