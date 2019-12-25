package org.cdlib.domain.objects.bib;

import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.cdlib.domain.objects.Link;
import org.cdlib.domain.objects.Summary;
import org.cdlib.domain.objects.meta.ResourceMeta;
import org.cdlib.util.JSON;

/**
 * Serializable representation of a bibliographic item.
 *
 */
public class Bib {

  private String author;
  private Carrier carrier;
  private String corporateAuthor;
  private String edition;
  private Identifiers<? extends StandardNumber> identifiers;
  private String language;
  private String placeOfPublication;
  private String publicationDate;
  private String publisher;
  @NotNull(message = "RecordType is required.")
  private RecordType recordType;
  private List<Link<? extends Summary, ?>> relatedResources;
  private ResourceMeta resourceMeta;
  @NotNull(message = "Seriality is required.")
  private Seriality seriality;
  @NotNull(message = "Title is required.")
  private Title title;

  public Bib() {}

  public Bib(Bib source) {
    this.author = source.author;
    this.title = source.title;
    this.publisher = source.publisher;
    this.placeOfPublication = source.placeOfPublication;
    this.publicationDate = source.publicationDate;
    this.edition = source.edition;
    this.language = source.language;
    this.recordType = source.recordType;
    this.seriality = source.seriality;
    this.carrier = source.carrier;
    this.identifiers = source.identifiers;
    this.relatedResources = Collections.unmodifiableList(source.relatedResources);
    this.resourceMeta = source.resourceMeta;
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

  public String getLanguage() {
    return language;
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

  public void setLanguage(String language) {
    this.language = language;
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
