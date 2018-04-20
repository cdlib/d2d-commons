package org.cdlib.util;

public class XPathFacadeException extends RuntimeException {

  public XPathFacadeException(String msg) {
    super(msg);
  }

  public XPathFacadeException(Exception e) {
    super(e);
  }

  public XPathFacadeException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public XPathFacadeException() {

  }

}
