package cn.max.pixiv.exception;

/**
 * 异常信息
 *
 * @author MaxStar
 * @date 2020/4/29
 */
public class PixivException extends Exception {

    private final ErrorCode errorCode;

    public PixivException() {
        super(PixivExceptionEnum.UNKNOWN_ERROR.getDescription());
        this.errorCode = PixivExceptionEnum.UNKNOWN_ERROR;
    }

    public PixivException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return errorCode.getDescription();
    }
}
