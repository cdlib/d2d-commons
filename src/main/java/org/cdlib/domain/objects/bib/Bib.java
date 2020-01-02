package org.cdlib.domain.objects.bib;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.Link;
import org.cdlib.domain.objects.meta.ResourceMeta;
import org.cdlib.util.JSON;

/**
 * Serializable representation of a bibliographic Instance,
 * intended in the sense of Instance in Bibframe, similar to 
 * FRBR Manifestation.
 *
 */
public class Bib {

  private String author;
  private Carrier carrier;
  private String corporateAuthor;
  private String edition;
  private BibIdentifiers identifiers;
  private String language;
  private Link marc;
  private List<Link> otherForms;
  private String placeOfPublication;

  private String publicationDate;
  private String publisher;
  @NotNull(message = "RecordType is required.")
  private RecordType recordType;
  private ResourceMeta resourceMeta;
  @NotNull(message = "Seriality is required.")
  private Seriality seriality;
  @NotNull(message = "Title is required.")
  private Title title;
  
  public Bib() {}
  
  public Bib(Bib source) {
    this.author = source.author;
    this.carrier = source.carrier;
    this.corporateAuthor = source.corporateAuthor;
    this.edition = source.edition;
    this.identifiers = source.identifiers;
    this.language = source.language;
    this.marc = source.marc;
    this.otherForms = source.otherForms;
    this.placeOfPublication = source.placeOfPublication;
    this.publicationDate = source.publicationDate;
    this.publisher = source.publisher;
    this.recordType = source.recordType;
    this.resourceMeta = source.resourceMeta;
    this.seriality = source.seriality;
  }
  
  public String getAuthor() {
    return author;
  }
  public Carrier getCarrier() {
    return carrier;
  }
  public String getCorporateAuthor() {
    return corporateAuthor;
  }
  public String getEdition() {
    return edition;
  }

  public BibIdentifiers getIdentifiers() {
    return identifiers;
  }

  public String getLanguage() {
    return language;
  }

  public Link getMarc() {
    return marc;
  }

  public List<Link> getOtherForms() {
    return otherForms;
  }

  public String getPlaceOfPublication() {
    return placeOfPublication;
  }

  public String getPublicationDate() {
    return publicationDate;
  }

  public String getPublisher() {
    return publisher;
  }

  public RecordType getRecordType() {
    return recordType;
  }

  public ResourceMeta getResourceMeta() {
    return resourceMeta;
  }

  public Seriality getSeriality() {
    return seriality;
  }

  public Title getTitle() {
    return title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setCarrier(Carrier carrier) {
    this.carrier = carrier;
  }

  public void setCorporateAuthor(String corporateAuthor) {
    this.corporateAuthor = corporateAuthor;
  }

  public void setEdition(String edition) {
    this.edition = edition;
  }

  public void setIdentifiers(BibIdentifiers identifiers) {
    this.identifiers = identifiers;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setMarc(Link marc) {
    this.marc = marc;
  }

  public void setOtherForms(List<Link> relatedResources) {
    this.otherForms = relatedResources;
  }

  public void setPlaceOfPublication(String placeOfPublication) {
    this.placeOfPublication = placeOfPublication;
  }

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setRecordType(RecordType recordType) {
    this.recordType = recordType;
  }

  public void setResorceMeta(ResourceMeta resourceMeta) {
    this.resourceMeta = resourceMeta;
  }

  public void setResourceMeta(ResourceMeta resourceMeta) {
    this.resourceMeta = resourceMeta;
  }

  public void setSeriality(Seriality seriality) {
    this.seriality = seriality;
  }

  public void setTitle(Title title) {
    this.title = title;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((author == null) ? 0 : author.hashCode());
    result = prime * result + ((carrier == null) ? 0 : carrier.hashCode());
    result = prime * result + ((corporateAuthor == null) ? 0 : corporateAuthor.hashCode());
    result = prime * result + ((edition == null) ? 0 : edition.hashCode());
    result = prime * result + ((identifiers == null) ? 0 : identifiers.hashCode());
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    result = prime * result + ((marc == null) ? 0 : marc.hashCode());
    result = prime * result + ((otherForms == null) ? 0 : otherForms.hashCode());
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
    if (corporateAuthor == null) {
      if (other.corporateAuthor != null)
        return false;
    } else if (!corporateAuthor.equals(other.corporateAuthor))
      return false;
    if (edition == null) {
      if (other.edition != null)
        return false;
    } else if (!edition.equals(other.edition))
      return false;
    if (identifiers == null) {
      if (other.identifiers != null)
        return false;
    } else if (!identifiers.equals(other.identifiers))
      return false;
    if (language == null) {
      if (other.language != null)
        return false;
    } else if (!language.equals(other.language))
      return false;
    if (marc == null) {
      if (other.marc != null)
        return false;
    } else if (!marc.equals(other.marc))
      return false;
    if (otherForms == null) {
      if (other.otherForms != null)
        return false;
    } else if (!otherForms.equals(other.otherForms))
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

}
