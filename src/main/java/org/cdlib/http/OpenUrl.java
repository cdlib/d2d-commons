package org.cdlib.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.cdlib.domain.objects.article.ArticleCitation;
import org.cdlib.domain.objects.identifier.Identifier;

public class OpenUrl {

  private static String URL_VERSION = "url_ver=Z39.88-2004";

  public static String encodedQueryFrom(ArticleCitation article) {
    List<String> keyValuePairs = new ArrayList<>();
    keyValuePairs.add(URL_VERSION);
    keyValuePair(article::getTitle, "rft.atitle").ifPresent((result) -> keyValuePairs.add(result));
    keyValuePair(article::getVolume, "rft.volume").ifPresent((result) -> keyValuePairs.add(result));
    keyValuePair(article::getIssue, "rft.issue").ifPresent((result) -> keyValuePairs.add(result));
    keyValuePair(article::getYearOfPublication, "rft.year").ifPresent((result) -> keyValuePairs.add(result));
    keyValuePair(article::getMonthOfPublication, "rft.month").ifPresent((result) -> keyValuePairs.add(result));
    keyValuePair(article::getSeasonOfPublication, "rft.ssn").ifPresent((result) -> keyValuePairs.add(result));
    keyValuePair(article::getPages, "rft.pages").ifPresent((result) -> keyValuePairs.add(result));
    keyValuePairsFrom(article.getIdentifiers()).ifPresent((result) -> keyValuePairs.addAll(result));
    return String.join("&", keyValuePairs);
  }

  private static Optional<String> keyValuePair(Supplier<String> supplier, String key) {
    String value = supplier.get();
    if (!(value == null || value.trim().isEmpty())) {
      return Optional.of(key + "=" + encodeValue(value));
    } else {
      return Optional.empty();
    }
  }

  /*
   * Use for encoding URL query field value.
   * Do not use for encoding URL segment.
   */
  public static String encodeValue(String value) {
    try {
      return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Unexpected failure of encoding for " + value + " to " + StandardCharsets.UTF_8.toString(), e);
    }
  }

  private static Optional<List<String>> keyValuePairsFrom(List<Identifier> identifiers) {
    return Optional.of(identifiers.stream()
        .filter((id) -> id != null)
        .flatMap((id) -> id.asEncodedOpenUrl().stream())
        .collect(Collectors.toList()));
  }

}
