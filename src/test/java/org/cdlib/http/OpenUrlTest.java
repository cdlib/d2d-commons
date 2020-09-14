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
import org.cdlib.domain.objects.identifier.PMID;
import org.cdlib.domain.objects.meta.ResourceMeta;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class OpenUrlTest {
  
  private static final String EXPECTED_ARTICLE = "url_ver=Z39.88-2004&rfr_id=google&rft.atitle=Article+Title&rft.au=Smith%2C+Harriet&rft.volume=2&rft.issue=1&rft.pages=12-24&rft_id=info%3Adoi%2F34567&rft.doi=34567&rft_id=info%3Apmid%2F1111111&rft.pmid=1111111&rft.jtitle=Journal+of+Journals&rft_id=urn%3AISSN%3A12345&rft.issn=12345&rft.eissn=1111111&rft.year=2019&rft.month=May&rft.ssn=Spring&rft.date=May+2019&rft.place=New+York&rft.publisher=Academic+Books";
  private static final String EXPECTED_MONOGRAPH = "url_ver=Z39.88-2004&rfr_id=google&rft.btitle=Monograph+Title&rft.au=Jones%2C+John&rft.year=2019&rft.date=2019&rft.place=New+York&rft.publisher=Academic+Books&rft_id=urn%3AISBN%3A12345&rft.isbn=12345&rft_id=info%3Aoclcnum%2F34567&rft.oclcnum=34567&rft_id=info%3Alccn%2F00001111111&rft.lccn=00001111111&rft.eisbn=1111111111111";
  private static final String EXPECTED_CHAPTER = "url_ver=Z39.88-2004&rfr_id=google&rft.atitle=Article+Title&rft.au=Smith%2C+Harriet&rft.pages=12-24&rft_id=info%3Adoi%2F34567&rft.doi=34567&rft_id=info%3Apmid%2F1111111&rft.pmid=1111111&rft.btitle=Proceedings+of+the+International+Society+of+International+Societies&rft.year=2019&rft.date=2019&rft.place=New+York&rft.publisher=Academic+Books&rft_id=urn%3AISBN%3A12345&rft.isbn=12345&rft_id=info%3Aoclcnum%2F34567&rft.oclcnum=34567&rft_id=info%3Alccn%2F00001111111&rft.lccn=00001111111&rft.eisbn=1111111111111";
  private static final String EXPECTED_FREESTANDING = "url_ver=Z39.88-2004&rfr_id=google&rft.atitle=Article+Title&rft.au=Smith%2C+Harriet&rft.volume=2&rft.issue=1&rft.pages=12-24&rft_id=info%3Adoi%2F34567&rft.doi=34567&rft_id=info%3Apmid%2F1111111&rft.pmid=1111111&rft.year=2019&rft.month=May&rft.ssn=Spring&rft.date=May+2019&rft.place=New+York&rft.publisher=Academic+Books";
  
  private static final OpenUrlDeriver openUrlDeriver = new OpenUrlDeriver();
  
  // The monograph openurl is derived entirely from the one object,
  // no special rules apply
  @Test
  public void testMonograph() {
    assertEquals(EXPECTED_MONOGRAPH, openUrlDeriver.encodedQueryFrom(stubMonograph()));
  }
  
  private Bib stubMonograph() {
    Bib bib = new Bib();
    bib.setTitle(new Title("Monograph Title"));
    bib.setAuthor("Jones, John");
    bib.setIdentifiers(stubMonographIdentifiers());
    bib.setSeriality(Seriality.MONOGRAPH);
    bib.setPublicationEvent(monoPubEvent());
    bib.setResourceMeta(metaWithSource());
    return bib;
  }
  
  private BibIdentifiers stubMonographIdentifiers() {
    BibIdentifiers testIdentifiers = new BibIdentifiers();
    testIdentifiers.setIsbn(new ISBN("12345"));
    testIdentifiers.setOclcNumber(new OclcNumber("34567"));
    testIdentifiers.seteIsbn(new EISBN("1111111111111"));
    testIdentifiers.setLccn(new LCCN("00001111111"));
    return testIdentifiers;
  }
  
  private PublicationEvent monoPubEvent() {
    PublicationEvent event = new PublicationEvent();
    event.setDate("2019");
    event.setPlace("New York");
    event.setPublisher("Academic Books");
    return event;
  }
  
  private ResourceMeta metaWithSource() {
    ResourceMeta result = new ResourceMeta();
    result.addProperty("source", "google");
    return result;
  }
  
  private PublicationEvent invalidPubEvent() {
    PublicationEvent invalid = monoPubEvent();
    invalid.setYear(null);
    invalid.setDate(null);
    return invalid;
  }
  
  @Test(expected = IllegalStateException.class)
  public void testInvalidPubEvent() {
    Bib invalidBib = stubMonograph();
    invalidBib.setPublicationEvent(invalidPubEvent());
    openUrlDeriver.encodedQueryFrom(invalidBib);
  }
  
  // The journal article should have the publication info of the article
  // and the author of the article
  // and it should include identifiers and titles from both levels
  @Test
  public void testJournalArticle() {
     assertEquals(EXPECTED_ARTICLE, openUrlDeriver.encodedQueryFrom(stubArticle()));
  }
  
  private BibPart stubArticle() {
    BibPart article = new BibPart();
    article.setIssue("1");
    article.setVolume("2");
    article.setTitle("Article Title");
    article.setPages("12-24");
    article.setIdentifiers(stubArticleIdentifiers());
    article.setAuthor("Smith, Harriet");
    article.setPublicationEvent(articlePubEvent());
    article.setContainer(stubSerial());
    article.setResourceMeta(metaWithSource());
    return article;
  }
  
  private List<Identifier> stubArticleIdentifiers() {
    List<Identifier> testIdentifiers = new ArrayList<>();
    testIdentifiers.add(new DOI("34567"));
    testIdentifiers.add(new PMID("1111111"));
    return testIdentifiers;
  }
  
  private PublicationEvent articlePubEvent() {
    PublicationEvent event = new PublicationEvent();
    event.setDate("May 2019");
    event.setYear("2019");
    event.setSeason("Spring");
    event.setMonth("May");
    event.setPlace("New York");
    event.setPublisher("Academic Books");
    return event;
  }
  
  private Bib stubSerial() {
    Bib bib = new Bib();
    bib.setSeriality(Seriality.SERIAL);
    bib.setTitle(new Title("Journal of Journals"));
    bib.setSeriality(Seriality.SERIAL);
    bib.setIdentifiers(stubJournalIdentifiers());
    bib.setPublicationEvent(journalPubEvent());
    bib.setAuthor("Thursten Howell III Publisher");
    return bib;
  }
  
  private BibIdentifiers stubJournalIdentifiers() {
    BibIdentifiers testIdentifiers = new BibIdentifiers();
    testIdentifiers.setIssn(new ISSN("12345"));
    testIdentifiers.seteIssn(new EISSN("1111111"));
    return testIdentifiers;
  }
  
  private PublicationEvent journalPubEvent() {
    PublicationEvent event = new PublicationEvent();
    event.setDate("1905");
    event.setYear("1905");
    event.setPublisher("Ancient Books");
    event.setPlace("London");
    return event;
  }
  
  @Test (expected = IllegalStateException.class)
  public void articleWithNoTitle_throwException() {
    BibPart stubArticle = stubArticle();
    stubArticle.setTitle("");
    stubArticle.setContainer(stubSerial());
    stubArticle.setPublicationEvent(articlePubEvent());
    assertEquals(EXPECTED_ARTICLE, openUrlDeriver.encodedQueryFrom(stubArticle));
  }
  
  // The book chapter should have the author of the part
  // and the publication info for the container
  // it should have identifiers for both the part and the container
  @Test
  public void testBookChapter() {
     assertEquals(EXPECTED_CHAPTER, openUrlDeriver.encodedQueryFrom(stubChapter()));
  }
  
  private BibPart stubChapter() {
    BibPart article = new BibPart();
    article.setTitle("Article Title");
    article.setPages("12-24");
    article.setIdentifiers(stubArticleIdentifiers());
    article.setAuthor("Smith, Harriet");
    article.setPublicationEvent(articlePubEvent());
    article.setResourceMeta(metaWithSource());
    article.setContainer(stubProceedings());
    return article;
  }
  
  private Bib stubProceedings() {
    Bib bib = new Bib();
    bib.setTitle(new Title("Proceedings of the International Society of International Societies"));
    bib.setAuthor("Jones, John, ed.");
    bib.setIdentifiers(stubMonographIdentifiers());
    bib.setSeriality(Seriality.MONOGRAPH);
    bib.setPublicationEvent(monoPubEvent());
    return bib;
  }
  
  @Test(expected = IllegalStateException.class)
  public void testBookChapterInvalidBib() {
     assertEquals(EXPECTED_CHAPTER, openUrlDeriver.encodedQueryFrom(stubChapterBadBib()));
  }
  
  private BibPart stubChapterBadBib() {
    BibPart chapter = stubChapter();
    chapter.getContainer().setSeriality(null);
    return chapter;
  }
  
  // The free-standing article will derive all data from the BibPart
  @Test
  public void testFreestandingArticle() {
     assertEquals(EXPECTED_FREESTANDING, openUrlDeriver.encodedQueryFrom(freeStanding()));
  }
  
  private BibPart freeStanding() {
    BibPart freeStanding = stubArticle();
    freeStanding.setContainer(null);
    return freeStanding;
  }
  

}
