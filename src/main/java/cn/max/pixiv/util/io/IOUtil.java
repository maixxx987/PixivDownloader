package cn.max.pixiv.util.io;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.TreeSet;
import java.util.zip.ZipFile;

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

    /**
     * 删除文件
     *
     * @param path
     * @throws IOException
     */
    public static void deleteFile(Path path) throws IOException {
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                Files.walkFileTree(path, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }

                });
            } else {
                Files.delete(path);
            }
        }
    }


    /**
     * 将zip中的jpg合成为gif
     *
     * @param zipPathStr 压缩包路径
     * @param desPathStr 解压路径
     * @throws IOException
     */
    public static void unZip(String zipPathStr, String desPathStr) throws IOException {
        Path zipPath = Path.of(zipPathStr);
        if (Files.exists(zipPath)) {
            FileSystem fs = FileSystems.newFileSystem(zipPath, null);
            Path desPath = Path.of(desPathStr);
            Files.createDirectory(desPath);

            // 遍历压缩文件
            Files.walkFileTree(fs.getPath(File.separator), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    //   file /xxxx ，去掉第一个/既是文件名
                    Files.copy(file, desPath.resolve(file.toString().substring(1)));
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    /**
     * 将jpg文件合成为gif
     *
     * @param jpgDirPath jpg文件夹
     * @param gifDes     输出路径
     * @param jsonArray  pixiv获取的json数组
     */
    public static void jpg2Gif(String jpgDirPath, String gifDes, JSONArray jsonArray) throws IOException {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.start(gifDes);
        e.setRepeat(0);
        for (Object frame : jsonArray) {
            JSONObject frameJson = (JSONObject) frame;
            BufferedImage img = ImageIO.read(Path.of(jpgDirPath + File.separator + frameJson.getString("file")).toFile());
            e.setDelay(frameJson.getInteger("delay"));
            e.addFrame(img);
        }
        e.finish();
    }
}
