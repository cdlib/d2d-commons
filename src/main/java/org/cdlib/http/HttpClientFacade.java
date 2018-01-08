/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cdlib.http;

/**
 *
 * @author jferrie
 */
public interface HttpClientFacade {
  
  public String get(String url);

  /**
   * get returns the result of the HTTP get URL.
   * @param url - the URL to get
   * @param cycles - the number of time to try
   * @param timeout - the max time to wait in millisecs. No timeout if <= 0
   * @return
   */
  public String get(String url, int cycles, int timeout);

  /** post return the result of post to the supplied URL
   *
   * @param url
   * @param post
   * @param timeout
   * @return
   */
  public String post(String url, String post, int timeout);
  
}
