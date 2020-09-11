package org.cdlib.http;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.cdlib.domain.objects.bib.BibPart;
import org.cdlib.domain.objects.bib.PublicationEvent;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.bib.Seriality;
import org.cdlib.domain.objects.bib.Title;
import org.cdlib.domain.objects.identifier.BibIdentifiers;
import org.cdlib.domain.objects.identifier.DOI;
import org.cdlib.domain.objects.identifier.EISBN;
import org.cdlib.domain.objects.identifier.EISSN;
import org.cdlib.domain.objects.identifier.ISBN;
import org.cdlib.domain.objects.identifier.ISSN;
import org.cdlib.domain.objects.identifier.Identifier;
import org.cdlib.domain.objects.identifier.LCCN;
import org.cdlib.domain.objects.identifier.OclcNumber;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class OpenUrlTest {
  
  private String EXPECTED_ARTICLE = "url_ver=Z39.88-2004&rft.atitle=Article+Title&rft.volume=2&rft.issue=1&rft.year=2019&rft.month=May&rft.ssn=Spring&rft.date=May+2019&rft.pages=12-24&rft_id=info%3Adoi%2F34567&rft.doi=34567&rft.eissn=1111111&rft.jtitle=Journal+of+Journals&rft_id=urn%3AISSN%3A12345&rft.issn=12345&rft.eissn=1111111";
  private String EXPECTED_MONOGRAPH = "url_ver=Z39.88-2004&rft.btitle=Monograph+Title&rft_id=urn%3AISBN%3A12345&rft.isbn=12345&rft_id=info%3Aoclcnum%2F34567&rft.oclcnum=34567&rft_id=info%3Alccn%2F00001111111&rft.lccn=00001111111&rft.eisbn=1111111111111&rft.au=Jones%2C+John";
  private String EXPECTED_CHAPTER = "url_ver=Z39.88-2004&rft.btitle=Monograph+Title&rft_id=urn%3AISBN%3A12345&rft.isbn=12345&rft_id=info%3Aoclcnum%2F34567&rft.oclcnum=34567&rft_id=info%3Alccn%2F00001111111&rft.lccn=00001111111&rft.eisbn=1111111111111&rft.au=Smith%2C+Harriet";
  private OpenUrlDeriver openUrlDeriver = new OpenUrlDeriver();
 
  private BibPart stubArticle() {
    BibPart article = new BibPart();
    article.setIssue("1");
    article.setVolume("2");
    article.setTitle("Article Title");
    article.setPages("12-24");
    article.setIdentifiers(stubArticleIdentifiers());
    article.setAuthor("Smith, Harriet");
    return article;
  }
  
  private BibIdentifiers stubMonographIdentifiers() {
    BibIdentifiers testIdentifiers = new BibIdentifiers();
    testIdentifiers.setIsbn(new ISBN("12345"));
    testIdentifiers.setOclcNumber(new OclcNumber("34567"));
    testIdentifiers.seteIsbn(new EISBN("1111111111111"));
    testIdentifiers.setLccn(new LCCN("00001111111"));
    return testIdentifiers;
  }
  
  private List<Identifier> stubArticleIdentifiers() {
    List<Identifier> testIdentifiers = new ArrayList<>();
    testIdentifiers.add(new DOI("34567"));
    testIdentifiers.add(new EISSN("1111111"));
    return testIdentifiers;
  }
  
  private BibIdentifiers stubJournalIdentifiers() {
    BibIdentifiers testIdentifiers = new BibIdentifiers();
    testIdentifiers.setIssn(new ISSN("12345"));
    testIdentifiers.seteIssn(new EISSN("1111111"));
    return testIdentifiers;
  }
  
  private PublicationEvent monoPubEvent() {
    PublicationEvent event = new PublicationEvent();
    event.setDate("2019");
    event.setPlace("New York");
    event.setPublisher("Academic Books");
    return event;
  }
  
  private PublicationEvent serialPubEvent() {
    PublicationEvent event = new PublicationEvent();
    event.setDate("May 2019");
    event.setYear("2019");
    event.setSeason("Spring");
    event.setMonth("May");
    event.setPlace("New York");
    event.setPublisher("Academic Books");
    return event;
  }
  
  private Bib stubMonograph() {
    Bib bib = new Bib();
    bib.setTitle(new Title("Monograph Title"));
    bib.setAuthor("Jones, John");
    bib.setIdentifiers(stubMonographIdentifiers());
    bib.setSeriality(Seriality.MONOGRAPH);
    bib.setPublicationEvent(monoPubEvent());
    return bib;
  }
  
  private Bib stubSerial() {
    Bib bib = new Bib();
    bib.setSeriality(Seriality.SERIAL);
    bib.setTitle(new Title("Journal of Journals"));
    bib.setSeriality(Seriality.SERIAL);
    bib.setIdentifiers(stubJournalIdentifiers());
    return bib;
  }
  
  @Test
  public void testMonograph() {
    Bib testBib = stubMonograph();
    assertEquals(EXPECTED_MONOGRAPH, openUrlDeriver.encodedQueryFrom(testBib));
  }
  
  @Test
  public void testJournalArticle() {
     BibPart stubArticle = stubArticle();
     stubArticle.setContainer(stubSerial());
     stubArticle.setPublicationEvent(serialPubEvent());
     assertEquals(EXPECTED_ARTICLE, openUrlDeriver.encodedQueryFrom(stubArticle));
  }
  
  @Test (expected = IllegalStateException.class)
  public void articleWithNoTitle_throwException() {
    BibPart stubArticle = stubArticle();
    stubArticle.setTitle("");
    stubArticle.setContainer(stubSerial());
    stubArticle.setPublicationEvent(serialPubEvent());
    assertEquals(EXPECTED_ARTICLE, openUrlDeriver.encodedQueryFrom(stubArticle));
  }
  
  @Test
  public void testBookChapter() {
     BibPart stubArticle = stubArticle();
     stubArticle.setContainer(stubMonograph());
     assertEquals(EXPECTED_CHAPTER, openUrlDeriver.encodedQueryFrom(stubArticle));
  }
  

}
