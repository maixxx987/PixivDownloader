package cn.max.pixiv;

import cn.max.pixiv.common.ThreadPool;
import cn.max.pixiv.crawler.CrawlerThread;
import cn.max.pixiv.entity.Image;
import cn.max.pixiv.util.http.HttpUtil;
import cn.max.pixiv.util.jsoup.JsoupHelper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author MaxStar
 * @date 2020/4/10
 */
public class TestCrawler {

    @Test
    public void testSinglePic() throws InterruptedException {
        ThreadPool.crawlerPool.execute(new CrawlerThread(new Image(58733820)));
        ThreadPool.crawlerPool.execute(new CrawlerThread(new Image(80568514)));
        ThreadPool.crawlerPool.execute(new CrawlerThread(new Image(41466676)));

        ThreadPool.crawlerPool.shutdown();
        try {
            boolean loop = true;
            do {
                //阻塞，直到线程池里所有任务结束
                loop = !ThreadPool.crawlerPool.awaitTermination(2, TimeUnit.SECONDS);
            } while (loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMultiPic() throws InterruptedException {
        ThreadPool.crawlerPool.execute(new CrawlerThread(new Image(74751807)));
        ThreadPool.crawlerPool.execute(new CrawlerThread(new Image(76138376)));
        ThreadPool.crawlerPool.execute(new CrawlerThread(new Image(73355358)));

        ThreadPool.crawlerPool.shutdown();
        try {
            boolean loop = true;
            do {
                //阻塞，直到线程池里所有任务结束
                loop = !ThreadPool.crawlerPool.awaitTermination(2, TimeUnit.SECONDS);
            } while (loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUploadFile() throws IOException {
        String str = HttpUtil.uploadFile("https://saucenao.com/search.php", "src/test/resources/example.jpg");
        List<Image> images = JsoupHelper.parseSauceNAO(str);
        if (images != null && images.size() > 0) {
            Image image = images.get(0);
            ThreadPool.crawlerPool.execute(new CrawlerThread(image));
        }

        ThreadPool.crawlerPool.shutdown();
        try {
            boolean loop = true;
            do {
                //阻塞，直到线程池里所有任务结束
                loop = !ThreadPool.crawlerPool.awaitTermination(2, TimeUnit.SECONDS);
            } while (loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParaseSauceNao() throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/cn/max/pixiv/example/sauge.html")));) {
            String str;
            while ((str = br.readLine()) != null) {
                result.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsoupHelper.parseSauceNAO(result.toString());

    }

}
