package org.cdlib.test;

/*
 * Used for testing JSON serialization and deserialization.
 */
public class SerializablePojo {
  private String testString;
  private int testInt;
  
  public SerializablePojo() {
    testString = "test String val 好比不上";
    testInt = 22;
  }

  public String getTestString() {
    return testString;
  }

  public int getTestInt() {
    return testInt;
  }

  public void setTestString(String testString) {
    this.testString = testString;
  }

  public void setTestInt(int testInt) {
    this.testInt = testInt;
  }
  
}
