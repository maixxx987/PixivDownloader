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
public class CrawlerDemo {


    public static void getPicNoLogin(long id) {
        // 创建get请求
        String picPage = Constant.THUMBNAIL_URL_PREFIX + id;
        String content = HttpUtil.getContent(picPage);
        if (content != null && !content.isBlank()) {
            // 获取原图连接
            String originSrc = JsoupHelper.getOriginURL(content, Constant.SINGLE_PIC_DIV_NOT_LOGIN);
            // 单图
            if (originSrc != null) {
                // 发送获取原图请求
                InputStream inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", picPage));
                // 使用 common-io 下载图片到本地，注意图片名不能重复
                IOUtil.inputStream2Picture(inputStream, ("G:\\test\\" + id + originSrc.substring(originSrc.lastIndexOf("."))));

            }
            // 多图
            else {
                originSrc = JsoupHelper.getOriginURL(content, Constant.MULTI_PIC_DIV_NOT_LOGIN);
                if (originSrc == null) {
                    originSrc = JsoupHelper.getOriginURL(content, Constant.SENSORED_PIC_DIV_NOT_LOGIN);
                }
                int i = 0;
                while (true) {

//                String currentUrl = originSrc;

                    // 发送获取原图请求
                    if (i != 0) {
                        // 每次p+1
                        originSrc = originSrc.replace("_p" + (i - 1), "_p" + i);
                    }

                    InputStream inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", picPage));

                    if (inputStream == null) {
                        break;
                    }

                    // 使用 common-io 下载图片到本地，注意图片名不能重复
                    IOUtil.inputStream2Picture(inputStream, ("G:\\test\\" + id + "_p" + i + originSrc.substring(originSrc.lastIndexOf("."))));

                    i++;
                }
            }

        }
    }
}

