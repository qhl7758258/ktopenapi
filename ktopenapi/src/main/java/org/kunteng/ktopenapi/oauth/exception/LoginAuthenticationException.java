package org.kunteng.ktopenapi.oauth.exception;


import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 登录过程中的异常
 * hlqian
 */
public class LoginAuthenticationException extends InternalAuthenticationServiceException {
    public LoginAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public LoginAuthenticationException(String msg) {
        super(msg);
    }
}
