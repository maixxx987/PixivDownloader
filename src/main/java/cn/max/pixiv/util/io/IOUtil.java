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
     * inputStream 转为图片，储存在本地
     *
     * @param inputStream inputStream
     * @param path        存储路径
     */
    public static void inputStream2Picture(InputStream inputStream, String path) {
        try {
            FileUtils.copyToFile(inputStream, new File(path));
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("input stream to picture error");
        }
    }
}
