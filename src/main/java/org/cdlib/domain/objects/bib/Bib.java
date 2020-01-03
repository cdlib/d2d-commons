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
  private PublicationEvent publicationEvent;
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
    this.publicationEvent = source.publicationEvent;
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

  public PublicationEvent getPublicationEvent() {
    return publicationEvent;
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

  public void setPublicationEvent(PublicationEvent publicationEvent) {
    this.publicationEvent = publicationEvent;
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
  public String toString() {
    return JSON.serialize(this);
  }

}
