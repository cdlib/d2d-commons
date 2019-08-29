package org.cdlib.util;

/**
 * 
 * Exception raised when a serialized object unexpectedly 
 * fails to be deserialized.
 * 
 */
public class DeserializationException extends Exception {

  private static final long serialVersionUID = 1L;
  private String serializedForm;

  public DeserializationException() {
    super();
  }
  
  public DeserializationException(Throwable e, String input) {
    super(e);
    serializedForm = input;
  }
  
  public DeserializationException(String msg, Throwable e) {
    super(msg, e);
  }
  
  public DeserializationException(String msg, Throwable e, String input) {
    super(msg, e);
    serializedForm = input;
  }
  
  /**
   * 
   * @return the serialized form of the object that failed to be deserialized.
   */
  public String getSerializedForm() {
    return serializedForm;
  }

}
