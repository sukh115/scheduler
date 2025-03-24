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

@ControllerAdvice
public class GlobalExceptionHandler {

    private String getNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss"));
    }
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