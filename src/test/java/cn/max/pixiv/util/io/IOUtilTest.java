package cn.max.pixiv.util.io;

import cn.max.pixiv.common.Constant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author MaxStar
 * @date 2020/5/6
 */
class IOUtilTest {

    @Test
    void testLoadPropertiesFile() throws IOException {

        IOUtil.loadProperties();
    }

    @Test
    void testSetPropertiesFile() throws IOException {
        IOUtil.loadProperties();
//        Constant.properties = new Properties(System.getProperty("user.dir") + "\\download", "", -1);
        Constant.properties.setDownloadPath(System.getProperty("user.dir") + "\\download\\aaaa\\test\\test");
        Constant.properties.setProxy("127.1.1.1", 2222);
        IOUtil.writeProperties();
    }

    @Test
    void testFile() throws IOException {
        String filePath = System.getProperty("user.dir") + File.separator + "download" + File.separator + "test" ;
        System.out.println(filePath);
//        System.out.println(filePath.endsWith(File.separator));
        if (!filePath.endsWith(File.separator)){
            filePath += File.separator;
        }

        System.out.println(filePath);
        System.out.println(filePath.endsWith(File.separator));
//        Files.createDirectories(Path.of(System.getProperty("user.dir") + File.separator + "download" + File.separator + "test" + File.separator));

    }
}