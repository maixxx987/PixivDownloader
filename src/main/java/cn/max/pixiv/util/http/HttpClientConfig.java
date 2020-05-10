package cn.max.pixiv.util.http;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.common.ThreadPool;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.time.Duration;

/**
 * @author MaxStar
 * @date 2020/5/4
 */
public class HttpClientConfig {

    private static HttpClient httpClient = null;

    public static HttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (HttpClientConfig.class) {
                if (httpClient == null) {
                    initHttpClient();
                }
            }
        }
        return httpClient;
    }

    private static void initHttpClient() {
        HttpClient.Builder builder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                // connection timeout
                .connectTimeout(Duration.ofSeconds(5))
                .executor(ThreadPool.httpPool);

        String host = Constant.properties.getProxy().getHost();
        Integer port = Constant.properties.getProxy().getPort();

        if (!host.isBlank() && port != -1) {
            builder.proxy(ProxySelector.of(new InetSocketAddress(host, port)));
        }

        httpClient = builder.build();
    }

    /**
     * 重新生成HttpClient
     */
    public static void rebuildHttpClient() {
        if (httpClient != null) {
            initHttpClient();
        }
    }
}
