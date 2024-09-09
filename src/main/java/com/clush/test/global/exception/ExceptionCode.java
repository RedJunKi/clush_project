package com.clush.test.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    UNAUTHORIZED(401,"로그인 정보가 없습니다."),
    MEMBER_NOT_FOUND(404, "해당 아이디가 존재하지 않습니다."),
    MEMBER_DUPLICATE(404, "해당 아이디가 이미 존재합니다."),
    PASSWORD_MISMATCH(404, "비밀번호가 일치하지 않습니다."),
    INFORMATION_NOT_FOUND(404, "해당 정보가 존재하지 않습니다."),
    INVALID_TYPE(400, "유효하지 않은 형식입니다.");

    private int status;

    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}