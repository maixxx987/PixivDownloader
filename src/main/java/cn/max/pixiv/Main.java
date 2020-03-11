package cn.max.pixiv;

import cn.max.pixiv.http.CrawlerDemo;

/**
 * @author MaxStar
 * @date 2019/5/8
 */
public class Main {

    public static void main(String[] args) {

        // 单图
        new Thread(()->CrawlerDemo.getPicNoLogin(58733820)).start();

        // 多图
        new Thread(()->CrawlerDemo.getPicNoLogin(74751807)).start();
//        CrawlerDemo.getPicNoLogin(74751807);

        // 敏感多图
        CrawlerDemo.getPicNoLogin(73355358);
        System.exit(0);

    }
}
