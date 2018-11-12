package org.cdlib.domain.objects.meta;

import java.time.LocalDate;

/**
 *
 * A serializable log statement that can be sent as part of resource metadata.
 */
public class LogStatement {
  
  private String message;
  private LocalDate timeStamp;
  private LogLevel logLevel;
  
  /**
 *
 * Level for logging statements included as resource metadata.
 */
public static enum LogLevel {
  
  WARN,
  ERROR,
  INFO,
  DEBUG;

}

  
  

}
