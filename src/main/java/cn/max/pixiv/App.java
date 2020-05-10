package cn.max.pixiv;

import asg.cliche.ShellFactory;
import cn.max.pixiv.common.MenuConstant;
import cn.max.pixiv.menu.MainMenu;
import cn.max.pixiv.util.io.IOUtil;

import java.io.IOException;

/**
 * 程序启动
 *
 * @author MaxStar
 * @date 2020/4/26
 */
public class App {

    public static void main(String[] args) throws IOException {
        IOUtil.loadProperties();
        ShellFactory.createConsoleShell("主菜单", MenuConstant.MAIN_TITLE, new MainMenu())
                .commandLoop();
    }
}
