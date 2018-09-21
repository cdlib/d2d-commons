package org.cdlib.domain.objects.fulfillment;

import java.time.LocalDate;
import java.util.Objects;

public class PatronComments {

    private LocalDate needByDate;
    private String note;

    public PatronComments() {
    }

    public PatronComments(PatronComments source) {
        this.needByDate = source.needByDate;
        this.note = source.note;
    }

    public LocalDate getNeedByDate() {
        return needByDate;
    }

    public String getNote() {
        return note;
    }

    public void setNeedByDate(LocalDate needByDate) {
        this.needByDate = needByDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.needByDate);
        hash = 23 * hash + Objects.hashCode(this.note);
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
        final PatronComments other = (PatronComments) obj;
        if (!Objects.equals(this.note, other.note)) {
            return false;
        }
        if (!Objects.equals(this.needByDate, other.needByDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PatronComments{" + "needByDate=" + needByDate + ", note=" + note + '}';
    }

}
