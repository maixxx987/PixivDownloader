package cn.max.pixiv.exception;

/**
 * 异常错误码
 *
 * @author MaxStar
 * @date 2020/4/29
 */
public enum PixivExceptionEnum implements ErrorCode {

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(101, "未知异常"),

    /**
     * 未找到图片
     */
    NOT_FOUND(201, "未找到此图"),

    /**
     * 未找到原图
     */
    NOT_FOUND_ORIGIN(202, "未找到原图地址"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(301, "文件不存在"),

    /**
     * 网络错误
     */
    NETWORK_ERROR(401, "网络异常");


    private final Integer code;
    private final String description;

    PixivExceptionEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
