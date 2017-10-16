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

public class URLClientImpl implements URLClient {

    private static Logger LOGGER = LogManager.getLogger(URLClientImpl.class);
    private static final int DEFAULT_TRIES = 3;
    private static final int DEFAULT_TIMEOUT = 20000;

    private String error = null;
    private String myURL = null;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        String result = "";
        content = null;

        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);

        try {
            HttpEntity requestEntity = new StringEntity(post, ContentType.APPLICATION_FORM_URLENCODED);
            httpPost.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(httpPost);
            content = EntityUtils.toString(response.getEntity(), "utf=8");
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
            LOGGER.error(e.toString());
            error = e.toString();
            return result;
        } finally {
            httpPost.releaseConnection();
        }
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
        content = null;
        int attempt = 0;
        if (timeout < 0) {
            timeout = 0;
        }
        for (attempt = 1; attempt <= cycles; attempt++) {
            try {
                LOGGER.debug("URLClientImpl: cycleGet timeout= " + timeout + ", attempt " + attempt + 1);
                LOGGER.debug("URL is: " + url);
                return doURLGet(url, timeout);
            } catch (Exception e) {
                LOGGER.error("A recoverable exception occurred, attempt " + attempt + ". "
                        + e.getMessage());
            }
        }
        LOGGER.error("Unable to get result for " + url);
        error = "Unable to get result for " + url;
        return "";

    }

    private String doURLGet(String url, int timeout) throws Exception {
        LOGGER.debug("URLClientImpl doURLGet: url=" + url + " timeout=" + timeout);
        String result = "";
        content = null;
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            content = EntityUtils.toString(response.getEntity(), "utf=8");
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
        } finally {
            httpGet.releaseConnection();
        }
        return result;

    }

}
