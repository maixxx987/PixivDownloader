package cn.max.pixiv.util.io;

import java.io.FileOutputStream;
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

        try (FileOutputStream out = new FileOutputStream(path)) {
            int index;
            byte[] bytes = new byte[1024 * 4];
            while ((index = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, index);
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
