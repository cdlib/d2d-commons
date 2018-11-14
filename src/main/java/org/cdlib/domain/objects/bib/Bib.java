package org.cdlib.domain.objects.bib;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Bibliographic data about an item that is requested.
 *
 * This information is directly related to data available in a MARC Bib record
 * for the item requested.
 */
public class Bib {

    private OclcNumber oclcNumber;
    private String authorName;
    @NotNull(message = "Title is required.")
    private String title;
    private String publisher;
    private String placeOfPublication;
    private String publicationDate;
    private ISSN issn;
    private ISBN isbn;
    private String edition;
    private String corporateAuthor;
    private String dissertationNumber;
    private String ericIdentifier;
    @NotNull(message = "RecordType is required.")
    private RecordType recordType;
    @NotNull(message = "Seriality is required.")
    private Seriality seriality;
    private MultipartResourceRecordLevel multipartLevel;

    public Bib() {
    }

    public Bib(Bib source) {
        this.authorName = source.authorName;
        this.oclcNumber = source.oclcNumber;
        this.title = source.title;
        this.publisher = source.publisher;
        this.placeOfPublication = source.placeOfPublication;
        this.publicationDate = source.publicationDate;
        this.issn = source.issn;
        this.isbn = source.isbn;
        this.edition = source.edition;
        this.corporateAuthor = source.corporateAuthor;
        this.dissertationNumber = source.dissertationNumber;
        this.ericIdentifier = source.ericIdentifier;
        this.recordType = source.recordType;
        this.seriality = source.seriality;
        this.multipartLevel = source.multipartLevel;
    }

    public Bib(OclcNumber oclcNumber) {
        this.oclcNumber = oclcNumber;
    }

    public OclcNumber getOclcNumber() {
        return oclcNumber;
    }

    public void setOclcNumber(OclcNumber oclcNumber) {
        this.oclcNumber = oclcNumber;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPlaceOfPublication() {
        return placeOfPublication;
    }

    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public ISSN getIssn() {
        return issn;
    }

    public void setIssn(ISSN issn) {
        this.issn = issn;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    public void setIsbn(ISBN isbn) {
        this.isbn = isbn;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getCorporateAuthor() {
        return corporateAuthor;
    }

    public void setCorporateAuthor(String corporateAuthor) {
        this.corporateAuthor = corporateAuthor;
    }

    public String getDissertationNumber() {
        return dissertationNumber;
    }

    public void setDissertationNumber(String dissertationNumber) {
        this.dissertationNumber = dissertationNumber;
    }

    public String getEricIdentifier() {
        return ericIdentifier;
    }

    public void setEricIdentifier(String ericIdentifier) {
        this.ericIdentifier = ericIdentifier;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public Seriality getSeriality() {
        return seriality;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public void setSeriality(Seriality seriality) {
        this.seriality = seriality;
    }

    public MultipartResourceRecordLevel getMultipartLevel() {
        return multipartLevel;
    }

    public void setMultipartLevel(MultipartResourceRecordLevel multipartLevel) {
        this.multipartLevel = multipartLevel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.oclcNumber);
        hash = 61 * hash + Objects.hashCode(this.authorName);
        hash = 61 * hash + Objects.hashCode(this.title);
        hash = 61 * hash + Objects.hashCode(this.publisher);
        hash = 61 * hash + Objects.hashCode(this.placeOfPublication);
        hash = 61 * hash + Objects.hashCode(this.publicationDate);
        hash = 61 * hash + Objects.hashCode(this.issn);
        hash = 61 * hash + Objects.hashCode(this.isbn);
        hash = 61 * hash + Objects.hashCode(this.edition);
        hash = 61 * hash + Objects.hashCode(this.corporateAuthor);
        hash = 61 * hash + Objects.hashCode(this.dissertationNumber);
        hash = 61 * hash + Objects.hashCode(this.ericIdentifier);
        hash = 61 * hash + Objects.hashCode(this.recordType);
        hash = 61 * hash + Objects.hashCode(this.seriality);
        hash = 61 * hash + Objects.hashCode(this.multipartLevel);
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
        final Bib other = (Bib) obj;
        if (!Objects.equals(this.oclcNumber, other.oclcNumber)) {
            return false;
        }
        if (!Objects.equals(this.authorName, other.authorName)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.publisher, other.publisher)) {
            return false;
        }
        if (!Objects.equals(this.placeOfPublication, other.placeOfPublication)) {
            return false;
        }
        if (!Objects.equals(this.publicationDate, other.publicationDate)) {
            return false;
        }
        if (!Objects.equals(this.issn, other.issn)) {
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        if (!Objects.equals(this.edition, other.edition)) {
            return false;
        }
        if (!Objects.equals(this.corporateAuthor, other.corporateAuthor)) {
            return false;
        }
        if (!Objects.equals(this.dissertationNumber, other.dissertationNumber)) {
            return false;
        }
        if (!Objects.equals(this.ericIdentifier, other.ericIdentifier)) {
            return false;
        }
        if (this.recordType != other.recordType) {
            return false;
        }
        if (this.seriality != other.seriality) {
            return false;
        }
        if (this.multipartLevel != other.multipartLevel) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        append(sb, "oclcNumber", oclcNumber);
        append(sb, "authorName", authorName);
        append(sb, "title", title);
        append(sb, "publisher", publisher);
        append(sb, "placeOfPublication", placeOfPublication);
        append(sb, "publicationDate", publicationDate);
        append(sb, "issn", issn);
        append(sb, "isbn", isbn);
        append(sb, "edition", edition);
        append(sb, "corporateAuthor", corporateAuthor);
        append(sb, "dissertationNumber", dissertationNumber);
        append(sb, "ericIdentifier", ericIdentifier);
        append(sb, "recordType", recordType);
        append(sb, "seriality", seriality);
        append(sb, "multipartLevel", getMultipartLevel());

        return "Bib{" + sb.toString() + '}';

    }

    private StringBuilder append(StringBuilder sb, String label, Object var) {
        if (var == null) {
            return sb;
        }
        String varString = var.toString();
        if (varString.isEmpty()) {
            return sb;
        }
        if (sb.length() != 0) {
            sb.append(", ");
        }
        sb.append(label).append("=").append(varString);
        return sb;
    }
}
