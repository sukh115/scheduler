package com.example.scheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String updatedDate;
    private Long authorId;

    public ScheduleResponseDto(Long id, String title, String content, String updateDate, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updatedDate = updateDate;
        this.authorId = authorId;
    }
}
