package cn.max.pixiv.util.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author MaxStar
 * @date 2019/5/17
 */
public class IOUtil {

    /**
     * inputStream转图片
     *
     * @param inputStream inputStream
     * @param path        download path
     */
    public static void inputStream2Picture(InputStream inputStream, String path) {
        try {
            FileUtils.copyToFile(inputStream, new File(path));
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
