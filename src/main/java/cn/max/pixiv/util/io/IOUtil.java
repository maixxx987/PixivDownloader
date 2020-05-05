package cn.max.pixiv.util.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public static void createFile(Path path)  {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(Path path) throws IOException {
        Files.deleteIfExists(path);
    }
}
