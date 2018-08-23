package org.cdlib.http;

import org.apache.http.entity.ContentType;

public interface HttpClientFacade {
  
  public String get(String url);

  public String post(String url, String post, int timeout);
  
  public String post(String url, String post, int timeout, ContentType contentType);
  
}
