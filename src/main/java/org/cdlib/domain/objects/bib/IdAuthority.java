package org.cdlib.domain.objects.bib;

/*
 * Controlled vocabulary of bibliographic
 * identifier authorities.
 */
public enum IdAuthority {
  
  ISBN("isbn-international.org"),
  ISSN("issn.org"),
  LOC("loc.gov"),
  NLM("nlm.nih.gov"),
  OCLC("oclc.org");
  
  private String uri;
  
  IdAuthority(String uri) {
    this.uri = uri;
  }
  
  public String getUri() {
    return uri;
  }
 
}
