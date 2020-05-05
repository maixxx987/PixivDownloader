package cn.max.pixiv;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.crawler.CrawlerTask;
import cn.max.pixiv.util.http.HttpClientConfig;

import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 菜单
 *
 * @author MaxStar
 * @date 2020/4/26
 */
public class SimpleMenu {

    public void start() {

        System.out.println("pixiv 下载助手");
        System.out.println("==========================");

        boolean notQuit = true;

        while (notQuit) {
            System.out.println("请输入以下数字+回车执行对应功能");
            System.out.println("1.通过SauceNAO搜图并下载");
            System.out.println("2.根据图片ID下载图片");
            System.out.println("3.设置代理");
            System.out.println("0.退出");
            System.out.println();

            Scanner sc = new Scanner(System.in);

            if (sc.hasNextInt()) {
                int i = sc.nextInt();
                switch (i) {
                    case 1:
                        sauceNAOMenu(sc);
                        break;
                    case 2:
                        pixivMenu(sc);
                        break;
                    case 3:
                        proxyMenu(sc);
                        break;
                    case 0:
                        notQuit = false;
                        break;
                    default:
                        System.out.println("输入错误数字,请重新输入");
                        break;
                }
            } else {
                System.out.println("输入错误,请重新输入");
            }

        }
    }


    /**
     * SauceNAO菜单
     *
     * @param sc
     */
    private void sauceNAOMenu(Scanner sc) {
        System.out.println("请输入文件的绝对路径(或者将文件直接拖入窗口中),输入0返回");

        while (true) {
            if (sc.hasNext()) {
                String filePath = sc.next().trim();
                if ("0".equals(filePath)) {
                    break;
                }

                if (Pattern.matches(Constant.FILE_REG, filePath)) {
                    CrawlerTask task = new CrawlerTask();
                    Set<Integer> idSet = task.sauceNaoTask(filePath);
                    if (idSet != null && !idSet.isEmpty()) {
                        for (Integer id : idSet) {
                            task.pixivTask(id);
                        }
                    }

                    break;
                } else {
                    sc.nextLine();
                    System.out.println("文件路径输入有误,请重新输入");
                }

            }
        }
    }

    /**
     * pixiv菜单
     *
     * @param sc
     */
    private void pixivMenu(Scanner sc) {
        System.out.println("请输入图片ID,输入0返回");
        while (true) {
            if (sc.hasNextInt()) {
                int input = sc.nextInt();
                if (input != 0) {
                    CrawlerTask task = new CrawlerTask();
                    task.pixivTask(input);
                }
                break;
            } else {
                sc.next();
                System.out.println("输入错误,请重新输入");
            }
        }
    }

    /**
     * 设置代理
     *
     * @param sc
     */
    private void proxyMenu(Scanner sc) {
        System.out.println("请输入代理IP,如127.0.0.1,输入0返回");
        while (true) {
            if (sc.hasNext()) {
                String host = sc.next().trim();
                if ("0".equals(host)) {
                    break;
                }
                System.out.println("请输入端口");
                if (sc.hasNextInt()) {
                    int port = sc.nextInt();
                    HttpClientConfig.setProxy(host, port);
                    break;
                }
            }
        }
    }
}
