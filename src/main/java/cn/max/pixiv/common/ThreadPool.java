package cn.max.pixiv.common;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author MaxStar
 * @date 2020/4/11
 */
public class ThreadPool {

    /**
     * clean http thread pool
     */
    public static ScheduledExecutorService scheduledPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build());
}