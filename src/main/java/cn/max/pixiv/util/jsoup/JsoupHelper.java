package cn.max.pixiv.util.jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author MaxStar
 * @date 2019/5/16
 */
public class JsoupHelper {

    /**
     * parse origin picture url from content by Jsoup
     *
     * eg.
     * page src: https://www.pixiv.net/artworks/63472776
     * origin:   https://i.pximg.net/img-original/img/2017/06/20/01/14/35/63472776_p0.png
     *
     * @param content page source
     * @param id      picture ID
     * @return
     */
    public static String getOriginUrl(String content, String id) {
        Document doc = Jsoup.parse(content);

        // origin url json string
        String preLoadDataJsonStr = doc.select("meta[name=preload-data]").get(0).attr("content");

        JSONObject preLoadDataJsonObject = JSON.parseObject(preLoadDataJsonStr);

        // illust -> urls -> original
        String originSrc = preLoadDataJsonObject.getJSONObject("illust").getJSONObject(id).getJSONObject("urls").getString("original");
        System.out.println(originSrc);
        return originSrc;
    }
}
