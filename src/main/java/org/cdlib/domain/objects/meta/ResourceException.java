package org.cdlib.domain.objects.meta;

import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * ResourceException for inclusion in ResourceMeta.
 * 
 * Serializable data about any error conditions that occurred while obtaining a resource.
 */
public class ResourceException {
  
  @NotNull
  @NotEmpty
  private String message;
  
  @NotNull
  private Severity severity;
  
  @NotNull
  private Scope scope;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Severity getSeverity() {
    return severity;
  }

  public void setSeverity(Severity severity) {
    this.severity = severity;
  }

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, scope, severity);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof ResourceException)) {
      return false;
    }
    ResourceException other = (ResourceException) obj;
    return Objects.equals(message, other.message) && scope == other.scope && severity == other.severity;
  }

  @Override
  public String toString() {
    return "ResourceException{" + "message=" + message + ", severity=" + severity + ", scope=" + scope + '}';
  }
  
  public static enum Severity {
    INFO,
    WARN,
    ERROR
  }
  
  public static enum Scope {
    /**
    * 
    * All resource data was affected by exception.
    * 
    * All data is missing, or all data should be regarded as incorrect.
    * Generally this indicates a failure to obtain data from all data sources.
    */
    TOTAL,
    
    /**
     * Resource data is incomplete due to exception.
     * 
     * Data is incomplete, generally due to failure to obtain data from one of 
     * multiple data sources.
     */
    PARTIAL
  }

}
