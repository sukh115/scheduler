package com.example.scheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {
    private String title;
    private String content;
    private String userName;
    private String password;
}
