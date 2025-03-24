package com.example.scheduler.exception;

import com.example.scheduler.exception.exceptionCode.ExceptionCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ExceptionCode errorCode;

    public CustomException(ExceptionCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
