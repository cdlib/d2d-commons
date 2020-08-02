package org.cdlib.http;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.cdlib.domain.objects.article.ArticleCitation;
import org.cdlib.domain.objects.identifier.DOI;
import org.cdlib.domain.objects.identifier.EISBN;
import org.cdlib.domain.objects.identifier.EISSN;
import org.cdlib.domain.objects.identifier.ISBN;
import org.cdlib.domain.objects.identifier.ISSN;
import org.cdlib.domain.objects.identifier.Identifier;
import org.cdlib.domain.objects.identifier.LCCN;
import org.cdlib.domain.objects.identifier.OclcNumber;
import org.cdlib.domain.objects.identifier.PMID;
import org.junit.Test;

public class OpenUrlTest {
  
  private String EXPECTED = "url_ver=Z39.88-2004&rft.atitle=Article+Title&rft.volume=2&rft.issue=1&rft.year=1968&rft.month=JAN&rft.ssn=spring&rft.pages=12-24";
  
  private ArticleCitation testArticle() {
    ArticleCitation article = new ArticleCitation();
    article.setIssue("1");
    article.setVolume("2");
    article.setTitle("Article Title");
    article.setYearOfPublication("1968");
    article.setMonthOfPublication("JAN");
    article.setSeasonOfPublication("spring");
    article.setPages("12-24");
    return article;
  }
  
  private List<Identifier> testIdentifiers() {
    List<Identifier> testIdentifiers = new ArrayList<>();
    testIdentifiers.add(new ISBN("12345"));
    testIdentifiers.add(new OclcNumber("34567"));
    testIdentifiers.add(new EISBN("1111111111111"));
    testIdentifiers.add(new LCCN("00001111111"));
    return testIdentifiers;
  }
  
  private List<Identifier> testSerialIdentifiers() {
    List<Identifier> testIdentifiers = new ArrayList<>();
    testIdentifiers.add(new ISSN("12345"));
    testIdentifiers.add(new DOI("34567"));
    testIdentifiers.add(new EISSN("1111111"));
    testIdentifiers.add(new PMID("2222222"));
    return testIdentifiers;
  }
  
  @Test
  public void testArticleOpenUrlQuery() {
     assertEquals(EXPECTED, OpenUrl.encodedQueryFrom(testArticle()));
  }
  
  @Test
  public void testWithMonographIdentifier() {
    EXPECTED += "&rft_id=urn%3AISBN%3A12345&rft.isbn=12345&rft_id=info%3Aoclcnum%2F34567&rft.oclcnum=34567&rft.eisbn=1111111111111&rft_id=info%3Alccn%2F00001111111&rft.lccn=00001111111";
    ArticleCitation testArticle = testArticle();
    testArticle.setIdentifiers(testIdentifiers());
    assertEquals(EXPECTED, OpenUrl.encodedQueryFrom(testArticle));
       
  }
  
  @Test
  public void testWithSerialIdentifier() {
    EXPECTED += "&rft_id=urn%3AISSN%3A12345&rft.issn=12345&rft_id=info%3Adoi%2F34567&rft.doi=34567&rft.eissn=1111111&rft_id=info%3Apmid%2F2222222&rft.pmid=2222222";
    ArticleCitation testArticle = testArticle();
    testArticle.setIdentifiers(testSerialIdentifiers());
    assertEquals(EXPECTED, OpenUrl.encodedQueryFrom(testArticle));
       
  }

}
