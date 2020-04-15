package cn.max.pixiv.crawler;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.entity.Image;
import cn.max.pixiv.util.http.HttpUtil;
import cn.max.pixiv.util.io.IOUtil;
import cn.max.pixiv.util.jsoup.JsoupHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author MaxStar
 * @date 2020/4/13
 */
public class CrawlerTask {

    public static void task(Image image) throws IOException {
        if (image.getOriginUrl() == null) {
            parse(image);
        }

        if (image.getOriginUrl() != null) {
            download(image);
        }
    }

    private static void parse(Image image) throws IOException {
        long id = image.getId();
        String url = Constant.THUMBNAIL_URL_PREFIX + id;

        image.setUrl(url);

        String content = HttpUtil.getContent(url);

        if (content != null && !content.isBlank()) {
            String originUrl = JsoupHelper.parseImgOriginUrl(content, String.valueOf(id));
            if (originUrl != null) {
                image.setOriginUrl(originUrl);
            }
        }
    }

    private static void download(Image image) throws IOException {
        long id = image.getId();
        String originSrc = image.getOriginUrl();
        int currNum = image.getCurrNum();

        int suffixIndex = originSrc.lastIndexOf(".");
        String suffix = originSrc.substring(suffixIndex);

        int _pIndex = originSrc.lastIndexOf("_p");

        String prefix = originSrc.substring(0, _pIndex);


        // 如果不是首图，则要重新拼接url
        if (currNum != 0) {
            originSrc = prefix + "_p" + currNum + suffix;
        }


        InputStream inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", image.getUrl()));


        while (inputStream != null) {
            IOUtil.inputStream2Picture(inputStream, ("src/test/resources/" + id + "_p" + currNum + suffix));
            System.out.println("文件名： " + id + "_p" + currNum + suffix);

            // get next picture inputStream
            currNum += 1;
            image.setCurrNum(currNum);
            originSrc = prefix + "_p" + currNum + suffix;
            System.out.println(currNum + " : " + originSrc);

            // download picture

            inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", image.getUrl()));

        }
        System.out.println("break + num = " + currNum);
    }
}
