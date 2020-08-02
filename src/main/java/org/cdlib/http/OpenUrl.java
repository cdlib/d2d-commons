package org.cdlib.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.cdlib.domain.objects.article.ArticleCitation;
import org.cdlib.domain.objects.identifier.Identifier;

public class OpenUrl {
  
  private static String URL_VERSION = "url_ver=Z39.88-2004";

  public static String toEncodedQuery(ArticleCitation article) {
    List<String> fieldValuePairs = new ArrayList<>();
    fieldValuePairs.add(URL_VERSION);
    addTitle(article, fieldValuePairs);
    addVolume(article, fieldValuePairs);
    addIssue(article, fieldValuePairs);
    addYear(article, fieldValuePairs);
    addMonth(article, fieldValuePairs);
    addSeason(article, fieldValuePairs);
    addPages(article, fieldValuePairs);
    addIdentifiers(article.getIdentifiers(), fieldValuePairs);
    return String.join("&", fieldValuePairs);
  }

  private static void addYear(ArticleCitation article, List<String> fieldValuePairs) {
    String year = article.getYearOfPublication();
    if (!(year == null || year.trim().isEmpty())) {
      fieldValuePairs.add("rft.year" + "=" + encodeValue(year));
    }
  }
  
  /*
   * Use for encoding URL query field and value;
   * Do not use for encoding URL segment.
   */
  public static String encodeValue(String value) {
    try {
      return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Unexpected unsupported encoding.", e);
    }
  }
  
  private static void addTitle(ArticleCitation article, List<String> fieldValuePairs) {
    String title = article.getTitle();
    if (!(title == null || title.trim().isEmpty())) {
      fieldValuePairs.add("rft.atitle" + "=" + encodeValue(title));
    }
  }
  
  private static void addMonth(ArticleCitation article, List<String> fieldValuePairs) {
    String month = article.getMonthOfPublication();
    if (!(month == null || month.trim().isEmpty())) {
      fieldValuePairs.add("rft.month" + "=" + encodeValue(month));
    }
  }
  
  private static void addSeason(ArticleCitation article, List<String> fieldValuePairs) {
    String season = article.getSeasonOfPublication();
    if (!(season == null || season.trim().isEmpty())) {
      fieldValuePairs.add("rft.ssn" + "=" + encodeValue(season));
    }
  }

  private static void addIssue(ArticleCitation article, List<String> fieldValuePairs) {
    String issue = article.getIssue();
    if (!(issue == null || issue.trim().isEmpty())) {
      fieldValuePairs.add("rft.issue" + "=" + encodeValue(issue));
    }
  }
  
  private static void addVolume(ArticleCitation article, List<String> fieldValuePairs) {
    String volume = article.getVolume();
    if (!(volume == null || volume.trim().isEmpty())) {
      fieldValuePairs.add("rft.volume" + "=" + encodeValue(volume));
    }
  }
  
  private static void addPages(ArticleCitation article, List<String> fieldValuePairs) {
    String pages = article.getPages();
    if (!(pages == null || pages.trim().isEmpty())) {
      fieldValuePairs.add("rft.pages" + "=" + encodeValue(pages));
    }
  }
  
  private static void addIdentifiers(List<Identifier> identifiers, List<String> fieldValuePairs) {
    identifiers.forEach((id) -> fieldValuePairs.addAll(id.asEncodedOpenUrl()));
  }


}
