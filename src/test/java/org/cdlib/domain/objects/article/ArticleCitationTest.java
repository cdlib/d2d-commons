package org.cdlib.domain.objects.article;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;
import java.util.List;
import org.cdlib.domain.objects.identifier.DOI;
import org.cdlib.domain.objects.identifier.ISBN;
import org.cdlib.domain.objects.identifier.ISSN;
import org.cdlib.domain.objects.identifier.Identifier;
import org.junit.Before;
import org.junit.Test;

public class ArticleCitationTest {
  
  private List<Identifier> stubIdentifiers;
  
  @Before
  public void init() {
    stubIdentifiers = stubIdentifiers();
  }
  
  private List<Identifier> stubIdentifiers() {
    List<Identifier> result = new ArrayList<>();
    ISBN isbn1 = new ISBN("12345");
    ISSN issn1 = new ISSN("34567");
    DOI doi1 = new DOI("01234");
    result.add(isbn1);
    result.add(issn1);
    result.add(doi1);
    return result;
  }
  
  private ArticleCitation stubArticleCitation() {
    ArticleCitation result = new ArticleCitation();
    result.setAuthor("Jones, John");
    result.setIdentifiers(stubIdentifiers());
    return result;
  }
  
  @Test
  public void testDeepCopyOfIdentifiers() {
    ArticleCitation article = stubArticleCitation();
    assertFalse(stubIdentifiers == article.getIdentifiers());
    assertFalse(article.getIdentifiers().get(0) == stubIdentifiers.get(0));
    assertEquals(ISBN.class, article.getIdentifiers().get(0).getClass());
  }

}
