package org.cdlib.domain.objects;

import java.util.Optional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/*
 * A permalink to a related resource.
 */
public class Link<T extends Summary, T2> {
  
  @NotNull
  private Relationship rel;
  
  @NotNull
  @NotEmpty
  private String href;
  
  @NotNull
  @NotEmpty
  private String mimeType;
  
  /*
   * Summary information about the target
   * to support decision about whether the 
   * client needs the target resource.
   * 
   * This value can be null.
   */
  private T summary;
  
  private Class<T2> to;
  
  public Link() {}
  
  public Link(Relationship rel, String uri, String mimeType, T summary, Class<T2> linkTo) {
    this.rel = rel;
    this.href = uri;
    this.mimeType = mimeType;
    this.summary = summary;
    this.to = linkTo;
  }

  public Relationship getRelationship() {
    return rel;
  }
  
  public String getHref() {
    return href;
  }
  
  public T getSummary() {
    return summary;
  }

  public String getMimeType() {
    return mimeType;
  }
  
  public Class<T2> to() {
    return to;
  }

}
