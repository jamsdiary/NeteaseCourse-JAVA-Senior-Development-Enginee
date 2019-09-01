package com.study.security.common.exception;

/**
 * @ClassName: BaseException
 * @Description: 基础异常工具类
 * @Author: 网易云课堂微专业 - java高级开发工程师
 * @Date: 2019/6/11 15:39
 * @Version: 1.0
 */
public class BaseException extends RuntimeException {
    private int status = 200;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BaseException() {
    }

    public BaseException(String message,int status) {
        super(message);
        this.status = status;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
