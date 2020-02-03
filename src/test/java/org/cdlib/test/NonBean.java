package org.cdlib.test;

/*
 *
 * Used for testing serialization failures.
 * This object cannot be serialized using Jackson.
 */
public class NonBean {
  
  public Object someMethod() {
    return null;
  }
  
  public void someOtherMethod() {
    
  }

}
