package cn.max.pixiv.menu;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import cn.max.pixiv.common.MenuConstant;

import java.io.IOException;

/**
 * 主菜单
 * 1.sauceNAO菜单
 * 2.pixiv菜单
 * 3.设置菜单
 *
 * @author MaxStar
 * @date 2020/5/8
 */
public class MainMenu {

    @Command(name = "1", description = "打开下载菜单")
    public void pixivMenu() throws IOException {
        ShellFactory.createConsoleShell("pixiv", MenuConstant.PIXIV_TITLE, new PixivMenu())
                .commandLoop();
    }

    @Command(name = "2", description = "打开设置菜单")
    public void settingMenu() throws IOException {
        ShellFactory.createConsoleShell("setting", MenuConstant.SETTING_TITLE, new SettingMenu())
                .commandLoop();
    }
}
