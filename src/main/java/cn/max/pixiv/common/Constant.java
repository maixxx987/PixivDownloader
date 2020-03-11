package cn.max.pixiv.common;

/**
 * @author MaxStar
 * @date 2019/5/16
 */
public class Constant {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";

    /**
     * 缩略图URL前缀
     */
    public static final String THUMBNAIL_URL_PREFIX = "https://www.pixiv.net/member_illust.php?mode=medium&illust_id=";
//    public static final String REFER_URL_PREFIX = "https://www.pixiv.net/member_illust.php?mode=medium&illust_id";

    /**
     * 图片div的class名
     */
    public static final String SINGLE_PIC_DIV_NOT_LOGIN = "require-register medium-image  _work ";
    public static final String MULTI_PIC_DIV_NOT_LOGIN = "medium-image  _work multiple ";
    public static final String SENSORED_PIC_DIV_NOT_LOGIN = "sensored";

    /**
     * 原图连接前缀
     */
    public static final String ORIGIN_URL_PREFIX = "https://i.pximg.net/img-original";

}
