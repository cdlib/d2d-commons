package org.cdlib.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;

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

  /*
   * Serialize a Java object to a JSON string
   */
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
  
  /*
   * Deserialize a JSON inputstream
   */
  public static <T> T deserialize(InputStream json, Class<T> clazz) {
    checkDeserialize(clazz);
    T result = null;
    try {
      result = objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new JSONConversionException("Could not deserialize JSON", e);
    }
    return result;
  }

  /*
   * Deserialize a JSON string
   */
  public static <T> T deserialize(String json, Class<T> clazz) {
    checkDeserialize(clazz);
    T result = null;
    try {
      result = objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new JSONConversionException("Could not deserialize JSON", e);
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
