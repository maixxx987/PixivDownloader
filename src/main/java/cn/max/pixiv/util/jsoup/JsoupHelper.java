package cn.max.pixiv.util.jsoup;

import cn.max.pixiv.common.Constant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author MaxStar
 * @date 2019/5/16
 */
public class JsoupHelper {

    /**
     * 获取原图地址
     * 先从解析获取图片的src，然后拼成正确的原图地址
     * eg.
     * page src: https://i.pximg.net/c/600x600/img-master/img/2019/05/16/13/09/10/74751807_p0_master1200.jpg
     * origin:   https://i.pximg.net/img-original/img/2019/05/16/13/09/10/74751807_p0.png
     *
     * @param content   网页代码
     * @param className div的class名
     * @return
     */
    public static String getOriginURL(String content, String className) {
        Document doc = Jsoup.parse(content);
        Elements divs = doc.getElementsByClass(className);
        for (Element div : divs) {
            Elements imgs = div.getElementsByTag("img");
            for (Element img : imgs) {

                String imgSrc = img.attr("src");
                System.out.println("Thumbnail src = " + imgSrc);

                // 拼接原图地址（真）
                String date = imgSrc.substring(imgSrc.indexOf("/img/"), imgSrc.indexOf("_p"));
                String _p = imgSrc.substring(imgSrc.indexOf("_p"), imgSrc.indexOf("_p") + 3);
                String format = imgSrc.substring(imgSrc.lastIndexOf("."));
                String originUrl = Constant.ORIGIN_URL_PREFIX + date + _p + format;
                System.out.println(originUrl);
                return originUrl;
            }
        }

        return null;
    }
}
