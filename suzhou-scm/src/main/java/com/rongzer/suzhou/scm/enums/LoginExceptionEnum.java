package com.rongzer.suzhou.scm.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tiancl
 * Date: 2022/1/6
 * Description: 登录异常枚举
 */
public enum  LoginExceptionEnum {
    GET_ACCESS_TOKEN_FAIL(101, "获取access_token失败"),

    VALID_PHONE_CODE(102, "获取手机号code不合法"),

    VALID_LOGIN_CODE(103, "登录code无效"),

    WX_SYSTEM_BUSY(104, "系统繁忙，此时请开发者稍候再试"),

    OVER_LIMIT(105, "频率限制，每个用户每分钟100次"),

    DANGER_USER(106, "高风险等级用户"),

    NO_SUCH_USER(107, "用户不存在"),

    CODE_BEEN_USED(108, "登录code已使用"),

    VALID_TOKEN(201, "token已失效"),

    USER_NOT_LOGIN(202, "用户未登录"),

    INCORRECT_USER_ROLE(203, "用户当前角色不正确"),

    UNKNOWN_ERROR(999, "未知异常");


    private int errorCode;

    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    LoginExceptionEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static LoginExceptionEnum get(String errorMsg) {
        for (LoginExceptionEnum exceptionEnum : LoginExceptionEnum.values()) {
            if (exceptionEnum.errorMsg.equals(errorMsg)) {
                return exceptionEnum;
            }
        }

        return null;
    }

}