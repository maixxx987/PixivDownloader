package cn.max.pixiv;

import cn.max.pixiv.http.Crawler;
import org.junit.jupiter.api.Test;

/**
 * @author MaxStar
 * @date 2020/4/10
 */
public class TestCrawler {

    @Test
    public void testSinglePic() {
        Crawler.getPic(58733820);

        // R18
        Crawler.getPic(41466676);
    }

    @Test
    public void testMultiPic() {

        Crawler.getPic(74751807);
        Crawler.getPic(76138376);
        Crawler.getPic(73355358);
    }
}
