package org.cdlib.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.util.Assert;

public final class JSON {

  private JSON() {
  }

  private static ObjectMapper objectMapper = initMapper();

  private static ObjectMapper initMapper() {
    ObjectMapper jsonMapper = new ObjectMapper();
    jsonMapper = jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    jsonMapper = jsonMapper.registerModule(new JavaTimeModule());
    return jsonMapper;
  }

  public static String serialize(Object pojo) {
    if (pojo == null) {
      throw new JSONConversionException("cannot serialize null");
    }
    String json = null;
    if (!objectMapper.canSerialize(pojo.getClass())) {
      throw new JSONConversionException("object cannot be serialized");
    }
    try {
      json = objectMapper.writeValueAsString(pojo);
    } catch (IOException e) {
      throw new JSONConversionException("Could not serialize JSON", e);
    }
    return json;
  }

  private static void checkDeserialize(Class clazz) {
    if (!objectMapper.canDeserialize(objectMapper.constructType(clazz))) {
      throw new JSONConversionException("json cannot be deserialized to object type");
    }
  }

  public static <T> T deserialize(InputStream json, Class<T> clazz) {
    Assert.notNull(clazz);
    Assert.notNull(json);
    checkDeserialize(clazz);
    T result = null;
    try {
      result = objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new JSONConversionException("Could not deserialize JSON", e);
    }
    return result;
  }

  public static <T> T deserialize(String json, Class<T> clazz) {
    Assert.notNull(clazz);
    Assert.notNull(json);
    checkDeserialize(clazz);
    T result = null;
    try {
      result = objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new JSONConversionException("Could not deserialize JSON", e);
    } catch (RuntimeException e) {
      throw new JSONConversionException("Unexpected runtime exception caught when attempting to deserialize " + json);
    }
    return result;
  }

  public static class JSONConversionException extends RuntimeException {

    public JSONConversionException(String message) {
      super(message);
    }

    public JSONConversionException(Exception e) {
      super(e);
    }

    public JSONConversionException(String message, Exception e) {
      super(message, e);
    }
  }
}
