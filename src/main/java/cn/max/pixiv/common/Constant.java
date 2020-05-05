package cn.max.pixiv.common;

/**
 * 常量
 *
 * @author MaxStar
 * @date 2019/5/16
 */
public class Constant {


    public static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
    public static final String ACCEPT_ENCODING = "gzip, deflate, br";
    public static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data; boundary=";

    /**
     * pixiv picture url
     */
    public static final String THUMBNAIL_URL_PREFIX = "https://www.pixiv.net/artworks/";

    /**
     * user url
     */
    public static final String USER_URL_PREFIX = "https://www.pixiv.net/users/";

    /**
     * sauceNAO url
     */
    public static final String SAUCENAO_URL = "https://saucenao.com";
    public static final String SAUCENAO_SEARCH_URL = "https://saucenao.com/search.php";

    /**
     * file RegEx
     */
    public static final String FILE_REG = "^([a-zA-Z]:)?\\\\.*\\.(JPEG|jpeg|JPG|jpg|PNG|png)$";
}
