package cn.max.pixiv.aop;

import cn.max.pixiv.exception.PixivException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import java.io.IOException;

/**
 * 异常处理AOP
 *
 * @author MaxStar
 * @date 2020/4/29
 */
@Aspect
public class ExceptionAop {

    @AfterThrowing(pointcut = "execution(* cn.max.pixiv.crawler.CrawlerTask.*(..))", throwing = "exception")
    public void pixivExceptionHandler(Exception exception) {
        if (exception instanceof PixivException) {
            System.out.println("查询图片错误：" + ((PixivException) exception).getDescription());
        } else {
            System.out.println("发生错误：" + exception.getMessage());
        }
    }
}
