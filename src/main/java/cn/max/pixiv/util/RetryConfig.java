package cn.max.pixiv.util;

import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * @author MaxStar
 * @date 2020/4/13
 */
public class RetryConfig {

    /**
     * 重试间隔
     */
    private static long fixedPeriodTime = 1000L;


    /**
     * 重试次数
     */
    private static int maxRetryTimes = 3;

    /**
     * 需重试的map
     */
    private static Map<Class<? extends Throwable>, Boolean> exceptionMap = Map.of(IOException.class, true);

    public static FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
    public static SimpleRetryPolicy retryPolicy  = new SimpleRetryPolicy(maxRetryTimes, exceptionMap);

    static {
        backOffPolicy.setBackOffPeriod(fixedPeriodTime);
    }


    public static void setRetryTemplateConfig(RetryTemplate retryTemplate) {
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
    }
}
