package org.cdlib.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cdlib.domain.objects.bib.Bib;
import org.cdlib.domain.objects.bib.BibPart;
import org.cdlib.domain.objects.bib.PublicationEvent;
import org.cdlib.domain.objects.bib.Seriality;
import org.cdlib.domain.objects.identifier.Identifier;
import org.cdlib.domain.objects.meta.ResourceMeta;

/*
 * Provides methods for serializing bibliographic data to an OpenURL 1.0 query.
 */
public class OpenUrlDeriver {

  private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private Validator validator = (Validator) factory.getValidator();
  private static final Logger logger = LoggerFactory.getLogger(OpenUrlDeriver.class);
  private static String VERSION = "url_ver=Z39.88-2004";

  /*
   * Throws an IllegalStateException if the bib is not valid
   */
  public String encodedQueryFrom(Bib bib) {
    checkValid(bib);
    List<String> keyValuePairs = new ArrayList<>();
    keyValuePairs.add(VERSION);
    keyValuePair(sourceSupplier(bib.getResourceMeta()), "rfr_id").ifPresent(keyValuePairs::add);
    if (Seriality.MONOGRAPH.equals(bib.getSeriality())) {
      keyValuePair(() -> bib.getTitle().getMainTitle(), "rft.btitle").ifPresent(keyValuePairs::add);
    } else {
      keyValuePair(() -> bib.getTitle().getMainTitle(), "rft.jtitle").ifPresent(keyValuePairs::add);
    }
    keyValuePair(() -> bib.getAuthor(), "rft.au").ifPresent(keyValuePairs::add);
    keyValuePairsFrom(bib.getPublicationEvent()).ifPresent(keyValuePairs::addAll);
    keyValuePairsFrom(bib.getIdentifiersAsList()).ifPresent(keyValuePairs::addAll);
    return String.join("&", keyValuePairs);
  }
  
  private static Supplier<String> sourceSupplier(ResourceMeta meta) {
    return () -> {
      Object value = meta.getProperties().get("source");
      if (value == null) {
        return null;
      }
      if (value.toString().trim().isEmpty()) {
        return null;
      }
      return value.toString();
    };
  }

  /*
   * Throws an IllegalStateException if the article is not valid
   */
  public String encodedQueryFrom(BibPart article) {
    checkValid(article);
    List<String> keyValuePairs = new ArrayList<>();
    keyValuePairs.add(VERSION);
    keyValuePair(sourceSupplier(article.getResourceMeta()), "rfr_id").ifPresent(keyValuePairs::add);
    keyValuePair(article::getTitle, "rft.atitle").ifPresent(keyValuePairs::add);
    keyValuePair(article::getAuthor, "rft.au").ifPresent(keyValuePairs::add);
    keyValuePair(article::getVolume, "rft.volume").ifPresent(keyValuePairs::add);
    keyValuePair(article::getIssue, "rft.issue").ifPresent(keyValuePairs::add);
    keyValuePair(article::getPages, "rft.pages").ifPresent(keyValuePairs::add);
    keyValuePairsFrom(article.getIdentifiers()).ifPresent(keyValuePairs::addAll);
    
    if (!isFreeStanding(article)) {
      checkValid(article.getContainer());
      keyValuePairsFromContainer(article.getContainer()).ifPresent(keyValuePairs::addAll);
    }
    
    if (isFreeStanding(article) || isSerialPart(article)) {
      keyValuePairsFrom(article.getPublicationEvent()).ifPresent(keyValuePairs::addAll);
    }
    
    return String.join("&", keyValuePairs);
  }

  /*
   * BibPart with no container (an article published outside any journal or monograph)
   */
  private boolean isFreeStanding(BibPart article) {
    return article.getContainer() == null;
  }

  private boolean isSerialPart(BibPart bibPart) {
    return !bibPart.getContainer().getSeriality().equals(Seriality.MONOGRAPH);
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

  private Optional<List<String>> keyValuePairsFromContainer(Bib bib) {
    List<String> keyValuePairs = new ArrayList<>();
    keyValuePair(sourceSupplier(bib.getResourceMeta()), "rfr_id").ifPresent(keyValuePairs::add);
    if (Seriality.MONOGRAPH.equals(bib.getSeriality())) {
      keyValuePair(() -> bib.getTitle().getMainTitle(), "rft.btitle").ifPresent(keyValuePairs::add);
      keyValuePairsFrom(bib.getPublicationEvent()).ifPresent(keyValuePairs::addAll);
    } else {
      keyValuePair(() -> bib.getTitle().getMainTitle(), "rft.jtitle").ifPresent(keyValuePairs::add);
    }
    keyValuePairsFrom(bib.getIdentifiersAsList()).ifPresent(keyValuePairs::addAll);
    return Optional.of(keyValuePairs);
  }

  private static Optional<List<String>> keyValuePairsFrom(List<Identifier> identifiers) {
    return Optional.of(identifiers.stream()
                                  .flatMap((id) -> id.asEncodedOpenUrl().stream())
                                  .collect(Collectors.toList()));
  }

  private Optional<List<String>> keyValuePairsFrom(PublicationEvent pubEvent) {
    List<String> keyValuePairs = new ArrayList<>();
    keyValuePair(pubEvent::getYear, "rft.year").ifPresent(keyValuePairs::add);
    keyValuePair(pubEvent::getMonth, "rft.month").ifPresent(keyValuePairs::add);
    keyValuePair(pubEvent::getSeason, "rft.ssn").ifPresent(keyValuePairs::add);
    keyValuePair(pubEvent::getDate, "rft.date").ifPresent(keyValuePairs::add);
    keyValuePair(pubEvent::getPlace, "rft.place").ifPresent(keyValuePairs::add);
    keyValuePair(pubEvent::getPublisher, "rft.publisher").ifPresent(keyValuePairs::add);
    return Optional.of(keyValuePairs);
  }

  private <T> void checkValid(T validatable) {
    Set<ConstraintViolation<T>> violations = validator.validate(validatable);
    if (!violations.isEmpty()) {
      List<String> messages = new ArrayList<>();
      violations.forEach((violation) -> messages.add(violation.getMessage()));
      throw new IllegalStateException(String.join(", ", messages));
    }
  }

}
