package cn.max.pixiv.util.http;

import cn.max.pixiv.common.Constant;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
        httpGet.addHeader("accept", Constant.ACCEPT);
        httpGet.addHeader("accept-encoding", Constant.ACCEPT_ENCODING);
        httpGet.addHeader("accept-language", Constant.ACCEPT_LANGUAGE);
        httpGet.addHeader("user-agent", Constant.USER_AGENT);

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(httpGet::addHeader);
        }

        CloseableHttpResponse response = HttpConfig.getHttpClient().execute(httpGet);

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

    public static String uploadFile(String url, String filePath) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Accept", Constant.ACCEPT);
        httpPost.addHeader("Accept-Encoding", Constant.ACCEPT_ENCODING);
        httpPost.addHeader("Accept-Language", Constant.ACCEPT_LANGUAGE);
        httpPost.addHeader("User-Agent", Constant.USER_AGENT);

        CloseableHttpResponse response = null;
        String resultString = null;
        try {
            FileBody file = new FileBody(new File(filePath));

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("file", file);
            builder.setCharset(StandardCharsets.UTF_8);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            HttpEntity reqEntity = builder.build();
            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = HttpConfig.getHttpClient().execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            EntityUtils.consume(response.getEntity());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultString;
    }
}
