/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cdlib.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;


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

  private static ObjectMapper initMapper() {
    ObjectMapper jsonMapper = new ObjectMapper();
    jsonMapper = jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    jsonMapper = jsonMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
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
