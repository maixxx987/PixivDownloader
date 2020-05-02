package cn.max.pixiv.crawler;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.entity.PixivImage;
import cn.max.pixiv.exception.PixivException;
import cn.max.pixiv.exception.PixivExceptionEnum;
import cn.max.pixiv.util.http.HttpUtil;
import cn.max.pixiv.util.io.IOUtil;
import cn.max.pixiv.util.jsoup.JsoupHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * 爬虫具体任务
 *
 * @author MaxStar
 * @date 2020/4/13
 */
public class CrawlerTask {

    public Set<Integer> sauceNaoTask(String filePath) {
        try {
            return getPicsFromSauceNao(filePath);
        } catch (PixivException | IOException ignored) {
            return null;
        }
    }

    public void pixivTask(Integer id) {
        pixivTask(id, null);
    }

    public void pixivTask(PixivImage image) {
        pixivTask(null, image);
    }

    public void pixivTask(Integer id, PixivImage image) {
        try {
            if (image == null) {
                image = parse(id);
            }
            download(image);
        } catch (PixivException | IOException ignored) {
        }
    }

    /**
     * 在SauceNao中以图搜图
     *
     * @param filePath 待搜的文件路径
     * @return
     * @throws IOException
     */
    private Set<Integer> getPicsFromSauceNao(String filePath) throws PixivException, IOException {
        if (new File(filePath).exists()) {
            String str = HttpUtil.uploadFile(Constant.SAUCENAO_URL, filePath);
            Set<Integer> idSet = JsoupHelper.parseSauceNAO(str);
            if (idSet == null || idSet.size() == 0) {
                throw new PixivException(PixivExceptionEnum.NOT_FOUND);
            }
            return idSet;
        } else {
            throw new PixivException(PixivExceptionEnum.FILE_NOT_FOUND);
        }
    }

    /**
     * 通过图片Id获取页面并解析
     *
     * @param id 图片ID
     * @return 图片
     * @throws IOException
     */
    public PixivImage parse(Integer id) throws PixivException {
        String url = Constant.THUMBNAIL_URL_PREFIX + id;
        PixivImage image = new PixivImage(id);
        image.setImgUrl(url);

        String content = null;
        try {
            content = HttpUtil.getContent(url);
        } catch (IOException e) {
            throw new PixivException(PixivExceptionEnum.NETWORK_ERROR);
        }

        if (content != null && !content.isEmpty()) {
            JsoupHelper.parseImgInfo(content, image);
        }

        if (image.getImgOriginUrl() == null || image.getImgOriginUrl().isEmpty()) {
            throw new PixivException(PixivExceptionEnum.NOT_FOUND_ORIGIN);
        }


        return image;
    }

    /**
     * 下载图片
     *
     * @param pixivImage
     * @throws IOException
     */
    private void download(PixivImage pixivImage) throws PixivException, IOException {
        long id = pixivImage.getImgId();
        String originSrc = pixivImage.getImgOriginUrl();
        int currNum = pixivImage.getCurrNum();

        int suffixIndex = originSrc.lastIndexOf(".");
        String suffix = originSrc.substring(suffixIndex);
        int _pIndex = originSrc.lastIndexOf("_p");
        String prefix = originSrc.substring(0, _pIndex);

        // 如果不是首图，则要重新拼接url
        if (currNum != 0) {
            originSrc = prefix + "_p" + currNum + suffix;
        }

        InputStream inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", pixivImage.getImgUrl()));

        while (inputStream != null) {
            IOUtil.inputStream2Picture(inputStream, (System.getProperty("user.dir") + "\\" + id + "_p" + currNum + suffix));

            // get next picture inputStream
            currNum += 1;
            pixivImage.setCurrNum(currNum);
            originSrc = prefix + "_p" + currNum + suffix;

            // download picture
            inputStream = HttpUtil.getInputStream(originSrc, Map.of("Referer", pixivImage.getImgUrl()));
        }
    }
}
