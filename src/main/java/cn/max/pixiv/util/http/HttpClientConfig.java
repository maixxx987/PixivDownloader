package cn.max.pixiv.util.http;

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
        buildHttpClient();
        return httpClient;
    }

    private static void buildHttpClient() {
        buildHttpClient(null, null);
    }

    private static void buildHttpClient(String host, Integer port) {
        if (httpClient == null) {
            synchronized (HttpClientConfig.class) {
                if (httpClient == null) {
                    HttpClient.Builder builder = HttpClient.newBuilder()
                            .version(HttpClient.Version.HTTP_2)
                            // connection timeout
                            .connectTimeout(Duration.ofSeconds(5))
                            .executor(ThreadPool.httpPool);

                    if (host != null && !host.isBlank() && port != null && port != 0) {
                        builder.proxy(ProxySelector.of(new InetSocketAddress(host, port)));
                    }

                    httpClient = builder.build();
                }
            }
        }
    }

    public static void setProxy(String host, int port) {
        buildHttpClient(host, port);
    }
}
