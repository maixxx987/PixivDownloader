package cn.max.pixiv.util.jsoup;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.entity.PixivImage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * @return
     */
    public static void parseImgInfo(String content, PixivImage image) {
        Document doc = Jsoup.parse(content);

        // origin url json string
        String preLoadDataJsonStr = doc.select("meta[name=preload-data]").get(0).attr("content");

        JSONObject preLoadDataJsonObject = JSON.parseObject(preLoadDataJsonStr);

        JSONObject imgInfo = preLoadDataJsonObject.getJSONObject("illust").getJSONObject(String.valueOf(image.getImgId()));
        image.setImgTitle(imgInfo.getString("illustTitle"));
        long userId = imgInfo.getLong("userId");
        image.setArtistId(userId);
        image.setArtistName(imgInfo.getString("userName"));
        image.setArtistUrl(Constant.USER_URL_PREFIX + userId);

        JSONArray tags = imgInfo.getJSONObject("tags").getJSONArray("tags");
        if (tags != null && tags.size() > 0) {
            List<String> tagList = new ArrayList<>();
            for (Object o : tags) {
                JSONObject tag = (JSONObject) o;
                String tagStr = tag.getString("tag");
                if (tag.getJSONObject("translation") != null && tag.getJSONObject("translation").getString("en") != null) {
                    tagStr = tagStr + " / " + tag.getJSONObject("translation").getString("en");
                }
                tagList.add(tagStr);
            }
            image.setTagList(tagList);
        }

        // illust -> urls -> original
        image.setImgOriginUrl(imgInfo.getJSONObject("urls").getString("original"));

        image.setImgComment(doc.select("meta[name=description]").get(0).attr("content"));
    }


    /**
     * 解析SauceNAO
     *
     * @param content url
     * @return 来自pixiv的图片合集
     */
    public static Set<Integer> parseSauceNAO(String content)  {
        Document doc = Jsoup.parse(content);
        Elements resultsDiv = doc.select("div[class=result]");
        Set<Integer> idSet = null;
        if (resultsDiv != null && resultsDiv.size() > 0) {
            idSet = new HashSet<>();
            for (Element div : resultsDiv) {
                Element resultContentColumnDiv = div.select("div[class=resultcontentcolumn]").first();
                if (resultContentColumnDiv != null) {
                    Element strong = resultContentColumnDiv.select("strong").first();

                    // 判断是否来自Pixiv
                    if (strong != null && strong.text().contains("Pixiv")) {
                        idSet.add(Integer.parseInt(resultContentColumnDiv.select("a[class=linkify]").first().text()));
                    }
                }
            }
        }
        return idSet;
    }
}
