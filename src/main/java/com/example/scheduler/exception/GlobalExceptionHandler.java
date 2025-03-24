package com.example.scheduler.exception;

import com.example.scheduler.exception.exceptionCode.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리 클래스
 * - ControllerAdvice를 통해 모든 예외를 공통 포맷으로 처리
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 예외 발생 시 현재 시각 반환 (yyyy-MM-dd HH:mm:ss 포맷)
    private String getNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss"));
    }

    /**
     * CustomException 처리
     *
     * @param e 커스텀 예외
     * @return ExceptionResponse 객체와 상태 코드
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        ExceptionCode error = e.getErrorCode();

        ExceptionResponse response = new ExceptionResponse(
                error.getStatus(),
                error.getCode(),
                error.getMessage(),
                getNow()
        );

        return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
    }

    /**
     * ResponseStatusException 처리
     * - 주로 직접 throw된 404, 400 등의 표준 예외 대응
     *
     * @param e 응답 상태 예외
     * @return ExceptionResponse
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException e) {
        ExceptionResponse response = new ExceptionResponse(
                e.getStatusCode().value(),
                "RESPONSE_STATUS",
                e.getReason(),
                getNow()
        );
        return new ResponseEntity<>(response, e.getStatusCode());
    }

    /**
     * NullPointerException 처리
     * - 디버깅이 필요한 내부 오류
     *
     * @param e NullPointerException
     * @return ExceptionResponse
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "NULL_POINTER",
                "잘못된 요청 : " + e.getMessage(),
                getNow()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 그 외 모든 예외 처리
     *
     * @param e 예측하지 못한 예외
     * @return 500 INTERNAL_SERVER_ERROR 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "서버 내부 오류 : " + e.getMessage(),
                getNow()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Bean Validation 실패 처리
     * - @Valid annotated request DTO의 유효성 검사 실패 시 호출
     *
     * @param e MethodArgumentNotValidException
     * @return ValidationExceptionResponse (필드별 메시지 포함)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            String message = error.getDefaultMessage();
            errors.put(error.getField(), message != null ? message : "알 수 없는 오류");
        }

        return ResponseEntity.badRequest().body(
                new ValidationExceptionResponse(400, errors, getNow())
        );
    }
}
//레이어 ,익셉션, 파라미터 상수처리