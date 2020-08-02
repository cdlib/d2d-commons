package org.cdlib.http;

import static org.junit.Assert.assertEquals;
import org.cdlib.domain.objects.article.ArticleCitation;
import org.junit.Test;

public class OpenUrlTest {
  
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
  
  @Test
  public void testArticleOpenUrlQuery() {
    String expected = "url_ver=Z39.88-2004&rft.atitle=Article+Title&rft.volume=2&rft.issue=1&rft.year=1968&rft.month=JAN&rft.ssn=spring&rft.pages=12-24";
    assertEquals(expected, OpenUrl.toEncodedQuery(testArticle()));
  }

}
