package cn.max.pixiv.util.jsoup;

import cn.max.pixiv.entity.Image;
import cn.max.pixiv.util.http.HttpUtil;
import cn.max.pixiv.util.io.IOUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MaxStar
 * @date 2019/5/16
 */
public class JsoupHelper {

    /**
     * parse origin picture url from content by Jsoup
     * <p>
     * eg.
     * page src: https://www.pixiv.net/artworks/63472776
     * origin:   https://i.pximg.net/img-original/img/2017/06/20/01/14/35/63472776_p0.png
     *
     * @param content page source
     * @param id      picture ID
     * @return
     */
    public static String parseImgOriginUrl(String content, String id) {
        Document doc = Jsoup.parse(content);

        // origin url json string
        String preLoadDataJsonStr = doc.select("meta[name=preload-data]").get(0).attr("content");

        JSONObject preLoadDataJsonObject = JSON.parseObject(preLoadDataJsonStr);

        // illust -> urls -> original
        String originSrc = preLoadDataJsonObject.getJSONObject("illust").getJSONObject(id).getJSONObject("urls").getString("original");
        System.out.println(originSrc);
        return originSrc;
    }


    /**
     * 解析SauceNAO
     *
     * @param content url
     * @return
     */
    public static List<Image> parseSauceNAO(String content) throws IOException {
        List<Image> list = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Elements resultsDiv = doc.select("div[class=result]");
        if (resultsDiv != null && resultsDiv.size() > 0) {
            for (Element div : resultsDiv) {
                Element resultContentColumnDiv = div.select("div[class=resultcontentcolumn]").first();
                if (resultContentColumnDiv != null) {
                    Element strong = resultContentColumnDiv.select("strong").first();

                    // 判断是否来自Pixiv
                    if (strong != null && strong.text().contains("Pixiv")) {
                        Image image = new Image();
                        image.setId(Long.parseLong(resultContentColumnDiv.select("a[class=linkify]").first().text()));

                        Element artistInfo = resultContentColumnDiv.select("a[class=linkify]").last();
                        String artistHref = artistInfo.attr("href");
                        image.setArtistUrl(artistHref);
                        image.setArtistName(artistInfo.text());
                        image.setArtistId(Long.parseLong(artistHref.substring(artistHref.indexOf("=") + 1)));

                        // 缩略图
//                String simpleImgUrl = div.select("img").first().attr("src");
//                InputStream simpleImage = HttpUtil.getInputStream(simpleImgUrl, null);
//                String fileName = div.select("img").first().attr("title");
//                fileName = fileName.substring(fileName.indexOf("-") + 2);

                        list.add(image);
                    }
                }
            }
        }

        return list;
    }
}
