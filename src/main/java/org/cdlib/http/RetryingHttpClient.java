package org.cdlib.http;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RetryingHttpClient {

  private static final int DEFAULT_TRIES = 3;
  private static final int DEFAULT_TIMEOUT = 10000;
  private static final Logger LOGGER = LogManager.getLogger(RetryingHttpClient.class);

 public String httpPost(String url, String post) {
   return httpPost(url, post, DEFAULT_TIMEOUT);
 }
  
  /**
   * doURLPost return the result of post to the supplied URL
   *
   * @param url
   * @param post
   * @param timeout
   * @return
   */
  public String httpPost(String url, String post, int timeout) {
    String result = "";

    RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
    CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    HttpPost httpPost = new HttpPost(url);

    try {
      HttpEntity requestEntity = new StringEntity(post, ContentType.APPLICATION_FORM_URLENCODED);
      httpPost.setEntity(requestEntity);
      HttpResponse response = httpClient.execute(httpPost);
      if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 206) {
        LOGGER.error("Response status line = "
                + response.getStatusLine()
                + ", code = "
                + response.getStatusLine().getStatusCode()
                + ", from URL " + url + " post "
                + post);
        return null;
      }
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, "utf-8");
      EntityUtils.consume(entity);
    } catch (IOException | UnsupportedCharsetException | ParseException e) {
      // TODO -- throw WebException and have caller handle this case
      LOGGER.error(e.getMessage());
      return result;
    } finally {
      httpPost.releaseConnection();
    }
    return result;
  }

  public String httpGet(String url) {
    return httpGet(url, DEFAULT_TRIES, DEFAULT_TIMEOUT);
  }


  /**
   * doURLGet returns the result of the HTTP get URL.
   *
   * @param url - the URL to get
   * @param cycles - the number of time to try
   * @param timeout - the max time to wait in millisecs. No timeout if <= 0
   * @return
   */
  public String httpGet(String url, int cycles, int timeout) {
    WebException exception = null;
    int attempt;
    if (timeout < 0) {
      timeout = 0;
    }
    for (attempt = 1; attempt <= cycles; attempt++) {
      try {
        LOGGER.debug(this.getClass().getName() + ": cycleGet timeout= " + timeout + ", attempt " + attempt);
        LOGGER.debug("URL is: " + url);
        return RetryingHttpClient.this.httpGet(url, timeout);
      } catch (WebException e) {
        LOGGER.debug("An exception occurred on attempt " + attempt + ". " + e.getMessage());
        exception = e;
        if (e.getStatus() < 500) {
          break;
        }
      }
    }
    throw exception;
  }

  private String httpGet(String url, int timeout) throws WebException {
    LOGGER.debug(this.getClass().getName() + " doURLGet: url=" + url + " timeout=" + timeout);
    String result = "";
    RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
    CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    HttpGet httpGet = new HttpGet(url);

    try {
      HttpResponse response = httpClient.execute(httpGet);
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode != 200) {
        LOGGER.error("Response status line = " + response.getStatusLine() + ", code = " + response.getStatusLine().getStatusCode() + ", from url " + url);
        throw new WebException(response);
      }
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, "utf-8");
      EntityUtils.consume(entity);
    } catch (IOException e) {
      LOGGER.error("While getting url " + url + " error: " + e.toString());
      throw new WebException(e.getMessage(), 500);
    } catch (ParseException e) {
      LOGGER.error("While getting url " + url + " error: " + e.toString());
      throw new WebException(e.getMessage(), 422);
    } finally {
      httpGet.releaseConnection();
    }
    return result;

  }

}
