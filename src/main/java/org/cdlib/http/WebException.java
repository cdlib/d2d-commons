package org.cdlib.http;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * Runtime error that stores HTTP status code. Wrap all exception that will be
 * handled by the controller class in one of these and throw.
 *
 * @author jferrie
 */
public class WebException extends RuntimeException {

  private final int httpStatus;
  private final String content;
  private final Header[] headers;

  public WebException(String message, HttpResponse httpResponse) {
    super(message);
    String body = "";
    httpStatus = httpResponse.getStatusLine().getStatusCode();
    headers = httpResponse.getAllHeaders();
    try {
      body = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
    } catch (IOException e) {

    }
    content = body;
  }
  
  public WebException(HttpResponse httpResponse) {
    super(httpResponse.getStatusLine().getReasonPhrase());
    String body = "";
    httpStatus = httpResponse.getStatusLine().getStatusCode();
    headers = httpResponse.getAllHeaders();
    try {
      body = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
    } catch (IOException e) {

    }
    content = body;
  }

  public WebException(String message, int status) {
    super(message);
    content = "";
    httpStatus = status;
    headers = null;
  }

  public WebException(Throwable error, int status) {
    super(error);
    content = "";
    httpStatus = status;
    headers = null;
  }

  public WebException(String message, Throwable error, int status) {
    super(message, error);
    content = "";
    httpStatus = status;
    headers = null;
  }

  public int getStatus() {
    return httpStatus;
  }

  public String getContent() {
    return content;
  }
  
  public Header[] getHeaders() {
    return headers;
  }

}
