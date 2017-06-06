package org.cdlib.test;

/**
 *
 * @author jferrie
 */
public class SerializablePojo {
  private String testString;
  private int testInt;
  
  public SerializablePojo() {
    testString = "test String val";
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
