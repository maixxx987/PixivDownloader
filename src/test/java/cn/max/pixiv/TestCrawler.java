package cn.max.pixiv;

import cn.max.pixiv.crawler.CrawlerTask;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author MaxStar
 * @date 2020/4/10
 */
public class TestCrawler {

    private static CrawlerTask task;

    @BeforeAll
    static void setUp() {
        task = new CrawlerTask();
    }

    @Test
    public void testDownloadSingle() {
        task.pixivTask(58805869);
        task.pixivTask(81046824);
        task.pixivTask(81187455);
    }

    @Test
    public void testDownloadMulti() {
        task.pixivTask(63182383);
        task.pixivTask(80395573);
    }

    @Test
    public void testDownloadGif() {
        task.pixivTask(81270154);
        task.pixivTask(81272435);
        task.pixivTask(81281335);
    }

//    @AfterAll
//    static void tearDown() {
//        IOUtil.deleteFile();
//    }
}
