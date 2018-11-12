package org.cdlib.domain.objects.meta;

import java.util.List;
import org.cdlib.domain.objects.consortium.InstitutionCode;

/**
 *
 * ExceptionEvent for inclusion in ResourceMeta.
 */
public class ExceptionEvent {
  
  private String message;
  private Severity severity;
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
  
  public static enum Severity {
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
