package com.puputuan.common.enums;

/**
 * Created by chenzhuobin on 2017/5/23.
 */
public enum ExceptionMessage {

    NoSuchMethodException("NoSuchMethod error"),
    IllegalAccessException("IllegalAccess error"),
    InvocationTargetException("InvocationTarget error"),
    UnknownAccountException("UnknownAccount error"),
    IncorrectCredentialsException("IncorrectCredentials error"),
    LockedAccountException("LockedAccount error"),
    IllegalArgumentException("IllegalArgument error"),
    ExcessiveAttemptsException("ExcessiveAttempts error"),
    Exception("error")

    ;

    private String message;

    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
