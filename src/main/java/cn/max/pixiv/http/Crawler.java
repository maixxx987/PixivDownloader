package cn.max.pixiv.http;

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
public class Crawler {

    /**
     * getContent -> Jsoup解析 -> 获取原图url -> 获取原图流 -> 转为文件
     *
     * @param id picture Id
     */
    public static void getPic(long id) {
        // 创建get请求
        String picPage = Constant.THUMBNAIL_URL_PREFIX + id;
        String content = HttpUtil.getContent(picPage);
        if (content != null && !content.isBlank()) {
            // 获取原图连接
            String originSrc = JsoupHelper.getOriginUrl(content, String.valueOf(id));
            // 单图
            if (originSrc != null) {
                // 发送获取原图请求
                InputStream inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", picPage));
                int currNum = 0;

                // 后缀
                int suffixIndex = originSrc.lastIndexOf(".");
                String suffix = originSrc.substring(suffixIndex);

                // _p0.jpg (0代表当前url的图片序号)
                int _pIndex = originSrc.lastIndexOf("_p");
                String _p = originSrc.substring(originSrc.lastIndexOf("_"));

                // 前缀
                String prefix = originSrc.substring(0, _pIndex);

                while (inputStream != null) {

                    // 使用 common-io 下载图片到本地，注意图片名不能重复
                    IOUtil.inputStream2Picture(inputStream, ("G:\\test\\" + id + "_p" + currNum + suffix));
                    System.out.println("文件名： " + id + "_p" + currNum + suffix);


                    // 获取下一张图的连接(将_p后的数字+1)
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

