package com.base.util;

/**
 * 解析日期异常类。
 */
public class ParseDateException extends RuntimeException {
    private static final long serialVersionUID = 7907694920253292243L;

    public ParseDateException() {
    }

    public ParseDateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
