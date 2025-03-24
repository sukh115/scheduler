package com.example.scheduler.exception;

import com.example.scheduler.exception.exceptionCode.ExceptionCode;
import lombok.Getter;

/**
 * 사용자 정의 예외 클래스
 * - 예외 코드(Enum)를 기반으로 상세한 예외 정보를 전달
 */
@Getter
public class CustomException extends RuntimeException{
    // 예외 코드 (상세한 상태, 코드, 메시지 포함)
    private final ExceptionCode errorCode;

    /**
     * 예외 생성자
     *
     * @param errorCode 예외 코드 Enum
     */
    public CustomException(ExceptionCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
