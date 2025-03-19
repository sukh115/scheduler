package com.example.scheduler.dto;

import com.example.scheduler.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private Timestamp updatedDate;
    private String userName;

    public ScheduleResponseDto(Long id, String title, String content, Timestamp updateDate, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updatedDate = updateDate;
        this.userName = userName;
    }

}
