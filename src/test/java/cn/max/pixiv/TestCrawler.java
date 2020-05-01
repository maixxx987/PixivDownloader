package cn.max.pixiv;

import cn.max.pixiv.common.Constant;
import cn.max.pixiv.util.jsoup.JsoupHelper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author MaxStar
 * @date 2020/4/10
 */
public class TestCrawler {

    @Test
    public void testParaseSauceNao() throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/test/resource/sauge.html")));) {
            String str;
            while ((str = br.readLine()) != null) {
                result.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsoupHelper.parseSauceNAO(result.toString());

    }

    @Test
    public void testReg(){
        String testCase1 = "c:\\a.jpg";
        String testCase2 = "d:\\sabsds\\a.png";
        String testCase3 = "d:\\sabsds\\中文路径\\a.JPEG";
        String testCase4 = "\\sabsds\\中文路径\\a.PNG";


        String testCase5 = "a.PNG";
        String testCase6 = "sabsds\\a.PNG";
        String testCase7 = "\\sabsds\\a.exe";


        assertTrue(Pattern.matches(Constant.FILE_REG,testCase1));
        assertTrue(Pattern.matches(Constant.FILE_REG,testCase2));
        assertTrue(Pattern.matches(Constant.FILE_REG,testCase3));
        assertTrue(Pattern.matches(Constant.FILE_REG,testCase4));
        assertFalse(Pattern.matches(Constant.FILE_REG,testCase5));
        assertFalse(Pattern.matches(Constant.FILE_REG,testCase6));
        assertFalse(Pattern.matches(Constant.FILE_REG,testCase7));

    }

}
