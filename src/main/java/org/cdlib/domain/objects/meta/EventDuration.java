package org.cdlib.domain.objects.meta;

import java.util.Date;

public class EventDuration {
  
  private final Date startTime;
  private final Date endTime;

  public EventDuration(Date startTime, Date endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Date getStartTime() {
    return startTime;
  }

  public Date getEndTime() {
    return endTime;
  }
  
  public long getDurationMillis() {
    return endTime.getTime() - startTime.getTime();
  }
  

}
