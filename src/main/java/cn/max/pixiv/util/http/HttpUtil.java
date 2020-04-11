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
        CloseableHttpClient httpClient = HttpConfig.getHttpClient();
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
}
