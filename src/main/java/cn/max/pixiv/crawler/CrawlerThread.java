package cn.max.pixiv.crawler;

import cn.max.pixiv.entity.Image;
import cn.max.pixiv.util.RetryConfig;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;

/**
 * @author MaxStar
 * @date 2020/4/13
 */
public class CrawlerThread implements Runnable {

    private Image image;

    public CrawlerThread(Image image) {
        this.image = image;
    }


    @Override
    public void run() {
        RetryTemplate retryTemplate = new RetryTemplate();
        RetryConfig.setRetryTemplateConfig(retryTemplate);

        try {
            retryTemplate.execute(
                    retryContext -> {
                        CrawlerTask.task(image);
                        return true;
                    },
                    retryContext -> false
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
