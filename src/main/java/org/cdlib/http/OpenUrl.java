package org.cdlib.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.cdlib.domain.objects.article.BibPart;
import org.cdlib.domain.objects.author.Author;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.bib.Seriality;
import org.cdlib.domain.objects.identifier.Identifier;

/*
 * Provides methods for serializing bibliographic data to an OpenURL 1.0 query.
 */
public class OpenUrl {

  private static final Logger logger = Logger.getLogger(OpenUrl.class);
  private static String VERSION = "url_ver=Z39.88-2004";

  public static String encodedQueryFrom(BibPart article) {
    List<String> keyValuePairs = new ArrayList<>();
    keyValuePairs.add(VERSION);
    keyValuePair(article::getTitle, "rft.atitle").ifPresent(keyValuePairs::add);
    keyValuePair(article::getVolume, "rft.volume").ifPresent(keyValuePairs::add);
    keyValuePair(article::getIssue, "rft.issue").ifPresent(keyValuePairs::add);
    keyValuePair(article::getYearOfPublication, "rft.year").ifPresent(keyValuePairs::add);
    keyValuePair(article::getMonthOfPublication, "rft.month").ifPresent(keyValuePairs::add);
    keyValuePair(article::getSeasonOfPublication, "rft.ssn").ifPresent(keyValuePairs::add);
    keyValuePair(article::getPages, "rft.pages").ifPresent(keyValuePairs::add);
    keyValuePairsFrom(article.getIdentifiers()).ifPresent(keyValuePairs::addAll);
    keyValuePairsFrom(article.getContainer()).ifPresent(keyValuePairs::addAll);
    return String.join("&", keyValuePairs);
  }

  private static Optional<String> keyValuePair(Supplier<String> supplier, String key) {
    String value = supplier.get();
    if (value == null || value.trim().isEmpty()) {
      return Optional.empty();
    }
    Optional<String> encodedValue = encodeValue(value);
    if (encodedValue.isPresent()) {
      return Optional.of(key + "=" + encodedValue.get());
    }
    return Optional.empty();
  }

  /*
   * Use for encoding URL query field value. Do not use for encoding URL segment.
   */
  public static Optional<String> encodeValue(String value) {
    try {
      return Optional.of(URLEncoder.encode(value, StandardCharsets.UTF_8.toString()));
    } catch (UnsupportedEncodingException e) {
      logger.error("Unexpected encoding exception when encoding " + value, e);
      return Optional.empty();
    }
  }
  
  private static Optional<List<String>> keyValuePairsFrom(Author author) {
    List<String> keyValuePairs = new ArrayList<>();
    return Optional.of(keyValuePairs);
  }
  
  
  private static Optional<List<String>> keyValuePairsFrom(Bib bib) {
    List<String> keyValuePairs = new ArrayList<>();
    if (Seriality.MONOGRAPH.equals(bib.getSeriality())) {
      keyValuePair(() -> bib.getTitle().getMainTitle(), "rft.btitle").ifPresent(keyValuePairs::add);
    } else {
      keyValuePair(() -> bib.getTitle().getMainTitle(), "rft.jtitle").ifPresent(keyValuePairs::add);
    }
    keyValuePairsFrom(bib.getIdentifiers().asList()).ifPresent(keyValuePairs::addAll);
   
    return Optional.of(keyValuePairs);
  }

  private static Optional<List<String>> keyValuePairsFrom(List<Identifier> identifiers) {
    return Optional.of(identifiers.stream()
                                  .flatMap((id) -> id.asEncodedOpenUrl().stream())
                                  .collect(Collectors.toList()));
  }
  
  public static String encodedQueryFrom(Bib bib) {
    List<String> keyValuePairs = new ArrayList<>();
    keyValuePairs.add(VERSION);
    keyValuePairs.addAll(keyValuePairsFrom(bib).get());
    return String.join("&", keyValuePairs);
  }

}
