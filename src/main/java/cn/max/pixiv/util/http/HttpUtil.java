package cn.max.pixiv.util.http;

import cn.max.pixiv.common.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author MaxStar
 * @date 2019/5/15
 */
public class HttpUtil {

    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CODE_NOT_FOUND = 404;

    /**
     * 创建Http请求(有头文件)
     *
     * @param url     请求连接
     * @param headers 请求头
     * @return 返回实体
     */
    private static HttpEntity httpGet(String url, Map<String, String> headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", Constant.USER_AGENT);

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(httpGet::setHeader);
        }

        CloseableHttpClient httpClient = HttpConfig.getHttpClient();
        CloseableHttpResponse response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == STATUS_CODE_NOT_FOUND) {
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
    public static String getContent(String url) throws IOException {
        HttpEntity entity = httpGet(url, null);

        if (entity != null && entity.getContent() != null) {
            String content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } else {
            return null;
        }

    }

    /**
     * 获取InputStream(转为图片)
     *
     * @param url 请求路径
     * @return inputStream
     */
    public static InputStream getInputStream(String url, Map<String, String> headers) throws IOException {
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
}
