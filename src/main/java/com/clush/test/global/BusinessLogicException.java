package com.clush.test.global;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public BusinessLogicException(Throwable cause, ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage() ,cause);
        this.exceptionCode = exceptionCode;
    }

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
