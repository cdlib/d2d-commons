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
public interface URLClient {
  
  public String doURLGet(String url);

  /**
   * doURLGet returns the result of the default get URL.
   * @param cycles - the number of time to try
   * @param timeout - the max time to wait in millisecs. No timeout if <= 0
   * @return
   */
  public String doURLGet(int cycles, int timeout);

  /**
   * doURLGet returns the result of the HTTP get URL.
   * @param url - the URL to get
   * @param cycles - the number of time to try
   * @param timeout - the max time to wait in millisecs. No timeout if <= 0
   * @return
   */
  public String doURLGet(String url, int cycles, int timeout);
  
  public String doURLPost(String post);

  /**
   * doURLPost return the result os posting to the default URL
   * @param post
   * @param timeout
   * @returns result of post as a string
   */
  public String doURLPost(String post, int timeout);

  /** doURLPost return the result of post to the supplied URL
   *
   * @param url
   * @param post
   * @param timeout
   * @return
   */
  public String doURLPost(String url, String post, int timeout);

  String getError();

  String getURL();
  
}
