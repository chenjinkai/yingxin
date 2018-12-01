package com.yingxin.tec.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 6827115145993212875L;

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }
}
