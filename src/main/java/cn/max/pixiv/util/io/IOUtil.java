package cn.max.pixiv.util.io;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.entity.properties.Properties;
import cn.max.pixiv.entity.properties.Proxy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

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
     * 删除文件(包含文件夹)
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

    /**
     * 编写配置文件
     *
     * @throws IOException
     */
    public static void writeProperties() {
        Path path = Path.of(System.getProperty("user.dir") + File.separator + "properties.json");
        try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), "rw");) {
            if (Files.notExists(path)) {
                Files.createFile(path);
            }

            Path downloadPath = Path.of(Constant.properties.getDownloadPath());
            if (Files.notExists(downloadPath)) {
                Files.createDirectory(downloadPath);
            }
            if (!Constant.properties.getDownloadPath().endsWith(File.separator)) {
                Constant.properties.setDownloadPath(Constant.properties.getDownloadPath() + File.separator);
            }

            String jsonStr = Constant.properties.toJsonString();
            raf.setLength(jsonStr.length());
            raf.write(jsonStr.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("写入失败");
        }
    }

    /**
     * 加载配置文件，若配置文件不存在则创建配置文件
     */
    public static void loadProperties() {
        Path path = Path.of(System.getProperty("user.dir") + File.separator + "properties.json");
        Constant.properties = new Properties();
        if (Files.exists(path)) {
            File file = path.toFile();
            try (RandomAccessFile raf = new RandomAccessFile(file, "r");) {
                byte[] bytes = new byte[(int) file.length()];
                raf.read(bytes);
                System.out.println(new String(bytes));
                JSONObject jsonObject = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8));
                Constant.properties.setDownloadPath(jsonObject.getString("downloadPath"));
                Constant.properties.setProxy(JSON.parseObject(jsonObject.getString("proxy"), Proxy.class));
            } catch (IOException e) {
                System.out.println("读取配置文件失败：" + e.getMessage());
            }
        } else {
            writeProperties();
        }
    }
}
