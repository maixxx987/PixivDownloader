package cn.max.pixiv.util.http;

import cn.max.pixiv.common.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author MaxStar
 * @date 2019/5/15
 */
public class HttpUtil {

    private static PoolingHttpClientConnectionManager connectionManager;
    private static CloseableHttpClient httpClient = getHttpClient();

    /**
     * 创建Http请求(有头文件)
     *
     * @param url     请求连接
     * @param headers 请求头
     * @return 返回实体
     */
    private static HttpEntity httpGet(String url, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", Constant.USER_AGENT);

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(httpGet::setHeader);
        }

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println("status code :" + response.getStatusLine().getStatusCode());
            return null;
        }

        HttpEntity entity = response.getEntity();

        if (entity == null) {
            System.out.println("entity is null");
            return null;
        }

        return entity;
    }

    /**
     * 获取页面内容
     *
     * @param url 请求连接
     * @return 页面内容
     */
    public static String getContent(String url) {
        HttpEntity entity = httpGet(url, null);
        try {
            if (entity != null && entity.getContent() != null) {
                String content = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
                return content;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
            return null;
        }
    }

    /**
     * 获取InputStream(转为图片)
     *
     * @param url 请求路径
     * @return inputStream
     */
    public static InputStream getInputStream(String url, Map<String, String> headers) {
        HttpEntity entity = httpGet(url, headers);
        if (entity != null) {
            try {
                return entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("get inputStream error");
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 配置HttpClient的具体方法
     *
     * @return httpClient
     */
    private static CloseableHttpClient createHttpClient() {
        // 设置代理
        HttpHost proxy = new HttpHost("127.0.0.1", 1081);

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();

        // 连接池
        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        // 最大连接数
//        connectionManager.setMaxTotal(5);

        // 最大路由连接数（单个主机最大连接数）
        connectionManager.setDefaultMaxPerRoute(5);

        // httpClient配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectionRequestTimeout(HttpConfig.GET_CONNECTION_TIME_OUT)
                .setConnectTimeout(HttpConfig.CONNECTION_TIME_OUT)
                .setSocketTimeout(HttpConfig.SOCKET_TIME_OUT)
                .build();

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
    }


    /**
     * 单例方式获取生成，并启动一个检测线程清理异常和空闲的连接
     *
     * @return httpClient
     */
    private static CloseableHttpClient getHttpClient() {
        httpClient = createHttpClient();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            connectionManager.closeExpiredConnections();
            connectionManager.closeIdleConnections(HttpConfig.IDLE, TimeUnit.MILLISECONDS);
        }, HttpConfig.INITIAL_DELAY, HttpConfig.PERIOD, TimeUnit.MILLISECONDS);
        return httpClient;
    }

}
