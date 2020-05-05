package cn.max.pixiv.common;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author MaxStar
 * @date 2020/4/11
 */
public class ThreadPool {

    /**
     * clean http thread pool
     */
//    public static ScheduledExecutorService scheduledPool = new ScheduledThreadPoolExecutor(1,
//            new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build());

    /**
     * crawler pool
     */
    public static ExecutorService httpPool = new ThreadPoolExecutor(3,
            10,
            5 * 1000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new BasicThreadFactory.Builder().namingPattern("http-pool-%d").daemon(true).build());
}