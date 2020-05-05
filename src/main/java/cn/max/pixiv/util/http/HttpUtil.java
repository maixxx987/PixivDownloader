package cn.max.pixiv.util.http;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.util.io.IOUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author MaxStar
 * @date 2020/5/3
 */
public class HttpUtil {

    private static final int STATUS_CODE_OK = 200;

    private static HttpRequest getRequest(String url, Map<String, String> headers) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                // read timeout
                .timeout(Duration.ofSeconds(10))
                .header("accept", Constant.ACCEPT)
                .header("accept-language", Constant.ACCEPT_LANGUAGE)
                .header("user-agent", Constant.USER_AGENT)
                .GET();

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(builder::header);
        }

        return builder.build();
    }

    public static String httpGet(String url, Map<String, String> headers) throws IOException, InterruptedException {
        HttpRequest request = getRequest(url, headers);
        HttpResponse<String> response = HttpClientConfig.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == STATUS_CODE_OK) {
            return response.body();
        }
        return null;
    }


    public static void download(String url, Map<String, String> headers, String fileDes) throws IOException, InterruptedException {
        HttpRequest request = getRequest(url, headers);
        HttpResponse<Path> response = HttpClientConfig.getHttpClient().send(request, HttpResponse.BodyHandlers.ofFile(Path.of(fileDes)));

        if (response.statusCode() != STATUS_CODE_OK) {
            // 文件不存在则删除
            IOUtil.deleteFile(Path.of(fileDes));
        }
    }

    public static String uploadImg2SauceNAO(String filePath) throws IOException, InterruptedException {

        String boundary = UUID.randomUUID().toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Constant.SAUCENAO_SEARCH_URL))
//                .timeout(Duration.ofMillis(5000))
                .header("Accept", Constant.ACCEPT)
                .header("Accept-Encoding", Constant.ACCEPT_LANGUAGE)
                .header("User-Agent", Constant.USER_AGENT)
                .header("Content-Type", Constant.MULTIPART_FORM_DATA + boundary)
                .POST(setMultipartData(Map.of("file", Path.of(filePath)), boundary))
                .build();

        HttpResponse<String> response = HttpClientConfig.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == STATUS_CODE_OK) {
            return response.body();
        }
        return null;
    }

    /**
     * @link https://stackoverflow.com/questions/56481475/how-to-define-multiple-parameters-for-a-post-request-using-java-11-http-client
     */
    private static HttpRequest.BodyPublisher setMultipartData(Map<Object, Object> data, String boundary) throws IOException {
        // Result request body
        List<byte[]> byteArrays = new ArrayList<>();

        // Separator with boundary
        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(StandardCharsets.UTF_8);

        // Iterating over data parts
        for (Map.Entry<Object, Object> entry : data.entrySet()) {

            // Opening boundary
            byteArrays.add(separator);

            // If value is type of Path (file) append content type with file name and file binaries, otherwise simply append key=value
            if (entry.getValue() instanceof Path) {
                Path path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName()
                        + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n")
                        .getBytes(StandardCharsets.UTF_8));
            }
        }

        // Closing boundary
        byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));

        // Serializing as byte array
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }
}
