package com.atguigu.crowd.mvc.interceptor;

/**
 * @author YUChangcan
 * @date 2022/12/22 - 22:19
 */
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public LoginAcctAlreadyInUseForUpdateException() {
        super();
    }

    public LoginAcctAlreadyInUseForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
