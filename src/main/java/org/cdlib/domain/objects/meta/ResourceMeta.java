package org.cdlib.domain.objects.meta;

import java.util.Map;

/**
 *
 * Provides metadata about domain resource.
 * 
 * Provides information about resource context, such as errors, diagnostics, and duration.
 */
public class ResourceMeta {
  
  private Map<String, ?> metaProperties;
  
  public Map<String, ?> getMetaProperties() {
    return metaProperties;
  }

  public void setMetaProperties(Map<String, ?> properties) {
    metaProperties = properties;
  }

}
