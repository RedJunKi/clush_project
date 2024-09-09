package com.clush.test.global.advice;

import com.clush.test.global.exception.BusinessLogicException;
import com.clush.test.global.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice("com.clush.test.domain")
public class ExControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleBusinessLogicException(BusinessLogicException e) {
        Map<String, Object> result = new HashMap<>();
        log.debug("# BusinessLogicException: {}-{}", e.getExceptionCode(), e.getMessage());
        result.put("status", e.getExceptionCode().getStatus());
        result.put("message", e.getExceptionCode().getMessage());

        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ExceptionCode.INVALID_TYPE.getStatus());
        response.put("message", ExceptionCode.INVALID_TYPE.getMessage());

        log.debug("# MethodArgumentNotValidException: {}", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}