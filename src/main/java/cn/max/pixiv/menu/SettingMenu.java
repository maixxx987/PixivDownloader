package cn.max.pixiv.menu;

import asg.cliche.Command;
import cn.max.pixiv.common.Constant;
import cn.max.pixiv.util.io.IOUtil;

/**
 * SauceNao菜单
 *
 * @author MaxStar
 * @date 2020/5/8
 */
public class SettingMenu {

    @Command(name = "1", description = "设置代理及端口")
    public void setProxy(String host, Integer port) {
        Constant.properties.setProxy(host, port);
        IOUtil.writeProperties();
    }

    @Command(name = "2", description = "设置下载路径")
    public void setDownloadPath(String downloadPath) {
        Constant.properties.setDownloadPath(downloadPath);
        IOUtil.writeProperties();
    }
}
