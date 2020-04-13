package cn.max.pixiv;

import cn.max.pixiv.common.ThreadPool;
import cn.max.pixiv.crawler.CrawlerThread;
import cn.max.pixiv.entity.Image;
import org.junit.jupiter.api.Test;

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

}
