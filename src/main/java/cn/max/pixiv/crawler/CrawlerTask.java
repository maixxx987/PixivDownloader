package cn.max.pixiv.crawler;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.entity.PixivImage;
import cn.max.pixiv.exception.PixivException;
import cn.max.pixiv.exception.PixivExceptionEnum;
import cn.max.pixiv.util.http.HttpUtil;
import cn.max.pixiv.util.io.IOUtil;
import cn.max.pixiv.util.jsoup.JsoupHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        } catch (PixivException | IOException | InterruptedException ignored) {
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
        } catch (PixivException | IOException | InterruptedException ignored) {
        }
    }

    /**
     * 在SauceNao中以图搜图
     *
     * @param filePath 待搜的文件路径
     * @return
     * @throws IOException
     */
    private Set<Integer> getPicsFromSauceNao(String filePath) throws PixivException, IOException, InterruptedException {
        if (Files.exists(Path.of(filePath))) {
            String str = HttpUtil.uploadImg2SauceNAO(filePath);
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
        String url = Constant.IMG_THUMBNAIL_URL_PREFIX + id;
        PixivImage image = new PixivImage(id);
        image.setImgUrl(url);

        String content = null;
        try {
            content = HttpUtil.httpGet(url, null);
        } catch (IOException | InterruptedException e) {
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
    private void download(PixivImage pixivImage) throws IOException, InterruptedException {
        long id = pixivImage.getImgId();
        String originSrc = pixivImage.getImgOriginUrl();
        int pageCount = pixivImage.getPageCount();
        int currNum = pixivImage.getCurrNum();

        int suffixIndex = originSrc.lastIndexOf(".");
        String suffix = originSrc.substring(suffixIndex);
        int _pIndex = originSrc.lastIndexOf("_p");

        // 如果url中没有_p，则是动图
        if (_pIndex != -1) {
            String prefix = originSrc.substring(0, _pIndex);

            // 如果不是首图，则要重新拼接url
            if (currNum != 0) {
                originSrc = prefix + "_p" + currNum + suffix;
            }

            while (currNum < pageCount) {
                HttpUtil.download(originSrc, Map.of("Referer", pixivImage.getImgUrl()), Constant.properties.getDownloadPath(), id + "_p" + currNum + suffix);

                currNum += 1;
                pixivImage.setCurrNum(currNum);
                originSrc = prefix + "_p" + currNum + suffix;
            }
        } else {
            downloadGif(pixivImage);
        }
    }

    /**
     * 下载GIF
     *
     * @param pixivImage
     * @throws IOException
     * @throws InterruptedException
     */
    private void downloadGif(PixivImage pixivImage) throws IOException, InterruptedException {
        String gifInfoBody = HttpUtil.httpGet(Constant.GIF_URL_PREFIX + pixivImage.getImgId() + Constant.GIF_URL_SUFFIX, null);

        if (gifInfoBody != null && !gifInfoBody.isBlank()) {
            JSONObject gifJson = JSON.parseObject(gifInfoBody).getJSONObject("body");
            String originSrc = gifJson.getString("src").replace("\\", "");
            int id = pixivImage.getImgId();
            pixivImage.setImgOriginUrl(originSrc);
            String zipFilePathOutStr = Constant.properties.getDownloadPath() + File.separator + "tempZip_" + id;
            String zipFilePathStr = zipFilePathOutStr + ".zip";

            HttpUtil.download(originSrc, Map.of("Referer", pixivImage.getImgUrl()), Constant.properties.getDownloadPath(), "tempZip_" + id + ".zip");

            JSONArray framesArray = gifJson.getJSONArray("frames");

            IOUtil.unZip(zipFilePathStr, zipFilePathOutStr);
            IOUtil.jpg2Gif(zipFilePathOutStr, zipFilePathOutStr + ".gif", framesArray);

            IOUtil.deleteFile(Path.of(zipFilePathOutStr));
            IOUtil.deleteFile(Path.of(zipFilePathStr));
        }
    }
}
