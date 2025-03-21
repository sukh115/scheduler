package com.example.scheduler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ValidationExceptionResponse {
    private int status;
    private Map<String, String> messages;
    private String timestamp;
}
