/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cdlib.http;

import org.apache.http.entity.ContentType;

/**
 *
 * @author jferrie
 */
public interface HttpClientFacade {
  
  public String get(String url);

  public String get(String url, int cycles, int timeout);

  public String post(String url, String post, int timeout);
  
  public String post(String url, String post, int timeout, ContentType contentType);
  
}
