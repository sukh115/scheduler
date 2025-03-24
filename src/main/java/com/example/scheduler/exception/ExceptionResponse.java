package com.example.scheduler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 단일 예외 응답 포맷 클래스
 * - CustomException, ResponseStatusException 등에서 사용
 */
@Getter
@AllArgsConstructor
public class ExceptionResponse {

    // HTTP 상태 코드
    private int status;

    // 에러 코드 문자열
    private String code;

    // 에러 메시지
    private String message;

    // 예외 발생 시각
    private String timestamp;

}
