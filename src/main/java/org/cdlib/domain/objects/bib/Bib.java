package org.cdlib.domain.objects.bib;

import java.net.URL;
import java.util.List;

import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.meta.ResourceMeta;
import org.cdlib.util.JSON;

/**
 * Bibliographic data about an item that is requested.
 *
 * This information is directly related to data available in a MARC Bib record
 * for the item requested.
 */
public class Bib {

    private OclcNumber oclcNumber;
    private String author;
    private String corporateAuthor; 
    
    @NotNull(message = "Title is required.")
    private Title title;
    private String publisher;
    private String placeOfPublication;
    private String publicationDate;
    private ISSN issn;
    private ISBN isbn;
    private String edition;
    private String dissertationNumber;
    private String ericIdentifier;
    private URL marcUrl;
    private String language;
    private String govDocNumber; 
    private String lccn;
    private ResourceMeta resourceMeta;
    private List<CachedBib> additionalForms;
    @NotNull(message = "RecordType is required.")
    private RecordType recordType;
    
    @NotNull(message = "Seriality is required.")
    private Seriality seriality;
    private Carrier carrier;

    public Bib() {
    }

    public Bib(Bib source) {
        this.author = source.author;
        this.oclcNumber = source.oclcNumber;
        this.title = source.title;
        this.publisher = source.publisher;
        this.placeOfPublication = source.placeOfPublication;
        this.publicationDate = source.publicationDate;
        this.issn = source.issn;
        this.isbn = source.isbn;
        this.edition = source.edition;
        this.dissertationNumber = source.dissertationNumber;
        this.ericIdentifier = source.ericIdentifier;
        this.marcUrl = source.marcUrl;
        this.language = source.language;
        this.govDocNumber = source.govDocNumber;
        this.lccn = source.lccn; 
        this.recordType = source.recordType;
        this.seriality = source.seriality;
        this.carrier = source.carrier;
        this.resourceMeta = source.resourceMeta;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public Carrier getCarrier() {
      return carrier;
    }
    
    public void setCarrier(Carrier carrier) {
      this.carrier = carrier;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
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
    
    public URL getMarcUrl() {
        return marcUrl;
    }
    
    public void setMarcUrl(URL marcUrl) {
        this.marcUrl = marcUrl; 
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language; 
    }

    public String getGovDocNumber() {
        return govDocNumber;
    }
    
    public void setGovDocNumber(String govDocNumber) {
        this.govDocNumber = govDocNumber; 
    }
    
    public String getLccn() {
        return lccn;
    }
    
    public void setLccn(String lccn) {
        this.lccn = lccn; 
    }
    
    public RecordType getRecordType() {
        return recordType;
    }
    
    public void setRecordType(RecordType recordType) {
      this.recordType = recordType;
  }
    
    public ResourceMeta getResourceMeta() {
      return resourceMeta;
    }
    
    public void setResorceMeta(ResourceMeta resourceMeta) {
      this.resourceMeta = resourceMeta;
    }

    public Seriality getSeriality() {
        return seriality;
    }

    public void setSeriality(Seriality seriality) {
        this.seriality = seriality;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((carrier == null) ? 0 : carrier.hashCode());
        result = prime * result + ((dissertationNumber == null) ? 0 : dissertationNumber.hashCode());
        result = prime * result + ((edition == null) ? 0 : edition.hashCode());
        result = prime * result + ((ericIdentifier == null) ? 0 : ericIdentifier.hashCode());
        result = prime * result + ((govDocNumber == null) ? 0 : govDocNumber.hashCode());
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        result = prime * result + ((issn == null) ? 0 : issn.hashCode());
        result = prime * result + ((language == null) ? 0 : language.hashCode());
        result = prime * result + ((lccn == null) ? 0 : lccn.hashCode());
        result = prime * result + ((marcUrl == null) ? 0 : marcUrl.hashCode());
        result = prime * result + ((oclcNumber == null) ? 0 : oclcNumber.hashCode());
        result = prime * result + ((placeOfPublication == null) ? 0 : placeOfPublication.hashCode());
        result = prime * result + ((publicationDate == null) ? 0 : publicationDate.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((recordType == null) ? 0 : recordType.hashCode());
        result = prime * result + ((resourceMeta == null) ? 0 : resourceMeta.hashCode());
        result = prime * result + ((seriality == null) ? 0 : seriality.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bib other = (Bib) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (carrier != other.carrier)
            return false;
        if (dissertationNumber == null) {
            if (other.dissertationNumber != null)
                return false;
        } else if (!dissertationNumber.equals(other.dissertationNumber))
            return false;
        if (edition == null) {
            if (other.edition != null)
                return false;
        } else if (!edition.equals(other.edition))
            return false;
        if (ericIdentifier == null) {
            if (other.ericIdentifier != null)
                return false;
        } else if (!ericIdentifier.equals(other.ericIdentifier))
            return false;
        if (govDocNumber == null) {
            if (other.govDocNumber != null)
                return false;
        } else if (!govDocNumber.equals(other.govDocNumber))
            return false;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        if (issn == null) {
            if (other.issn != null)
                return false;
        } else if (!issn.equals(other.issn))
            return false;
        if (language == null) {
            if (other.language != null)
                return false;
        } else if (!language.equals(other.language))
            return false;
        if (lccn == null) {
            if (other.lccn != null)
                return false;
        } else if (!lccn.equals(other.lccn))
            return false;
        if (marcUrl == null) {
            if (other.marcUrl != null)
                return false;
        } else if (!marcUrl.equals(other.marcUrl))
            return false;
        if (oclcNumber == null) {
            if (other.oclcNumber != null)
                return false;
        } else if (!oclcNumber.equals(other.oclcNumber))
            return false;
        if (placeOfPublication == null) {
            if (other.placeOfPublication != null)
                return false;
        } else if (!placeOfPublication.equals(other.placeOfPublication))
            return false;
        if (publicationDate == null) {
            if (other.publicationDate != null)
                return false;
        } else if (!publicationDate.equals(other.publicationDate))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (recordType != other.recordType)
            return false;
        if (resourceMeta == null) {
            if (other.resourceMeta != null)
                return false;
        } else if (!resourceMeta.equals(other.resourceMeta))
            return false;
        if (seriality != other.seriality)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
      return JSON.serialize(this);
    }

    public String getCorporateAuthor() {
        return corporateAuthor;
    }

    public void setCorporateAuthor(String corporateAuthor) {
        this.corporateAuthor = corporateAuthor;
    }

    public List<CachedBib> getAdditionalForms() {
        return additionalForms;
    }

    public void setAdditionalForms(List<CachedBib> additionalForms) {
        this.additionalForms = additionalForms;
    }

}
