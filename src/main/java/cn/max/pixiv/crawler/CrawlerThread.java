package cn.max.pixiv.crawler;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.util.http.HttpUtil;
import cn.max.pixiv.util.io.IOUtil;
import cn.max.pixiv.util.jsoup.JsoupHelper;

import java.io.InputStream;
import java.util.Map;

/**
 * @author MaxStar
 * @date 2019/5/8
 */
public class CrawlerThread implements Runnable {

    private long id;

    public CrawlerThread(long id) {
        this.id = id;
    }

    /**
     * getContent -> parse origin url -> get picture inputStream -> transfer to file -> get next picture
     */
    @Override
    public void run() {

        String picPage = Constant.THUMBNAIL_URL_PREFIX + id;
        String content = HttpUtil.getContent(picPage);
        if (content != null && !content.isBlank()) {

            String originSrc = JsoupHelper.getOriginUrl(content, String.valueOf(id));
            if (originSrc != null) {
                InputStream inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", picPage));
                int currNum = 0;

                int suffixIndex = originSrc.lastIndexOf(".");
                String suffix = originSrc.substring(suffixIndex);

                int _pIndex = originSrc.lastIndexOf("_p");
                String _p = originSrc.substring(originSrc.lastIndexOf("_"));

                String prefix = originSrc.substring(0, _pIndex);

                while (inputStream != null) {

                    // download picture
                    IOUtil.inputStream2Picture(inputStream, ("G:\\test\\" + id + "_p" + currNum + suffix));
                    System.out.println("文件名： " + id + "_p" + currNum + suffix);


                    // get next picture inputStream
                    currNum += 1;
                    originSrc = prefix + "_p" + currNum + suffix;
                    System.out.println(currNum + " : " + originSrc);
                    inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", picPage));
                }
                System.out.println("break + num = " + currNum);
            }
        }
    }
}

