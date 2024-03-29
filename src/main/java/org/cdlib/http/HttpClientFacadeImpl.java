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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientFacadeImpl implements HttpClientFacade {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientFacadeImpl.class);
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
       throw new WebException(response.getStatusLine().getReasonPhrase(), response);
      }
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, "utf-8");
      EntityUtils.consume(entity);
    } catch (IOException | UnsupportedCharsetException | ParseException e) {
      // TODO throw an exception
      return "";
    } finally {
      httpPost.releaseConnection();
    }
    return result;

  }

  @Override
  public String get(String url) {
    String result = "";
    RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_TIMEOUT).setSocketTimeout(DEFAULT_TIMEOUT).build();
    HttpGet httpGet = new HttpGet(url);
    
    try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build()) {
      HttpResponse response = httpClient.execute(httpGet);
      if (response.getStatusLine().getStatusCode() != 200) {
        throw new WebException(response.getStatusLine().getReasonPhrase(), response);
      }
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, "utf-8");
      EntityUtils.consume(entity);
    } catch (IOException e) {
      throw new WebException(e.getMessage(), e, 500);
    } catch (ParseException e) {
      throw new WebException(e.getMessage(), e, 400);
    }
    
    return result;
    
  }

}
