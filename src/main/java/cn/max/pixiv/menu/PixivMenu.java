package cn.max.pixiv.menu;

import asg.cliche.Command;
import cn.max.pixiv.common.Constant;
import cn.max.pixiv.crawler.CrawlerTask;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * SauceNao菜单
 *
 * @author MaxStar
 * @date 2020/5/8
 */
public class PixivMenu {

    @Command(name = "1", description = "本地图片通过SauceNAO找原图")
    public void getId(String filePath) {
        if (Pattern.matches(Constant.FILE_REG, filePath)) {
            CrawlerTask task = new CrawlerTask();
            Set<Integer> idSet = task.sauceNaoTask(filePath);
            if (idSet != null && !idSet.isEmpty()) {
                for (Integer id : idSet) {
                    task.pixivTask(id);
                }
            }
        } else {
            System.out.println("路径输入错误，请重新输入");
        }
    }

    @Command(name = "2", description = "通过ID下载原图")
    public void getId(Integer id) {
        CrawlerTask task = new CrawlerTask();
        task.pixivTask(id);
    }
}
