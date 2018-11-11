package org.cdlib.domain.objects.meta;

/**
 *
 * Provides metadata about domain resource.
 * 
 * Provides information about resource context, such as errors, diagnostics, and duration.
 */
public class ResourceMeta<S, E, L> {
  
  private S source;
  private E status;
  private L eventLog;

  public S getSource() {
    return source;
  }

  public void setSource(S source) {
    this.source = source;
  }

  public E getStatus() {
    return status;
  }

  public void setStatus(E status) {
    this.status = status;
  }

  public L getEventLog() {
    return eventLog;
  }

  public void setEventLog(L eventLog) {
    this.eventLog = eventLog;
  }

}
