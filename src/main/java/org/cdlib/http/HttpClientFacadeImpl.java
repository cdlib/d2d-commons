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

public class HttpClientFacadeImpl implements HttpClientFacade {

  private static final Logger LOGGER = LogManager.getLogger(HttpClientFacadeImpl.class);
  private static final int DEFAULT_TRIES = 3;
  private static final int DEFAULT_TIMEOUT = 20000;
  private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.APPLICATION_FORM_URLENCODED;

  public HttpClientFacadeImpl() {
  }
  
  @Override
  public String post(String url, String post, int timeout) {
    return post(url, post, timeout, DEFAULT_CONTENT_TYPE);
  }

  @Override
  public String post(String url, String post, int timeout, ContentType contentType) {
    String result = "";

    RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
    CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    HttpPost httpPost = new HttpPost(url);

    try {
      HttpEntity requestEntity = new StringEntity(post, contentType);
      httpPost.setEntity(requestEntity);
      HttpResponse response = httpClient.execute(httpPost);
      if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 399) {
        LOGGER.error("Response status line = " + response.getStatusLine() + ", code = " + response.getStatusLine().getStatusCode() + ", from URL " + url + " post " + post);
        throw new WebException(response.getStatusLine().getReasonPhrase(), response);
      }
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, "utf-8");
      EntityUtils.consume(entity);
    } catch (IOException | UnsupportedCharsetException | ParseException e) {
      LOGGER.error(e.toString());
      return "";
    } finally {
      httpPost.releaseConnection();
    }
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
    WebException exception = new WebException("Unknown exception.", 500);
    int attempt = 0;
    if (timeout < 0) {
      timeout = 0;
    }
    for (attempt = 1; attempt <= cycles; attempt++) {
      try {
        LOGGER.debug("URLClientImpl: cycleGet timeout= " + timeout + ", attempt " + attempt + 1);
        LOGGER.debug("URL is: " + url);
        return doURLGet(url, timeout);
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

  private String doURLGet(String url, int timeout) {
    LOGGER.debug("URLClientImpl doURLGet: url=" + url + " timeout=" + timeout);
    String result = "";
    RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
    CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    HttpGet httpGet = new HttpGet(url);

    try {
      HttpResponse response = httpClient.execute(httpGet);
      if (response.getStatusLine().getStatusCode() != 200) {
        LOGGER.error("Response status line = " + response.getStatusLine() + ", code = " + response.getStatusLine().getStatusCode() + ", from url " + url);
        return "";
      }
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, "utf-8");
      EntityUtils.consume(entity);
    } catch (IOException e) {
      LOGGER.error("While getting url " + url + " error: " + e.toString());
      throw new WebException(e.getMessage(), e, 500);
    } catch (ParseException e) {
      LOGGER.error("While getting url " + url + " error: " + e.toString());
      throw new WebException(e.getMessage(), e, 422);
    } finally {
      httpGet.releaseConnection();
    }
    return result;

  }

}
