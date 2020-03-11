package cn.max.pixiv.util.http;

/**
 * @author MaxStar
 * @date 2019/5/16
 */
public class HttpConfig {

    /**
     * 连接超时
     */
    public static final int CONNECTION_TIME_OUT = 10 * 1000;

    /**
     * 返回超时
     */
    public static final int SOCKET_TIME_OUT = 10 * 1000;

    /**
     * 从连接池获取连接超时
     */
    public static final int GET_CONNECTION_TIME_OUT = 1 * 1000;


    /**
     * 连接空闲时间
     */
    public static final long IDLE = 5 * 1000;


    /**
     * 检测线程启动延时
     */
    public static final long INITIAL_DELAY = 5 * 1000;

    /**
     * 检测线程执行间隔
     */
    public static final long PERIOD = 5 * 1000;

}
