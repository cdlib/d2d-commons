package org.cdlib.domain.objects.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.cdlib.util.JSON;

/**
 *
 * Provides metadata about domain resource.
 * 
 * Provides serializable data about resource context, such as errors, diagnostics, and duration.
 */
public class ResourceMeta {

  @NotNull
  private List<ResourceException> exceptions;
  
  @NotNull
  private Map<String, String> properties;

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

  /**
   * 
   * @return an unmodifiable List of any exceptions caught when attempting to obtain or process the
   *         resource.
   */
  public List<ResourceException> getExceptions() {
    return Collections.unmodifiableList(exceptions);
  }

  public void setExceptions(List<ResourceException> exceptions) {
    this.exceptions = exceptions;
  }

  public Map<String, String> getProperties() {
    return Collections.unmodifiableMap(properties);
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((exceptions == null) ? 0 : exceptions.hashCode());
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ResourceMeta other = (ResourceMeta) obj;
    if (exceptions == null) {
      if (other.exceptions != null)
        return false;
    } else if (!exceptions.equals(other.exceptions))
      return false;
    if (properties == null) {
      if (other.properties != null)
        return false;
    } else if (!properties.equals(other.properties))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return JSON.serialize(this);
  }

  
}
