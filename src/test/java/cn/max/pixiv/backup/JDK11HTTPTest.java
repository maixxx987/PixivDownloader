//package cn.max.pixiv.backup;
//
//import cn.max.pixiv.common.Constant;
//import cn.max.pixiv.backup.HttpUtil;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @author MaxStar
// * @date 2020/5/3
// */
//class JDK11HTTPTest {
//
//    @Test
//    void demoJdk11HttpClient() throws IOException, InterruptedException {
//        JDK11HTTP.demoJdk11HttpClient(81046824);
//    }
//
//    @Test
//    void ApacheHttp() throws IOException, InterruptedException {
//        HttpUtil.httpGet(Constant.THUMBNAIL_URL_PREFIX + 81046824, null);
//    }
//}