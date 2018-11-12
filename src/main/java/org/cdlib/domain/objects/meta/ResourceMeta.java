package org.cdlib.domain.objects.meta;

import java.util.List;

/**
 *
 * Provides metadata about domain resource.
 * 
 * Provides information about resource context, such as errors, diagnostics, and duration.
 */
public class ResourceMeta {
  
 private List<ExceptionEvent> exceptions;
 private List<LogStatement> log;

  public List<ExceptionEvent> getExceptions() {
    return exceptions;
  }

  public void setExceptions(List<ExceptionEvent> exceptions) {
    this.exceptions = exceptions;
  }

  public List<LogStatement> getLog() {
    return log;
  }

  public void setLog(List<LogStatement> log) {
    this.log = log;
  }

}
