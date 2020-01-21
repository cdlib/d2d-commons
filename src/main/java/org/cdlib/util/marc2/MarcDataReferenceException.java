package org.cdlib.util.marc2;

@SuppressWarnings("serial")
public class MarcDataReferenceException extends Exception {

  public MarcDataReferenceException() {
    super();
  }

  public MarcDataReferenceException(String message) {
    super(message);
  }
  
  public MarcDataReferenceException(String message, Exception cause) {
    super(message, cause);
  }
  
}
