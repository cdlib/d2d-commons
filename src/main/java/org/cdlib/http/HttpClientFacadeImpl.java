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

public class HttpClientFacadeImpl implements HttpClientFacade {

  private static Logger LOGGER = LogManager.getLogger(HttpClientFacadeImpl.class);
  private static final int DEFAULT_TRIES = 3;
  private static final int DEFAULT_TIMEOUT = 20000;

  public HttpClientFacadeImpl() {
  }

  /**
   * post return the result of post to the supplied URL
   *
   * @param url
   * @param post
   * @param timeout
   * @return
   */
  @Override
  public String post(String url, String post, int timeout) {
    //LOGGER.debug("URLClientImpl post: url=" + url + " timeout=" + timeout);
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
      //LOGGER.debug("URLClientImpl response with error "+error);
      return result;
    } finally {
      httpPost.releaseConnection();
    }
    //LOGGER.debug("URLClientImpl response: "+result);
    return result;

  }

  @Override
  public String get(String url) {
    return get(url, DEFAULT_TRIES, DEFAULT_TIMEOUT);
  }

  /**
   * get returns the result of the HTTP get URL.
   *
   * @param url - the URL to get
   * @param cycles - the number of time to try
   * @param timeout - the max time to wait in millisecs. No timeout if <= 0
   * @return
   */
  @Override
  public String get(String url, int cycles, int timeout) {
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
