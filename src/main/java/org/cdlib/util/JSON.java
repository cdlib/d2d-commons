/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cdlib.util;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

/**
 * Utility class for JSON serialization and deserialization.
 *
 * @author jferrie
 */
public final class JSON {

  /*
   * Prevent instantiation.
   */
  private JSON() {
  }

  private static ObjectMapper objectMapper = initMapper();

  // instantiate and configure the mapper
  private static ObjectMapper initMapper() {
    ObjectMapper jsonMapper = new ObjectMapper();
    jsonMapper = jsonMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    jsonMapper = jsonMapper.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    return jsonMapper;
  }

  /*
   * Serialize a Java object to a JSON string
   */
  public static String serialize(Object pojo) {
    String json = null;
    if (!objectMapper.canSerialize(pojo.getClass())) {
      throw new RuntimeException("object cannot be serialized");
    }
    try {
      json = objectMapper.writeValueAsString(pojo);
    } catch (IOException e) {
      throw new JSONConversionException("Could not serialize JSON", e);
    }
    return json;
  }

  private static void checkDeserialize(Class clazz) {
    JavaType javaType = TypeFactory.fromCanonical(clazz.getCanonicalName());
    if (!objectMapper.canDeserialize(javaType)) {
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
