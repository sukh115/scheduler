package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;               // 일정 ID
    private String title;          // 일정 제목
    private String content;        // 일정 내용
    private Timestamp createDate;  // 생성 날짜
    private Timestamp updateDate;  // 수정 날짜
    private String userName;       // 사용자 이름
    private String password;

    public ScheduleResponseDto(Long id, String title, String content, Timestamp createDate, Timestamp updateDate, String userName, String password) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.userName = userName;
        this.password = password;
    }
}