package org.cdlib.domain.objects.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Provides metadata about domain resource.
 * 
 * Provides serializable data about resource context, such as errors, diagnostics, and duration.
 */
public class ResourceMeta {
  
 private List<ResourceException> exceptions;
 private Map<String,String> properties;
 
 public ResourceMeta() {
   this.exceptions = new ArrayList<>();
   this.properties = new HashMap<>();
 }
 
 public void addException(ResourceException exception) {
   exceptions.add(exception);
 }
 
 public void addProperty(String key, String value) {
   properties.put(key, value);
 }

  public List<ResourceException> getExceptions() {
    return exceptions;
  }

  public void setExceptions(List<ResourceException> exceptions) {
    this.exceptions = exceptions;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public String toString() {
    return "ResourceMeta{" + "exceptions=" + exceptions + ", properties=" + properties + '}';
  }
  
}
