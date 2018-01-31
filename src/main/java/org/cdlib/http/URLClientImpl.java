package org.cdlib.http;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class URLClientImpl implements URLClient {

  private static Logger LOGGER = LogManager.getLogger(URLClientImpl.class);
  private static final int DEFAULT_TRIES = 3;
  private static final int DEFAULT_TIMEOUT = 20000;

  private String error = null;
  private String myURL = null;

  public URLClientImpl() {
  }

  public URLClientImpl(String URL) {
    myURL = URL;
  }

  @Override
  public String getError() {
    return error;
  }

  @Override
  public String getURL() {
    return myURL;
  }

  String returnString(byte[] byteArr) {
    String retval = null;
    try {
            //because charset will not raise an exception on an invalid character type
      // you have to check to see if the length of the result String included the entire
      // byte array
      String utfs = new String(byteArr, "utf-8");
      byte[] utfb = utfs.getBytes("utf-8");
      if (byteArr.length > utfb.length) {
        retval = new String(byteArr, "iso-8859-1");
      } else {
        retval = utfs;
      }

    } catch (Exception ex) {
      retval = null;
    }
    return retval;
  }
  
  @Override
  public String doURLPost(String post) {
    return doURLPost(myURL, post, DEFAULT_TIMEOUT);
  }

  /**
   * doURLPost return the result os posting to the default URL
   *
   * @param post
   * @param timeout
   * @returns result of post as a string
   */
  @Override
  public String doURLPost(String post, int timeout) {
    return doURLPost(myURL, post, timeout);
  }

  /**
   * doURLPost return the result of post to the supplied URL
   *
   * @param url
   * @param post
   * @param timeout
   * @return
   */
  @Override
  public String doURLPost(String url, String post, int timeout) {
    //LOGGER.debug("URLClientImpl doURLPost: url=" + url + " timeout=" + timeout);
    String result = "";

    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpPost httpPost = new HttpPost(url);
    httpPost.getParams().setParameter("http.socket.timeout", new Integer(timeout));
    httpPost.getParams().setParameter("http.connection.timeout", new Integer(timeout));

    try {
      HttpEntity requestEntity = new StringEntity(post, ContentType.APPLICATION_FORM_URLENCODED);
      httpPost.setEntity(requestEntity);
      HttpResponse response = httpclient.execute(httpPost);
      if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 206) {
        LOGGER.error("Response status line = " + response.getStatusLine() + ", code = " + response.getStatusLine().getStatusCode() + ", from URL " + url + " post " + post);
        return null;
      }
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, "utf-8");
      EntityUtils.consume(entity);
    } catch (Exception e) {
      LOGGER.error(e.toString());
      error = e.toString();
      //LOGGER.debug("URLClientImpl response with error "+error);
      return result;
    } finally {
      httpPost.releaseConnection();
    }
    LOGGER.debug("URLClientImpl response: "+result);
    return result;

  }

  @Override
  public String doURLGet(String url) {
    return doURLGet(url, DEFAULT_TRIES, DEFAULT_TIMEOUT);
  }

  /**
   * doURLGet returns the result of the default get URL.
   *
   * @param cycles - the number of time to try
   * @param timeout - the max time to wait in millisecs. No timeout if <= 0
   * @return
   */
  @Override
  public String doURLGet(int cycles, int timeout) {
    return doURLGet(myURL, cycles, timeout);
  }

  /**
   * doURLGet returns the result of the HTTP get URL.
   *
   * @param url - the URL to get
   * @param cycles - the number of time to try
   * @param timeout - the max time to wait in millisecs. No timeout if <= 0
   * @return
   */
  @Override
  public String doURLGet(String url, int cycles, int timeout) {
    int attempt = 0;
    if (timeout < 0) {
      timeout = 0; // no timeout
    }
    // We will retry up to n times.
    for (attempt = 1; attempt <= cycles; attempt++) {
      try {
                // execute the method.
        LOGGER.debug("URLClientImpl: cycleGet timeout= " + timeout + ", attempt " + attempt + 1);
        LOGGER.debug("URL is: " + url);
        return doURLGet(url, timeout);
      } catch (Exception e) {
        LOGGER.error("A recoverable exception occurred, attempt " + attempt + ". "
                + e.getMessage());
      }
    }

    // If we are here, we didn't succeed
    LOGGER.error("Unable to get result for " + url);
    error = "Unable to get result for " + url;
    return "";

  }

  private String doURLGet(String url, int timeout) throws Exception {
    LOGGER.debug("URLClientImpl doURLGet: url=" + url + " timeout=" + timeout);
    String result = "";
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(url);
    httpGet.getParams().setParameter("http.socket.timeout", new Integer(timeout));
    httpGet.getParams().setParameter("http.connection.timeout", new Integer(timeout));

    try {
      HttpResponse response = httpclient.execute(httpGet);
      if (response.getStatusLine().getStatusCode() != 200) {
        LOGGER.error("Response status line = " + response.getStatusLine() + ", code = " + response.getStatusLine().getStatusCode() + ", from url " + url);
        return null;
      }
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, "utf-8");
      EntityUtils.consume(entity);
    } catch (IOException e) {
      e.printStackTrace();
      LOGGER.error("While getting url " + url + " error: " + e.toString());
      throw (e);
    } catch (ParseException e) {
      e.printStackTrace();
      LOGGER.error("While getting url " + url + " error: " + e.toString());
      throw (e);
    }
    
    finally {
      httpGet.releaseConnection();
    }
    return result;

  }

}
