package cn.max.pixiv;

import cn.max.pixiv.http.Crawler;

import java.util.Scanner;

/**
 * @author MaxStar
 * @date 2019/5/8
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("please input the picture id:");
        Scanner scanner = new Scanner(System.in);
        try {
            long id = scanner.nextLong();
            Crawler.getPic(id);
        } catch (Exception e) {
            System.out.println("请输入正确ID");
        }

//        Login login = new Login();
//
//        try {
//            login.getDriver();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        IOUtil.getUserProperties();

//        HttpUtil.getContent("https://www.baidu.com");
//        CrawlerDemo.getPicNoLogin(58733820);
        // 单图
//        CrawlerDemo.getPic(58733820);
        // R18
        Crawler.getPic(41466676);
//        new Thread(()->CrawlerDemo.getPic(58733820)).start();
//        new Thread(()->CrawlerDemo.getPic(63472776)).start();

        // 多图
//        CrawlerDemo.getPic(74751807);
//        CrawlerDemo.getPic(76138376);
//        CrawlerDemo.getPic(73355358);
//        CrawlerDemo.getPicNoLogin(74751807);

        // 敏感多图
//        CrawlerDemo.getPicNoLogin(73355358);
        System.exit(0);

    }
}
