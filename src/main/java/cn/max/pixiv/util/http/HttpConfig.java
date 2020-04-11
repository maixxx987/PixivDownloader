package cn.max.pixiv.util.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

import static cn.max.pixiv.common.ThreadPool.scheduledPool;

/**
 * @author MaxStar
 * @date 2019/5/16
 */
public class HttpConfig {

    /**
     * 连接超时
     */
    public static final int CONNECTION_TIME_OUT = 10 * 1000;

    /**
     * 返回超时
     */
    public static final int SOCKET_TIME_OUT = 10 * 1000;

    /**
     * 从连接池获取连接超时
     */
    public static final int GET_CONNECTION_TIME_OUT = 5 * 1000;

    /**
     * 连接空闲时间
     */
    public static final long IDLE = 5 * 1000;

    /**
     * 检测线程启动延时
     */
    public static final long INITIAL_DELAY = 10 * 1000;

    /**
     * 检测线程执行间隔
     */
    public static final long PERIOD = 10 * 1000;


    private static PoolingHttpClientConnectionManager connectionManager;

    private static CloseableHttpClient httpClient;

    private static final Object LOCK = new Object();


    /**
     * 配置HttpClient的具体方法
     *
     * @return httpClient
     */
    private static CloseableHttpClient createHttpClient() {
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
    static CloseableHttpClient getHttpClient() {
        if (httpClient == null){
            synchronized (LOCK){
                if (httpClient == null){
                    httpClient = createHttpClient();
                    scheduledPool.scheduleAtFixedRate(() -> {
                        connectionManager.closeExpiredConnections();
                        connectionManager.closeIdleConnections(HttpConfig.IDLE, TimeUnit.MILLISECONDS);
                    }, HttpConfig.INITIAL_DELAY, HttpConfig.PERIOD, TimeUnit.MILLISECONDS);
                }
            }
        }
        return httpClient;
    }

}
