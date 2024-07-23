package com.rongzer.suzhou.scm.exception;

/**
 * Created with IntelliJ IDEA.
 * User: tiancl
 * Date: 2022/1/6
 * Description: 登录相关异常
 */
public class LoginException extends RuntimeException {

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}