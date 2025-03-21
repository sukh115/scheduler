package com.example.scheduler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private int status;
    private String message;
    private String timestamp;
}
