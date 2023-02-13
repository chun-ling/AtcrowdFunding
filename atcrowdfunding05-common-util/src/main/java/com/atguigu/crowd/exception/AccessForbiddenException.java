package com.atguigu.crowd.exception;

/**
 * @author YUChangcan
 * @date 2022/12/22 - 9:35
 * 表示用户没用登录就访问受保护的资源
 */
public class AccessForbiddenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public AccessForbiddenException() {
        super();
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    protected AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
