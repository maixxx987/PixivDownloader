package cn.max.pixiv.aop;

import cn.max.pixiv.entity.PixivImage;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 输出信息AOP
 *
 * @author MaxStar
 * @date 2020/4/27
 */

@Aspect
public class DisplayMessageAop {

    @Before(value = "execution(* cn.max.pixiv.crawler.CrawlerTask.sauceNaoTask(..))")
    public void beforeSauceNao() {
        System.out.println("*   开始查询图片地址...");
    }

    @Before(value = "execution(* cn.max.pixiv.crawler.CrawlerTask.parse(..))")
    public void beforeParse() {
//        System.out.println("已查询到图片");
        System.out.println("*   开始解析原图地址...");
    }

    /**
     * 输出下载图片的信息
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(* cn.max.pixiv.crawler.CrawlerTask.download(..))")
    public void aroundDownload(ProceedingJoinPoint joinPoint) throws Throwable {
        PixivImage image = (PixivImage) joinPoint.getArgs()[0];
        System.out.println("************************************************");
        System.out.println("*   图片ID：" + image.getImgId());
        System.out.println("*   图片名：" + image.getImgTitle());
        System.out.println("*   图片页面连接：" + image.getImgUrl());
        System.out.println("*   图片简介：" + image.getImgDescription());
        System.out.println("*   图片Tag：" + JSON.toJSONString(image.getTagList()));
        System.out.println("*   作者ID：" + image.getArtistId());
        System.out.println("*   作者名：" + image.getArtistName());
        System.out.println("*   作者主页连接：" + image.getArtistUrl());
        System.out.println("************************************************");
        System.out.println("*   开始下载...");
        joinPoint.proceed();
        System.out.println("*   下载完成");
        System.out.println("************************************************");
    }
}
