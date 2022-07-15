package com.xiaojie.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.InMemoryDnsResolver;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil {

    public final static PoolingHttpClientConnectionManager cm;
    public final static HttpRequestRetryHandler retryHandler = new TTHttpRequestRetryHandler(3,
        true);
    public final static InMemoryDnsResolver memoryDns = new InMemoryDnsResolver();
    public final static List<String> UN_KEEY_ALIVE_HOSTS = new ArrayList<String>();
    static {
        loadDnsConfig();
        loadUnKeepAliveConfig();
        cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        cm.setMaxTotal(500);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(50);
    }

    public static Registry<ConnectionSocketFactory> defaultRegistry() {
        return RegistryBuilder.<ConnectionSocketFactory> create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
    }

    private static void loadUnKeepAliveConfig() {
        ResourceBundle kpconfig = ConfigUtil.getInstance().loadFile("un-keep-alive");
        if (kpconfig == null) {
            return;
        }
        Set<String> keys = kpconfig.keySet();
        for (String key : keys) {
            if ("true".equals(kpconfig.getString(key))) {
                UN_KEEY_ALIVE_HOSTS.add(key);
            }
        }
    }

    private static void loadDnsConfig() {
        ResourceBundle dnsconfig = ConfigUtil.getInstance().loadFile("dns");
        if (dnsconfig == null) {
            return;
        }
        Set<String> keys = dnsconfig.keySet();
        for (String key : keys) {
            add(key, getByNames(dnsconfig.getString(key).split(",")));
        }
    }

    private static InetAddress[] getByNames(String[] names) {
        try {
            InetAddress[] address = new InetAddress[names.length];
            for (int i = 0; i < names.length; i++) {
                address[i] = InetAddress.getByName(names[i]);
            }
            return address;
        } catch (Exception e) {
            return null;
        }
    }

    public static void add(String host, InetAddress... ips) {
        Args.notNull(host, "Host name");
        Args.notNull(ips, "Array of IP addresses");
        memoryDns.add(host, ips);
    }

    public static RequestConfig buildConfig(int connectTimeOut, int readTimeout) {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 璁剧疆杩炴帴瓒呮椂
        configBuilder.setConnectTimeout(connectTimeOut);
        // 璁剧疆璇诲彇瓒呮椂
        configBuilder.setSocketTimeout(readTimeout);
        // 璁剧疆浠庤繛鎺ユ睜鑾峰彇杩炴帴瀹炰緥鐨勮秴鏃�
        configBuilder.setConnectionRequestTimeout(500);
        return configBuilder.build();
    }

    /**
     * 鑾峰彇涓�涓猦ttpclient
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * 鑾峰彇涓�涓猦ttpclient
     * @return
     */
    public static CloseableHttpClient getHttpClient(HttpRequestRetryHandler retryHandler) {
        return HttpClients.custom().setRetryHandler(retryHandler).setConnectionManager(cm).build();
    }

    /**
     * 
     * MAP绫诲瀷鏁扮粍杞崲鎴怤ameValuePair绫诲瀷
     * @param params
     * @return
     */
    public static List<NameValuePair> buildNameValuePair(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return nvps;
    }

    public static String doPost(String body, String url) throws ClientProtocolException,
                                                        IOException {
        return doPost(body, url, null);
    }

    public static String doPost(Map<String, String> params, String url)
                                                                       throws ClientProtocolException,
                                                                       IOException {
        return doPost(params, url, null);
    }

    public static String doPost(Map<String, String> params, String url, RequestConfig requestConfig)
                                                                                                    throws ClientProtocolException,
                                                                                                    IOException {
        return doPost(params, url, requestConfig, null);
    }

    /**
     * 
     * 
     * @param params
     * @param url
     * @param requestConfig
     * @param retryHandler
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doPost(Map<String, String> params, String url,
                                RequestConfig requestConfig, HttpRequestRetryHandler retryHandler)
                                                                                                  throws ClientProtocolException,
                                                                                                  IOException {
        return doPost(params, url, requestConfig, retryHandler, cm);
    }

    /**
     * 
     * 
     * @param params
     * @param url
     * @param requestConfig
     * @param retryHandler
     * @param cm
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doPost(Map<String, String> params, String url,
                                RequestConfig requestConfig, HttpRequestRetryHandler retryHandler,
                                HttpClientConnectionManager cm) throws ClientProtocolException,
                                                               IOException {
        String result = null;
        List<NameValuePair> nvps = buildNameValuePair(params);
        //Pooling connection manager
        CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(retryHandler)
            .setConnectionManager(cm).build();
        HttpPost httpPost = new HttpPost(url);
        String host = urlToHost(url);
        if (UN_KEEY_ALIVE_HOSTS.contains(host)) {
            httpPost.setHeader("Connection", "close");
        }
        if (null != requestConfig) {
            httpPost.setConfig(requestConfig);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            String reasonPhrase = response.getStatusLine().getReasonPhrase();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
        } finally {
            if (response != null)
                response.close();
        }
        return result;
    }

    public static String doPost(String body, String url, RequestConfig requestConfig)
                                                                                     throws ClientProtocolException,
                                                                                     IOException {
        return doPost(body, url, requestConfig, null);
    }

    public static String doPost(String body, String url, RequestConfig requestConfig,
                                HttpRequestRetryHandler retryHandler)
                                                                     throws ClientProtocolException,
                                                                     IOException {
        return doPost(body, url, requestConfig, retryHandler, cm);
    }

    /**
     * post 鎻愪氦鏁版嵁
     * @param params
     * @param url
     * @return String 
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doPost(String body, String url, RequestConfig requestConfig,
                                HttpRequestRetryHandler retryHandler, HttpClientConnectionManager cm)
                                                                                                     throws ClientProtocolException,
                                                                                                     IOException {
        String result = null;
        //Pooling connection manager
        CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(retryHandler)
            .setConnectionManager(cm).build();
        HttpPost httpPost = new HttpPost(url);
        if (requestConfig != null) {
            httpPost.setConfig(requestConfig);
        }
        String host = urlToHost(url);
        if (UN_KEEY_ALIVE_HOSTS.contains(host)) {
            httpPost.setHeader("Connection", "close");
        }
        httpPost.setEntity(new StringEntity(body, "utf-8"));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            String reasonPhrase = response.getStatusLine().getReasonPhrase();
            int statusCode = response.getStatusLine().getStatusCode();
            if (reasonPhrase.equals("OK") && statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
            }
            EntityUtils.consume(entity);
        } finally {
            if (response != null)
                response.close();
        }
        return result;
    }

    public static String doGet(String url) throws ClientProtocolException, IOException {
        return doGet(url, null);
    }

    public static String doGet(Map<String, String> params, String url)
                                                                      throws ClientProtocolException,
                                                                      IOException {
        return doGet(params, url, null);
    }

    /**
     * 
     * @param params
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(Map<String, String> params, String url, RequestConfig requestConfig)
                                                                                                   throws ClientProtocolException,
                                                                                                   IOException {
        List<NameValuePair> nvps = buildNameValuePair(params);
        String query = URLEncodedUtils.format(nvps, "UTF-8");
        if (url.indexOf("?") == -1) {
            url += "?" + query;
        } else {
            url += "&" + query;
        }
        return doGet(url, requestConfig);
    }

    public static String doGet(String url, RequestConfig requestConfig)
                                                                       throws ClientProtocolException,
                                                                       IOException {
        return doGet(url, requestConfig, null);
    }

    public static String doGet(String url, RequestConfig requestConfig,
                               HttpRequestRetryHandler retryHandler)
                                                                    throws ClientProtocolException,
                                                                    IOException {
        return doGet(url, requestConfig, retryHandler, cm);
    }

    /**
     * get 鎻愪氦鏁版嵁
     * 
     * @param url
     * @return String
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String url, RequestConfig requestConfig,
                               HttpRequestRetryHandler retryHandler, HttpClientConnectionManager cm)
                                                                                                    throws ClientProtocolException,
                                                                                                    IOException {
        String result = null;
        //Pooling connection manager
        CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(retryHandler)
            .setConnectionManager(cm).build();
        HttpGet httpGet = new HttpGet(url);
        if (requestConfig != null) {
            httpGet.setConfig(requestConfig);
        }
        String host = urlToHost(url);
        if (UN_KEEY_ALIVE_HOSTS.contains(host)) {
            httpGet.setHeader("Connection", "close");
        }
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            HttpEntity entity = response.getEntity();
            String reasonPhrase = response.getStatusLine().getReasonPhrase();
            int statusCode = response.getStatusLine().getStatusCode();
            if (reasonPhrase.equals("OK") && statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
            }
            EntityUtils.consume(entity);
        } finally {
            if (response != null)
                response.close();
        }
        return result;
    }

    /**
     * 妫�娴嬭繛鎺ュ湪5s涓旇姹傚湪5s鍐呯殑鍦板潃鍝嶅簲缁撴灉
     * 
     * @param url
     * @return
     */
    public static boolean checkUrl(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        RequestConfig rc = RequestConfig.custom().setConnectTimeout(10000)
            .setConnectionRequestTimeout(500).build();
        HttpGet hg = new HttpGet(url);
        hg.setConfig(rc);
        String host = urlToHost(url);
        if (UN_KEEY_ALIVE_HOSTS.contains(host)) {
            hg.setHeader("Connection", "close");
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(hg);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200)
                return true;
        } catch (IOException e) {
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
            }
        }
        return false;
    }

    public static String urlToHost(String urlStr) {
        try {
            URL url = new URL(urlStr);
            return url.getHost();
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
