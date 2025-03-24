package com.example.scheduler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 유효성 검증 실패 시 응답 포맷 클래스
 * - field:message 형식의 에러 정보 포함
 */
@Getter
@AllArgsConstructor
public class ValidationExceptionResponse {

    // HTTP 상태 코드
    private int status;

    // 필드별 오류 메시지 맵
    private Map<String, String> messages;

    // 예외 발생 시각
    private String timestamp;

}
