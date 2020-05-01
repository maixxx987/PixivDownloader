package cn.max.pixiv.exception;

/**
 * 错误码接口
 *
 * @author MaxStar
 * @date 2020/4/29
 */
public interface ErrorCode {

    /**
     * 获取错误码
     *
     * @return
     */
    Integer getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getDescription();
}

